package commands.logcat.dumpsys;

import commands.Command;
import commands.Keychain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DumpsysCommand implements Command {

    private final Map<Keychain, String> keyValue;
    private final String service;

    private DumpsysCommand(Map<Keychain, String> keyValue, String service) {
        this.keyValue = keyValue;
        this.service = service;
    }

    @Override
    public List<String> getCommandComponents() {
        List<String> commandComponents = new ArrayList<>();
        commandComponents.add("dumpsys");
        if (Objects.nonNull(keyValue)) {
            final Keychain key = keyValue.keySet().stream().findFirst().orElseThrow(() -> new Error("The command key was not set!"));
            assert key instanceof DumpsysKey;
            commandComponents.add(key.getKey());
            commandComponents.add(keyValue.get(key));
        }
        if (Objects.nonNull(service)) {
            commandComponents.add(service);
        }
        return commandComponents;
    }

    @Override
    public boolean isShellCommand() {
        return true;
    }

    public static final class Builder {

        private String service;
        private Map<Keychain, String> keyValue;

        public DumpsysCommand.Builder setKeyValue(Map<Keychain, String> keyValue) {
            this.keyValue = keyValue;
            return this;
        }

        public DumpsysCommand.Builder setService(String service) {
            this.service = service;
            return this;
        }

        public DumpsysCommand build() {
            return new DumpsysCommand(keyValue, service);
        }
    }
}