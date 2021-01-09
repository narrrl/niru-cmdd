import javax.annotation.Nonnull;
import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import nirusu.nirucmd.CommandContext;
import nirusu.nirucmd.CommandDispatcher;

public class Bot {

    public Bot(@Nonnull String token) {
        // create dispatcher
        dispatcher = new CommandDispatcher.Builder()
                // add package that contains the commands
                .addPackage("nirusu.nirubot.command").build();
        // create jd4 client
        DiscordClient client = DiscordClient.create(token);
        // connect to gateway
        client.login().blockOptional().ifPresent(gateway -> {
            // create trigge
            gateway.on(MessageCreateEvent.class).subscribe(event -> {

                Message mes = event.getMessage();
                // get message content
                String raw = mes.getContent();
                raw = raw == null ? "" : raw;
                String prefix = "!";
                // check if message starts with prefix !
                if (raw.startsWith(prefix) && raw.length() > prefix.length()) {
                    // create args by separating each argument at whitespaces
                    String[] args = raw.substring(prefix.length()).split("\\s+");
                    CommandContext ctx = new CommandContext(event);
                    // set args for command context
                    ctx.setArgsAndKey(args, args[0], true);
                    CommandToRun cmd = dispatcher.getCommand(ctx, ctx.getKey());
                    cmd.run();
                }
            });

            gateway.onDisconnect().block();
        });

    }
}
