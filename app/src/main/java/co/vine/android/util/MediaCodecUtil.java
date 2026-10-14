            package co.vine.android.util;

            import android.annotation.TargetApi;
            import android.media.MediaCodecInfo;
            import android.media.MediaCodecList;
            import android.os.Build;
            import android.util.Pair;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 40 */    public class MediaCodecUtil {
                public static final int SDK_INT = Build.VERSION.SDK_INT;
                private static final HashMap<String, Pair<String, MediaCodecInfo.CodecCapabilities>> codecs = new HashMap<>();

                private interface MediaCodecListCompat {
                    int getCodecCount();

                    MediaCodecInfo getCodecInfoAt(int i);

                    boolean secureDecodersExplicit();
                }

/* 41 */        public static String getDecoderInfo(String mimeType) {
/* 42 */            Pair<String, MediaCodecInfo.CodecCapabilities> info = getMediaCodecInfo(mimeType);
/* 43 */            if (info == null) {
/* 44 */                return null;
                    }
                    return (String) info.first;
                }

/* 53 */        private static synchronized Pair<String, MediaCodecInfo.CodecCapabilities> getMediaCodecInfo(String mimeType) {
                    Pair<String, MediaCodecInfo.CodecCapabilities> mediaCodecInfo;
                    try {
                        if (codecs.containsKey(mimeType)) {
/* 55 */                    mediaCodecInfo = codecs.get(mimeType);
                        } else {
                            mediaCodecInfo = getMediaCodecInfo(mimeType, SDK_INT >= 21 ? new MediaCodecListCompatV21(false) : new MediaCodecListCompatV16(null));
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return mediaCodecInfo;
                }

/* 62 */        private static Pair<String, MediaCodecInfo.CodecCapabilities> getMediaCodecInfo(String mimeType, MediaCodecListCompat mediaCodecList) {
/* 63 */            int numberOfCodecs = mediaCodecList.getCodecCount();
/* 64 */            boolean secureDecodersExplicit = mediaCodecList.secureDecodersExplicit();
                    for (int i = 0; i < numberOfCodecs; i++) {
/* 67 */                MediaCodecInfo info = mediaCodecList.getCodecInfoAt(i);
/* 68 */                String codecName = info.getName();
/* 69 */                if (!info.isEncoder() && codecName.startsWith("OMX.") && (secureDecodersExplicit || !codecName.endsWith(".secure"))) {
/* 71 */                    String[] supportedTypes = info.getSupportedTypes();
                            for (String supportedType : supportedTypes) {
/* 73 */                        if (supportedType.equalsIgnoreCase(mimeType)) {
/* 74 */                            MediaCodecInfo.CodecCapabilities capabilities = info.getCapabilitiesForType(supportedType);
/* 75 */                            Pair<String, MediaCodecInfo.CodecCapabilities> pair = Pair.create(codecName, capabilities);
/* 76 */                            codecs.put(mimeType, pair);
/* 82 */                            return pair;
                                }
                            }
                        }
                    }
/* 82 */            return null;
                }

                @TargetApi(21)
/* 110 */       private static final class MediaCodecListCompatV21 implements MediaCodecListCompat {
                    private final MediaCodecInfo[] mediaCodecInfos;

/* 111 */           public MediaCodecListCompatV21(boolean includeSecure) {
/* 113 */               int codecKind = includeSecure ? 1 : 0;
/* 114 */               this.mediaCodecInfos = new MediaCodecList(codecKind).getCodecInfos();
                    }

                    @Override // co.vine.android.util.MediaCodecUtil.MediaCodecListCompat
/* 118 */           public int getCodecCount() {
/* 119 */               return this.mediaCodecInfos.length;
                    }

                    @Override // co.vine.android.util.MediaCodecUtil.MediaCodecListCompat
/* 123 */           public MediaCodecInfo getCodecInfoAt(int index) {
/* 124 */               return this.mediaCodecInfos[index];
                    }

                    @Override // co.vine.android.util.MediaCodecUtil.MediaCodecListCompat
/* 128 */           public boolean secureDecodersExplicit() {
/* 129 */               return true;
                    }
                }

/* 133 */       private static final class MediaCodecListCompatV16 implements MediaCodecListCompat {
/* 134 */           private MediaCodecListCompatV16() {
                    }

/* 134 */           /* synthetic */ MediaCodecListCompatV16(Object x0) {
/* 135 */               this();
                    }

                    @Override // co.vine.android.util.MediaCodecUtil.MediaCodecListCompat
/* 138 */           public int getCodecCount() {
/* 139 */               return MediaCodecList.getCodecCount();
                    }

                    @Override // co.vine.android.util.MediaCodecUtil.MediaCodecListCompat
/* 143 */           public MediaCodecInfo getCodecInfoAt(int index) {
/* 144 */               return MediaCodecList.getCodecInfoAt(index);
                    }

                    @Override // co.vine.android.util.MediaCodecUtil.MediaCodecListCompat
/* 148 */           public boolean secureDecodersExplicit() {
/* 149 */               return false;
                    }
                }
            }
