package com.example.report;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Report {
    public static String getReport(String text) {
        var punctuations = 0L;
        var whiteSpaces = 0L;
        var words = new ArrayList<String>();
        var wordsFreq = new LinkedHashMap<String, Long>();

        var patternWord = Pattern.compile("[A-Za-zА-Яа-яёЁ]+");
        var matcherWord = patternWord.matcher(text);

        var patternPunctuations = Pattern.compile("[^A-Za-zА-Яа-яёЁ\\s]");
        var matcherPunctuations = patternPunctuations.matcher(text);

        var patternWhiteSpaces = Pattern.compile("\\s|\\n|\\r|\\t");
        var matcherWhiteSpaces = patternWhiteSpaces.matcher(text);


        while (matcherWord.find()) {
            words.add(matcherWord.group());
        }
        while (matcherPunctuations.find()) {
            punctuations++;
        }
        while (matcherWhiteSpaces.find()) {
            whiteSpaces++;
        }

        var maxLengthString = words.stream().mapToInt(String::length).max().orElse(-1);
        var maxLengthCurrent = 0L; // counter for remove less length string
        for (int i = 0; i < maxLengthString; i++) {
            for (var word : words) {
                if (word.length() > maxLengthCurrent) {
                    for (int j = 0; j + i < word.length(); j++) {
                        var subString = word.substring(j, j + i + 1).toLowerCase();
                        wordsFreq.computeIfPresent(subString, (key, count) -> ++count);
                        wordsFreq.putIfAbsent(subString, 1L);
                    }
                }
            }
            maxLengthCurrent++;
        }
        var wordFreqString = wordsFreq.keySet().stream()
                .map(key -> key + " - " + wordsFreq.get(key))
                .collect(Collectors.joining("\n"));

        return """
                "    Анализ текста
                Всего в тексте слов – %s,
                Знаков препинания и прочих символов – %s,
                Пробелов и прочих отступов – %s.
                Встречаемость сочетаний букв:
                %s"              
                """.formatted(words.size(), punctuations, whiteSpaces, wordFreqString);

    }
}
