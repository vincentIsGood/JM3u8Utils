package com.vincentcodes.m3u8;

import com.vincentcodes.m3u8.types.KeyTag;
import com.vincentcodes.m3u8.types.Media;
import com.vincentcodes.m3u8.types.Stream;

public class PlaylistGenerator {
    public static String toM3u8(MasterPlaylist master){
        StringBuilder sb = new StringBuilder();
        writeNewLine(sb.append("#EXTM3U"));
        writeNewLine(sb.append("#EXT-X-VERSION:").append(master.version));
        for(Stream stream : master.streams){
            writeStream(sb, stream);
        }
        return sb.toString();
    }
    
    public static String toM3u8(MediaPlaylist media){
        StringBuilder sb = new StringBuilder();
        writeNewLine(sb.append("#EXTM3U"));
        writeNewLine(sb.append("#EXT-X-VERSION:").append(media.version));
        if(media.type != null) writeNewLine(sb.append("#EXT-X-PLAYLIST-TYPE:").append(media.type));
        if(media.sequence != -1) writeNewLine(sb.append("#EXT-X-MEDIA-SEQUENCE:").append(media.sequence));
        if(media.disSequence != -1) writeNewLine(sb.append("#EXT-X-DISCONTINUITY-SEQUENCE:").append(media.disSequence));
        if(media.targetDuration != -1) writeNewLine(sb.append("#EXT-X-TARGETDURATION:").append((int)media.targetDuration));
        if(media.iframesOnly) writeNewLine(sb.append("#EXT-X-I-FRAMES-ONLY"));

        for(int i = 0; i < media.segments.size(); i++){
            if(media.keys.get(i) != null){
                writeKey(sb, media.keys.get(i));
            }
            writeNewLine(sb.append("#EXTINF:").append(media.segDurations.get(i)).append(","));
            writeNewLine(sb.append(media.segments.get(i)));
        }
        
        if(media.doesPlaylistEnds) writeNewLine(sb.append("#EXT-X-ENDLIST"));
        return sb.toString();
    }

    private static void writeStream(StringBuilder sb, Stream stream){
        if(stream.relatedMedia.size() > 0){
            for(Media media : stream.relatedMedia){
                writeMedia(sb, media);
            }
        }
        if(stream.isIframeStream())
            sb.append("#EXT-X-I-FRAME-STREAM-INF:");
        else sb.append("#EXT-X-STREAM-INF:");
        if(stream.getProgramId() != -1) sb.append("PROGRAM-ID=").append(stream.getProgramId()).append(",");
        if(stream.getBandwidth() != -1) sb.append("BANDWIDTH=").append(stream.getBandwidth()).append(",");
        if(stream.getAvgBandwidth() != -1) sb.append("AVERAGE-BANDWIDTH=").append(stream.getAvgBandwidth()).append(",");
        if(stream.getCodecs() != null) sb.append("CODECS=").append(quoteString(stream.getCodecs())).append(",");
        if(stream.getResolution() != null) sb.append("RESOLUTION=").append(stream.getResolution()).append(",");
        if(stream.getFps() != -1) sb.append("FRAME-RATE=").append(stream.getFps()).append(",");
        if(stream.getAudioId() != null) sb.append("AUDIO=").append(quoteString(stream.getAudioId())).append(",");
        if(stream.getVideoId() != null) sb.append("VIDEO=").append(quoteString(stream.getVideoId())).append(",");
        if(stream.getSubtitlesId() != null) sb.append("SUBTITLES=").append(quoteString(stream.getSubtitlesId())).append(",");
        String closedCaptions = stream.getClosedCaptions();
        if(closedCaptions != null){
            if(!closedCaptions.equals("NONE"))
                closedCaptions = quoteString(closedCaptions);
            sb.append("CLOSED-CAPTIONS=").append(closedCaptions).append(",");
        }
        if(stream.getURI() != null) sb.append("URI=").append(quoteString(stream.getURI())).append(",");
        sb.setLength(sb.length()-1);
        writeNewLine(sb);
        writeNewLine(sb.append(stream.playlist));
    }

    private static void writeMedia(StringBuilder sb, Media media){
        sb.append("#EXT-X-MEDIA:");
        if(media.getType() != null) sb.append("TYPE=").append(media.getType()).append(",");
        if(media.getGroupId() != null) sb.append("GROUP-ID=").append(quoteString(media.getGroupId())).append(",");
        if(media.getLang() != null) sb.append("LANGUAGE=").append(quoteString(media.getLang())).append(",");
        if(media.getAssocLang() != null) sb.append("ASSOC-LANGUAGE=").append(quoteString(media.getAssocLang())).append(",");
        if(media.getName() != null) sb.append("NAME=").append(quoteString(media.getName())).append(",");
        if(media.isDefault()) sb.append("DEFAULT=").append(toYesNo(media.isDefault())).append(",");
        if(media.doAutoSelect()) sb.append("AUTOSELECT=").append(toYesNo(media.doAutoSelect())).append(",");
        if(media.isForced()) sb.append("FORCED=").append(toYesNo(media.isForced())).append(",");
        if(media.getInStreamId() != null) sb.append("INSTREAM-ID=").append(quoteString(media.getInStreamId())).append(",");
        if(media.getChannels() != null) sb.append("CHANNELS=").append(quoteString(media.getChannels())).append(",");
        if(media.getURI() != null) sb.append("URI=").append(quoteString(media.getURI())).append(",");
        sb.setLength(sb.length()-1);
        writeNewLine(sb);
    }

    private static void writeKey(StringBuilder sb, KeyTag key){
        sb.append("#EXT-X-KEY:");
        if(key.getMethod() != null) sb.append("METHOD=").append(key.getMethod()).append(",");
        if(key.getURI() != null) sb.append("URI=").append(quoteString(key.getURI())).append(",");
        if(key.getIV() != null) sb.append("IV=").append(key.getIV()).append(",");
        if(key.getKeyFormat() != null) sb.append("KEYFORMAT=").append(quoteString(key.getKeyFormat())).append(",");
        if(key.getKeyForMatVersions() != null) sb.append("KEYFORMATVERSIONS=").append(quoteString(key.getKeyForMatVersions())).append(",");
        sb.setLength(sb.length()-1);
        writeNewLine(sb);
    }

    private static String quoteString(String str){
        return "\""+str+"\"";
    }
    private static String toYesNo(boolean bool){
        return bool? "YES" : "NO";
    }

    private static void writeNewLine(StringBuilder stringBuilder){
        stringBuilder.append("\n");
    }
}
