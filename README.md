# Introduction
M3u8Utils is a partially implemented m3u8 library. It allows you to parse and generate m3u8 files. Since m3u8 files are separated into two types: *Master* and *Media* m3u8 files, you are required to parse the m3u8 files separately. 

**Note**: Currently, low level operations are required to create a Stream or Media. (ie. you need to know what attribute you are dealing with) For more details take a look at `Generating a master playlist m3u8 file` section.

## Caution
Tests are not done excessively, there may be lots of / some bugs. Only few manual tests are done which you can find in `src/com/vincentcodes/tests`.

## Library Details
Mainly, you will be dealing with 3 classes:
- `MasterPlaylistParser`
- `MediaPlaylistParser`
- `PlaylistGenerator`

It is highly recommended to use intellisense with this library to see what properties you can use in `MasterPlaylist` and `MediaPlaylist`.

Example Usages:
```java
MasterPlaylist masterPlaylist = MasterPlaylistParser.parse(master1);
MediaPlaylist mediaPlaylist = MediaPlaylistParser.parse(media1);

PlaylistGenerator.toM3u8(masterPlaylist)
PlaylistGenerator.toM3u8(mediaPlaylist)
```

Currently, you can only generate m3u8 files in a very crude way which is shown below.

### Generating a master playlist m3u8 file
Example:
```java
MasterPlaylist master = new MasterPlaylist();
master.version = 4;
master.addStream(new Stream(
    Map.of("BANDWIDTH", "2568500", 
    "CODECS", "mp4a.40.2,avc1.640028", 
    "RESOLUTION", "1920x1080", 
    "AUDIO", "audio-3"), "http://127.0.0.1/media.m3u8"));

// If a stream has a URI attribute, it is an iframe stream.
master.addStream(new Stream(
    Map.of("BANDWIDTH", "2568500",
    "CODECS", "mp4a.40.2,avc1.640028",
    "RESOLUTION", "1920x1080",
    "URI", "http://127.0.0.1/iframe.m3u8"), null));

master.addMedia(new Media(
    Map.of("TYPE", "AUDIO",
    "GROUP-ID", "audio-3",
    "Name", "en (Main)",
    "DEFAULT", "YES",
    "URI", "http://127.0.0.1/rendition.m3u8")));
```

Output:
```m3u8
#EXTM3U
#EXT-X-VERSION:4
#EXT-X-MEDIA:TYPE=AUDIO,GROUP-ID="audio-3",DEFAULT=YES,URI="http://127.0.0.1/rendition.m3u8"
#EXT-X-STREAM-INF:BANDWIDTH=2568500,CODECS="mp4a.40.2,avc1.640028",RESOLUTION=1920x1080,AUDIO="audio-3"
http://127.0.0.1/media.m3u8
#EXT-X-I-FRAME-STREAM-INF:BANDWIDTH=2568500,CODECS="mp4a.40.2,avc1.640028",RESOLUTION=1920x1080,URI="http://127.0.0.1/iframe.m3u8"
```

### Generating a media playlist m3u8 file
Example:
```java
MediaPlaylist media = new MediaPlaylist();
media.version = 4;
media.targetDuration = 10;
media.sequence = 1;
media.doesPlaylistEnds = true;
for(int i = 1; i <= 5; i++)
    media.addSegment("seg"+i+".ts", 10.0f);
System.out.println(PlaylistGenerator.toM3u8(media));
```

Output:
```m3u8
#EXTM3U
#EXT-X-VERSION:4        
#EXT-X-MEDIA-SEQUENCE:1 
#EXT-X-TARGETDURATION:10
#EXTINF:10.0,
seg1.ts
#EXTINF:10.0,
seg2.ts
#EXTINF:10.0,
seg3.ts
#EXTINF:10.0,
seg4.ts
#EXTINF:10.0,
seg5.ts
#EXT-X-ENDLIST
```

## Future goals of this library
Hopefully, in the near future the following features will be implemented:
- Master and Media playlist detection
- Create a builder for Media, Stream to easily initialize them.