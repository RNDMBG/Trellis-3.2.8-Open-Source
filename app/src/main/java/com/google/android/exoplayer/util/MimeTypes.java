            package com.google.android.exoplayer.util;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 38 */    public class MimeTypes {
                public static final String APPLICATION_TTML = "application/ttml+xml";
                public static final String AUDIO_AAC = "audio/mp4a-latm";
                public static final String AUDIO_MP4 = "audio/mp4";
                public static final String BASE_TYPE_APPLICATION = "application";
                public static final String BASE_TYPE_AUDIO = "audio";
                public static final String BASE_TYPE_TEXT = "text";
                public static final String BASE_TYPE_VIDEO = "video";
                public static final String TEXT_VTT = "text/vtt";
                public static final String VIDEO_H264 = "video/avc";
                public static final String VIDEO_MP4 = "video/mp4";
                public static final String VIDEO_VP9 = "video/x-vnd.on2.vp9";
                public static final String VIDEO_WEBM = "video/webm";

/* 39 */        private MimeTypes() {
                }

/* 48 */        public static String getTopLevelType(String mimeType) {
/* 49 */            int indexOfSlash = mimeType.indexOf(47);
/* 50 */            if (indexOfSlash == -1) {
/* 51 */                throw new IllegalArgumentException("Invalid mime type: " + mimeType);
                    }
/* 53 */            return mimeType.substring(0, indexOfSlash);
                }

/* 62 */        public static boolean isAudio(String mimeType) {
/* 63 */            return getTopLevelType(mimeType).equals(BASE_TYPE_AUDIO);
                }

/* 72 */        public static boolean isVideo(String mimeType) {
/* 73 */            return getTopLevelType(mimeType).equals(BASE_TYPE_VIDEO);
                }

/* 82 */        public static boolean isText(String mimeType) {
/* 83 */            return getTopLevelType(mimeType).equals(BASE_TYPE_TEXT);
                }

/* 92 */        public static boolean isApplication(String mimeType) {
/* 93 */            return getTopLevelType(mimeType).equals(BASE_TYPE_APPLICATION);
                }

/* 102 */       public static boolean isTtml(String mimeType) {
/* 103 */           return mimeType.equals(APPLICATION_TTML);
                }
            }
