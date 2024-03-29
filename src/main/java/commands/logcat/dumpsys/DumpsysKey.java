package commands.logcat.dumpsys;

import commands.Keychain;

public enum DumpsysKey implements Keychain {

    NEW_KEY,
    ALL_SYS_SERVICES("-l"),
    SKIP_SERVICES("--skip"),
    TIMEOUT_SEC("-t"),
    TIMEOUT_MS("-T"),
    PID("--pid");

    private String key;

    DumpsysKey(String key) {
        this.key = key;
    }

    DumpsysKey() {
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
