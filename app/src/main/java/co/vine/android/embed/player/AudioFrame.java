            package co.vine.android.embed.player;

            import android.media.MediaCodec;
            import co.vine.android.util.AudioUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    public class AudioFrame implements Comparable {
                public final int offset;
                public final long presentationTimeUs;
                public final int size;
                public final long startUs;
                public final boolean syncFrame;
                public final int trackIndex;

/* 18 */        public AudioFrame(AudioSampleTrack track, boolean syncFrame, int trackIndex, MediaCodec.BufferInfo info) {
/* 20 */            this.syncFrame = syncFrame;
/* 21 */            this.trackIndex = trackIndex;
/* 22 */            this.presentationTimeUs = info.presentationTimeUs;
/* 23 */            this.size = info.size;
/* 24 */            this.offset = info.offset;
/* 25 */            this.startUs = AudioUtils.presentationTimeUsToStartTimeUs(this.presentationTimeUs, this.size, track.sampleRate, track.channelCount);
                }

                @Override // java.lang.Comparable
/* 29 */        public int compareTo(Object another) {
/* 30 */            return (int) (this.presentationTimeUs - ((AudioFrame) another).presentationTimeUs);
                }
            }
