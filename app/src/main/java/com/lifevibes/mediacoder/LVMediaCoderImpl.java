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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(16)
/* 72 */    public class LVMediaCoderImpl implements LVMediaCoder {
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

/* 73 */        static /* synthetic */ boolean access$000(LVMediaCoderImpl x0) {
/* 74 */            return x0.pullerFlagExit;
                }

/* 73 */        static /* synthetic */ ByteBuffer access$100() {
/* 74 */            return outData;
                }

/* 73 */        static /* synthetic */ ByteBuffer access$102(ByteBuffer x0) {
/* 74 */            outData = x0;
                    return x0;
                }

/* 73 */        static /* synthetic */ LVMediaCoderSFVideoEncoder access$200(LVMediaCoderImpl x0) {
/* 74 */            return x0.mICSVideoEncoder;
                }

/* 73 */        static /* synthetic */ int access$300(LVMediaCoderImpl x0) {
/* 74 */            return x0.mRetrievedEncodedFrameCount;
                }

/* 73 */        static /* synthetic */ int access$308(LVMediaCoderImpl x0) {
/* 74 */            int i = x0.mRetrievedEncodedFrameCount;
                    x0.mRetrievedEncodedFrameCount = i + 1;
                    return i;
                }

/* 73 */        static /* synthetic */ LVMediaCoder.LVMediaCoderICSEncoderProgressListener access$400(LVMediaCoderImpl x0) {
/* 74 */            return x0.mICSVideoEncoderListener;
                }

/* 73 */        static /* synthetic */ boolean access$500(LVMediaCoderImpl x0) {
/* 74 */            return x0.isEndOfStream;
                }

/* 73 */        static /* synthetic */ int access$600(LVMediaCoderImpl x0) {
/* 74 */            return x0.mSuppliedEncodedFrameCount;
                }

/* 73 */        static /* synthetic */ void access$700(LVMediaCoderImpl x0) {
/* 74 */            x0.cancelEncodingICS();
                }

/* 164 */       public LVMediaCoderImpl(Context context, LVMediaCoderConfiguration configuration) throws NullPointerException {
/* 166 */           if (context == null) {
/* 167 */               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Context is NULL.", true);
/* 168 */               throw new NullPointerException("Context is NULL.");
                    }
/* 170 */           if (configuration == null) {
/* 171 */               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Configuration is NULL.", true);
/* 172 */               throw new NullPointerException("Configuration is NULL.");
                    }
/* 175 */           this.mConfiguration = configuration;
/* 177 */           if (!isRunningOnICS) {
/* 178 */               configure(context, this.mConfiguration);
                    }
                }

/* 187 */       private void configure(Context context, LVMediaCoderConfiguration configuration) {
/* 188 */           LVMediaCoderFactory.printLog("[LVMediaCoderImpl] configure called!", false);
/* 189 */           this.mContext = context;
/* 195 */           this.mRGBBufferSize = this.mConfiguration.videoWidth * this.mConfiguration.videoHeight * 3;
/* 196 */           this.mRGBABufferSize = this.mConfiguration.videoWidth * this.mConfiguration.videoHeight * 4;
/* 197 */           this.mYUVBufferSize = this.mRGBBufferSize / 2;
/* 199 */           checkDevice();
/* 200 */           checkAudioCodecs();
/* 202 */           this.mVideoFormat = MediaFormat.createVideoFormat("video/avc", this.mConfiguration.videoWidth, this.mConfiguration.videoHeight);
/* 203 */           this.mVideoFormat.setInteger("color-format", getColorFormat());
/* 204 */           if (this.correctVideoBitRate) {
/* 205 */               this.mVideoFormat.setInteger("bitrate", this.mConfiguration.videoBitRate * 8);
                    } else {
/* 207 */               this.mVideoFormat.setInteger("bitrate", this.mConfiguration.videoBitRate);
                    }
/* 209 */           this.mVideoFormat.setInteger("frame-rate", this.mConfiguration.videoFrameRate);
/* 210 */           this.mVideoFormat.setInteger("i-frame-interval", this.mConfiguration.videoIFrameInterval);
/* 211 */           this.mVideoFormat.setInteger("aac-profile", 1);
/* 213 */           this.mAudioFormat = new MediaFormat();
/* 214 */           this.mAudioFormat.setString("mime", "audio/mp4a-latm");
/* 215 */           this.mAudioFormat.setInteger("bitrate", this.mConfiguration.audioBitRate);
/* 216 */           this.mAudioFormat.setInteger("channel-count", this.mConfiguration.audioChannelCount);
/* 217 */           this.mAudioFormat.setInteger("sample-rate", this.mConfiguration.audioSampleRate);
/* 218 */           this.mAudioFormat.setInteger("aac-profile", 2);
                }

/* 224 */       private int getColorFormat() {
/* 225 */           int index = 0;
/* 226 */           ArrayList<MediaCodecInfo> compatibleEncoders = new ArrayList<>();
                    for (int i = 0; i < MediaCodecList.getCodecCount(); i++) {
/* 228 */               MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
/* 229 */               if (codecInfo.isEncoder()) {
/* 232 */                   String[] supportedTypes = codecInfo.getSupportedTypes();
                            for (String type : supportedTypes) {
/* 234 */                       if (type.toLowerCase(Locale.ENGLISH).contains("avc")) {
/* 235 */                           compatibleEncoders.add(codecInfo);
/* 236 */                           index = i;
                                    break;
                                }
                            }
                        }
                    }
/* 242 */           MediaCodecInfo.CodecCapabilities capabilities = MediaCodecList.getCodecInfoAt(index).getCapabilitiesForType("video/avc");
/* 244 */           int colorFormat = 0;
                    for (int i2 = 0; i2 < capabilities.colorFormats.length; i2++) {
/* 247 */               int format = capabilities.colorFormats[i2];
                        switch (format) {
                            case 19:
                            case 20:
                            case 21:
                            case 39:
                            case 2130706688:
/* 255 */                       colorFormat = format;
                                break;
                            default:
/* 260 */                       LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Skipping unsupported color format " + format, false);
                                break;
                        }
                    }
/* 265 */           this.mSupportedColorFormat = colorFormat;
/* 266 */           LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Color format selected: " + this.mSupportedColorFormat, false);
/* 267 */           return colorFormat;
                }

/* 274 */       private void checkAudioCodecs() {
/* 275 */           this.mSupportedAudioCodecs = new ArrayList<>();
                    for (int i = 0; i < MediaCodecList.getCodecCount(); i++) {
/* 278 */               MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
/* 279 */               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Encoder " + i + ": " + codecInfo.getName(), false);
/* 280 */               if (codecInfo.isEncoder()) {
/* 283 */                   String[] supportedTypes = codecInfo.getSupportedTypes();
                            for (String type : supportedTypes) {
/* 285 */                       if (type.toLowerCase(Locale.ENGLISH).contains("mp4a-latm")) {
/* 286 */                           this.mSupportedAudioCodecs.add(codecInfo.getName());
/* 287 */                           LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Encoder " + i + "/" + MediaCodecList.getCodecCount() + ": " + codecInfo.getName() + " supports [" + type + "]", false);
                                    break;
                                }
                            }
                        }
                    }
                }

/* 299 */       private void checkDevice() {
/* 300 */           if ((Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("lge") && Build.BOARD.toLowerCase(Locale.ENGLISH).contains("hammerhead")) || Build.BOARD.toLowerCase(Locale.ENGLISH).contains("msm8974") || (Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("lge") && Build.MODEL.toLowerCase(Locale.ENGLISH).contains("lg-d802"))) {
/* 304 */               this.correctVideoBitRate = true;
                    }
/* 308 */           if (Build.VERSION.SDK_INT >= 19) {
/* 309 */               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on Android version: " + Build.VERSION.SDK_INT + ". No need of special color-conv filters.", false);
/* 395 */               return;
                    }
/* 313 */           if (Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("asus") && Build.BOARD.equalsIgnoreCase("flo")) {
/* 314 */               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on ASUS on flo board!", false);
/* 315 */               this.isYUVStride2KAligned = true;
/* 395 */               return;
                    }
/* 317 */           if (Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("motorola") && Build.BOARD.equalsIgnoreCase("msm8960")) {
/* 318 */               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on Motorola on msm8960 board!", false);
/* 319 */               this.isUVStride2KAligned = true;
/* 395 */               return;
                    }
/* 321 */           if (Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("htc") && Build.MODEL.toLowerCase(Locale.ENGLISH).contains("htc one")) {
/* 323 */               if (Build.BOARD.toLowerCase(Locale.ENGLISH).equals("apq8064")) {
/* 324 */                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on HTC One [APQ8064] with 2048 bit align stride!", false);
/* 325 */                   this.isYUVStride2KAligned = true;
/* 395 */                   return;
                        } else if (Build.HARDWARE.equals("m7")) {
/* 327 */                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on HTC One [APQ8064T] with 2048 bit align stride!", false);
/* 328 */                   this.isUVStride2KAligned = true;
/* 395 */                   return;
                        } else {
/* 329 */                   if (Build.HARDWARE.equals("elite") || Build.HARDWARE.equals("ville")) {
/* 331 */                       LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on HTC One X/S with 2048 bit align stride!", false);
/* 332 */                       this.isUVStride2KAligned = true;
/* 395 */                       return;
                            }
/* 395 */                   return;
                        }
                    }
/* 335 */           if (Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("sony")) {
/* 336 */               if (Build.BOARD.equalsIgnoreCase("MSM8660_SURF")) {
/* 337 */                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on SONY device with 2048 bit align stride!", false);
/* 338 */                   this.isYUVStride2KAligned = true;
                        } else if (Build.BOARD.toLowerCase(Locale.ENGLISH).contains("msm8974")) {
/* 341 */                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on SONY XPeria Z1 with 512 bit align stride!", false);
/* 342 */                   this.isStride512Aligned = true;
                        }
/* 344 */               if (Build.BOARD.equalsIgnoreCase("MSM8226")) {
/* 345 */                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on SONY XPeria Z1 with bit rate correction!", false);
/* 346 */                   this.correctVideoBitRate = true;
/* 395 */                   return;
                        }
/* 395 */               return;
                    }
/* 349 */           if (Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("lge")) {
/* 350 */               if (Build.BOARD.equalsIgnoreCase("MAKO")) {
/* 352 */                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on LGE Nexus 4 with 2048 bit aligned stride!", false);
/* 353 */                   this.isYUVStride2KAligned = true;
/* 395 */                   return;
                        }
/* 355 */               if (Build.MODEL.toLowerCase(Locale.ENGLISH).contains("lg-d802")) {
/* 357 */                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on LGE G2 with 512 bit aligned stride!", false);
/* 358 */                   this.isStride512Aligned = true;
/* 395 */                   return;
                        } else if (Build.BOARD.equalsIgnoreCase("gee")) {
/* 362 */                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on LG device on gee board!", false);
/* 363 */                   this.isUVStride2KAligned = true;
/* 395 */                   return;
                        } else {
/* 365 */                   if (Build.BOARD.equalsIgnoreCase("u2")) {
/* 366 */                       LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on LG device that returns extra zeores with 1st encoded frame!", false);
/* 367 */                       this.skipExtraBytesAtStart = true;
/* 395 */                       return;
                            }
/* 395 */                   return;
                        }
                    }
/* 370 */           if (Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("samsung")) {
/* 371 */               if (Build.BOARD.toLowerCase(Locale.ENGLISH).contains("smdk4x12") && Build.MODEL.toLowerCase(Locale.ENGLISH).contains("gt-i9300") && Build.VERSION.SDK_INT < 18) {
/* 375 */                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on Samsung smdk4x12 with UV-planes interchanged!", false);
/* 376 */                   this.isUVInterchanged = true;
/* 395 */                   return;
                        } else if (Build.BOARD.equalsIgnoreCase("msm8960")) {
/* 379 */                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on Samsung MSM8960 board!", false);
/* 380 */                   this.isUVStride2KAligned = true;
/* 395 */                   return;
                        } else {
/* 382 */                   if (Build.BOARD.equalsIgnoreCase("piranha")) {
/* 383 */                       LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on Samsung Piranha board!", false);
/* 384 */                       this.skipExtraBytesAtStart = true;
/* 395 */                       return;
                            }
/* 395 */                   return;
                        }
                    }
/* 386 */           if (Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("huawei") && Build.BOARD.toLowerCase(Locale.ENGLISH).contains("y301a1")) {
/* 388 */               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on Huawei device, which has UV planes interleaved!", false);
/* 389 */               this.isUVInterchanged = true;
                    } else if (Build.BOARD.toLowerCase(Locale.ENGLISH).contains("msm8974")) {
/* 391 */               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Application is running on Qualcomm MSM8974!", false);
/* 392 */               this.isYUVStride2KAligned = true;
                    }
                }

                @Override // com.lifevibes.mediacoder.LVMediaCoder
/* 398 */       public void setEncoderVideoAsyncListener(LVMediaCoder.LVMediaCoderICSEncoderProgressListener listener) {
/* 399 */           this.mICSVideoEncoderListener = listener;
                }

                @Override // com.lifevibes.mediacoder.LVMediaCoder
/* 402 */       public void encodeVideoAsync(ByteBuffer inputFrame, int inputType, boolean isEoS) throws IllegalStateException {
/* 403 */           if (!this.isVideoEncoderRunning) {
/* 404 */               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeVideo(): LVMediaCoder is not running.", true);
/* 405 */               throw new IllegalStateException("LVMediaCoder is not running.");
                    }
/* 407 */           if (isRunningOnICS) {
/* 409 */               if (!this.run) {
/* 411 */                   encodeVideoAsyncPull();
/* 412 */                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Starting encodeVideoAsyncPull()", false);
/* 413 */                   this.run = true;
                        }
/* 415 */               this.isEndOfStream = isEoS;
/* 416 */               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Sending RGB frame " + this.mSuppliedEncodedFrameCount, false);
/* 417 */               encodeICSVideo(inputFrame, inputType, isEoS);
/* 418 */               this.mSuppliedEncodedFrameCount++;
/* 422 */               return;
                    }
/* 420 */           throw new IllegalStateException("Method encodeVideoAsync is not supported on JB and above.");
                }

/* 424 */       private void encodeVideoAsyncPull() throws IllegalStateException {
/* 425 */           if (isRunningOnICS) {
                        synchronized (this) {
                            try {
/* 429 */                       new Thread(new RunnableC12471()).start();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

                /* JADX INFO: renamed from: com.lifevibes.mediacoder.LVMediaCoderImpl$1 */
/* 427 */       class RunnableC12471 implements Runnable {
/* 428 */           RunnableC12471() {
                    }

                    @Override // java.lang.Runnable
/* 430 */           public void run() {
/* 432 */               boolean first = true;
/* 433 */               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] PULLER +++++++++++++++++++++++++++++++++++++++++++++++++++++++++", false);
/* 434 */               while (!LVMediaCoderImpl.access$000(LVMediaCoderImpl.this)) {
                            try {
/* 437 */                       LVMediaCoderImpl.access$102(LVMediaCoderImpl.access$200(LVMediaCoderImpl.this).pollEncoderForOutput());
/* 438 */                       if (LVMediaCoderImpl.access$100() == null) {
/* 440 */                           LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Puller: Encoder output is null ##########", false);
                                } else if (first) {
/* 447 */                           first = false;
/* 448 */                           LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Puller: Length of Encoder output (DSI): " + LVMediaCoderImpl.access$300(LVMediaCoderImpl.this) + " DSI ##########" + LVMediaCoderImpl.access$100().array().length, false);
                                } else {
/* 451 */                           byte[] startCode = LVMediaCoderImpl.access$100().array();
/* 454 */                           if (startCode.length > 4) {
/* 455 */                               if (startCode[4] != 10) {
/* 456 */                                   LVMediaCoderImpl.access$400(LVMediaCoderImpl.this).onFrameAvailable(LVMediaCoderImpl.access$100());
/* 457 */                                   LVMediaCoderImpl.access$308(LVMediaCoderImpl.this);
/* 458 */                                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Puller: Length of Encoder output: " + LVMediaCoderImpl.access$300(LVMediaCoderImpl.this) + " ##########" + LVMediaCoderImpl.access$100().array().length, false);
/* 460 */                                   if (LVMediaCoderImpl.access$500(LVMediaCoderImpl.this) && LVMediaCoderImpl.access$300(LVMediaCoderImpl.this) == LVMediaCoderImpl.access$600(LVMediaCoderImpl.this)) {
/* 461 */                                       LVMediaCoderImpl.access$400(LVMediaCoderImpl.this).onComplete();
                                            }
                                        }
                                    } else {
/* 465 */                               LVMediaCoderImpl.access$400(LVMediaCoderImpl.this).onError(new Exception("Black frame encountered"), -1);
                                    }
                                }
/* 469 */                       Thread.sleep(10L);
                            } catch (Exception e) {
/* 471 */                       LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeVideoPoll(): returned: Exception: " + e.getMessage(), true);
/* 472 */                       LVMediaCoderImpl.access$700(LVMediaCoderImpl.this);
/* 473 */                       throw new IllegalStateException(e);
                            }
                        }
/* 476 */               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] STOP DONE ----------------------------", false);
                    }
                }

/* 484 */       private ByteBuffer encodeICSVideo(ByteBuffer inputFrame, int inputType, boolean isEoS) throws IllegalStateException {
/* 485 */           if (inputType == 0 && inputFrame.remaining() != this.mRGBBufferSize) {
/* 486 */               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeVideo(): Improper size of input RGB data.", true);
/* 487 */               throw new IllegalArgumentException("Improper size of input RGB data.");
                    }
/* 489 */           if (inputType == 1 && inputFrame.remaining() != this.mRGBABufferSize) {
/* 490 */               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeVideo(): Improper size of input RGBA data.", true);
/* 491 */               throw new IllegalArgumentException("Improper size of input RGBA data.");
                    }
/* 494 */           LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeVideo(): Called.", false);
/* 498 */           byte[] yuvData = convertICSRGBToYUV(inputFrame, inputType);
/* 499 */           if (LVMediaCoderProperties.DEBUG) {
                        try {
/* 501 */                   writeYUVToFile(yuvData);
                        } catch (Exception e) {
/* 503 */                   if (SLog.sLogsOn) {
/* 504 */                       e.printStackTrace();
                            }
/* 506 */                   throw new IllegalStateException(e.getMessage());
                        }
                    }
/* 511 */           this.mICSVideoEncoder.encode(yuvData, isEoS);
/* 510 */           return null;
                }

                @Override // com.lifevibes.mediacoder.LVMediaCoder
/* 518 */       public ByteBuffer encodeVideo(ByteBuffer inputFrame, int inputType) throws IllegalStateException {
/* 519 */           LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeVideo(): Called.", false);
/* 521 */           if (isRunningOnICS) {
/* 522 */               throw new IllegalStateException("LVMediaCoder is running on ICS. Call encodeVideoAsync instead.");
                    }
/* 525 */           if (!this.isVideoEncoderRunning) {
/* 526 */               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeVideo(): LVMediaCoder is not running.", true);
/* 527 */               throw new IllegalStateException("LVMediaCoder is not running.");
                    }
/* 529 */           if (inputType == 0 && inputFrame.remaining() != this.mRGBBufferSize) {
/* 530 */               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeVideo(): Improper size of input RGB data.", true);
/* 531 */               throw new IllegalArgumentException("Improper size of input RGB data.");
                    }
/* 533 */           if (inputType == 1 && inputFrame.remaining() != this.mRGBABufferSize) {
/* 534 */               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeVideo(): Improper size of input RGBA data." + inputFrame.remaining() + " bytes.", true);
/* 535 */               throw new IllegalArgumentException("Improper size of input RGBA data.");
                    }
/* 544 */           byte[] yuvData = convertRGBToYUV(inputFrame, inputType);
/* 545 */           if (LVMediaCoderProperties.DEBUG) {
                        try {
/* 547 */                   writeYUVToFile(yuvData);
                        } catch (Exception e) {
/* 549 */                   if (SLog.sLogsOn) {
/* 550 */                       e.printStackTrace();
                            }
/* 552 */                   throw new IllegalStateException(e.getMessage());
                        }
                    }
                    try {
/* 558 */               ByteBuffer buffer = encodeYUVToH264(yuvData);
/* 566 */               if (LVMediaCoderProperties.DEBUG) {
                            try {
/* 568 */                       dumpH264DataToFile(buffer.array());
                            } catch (Exception e2) {
/* 570 */                       if (SLog.sLogsOn) {
/* 571 */                           e2.printStackTrace();
                                }
/* 573 */                       throw new IllegalStateException(e2.getMessage());
                            }
                        }
/* 577 */               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeVideo(): Returning " + buffer.array().length + " bytes.", false);
/* 578 */               return buffer;
                    } catch (Exception e1) {
/* 560 */               if (SLog.sLogsOn) {
/* 561 */                   e1.printStackTrace();
                        }
/* 563 */               throw new IllegalStateException(e1.getMessage());
                    }
                }

/* 582 */       private void dumpH264DataToFile(byte[] h264Data) throws Exception {
                    try {
/* 583 */               new File("/mnt/sdcard/Twitter/").mkdir();
/* 585 */               File file = new File("/mnt/sdcard/Twitter/Encoded Video.h264");
/* 586 */               this.mOutputH264Dump = new FileOutputStream(file, true);
/* 587 */               this.mOutputH264Dump.write(h264Data);
/* 588 */               this.mOutputH264Dump.flush();
                    } catch (Exception e) {
/* 590 */               if (SLog.sLogsOn) {
/* 591 */                   e.printStackTrace();
                        }
/* 593 */               throw new Exception("Error while dumping H264 data to file: " + e.getMessage());
                    }
                }

/* 597 */       private void writeYUVToFile(byte[] yuvData) throws Exception {
/* 598 */           File file = new File("mnt/sdcard/Twitter/");
/* 599 */           file.mkdirs();
/* 600 */           File file2 = new File("mnt/sdcard/Twitter/YUV_CF_" + this.mSupportedColorFormat + ".yuv");
                    try {
/* 602 */               FileOutputStream fileOutputStream = new FileOutputStream(file2, true);
/* 603 */               fileOutputStream.write(yuvData);
/* 604 */               fileOutputStream.flush();
/* 605 */               fileOutputStream.close();
/* 606 */               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] writeYUVToFile(): " + yuvData.length + " bytes written to file.", false);
                    } catch (Exception e) {
/* 608 */               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] writeYUVToFile(): Error writting " + yuvData.length + " bytes to file.", true);
/* 609 */               throw new Exception("Error writting " + yuvData.length + " bytes to file: " + e.getMessage());
                    }
                }

/* 614 */       private byte[] convertRGBToYUVinRS(ByteBuffer inputFrame, int inputType) {
/* 615 */           if (this.mVideoDataInput == null) {
/* 616 */               if (inputType == 0) {
/* 617 */                   this.mVideoDataInput = new byte[this.mRGBBufferSize];
                        } else if (inputType == 1) {
/* 619 */                   this.mVideoDataInput = new byte[this.mRGBABufferSize];
                        }
                    }
/* 622 */           inputFrame.get(this.mVideoDataInput);
/* 623 */           if (LVMediaCoderProperties.DUMPS) {
                        LVMediaCoderFactory.dumpInputVideoData(this.mVideoDataInput);
                    }
/* 625 */           if (this.mYUVData == null) {
/* 626 */               if (this.isStride512Aligned) {
/* 628 */                   this.mYUVData = new byte[368640];
                        } else {
/* 630 */                   this.mYUVData = new byte[this.mYUVBufferSize];
                        }
                    }
/* 635 */           if (inputType == 0) {
/* 636 */               if (this.mInputDataToRSFilter == null) {
/* 637 */                   this.mInputDataToRSFilter = new byte[this.mVideoDataInput.length + (this.mVideoDataInput.length / 3)];
                        }
/* 639 */               int i = 0;
                        int j = 0;
                        while (i < this.mVideoDataInput.length) {
/* 640 */                   this.mInputDataToRSFilter[j] = this.mVideoDataInput[i];
/* 641 */                   this.mInputDataToRSFilter[j + 1] = this.mVideoDataInput[i + 1];
/* 642 */                   this.mInputDataToRSFilter[j + 2] = this.mVideoDataInput[i + 2];
/* 639 */                   i += 3;
                            j += 4;
                        }
                    } else {
/* 645 */               if (this.mInputDataToRSFilter == null) {
/* 646 */                   this.mInputDataToRSFilter = new byte[this.mVideoDataInput.length];
                        }
/* 648 */               this.mInputDataToRSFilter = this.mVideoDataInput;
                    }
/* 651 */           if (this.mOutputFromRSFilter == null) {
/* 652 */               this.mOutputFromRSFilter = new byte[this.mInputDataToRSFilter.length];
                    }
/* 655 */           long startTime = System.currentTimeMillis();
/* 657 */           if (this.ain == null || this.aout == null) {
/* 658 */               Element e = Element.RGB_888(this.mRenderScript);
/* 659 */               this.ain = Allocation.createSized(this.mRenderScript, e, this.mInputDataToRSFilter.length / 4);
/* 660 */               this.ain2 = Allocation.createSized(this.mRenderScript, Element.U8(this.mRenderScript), this.mVideoDataInput.length);
/* 661 */               this.ain3 = Allocation.createSized(this.mRenderScript, Element.U8(this.mRenderScript), this.mVideoDataInput.length);
/* 662 */               this.ain4 = Allocation.createSized(this.mRenderScript, Element.U8(this.mRenderScript), 460800);
/* 663 */               this.aout = Allocation.createSized(this.mRenderScript, e, this.mInputDataToRSFilter.length / 4);
/* 664 */               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Time taken for setting up RS: " + (System.currentTimeMillis() - startTime), false);
                    }
/* 666 */           if (this.tempBuffer == null) {
/* 667 */               this.tempBuffer = new byte[691200];
/* 668 */               this.tempBuffer1 = new byte[460800];
                    }
/* 671 */           this.ain2.copyFrom(this.mVideoDataInput);
/* 672 */           this.mScript.set_input(this.ain2);
/* 673 */           this.mScript.forEach_RGB888YUV444(this.ain);
/* 675 */           this.mScript.set_output(this.ain3);
/* 677 */           this.mScript.forEach_YUVPackedToPlanar(this.ain, this.aout);
/* 678 */           this.mScript.set_output1(this.ain3);
/* 680 */           this.mScript.set_output2(this.ain4);
/* 683 */           if (this.mSupportedColorFormat == 19) {
/* 684 */               this.mScript.forEach_YUV444ToYUV420(this.ain4);
                    } else if (this.mSupportedColorFormat == 21) {
/* 686 */               this.mScript.forEach_YUV444ToYUV420SemiPlanar(this.ain4);
                    } else {
/* 688 */               throw new IllegalStateException("RenderScript doesn't have color conversion filter for Color Format: " + this.mSupportedColorFormat);
                    }
/* 690 */           this.ain3.copyTo(this.tempBuffer);
/* 691 */           this.ain4.copyTo(this.tempBuffer1);
/* 692 */           System.arraycopy(this.tempBuffer, 0, this.mYUVData, 0, 230400);
/* 693 */           System.arraycopy(this.tempBuffer1, 0, this.mYUVData, 230400, 115200);
/* 694 */           return this.mYUVData;
                }

/* 697 */       private byte[] convertICSRGBToYUV(ByteBuffer inputFrame, int inputType) {
/* 698 */           this.mRGBBufferSize = this.mConfiguration.videoWidth * this.mConfiguration.videoHeight * 3;
/* 699 */           this.mRGBABufferSize = this.mConfiguration.videoWidth * this.mConfiguration.videoHeight * 4;
/* 700 */           this.mYUVBufferSize = this.mRGBBufferSize / 2;
/* 701 */           if (this.mVideoDataInput == null) {
/* 702 */               if (inputType == 0) {
/* 703 */                   this.mVideoDataInput = new byte[this.mRGBBufferSize];
                        } else if (inputType == 1) {
/* 705 */                   this.mVideoDataInput = new byte[this.mRGBABufferSize];
                        }
                    }
/* 708 */           this.mYUVData = new byte[this.mRGBBufferSize / 2];
/* 710 */           inputFrame.get(this.mVideoDataInput);
/* 711 */           if (LVMediaCoderProperties.DUMPS) {
                        LVMediaCoderFactory.dumpInputVideoData(this.mVideoDataInput);
                    }
/* 714 */           LVMediaCoderWriterJNI.RGB888toYUV420WithPlanar(this.mVideoDataInput, this.mYUVData, this.mConfiguration.videoWidth, this.mConfiguration.videoHeight, inputType);
/* 715 */           return this.mYUVData;
                }

/* 725 */       private byte[] convertRGBToYUV(ByteBuffer inputFrame, int inputType) {
/* 726 */           if (this.mVideoDataInput == null) {
/* 727 */               if (inputType == 0) {
/* 728 */                   this.mVideoDataInput = new byte[this.mRGBBufferSize];
                        } else if (inputType == 1) {
/* 730 */                   this.mVideoDataInput = new byte[this.mRGBABufferSize];
                        }
                    }
/* 733 */           inputFrame.get(this.mVideoDataInput);
/* 734 */           if (LVMediaCoderProperties.DUMPS) {
                        LVMediaCoderFactory.dumpInputVideoData(this.mVideoDataInput);
                    }
/* 737 */           if (this.isStride512Aligned) {
/* 739 */               if (this.mYUVData == null) {
/* 740 */                   if (this.mConfiguration.videoWidth % 128 != 0) {
/* 741 */                       this.mSonyXperiaZ1_Width = ((this.mConfiguration.videoWidth / 128) + 1) * 128;
                            } else {
/* 743 */                       this.mSonyXperiaZ1_Width = this.mConfiguration.videoWidth;
                            }
/* 744 */                   if (this.mConfiguration.videoHeight % 32 != 0) {
/* 745 */                       this.mSonyXperiaZ1_Height = ((this.mConfiguration.videoHeight / 32) + 1) * 32;
                            } else {
/* 747 */                       this.mSonyXperiaZ1_Height = this.mConfiguration.videoHeight;
                            }
/* 748 */                   this.mYUVData = new byte[((this.mSonyXperiaZ1_Width * this.mSonyXperiaZ1_Height) * 3) / 2];
                        }
/* 750 */               LVMediaCoderWriterJNI.RGB888toYUV420WithStride512Aligned(this.mVideoDataInput, this.mYUVData, this.mSonyXperiaZ1_Width, this.mSonyXperiaZ1_Height, this.mConfiguration.videoWidth, this.mConfiguration.videoHeight, inputType);
                    } else if (this.isYUVStride2KAligned) {
/* 753 */               if (this.mYUVData == null) {
/* 754 */                   int lgeNexus4StrideOffSet = (this.mConfiguration.videoWidth * this.mConfiguration.videoHeight) % 2048;
/* 755 */                   if (lgeNexus4StrideOffSet > 0) {
/* 756 */                       lgeNexus4StrideOffSet = 2048 - lgeNexus4StrideOffSet;
                            }
/* 758 */                   int UVOffset = ((this.mConfiguration.videoWidth * this.mConfiguration.videoHeight) / 2) % 2048;
/* 759 */                   if (UVOffset > 0) {
/* 760 */                       lgeNexus4StrideOffSet += 2048 - UVOffset;
                            }
/* 762 */                   this.mYUVData = new byte[this.mYUVBufferSize + lgeNexus4StrideOffSet];
                        }
/* 764 */               LVMediaCoderWriterJNI.RGB888toYUV420WithSemiPlanar(this.mVideoDataInput, this.mYUVData, this.mConfiguration.videoWidth, this.mConfiguration.videoHeight, inputType);
                    } else if (this.isUVStride2KAligned) {
/* 767 */               if (this.mYUVData == null) {
/* 768 */                   int lgeNexus4StrideOffSet2 = (this.mConfiguration.videoWidth * this.mConfiguration.videoHeight) % 2048;
/* 769 */                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Calling HTCOne stride:" + lgeNexus4StrideOffSet2, false);
/* 770 */                   if (lgeNexus4StrideOffSet2 > 0) {
/* 771 */                       lgeNexus4StrideOffSet2 = 2048 - lgeNexus4StrideOffSet2;
                            }
/* 773 */                   this.mYUVData = new byte[this.mYUVBufferSize + lgeNexus4StrideOffSet2];
                        }
/* 775 */               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Calling HTCOne filter....", false);
/* 776 */               LVMediaCoderWriterJNI.RGB888toYUV420WithUVStride2KAligned(this.mVideoDataInput, this.mYUVData, this.mConfiguration.videoWidth, this.mConfiguration.videoHeight, inputType);
                    } else {
/* 779 */               if (this.mYUVData == null) {
/* 780 */                   this.mYUVData = new byte[this.mYUVBufferSize];
                        }
/* 782 */               if (this.mSupportedColorFormat == 19) {
/* 783 */                   LVMediaCoderWriterJNI.RGB888toYUV420WithPlanar(this.mVideoDataInput, this.mYUVData, this.mConfiguration.videoWidth, this.mConfiguration.videoHeight, inputType);
                        } else if (this.isUVInterchanged) {
/* 786 */                   LVMediaCoderWriterJNI.RGB888toYUV420WithUVInterchanged(this.mVideoDataInput, this.mYUVData, this.mConfiguration.videoWidth, this.mConfiguration.videoHeight, inputType);
                        } else {
/* 789 */                   LVMediaCoderWriterJNI.RGB888toYUV420WithSemiPlanar(this.mVideoDataInput, this.mYUVData, this.mConfiguration.videoWidth, this.mConfiguration.videoHeight, inputType);
                        }
                    }
/* 793 */           return this.mYUVData;
                }

/* 803 */       private ByteBuffer encodeYUVToH264(byte[] yuvData) throws Exception {
                    int size;
/* 804 */           if (yuvData == null) {
/* 805 */               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeYUVToH264(): YUV Data is null", true);
/* 806 */               throw new IllegalArgumentException("YUV Data is null");
                    }
/* 810 */           boolean doneSubmittingInput = false;
                    while (true) {
/* 814 */               if (!doneSubmittingInput) {
/* 815 */                   int inputBufferIndex = this.mVideoEncoder.dequeueInputBuffer(1L);
/* 816 */                   LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeYUVToH264(): Video InputBufferIndex : " + inputBufferIndex + " for frame# " + this.mVideoFrameNumber, false);
/* 817 */                   if (inputBufferIndex != -1 && inputBufferIndex >= 0) {
/* 819 */                       this.mInputVideoBuffers[inputBufferIndex].clear();
/* 820 */                       this.mInputVideoBuffers[inputBufferIndex].put(yuvData);
/* 822 */                       this.mVideoEncoder.queueInputBuffer(inputBufferIndex, 0, yuvData.length, this.mVideoFrameNumber * 33, 0);
/* 823 */                       doneSubmittingInput = true;
/* 824 */                       this.mVideoFrameNumber++;
                            }
                        }
/* 829 */               if (doneSubmittingInput) {
/* 830 */                   if (this.mBufferInfo == null) {
/* 831 */                       this.mBufferInfo = new MediaCodec.BufferInfo();
                            }
/* 833 */                   int outputBufferIndex = this.mVideoEncoder.dequeueOutputBuffer(this.mBufferInfo, 0L);
/* 834 */                   if (SLog.sLogsOn) {
/* 835 */                       LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeYUVToH264(): Video OutputBufferIndex : " + outputBufferIndex + " for frame# " + this.mVideoFrameNumber, false);
                            }
/* 837 */                   if (outputBufferIndex != -1 && outputBufferIndex != -2) {
/* 839 */                       if (outputBufferIndex == -3) {
/* 840 */                           this.mOutputVideoBuffers = this.mVideoEncoder.getOutputBuffers();
                                } else {
/* 842 */                           ByteBuffer outputBuffer = this.mOutputVideoBuffers[outputBufferIndex];
/* 843 */                           boolean isDsi = this.skipExtraBytesAtStart && this.mVideoFrameNumber == 3;
/* 847 */                           if (isDsi) {
/* 848 */                               size = this.mBufferInfo.size + mVideoDSI.length;
/* 849 */                               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] OutputBufferSize: " + outputBuffer.remaining() + "mBufferInfo.size: " + this.mBufferInfo.size + "mVideoDSI.length: " + mVideoDSI.length, false);
                                    } else {
/* 851 */                               size = this.mBufferInfo.size;
                                    }
/* 853 */                           if (this.mOutBuffer == null || this.mOutBuffer.capacity() < size) {
/* 854 */                               this.mOutBuffer = ByteBuffer.allocate(size);
                                    }
/* 856 */                           this.mOutBuffer.position(0);
/* 857 */                           this.mOutBuffer.limit(size);
                                    for (int i = 0; i < size; i++) {
/* 859 */                               this.mOutBuffer.put(outputBuffer.get());
                                    }
/* 861 */                           outputBuffer.clear();
/* 863 */                           if ((this.mBufferInfo.flags & 4) != 0) {
/* 864 */                               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeYUVToH264(): BUFFER_FLAG_END_OF_STREAM. Breaking out!!!!", false);
                                        break;
                                    }
/* 868 */                           if (this.mOutBuffer.get(0) != 0 || this.mOutBuffer.get(1) != 0 || this.mOutBuffer.get(2) != 0 || this.mOutBuffer.get(3) != 1) {
/* 871 */                               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeYUVToH264(): Header missing! for Frame: " + this.mVideoFrameNumber, false);
                                    }
/* 874 */                           this.mVideoEncoder.releaseOutputBuffer(outputBufferIndex, false);
/* 875 */                           this.mOutBuffer.position(0);
/* 876 */                           if (this.mVideoFrameNumber == 1) {
/* 877 */                               mVideoDSI = new byte[this.mOutBuffer.limit()];
/* 878 */                               this.mOutBuffer.get(mVideoDSI);
/* 879 */                               if (this.isRecordingSession && LVMediaCoderProperties.DEBUG) {
/* 881 */                                   new File("/mnt/sdcard/Twitter/").mkdir();
/* 883 */                                   File file = new File("/mnt/sdcard/Twitter/Encoded Video.h264");
                                            try {
/* 885 */                                       this.mOutputH264Dump = new FileOutputStream(file);
/* 886 */                                       this.mOutputH264Dump.write(mVideoDSI);
/* 887 */                                       this.mOutputH264Dump.flush();
/* 888 */                                       this.mOutputH264Dump.close();
                                            } catch (IOException e) {
/* 890 */                                       if (SLog.sLogsOn) {
/* 891 */                                           e.printStackTrace();
                                                }
/* 893 */                                       throw new Exception("Error while writing VideoDSI to debug file: " + e.getMessage());
                                            }
                                        }
/* 897 */                               this.mVideoFrameNumber++;
/* 898 */                               doneSubmittingInput = false;
                                    } else if (this.mVideoFrameNumber > 1) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
/* 908 */           LVMediaCoderFactory.printLog("[LVMediaCoderImpl] encodeYUVToH264(): Returning " + this.mOutBuffer.array().length + " bytes.", false);
/* 910 */           return this.mOutBuffer;
                }

                @Override // com.lifevibes.mediacoder.LVMediaCoder
/* 915 */       public void cancel() throws IllegalStateException {
/* 916 */           if (isRunningOnICS) {
/* 917 */               cancelEncodingICS();
                    } else if (this.mVideoEncoder == null || !this.isVideoEncoderRunning || this.mAudioEncoder == null || !this.isAudioEncoderRunning) {
/* 919 */               throw new IllegalStateException("Encoder is not running.");
                    }
/* 922 */           stop();
/* 924 */           if (this.isRecordingSession) {
/* 925 */               deleteOutputFile();
                    }
                }

/* 930 */       private void cancelEncodingICS() {
/* 931 */           if (!this.isVideoEncoderRunning || !this.isAudioEncoderRunning) {
/* 932 */               throw new IllegalStateException("Encoder is not running.");
                    }
/* 933 */           this.mSuppliedEncodedFrameCount = this.mRetrievedEncodedFrameCount;
                }

/* 936 */       private void deleteOutputFile() {
/* 937 */           File file = new File(this.mOutputFilePath);
/* 938 */           if (file.exists()) {
/* 939 */               file.delete();
                    }
                }

                @Override // com.lifevibes.mediacoder.LVMediaCoder
/* 944 */       public void startEncoding() {
/* 945 */           if (this.isAudioEncoderRunning || this.isVideoEncoderRunning) {
/* 946 */               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] LVMediaCoder is already running.", true);
/* 947 */               throw new IllegalStateException("LVMediaCoder is already running.");
                    }
/* 949 */           this.isRecordingSession = false;
/* 951 */           if (isRunningOnICS) {
/* 952 */               configureLVSFSession();
/* 963 */               return;
                    }
                    try {
/* 955 */               configureSession();
                    } catch (Exception e) {
/* 957 */               if (SLog.sLogsOn) {
/* 958 */                   e.printStackTrace();
                        }
/* 960 */               throw new IllegalStateException(e.getMessage());
                    }
                }

                @Override // com.lifevibes.mediacoder.LVMediaCoder
/* 966 */       public void startRecording(String outputFilePath) {
/* 967 */           if (this.isAudioEncoderRunning || this.isVideoEncoderRunning) {
/* 968 */               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] LVMediaCoder is already running.", true);
/* 969 */               throw new IllegalStateException("LVMediaCoder is already running.");
                    }
/* 971 */           if (outputFilePath == null || outputFilePath.equals("")) {
/* 972 */               LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Invalid output filepath", true);
/* 973 */               throw new IllegalArgumentException("Invalid output filepath parameter.");
                    }
/* 975 */           this.mOutputFilePath = outputFilePath;
/* 978 */           String[] directories = this.mOutputFilePath.split("/");
/* 979 */           String outputDirectory = "";
                    for (int i = 0; i < directories.length - 1; i++) {
/* 982 */               outputDirectory = (outputDirectory + "/") + directories[i];
                    }
/* 984 */           new File(outputDirectory).mkdirs();
/* 986 */           this.isRecordingSession = true;
/* 987 */           if (isRunningOnICS) {
/* 988 */               configureLVSFSession();
                    } else {
                        try {
/* 991 */                   configureSession();
                        } catch (Exception e) {
/* 993 */                   if (SLog.sLogsOn) {
/* 994 */                       e.printStackTrace();
                            }
/* 996 */                   throw new IllegalStateException(e.getMessage());
                        }
                    }
/* 1000 */          if (this.mAudioFrameDuration == 0) {
/* 1001 */              int pcmSize = 1024;
/* 1002 */              if (this.mConfiguration.audioChannelCount == 2) {
/* 1003 */                  pcmSize = 2048;
                        }
/* 1005 */              this.mAudioFrameDuration = ((pcmSize * 2) * 1000) / ((this.mConfiguration.audioSampleRate * this.mConfiguration.audioChannelCount) * 2);
                    }
/* 1007 */          this.mWriterJNIContext = LVMediaCoderWriterJNI.LVMediaCoderInitWriter(this.mOutputFilePath);
/* 1008 */          if (this.mWriterJNIContext == 0) {
/* 1009 */              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] LVMediaCoderInitWriter failed! " + outputFilePath, true);
/* 1010 */              throw new IllegalArgumentException("LVMediaCoderInitWriter failed! " + outputFilePath);
                    }
/* 1013 */          if (mVideoDSI == null) {
/* 1014 */              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] startRecording(): Problem getting the Video DSI.", true);
/* 1015 */              throw new IllegalStateException("Problem getting the Video DSI.");
                    }
/* 1017 */          if (mAudioDSI == null) {
/* 1018 */              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] startRecording(): Problem getting the Audio DSI", true);
/* 1019 */              throw new IllegalStateException("Problem getting the Audio DSI.");
                    }
