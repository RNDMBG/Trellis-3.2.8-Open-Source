            package com.google.android.exoplayer.parser.mp4;

            import com.google.android.exoplayer.MediaFormat;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 66 */    public final class Track {
                public static final int TYPE_AUDIO = 1936684398;
                public static final int TYPE_HINT = 1751740020;
                public static final int TYPE_META = 1835365473;
                public static final int TYPE_VIDEO = 1986618469;

                /* JADX INFO: renamed from: id */
                public final int f878id;
                public final MediaFormat mediaFormat;
                public final TrackEncryptionBox[] sampleDescriptionEncryptionBoxes;
                public final long timescale;
                public final int type;

/* 67 */        public Track(int id, int type, long timescale, MediaFormat mediaFormat, TrackEncryptionBox[] sampleDescriptionEncryptionBoxes) {
/* 69 */            this.f878id = id;
/* 70 */            this.type = type;
/* 71 */            this.timescale = timescale;
/* 72 */            this.mediaFormat = mediaFormat;
/* 73 */            this.sampleDescriptionEncryptionBoxes = sampleDescriptionEncryptionBoxes;
                }
            }
