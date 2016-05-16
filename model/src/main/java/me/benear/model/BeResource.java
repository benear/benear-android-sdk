package me.benear.model;

import com.google.gson.JsonObject;
/**
 * Created by mreverter on 14/5/16.
 */
public class BeResource {
    private String type;
    private JsonObject metadata;

    public String getType() {
        return type;
    }

    public JsonObject getMetadata() {
        return metadata;
    }
}
