package com.vincentcodes.m3u8.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Stream. One stream may relate to multiple media. 
 * But an "identical" stream can have multiple bandwidth 
 * to choose from. In this case, the "identical" stream
 * is separated into its own Stream. Iframe streams
 * are video streams while normal streams are audio streams.
 * 
 * <p>
 * For example, these are different although they refer 
 * to the same audio id "audio"
 * <pre>
 * #EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=195023,CODECS="avc1.42e00a,mp4a.40.2",AUDIO="audio" 
 * low/prog_index.m3u8
 * #EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=591680,CODECS="avc1.42e01e,mp4a.40.2",AUDIO="audio"
 * high/prog_index.m3u8
 * </pre>
 * 
 */
public class Stream {
    private final Map<String, String> attributes;
    public final List<Media> relatedMedia;
    public final String playlist;

    public Stream(Map<String, String> attributes, String playlist){
        this.attributes = attributes;
        this.playlist = playlist == null? "" : playlist;
        relatedMedia = new ArrayList<>();
    }

    public void addMedia(Media media){
        relatedMedia.add(media);
    }

    public boolean isIframeStream(){
        return getURI() != null;
    }
    
    // Getters
    public int getProgramId(){
        String val = attributes.get("PROGRAM-ID") == null? "-1" : attributes.get("PROGRAM-ID");
        return Integer.parseInt(val);
    }

    public int getBandwidth() {
        String val = attributes.get("BANDWIDTH") == null? "-1" : attributes.get("BANDWIDTH");
        return Integer.parseInt(val);
    }

    public int getAvgBandwidth() {
        String val = attributes.get("AVERAGE-BANDWIDTH") == null? "-1" : attributes.get("AVERAGE-BANDWIDTH");
        return Integer.parseInt(val);
    }

    public String getCodecs() {
        return attributes.get("CODECS");
    }

    public String getResolution() {
        return attributes.get("RESOLUTION");
    }

    public float getFps() {
        String val = attributes.get("FRAME-RATE") == null? "-1" : attributes.get("FRAME-RATE");
        return Float.parseFloat(val);
    }

    public String getAudioId() {
        return attributes.get("AUDIO");
    }

    public String getVideoId() {
        return attributes.get("VIDEO");
    }

    public String getSubtitlesId() {
        return attributes.get("SUBTITLES");
    }

    public String getClosedCaptions() {
        return attributes.get("CLOSED-CAPTIONS");
    }

    /**
     * Only EXT-X-I-FRAME-STREAM-INF tags have this attribute.
     */
    public String getURI(){
        return attributes.get("URI");
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public List<Media> getMedia() {
        return relatedMedia;
    }

    public String toString(){
        return "{Stream playlist:\""+ playlist +"\", attributes:"+ attributes.toString() +", media:"+ relatedMedia.toString() +"}";
    }
}
