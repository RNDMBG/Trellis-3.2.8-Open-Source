            package com.lifevibes.mediacoder;

            import android.annotation.TargetApi;
            import android.content.Context;
            import android.media.MediaCodecInfo;
            import android.media.MediaCodecList;
            import android.os.Build;
            import android.os.Environment;
            import android.util.Log;
            import com.edisonwang.android.slog.SLog;
            import java.io.File;
            import java.io.FileNotFoundException;
            import java.io.FileOutputStream;
            import java.io.IOException;
            import java.io.PrintStream;
            import java.util.ArrayList;
            
            
            @TargetApi(16)
    public class LVMediaCoderFactory {
                private static final String TAG = "LVMediaCoderFactory";
                private static File inputAudioFile;
                private static File inputVideoFile;
                private static File mLogFile;
                private static PrintStream mPrintLogStream;
                private static String mUnsupportedColorFormat;

        private LVMediaCoderFactory() {
                }

       public static LVMediaCoderImpl create(Context context, LVMediaCoderConfiguration configuration) throws IllegalArgumentException {
           if (SLog.sLogsOn && !new File(Environment.getExternalStorageDirectory() + "/Twitter/").mkdirs()) {
               SLog.m455e("Logs may not be working.");
                    }
           if (Build.VERSION.SDK_INT <= 15) {
               LVMediaCoderImpl.isRunningOnICS = true;
                    } else if (!checkColorFormat()) {
               throw new IllegalStateException("Currently color format " + mUnsupportedColorFormat + " is NOT supported");
                    }
           validateConfiguration(configuration);
           return new LVMediaCoderImpl(context, configuration);
                }

       private static boolean checkColorFormat() {
           int index = 0;
           ArrayList<MediaCodecInfo> compatibleEncoders = LVMediaCoderProperties.DEBUG ? new ArrayList<>() : null;
                    for (int i = 0; i < MediaCodecList.getCodecCount(); i++) {
               MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
               if (LVMediaCoderProperties.DEBUG) {
                   printLog("[LVMediaCoderFactory] Encoder " + i + ": " + codecInfo.getName(), false);
                        }
               if (codecInfo.isEncoder()) {
                   String[] supportedTypes = codecInfo.getSupportedTypes();
                            for (String type : supportedTypes) {
                       if (type.contains("avc")) {
                           if (LVMediaCoderProperties.DEBUG) {
                               printLog("[LVMediaCoderFactory] Encoder " + i + "/" + MediaCodecList.getCodecCount() + ": " + codecInfo.getName() + " supports [" + type + "]", false);
                               if (compatibleEncoders != null) {
                                   compatibleEncoders.add(codecInfo);
                                        }
                                    }
                           index = i;
                                    break;
                                }
                            }
                        }
                    }
           MediaCodecInfo.CodecCapabilities capabilities = MediaCodecList.getCodecInfoAt(index).getCapabilitiesForType("video/avc");
           if (LVMediaCoderProperties.DEBUG) {
               int[] arr$ = capabilities.colorFormats;
                        for (int colorFormat : arr$) {
                   printLog("[LVMediaCoderFactory] Color Format: " + colorFormat, false);
                        }
                    }
           ArrayList<Integer> colorFormats = new ArrayList<>();
                    for (int i2 = 0; i2 < capabilities.colorFormats.length; i2++) {
               colorFormats.add(Integer.valueOf(capabilities.colorFormats[i2]));
                    }
           if (colorFormats.contains(19) || colorFormats.contains(20) || colorFormats.contains(21) || colorFormats.contains(39) || colorFormats.contains(2130706688)) {
               return true;
                    }
           mUnsupportedColorFormat = colorFormats.get(0) + "";
                    return false;
                }

       private static void validateConfiguration(LVMediaCoderConfiguration configuration) {
           if (configuration == null) {
               throw new NullPointerException("Configuration is null.");
                    }
           if (LVMediaCoderProperties.DEBUG) {
                        printLog("[LVMediaCoderFactory] Session configuration:\n\t\t\t\t Video BitRate:\t\t" + configuration.videoBitRate + "\n\t\t\t\t Video Frame Rate:\t" + configuration.videoFrameRate + "\n\t\t\t\t Video I-Frame Int:\t" + configuration.videoIFrameInterval + "\n\t\t\t\t Audio Bit Rate:\t" + configuration.audioBitRate + "\n\t\t\t\t Audio Channels:\t" + configuration.audioChannelCount + "\n\t\t\t\t Audio Sample Rate:\t" + configuration.audioSampleRate, false);
                    }
           if (configuration.videoIFrameInterval <= 0) {
               throw new IllegalArgumentException("Video IFrame interval should be greater than 0.");
                    }
           if (!LVMediaCoderProperties.getSupportedVideoBitrates().contains(Integer.valueOf(configuration.videoBitRate))) {
               throw new IllegalArgumentException(configuration.videoBitRate + " video bit-rate is not supported. Use LVMediaCoderProperties.getSupportedBitrates() to get the list of supported video bit-rates.");
                    }
           if (!LVMediaCoderProperties.getSupportedHeightAndWidth().contains(Integer.valueOf(configuration.videoWidth)) || !LVMediaCoderProperties.getSupportedHeightAndWidth().contains(Integer.valueOf(configuration.videoHeight))) {
               throw new IllegalArgumentException("Video resolution: " + configuration.videoWidth + "x" + configuration.videoHeight + " is not supported. Please use LVMediaCoderProperties.getSupportedHeightAndWidth() to get the list of supported Heights and Widths.");
                    }
           if (configuration.videoWidth != configuration.videoHeight) {
               throw new IllegalArgumentException("Video resolution: " + configuration.videoWidth + "x" + configuration.videoHeight + " is not supported. Currenly only aspect ratio of 1:1 is supported.");
                    }
           if (!LVMediaCoderProperties.getSupportedFrameRates().contains(Integer.valueOf(configuration.videoFrameRate))) {
               throw new IllegalArgumentException(configuration.videoFrameRate + " frame-rate is not supported. Use LVMediaCoderProperties.getSupportedFrameRates() to get the list of supported frame-rates.");
                    }
           if (!LVMediaCoderProperties.getSupportedAudioBitrates().contains(Integer.valueOf(configuration.audioBitRate))) {
               throw new IllegalArgumentException(configuration.audioBitRate + " audio bit-rate is not supported. Use LVMediaCoderProperties.getSupportedBitrates() to get the list of supported audio bit-rates.");
                    }
           if (!LVMediaCoderProperties.getSupportedChannelCounts().contains(Integer.valueOf(configuration.audioChannelCount))) {
               throw new IllegalArgumentException(configuration.audioChannelCount + " audio channel count is not supported. Use LVMediaCoderProperties.getSupportedChannelCounts() to get the list of supported audio channel counts.");
                    }
           if (!LVMediaCoderProperties.getSupportedSampleRates().contains(Integer.valueOf(configuration.audioSampleRate))) {
               throw new IllegalArgumentException(configuration.audioSampleRate + " audio sample rate is not supported. Use LVMediaCoderProperties.getSupportedSampleRates()to get the list of supported audio sample rates.");
                    }
                }

       public static void setLoggingEnabled(boolean debug) {
           LVMediaCoderProperties.DEBUG = debug;
           if (debug) {
               new File("/mnt/sdcard/Twitter/").mkdirs();
               mLogFile = new File("/mnt/sdcard/Twitter/LVMediaCoderLog.txt");
               if (mLogFile.exists()) {
                   mLogFile.delete();
                        }
               mLogFile = new File("/mnt/sdcard/Twitter/LVMediaCoderLog.txt");
                        try {
                   mPrintLogStream = new PrintStream(mLogFile);
                        } catch (FileNotFoundException e) {
                   e.printStackTrace();
                        }
                    }
                }

       static void printLog(String log, boolean exception) {
           if (LVMediaCoderProperties.DEBUG) {
               if (mPrintLogStream == null || mLogFile == null || !mLogFile.exists()) {
                   Log.d(TAG, "Exception while writing to the log file.");
                   return;
                        }
               Log.d(TAG, log.replace("[LVMediaCoderFactory] ", ""));
               if (exception) {
                   mPrintLogStream.println("======================= EXCEPTION [START] =======================");
                   mPrintLogStream.flush();
                        }
               mPrintLogStream.println(log);
               mPrintLogStream.flush();
               if (exception) {
                   mPrintLogStream.println("======================== EXCEPTION [END] ========================");
                   mPrintLogStream.flush();
                        }
                    }
                }

       public static void setDumpingEnabled(boolean dumping) {
           LVMediaCoderProperties.DUMPS = dumping;
           if (dumping) {
               new File("/mnt/sdcard/Twitter/").mkdir();
               inputVideoFile = new File("/mnt/sdcard/Twitter/Twitter_Input_Video.raw");
               inputAudioFile = new File("/mnt/sdcard/Twitter/Twitter_Input_Audio.raw");
               if (inputVideoFile.exists()) {
                   inputVideoFile.delete();
                        }
               if (inputAudioFile.exists()) {
                   inputAudioFile.delete();
                        }
               inputVideoFile = new File("/mnt/sdcard/Twitter/Twitter_Input_Video.raw");
               inputAudioFile = new File("/mnt/sdcard/Twitter/Twitter_Input_Audio.raw");
                    }
                }

       static void dumpInputVideoData(byte[] videoData) {
           if (LVMediaCoderProperties.DUMPS || inputVideoFile != null) {
                        try {
                   FileOutputStream outputStreamVideo = new FileOutputStream(inputVideoFile, true);
                   outputStreamVideo.write(videoData);
                   outputStreamVideo.flush();
                   outputStreamVideo.close();
                        } catch (IOException e) {
                   e.printStackTrace();
                        }
                    }
                }

       static void dumpInputAudioData(byte[] audioData) {
           if (LVMediaCoderProperties.DUMPS || inputAudioFile != null) {
                        try {
                   FileOutputStream outputStreamAudio = new FileOutputStream(inputAudioFile, true);
                   outputStreamAudio.write(audioData);
                   outputStreamAudio.flush();
                   outputStreamAudio.close();
                        } catch (IOException e) {
                   e.printStackTrace();
                        }
                    }
                }
            }
