package commands.logcat.dumpsys;

public enum BatteryProperty {
    IS_AC_POWERED("AC powered: "),
    IS_USB_POWERED("USB powered: "),
    MAX_CURRENT("Max charging current: "),
    CHARGE_COUNTER("Charge counter: "),
    STATUS("status: "),
    HEALTH("health: "),
    LEVEL("level: "),
    VOLTAGE("  voltage: "),
    TEMPERATURE("temperature: ");

    private String prop;

    BatteryProperty(String prop) {
        this.prop = prop;
    }

    public String getBatteryProperty() {
        return prop;
    }
}
