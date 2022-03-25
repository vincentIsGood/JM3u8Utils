package com.vincentcodes.tests;

import java.util.Map;

import com.vincentcodes.m3u8.MasterPlaylist;
import com.vincentcodes.m3u8.MediaPlaylist;
import com.vincentcodes.m3u8.PlaylistGenerator;
import com.vincentcodes.m3u8.types.Media;
import com.vincentcodes.m3u8.types.Stream;

public class Test1 {
    private static String master1 = """
        #EXTM3U
        #EXT-X-MEDIA:TYPE=AUDIO,GROUP-ID=\"audio\",LANGUAGE=\"eng\",NAME=\"English\",AUTOSELECT=YES, DEFAULT=YES,URI=\"eng/prog_index.m3u8\"
        #EXT-X-MEDIA:TYPE=AUDIO,GROUP-ID=\"audio\",LANGUAGE=\"fre\",NAME=\"Français\",AUTOSELECT=YES, DEFAULT=NO,URI=\"fre/prog_index.m3u8\"
        #EXT-X-MEDIA:TYPE=AUDIO,GROUP-ID=\"audio\",LANGUAGE=\"sp\",NAME=\"Espanol\",AUTOSELECT=YES, DEFAULT=NO,URI=\"sp/prog_index.m3u8\"
        
        #EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=195023,CODECS=\"avc1.42e00a,mp4a.40.2\",AUDIO=\"audio\"
        lo/prog_index.m3u8
        #EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=591680,CODECS=\"avc1.42e01e,mp4a.40.2\",AUDIO=\"audio\"
        hi/prog_index.m3u8
    """;

    private static String master2 = """
        #EXTM3U
        #EXT-X-VERSION:4
        #EXT-X-MEDIA:TYPE=AUDIO,GROUP-ID=\"audio-0\",NAME=\"en (Main)\",DEFAULT=YES,AUTOSELECT=YES,LANGUAGE=\"en\",URI=\"http://127.0.0.1/rendition.m3u8\"
        #EXT-X-STREAM-INF:PROGRAM-ID=0,BANDWIDTH=476300,CODECS=\"mp4a.40.2,avc1.420015\",RESOLUTION=480x270,AUDIO=\"audio-0\",CLOSED-CAPTIONS=NONE
        http://127.0.0.1/rendition.m3u8
        #EXT-X-I-FRAME-STREAM-INF:PROGRAM-ID=0,BANDWIDTH=476300,CODECS=\"mp4a.40.2,avc1.420015\",RESOLUTION=480x270,URI=\"http://127.0.0.1/iframe.m3u8\"
        #EXT-X-MEDIA:TYPE=AUDIO,GROUP-ID=\"audio-1\",NAME=\"en (Main)\",DEFAULT=YES,AUTOSELECT=YES,LANGUAGE=\"en\",URI=\"http://127.0.0.1/rendition.m3u8\"
        #EXT-X-STREAM-INF:PROGRAM-ID=0,BANDWIDTH=863500,CODECS=\"mp4a.40.2,avc1.4d001f\",RESOLUTION=960x540,AUDIO=\"audio-1\",CLOSED-CAPTIONS=NONE
        http://127.0.0.1/rendition.m3u8
        #EXT-X-I-FRAME-STREAM-INF:PROGRAM-ID=0,BANDWIDTH=863500,CODECS=\"mp4a.40.2,avc1.4d001f\",RESOLUTION=960x540,URI=\"http://127.0.0.1/iframe.m3u8\"
        #EXT-X-MEDIA:TYPE=AUDIO,GROUP-ID=\"audio-2\",NAME=\"en (Main)\",DEFAULT=YES,AUTOSELECT=YES,LANGUAGE=\"en\",URI=\"http://127.0.0.1/rendition.m3u8\"
        #EXT-X-STREAM-INF:PROGRAM-ID=0,BANDWIDTH=1291400,CODECS=\"mp4a.40.2,avc1.4d001f\",RESOLUTION=1280x720,AUDIO=\"audio-2\",CLOSED-CAPTIONS=NONE
        http://127.0.0.1/rendition.m3u8
        #EXT-X-I-FRAME-STREAM-INF:PROGRAM-ID=0,BANDWIDTH=1291400,CODECS=\"mp4a.40.2,avc1.4d001f\",RESOLUTION=1280x720,URI=\"http://127.0.0.1/iframe.m3u8\"
        #EXT-X-MEDIA:TYPE=AUDIO,GROUP-ID=\"audio-3\",NAME=\"en (Main)\",DEFAULT=YES,AUTOSELECT=YES,LANGUAGE=\"en\",URI=\"http://127.0.0.1/rendition.m3u8\"
        #EXT-X-STREAM-INF:PROGRAM-ID=0,BANDWIDTH=2568500,CODECS=\"mp4a.40.2,avc1.640028\",RESOLUTION=1920x1080,AUDIO=\"audio-3\",CLOSED-CAPTIONS=NONE
        http://127.0.0.1/rendition.m3u8
        #EXT-X-I-FRAME-STREAM-INF:PROGRAM-ID=0,BANDWIDTH=2568500,CODECS=\"mp4a.40.2,avc1.640028\",RESOLUTION=1920x1080,URI=\"http://127.0.0.1/iframe.m3u8\"
    """;

