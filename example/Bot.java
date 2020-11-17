import nirusu.nirucmd.CommandContext;
import nirusu.nirucmd.CommandDispatcher;
import nirusu.nirucmd.annotation.Command;

/**
 * This class is not implemented yet, for now it just represents the basic idea
 * */
public class Bot {
    private CommandDispatcher dispatcher;

    //TODO: implement with jd4
    //
    public Bot() {
        // some other bot config stuff
        //
        dispatcher = new CommandDispatcher.Builder()
            .addPackage("nirusu.nirubot.command")
            .build();
    }



    public void onGuildCommand(DummyEvent event) {
        // context of the command
        CommandContext ctx = new CommandContext(event, args, Command.Context.GUILD);

        // command would be executed here
        dispatcher.run(ctx, key);
    }


    public void onPrivateCommand(DummyEvent event) {
        // context of the command
        CommandContext ctx = new CommandContext(event, args, Command.Context.PRIVATE);

        // command would be executed here
        dispatcher.run(ctx, key);

    }
}
