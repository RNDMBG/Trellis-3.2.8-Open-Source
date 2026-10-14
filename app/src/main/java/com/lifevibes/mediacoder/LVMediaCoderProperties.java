            package com.lifevibes.mediacoder;

            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 163 */   public class LVMediaCoderProperties {
                public static final int AUDIO_CHANNEL_COUNT_1 = 1;
                static final int AUDIO_CHANNEL_COUNT_2 = 2;
                public static final int AUDIO_SAMPLE_RATE_11025 = 11025;
                public static final int AUDIO_SAMPLE_RATE_12000 = 12000;
                public static final int AUDIO_SAMPLE_RATE_16000 = 16000;
                public static final int AUDIO_SAMPLE_RATE_22050 = 22050;
                public static final int AUDIO_SAMPLE_RATE_24000 = 24000;
                public static final int AUDIO_SAMPLE_RATE_32000 = 32000;
                public static final int AUDIO_SAMPLE_RATE_4096 = 4096;
                public static final int AUDIO_SAMPLE_RATE_44100 = 44100;
                public static final int AUDIO_SAMPLE_RATE_48000 = 48000;
                public static final int AUDIO_SAMPLE_RATE_64000 = 64000;
                public static final int AUDIO_SAMPLE_RATE_7350 = 7350;
                public static final int AUDIO_SAMPLE_RATE_8000 = 8000;
                public static final int AUDIO_SAMPLE_RATE_88200 = 88200;
                public static final int AUDIO_SAMPLE_RATE_96000 = 96000;
                public static final int BITRATE_1M = 1024000;
                public static final int BITRATE_2M = 2048000;
                public static final int BITRATE_3M = 3072000;
                public static final int BITRATE_512K = 512000;
                public static final int BITRATE_5M = 5120000;
                public static final int BITRATE_8M = 8192000;
                public static final int BITRATE_AUDIO_128K = 128000;
                public static final int BITRATE_AUDIO_64K = 64000;
                static boolean DEBUG = false;
                static boolean DUMPS = false;
                public static final int ENCODE_AUDIO = 1;
                public static final int ENCODE_VIDEO = 0;
                protected static final int ERROR_BLACK_FRAME = -1;
                public static final int FRAMERATE_15 = 15;
                public static final int FRAMERATE_24 = 24;
                public static final int FRAMERATE_25 = 25;
                public static final int FRAMERATE_30 = 30;
                public static final int INPUT_TYPE_RGB = 0;
                public static final int INPUT_TYPE_RGBA = 1;
                static final boolean RS_FILTERS_ENABLED = false;
                public static final int VIDEO_HEIGHT_240 = 240;
                public static final int VIDEO_HEIGHT_320 = 320;
                public static final int VIDEO_HEIGHT_480 = 480;
                public static final int VIDEO_HEIGHT_640 = 640;
                public static final int VIDEO_HEIGHT_720 = 720;
                public static final int VIDEO_WIDTH_240 = 240;
                public static final int VIDEO_WIDTH_320 = 320;
                public static final int VIDEO_WIDTH_480 = 480;
                public static final int VIDEO_WIDTH_640 = 640;
                public static final int VIDEO_WIDTH_720 = 720;
                private static final ArrayList<Integer> SUPPORTED_HEIGHT_WIDTH = new C12481();
                private static final ArrayList<Integer> SUPPORTED_AUDIO_BITRATES = new C12492();
                private static final ArrayList<Integer> SUPPORTED_VIDEO_BITRATES = new C12503();
                private static final ArrayList<Integer> SUPPORTED_AUDIO_CHANNELCOUNTS = new C12514();
                private static final ArrayList<Integer> SUPPORTED_FRAMERATES = new C12525();
                private static final ArrayList<Integer> SUPPORTED_AUDIO_SAMPLERATES = new C12536();

                /* JADX INFO: renamed from: com.lifevibes.mediacoder.LVMediaCoderProperties$1 */
/* 164 */       static class C12481 extends ArrayList<Integer> {
                    private static final long serialVersionUID = 1;

/* 165 */           C12481() {
/* 172 */               add(240);
/* 173 */               add(320);
/* 174 */               add(480);
/* 175 */               add(640);
/* 176 */               add(720);
                    }
                }

                /* JADX INFO: renamed from: com.lifevibes.mediacoder.LVMediaCoderProperties$2 */
/* 181 */       static class C12492 extends ArrayList<Integer> {
                    private static final long serialVersionUID = 1;

/* 182 */           C12492() {
/* 189 */               add(64000);
/* 190 */               add(Integer.valueOf(LVMediaCoderProperties.BITRATE_AUDIO_128K));
                    }
                }

                /* JADX INFO: renamed from: com.lifevibes.mediacoder.LVMediaCoderProperties$3 */
/* 194 */       static class C12503 extends ArrayList<Integer> {
                    private static final long serialVersionUID = 1;

/* 195 */           C12503() {
/* 202 */               add(Integer.valueOf(LVMediaCoderProperties.BITRATE_512K));
/* 203 */               add(Integer.valueOf(LVMediaCoderProperties.BITRATE_1M));
/* 204 */               add(Integer.valueOf(LVMediaCoderProperties.BITRATE_2M));
/* 205 */               add(Integer.valueOf(LVMediaCoderProperties.BITRATE_3M));
/* 206 */               add(Integer.valueOf(LVMediaCoderProperties.BITRATE_5M));
/* 207 */               add(Integer.valueOf(LVMediaCoderProperties.BITRATE_8M));
                    }
                }

                /* JADX INFO: renamed from: com.lifevibes.mediacoder.LVMediaCoderProperties$4 */
/* 211 */       static class C12514 extends ArrayList<Integer> {
                    private static final long serialVersionUID = 1;

/* 212 */           C12514() {
/* 219 */               add(1);
                    }
                }

                /* JADX INFO: renamed from: com.lifevibes.mediacoder.LVMediaCoderProperties$5 */
/* 223 */       static class C12525 extends ArrayList<Integer> {
                    private static final long serialVersionUID = 1;

/* 224 */           C12525() {
/* 231 */               add(15);
/* 232 */               add(24);
/* 233 */               add(25);
/* 234 */               add(30);
                    }
                }

                /* JADX INFO: renamed from: com.lifevibes.mediacoder.LVMediaCoderProperties$6 */
/* 238 */       static class C12536 extends ArrayList<Integer> {
                    private static final long serialVersionUID = 1;

/* 239 */           C12536() {
/* 246 */               add(Integer.valueOf(LVMediaCoderProperties.AUDIO_SAMPLE_RATE_48000));
/* 247 */               add(44100);
/* 248 */               add(Integer.valueOf(LVMediaCoderProperties.AUDIO_SAMPLE_RATE_32000));
/* 249 */               add(Integer.valueOf(LVMediaCoderProperties.AUDIO_SAMPLE_RATE_22050));
/* 250 */               add(Integer.valueOf(LVMediaCoderProperties.AUDIO_SAMPLE_RATE_16000));
/* 251 */               add(Integer.valueOf(LVMediaCoderProperties.AUDIO_SAMPLE_RATE_11025));
/* 252 */               add(8000);
                    }
                }

/* 265 */       public static ArrayList<Integer> getSupportedHeightAndWidth() {
/* 266 */           return SUPPORTED_HEIGHT_WIDTH;
                }

/* 272 */       public static ArrayList<Integer> getSupportedAudioBitrates() {
/* 273 */           return SUPPORTED_AUDIO_BITRATES;
                }

/* 279 */       public static ArrayList<Integer> getSupportedVideoBitrates() {
/* 280 */           return SUPPORTED_VIDEO_BITRATES;
                }

/* 286 */       public static ArrayList<Integer> getSupportedChannelCounts() {
/* 287 */           return SUPPORTED_AUDIO_CHANNELCOUNTS;
                }

/* 293 */       public static ArrayList<Integer> getSupportedSampleRates() {
/* 294 */           return SUPPORTED_AUDIO_SAMPLERATES;
                }

/* 300 */       public static ArrayList<Integer> getSupportedFrameRates() {
/* 301 */           return SUPPORTED_FRAMERATES;
                }
            }
