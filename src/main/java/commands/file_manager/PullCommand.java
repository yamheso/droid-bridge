package commands.file_manager;

import commands.Command;

import java.util.ArrayList;
import java.util.List;

public class PullCommand implements Command {

    private String pathFrom;
    private String pathTo;

    private PullCommand(String pathFrom, String pathTo) {
        this.pathFrom = pathFrom;
        this.pathTo = pathTo;
    }

    @Override
    public List<String> getCommandComponents() {
        List<String> commandComponents = new ArrayList<>();
        commandComponents.add("pull");
        commandComponents.add(pathFrom);
        commandComponents.add(pathTo);
        return commandComponents;
    }

    public static final class Builder {

        private String pathFrom;
        private String pathTo;

        public PullCommand.Builder setFromPath(String pathFrom) {
            this.pathFrom = pathFrom;
            return this;
        }

        public PullCommand.Builder setToPath(String pathTo) {
            this.pathTo = pathTo;
            return this;
        }

        public PullCommand build() {
            return new PullCommand(pathFrom, pathTo);
        }
    }
}
