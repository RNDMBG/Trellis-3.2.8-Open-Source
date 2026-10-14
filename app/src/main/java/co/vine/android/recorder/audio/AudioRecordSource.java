            package co.vine.android.recorder.audio;

            import android.media.AudioRecord;
            import android.os.Process;
            import android.support.annotation.NonNull;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.service.ResourceService;
            import co.vine.android.util.CrashUtil;
            import com.edisonwang.android.slog.SLog;
            
            
    public class AudioRecordSource implements AudioSource {
                private final AudioArray mAudioDataBuffer;
                private AudioRecord mAudioRecord;
                private Thread mAudioThread;
                private final String mCurrentTarget;
                private volatile boolean mIsInitialized;
                private boolean mIsStopped;

        static  AudioArray access$000(AudioRecordSource x0) {
            return x0.mAudioDataBuffer;
                }

        static  boolean access$102(AudioRecordSource x0, boolean x1) {
            x0.mIsInitialized = x1;
                    return x1;
                }

        static  AudioRecord access$200(AudioRecordSource x0) {
            return x0.mAudioRecord;
                }

        static  String access$300(AudioRecordSource x0) {
            return x0.mCurrentTarget;
                }

        static  boolean access$400(AudioRecordSource x0) {
            return x0.mIsStopped;
                }

        public AudioRecordSource(String targetTag, @NonNull AudioArray buffer) {
            this.mCurrentTarget = targetTag;
            this.mAudioDataBuffer = buffer;
                }

        public class AudioGatherer implements Runnable {
                    private final AudioReceiver mReceiver;

            public AudioGatherer(AudioReceiver receiver) {
                this.mReceiver = receiver;
                    }

                    @Override 
            public void run() {
                Process.setThreadPriority(-19);
                        try {
                            AudioRecordSource.this.mAudioRecord = new AudioRecord(1, 44100, 16, 2, RecordConfigUtils.AUDIO_RUNNABLE_SAMPLE_SIZE);
                        } catch (Throwable e) {
                            SLog.m459e("Unable to initialize AudioRecord; audio capture is disabled for this session.", e);
                            ResourceService.releaseAudioBuffer(AudioArrays.typeOf(AudioRecordSource.access$000(AudioRecordSource.this)));
                            this.mReceiver.onAudioSourceStopped();
                            return;
                        }
                SLog.m446d("mAudioRecord.startRecording()");
                SLog.m447d("Buffer size {}.", Integer.valueOf(AudioRecordSource.access$000(AudioRecordSource.this).getLength()));
                AudioRecordSource.access$102(AudioRecordSource.this, false);
                long initializationDeadline = System.currentTimeMillis() + 2000L;
                while (AudioRecordSource.access$200(AudioRecordSource.this).getState() == AudioRecord.STATE_UNINITIALIZED
                            && !AudioRecordSource.access$400(AudioRecordSource.this)
                            && System.currentTimeMillis() < initializationDeadline) {
                             try {
                        Thread.sleep(100L);
                             } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                break;
                             }
                         }
                if (AudioRecordSource.access$400(AudioRecordSource.this)
                            || AudioRecordSource.access$200(AudioRecordSource.this).getState() != AudioRecord.STATE_INITIALIZED) {
                            SLog.m455e("AudioRecord could not be initialized; ending audio capture safely.");
                            ResourceService.releaseAudioBuffer(AudioArrays.typeOf(AudioRecordSource.access$000(AudioRecordSource.this)));
                            AudioRecordSource.access$200(AudioRecordSource.this).release();
                            AudioRecordSource.access$102(AudioRecordSource.this, false);
                            this.mReceiver.onAudioSourceStopped();
                            return;
                        }
                AudioRecordSource.access$102(AudioRecordSource.this, true);
                CrashUtil.log("mAudioRecord.isInitialized: {}.", AudioRecordSource.access$300(AudioRecordSource.this));
                AudioRecordSource.access$200(AudioRecordSource.this).startRecording();
                while (this.mReceiver.needsMoreData() && !AudioRecordSource.access$400(AudioRecordSource.this)) {
                    this.mReceiver.beforeDataCollection();
                    int bufferReadResult = AudioRecordSource.access$000(AudioRecordSource.this).readFrom(AudioRecordSource.access$200(AudioRecordSource.this), AudioRecordSource.access$000(AudioRecordSource.this).getLength());
                    if (bufferReadResult == -3) {
                        SLog.m446d("Audio Record in invalid state: (valid = 3) " + AudioRecordSource.access$200(AudioRecordSource.this).getState());
                                break;
                            } else if (bufferReadResult > 0 && !this.mReceiver.onAudioDataReceived(bufferReadResult, AudioRecordSource.access$000(AudioRecordSource.this))) {
                                break;
                            }
                        }
                ResourceService.releaseAudioBuffer(AudioArrays.typeOf(AudioRecordSource.access$000(AudioRecordSource.this)));
                SLog.m446d("AudioThread Finished, release mAudioRecord");
                AudioRecordSource.access$200(AudioRecordSource.this).stop();
                AudioRecordSource.access$200(AudioRecordSource.this).release();
                AudioRecordSource.access$102(AudioRecordSource.this, false);
                CrashUtil.log("mAudioRecord released: {}.", AudioRecordSource.access$300(AudioRecordSource.this));
                this.mReceiver.onAudioSourceStopped();
                    }
                }

                @Override 
        public void start(AudioReceiver receiver) {
            this.mIsStopped = false;
            this.mAudioThread = new Thread(new AudioGatherer(receiver), "AudioRunnable");
            this.mAudioThread.start();
                }

                @Override 
        public void stop() {
            CrashUtil.log("Stop audio record source: {}", Boolean.valueOf(this.mIsInitialized));
            this.mIsStopped = true;
                        try {
                            if (this.mAudioRecord != null && this.mAudioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
                                this.mAudioRecord.stop();
                            }
                        } catch (IllegalStateException ignored) {
                            
                        }
            if (this.mAudioThread != null) {
                        try {
                    this.mAudioThread.join();
                        } catch (InterruptedException e) {
                   SLog.m455e("Error join audio thread.");
                        }
                    }
                }

                @Override 
       public boolean isInitialized() {
           return this.mIsInitialized;
                }
            }
