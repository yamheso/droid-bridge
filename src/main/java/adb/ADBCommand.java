package adb;

import commands.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

 final class ADBCommand implements Command {

    private String deviceSerial;
    private Integer transportId;
    private Command command;

    private ADBCommand(String deviceSerial, Integer transportId, Command command) {
        this.deviceSerial = deviceSerial;
        this.transportId = transportId;
        this.command = command;
    }

    @Override
    public List<String> getCommandComponents() {
        if (Objects.nonNull(deviceSerial) && Objects.nonNull(transportId)) throw new Error("Only serial or transport id of device can be set");
        List<String> commandComponents = new ArrayList<>();
        commandComponents.add("adb");
        if (Objects.nonNull(deviceSerial) || Objects.nonNull(transportId)) {
            commandComponents.add(Objects.nonNull(deviceSerial) ? "-s" : "-t");
            commandComponents.add(Objects.nonNull(deviceSerial) ? deviceSerial : String.valueOf(transportId));
        }
        if (command.isShellCommand()) commandComponents.add("shell");
        commandComponents.addAll(command.getCommandComponents());

        return commandComponents;
    }

    public static class Builder {

        private String deviceSerial;
        private Integer transportId;
        private Command command;

        public Builder setDeviceSerial(String deviceSerial) {
            this.deviceSerial = deviceSerial;
            return this;
        }

        public Builder setTransportId(Integer transportId) {
            this.transportId = transportId;
            return this;
        }

        public Builder setCommand(Command command) {
            this.command = command;
            return this;
        }

        public ADBCommand build() {
            return new ADBCommand(deviceSerial, transportId, command);
        }
    }
}
