            package co.vine.android.recorder;

            import android.support.annotation.NonNull;
            import co.vine.android.recorder.audio.AudioArray;
            import co.vine.android.recorder.audio.AudioArrays;
            import co.vine.android.recorder.audio.AudioData;
            import co.vine.android.recorder.audio.AudioReceiver;
            import co.vine.android.recorder.audio.AudioStateListener;
            import co.vine.android.util.CrashUtil;
            import com.edisonwang.android.slog.SLog;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 35 */    public class AudioDataReceiver implements AudioReceiver, AudioStateListener {
                private AudioArray<?, ?> mAudioDataBufferMax;
                private final RecordClock mClock;
                private int mCount;
                private int mInitialDurationUs;
                private final RecordState mState;
                private final AudioArrays.AudioArrayType mType;
                private AudioLengthValidator mValidator;
                private final double AUDIO_SAMPLE_PER_US_EXACT = 0.0441d;
                private boolean mAudioTrim = false;

                public interface AudioLengthValidator {
                    boolean isAudioLengthResonable(long j);
                }

/* 36 */        public AudioDataReceiver(RecordClock clock, RecordState state, AudioArray<?, ?> audioDataMax, int initialCount, int durationMs, AudioArrays.AudioArrayType type) {
/* 38 */            this.mCount = initialCount;
/* 39 */            this.mState = state;
/* 40 */            this.mType = type;
/* 41 */            this.mAudioDataBufferMax = audioDataMax;
/* 42 */            this.mInitialDurationUs = (durationMs * 1000) - ((int) (((double) this.mCount) / 0.0441d));
/* 43 */            this.mClock = clock;
                }

                @Override // co.vine.android.recorder.audio.AudioStateListener
/* 47 */        public void onSessionTimestampChanged(RecordSession session) {
/* 48 */            this.mCount = session.calculateMemoryBackedAudioCount();
/* 49 */            this.mInitialDurationUs = (session.getDurationMs() * 1000) - ((int) (((double) this.mCount) / 0.0441d));
                }

                @Override // co.vine.android.recorder.audio.AudioStateListener
/* 53 */        public void onExternalClipAdded(int microseconds) {
/* 54 */            this.mInitialDurationUs += microseconds;
                }

                @Override // co.vine.android.recorder.audio.AudioReceiver
/* 58 */        public boolean needsMoreData() {
                    return (this.mState.collectMoreAudio() || this.mClock.isVideoAhead()) && !this.mState.isEnded();
                }

                @Override // co.vine.android.recorder.audio.AudioReceiver
/* 63 */        public void beforeDataCollection() {
/* 64 */            this.mClock.updateAudioTimestamp(false, getCurrentTimestampUs());
                }

                @Override // co.vine.android.recorder.audio.AudioReceiver
/* 68 */        public boolean onAudioDataReceived(int bufferReadResult, @NonNull AudioArray audioDataBuffer) {
/* 69 */            int shouldPut = !this.mAudioTrim ? bufferReadResult : (int) Math.min(((this.mClock.getVideoTimestampUs() - ((long) this.mClock.getAudioTimestampUs())) * 0.0441d) + 1.0d, bufferReadResult);
/* 71 */            if (shouldPut > 0) {
/* 72 */                RecordSegment segment = this.mState.getCurrentSegment();
/* 73 */                if (segment != null && this.mState.recordingAudio()) {
/* 74 */                    record(segment, audioDataBuffer, shouldPut);
/* 75 */                    this.mState.setLastAudioRecordingSegment(segment);
                        } else {
/* 77 */                    RecordSegment segment2 = this.mState.getLastAudioRecordingSegment();
/* 78 */                    if (segment2 == null) {
/* 79 */                        segment2 = this.mState.getLastVideoSegment();
                            }
/* 81 */                    if (this.mClock.isVideoAhead()) {
/* 82 */                        if (segment2 == null) {
/* 83 */                            SLog.m457e("Recording have not started yet? {} vs {}.", Long.valueOf(this.mClock.getVideoTimestampUs()), Integer.valueOf(this.mClock.getAudioTimestampUs()));
                                } else {
/* 85 */                            record(segment2, audioDataBuffer, shouldPut);
                                }
                            }
                        }
                    }
/* 90 */            return this.mValidator.isAudioLengthResonable(this.mClock.getAudioTimestampUs());
                }

                @Override // co.vine.android.recorder.audio.AudioReceiver
/* 94 */        public void onAudioSourceStopped() {
/* 95 */            CrashUtil.log("mAudioRecord released on conditions: {} {}", Boolean.valueOf(this.mState.collectMoreAudio()), Boolean.valueOf(this.mState.isEnded()));
                }

                @Override // co.vine.android.recorder.audio.AudioStateListener
/* 99 */        public void setAudioTrim(boolean enabled) {
/* 100 */           this.mAudioTrim = enabled;
                }

                @Override // co.vine.android.recorder.audio.AudioReceiver
/* 104 */       public AudioArrays.AudioArrayType getType() {
/* 105 */           return this.mType;
                }

                @Override // co.vine.android.recorder.audio.AudioStateListener
/* 109 */       public int getCurrentTimestampUs() {
/* 110 */           return this.mInitialDurationUs + ((int) (((double) this.mCount) / 0.0441d));
                }

/* 113 */       public void setValidator(AudioLengthValidator audioLengthValidator) {
/* 114 */           this.mValidator = audioLengthValidator;
                }

/* 117 */       protected void record(RecordSegment segment, AudioArray buffer, int limit) {
/* 118 */           buffer.putInto(this.mAudioDataBufferMax, this.mCount, limit);
/* 119 */           segment.addAudioData(new AudioData(this.mCount, limit));
/* 120 */           this.mCount += limit;
                }
            }
