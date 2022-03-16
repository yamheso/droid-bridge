package commands;

import java.util.ArrayList;
import java.util.List;

public class DevicesCommand implements Command {

    private boolean isLongOutput;

    private DevicesCommand(boolean isLongOutput) {
        this.isLongOutput = isLongOutput;
    }

    @Override
    public List<String> getCommandComponents() {
        List<String> commandComponents = new ArrayList<>();
        commandComponents.add("devices");
        if (isLongOutput) commandComponents.add("-l");
        return commandComponents;

    }

    public static final class Builder {

        private boolean isLongOutput;

        public DevicesCommand.Builder setLongOutput(boolean isLongOutput) {
            this.isLongOutput = isLongOutput;
            return this;
        }

        public DevicesCommand build() {
            return new DevicesCommand(isLongOutput);
        }
    }
}
