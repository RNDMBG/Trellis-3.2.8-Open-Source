            package co.vine.android.embed.player;

            import android.media.MediaCodec;
            import android.media.MediaCrypto;
            import android.media.MediaFormat;
            import android.os.Build;
            import android.util.Log;
            import android.view.Surface;
            import co.vine.android.util.MediaCodecUtil;
            import java.io.IOException;
            import java.nio.ByteBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 55 */    public class VideoRenderer {
                private static final int FRAME_DELAY_THRESHOLD = 50;
                private static final String KEY_CROP_BOTTOM = "crop-bottom";
                private static final String KEY_CROP_LEFT = "crop-left";
                private static final String KEY_CROP_RIGHT = "crop-right";
                private static final String KEY_CROP_TOP = "crop-top";
                private static final String TAG = "VideoRenderer";
                private MediaCodec mCodec;
                private int mCurrentFrameIndex;
                private int mCurrentHeight;
                private ByteBuffer mCurrentInputBuffer;
                private int mCurrentWidth;
                private MediaFormat mFormat;
                private byte[] mFrameData;
                private MediaCodec.BufferInfo[] mFrameInfo;
                private ByteBuffer[] mInputBuffers;
                private int mInputIndex;
                private long mLastPositionUs;
                private long mLastRenderMs;
                private final MediaCodec.BufferInfo mOutputBufferInfo = new MediaCodec.BufferInfo();
                private int mOutputIndex;
                private boolean mRenderedFirstFrame;
                private long mRendereredFrames;
                private Surface mSurface;
                private boolean mWaitingForFirstSyncFrame;

/* 56 */        public void release() {
/* 57 */            releaseCodec();
/* 58 */            this.mCurrentInputBuffer = null;
/* 59 */            this.mInputBuffers = null;
/* 60 */            this.mFrameInfo = null;
/* 61 */            this.mFrameData = null;
                }

/* 64 */        public void doSomeWork(MediaClock clock, boolean hasAudioTrack, boolean justResumed) throws IOException {
/* 65 */            long positionUs = clock.positionUs();
/* 66 */            if (this.mCurrentFrameIndex >= this.mFrameInfo.length || this.mLastPositionUs > positionUs) {
/* 68 */                this.mCurrentFrameIndex = 0;
/* 69 */                if (!hasAudioTrack) {
/* 70 */                    clock.setPosition(0L);
                        }
/* 72 */                flushCodec();
                    }
/* 75 */            while (drainOutputBuffer(clock, hasAudioTrack)) {
                    }
/* 77 */            if (feedInputBuffer(clock, hasAudioTrack)) {
/* 78 */                while (feedInputBuffer(clock, hasAudioTrack)) {
                        }
                    }
/* 81 */            this.mLastPositionUs = positionUs;
                }

/* 84 */        public void prepare(VineSampleSource source, Surface surface) throws IOException {
/* 85 */            long start = System.currentTimeMillis();
/* 86 */            this.mFrameInfo = source.getVideoFrameInfo();
/* 87 */            this.mFrameData = source.getVideoFrames();
/* 88 */            this.mFormat = source.getVideoTrackFormat();
/* 89 */            if (this.mSurface != surface) {
/* 92 */                this.mInputIndex = -1;
/* 93 */                this.mOutputIndex = -1;
/* 94 */                this.mSurface = surface;
/* 95 */                releaseCodec();
/* 96 */                initCodec();
/* 97 */                Log.i(TAG, "Video prepare took " + (System.currentTimeMillis() - start));
                    }
                }

/* 100 */       private void initCodec() throws IOException {
/* 101 */           String selectedDecoderName = MediaCodecUtil.getDecoderInfo(this.mFormat.getString("mime"));
/* 103 */           this.mCodec = MediaCodec.createByCodecName(selectedDecoderName);
/* 104 */           this.mCodec.configure(this.mFormat, this.mSurface, (MediaCrypto) null, 0);
/* 105 */           this.mCodec.setVideoScalingMode(1);
/* 106 */           this.mCodec.start();
/* 107 */           this.mInputBuffers = this.mCodec.getInputBuffers();
/* 108 */           this.mInputIndex = -1;
/* 109 */           this.mOutputIndex = -1;
/* 110 */           this.mWaitingForFirstSyncFrame = true;
                }

/* 113 */       private void releaseCodec() {
/* 114 */           if (this.mCodec != null) {
/* 115 */               this.mInputIndex = -1;
/* 116 */               this.mOutputIndex = -1;
/* 117 */               this.mInputBuffers = null;
                        try {
/* 119 */                   this.mCodec.stop();
                            try {
/* 122 */                       this.mCodec.release();
/* 124 */                       this.mCodec = null;
                            } catch (Throwable th) {
                                this.mCodec = null;
                                throw th;
                            }
                        } catch (Throwable th2) {
                            try {
/* 122 */                       this.mCodec.release();
/* 124 */                       this.mCodec = null;
                                throw th2;
                            } catch (Throwable th3) {
                                this.mCodec = null;
                                throw th3;
                            }
                        }
                    }
                }

/* 131 */       private boolean processOutputBuffer(MediaClock clock, MediaCodec codec, MediaCodec.BufferInfo bufferInfo, int bufferIndex) {
                    if (clock.getPositionSetTimeMs() == 0) {
/* 134 */               clock.setPosition(0L);
                    }
/* 138 */           long earlyUs = (bufferInfo.presentationTimeUs - clock.positionUs()) - clock.timeSincePositionSetUs();
/* 140 */           long renderingGapUs = (System.currentTimeMillis() - this.mLastRenderMs) * 1000;
/* 142 */           long renderingDiff = renderingGapUs + earlyUs;
                    if (earlyUs < -30000 && earlyUs > -70000) {
/* 145 */               Log.e(TAG, "Delayed Frame used " + earlyUs + " diff: " + renderingDiff);
/* 146 */               renderOutputBufferImmediate(codec, bufferIndex);
/* 147 */               return true;
                    }
/* 150 */           if (!this.mRenderedFirstFrame) {
/* 151 */               renderOutputBufferImmediate(codec, bufferIndex);
/* 152 */               this.mRenderedFirstFrame = true;
/* 153 */               return true;
                    }
                    if (earlyUs < -70000) {
/* 158 */               dropOutputBuffer(codec, bufferIndex);
/* 159 */               Log.e(TAG, "Delayed Frame dropped " + earlyUs + " diff: " + renderingDiff);
/* 160 */               return true;
                    }
                    if (earlyUs < 30000) {
                        if (earlyUs > 11000) {
                            try {
/* 169 */                       Thread.sleep((earlyUs - 10000) / 1000);
                            } catch (InterruptedException e) {
/* 171 */                       Thread.currentThread().interrupt();
                            }
                        }
/* 174 */               renderOutputBufferImmediate(codec, bufferIndex);
/* 175 */               return true;
                    }
/* 179 */           return false;
                }

/* 182 */       private void dropOutputBuffer(MediaCodec codec, int bufferIndex) {
/* 183 */           codec.releaseOutputBuffer(bufferIndex, false);
                }

/* 186 */       private void renderOutputBufferImmediate(MediaCodec codec, int bufferIndex) {
/* 187 */           onFrameRendered();
/* 188 */           codec.releaseOutputBuffer(bufferIndex, true);
                }

/* 191 */       private void onFrameRendered() {
                    if (this.mLastRenderMs > 0) {
/* 193 */               long timeSinceLastDrain = System.currentTimeMillis() - this.mLastRenderMs;
                        if (timeSinceLastDrain > 50) {
/* 195 */                   Log.e(TAG, "!Delayed! Time since last frame was rendered " + timeSinceLastDrain);
                        }
                    }
/* 199 */           this.mLastRenderMs = System.currentTimeMillis();
/* 201 */           this.mRendereredFrames++;
                }

/* 205 */       private void flushCodec() throws IOException {
/* 206 */           long start = System.currentTimeMillis();
/* 207 */           this.mInputIndex = -1;
/* 208 */           this.mOutputIndex = -1;
/* 209 */           this.mWaitingForFirstSyncFrame = true;
/* 212 */           if (Build.VERSION.SDK_INT >= 18) {
/* 213 */               this.mCodec.flush();
                    } else {
/* 215 */               releaseCodec();
/* 216 */               initCodec();
                    }
/* 218 */           Log.i(TAG, "Flush took " + (System.currentTimeMillis() - start));
                }

/* 227 */       private boolean feedInputBuffer(MediaClock clock, boolean hasAudioTrack) throws IOException {
/* 228 */           if (this.mInputIndex < 0) {
/* 229 */               this.mInputIndex = this.mCodec.dequeueInputBuffer(0L);
/* 230 */               if (this.mInputIndex < 0) {
/* 231 */                   return false;
                        }
/* 233 */               this.mCurrentInputBuffer = this.mInputBuffers[this.mInputIndex];
/* 234 */               this.mCurrentInputBuffer.clear();
                    }
/* 237 */           MediaCodec.BufferInfo nextFrame = this.mFrameInfo[this.mCurrentFrameIndex];
/* 238 */           int offset = this.mCurrentInputBuffer.position();
/* 239 */           this.mCurrentInputBuffer.put(this.mFrameData, nextFrame.offset, nextFrame.size);
/* 240 */           this.mCurrentInputBuffer.position(nextFrame.size + offset);
/* 241 */           this.mCurrentFrameIndex++;
/* 243 */           if (this.mWaitingForFirstSyncFrame) {
/* 244 */               if ((nextFrame.flags & 1) == 0) {
/* 245 */                   this.mCurrentInputBuffer.clear();
/* 246 */                   return true;
                        }
/* 248 */               this.mWaitingForFirstSyncFrame = false;
                    }
/* 251 */           int bufferSize = this.mCurrentInputBuffer != null ? this.mCurrentInputBuffer.position() : 0;
/* 252 */           long presentationTimeUs = nextFrame.presentationTimeUs;
/* 253 */           this.mCodec.queueInputBuffer(this.mInputIndex, 0, bufferSize, presentationTimeUs, 0);
/* 254 */           this.mInputIndex = -1;
/* 255 */           if (this.mCurrentFrameIndex >= this.mFrameInfo.length) {
/* 256 */               this.mCurrentFrameIndex = 0;
/* 257 */               if (!hasAudioTrack) {
/* 258 */                   clock.setPosition(0L);
                        }
                    }
/* 261 */           return true;
                }

/* 264 */       private void onOutputFormatChanged(MediaFormat format) {
/* 265 */           boolean hasCrop = format.containsKey(KEY_CROP_RIGHT) && format.containsKey(KEY_CROP_LEFT) && format.containsKey(KEY_CROP_BOTTOM) && format.containsKey(KEY_CROP_TOP);
/* 267 */           this.mCurrentWidth = hasCrop ? (format.getInteger(KEY_CROP_RIGHT) - format.getInteger(KEY_CROP_LEFT)) + 1 : format.getInteger("width");
/* 270 */           this.mCurrentHeight = hasCrop ? (format.getInteger(KEY_CROP_BOTTOM) - format.getInteger(KEY_CROP_TOP)) + 1 : format.getInteger("height");
                }

/* 280 */       private boolean drainOutputBuffer(MediaClock clock, boolean hasAudioTrack) {
/* 281 */           if (this.mOutputIndex < 0) {
/* 282 */               this.mOutputIndex = this.mCodec.dequeueOutputBuffer(this.mOutputBufferInfo, 0L);
                    }
/* 285 */           if (this.mOutputIndex == -2) {
/* 286 */               onOutputFormatChanged(this.mCodec.getOutputFormat());
                        return true;
                    }
/* 288 */           if (this.mOutputIndex == -3) {
                        return true;
                    }
/* 290 */           if (this.mOutputIndex >= 0 && processOutputBuffer(clock, this.mCodec, this.mOutputBufferInfo, this.mOutputIndex)) {
/* 296 */               clock.setVideoPositionUs(this.mOutputBufferInfo.presentationTimeUs);
/* 297 */               if (!hasAudioTrack) {
/* 298 */                   clock.setPosition(this.mOutputBufferInfo.presentationTimeUs);
                        }
/* 300 */               this.mOutputIndex = -1;
                        return true;
                    }
                    return false;
                }

/* 307 */       public int getWidth() {
/* 308 */           return this.mCurrentWidth;
                }

/* 311 */       public int getHeight() {
/* 312 */           return this.mCurrentHeight;
                }

/* 315 */       public long getDurationMs() {
/* 316 */           if (this.mFrameInfo != null) {
/* 317 */               return this.mFrameInfo[this.mFrameInfo.length - 1].presentationTimeUs / 1000;
                    }
                    return -1L;
                }

/* 322 */       public long getFramesRendered() {
/* 323 */           return this.mRendereredFrames;
                }
            }
