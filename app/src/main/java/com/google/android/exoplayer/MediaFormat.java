            package com.google.android.exoplayer;

            import android.annotation.SuppressLint;
            import android.annotation.TargetApi;
            import com.google.android.exoplayer.util.Util;
            import java.nio.ByteBuffer;
            import java.util.ArrayList;
            import java.util.Arrays;
            import java.util.Collections;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 55 */    public class MediaFormat {
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
/* 56 */        public static MediaFormat createFromFrameworkMediaFormatV16(android.media.MediaFormat format) {
/* 57 */            return new MediaFormat(format);
                }

/* 61 */        public static MediaFormat createVideoFormat(String mimeType, int maxInputSize, int width, int height, List<byte[]> initializationData) {
/* 62 */            return new MediaFormat(mimeType, maxInputSize, width, height, -1, -1, initializationData);
                }

/* 67 */        public static MediaFormat createAudioFormat(String mimeType, int maxInputSize, int channelCount, int sampleRate, List<byte[]> initializationData) {
/* 68 */            return new MediaFormat(mimeType, maxInputSize, -1, -1, channelCount, sampleRate, initializationData);
                }

                @TargetApi(16)
/* 72 */        private MediaFormat(android.media.MediaFormat format) {
/* 74 */            this.frameworkMediaFormat = format;
/* 75 */            this.mimeType = format.getString("mime");
/* 76 */            this.maxInputSize = getOptionalIntegerV16(format, "max-input-size");
/* 77 */            this.width = getOptionalIntegerV16(format, "width");
/* 78 */            this.height = getOptionalIntegerV16(format, "height");
/* 79 */            this.channelCount = getOptionalIntegerV16(format, "channel-count");
/* 80 */            this.sampleRate = getOptionalIntegerV16(format, "sample-rate");
/* 81 */            this.initializationData = new ArrayList();
                    for (int i = 0; format.containsKey("csd-" + i); i++) {
/* 83 */                ByteBuffer buffer = format.getByteBuffer("csd-" + i);
/* 84 */                byte[] data = new byte[buffer.limit()];
/* 85 */                buffer.get(data);
/* 86 */                this.initializationData.add(data);
/* 87 */                buffer.flip();
                    }
/* 89 */            this.maxWidth = -1;
/* 90 */            this.maxHeight = -1;
                }

/* 93 */        private MediaFormat(String mimeType, int maxInputSize, int width, int height, int channelCount, int sampleRate, List<byte[]> initializationData) {
/* 95 */            this.mimeType = mimeType;
/* 96 */            this.maxInputSize = maxInputSize;
/* 97 */            this.width = width;
/* 98 */            this.height = height;
/* 99 */            this.channelCount = channelCount;
/* 100 */           this.sampleRate = sampleRate;
/* 101 */           this.initializationData = initializationData == null ? Collections.emptyList() : initializationData;
/* 103 */           this.maxWidth = -1;
/* 104 */           this.maxHeight = -1;
                }

/* 107 */       public void setMaxVideoDimensions(int maxWidth, int maxHeight) {
/* 108 */           this.maxWidth = maxWidth;
/* 109 */           this.maxHeight = maxHeight;
/* 110 */           if (this.frameworkMediaFormat != null) {
/* 111 */               maybeSetMaxDimensionsV16(this.frameworkMediaFormat);
                    }
                }

/* 115 */       public int getMaxVideoWidth() {
/* 116 */           return this.maxWidth;
                }

/* 119 */       public int getMaxVideoHeight() {
/* 120 */           return this.maxHeight;
                }

/* 124 */       public int hashCode() {
/* 125 */           if (this.hashCode == 0) {
/* 127 */               int result = new StringBuilder().append(527).append(this.mimeType).toString() == null ? 0 : this.mimeType.hashCode();
/* 134 */               int result2 = (((((((((((((result * 31) + this.maxInputSize) * 31) + this.width) * 31) + this.height) * 31) + this.maxWidth) * 31) + this.maxHeight) * 31) + this.channelCount) * 31) + this.sampleRate;
                        for (int i = 0; i < this.initializationData.size(); i++) {
/* 136 */                   result2 = (result2 * 31) + Arrays.hashCode(this.initializationData.get(i));
                        }
/* 138 */               this.hashCode = result2;
                    }
/* 140 */           return this.hashCode;
                }

/* 144 */       public boolean equals(Object obj) {
/* 145 */           if (this == obj) {
/* 146 */               return true;
                    }
/* 148 */           if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
/* 151 */           return equalsInternal((MediaFormat) obj, false);
                }

/* 154 */       public boolean equals(MediaFormat other, boolean ignoreMaxDimensions) {
/* 155 */           if (this == other) {
/* 156 */               return true;
                    }
/* 158 */           if (other == null) {
/* 159 */               return false;
                    }
/* 161 */           return equalsInternal(other, ignoreMaxDimensions);
                }

/* 164 */       private boolean equalsInternal(MediaFormat other, boolean ignoreMaxDimensions) {
/* 165 */           if (this.maxInputSize != other.maxInputSize || this.width != other.width || this.height != other.height || ((!ignoreMaxDimensions && (this.maxWidth != other.maxWidth || this.maxHeight != other.maxHeight)) || this.channelCount != other.channelCount || this.sampleRate != other.sampleRate || !Util.areEqual(this.mimeType, other.mimeType) || this.initializationData.size() != other.initializationData.size())) {
                        return false;
                    }
                    for (int i = 0; i < this.initializationData.size(); i++) {
/* 173 */               if (!Arrays.equals(this.initializationData.get(i), other.initializationData.get(i))) {
                            return false;
                        }
                    }
/* 177 */           return true;
                }

/* 181 */       public String toString() {
/* 182 */           return "MediaFormat(" + this.mimeType + ", " + this.maxInputSize + ", " + this.width + ", " + this.height + ", " + this.channelCount + ", " + this.sampleRate + ", " + this.maxWidth + ", " + this.maxHeight + ")";
                }

                @TargetApi(16)
/* 190 */       public final android.media.MediaFormat getFrameworkMediaFormatV16() {
/* 191 */           if (this.frameworkMediaFormat == null) {
/* 192 */               android.media.MediaFormat format = new android.media.MediaFormat();
/* 193 */               format.setString("mime", this.mimeType);
/* 194 */               maybeSetIntegerV16(format, "max-input-size", this.maxInputSize);
/* 195 */               maybeSetIntegerV16(format, "width", this.width);
/* 196 */               maybeSetIntegerV16(format, "height", this.height);
/* 197 */               maybeSetIntegerV16(format, "channel-count", this.channelCount);
/* 198 */               maybeSetIntegerV16(format, "sample-rate", this.sampleRate);
                        for (int i = 0; i < this.initializationData.size(); i++) {
/* 200 */                   format.setByteBuffer("csd-" + i, ByteBuffer.wrap(this.initializationData.get(i)));
                        }
/* 202 */               maybeSetMaxDimensionsV16(format);
/* 203 */               this.frameworkMediaFormat = format;
                    }
/* 205 */           return this.frameworkMediaFormat;
                }

                @SuppressLint({"InlinedApi"})
                @TargetApi(16)
/* 210 */       private final void maybeSetMaxDimensionsV16(android.media.MediaFormat format) {
/* 211 */           maybeSetIntegerV16(format, "max-width", this.maxWidth);
/* 212 */           maybeSetIntegerV16(format, "max-height", this.maxHeight);
                }

                @TargetApi(16)
/* 217 */       private static final void maybeSetIntegerV16(android.media.MediaFormat format, String key, int value) {
/* 218 */           if (value != -1) {
/* 219 */               format.setInteger(key, value);
                    }
                }

                @TargetApi(16)
/* 225 */       private static final int getOptionalIntegerV16(android.media.MediaFormat format, String key) {
/* 226 */           if (format.containsKey(key)) {
                        return format.getInteger(key);
                    }
                    return -1;
                }
            }
