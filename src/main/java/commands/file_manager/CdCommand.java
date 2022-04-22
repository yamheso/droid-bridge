package commands.file_manager;

import commands.Command;

import java.util.ArrayList;
import java.util.List;

public class CdCommand implements Command {

    private String path;

    private CdCommand(String path) {
        this.path = path;
    }

    @Override
    public List<String> getCommandComponents() {
        List<String> commandComponents = new ArrayList<>();
        commandComponents.add("cd");
        commandComponents.add(path);
        return commandComponents;
    }

    @Override
    public boolean isShellCommand() {
        return true;
    }

    public static final class Builder {

        private String path;

        public CdCommand.Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public CdCommand build() {
            return new CdCommand(path);
        }
    }
}