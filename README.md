## What is droid-bridge?
Droid-Bridge is a library (software API) for interacting with the ADB (Android Debug Bridge) utility from Java applications. The library provides a convenient interface for executing ADB commands, allowing you to control Android devices in your application.

## How to use it? 
Suppose we have the following devices connected
`
~ user$ adb devices
List of devices attached
0C091JECB06816 offline
0C141JECB02566 device
16171JECB04684 device
28231JEGR35194 unauthorized
28251JEGR15058 device
29231JEGR06052 device
`
```
// Create a list of device serial numbers with the 'device' status
List<String> devices = ADBUtils.getOkStatusDeviceIds();
System.out.println(devices);
//[0C141JECB02566, 16171JECB04684, 28251JEGR15058, 29231JEGR06052]

// Connect devices to adb (for example, the first and last devices)
String firstDevice = devices.get(0);
String lastDevice = devices.get(devices.size() - 1);
ADBUtils firstDeviceAdb = new ADBUtils(firstDevice);
ADBUtils lastDeviceAdb = new ADBUtils(lastDevice);

// Or you can use the transportId
ADBUtils deviceAdb = new ADBUtils(1);

// Execute commands through adb
// Create a list of third-party apps on the first device
List<String> thirdPartyPackagesOnFirstDevice = firstDeviceAdb.getPackagesList(PackagesListKey.THIRD_PARTY);

// Take a screenshot from the device and send it to your PC
lastDeviceAdb.takeScreenshot(screenPathOnDevice);
lastDeviceAdb.pullFile(screenPathOnDevice, pathOnPc);

// Install an application on the device with transportId 1
deviceAdb.installPackage(InstallKey.ALLOW_VERSION_DOWNGRADE, pathToApkOnPc);
```