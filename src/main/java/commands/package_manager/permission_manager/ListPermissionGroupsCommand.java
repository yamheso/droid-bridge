package commands.package_manager.permission_manager;

import commands.Command;

import java.util.ArrayList;
import java.util.List;

public class ListPermissionGroupsCommand implements Command {

    @Override
    public List<String> getCommandComponents() {
        List<String> commandComponents = new ArrayList<>();
        commandComponents.add("pm list permission-groups");
        return commandComponents;
    }

    @Override
    public boolean isShellCommand() {
        return true;
    }

    public static final class Builder {

        public ListPermissionGroupsCommand build() {
            return new ListPermissionGroupsCommand();
        }
    }
}