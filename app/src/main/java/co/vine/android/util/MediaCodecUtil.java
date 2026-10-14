            package co.vine.android.util;

            import android.annotation.TargetApi;
            import android.media.MediaCodecInfo;
            import android.media.MediaCodecList;
            import android.os.Build;
            import android.util.Pair;
            import java.util.HashMap;
            
            
    public class MediaCodecUtil {
                public static final int SDK_INT = Build.VERSION.SDK_INT;
                private static final HashMap<String, Pair<String, MediaCodecInfo.CodecCapabilities>> codecs = new HashMap<>();

                private interface MediaCodecListCompat {
                    int getCodecCount();

                    MediaCodecInfo getCodecInfoAt(int i);

                    boolean secureDecodersExplicit();
                }

        public static String getDecoderInfo(String mimeType) {
            Pair<String, MediaCodecInfo.CodecCapabilities> info = getMediaCodecInfo(mimeType);
            if (info == null) {
                return null;
                    }
                    return (String) info.first;
                }

        private static synchronized Pair<String, MediaCodecInfo.CodecCapabilities> getMediaCodecInfo(String mimeType) {
                    Pair<String, MediaCodecInfo.CodecCapabilities> mediaCodecInfo;
                    try {
                        if (codecs.containsKey(mimeType)) {
                    mediaCodecInfo = codecs.get(mimeType);
                        } else {
                            mediaCodecInfo = getMediaCodecInfo(mimeType, SDK_INT >= 21 ? new MediaCodecListCompatV21(false) : new MediaCodecListCompatV16(null));
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return mediaCodecInfo;
                }

        private static Pair<String, MediaCodecInfo.CodecCapabilities> getMediaCodecInfo(String mimeType, MediaCodecListCompat mediaCodecList) {
            int numberOfCodecs = mediaCodecList.getCodecCount();
            boolean secureDecodersExplicit = mediaCodecList.secureDecodersExplicit();
                    for (int i = 0; i < numberOfCodecs; i++) {
                MediaCodecInfo info = mediaCodecList.getCodecInfoAt(i);
                String codecName = info.getName();
                if (!info.isEncoder() && codecName.startsWith("OMX.") && (secureDecodersExplicit || !codecName.endsWith(".secure"))) {
                    String[] supportedTypes = info.getSupportedTypes();
                            for (String supportedType : supportedTypes) {
                        if (supportedType.equalsIgnoreCase(mimeType)) {
                            MediaCodecInfo.CodecCapabilities capabilities = info.getCapabilitiesForType(supportedType);
                            Pair<String, MediaCodecInfo.CodecCapabilities> pair = Pair.create(codecName, capabilities);
                            codecs.put(mimeType, pair);
                            return pair;
                                }
                            }
                        }
                    }
            return null;
                }

                @TargetApi(21)
       private static final class MediaCodecListCompatV21 implements MediaCodecListCompat {
                    private final MediaCodecInfo[] mediaCodecInfos;

           public MediaCodecListCompatV21(boolean includeSecure) {
               int codecKind = includeSecure ? 1 : 0;
               this.mediaCodecInfos = new MediaCodecList(codecKind).getCodecInfos();
                    }

                    @Override 
           public int getCodecCount() {
               return this.mediaCodecInfos.length;
                    }

                    @Override 
           public MediaCodecInfo getCodecInfoAt(int index) {
               return this.mediaCodecInfos[index];
                    }

                    @Override 
           public boolean secureDecodersExplicit() {
               return true;
                    }
                }

       private static final class MediaCodecListCompatV16 implements MediaCodecListCompat {
           private MediaCodecListCompatV16() {
                    }

            MediaCodecListCompatV16(Object x0) {
               this();
                    }

                    @Override 
           public int getCodecCount() {
               return MediaCodecList.getCodecCount();
                    }

                    @Override 
           public MediaCodecInfo getCodecInfoAt(int index) {
               return MediaCodecList.getCodecInfoAt(index);
                    }

                    @Override 
           public boolean secureDecodersExplicit() {
               return false;
                    }
                }
            }
