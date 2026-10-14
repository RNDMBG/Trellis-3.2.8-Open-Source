            package co.vine.android.embed.player;

            import android.util.Log;
            import co.vine.android.util.CommonUtil;
            import java.nio.ByteBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public class AudioRenderer {
                private static final int FIRST_BATCH_COUNT_TIME_US = 100000;
                private static final int MIN_BATCH_COUNT_TIME_US = 60000;
                private static final String TAG = "AudioRenderer";
                private int mAudioSessionId;
                private AudioTrack mAudioTrack;
                private int mBufferedSampleIndex;
                private int mCurrentBatchCount;
                private boolean mEndReached;
                private long mLastKnownIndex;
                private boolean mNewBatch;
                private long mOneIndexToUs;
                private double mOneUsToIndex;
                private VineSampleSource mSource;
                private float mGain = 1.0f;
                private boolean mUpdateTimestamp = true;

/* 33 */        public boolean doSomeWork(MediaClock clock, boolean justResumed) throws AudioTrack.InitializationException, AudioTrack.WriteException {
/* 34 */            if (this.mSource == null) {
/* 35 */                return false;
                    }
/* 38 */            ByteBuffer preparedSamples = this.mSource.getPreparedAudioSamples();
/* 40 */            ByteBuffer sampleBuffer = preparedSamples != null ? preparedSamples : this.mSource.getUnpreparedAudioSamples();
/* 42 */            if (sampleBuffer == null) {
/* 43 */                return false;
                    }
/* 46 */            int sampleSize = sampleBuffer.limit();
/* 48 */            if (this.mNewBatch) {
/* 49 */                if (clock.isFirstOperation()) {
/* 50 */                    this.mCurrentBatchCount = (int) (100000.0d * this.mOneUsToIndex);
                        } else {
/* 52 */                    this.mCurrentBatchCount = (int) (Math.max(CommonUtil.MINUTE_MILLIS, clock.timeSinceLastOperationStartUs() * 2) * this.mOneIndexToUs);
                        }
/* 55 */                if (this.mCurrentBatchCount <= 0) {
/* 56 */                    return false;
                        }
/* 59 */                this.mNewBatch = false;
/* 62 */                if (preparedSamples == null && sampleSize < this.mCurrentBatchCount) {
/* 63 */                    return false;
                        }
                    }
/* 67 */            boolean justReachedEnd = false;
/* 69 */            if (justResumed) {
/* 70 */                clock.setPosition(this.mLastKnownIndex * this.mOneIndexToUs);
                    }
/* 73 */            if (this.mUpdateTimestamp) {
/* 74 */                this.mUpdateTimestamp = false;
/* 75 */                clock.setPosition(((long) this.mBufferedSampleIndex) * this.mOneIndexToUs);
                    }
/* 79 */            int ret = processOutputBuffer(sampleBuffer, this.mBufferedSampleIndex, this.mCurrentBatchCount, clock.getAudioPresentationTimeUs());
                    if (ret != 0) {
/* 80 */                if (ret == 4) {
/* 81 */                    Log.w(TAG, "Staled data detected, there may not be enough data.");
                        }
/* 83 */                this.mCurrentBatchCount = this.mAudioTrack.getLastSize();
/* 84 */                clock.incrementAudioPresentationTimeUs((long) (this.mOneUsToIndex * ((double) this.mCurrentBatchCount)));
/* 85 */                if (this.mEndReached) {
/* 86 */                    this.mBufferedSampleIndex = 0;
/* 87 */                    this.mNewBatch = true;
/* 88 */                    this.mEndReached = false;
/* 89 */                    justReachedEnd = true;
                        } else {
/* 91 */                    this.mBufferedSampleIndex += this.mCurrentBatchCount;
/* 92 */                    if (this.mBufferedSampleIndex + this.mCurrentBatchCount > sampleSize) {
/* 93 */                        this.mEndReached = true;
/* 94 */                        this.mCurrentBatchCount = sampleSize - this.mBufferedSampleIndex;
                            } else {
/* 96 */                        this.mEndReached = false;
/* 97 */                        this.mNewBatch = true;
                            }
                        }
/* 100 */               this.mUpdateTimestamp = true;
/* 106 */               return justReachedEnd;
                    }
/* 102 */           this.mCurrentBatchCount = this.mAudioTrack.getLastSize();
/* 103 */           this.mLastKnownIndex = ((long) this.mBufferedSampleIndex) + (((long) this.mCurrentBatchCount) - this.mAudioTrack.getTemporaryBufferSize());
/* 67 */            return false;
                }

/* 109 */       public void release() {
/* 110 */           this.mSource = null;
/* 111 */           if (this.mAudioTrack != null) {
/* 112 */               this.mAudioTrack.reset();
/* 113 */               this.mAudioTrack = null;
                    }
/* 115 */           this.mAudioSessionId = 0;
                }

/* 121 */       private int processOutputBuffer(ByteBuffer buffer, int offset, int size, long presentationTimeUs) throws AudioTrack.InitializationException, AudioTrack.WriteException {
/* 122 */           if (!this.mAudioTrack.isInitialized()) {
/* 123 */               if (this.mAudioSessionId != 0) {
/* 124 */                   this.mAudioTrack.initialize(this.mAudioSessionId);
                        } else {
/* 126 */                   this.mAudioSessionId = this.mAudioTrack.initialize();
                        }
/* 129 */               this.mAudioTrack.play();
                    }
/* 132 */           int handleBufferResult = this.mAudioTrack.handleBuffer(buffer, offset, size, presentationTimeUs);
/* 135 */           if ((handleBufferResult & 1) != 0) {
/* 136 */               Log.w(TAG, "We are out of sync.");
                    }
/* 140 */           if ((handleBufferResult & 2) != 0) {
/* 141 */               return 2;
                    }
/* 144 */           if ((handleBufferResult & 4) != 0) {
/* 145 */               return 4;
                    }
/* 148 */           return 0;
                }

/* 151 */       public void prepare(VineSampleSource source) {
/* 152 */           AudioSampleTrack track = source.getAudioTrack();
/* 153 */           if (track != null) {
/* 154 */               this.mAudioTrack = new AudioTrack();
/* 155 */               this.mAudioTrack.reconfigure(track.format);
/* 156 */               this.mAudioTrack.setVolume(this.mGain);
/* 157 */               this.mOneUsToIndex = source.getAudioTrack().decodedTimeUsToIndex(1L, 2);
/* 158 */               this.mOneIndexToUs = source.getAudioTrack().decodedIndexToTimeUs(1, 2);
/* 159 */               this.mSource = source;
/* 160 */               this.mBufferedSampleIndex = 0;
/* 161 */               this.mAudioSessionId = 0;
/* 162 */               this.mNewBatch = true;
                    }
                }

/* 166 */       public void setVolume(float gain) {
/* 167 */           this.mGain = gain;
/* 168 */           if (this.mAudioTrack != null) {
/* 169 */               this.mAudioTrack.setVolume(gain);
                    }
                }

/* 173 */       public long getDurationMs() {
/* 174 */           if (this.mSource == null || this.mSource.getPreparedAudioSamples() == null) {
                        return -1L;
                    }
/* 176 */           return (((long) this.mSource.getPreparedAudioSamples().capacity()) * this.mOneIndexToUs) / 1000;
                }
            }
