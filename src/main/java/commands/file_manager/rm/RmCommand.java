package commands.file_manager.rm;

import commands.Command;
import commands.Keychain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RmCommand implements Command {

    private Keychain key;
    private String path;

    private RmCommand(Keychain key, String path) {
        this.key = key;
        this.path = path;
    }

    @Override
    public List<String> getCommandComponents() {
        List<String> commandComponents = new ArrayList<>();
        commandComponents.add("rm");
        if (Objects.nonNull(key)) {
            assert key instanceof RmKey;
            if (!key.equals(RmKey.NONE)) commandComponents.add(key.getKey());
        }
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
        private String path;

        public RmCommand.Builder setKey(Keychain key) {
            this.key = key;
            return this;
        }

        public RmCommand.Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public RmCommand build() {
            return new RmCommand(key, path);
        }
    }
}
