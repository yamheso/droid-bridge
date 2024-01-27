package adb;

import commands.Keychain;
import commands.adb_debugging.DevicesCommand;
import commands.adb_debugging.KillServerCommand;
import commands.adb_debugging.StartServerCommand;
import commands.file_manager.CdCommand;
import commands.file_manager.PullCommand;
import commands.file_manager.PushCommand;
import commands.file_manager.ls.LsCommand;
import commands.file_manager.ls.LsKey;
import commands.file_manager.rm.RmCommand;
import commands.file_manager.rm.RmKey;
import commands.logcat.dumpsys.DumpsysCommand;
import commands.logcat.dumpsys.DumpsysKey;
import commands.package_manager.*;
import commands.package_manager.install_manager.InstallCommand;
import commands.package_manager.install_manager.InstallKey;
import commands.package_manager.packages_manager.ListPackagesCommand;
import commands.package_manager.packages_manager.PackagesListKey;
import commands.package_manager.permission_manager.GroupPermissionsKey;
import commands.package_manager.permission_manager.ListPermissionGroupsCommand;
import commands.package_manager.permission_manager.ListPermissionsCommand;
import commands.screenshot.ScreencapCommand;
import commands.system.get_prop.DeviceProperties;
import commands.system.get_prop.GetpropCommand;
import utils.ConsoleCommandExecutor;
import utils.Regex;
import utils.RegexHelper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ADBUtils {

    private Integer transportID;
    private String serial;
    private static Map<String, String> allConnectedDevices;

    static {
        getAttachedDevicesListInfo();
    }

    public ADBUtils() {
    }

    public ADBUtils(String serial) {
        this.serial = serial;
    }

    public ADBUtils(Integer transportID) {
        this.transportID = transportID;
    }

    public static void setAllConnectedDevices(Map<String, String> serialNumbers) {
        allConnectedDevices = serialNumbers;
    }

    public static Map<String, String> getAllConnectedDevices() {
        return allConnectedDevices;
    }

    public static List<String> getOkStatusDeviceIds() {
        return getAllConnectedDevices()
                .entrySet().stream()
                .filter(entry -> entry.getValue().equals(DeviceStatus.OK_STATUS.getDeviceStatus()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public static List<String> getDeviceIdsByStatus(DeviceStatus status) {
        return getAllConnectedDevices()
                .entrySet().stream()
                .filter(entry -> entry.getValue().equals(status.getDeviceStatus()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * device: The device is connected and ready for use.
     * offline: The device is connected but not available or is in offline mode.
     * unauthorized: The device is connected but not authorized for use with adb.
     * bootloader: The device is in bootloader or fastboot mode.
     * recovery: The device is in recovery mode.
     * no permissions: There are no permissions to access the device.
     * unknown: The state of the device is unknown or undefined.
     */
    public enum DeviceStatus {
        OK_STATUS("device"),
        OFFLINE_STATUS("offline"),
        UNAUTHORIZED_STATUS("unauthorized"),
        BOOTLOADER_STATUS("bootloader"),
        RECOVERY_STATUS("recovery"),
        NO_PERMISSIONS_STATUS("no permissions"),
        UNKNOWN_STATUS("unknown");

        private String status;

        DeviceStatus(String status) {
            this.status = status;
        }

        public String getDeviceStatus() {
            return status;
        }
    }

    public void killServer() {
        ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new KillServerCommand.Builder().build())
                .build());
    }

    public void startServer() {
        ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new StartServerCommand.Builder().build())
                .build());
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

    public String getAttachedDevicesListInfo(boolean isLongOutput) {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new DevicesCommand.Builder()
                        .setShouldBeLongOutput(isLongOutput)
                        .build())
                .build());
    }

    public static String getAttachedDevicesListInfo() {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new DevicesCommand.Builder()
                        .setShouldBeLongOutput(false)
                        .build())
                .build());
    }

    public List<String> getDeviceIds(String regex) {
        String allDevices = ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new DevicesCommand.Builder()
                        .setShouldBeLongOutput(false)
                        .build())
                .build());
        return RegexHelper.getRegexMatch(allDevices, regex);

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
        String allCommands = ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new GetpropCommand.Builder()
                        .setProperty(new DeviceProperties().setAllProperties().getPropertyName())
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
        return RegexHelper.getRegexMatch(allCommands, regex);
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

    public String pushFile(String pathFrom, String pathTo) {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new PushCommand.Builder()
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
        return RegexHelper.getRegexMatch(getPackages(keyword), Regex.LIST_PACKAGE);
    }

    public List<String> getPackagesList(PackagesListKey key, String regex) {
        return RegexHelper.getRegexMatch(getPackages(key), regex);
    }

    public List<String> getPackagesList(PackagesListKey key) {
        return RegexHelper.getRegexMatch(getPackages(key), Regex.LIST_PACKAGE);
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

    public List<String> getPermissionGroups() {
        String allGroups = ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new ListPermissionGroupsCommand.Builder().build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
        return RegexHelper.getRegexMatch(allGroups, Regex.PERMISSION_GROUP);
    }

    public List<String> getPermissionGroups(String regex) {
        String allGroups = ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new ListPermissionGroupsCommand.Builder().build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
        return RegexHelper.getRegexMatch(allGroups, regex);
    }

    public String getPermissionsGroup(GroupPermissionsKey key, String group) {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new ListPermissionsCommand.Builder()
                        .setKey(key)
                        .setGroup(group)
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }

    public String getPermissionsGroup(String key, String group) {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new ListPermissionsCommand.Builder()
                        .setKey(GroupPermissionsKey.NEW_KEY.setKey(key))
                        .setGroup(group)
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }

    public String getPermissionsInAllGroups(GroupPermissionsKey key) {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new ListPermissionsCommand.Builder()
                        .setKey(key)
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }

    public String getPermissionsInAllGroups(String key) {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new ListPermissionsCommand.Builder()
                        .setKey(GroupPermissionsKey.NEW_KEY.setKey(key))
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }

    public String getFilesOrDirectories(LsKey key, String path) {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new LsCommand.Builder()
                        .setKey(key)
                        .setPath(path)
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }

    public String getAllFilesOrDirectories(LsKey key) {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new LsCommand.Builder()
                        .setKey(key)
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }

    public String removeFilesOrDirectories(RmKey key, String path) {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new RmCommand.Builder()
                        .setKey(key)
                        .setPath(path)
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }

    public String removeFilesOrDirectories(String key, String path) {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new RmCommand.Builder()
                        .setKey(RmKey.NEW_KEY.setKey(key))
                        .setPath(path)
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }

    public void changeDirectory(String path) {
        ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new CdCommand.Builder()
                        .setPath(path)
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }

    public String getTestPackages(boolean shouldShowApk) {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new ListInstrumentationCommand.Builder()
                        .setShouldShowApk(shouldShowApk)
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }

    public String getTestPackages(String packageName, boolean shouldShowApk) {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new ListInstrumentationCommand.Builder()
                        .setPackageName(packageName)
                        .setShouldShowApk(shouldShowApk)
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }

    public String getFeatures() {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new ListFeaturesCommand.Builder().build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }

    public String getLibraries() {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new ListLibrariesCommand.Builder().build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }

    public String getUsers() {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new ListUsersCommand.Builder().build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }

    public String getDumpsysInfo(String service) {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new DumpsysCommand.Builder()
                        .setService(service)
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }

    public String getDumpsysInfo(Map<Keychain, String> keyValue) {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new DumpsysCommand.Builder()
                        .setKeyValue(keyValue)
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }

    public String getDumpsysInfo(String key, String value) {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new DumpsysCommand.Builder()
                        .setKeyValue(Collections.singletonMap(DumpsysKey.NEW_KEY.setKey(key), value))
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }

    public String getDumpsysInfo(Map<Keychain, String> keyValue, String service) {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new DumpsysCommand.Builder()
                        .setKeyValue(keyValue)
                        .setService(service)
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }

    public String getDumpsysInfo(String key, String value, String service) {
        return ConsoleCommandExecutor.exec(new ADBCommand.Builder()
                .setCommand(new DumpsysCommand.Builder()
                        .setKeyValue(Collections.singletonMap(DumpsysKey.NEW_KEY.setKey(key), value))
                        .setService(service)
                        .build())
                .setDeviceSerial(serial)
                .setTransportId(transportID)
                .build());
    }
}