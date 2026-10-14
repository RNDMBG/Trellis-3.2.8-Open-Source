            package co.vine.android.embed.player;

            import android.media.MediaCodec;
            import co.vine.android.util.AudioUtils;
            
            
    public class AudioFrame implements Comparable {
                public final int offset;
                public final long presentationTimeUs;
                public final int size;
                public final long startUs;
                public final boolean syncFrame;
                public final int trackIndex;

        public AudioFrame(AudioSampleTrack track, boolean syncFrame, int trackIndex, MediaCodec.BufferInfo info) {
            this.syncFrame = syncFrame;
            this.trackIndex = trackIndex;
            this.presentationTimeUs = info.presentationTimeUs;
            this.size = info.size;
            this.offset = info.offset;
            this.startUs = AudioUtils.presentationTimeUsToStartTimeUs(this.presentationTimeUs, this.size, track.sampleRate, track.channelCount);
                }

                @Override 
        public int compareTo(Object another) {
            return (int) (this.presentationTimeUs - ((AudioFrame) another).presentationTimeUs);
                }
            }
