package adb;

import commands.adb_debugging.DevicesCommand;
import commands.system.get_prop.DeviceProperties;
import commands.system.get_prop.GetpropCommand;
import commands.file_manager.PullCommand;
import commands.screenshot.ScreencapCommand;
import utils.ConsoleCommandExecutor;
import utils.RegexHelper;

import java.util.List;

public class ADBUtils {

    private Integer transportID;
    private String serial;

    public ADBUtils() {
    }

    public ADBUtils(String serial) {
        this.serial = serial;
    }

    public ADBUtils(Integer transportID) {
        this.transportID = transportID;
    }

    public void takeScreenshot(String path) {
        ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new ScreencapCommand.Builder()
                        .setPath(path)
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }

    public String getDevicesId(boolean isLongOutput) {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new DevicesCommand.Builder()
                        .setLongOutput(isLongOutput)
                        .build())
                .build());
    }

    public String getDeviceProperty(DeviceProperties property) {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new GetpropCommand.Builder()
                        .setProperty(property.getPropertyName())
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }

    public List<String> getDevicePropertyNamesList(String regex) {
        String allCommand = ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new GetpropCommand.Builder()
                        .setProperty(new DeviceProperties().setAllProperties().getPropertyName())
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
        return RegexHelper.getRegexMatch(allCommand, regex);
    }

    public String pullFile(String pathFrom, String pathTo) {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new PullCommand.Builder()
                        .setFromPath(pathFrom)
                        .setToPath(pathTo)
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }
}