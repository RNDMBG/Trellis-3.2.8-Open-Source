            package co.vine.android.mock.artists;

            import android.media.AudioRecord;
            import com.edisonwang.android.slog.SLog;
            import java.lang.reflect.Field;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public interface MockMusicArtist {
                short[] setupAudio(AudioRecord audioRecord);

                int speak(short[] sArr, long j);

/* 25 */        public static class MusicArtistHelper {
                    public long actualSamplesPassed;
                    public int bufferSize;
                    public long lastTimeSpoke;
                    public int sampleRate;
                    public int samplesToGive;

/* 26 */            public void onSpeak(short[] buffer, long time) {
                        if (this.lastTimeSpoke != -1) {
/* 28 */                    long durationPassed = time - this.lastTimeSpoke;
                            if (durationPassed < 20) {
                                try {
/* 31 */                            Thread.sleep(10L);
                                } catch (InterruptedException e) {
                                }
                            }
/* 36 */                    this.actualSamplesPassed = (((long) this.sampleRate) * durationPassed) / 1000;
/* 37 */                    this.samplesToGive = (int) Math.min(this.actualSamplesPassed, this.bufferSize);
                            if (this.actualSamplesPassed > this.samplesToGive) {
/* 39 */                        SLog.m456e("Audio dropped {} samples", Long.valueOf(this.actualSamplesPassed - ((long) this.samplesToGive)));
                            }
/* 41 */                    if (this.samplesToGive > buffer.length) {
/* 42 */                        throw new IllegalArgumentException("Did you pass in the wrong buffer? Sizes do not match");
                            }
                        } else {
/* 45 */                    this.actualSamplesPassed = this.bufferSize;
/* 46 */                    this.samplesToGive = this.bufferSize;
                        }
/* 48 */                this.lastTimeSpoke = time;
                    }

/* 51 */            public short[] onSetupAudio(AudioRecord audioRecord) {
/* 52 */                this.sampleRate = audioRecord.getSampleRate();
/* 53 */                int format = audioRecord.getAudioFormat();
/* 54 */                if (format != 2) {
/* 55 */                    throw new IllegalArgumentException("Only PCM 16bit is supported right now for this.");
                        }
/* 57 */                int channelCount = audioRecord.getChannelCount();
/* 58 */                if (channelCount != 1) {
/* 59 */                    throw new IllegalArgumentException("Only MONO is supported right now for this.");
                        }
                        try {
/* 62 */                    Field f = audioRecord.getClass().getDeclaredField("mNativeBufferSizeInBytes");
/* 63 */                    f.setAccessible(true);
/* 64 */                    this.bufferSize = ((Integer) f.get(audioRecord)).intValue();
                        } catch (IllegalAccessException | NoSuchFieldException e) {
/* 67 */                    this.bufferSize = 2048;
                        }
/* 69 */                this.lastTimeSpoke = -1L;
/* 70 */                return new short[this.bufferSize];
                    }
                }
            }
