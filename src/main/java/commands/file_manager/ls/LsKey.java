package commands.file_manager.ls;

public enum LsKey {

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

    public String getKey() {
        return key;
    }
}