            package com.google.android.exoplayer.parser.mp4;

            import android.annotation.SuppressLint;
            import android.util.Pair;
            import com.google.android.exoplayer.util.Assertions;
            import com.googlecode.javacv.cpp.avcodec;
            import com.lifevibes.mediacoder.LVMediaCoderProperties;
            import java.util.ArrayList;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 38 */    public final class CodecSpecificDataUtil {
                private static final int SPS_NAL_UNIT_TYPE = 7;
                private static final byte[] NAL_START_CODE = {0, 0, 0, 1};
                private static final int[] AUDIO_SPECIFIC_CONFIG_SAMPLING_RATE_TABLE = {LVMediaCoderProperties.AUDIO_SAMPLE_RATE_96000, LVMediaCoderProperties.AUDIO_SAMPLE_RATE_88200, 64000, LVMediaCoderProperties.AUDIO_SAMPLE_RATE_48000, 44100, LVMediaCoderProperties.AUDIO_SAMPLE_RATE_32000, LVMediaCoderProperties.AUDIO_SAMPLE_RATE_24000, LVMediaCoderProperties.AUDIO_SAMPLE_RATE_22050, LVMediaCoderProperties.AUDIO_SAMPLE_RATE_16000, LVMediaCoderProperties.AUDIO_SAMPLE_RATE_12000, LVMediaCoderProperties.AUDIO_SAMPLE_RATE_11025, 8000, LVMediaCoderProperties.AUDIO_SAMPLE_RATE_7350};

/* 39 */        private CodecSpecificDataUtil() {
                }

/* 48 */        public static Pair<Integer, Integer> parseAudioSpecificConfig(byte[] audioSpecificConfig) {
/* 49 */            int audioObjectType = (audioSpecificConfig[0] >> 3) & 31;
/* 50 */            int byteOffset = (audioObjectType == 5 || audioObjectType == 29) ? 1 : 0;
/* 51 */            int frequencyIndex = ((audioSpecificConfig[byteOffset] & 7) << 1) | ((audioSpecificConfig[byteOffset + 1] >> 7) & 1);
/* 53 */            Assertions.checkState(frequencyIndex < 13);
/* 54 */            int sampleRate = AUDIO_SPECIFIC_CONFIG_SAMPLING_RATE_TABLE[frequencyIndex];
/* 55 */            int channelCount = (audioSpecificConfig[byteOffset + 1] >> 3) & 15;
/* 56 */            return Pair.create(Integer.valueOf(sampleRate), Integer.valueOf(channelCount));
                }

/* 66 */        public static byte[] buildAudioSpecificConfig(int sampleRate, int numChannels) {
/* 67 */            int sampleRateIndex = -1;
                    for (int i = 0; i < AUDIO_SPECIFIC_CONFIG_SAMPLING_RATE_TABLE.length; i++) {
/* 69 */                if (sampleRate == AUDIO_SPECIFIC_CONFIG_SAMPLING_RATE_TABLE[i]) {
/* 70 */                    sampleRateIndex = i;
                        }
                    }
/* 74 */            byte[] csd = {(byte) ((sampleRateIndex >> 1) | 16), (byte) (((sampleRateIndex & 1) << 7) | (numChannels << 3))};
/* 77 */            return csd;
                }

/* 88 */        public static byte[] buildNalUnit(byte[] data, int offset, int length) {
/* 89 */            byte[] nalUnit = new byte[NAL_START_CODE.length + length];
/* 90 */            System.arraycopy(NAL_START_CODE, 0, nalUnit, 0, NAL_START_CODE.length);
/* 91 */            System.arraycopy(data, offset, nalUnit, NAL_START_CODE.length, length);
/* 92 */            return nalUnit;
                }

/* 106 */       public static byte[][] splitNalUnits(byte[] data) {
/* 107 */           if (!isNalStartCode(data, 0)) {
/* 109 */               return (byte[][]) null;
                    }
/* 111 */           List<Integer> starts = new ArrayList<>();
/* 112 */           int nalUnitIndex = 0;
/* 116 */           do {
/* 114 */               starts.add(Integer.valueOf(nalUnitIndex));
/* 115 */               nalUnitIndex = findNalStartCode(data, NAL_START_CODE.length + nalUnitIndex);
/* 116 */           } while (nalUnitIndex != -1);
/* 117 */           byte[][] split = new byte[starts.size()][];
/* 118 */           int i = 0;
                    while (i < starts.size()) {
/* 119 */               int startIndex = starts.get(i).intValue();
/* 120 */               int endIndex = i < starts.size() + (-1) ? starts.get(i + 1).intValue() : data.length;
/* 121 */               byte[] nal = new byte[endIndex - startIndex];
/* 122 */               System.arraycopy(data, startIndex, nal, 0, nal.length);
/* 123 */               split[i] = nal;
/* 118 */               i++;
                    }
                    return split;
                }

/* 135 */       private static int findNalStartCode(byte[] data, int index) {
/* 136 */           int endIndex = data.length - NAL_START_CODE.length;
                    for (int i = index; i <= endIndex; i++) {
/* 138 */               if (isNalStartCode(data, i)) {
/* 142 */                   return i;
                        }
                    }
/* 142 */           return -1;
                }

/* 152 */       private static boolean isNalStartCode(byte[] data, int index) {
/* 153 */           if (data.length - index <= NAL_START_CODE.length) {
                        return false;
                    }
                    for (int j = 0; j < NAL_START_CODE.length; j++) {
/* 157 */               if (data[index + j] != NAL_START_CODE[j]) {
                            return false;
                        }
                    }
/* 161 */           return true;
                }

/* 180 */       public static Pair<Integer, Integer> parseSpsNalUnit(byte[] spsNalUnit) {
/* 181 */           if (isNalStartCode(spsNalUnit, 0) && spsNalUnit.length == 8 && (spsNalUnit[5] & 31) == 7) {
/* 183 */               return Pair.create(Integer.valueOf(parseAvcProfile(spsNalUnit)), Integer.valueOf(parseAvcLevel(spsNalUnit)));
                    }
                    return null;
                }

                @SuppressLint({"InlinedApi"})
/* 189 */       private static int parseAvcProfile(byte[] data) {
/* 190 */           int profileIdc = data[6] & 255;
                    switch (profileIdc) {
                        case 66:
/* 193 */                   return 1;
                        case 77:
/* 195 */                   return 2;
                        case 88:
/* 197 */                   return 4;
                        case 100:
/* 199 */                   return 8;
                        case 110:
/* 201 */                   return 16;
                        case 122:
/* 203 */                   return 32;
                        case avcodec.AVCodecContext.FF_PROFILE_H264_HIGH_444_PREDICTIVE /* 244 */:
/* 205 */                   return 64;
                        default:
/* 207 */                   return 0;
                    }
                }

                @SuppressLint({"InlinedApi"})
/* 212 */       private static int parseAvcLevel(byte[] data) {
/* 213 */           int levelIdc = data[8] & 255;
                    switch (levelIdc) {
                        case 9:
/* 216 */                   return 2;
                        case 10:
/* 218 */                   return 1;
                        case 11:
/* 220 */                   return 4;
                        case 12:
                            return 8;
                        case 13:
/* 224 */                   return 16;
                        case 20:
/* 226 */                   return 32;
                        case 21:
/* 228 */                   return 64;
                        case 22:
/* 230 */                   return 128;
                        case 30:
/* 232 */                   return 256;
                        case 31:
/* 234 */                   return 512;
                        case 32:
/* 236 */                   return 1024;
                        case 40:
/* 238 */                   return 2048;
                        case 41:
/* 240 */                   return 4096;
                        case 42:
/* 242 */                   return 8192;
                        case 50:
/* 244 */                   return 16384;
                        case 51:
/* 246 */                   return 32768;
                        default:
/* 248 */                   return 0;
                    }
                }
            }