/* 1022 */          LVMediaCoderWriterJNI.LVMediaCoderWriterAddStreamVideo(this.mWriterJNIContext, mVideoDSI, this.mConfiguration.videoWidth, this.mConfiguration.videoHeight, mVideoDSI.length);
/* 1023 */          LVMediaCoderWriterJNI.LVMediaCoderWriterAddStreamAudio(this.mWriterJNIContext, mAudioDSI, mAudioDSI.length, this.mConfiguration.audioBitRate, this.mConfiguration.audioSampleRate, this.mConfiguration.audioChannelCount);
                }

/* 1035 */      private void generateVideoDSI() throws Exception {
/* 1036 */          this.mVideoEncoder = MediaCodec.createEncoderByType("video/avc");
/* 1037 */          this.mVideoEncoder.configure(this.mVideoFormat, (Surface) null, (MediaCrypto) null, 1);
/* 1040 */          this.mVideoEncoder.start();
/* 1043 */          this.mInputVideoBuffers = this.mVideoEncoder.getInputBuffers();
/* 1044 */          this.mOutputVideoBuffers = this.mVideoEncoder.getOutputBuffers();
/* 1047 */          byte[] yuvData = new byte[this.mYUVBufferSize];
                    try {
/* 1049 */              encodeYUVToH264(yuvData);
/* 1058 */              this.mVideoEncoder.stop();
/* 1059 */              this.mVideoEncoder.release();
/* 1060 */              this.mVideoEncoder = null;
/* 1061 */              this.mInputVideoBuffers = null;
/* 1062 */              this.mOutputVideoBuffers = null;
/* 1064 */              this.mVideoFrameNumber = 0;
/* 1065 */              this.isVideoEncoderRunning = false;
                    } catch (Exception e) {
/* 1051 */              if (SLog.sLogsOn) {
/* 1052 */                  e.printStackTrace();
                        }
/* 1054 */              throw e;
                    }
                }

