package ksbysample.library.simpleutils;

import java.util.List;
import java.util.stream.Collectors;

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

}
