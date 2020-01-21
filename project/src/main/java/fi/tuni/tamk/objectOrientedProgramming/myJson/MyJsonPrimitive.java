package fi.tuni.tamk.objectOrientedProgramming.myJson;

/**
 * A JSON primitive represents a String, Number or Boolean value.
 */
public class MyJsonPrimitive extends MyJsonElement {
    private Boolean bool = null;
    private String str = null;
    private Number num = null;

    /**
     * Creates a JSON primitive with a String value.
     * @param value a String value
     */
    public MyJsonPrimitive(String value) {
        this.str = value;
    }

    /**
     * Creates a JSON primitive with a Number value.
     * @param value a Number value
     */
    public MyJsonPrimitive(Number value) {
        this.num = value;
    }

    /**
     * Creates a JSON primitive with a Boolean value.
     * @param value a Boolean value
     */
    public MyJsonPrimitive(Boolean value) {
        this.bool = value;
    }

    /**
     * Returns this primitive's used value in a String form.
     * @return The value being used.
     */
    public String asString() {
        if (bool != null) {
            return bool.toString();
        } else if (str != null) {
            return str.toString();
        } else {
            return num.toString();
        }
    }

    /**
     * Returns the primitive value in a String form.
     * @param writer Current writer in use. Required for correct white space printing.
     * @return String representation of this value
     */
    public String toJson(MyJsonWriter writer) {
        if (bool != null) {
            return bool.toString();
        } else if (str != null) {
            return "\"" + str + "\"";
        } else {
            return num.toString();
        }
    }
}
