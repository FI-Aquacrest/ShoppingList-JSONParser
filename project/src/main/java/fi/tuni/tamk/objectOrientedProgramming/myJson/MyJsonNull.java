package fi.tuni.tamk.objectOrientedProgramming.myJson;

/**
 * An object of this class is simply a null value for easier printing.
 */
public class MyJsonNull extends MyJsonElement {
    /**
     * Returns a Null in a String object.
     *
     * @param writer Current writer in use. Required for correct white space printing.
     * @return Null in a String.
     */
    public String toJson(MyJsonWriter writer) {
        String result = null;
        return null;
    }
}
