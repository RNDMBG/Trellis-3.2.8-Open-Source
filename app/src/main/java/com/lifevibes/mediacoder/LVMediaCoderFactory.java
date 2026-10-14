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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(16)
/* 84 */    public class LVMediaCoderFactory {
                private static final String TAG = "LVMediaCoderFactory";
                private static File inputAudioFile;
                private static File inputVideoFile;
                private static File mLogFile;
                private static PrintStream mPrintLogStream;
                private static String mUnsupportedColorFormat;

/* 85 */        private LVMediaCoderFactory() {
                }

/* 100 */       public static LVMediaCoderImpl create(Context context, LVMediaCoderConfiguration configuration) throws IllegalArgumentException {
/* 101 */           if (SLog.sLogsOn && !new File(Environment.getExternalStorageDirectory() + "/Twitter/").mkdirs()) {
/* 103 */               SLog.m455e("Logs may not be working.");
                    }
/* 108 */           if (Build.VERSION.SDK_INT <= 15) {
/* 109 */               LVMediaCoderImpl.isRunningOnICS = true;
                    } else if (!checkColorFormat()) {
/* 113 */               throw new IllegalStateException("Currently color format " + mUnsupportedColorFormat + " is NOT supported");
                    }
/* 118 */           validateConfiguration(configuration);
/* 119 */           return new LVMediaCoderImpl(context, configuration);
                }

/* 122 */       private static boolean checkColorFormat() {
/* 123 */           int index = 0;
/* 124 */           ArrayList<MediaCodecInfo> compatibleEncoders = LVMediaCoderProperties.DEBUG ? new ArrayList<>() : null;
                    for (int i = 0; i < MediaCodecList.getCodecCount(); i++) {
/* 126 */               MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
/* 127 */               if (LVMediaCoderProperties.DEBUG) {
/* 128 */                   printLog("[LVMediaCoderFactory] Encoder " + i + ": " + codecInfo.getName(), false);
                        }
/* 130 */               if (codecInfo.isEncoder()) {
/* 133 */                   String[] supportedTypes = codecInfo.getSupportedTypes();
                            for (String type : supportedTypes) {
/* 135 */                       if (type.contains("avc")) {
/* 136 */                           if (LVMediaCoderProperties.DEBUG) {
/* 137 */                               printLog("[LVMediaCoderFactory] Encoder " + i + "/" + MediaCodecList.getCodecCount() + ": " + codecInfo.getName() + " supports [" + type + "]", false);
/* 138 */                               if (compatibleEncoders != null) {
/* 139 */                                   compatibleEncoders.add(codecInfo);
                                        }
                                    }
/* 142 */                           index = i;
                                    break;
                                }
                            }
                        }
                    }
/* 148 */           MediaCodecInfo.CodecCapabilities capabilities = MediaCodecList.getCodecInfoAt(index).getCapabilitiesForType("video/avc");
/* 150 */           if (LVMediaCoderProperties.DEBUG) {
/* 151 */               int[] arr$ = capabilities.colorFormats;
                        for (int colorFormat : arr$) {
/* 152 */                   printLog("[LVMediaCoderFactory] Color Format: " + colorFormat, false);
                        }
                    }
/* 156 */           ArrayList<Integer> colorFormats = new ArrayList<>();
                    for (int i2 = 0; i2 < capabilities.colorFormats.length; i2++) {
/* 158 */               colorFormats.add(Integer.valueOf(capabilities.colorFormats[i2]));
                    }
/* 161 */           if (colorFormats.contains(19) || colorFormats.contains(20) || colorFormats.contains(21) || colorFormats.contains(39) || colorFormats.contains(2130706688)) {
/* 167 */               return true;
                    }
/* 169 */           mUnsupportedColorFormat = colorFormats.get(0) + "";
                    return false;
                }

/* 173 */       private static void validateConfiguration(LVMediaCoderConfiguration configuration) {
/* 174 */           if (configuration == null) {
/* 175 */               throw new NullPointerException("Configuration is null.");
                    }
/* 178 */           if (LVMediaCoderProperties.DEBUG) {
                        printLog("[LVMediaCoderFactory] Session configuration:\n\t\t\t\t Video BitRate:\t\t" + configuration.videoBitRate + "\n\t\t\t\t Video Frame Rate:\t" + configuration.videoFrameRate + "\n\t\t\t\t Video I-Frame Int:\t" + configuration.videoIFrameInterval + "\n\t\t\t\t Audio Bit Rate:\t" + configuration.audioBitRate + "\n\t\t\t\t Audio Channels:\t" + configuration.audioChannelCount + "\n\t\t\t\t Audio Sample Rate:\t" + configuration.audioSampleRate, false);
                    }
/* 186 */           if (configuration.videoIFrameInterval <= 0) {
/* 187 */               throw new IllegalArgumentException("Video IFrame interval should be greater than 0.");
                    }
/* 189 */           if (!LVMediaCoderProperties.getSupportedVideoBitrates().contains(Integer.valueOf(configuration.videoBitRate))) {
/* 190 */               throw new IllegalArgumentException(configuration.videoBitRate + " video bit-rate is not supported. Use LVMediaCoderProperties.getSupportedBitrates() to get the list of supported video bit-rates.");
                    }
/* 192 */           if (!LVMediaCoderProperties.getSupportedHeightAndWidth().contains(Integer.valueOf(configuration.videoWidth)) || !LVMediaCoderProperties.getSupportedHeightAndWidth().contains(Integer.valueOf(configuration.videoHeight))) {
/* 193 */               throw new IllegalArgumentException("Video resolution: " + configuration.videoWidth + "x" + configuration.videoHeight + " is not supported. Please use LVMediaCoderProperties.getSupportedHeightAndWidth() to get the list of supported Heights and Widths.");
                    }
/* 195 */           if (configuration.videoWidth != configuration.videoHeight) {
/* 196 */               throw new IllegalArgumentException("Video resolution: " + configuration.videoWidth + "x" + configuration.videoHeight + " is not supported. Currenly only aspect ratio of 1:1 is supported.");
                    }
/* 198 */           if (!LVMediaCoderProperties.getSupportedFrameRates().contains(Integer.valueOf(configuration.videoFrameRate))) {
/* 199 */               throw new IllegalArgumentException(configuration.videoFrameRate + " frame-rate is not supported. Use LVMediaCoderProperties.getSupportedFrameRates() to get the list of supported frame-rates.");
                    }
/* 201 */           if (!LVMediaCoderProperties.getSupportedAudioBitrates().contains(Integer.valueOf(configuration.audioBitRate))) {
/* 202 */               throw new IllegalArgumentException(configuration.audioBitRate + " audio bit-rate is not supported. Use LVMediaCoderProperties.getSupportedBitrates() to get the list of supported audio bit-rates.");
                    }
/* 204 */           if (!LVMediaCoderProperties.getSupportedChannelCounts().contains(Integer.valueOf(configuration.audioChannelCount))) {
/* 205 */               throw new IllegalArgumentException(configuration.audioChannelCount + " audio channel count is not supported. Use LVMediaCoderProperties.getSupportedChannelCounts() to get the list of supported audio channel counts.");
                    }
/* 207 */           if (!LVMediaCoderProperties.getSupportedSampleRates().contains(Integer.valueOf(configuration.audioSampleRate))) {
/* 208 */               throw new IllegalArgumentException(configuration.audioSampleRate + " audio sample rate is not supported. Use LVMediaCoderProperties.getSupportedSampleRates()to get the list of supported audio sample rates.");
                    }
                }

/* 218 */       public static void setLoggingEnabled(boolean debug) {
/* 219 */           LVMediaCoderProperties.DEBUG = debug;
/* 220 */           if (debug) {
/* 221 */               new File("/mnt/sdcard/Twitter/").mkdirs();
/* 223 */               mLogFile = new File("/mnt/sdcard/Twitter/LVMediaCoderLog.txt");
/* 224 */               if (mLogFile.exists()) {
/* 225 */                   mLogFile.delete();
                        }
/* 227 */               mLogFile = new File("/mnt/sdcard/Twitter/LVMediaCoderLog.txt");
                        try {
/* 229 */                   mPrintLogStream = new PrintStream(mLogFile);
                        } catch (FileNotFoundException e) {
/* 231 */                   e.printStackTrace();
                        }
                    }
                }

/* 236 */       static void printLog(String log, boolean exception) {
/* 237 */           if (LVMediaCoderProperties.DEBUG) {
/* 240 */               if (mPrintLogStream == null || mLogFile == null || !mLogFile.exists()) {
/* 241 */                   Log.d(TAG, "Exception while writing to the log file.");
/* 256 */                   return;
                        }
/* 245 */               Log.d(TAG, log.replace("[LVMediaCoderFactory] ", ""));
/* 246 */               if (exception) {
/* 247 */                   mPrintLogStream.println("======================= EXCEPTION [START] =======================");
/* 248 */                   mPrintLogStream.flush();
                        }
/* 250 */               mPrintLogStream.println(log);
/* 251 */               mPrintLogStream.flush();
/* 252 */               if (exception) {
/* 253 */                   mPrintLogStream.println("======================== EXCEPTION [END] ========================");
/* 254 */                   mPrintLogStream.flush();
                        }
                    }
                }

/* 258 */       public static void setDumpingEnabled(boolean dumping) {
/* 259 */           LVMediaCoderProperties.DUMPS = dumping;
/* 260 */           if (dumping) {
/* 261 */               new File("/mnt/sdcard/Twitter/").mkdir();
/* 263 */               inputVideoFile = new File("/mnt/sdcard/Twitter/Twitter_Input_Video.raw");
/* 264 */               inputAudioFile = new File("/mnt/sdcard/Twitter/Twitter_Input_Audio.raw");
/* 265 */               if (inputVideoFile.exists()) {
/* 266 */                   inputVideoFile.delete();
                        }
/* 268 */               if (inputAudioFile.exists()) {
/* 269 */                   inputAudioFile.delete();
                        }
/* 271 */               inputVideoFile = new File("/mnt/sdcard/Twitter/Twitter_Input_Video.raw");
/* 272 */               inputAudioFile = new File("/mnt/sdcard/Twitter/Twitter_Input_Audio.raw");
                    }
                }

/* 276 */       static void dumpInputVideoData(byte[] videoData) {
/* 277 */           if (LVMediaCoderProperties.DUMPS || inputVideoFile != null) {
                        try {
/* 281 */                   FileOutputStream outputStreamVideo = new FileOutputStream(inputVideoFile, true);
/* 282 */                   outputStreamVideo.write(videoData);
/* 283 */                   outputStreamVideo.flush();
/* 284 */                   outputStreamVideo.close();
                        } catch (IOException e) {
/* 286 */                   e.printStackTrace();
                        }
                    }
                }

/* 290 */       static void dumpInputAudioData(byte[] audioData) {
/* 291 */           if (LVMediaCoderProperties.DUMPS || inputAudioFile != null) {
                        try {
/* 295 */                   FileOutputStream outputStreamAudio = new FileOutputStream(inputAudioFile, true);
/* 296 */                   outputStreamAudio.write(audioData);
/* 297 */                   outputStreamAudio.flush();
/* 298 */                   outputStreamAudio.close();
                        } catch (IOException e) {
/* 300 */                   e.printStackTrace();
                        }
                    }
                }
            }
