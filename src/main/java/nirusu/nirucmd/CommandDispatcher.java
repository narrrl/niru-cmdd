package nirusu.nirucmd;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.reflections.Reflections;

import nirusu.nirucmd.annotation.Command;
import nirusu.nirucmd.exception.NoSuchCommandException;

/**
 * This class handles all commands
 *
 * This class loads all command modules on startup with the given packages.
 * After the modules are build, the commands can be invoked and executed with a given key.
 *
 * The command modules must extend {@link BaseModule}
 * The commands must annote {@link Command}
 */

public class CommandDispatcher {
    private Set<Class<? extends BaseModule>> modules;

    public static class Builder {
        private HashSet<String> packages;

        public Builder() {
            packages = new HashSet<>();
        }

        public Builder addPackage(@Nonnull String pkg) {
            packages.add(pkg);
            return this;
        }

        public CommandDispatcher build() {
            return new CommandDispatcher(this);
        }
    }

    private CommandDispatcher(Builder b) {
        for (String pkg : b.packages) {
            Reflections ref = new Reflections(pkg);
            modules = ref.getSubTypesOf(BaseModule.class);
        }
    }


    public void run(@Nonnull CommandContext ctx, @Nonnull String key)
            throws NoSuchCommandException {
        BaseModule module = getModuleWith(key);
        Method refl = getMethodWith(module, key);
        module.setCommandContext(ctx);
        try {
            refl.invoke(module);
        } catch (IllegalAccessException |
                InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException argsE) {
            System.err.println("Commands shouldn't have parameters");
            argsE.printStackTrace();
        }
    }

    private BaseModule getModuleWith(@Nonnull String key)
            throws NoSuchCommandException {
        for (Class<? extends BaseModule> md : modules) {
            if (hasMethodWith(md, key)) {
                try {
                    return md.getConstructor().newInstance();
                } catch (IllegalAccessException | InstantiationException
                        | InvocationTargetException | NoSuchMethodException e ) {
                    e.printStackTrace();
                }
            }
        }
        throw new NoSuchCommandException();
    }

    private Method getMethodWith(@Nonnull BaseModule module, @Nonnull String key)
            throws NoSuchCommandException {
        for (Method refl : module.getClass().getMethods()) {
            if (refl.isAnnotationPresent(Command.class)) {
                for (String k : refl.getAnnotation(Command.class).key()) {
                    if (k.equals(key)) {
                        return refl;
                    }
                }
            }
        }
        throw new NoSuchCommandException();
    }

    private boolean hasMethodWith(@Nonnull Class<? extends BaseModule> module, @Nonnull String key) {
        for (Method refl : module.getClass().getMethods()) {
            if (refl.isAnnotationPresent(Command.class)) {
                for (String k : refl.getAnnotation(Command.class).key()) {
                    if (k.equals(key)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
