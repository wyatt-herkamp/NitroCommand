package dev.nitrocommand.jda4.annotations;

import net.dv8tion.jda.api.Permission;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)

public @interface JDAPermission {
    Permission value() default Permission.MESSAGE_WRITE;
}
