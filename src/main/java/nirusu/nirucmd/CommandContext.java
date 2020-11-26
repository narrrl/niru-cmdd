package nirusu.nirucmd;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.Channel;
import discord4j.core.object.entity.channel.MessageChannel;
import nirusu.nirucmd.annotation.Command;

/**
 * This class represents the current command context (Context (Guild, Private, ...), Event, Message etc.)
 * and contains useful methods to interact with the bot.
 */
public class CommandContext {

    private List<String> args;
    private final Command.Context context;
    private final MessageCreateEvent event;

    /**
     * Creates a new {@link CommandContext}.
     *
     * @param event represents the current event
     */
    public CommandContext(@Nonnull MessageCreateEvent event) {
        this.event = event;
        Channel.Type type = event.getMessage().getChannel().block().getType();
        if (type.equals(Channel.Type.DM)) {
            context = Command.Context.PRIVATE;
        } else if (type.equals(Channel.Type.GUILD_TEXT)) {
            context = Command.Context.GUILD;
        } else {
            //TODO: write own exception
            throw new IllegalArgumentException("Invalid Context");
        }
    }

    public void setArgs(@Nonnull List<String> args) {
        this.args = Collections.unmodifiableList(args);
    }


    /**
     * Shortcut for a reply with a given message @param message
     */
    public void reply(@Nonnull String message) {
        MessageChannel m = event.getMessage().getChannel().block();
        m.createMessage(message).block();
    }

    /**
     * Checks if the current context is equal to @param context
     *
     * @return true if {@link #context} equals @param context
     */
    public boolean isContext(Command.Context context) {
        return this.context.equals(context);
    }


    /**
     * !!! ARGS IS NOT MODIFIABLE !!!
     * {@link Collections#unmodifiableList}
     *
     * @return list of all arguments
     */
    public List<String> getArgs() {
        return this.args;
    }

    public boolean isPrivate() {
        return isContext(Command.Context.PRIVATE);
    }

    public boolean isGuild() {
        return isContext(Command.Context.GUILD);
    }

    public MessageCreateEvent getEvent() {
        return this.event;
    }
}
