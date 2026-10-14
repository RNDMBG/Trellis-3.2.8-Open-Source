            package co.vine.android.recorder;

            import android.annotation.TargetApi;
            import android.content.Context;
            import android.graphics.Bitmap;
            import android.media.MediaCodec;
            import android.media.MediaCrypto;
            import android.media.MediaExtractor;
            import android.media.MediaFormat;
            import android.media.MediaMetadataRetriever;
            import android.net.Uri;
            import android.os.Build;
            import android.util.Log;
            import co.vine.android.util.CrashUtil;
            import com.edisonwang.android.slog.SLog;
            import java.io.IOException;
            import java.nio.ByteBuffer;
            import java.util.Map;
            import java.util.concurrent.LinkedBlockingDeque;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(16)
/* 24 */    public class ThumbnailExtractor implements ThumbnailExtractorInterface {
                private static final String TAG = "ThumbnailExtractor";
                private static final int TIMEOUT_USEC = 10000;
                public static final boolean VERBOSE = true;
                private Context mContext;
                private MediaCodec mDecoder;
                private int mHeight;
                private Uri mInputUri;
                private ThumbnailExtractorInterface.OnThumbnailRetrievedListener mListener;
                private MediaExtractor mMediaExtractor;
                private FrameExtractingCodecOutputSurface mOutputSurface;
                private int mTargetSize;
                private Thread mThumbnailExtractingThread;
                private int mWidth;
                private LinkedBlockingDeque<Long> mTimestamps = new LinkedBlockingDeque<>();
                private boolean mExit = false;
                private long mLastAddedTimestamp = Long.MIN_VALUE;
                private MediaMetadataExtractorThumbnailExtractor mFailoverExtractor = null;

/* 25 */        static /* synthetic */ MediaExtractor access$100(ThumbnailExtractor x0) {
/* 26 */            return x0.mMediaExtractor;
                }

/* 25 */        static /* synthetic */ boolean access$1000(ThumbnailExtractor x0) {
/* 26 */            return x0.mExit;
                }

/* 25 */        static /* synthetic */ boolean access$1002(ThumbnailExtractor x0, boolean x1) {
/* 26 */            x0.mExit = x1;
                    return x1;
                }

/* 25 */        static /* synthetic */ MediaExtractor access$102(ThumbnailExtractor x0, MediaExtractor x1) {
/* 26 */            x0.mMediaExtractor = x1;
                    return x1;
                }

/* 25 */        static /* synthetic */ LinkedBlockingDeque access$1100(ThumbnailExtractor x0) {
/* 26 */            return x0.mTimestamps;
                }

/* 25 */        static /* synthetic */ MediaMetadataExtractorThumbnailExtractor access$1200(ThumbnailExtractor x0) {
/* 26 */            return x0.mFailoverExtractor;
                }

/* 25 */        static /* synthetic */ MediaMetadataExtractorThumbnailExtractor access$1202(ThumbnailExtractor x0, MediaMetadataExtractorThumbnailExtractor x1) {
/* 26 */            x0.mFailoverExtractor = x1;
                    return x1;
                }

/* 25 */        static /* synthetic */ Context access$200(ThumbnailExtractor x0) {
/* 26 */            return x0.mContext;
                }

/* 25 */        static /* synthetic */ Uri access$300(ThumbnailExtractor x0) {
/* 26 */            return x0.mInputUri;
                }

/* 25 */        static /* synthetic */ ThumbnailExtractorInterface.OnThumbnailRetrievedListener access$400(ThumbnailExtractor x0) {
/* 26 */            return x0.mListener;
                }

/* 25 */        static /* synthetic */ int access$500(ThumbnailExtractor x0) {
/* 26 */            return x0.mWidth;
                }

/* 25 */        static /* synthetic */ int access$502(ThumbnailExtractor x0, int x1) {
/* 26 */            x0.mWidth = x1;
                    return x1;
                }

/* 25 */        static /* synthetic */ int access$600(ThumbnailExtractor x0) {
/* 26 */            return x0.mHeight;
                }

/* 25 */        static /* synthetic */ int access$602(ThumbnailExtractor x0, int x1) {
/* 26 */            x0.mHeight = x1;
                    return x1;
                }

/* 25 */        static /* synthetic */ FrameExtractingCodecOutputSurface access$700(ThumbnailExtractor x0) {
/* 26 */            return x0.mOutputSurface;
                }

/* 25 */        static /* synthetic */ FrameExtractingCodecOutputSurface access$702(ThumbnailExtractor x0, FrameExtractingCodecOutputSurface x1) {
/* 26 */            x0.mOutputSurface = x1;
                    return x1;
                }

/* 25 */        static /* synthetic */ int access$800(ThumbnailExtractor x0) {
/* 26 */            return x0.mTargetSize;
                }

/* 25 */        static /* synthetic */ MediaCodec access$900(ThumbnailExtractor x0) {
/* 26 */            return x0.mDecoder;
                }

/* 25 */        static /* synthetic */ MediaCodec access$902(ThumbnailExtractor x0, MediaCodec x1) {
/* 26 */            x0.mDecoder = x1;
                    return x1;
                }

                @Override // co.vine.android.recorder.ThumbnailExtractorInterface
/* 54 */        public void start(Context context, Uri input, int targetLongestSize, ThumbnailExtractorInterface.OnThumbnailRetrievedListener listener) {
/* 55 */            this.mContext = context;
/* 56 */            this.mInputUri = input;
/* 57 */            this.mTargetSize = targetLongestSize;
/* 58 */            this.mListener = listener;
/* 60 */            this.mThumbnailExtractingThread = new ThumbnailExtractingThread(this, null);
/* 61 */            this.mThumbnailExtractingThread.setPriority(10);
/* 62 */            this.mThumbnailExtractingThread.start();
                }

/* 63 */        private class ThumbnailExtractingThread extends Thread {
/* 64 */            private ThumbnailExtractingThread() {
                    }

/* 64 */            /* synthetic */ ThumbnailExtractingThread(ThumbnailExtractor x0, Object x1) {
/* 65 */                this();
                    }

                    @Override // java.lang.Thread, java.lang.Runnable
/* 67 */            public void run() {
/* 68 */                String mime = null;
                        try {
/* 70 */                    ThumbnailExtractor.access$102(ThumbnailExtractor.this, new MediaExtractor());
                            try {
/* 73 */                        ThumbnailExtractor.access$100(ThumbnailExtractor.this).setDataSource(ThumbnailExtractor.access$200(ThumbnailExtractor.this), ThumbnailExtractor.access$300(ThumbnailExtractor.this), (Map<String, String>) null);
/* 79 */                        int trackIndex = ExtractorTranscoder.selectVideoTrack(ThumbnailExtractor.access$100(ThumbnailExtractor.this));
/* 80 */                        if (trackIndex < 0) {
/* 81 */                            ThumbnailExtractor.access$400(ThumbnailExtractor.this).onError();
/* 82 */                            CrashUtil.logException(new Exception("No video track found in " + ThumbnailExtractor.access$300(ThumbnailExtractor.this).toString()));
/* 250 */                           return;
                                }
/* 86 */                        ThumbnailExtractor.access$100(ThumbnailExtractor.this).selectTrack(trackIndex);
/* 88 */                        MediaFormat format = ThumbnailExtractor.access$100(ThumbnailExtractor.this).getTrackFormat(trackIndex);
/* 90 */                        Log.d(ThumbnailExtractor.TAG, "Video size is " + format.getInteger("width") + "x" + format.getInteger("height"));
/* 94 */                        int orientation = 0;
/* 96 */                        if (Build.VERSION.SDK_INT >= 17) {
/* 97 */                            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
/* 98 */                            mediaMetadataRetriever.setDataSource(ThumbnailExtractor.access$200(ThumbnailExtractor.this), ThumbnailExtractor.access$300(ThumbnailExtractor.this));
/* 99 */                            String orientationString = mediaMetadataRetriever.extractMetadata(24);
/* 101 */                           if (orientationString != null) {
/* 102 */                               orientation = Integer.valueOf(orientationString).intValue();
                                    }
/* 104 */                           mediaMetadataRetriever.release();
                                }
/* 107 */                       ThumbnailExtractor.access$502(ThumbnailExtractor.this, format.getInteger("width"));
/* 108 */                       ThumbnailExtractor.access$602(ThumbnailExtractor.this, format.getInteger("height"));
/* 110 */                       ThumbnailExtractor.access$702(ThumbnailExtractor.this, new FrameExtractingCodecOutputSurface(ThumbnailExtractor.access$500(ThumbnailExtractor.this), ThumbnailExtractor.access$600(ThumbnailExtractor.this), ThumbnailExtractor.access$800(ThumbnailExtractor.this), orientation));
/* 112 */                       if (format.containsKey("rotation-degrees")) {
/* 115 */                           format.setInteger("rotation-degrees", 0);
                                }
/* 118 */                       mime = format.getString("mime");
/* 119 */                       ThumbnailExtractor.access$902(ThumbnailExtractor.this, MediaCodec.createDecoderByType(mime));
/* 120 */                       ThumbnailExtractor.access$900(ThumbnailExtractor.this).configure(format, ThumbnailExtractor.access$700(ThumbnailExtractor.this).getSurface(), (MediaCrypto) null, 0);
/* 121 */                       ThumbnailExtractor.access$900(ThumbnailExtractor.this).start();
/* 123 */                       ByteBuffer[] decoderinput = ThumbnailExtractor.access$900(ThumbnailExtractor.this).getInputBuffers();
/* 124 */                       MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
/* 125 */                       int inputChunk = 0;
/* 127 */                       boolean outputDone = false;
/* 128 */                       boolean inputDone = false;
/* 130 */                       long timestamp = -1;
/* 132 */                       while (!outputDone && !ThumbnailExtractor.access$1000(ThumbnailExtractor.this)) {
                                    if (timestamp == -1) {
                                        try {
/* 135 */                                   timestamp = ((Long) ThumbnailExtractor.access$1100(ThumbnailExtractor.this).take()).longValue();
                                        } catch (InterruptedException e) {
/* 137 */                                   inputDone = true;
/* 138 */                                   outputDone = true;
/* 139 */                                   ThumbnailExtractor.access$1002(ThumbnailExtractor.this, true);
                                        }
                                    }
/* 143 */                           if (!inputDone) {
/* 144 */                               int inputBufIndex = ThumbnailExtractor.access$900(ThumbnailExtractor.this).dequeueInputBuffer(10000L);
/* 145 */                               if (inputBufIndex >= 0) {
/* 146 */                                   ByteBuffer inputBuf = decoderinput[inputBufIndex];
/* 149 */                                   int chunkSize = ThumbnailExtractor.access$100(ThumbnailExtractor.this).readSampleData(inputBuf, 0);
/* 150 */                                   if (chunkSize < 0) {
/* 152 */                                       ThumbnailExtractor.access$900(ThumbnailExtractor.this).queueInputBuffer(inputBufIndex, 0, 0, 0L, 4);
/* 154 */                                       inputDone = true;
/* 155 */                                       Log.d(ThumbnailExtractor.TAG, "sent input EOS");
                                            } else {
/* 157 */                                       if (ThumbnailExtractor.access$100(ThumbnailExtractor.this).getSampleTrackIndex() != trackIndex) {
/* 158 */                                           Log.w(ThumbnailExtractor.TAG, "WEIRD: got sample from track " + ThumbnailExtractor.access$100(ThumbnailExtractor.this).getSampleTrackIndex() + ", expected " + trackIndex);
                                                }
/* 161 */                                       long presentationTimeUs = ThumbnailExtractor.access$100(ThumbnailExtractor.this).getSampleTime();
/* 162 */                                       ThumbnailExtractor.access$900(ThumbnailExtractor.this).queueInputBuffer(inputBufIndex, 0, chunkSize, presentationTimeUs, 0);
/* 165 */                                       Log.d(ThumbnailExtractor.TAG, "submitted frame " + inputChunk + " to dec, size=" + chunkSize);
/* 168 */                                       inputChunk++;
/* 169 */                                       ThumbnailExtractor.access$100(ThumbnailExtractor.this).advance();
                                            }
                                        } else {
/* 172 */                                   Log.d(ThumbnailExtractor.TAG, "input buffer not available");
                                        }
                                    }
/* 176 */                           int decoderStatus = ThumbnailExtractor.access$900(ThumbnailExtractor.this).dequeueOutputBuffer(info, 10000L);
/* 177 */                           if (decoderStatus == -1) {
/* 179 */                               SLog.m446d("no output from decoder available");
                                    } else if (decoderStatus == -3) {
/* 182 */                               SLog.m446d("decoder output buffers changed");
                                    } else if (decoderStatus == -2) {
/* 184 */                               MediaFormat newFormat = ThumbnailExtractor.access$900(ThumbnailExtractor.this).getOutputFormat();
/* 185 */                               SLog.m446d("decoder output format changed: " + newFormat);
                                    } else if (decoderStatus >= 0) {
/* 188 */                               SLog.m446d("surface decoder given buffer " + decoderStatus + " (size=" + info.size + ")");
/* 190 */                               if ((info.flags & 4) != 0) {
/* 191 */                                   outputDone = true;
                                        }
/* 194 */                               boolean doRender = false;
                                        if (info.presentationTimeUs >= timestamp) {
/* 196 */                                   doRender = true;
/* 197 */                                   timestamp = -1;
                                        }
/* 200 */                               if (!ThumbnailExtractor.access$1000(ThumbnailExtractor.this)) {
/* 201 */                                   ThumbnailExtractor.access$900(ThumbnailExtractor.this).releaseOutputBuffer(decoderStatus, doRender);
/* 203 */                                   if (doRender) {
/* 204 */                                       boolean draw = true;
                                                try {
/* 206 */                                           ThumbnailExtractor.access$700(ThumbnailExtractor.this).awaitNewImage();
                                                } catch (Exception e2) {
/* 208 */                                           draw = false;
                                                }
/* 210 */                                       if (draw) {
/* 211 */                                           ThumbnailExtractor.access$700(ThumbnailExtractor.this).drawImage(true);
/* 213 */                                           Bitmap b = ThumbnailExtractor.access$700(ThumbnailExtractor.this).saveFrame();
/* 215 */                                           if (ThumbnailExtractor.access$400(ThumbnailExtractor.this) != null) {
/* 216 */                                               ThumbnailExtractor.access$400(ThumbnailExtractor.this).onThumbnailRetrieved(timestamp, b);
                                                    }
                                                } else {
/* 219 */                                           ThumbnailExtractor.access$1002(ThumbnailExtractor.this, true);
                                                }
                                            }
                                        }
                                    }
                                }
/* 226 */                       if (ThumbnailExtractor.access$100(ThumbnailExtractor.this) != null) {
/* 227 */                           ThumbnailExtractor.access$100(ThumbnailExtractor.this).release();
/* 228 */                           ThumbnailExtractor.access$102(ThumbnailExtractor.this, null);
                                }
/* 231 */                       if (ThumbnailExtractor.access$900(ThumbnailExtractor.this) != null) {
/* 232 */                           ThumbnailExtractor.access$900(ThumbnailExtractor.this).stop();
/* 233 */                           ThumbnailExtractor.access$900(ThumbnailExtractor.this).release();
/* 234 */                           ThumbnailExtractor.access$902(ThumbnailExtractor.this, null);
                                }
/* 237 */                       if (ThumbnailExtractor.access$700(ThumbnailExtractor.this) != null) {
/* 238 */                           ThumbnailExtractor.access$700(ThumbnailExtractor.this).release();
/* 239 */                           ThumbnailExtractor.access$702(ThumbnailExtractor.this, null);
                                }
                            } catch (IOException e3) {
/* 75 */                        ThumbnailExtractor.access$400(ThumbnailExtractor.this).onError();
                            }
                        } catch (Exception e4) {
/* 242 */                   SLog.m446d("ryango failover");
/* 243 */                   CrashUtil.logException(e4, "Failing over from MediaCodec thumbnail extractor to MediaMetadataRetriever version - phone model {} mime {}", Build.MODEL, mime);
/* 244 */                   ThumbnailExtractor.access$1202(ThumbnailExtractor.this, new MediaMetadataExtractorThumbnailExtractor());
/* 245 */                   while (!ThumbnailExtractor.access$1100(ThumbnailExtractor.this).isEmpty()) {
/* 246 */                       ThumbnailExtractor.access$1200(ThumbnailExtractor.this).requestThumbnail(((Long) ThumbnailExtractor.access$1100(ThumbnailExtractor.this).removeFirst()).longValue());
                            }
/* 248 */                   ThumbnailExtractor.access$1200(ThumbnailExtractor.this).start(ThumbnailExtractor.access$200(ThumbnailExtractor.this), ThumbnailExtractor.access$300(ThumbnailExtractor.this), ThumbnailExtractor.access$800(ThumbnailExtractor.this), ThumbnailExtractor.access$400(ThumbnailExtractor.this));
                        }
                    }
                }

                @Override // co.vine.android.recorder.ThumbnailExtractorInterface
/* 253 */       public void requestThumbnail(long timestamp) {
                    if (timestamp <= this.mLastAddedTimestamp) {
/* 255 */               throw new IllegalArgumentException("Cannot request a thumbnail that is before the last one");
                    }
/* 257 */           this.mLastAddedTimestamp = timestamp;
/* 258 */           this.mTimestamps.add(Long.valueOf(timestamp));
                }

                @Override // co.vine.android.recorder.ThumbnailExtractorInterface
/* 261 */       public void stop() {
/* 262 */           this.mExit = true;
/* 263 */           if (this.mThumbnailExtractingThread != null) {
/* 264 */               this.mThumbnailExtractingThread.interrupt();
                    }
/* 266 */           if (this.mFailoverExtractor != null) {
/* 267 */               this.mFailoverExtractor.stop();
                    }
                }

/* 271 */       public static ThumbnailExtractorInterface getExtractor(boolean forceMetadataRetriever) {
                    return (forceMetadataRetriever || RecordConfigUtils.RecordConfig.SHOULD_USE_MEDIAMETADATAEXTRACTOR) ? new MediaMetadataExtractorThumbnailExtractor() : new ThumbnailExtractor();
                }
            }
