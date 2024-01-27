package commands.package_manager.permission_manager;

import commands.Command;
import commands.Keychain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListPermissionsCommand implements Command {

    private Keychain key;
    private String group;

    private ListPermissionsCommand(Keychain key, String group) {
        this.key = key;
        this.group = group;
    }

    @Override
    public List<String> getCommandComponents() {
        List<String> commandComponents = new ArrayList<>();
        commandComponents.add("pm list permissions");
        if (Objects.nonNull(key)) {
            assert key instanceof GroupPermissionsKey;
            if (!key.equals(GroupPermissionsKey.ALL)) commandComponents.add(key.getKey());
        }
        if (Objects.nonNull(group)) commandComponents.add(group);
        return commandComponents;
    }

    @Override
    public boolean isShellCommand() {
        return true;
    }

    public static final class Builder {

        private Keychain key;
        private String group;

        public ListPermissionsCommand.Builder setKey(Keychain key) {
            this.key = key;
            return this;
        }

        public ListPermissionsCommand.Builder setGroup(String group) {
            this.group = group;
            return this;
        }

        public ListPermissionsCommand build() {
            return new ListPermissionsCommand(key, group);
        }
    }
}