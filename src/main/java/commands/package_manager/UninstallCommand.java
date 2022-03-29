package commands.package_manager;

import commands.Command;

import java.util.ArrayList;
import java.util.List;

public class UninstallCommand implements Command {

    private String packageName;
    private boolean shouldKeepData;

    private UninstallCommand(String packageName, boolean shouldKeepData) {
        this.packageName = packageName;
        this.shouldKeepData = shouldKeepData;
    }

    @Override
    public List<String> getCommandComponents() {
        List<String> commandComponents = new ArrayList<>();
        commandComponents.add("pm uninstall");
        if (shouldKeepData) commandComponents.add("-k");
        commandComponents.add(packageName);
        return commandComponents;
    }

    @Override
    public boolean isShellCommand(){
        return true;
    }

    public static final class Builder {

        private String packageName;
        private boolean shouldKeepData;

        public UninstallCommand.Builder setPackageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public UninstallCommand.Builder setShouldKeepData(boolean shouldKeepData) {
            this.shouldKeepData = shouldKeepData;
            return this;
        }

        public UninstallCommand build() {
            return new UninstallCommand(packageName, shouldKeepData);
        }
    }
}