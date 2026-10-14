            package co.vine.android.embed.player;

            import android.media.MediaFormat;
            import co.vine.android.util.AudioUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 15 */    public class AudioSampleTrack {
                public final int channelCount;
                public final MediaFormat format;
                public final int index;
                public final String mime;
                public final int sampleRate;

/* 16 */        public AudioSampleTrack(String mime, int index, int sampleRate, MediaFormat format) {
/* 18 */            this.mime = mime;
/* 19 */            this.index = index;
/* 20 */            this.sampleRate = sampleRate;
/* 21 */            this.format = format;
/* 22 */            this.channelCount = format.getInteger("channel-count");
                }

/* 25 */        public long decodedIndexToTimeUs(int index, int frameSize) {
/* 26 */            return (((long) index) * AudioUtils.MICROS_PER_SECOND) / ((long) ((this.sampleRate * this.channelCount) * frameSize));
                }

/* 29 */        public double decodedTimeUsToIndex(long timeUs, int frameSize) {
/* 30 */            return (((timeUs * ((double) this.sampleRate)) * ((double) this.channelCount)) * ((double) frameSize)) / 1000000.0d;
                }
            }
