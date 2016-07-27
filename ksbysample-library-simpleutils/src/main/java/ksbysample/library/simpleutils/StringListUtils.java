package ksbysample.library.simpleutils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

public class StringListUtils {

    static public String convertStringList2LengthCsv(List<String> stringList) {
        String result = null;
        if (stringList != null) {
            result = stringList.stream()
                    .map(s -> s == null ? "0" : Integer.toString(s.length()))
                    .collect(Collectors.joining(","));
        }

        return result;
    }

    static public int sumStringLength(List<String> stringList) {
        int result = 0;
        if (stringList != null) {
            result = stringList.stream()
                    .mapToInt(s -> s == null ? 0 : s.length())
                    .sum();
        }

        return result;
    }

    static public Optional<String> maxLengthString(List<String> stringList) {
        Optional<String> result = Optional.empty();
        if (stringList != null) {
            result = stringList.stream()
                    .filter(s -> s != null)
                    .sorted(comparing(String::length, reverseOrder()))
                    .findFirst();
        }

        return result;
    }

    static public Optional<String> minLengthString(List<String> stringList) {
        Optional<String> result = Optional.empty();
        if (stringList != null) {
            result = stringList.stream()
                    .filter(s -> s != null)
                    .sorted(comparing(String::length))
                    .findFirst();
        }

        return result;
    }

}
