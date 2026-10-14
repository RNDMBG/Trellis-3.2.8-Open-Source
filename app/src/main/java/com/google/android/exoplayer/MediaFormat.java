            package com.google.android.exoplayer;

            import android.annotation.SuppressLint;
            import android.annotation.TargetApi;
            import com.google.android.exoplayer.util.Util;
            import java.nio.ByteBuffer;
            import java.util.ArrayList;
            import java.util.Arrays;
            import java.util.Collections;
            import java.util.List;
            
            
    public class MediaFormat {
                public static final int NO_VALUE = -1;
                public final int channelCount;
                private android.media.MediaFormat frameworkMediaFormat;
                private int hashCode;
                public final int height;
                public final List<byte[]> initializationData;
                private int maxHeight;
                public final int maxInputSize;
                private int maxWidth;
                public final String mimeType;
                public final int sampleRate;
                public final int width;

                @TargetApi(16)
        public static MediaFormat createFromFrameworkMediaFormatV16(android.media.MediaFormat format) {
            return new MediaFormat(format);
                }

        public static MediaFormat createVideoFormat(String mimeType, int maxInputSize, int width, int height, List<byte[]> initializationData) {
            return new MediaFormat(mimeType, maxInputSize, width, height, -1, -1, initializationData);
                }

        public static MediaFormat createAudioFormat(String mimeType, int maxInputSize, int channelCount, int sampleRate, List<byte[]> initializationData) {
            return new MediaFormat(mimeType, maxInputSize, -1, -1, channelCount, sampleRate, initializationData);
                }

                @TargetApi(16)
        private MediaFormat(android.media.MediaFormat format) {
            this.frameworkMediaFormat = format;
            this.mimeType = format.getString("mime");
            this.maxInputSize = getOptionalIntegerV16(format, "max-input-size");
            this.width = getOptionalIntegerV16(format, "width");
            this.height = getOptionalIntegerV16(format, "height");
            this.channelCount = getOptionalIntegerV16(format, "channel-count");
            this.sampleRate = getOptionalIntegerV16(format, "sample-rate");
            this.initializationData = new ArrayList();
                    for (int i = 0; format.containsKey("csd-" + i); i++) {
                ByteBuffer buffer = format.getByteBuffer("csd-" + i);
                byte[] data = new byte[buffer.limit()];
                buffer.get(data);
                this.initializationData.add(data);
                buffer.flip();
                    }
            this.maxWidth = -1;
            this.maxHeight = -1;
                }

        private MediaFormat(String mimeType, int maxInputSize, int width, int height, int channelCount, int sampleRate, List<byte[]> initializationData) {
            this.mimeType = mimeType;
            this.maxInputSize = maxInputSize;
            this.width = width;
            this.height = height;
            this.channelCount = channelCount;
           this.sampleRate = sampleRate;
           this.initializationData = initializationData == null ? Collections.emptyList() : initializationData;
           this.maxWidth = -1;
           this.maxHeight = -1;
                }

       public void setMaxVideoDimensions(int maxWidth, int maxHeight) {
           this.maxWidth = maxWidth;
           this.maxHeight = maxHeight;
           if (this.frameworkMediaFormat != null) {
               maybeSetMaxDimensionsV16(this.frameworkMediaFormat);
                    }
                }

       public int getMaxVideoWidth() {
           return this.maxWidth;
                }

       public int getMaxVideoHeight() {
           return this.maxHeight;
                }

       public int hashCode() {
           if (this.hashCode == 0) {
               int result = new StringBuilder().append(527).append(this.mimeType).toString() == null ? 0 : this.mimeType.hashCode();
               int result2 = (((((((((((((result * 31) + this.maxInputSize) * 31) + this.width) * 31) + this.height) * 31) + this.maxWidth) * 31) + this.maxHeight) * 31) + this.channelCount) * 31) + this.sampleRate;
                        for (int i = 0; i < this.initializationData.size(); i++) {
                   result2 = (result2 * 31) + Arrays.hashCode(this.initializationData.get(i));
                        }
               this.hashCode = result2;
                    }
           return this.hashCode;
                }

       public boolean equals(Object obj) {
           if (this == obj) {
               return true;
                    }
           if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
           return equalsInternal((MediaFormat) obj, false);
                }

       public boolean equals(MediaFormat other, boolean ignoreMaxDimensions) {
           if (this == other) {
               return true;
                    }
           if (other == null) {
               return false;
                    }
           return equalsInternal(other, ignoreMaxDimensions);
                }

       private boolean equalsInternal(MediaFormat other, boolean ignoreMaxDimensions) {
           if (this.maxInputSize != other.maxInputSize || this.width != other.width || this.height != other.height || ((!ignoreMaxDimensions && (this.maxWidth != other.maxWidth || this.maxHeight != other.maxHeight)) || this.channelCount != other.channelCount || this.sampleRate != other.sampleRate || !Util.areEqual(this.mimeType, other.mimeType) || this.initializationData.size() != other.initializationData.size())) {
                        return false;
                    }
                    for (int i = 0; i < this.initializationData.size(); i++) {
               if (!Arrays.equals(this.initializationData.get(i), other.initializationData.get(i))) {
                            return false;
                        }
                    }
           return true;
                }

       public String toString() {
           return "MediaFormat(" + this.mimeType + ", " + this.maxInputSize + ", " + this.width + ", " + this.height + ", " + this.channelCount + ", " + this.sampleRate + ", " + this.maxWidth + ", " + this.maxHeight + ")";
                }

                @TargetApi(16)
       public final android.media.MediaFormat getFrameworkMediaFormatV16() {
           if (this.frameworkMediaFormat == null) {
               android.media.MediaFormat format = new android.media.MediaFormat();
               format.setString("mime", this.mimeType);
               maybeSetIntegerV16(format, "max-input-size", this.maxInputSize);
               maybeSetIntegerV16(format, "width", this.width);
               maybeSetIntegerV16(format, "height", this.height);
               maybeSetIntegerV16(format, "channel-count", this.channelCount);
               maybeSetIntegerV16(format, "sample-rate", this.sampleRate);
                        for (int i = 0; i < this.initializationData.size(); i++) {
                   format.setByteBuffer("csd-" + i, ByteBuffer.wrap(this.initializationData.get(i)));
                        }
               maybeSetMaxDimensionsV16(format);
               this.frameworkMediaFormat = format;
                    }
           return this.frameworkMediaFormat;
                }

                @SuppressLint({"InlinedApi"})
                @TargetApi(16)
       private final void maybeSetMaxDimensionsV16(android.media.MediaFormat format) {
           maybeSetIntegerV16(format, "max-width", this.maxWidth);
           maybeSetIntegerV16(format, "max-height", this.maxHeight);
                }

                @TargetApi(16)
       private static final void maybeSetIntegerV16(android.media.MediaFormat format, String key, int value) {
           if (value != -1) {
               format.setInteger(key, value);
                    }
                }

                @TargetApi(16)
       private static final int getOptionalIntegerV16(android.media.MediaFormat format, String key) {
           if (format.containsKey(key)) {
                        return format.getInteger(key);
                    }
                    return -1;
                }
            }
