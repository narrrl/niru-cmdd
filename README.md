
# Niru-CMDD

This is a command system for [Discord4J](https://github.com/Discord4J/Discord4J) that I use in my private Discord-Bot [Nirubot](https://github.com/Nirusu99/nirubot)

## Installation

[![](https://jitpack.io/v/Nirusu99/niru-cmdd.svg)](https://jitpack.io/#Nirusu99/niru-cmdd)

```xml
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>

    	<dependency>
	    <groupId>com.github.Nirusu99</groupId>
	    <artifactId>niru-cmdd</artifactId>
	    <version>$VERSION</version>
	</dependency>
```

## Usage

[Basic example](https://github.com/Nirusu99/niru-cmdd/tree/main/example)

```java
public class HelpModule extends BaseModule {

    @Command(
        key = "ping",
        description = "Ping the bot",
        context = {Command.Context.GUILD, Command.Context.PRIVATE})
    public void ping() {
        ctx.reply("Pong!");
    }

}

public class Bot {

    public Bot(@Nonnull String token) {
        // create jd4 client
        DiscordClient client = DiscordClient.create(token);
        // connect to gateway
        GatewayDiscordClient gateway = client.login().block();
        // create dispatcher
        dispatcher = new CommandDispatcher.Builder()
                // add package that contains the commands
                .addPackage("nirusu.nirubot.command").build();

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
                ctx.setArgs(Arras.asList(raw.substring(prefix.length()).split("\\s+")));
                // run dispatcher
                try {
                    dispatcher.run(ctx, ctx.getKey());
                } catch (NoSuchCommandException e) {
                    ctx.reply("Unknown command!");
                }
            }
        });

        gateway.onDisconnect().block();
    }
}
```
