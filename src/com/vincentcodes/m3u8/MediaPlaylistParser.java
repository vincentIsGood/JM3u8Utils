package com.vincentcodes.m3u8;

import com.vincentcodes.m3u8.errors.InvalidM3u8FileFormatException;
import com.vincentcodes.m3u8.types.KeyTag;

public class MediaPlaylistParser {
    public static MediaPlaylist parse(String content){
        content = content.trim();
        if(!content.startsWith("#EXTM3U")){
            throw new InvalidM3u8FileFormatException("First line of every Media Playlist and every Master Playlist must start with #EXTM3U");
        }

        MediaPlaylist media = new MediaPlaylist();
        String[] lines = content.split("\n");
        int i = 0;
        try{
            // ignore first line.
            boolean canGetSegment = false;
            float segDuration = 0;
            for(i = 1; i < lines.length; i++){
                String line = lines[i];
                line = line.trim();

                if(!line.startsWith("#") && canGetSegment){
                    media.addSegment(line, segDuration);
                    canGetSegment = false;
                    continue;
                }

                String[] parsedLine = ParsingUtils.parseLineRaw(line);
                switch(parsedLine[0]){
                    case "EXT-X-VERSION":
                        media.version = Integer.parseInt(parsedLine[1]);
                        break;
                    case "EXT-X-TARGETDURATION":
                        media.targetDuration = Integer.parseInt(parsedLine[1]);
                        break;
                    case "EXT-X-MEDIA-SEQUENCE":
                        media.sequence = Integer.parseInt(parsedLine[1]);
                        break;
                    case "EXT-X-DISCONTINUITY-SEQUENCE":
                        media.disSequence = Integer.parseInt(parsedLine[1]);
                        break;
                    case "EXT-X-ENDLIST":
                        media.doesPlaylistEnds = true;
                        break;
                    case "EXT-X-PLAYLIST-TYPE":
                        media.type = parsedLine[1];
                        break;
                    case "EXT-X-I-FRAMES-ONLY":
                        media.iframesOnly = true;
                        break;
                    case "EXT-X-KEY":
                        media.addKey(new KeyTag(ParsingUtils.parseAttributeList(parsedLine[1])));
                        break;
                    case "EXTINF":
                        canGetSegment = true;
                        segDuration = Float.parseFloat(ParsingUtils.parseCommaSeparatedList(parsedLine[1])[0]);
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
        return media;
    }
}
