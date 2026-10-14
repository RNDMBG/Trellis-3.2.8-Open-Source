            package com.google.android.exoplayer;

            import android.annotation.TargetApi;
            import android.media.MediaCodecInfo;
            import android.media.MediaCodecList;
            import android.support.v4.view.accessibility.AccessibilityEventCompat;
            import android.util.Pair;
            import com.google.android.exoplayer.util.Util;
            import java.util.HashMap;
            
            
            @TargetApi(16)
    public class MediaCodecUtil {
                private static final HashMap<String, Pair<MediaCodecInfo, MediaCodecInfo.CodecCapabilities>> codecs = new HashMap<>();

        public static DecoderInfo getDecoderInfo(String mimeType) {
            Pair<MediaCodecInfo, MediaCodecInfo.CodecCapabilities> info = getMediaCodecInfo(mimeType);
            if (info == null) {
                return null;
                    }
                    return new DecoderInfo(((MediaCodecInfo) info.first).getName(), isAdaptive((MediaCodecInfo.CodecCapabilities) info.second));
                }

        public static synchronized void warmCodecs(String[] mimeTypes) {
                    for (String str : mimeTypes) {
                        try {
                    getMediaCodecInfo(str);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

        private static synchronized Pair<MediaCodecInfo, MediaCodecInfo.CodecCapabilities> getMediaCodecInfo(String mimeType) {
                    Pair<MediaCodecInfo, MediaCodecInfo.CodecCapabilities> pair;
                    try {
                        Pair<MediaCodecInfo, MediaCodecInfo.CodecCapabilities> result = codecs.get(mimeType);
                if (result != null) {
                            pair = result;
                        } else {
                    int numberOfCodecs = MediaCodecList.getCodecCount();
                            for (int i = 0; i < numberOfCodecs; i++) {
                        MediaCodecInfo info = MediaCodecList.getCodecInfoAt(i);
                        String codecName = info.getName();
                        if (!info.isEncoder() && isOmxCodec(codecName)) {
                            String[] supportedTypes = info.getSupportedTypes();
                                    for (String supportedType : supportedTypes) {
                                if (supportedType.equalsIgnoreCase(mimeType)) {
                                            try {
                                        Pair<MediaCodecInfo, MediaCodecInfo.CodecCapabilities> result2 = Pair.create(info, info.getCapabilitiesForType(supportedType));
                                        codecs.put(mimeType, result2);
                                                pair = result2;
                                            } catch (IllegalArgumentException e) {
                                            }
                                        }
                                    }
                                }
                            }
                    pair = null;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return pair;
                }

       private static boolean isOmxCodec(String name) {
           return name.startsWith("OMX.");
                }

       private static boolean isAdaptive(MediaCodecInfo.CodecCapabilities capabilities) {
           if (Util.SDK_INT >= 19) {
               return isAdaptiveV19(capabilities);
                    }
                    return false;
                }

                @TargetApi(19)
       private static boolean isAdaptiveV19(MediaCodecInfo.CodecCapabilities capabilities) {
           return capabilities.isFeatureSupported("adaptive-playback");
                }

       public static boolean isH264ProfileSupported(int profile, int level) {
           Pair<MediaCodecInfo, MediaCodecInfo.CodecCapabilities> info = getMediaCodecInfo("video/avc");
           if (info == null) {
                        return false;
                    }
           MediaCodecInfo.CodecCapabilities capabilities = (MediaCodecInfo.CodecCapabilities) info.second;
                    for (int i = 0; i < capabilities.profileLevels.length; i++) {
               MediaCodecInfo.CodecProfileLevel profileLevel = capabilities.profileLevels[i];
               if (profileLevel.profile == profile && profileLevel.level >= level) {
                   return true;
                        }
                    }
                    return false;
                }

       public static int maxH264DecodableFrameSize() {
           Pair<MediaCodecInfo, MediaCodecInfo.CodecCapabilities> info = getMediaCodecInfo("video/avc");
           if (info == null) {
               return 0;
                    }
           int maxH264DecodableFrameSize = 0;
           MediaCodecInfo.CodecCapabilities capabilities = (MediaCodecInfo.CodecCapabilities) info.second;
                    for (int i = 0; i < capabilities.profileLevels.length; i++) {
               MediaCodecInfo.CodecProfileLevel profileLevel = capabilities.profileLevels[i];
               maxH264DecodableFrameSize = Math.max(avcLevelToMaxFrameSize(profileLevel.level), maxH264DecodableFrameSize);
                    }
           return maxH264DecodableFrameSize;
                }

       private static int avcLevelToMaxFrameSize(int avcLevel) {
                    switch (avcLevel) {
                        case 1:
                        case 2:
                            return 25344;
                        case 8:
                            return 101376;
                        case 16:
                            return 101376;
                        case 32:
                            return 101376;
                        case 64:
                   return 202752;
                        case 128:
                            return 414720;
                        case 256:
                            return 414720;
                        case 512:
                   return 921600;
                        case 1024:
                   return 1310720;
                        case 2048:
                            return AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_END;
                        case 4096:
                            return AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_END;
                        case 8192:
                   return 2228224;
                        case 16384:
                   return 5652480;
                        case 32768:
                   return 9437184;
                        default:
                   return -1;
                    }
                }
            }
