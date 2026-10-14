            package co.vine.android.mock.artists;

            import android.media.AudioRecord;
            import com.edisonwang.android.slog.SLog;
            import java.lang.reflect.Field;
            
            
    public interface MockMusicArtist {
                short[] setupAudio(AudioRecord audioRecord);

                int speak(short[] sArr, long j);

        public static class MusicArtistHelper {
                    public long actualSamplesPassed;
                    public int bufferSize;
                    public long lastTimeSpoke;
                    public int sampleRate;
                    public int samplesToGive;

            public void onSpeak(short[] buffer, long time) {
                        if (this.lastTimeSpoke != -1) {
                    long durationPassed = time - this.lastTimeSpoke;
                            if (durationPassed < 20) {
                                try {
                            Thread.sleep(10L);
                                } catch (InterruptedException e) {
                                }
                            }
                    this.actualSamplesPassed = (((long) this.sampleRate) * durationPassed) / 1000;
                    this.samplesToGive = (int) Math.min(this.actualSamplesPassed, this.bufferSize);
                            if (this.actualSamplesPassed > this.samplesToGive) {
                        SLog.m456e("Audio dropped {} samples", Long.valueOf(this.actualSamplesPassed - ((long) this.samplesToGive)));
                            }
                    if (this.samplesToGive > buffer.length) {
                        throw new IllegalArgumentException("Did you pass in the wrong buffer? Sizes do not match");
                            }
                        } else {
                    this.actualSamplesPassed = this.bufferSize;
                    this.samplesToGive = this.bufferSize;
                        }
                this.lastTimeSpoke = time;
                    }

            public short[] onSetupAudio(AudioRecord audioRecord) {
                this.sampleRate = audioRecord.getSampleRate();
                int format = audioRecord.getAudioFormat();
                if (format != 2) {
                    throw new IllegalArgumentException("Only PCM 16bit is supported right now for this.");
                        }
                int channelCount = audioRecord.getChannelCount();
                if (channelCount != 1) {
                    throw new IllegalArgumentException("Only MONO is supported right now for this.");
                        }
                        try {
                    Field f = audioRecord.getClass().getDeclaredField("mNativeBufferSizeInBytes");
                    f.setAccessible(true);
                    this.bufferSize = ((Integer) f.get(audioRecord)).intValue();
                        } catch (IllegalAccessException | NoSuchFieldException e) {
                    this.bufferSize = 2048;
                        }
                this.lastTimeSpoke = -1L;
                return new short[this.bufferSize];
                    }
                }
            }
