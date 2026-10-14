            package com.google.android.exoplayer.chunk;

            import com.google.android.exoplayer.util.Assertions;
            import java.util.Comparator;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public class Format {
                public final int audioSamplingRate;

                @Deprecated
                public final int bandwidth;
                public final int bitrate;
                public final int height;

                /* JADX INFO: renamed from: id */
                public final String f875id;
                public final String language;
                public final String mimeType;
                public final int numChannels;
                public final int width;

/* 28 */        public static final class DecreasingBandwidthComparator implements Comparator<Format> {

                    /* JADX INFO: renamed from: compare, reason: avoid collision after fix types in other method */
/* 33 */            public int compare(Format a, Format b) {
/* 34 */                return b.bitrate - a.bitrate;
                    }
                }

/* 100 */       public Format(String id, String mimeType, int width, int height, int numChannels, int audioSamplingRate, int bitrate) {
/* 101 */           this(id, mimeType, width, height, numChannels, audioSamplingRate, bitrate, null);
                }

/* 114 */       public Format(String id, String mimeType, int width, int height, int numChannels, int audioSamplingRate, int bitrate, String language) {
/* 116 */           this.f875id = (String) Assertions.checkNotNull(id);
/* 117 */           this.mimeType = mimeType;
/* 118 */           this.width = width;
/* 119 */           this.height = height;
/* 120 */           this.numChannels = numChannels;
/* 121 */           this.audioSamplingRate = audioSamplingRate;
/* 122 */           this.bitrate = bitrate;
/* 123 */           this.language = language;
/* 124 */           this.bandwidth = bitrate / 8;
                }

/* 128 */       public int hashCode() {
/* 129 */           return this.f875id.hashCode();
                }

/* 136 */       public boolean equals(Object obj) {
/* 137 */           if (this == obj) {
/* 138 */               return true;
                    }
/* 140 */           if (obj == null || getClass() != obj.getClass()) {
/* 141 */               return false;
                    }
/* 143 */           Format other = (Format) obj;
/* 144 */           return other.f875id.equals(this.f875id);
                }
            }
