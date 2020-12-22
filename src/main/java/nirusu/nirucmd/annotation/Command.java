package nirusu.nirucmd.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import discord4j.core.object.entity.channel.Channel;

/**
 * This class contains the metadata for every command.
 *
 * {@link #key} is the string that triggers the command aka the key for the
 * command. {@link #description} is a short description for the command.
 * {@link #context} are the contexts for this command, can contain both.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {
    String[] key();
    String description();
    Context[] context() default {Context.GUILD, Context.PRIVATE};
    public  enum Context {
        GUILD, PRIVATE, INVALID;

        public static Context getContextFor(Channel.Type type) {
            int v = type.getValue();
            if (v == 1) {
                return PRIVATE;
            } else if (v == 0 || v > 4) {
                return GUILD;
            }
            return INVALID;
        }
    }
}