/* 1072 */      private void generateAudioDSI() {
/* 1075 */          mAudioDSI = new byte[]{18, -8};
                }

/* 1084 */      private void configureLVSFSession() {
/* 1085 */          this.mRGBBufferSize = this.mConfiguration.videoWidth * this.mConfiguration.videoHeight * 3;
/* 1086 */          this.mRGBABufferSize = this.mConfiguration.videoWidth * this.mConfiguration.videoHeight * 4;
/* 1087 */          this.mYUVBufferSize = this.mRGBBufferSize / 2;
/* 1089 */          this.mICSVideoEncoder = LVMediaCoderSFVideoEncoder.create("video/avc");
/* 1090 */          this.mICSVideoEncoder.configure(this.mConfiguration.videoHeight, this.mConfiguration.videoWidth, this.mConfiguration.videoBitRate, this.mConfiguration.videoFrameRate);
/* 1092 */          this.mICSVideoEncoder.start();
/* 1093 */          this.isVideoEncoderRunning = true;
/* 1094 */          this.mRetrievedEncodedFrameCount = 0;
/* 1095 */          this.mSuppliedEncodedFrameCount = 0;
/* 1100 */          this.mICSAudioEncoder = LVMediaCoderSFAudioEncoder.create("audio/mp4a-latm");
/* 1101 */          this.mICSAudioEncoder.configure(this.mConfiguration.audioBitRate, this.mConfiguration.audioSampleRate, this.mConfiguration.audioChannelCount);
/* 1103 */          this.mICSAudioEncoder.start();
/* 1104 */          this.isAudioEncoderRunning = true;
/* 1106 */          this.mICSAudioEncoder.getDSI();
                }

