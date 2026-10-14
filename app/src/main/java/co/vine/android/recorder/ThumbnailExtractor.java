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
            
            
            @TargetApi(16)
    public class ThumbnailExtractor implements ThumbnailExtractorInterface {
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

        static  MediaExtractor access$100(ThumbnailExtractor x0) {
            return x0.mMediaExtractor;
                }

        static  boolean access$1000(ThumbnailExtractor x0) {
            return x0.mExit;
                }

        static  boolean access$1002(ThumbnailExtractor x0, boolean x1) {
            x0.mExit = x1;
                    return x1;
                }

        static  MediaExtractor access$102(ThumbnailExtractor x0, MediaExtractor x1) {
            x0.mMediaExtractor = x1;
                    return x1;
                }

        static  LinkedBlockingDeque access$1100(ThumbnailExtractor x0) {
            return x0.mTimestamps;
                }

        static  MediaMetadataExtractorThumbnailExtractor access$1200(ThumbnailExtractor x0) {
            return x0.mFailoverExtractor;
                }

        static  MediaMetadataExtractorThumbnailExtractor access$1202(ThumbnailExtractor x0, MediaMetadataExtractorThumbnailExtractor x1) {
            x0.mFailoverExtractor = x1;
                    return x1;
                }

        static  Context access$200(ThumbnailExtractor x0) {
            return x0.mContext;
                }

        static  Uri access$300(ThumbnailExtractor x0) {
            return x0.mInputUri;
                }

        static  ThumbnailExtractorInterface.OnThumbnailRetrievedListener access$400(ThumbnailExtractor x0) {
            return x0.mListener;
                }

        static  int access$500(ThumbnailExtractor x0) {
            return x0.mWidth;
                }

        static  int access$502(ThumbnailExtractor x0, int x1) {
            x0.mWidth = x1;
                    return x1;
                }

        static  int access$600(ThumbnailExtractor x0) {
            return x0.mHeight;
                }

        static  int access$602(ThumbnailExtractor x0, int x1) {
            x0.mHeight = x1;
                    return x1;
                }

        static  FrameExtractingCodecOutputSurface access$700(ThumbnailExtractor x0) {
            return x0.mOutputSurface;
                }

        static  FrameExtractingCodecOutputSurface access$702(ThumbnailExtractor x0, FrameExtractingCodecOutputSurface x1) {
            x0.mOutputSurface = x1;
                    return x1;
                }

        static  int access$800(ThumbnailExtractor x0) {
            return x0.mTargetSize;
                }

        static  MediaCodec access$900(ThumbnailExtractor x0) {
            return x0.mDecoder;
                }

        static  MediaCodec access$902(ThumbnailExtractor x0, MediaCodec x1) {
            x0.mDecoder = x1;
                    return x1;
                }

                @Override 
        public void start(Context context, Uri input, int targetLongestSize, ThumbnailExtractorInterface.OnThumbnailRetrievedListener listener) {
            this.mContext = context;
            this.mInputUri = input;
            this.mTargetSize = targetLongestSize;
            this.mListener = listener;
            this.mThumbnailExtractingThread = new ThumbnailExtractingThread(this, null);
            this.mThumbnailExtractingThread.setPriority(10);
            this.mThumbnailExtractingThread.start();
                }

        private class ThumbnailExtractingThread extends Thread {
            private ThumbnailExtractingThread() {
                    }

             ThumbnailExtractingThread(ThumbnailExtractor x0, Object x1) {
                this();
                    }

                    @Override 
            public void run() {
                String mime = null;
                        try {
                    ThumbnailExtractor.access$102(ThumbnailExtractor.this, new MediaExtractor());
                            try {
                        ThumbnailExtractor.access$100(ThumbnailExtractor.this).setDataSource(ThumbnailExtractor.access$200(ThumbnailExtractor.this), ThumbnailExtractor.access$300(ThumbnailExtractor.this), (Map<String, String>) null);
                        int trackIndex = ExtractorTranscoder.selectVideoTrack(ThumbnailExtractor.access$100(ThumbnailExtractor.this));
                        if (trackIndex < 0) {
                            ThumbnailExtractor.access$400(ThumbnailExtractor.this).onError();
                            CrashUtil.logException(new Exception("No video track found in " + ThumbnailExtractor.access$300(ThumbnailExtractor.this).toString()));
                           return;
                                }
                        ThumbnailExtractor.access$100(ThumbnailExtractor.this).selectTrack(trackIndex);
                        MediaFormat format = ThumbnailExtractor.access$100(ThumbnailExtractor.this).getTrackFormat(trackIndex);
                        Log.d(ThumbnailExtractor.TAG, "Video size is " + format.getInteger("width") + "x" + format.getInteger("height"));
                        int orientation = 0;
                        if (Build.VERSION.SDK_INT >= 17) {
                            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                            mediaMetadataRetriever.setDataSource(ThumbnailExtractor.access$200(ThumbnailExtractor.this), ThumbnailExtractor.access$300(ThumbnailExtractor.this));
                            String orientationString = mediaMetadataRetriever.extractMetadata(24);
                           if (orientationString != null) {
                               orientation = Integer.valueOf(orientationString).intValue();
                                    }
                           mediaMetadataRetriever.release();
                                }
                       ThumbnailExtractor.access$502(ThumbnailExtractor.this, format.getInteger("width"));
                       ThumbnailExtractor.access$602(ThumbnailExtractor.this, format.getInteger("height"));
                       ThumbnailExtractor.access$702(ThumbnailExtractor.this, new FrameExtractingCodecOutputSurface(ThumbnailExtractor.access$500(ThumbnailExtractor.this), ThumbnailExtractor.access$600(ThumbnailExtractor.this), ThumbnailExtractor.access$800(ThumbnailExtractor.this), orientation));
                       if (format.containsKey("rotation-degrees")) {
                           format.setInteger("rotation-degrees", 0);
                                }
                       mime = format.getString("mime");
                       ThumbnailExtractor.access$902(ThumbnailExtractor.this, MediaCodec.createDecoderByType(mime));
                       ThumbnailExtractor.access$900(ThumbnailExtractor.this).configure(format, ThumbnailExtractor.access$700(ThumbnailExtractor.this).getSurface(), (MediaCrypto) null, 0);
                       ThumbnailExtractor.access$900(ThumbnailExtractor.this).start();
                       ByteBuffer[] decoderinput = ThumbnailExtractor.access$900(ThumbnailExtractor.this).getInputBuffers();
                       MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
                       int inputChunk = 0;
                       boolean outputDone = false;
                       boolean inputDone = false;
                       long timestamp = -1;
                       while (!outputDone && !ThumbnailExtractor.access$1000(ThumbnailExtractor.this)) {
                                    if (timestamp == -1) {
                                        try {
                                   timestamp = ((Long) ThumbnailExtractor.access$1100(ThumbnailExtractor.this).take()).longValue();
                                        } catch (InterruptedException e) {
                                   inputDone = true;
                                   outputDone = true;
                                   ThumbnailExtractor.access$1002(ThumbnailExtractor.this, true);
                                        }
                                    }
                           if (!inputDone) {
                               int inputBufIndex = ThumbnailExtractor.access$900(ThumbnailExtractor.this).dequeueInputBuffer(10000L);
                               if (inputBufIndex >= 0) {
                                   ByteBuffer inputBuf = decoderinput[inputBufIndex];
                                   int chunkSize = ThumbnailExtractor.access$100(ThumbnailExtractor.this).readSampleData(inputBuf, 0);
                                   if (chunkSize < 0) {
                                       ThumbnailExtractor.access$900(ThumbnailExtractor.this).queueInputBuffer(inputBufIndex, 0, 0, 0L, 4);
                                       inputDone = true;
                                       Log.d(ThumbnailExtractor.TAG, "sent input EOS");
                                            } else {
                                       if (ThumbnailExtractor.access$100(ThumbnailExtractor.this).getSampleTrackIndex() != trackIndex) {
                                           Log.w(ThumbnailExtractor.TAG, "WEIRD: got sample from track " + ThumbnailExtractor.access$100(ThumbnailExtractor.this).getSampleTrackIndex() + ", expected " + trackIndex);
                                                }
                                       long presentationTimeUs = ThumbnailExtractor.access$100(ThumbnailExtractor.this).getSampleTime();
                                       ThumbnailExtractor.access$900(ThumbnailExtractor.this).queueInputBuffer(inputBufIndex, 0, chunkSize, presentationTimeUs, 0);
                                       Log.d(ThumbnailExtractor.TAG, "submitted frame " + inputChunk + " to dec, size=" + chunkSize);
                                       inputChunk++;
                                       ThumbnailExtractor.access$100(ThumbnailExtractor.this).advance();
                                            }
                                        } else {
                                   Log.d(ThumbnailExtractor.TAG, "input buffer not available");
                                        }
                                    }
                           int decoderStatus = ThumbnailExtractor.access$900(ThumbnailExtractor.this).dequeueOutputBuffer(info, 10000L);
                           if (decoderStatus == -1) {
                               SLog.m446d("no output from decoder available");
                                    } else if (decoderStatus == -3) {
                               SLog.m446d("decoder output buffers changed");
                                    } else if (decoderStatus == -2) {
                               MediaFormat newFormat = ThumbnailExtractor.access$900(ThumbnailExtractor.this).getOutputFormat();
                               SLog.m446d("decoder output format changed: " + newFormat);
                                    } else if (decoderStatus >= 0) {
                               SLog.m446d("surface decoder given buffer " + decoderStatus + " (size=" + info.size + ")");
                               if ((info.flags & 4) != 0) {
                                   outputDone = true;
                                        }
                               boolean doRender = false;
                                        if (info.presentationTimeUs >= timestamp) {
                                   doRender = true;
                                   timestamp = -1;
                                        }
                               if (!ThumbnailExtractor.access$1000(ThumbnailExtractor.this)) {
                                   ThumbnailExtractor.access$900(ThumbnailExtractor.this).releaseOutputBuffer(decoderStatus, doRender);
                                   if (doRender) {
                                       boolean draw = true;
                                                try {
                                           ThumbnailExtractor.access$700(ThumbnailExtractor.this).awaitNewImage();
                                                } catch (Exception e2) {
                                           draw = false;
                                                }
                                       if (draw) {
                                           ThumbnailExtractor.access$700(ThumbnailExtractor.this).drawImage(true);
                                           Bitmap b = ThumbnailExtractor.access$700(ThumbnailExtractor.this).saveFrame();
                                           if (ThumbnailExtractor.access$400(ThumbnailExtractor.this) != null) {
                                               ThumbnailExtractor.access$400(ThumbnailExtractor.this).onThumbnailRetrieved(timestamp, b);
                                                    }
                                                } else {
                                           ThumbnailExtractor.access$1002(ThumbnailExtractor.this, true);
                                                }
                                            }
                                        }
                                    }
                                }
                       if (ThumbnailExtractor.access$100(ThumbnailExtractor.this) != null) {
                           ThumbnailExtractor.access$100(ThumbnailExtractor.this).release();
                           ThumbnailExtractor.access$102(ThumbnailExtractor.this, null);
                                }
                       if (ThumbnailExtractor.access$900(ThumbnailExtractor.this) != null) {
                           ThumbnailExtractor.access$900(ThumbnailExtractor.this).stop();
                           ThumbnailExtractor.access$900(ThumbnailExtractor.this).release();
                           ThumbnailExtractor.access$902(ThumbnailExtractor.this, null);
                                }
                       if (ThumbnailExtractor.access$700(ThumbnailExtractor.this) != null) {
                           ThumbnailExtractor.access$700(ThumbnailExtractor.this).release();
                           ThumbnailExtractor.access$702(ThumbnailExtractor.this, null);
                                }
                            } catch (IOException e3) {
                        ThumbnailExtractor.access$400(ThumbnailExtractor.this).onError();
                            }
                        } catch (Exception e4) {
                   SLog.m446d("ryango failover");
                   CrashUtil.logException(e4, "Failing over from MediaCodec thumbnail extractor to MediaMetadataRetriever version - phone model {} mime {}", Build.MODEL, mime);
                   ThumbnailExtractor.access$1202(ThumbnailExtractor.this, new MediaMetadataExtractorThumbnailExtractor());
                   while (!ThumbnailExtractor.access$1100(ThumbnailExtractor.this).isEmpty()) {
                       ThumbnailExtractor.access$1200(ThumbnailExtractor.this).requestThumbnail(((Long) ThumbnailExtractor.access$1100(ThumbnailExtractor.this).removeFirst()).longValue());
                            }
                   ThumbnailExtractor.access$1200(ThumbnailExtractor.this).start(ThumbnailExtractor.access$200(ThumbnailExtractor.this), ThumbnailExtractor.access$300(ThumbnailExtractor.this), ThumbnailExtractor.access$800(ThumbnailExtractor.this), ThumbnailExtractor.access$400(ThumbnailExtractor.this));
                        }
                    }
                }

                @Override 
       public void requestThumbnail(long timestamp) {
                    if (timestamp <= this.mLastAddedTimestamp) {
               throw new IllegalArgumentException("Cannot request a thumbnail that is before the last one");
                    }
           this.mLastAddedTimestamp = timestamp;
           this.mTimestamps.add(Long.valueOf(timestamp));
                }

                @Override 
       public void stop() {
           this.mExit = true;
           if (this.mThumbnailExtractingThread != null) {
               this.mThumbnailExtractingThread.interrupt();
                    }
           if (this.mFailoverExtractor != null) {
               this.mFailoverExtractor.stop();
                    }
                }

       public static ThumbnailExtractorInterface getExtractor(boolean forceMetadataRetriever) {
                    return (forceMetadataRetriever || RecordConfigUtils.RecordConfig.SHOULD_USE_MEDIAMETADATAEXTRACTOR) ? new MediaMetadataExtractorThumbnailExtractor() : new ThumbnailExtractor();
                }
            }
