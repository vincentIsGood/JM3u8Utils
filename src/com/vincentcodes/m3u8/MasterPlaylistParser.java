package com.vincentcodes.m3u8;

import com.vincentcodes.m3u8.errors.InvalidM3u8FileFormatException;
import com.vincentcodes.m3u8.types.Media;
import com.vincentcodes.m3u8.types.Stream;

public class MasterPlaylistParser {
    public static MasterPlaylist parse(String content){
        content = content.trim();
        if(!content.startsWith("#EXTM3U")){
            throw new InvalidM3u8FileFormatException("First line of every Media Playlist and every Master Playlist must start with #EXTM3U");
        }

        MasterPlaylist master = new MasterPlaylist();
        String[] lines = content.split("\n");
        int i = 0;
        try{
            // ignore first line.
            for(i = 1; i < lines.length; i++){
                String line = lines[i];
                line = line.trim();
                String[] parsedLine = ParsingUtils.parseLineRaw(line);
                switch(parsedLine[0]){
                    case "EXT-X-VERSION":
                        master.version = Integer.parseInt(parsedLine[1]);
                        break;
                    case "EXT-X-MEDIA":
                        master.addMedia(new Media(ParsingUtils.parseAttributeList(parsedLine[1])));
                        break;
                    case "EXT-X-STREAM-INF":
                        master.addStream(new Stream(ParsingUtils.parseAttributeList(parsedLine[1]), lines[++i].trim()));
                        break;
                    case "EXT-X-I-FRAME-STREAM-INF":
                        master.addStream(new Stream(ParsingUtils.parseAttributeList(parsedLine[1]), null));
                        break;
                    default:
                        if(parsedLine[0].startsWith("EXT")){
                            System.err.println("Unsupported tag at line "+ (i+1) +": "+ parsedLine[0]);
                        }
                }
            }
        }catch(RuntimeException e){
            throw new RuntimeException("Cannot parse line " + (i+1), e);
        }
        return master;
    }
}
