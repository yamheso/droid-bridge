package commands.adb_debugging;

import commands.Command;

import java.util.ArrayList;
import java.util.List;

public class StartServerCommand implements Command {

    private StartServerCommand() {
    }

    @Override
    public List<String> getCommandComponents() {
        List<String> commandComponents = new ArrayList<>();
        commandComponents.add("start-server");
        return commandComponents;
    }

    public static final class Builder {

        public StartServerCommand build() {
            return new StartServerCommand();
        }
    }
}