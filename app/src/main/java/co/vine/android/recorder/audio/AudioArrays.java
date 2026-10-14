            package co.vine.android.recorder.audio;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 11 */    public class AudioArrays {

                public enum AudioArrayType {
                    BYTE,
                    SHORT
                }

/* 12 */        private AudioArrays() {
                }

/* 17 */        public static AudioArray wrap(Object audioData) {
/* 18 */            if (audioData instanceof byte[]) {
/* 19 */                return new AudioByteArray((byte[]) audioData);
                    }
/* 20 */            if (audioData instanceof short[]) {
/* 21 */                return new AudioShortArray((short[]) audioData);
                    }
/* 23 */            throw new IllegalArgumentException("type invalid: " + audioData);
                }

/* 27 */        public static AudioArrayType typeOf(AudioArray audioArray) {
/* 28 */            if (audioArray instanceof AudioByteArray) {
/* 29 */                return AudioArrayType.BYTE;
                    }
/* 30 */            if (audioArray instanceof AudioShortArray) {
/* 31 */                return AudioArrayType.SHORT;
                    }
/* 33 */            throw new IllegalArgumentException("type unknown: " + audioArray);
                }

/* 37 */        public static AudioArray newInstance(int audioRunnableSampleSize, AudioArrayType type) {
/* 38 */            if (type == AudioArrayType.SHORT) {
/* 39 */                return new AudioShortArray(audioRunnableSampleSize);
                    }
/* 40 */            if (type == AudioArrayType.BYTE) {
/* 41 */                return new AudioByteArray(audioRunnableSampleSize);
                    }
/* 43 */            throw new IllegalArgumentException("type invalid: " + type);
                }
            }
