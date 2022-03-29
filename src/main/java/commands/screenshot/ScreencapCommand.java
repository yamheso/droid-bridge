package commands.screenshot;

import commands.Command;

import java.util.ArrayList;
import java.util.List;

public class ScreencapCommand implements Command {

    private String path;

    private ScreencapCommand(String path) {
        this.path = path;
    }

    @Override
    public List<String> getCommandComponents() {
        List<String> commandComponents = new ArrayList<>();
        commandComponents.add("screencap");
        commandComponents.add(path);
        return commandComponents;
    }

    @Override
    public boolean isShellCommand(){
        return true;
    }

    public static final class Builder {

        private String path;

        public ScreencapCommand.Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public ScreencapCommand build() {
            return new ScreencapCommand(path);
        }
    }
}