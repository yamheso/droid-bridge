package commands.system.get_prop;

import commands.Command;

import java.util.ArrayList;
import java.util.List;

public class GetpropCommand implements Command {

    private String property;

    private GetpropCommand(String property) {
        this.property = property;
    }

    @Override
    public List<String> getCommandComponents() {
        List<String> commandComponents = new ArrayList<>();
        commandComponents.add("getprop");
        commandComponents.add(property);
        return commandComponents;
    }

    @Override
    public boolean isShellCommand(){
        return true;
    }

    public static final class Builder {

        private String property;

        public GetpropCommand.Builder setProperty(String property) {
            this.property = property;
            return this;
        }

        public GetpropCommand build() {
            return new GetpropCommand(property);
        }
    }
}