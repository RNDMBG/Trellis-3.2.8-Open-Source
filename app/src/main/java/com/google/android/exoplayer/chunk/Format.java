            package com.google.android.exoplayer.chunk;

            import com.google.android.exoplayer.util.Assertions;
            import java.util.Comparator;
            
            
    public class Format {
                public final int audioSamplingRate;

                @Deprecated
                public final int bandwidth;
                public final int bitrate;
                public final int height;

                
                public final String f875id;
                public final String language;
                public final String mimeType;
                public final int numChannels;
                public final int width;

        public static final class DecreasingBandwidthComparator implements Comparator<Format> {

                    
            public int compare(Format a, Format b) {
                return b.bitrate - a.bitrate;
                    }
                }

       public Format(String id, String mimeType, int width, int height, int numChannels, int audioSamplingRate, int bitrate) {
           this(id, mimeType, width, height, numChannels, audioSamplingRate, bitrate, null);
                }

       public Format(String id, String mimeType, int width, int height, int numChannels, int audioSamplingRate, int bitrate, String language) {
           this.f875id = (String) Assertions.checkNotNull(id);
           this.mimeType = mimeType;
           this.width = width;
           this.height = height;
           this.numChannels = numChannels;
           this.audioSamplingRate = audioSamplingRate;
           this.bitrate = bitrate;
           this.language = language;
           this.bandwidth = bitrate / 8;
                }

       public int hashCode() {
           return this.f875id.hashCode();
                }

       public boolean equals(Object obj) {
           if (this == obj) {
               return true;
                    }
           if (obj == null || getClass() != obj.getClass()) {
               return false;
                    }
           Format other = (Format) obj;
           return other.f875id.equals(this.f875id);
                }
            }
