package commands.file_manager;

import commands.Command;

import java.util.ArrayList;
import java.util.List;

public class PushCommand implements Command {

    private final String pathFrom;
    private final String pathTo;

    private PushCommand(String pathFrom, String pathTo) {
        this.pathFrom = pathFrom;
        this.pathTo = pathTo;
    }

    @Override
    public List<String> getCommandComponents() {
        List<String> commandComponents = new ArrayList<>();
        commandComponents.add("push");
        commandComponents.add(pathFrom);
        commandComponents.add(pathTo);
        return commandComponents;
    }

    public static final class Builder {

        private String pathFrom;
        private String pathTo;

        public PushCommand.Builder setFromPath(String pathFrom) {
            this.pathFrom = pathFrom;
            return this;
        }

        public PushCommand.Builder setToPath(String pathTo) {
            this.pathTo = pathTo;
            return this;
        }

        public PushCommand build() {
            return new PushCommand(pathFrom, pathTo);
        }
    }
}