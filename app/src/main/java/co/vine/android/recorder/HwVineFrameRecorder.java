            package co.vine.android.recorder;

            import android.content.Context;
            import android.os.Build;
            import android.util.Log;
            import com.edisonwang.android.slog.SLog;
            import com.lifevibes.mediacoder.LVMediaCoderFactory;
            import com.lifevibes.mediacoder.LVMediaCoderImpl;
            import java.io.IOException;
            import java.io.ObjectOutputStream;
            import java.nio.ByteBuffer;
            import java.nio.ShortBuffer;
            
            
    public class HwVineFrameRecorder {
                private static final int[] LOCK = new int[0];
                private static final boolean LOGGING = false;
                private static final String TAG = "Recorder";
                private long mAudioRecordTime;
                private final LVMediaCoderImpl mCoder;
                private int mEncodingCount;
                private int mFrameNumber;
                private boolean mHasData;
                private int mLastTimestamp;
                private ObjectOutputStream mLoggingVideoFile;
                private final String mOutput;
                private final double mTpfMs;
                private final double mTpfUs;
                private long mVideoEncodeTime;
                private long mVideoRecordTime;

                public static class Exception extends RuntimeException {
                }

                static {
                    try {
                System.loadLibrary("LV3GPWriterAndColorFilters");
                    } catch (UnsatisfiedLinkError e) {
                        Log.w(TAG, "Hardware recorder native library is unavailable on this ABI", e);
                    }
            if (Build.VERSION.SDK_INT < 16) {
                        try {
                    System.loadLibrary("LifeVibesSFEncoder");
                        } catch (UnsatisfiedLinkError e) {
                            Log.w(TAG, "Legacy hardware encoder native library is unavailable", e);
                        }
                    }
                }

        public HwVineFrameRecorder(Context context, String output, int frameRate) {
            Log.d(TAG, "Created frame recorder: encoding only? " + (output == null));
                    synchronized (LOCK) {
                        try {
                    Thread.dumpStack();
                    this.mOutput = output;
                    this.mCoder = LVMediaCoderFactory.create(context, RecordConfigUtils.getEncoderConfiguration());
                    if (this.mOutput == null) {
                        this.mCoder.startEncoding();
                            } else {
                        this.mCoder.startRecording(this.mOutput);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
            this.mTpfMs = 1000.0d / ((double) frameRate);
            this.mTpfUs = this.mTpfMs * 1000.0d;
                }

        public void recordAudio(byte[] data, int start, int size) {
            this.mHasData = true;
            int encoderSizeLimit = 2048;
            SLog.m456e("Audio buffer size: {}.", Integer.valueOf(size));
            long startTime = System.currentTimeMillis();
            while (size > 0) {
                if (size < encoderSizeLimit) {
                    encoderSizeLimit = size;
                        }
                ShortBuffer encoded = this.mCoder.encodeAudio(data, start, encoderSizeLimit);
                size -= encoderSizeLimit;
                start += encoderSizeLimit;
                encoded.position(0);
                this.mCoder.recordAudio(encoded);
                    }
            this.mAudioRecordTime += System.currentTimeMillis() - startTime;
                }

        public void stopEncoding() {
            SLog.m446d("Stop Encoding Start.");
            this.mCoder.stop();
            SLog.m446d("Stop Encoding Done.");
            if (this.mEncodingCount > 0) {
                SLog.m442b("Encoding video took: {}ms.", Long.valueOf(this.mVideoEncodeTime));
                SLog.m442b("Encoding fps: {}fps, ", Long.valueOf(((long) (this.mEncodingCount * 1000)) / this.mVideoEncodeTime));
                    }
                }

        public String getOutput() {
           return this.mOutput;
                }

       public long getTimestamp() {
           return Math.round(((double) this.mFrameNumber) * this.mTpfUs);
                }

       public void setTimestamp(long determinedTimestamp) {
           this.mFrameNumber = (int) Math.round(determinedTimestamp / this.mTpfUs);
                }

       public int getFrameNumber() {
           return this.mFrameNumber;
                }

       public void writeEncodedImage(byte[] videoDataArray, int start, int size) {
           long startTime = System.currentTimeMillis();
           this.mHasData = true;
           this.mLastTimestamp = (int) (((double) this.mFrameNumber) * this.mTpfMs);
           this.mCoder.recordVideo(ByteBuffer.wrap(videoDataArray, start, size), this.mLastTimestamp + 33);
           this.mFrameNumber++;
           this.mVideoRecordTime += System.currentTimeMillis() - startTime;
                }

       public void stopCombining() {
           if (this.mLoggingVideoFile != null) {
                        try {
                   this.mLoggingVideoFile.flush();
                   this.mLoggingVideoFile.close();
                   Log.d(TAG, "Video file binary saved.");
                        } catch (IOException e) {
                   if (SLog.sLogsOn) {
                       e.printStackTrace();
                            }
                        }
                    }
           SLog.m442b("Recording audio took: {}ms", Long.valueOf(this.mAudioRecordTime));
           SLog.m442b("Recording video took: {}ms.", Long.valueOf(this.mVideoRecordTime));
           this.mCoder.stop();
                }

       public boolean hasData() {
           return this.mHasData;
                }

       public int encode(ByteBuffer bytes, byte[] videoDataBufferMax, int videoDataBufferPosition, boolean lastFrame) {
           long startTime = System.currentTimeMillis();
           this.mHasData = true;
           ByteBuffer encoded = null;
           if (bytes == null) {
                        return -1;
                    }
           if (Build.VERSION.SDK_INT >= 16) {
               encoded = this.mCoder.encodeVideo(bytes, 1);
                    } else {
               this.mCoder.encodeVideoAsync(bytes, 1, lastFrame);
                    }
           if (this.mLoggingVideoFile != null) {
               bytes.position(0);
                        try {
                   this.mLoggingVideoFile.writeObject(bytes.array());
                        } catch (IOException e) {
                   if (SLog.sLogsOn) {
                       e.printStackTrace();
                            }
                        }
                    }
           if (encoded == null) {
                        return -1;
                    }
           int iPutEncodedData = putEncodedData(encoded, videoDataBufferMax, videoDataBufferPosition);
           this.mEncodingCount++;
           this.mVideoEncodeTime += System.currentTimeMillis() - startTime;
           return iPutEncodedData;
                }

       public int putEncodedData(ByteBuffer encoded, byte[] videoDataBufferMax, int videoDataBufferPosition) {
           ByteBuffer current = ByteBuffer.wrap(videoDataBufferMax);
           current.position(videoDataBufferPosition);
           current.put(encoded);
           return current.position() - videoDataBufferPosition;
                }

       public long getLastTimestamp() {
           return this.mLastTimestamp;
                }

       public void setDataListener(HwEncodingRunnable.EncodingProcessRunnable encodingProcessRunnable) {
           if (Build.VERSION.SDK_INT < 16) {
               this.mCoder.setEncoderVideoAsyncListener(encodingProcessRunnable);
                    }
                }
            }
