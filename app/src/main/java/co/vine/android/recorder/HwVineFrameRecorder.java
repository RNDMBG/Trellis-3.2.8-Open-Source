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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 35 */    public class HwVineFrameRecorder {
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
/* 40 */                System.loadLibrary("LV3GPWriterAndColorFilters");
                    } catch (UnsatisfiedLinkError e) {
                        Log.w(TAG, "Hardware recorder native library is unavailable on this ABI", e);
                    }
/* 41 */            if (Build.VERSION.SDK_INT < 16) {
                        try {
/* 42 */                    System.loadLibrary("LifeVibesSFEncoder");
                        } catch (UnsatisfiedLinkError e) {
                            Log.w(TAG, "Legacy hardware encoder native library is unavailable", e);
                        }
                    }
                }

/* 48 */        public HwVineFrameRecorder(Context context, String output, int frameRate) {
/* 50 */            Log.d(TAG, "Created frame recorder: encoding only? " + (output == null));
                    synchronized (LOCK) {
                        try {
/* 52 */                    Thread.dumpStack();
/* 53 */                    this.mOutput = output;
/* 58 */                    this.mCoder = LVMediaCoderFactory.create(context, RecordConfigUtils.getEncoderConfiguration());
/* 59 */                    if (this.mOutput == null) {
/* 60 */                        this.mCoder.startEncoding();
                            } else {
/* 62 */                        this.mCoder.startRecording(this.mOutput);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 65 */            this.mTpfMs = 1000.0d / ((double) frameRate);
/* 66 */            this.mTpfUs = this.mTpfMs * 1000.0d;
                }

/* 69 */        public void recordAudio(byte[] data, int start, int size) {
/* 70 */            this.mHasData = true;
/* 72 */            int encoderSizeLimit = 2048;
/* 74 */            SLog.m456e("Audio buffer size: {}.", Integer.valueOf(size));
/* 75 */            long startTime = System.currentTimeMillis();
/* 76 */            while (size > 0) {
/* 77 */                if (size < encoderSizeLimit) {
/* 78 */                    encoderSizeLimit = size;
                        }
/* 80 */                ShortBuffer encoded = this.mCoder.encodeAudio(data, start, encoderSizeLimit);
/* 81 */                size -= encoderSizeLimit;
/* 82 */                start += encoderSizeLimit;
/* 83 */                encoded.position(0);
/* 84 */                this.mCoder.recordAudio(encoded);
                    }
/* 86 */            this.mAudioRecordTime += System.currentTimeMillis() - startTime;
                }

/* 89 */        public void stopEncoding() {
/* 90 */            SLog.m446d("Stop Encoding Start.");
/* 91 */            this.mCoder.stop();
/* 92 */            SLog.m446d("Stop Encoding Done.");
/* 93 */            if (this.mEncodingCount > 0) {
/* 94 */                SLog.m442b("Encoding video took: {}ms.", Long.valueOf(this.mVideoEncodeTime));
/* 95 */                SLog.m442b("Encoding fps: {}fps, ", Long.valueOf(((long) (this.mEncodingCount * 1000)) / this.mVideoEncodeTime));
                    }
                }

/* 99 */        public String getOutput() {
/* 100 */           return this.mOutput;
                }

/* 103 */       public long getTimestamp() {
/* 104 */           return Math.round(((double) this.mFrameNumber) * this.mTpfUs);
                }

/* 107 */       public void setTimestamp(long determinedTimestamp) {
/* 108 */           this.mFrameNumber = (int) Math.round(determinedTimestamp / this.mTpfUs);
                }

/* 111 */       public int getFrameNumber() {
/* 112 */           return this.mFrameNumber;
                }

/* 115 */       public void writeEncodedImage(byte[] videoDataArray, int start, int size) {
/* 116 */           long startTime = System.currentTimeMillis();
/* 117 */           this.mHasData = true;
/* 118 */           this.mLastTimestamp = (int) (((double) this.mFrameNumber) * this.mTpfMs);
/* 119 */           this.mCoder.recordVideo(ByteBuffer.wrap(videoDataArray, start, size), this.mLastTimestamp + 33);
/* 120 */           this.mFrameNumber++;
/* 121 */           this.mVideoRecordTime += System.currentTimeMillis() - startTime;
                }

/* 124 */       public void stopCombining() {
/* 125 */           if (this.mLoggingVideoFile != null) {
                        try {
/* 127 */                   this.mLoggingVideoFile.flush();
/* 128 */                   this.mLoggingVideoFile.close();
/* 129 */                   Log.d(TAG, "Video file binary saved.");
                        } catch (IOException e) {
/* 131 */                   if (SLog.sLogsOn) {
/* 132 */                       e.printStackTrace();
                            }
                        }
                    }
/* 136 */           SLog.m442b("Recording audio took: {}ms", Long.valueOf(this.mAudioRecordTime));
/* 137 */           SLog.m442b("Recording video took: {}ms.", Long.valueOf(this.mVideoRecordTime));
/* 138 */           this.mCoder.stop();
                }

/* 141 */       public boolean hasData() {
/* 142 */           return this.mHasData;
                }

/* 145 */       public int encode(ByteBuffer bytes, byte[] videoDataBufferMax, int videoDataBufferPosition, boolean lastFrame) {
/* 146 */           long startTime = System.currentTimeMillis();
/* 156 */           this.mHasData = true;
/* 159 */           ByteBuffer encoded = null;
/* 160 */           if (bytes == null) {
                        return -1;
                    }
/* 174 */           if (Build.VERSION.SDK_INT >= 16) {
/* 175 */               encoded = this.mCoder.encodeVideo(bytes, 1);
                    } else {
/* 177 */               this.mCoder.encodeVideoAsync(bytes, 1, lastFrame);
                    }
/* 179 */           if (this.mLoggingVideoFile != null) {
/* 180 */               bytes.position(0);
                        try {
/* 182 */                   this.mLoggingVideoFile.writeObject(bytes.array());
                        } catch (IOException e) {
/* 184 */                   if (SLog.sLogsOn) {
/* 185 */                       e.printStackTrace();
                            }
                        }
                    }
/* 189 */           if (encoded == null) {
                        return -1;
                    }
/* 196 */           int iPutEncodedData = putEncodedData(encoded, videoDataBufferMax, videoDataBufferPosition);
/* 197 */           this.mEncodingCount++;
/* 198 */           this.mVideoEncodeTime += System.currentTimeMillis() - startTime;
/* 199 */           return iPutEncodedData;
                }

/* 202 */       public int putEncodedData(ByteBuffer encoded, byte[] videoDataBufferMax, int videoDataBufferPosition) {
/* 203 */           ByteBuffer current = ByteBuffer.wrap(videoDataBufferMax);
/* 204 */           current.position(videoDataBufferPosition);
/* 205 */           current.put(encoded);
/* 206 */           return current.position() - videoDataBufferPosition;
                }

/* 209 */       public long getLastTimestamp() {
/* 210 */           return this.mLastTimestamp;
                }

/* 213 */       public void setDataListener(HwEncodingRunnable.EncodingProcessRunnable encodingProcessRunnable) {
/* 214 */           if (Build.VERSION.SDK_INT < 16) {
/* 215 */               this.mCoder.setEncoderVideoAsyncListener(encodingProcessRunnable);
                    }
                }
            }
