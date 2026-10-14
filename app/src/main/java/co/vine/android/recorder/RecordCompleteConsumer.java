            package co.vine.android.recorder;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface RecordCompleteConsumer extends Runnable {
                boolean isComplete();

                void onComplete(RecordingFile recordingFile, RecordCompleteConsumer recordCompleteConsumer);

                void onError(RecordingFile recordingFile, Exception exc, RecordCompleteConsumer recordCompleteConsumer);

                void reset();

                void resumeAll();

                @Override // java.lang.Runnable
                void run();
            }
