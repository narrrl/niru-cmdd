import nirusu.nirucmd.BaseModule;
import nirusu.nirucmd.annotation.Command;

public class HelpModule extends BaseModule {

    @Command(
        key = "ping",
        description = "Ping the bot",
        context = {Command.Context.GUILD, Command.Context.PRIVATE})
    public void ping() {
        ctx.reply("Pong!");
    }

    @Command(
        key = "help",
        description = "Lists usefull information about the bot and its commands",
        context = {Command.Context.GUILD, Command.Context.PRIVATE})
    public void help() {
        //TODO implement help command
    }

}
