            package com.google.android.exoplayer.util;

            import android.annotation.SuppressLint;
            import android.util.Pair;
            import com.googlecode.javacv.cpp.avcodec;
            import com.lifevibes.mediacoder.LVMediaCoderProperties;
            import java.util.ArrayList;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 40 */    public final class CodecSpecificDataUtil {
                private static final int SPS_NAL_UNIT_TYPE = 7;
                private static final byte[] NAL_START_CODE = {0, 0, 0, 1};
                private static final int[] AUDIO_SPECIFIC_CONFIG_SAMPLING_RATE_TABLE = {LVMediaCoderProperties.AUDIO_SAMPLE_RATE_96000, LVMediaCoderProperties.AUDIO_SAMPLE_RATE_88200, 64000, LVMediaCoderProperties.AUDIO_SAMPLE_RATE_48000, 44100, LVMediaCoderProperties.AUDIO_SAMPLE_RATE_32000, LVMediaCoderProperties.AUDIO_SAMPLE_RATE_24000, LVMediaCoderProperties.AUDIO_SAMPLE_RATE_22050, LVMediaCoderProperties.AUDIO_SAMPLE_RATE_16000, LVMediaCoderProperties.AUDIO_SAMPLE_RATE_12000, LVMediaCoderProperties.AUDIO_SAMPLE_RATE_11025, 8000, LVMediaCoderProperties.AUDIO_SAMPLE_RATE_7350};
                private static final int[] AUDIO_SPECIFIC_CONFIG_CHANNEL_COUNT_TABLE = {0, 1, 2, 3, 4, 5, 6, 8};

/* 41 */        private CodecSpecificDataUtil() {
                }

/* 50 */        public static Pair<Integer, Integer> parseAudioSpecificConfig(byte[] audioSpecificConfig) {
/* 51 */            int audioObjectType = (audioSpecificConfig[0] >> 3) & 31;
/* 52 */            int byteOffset = (audioObjectType == 5 || audioObjectType == 29) ? 1 : 0;
/* 53 */            int frequencyIndex = ((audioSpecificConfig[byteOffset] & 7) << 1) | ((audioSpecificConfig[byteOffset + 1] >> 7) & 1);
/* 55 */            Assertions.checkState(frequencyIndex < 13);
/* 56 */            int sampleRate = AUDIO_SPECIFIC_CONFIG_SAMPLING_RATE_TABLE[frequencyIndex];
/* 57 */            int channelCount = (audioSpecificConfig[byteOffset + 1] >> 3) & 15;
/* 58 */            return Pair.create(Integer.valueOf(sampleRate), Integer.valueOf(channelCount));
                }

/* 70 */        public static byte[] buildAudioSpecificConfig(int audioObjectType, int sampleRateIndex, int channelConfig) {
/* 71 */            byte[] audioSpecificConfig = {(byte) (((audioObjectType << 3) & 248) | ((sampleRateIndex >> 1) & 7)), (byte) (((sampleRateIndex << 7) & 128) | ((channelConfig << 3) & 120))};
/* 74 */            return audioSpecificConfig;
                }

/* 84 */        public static byte[] buildAudioSpecificConfig(int sampleRate, int numChannels) {
/* 85 */            int sampleRateIndex = -1;
                    for (int i = 0; i < AUDIO_SPECIFIC_CONFIG_SAMPLING_RATE_TABLE.length; i++) {
/* 87 */                if (sampleRate == AUDIO_SPECIFIC_CONFIG_SAMPLING_RATE_TABLE[i]) {
/* 88 */                    sampleRateIndex = i;
                        }
                    }
/* 91 */            int channelConfig = -1;
                    for (int i2 = 0; i2 < AUDIO_SPECIFIC_CONFIG_CHANNEL_COUNT_TABLE.length; i2++) {
/* 93 */                if (numChannels == AUDIO_SPECIFIC_CONFIG_CHANNEL_COUNT_TABLE[i2]) {
/* 94 */                    channelConfig = i2;
                        }
                    }
/* 98 */            byte[] csd = {(byte) ((sampleRateIndex >> 1) | 16), (byte) (((sampleRateIndex & 1) << 7) | (channelConfig << 3))};
/* 101 */           return csd;
                }

/* 112 */       public static byte[] buildNalUnit(byte[] data, int offset, int length) {
/* 113 */           byte[] nalUnit = new byte[NAL_START_CODE.length + length];
/* 114 */           System.arraycopy(NAL_START_CODE, 0, nalUnit, 0, NAL_START_CODE.length);
/* 115 */           System.arraycopy(data, offset, nalUnit, NAL_START_CODE.length, length);
/* 116 */           return nalUnit;
                }

/* 130 */       public static byte[][] splitNalUnits(byte[] data) {
/* 131 */           if (!isNalStartCode(data, 0)) {
/* 133 */               return (byte[][]) null;
                    }
/* 135 */           List<Integer> starts = new ArrayList<>();
/* 136 */           int nalUnitIndex = 0;
/* 140 */           do {
/* 138 */               starts.add(Integer.valueOf(nalUnitIndex));
/* 139 */               nalUnitIndex = findNalStartCode(data, NAL_START_CODE.length + nalUnitIndex);
/* 140 */           } while (nalUnitIndex != -1);
/* 141 */           byte[][] split = new byte[starts.size()][];
/* 142 */           int i = 0;
                    while (i < starts.size()) {
/* 143 */               int startIndex = starts.get(i).intValue();
/* 144 */               int endIndex = i < starts.size() + (-1) ? starts.get(i + 1).intValue() : data.length;
/* 145 */               byte[] nal = new byte[endIndex - startIndex];
/* 146 */               System.arraycopy(data, startIndex, nal, 0, nal.length);
/* 147 */               split[i] = nal;
/* 142 */               i++;
                    }
                    return split;
                }

/* 159 */       private static int findNalStartCode(byte[] data, int index) {
/* 160 */           int endIndex = data.length - NAL_START_CODE.length;
                    for (int i = index; i <= endIndex; i++) {
/* 162 */               if (isNalStartCode(data, i)) {
/* 166 */                   return i;
                        }
                    }
/* 166 */           return -1;
                }

/* 176 */       private static boolean isNalStartCode(byte[] data, int index) {
/* 177 */           if (data.length - index <= NAL_START_CODE.length) {
                        return false;
                    }
                    for (int j = 0; j < NAL_START_CODE.length; j++) {
/* 181 */               if (data[index + j] != NAL_START_CODE[j]) {
                            return false;
                        }
                    }
/* 185 */           return true;
                }

/* 204 */       public static Pair<Integer, Integer> parseSpsNalUnit(byte[] spsNalUnit) {
/* 205 */           if (isNalStartCode(spsNalUnit, 0) && spsNalUnit.length == 8 && (spsNalUnit[5] & 31) == 7) {
/* 207 */               return Pair.create(Integer.valueOf(parseAvcProfile(spsNalUnit)), Integer.valueOf(parseAvcLevel(spsNalUnit)));
                    }
                    return null;
                }

                @SuppressLint({"InlinedApi"})
/* 213 */       private static int parseAvcProfile(byte[] data) {
/* 214 */           int profileIdc = data[6] & 255;
                    switch (profileIdc) {
                        case 66:
/* 217 */                   return 1;
                        case 77:
/* 219 */                   return 2;
                        case 88:
/* 221 */                   return 4;
                        case 100:
/* 223 */                   return 8;
                        case 110:
/* 225 */                   return 16;
                        case 122:
/* 227 */                   return 32;
                        case avcodec.AVCodecContext.FF_PROFILE_H264_HIGH_444_PREDICTIVE /* 244 */:
/* 229 */                   return 64;
                        default:
/* 231 */                   return 0;
                    }
                }

                @SuppressLint({"InlinedApi"})
/* 236 */       private static int parseAvcLevel(byte[] data) {
/* 237 */           int levelIdc = data[8] & 255;
                    switch (levelIdc) {
                        case 9:
/* 240 */                   return 2;
                        case 10:
/* 242 */                   return 1;
                        case 11:
/* 244 */                   return 4;
                        case 12:
                            return 8;
                        case 13:
/* 248 */                   return 16;
                        case 20:
/* 250 */                   return 32;
                        case 21:
/* 252 */                   return 64;
                        case 22:
/* 254 */                   return 128;
                        case 30:
/* 256 */                   return 256;
                        case 31:
/* 258 */                   return 512;
                        case 32:
/* 260 */                   return 1024;
                        case 40:
/* 262 */                   return 2048;
                        case 41:
/* 264 */                   return 4096;
                        case 42:
/* 266 */                   return 8192;
                        case 50:
/* 268 */                   return 16384;
                        case 51:
/* 270 */                   return 32768;
                        default:
/* 272 */                   return 0;
                    }
                }
            }
