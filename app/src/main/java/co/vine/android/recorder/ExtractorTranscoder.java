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
            
            
    public class ExtractorTranscoder {
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

        static  void access$000(ExtractorTranscoder x0, Uri x1, long x2, long x3) throws Throwable {
            x0.transcodeAudio(x1, x2, x3);
                }

        static  ExtractorTranscoderListener access$100(ExtractorTranscoder x0) {
            return x0.mExtractorTranscoderListener;
                }

        public int getFrameRate() {
            return this.mFrameRate;
                }

        public void cancel() {
            this.mCancel = true;
                }

        public void onEncodingProgressMade(long justProcessedPresentationTimestampUs) {
            if (this.mExtractorTranscoderListener != null && this.mDurationMicros > 0) {
                this.mExtractorTranscoderListener.onProgressUpdate((int) ((100 * justProcessedPresentationTimestampUs) / this.mDurationMicros));
                    }
                }

        public void transcode(Context context, File draftFolder, RecordSession session, RecordSegment segment, Uri inUri, String outPath, long startMicros, long endMicros, Point cropOrigin, ExtractorTranscoderListener listener) throws Throwable {
                    long adjustedEndMicros;
           this.mStartTime = System.currentTimeMillis();
           this.mContext = context;
           SLog.m446d("ryango startMicros " + startMicros + " endmicros " + endMicros + " cropOrigin " + cropOrigin);
           int maxDurationUs = session.getConfig().maxDuration * 1000;
                    if (endMicros - startMicros > maxDurationUs) {
               SLog.m447d("ryango too long, originally {} sec now trimmed", Float.valueOf((endMicros - startMicros) / MICROS_PER_SECOND));
               adjustedEndMicros = startMicros + ((long) maxDurationUs);
                    } else {
               adjustedEndMicros = endMicros;
                    }
           this.mExtractorTranscoderListener = listener;
           this.mConfig = session.getConfig();
           this.mSegment = segment;
           this.mDraftFolder = draftFolder;
           this.mMaxVideoByteCount = RecordConfigUtils.getVideoBufferMax(this.mConfig);
           this.mRecorderOutPath = outPath;
           this.mRecorderTargetSize = this.mConfig.targetSize;
                    try {
               TranscodeAudioThread audioThread = new TranscodeAudioThread(inUri, startMicros, adjustedEndMicros);
               audioThread.start();
               transcodeVideo(inUri, startMicros, adjustedEndMicros, cropOrigin);
               SLog.m446d("Video Transcode done.");
               audioThread.join();
                    } catch (InterruptedException e) {
               if (SLog.sLogsOn) {
                   e.printStackTrace();
                        }
                    } catch (Exception e2) {
               if (listener != null) {
                   listener.onError(e2);
                        }
               CrashUtil.logException(e2, "Error while importing", new Object[0]);
                    }
           SLog.m446d("Verified Audio Transcode done.");
                }

       private class TranscodeAudioThread extends Thread {
                    private long mEndMicros;
                    private long mStartMicros;
                    private Uri mUri;

           public TranscodeAudioThread(Uri uri, long startMicros, long endMicros) {
               this.mUri = uri;
               this.mStartMicros = startMicros;
               this.mEndMicros = endMicros;
                    }

                    @Override 
           public void run() {
               long nanos = System.nanoTime();
                        try {
                   ExtractorTranscoder.access$000(ExtractorTranscoder.this, this.mUri, this.mStartMicros, this.mEndMicros);
                        } catch (Throwable e) {
                            Exception error = e instanceof Exception ? (Exception) e : new RuntimeException(e);
                   if (ExtractorTranscoder.access$100(ExtractorTranscoder.this) != null) {
                       ExtractorTranscoder.access$100(ExtractorTranscoder.this).onError(error);
                            }
                   CrashUtil.logException(error, "Error while transcoding audio", new Object[0]);
                        }
               SLog.m446d("ryango total audio transcode time {} " + ((System.nanoTime() - nanos) / AudioUtils.MICROS_PER_SECOND));
                    }
                }

                @TargetApi(16)
       private void transcodeAudio(Uri uri, long startMicros, long endMicros) throws Throwable {
           MediaExtractor extractor = null;
           MediaCodec decoder = null;
                    try {
               MediaExtractor extractor2 = new MediaExtractor();
                        try {
                   extractor2.setDataSource(this.mContext, uri, (Map<String, String>) null);
                   int trackIndex = selectAudioTrack(extractor2);
                   if (trackIndex < 0) {
                       generateNoise(endMicros - startMicros);
                       if (extractor2 != null) {
                           extractor2.release();
                                }
                       if (0 != 0) {
                           decoder.stop();
                           decoder.release();
                           return;
                                }
                       return;
                            }
                   extractor2.selectTrack(trackIndex);
                   long endMicros2 = endMicros + 15000;
                   MediaFormat format = extractor2.getTrackFormat(trackIndex);
                   int channelCount = format.getInteger("channel-count");
                   if (format.containsKey("bitrate")) {
                       format.getInteger("bitrate");
                            }
                   if (format.containsKey("durationUs")) {
                       format.getLong("durationUs");
                            }
                   int sampleRate = format.containsKey("sample-rate") ? format.getInteger("sample-rate") : -1;
                   if (sampleRate < 0) {
                       throw new RuntimeException("no sample rate");
                            }
                   long sampleDurationUs = (long) ((1.0f / sampleRate) * MICROS_PER_SECOND);
                   String mime = format.getString("mime");
                   MediaCodec decoder2 = MediaCodec.createDecoderByType(mime);
                   decoder2.configure(format, (Surface) null, (MediaCrypto) null, 0);
                   decoder2.start();
                   ByteBuffer[] input = decoder2.getInputBuffers();
                   ByteBuffer[] output = decoder2.getOutputBuffers();
                   MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
                   boolean sawInputEOS = false;
                   boolean sawOutputEOS = false;
                   int noOutputCounter = 0;
                   Boolean doStop = false;
                   float sampleRatio = sampleRate / 44100;
                   short[] audioArray = new short[(int) (RecordConfigUtils.getMaxAudioBufferSize(this.mConfig) * sampleRatio * 2.0f)];
                   int bufferSize = 0;
                   extractor2.seekTo(startMicros, 0);
                   SLog.m448d("ryango audio start time {}: delta of {} from start", Long.valueOf(extractor2.getSampleTime()), Long.valueOf(extractor2.getSampleTime() - startMicros));
                   byte[] chunk = null;
                   while (!this.mCancel && !sawOutputEOS && noOutputCounter < 50 && !doStop.booleanValue()) {
                       noOutputCounter++;
                       if (!sawInputEOS) {
                           long presentationTimeUs = 0;
                           int inputBufIndex = decoder2.dequeueInputBuffer(10000L);
                           if (inputBufIndex >= 0) {
                               ByteBuffer dstBuf = input[inputBufIndex];
                               int sampleSize = extractor2.readSampleData(dstBuf, 0);
                               if (sampleSize < 0) {
                                   sawInputEOS = true;
                                   sampleSize = 0;
                                        } else {
                                   presentationTimeUs = extractor2.getSampleTime();
                                        }
                               decoder2.queueInputBuffer(inputBufIndex, 0, sampleSize, presentationTimeUs, sawInputEOS ? 4 : 0);
                               if (!sawInputEOS) {
                                   extractor2.advance();
                                        }
                                    }
                           int res = decoder2.dequeueOutputBuffer(info, 10000L);
                           if (res >= 0) {
                               if (info.size > 0) {
                                   noOutputCounter = 0;
                                        }
                               ByteBuffer buf = output[res];
                               if (chunk == null || chunk.length < info.size) {
                                   chunk = new byte[info.size];
                                        }
                               buf.get(chunk, 0, info.size);
                               buf.clear();
                               int i = 0;
                               int ratio = channelCount * 2;
                               long sampleTimestampUs = info.presentationTimeUs;
                               long expectedSampleTime = ((long) bufferSize) + ((((long) (info.size / ratio)) * AudioUtils.MICROS_PER_SECOND) / ((long) sampleRate));
                                        if (info.presentationTimeUs != 0 && expectedSampleTime / info.presentationTimeUs == 2) {
                                   channelCount *= 2;
                                   ratio = channelCount * 2;
                                   SLog.m465i("Updated channel count: {}, one time upgrade.", Integer.valueOf(channelCount));
                                        }
                               while (i < info.size / ratio) {
                                   int value = (chunk[i * ratio] & Constants.UNKNOWN) | (chunk[(i * ratio) + 1] << 8);
                                   if (channelCount == 2) {
                                       int value2 = chunk[(i * ratio) + 2] & Constants.UNKNOWN;
                                       value = (value + (value2 | (chunk[(i * ratio) + 3] << 8))) / 2;
                                            }
                                   audioArray[bufferSize] = (short) value;
                                   sampleTimestampUs += sampleDurationUs;
                                            if (sampleTimestampUs >= endMicros2) {
                                       doStop = true;
                                       SLog.m447d("ryango last audio timestamp {}", Long.valueOf(sampleTimestampUs));
                                                break;
                                            } else {
                                       i++;
                                                bufferSize++;
                                            }
                                        }
                               decoder2.releaseOutputBuffer(res, false);
                               if ((info.flags & 4) != 0) {
                                   sawOutputEOS = true;
                                        }
                                    } else if (res == -3) {
                               output = decoder2.getOutputBuffers();
                                    } else if (res == -2) {
                               decoder2.getOutputFormat();
                                    }
                                }
                            }
                   if (!this.mCancel) {
                       if (sampleRate != 44100) {
                           long startNanos = System.nanoTime();
                           ByteBuffer buffer = ByteBuffer.allocate(audioArray.length * 2);
                           buffer.order(ByteOrder.LITTLE_ENDIAN);
                           buffer.asShortBuffer().put(audioArray);
                           byte[] out = SwVineFrameRecorder.resample(sampleRate, bufferSize, buffer.array(), 44100);
                           int shortCount = out.length / 2;
                           if (audioArray.length < shortCount) {
                               SLog.m448d("ryango reallocating audioArray old: {} new: {}", Integer.valueOf(audioArray.length), Integer.valueOf(shortCount));
                               audioArray = new short[shortCount];
                                    }
                           ByteBuffer.wrap(out).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(audioArray, 0, shortCount);
                           bufferSize = shortCount;
                           SLog.m447d("ryango resampling audio took {} ms", Long.valueOf((System.nanoTime() - startNanos) / AudioUtils.MICROS_PER_SECOND));
                                }
                       this.mAudioArray = AudioArrays.wrap(audioArray);
                       this.mSegment.addAudioData(new AudioData(0, bufferSize));
                       this.mAudioEncodingFinished = true;
                       tryFinishingRecording();
                            }
                   if (extractor2 != null) {
                       extractor2.release();
                            }
                   if (decoder2 != null) {
                       decoder2.stop();
                       decoder2.release();
                            }
                        } catch (Throwable th) {
                   th = th;
                            extractor = extractor2;
                            if (extractor != null) {
                       extractor.release();
                            }
                   if (0 != 0) {
                       decoder.stop();
                       decoder.release();
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

       private void generateNoise(long durationUs) {
           Random r = new Random();
           int samples = (int) ((44100 * durationUs) / MICROS_PER_SECOND);
           short[] buffer = new short[samples];
                    for (int i = 0; i < samples; i++) {
               buffer[i] = (short) (r.nextInt(avutil.FF_LAMBDA_MAX) * NOISE_GAIN);
                    }
           this.mAudioArray = AudioArrays.wrap(buffer);
           this.mSegment.addAudioData(new AudioData(0, samples));
           this.mAudioEncodingFinished = true;
           tryFinishingRecording();
                }

                @TargetApi(16)
       private void transcodeVideo(Uri uri, long startMicros, long endMicros, Point cropOrigin) throws Throwable {
                    TranscodingCodecOutputSurface outputSurface = null;
                    Throwable failure = null;
           MediaCodec decoder = null;
           MediaExtractor extractor = null;
                    try {
               MediaExtractor extractor2 = new MediaExtractor();
                        try {
                   extractor2.setDataSource(this.mContext, uri, (Map<String, String>) null);
                   int trackIndex = selectVideoTrack(extractor2);
                   if (trackIndex < 0) {
                       throw new RuntimeException("No video track found in " + uri.toString());
                            }
                   extractor2.selectTrack(trackIndex);
                   MediaFormat format = extractor2.getTrackFormat(trackIndex);
                   Log.d(TAG, "Video size is " + format.getInteger("width") + "x" + format.getInteger("height"));
                   if (format.containsKey("rotation-degrees")) {
                       format.setInteger("rotation-degrees", 0);
                            }
                   this.mWidth = format.getInteger("width");
                   this.mHeight = format.getInteger("height");
                   if (format.containsKey("frame-rate")) {
                                try {
                           this.mFrameRate = (int) format.getFloat("frame-rate");
                                } catch (ClassCastException e) {
                           this.mFrameRate = format.getInteger("frame-rate");
                                }
                       SLog.m447d("Frame rate: {}.", Integer.valueOf(this.mFrameRate));
                            } else {
                       this.mFrameRate = 30;
                       SLog.m447d("Frame rate missing: {}.", Integer.valueOf(this.mFrameRate));
                            }
                   this.mDurationMicros = endMicros - startMicros;
                            if (this.mDurationMicros == 0) {
                       throw new IllegalArgumentException("Duration can't be 0, end == start");
                            }
                   int totalFrames = (int) ((this.mDurationMicros / MICROS_PER_SECOND) * this.mFrameRate);
                   int orientation = 0;
                   MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                   mediaMetadataRetriever.setDataSource(this.mContext, uri);
                   if (Build.VERSION.SDK_INT >= 17) {
                       String orientationString = mediaMetadataRetriever.extractMetadata(24);
                       if (orientationString != null) {
                           orientation = Integer.valueOf(orientationString).intValue();
                                }
                            } else {
                       Bitmap b = mediaMetadataRetriever.getFrameAtTime(0L);
                       if (this.mWidth != this.mHeight && this.mHeight == b.getWidth()) {
                           orientation = 90;
                                }
                            }
                   mediaMetadataRetriever.release();
                   outputSurface = new TranscodingCodecOutputSurface(this.mWidth, this.mHeight, cropOrigin, orientation, 480, totalFrames, this, this.mMaxVideoByteCount);
                            try {
                       String mime = format.getString("mime");
                       this.mVideoMime = mime;
                       decoder = MediaCodec.createDecoderByType(mime);
                       decoder.configure(format, outputSurface.getSurface(), (MediaCrypto) null, 0);
                       decoder.start();
                       doExtract(extractor2, trackIndex, decoder, outputSurface, startMicros, endMicros);
                       if (outputSurface != null) {
                           outputSurface.release();
                                }
                       if (decoder != null) {
                           decoder.stop();
                           decoder.release();
                                }
                       if (extractor2 != null) {
                           extractor2.release();
                           return;
                                }
                       return;
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
               outputSurface.release();
                    }
           if (decoder != null) {
               decoder.stop();
               decoder.release();
                    }
           if (extractor != null) {
               extractor.release();
                    }
                    if (failure != null) {
                        throw failure;
                    }
                }

       public SwVineFrameRecorder getRecorder() {
                    SwVineFrameRecorder recorder;
                    synchronized (SwVineFrameRecorder.LOCK) {
                        try {
                   recorder = RecordConfigUtils.newVideoRecorder(this.mRecorderOutPath, this.mFrameRate, this.mRecorderTargetSize, false);
                            try {
                       recorder.start();
                            } catch (SwVineFrameRecorder.Exception e) {
                       throw new RuntimeException(e);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return recorder;
                }

                @TargetApi(16)
       static int selectVideoTrack(MediaExtractor extractor) {
           return selectTrack(extractor, TYPE_VIDEO);
                }

                @TargetApi(16)
       static int selectAudioTrack(MediaExtractor extractor) {
           return selectTrack(extractor, TYPE_AUDIO);
                }

                @TargetApi(16)
       static int selectTrack(MediaExtractor extractor, String type) {
           int numTracks = extractor.getTrackCount();
                    for (int i = 0; i < numTracks; i++) {
               MediaFormat format = extractor.getTrackFormat(i);
               String mime = format.getString("mime");
               if (mime.startsWith(type)) {
                   Log.d(TAG, "Extractor selected track " + i + " (" + mime + "): " + format);
                   return i;
                        }
                    }
           return -1;
                }

                @TargetApi(16)
       private void doExtract(MediaExtractor extractor, int trackIndex, MediaCodec decoder, TranscodingCodecOutputSurface outputSurface, long startMicros, long endMicros) throws IOException {
           ByteBuffer[] decoderinput = decoder.getInputBuffers();
           MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
           int inputChunk = 0;
           boolean frameRendered = false;
           boolean outputDone = false;
           boolean inputDone = false;
           long totalDecodeNanos = 0;
           long totalDrawNanos = 0;
           extractor.seekTo(startMicros, 0);
           while (!outputDone && !this.mCancel) {
               long startDecodeNanos = System.nanoTime();
               if (!inputDone) {
                   int inputBufIndex = decoder.dequeueInputBuffer(10000L);
                   if (inputBufIndex >= 0) {
                       ByteBuffer inputBuf = decoderinput[inputBufIndex];
                       int chunkSize = extractor.readSampleData(inputBuf, 0);
                       if (chunkSize < 0) {
                           decoder.queueInputBuffer(inputBufIndex, 0, 0, 0L, 4);
                           inputDone = true;
                           Log.d(TAG, "sent input EOS");
                                } else {
                           if (extractor.getSampleTrackIndex() != trackIndex) {
                               Log.w(TAG, "WEIRD: got sample from track " + extractor.getSampleTrackIndex() + ", expected " + trackIndex);
                                    }
                           long presentationTimeUs = extractor.getSampleTime();
                           decoder.queueInputBuffer(inputBufIndex, 0, chunkSize, presentationTimeUs, 0);
                           Log.d(TAG, "submitted frame " + inputChunk + " to dec, size=" + chunkSize);
                           inputChunk++;
                           extractor.advance();
                                }
                            } else {
                       Log.d(TAG, "input buffer not available");
                            }
                        }
               int decoderStatus = decoder.dequeueOutputBuffer(info, 10000L);
               if (decoderStatus == -1) {
                   SLog.m446d("no output from decoder available");
                        } else if (decoderStatus == -3) {
                   SLog.m446d("decoder output buffers changed");
                        } else if (decoderStatus == -2) {
                   MediaFormat newFormat = decoder.getOutputFormat();
                   SLog.m446d("decoder output format changed: " + newFormat);
                        } else if (decoderStatus >= 0) {
                   SLog.m446d("surface decoder given buffer " + decoderStatus + " (size=" + info.size + ")");
                   if ((info.flags & 4) != 0) {
                       outputDone = true;
                            }
                   this.mExtractorTranscoderListener.onProgressUpdate((int) ((80 * (info.presentationTimeUs - startMicros)) / this.mDurationMicros));
                            if (info.presentationTimeUs >= endMicros) {
                       this.mExtractorTranscoderListener.onProgressUpdate(85);
                       outputDone = true;
                            }
                   boolean doRender = info.presentationTimeUs >= startMicros;
                   decoder.releaseOutputBuffer(decoderStatus, doRender);
                   totalDecodeNanos += System.nanoTime() - startDecodeNanos;
                   if (doRender) {
                       boolean draw = true;
                                try {
                           outputSurface.awaitNewImage();
                                } catch (Exception e) {
                           if (!frameRendered) {
                               CrashUtil.log("Failed to render any frames. They all timed out. model {} mime {} ", Build.MODEL, this.mVideoMime);
                               this.mExtractorTranscoderListener.onError(new RuntimeException("Failed to render any frames. They all timed out. model " + Build.MODEL + " mime " + this.mVideoMime));
                               cancel();
                                    }
                           draw = false;
                           outputDone = true;
                                }
                       if (draw) {
                           outputSurface.drawImage(true);
                           long startDrawNanos = System.nanoTime();
                           outputSurface.saveFrame(info.presentationTimeUs);
                           totalDrawNanos += System.nanoTime() - startDrawNanos;
                           frameRendered = true;
                                }
                            }
                        }
                    }
           SLog.m447d("ryango time spent decoding {}", Long.valueOf(totalDecodeNanos / AudioUtils.MICROS_PER_SECOND));
           SLog.m447d("ryango time spent drawing {}", Long.valueOf(totalDrawNanos / AudioUtils.MICROS_PER_SECOND));
           outputSurface.done();
                }

       public void onFinishedVideoEncoding(byte[] videoBytes, ArrayList<VideoData> videoData, String thumbnailPath, Bitmap scaledCroppedBitmap, int frameRate) {
           if (!this.mCancel) {
               this.mSegment.getVideoData().addAll(videoData);
               this.mSegment.setThumbnailPath(thumbnailPath);
               this.mSegment.setFrameRate(frameRate);
               this.mVideoBytes = videoBytes;
               this.mVideoEncodingFinished = true;
               if (scaledCroppedBitmap != null) {
                   this.mScaledCroppedBitmap = scaledCroppedBitmap;
                        }
               tryFinishingRecording();
                    }
                }

       public void tryFinishingRecording() {
           if (!this.mCancel && this.mAudioEncodingFinished && this.mVideoEncodingFinished) {
                        try {
                   long startNanos = System.nanoTime();
                   this.mSegment.saveDataToDiskIfNeeded(this.mDraftFolder, this.mVideoBytes, this.mAudioArray, null);
                   if (this.mExtractorTranscoderListener != null) {
                       this.mExtractorTranscoderListener.onFinishedTranscoding(this.mSegment, this.mScaledCroppedBitmap);
                            }
                   SLog.m447d("ryango saving to disk took {} ms", Long.valueOf((System.nanoTime() - startNanos) / AudioUtils.MICROS_PER_SECOND));
                   SLog.m446d("ryango total transcode time {} " + (System.currentTimeMillis() - this.mStartTime));
                        } catch (IOException e) {
                   throw new RuntimeException(e);
                        }
                    }
                }
            }