/* 1115 */      private void configureSession() throws Exception {
/* 1116 */          LVMediaCoderFactory.printLog("[LVMediaCoderImpl] \nBOARD = " + Build.BOARD + "\nBOOTLOADER = " + Build.BOOTLOADER + "\nBRAND = " + Build.BRAND + "\nCPU_ABI = " + Build.CPU_ABI + "\nCPU_ABI2 = " + Build.CPU_ABI2 + "\nDEVICE = " + Build.DEVICE + "\nAndroid Version = " + Build.VERSION.SDK_INT + "\nFINGERPRINT = " + Build.FINGERPRINT + "\nHARDWARE = " + Build.HARDWARE + "\nHOST = " + Build.HOST + "\nID = " + Build.ID + "\nMANUFACTURER = " + Build.MANUFACTURER + "\nMODEL = " + Build.MODEL + "\nPRODUCT = " + Build.PRODUCT + "\nSERIAL = " + Build.SERIAL + "\nTAGS = " + Build.TAGS + "\nTIME = " + Build.TIME + "\nTYPE = " + Build.TYPE + "\nUNKNOWN = " + EnvironmentCompat.MEDIA_UNKNOWN + "\nUSER = " + Build.USER + "\ngetRadioVersion() = " + Build.getRadioVersion(), false);
/* 1141 */          generateVideoDSI();
/* 1144 */          if (this.mVideoEncoder == null) {
/* 1145 */              this.mVideoEncoder = MediaCodec.createEncoderByType("video/avc");
/* 1146 */              this.mVideoEncoder.configure(this.mVideoFormat, (Surface) null, (MediaCrypto) null, 1);
/* 1147 */              this.mVideoEncoder.start();
/* 1148 */              this.mInputVideoBuffers = this.mVideoEncoder.getInputBuffers();
/* 1149 */              this.mOutputVideoBuffers = this.mVideoEncoder.getOutputBuffers();
/* 1150 */              this.isVideoEncoderRunning = true;
                    }
/* 1154 */          if (!this.isAudioEncoderRunning || this.mAudioEncoder == null) {
                        try {
/* 1156 */                  this.mAudioEncoder = MediaCodec.createEncoderByType("audio/mp4a-latm");
/* 1157 */                  this.mAudioEncoder.configure(this.mAudioFormat, (Surface) null, (MediaCrypto) null, 1);
                        } catch (Exception e) {
/* 1159 */                  LVMediaCoderFactory.printLog("[LVMediaCoderImpl] " + e.toString(), true);
                            for (String audioEncoder : this.mSupportedAudioCodecs) {
                                try {
/* 1162 */                          if (this.mAudioEncoder != null) {
/* 1163 */                              this.mAudioEncoder.release();
                                    }
/* 1165 */                          LVMediaCoderFactory.printLog("[LVMediaCoderImpl] " + e.toString() + ", Trying to instantiate AudioEncoder with " + audioEncoder, false);
/* 1166 */                          this.mAudioEncoder = MediaCodec.createByCodecName(audioEncoder);
/* 1167 */                          this.mAudioEncoder.configure(this.mAudioFormat, (Surface) null, (MediaCrypto) null, 1);
                                    break;
                                } catch (Exception e2) {
/* 1170 */                          LVMediaCoderFactory.printLog("[LVMediaCoderImpl] " + e.toString(), true);
/* 1171 */                          if (this.mSupportedAudioCodecs.indexOf(audioEncoder) == this.mSupportedAudioCodecs.size() - 1) {
/* 1172 */                              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] " + e.toString() + ", AudioEncoder creation failed with available audio codecs on the device. Try with different audio parameters.", true);
/* 1173 */                              throw new IllegalStateException("AudioEncoder creation failed with available audio codecs on the device. Try with different audio parameters.");
                                    }
                                }
                            }
                        }
/* 1178 */              this.mAudioEncoder.start();
/* 1179 */              this.mInputAudioBuffers = this.mAudioEncoder.getInputBuffers();
/* 1180 */              this.mOutputAudioBuffers = this.mAudioEncoder.getOutputBuffers();
/* 1181 */              this.isAudioEncoderRunning = true;
                    }
