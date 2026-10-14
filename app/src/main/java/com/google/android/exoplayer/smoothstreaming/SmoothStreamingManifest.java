            package com.google.android.exoplayer.smoothstreaming;

            import co.vine.android.util.AudioUtils;
            import com.google.android.exoplayer.util.MimeTypes;
            import com.google.android.exoplayer.util.Util;
            import java.util.UUID;
            
            
    public class SmoothStreamingManifest {
                private final long duration;
                public final int lookAheadCount;
                public final int majorVersion;
                public final int minorVersion;
                public final ProtectionElement protectionElement;
                public final StreamElement[] streamElements;
                public final long timeScale;

        public SmoothStreamingManifest(int majorVersion, int minorVersion, long timeScale, long duration, int lookAheadCount, ProtectionElement protectionElement, StreamElement[] streamElements) {
            this.majorVersion = majorVersion;
            this.minorVersion = minorVersion;
            this.timeScale = timeScale;
            this.duration = duration;
            this.lookAheadCount = lookAheadCount;
            this.protectionElement = protectionElement;
            this.streamElements = streamElements;
                }

        public long getDurationUs() {
            return (this.duration * AudioUtils.MICROS_PER_SECOND) / this.timeScale;
                }

        public static class ProtectionElement {
                    public final byte[] data;
                    public final UUID uuid;

            public ProtectionElement(UUID uuid, byte[] data) {
                this.uuid = uuid;
                this.data = data;
                    }
                }

       public static class TrackElement {
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

           public TrackElement(int index, int bitrate, String fourCC, byte[][] csd, int profile, int level, int maxWidth, int maxHeight, int sampleRate, int channels, int packetSize, int audioTag, int bitPerSample, int nalUnitLengthField, String content) {
               this.index = index;
               this.bitrate = bitrate;
               this.fourCC = fourCC;
               this.csd = csd;
               this.profile = profile;
               this.level = level;
               this.maxWidth = maxWidth;
               this.maxHeight = maxHeight;
               this.sampleRate = sampleRate;
               this.numChannels = channels;
               this.packetSize = packetSize;
               this.audioTag = audioTag;
               this.bitPerSample = bitPerSample;
               this.nalUnitLengthField = nalUnitLengthField;
               this.content = content;
               this.mimeType = fourCCToMimeType(fourCC);
                    }

           private static String fourCCToMimeType(String fourCC) {
               if (fourCC.equalsIgnoreCase("H264") || fourCC.equalsIgnoreCase("AVC1") || fourCC.equalsIgnoreCase("DAVC")) {
                   return "video/avc";
                        }
               if (fourCC.equalsIgnoreCase("AACL") || fourCC.equalsIgnoreCase("AACH")) {
                   return MimeTypes.AUDIO_AAC;
                        }
               if (fourCC.equalsIgnoreCase("TTML")) {
                   return MimeTypes.APPLICATION_TTML;
                        }
               return null;
                    }
                }

       public static class StreamElement {
                    static final  boolean $assertionsDisabled;
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
               $assertionsDisabled = !SmoothStreamingManifest.class.desiredAssertionStatus();
                    }

           public StreamElement(int type, String subType, long timeScale, String name, int qualityLevels, String url, int maxWidth, int maxHeight, int displayWidth, int displayHeight, String language, TrackElement[] tracks, long[] chunkStartTimes) {
               this.type = type;
               this.subType = subType;
               this.timeScale = timeScale;
               this.name = name;
               this.qualityLevels = qualityLevels;
               this.url = url;
               this.maxWidth = maxWidth;
               this.maxHeight = maxHeight;
               this.displayWidth = displayWidth;
               this.displayHeight = displayHeight;
               this.language = language;
               this.tracks = tracks;
               this.chunkCount = chunkStartTimes.length;
               this.chunkStartTimes = chunkStartTimes;
                    }

           public int getChunkIndex(long timeUs) {
               return Util.binarySearchFloor(this.chunkStartTimes, (this.timeScale * timeUs) / AudioUtils.MICROS_PER_SECOND, true, true);
                    }

           public long getStartTimeUs(int chunkIndex) {
               return (this.chunkStartTimes[chunkIndex] * AudioUtils.MICROS_PER_SECOND) / this.timeScale;
                    }

           public String buildRequestUrl(int track, int chunkIndex) {
               if (!$assertionsDisabled && this.tracks == null) {
                            throw new AssertionError();
                        }
               if (!$assertionsDisabled && this.chunkStartTimes == null) {
                            throw new AssertionError();
                        }
               if ($assertionsDisabled || chunkIndex < this.chunkStartTimes.length) {
                   return this.url.replace(URL_PLACEHOLDER_BITRATE, Integer.toString(this.tracks[track].bitrate)).replace(URL_PLACEHOLDER_START_TIME, Long.toString(this.chunkStartTimes[chunkIndex]));
                        }
                        throw new AssertionError();
                    }
                }
            }
