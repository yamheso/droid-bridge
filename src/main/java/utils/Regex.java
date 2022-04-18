package utils;

public class Regex {

    public static final String PERMISSION_GROUP = "group:(.+?)permission\\s|group:(.+)";
    public static final String LIST_PACKAGE = ":(.+?)package|:(.+)";
    public static final String DEVICE_PROPERTIES = "\\]\\[(.+?)\\]\\:";
    public static final String DEVICES = "List of devices attached(.+?)\\tdevice|(.+?)\\tdevice";
}