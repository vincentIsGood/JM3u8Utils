package com.vincentcodes.m3u8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vincentcodes.m3u8.types.KeyTag;

public class MediaPlaylist {
    public int version = -1;

    public String type;
    public boolean iframesOnly;
    public float targetDuration = -1;

    // Media Sequence
    public int sequence = -1;

    // Discontinuity Sequence
    public int disSequence = -1;

    // Has EXT-X-ENDLIST
    public boolean doesPlaylistEnds;

    // Next segment index to Key URI mapping
    public final Map<Integer, KeyTag> keys;
    public final List<String> segments;
    public final List<Float> segDurations;

    public MediaPlaylist(){
        keys = new HashMap<>();
        segments = new ArrayList<>();
        segDurations = new ArrayList<>();
    }

    public void addSegment(String uri, float duration){
        segments.add(uri);
        segDurations.add(duration);
    }

    public void addKey(KeyTag key){
        keys.put(segments.size(), key);
    }
}
