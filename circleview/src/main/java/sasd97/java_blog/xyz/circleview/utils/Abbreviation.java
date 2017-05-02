package sasd97.java_blog.xyz.circleview.utils;

/**
 * Created by alexander on 30/04/2017.
 */

public class Abbreviation {

    private static final String SPACE_SIGN = " ";

    private Abbreviation() {
    }

    public static String make(String text) {
        String temp = text.trim().toUpperCase();

        if (temp.contains(SPACE_SIGN)) {
            String[] slice = temp.split(SPACE_SIGN);
            StringBuilder builder = new StringBuilder();

            builder
                    .append(slice[0].charAt(0))
                    .append(slice[1].charAt(0));

            return builder.toString();
        }

        return String.valueOf(temp.charAt(0));
    }
}
