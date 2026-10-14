            package com.google.android.exoplayer;

            import android.annotation.TargetApi;
            import android.media.MediaCodecInfo;
            import android.media.MediaCodecList;
            import android.support.v4.view.accessibility.AccessibilityEventCompat;
            import android.util.Pair;
            import com.google.android.exoplayer.util.Util;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(16)
/* 45 */    public class MediaCodecUtil {
                private static final HashMap<String, Pair<MediaCodecInfo, MediaCodecInfo.CodecCapabilities>> codecs = new HashMap<>();

/* 46 */        public static DecoderInfo getDecoderInfo(String mimeType) {
/* 47 */            Pair<MediaCodecInfo, MediaCodecInfo.CodecCapabilities> info = getMediaCodecInfo(mimeType);
/* 48 */            if (info == null) {
/* 49 */                return null;
                    }
                    return new DecoderInfo(((MediaCodecInfo) info.first).getName(), isAdaptive((MediaCodecInfo.CodecCapabilities) info.second));
                }

/* 58 */        public static synchronized void warmCodecs(String[] mimeTypes) {
                    for (String str : mimeTypes) {
                        try {
/* 60 */                    getMediaCodecInfo(str);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 69 */        private static synchronized Pair<MediaCodecInfo, MediaCodecInfo.CodecCapabilities> getMediaCodecInfo(String mimeType) {
                    Pair<MediaCodecInfo, MediaCodecInfo.CodecCapabilities> pair;
                    try {
                        Pair<MediaCodecInfo, MediaCodecInfo.CodecCapabilities> result = codecs.get(mimeType);
/* 71 */                if (result != null) {
                            pair = result;
                        } else {
/* 74 */                    int numberOfCodecs = MediaCodecList.getCodecCount();
                            for (int i = 0; i < numberOfCodecs; i++) {
/* 77 */                        MediaCodecInfo info = MediaCodecList.getCodecInfoAt(i);
/* 78 */                        String codecName = info.getName();
/* 79 */                        if (!info.isEncoder() && isOmxCodec(codecName)) {
/* 80 */                            String[] supportedTypes = info.getSupportedTypes();
                                    for (String supportedType : supportedTypes) {
/* 83 */                                if (supportedType.equalsIgnoreCase(mimeType)) {
                                            try {
/* 85 */                                        Pair<MediaCodecInfo, MediaCodecInfo.CodecCapabilities> result2 = Pair.create(info, info.getCapabilitiesForType(supportedType));
/* 86 */                                        codecs.put(mimeType, result2);
                                                pair = result2;
                                            } catch (IllegalArgumentException e) {
                                            }
                                        }
                                    }
                                }
                            }
/* 97 */                    pair = null;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return pair;
                }

/* 100 */       private static boolean isOmxCodec(String name) {
/* 101 */           return name.startsWith("OMX.");
                }

/* 104 */       private static boolean isAdaptive(MediaCodecInfo.CodecCapabilities capabilities) {
/* 105 */           if (Util.SDK_INT >= 19) {
/* 106 */               return isAdaptiveV19(capabilities);
                    }
                    return false;
                }

                @TargetApi(19)
/* 113 */       private static boolean isAdaptiveV19(MediaCodecInfo.CodecCapabilities capabilities) {
/* 114 */           return capabilities.isFeatureSupported("adaptive-playback");
                }

/* 122 */       public static boolean isH264ProfileSupported(int profile, int level) {
/* 123 */           Pair<MediaCodecInfo, MediaCodecInfo.CodecCapabilities> info = getMediaCodecInfo("video/avc");
/* 124 */           if (info == null) {
                        return false;
                    }
/* 128 */           MediaCodecInfo.CodecCapabilities capabilities = (MediaCodecInfo.CodecCapabilities) info.second;
                    for (int i = 0; i < capabilities.profileLevels.length; i++) {
/* 130 */               MediaCodecInfo.CodecProfileLevel profileLevel = capabilities.profileLevels[i];
/* 131 */               if (profileLevel.profile == profile && profileLevel.level >= level) {
/* 132 */                   return true;
                        }
                    }
                    return false;
                }

/* 142 */       public static int maxH264DecodableFrameSize() {
/* 143 */           Pair<MediaCodecInfo, MediaCodecInfo.CodecCapabilities> info = getMediaCodecInfo("video/avc");
/* 144 */           if (info == null) {
/* 145 */               return 0;
                    }
/* 148 */           int maxH264DecodableFrameSize = 0;
/* 149 */           MediaCodecInfo.CodecCapabilities capabilities = (MediaCodecInfo.CodecCapabilities) info.second;
                    for (int i = 0; i < capabilities.profileLevels.length; i++) {
/* 151 */               MediaCodecInfo.CodecProfileLevel profileLevel = capabilities.profileLevels[i];
/* 152 */               maxH264DecodableFrameSize = Math.max(avcLevelToMaxFrameSize(profileLevel.level), maxH264DecodableFrameSize);
                    }
/* 156 */           return maxH264DecodableFrameSize;
                }

/* 166 */       private static int avcLevelToMaxFrameSize(int avcLevel) {
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
/* 173 */                   return 202752;
                        case 128:
                            return 414720;
                        case 256:
                            return 414720;
                        case 512:
/* 176 */                   return 921600;
                        case 1024:
/* 177 */                   return 1310720;
                        case 2048:
                            return AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_END;
                        case 4096:
                            return AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_END;
                        case 8192:
/* 180 */                   return 2228224;
                        case 16384:
/* 181 */                   return 5652480;
                        case 32768:
/* 182 */                   return 9437184;
                        default:
/* 183 */                   return -1;
                    }
                }
            }
