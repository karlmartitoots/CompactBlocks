package ee.kmtster.compact_blocks;

public class StringUtils {
    public static String snakeCase(String str) {
        if (!str.contains(" "))
            return str.toLowerCase();

        return String.join("_", str.split(" ")).toLowerCase();
    }

    static String capitalize(String str) {
        char[] charArray = str.toCharArray();
        boolean foundSpace = true;

        for (int i = 0; i < charArray.length; i++) {

            // if the array element is a letter
            if (Character.isLetter(charArray[i])) {

                // check space is present before the letter
                if (foundSpace) {

                    // change the letter into uppercase
                    charArray[i] = Character.toUpperCase(charArray[i]);
                    foundSpace = false;
                }
            }

            else {
                // if the new character is not character
                foundSpace = true;
            }
        }

        // convert the char array to the string
        return String.valueOf(charArray);
    }
}
