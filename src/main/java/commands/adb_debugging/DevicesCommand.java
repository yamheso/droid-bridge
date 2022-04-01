package commands.adb_debugging;

import commands.Command;

import java.util.ArrayList;
import java.util.List;

public class DevicesCommand implements Command {

    private final boolean shouldLongOutput;

    private DevicesCommand(boolean shouldLongOutput) {
        this.shouldLongOutput = shouldLongOutput;
    }

    @Override
    public List<String> getCommandComponents() {
        List<String> commandComponents = new ArrayList<>();
        commandComponents.add("devices");
        if (shouldLongOutput) commandComponents.add("-l");
        return commandComponents;

    }

    public static final class Builder {

        private boolean shouldLongOutput;

        public DevicesCommand.Builder setShouldBeLongOutput(boolean shouldLongOutput) {
            this.shouldLongOutput = shouldLongOutput;
            return this;
        }

        public DevicesCommand build() {
            return new DevicesCommand(shouldLongOutput);
        }
    }
}