/* 1185 */          generateAudioDSI();
/* 1187 */          LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Session configuration_______________\n\t\t\t\t Is Recording ON:\t" + this.isRecordingSession + "\n\t\t\t\t Color Format:\t\t" + this.mVideoFormat.getInteger("color-format") + "\n\t\t\t\t Video Resolution:\t" + this.mConfiguration.videoWidth + "x" + this.mConfiguration.videoHeight + "\n\t\t\t\t Video BitRate:\t\t" + this.mVideoFormat.getInteger("bitrate") + "\n\t\t\t\t Video Frame Rate:\t" + this.mVideoFormat.getInteger("frame-rate") + "\n\t\t\t\t Video I-Frame Int:\t" + this.mVideoFormat.getInteger("i-frame-interval") + "\n\t\t\t\t Audio Bit Rate:\t" + this.mAudioFormat.getInteger("bitrate") + "\n\t\t\t\t Audio Channels:\t" + this.mAudioFormat.getInteger("channel-count") + "\n\t\t\t\t Audio Sample Rate:\t" + this.mAudioFormat.getInteger("sample-rate"), false);
                }

                @Override // com.lifevibes.mediacoder.LVMediaCoder
/* 1201 */      public void stop() {
                    try {
/* 1202 */              if (isRunningOnICS) {
/* 1203 */                  while (this.mRetrievedEncodedFrameCount < this.mSuppliedEncodedFrameCount) {
/* 1205 */                      LVMediaCoderFactory.printLog("[LVMediaCoderImpl] WAITING to complete frame extraction .... ..... ... !!!!!! ", false);
/* 1206 */                      LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Got : " + this.mRetrievedEncodedFrameCount + "sent :" + this.mSuppliedEncodedFrameCount, false);
/* 1208 */                      this.pullerFlagExit = false;
                            }
/* 1211 */                  LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Got : " + this.mRetrievedEncodedFrameCount + "sent :" + this.mSuppliedEncodedFrameCount, false);
/* 1213 */                  if (this.mSuppliedEncodedFrameCount == this.mRetrievedEncodedFrameCount) {
/* 1214 */                      this.pullerFlagExit = true;
/* 1215 */                      this.mSuppliedEncodedFrameCount = 0;
/* 1216 */                      this.mRetrievedEncodedFrameCount = 0;
/* 1217 */                      LVMediaCoderFactory.printLog("[LVMediaCoderImpl] stop(): DONE with frame extraction .... ..... ... !!!!!! ", false);
                            }
/* 1219 */                  this.run = false;
                        }
/* 1222 */              if (this.isRecordingSession) {
/* 1223 */                  LVMediaCoderWriterJNI.LVMediaCoderWriterClose(this.mWriterJNIContext);
                        }
/* 1226 */              this.isRecordingSession = false;
/* 1227 */              this.isAudioEncoderRunning = false;
/* 1228 */              this.isVideoEncoderRunning = false;
/* 1230 */              this.mInputVideoBuffers = null;
/* 1231 */              this.mOutputVideoBuffers = null;
/* 1232 */              this.mInputAudioBuffers = null;
/* 1233 */              this.mOutputAudioBuffers = null;
/* 1235 */              if (!isRunningOnICS && this.mAudioEncoder != null) {
/* 1236 */                  this.mAudioEncoder.stop();
/* 1237 */                  this.mAudioEncoder.release();
/* 1238 */                  this.mAudioEncoder = null;
                        }
/* 1240 */              if (isRunningOnICS && this.mICSAudioEncoder != null) {
/* 1241 */                  this.mICSAudioEncoder.stop();
/* 1242 */                  this.mICSAudioEncoder = null;
                        }
/* 1245 */              if (!isRunningOnICS && this.mVideoEncoder != null) {
/* 1246 */                  this.mVideoEncoder.stop();
/* 1247 */                  this.mVideoEncoder.release();
/* 1248 */                  this.mVideoEncoder = null;
                        }
/* 1250 */              if (isRunningOnICS && this.mICSVideoEncoder != null) {
/* 1251 */                  this.mICSVideoEncoder.stop();
/* 1252 */                  this.mICSVideoEncoder = null;
                        }
/* 1255 */              this.mWriterJNIContext = 0;
/* 1256 */              mAudioDSI = null;
/* 1257 */              mVideoDSI = null;
/* 1259 */              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] LVMediaCoder stopped!", false);
                    } catch (Exception e) {
/* 1261 */              if (SLog.sLogsOn) {
/* 1262 */                  e.printStackTrace();
                        }
/* 1264 */              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] " + e.toString(), true);
/* 1265 */              throw new IllegalStateException(e.getMessage() + " Error while executing stop()");
                    }
                }

                @Override // com.lifevibes.mediacoder.LVMediaCoder
