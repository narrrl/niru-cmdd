package nirusu.nirucmd;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;

public class CommandToRun {
    private static final CommandToRun INVALID;
    static {
        INVALID = new CommandToRun() {
            @Override
            public void run() {
                // null object
            }
        };
    }
    private final Method command;
    private final BaseModule module;

    public CommandToRun(@Nonnull Method command, @Nonnull BaseModule module) {
        this.command = command;
        this.module = module;
    }

    private CommandToRun() {
        this.command = null;
        this.module = null;
    }

    public void run() {
        try {
            command.invoke(module);
        } catch (IllegalAccessException e) {
            CommandDispatcher.getLogger().error("Command couldn't be invoked. No public modifier?", e);
        } catch (IllegalArgumentException argsE) {
            CommandDispatcher.getLogger().error("Commands shouldn't have parameters", argsE);
        } catch (InvocationTargetException e) {
            CommandDispatcher.getLogger().error(e.getMessage(), e);
        }
    }

    public static CommandToRun getInvalid() {
        return INVALID;
    }
    
}
