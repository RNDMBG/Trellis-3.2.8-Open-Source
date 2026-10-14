            package co.vine.android.recorder.audio;

            import co.vine.android.recorder.RecordSession;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface AudioStateListener {
                int getCurrentTimestampUs();

                void onExternalClipAdded(int i);

                void onSessionTimestampChanged(RecordSession recordSession);

                void setAudioTrim(boolean z);
            }
