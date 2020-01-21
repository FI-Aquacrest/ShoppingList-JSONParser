package fi.tuni.tamk.objectOrientedProgramming.myJson;

/**
 * A JSON value pair holds a key and a value assigned to that key.
 */
public class MyJsonValuePair extends MyJsonElement {
    private String key = null;
    private MyJsonElement value = null;

    /**
     * Creates a new value pair with a key and the received element.
     * @param key to assign to this pair. Is never empty.
     * @param value the element to assing as the value for this pair.
     */
    public MyJsonValuePair(String key, MyJsonElement value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the key of this value pair.
     * @return key The key of this value pair.
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Returns the value element of this pair.
     * @return value The value of this pair.
     */
    public MyJsonElement getValue() {
        return this.value;
    }

    /**
     * Converts this Object into a String in JSON format.
     * @param writer Current writer in use. Required for correct white space printing.
     * @return a String representation of this value pair.
     */
    public String toJson(MyJsonWriter writer) {
        if (key == null) {
            return this.value.toJson(writer);
        }

        return "\"" + this.key + "\"" + ": " + this.value.toJson(writer);
    }
}