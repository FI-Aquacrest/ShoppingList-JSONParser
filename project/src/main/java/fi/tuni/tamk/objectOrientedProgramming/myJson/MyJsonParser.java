package fi.tuni.tamk.objectOrientedProgramming.myJson;

/**
 * Converts JSON values from a String into Java Objects.
 */
public class MyJsonParser {
    /**
     * Needed to parse through the String.
     *
     * This index is used by all methods in order to effectively parse through the individual Characters
     * within the String.
     */
    static int index = 1;

    /**
     * The received String is saved here.
     */
    static String oneLine = "";

    /**
     * This method is used to initiate a parse.
     *
     * It is recommended that the String to be parsed through is trimmed and in a single line
     * in order to improve parsing efficiency.
     *
     * @param data The received String holding the JSON values.
     * @return A complete Object holding all of the data found.
     */
    public static MyJsonObject readAndParse(String data) {
        index = 1;
        oneLine = data.trim();
        return parseObject("");
    }

    /**
     * This method is used to parse through an Object surrounded by curly brackets {}.
     *
     * @param key The key to be assinged to the Object. This can be empty, in which case
     *            the Object is created without a key.
     *
     * @return A complete Object holding any data found.
     */
    public static MyJsonObject parseObject(String key) {
        MyJsonObject result = null;

        if (key.equals("")) {
            result = new MyJsonObject();
        } else {
            result = new MyJsonObject(key);
        }

        while (index < oneLine.length() && oneLine.charAt(index) != '}') {
            MyJsonElement data = findData();
            index++;
            if (data != null) {
                result.add(data);
            } else {
                return result;
            }
        }

        return result;
    }

    /**
     * Parses through an array surrounded by square brackets [].
     *
     * @param key The key to be assinged to the array. For an array this key is never empty.
     * @return A complete array holding any data found.
     */
    public static MyJsonArray parseArray(String key) {
        MyJsonArray array = new MyJsonArray(key);

        while (index < oneLine.length() && oneLine.charAt(index) != ']') {
            MyJsonElement data = findData();
            index++;
            if (data != null) {
                array.add(data);
            } else {
                return array;
            }
        }

        return array;
    }

    /**
     * Extracts a String surrounded by quotation marks "" from the data.
     *
     * @return Found String.
     */
    public static String parseString() {
        String data = "";

        while (oneLine.charAt(index) != '"') {
            data += oneLine.charAt(index);
            index++;
        }

        return data;
    }

    /**
     * Extracts a number without quotation marks from the data.
     *
     * @return Found int value.
     */
    public static int parseInt() {
        String data = "";

        while ((int) oneLine.charAt(index) >= 48 && (int) oneLine.charAt(index) <= 57) {
            data += oneLine.charAt(index);
            index++;
        }

        return Integer.parseInt(data);
    }

    /**
     * Parses through the data and returns the first JSON object found.
     *
     * Most often this object holds a key and a value, but sometimes an object is returned without a key.
     *
     * @return A Java Object created from the parsed data.
     */
    public static MyJsonElement findData() {
        String key = "";
        boolean keyFound = false;

        while (index < oneLine.length()) {
            if (oneLine.charAt(index) == '"' && !keyFound) {
                index++;
                key = parseString();
                keyFound = true;
            } else if (oneLine.charAt(index) == '"' && keyFound) {
                index++;
                String sValue = parseString();
                return new MyJsonValuePair(key, new MyJsonPrimitive(sValue));
            } else if ((int) oneLine.charAt(index) >= 48 && (int) oneLine.charAt(index) <= 57 && keyFound) {
                return new MyJsonValuePair(key, new MyJsonPrimitive(parseInt()));
            } else if (oneLine.charAt(index) == '[') {
                index++;
                return parseArray(key);
            } else if (oneLine.charAt(index) == '{') {
                index++;
                return parseObject(key);
            } else if (oneLine.charAt(index) == 'n' && keyFound) {
                index += 3;
                return new MyJsonValuePair(key, new MyJsonNull());
            } else if (oneLine.charAt(index) == 't' && keyFound) {
                index += 3;
                return new MyJsonValuePair(key, new MyJsonPrimitive(true));
            } else if (oneLine.charAt(index) == 'f' && keyFound) {
                index += 4;
                return new MyJsonValuePair(key, new MyJsonPrimitive(false));
            }
            index++;
        }
        return null;
    }
}
