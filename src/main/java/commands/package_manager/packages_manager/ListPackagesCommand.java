package commands.package_manager.packages_manager;

import commands.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListPackagesCommand implements Command {

    private final PackagesListKey key;
    private final String keyword;

    private ListPackagesCommand(PackagesListKey key, String keyword) {
        this.key = key;
        this.keyword = keyword;
    }

    @Override
    public List<String> getCommandComponents() {
        if (Objects.nonNull(key) && Objects.nonNull(keyword)) throw new Error("Only key or keyword can be set");
        List<String> commandComponents = new ArrayList<>();
        commandComponents.add("pm list packages");
        if (Objects.nonNull(key)) {
            if (!key.equals(PackagesListKey.ALL)) commandComponents.add(key.getKey());
        } else {
            commandComponents.add(keyword);
        }
        return commandComponents;
    }

    @Override
    public boolean isShellCommand() {
        return true;
    }

    public static final class Builder {

        private PackagesListKey key;
        private String keyword;

        public ListPackagesCommand.Builder setKey(PackagesListKey key) {
            this.key = key;
            return this;
        }

        public ListPackagesCommand.Builder setKeyword(String keyword) {
            this.keyword = keyword;
            return this;
        }

        public ListPackagesCommand build() {
            return new ListPackagesCommand(key, keyword);
        }
    }
}