/* 1270 */      public ShortBuffer encodeAudio(byte[] pcmFrame, int start, int shortCount) throws IllegalStateException {
/* 1271 */          if (isRunningOnICS) {
                        return encodeICSAudioFrame(pcmFrame, start, shortCount);
                    }
                    return encodeAudioFrame(pcmFrame, start, shortCount);
                }

/* 1278 */      private ShortBuffer encodeICSAudioFrame(byte[] pcmFrame, int start, int shortCount) {
/* 1279 */          LVMediaCoderFactory.printLog("[LVMediaCoderImpl] ICS: encodeICSAudioFrame", false);
/* 1280 */          if (!this.isAudioEncoderRunning) {
/* 1281 */              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] LVMediaCoder is not running.", true);
/* 1282 */              throw new IllegalStateException("LVMediaCoder is not running.");
                    }
/* 1284 */          if (start + shortCount > pcmFrame.length) {
/* 1285 */              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Index out of bound. Length of pcmFrame is: " + pcmFrame.length + " however (Start + ShortCount) = " + (start + shortCount), true);
/* 1286 */              throw new IndexOutOfBoundsException("Index out of bound. Length of pcmFrame is: " + pcmFrame.length + " Start + ShortCount = " + (start + shortCount));
                    }
/* 1289 */          LVMediaCoderFactory.printLog("[LVMediaCoderImpl] \t\tencodeAudio(): Length of input pcmFrame# " + this.mAudioFrameNumber + ": " + pcmFrame.length + ", Length: " + shortCount + ", Position: " + start, false);
/* 1290 */          if (this.mPCMFrameData == null) {
/* 1291 */              if (this.mConfiguration.audioChannelCount == 1) {
/* 1292 */                  this.mPCMFrameData = new byte[2048];
                        } else {
/* 1294 */                  this.mPCMFrameData = new byte[4096];
                        }
                    }
