package fi.tuni.tamk.objectOrientedProgramming.myJson;

/**
 * Prints out a value without a key.
 *
 * Whether not not this is required is not yet clear, as examples of JSON code vary.
 */
@Deprecated
public class MyJsonValue extends MyJsonElement {
    private String value;

    /**
     * Assings the received String as the value.
     * @param value received value.
     */
    public MyJsonValue(String value) {
        this.value = value;
    }

    /**
     * Converts the value into a String form in JSON format.
     * @param writer Current writer in use. Required for correct white space printing.
     * @return a String representation of this value.
     */
    public String toJson(MyJsonWriter writer) {
        return "\"" + this.value + "\"";
    }
}
