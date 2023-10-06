package commands.logcat.dumpsys;

public enum DumpsysKey {
    ALL_SYS_SERVICES("-l"),
    SKIP_SERVICES("--skip"),
    TIMEOUT_SEC("-t"),
    TIMEOUT_MS("-T"),
    PID("--pid:");


    private String key;

    DumpsysKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
