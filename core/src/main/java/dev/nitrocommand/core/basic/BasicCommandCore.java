package dev.nitrocommand.core.basic;

import dev.nitrocommand.core.*;
import dev.nitrocommand.core.annotations.*;
import dev.nitrocommand.core.exceptions.InvalidCommandException;
import me.kingtux.simpleannotation.MethodFinder;
import org.apache.commons.lang3.Validate;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class BasicCommandCore<T> implements CommandCore<T> {
    protected List<ArgumentParser> parsers = new ArrayList<>();
    protected List<NitroCommandObject> commandObjects = new ArrayList<>();

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


    @Override
    public NitroCommandObject registerCommand(Object object) {
        Validate.notNull(object, "Warning a command object cant be null");

        NitroCommand command = object.getClass().getAnnotation(NitroCommand.class);
        if (command == null) {
            //Follow the rules dumbass
            throw new InvalidCommandException("All commands must have the annotation @NitroCommand at the type level.");
        }
        Method baseMethod =
                MethodFinder.getFirstMethodWithAnnotation(object.getClass(), BaseCommand.class).orElseThrow(() -> new InvalidCommandException("All commands must have a method with the annotation @BaseCommand."));


        //Build the Command Object
        BasicCommandObject commandObject = new BasicCommandObject(command.command(), null, command.format(), object);
        commandObject.setBaseCommand(new BasicSubCommand(baseMethod, commandObject));
        commandObject.setDescription(command.description());
        //Get all command methods and add
        for (Method method : object.getClass().getMethods()) {
            if (method.getAnnotation(SubCommand.class) != null || method.getAnnotation(SubCommands.class) != null) {
                commandObject.add(new BasicSubCommand(method, commandObject));
            }
        }
        if (supportsTabCompleter()) {
            for (Method method : object.getClass().getMethods()) {
                if (method.isAnnotationPresent(TabCompleter.class)) {
                    commandObject.add(new BasicTabCompleter(method, commandObject));
                }
            }
        }
        if (NitroCMD.LOGGER.isDebugEnabled())
            NitroCMD.LOGGER.debug(String.format("Registering \"%s\".", commandObject.toString()));
        //Add to parser
        commandObjects.add(commandObject);
        return commandObject;
    }

    @Override
    public void registerAllCommands(String packageToLookIn) {
        NitroCMD.INTERNAL_THREAD_POOL.submit(() -> {
            Reflections reflections = new Reflections(packageToLookIn);
            for (Class<?> s : reflections.getTypesAnnotatedWith(AutoLoad.class)) {
                try {
                    registerCommand(s.getConstructor().newInstance());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    NitroCMD.LOGGER.error("Unable to initiate \"" + s.getName() + "\".", e);
                }

            }
        });
    }

    @Override
    //Looks like some reflection magic
    //AKA one big pile of reflection magic
    public void registerAllCommands(String packageToLookIn, Object... value) {
        NitroCMD.INTERNAL_THREAD_POOL.submit(() -> {
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
        });
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

    @Override
    public List<NitroCommandObject> registeredCommands() {
        return commandObjects;
    }

    @Override
    public boolean doesCommandExist(String command) {
        return getCommand(command) != null;
    }

    @Override
    public NitroCommandObject getCommand(String command) {
        Validate.notNull(command, "Command must not be null");
        for (NitroCommandObject object : commandObjects) {
            for (String string : object.aliases()) {
                if (string.equalsIgnoreCase(command)) {
                    return object;
                }
            }
        }

        return null;
    }
}
