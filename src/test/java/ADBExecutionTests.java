import adb.ADBUtils;
import commands.package_manager.install_manager.InstallKey;
import commands.package_manager.list_manager.PackagesListKey;
import commands.package_manager.permission_manager.GroupPermissionsKey;
import commands.system.get_prop.DeviceProperties;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ADBExecutionTests {

    private String deviceId = "0B301JECB04590";
    private String desktopPath = System.getProperty("user.home") + "/Desktop";
    private String packageForUninstall = "ru.rian.inosmi";
    private ADBUtils adb = new ADBUtils(deviceId);

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
    public void installPackageCommandTest() {
        String commandAnswer = adb.installPackage(InstallKey.ALLOW_VERSION_DOWNGRADE, desktopPath + "/app.apk");
        assertTrue(commandAnswer.contains("Success"));
    }

    @Test
    public void permissionsCommandTest() {
        List<String> permissionGroupsList = adb.getPermissionGroups();
        int indexLocationGroup = permissionGroupsList.indexOf("android.permission-group.LOCATION");
        String infoPermissionLocationGroup = adb.getPermissionsGroup(GroupPermissionsKey.INFO, permissionGroupsList.get(indexLocationGroup));
        assertTrue(infoPermissionLocationGroup.contains("Access your car's speed"));
        String allLocationPermissions = adb.getPermissionsGroup(GroupPermissionsKey.ALL, permissionGroupsList.get(indexLocationGroup));
        assertTrue(allLocationPermissions.contains("com.google.android.gms.permission.CAR_SPEED"));
    }

    private void checkPulledScreenshot(String screenPath) {
        adb.takeScreenshot(screenPath);
        assertTrue(adb.pullFile(screenPath, desktopPath).contains(screenPath + ": 1 file pulled"));
    }
}