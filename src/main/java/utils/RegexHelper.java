package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {

    public static List<String> getRegexMatch(String text, String regex) {
        List<String> allMatches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            while (matcher.find()) {
                allMatches.add(matcher.group(1));
            }
        } else {
            throw new Error(String.format("The text [%s] does not match the specified regular expression", text));
        }
        return allMatches;
    }
}