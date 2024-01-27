package commands.file_manager.rm;

import commands.Keychain;

public enum RmKey implements Keychain {

    NONE,
    NEW_KEY,
    FORCE("-f"),
    RECURSIVELY("-r"),
    DIRECTORY("-rM"),
    VERBOSE("-v"),
    INTERACTIVE("-i");

    private String key;

    RmKey(String key) {
        this.key = key;
    }

    RmKey() {
    }

    public Keychain setKey(String key) {
        this.key = key;
        return this;
    }

    @Override
    public String getKey() {
        return key;
    }
}
