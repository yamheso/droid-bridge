package commands.package_manager;

import commands.Command;

import java.util.ArrayList;
import java.util.List;

public class ListLibrariesCommand implements Command {

    private ListLibrariesCommand() {
    }

    @Override
    public List<String> getCommandComponents() {
        List<String> commandComponents = new ArrayList<>();
        commandComponents.add("pm list libraries");
        return commandComponents;
    }

    @Override
    public boolean isShellCommand() {
        return true;
    }

    public static final class Builder {

        public ListLibrariesCommand build() {
            return new ListLibrariesCommand();
        }
    }
}