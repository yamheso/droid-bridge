import adb.ADBUtils;
import commands.file_manager.ls.LsKey;
import commands.file_manager.rm.RmKey;
import commands.logcat.dumpsys.DumpsysKey;
import commands.package_manager.install_manager.InstallKey;
import commands.package_manager.packages_manager.PackagesListKey;
import commands.package_manager.permission_manager.GroupPermissionsKey;
import commands.system.get_prop.DeviceProperties;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ADBExecutionTests {

    private String deviceId = ADBUtils.getOkStatusDeviceIds().get(0);
    private String desktopPath = System.getProperty("user.home") + "/Desktop/";
    private String packageForUninstall = "com.shazam.android";
    private ADBUtils adb = new ADBUtils(deviceId);
    private String fileDocx = "some_doc.docx";

    private Map<String, ADBUtils> initAdbForDevices() {
        return ADBUtils.getOkStatusDeviceIds().stream()
                .collect(Collectors.toMap(
                        id -> id,
                        ADBUtils::new
                ));
    }

    @Test
    public void checkKillAndStartServerCommandsTest() {
        System.out.println(ADBUtils.getOkStatusDeviceIds());
        adb = initAdbForDevices().get("0B301JECB04590");
        adb.killServer();
        adb.startServer();
    }

    @Test
    public void checkScreencapAndPullCommandsTest() {
        String screenPath = "/storage/emulated/0/Pictures/Screenshots/test_screen.png";
        checkPulledScreenshot(screenPath);
        screenPath = String.format("/storage/emulated/0/Pictures/Screenshots/test_screen%d.png", 1);
        adb = new ADBUtils(1);
        checkPulledScreenshot(screenPath);
        screenPath = String.format("/storage/emulated/0/Pictures/Screenshots/test_screen%d.png", 2);
        adb = new ADBUtils();
        checkPulledScreenshot(screenPath);
    }

    @Test
    public String checkPushAndLsCommandsTest() {
        String fromPath = desktopPath + fileDocx;
        String path = "/storage/emulated/0/Documents/";
        String toPath = path + fileDocx;
        assertTrue(adb.pushFile(fromPath, toPath).contains(fromPath + ": 1 file pushed"));
        assertTrue(adb.getFilesOrDirectories(LsKey.ALL, path).contains(fileDocx));
        return toPath;
    }

    @Test
    public void checkGetPropCommandTest() {
        adb = new ADBUtils(1);
        String propertyFromADB = adb.getDeviceProperty(new DeviceProperties(adb, "ro.build.date"));
        String property2FromADB = adb.getDeviceProperty(new DeviceProperties().setBuildDate());
        assertFalse(propertyFromADB.isEmpty());
        assertEquals(propertyFromADB, property2FromADB);
        ADBUtils adb2 = new ADBUtils(2);
        String propertyFromADB2 = adb2.getDeviceProperty(new DeviceProperties(adb2, "ro.build.date"));
        assertFalse(propertyFromADB2.isEmpty());
        assertNotEquals(propertyFromADB, propertyFromADB2);
    }

    @Test
    public void checkPackageManagerCommandsTest() {
        List<String> allPackages = adb.getPackagesList(PackagesListKey.ALL);
        List<String> thirdPartyPackages = adb.getPackagesList(PackagesListKey.THIRD_PARTY);
        assertFalse(allPackages.isEmpty() && thirdPartyPackages.isEmpty());
        thirdPartyPackages.forEach(pack -> assertTrue(allPackages.contains(pack)));
        List<String> someThirdPartyPackage = adb.getPackagesList(adb.getPackagesList(PackagesListKey.THIRD_PARTY).get(0));
        someThirdPartyPackage.forEach(pack -> assertTrue(allPackages.contains(pack)));
    }

    @Test
    public void uninstallPackageCommandTest() {
        String commandAnswer = adb.uninstallPackage(packageForUninstall, false);
        assertEquals("Success", commandAnswer);
    }

    @Test
    public void checkPackagePathCommandTest() {
        List<String> allPackages = adb.getPackagesList(PackagesListKey.ALL);
        int allPackagesSize = allPackages.size();
        String randomPackage = allPackages.get(new Random().nextInt(allPackagesSize - 1));
        assertTrue(adb.getPackagePath(randomPackage).contains(".apk"));
    }

    @Test
    public void checkInstallPackageCommandTest() {
        String commandAnswer = adb.installPackage(InstallKey.ALLOW_VERSION_DOWNGRADE, desktopPath + "app.apk");
        assertTrue(commandAnswer.contains("Success"));
    }

    @Test
    public void checkPermissionsCommandTest() {
        List<String> permissionGroupsList = adb.getPermissionGroups();
        int indexLocationGroup = permissionGroupsList.indexOf("android.permission-group.LOCATION");
        String infoPermissionLocationGroup = adb.getPermissionsGroup(GroupPermissionsKey.INFO, permissionGroupsList.get(indexLocationGroup));
        assertTrue(infoPermissionLocationGroup.contains("Access your car's speed"));
        String allLocationPermissions = adb.getPermissionsGroup(GroupPermissionsKey.ALL, permissionGroupsList.get(indexLocationGroup));
        assertTrue(allLocationPermissions.contains("com.google.android.gms.permission.CAR_SPEED"));
    }

    @Test
    public void checkListInstrumentationCommandTest() {
        String expectedPackage = "io.appium.uiautomator2.server";
        String firstAnswer = adb.getTestPackages(true);
        assertTrue(firstAnswer.contains(".apk=" + expectedPackage + ".test"));
        String secondAnswer = adb.getTestPackages(expectedPackage, false);
        assertTrue(!secondAnswer.contains(".apk=") && secondAnswer.contains(expectedPackage + ".test"));
    }

    @Test
    public void checkDifferentListCommandsTest() {
        String expectedFeature = "android.software.managed_users";
        String answer = adb.getFeatures();
        assertTrue(answer.contains(expectedFeature));
        String expectedLibrary = "com.qualcomm.uimremoteserverlibrary";
        answer = adb.getLibraries();
        assertTrue(answer.contains(expectedLibrary));
        String expectedUser = "Owner:c13";
        answer = adb.getUsers();
        assertTrue(answer.contains(expectedUser));
    }

    @Test
    public void checkDumpsysCommandTest() {
        String pid = "56565";
        String errorMessage = "Can't find service: ".concat(pid);
        String answer = adb.getDumpsysInfo(Collections.singletonMap(DumpsysKey.PID, pid));
        assertEquals(errorMessage, answer);
    }

    @Test
    public void checkRmCommandTest() {
        String path = checkPushAndLsCommandsTest();
        String key = "-f";
        adb.removeFilesOrDirectories(key, path);
        assertFalse(adb.getFilesOrDirectories(LsKey.ALL, path).contains(fileDocx));
        path = checkPushAndLsCommandsTest();
        adb.removeFilesOrDirectories(RmKey.FORCE, path);
        assertFalse(adb.getFilesOrDirectories(LsKey.ALL, path).contains(fileDocx));
    }

    private void checkPulledScreenshot(String screenPath) {
        adb.takeScreenshot(screenPath);
        assertTrue(adb.pullFile(screenPath, desktopPath).contains(screenPath + ": 1 file pulled"));
    }
}