package utils;

import adb.ADBUtils;
import com.google.common.base.Splitter;
import commands.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ConsoleCommandExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(ConsoleCommandExecutor.class);

    public static String exec(Command command) {
        String input;
        try {
            String commandToString = String.join(" ", command.getCommandComponents());
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(command.getCommandComponents());
            builder.directory(new File(System.getProperty("user.home")));
            Process process = builder.start();
            process.waitFor(5000, TimeUnit.MILLISECONDS);
            if (commandToString.equals(AdbDevicesCommandFormatter.COMMAND)) {
                return AdbDevicesCommandFormatter.setDevicesMap(process);
            }
            input = InputStreamHelper.parseInputStream(process.getInputStream());
            LOG.info("Command [{}] is executed. There is answer [{}]", commandToString, input);
            process.destroy();
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
            LOG.error(ex.getMessage());
            throw new RuntimeException();
        }
        return input;
    }

    static class AdbDevicesCommandFormatter {

        private static final String COMMAND = "adb devices";

        public static String setDevicesMap(final Process adbDevicesProcess) {
            try {
                InputStream adbOutput = adbDevicesProcess.getInputStream();
                if (Objects.isNull(ADBUtils.getAllConnectedDevices()) || ADBUtils.getAllConnectedDevices().isEmpty()) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(adbOutput));
                    String line;
                    StringBuilder devicesOutput = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("List of")) continue;
                        devicesOutput.append(line).append("\n");
                    }
                    adbDevicesProcess.waitFor(5000, TimeUnit.MILLISECONDS);
                    String devicesResult = devicesOutput.toString().trim();
                    ADBUtils.setAllConnectedDevices(getConnectedDevicesMap(devicesResult));
                }
                return InputStreamHelper.parseInputStream(adbOutput);
            } catch (Exception ex) {
                LOG.error(ex.getMessage());
                throw new RuntimeException("There is error while creating a map of connected devices!");
            }
        }

        private static Map<String, String> getConnectedDevicesMap(String devicesResult) {
            if (devicesResult.isEmpty()) throw new RuntimeException("No connected devices found!");
            return Splitter.on("\n")
                    .trimResults()
                    .omitEmptyStrings()
                    .withKeyValueSeparator("\t")
                    .split(devicesResult);
        }
    }

}