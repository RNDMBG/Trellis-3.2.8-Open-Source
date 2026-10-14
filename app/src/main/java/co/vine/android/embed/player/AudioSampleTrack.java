            package co.vine.android.embed.player;

            import android.media.MediaFormat;
            import co.vine.android.util.AudioUtils;
            
            
    public class AudioSampleTrack {
                public final int channelCount;
                public final MediaFormat format;
                public final int index;
                public final String mime;
                public final int sampleRate;

        public AudioSampleTrack(String mime, int index, int sampleRate, MediaFormat format) {
            this.mime = mime;
            this.index = index;
            this.sampleRate = sampleRate;
            this.format = format;
            this.channelCount = format.getInteger("channel-count");
                }

        public long decodedIndexToTimeUs(int index, int frameSize) {
            return (((long) index) * AudioUtils.MICROS_PER_SECOND) / ((long) ((this.sampleRate * this.channelCount) * frameSize));
                }

        public double decodedTimeUsToIndex(long timeUs, int frameSize) {
            return (((timeUs * ((double) this.sampleRate)) * ((double) this.channelCount)) * ((double) frameSize)) / 1000000.0d;
                }
            }
