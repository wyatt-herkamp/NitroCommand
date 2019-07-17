package dev.nitrocommand.core.basic;

import dev.nitrocommand.core.*;
import dev.nitrocommand.core.annotations.*;
import dev.nitrocommand.core.exceptions.InvalidCommandException;
import me.kingtux.simpleannotation.MethodFinder;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class BasicCommandCore implements CommandCore {
    private BasicCommandParser parser = new BasicCommandParser();
    private List<ArgumentParser> parsers = new ArrayList<>();

    public BasicCommandCore() {
        NitroCMD.INTERNAL_THREAD_POOL.submit(this::locateAllArgumentParsersAndCreate);
    }

    private void locateAllArgumentParsersAndCreate() {
        Reflections reflections = new Reflections("dev.nitrocommand");
        for (Class<? extends ArgumentParser> t : reflections.getSubTypesOf(ArgumentParser.class)) {
            try {

                if (Utils.contructorExists(t, CommandCore.class)) {
                    ArgumentParser parser = t.getConstructor(CommandCore.class).newInstance(this);
                    registerArgumentParser(parser);

                } else {
                    ArgumentParser parser = t.getConstructor().newInstance();
                    registerArgumentParser(parser);
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                NitroCMD.LOGGER.warn("Unable to create ArgumentParser", e);
            }

        }
    }

    protected boolean executeCommand(String message, Object... otherArguments) {
        NitroSubCommand command = parser.locateCommand(message);
        if (command == null) {
            return false;
        }

        parser.executeCommand(command, Utils.getArguments(message, command, command.method().getParameters(), otherArguments, this));

        return true;
    }

    @Override
    public void registerCommand(Object object) {
        NitroCommand command = object.getClass().getAnnotation(NitroCommand.class);
        if (command == null) {
            //Follow the rules dumbass
            throw new InvalidCommandException("All commands must have the annotation @NitroCommand at the type level.");
        }
        Method baseMethod = MethodFinder.getFirstMethodWithAnnotation(object.getClass(), BaseCommand.class);

        if (baseMethod == null) {
            throw new InvalidCommandException("All commands must have a method with the annotation @BaseCommand.");
        }
        //Build the Command Object
        BasicCommandObject commandObject = new BasicCommandObject(command.command(), null, object);
        commandObject.setBaseCommand(new BasicSubCommand(baseMethod, commandObject));
        commandObject.setDescription(command.description());
        //Get all command methods and add
        for (Method method : object.getClass().getMethods()) {
            if (method.getAnnotation(SubCommand.class) != null || method.getAnnotation(SubCommands.class) != null) {
                commandObject.add(new BasicSubCommand(method, commandObject));
            }
        }
        if (NitroCMD.LOGGER.isDebugEnabled())
            NitroCMD.LOGGER.debug(String.format("Registering \"%s\".", commandObject.toString()));
        //Add to parser
        parser.add(commandObject);
    }

    @Override
    public void registerAllCommands(String packageToLookIn) {
        Reflections reflections = new Reflections(packageToLookIn);
        for (Class<?> s : reflections.getTypesAnnotatedWith(AutoLoad.class)) {
            try {
                registerCommand(s.getConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                NitroCMD.LOGGER.error("Unable to initiate \"" + s.getName() + "\".", e);
            }
        }
    }

    @Override
    //Looks like some reflection magic
    //AKA one big pile of reflection magic
    public void registerAllCommands(String packageToLookIn, Object... value) {
        Class<?>[] types = new Class<?>[value.length];
        int i = 0;
        for (Object o : value) {
            types[i] = o.getClass();
            i++;
        }
        Reflections reflections = new Reflections(packageToLookIn);
        for (Class<?> s : reflections.getTypesAnnotatedWith(AutoLoad.class)) {
            try {
                registerCommand(s.getConstructor(types).newInstance(value));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                NitroCMD.LOGGER.error("Unable to initiate \"" + s.getName() + "\".", e);
            }
        }
    }

    @Override
    public void registerArgumentParser(ArgumentParser parser) {
        NitroCMD.LOGGER.debug("Registering ArgumentParser " + parser.getClass().getCanonicalName());
        parsers.add(parser);
    }

    @Override
    public ArgumentParser getArgumentParser(Class<?> type) {
        return parsers.stream().filter(c -> c.getType().isAssignableFrom(type)).findFirst().get();
    }
}
