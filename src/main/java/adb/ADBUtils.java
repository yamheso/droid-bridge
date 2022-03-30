package adb;

import commands.adb_debugging.DevicesCommand;
import commands.file_manager.PullCommand;
import commands.package_manager.PathCommand;
import commands.package_manager.UninstallCommand;
import commands.package_manager.install_manager.InstallCommand;
import commands.package_manager.install_manager.InstallKey;
import commands.package_manager.list_manager.ListPackagesCommand;
import commands.package_manager.list_manager.PackagesListKey;
import commands.screenshot.ScreencapCommand;
import commands.system.get_prop.DeviceProperties;
import commands.system.get_prop.GetpropCommand;
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
                        .setShouldBeLongOutput(isLongOutput)
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

    public String uninstallPackage(String packageName, boolean shouldKeepData) {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new UninstallCommand.Builder()
                        .setPackageName(packageName)
                        .setShouldKeepData(shouldKeepData)
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }

    public List<String> getPackagesList(String keyword, String regex) {
        return RegexHelper.getRegexMatch(getPackages(keyword), regex);
    }

    public List<String> getPackagesList(String keyword) {
        return RegexHelper.getRegexMatch(getPackages(keyword), ":(.+?)package|:(.+)");
    }

    public List<String> getPackagesList(PackagesListKey key, String regex) {
        return RegexHelper.getRegexMatch(getPackages(key), regex);
    }

    public List<String> getPackagesList(PackagesListKey key) {
        return RegexHelper.getRegexMatch(getPackages(key), ":(.+?)package|:(.+)");
    }

    public String getPackages(PackagesListKey key) {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new ListPackagesCommand.Builder()
                        .setKey(key)
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }

    public String getPackages(String keyword) {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new ListPackagesCommand.Builder()
                        .setKeyword(keyword)
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }

    public String getPackagePath(String packageName) {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new PathCommand.Builder()
                        .setPackageName(packageName)
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }

    public String installSpecifyingPackageName(String packageName, String... paths) {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new InstallCommand.Builder()
                        .setPaths(paths)
                        .setKey(InstallKey.SPECIFY_PACKAGE_NAME)
                        .setPackageName(packageName)
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }

    public String installPackage(InstallKey key, String... paths) {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new InstallCommand.Builder()
                        .setPaths(paths)
                        .setKey(key)
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }

    public String installPackage(String... paths) {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new InstallCommand.Builder()
                        .setPaths(paths)
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }
}