package nirusu.nirucmd;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import nirusu.nirucmd.annotation.Command;

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
    private List<String> packages;
    private List<BaseModule> modules;

    public static class Builder {
        private List<String> packages;

        public Builder() {
            packages = new ArrayList<>();
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
        packages = b.packages;
        modules = new ArrayList<>();
        //TODO: load modules
    }


    public void run(@Nonnull CommandContext ctx, @Nonnull String key) {
        //TODO: implement
    }
}
