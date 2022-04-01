package commands.package_manager.install_manager;

import commands.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class InstallCommand implements Command {

    private final InstallKey key;
    private final String[] paths;
    private final String packageName;

    private InstallCommand(InstallKey key, String[] paths, String packageName) {
        this.key = key;
        this.paths = paths;
        this.packageName = packageName;
    }

    @Override
    public List<String> getCommandComponents() {
        List<String> commandComponents = new ArrayList<>();
        if (paths.length > 1) {
            commandComponents.add("install-multiple");
        } else {
            commandComponents.add("install");
        }
        if (Objects.nonNull(key)) {
            commandComponents.add(key.getKey());
            if (key.equals(InstallKey.SPECIFY_PACKAGE_NAME)) {
                Objects.requireNonNull(packageName, String.format("When using [%s] option, then package name cannot be null", key.getKey()));
                commandComponents.add(packageName);
            }
        }
        commandComponents.addAll(Arrays.asList(paths));
        return commandComponents;
    }

    public static final class Builder {

        private InstallKey key;
        private String[] paths;
        private String packageName;

        public InstallCommand.Builder setKey(InstallKey key) {
            this.key = key;
            return this;
        }

        public InstallCommand.Builder setPaths(String[] paths) {
            this.paths = paths;
            return this;
        }

        public InstallCommand.Builder setPackageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public InstallCommand build() {
            return new InstallCommand(key, paths, packageName);
        }
    }
}