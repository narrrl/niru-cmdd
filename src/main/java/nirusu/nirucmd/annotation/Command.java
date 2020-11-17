package nirusu.nirucmd.annotation;

/**
 * This class contains the metadata for every command.
 *
 * {@link #key} is the string that triggers the command aka the key for the command.
 * {@link #description} is a short description for the command.
 * {@link #context} are the contexts for this command, can contain both.
 */
public @interface Command {
    String[] key();
    String description();
    Context[] context();
    public enum Context {
        GUILD, PRIVATE
    }
}
