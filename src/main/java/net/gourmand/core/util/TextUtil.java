package net.gourmand.core.util;

import java.util.Locale;
import java.util.stream.Stream;

public class TextUtil {

    // for example the input "sawblade_holly" would turn into "Sawblade Berry
    public static String getName(String string){
        //TODO: this could be made more sane?
        final String[] new_string = {""};
        final int[] count = {0};

        Stream.of(string.toLowerCase(Locale.ROOT).split("_")).forEach(str -> {
            if (count[0] == 0){
                new_string[0] = str.substring(0, 1).toUpperCase() + str.substring(1);
            } else {
                new_string[0] = new_string[0] + " " + str.substring(0, 1).toUpperCase() + str.substring(1);
            }
            count[0] = count[0] + 1;
        });

        return new_string[0];
    }
}
