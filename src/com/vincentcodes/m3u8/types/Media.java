package com.vincentcodes.m3u8.types;

import java.util.Map;

public class Media {
    private final Map<String, String> attributes;

    public Media(Map<String, String> attributes){
        this.attributes = attributes;
    }

    public String getType() {
        return attributes.get("TYPE");
    }

    public String getURI() {
        return attributes.get("URI");
    }

    public String getGroupId() {
        return attributes.get("GROUP-ID");
    }

    public String getLang() {
        return attributes.get("LANGUAGE");
    }

    public String getAssocLang() {
        return attributes.get("ASSOC-LANGUAGE");
    }

    public String getName() {
        return attributes.get("NAME");
    }

    public boolean isDefault() {
        return attributes.get("DEFAULT") != null && attributes.get("DEFAULT").equals("YES");
    }

    public boolean doAutoSelect() {
        return attributes.get("AUTOSELECT") != null && attributes.get("AUTOSELECT").equals("YES");
    }

    public boolean isForced() {
        return attributes.get("FORCED") != null && attributes.get("FORCED").equals("YES");
    }

    public String getInStreamId() {
        return attributes.get("INSTREAM-ID");
    }

    public String getChannels() {
        return attributes.get("CHANNELS");
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public String toString(){
        return "{Media attributes:"+ attributes.toString() +"}";
    }

}
