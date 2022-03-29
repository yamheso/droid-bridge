package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {

    public static List<String> getRegexMatch(String text, String regex) {
        if (text.isEmpty()) throw new Error("Text cannot be empty");
        List<String> allMatches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            while (matcher.find()) {
                for (int i = 1; i <= matcher.groupCount(); i++) {
                    if (Objects.isNull(matcher.group(i))) continue;
                    allMatches.add(matcher.group(i));
                }
            }
            return allMatches;
        }
        throw new Error(String.format("The text [%s] does not match the specified regular expression", text));
    }
}