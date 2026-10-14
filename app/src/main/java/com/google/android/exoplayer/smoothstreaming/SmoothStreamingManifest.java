            package com.google.android.exoplayer.smoothstreaming;

            import co.vine.android.util.AudioUtils;
            import com.google.android.exoplayer.util.MimeTypes;
            import com.google.android.exoplayer.util.Util;
            import java.util.UUID;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 39 */    public class SmoothStreamingManifest {
                private final long duration;
                public final int lookAheadCount;
                public final int majorVersion;
                public final int minorVersion;
                public final ProtectionElement protectionElement;
                public final StreamElement[] streamElements;
                public final long timeScale;

/* 40 */        public SmoothStreamingManifest(int majorVersion, int minorVersion, long timeScale, long duration, int lookAheadCount, ProtectionElement protectionElement, StreamElement[] streamElements) {
/* 42 */            this.majorVersion = majorVersion;
/* 43 */            this.minorVersion = minorVersion;
/* 44 */            this.timeScale = timeScale;
/* 45 */            this.duration = duration;
/* 46 */            this.lookAheadCount = lookAheadCount;
/* 47 */            this.protectionElement = protectionElement;
/* 48 */            this.streamElements = streamElements;
                }

/* 57 */        public long getDurationUs() {
/* 58 */            return (this.duration * AudioUtils.MICROS_PER_SECOND) / this.timeScale;
                }

/* 67 */        public static class ProtectionElement {
                    public final byte[] data;
                    public final UUID uuid;

/* 68 */            public ProtectionElement(UUID uuid, byte[] data) {
/* 70 */                this.uuid = uuid;
/* 71 */                this.data = data;
                    }
                }

/* 108 */       public static class TrackElement {
                    public final int audioTag;
                    public final int bitPerSample;
                    public final int bitrate;
                    public final String content;
                    public final byte[][] csd;
                    public final String fourCC;
                    public final int index;
                    public final int level;
                    public final int maxHeight;
                    public final int maxWidth;
                    public final String mimeType;
                    public final int nalUnitLengthField;
                    public final int numChannels;
                    public final int packetSize;
                    public final int profile;
                    public final int sampleRate;

/* 109 */           public TrackElement(int index, int bitrate, String fourCC, byte[][] csd, int profile, int level, int maxWidth, int maxHeight, int sampleRate, int channels, int packetSize, int audioTag, int bitPerSample, int nalUnitLengthField, String content) {
/* 111 */               this.index = index;
/* 112 */               this.bitrate = bitrate;
/* 113 */               this.fourCC = fourCC;
/* 114 */               this.csd = csd;
/* 115 */               this.profile = profile;
/* 116 */               this.level = level;
/* 117 */               this.maxWidth = maxWidth;
/* 118 */               this.maxHeight = maxHeight;
/* 119 */               this.sampleRate = sampleRate;
/* 120 */               this.numChannels = channels;
/* 121 */               this.packetSize = packetSize;
/* 122 */               this.audioTag = audioTag;
/* 123 */               this.bitPerSample = bitPerSample;
/* 124 */               this.nalUnitLengthField = nalUnitLengthField;
/* 125 */               this.content = content;
/* 126 */               this.mimeType = fourCCToMimeType(fourCC);
                    }

/* 129 */           private static String fourCCToMimeType(String fourCC) {
/* 130 */               if (fourCC.equalsIgnoreCase("H264") || fourCC.equalsIgnoreCase("AVC1") || fourCC.equalsIgnoreCase("DAVC")) {
/* 132 */                   return "video/avc";
                        }
/* 133 */               if (fourCC.equalsIgnoreCase("AACL") || fourCC.equalsIgnoreCase("AACH")) {
/* 134 */                   return MimeTypes.AUDIO_AAC;
                        }
/* 135 */               if (fourCC.equalsIgnoreCase("TTML")) {
/* 136 */                   return MimeTypes.APPLICATION_TTML;
                        }
/* 138 */               return null;
                    }
                }

/* 144 */       public static class StreamElement {
                    static final /* synthetic */ boolean $assertionsDisabled;
                    public static final int TYPE_AUDIO = 0;
                    public static final int TYPE_TEXT = 2;
                    public static final int TYPE_UNKNOWN = -1;
                    public static final int TYPE_VIDEO = 1;
                    private static final String URL_PLACEHOLDER_BITRATE = "{bitrate}";
                    private static final String URL_PLACEHOLDER_START_TIME = "{start time}";
                    public final int chunkCount;
                    private final long[] chunkStartTimes;
                    public final int displayHeight;
                    public final int displayWidth;
                    public final String language;
                    public final int maxHeight;
                    public final int maxWidth;
                    public final String name;
                    public final int qualityLevels;
                    public final String subType;
                    public final long timeScale;
                    public final TrackElement[] tracks;
                    public final int type;
                    public final String url;

                    static {
/* 146 */               $assertionsDisabled = !SmoothStreamingManifest.class.desiredAssertionStatus();
                    }

/* 173 */           public StreamElement(int type, String subType, long timeScale, String name, int qualityLevels, String url, int maxWidth, int maxHeight, int displayWidth, int displayHeight, String language, TrackElement[] tracks, long[] chunkStartTimes) {
/* 175 */               this.type = type;
/* 176 */               this.subType = subType;
/* 177 */               this.timeScale = timeScale;
/* 178 */               this.name = name;
/* 179 */               this.qualityLevels = qualityLevels;
/* 180 */               this.url = url;
/* 181 */               this.maxWidth = maxWidth;
/* 182 */               this.maxHeight = maxHeight;
/* 183 */               this.displayWidth = displayWidth;
/* 184 */               this.displayHeight = displayHeight;
/* 185 */               this.language = language;
/* 186 */               this.tracks = tracks;
/* 187 */               this.chunkCount = chunkStartTimes.length;
/* 188 */               this.chunkStartTimes = chunkStartTimes;
                    }

/* 197 */           public int getChunkIndex(long timeUs) {
/* 198 */               return Util.binarySearchFloor(this.chunkStartTimes, (this.timeScale * timeUs) / AudioUtils.MICROS_PER_SECOND, true, true);
                    }

/* 207 */           public long getStartTimeUs(int chunkIndex) {
/* 208 */               return (this.chunkStartTimes[chunkIndex] * AudioUtils.MICROS_PER_SECOND) / this.timeScale;
                    }

/* 218 */           public String buildRequestUrl(int track, int chunkIndex) {
/* 219 */               if (!$assertionsDisabled && this.tracks == null) {
                            throw new AssertionError();
                        }
/* 220 */               if (!$assertionsDisabled && this.chunkStartTimes == null) {
                            throw new AssertionError();
                        }
/* 221 */               if ($assertionsDisabled || chunkIndex < this.chunkStartTimes.length) {
/* 222 */                   return this.url.replace(URL_PLACEHOLDER_BITRATE, Integer.toString(this.tracks[track].bitrate)).replace(URL_PLACEHOLDER_START_TIME, Long.toString(this.chunkStartTimes[chunkIndex]));
                        }
                        throw new AssertionError();
                    }
                }
            }
