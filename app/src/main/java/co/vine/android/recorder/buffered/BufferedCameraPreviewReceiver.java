            package co.vine.android.recorder.buffered;

            import android.hardware.Camera;
            import co.vine.android.plugin.BaseRecorderPluginManager;
            import co.vine.android.recorder.RecordClock;
            import co.vine.android.recorder.RecordSegment;
            import co.vine.android.recorder.RecordState;
            import co.vine.android.recorder.video.VideoData;
            import co.vine.android.util.CrashUtil;
            import com.edisonwang.android.slog.SLog;
            import java.nio.ByteBuffer;
            import java.util.concurrent.ConcurrentLinkedQueue;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public class BufferedCameraPreviewReceiver implements Camera.PreviewCallback {
                private final RecordClock mClock;
                private boolean mCurrentSegmentEverReceivedFrames;
                private final ConcurrentLinkedQueue<VideoData> mDataQueue;
                private VideoData mLastFrame;
                private VideoData mLastLastFrame;
                private final BaseRecorderPluginManager mPluginManager;
                private final BufferedPreviewManagerCallback mPreviewManagerCallback;
                private final RecordState mState;
                private PreviewStatusValidator mValidator;
                private final int[] mVideoRecordLock = new int[0];

                public interface PreviewStatusValidator {
                    boolean validateOnFrameReceived(boolean z);
                }

/* 33 */        public void setValidator(PreviewStatusValidator previewStatusValidator) {
/* 34 */            this.mValidator = previewStatusValidator;
                }

/* 41 */        public BufferedCameraPreviewReceiver(RecordState state, RecordClock clock, ConcurrentLinkedQueue<VideoData> dataQueue, BaseRecorderPluginManager pluginManager, BufferedPreviewManagerCallback previewManagerCallback) {
/* 43 */            this.mState = state;
/* 44 */            this.mClock = clock;
/* 45 */            this.mDataQueue = dataQueue;
/* 46 */            this.mPluginManager = pluginManager;
/* 47 */            this.mPreviewManagerCallback = previewManagerCallback;
                }

                @Override // android.hardware.Camera.PreviewCallback
/* 51 */        public void onPreviewFrame(byte[] data, Camera camera) {
/* 52 */            this.mCurrentSegmentEverReceivedFrames = true;
/* 53 */            if (this.mValidator != null) {
                        if (!this.mValidator.validateOnFrameReceived(data == null)) {
/* 76 */                    return;
                        }
                    }
/* 56 */            long timestamp = this.mClock.onNewVideoFrame();
/* 57 */            this.mPreviewManagerCallback.removeBufferFromAvailableQueue(data);
                    synchronized (this.mVideoRecordLock) {
                        try {
/* 59 */                    RecordSegment segment = this.mState.getCurrentSegment();
/* 60 */                    if (segment != null) {
/* 61 */                        SLog.m447d("new video timestamp: {}", Long.valueOf(timestamp));
/* 62 */                        if (this.mLastFrame == null) {
/* 63 */                            this.mLastFrame = new VideoData(timestamp, data);
/* 64 */                            offerLastFrame(segment, null);
                                } else {
/* 66 */                            offerLastFrame(segment, new VideoData(timestamp, data));
                                }
                            } else {
/* 69 */                        if (this.mLastLastFrame != null) {
/* 70 */                            this.mPreviewManagerCallback.addCallbackBuffer(this.mLastLastFrame.data);
                                }
/* 72 */                        this.mLastLastFrame = this.mLastFrame;
/* 73 */                        this.mLastFrame = new VideoData(timestamp, data);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 78 */        public void offerLastFrame(RecordSegment segment, VideoData next) {
                    synchronized (this.mVideoRecordLock) {
                        try {
/* 80 */                    if (this.mLastFrame != null) {
/* 81 */                        this.mClock.onFrameIncrement();
                                if (this.mLastFrame.timestamp > this.mClock.getVideoTimestampUs()) {
/* 83 */                            this.mClock.setVideoTimestampUs(this.mLastFrame.timestamp);
                                }
/* 85 */                        this.mLastFrame.setSegment(segment);
/* 86 */                        segment.getVideoData().add(this.mLastFrame);
/* 87 */                        this.mDataQueue.offer(this.mLastFrame);
/* 89 */                        ByteBuffer bb = this.mPreviewManagerCallback.getSharedByteBuffer();
/* 90 */                        bb.rewind();
/* 91 */                        bb.put(this.mLastFrame.data);
/* 92 */                        this.mPluginManager.onOfferLastFrame(bb.array(), segment.getCameraSetting());
/* 93 */                        this.mState.setLastVideoSegment(segment);
/* 95 */                        SLog.m449d("Video timestamp {}, actual {}, segment: {}", Long.valueOf(this.mClock.getVideoTimestampUs()), Long.valueOf(this.mLastFrame.timestamp), segment);
/* 96 */                        this.mLastFrame = next;
/* 97 */                        if (next == null) {
/* 98 */                            CrashUtil.log("Flushed last frame to " + segment);
                                }
                            } else {
/* 101 */                       CrashUtil.log("Offer last frame failed, did we ever got a frame? " + this.mCurrentSegmentEverReceivedFrames + ", " + this + " - Callback state: " + BufferedPreviewManagerCallback.getCallbackStateString());
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 108 */       public void clearLastFrames() {
                    synchronized (this.mVideoRecordLock) {
                        try {
/* 110 */                   if (this.mLastFrame != null && !this.mPreviewManagerCallback.isFrameAdded(this.mLastFrame.data)) {
/* 111 */                       SLog.m446d("Cleared Last frame.");
/* 112 */                       this.mPreviewManagerCallback.addBuffer(this.mLastFrame.data, false);
/* 113 */                       this.mLastFrame = null;
                            }
/* 115 */                   if (this.mLastLastFrame != null && !this.mPreviewManagerCallback.isFrameAdded(this.mLastLastFrame.data)) {
/* 116 */                       SLog.m446d("Cleared Last Last frame.");
/* 117 */                       this.mPreviewManagerCallback.addBuffer(this.mLastLastFrame.data, false);
/* 118 */                       this.mLastLastFrame = null;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 123 */       public void updateVideoTimestampUs(long timestamp) {
                    synchronized (this.mVideoRecordLock) {
                        try {
/* 125 */                   clearLastFrames();
/* 126 */                   this.mClock.setVideoTimestampUs(timestamp);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 130 */       public void onSessionSwapped() {
/* 131 */           this.mLastLastFrame = null;
/* 132 */           this.mLastFrame = null;
                }

/* 135 */       public void updateLastFrameTimestampUs() {
/* 136 */           if (this.mLastFrame != null) {
/* 137 */               this.mClock.onFrameIncrement();
/* 138 */               this.mLastFrame.timestamp = this.mClock.getVideoTimestampUs();
                    }
                }

/* 142 */       public void onNewSegmentStart() {
/* 143 */           this.mCurrentSegmentEverReceivedFrames = false;
                }
            }
