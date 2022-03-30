package commands.package_manager.list_manager;

public enum PackagesListKey {

    ALL,
    ASSOCIATED_APK_FILE("-f"),
    ALL_KNOWS("-a"),
    DISABLED("-d"),
    ENABLED("-e"),
    SYSTEM("-s"),
    THIRD_PARTY("-3"),
    WITH_INSTALLER("-i"),
    IGNORED("-l"),
    WITH_UID("-U"),
    INCLUDE_UNINSTALLED("-u"),
    WITH_VERSION_CODE("--show-versioncode");

    private String key;

    PackagesListKey(String key) {
        this.key = key;
    }

    PackagesListKey() {
    }

    public String getKey() {
        return key;
    }
}