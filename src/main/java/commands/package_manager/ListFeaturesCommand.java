package commands.package_manager;

import commands.Command;

import java.util.ArrayList;
import java.util.List;

public class ListFeaturesCommand implements Command {

    private ListFeaturesCommand() {
    }

    @Override
    public List<String> getCommandComponents() {
        List<String> commandComponents = new ArrayList<>();
        commandComponents.add("pm list features");
        return commandComponents;
    }

    @Override
    public boolean isShellCommand() {
        return true;
    }

    public static final class Builder {

        public ListFeaturesCommand build() {
            return new ListFeaturesCommand();
        }
    }
}