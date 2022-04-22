package commands.file_manager.ls;

import commands.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LsCommand implements Command {

    private LsKey key;
    private String path;

    private LsCommand(LsKey key, String path) {
        this.key = key;
        this.path = path;
    }

    @Override
    public List<String> getCommandComponents() {
        List<String> commandComponents = new ArrayList<>();
        commandComponents.add("ls");
        if (Objects.nonNull(key) && !key.equals(LsKey.ALL)) commandComponents.add(key.getKey());
        if (Objects.nonNull(path)) commandComponents.add(path);
        return commandComponents;
    }

    @Override
    public boolean isShellCommand() {
        return true;
    }

    public static final class Builder {

        private LsKey key;
        private String path;

        public LsCommand.Builder setKey(LsKey key) {
            this.key = key;
            return this;
        }

        public LsCommand.Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public LsCommand build() {
            return new LsCommand(key, path);
        }
    }
}