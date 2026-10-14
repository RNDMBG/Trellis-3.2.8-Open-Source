            package co.vine.android.mock.artists;

            import android.media.AudioRecord;
            import com.edisonwang.android.slog.SLog;
            
            
    public class AnnoyingSinSoundArist implements MockMusicArtist {
                private int mEndPosition;
                private final float mFrequency = 2000.0f;
                private final MockMusicArtist.MusicArtistHelper mHelper = new MockMusicArtist.MusicArtistHelper();
                private short[] mSamples;

                @Override 
        public short[] setupAudio(AudioRecord audioRecord) {
            short[] buffer = this.mHelper.onSetupAudio(audioRecord);
            this.mSamples = new short[this.mHelper.sampleRate];
                    for (int i = 0; i < this.mHelper.sampleRate; i++) {
                this.mSamples[i] = (short) (Math.sin((6.283185307179586d * ((double) i)) / ((double) ((int) (this.mHelper.sampleRate / this.mFrequency)))) * 32767.0d);
                    }
            return buffer;
                }

                @Override 
        public int speak(short[] buffer, long time) {
            this.mHelper.onSpeak(buffer, time);
            this.mEndPosition = (int) ((((long) this.mEndPosition) + this.mHelper.actualSamplesPassed) % ((long) this.mSamples.length));
            SLog.m466i("Speaking about {} samples starting with #{}.", Integer.valueOf(this.mHelper.samplesToGive), Integer.valueOf(this.mEndPosition));
            int pos = this.mEndPosition - 1;
            int i = this.mHelper.samplesToGive - 1;
                    while (i >= 0) {
                if (pos < 0) {
                    pos = this.mSamples.length - 1;
                        }
                buffer[i] = this.mSamples[pos];
                i--;
                        pos--;
                    }
            return this.mHelper.samplesToGive;
                }
            }
