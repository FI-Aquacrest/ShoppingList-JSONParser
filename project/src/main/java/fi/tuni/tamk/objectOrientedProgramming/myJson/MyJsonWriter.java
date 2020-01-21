package fi.tuni.tamk.objectOrientedProgramming.myJson;

/**
 * Used in order to track white space usage when writing in JSON format.
 */
public class MyJsonWriter {
    /**
     * How many white spaces need to be written.
     */
    private int spaceCount = 0;

    /**
     * Increase the indent of the JSON format. This can be changed based on preference.
     */
    public void increaseSpaceCount() {
        spaceCount += 4;
    }

    /**
     * Decrease the indent of the JSON format. This can be changed based on preference.
     */
    public void decreaseSpaceCount() {
        spaceCount -= 4;
    }

    /**
     * Returns the current amount of spaces in the indent.
     * @return a String containing white spaces.
     */
    public String getSpaces() {
        String result = "";

        for (int x = 0; x < spaceCount; x++) {
            result += " ";
        }

        return result;
    }
}
