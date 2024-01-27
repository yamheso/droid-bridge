package commands.file_manager.ls;

import commands.Keychain;

public enum LsKey implements Keychain {

    ALL,
    INCLUDING_HIDDEN("-a"),
    ONLY_DIRECTORY("-d"),
    RECURSIVELY_LIST("-R");

    private String key;

    LsKey(String key) {
        this.key = key;
    }

    LsKey() {
    }

    @Override
    public String getKey() {
        return key;
    }
}