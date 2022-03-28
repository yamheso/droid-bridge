package commands.system.get_prop;

import adb.ADBUtils;

import java.util.List;

public class DeviceProperties {

    private String property;
    private final String ALL_PROPERTY_NAMES = "-T";
    private final String DEVICE_PROVISIONED = "DEVICE_PROVISIONED";
    private final String SYS_LOCALE = "persist.sys.locale";
    private final String PRODUCT_MANUFACTURER = "ro.product.manufacturer";
    private final String PRODUCT_MODEL = "ro.product.model";
    private final String PRODUCT_CPU_ABI = "ro.product.cpu.abi";
    private final String PRODUCT_CPU_ABI_2 = "ro.product.cpu.abi2";
    private final String PRODUCT_SDK_VERSION = "ro.product.build.version.sdk";
    private final String PRODUCT_LOCALE_VERSION = "ro.product.locale.language";
    private final String PRODUCT_LOCALE_REGION = "ro.product.locale.region";
    private final String PRODUCT_BOARD = "ro.product.board";
    private final String BUILD_PRODUCT = "ro.build.product";
    private final String BUILD_VERSION_RELEASE = "ro.build.version.release";
    private final String BUILD_VERSION_CODE_NAME = "ro.build.version.codename";
    private final String BUILD_DESCRIPTION = "ro.build.description";
    private final String BUILD_FINGER_PRINT = "ro.build.fingerprint";
    private final String BUILD_DISPLAY_ID = "ro.build.display.id";
    private final String BUILD_VERSION_INCREMENTAL = "ro.build.version.incremental";
    private final String BUILD_DATE = "ro.build.date";
    private final String BUILD_TYPE = "ro.build.type";
    private final String BUILD_USER = "ro.build.user";
    private final String BOARD_PLATFORM = "ro.board.platform";
    private final String GSM_FLEX_VERSION = "ro.gsm.flexversion";
    private final String WI_FI_CHANNELS = "ro.wifi.channels";
    private final String BASE_BAND_IMEI = "gsm.baseband.imei";
    private final String BT_NAME = "net.bt.name";
    private String regex = "\\]\\[(.+?)\\]\\:";

    public DeviceProperties() {
    }

    public DeviceProperties(ADBUtils adb, String property) {
        initProperty(adb, property, this.regex);
    }

    public DeviceProperties(ADBUtils adb, String property, String regex) {
        initProperty(adb, property, regex);
    }

    private void initProperty(ADBUtils adb, String property, String regex) {
        List<String> allCommandList = adb.getDevicePropertyNamesList(regex);
        int propertyIndex = allCommandList.indexOf(property);
        if (propertyIndex == -1)
            throw new Error(String.format("The [%s] property was not found in the list", property));
        this.property = allCommandList.get(propertyIndex);
    }

    public String getPropertyName() {
        return property;
    }

    public DeviceProperties setAllProperties() {
        this.property = ALL_PROPERTY_NAMES;
        return this;
    }

    public DeviceProperties setDeviceProvisioned() {
        this.property = DEVICE_PROVISIONED;
        return this;
    }

    public DeviceProperties setSystemLocale() {
        this.property = SYS_LOCALE;
        return this;
    }

    public DeviceProperties setProductManufacturer() {
        this.property = PRODUCT_MANUFACTURER;
        return this;
    }

    public DeviceProperties setProductModel() {
        this.property = PRODUCT_MODEL;
        return this;
    }

    public DeviceProperties setProductCpuAbi() {
        this.property = PRODUCT_CPU_ABI;
        return this;
    }

    public DeviceProperties setProductCpuAbi2() {
        this.property = PRODUCT_CPU_ABI_2;
        return this;
    }

    public DeviceProperties setProductSdkVersion() {
        this.property = PRODUCT_SDK_VERSION;
        return this;
    }

    public DeviceProperties setProductLocalVersion() {
        this.property = PRODUCT_LOCALE_VERSION;
        return this;
    }

    public DeviceProperties setProductLocalRegion() {
        this.property = PRODUCT_LOCALE_REGION;
        return this;
    }

    public DeviceProperties setProductBoard() {
        this.property = PRODUCT_BOARD;
        return this;
    }

    public DeviceProperties setBuildProduct() {
        this.property = BUILD_PRODUCT;
        return this;
    }

    public DeviceProperties setBuildVersionRelease() {
        this.property = BUILD_VERSION_RELEASE;
        return this;
    }

    public DeviceProperties setBuildVersionCodeName() {
        this.property = BUILD_VERSION_CODE_NAME;
        return this;
    }

    public DeviceProperties setBuildDescription() {
        this.property = BUILD_DESCRIPTION;
        return this;
    }

    public DeviceProperties setFingerPrint() {
        this.property = BUILD_FINGER_PRINT;
        return this;
    }

    public DeviceProperties setBuildDisplayId() {
        this.property = BUILD_DISPLAY_ID;
        return this;
    }

    public DeviceProperties setBuildVersionIncremental() {
        this.property = BUILD_VERSION_INCREMENTAL;
        return this;
    }

    public DeviceProperties setBuildDate() {
        this.property = BUILD_DATE;
        return this;
    }

    public DeviceProperties setBuildType() {
        this.property = BUILD_TYPE;
        return this;
    }

    public DeviceProperties setBuildUser() {
        this.property = BUILD_USER;
        return this;
    }

    public DeviceProperties setBoardPlatform() {
        this.property = BOARD_PLATFORM;
        return this;
    }

    public DeviceProperties setGsmFlexVersion() {
        this.property = GSM_FLEX_VERSION;
        return this;
    }

    public DeviceProperties setWiFiChannels() {
        this.property = WI_FI_CHANNELS;
        return this;
    }

    public DeviceProperties setBaseBandImei() {
        this.property = BASE_BAND_IMEI;
        return this;
    }

    public DeviceProperties setBtName() {
        this.property = BT_NAME;
        return this;
    }

    public DeviceProperties setRegex(String regex) {
        this.regex = regex;
        return this;
    }
}