package utils;

import commands.Command;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ConsoleCommandExecutor {

    public static String exec(Command command) {
        String input = null;
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(command.getCommandComponents());
            builder.directory(new File(System.getProperty("user.home")));
            Process process = builder.start();
            process.waitFor(2000, TimeUnit.MILLISECONDS);
            input = InputStreamHelper.parseInputStream(process.getInputStream());
            String commandToString = String.join(" ", command.getCommandComponents());
            System.out.println(String.format("Command [%s] is executed. There is answer [%s]", commandToString, input));
            process.destroy();
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
            System.err.println();
        }
        return input;
    }
}