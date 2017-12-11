package newnews.domain;

/**
 * A utility class for converting entity names to shortnames.
 *
 * @author Oliver
 */
public final class Trimmer {

    private Trimmer() {
    }

    /**
     * Trims a string by first removing all non-alphanumeric characters other
     * than spaces, then replacing spaces with hyphens, converting the string to
     * lower case and trimming it to 50 characters if necessary.
     *
     * @param string The input string.
     * @return The trimmed result.
     */
    public static String trim(String string) {
        String temp = string.replaceAll("[^a-zA-Z0-9\\s]", "").replaceAll(" ", "-").toLowerCase();
        return (temp.length() > 50 ? temp.substring(0, 50) : temp);
    }

}
