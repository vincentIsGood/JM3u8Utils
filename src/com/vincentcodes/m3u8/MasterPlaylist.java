package com.vincentcodes.m3u8;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.vincentcodes.m3u8.errors.InvalidMediaType;
import com.vincentcodes.m3u8.errors.MissingAttributeException;
import com.vincentcodes.m3u8.types.Media;
import com.vincentcodes.m3u8.types.Stream;

public class MasterPlaylist {
    public int version;

    /**
     * Stream object itself relates to multiple Media and iframe streams
     */
    public final List<Stream> streams;
    public final List<Media> allMedia;

    public MasterPlaylist(){
        streams = new ArrayList<>();
        allMedia = new ArrayList<>();
    }

    /**
     * Add a new stream to this master playlist. All related media will be
     * added to the stream automatically.
     */
    public void addStream(Stream stream){
        if(stream.isIframeStream()){
            if(stream.getBandwidth() > 0){
                streams.add(stream);
                return;
            }else
                throw new MissingAttributeException("Iframe Stream must include a bandwidth attribute");
        }

        streams.add(stream);
        // if(stream.getAudioId() == null
        // && stream.getVideoId() == null
        // && stream.getSubtitlesId() == null){
        //     throw new MissingAttributeException("A stream cannot have no id (AUDIO & VIDEO & SUBTITLES)");
        // }
        for(Media media : allMedia){
            if(media.getType().equals("AUDIO")){
                if(stream.getAudioId() != null && stream.getAudioId().equals(media.getGroupId())){
                    stream.addMedia(media);
                }
            }else if(media.getType().equals("VIDEO")){
                if(stream.getVideoId() != null && stream.getVideoId().equals(media.getGroupId())){
                    stream.addMedia(media);
                }
            }else if(media.getType().equals("SUBTITLES")){
                if(stream.getSubtitlesId() != null && stream.getSubtitlesId().equals(media.getGroupId())){
                    stream.addMedia(media);
                }
            }
        }
    }

    /**
     * Add media to a related stream after its type is checked
     * to match the one in stream. This process is done 
     * automatically.
     */
    public void addMedia(Media media){
        allMedia.add(media);
        if(media.getGroupId() == null){
            throw new MissingAttributeException("GROUP-ID is missing");
        }
        for(Stream stream : streams){
            if(media.getType().equals("AUDIO")){
                if(stream.getAudioId() != null && stream.getAudioId().equals(media.getGroupId())){
                    stream.addMedia(media);
                }
            }else if(media.getType().equals("VIDEO")){
                if(stream.getVideoId() != null && stream.getVideoId().equals(media.getGroupId())){
                    stream.addMedia(media);
                }
            }else if(media.getType().equals("SUBTITLES")){
                if(stream.getSubtitlesId() != null && stream.getSubtitlesId().equals(media.getGroupId())){
                    stream.addMedia(media);
                }
            }else{
                throw new InvalidMediaType("'" + media.getType() + "' is not a valid media type.");
            }
        }
    }

    public Set<Integer> getAvailableBandwidths(){
        Set<Integer> bandwidths = new HashSet<>();
        for(Stream s : streams){
            bandwidths.add(s.getBandwidth());
        }
        return bandwidths;
    }

    /**
     * @return null if not found
     */
    public Optional<Stream> findStreamWithBandwidth(int bandwidth){
        for(Stream s : streams){
            if(s.getBandwidth() == bandwidth)
                return Optional.of(s);
        }
        return Optional.empty();
    }

    public List<Stream> findStreamsByBandwidth(int bandwidth){
        List<Stream> result = new ArrayList<>();
        for(Stream s : streams){
            if(s.getBandwidth() == bandwidth)
                result.add(s);
        }
        return result;
    }
}
