package commands.logcat.dumpsys;

import commands.Command;

import java.util.ArrayList;
import java.util.List;

public class DumpsysCommand implements Command {

    private final String service;

    private DumpsysCommand(String service) {
        this.service = service;
    }

    @Override
    public List<String> getCommandComponents() {
        List<String> commandComponents = new ArrayList<>();
        commandComponents.add("dumpsys");
        commandComponents.add(service);
        return commandComponents;
    }

    @Override
    public boolean isShellCommand(){
        return true;
    }

    public static final class Builder {

        private String service;
        private String key;

        public DumpsysCommand.Builder setService(String service) {
            this.service = service ;
            return this;
        }

        public DumpsysCommand build() {
            return new DumpsysCommand(service);
        }
    }
}