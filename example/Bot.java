import java.util.List;

import javax.annotation.Nonnull;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import nirusu.nirucmd.CommandContext;
import nirusu.nirucmd.CommandDispatcher;
import nirusu.nirucmd.annotation.Command;

/**
 * This class is not implemented yet, for now it just represents the basic idea
 * */
public class Bot {
    private CommandDispatcher dispatcher;

    public Bot(@Nonnull String token) {
        // create jd4 client
        DiscordClient client = DiscordClient.create(token);
        // connect to gateway
        GatewayDiscordClient gateway = client.login().block();
        // create dispatcher
        dispatcher = new CommandDispatcher.Builder()
            // add package that contains the commands
            .addPackage("nirusu.nirubot.command")
            .build();

        // create trigge
        gateway.on(MessageCreateEvent.class).subscribe(event -> {

            Message mes = event.getMessage();
            // get message content
            String raw = mes.getContent();
            raw = raw == null ? "" : raw;
            String prefix = "!";
            // check if message starts with prefix !
            if (raw.startsWith(prefix) && raw.length() > prefix.length()) {
                // create the CommandContext
                CommandContext ctx = new CommandContext(event);
                List<String> args = Arrays.asList(raw.split("\\w+"));
                if (args.size() > 0) {
                    // get key to trigger command
                    String key = args.get(0);
                    // remove the key from the arguments
                    args.remove(key);
                    // set arguments for the command context
                    ctx.setArgs(args);
                    // run dispatcher
                    dispatcher.run(ctx, key); }
            }
        });
    }
}
