package commands.package_manager;

import commands.Command;

import java.util.ArrayList;
import java.util.List;

public class PathCommand implements Command {

    private final String packageName;

    private PathCommand(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public List<String> getCommandComponents() {
        List<String> commandComponents = new ArrayList<>();
        commandComponents.add("pm path");
        commandComponents.add(packageName);
        return commandComponents;
    }

    @Override
    public boolean isShellCommand(){
        return true;
    }

    public static final class Builder {

        private String packageName;

        public PathCommand.Builder setPackageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public PathCommand build() {
            return new PathCommand(packageName);
        }
    }
}