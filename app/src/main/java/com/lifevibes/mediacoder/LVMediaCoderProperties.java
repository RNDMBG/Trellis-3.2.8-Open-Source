            package com.lifevibes.mediacoder;

            import java.util.ArrayList;
            
            
   public class LVMediaCoderProperties {
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

                
       static class C12481 extends ArrayList<Integer> {
                    private static final long serialVersionUID = 1;

           C12481() {
               add(240);
               add(320);
               add(480);
               add(640);
               add(720);
                    }
                }

                
       static class C12492 extends ArrayList<Integer> {
                    private static final long serialVersionUID = 1;

           C12492() {
               add(64000);
               add(Integer.valueOf(LVMediaCoderProperties.BITRATE_AUDIO_128K));
                    }
                }

                
       static class C12503 extends ArrayList<Integer> {
                    private static final long serialVersionUID = 1;

           C12503() {
               add(Integer.valueOf(LVMediaCoderProperties.BITRATE_512K));
               add(Integer.valueOf(LVMediaCoderProperties.BITRATE_1M));
               add(Integer.valueOf(LVMediaCoderProperties.BITRATE_2M));
               add(Integer.valueOf(LVMediaCoderProperties.BITRATE_3M));
               add(Integer.valueOf(LVMediaCoderProperties.BITRATE_5M));
               add(Integer.valueOf(LVMediaCoderProperties.BITRATE_8M));
                    }
                }

                
       static class C12514 extends ArrayList<Integer> {
                    private static final long serialVersionUID = 1;

           C12514() {
               add(1);
                    }
                }

                
       static class C12525 extends ArrayList<Integer> {
                    private static final long serialVersionUID = 1;

           C12525() {
               add(15);
               add(24);
               add(25);
               add(30);
                    }
                }

                
       static class C12536 extends ArrayList<Integer> {
                    private static final long serialVersionUID = 1;

           C12536() {
               add(Integer.valueOf(LVMediaCoderProperties.AUDIO_SAMPLE_RATE_48000));
               add(44100);
               add(Integer.valueOf(LVMediaCoderProperties.AUDIO_SAMPLE_RATE_32000));
               add(Integer.valueOf(LVMediaCoderProperties.AUDIO_SAMPLE_RATE_22050));
               add(Integer.valueOf(LVMediaCoderProperties.AUDIO_SAMPLE_RATE_16000));
               add(Integer.valueOf(LVMediaCoderProperties.AUDIO_SAMPLE_RATE_11025));
               add(8000);
                    }
                }

       public static ArrayList<Integer> getSupportedHeightAndWidth() {
           return SUPPORTED_HEIGHT_WIDTH;
                }

       public static ArrayList<Integer> getSupportedAudioBitrates() {
           return SUPPORTED_AUDIO_BITRATES;
                }

       public static ArrayList<Integer> getSupportedVideoBitrates() {
           return SUPPORTED_VIDEO_BITRATES;
                }

       public static ArrayList<Integer> getSupportedChannelCounts() {
           return SUPPORTED_AUDIO_CHANNELCOUNTS;
                }

       public static ArrayList<Integer> getSupportedSampleRates() {
           return SUPPORTED_AUDIO_SAMPLERATES;
                }

       public static ArrayList<Integer> getSupportedFrameRates() {
           return SUPPORTED_FRAMERATES;
                }
            }
