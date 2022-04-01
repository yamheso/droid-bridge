package commands.package_manager;

import commands.Command;

import java.util.ArrayList;
import java.util.List;

public class ListUsersCommand implements Command {

    private ListUsersCommand() {
    }

    @Override
    public List<String> getCommandComponents() {
        List<String> commandComponents = new ArrayList<>();
        commandComponents.add("pm list users");
        return commandComponents;
    }

    @Override
    public boolean isShellCommand() {
        return true;
    }

    public static final class Builder {

        public ListUsersCommand build() {
            return new ListUsersCommand();
        }
    }
}