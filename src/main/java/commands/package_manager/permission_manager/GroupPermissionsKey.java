package commands.package_manager.permission_manager;

import commands.Keychain;

public enum GroupPermissionsKey implements Keychain {

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

    @Override
    public String getKey() {
        return key;
    }
}