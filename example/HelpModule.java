import nirusu.nirucmd.BaseModule;
import nirusu.nirucmd.annotation.Command;

public class HelpModule extends BaseModule {

    @Command(
        key = "ping",
        description = "Ping the bot",
        context = {Command.Context.GUILD, Command.Context.PRIVATE})
    public void ping() {
        // TODO: impelemnt ping command
        // something like:
        // ctx.reply("Pong!");
    }

}
