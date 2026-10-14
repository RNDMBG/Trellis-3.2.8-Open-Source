            package co.vine.android.recorder.audio;

            import android.support.annotation.NonNull;
            
            
            public interface AudioReceiver {
                void beforeDataCollection();

                @NonNull
                AudioArrays.AudioArrayType getType();

                boolean needsMoreData();

                boolean onAudioDataReceived(int i, @NonNull AudioArray audioArray);

                void onAudioSourceStopped();
            }
