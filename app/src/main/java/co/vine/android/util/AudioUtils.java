            package co.vine.android.util;

            import com.flurry.android.Constants;
            
            
     public class AudioUtils {
                public static final long MICROS_PER_SECOND = 1000000;
                public static final float NOISE_FLOOR = -50.0f;
                public static final float[] PCM_SHORT_TO_AMP = new float[32769];
                public static final int RAMPING_US = 110000;

                static {
                    for (int i = 0; i <= 32767; i++) {
                double value = 20.0d * Math.log10(i / 32767.0f);
                        if (value < -50.0d) {
                            value = -50.0d;
                        } else if (value >= 0.0d) {
                            value = 0.0d;
                        }
                PCM_SHORT_TO_AMP[i] = (float) value;
                    }
                }

        public static long presentationTimeUsToStartTimeUs(long presentationTimeUs, int size, int sampleRate, int channelCount) {
            return presentationTimeUs - (((long) size) / (((long) ((sampleRate * 2) * channelCount)) * MICROS_PER_SECOND));
                }

        public static byte[] linearCrossFade(int startIndex, int rampCount, int lastIndex, byte[] data) {
            int starRampEnd = (rampCount + startIndex) - 1;
            int endRampStart = lastIndex - rampCount;
            byte[] playData = new byte[(endRampStart - startIndex) + 1];
                    for (int i = startIndex; i <= starRampEnd; i += 2) {
                double percentage = ((double) (i - startIndex)) / ((double) rampCount);
                short sampleFront = (short) (((double) ((data[i] & Constants.UNKNOWN) | (data[i + 1] << 8))) * percentage);
                short sampleBack = (short) (((double) ((data[endRampStart + 1 + i] & Constants.UNKNOWN) | (data[(endRampStart + 2) + i] << 8))) * (1.0d - percentage));
                int sample = (short) (sampleBack + sampleFront);
                playData[i - startIndex] = (byte) (sample & 255);
                playData[(i - startIndex) + 1] = (byte) ((sample >> 8) & 255);
                    }
            System.arraycopy(data, starRampEnd + 1, playData, rampCount, endRampStart - starRampEnd);
            return playData;
                }

        public static byte[] cubicCrossFade(int startIndex, int rampCount, int lastIndex, byte[] data) {
                    short sample;
            int starRampEnd = (rampCount + startIndex) - 1;
            int endRampStart = lastIndex - rampCount;
            byte[] playData = new byte[(endRampStart - startIndex) + 1];
                    for (int i = startIndex; i <= starRampEnd; i += 2) {
                double p = ((double) (i - startIndex)) / ((double) rampCount);
                double frontPercentage = Math.pow(p - 1.0d, 3.0d) + 1.0d;
                double backPercentage = 1.0d - Math.pow(p, 3.0d);
                short sampleFront = (short) (((double) ((data[i] & Constants.UNKNOWN) | (data[i + 1] << 8))) * frontPercentage);
                short sampleBack = (short) (((double) ((data[endRampStart + 1 + i] & Constants.UNKNOWN) | (data[(endRampStart + 2) + i] << 8))) * backPercentage);
                int combined = sampleBack + sampleFront;
                if (combined > 32767) {
                            sample = Short.MAX_VALUE;
                        } else {
                    sample = combined < -32768 ? Short.MIN_VALUE : (short) combined;
                        }
                playData[i - startIndex] = (byte) (sample & 255);
                playData[(i - startIndex) + 1] = (byte) ((sample >> 8) & 255);
                    }
            System.arraycopy(data, starRampEnd + 1, playData, rampCount, endRampStart - starRampEnd);
            return playData;
                }
            }
