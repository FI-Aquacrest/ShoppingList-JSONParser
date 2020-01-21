package fi.tuni.tamk.objectOrientedProgramming.myJson;

/**
 * This Abstract class holds the method necessary for all element classes.
 */
public abstract class MyJsonElement {
    /**
     * Returns the String output of the current element in a JSON format.
     * @param writer Current writer in use. Required for correct white space printing.
     * @return Current element in a String form.
     */
    public abstract String toJson(MyJsonWriter writer);
}
