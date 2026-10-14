            package co.vine.android.mock.artists;

            import android.media.AudioRecord;
            import com.edisonwang.android.slog.SLog;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public class AnnoyingSinSoundArist implements MockMusicArtist {
                private int mEndPosition;
                private final float mFrequency = 2000.0f;
                private final MockMusicArtist.MusicArtistHelper mHelper = new MockMusicArtist.MusicArtistHelper();
                private short[] mSamples;

                @Override // co.vine.android.mock.artists.MockMusicArtist
/* 24 */        public short[] setupAudio(AudioRecord audioRecord) {
/* 25 */            short[] buffer = this.mHelper.onSetupAudio(audioRecord);
/* 26 */            this.mSamples = new short[this.mHelper.sampleRate];
                    for (int i = 0; i < this.mHelper.sampleRate; i++) {
/* 28 */                this.mSamples[i] = (short) (Math.sin((6.283185307179586d * ((double) i)) / ((double) ((int) (this.mHelper.sampleRate / this.mFrequency)))) * 32767.0d);
                    }
/* 31 */            return buffer;
                }

                @Override // co.vine.android.mock.artists.MockMusicArtist
/* 35 */        public int speak(short[] buffer, long time) {
/* 36 */            this.mHelper.onSpeak(buffer, time);
/* 37 */            this.mEndPosition = (int) ((((long) this.mEndPosition) + this.mHelper.actualSamplesPassed) % ((long) this.mSamples.length));
/* 38 */            SLog.m466i("Speaking about {} samples starting with #{}.", Integer.valueOf(this.mHelper.samplesToGive), Integer.valueOf(this.mEndPosition));
/* 40 */            int pos = this.mEndPosition - 1;
/* 42 */            int i = this.mHelper.samplesToGive - 1;
                    while (i >= 0) {
/* 43 */                if (pos < 0) {
/* 44 */                    pos = this.mSamples.length - 1;
                        }
/* 46 */                buffer[i] = this.mSamples[pos];
/* 42 */                i--;
                        pos--;
                    }
/* 48 */            return this.mHelper.samplesToGive;
                }
            }
