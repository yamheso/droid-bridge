package commands.package_manager;

import commands.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListInstrumentationCommand implements Command {

    private final String packageName;
    private final boolean shouldShowApk;

    private ListInstrumentationCommand(String packageName, boolean shouldShowApk) {
        this.packageName = packageName;
        this.shouldShowApk = shouldShowApk;
    }

    @Override
    public List<String> getCommandComponents() {
        List<String> commandComponents = new ArrayList<>();
        commandComponents.add("pm list instrumentation");
        if (shouldShowApk) commandComponents.add("-f");
        if (!Objects.isNull(packageName)) commandComponents.add(packageName);
        return commandComponents;
    }

    @Override
    public boolean isShellCommand() {
        return true;
    }

    public static final class Builder {

        private String packageName;
        private boolean shouldShowApk;

        public ListInstrumentationCommand.Builder setPackageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public ListInstrumentationCommand.Builder setShouldShowApk(boolean shouldShowApk) {
            this.shouldShowApk = shouldShowApk;
            return this;
        }

        public ListInstrumentationCommand build() {
            return new ListInstrumentationCommand(packageName, shouldShowApk);
        }
    }
}