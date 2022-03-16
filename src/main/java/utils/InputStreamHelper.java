package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputStreamHelper {

    public static String parseInputStream(InputStream input) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line;
            StringBuilder resultBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                resultBuilder.append(line);
            }

            return resultBuilder.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
