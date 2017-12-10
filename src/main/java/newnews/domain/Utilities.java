package newnews.domain;

public final class Utilities {

    private Utilities() {
    }

    public static String trim(String string) {
        return string.replaceAll("[^a-zA-Z0-9\\s]", "").replaceAll(" ", "-")
                .toLowerCase().substring(0, Math.min(50, string.length()));
    }

}
