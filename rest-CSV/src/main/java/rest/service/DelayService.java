package rest.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class DelayService {

    public static long getTimeToMs(String value) {
        var regex = "[a-z]+";
        if (isNumeric(value)) {
            return Long.parseLong(value);
        }


        var timeMap = Map.of("h", 3600000L, "m", 60000L, "s", 1000L, "ms", 1L);
        var timeKeyPattern = Pattern.compile(regex);
        var timeKeyMatcher = timeKeyPattern.matcher(value);
        var timeValue = value.split(regex);

        var i = 0;
        var result = 0L;
        while(timeKeyMatcher.find()){
            result += timeMap.get(timeKeyMatcher.group()) * Long.parseLong(timeValue[i]);
            i++;
        }

        return result;
    }

    public static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
}