/* 1298 */          if (this.mConfiguration.audioChannelCount == 1 && shortCount == 2048) {
/* 1299 */              int index = 0;
                        for (int i = start; i < start + shortCount; i++) {
/* 1301 */                  this.mPCMFrameData[index] = pcmFrame[i];
/* 1302 */                  index++;
                        }
                    } else if (this.mConfiguration.audioChannelCount == 1 && shortCount != 2048) {
/* 1306 */              if (shortCount < 2048) {
/* 1307 */                  LVMediaCoderFactory.printLog("[LVMediaCoderImpl] \t\tencodeAudio(): pcmFrame# " + this.mAudioFrameNumber + ": Length < PCM_MONO, hence wraping it in 2048 byte array.", false);
/* 1309 */                  int index2 = 0;
                            for (int i2 = start; i2 < start + shortCount; i2++) {
/* 1311 */                      this.mPCMFrameData[index2] = pcmFrame[i2];
/* 1312 */                      index2++;
                            }
                            for (int i3 = shortCount; i3 < 2048; i3++) {
/* 1316 */                      this.mPCMFrameData[i3] = 0;
                            }
                        } else {
/* 1319 */                  LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Check the size of input PCM data. For Mono PCM Data, this should be 1024 bytes.\nSize of input pcmFrame: " + shortCount, true);
/* 1320 */                  throw new IllegalArgumentException("Check the size of input PCM data. For Mono PCM Data, this should be 1024 bytes.");
                        }
                    } else {
/* 1322 */              if (this.mConfiguration.audioChannelCount == 2 && shortCount != 4096) {
/* 1323 */                  LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Check the size of input PCM data. For Stereo PCM Data, this should be 2048 bytes.\nSize of input pcmFrame: " + shortCount, true);
/* 1324 */                  throw new IllegalArgumentException("Check the size of input PCM data. For Stereo PCM Data, this should be 2048 bytes.");
                        }
/* 1325 */              if (this.mConfiguration.audioChannelCount == 2 && shortCount == 4096) {
/* 1326 */                  int index3 = 0;
                            for (int i4 = start; i4 < start + shortCount; i4++) {
/* 1328 */                      this.mPCMFrameData[index3] = pcmFrame[i4];
/* 1329 */                      index3++;
                            }
                        }
                    }
/* 1333 */          if (LVMediaCoderProperties.DUMPS) {
                        LVMediaCoderFactory.dumpInputAudioData(this.mPCMFrameData);
                    }
/* 1336 */          ShortBuffer shortBuffer = this.mICSAudioEncoder.encode(this.mPCMFrameData);
/* 1337 */          LVMediaCoderFactory.printLog("[LVMediaCoderImpl] \t\tencodeAudio(): Operation Ended! returning " + shortBuffer.limit() + " bytes.", false);
/* 1339 */          return shortBuffer;
                }

/* 1343 */      private ShortBuffer encodeAudioFrame(byte[] pcmFrame, int start, int shortCount) {
                    int outputBufferIndex;
/* 1344 */          if (!this.isAudioEncoderRunning) {
/* 1345 */              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] LVMediaCoder is not running.", true);
/* 1346 */              throw new IllegalStateException("LVMediaCoder is not running.");
                    }
/* 1348 */          if (start + shortCount > pcmFrame.length) {
/* 1349 */              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Index out of bound. Length of pcmFrame is: " + pcmFrame.length + " however (Start + ShortCount) = " + (start + shortCount), true);
/* 1350 */              throw new IndexOutOfBoundsException("Index out of bound. Length of pcmFrame is: " + pcmFrame.length + " Start + ShortCount = " + (start + shortCount));
                    }
/* 1353 */          LVMediaCoderFactory.printLog("[LVMediaCoderImpl] \t\tencodeAudio(): Length of input pcmFrame# " + this.mAudioFrameNumber + ": " + pcmFrame.length + ", Length: " + shortCount + ", Position: " + start, false);
/* 1354 */          if (this.mPCMFrameData == null) {
/* 1355 */              if (this.mConfiguration.audioChannelCount == 1) {
/* 1356 */                  this.mPCMFrameData = new byte[2048];
                        } else {
/* 1358 */                  this.mPCMFrameData = new byte[4096];
                        }
                    }
/* 1362 */          if (this.mConfiguration.audioChannelCount == 1 && shortCount == 2048) {
/* 1363 */              int index = 0;
                        for (int i = start; i < start + shortCount; i++) {
/* 1365 */                  this.mPCMFrameData[index] = pcmFrame[i];
/* 1366 */                  index++;
                        }
                    } else if (this.mConfiguration.audioChannelCount == 1 && shortCount != 2048) {
/* 1370 */              if (shortCount < 2048) {
/* 1371 */                  LVMediaCoderFactory.printLog("[LVMediaCoderImpl] \t\tencodeAudio(): pcmFrame# " + this.mAudioFrameNumber + ": Length < PCM_MONO, hence wraping it in 2048 byte array.", false);
/* 1373 */                  int index2 = 0;
                            for (int i2 = start; i2 < start + shortCount; i2++) {
/* 1375 */                      this.mPCMFrameData[index2] = pcmFrame[i2];
/* 1376 */                      index2++;
                            }
                            for (int i3 = shortCount; i3 < 2048; i3++) {
/* 1380 */                      this.mPCMFrameData[i3] = 0;
                            }
                        } else {
/* 1383 */                  LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Check the size of input PCM data. For Mono PCM Data, this should be 1024 bytes.\nSize of input pcmFrame: " + shortCount, true);
/* 1384 */                  throw new IllegalArgumentException("Check the size of input PCM data. For Mono PCM Data, this should be 1024 bytes.");
                        }
                    } else {
/* 1386 */              if (this.mConfiguration.audioChannelCount == 2 && shortCount != 4096) {
/* 1387 */                  LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Check the size of input PCM data. For Stereo PCM Data, this should be 2048 bytes.\nSize of input pcmFrame: " + shortCount, true);
/* 1388 */                  throw new IllegalArgumentException("Check the size of input PCM data. For Stereo PCM Data, this should be 2048 bytes.");
                        }
/* 1389 */              if (this.mConfiguration.audioChannelCount == 2 && shortCount == 4096) {
/* 1390 */                  int index3 = 0;
                            for (int i4 = start; i4 < start + shortCount; i4++) {
/* 1392 */                      this.mPCMFrameData[index3] = pcmFrame[i4];
/* 1393 */                      index3++;
                            }
                        }
                    }
