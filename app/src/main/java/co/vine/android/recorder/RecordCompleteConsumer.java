            package co.vine.android.recorder;
            
            
            public interface RecordCompleteConsumer extends Runnable {
                boolean isComplete();

                void onComplete(RecordingFile recordingFile, RecordCompleteConsumer recordCompleteConsumer);

                void onError(RecordingFile recordingFile, Exception exc, RecordCompleteConsumer recordCompleteConsumer);

                void reset();

                void resumeAll();

                @Override 
                void run();
            }
