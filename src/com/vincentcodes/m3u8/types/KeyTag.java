package com.vincentcodes.m3u8.types;

import java.util.Map;

public class KeyTag {
    private final Map<String, String> attributes;

    public KeyTag(Map<String, String> attributes){
        this.attributes = attributes;
    }

    public String getMethod(){
        return attributes.get("METHOD");
    }

    public String getURI(){
        return attributes.get("URI");
    }

    public String getIV(){
        return attributes.get("IV");
    }

    public String getKeyFormat(){
        return attributes.get("KEYFORMAT");
    }
    
    public String getKeyForMatVersions(){
        return attributes.get("KEYFORMATVERSIONS");
    }
}
