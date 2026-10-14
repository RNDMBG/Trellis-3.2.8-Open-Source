            package com.google.android.exoplayer.util;
            
            
    public class MimeTypes {
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

        private MimeTypes() {
                }

        public static String getTopLevelType(String mimeType) {
            int indexOfSlash = mimeType.indexOf(47);
            if (indexOfSlash == -1) {
                throw new IllegalArgumentException("Invalid mime type: " + mimeType);
                    }
            return mimeType.substring(0, indexOfSlash);
                }

        public static boolean isAudio(String mimeType) {
            return getTopLevelType(mimeType).equals(BASE_TYPE_AUDIO);
                }

        public static boolean isVideo(String mimeType) {
            return getTopLevelType(mimeType).equals(BASE_TYPE_VIDEO);
                }

        public static boolean isText(String mimeType) {
            return getTopLevelType(mimeType).equals(BASE_TYPE_TEXT);
                }

        public static boolean isApplication(String mimeType) {
            return getTopLevelType(mimeType).equals(BASE_TYPE_APPLICATION);
                }

       public static boolean isTtml(String mimeType) {
           return mimeType.equals(APPLICATION_TTML);
                }
            }
