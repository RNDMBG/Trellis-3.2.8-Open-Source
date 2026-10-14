            package co.vine.android.recorder.audio;

            import android.media.AudioRecord;
            import android.os.Process;
            import android.support.annotation.NonNull;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.service.ResourceService;
            import co.vine.android.util.CrashUtil;
            import com.edisonwang.android.slog.SLog;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 15 */    public class AudioRecordSource implements AudioSource {
                private final AudioArray mAudioDataBuffer;
                private AudioRecord mAudioRecord;
                private Thread mAudioThread;
                private final String mCurrentTarget;
                private volatile boolean mIsInitialized;
                private boolean mIsStopped;

/* 16 */        static /* synthetic */ AudioArray access$000(AudioRecordSource x0) {
/* 17 */            return x0.mAudioDataBuffer;
                }

/* 16 */        static /* synthetic */ boolean access$102(AudioRecordSource x0, boolean x1) {
/* 17 */            x0.mIsInitialized = x1;
                    return x1;
                }

/* 16 */        static /* synthetic */ AudioRecord access$200(AudioRecordSource x0) {
/* 17 */            return x0.mAudioRecord;
                }

/* 16 */        static /* synthetic */ String access$300(AudioRecordSource x0) {
/* 17 */            return x0.mCurrentTarget;
                }

/* 16 */        static /* synthetic */ boolean access$400(AudioRecordSource x0) {
/* 17 */            return x0.mIsStopped;
                }

/* 28 */        public AudioRecordSource(String targetTag, @NonNull AudioArray buffer) {
/* 30 */            this.mCurrentTarget = targetTag;
/* 31 */            this.mAudioDataBuffer = buffer;
                }

/* 36 */        public class AudioGatherer implements Runnable {
                    private final AudioReceiver mReceiver;

/* 37 */            public AudioGatherer(AudioReceiver receiver) {
/* 39 */                this.mReceiver = receiver;
                    }

                    @Override // java.lang.Runnable
/* 43 */            public void run() {
/* 44 */                Process.setThreadPriority(-19);
                        try {
                            AudioRecordSource.this.mAudioRecord = new AudioRecord(1, 44100, 16, 2, RecordConfigUtils.AUDIO_RUNNABLE_SAMPLE_SIZE);
                        } catch (Throwable e) {
                            SLog.m459e("Unable to initialize AudioRecord; audio capture is disabled for this session.", e);
                            ResourceService.releaseAudioBuffer(AudioArrays.typeOf(AudioRecordSource.access$000(AudioRecordSource.this)));
                            this.mReceiver.onAudioSourceStopped();
                            return;
                        }
/* 45 */                SLog.m446d("mAudioRecord.startRecording()");
/* 46 */                SLog.m447d("Buffer size {}.", Integer.valueOf(AudioRecordSource.access$000(AudioRecordSource.this).getLength()));
/* 47 */                AudioRecordSource.access$102(AudioRecordSource.this, false);
/* 48 */                long initializationDeadline = System.currentTimeMillis() + 2000L;
/* 48 */                while (AudioRecordSource.access$200(AudioRecordSource.this).getState() == AudioRecord.STATE_UNINITIALIZED
                            && !AudioRecordSource.access$400(AudioRecordSource.this)
                            && System.currentTimeMillis() < initializationDeadline) {
                             try {
/* 50 */                        Thread.sleep(100L);
                             } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                break;
                             }
                         }
/* 54 */                if (AudioRecordSource.access$400(AudioRecordSource.this)
                            || AudioRecordSource.access$200(AudioRecordSource.this).getState() != AudioRecord.STATE_INITIALIZED) {
                            SLog.m455e("AudioRecord could not be initialized; ending audio capture safely.");
                            ResourceService.releaseAudioBuffer(AudioArrays.typeOf(AudioRecordSource.access$000(AudioRecordSource.this)));
                            AudioRecordSource.access$200(AudioRecordSource.this).release();
                            AudioRecordSource.access$102(AudioRecordSource.this, false);
                            this.mReceiver.onAudioSourceStopped();
                            return;
                        }
/* 55 */                AudioRecordSource.access$102(AudioRecordSource.this, true);
/* 56 */                CrashUtil.log("mAudioRecord.isInitialized: {}.", AudioRecordSource.access$300(AudioRecordSource.this));
/* 57 */                AudioRecordSource.access$200(AudioRecordSource.this).startRecording();
/* 61 */                while (this.mReceiver.needsMoreData() && !AudioRecordSource.access$400(AudioRecordSource.this)) {
/* 65 */                    this.mReceiver.beforeDataCollection();
/* 66 */                    int bufferReadResult = AudioRecordSource.access$000(AudioRecordSource.this).readFrom(AudioRecordSource.access$200(AudioRecordSource.this), AudioRecordSource.access$000(AudioRecordSource.this).getLength());
/* 67 */                    if (bufferReadResult == -3) {
/* 68 */                        SLog.m446d("Audio Record in invalid state: (valid = 3) " + AudioRecordSource.access$200(AudioRecordSource.this).getState());
                                break;
                            } else if (bufferReadResult > 0 && !this.mReceiver.onAudioDataReceived(bufferReadResult, AudioRecordSource.access$000(AudioRecordSource.this))) {
                                break;
                            }
                        }
/* 77 */                ResourceService.releaseAudioBuffer(AudioArrays.typeOf(AudioRecordSource.access$000(AudioRecordSource.this)));
/* 78 */                SLog.m446d("AudioThread Finished, release mAudioRecord");
/* 79 */                AudioRecordSource.access$200(AudioRecordSource.this).stop();
/* 80 */                AudioRecordSource.access$200(AudioRecordSource.this).release();
/* 81 */                AudioRecordSource.access$102(AudioRecordSource.this, false);
/* 82 */                CrashUtil.log("mAudioRecord released: {}.", AudioRecordSource.access$300(AudioRecordSource.this));
/* 83 */                this.mReceiver.onAudioSourceStopped();
                    }
                }

                @Override // co.vine.android.recorder.audio.AudioSource
/* 88 */        public void start(AudioReceiver receiver) {
/* 89 */            this.mIsStopped = false;
/* 90 */            this.mAudioThread = new Thread(new AudioGatherer(receiver), "AudioRunnable");
/* 91 */            this.mAudioThread.start();
                }

                @Override // co.vine.android.recorder.audio.AudioSource
/* 94 */        public void stop() {
/* 95 */            CrashUtil.log("Stop audio record source: {}", Boolean.valueOf(this.mIsInitialized));
/* 96 */            this.mIsStopped = true;
                        try {
                            if (this.mAudioRecord != null && this.mAudioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
                                this.mAudioRecord.stop();
                            }
                        } catch (IllegalStateException ignored) {
                            // The audio device may already be uninitialized or released.
                        }
/* 97 */            if (this.mAudioThread != null) {
                        try {
/* 99 */                    this.mAudioThread.join();
                        } catch (InterruptedException e) {
/* 101 */                   SLog.m455e("Error join audio thread.");
                        }
                    }
                }

                @Override // co.vine.android.recorder.audio.AudioSource
/* 107 */       public boolean isInitialized() {
/* 108 */           return this.mIsInitialized;
                }
            }
