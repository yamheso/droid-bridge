package commands.adb_debugging;

import commands.Command;

import java.util.ArrayList;
import java.util.List;

public class KillServerCommand implements Command {

    private KillServerCommand() {
    }

    @Override
    public List<String> getCommandComponents() {
        List<String> commandComponents = new ArrayList<>();
        commandComponents.add("kill-server");
        return commandComponents;
    }

    public static final class Builder {

        public KillServerCommand build() {
            return new KillServerCommand();
        }
    }
}