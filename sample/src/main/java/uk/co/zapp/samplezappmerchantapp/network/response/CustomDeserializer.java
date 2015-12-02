package uk.co.zapp.samplezappmerchantapp.network.response;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * The base custom deserializer from json to java objects. Contains common helper methods.
 */
public abstract class CustomDeserializer {

    /**
     * A helper method which returns null in case such element name doesn't exist in the given json object.
     *
     * @param jsonObject the json object which contains the element with such name.
     * @param name       name of the member that is being requested.
     * @return the string value of member matching the name. Null if no such member exists.
     */
    protected String getStringElement(final JsonObject jsonObject, final String name) {
        final JsonElement element = jsonObject.get(name);
        String value = null;
        if (element != null) {
            value = element.getAsString();
        }
        return value;
    }

    /**
     * A helper method which returns false in case no boolean element has been found for the given name.
     *
     * @param jsonObject the json object which contains the element with such name.
     * @param name       name of the member that is being requested.
     * @return the boolean value of member matching the name. False if no such member exists.
     */
    protected boolean getBooleanElement(final JsonObject jsonObject, final String name) {
        final JsonElement element = jsonObject.get(name);
        boolean value = false;
        if (element != null) {
            value = element.getAsBoolean();
        }
        return value;
    }

    /**
     * A helper method which returns zero in case no int element has been found with the given name.
     *
     * @param jsonObject the json object which contains the element with such name.
     * @param name       name of the member that is being requested.
     * @return the int value of member matching the name. Zero if no such member exists.
     */
    protected int getIntElement(final JsonObject jsonObject, final String name) {
        final JsonElement element = jsonObject.get(name);
        int value = 0;
        if (element != null) {
            value = element.getAsInt();
        }
        return value;
    }
}
