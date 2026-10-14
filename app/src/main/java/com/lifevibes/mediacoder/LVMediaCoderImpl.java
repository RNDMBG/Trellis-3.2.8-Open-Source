            package com.lifevibes.mediacoder;

            import android.annotation.TargetApi;
            import android.content.Context;
            import android.media.MediaCodec;
            import android.media.MediaCodecInfo;
            import android.media.MediaCodecList;
            import android.media.MediaCrypto;
            import android.media.MediaFormat;
            import android.os.Build;
            import android.support.v8.renderscript.Allocation;
            import android.support.v8.renderscript.Element;
            import android.support.v8.renderscript.RenderScript;
            import android.support.v4.os.EnvironmentCompat;
            import android.view.Surface;
            import com.edisonwang.android.slog.SLog;
            import java.io.File;
            import java.io.FileOutputStream;
            import java.io.IOException;
            import java.nio.ByteBuffer;
            import java.nio.ShortBuffer;
            import java.util.ArrayList;
            import java.util.Locale;
            
            
            @TargetApi(16)
    public class LVMediaCoderImpl implements LVMediaCoder {
                private static final String AAC_ADTS = "audio/mp4a-latm";
                private static final int AUDIO_SAMPLE_SIZE = 2;
                public static final String H264_AVC = "video/avc";
                private static final int PCM_SIZE_MONO = 2048;
                private static final int PCM_SIZE_STEREO = 4096;
                private static final String TAG = "LVMediaCoderImpl";
                static boolean isRunningOnICS;
                static byte[] mAudioDSI;
                static byte[] mVideoDSI;
                private static ByteBuffer outData;
                private Allocation ain;
                private Allocation ain2;
                private Allocation ain3;
                private Allocation ain4;
                private Allocation aout;
                private boolean correctVideoBitRate;
                private boolean isAudioEncoderRunning;
                private boolean isEndOfStream;
                private boolean isRecordingSession;
                private boolean isStride512Aligned;
                private boolean isUVInterchanged;
                private boolean isUVStride2KAligned;
                private boolean isVideoEncoderRunning;
                private boolean isYUVStride2KAligned;
                private MediaCodec.BufferInfo mAudioBufferInfo;
                private MediaCodec mAudioEncoder;
                private MediaFormat mAudioFormat;
                private int mAudioFrameDuration;
                private int mAudioFramesRecorded;
                private MediaCodec.BufferInfo mBufferInfo;
                private LVMediaCoderConfiguration mConfiguration;
                private Context mContext;
                private LVMediaCoderSFAudioEncoder mICSAudioEncoder;
                private LVMediaCoderSFVideoEncoder mICSVideoEncoder;
                private LVMediaCoder.LVMediaCoderICSEncoderProgressListener mICSVideoEncoderListener;
                private ByteBuffer[] mInputAudioBuffers;
                private byte[] mInputDataToRSFilter;
                private ByteBuffer[] mInputVideoBuffers;
                private ByteBuffer mOutBuffer;
                private ByteBuffer[] mOutputAudioBuffers;
                private String mOutputFilePath;
                private byte[] mOutputFromRSFilter;
                private FileOutputStream mOutputH264Dump;
                private ByteBuffer[] mOutputVideoBuffers;
                private byte[] mPCMFrameData;
                private int mRGBABufferSize;
                private int mRGBBufferSize;
                private RenderScript mRenderScript;
                private int mRetrievedEncodedFrameCount;
                private ScriptC_Filters mScript;
                private int mSonyXperiaZ1_Height;
                private int mSonyXperiaZ1_Width;
                private int mSuppliedEncodedFrameCount;
                private ArrayList<String> mSupportedAudioCodecs;
                private int mSupportedColorFormat;
                private byte[] mVideoDataInput;
                private MediaCodec mVideoEncoder;
                private MediaFormat mVideoFormat;
                private int mVideoFramesRecorded;
                private int mWriterJNIContext;
                private int mYUVBufferSize;
                private byte[] mYUVData;
                private boolean skipExtraBytesAtStart;
                private byte[] tempBuffer;
                private byte[] tempBuffer1;
                private boolean run = false;
                private boolean pullerFlagExit = false;
                private int mVideoFrameNumber = 0;
                private int mAudioFrameNumber = 0;

        static  boolean access$000(LVMediaCoderImpl x0) {
            return x0.pullerFlagExit;
                }

        static  ByteBuffer access$100() {
            return outData;
                }

        static  ByteBuffer access$102(ByteBuffer x0) {
            outData = x0;
                    return x0;
                }

        static  LVMediaCoderSFVideoEncoder access$200(LVMediaCoderImpl x0) {
            return x0.mICSVideoEncoder;
                }

        static  int access$300(LVMediaCoderImpl x0) {
            return x0.mRetrievedEncodedFrameCount;
                }

        static  int access$308(LVMediaCoderImpl x0) {
            int i = x0.mRetrievedEncodedFrameCount;
                    x0.mRetrievedEncodedFrameCount = i + 1;
                    return i;
                }

        static  LVMediaCoder.LVMediaCoderICSEncoderProgressListener access$400(LVMediaCoderImpl x0) {
            return x0.mICSVideoEncoderListener;
                }

        static  boolean access$500(LVMediaCoderImpl x0) {
            return x0.isEndOfStream;
                }

        static  int access$600(LVMediaCoderImpl x0) {
            return x0.mSuppliedEncodedFrameCount;
                }

        static  void access$700(LVMediaCoderImpl x0) {
            x0.cancelEncodingICS();
                }

       public LVMediaCoderImpl(Context context, LVMediaCoderConfiguration configuration) throws NullPointerException {
           if (context == null) {
               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Context is NULL.", true);
               throw new NullPointerException("Context is NULL.");
                    }
           if (configuration == null) {
               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Configuration is NULL.", true);
               throw new NullPointerException("Configuration is NULL.");
                    }
           this.mConfiguration = configuration;
           if (!isRunningOnICS) {
               configure(context, this.mConfiguration);
                    }
                }

       private void configure(Context context, LVMediaCoderConfiguration configuration) {
           LVMediaCoderFactory.printLog("[LVMediaCoderImpl] configure called!", false);
           this.mContext = context;
           this.mRGBBufferSize = this.mConfiguration.videoWidth * this.mConfiguration.videoHeight * 3;
           this.mRGBABufferSize = this.mConfiguration.videoWidth * this.mConfiguration.videoHeight * 4;
           this.mYUVBufferSize = this.mRGBBufferSize / 2;
           checkDevice();
           checkAudioCodecs();
           this.mVideoFormat = MediaFormat.createVideoFormat("video/avc", this.mConfiguration.videoWidth, this.mConfiguration.videoHeight);
           this.mVideoFormat.setInteger("color-format", getColorFormat());
           if (this.correctVideoBitRate) {
               this.mVideoFormat.setInteger("bitrate", this.mConfiguration.videoBitRate * 8);
                    } else {
               this.mVideoFormat.setInteger("bitrate", this.mConfiguration.videoBitRate);
                    }
           this.mVideoFormat.setInteger("frame-rate", this.mConfiguration.videoFrameRate);
           this.mVideoFormat.setInteger("i-frame-interval", this.mConfiguration.videoIFrameInterval);
           this.mVideoFormat.setInteger("aac-profile", 1);
           this.mAudioFormat = new MediaFormat();
           this.mAudioFormat.setString("mime", "audio/mp4a-latm");
           this.mAudioFormat.setInteger("bitrate", this.mConfiguration.audioBitRate);
           this.mAudioFormat.setInteger("channel-count", this.mConfiguration.audioChannelCount);
           this.mAudioFormat.setInteger("sample-rate", this.mConfiguration.audioSampleRate);
           this.mAudioFormat.setInteger("aac-profile", 2);
                }

       private int getColorFormat() {
           int index = 0;
           ArrayList<MediaCodecInfo> compatibleEncoders = new ArrayList<>();
                    for (int i = 0; i < MediaCodecList.getCodecCount(); i++) {
               MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
               if (codecInfo.isEncoder()) {
                   String[] supportedTypes = codecInfo.getSupportedTypes();
                            for (String type : supportedTypes) {
                       if (type.toLowerCase(Locale.ENGLISH).contains("avc")) {
                           compatibleEncoders.add(codecInfo);
                           index = i;
                                    break;
                                }
                            }
                        }
                    }
           MediaCodecInfo.CodecCapabilities capabilities = MediaCodecList.getCodecInfoAt(index).getCapabilitiesForType("video/avc");
           int colorFormat = 0;
                    for (int i2 = 0; i2 < capabilities.colorFormats.length; i2++) {
               int format = capabilities.colorFormats[i2];
                        switch (format) {
                            case 19:
                            case 20:
                            case 21:
                            case 39:
                            case 2130706688:
                       colorFormat = format;
                                break;
                            default:
                       LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Skipping unsupported color format " + format, false);
                                break;
                        }
                    }
           this.mSupportedColorFormat = colorFormat;
           LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Color format selected: " + this.mSupportedColorFormat, false);
           return colorFormat;
                }

       private void checkAudioCodecs() {
           this.mSupportedAudioCodecs = new ArrayList<>();
                    for (int i = 0; i < MediaCodecList.getCodecCount(); i++) {
               MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Encoder " + i + ": " + codecInfo.getName(), false);
               if (codecInfo.isEncoder()) {
                   String[] supportedTypes = codecInfo.getSupportedTypes();
                            for (String type : supportedTypes) {
                       if (type.toLowerCase(Locale.ENGLISH).contains("mp4a-latm")) {
                           this.mSupportedAudioCodecs.add(codecInfo.getName());
                           LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Encoder " + i + "/" + MediaCodecList.getCodecCount() + ": " + codecInfo.getName() + " supports [" + type + "]", false);
                                    break;
                                }
                            }
                        }
                    }
                }

       private void checkDevice() {
           if ((Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("lge") && Build.BOARD.toLowerCase(Locale.ENGLISH).contains("hammerhead")) || Build.BOARD.toLowerCase(Locale.ENGLISH).contains("msm8974") || (Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("lge") && Build.MODEL.toLowerCase(Locale.ENGLISH).contains("lg-d802"))) {
               this.correctVideoBitRate = true;
                    }
           if (Build.VERSION.SDK_INT >= 19) {
               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on Android version: " + Build.VERSION.SDK_INT + ". No need of special color-conv filters.", false);
               return;
                    }
           if (Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("asus") && Build.BOARD.equalsIgnoreCase("flo")) {
               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on ASUS on flo board!", false);
               this.isYUVStride2KAligned = true;
               return;
                    }
           if (Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("motorola") && Build.BOARD.equalsIgnoreCase("msm8960")) {
               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on Motorola on msm8960 board!", false);
               this.isUVStride2KAligned = true;
               return;
                    }
           if (Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("htc") && Build.MODEL.toLowerCase(Locale.ENGLISH).contains("htc one")) {
               if (Build.BOARD.toLowerCase(Locale.ENGLISH).equals("apq8064")) {
                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on HTC One [APQ8064] with 2048 bit align stride!", false);
                   this.isYUVStride2KAligned = true;
                   return;
                        } else if (Build.HARDWARE.equals("m7")) {
                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on HTC One [APQ8064T] with 2048 bit align stride!", false);
                   this.isUVStride2KAligned = true;
                   return;
                        } else {
                   if (Build.HARDWARE.equals("elite") || Build.HARDWARE.equals("ville")) {
                       LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on HTC One X/S with 2048 bit align stride!", false);
                       this.isUVStride2KAligned = true;
                       return;
                            }
                   return;
                        }
                    }
           if (Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("sony")) {
               if (Build.BOARD.equalsIgnoreCase("MSM8660_SURF")) {
                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on SONY device with 2048 bit align stride!", false);
                   this.isYUVStride2KAligned = true;
                        } else if (Build.BOARD.toLowerCase(Locale.ENGLISH).contains("msm8974")) {
                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on SONY XPeria Z1 with 512 bit align stride!", false);
                   this.isStride512Aligned = true;
                        }
               if (Build.BOARD.equalsIgnoreCase("MSM8226")) {
                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on SONY XPeria Z1 with bit rate correction!", false);
                   this.correctVideoBitRate = true;
                   return;
                        }
               return;
                    }
           if (Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("lge")) {
               if (Build.BOARD.equalsIgnoreCase("MAKO")) {
                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on LGE Nexus 4 with 2048 bit aligned stride!", false);
                   this.isYUVStride2KAligned = true;
                   return;
                        }
               if (Build.MODEL.toLowerCase(Locale.ENGLISH).contains("lg-d802")) {
                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on LGE G2 with 512 bit aligned stride!", false);
                   this.isStride512Aligned = true;
                   return;
                        } else if (Build.BOARD.equalsIgnoreCase("gee")) {
                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on LG device on gee board!", false);
                   this.isUVStride2KAligned = true;
                   return;
                        } else {
                   if (Build.BOARD.equalsIgnoreCase("u2")) {
                       LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on LG device that returns extra zeores with 1st encoded frame!", false);
                       this.skipExtraBytesAtStart = true;
                       return;
                            }
                   return;
                        }
                    }
           if (Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("samsung")) {
               if (Build.BOARD.toLowerCase(Locale.ENGLISH).contains("smdk4x12") && Build.MODEL.toLowerCase(Locale.ENGLISH).contains("gt-i9300") && Build.VERSION.SDK_INT < 18) {
                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on Samsung smdk4x12 with UV-planes interchanged!", false);
                   this.isUVInterchanged = true;
                   return;
                        } else if (Build.BOARD.equalsIgnoreCase("msm8960")) {
                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on Samsung MSM8960 board!", false);
                   this.isUVStride2KAligned = true;
                   return;
                        } else {
                   if (Build.BOARD.equalsIgnoreCase("piranha")) {
                       LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on Samsung Piranha board!", false);
                       this.skipExtraBytesAtStart = true;
                       return;
                            }
                   return;
                        }
                    }
           if (Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("huawei") && Build.BOARD.toLowerCase(Locale.ENGLISH).contains("y301a1")) {
               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on Huawei device, which has UV planes interleaved!", false);
               this.isUVInterchanged = true;
                    } else if (Build.BOARD.toLowerCase(Locale.ENGLISH).contains("msm8974")) {
               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on Qualcomm MSM8974!", false);
               this.isYUVStride2KAligned = true;
                    }
                }

                @Override 
       public void setEncoderVideoAsyncListener(LVMediaCoder.LVMediaCoderICSEncoderProgressListener listener) {
           this.mICSVideoEncoderListener = listener;
                }

                @Override 
       public void encodeVideoAsync(ByteBuffer inputFrame, int inputType, boolean isEoS) throws IllegalStateException {
           if (!this.isVideoEncoderRunning) {
               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeVideo(): LVMediaCoder is not running.", true);
               throw new IllegalStateException("LVMediaCoder is not running.");
                    }
           if (isRunningOnICS) {
               if (!this.run) {
                   encodeVideoAsyncPull();
                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Starting encodeVideoAsyncPull()", false);
                   this.run = true;
                        }
               this.isEndOfStream = isEoS;
               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Sending RGB frame " + this.mSuppliedEncodedFrameCount, false);
               encodeICSVideo(inputFrame, inputType, isEoS);
               this.mSuppliedEncodedFrameCount++;
               return;
                    }
           throw new IllegalStateException("Method encodeVideoAsync is not supported on JB and above.");
                }

       private void encodeVideoAsyncPull() throws IllegalStateException {
           if (isRunningOnICS) {
                        synchronized (this) {
                            try {
                       new Thread(new RunnableC12471()).start();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

                
       class RunnableC12471 implements Runnable {
           RunnableC12471() {
                    }

                    @Override 
           public void run() {
               boolean first = true;
               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] PULLER +++++++++++++++++++++++++++++++++++++++++++++++++++++++++", false);
               while (!LVMediaCoderImpl.access$000(LVMediaCoderImpl.this)) {
                            try {
                       LVMediaCoderImpl.access$102(LVMediaCoderImpl.access$200(LVMediaCoderImpl.this).pollEncoderForOutput());
                       if (LVMediaCoderImpl.access$100() == null) {
                           LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Puller: Encoder output is null ##########", false);
                                } else if (first) {
                           first = false;
                           LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Puller: Length of Encoder output (DSI): " + LVMediaCoderImpl.access$300(LVMediaCoderImpl.this) + " DSI ##########" + LVMediaCoderImpl.access$100().array().length, false);
                                } else {
                           byte[] startCode = LVMediaCoderImpl.access$100().array();
                           if (startCode.length > 4) {
                               if (startCode[4] != 10) {
                                   LVMediaCoderImpl.access$400(LVMediaCoderImpl.this).onFrameAvailable(LVMediaCoderImpl.access$100());
                                   LVMediaCoderImpl.access$308(LVMediaCoderImpl.this);
                                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Puller: Length of Encoder output: " + LVMediaCoderImpl.access$300(LVMediaCoderImpl.this) + " ##########" + LVMediaCoderImpl.access$100().array().length, false);
                                   if (LVMediaCoderImpl.access$500(LVMediaCoderImpl.this) && LVMediaCoderImpl.access$300(LVMediaCoderImpl.this) == LVMediaCoderImpl.access$600(LVMediaCoderImpl.this)) {
                                       LVMediaCoderImpl.access$400(LVMediaCoderImpl.this).onComplete();
                                            }
                                        }
                                    } else {
                               LVMediaCoderImpl.access$400(LVMediaCoderImpl.this).onError(new Exception("Black frame encountered"), -1);
                                    }
                                }
                       Thread.sleep(10L);
                            } catch (Exception e) {
                       LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeVideoPoll(): returned: Exception: " + e.getMessage(), true);
                       LVMediaCoderImpl.access$700(LVMediaCoderImpl.this);
                       throw new IllegalStateException(e);
                            }
                        }
               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] STOP DONE ----------------------------", false);
                    }
                }

       private ByteBuffer encodeICSVideo(ByteBuffer inputFrame, int inputType, boolean isEoS) throws IllegalStateException {
           if (inputType == 0 && inputFrame.remaining() != this.mRGBBufferSize) {
               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeVideo(): Improper size of input RGB data.", true);
               throw new IllegalArgumentException("Improper size of input RGB data.");
                    }
           if (inputType == 1 && inputFrame.remaining() != this.mRGBABufferSize) {
               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeVideo(): Improper size of input RGBA data.", true);
               throw new IllegalArgumentException("Improper size of input RGBA data.");
                    }
           LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeVideo(): Called.", false);
           byte[] yuvData = convertICSRGBToYUV(inputFrame, inputType);
           if (LVMediaCoderProperties.DEBUG) {
                        try {
                   writeYUVToFile(yuvData);
                        } catch (Exception e) {
                   if (SLog.sLogsOn) {
                       e.printStackTrace();
                            }
                   throw new IllegalStateException(e.getMessage());
                        }
                    }
           this.mICSVideoEncoder.encode(yuvData, isEoS);
           return null;
                }

                @Override 
       public ByteBuffer encodeVideo(ByteBuffer inputFrame, int inputType) throws IllegalStateException {
           LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeVideo(): Called.", false);
           if (isRunningOnICS) {
               throw new IllegalStateException("LVMediaCoder is running on ICS. Call encodeVideoAsync instead.");
                    }
           if (!this.isVideoEncoderRunning) {
               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeVideo(): LVMediaCoder is not running.", true);
               throw new IllegalStateException("LVMediaCoder is not running.");
                    }
           if (inputType == 0 && inputFrame.remaining() != this.mRGBBufferSize) {
               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeVideo(): Improper size of input RGB data.", true);
               throw new IllegalArgumentException("Improper size of input RGB data.");
                    }
           if (inputType == 1 && inputFrame.remaining() != this.mRGBABufferSize) {
               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeVideo(): Improper size of input RGBA data." + inputFrame.remaining() + " bytes.", true);
               throw new IllegalArgumentException("Improper size of input RGBA data.");
                    }
           byte[] yuvData = convertRGBToYUV(inputFrame, inputType);
           if (LVMediaCoderProperties.DEBUG) {
                        try {
                   writeYUVToFile(yuvData);
                        } catch (Exception e) {
                   if (SLog.sLogsOn) {
                       e.printStackTrace();
                            }
                   throw new IllegalStateException(e.getMessage());
                        }
                    }
                    try {
               ByteBuffer buffer = encodeYUVToH264(yuvData);
               if (LVMediaCoderProperties.DEBUG) {
                            try {
                       dumpH264DataToFile(buffer.array());
                            } catch (Exception e2) {
                       if (SLog.sLogsOn) {
                           e2.printStackTrace();
                                }
                       throw new IllegalStateException(e2.getMessage());
                            }
                        }
               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeVideo(): Returning " + buffer.array().length + " bytes.", false);
               return buffer;
                    } catch (Exception e1) {
               if (SLog.sLogsOn) {
                   e1.printStackTrace();
                        }
               throw new IllegalStateException(e1.getMessage());
                    }
                }

       private void dumpH264DataToFile(byte[] h264Data) throws Exception {
                    try {
               new File("/mnt/sdcard/Twitter/").mkdir();
               File file = new File("/mnt/sdcard/Twitter/Encoded Video.h264");
               this.mOutputH264Dump = new FileOutputStream(file, true);
               this.mOutputH264Dump.write(h264Data);
               this.mOutputH264Dump.flush();
                    } catch (Exception e) {
               if (SLog.sLogsOn) {
                   e.printStackTrace();
                        }
               throw new Exception("Error while dumping H264 data to file: " + e.getMessage());
                    }
                }

       private void writeYUVToFile(byte[] yuvData) throws Exception {
           File file = new File("mnt/sdcard/Twitter/");
           file.mkdirs();
           File file2 = new File("mnt/sdcard/Twitter/YUV_CF_" + this.mSupportedColorFormat + ".yuv");
                    try {
               FileOutputStream fileOutputStream = new FileOutputStream(file2, true);
               fileOutputStream.write(yuvData);
               fileOutputStream.flush();
               fileOutputStream.close();
               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] writeYUVToFile(): " + yuvData.length + " bytes written to file.", false);
                    } catch (Exception e) {
               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] writeYUVToFile(): Error writting " + yuvData.length + " bytes to file.", true);
               throw new Exception("Error writting " + yuvData.length + " bytes to file: " + e.getMessage());
                    }
                }

       private byte[] convertRGBToYUVinRS(ByteBuffer inputFrame, int inputType) {
           if (this.mVideoDataInput == null) {
               if (inputType == 0) {
                   this.mVideoDataInput = new byte[this.mRGBBufferSize];
                        } else if (inputType == 1) {
                   this.mVideoDataInput = new byte[this.mRGBABufferSize];
                        }
                    }
           inputFrame.get(this.mVideoDataInput);
           if (LVMediaCoderProperties.DUMPS) {
                        LVMediaCoderFactory.dumpInputVideoData(this.mVideoDataInput);
                    }
           if (this.mYUVData == null) {
               if (this.isStride512Aligned) {
                   this.mYUVData = new byte[368640];
                        } else {
                   this.mYUVData = new byte[this.mYUVBufferSize];
                        }
                    }
           if (inputType == 0) {
               if (this.mInputDataToRSFilter == null) {
                   this.mInputDataToRSFilter = new byte[this.mVideoDataInput.length + (this.mVideoDataInput.length / 3)];
                        }
               int i = 0;
                        int j = 0;
                        while (i < this.mVideoDataInput.length) {
                   this.mInputDataToRSFilter[j] = this.mVideoDataInput[i];
                   this.mInputDataToRSFilter[j + 1] = this.mVideoDataInput[i + 1];
                   this.mInputDataToRSFilter[j + 2] = this.mVideoDataInput[i + 2];
                   i += 3;
                            j += 4;
                        }
                    } else {
               if (this.mInputDataToRSFilter == null) {
                   this.mInputDataToRSFilter = new byte[this.mVideoDataInput.length];
                        }
               this.mInputDataToRSFilter = this.mVideoDataInput;
                    }
           if (this.mOutputFromRSFilter == null) {
               this.mOutputFromRSFilter = new byte[this.mInputDataToRSFilter.length];
                    }
           long startTime = System.currentTimeMillis();
           if (this.ain == null || this.aout == null) {
               Element e = Element.RGB_888(this.mRenderScript);
               this.ain = Allocation.createSized(this.mRenderScript, e, this.mInputDataToRSFilter.length / 4);
               this.ain2 = Allocation.createSized(this.mRenderScript, Element.U8(this.mRenderScript), this.mVideoDataInput.length);
               this.ain3 = Allocation.createSized(this.mRenderScript, Element.U8(this.mRenderScript), this.mVideoDataInput.length);
               this.ain4 = Allocation.createSized(this.mRenderScript, Element.U8(this.mRenderScript), 460800);
               this.aout = Allocation.createSized(this.mRenderScript, e, this.mInputDataToRSFilter.length / 4);
               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Time taken for setting up RS: " + (System.currentTimeMillis() - startTime), false);
                    }
           if (this.tempBuffer == null) {
               this.tempBuffer = new byte[691200];
               this.tempBuffer1 = new byte[460800];
                    }
           this.ain2.copyFrom(this.mVideoDataInput);
           this.mScript.set_input(this.ain2);
           this.mScript.forEach_RGB888YUV444(this.ain);
           this.mScript.set_output(this.ain3);
           this.mScript.forEach_YUVPackedToPlanar(this.ain, this.aout);
           this.mScript.set_output1(this.ain3);
           this.mScript.set_output2(this.ain4);
           if (this.mSupportedColorFormat == 19) {
               this.mScript.forEach_YUV444ToYUV420(this.ain4);
                    } else if (this.mSupportedColorFormat == 21) {
               this.mScript.forEach_YUV444ToYUV420SemiPlanar(this.ain4);
                    } else {
               throw new IllegalStateException("RenderScript doesn't have color conversion filter for Color Format: " + this.mSupportedColorFormat);
                    }
           this.ain3.copyTo(this.tempBuffer);
           this.ain4.copyTo(this.tempBuffer1);
           System.arraycopy(this.tempBuffer, 0, this.mYUVData, 0, 230400);
           System.arraycopy(this.tempBuffer1, 0, this.mYUVData, 230400, 115200);
           return this.mYUVData;
                }

       private byte[] convertICSRGBToYUV(ByteBuffer inputFrame, int inputType) {
           this.mRGBBufferSize = this.mConfiguration.videoWidth * this.mConfiguration.videoHeight * 3;
           this.mRGBABufferSize = this.mConfiguration.videoWidth * this.mConfiguration.videoHeight * 4;
           this.mYUVBufferSize = this.mRGBBufferSize / 2;
           if (this.mVideoDataInput == null) {
               if (inputType == 0) {
                   this.mVideoDataInput = new byte[this.mRGBBufferSize];
                        } else if (inputType == 1) {
                   this.mVideoDataInput = new byte[this.mRGBABufferSize];
                        }
                    }
           this.mYUVData = new byte[this.mRGBBufferSize / 2];
           inputFrame.get(this.mVideoDataInput);
           if (LVMediaCoderProperties.DUMPS) {
                        LVMediaCoderFactory.dumpInputVideoData(this.mVideoDataInput);
                    }
           LVMediaCoderWriterJNI.RGB888toYUV420WithPlanar(this.mVideoDataInput, this.mYUVData, this.mConfiguration.videoWidth, this.mConfiguration.videoHeight, inputType);
           return this.mYUVData;
                }

       private byte[] convertRGBToYUV(ByteBuffer inputFrame, int inputType) {
           if (this.mVideoDataInput == null) {
               if (inputType == 0) {
                   this.mVideoDataInput = new byte[this.mRGBBufferSize];
                        } else if (inputType == 1) {
                   this.mVideoDataInput = new byte[this.mRGBABufferSize];
                        }
                    }
           inputFrame.get(this.mVideoDataInput);
           if (LVMediaCoderProperties.DUMPS) {
                        LVMediaCoderFactory.dumpInputVideoData(this.mVideoDataInput);
                    }
           if (this.isStride512Aligned) {
               if (this.mYUVData == null) {
                   if (this.mConfiguration.videoWidth % 128 != 0) {
                       this.mSonyXperiaZ1_Width = ((this.mConfiguration.videoWidth / 128) + 1) * 128;
                            } else {
                       this.mSonyXperiaZ1_Width = this.mConfiguration.videoWidth;
                            }
                   if (this.mConfiguration.videoHeight % 32 != 0) {
                       this.mSonyXperiaZ1_Height = ((this.mConfiguration.videoHeight / 32) + 1) * 32;
                            } else {
                       this.mSonyXperiaZ1_Height = this.mConfiguration.videoHeight;
                            }
                   this.mYUVData = new byte[((this.mSonyXperiaZ1_Width * this.mSonyXperiaZ1_Height) * 3) / 2];
                        }
               LVMediaCoderWriterJNI.RGB888toYUV420WithStride512Aligned(this.mVideoDataInput, this.mYUVData, this.mSonyXperiaZ1_Width, this.mSonyXperiaZ1_Height, this.mConfiguration.videoWidth, this.mConfiguration.videoHeight, inputType);
                    } else if (this.isYUVStride2KAligned) {
               if (this.mYUVData == null) {
                   int lgeNexus4StrideOffSet = (this.mConfiguration.videoWidth * this.mConfiguration.videoHeight) % 2048;
                   if (lgeNexus4StrideOffSet > 0) {
                       lgeNexus4StrideOffSet = 2048 - lgeNexus4StrideOffSet;
                            }
                   int UVOffset = ((this.mConfiguration.videoWidth * this.mConfiguration.videoHeight) / 2) % 2048;
                   if (UVOffset > 0) {
                       lgeNexus4StrideOffSet += 2048 - UVOffset;
                            }
                   this.mYUVData = new byte[this.mYUVBufferSize + lgeNexus4StrideOffSet];
                        }
               LVMediaCoderWriterJNI.RGB888toYUV420WithSemiPlanar(this.mVideoDataInput, this.mYUVData, this.mConfiguration.videoWidth, this.mConfiguration.videoHeight, inputType);
                    } else if (this.isUVStride2KAligned) {
               if (this.mYUVData == null) {
                   int lgeNexus4StrideOffSet2 = (this.mConfiguration.videoWidth * this.mConfiguration.videoHeight) % 2048;
                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Calling HTCOne stride:" + lgeNexus4StrideOffSet2, false);
                   if (lgeNexus4StrideOffSet2 > 0) {
                       lgeNexus4StrideOffSet2 = 2048 - lgeNexus4StrideOffSet2;
                            }
                   this.mYUVData = new byte[this.mYUVBufferSize + lgeNexus4StrideOffSet2];
                        }
               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Calling HTCOne filter....", false);
               LVMediaCoderWriterJNI.RGB888toYUV420WithUVStride2KAligned(this.mVideoDataInput, this.mYUVData, this.mConfiguration.videoWidth, this.mConfiguration.videoHeight, inputType);
                    } else {
               if (this.mYUVData == null) {
                   this.mYUVData = new byte[this.mYUVBufferSize];
                        }
               if (this.mSupportedColorFormat == 19) {
                   LVMediaCoderWriterJNI.RGB888toYUV420WithPlanar(this.mVideoDataInput, this.mYUVData, this.mConfiguration.videoWidth, this.mConfiguration.videoHeight, inputType);
                        } else if (this.isUVInterchanged) {
                   LVMediaCoderWriterJNI.RGB888toYUV420WithUVInterchanged(this.mVideoDataInput, this.mYUVData, this.mConfiguration.videoWidth, this.mConfiguration.videoHeight, inputType);
                        } else {
                   LVMediaCoderWriterJNI.RGB888toYUV420WithSemiPlanar(this.mVideoDataInput, this.mYUVData, this.mConfiguration.videoWidth, this.mConfiguration.videoHeight, inputType);
                        }
                    }
           return this.mYUVData;
                }

       private ByteBuffer encodeYUVToH264(byte[] yuvData) throws Exception {
                    int size;
           if (yuvData == null) {
               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeYUVToH264(): YUV Data is null", true);
               throw new IllegalArgumentException("YUV Data is null");
                    }
           boolean doneSubmittingInput = false;
                    while (true) {
               if (!doneSubmittingInput) {
                   int inputBufferIndex = this.mVideoEncoder.dequeueInputBuffer(1L);
                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeYUVToH264(): Video InputBufferIndex : " + inputBufferIndex + " for frame# " + this.mVideoFrameNumber, false);
                   if (inputBufferIndex != -1 && inputBufferIndex >= 0) {
                       this.mInputVideoBuffers[inputBufferIndex].clear();
                       this.mInputVideoBuffers[inputBufferIndex].put(yuvData);
                       this.mVideoEncoder.queueInputBuffer(inputBufferIndex, 0, yuvData.length, this.mVideoFrameNumber * 33, 0);
                       doneSubmittingInput = true;
                       this.mVideoFrameNumber++;
                            }
                        }
               if (doneSubmittingInput) {
                   if (this.mBufferInfo == null) {
                       this.mBufferInfo = new MediaCodec.BufferInfo();
                            }
                   int outputBufferIndex = this.mVideoEncoder.dequeueOutputBuffer(this.mBufferInfo, 0L);
                   if (SLog.sLogsOn) {
                       LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeYUVToH264(): Video OutputBufferIndex : " + outputBufferIndex + " for frame# " + this.mVideoFrameNumber, false);
                            }
                   if (outputBufferIndex != -1 && outputBufferIndex != -2) {
                       if (outputBufferIndex == -3) {
                           this.mOutputVideoBuffers = this.mVideoEncoder.getOutputBuffers();
                                } else {
                           ByteBuffer outputBuffer = this.mOutputVideoBuffers[outputBufferIndex];
                           boolean isDsi = this.skipExtraBytesAtStart && this.mVideoFrameNumber == 3;
                           if (isDsi) {
                               size = this.mBufferInfo.size + mVideoDSI.length;
                               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] OutputBufferSize: " + outputBuffer.remaining() + "mBufferInfo.size: " + this.mBufferInfo.size + "mVideoDSI.length: " + mVideoDSI.length, false);
                                    } else {
                               size = this.mBufferInfo.size;
                                    }
                           if (this.mOutBuffer == null || this.mOutBuffer.capacity() < size) {
                               this.mOutBuffer = ByteBuffer.allocate(size);
                                    }
                           this.mOutBuffer.position(0);
                           this.mOutBuffer.limit(size);
                                    for (int i = 0; i < size; i++) {
                               this.mOutBuffer.put(outputBuffer.get());
                                    }
                           outputBuffer.clear();
                           if ((this.mBufferInfo.flags & 4) != 0) {
                               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeYUVToH264(): BUFFER_FLAG_END_OF_STREAM. Breaking out!!!!", false);
                                        break;
                                    }
                           if (this.mOutBuffer.get(0) != 0 || this.mOutBuffer.get(1) != 0 || this.mOutBuffer.get(2) != 0 || this.mOutBuffer.get(3) != 1) {
                               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeYUVToH264(): Header missing! for Frame: " + this.mVideoFrameNumber, false);
                                    }
                           this.mVideoEncoder.releaseOutputBuffer(outputBufferIndex, false);
                           this.mOutBuffer.position(0);
                           if (this.mVideoFrameNumber == 1) {
                               mVideoDSI = new byte[this.mOutBuffer.limit()];
                               this.mOutBuffer.get(mVideoDSI);
                               if (this.isRecordingSession && LVMediaCoderProperties.DEBUG) {
                                   new File("/mnt/sdcard/Twitter/").mkdir();
                                   File file = new File("/mnt/sdcard/Twitter/Encoded Video.h264");
                                            try {
                                       this.mOutputH264Dump = new FileOutputStream(file);
                                       this.mOutputH264Dump.write(mVideoDSI);
                                       this.mOutputH264Dump.flush();
                                       this.mOutputH264Dump.close();
                                            } catch (IOException e) {
                                       if (SLog.sLogsOn) {
                                           e.printStackTrace();
                                                }
                                       throw new Exception("Error while writing VideoDSI to debug file: " + e.getMessage());
                                            }
                                        }
                               this.mVideoFrameNumber++;
                               doneSubmittingInput = false;
                                    } else if (this.mVideoFrameNumber > 1) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
           LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeYUVToH264(): Returning " + this.mOutBuffer.array().length + " bytes.", false);
           return this.mOutBuffer;
                }

                @Override 
       public void cancel() throws IllegalStateException {
           if (isRunningOnICS) {
               cancelEncodingICS();
                    } else if (this.mVideoEncoder == null || !this.isVideoEncoderRunning || this.mAudioEncoder == null || !this.isAudioEncoderRunning) {
               throw new IllegalStateException("Encoder is not running.");
                    }
           stop();
           if (this.isRecordingSession) {
               deleteOutputFile();
                    }
                }

       private void cancelEncodingICS() {
           if (!this.isVideoEncoderRunning || !this.isAudioEncoderRunning) {
               throw new IllegalStateException("Encoder is not running.");
                    }
           this.mSuppliedEncodedFrameCount = this.mRetrievedEncodedFrameCount;
                }

       private void deleteOutputFile() {
           File file = new File(this.mOutputFilePath);
           if (file.exists()) {
               file.delete();
                    }
                }

                @Override 
       public void startEncoding() {
           if (this.isAudioEncoderRunning || this.isVideoEncoderRunning) {
               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] LVMediaCoder is already running.", true);
               throw new IllegalStateException("LVMediaCoder is already running.");
                    }
           this.isRecordingSession = false;
           if (isRunningOnICS) {
               configureLVSFSession();
               return;
                    }
                    try {
               configureSession();
                    } catch (Exception e) {
               if (SLog.sLogsOn) {
                   e.printStackTrace();
                        }
               throw new IllegalStateException(e.getMessage());
                    }
                }

                @Override 
       public void startRecording(String outputFilePath) {
           if (this.isAudioEncoderRunning || this.isVideoEncoderRunning) {
               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] LVMediaCoder is already running.", true);
               throw new IllegalStateException("LVMediaCoder is already running.");
                    }
           if (outputFilePath == null || outputFilePath.equals("")) {
               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Invalid output filepath", true);
               throw new IllegalArgumentException("Invalid output filepath parameter.");
                    }
           this.mOutputFilePath = outputFilePath;
           String[] directories = this.mOutputFilePath.split("/");
           String outputDirectory = "";
                    for (int i = 0; i < directories.length - 1; i++) {
               outputDirectory = (outputDirectory + "/") + directories[i];
                    }
           new File(outputDirectory).mkdirs();
           this.isRecordingSession = true;
           if (isRunningOnICS) {
               configureLVSFSession();
                    } else {
                        try {
                   configureSession();
                        } catch (Exception e) {
                   if (SLog.sLogsOn) {
                       e.printStackTrace();
                            }
                   throw new IllegalStateException(e.getMessage());
                        }
                    }
          if (this.mAudioFrameDuration == 0) {
              int pcmSize = 1024;
              if (this.mConfiguration.audioChannelCount == 2) {
                  pcmSize = 2048;
                        }
              this.mAudioFrameDuration = ((pcmSize * 2) * 1000) / ((this.mConfiguration.audioSampleRate * this.mConfiguration.audioChannelCount) * 2);
                    }
          this.mWriterJNIContext = LVMediaCoderWriterJNI.LVMediaCoderInitWriter(this.mOutputFilePath);
          if (this.mWriterJNIContext == 0) {
              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] LVMediaCoderInitWriter failed! " + outputFilePath, true);
              throw new IllegalArgumentException("LVMediaCoderInitWriter failed! " + outputFilePath);
                    }
          if (mVideoDSI == null) {
              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] startRecording(): Problem getting the Video DSI.", true);
              throw new IllegalStateException("Problem getting the Video DSI.");
                    }
          if (mAudioDSI == null) {
              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] startRecording(): Problem getting the Audio DSI", true);
              throw new IllegalStateException("Problem getting the Audio DSI.");
                    }
          LVMediaCoderWriterJNI.LVMediaCoderWriterAddStreamVideo(this.mWriterJNIContext, mVideoDSI, this.mConfiguration.videoWidth, this.mConfiguration.videoHeight, mVideoDSI.length);
          LVMediaCoderWriterJNI.LVMediaCoderWriterAddStreamAudio(this.mWriterJNIContext, mAudioDSI, mAudioDSI.length, this.mConfiguration.audioBitRate, this.mConfiguration.audioSampleRate, this.mConfiguration.audioChannelCount);
                }

      private void generateVideoDSI() throws Exception {
          this.mVideoEncoder = MediaCodec.createEncoderByType("video/avc");
          this.mVideoEncoder.configure(this.mVideoFormat, (Surface) null, (MediaCrypto) null, 1);
          this.mVideoEncoder.start();
          this.mInputVideoBuffers = this.mVideoEncoder.getInputBuffers();
          this.mOutputVideoBuffers = this.mVideoEncoder.getOutputBuffers();
          byte[] yuvData = new byte[this.mYUVBufferSize];
                    try {
              encodeYUVToH264(yuvData);
              this.mVideoEncoder.stop();
              this.mVideoEncoder.release();
              this.mVideoEncoder = null;
              this.mInputVideoBuffers = null;
              this.mOutputVideoBuffers = null;
              this.mVideoFrameNumber = 0;
              this.isVideoEncoderRunning = false;
                    } catch (Exception e) {
              if (SLog.sLogsOn) {
                  e.printStackTrace();
                        }
              throw e;
                    }
                }

      private void generateAudioDSI() {
          mAudioDSI = new byte[]{18, -8};
                }

      private void configureLVSFSession() {
          this.mRGBBufferSize = this.mConfiguration.videoWidth * this.mConfiguration.videoHeight * 3;
          this.mRGBABufferSize = this.mConfiguration.videoWidth * this.mConfiguration.videoHeight * 4;
          this.mYUVBufferSize = this.mRGBBufferSize / 2;
          this.mICSVideoEncoder = LVMediaCoderSFVideoEncoder.create("video/avc");
          this.mICSVideoEncoder.configure(this.mConfiguration.videoHeight, this.mConfiguration.videoWidth, this.mConfiguration.videoBitRate, this.mConfiguration.videoFrameRate);
          this.mICSVideoEncoder.start();
          this.isVideoEncoderRunning = true;
          this.mRetrievedEncodedFrameCount = 0;
          this.mSuppliedEncodedFrameCount = 0;
          this.mICSAudioEncoder = LVMediaCoderSFAudioEncoder.create("audio/mp4a-latm");
          this.mICSAudioEncoder.configure(this.mConfiguration.audioBitRate, this.mConfiguration.audioSampleRate, this.mConfiguration.audioChannelCount);
          this.mICSAudioEncoder.start();
          this.isAudioEncoderRunning = true;
          this.mICSAudioEncoder.getDSI();
                }

      private void configureSession() throws Exception {
          LVMediaCoderFactory.printLog("[LVMediaCoderImpl] \nBOARD = " + Build.BOARD + "\nBOOTLOADER = " + Build.BOOTLOADER + "\nBRAND = " + Build.BRAND + "\nCPU_ABI = " + Build.CPU_ABI + "\nCPU_ABI2 = " + Build.CPU_ABI2 + "\nDEVICE = " + Build.DEVICE + "\nAndroid Version = " + Build.VERSION.SDK_INT + "\nFINGERPRINT = " + Build.FINGERPRINT + "\nHARDWARE = " + Build.HARDWARE + "\nHOST = " + Build.HOST + "\nID = " + Build.ID + "\nMANUFACTURER = " + Build.MANUFACTURER + "\nMODEL = " + Build.MODEL + "\nPRODUCT = " + Build.PRODUCT + "\nSERIAL = " + Build.SERIAL + "\nTAGS = " + Build.TAGS + "\nTIME = " + Build.TIME + "\nTYPE = " + Build.TYPE + "\nUNKNOWN = " + EnvironmentCompat.MEDIA_UNKNOWN + "\nUSER = " + Build.USER + "\ngetRadioVersion() = " + Build.getRadioVersion(), false);
          generateVideoDSI();
          if (this.mVideoEncoder == null) {
              this.mVideoEncoder = MediaCodec.createEncoderByType("video/avc");
              this.mVideoEncoder.configure(this.mVideoFormat, (Surface) null, (MediaCrypto) null, 1);
              this.mVideoEncoder.start();
              this.mInputVideoBuffers = this.mVideoEncoder.getInputBuffers();
              this.mOutputVideoBuffers = this.mVideoEncoder.getOutputBuffers();
              this.isVideoEncoderRunning = true;
                    }
          if (!this.isAudioEncoderRunning || this.mAudioEncoder == null) {
                        try {
                  this.mAudioEncoder = MediaCodec.createEncoderByType("audio/mp4a-latm");
                  this.mAudioEncoder.configure(this.mAudioFormat, (Surface) null, (MediaCrypto) null, 1);
                        } catch (Exception e) {
                  LVMediaCoderFactory.printLog("[LVMediaCoderImpl] " + e.toString(), true);
                            for (String audioEncoder : this.mSupportedAudioCodecs) {
                                try {
                          if (this.mAudioEncoder != null) {
                              this.mAudioEncoder.release();
                                    }
                          LVMediaCoderFactory.printLog("[LVMediaCoderImpl] " + e.toString() + ", Trying to instantiate AudioEncoder with " + audioEncoder, false);
                          this.mAudioEncoder = MediaCodec.createByCodecName(audioEncoder);
                          this.mAudioEncoder.configure(this.mAudioFormat, (Surface) null, (MediaCrypto) null, 1);
                                    break;
                                } catch (Exception e2) {
                          LVMediaCoderFactory.printLog("[LVMediaCoderImpl] " + e.toString(), true);
                          if (this.mSupportedAudioCodecs.indexOf(audioEncoder) == this.mSupportedAudioCodecs.size() - 1) {
                              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] " + e.toString() + ", AudioEncoder creation failed with available audio codecs on the device. Try with different audio parameters.", true);
                              throw new IllegalStateException("AudioEncoder creation failed with available audio codecs on the device. Try with different audio parameters.");
                                    }
                                }
                            }
                        }
              this.mAudioEncoder.start();
              this.mInputAudioBuffers = this.mAudioEncoder.getInputBuffers();
              this.mOutputAudioBuffers = this.mAudioEncoder.getOutputBuffers();
              this.isAudioEncoderRunning = true;
                    }
          generateAudioDSI();
          LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Session configuration_______________\n\t\t\t\t Is Recording ON:\t" + this.isRecordingSession + "\n\t\t\t\t Color Format:\t\t" + this.mVideoFormat.getInteger("color-format") + "\n\t\t\t\t Video Resolution:\t" + this.mConfiguration.videoWidth + "x" + this.mConfiguration.videoHeight + "\n\t\t\t\t Video BitRate:\t\t" + this.mVideoFormat.getInteger("bitrate") + "\n\t\t\t\t Video Frame Rate:\t" + this.mVideoFormat.getInteger("frame-rate") + "\n\t\t\t\t Video I-Frame Int:\t" + this.mVideoFormat.getInteger("i-frame-interval") + "\n\t\t\t\t Audio Bit Rate:\t" + this.mAudioFormat.getInteger("bitrate") + "\n\t\t\t\t Audio Channels:\t" + this.mAudioFormat.getInteger("channel-count") + "\n\t\t\t\t Audio Sample Rate:\t" + this.mAudioFormat.getInteger("sample-rate"), false);
                }

                @Override 
      public void stop() {
                    try {
              if (isRunningOnICS) {
                  while (this.mRetrievedEncodedFrameCount < this.mSuppliedEncodedFrameCount) {
                      LVMediaCoderFactory.printLog("[LVMediaCoderImpl] WAITING to complete frame extraction .... ..... ... !!!!!! ", false);
                      LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Got : " + this.mRetrievedEncodedFrameCount + "sent :" + this.mSuppliedEncodedFrameCount, false);
                      this.pullerFlagExit = false;
                            }
                  LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Got : " + this.mRetrievedEncodedFrameCount + "sent :" + this.mSuppliedEncodedFrameCount, false);
                  if (this.mSuppliedEncodedFrameCount == this.mRetrievedEncodedFrameCount) {
                      this.pullerFlagExit = true;
                      this.mSuppliedEncodedFrameCount = 0;
                      this.mRetrievedEncodedFrameCount = 0;
                      LVMediaCoderFactory.printLog("[LVMediaCoderImpl] stop(): DONE with frame extraction .... ..... ... !!!!!! ", false);
                            }
                  this.run = false;
                        }
              if (this.isRecordingSession) {
                  LVMediaCoderWriterJNI.LVMediaCoderWriterClose(this.mWriterJNIContext);
                        }
              this.isRecordingSession = false;
              this.isAudioEncoderRunning = false;
              this.isVideoEncoderRunning = false;
              this.mInputVideoBuffers = null;
              this.mOutputVideoBuffers = null;
              this.mInputAudioBuffers = null;
              this.mOutputAudioBuffers = null;
              if (!isRunningOnICS && this.mAudioEncoder != null) {
                  this.mAudioEncoder.stop();
                  this.mAudioEncoder.release();
                  this.mAudioEncoder = null;
                        }
              if (isRunningOnICS && this.mICSAudioEncoder != null) {
                  this.mICSAudioEncoder.stop();
                  this.mICSAudioEncoder = null;
                        }
              if (!isRunningOnICS && this.mVideoEncoder != null) {
                  this.mVideoEncoder.stop();
                  this.mVideoEncoder.release();
                  this.mVideoEncoder = null;
                        }
              if (isRunningOnICS && this.mICSVideoEncoder != null) {
                  this.mICSVideoEncoder.stop();
                  this.mICSVideoEncoder = null;
                        }
              this.mWriterJNIContext = 0;
              mAudioDSI = null;
              mVideoDSI = null;
              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] LVMediaCoder stopped!", false);
                    } catch (Exception e) {
              if (SLog.sLogsOn) {
                  e.printStackTrace();
                        }
              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] " + e.toString(), true);
              throw new IllegalStateException(e.getMessage() + " Error while executing stop()");
                    }
                }

                @Override 
      public ShortBuffer encodeAudio(byte[] pcmFrame, int start, int shortCount) throws IllegalStateException {
          if (isRunningOnICS) {
                        return encodeICSAudioFrame(pcmFrame, start, shortCount);
                    }
                    return encodeAudioFrame(pcmFrame, start, shortCount);
                }

      private ShortBuffer encodeICSAudioFrame(byte[] pcmFrame, int start, int shortCount) {
          LVMediaCoderFactory.printLog("[LVMediaCoderImpl] ICS: encodeICSAudioFrame", false);
          if (!this.isAudioEncoderRunning) {
              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] LVMediaCoder is not running.", true);
              throw new IllegalStateException("LVMediaCoder is not running.");
                    }
          if (start + shortCount > pcmFrame.length) {
              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Index out of bound. Length of pcmFrame is: " + pcmFrame.length + " however (Start + ShortCount) = " + (start + shortCount), true);
              throw new IndexOutOfBoundsException("Index out of bound. Length of pcmFrame is: " + pcmFrame.length + " Start + ShortCount = " + (start + shortCount));
                    }
          LVMediaCoderFactory.printLog("[LVMediaCoderImpl] \t\tencodeAudio(): Length of input pcmFrame# " + this.mAudioFrameNumber + ": " + pcmFrame.length + ", Length: " + shortCount + ", Position: " + start, false);
          if (this.mPCMFrameData == null) {
              if (this.mConfiguration.audioChannelCount == 1) {
                  this.mPCMFrameData = new byte[2048];
                        } else {
                  this.mPCMFrameData = new byte[4096];
                        }
                    }
          if (this.mConfiguration.audioChannelCount == 1 && shortCount == 2048) {
              int index = 0;
                        for (int i = start; i < start + shortCount; i++) {
                  this.mPCMFrameData[index] = pcmFrame[i];
                  index++;
                        }
                    } else if (this.mConfiguration.audioChannelCount == 1 && shortCount != 2048) {
              if (shortCount < 2048) {
                  LVMediaCoderFactory.printLog("[LVMediaCoderImpl] \t\tencodeAudio(): pcmFrame# " + this.mAudioFrameNumber + ": Length < PCM_MONO, hence wraping it in 2048 byte array.", false);
                  int index2 = 0;
                            for (int i2 = start; i2 < start + shortCount; i2++) {
                      this.mPCMFrameData[index2] = pcmFrame[i2];
                      index2++;
                            }
                            for (int i3 = shortCount; i3 < 2048; i3++) {
                      this.mPCMFrameData[i3] = 0;
                            }
                        } else {
                  LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Check the size of input PCM data. For Mono PCM Data, this should be 1024 bytes.\nSize of input pcmFrame: " + shortCount, true);
                  throw new IllegalArgumentException("Check the size of input PCM data. For Mono PCM Data, this should be 1024 bytes.");
                        }
                    } else {
              if (this.mConfiguration.audioChannelCount == 2 && shortCount != 4096) {
                  LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Check the size of input PCM data. For Stereo PCM Data, this should be 2048 bytes.\nSize of input pcmFrame: " + shortCount, true);
                  throw new IllegalArgumentException("Check the size of input PCM data. For Stereo PCM Data, this should be 2048 bytes.");
                        }
              if (this.mConfiguration.audioChannelCount == 2 && shortCount == 4096) {
                  int index3 = 0;
                            for (int i4 = start; i4 < start + shortCount; i4++) {
                      this.mPCMFrameData[index3] = pcmFrame[i4];
                      index3++;
                            }
                        }
                    }
          if (LVMediaCoderProperties.DUMPS) {
                        LVMediaCoderFactory.dumpInputAudioData(this.mPCMFrameData);
                    }
          ShortBuffer shortBuffer = this.mICSAudioEncoder.encode(this.mPCMFrameData);
          LVMediaCoderFactory.printLog("[LVMediaCoderImpl] \t\tencodeAudio(): Operation Ended! returning " + shortBuffer.limit() + " bytes.", false);
          return shortBuffer;
                }

      private ShortBuffer encodeAudioFrame(byte[] pcmFrame, int start, int shortCount) {
                    int outputBufferIndex;
          if (!this.isAudioEncoderRunning) {
              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] LVMediaCoder is not running.", true);
              throw new IllegalStateException("LVMediaCoder is not running.");
                    }
          if (start + shortCount > pcmFrame.length) {
              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Index out of bound. Length of pcmFrame is: " + pcmFrame.length + " however (Start + ShortCount) = " + (start + shortCount), true);
              throw new IndexOutOfBoundsException("Index out of bound. Length of pcmFrame is: " + pcmFrame.length + " Start + ShortCount = " + (start + shortCount));
                    }
          LVMediaCoderFactory.printLog("[LVMediaCoderImpl] \t\tencodeAudio(): Length of input pcmFrame# " + this.mAudioFrameNumber + ": " + pcmFrame.length + ", Length: " + shortCount + ", Position: " + start, false);
          if (this.mPCMFrameData == null) {
              if (this.mConfiguration.audioChannelCount == 1) {
                  this.mPCMFrameData = new byte[2048];
                        } else {
                  this.mPCMFrameData = new byte[4096];
                        }
                    }
          if (this.mConfiguration.audioChannelCount == 1 && shortCount == 2048) {
              int index = 0;
                        for (int i = start; i < start + shortCount; i++) {
                  this.mPCMFrameData[index] = pcmFrame[i];
                  index++;
                        }
                    } else if (this.mConfiguration.audioChannelCount == 1 && shortCount != 2048) {
              if (shortCount < 2048) {
                  LVMediaCoderFactory.printLog("[LVMediaCoderImpl] \t\tencodeAudio(): pcmFrame# " + this.mAudioFrameNumber + ": Length < PCM_MONO, hence wraping it in 2048 byte array.", false);
                  int index2 = 0;
                            for (int i2 = start; i2 < start + shortCount; i2++) {
                      this.mPCMFrameData[index2] = pcmFrame[i2];
                      index2++;
                            }
                            for (int i3 = shortCount; i3 < 2048; i3++) {
                      this.mPCMFrameData[i3] = 0;
                            }
                        } else {
                  LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Check the size of input PCM data. For Mono PCM Data, this should be 1024 bytes.\nSize of input pcmFrame: " + shortCount, true);
                  throw new IllegalArgumentException("Check the size of input PCM data. For Mono PCM Data, this should be 1024 bytes.");
                        }
                    } else {
              if (this.mConfiguration.audioChannelCount == 2 && shortCount != 4096) {
                  LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Check the size of input PCM data. For Stereo PCM Data, this should be 2048 bytes.\nSize of input pcmFrame: " + shortCount, true);
                  throw new IllegalArgumentException("Check the size of input PCM data. For Stereo PCM Data, this should be 2048 bytes.");
                        }
              if (this.mConfiguration.audioChannelCount == 2 && shortCount == 4096) {
                  int index3 = 0;
                            for (int i4 = start; i4 < start + shortCount; i4++) {
                      this.mPCMFrameData[index3] = pcmFrame[i4];
                      index3++;
                            }
                        }
                    }
          if (LVMediaCoderProperties.DUMPS) {
                        LVMediaCoderFactory.dumpInputAudioData(this.mPCMFrameData);
                    }
          boolean doneSubmittingInput = false;
                    while (true) {
              if (!doneSubmittingInput) {
                  int inputBufferIndex = this.mAudioEncoder.dequeueInputBuffer(1L);
                  LVMediaCoderFactory.printLog("[LVMediaCoderImpl] \t\tencodeAudio(): Audio Input BufferIndex: " + inputBufferIndex, false);
                  if (inputBufferIndex != -1 && inputBufferIndex >= 0) {
                      byte[] pcmData = this.mPCMFrameData;
                      this.mInputAudioBuffers[inputBufferIndex].clear();
                      this.mInputAudioBuffers[inputBufferIndex].put(pcmData);
                      this.mAudioEncoder.queueInputBuffer(inputBufferIndex, 0, pcmData.length, 0L, 0);
                      doneSubmittingInput = true;
                      this.mAudioFrameNumber++;
                            }
                        }
              if (doneSubmittingInput) {
                  if (this.mAudioBufferInfo == null) {
                      this.mAudioBufferInfo = new MediaCodec.BufferInfo();
                            }
                  outputBufferIndex = this.mAudioEncoder.dequeueOutputBuffer(this.mAudioBufferInfo, 0L);
                  LVMediaCoderFactory.printLog("[LVMediaCoderImpl] \t\tencodeAudio(): Audio Output BufferIndex: " + outputBufferIndex, false);
                  if (outputBufferIndex != -1 && outputBufferIndex != -2) {
                      if (outputBufferIndex != -3) {
                                    break;
                                }
                      this.mOutputAudioBuffers = this.mAudioEncoder.getOutputBuffers();
                            }
                        }
                    }
          ByteBuffer outputBuffer = this.mOutputAudioBuffers[outputBufferIndex];
          byte[] outData2 = new byte[this.mAudioBufferInfo.size];
          outputBuffer.get(outData2);
          outputBuffer.clear();
          if ((this.mAudioBufferInfo.flags & 4) == 0) {
              this.mAudioEncoder.releaseOutputBuffer(outputBufferIndex, false);
              if (this.mAudioFrameNumber == 1 && mAudioDSI == null) {
                  mAudioDSI = outData2;
                  this.mAudioFrameNumber++;
                        }
                    }
          ShortBuffer shortBuffer = ShortBuffer.allocate(outData2.length);
          int size = outData2.length;
          short[] shortArray2 = new short[size];
                    for (int index4 = 0; index4 < size; index4++) {
              shortArray2[index4] = outData2[index4];
                    }
          shortBuffer.put(shortArray2);
          if (LVMediaCoderProperties.DEBUG) {
                        try {
                  writeAACToFile(outData2);
                        } catch (Exception e) {
                  throw new IllegalStateException(e.getMessage());
                        }
                    }
          LVMediaCoderFactory.printLog("[LVMediaCoderImpl] \t\tencodeAudio(): Operation Ended! returning " + shortArray2.length + " bytes.", false);
          return shortBuffer;
                }

      private void writeAACToFile(byte[] aacData) throws Exception {
          File file = new File("mnt/sdcard/Twitter/");
          file.mkdirs();
          File file2 = new File("mnt/sdcard/Twitter/Twitter_Audio.aac");
                    try {
              FileOutputStream fileOutputStream = new FileOutputStream(file2, true);
              fileOutputStream.write(aacData);
              fileOutputStream.flush();
              fileOutputStream.close();
              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] writeAACToFile(): " + aacData.length + " bytes written to file.", false);
                    } catch (Exception e) {
              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] writeAACToFile(): Error writting " + aacData.length + " bytes to file.", true);
              throw new Exception("writeAACToFile(): Error writting " + aacData.length + " bytes to file.");
                    }
                }

                @Override 
      public void recordAudio(ShortBuffer aacFrame) throws IllegalStateException {
          if (!this.isAudioEncoderRunning) {
              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] LVMediaCoder is not running.", true);
              throw new IllegalStateException("LVMediaCoder is not running.");
                    }
          if (!this.isRecordingSession) {
              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Current session is not configured for audio-recording.", true);
              throw new IllegalStateException("Current session is not configured for audio-recording.");
                    }
          LVMediaCoderFactory.printLog("[LVMediaCoderImpl] recordAudio(): Capacity of input aacFrame #" + this.mAudioFramesRecorded + ": " + aacFrame.capacity() + " shorts, Remaining: " + aacFrame.remaining(), false);
          int timeStamp = getAudioTimeStamp(this.mAudioFramesRecorded);
          this.mAudioFramesRecorded++;
          ByteBuffer audioBuffer = ByteBuffer.allocate(aacFrame.remaining());
          short[] aacFrameData = new short[aacFrame.remaining()];
          aacFrame.get(aacFrameData);
                    for (short s : aacFrameData) {
              audioBuffer.put((byte) s);
                    }
          byte[] audioData = audioBuffer.array();
          if (this.mWriterJNIContext == 0) {
              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] recordAudio(): WriterJNIContext is NULL", true);
              throw new IllegalArgumentException("recordAudio(): WriterJNIContext is NULL");
                    }
          LVMediaCoderWriterJNI.LVMediaCoderWriterProcess(this.mWriterJNIContext, audioData.length, timeStamp, timeStamp, audioData, 1);
                }

      private int getAudioTimeStamp(int audioFramesRecorded) {
          return this.mAudioFrameDuration * audioFramesRecorded;
                }

                @Override 
      public void recordVideo(ByteBuffer h264Frame, int timeStamp) throws IllegalStateException {
          if (!this.isVideoEncoderRunning) {
              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] LVMediaCoder is not running.", true);
              throw new IllegalStateException("LVMediaCoder is not running.");
                    }
          if (!this.isRecordingSession) {
              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Current session is not configured for video-recording.", true);
              throw new IllegalStateException("Current session is not configured for video-recording.");
                    }
          LVMediaCoderFactory.printLog("[LVMediaCoderImpl] recordVideo(): Size of input h264Frame #" + this.mVideoFramesRecorded + ": " + h264Frame.remaining() + " bytes. Position: " + h264Frame.position() + " Limit: " + h264Frame.limit() + " Capacity: " + h264Frame.capacity(), false);
          byte[] videoData = new byte[h264Frame.remaining()];
          h264Frame.get(videoData);
          this.mVideoFramesRecorded++;
          if (this.mWriterJNIContext == 0) {
              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] recordVideo(): WriterJNIContext is NULL", true);
              throw new IllegalArgumentException("recordVideo(): WriterJNIContext is NULL");
                    }
          LVMediaCoderWriterJNI.LVMediaCoderWriterProcess(this.mWriterJNIContext, videoData.length, timeStamp, timeStamp, videoData, 0);
                }

                @Override 
      public LVMediaCoderConfiguration getConfiguration() {
          return this.mConfiguration;
                }

                @Override 
      public String getOutputFilePath() {
          return this.mOutputFilePath;
                }
            }
