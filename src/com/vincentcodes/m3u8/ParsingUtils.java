package com.vincentcodes.m3u8;

import java.util.HashMap;
import java.util.Map;

public class ParsingUtils {
    /**
     * @return name of the tag (eg. EXT-X-ENDLIST) 
     * and its content (eg. <duration>,[<title>]).
     * If its not a tag, then the original line is
     * returned.
     * <pre>
     * {name|original line, content}
     * </pre>
     */
    public static String[] parseLineRaw(String line){
        String[] result = {null, null};
        if(line.startsWith("#")){
            line = line.substring(1);
            int colonPos;
            if((colonPos = line.indexOf(':')) != -1){
                result[0] = line.substring(0, colonPos);
                result[1] = line.substring(colonPos+1);
            }else{
                result[0] = line;
            }
            return result;
        }
        result[0] = line;
        return result;
    }

    public static String[] parseCommaSeparatedList(String content){
        return content.split(",");
    }

    public static Map<String, String> parseAttributeList(String content){
        Map<String, String> result = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        String currentKey = null;
        boolean quoting = false;
        for(int i = 0; i < content.length(); i++){
            char c = content.charAt(i);
            if(c == '=' && !quoting){
                currentKey = builder.toString();
                builder.setLength(0);
            }else if(c == '"'){
                quoting = !quoting;
            }else if(c == ',' && !quoting){
                result.put(currentKey, builder.toString());
                builder.setLength(0);
            }else
                builder.append(c);
        }
        if(currentKey != null)
            result.put(currentKey, builder.toString());
        return result;
    }
}
