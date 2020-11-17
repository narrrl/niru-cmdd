package nirusu.nirucmd;

import javax.annotation.Nonnull;

public abstract class BaseModule {
    private CommandContext ctx;

    public void setCommandContext(@Nonnull CommandContext ctx) {
        this.ctx = ctx;
    }
    
}
