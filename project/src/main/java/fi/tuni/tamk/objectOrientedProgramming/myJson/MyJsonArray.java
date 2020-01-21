package fi.tuni.tamk.objectOrientedProgramming.myJson;

import java.util.ArrayList;

/**
 * Represents a JSON array surrounded by square brackets [].
 *
 * Very similar to MyJsonObject, this class holds values appearing within square brackets [] in a JSON file.
 */
public class MyJsonArray extends MyJsonObject {
    /**
     * The values found within the array.
     */
    private ArrayList<MyJsonElement> values = new ArrayList<>();

    /**
     * Creates a new array and assings it a key.
     *
     * @param key The respective key of this array. For an array, this key is never Null.
     */
    public MyJsonArray(String key) {
        super(key);
    }

    /**
     * Adds a value to the array.
     *
     * @param data The value found. In most cases this should be an Object.
     */
    public void add(MyJsonElement data) {
        values.add(data);
    }

    /**
     * Returns this array's values in a String form and surrounds them with square brackets.
     *
     * @param writer The current writer in use. This is needed to know how many white spaces need to be printed.
     * @return The complete String with white spaces and square brackets.
     */
    public String toJson(MyJsonWriter writer) {
        String result = "\"" + getKey() + "\": [";

        if (values.isEmpty()) {
            result += "]";
            return result;
        }

        result += "\n";
        writer.increaseSpaceCount();

        for (MyJsonElement e : values) {
            result += writer.getSpaces() + e.toJson(writer) + ",\n";
        }

        result = result.substring(0, result.length() - 2);
        writer.decreaseSpaceCount();
        result += "\n" + writer.getSpaces() + "]";

        return result;
    }
}