    private static String media1 = """
        #EXTM3U
        #EXT-X-TARGETDURATION:10
        #EXT-X-VERSION:4
        #EXT-X-MEDIA-SEQUENCE:1
        #EXTINF:10.0,
        fileSequence1.ts
        #EXTINF:10.0,
        fileSequence2.ts
        #EXTINF:10.0,
        fileSequence3.ts
        #EXTINF:10.0,
        fileSequence4.ts
        #EXTINF:10.0,
        fileSequence5.ts
    """;

    private static String media2 = """
        #EXTM3U
        #EXT-X-VERSION:3
        #EXT-X-KEY:METHOD=AES-128,URI=\"https://127.0.0.1/testkeyfile\",IV=0x12312312312312312312312312312312
        #EXT-X-PLAYLIST-TYPE:VOD
        #EXT-X-MEDIA-SEQUENCE:0
        #EXT-X-TARGETDURATION:10
        #EXTINF:10.010,
        https://127.0.0.1/segment0.ts
        #EXTINF:10.010,
        https://127.0.0.1/segment1.ts
        #EXTINF:10.010,
        https://127.0.0.1/segment2.ts
        #EXTINF:10.010,
        https://127.0.0.1/segment3.ts
        #EXTINF:10.010,
        https://127.0.0.1/segment4.ts
        #EXTINF:10.010,
        https://127.0.0.1/segment5.ts
        #EXTINF:10.010,
        https://127.0.0.1/segment6.ts
        #EXTINF:10.010,
        https://127.0.0.1/segment7.ts
        #EXTINF:10.010,
        https://127.0.0.1/segment8.ts
        #EXTINF:10.010,
        https://127.0.0.1/segment9.ts
        #EXTINF:10.010,
        https://127.0.0.1/segment10.ts
        #EXTINF:10.010,
        https://127.0.0.1/segment11.ts
        #EXTINF:1.869,
        https://127.0.0.1/segment12.ts
        #EXT-X-ENDLIST
    """;

    public static void main(String[] args) throws Exception{
        // MasterPlaylist masterPlaylist1 = MasterPlaylistParser.parse(master2);
        // System.out.println(masterPlaylist1.streams);
        // MediaPlaylist mediaPlaylist = MediaPlaylistParser.parse(media2);
        // System.out.println(mediaPlaylist.sequence);
        // System.out.println(mediaPlaylist.segments);
        // System.out.println(mediaPlaylist.keys);
        // System.out.println(PlaylistGenerator.toM3u8(masterPlaylist1));
        // System.out.println(PlaylistGenerator.toM3u8(mediaPlaylist));
        
        // try(FileInputStream fis = new FileInputStream("testfiles/master.m3u8")){
        //     MasterPlaylist masterPlaylist2 = MasterPlaylistParser.parse(new String(fis.readAllBytes()));
        //     System.out.println(masterPlaylist2.streams);
        //     System.out.println(masterPlaylist2.findStreamsByBandwidth(1291400));
        // }
        generateMediaM3u8Crude();
    }

    private static void generateMasterM3u8Crude(){
        MasterPlaylist master = new MasterPlaylist();
        master.version = 4;
        master.addStream(new Stream(
            Map.of("BANDWIDTH", "2568500", 
            "CODECS", "mp4a.40.2,avc1.640028", 
            "RESOLUTION", "1920x1080", 
            "AUDIO", "audio-3"), "http://127.0.0.1/media.m3u8"));
        // If a stream has a URI attribute, it is an iframe stream.
        master.addStream(
            new Stream(
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
        System.out.println(PlaylistGenerator.toM3u8(master));
    }

    private static void generateMediaM3u8Crude(){
        MediaPlaylist media = new MediaPlaylist();
        media.version = 4;
        media.targetDuration = 10;
        media.sequence = 1;
        media.doesPlaylistEnds = true;
        for(int i = 1; i <= 5; i++)
            media.addSegment("seg"+i+".ts", 10.0f);
        System.out.println(PlaylistGenerator.toM3u8(media));
    }
}
