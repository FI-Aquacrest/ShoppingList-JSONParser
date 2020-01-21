package fi.tuni.tamk.objectOrientedProgramming.myJson;

import java.util.ArrayList;

/**
 * Represents a JSON object surrounded by curly brackets {}.
 */
public class MyJsonObject extends MyJsonElement {
    /**
     * Key of this object.
     */
    private String key = "";

    /**
     * List containing all the elements found within the JSON object.
     */
    private ArrayList<MyJsonElement> array = new ArrayList<>();

    /**
     * Creates a new JSON object and assigns a key to it.
     * @param key the key to assign to this object. Can be empty.
     */
    public MyJsonObject(String key) {
        this.key = key;
    }

    /**
     * Creates a JSON object without a key.
     */
    public MyJsonObject() {}

    /**
     * Adds the received element on to the list.
     * @param element the Element to be added in to the list.
     */
    public void add(MyJsonElement element) {
        array.add(element);
    }

    /**
     * Returns the key of this object.
     * @return the key of this object.
     */
    public String getKey() {
        return this.key;
    }

    /**
     * @return The values stored in this object as a List.
     */
    public ArrayList<MyJsonElement> getValues() {
        return this.array;
    }

    /**
     * Converts the object's contents to a String and surrounds them with curly brackets.
     * @param writer Current writer in use. Required for correct white space printing.
     * @return String representation of the object.
     */
    public String toJson(MyJsonWriter writer) {
        String result = "";

        if (key.equals("")) {
            result += "{\n";
        } else {
            result += "\"" + key + "\": {\n";
        }

        writer.increaseSpaceCount();

        for (MyJsonElement x : array) {
            result += writer.getSpaces() + x.toJson(writer) + ",\n";
        }

        // Remove comma and newline before returning.
        result = result.substring(0, result.length() - 2);
        writer.decreaseSpaceCount();
        result += "\n" + writer.getSpaces() + "}";
        return result;
    }
}
