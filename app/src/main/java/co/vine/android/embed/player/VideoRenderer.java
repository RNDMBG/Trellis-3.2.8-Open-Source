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
            
            
    public class VideoRenderer {
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

        public void release() {
            releaseCodec();
            this.mCurrentInputBuffer = null;
            this.mInputBuffers = null;
            this.mFrameInfo = null;
            this.mFrameData = null;
                }

        public void doSomeWork(MediaClock clock, boolean hasAudioTrack, boolean justResumed) throws IOException {
            long positionUs = clock.positionUs();
            if (this.mCurrentFrameIndex >= this.mFrameInfo.length || this.mLastPositionUs > positionUs) {
                this.mCurrentFrameIndex = 0;
                if (!hasAudioTrack) {
                    clock.setPosition(0L);
                        }
                flushCodec();
                    }
            while (drainOutputBuffer(clock, hasAudioTrack)) {
                    }
            if (feedInputBuffer(clock, hasAudioTrack)) {
                while (feedInputBuffer(clock, hasAudioTrack)) {
                        }
                    }
            this.mLastPositionUs = positionUs;
                }

        public void prepare(VineSampleSource source, Surface surface) throws IOException {
            long start = System.currentTimeMillis();
            this.mFrameInfo = source.getVideoFrameInfo();
            this.mFrameData = source.getVideoFrames();
            this.mFormat = source.getVideoTrackFormat();
            if (this.mSurface != surface) {
                this.mInputIndex = -1;
                this.mOutputIndex = -1;
                this.mSurface = surface;
                releaseCodec();
                initCodec();
                Log.i(TAG, "Video prepare took " + (System.currentTimeMillis() - start));
                    }
                }

       private void initCodec() throws IOException {
           String selectedDecoderName = MediaCodecUtil.getDecoderInfo(this.mFormat.getString("mime"));
           this.mCodec = MediaCodec.createByCodecName(selectedDecoderName);
           this.mCodec.configure(this.mFormat, this.mSurface, (MediaCrypto) null, 0);
           this.mCodec.setVideoScalingMode(1);
           this.mCodec.start();
           this.mInputBuffers = this.mCodec.getInputBuffers();
           this.mInputIndex = -1;
           this.mOutputIndex = -1;
           this.mWaitingForFirstSyncFrame = true;
                }

       private void releaseCodec() {
           if (this.mCodec != null) {
               this.mInputIndex = -1;
               this.mOutputIndex = -1;
               this.mInputBuffers = null;
                        try {
                   this.mCodec.stop();
                            try {
                       this.mCodec.release();
                       this.mCodec = null;
                            } catch (Throwable th) {
                                this.mCodec = null;
                                throw th;
                            }
                        } catch (Throwable th2) {
                            try {
                       this.mCodec.release();
                       this.mCodec = null;
                                throw th2;
                            } catch (Throwable th3) {
                                this.mCodec = null;
                                throw th3;
                            }
                        }
                    }
                }

       private boolean processOutputBuffer(MediaClock clock, MediaCodec codec, MediaCodec.BufferInfo bufferInfo, int bufferIndex) {
                    if (clock.getPositionSetTimeMs() == 0) {
               clock.setPosition(0L);
                    }
           long earlyUs = (bufferInfo.presentationTimeUs - clock.positionUs()) - clock.timeSincePositionSetUs();
           long renderingGapUs = (System.currentTimeMillis() - this.mLastRenderMs) * 1000;
           long renderingDiff = renderingGapUs + earlyUs;
                    if (earlyUs < -30000 && earlyUs > -70000) {
               Log.e(TAG, "Delayed Frame used " + earlyUs + " diff: " + renderingDiff);
               renderOutputBufferImmediate(codec, bufferIndex);
               return true;
                    }
           if (!this.mRenderedFirstFrame) {
               renderOutputBufferImmediate(codec, bufferIndex);
               this.mRenderedFirstFrame = true;
               return true;
                    }
                    if (earlyUs < -70000) {
               dropOutputBuffer(codec, bufferIndex);
               Log.e(TAG, "Delayed Frame dropped " + earlyUs + " diff: " + renderingDiff);
               return true;
                    }
                    if (earlyUs < 30000) {
                        if (earlyUs > 11000) {
                            try {
                       Thread.sleep((earlyUs - 10000) / 1000);
                            } catch (InterruptedException e) {
                       Thread.currentThread().interrupt();
                            }
                        }
               renderOutputBufferImmediate(codec, bufferIndex);
               return true;
                    }
           return false;
                }

       private void dropOutputBuffer(MediaCodec codec, int bufferIndex) {
           codec.releaseOutputBuffer(bufferIndex, false);
                }

       private void renderOutputBufferImmediate(MediaCodec codec, int bufferIndex) {
           onFrameRendered();
           codec.releaseOutputBuffer(bufferIndex, true);
                }

       private void onFrameRendered() {
                    if (this.mLastRenderMs > 0) {
               long timeSinceLastDrain = System.currentTimeMillis() - this.mLastRenderMs;
                        if (timeSinceLastDrain > 50) {
                   Log.e(TAG, "!Delayed! Time since last frame was rendered " + timeSinceLastDrain);
                        }
                    }
           this.mLastRenderMs = System.currentTimeMillis();
           this.mRendereredFrames++;
                }

       private void flushCodec() throws IOException {
           long start = System.currentTimeMillis();
           this.mInputIndex = -1;
           this.mOutputIndex = -1;
           this.mWaitingForFirstSyncFrame = true;
           if (Build.VERSION.SDK_INT >= 18) {
               this.mCodec.flush();
                    } else {
               releaseCodec();
               initCodec();
                    }
           Log.i(TAG, "Flush took " + (System.currentTimeMillis() - start));
                }

       private boolean feedInputBuffer(MediaClock clock, boolean hasAudioTrack) throws IOException {
           if (this.mInputIndex < 0) {
               this.mInputIndex = this.mCodec.dequeueInputBuffer(0L);
               if (this.mInputIndex < 0) {
                   return false;
                        }
               this.mCurrentInputBuffer = this.mInputBuffers[this.mInputIndex];
               this.mCurrentInputBuffer.clear();
                    }
           MediaCodec.BufferInfo nextFrame = this.mFrameInfo[this.mCurrentFrameIndex];
           int offset = this.mCurrentInputBuffer.position();
           this.mCurrentInputBuffer.put(this.mFrameData, nextFrame.offset, nextFrame.size);
           this.mCurrentInputBuffer.position(nextFrame.size + offset);
           this.mCurrentFrameIndex++;
           if (this.mWaitingForFirstSyncFrame) {
               if ((nextFrame.flags & 1) == 0) {
                   this.mCurrentInputBuffer.clear();
                   return true;
                        }
               this.mWaitingForFirstSyncFrame = false;
                    }
           int bufferSize = this.mCurrentInputBuffer != null ? this.mCurrentInputBuffer.position() : 0;
           long presentationTimeUs = nextFrame.presentationTimeUs;
           this.mCodec.queueInputBuffer(this.mInputIndex, 0, bufferSize, presentationTimeUs, 0);
           this.mInputIndex = -1;
           if (this.mCurrentFrameIndex >= this.mFrameInfo.length) {
               this.mCurrentFrameIndex = 0;
               if (!hasAudioTrack) {
                   clock.setPosition(0L);
                        }
                    }
           return true;
                }

       private void onOutputFormatChanged(MediaFormat format) {
           boolean hasCrop = format.containsKey(KEY_CROP_RIGHT) && format.containsKey(KEY_CROP_LEFT) && format.containsKey(KEY_CROP_BOTTOM) && format.containsKey(KEY_CROP_TOP);
           this.mCurrentWidth = hasCrop ? (format.getInteger(KEY_CROP_RIGHT) - format.getInteger(KEY_CROP_LEFT)) + 1 : format.getInteger("width");
           this.mCurrentHeight = hasCrop ? (format.getInteger(KEY_CROP_BOTTOM) - format.getInteger(KEY_CROP_TOP)) + 1 : format.getInteger("height");
                }

       private boolean drainOutputBuffer(MediaClock clock, boolean hasAudioTrack) {
           if (this.mOutputIndex < 0) {
               this.mOutputIndex = this.mCodec.dequeueOutputBuffer(this.mOutputBufferInfo, 0L);
                    }
           if (this.mOutputIndex == -2) {
               onOutputFormatChanged(this.mCodec.getOutputFormat());
                        return true;
                    }
           if (this.mOutputIndex == -3) {
                        return true;
                    }
           if (this.mOutputIndex >= 0 && processOutputBuffer(clock, this.mCodec, this.mOutputBufferInfo, this.mOutputIndex)) {
               clock.setVideoPositionUs(this.mOutputBufferInfo.presentationTimeUs);
               if (!hasAudioTrack) {
                   clock.setPosition(this.mOutputBufferInfo.presentationTimeUs);
                        }
               this.mOutputIndex = -1;
                        return true;
                    }
                    return false;
                }

       public int getWidth() {
           return this.mCurrentWidth;
                }

       public int getHeight() {
           return this.mCurrentHeight;
                }

       public long getDurationMs() {
           if (this.mFrameInfo != null) {
               return this.mFrameInfo[this.mFrameInfo.length - 1].presentationTimeUs / 1000;
                    }
                    return -1L;
                }

       public long getFramesRendered() {
           return this.mRendereredFrames;
                }
            }
