import adb.ADBUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

 public class ADBExecutionTests {

    private String deviceId = "0B301JECB04590";
    private String desktopPath = System.getProperty("user.home") + "/Desktop";
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

    private void checkPulledScreenshot(String screenPath) {
        adb.takeScreenshot(screenPath);
        assertTrue(adb.pullFile(screenPath, desktopPath).contains(screenPath + ": 1 file pulled"));
    }
}
