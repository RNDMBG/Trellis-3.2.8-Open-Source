            package com.google.android.exoplayer.util;

            import android.annotation.SuppressLint;
            import android.util.Pair;
            import com.googlecode.javacv.cpp.avcodec;
            import com.lifevibes.mediacoder.LVMediaCoderProperties;
            import java.util.ArrayList;
            import java.util.List;
            
            
    public final class CodecSpecificDataUtil {
                private static final int SPS_NAL_UNIT_TYPE = 7;
                private static final byte[] NAL_START_CODE = {0, 0, 0, 1};
                private static final int[] AUDIO_SPECIFIC_CONFIG_SAMPLING_RATE_TABLE = {LVMediaCoderProperties.AUDIO_SAMPLE_RATE_96000, LVMediaCoderProperties.AUDIO_SAMPLE_RATE_88200, 64000, LVMediaCoderProperties.AUDIO_SAMPLE_RATE_48000, 44100, LVMediaCoderProperties.AUDIO_SAMPLE_RATE_32000, LVMediaCoderProperties.AUDIO_SAMPLE_RATE_24000, LVMediaCoderProperties.AUDIO_SAMPLE_RATE_22050, LVMediaCoderProperties.AUDIO_SAMPLE_RATE_16000, LVMediaCoderProperties.AUDIO_SAMPLE_RATE_12000, LVMediaCoderProperties.AUDIO_SAMPLE_RATE_11025, 8000, LVMediaCoderProperties.AUDIO_SAMPLE_RATE_7350};
                private static final int[] AUDIO_SPECIFIC_CONFIG_CHANNEL_COUNT_TABLE = {0, 1, 2, 3, 4, 5, 6, 8};

        private CodecSpecificDataUtil() {
                }

        public static Pair<Integer, Integer> parseAudioSpecificConfig(byte[] audioSpecificConfig) {
            int audioObjectType = (audioSpecificConfig[0] >> 3) & 31;
            int byteOffset = (audioObjectType == 5 || audioObjectType == 29) ? 1 : 0;
            int frequencyIndex = ((audioSpecificConfig[byteOffset] & 7) << 1) | ((audioSpecificConfig[byteOffset + 1] >> 7) & 1);
            Assertions.checkState(frequencyIndex < 13);
            int sampleRate = AUDIO_SPECIFIC_CONFIG_SAMPLING_RATE_TABLE[frequencyIndex];
            int channelCount = (audioSpecificConfig[byteOffset + 1] >> 3) & 15;
            return Pair.create(Integer.valueOf(sampleRate), Integer.valueOf(channelCount));
                }

        public static byte[] buildAudioSpecificConfig(int audioObjectType, int sampleRateIndex, int channelConfig) {
            byte[] audioSpecificConfig = {(byte) (((audioObjectType << 3) & 248) | ((sampleRateIndex >> 1) & 7)), (byte) (((sampleRateIndex << 7) & 128) | ((channelConfig << 3) & 120))};
            return audioSpecificConfig;
                }

        public static byte[] buildAudioSpecificConfig(int sampleRate, int numChannels) {
            int sampleRateIndex = -1;
                    for (int i = 0; i < AUDIO_SPECIFIC_CONFIG_SAMPLING_RATE_TABLE.length; i++) {
                if (sampleRate == AUDIO_SPECIFIC_CONFIG_SAMPLING_RATE_TABLE[i]) {
                    sampleRateIndex = i;
                        }
                    }
            int channelConfig = -1;
                    for (int i2 = 0; i2 < AUDIO_SPECIFIC_CONFIG_CHANNEL_COUNT_TABLE.length; i2++) {
                if (numChannels == AUDIO_SPECIFIC_CONFIG_CHANNEL_COUNT_TABLE[i2]) {
                    channelConfig = i2;
                        }
                    }
            byte[] csd = {(byte) ((sampleRateIndex >> 1) | 16), (byte) (((sampleRateIndex & 1) << 7) | (channelConfig << 3))};
           return csd;
                }

       public static byte[] buildNalUnit(byte[] data, int offset, int length) {
           byte[] nalUnit = new byte[NAL_START_CODE.length + length];
           System.arraycopy(NAL_START_CODE, 0, nalUnit, 0, NAL_START_CODE.length);
           System.arraycopy(data, offset, nalUnit, NAL_START_CODE.length, length);
           return nalUnit;
                }

       public static byte[][] splitNalUnits(byte[] data) {
           if (!isNalStartCode(data, 0)) {
               return (byte[][]) null;
                    }
           List<Integer> starts = new ArrayList<>();
           int nalUnitIndex = 0;
           do {
               starts.add(Integer.valueOf(nalUnitIndex));
               nalUnitIndex = findNalStartCode(data, NAL_START_CODE.length + nalUnitIndex);
           } while (nalUnitIndex != -1);
           byte[][] split = new byte[starts.size()][];
           int i = 0;
                    while (i < starts.size()) {
               int startIndex = starts.get(i).intValue();
               int endIndex = i < starts.size() + (-1) ? starts.get(i + 1).intValue() : data.length;
               byte[] nal = new byte[endIndex - startIndex];
               System.arraycopy(data, startIndex, nal, 0, nal.length);
               split[i] = nal;
               i++;
                    }
                    return split;
                }

       private static int findNalStartCode(byte[] data, int index) {
           int endIndex = data.length - NAL_START_CODE.length;
                    for (int i = index; i <= endIndex; i++) {
               if (isNalStartCode(data, i)) {
                   return i;
                        }
                    }
           return -1;
                }

       private static boolean isNalStartCode(byte[] data, int index) {
           if (data.length - index <= NAL_START_CODE.length) {
                        return false;
                    }
                    for (int j = 0; j < NAL_START_CODE.length; j++) {
               if (data[index + j] != NAL_START_CODE[j]) {
                            return false;
                        }
                    }
           return true;
                }

       public static Pair<Integer, Integer> parseSpsNalUnit(byte[] spsNalUnit) {
           if (isNalStartCode(spsNalUnit, 0) && spsNalUnit.length == 8 && (spsNalUnit[5] & 31) == 7) {
               return Pair.create(Integer.valueOf(parseAvcProfile(spsNalUnit)), Integer.valueOf(parseAvcLevel(spsNalUnit)));
                    }
                    return null;
                }

                @SuppressLint({"InlinedApi"})
       private static int parseAvcProfile(byte[] data) {
           int profileIdc = data[6] & 255;
                    switch (profileIdc) {
                        case 66:
                   return 1;
                        case 77:
                   return 2;
                        case 88:
                   return 4;
                        case 100:
                   return 8;
                        case 110:
                   return 16;
                        case 122:
                   return 32;
                        case avcodec.AVCodecContext.FF_PROFILE_H264_HIGH_444_PREDICTIVE :
                   return 64;
                        default:
                   return 0;
                    }
                }

                @SuppressLint({"InlinedApi"})
       private static int parseAvcLevel(byte[] data) {
           int levelIdc = data[8] & 255;
                    switch (levelIdc) {
                        case 9:
                   return 2;
                        case 10:
                   return 1;
                        case 11:
                   return 4;
                        case 12:
                            return 8;
                        case 13:
                   return 16;
                        case 20:
                   return 32;
                        case 21:
                   return 64;
                        case 22:
                   return 128;
                        case 30:
                   return 256;
                        case 31:
                   return 512;
                        case 32:
                   return 1024;
                        case 40:
                   return 2048;
                        case 41:
                   return 4096;
                        case 42:
                   return 8192;
                        case 50:
                   return 16384;
                        case 51:
                   return 32768;
                        default:
                   return 0;
                    }
                }
            }