/* 1397 */          if (LVMediaCoderProperties.DUMPS) {
                        LVMediaCoderFactory.dumpInputAudioData(this.mPCMFrameData);
                    }
/* 1400 */          boolean doneSubmittingInput = false;
                    while (true) {
/* 1404 */              if (!doneSubmittingInput) {
/* 1405 */                  int inputBufferIndex = this.mAudioEncoder.dequeueInputBuffer(1L);
/* 1406 */                  LVMediaCoderFactory.printLog("[LVMediaCoderImpl] \t\tencodeAudio(): Audio Input BufferIndex: " + inputBufferIndex, false);
/* 1407 */                  if (inputBufferIndex != -1 && inputBufferIndex >= 0) {
/* 1409 */                      byte[] pcmData = this.mPCMFrameData;
/* 1410 */                      this.mInputAudioBuffers[inputBufferIndex].clear();
/* 1411 */                      this.mInputAudioBuffers[inputBufferIndex].put(pcmData);
/* 1413 */                      this.mAudioEncoder.queueInputBuffer(inputBufferIndex, 0, pcmData.length, 0L, 0);
/* 1414 */                      doneSubmittingInput = true;
/* 1415 */                      this.mAudioFrameNumber++;
                            }
                        }
/* 1419 */              if (doneSubmittingInput) {
/* 1420 */                  if (this.mAudioBufferInfo == null) {
/* 1421 */                      this.mAudioBufferInfo = new MediaCodec.BufferInfo();
                            }
/* 1423 */                  outputBufferIndex = this.mAudioEncoder.dequeueOutputBuffer(this.mAudioBufferInfo, 0L);
/* 1424 */                  LVMediaCoderFactory.printLog("[LVMediaCoderImpl] \t\tencodeAudio(): Audio Output BufferIndex: " + outputBufferIndex, false);
/* 1426 */                  if (outputBufferIndex != -1 && outputBufferIndex != -2) {
/* 1428 */                      if (outputBufferIndex != -3) {
                                    break;
                                }
/* 1429 */                      this.mOutputAudioBuffers = this.mAudioEncoder.getOutputBuffers();
                            }
                        }
                    }
/* 1431 */          ByteBuffer outputBuffer = this.mOutputAudioBuffers[outputBufferIndex];
/* 1432 */          byte[] outData2 = new byte[this.mAudioBufferInfo.size];
/* 1433 */          outputBuffer.get(outData2);
/* 1434 */          outputBuffer.clear();
/* 1435 */          if ((this.mAudioBufferInfo.flags & 4) == 0) {
/* 1439 */              this.mAudioEncoder.releaseOutputBuffer(outputBufferIndex, false);
/* 1441 */              if (this.mAudioFrameNumber == 1 && mAudioDSI == null) {
/* 1442 */                  mAudioDSI = outData2;
/* 1443 */                  this.mAudioFrameNumber++;
                        }
                    }
/* 1450 */          ShortBuffer shortBuffer = ShortBuffer.allocate(outData2.length);
/* 1451 */          int size = outData2.length;
/* 1452 */          short[] shortArray2 = new short[size];
                    for (int index4 = 0; index4 < size; index4++) {
/* 1455 */              shortArray2[index4] = outData2[index4];
                    }
/* 1457 */          shortBuffer.put(shortArray2);
/* 1458 */          if (LVMediaCoderProperties.DEBUG) {
                        try {
/* 1460 */                  writeAACToFile(outData2);
                        } catch (Exception e) {
/* 1462 */                  throw new IllegalStateException(e.getMessage());
                        }
                    }
/* 1466 */          LVMediaCoderFactory.printLog("[LVMediaCoderImpl] \t\tencodeAudio(): Operation Ended! returning " + shortArray2.length + " bytes.", false);
/* 1468 */          return shortBuffer;
                }

/* 1471 */      private void writeAACToFile(byte[] aacData) throws Exception {
/* 1472 */          File file = new File("mnt/sdcard/Twitter/");
/* 1473 */          file.mkdirs();
/* 1474 */          File file2 = new File("mnt/sdcard/Twitter/Twitter_Audio.aac");
                    try {
/* 1476 */              FileOutputStream fileOutputStream = new FileOutputStream(file2, true);
/* 1477 */              fileOutputStream.write(aacData);
/* 1478 */              fileOutputStream.flush();
/* 1479 */              fileOutputStream.close();
/* 1480 */              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] writeAACToFile(): " + aacData.length + " bytes written to file.", false);
                    } catch (Exception e) {
/* 1482 */              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] writeAACToFile(): Error writting " + aacData.length + " bytes to file.", true);
/* 1483 */              throw new Exception("writeAACToFile(): Error writting " + aacData.length + " bytes to file.");
                    }
                }

                @Override // com.lifevibes.mediacoder.LVMediaCoder
/* 1488 */      public void recordAudio(ShortBuffer aacFrame) throws IllegalStateException {
/* 1489 */          if (!this.isAudioEncoderRunning) {
/* 1490 */              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] LVMediaCoder is not running.", true);
/* 1491 */              throw new IllegalStateException("LVMediaCoder is not running.");
                    }
/* 1493 */          if (!this.isRecordingSession) {
/* 1494 */              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Current session is not configured for audio-recording.", true);
/* 1495 */              throw new IllegalStateException("Current session is not configured for audio-recording.");
                    }
/* 1498 */          LVMediaCoderFactory.printLog("[LVMediaCoderImpl] recordAudio(): Capacity of input aacFrame #" + this.mAudioFramesRecorded + ": " + aacFrame.capacity() + " shorts, Remaining: " + aacFrame.remaining(), false);
/* 1499 */          int timeStamp = getAudioTimeStamp(this.mAudioFramesRecorded);
/* 1500 */          this.mAudioFramesRecorded++;
/* 1502 */          ByteBuffer audioBuffer = ByteBuffer.allocate(aacFrame.remaining());
/* 1504 */          short[] aacFrameData = new short[aacFrame.remaining()];
/* 1505 */          aacFrame.get(aacFrameData);
                    for (short s : aacFrameData) {
/* 1507 */              audioBuffer.put((byte) s);
                    }
/* 1509 */          byte[] audioData = audioBuffer.array();
/* 1510 */          if (this.mWriterJNIContext == 0) {
/* 1511 */              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] recordAudio(): WriterJNIContext is NULL", true);
/* 1512 */              throw new IllegalArgumentException("recordAudio(): WriterJNIContext is NULL");
                    }
/* 1514 */          LVMediaCoderWriterJNI.LVMediaCoderWriterProcess(this.mWriterJNIContext, audioData.length, timeStamp, timeStamp, audioData, 1);
                }

/* 1521 */      private int getAudioTimeStamp(int audioFramesRecorded) {
/* 1522 */          return this.mAudioFrameDuration * audioFramesRecorded;
                }

                @Override // com.lifevibes.mediacoder.LVMediaCoder
/* 1526 */      public void recordVideo(ByteBuffer h264Frame, int timeStamp) throws IllegalStateException {
/* 1527 */          if (!this.isVideoEncoderRunning) {
/* 1528 */              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] LVMediaCoder is not running.", true);
/* 1529 */              throw new IllegalStateException("LVMediaCoder is not running.");
                    }
/* 1531 */          if (!this.isRecordingSession) {
/* 1532 */              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] Current session is not configured for video-recording.", true);
/* 1533 */              throw new IllegalStateException("Current session is not configured for video-recording.");
                    }
/* 1536 */          LVMediaCoderFactory.printLog("[LVMediaCoderImpl] recordVideo(): Size of input h264Frame #" + this.mVideoFramesRecorded + ": " + h264Frame.remaining() + " bytes. Position: " + h264Frame.position() + " Limit: " + h264Frame.limit() + " Capacity: " + h264Frame.capacity(), false);
/* 1538 */          byte[] videoData = new byte[h264Frame.remaining()];
/* 1539 */          h264Frame.get(videoData);
/* 1540 */          this.mVideoFramesRecorded++;
/* 1542 */          if (this.mWriterJNIContext == 0) {
/* 1543 */              LVMediaCoderFactory.printLog("[LVMediaCoderImpl] recordVideo(): WriterJNIContext is NULL", true);
/* 1544 */              throw new IllegalArgumentException("recordVideo(): WriterJNIContext is NULL");
                    }
/* 1546 */          LVMediaCoderWriterJNI.LVMediaCoderWriterProcess(this.mWriterJNIContext, videoData.length, timeStamp, timeStamp, videoData, 0);
                }

                @Override // com.lifevibes.mediacoder.LVMediaCoder
/* 1550 */      public LVMediaCoderConfiguration getConfiguration() {
/* 1551 */          return this.mConfiguration;
                }

                @Override // com.lifevibes.mediacoder.LVMediaCoder
/* 1555 */      public String getOutputFilePath() {
/* 1556 */          return this.mOutputFilePath;
                }
            }
