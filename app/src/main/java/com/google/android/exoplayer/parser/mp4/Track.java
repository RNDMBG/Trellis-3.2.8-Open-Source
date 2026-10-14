            package com.google.android.exoplayer.parser.mp4;

            import com.google.android.exoplayer.MediaFormat;
            
            
    public final class Track {
                public static final int TYPE_AUDIO = 1936684398;
                public static final int TYPE_HINT = 1751740020;
                public static final int TYPE_META = 1835365473;
                public static final int TYPE_VIDEO = 1986618469;

                
                public final int f878id;
                public final MediaFormat mediaFormat;
                public final TrackEncryptionBox[] sampleDescriptionEncryptionBoxes;
                public final long timescale;
                public final int type;

        public Track(int id, int type, long timescale, MediaFormat mediaFormat, TrackEncryptionBox[] sampleDescriptionEncryptionBoxes) {
            this.f878id = id;
            this.type = type;
            this.timescale = timescale;
            this.mediaFormat = mediaFormat;
            this.sampleDescriptionEncryptionBoxes = sampleDescriptionEncryptionBoxes;
                }
            }
