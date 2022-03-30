package commands.package_manager.permission_manager;

public enum GroupPermissionsKey {

    ALL,
    ORGANIZE_BY_GROUP("-g"),
    INFO("-f"),
    SHORT_SUMMARY("-s"),
    ONLY_DANGEROUS("-d"),
    ONLY_USER_WILL_SEE("-u");

    private String key;

    GroupPermissionsKey(String key) {
        this.key = key;
    }

    GroupPermissionsKey() {
    }

    public String getKey() {
        return key;
    }
}