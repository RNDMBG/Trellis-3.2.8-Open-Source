            package co.vine.android.util;

            import com.flurry.android.Constants;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 8 */     public class AudioUtils {
                public static final long MICROS_PER_SECOND = 1000000;
                public static final float NOISE_FLOOR = -50.0f;
                public static final float[] PCM_SHORT_TO_AMP = new float[32769];
                public static final int RAMPING_US = 110000;

                static {
                    for (int i = 0; i <= 32767; i++) {
/* 15 */                double value = 20.0d * Math.log10(i / 32767.0f);
                        if (value < -50.0d) {
                            value = -50.0d;
                        } else if (value >= 0.0d) {
                            value = 0.0d;
                        }
/* 17 */                PCM_SHORT_TO_AMP[i] = (float) value;
                    }
                }

/* 21 */        public static long presentationTimeUsToStartTimeUs(long presentationTimeUs, int size, int sampleRate, int channelCount) {
/* 22 */            return presentationTimeUs - (((long) size) / (((long) ((sampleRate * 2) * channelCount)) * MICROS_PER_SECOND));
                }

/* 25 */        public static byte[] linearCrossFade(int startIndex, int rampCount, int lastIndex, byte[] data) {
/* 26 */            int starRampEnd = (rampCount + startIndex) - 1;
/* 27 */            int endRampStart = lastIndex - rampCount;
/* 28 */            byte[] playData = new byte[(endRampStart - startIndex) + 1];
                    for (int i = startIndex; i <= starRampEnd; i += 2) {
/* 30 */                double percentage = ((double) (i - startIndex)) / ((double) rampCount);
/* 31 */                short sampleFront = (short) (((double) ((data[i] & Constants.UNKNOWN) | (data[i + 1] << 8))) * percentage);
/* 32 */                short sampleBack = (short) (((double) ((data[endRampStart + 1 + i] & Constants.UNKNOWN) | (data[(endRampStart + 2) + i] << 8))) * (1.0d - percentage));
/* 34 */                int sample = (short) (sampleBack + sampleFront);
/* 35 */                playData[i - startIndex] = (byte) (sample & 255);
/* 36 */                playData[(i - startIndex) + 1] = (byte) ((sample >> 8) & 255);
                    }
/* 38 */            System.arraycopy(data, starRampEnd + 1, playData, rampCount, endRampStart - starRampEnd);
/* 39 */            return playData;
                }

/* 42 */        public static byte[] cubicCrossFade(int startIndex, int rampCount, int lastIndex, byte[] data) {
                    short sample;
/* 43 */            int starRampEnd = (rampCount + startIndex) - 1;
/* 44 */            int endRampStart = lastIndex - rampCount;
/* 45 */            byte[] playData = new byte[(endRampStart - startIndex) + 1];
                    for (int i = startIndex; i <= starRampEnd; i += 2) {
/* 47 */                double p = ((double) (i - startIndex)) / ((double) rampCount);
/* 48 */                double frontPercentage = Math.pow(p - 1.0d, 3.0d) + 1.0d;
/* 49 */                double backPercentage = 1.0d - Math.pow(p, 3.0d);
/* 50 */                short sampleFront = (short) (((double) ((data[i] & Constants.UNKNOWN) | (data[i + 1] << 8))) * frontPercentage);
/* 51 */                short sampleBack = (short) (((double) ((data[endRampStart + 1 + i] & Constants.UNKNOWN) | (data[(endRampStart + 2) + i] << 8))) * backPercentage);
/* 52 */                int combined = sampleBack + sampleFront;
/* 53 */                if (combined > 32767) {
                            sample = Short.MAX_VALUE;
                        } else {
/* 53 */                    sample = combined < -32768 ? Short.MIN_VALUE : (short) combined;
                        }
/* 54 */                playData[i - startIndex] = (byte) (sample & 255);
/* 55 */                playData[(i - startIndex) + 1] = (byte) ((sample >> 8) & 255);
                    }
/* 57 */            System.arraycopy(data, starRampEnd + 1, playData, rampCount, endRampStart - starRampEnd);
/* 58 */            return playData;
                }
            }
