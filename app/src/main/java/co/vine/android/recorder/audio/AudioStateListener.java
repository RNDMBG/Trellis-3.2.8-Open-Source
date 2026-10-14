            package co.vine.android.recorder.audio;

            import co.vine.android.recorder.RecordSession;
            
            
            public interface AudioStateListener {
                int getCurrentTimestampUs();

                void onExternalClipAdded(int i);

                void onSessionTimestampChanged(RecordSession recordSession);

                void setAudioTrim(boolean z);
            }
