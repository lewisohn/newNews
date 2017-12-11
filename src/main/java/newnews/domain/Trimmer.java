package newnews.domain;

public final class Trimmer {

    private Trimmer() {
    }

    public static String trim(String string) {
        String temp = string.replaceAll("[^a-zA-Z0-9\\s]", "").replaceAll(" ", "-").toLowerCase();
        return (temp.length() > 50 ? temp.substring(0, 50) : temp);
    }

}
