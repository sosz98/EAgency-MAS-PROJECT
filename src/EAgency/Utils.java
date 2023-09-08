package EAgency;

/**
 * The type Utils.
 */
public class Utils {
    /**
     * Check if string is not null and not blank.
     *
     * @param text the text
     */
    public static void checkIfStringIsNotNullAndNotBlank(String text) {
        if (text == null || text.isBlank()) throw new IllegalArgumentException("Text is null or blank");
    }

}
