package commands.file_manager.rm;

import commands.Command;
import commands.Keychain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RmCommand implements Command {

    private Keychain key;
    private String path;
    private String newKey;

    private RmCommand(Keychain key, String newKey, String path) {
        this.key = key;
        this.newKey = newKey;
        this.path = path;
    }

    @Override
    public List<String> getCommandComponents() {
        List<String> commandComponents = new ArrayList<>();
        commandComponents.add("rm");
        if (Objects.nonNull(key) && !key.equals(RmKey.NONE)) commandComponents.add(key.getKey());
        Objects.requireNonNull(path, "The path cannot be null");
        commandComponents.add(path);
        return commandComponents;
    }

    @Override
    public boolean isShellCommand() {
        return true;
    }

    public static final class Builder {

        private Keychain key;
        private String newKey;
        private String path;

        public RmCommand.Builder setKey(Keychain key) {
            this.key = key;
            return this;
        }

        public RmCommand.Builder setKey(String newKey) {
            this.newKey = newKey;
            return this;
        }

        public RmCommand.Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public RmCommand build() {
            return new RmCommand(key, newKey, path);
        }
    }
}
