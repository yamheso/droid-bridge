package commands.package_manager.install_manager;

public enum InstallKey {

    WITH_FORWARD_LOCK("-l"),
    REINSTALL_WITH_KEEPING_DATA("-r"),
    TEST_APK("-t"),
    TO_SDCARD("-s"),
    SPECIFY_PACKAGE_NAME("-i"),
    TO_INTERNAL_MEMORY("-f"),
    ALLOW_VERSION_DOWNGRADE("-d"),
    WITH_ALL_PERMISSIONS("-g"),
    INSTANT("--instant"),
    FAST_DEPLOY("--fastdeploy"),
    NO_STREAMING("--no-streaming");

    private String key;

    InstallKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}