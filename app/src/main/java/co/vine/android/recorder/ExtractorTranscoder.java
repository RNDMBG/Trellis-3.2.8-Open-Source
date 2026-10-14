            package co.vine.android.recorder;

            import android.annotation.TargetApi;
            import android.content.Context;
            import android.graphics.Bitmap;
            import android.graphics.Point;
            import android.media.MediaCodec;
            import android.media.MediaCrypto;
            import android.media.MediaExtractor;
            import android.media.MediaFormat;
            import android.media.MediaMetadataRetriever;
            import android.net.Uri;
            import android.os.Build;
            import android.util.Log;
            import android.view.Surface;
            import co.vine.android.recorder.audio.AudioArray;
            import co.vine.android.recorder.audio.AudioArrays;
            import co.vine.android.recorder.audio.AudioData;
            import co.vine.android.recorder.video.VideoData;
            import co.vine.android.util.AudioUtils;
            import co.vine.android.util.CrashUtil;
            import com.edisonwang.android.slog.SLog;
            import com.flurry.android.Constants;
            import com.googlecode.javacv.cpp.avutil;
            import java.io.File;
            import java.io.IOException;
            import java.nio.ByteBuffer;
            import java.nio.ByteOrder;
            import java.util.ArrayList;
            import java.util.Map;
            import java.util.Random;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public class ExtractorTranscoder {
                private static final int ADDITIONAL_AUDIO_MS = 15000;
                private static final float MICROS_PER_SECOND = 1000000.0f;
                private static final int NANOS_PER_MILLI = 1000000;
                private static final float NOISE_GAIN = 0.01f;
                private static final String TAG = "ExtractorTranscoder";
                private static final int TIMEOUT_USEC = 10000;
                private static final String TYPE_AUDIO = "audio/";
                private static final String TYPE_VIDEO = "video/";
                public static final boolean VERBOSE = true;
                private AudioArray mAudioArray;
                private RecordConfigUtils.RecordConfig mConfig;
                private Context mContext;
                private File mDraftFolder;
                private long mDurationMicros;
                private ExtractorTranscoderListener mExtractorTranscoderListener;
                private int mFrameRate;
                private int mHeight;
                private int mMaxVideoByteCount;
                private String mRecorderOutPath;
                private int mRecorderTargetSize;
                private Bitmap mScaledCroppedBitmap;
                private RecordSegment mSegment;
                private long mStartTime;
                private byte[] mVideoBytes;
                private String mVideoMime;
                private int mWidth;
                private boolean mVideoEncodingFinished = false;
                private boolean mAudioEncodingFinished = false;
                private boolean mCancel = false;

                public interface ExtractorTranscoderListener {
                    void onError(Exception exc);

                    void onFinishedTranscoding(RecordSegment recordSegment, Bitmap bitmap);

                    void onProgressUpdate(int i);
                }

/* 33 */        static /* synthetic */ void access$000(ExtractorTranscoder x0, Uri x1, long x2, long x3) throws Throwable {
/* 34 */            x0.transcodeAudio(x1, x2, x3);
                }

/* 33 */        static /* synthetic */ ExtractorTranscoderListener access$100(ExtractorTranscoder x0) {
/* 34 */            return x0.mExtractorTranscoderListener;
                }

/* 73 */        public int getFrameRate() {
/* 74 */            return this.mFrameRate;
                }

/* 77 */        public void cancel() {
/* 78 */            this.mCancel = true;
                }

/* 87 */        public void onEncodingProgressMade(long justProcessedPresentationTimestampUs) {
/* 88 */            if (this.mExtractorTranscoderListener != null && this.mDurationMicros > 0) {
/* 89 */                this.mExtractorTranscoderListener.onProgressUpdate((int) ((100 * justProcessedPresentationTimestampUs) / this.mDurationMicros));
                    }
                }

/* 99 */        public void transcode(Context context, File draftFolder, RecordSession session, RecordSegment segment, Uri inUri, String outPath, long startMicros, long endMicros, Point cropOrigin, ExtractorTranscoderListener listener) throws Throwable {
                    long adjustedEndMicros;
/* 100 */           this.mStartTime = System.currentTimeMillis();
/* 101 */           this.mContext = context;
/* 104 */           SLog.m446d("ryango startMicros " + startMicros + " endmicros " + endMicros + " cropOrigin " + cropOrigin);
/* 106 */           int maxDurationUs = session.getConfig().maxDuration * 1000;
                    if (endMicros - startMicros > maxDurationUs) {
/* 110 */               SLog.m447d("ryango too long, originally {} sec now trimmed", Float.valueOf((endMicros - startMicros) / MICROS_PER_SECOND));
/* 111 */               adjustedEndMicros = startMicros + ((long) maxDurationUs);
                    } else {
/* 113 */               adjustedEndMicros = endMicros;
                    }
/* 116 */           this.mExtractorTranscoderListener = listener;
/* 118 */           this.mConfig = session.getConfig();
/* 119 */           this.mSegment = segment;
/* 120 */           this.mDraftFolder = draftFolder;
/* 122 */           this.mMaxVideoByteCount = RecordConfigUtils.getVideoBufferMax(this.mConfig);
/* 124 */           this.mRecorderOutPath = outPath;
/* 125 */           this.mRecorderTargetSize = this.mConfig.targetSize;
                    try {
/* 128 */               TranscodeAudioThread audioThread = new TranscodeAudioThread(inUri, startMicros, adjustedEndMicros);
/* 129 */               audioThread.start();
/* 130 */               transcodeVideo(inUri, startMicros, adjustedEndMicros, cropOrigin);
/* 131 */               SLog.m446d("Video Transcode done.");
/* 132 */               audioThread.join();
                    } catch (InterruptedException e) {
/* 134 */               if (SLog.sLogsOn) {
/* 135 */                   e.printStackTrace();
                        }
                    } catch (Exception e2) {
/* 138 */               if (listener != null) {
/* 139 */                   listener.onError(e2);
                        }
/* 141 */               CrashUtil.logException(e2, "Error while importing", new Object[0]);
                    }
/* 143 */           SLog.m446d("Verified Audio Transcode done.");
                }

/* 149 */       private class TranscodeAudioThread extends Thread {
                    private long mEndMicros;
                    private long mStartMicros;
                    private Uri mUri;

/* 150 */           public TranscodeAudioThread(Uri uri, long startMicros, long endMicros) {
/* 152 */               this.mUri = uri;
/* 153 */               this.mStartMicros = startMicros;
/* 154 */               this.mEndMicros = endMicros;
                    }

                    @Override // java.lang.Thread, java.lang.Runnable
/* 157 */           public void run() {
/* 158 */               long nanos = System.nanoTime();
                        try {
/* 160 */                   ExtractorTranscoder.access$000(ExtractorTranscoder.this, this.mUri, this.mStartMicros, this.mEndMicros);
                        } catch (Throwable e) {
                            Exception error = e instanceof Exception ? (Exception) e : new RuntimeException(e);
/* 162 */                   if (ExtractorTranscoder.access$100(ExtractorTranscoder.this) != null) {
/* 163 */                       ExtractorTranscoder.access$100(ExtractorTranscoder.this).onError(error);
                            }
/* 165 */                   CrashUtil.logException(error, "Error while transcoding audio", new Object[0]);
                        }
/* 168 */               SLog.m446d("ryango total audio transcode time {} " + ((System.nanoTime() - nanos) / AudioUtils.MICROS_PER_SECOND));
                    }
                }

                @TargetApi(16)
/* 173 */       private void transcodeAudio(Uri uri, long startMicros, long endMicros) throws Throwable {
/* 174 */           MediaExtractor extractor = null;
/* 175 */           MediaCodec decoder = null;
                    try {
/* 177 */               MediaExtractor extractor2 = new MediaExtractor();
                        try {
/* 178 */                   extractor2.setDataSource(this.mContext, uri, (Map<String, String>) null);
/* 179 */                   int trackIndex = selectAudioTrack(extractor2);
/* 180 */                   if (trackIndex < 0) {
/* 181 */                       generateNoise(endMicros - startMicros);
/* 356 */                       if (extractor2 != null) {
/* 357 */                           extractor2.release();
                                }
/* 361 */                       if (0 != 0) {
/* 362 */                           decoder.stop();
/* 363 */                           decoder.release();
/* 367 */                           return;
                                }
/* 367 */                       return;
                            }
/* 184 */                   extractor2.selectTrack(trackIndex);
/* 186 */                   long endMicros2 = endMicros + 15000;
/* 188 */                   MediaFormat format = extractor2.getTrackFormat(trackIndex);
/* 192 */                   int channelCount = format.getInteger("channel-count");
/* 193 */                   if (format.containsKey("bitrate")) {
/* 194 */                       format.getInteger("bitrate");
                            }
/* 195 */                   if (format.containsKey("durationUs")) {
/* 196 */                       format.getLong("durationUs");
                            }
/* 198 */                   int sampleRate = format.containsKey("sample-rate") ? format.getInteger("sample-rate") : -1;
/* 200 */                   if (sampleRate < 0) {
/* 201 */                       throw new RuntimeException("no sample rate");
                            }
/* 203 */                   long sampleDurationUs = (long) ((1.0f / sampleRate) * MICROS_PER_SECOND);
/* 205 */                   String mime = format.getString("mime");
/* 206 */                   MediaCodec decoder2 = MediaCodec.createDecoderByType(mime);
/* 207 */                   decoder2.configure(format, (Surface) null, (MediaCrypto) null, 0);
/* 208 */                   decoder2.start();
/* 209 */                   ByteBuffer[] input = decoder2.getInputBuffers();
/* 210 */                   ByteBuffer[] output = decoder2.getOutputBuffers();
/* 213 */                   MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
/* 214 */                   boolean sawInputEOS = false;
/* 215 */                   boolean sawOutputEOS = false;
/* 216 */                   int noOutputCounter = 0;
/* 219 */                   Boolean doStop = false;
/* 221 */                   float sampleRatio = sampleRate / 44100;
/* 224 */                   short[] audioArray = new short[(int) (RecordConfigUtils.getMaxAudioBufferSize(this.mConfig) * sampleRatio * 2.0f)];
/* 225 */                   int bufferSize = 0;
/* 227 */                   extractor2.seekTo(startMicros, 0);
/* 230 */                   SLog.m448d("ryango audio start time {}: delta of {} from start", Long.valueOf(extractor2.getSampleTime()), Long.valueOf(extractor2.getSampleTime() - startMicros));
/* 232 */                   byte[] chunk = null;
/* 233 */                   while (!this.mCancel && !sawOutputEOS && noOutputCounter < 50 && !doStop.booleanValue()) {
/* 234 */                       noOutputCounter++;
/* 236 */                       if (!sawInputEOS) {
/* 237 */                           long presentationTimeUs = 0;
/* 238 */                           int inputBufIndex = decoder2.dequeueInputBuffer(10000L);
/* 240 */                           if (inputBufIndex >= 0) {
/* 241 */                               ByteBuffer dstBuf = input[inputBufIndex];
/* 243 */                               int sampleSize = extractor2.readSampleData(dstBuf, 0);
/* 246 */                               if (sampleSize < 0) {
/* 247 */                                   sawInputEOS = true;
/* 248 */                                   sampleSize = 0;
                                        } else {
/* 250 */                                   presentationTimeUs = extractor2.getSampleTime();
                                        }
/* 252 */                               decoder2.queueInputBuffer(inputBufIndex, 0, sampleSize, presentationTimeUs, sawInputEOS ? 4 : 0);
/* 254 */                               if (!sawInputEOS) {
/* 255 */                                   extractor2.advance();
                                        }
                                    }
/* 258 */                           int res = decoder2.dequeueOutputBuffer(info, 10000L);
/* 259 */                           if (res >= 0) {
/* 260 */                               if (info.size > 0) {
/* 261 */                                   noOutputCounter = 0;
                                        }
/* 264 */                               ByteBuffer buf = output[res];
/* 266 */                               if (chunk == null || chunk.length < info.size) {
/* 267 */                                   chunk = new byte[info.size];
                                        }
/* 270 */                               buf.get(chunk, 0, info.size);
/* 271 */                               buf.clear();
/* 272 */                               int i = 0;
/* 276 */                               int ratio = channelCount * 2;
/* 278 */                               long sampleTimestampUs = info.presentationTimeUs;
/* 280 */                               long expectedSampleTime = ((long) bufferSize) + ((((long) (info.size / ratio)) * AudioUtils.MICROS_PER_SECOND) / ((long) sampleRate));
                                        if (info.presentationTimeUs != 0 && expectedSampleTime / info.presentationTimeUs == 2) {
/* 284 */                                   channelCount *= 2;
/* 285 */                                   ratio = channelCount * 2;
/* 286 */                                   SLog.m465i("Updated channel count: {}, one time upgrade.", Integer.valueOf(channelCount));
                                        }
/* 290 */                               while (i < info.size / ratio) {
/* 292 */                                   int value = (chunk[i * ratio] & Constants.UNKNOWN) | (chunk[(i * ratio) + 1] << 8);
/* 295 */                                   if (channelCount == 2) {
/* 296 */                                       int value2 = chunk[(i * ratio) + 2] & Constants.UNKNOWN;
/* 300 */                                       value = (value + (value2 | (chunk[(i * ratio) + 3] << 8))) / 2;
                                            }
/* 303 */                                   audioArray[bufferSize] = (short) value;
/* 307 */                                   sampleTimestampUs += sampleDurationUs;
                                            if (sampleTimestampUs >= endMicros2) {
/* 309 */                                       doStop = true;
/* 311 */                                       SLog.m447d("ryango last audio timestamp {}", Long.valueOf(sampleTimestampUs));
                                                break;
                                            } else {
/* 290 */                                       i++;
                                                bufferSize++;
                                            }
                                        }
/* 316 */                               decoder2.releaseOutputBuffer(res, false);
/* 317 */                               if ((info.flags & 4) != 0) {
/* 318 */                                   sawOutputEOS = true;
                                        }
                                    } else if (res == -3) {
/* 321 */                               output = decoder2.getOutputBuffers();
                                    } else if (res == -2) {
/* 323 */                               decoder2.getOutputFormat();
                                    }
                                }
                            }
/* 328 */                   if (!this.mCancel) {
/* 221 */                       if (sampleRate != 44100) {
/* 331 */                           long startNanos = System.nanoTime();
/* 333 */                           ByteBuffer buffer = ByteBuffer.allocate(audioArray.length * 2);
/* 334 */                           buffer.order(ByteOrder.LITTLE_ENDIAN);
/* 335 */                           buffer.asShortBuffer().put(audioArray);
/* 221 */                           byte[] out = SwVineFrameRecorder.resample(sampleRate, bufferSize, buffer.array(), 44100);
/* 337 */                           int shortCount = out.length / 2;
/* 338 */                           if (audioArray.length < shortCount) {
/* 340 */                               SLog.m448d("ryango reallocating audioArray old: {} new: {}", Integer.valueOf(audioArray.length), Integer.valueOf(shortCount));
/* 341 */                               audioArray = new short[shortCount];
                                    }
/* 344 */                           ByteBuffer.wrap(out).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(audioArray, 0, shortCount);
/* 345 */                           bufferSize = shortCount;
/* 347 */                           SLog.m447d("ryango resampling audio took {} ms", Long.valueOf((System.nanoTime() - startNanos) / AudioUtils.MICROS_PER_SECOND));
                                }
/* 350 */                       this.mAudioArray = AudioArrays.wrap(audioArray);
/* 351 */                       this.mSegment.addAudioData(new AudioData(0, bufferSize));
/* 352 */                       this.mAudioEncodingFinished = true;
/* 353 */                       tryFinishingRecording();
                            }
/* 356 */                   if (extractor2 != null) {
/* 357 */                       extractor2.release();
                            }
/* 361 */                   if (decoder2 != null) {
/* 362 */                       decoder2.stop();
/* 363 */                       decoder2.release();
                            }
                        } catch (Throwable th) {
/* 356 */                   th = th;
                            extractor = extractor2;
                            if (extractor != null) {
/* 357 */                       extractor.release();
                            }
/* 361 */                   if (0 != 0) {
/* 362 */                       decoder.stop();
/* 363 */                       decoder.release();
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

/* 369 */       private void generateNoise(long durationUs) {
/* 370 */           Random r = new Random();
/* 371 */           int samples = (int) ((44100 * durationUs) / MICROS_PER_SECOND);
/* 372 */           short[] buffer = new short[samples];
                    for (int i = 0; i < samples; i++) {
/* 374 */               buffer[i] = (short) (r.nextInt(avutil.FF_LAMBDA_MAX) * NOISE_GAIN);
                    }
/* 377 */           this.mAudioArray = AudioArrays.wrap(buffer);
/* 378 */           this.mSegment.addAudioData(new AudioData(0, samples));
/* 379 */           this.mAudioEncodingFinished = true;
/* 380 */           tryFinishingRecording();
                }

                @TargetApi(16)
/* 384 */       private void transcodeVideo(Uri uri, long startMicros, long endMicros, Point cropOrigin) throws Throwable {
                    TranscodingCodecOutputSurface outputSurface = null;
                    Throwable failure = null;
/* 385 */           MediaCodec decoder = null;
/* 387 */           MediaExtractor extractor = null;
                    try {
/* 390 */               MediaExtractor extractor2 = new MediaExtractor();
                        try {
/* 391 */                   extractor2.setDataSource(this.mContext, uri, (Map<String, String>) null);
/* 392 */                   int trackIndex = selectVideoTrack(extractor2);
/* 393 */                   if (trackIndex < 0) {
/* 394 */                       throw new RuntimeException("No video track found in " + uri.toString());
                            }
/* 396 */                   extractor2.selectTrack(trackIndex);
/* 398 */                   MediaFormat format = extractor2.getTrackFormat(trackIndex);
/* 400 */                   Log.d(TAG, "Video size is " + format.getInteger("width") + "x" + format.getInteger("height"));
/* 404 */                   if (format.containsKey("rotation-degrees")) {
/* 407 */                       format.setInteger("rotation-degrees", 0);
                            }
/* 410 */                   this.mWidth = format.getInteger("width");
/* 411 */                   this.mHeight = format.getInteger("height");
/* 413 */                   if (format.containsKey("frame-rate")) {
                                try {
/* 415 */                           this.mFrameRate = (int) format.getFloat("frame-rate");
                                } catch (ClassCastException e) {
/* 417 */                           this.mFrameRate = format.getInteger("frame-rate");
                                }
/* 420 */                       SLog.m447d("Frame rate: {}.", Integer.valueOf(this.mFrameRate));
                            } else {
/* 422 */                       this.mFrameRate = 30;
/* 423 */                       SLog.m447d("Frame rate missing: {}.", Integer.valueOf(this.mFrameRate));
                            }
/* 426 */                   this.mDurationMicros = endMicros - startMicros;
                            if (this.mDurationMicros == 0) {
/* 429 */                       throw new IllegalArgumentException("Duration can't be 0, end == start");
                            }
/* 432 */                   int totalFrames = (int) ((this.mDurationMicros / MICROS_PER_SECOND) * this.mFrameRate);
/* 434 */                   int orientation = 0;
/* 435 */                   MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
/* 436 */                   mediaMetadataRetriever.setDataSource(this.mContext, uri);
/* 437 */                   if (Build.VERSION.SDK_INT >= 17) {
/* 438 */                       String orientationString = mediaMetadataRetriever.extractMetadata(24);
/* 440 */                       if (orientationString != null) {
/* 441 */                           orientation = Integer.valueOf(orientationString).intValue();
                                }
                            } else {
/* 444 */                       Bitmap b = mediaMetadataRetriever.getFrameAtTime(0L);
/* 445 */                       if (this.mWidth != this.mHeight && this.mHeight == b.getWidth()) {
/* 446 */                           orientation = 90;
                                }
                            }
/* 449 */                   mediaMetadataRetriever.release();
/* 451 */                   outputSurface = new TranscodingCodecOutputSurface(this.mWidth, this.mHeight, cropOrigin, orientation, 480, totalFrames, this, this.mMaxVideoByteCount);
                            try {
/* 458 */                       String mime = format.getString("mime");
/* 459 */                       this.mVideoMime = mime;
/* 460 */                       decoder = MediaCodec.createDecoderByType(mime);
/* 461 */                       decoder.configure(format, outputSurface.getSurface(), (MediaCrypto) null, 0);
/* 462 */                       decoder.start();
/* 463 */                       doExtract(extractor2, trackIndex, decoder, outputSurface, startMicros, endMicros);
/* 466 */                       if (outputSurface != null) {
/* 467 */                           outputSurface.release();
                                }
/* 470 */                       if (decoder != null) {
/* 471 */                           decoder.stop();
/* 472 */                           decoder.release();
                                }
/* 475 */                       if (extractor2 != null) {
/* 476 */                           extractor2.release();
/* 480 */                           return;
                                }
/* 480 */                       return;
                            } catch (Throwable th) {
                                th = th;
                                extractor = extractor2;
                            }
                        } catch (Throwable th2) {
                            failure = th2;
                            extractor = extractor2;
                        }
                    } catch (Throwable th3) {
                        failure = th3;
                        outputSurface = null;
                    }
                    if (outputSurface != null) {
/* 467 */               outputSurface.release();
                    }
/* 470 */           if (decoder != null) {
/* 471 */               decoder.stop();
/* 472 */               decoder.release();
                    }
/* 475 */           if (extractor != null) {
/* 476 */               extractor.release();
                    }
                    if (failure != null) {
                        throw failure;
                    }
                }

/* 482 */       public SwVineFrameRecorder getRecorder() {
                    SwVineFrameRecorder recorder;
                    synchronized (SwVineFrameRecorder.LOCK) {
                        try {
/* 484 */                   recorder = RecordConfigUtils.newVideoRecorder(this.mRecorderOutPath, this.mFrameRate, this.mRecorderTargetSize, false);
                            try {
/* 486 */                       recorder.start();
                            } catch (SwVineFrameRecorder.Exception e) {
/* 488 */                       throw new RuntimeException(e);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return recorder;
                }

                @TargetApi(16)
/* 500 */       static int selectVideoTrack(MediaExtractor extractor) {
/* 501 */           return selectTrack(extractor, TYPE_VIDEO);
                }

                @TargetApi(16)
/* 510 */       static int selectAudioTrack(MediaExtractor extractor) {
/* 511 */           return selectTrack(extractor, TYPE_AUDIO);
                }

                @TargetApi(16)
/* 516 */       static int selectTrack(MediaExtractor extractor, String type) {
/* 517 */           int numTracks = extractor.getTrackCount();
                    for (int i = 0; i < numTracks; i++) {
/* 519 */               MediaFormat format = extractor.getTrackFormat(i);
/* 520 */               String mime = format.getString("mime");
/* 521 */               if (mime.startsWith(type)) {
/* 523 */                   Log.d(TAG, "Extractor selected track " + i + " (" + mime + "): " + format);
/* 529 */                   return i;
                        }
                    }
/* 529 */           return -1;
                }

                @TargetApi(16)
/* 538 */       private void doExtract(MediaExtractor extractor, int trackIndex, MediaCodec decoder, TranscodingCodecOutputSurface outputSurface, long startMicros, long endMicros) throws IOException {
/* 539 */           ByteBuffer[] decoderinput = decoder.getInputBuffers();
/* 540 */           MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
/* 541 */           int inputChunk = 0;
/* 542 */           boolean frameRendered = false;
/* 544 */           boolean outputDone = false;
/* 545 */           boolean inputDone = false;
/* 546 */           long totalDecodeNanos = 0;
/* 547 */           long totalDrawNanos = 0;
/* 549 */           extractor.seekTo(startMicros, 0);
/* 551 */           while (!outputDone && !this.mCancel) {
/* 553 */               long startDecodeNanos = System.nanoTime();
/* 554 */               if (!inputDone) {
/* 555 */                   int inputBufIndex = decoder.dequeueInputBuffer(10000L);
/* 556 */                   if (inputBufIndex >= 0) {
/* 557 */                       ByteBuffer inputBuf = decoderinput[inputBufIndex];
/* 560 */                       int chunkSize = extractor.readSampleData(inputBuf, 0);
/* 561 */                       if (chunkSize < 0) {
/* 563 */                           decoder.queueInputBuffer(inputBufIndex, 0, 0, 0L, 4);
/* 565 */                           inputDone = true;
/* 566 */                           Log.d(TAG, "sent input EOS");
                                } else {
/* 568 */                           if (extractor.getSampleTrackIndex() != trackIndex) {
/* 569 */                               Log.w(TAG, "WEIRD: got sample from track " + extractor.getSampleTrackIndex() + ", expected " + trackIndex);
                                    }
/* 572 */                           long presentationTimeUs = extractor.getSampleTime();
/* 573 */                           decoder.queueInputBuffer(inputBufIndex, 0, chunkSize, presentationTimeUs, 0);
/* 576 */                           Log.d(TAG, "submitted frame " + inputChunk + " to dec, size=" + chunkSize);
/* 579 */                           inputChunk++;
/* 580 */                           extractor.advance();
                                }
                            } else {
/* 583 */                       Log.d(TAG, "input buffer not available");
                            }
                        }
/* 587 */               int decoderStatus = decoder.dequeueOutputBuffer(info, 10000L);
/* 588 */               if (decoderStatus == -1) {
/* 590 */                   SLog.m446d("no output from decoder available");
                        } else if (decoderStatus == -3) {
/* 593 */                   SLog.m446d("decoder output buffers changed");
                        } else if (decoderStatus == -2) {
/* 595 */                   MediaFormat newFormat = decoder.getOutputFormat();
/* 596 */                   SLog.m446d("decoder output format changed: " + newFormat);
                        } else if (decoderStatus >= 0) {
/* 599 */                   SLog.m446d("surface decoder given buffer " + decoderStatus + " (size=" + info.size + ")");
/* 601 */                   if ((info.flags & 4) != 0) {
/* 602 */                       outputDone = true;
                            }
/* 605 */                   this.mExtractorTranscoderListener.onProgressUpdate((int) ((80 * (info.presentationTimeUs - startMicros)) / this.mDurationMicros));
                            if (info.presentationTimeUs >= endMicros) {
/* 608 */                       this.mExtractorTranscoderListener.onProgressUpdate(85);
/* 609 */                       outputDone = true;
                            }
/* 611 */                   boolean doRender = info.presentationTimeUs >= startMicros;
/* 617 */                   decoder.releaseOutputBuffer(decoderStatus, doRender);
/* 618 */                   totalDecodeNanos += System.nanoTime() - startDecodeNanos;
/* 620 */                   if (doRender) {
/* 621 */                       boolean draw = true;
                                try {
/* 623 */                           outputSurface.awaitNewImage();
                                } catch (Exception e) {
/* 626 */                           if (!frameRendered) {
/* 627 */                               CrashUtil.log("Failed to render any frames. They all timed out. model {} mime {} ", Build.MODEL, this.mVideoMime);
/* 628 */                               this.mExtractorTranscoderListener.onError(new RuntimeException("Failed to render any frames. They all timed out. model " + Build.MODEL + " mime " + this.mVideoMime));
/* 629 */                               cancel();
                                    }
/* 631 */                           draw = false;
/* 632 */                           outputDone = true;
                                }
/* 635 */                       if (draw) {
/* 636 */                           outputSurface.drawImage(true);
/* 638 */                           long startDrawNanos = System.nanoTime();
/* 639 */                           outputSurface.saveFrame(info.presentationTimeUs);
/* 640 */                           totalDrawNanos += System.nanoTime() - startDrawNanos;
/* 642 */                           frameRendered = true;
                                }
                            }
                        }
                    }
/* 648 */           SLog.m447d("ryango time spent decoding {}", Long.valueOf(totalDecodeNanos / AudioUtils.MICROS_PER_SECOND));
/* 649 */           SLog.m447d("ryango time spent drawing {}", Long.valueOf(totalDrawNanos / AudioUtils.MICROS_PER_SECOND));
/* 652 */           outputSurface.done();
                }

/* 656 */       public void onFinishedVideoEncoding(byte[] videoBytes, ArrayList<VideoData> videoData, String thumbnailPath, Bitmap scaledCroppedBitmap, int frameRate) {
/* 657 */           if (!this.mCancel) {
/* 658 */               this.mSegment.getVideoData().addAll(videoData);
/* 659 */               this.mSegment.setThumbnailPath(thumbnailPath);
/* 660 */               this.mSegment.setFrameRate(frameRate);
/* 661 */               this.mVideoBytes = videoBytes;
/* 662 */               this.mVideoEncodingFinished = true;
/* 663 */               if (scaledCroppedBitmap != null) {
/* 664 */                   this.mScaledCroppedBitmap = scaledCroppedBitmap;
                        }
/* 666 */               tryFinishingRecording();
                    }
                }

/* 674 */       public void tryFinishingRecording() {
/* 675 */           if (!this.mCancel && this.mAudioEncodingFinished && this.mVideoEncodingFinished) {
                        try {
/* 677 */                   long startNanos = System.nanoTime();
/* 678 */                   this.mSegment.saveDataToDiskIfNeeded(this.mDraftFolder, this.mVideoBytes, this.mAudioArray, null);
/* 679 */                   if (this.mExtractorTranscoderListener != null) {
/* 680 */                       this.mExtractorTranscoderListener.onFinishedTranscoding(this.mSegment, this.mScaledCroppedBitmap);
                            }
/* 683 */                   SLog.m447d("ryango saving to disk took {} ms", Long.valueOf((System.nanoTime() - startNanos) / AudioUtils.MICROS_PER_SECOND));
/* 684 */                   SLog.m446d("ryango total transcode time {} " + (System.currentTimeMillis() - this.mStartTime));
                        } catch (IOException e) {
/* 686 */                   throw new RuntimeException(e);
                        }
                    }
                }
            }
