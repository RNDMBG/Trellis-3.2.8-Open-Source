            package co.vine.android.recorder.audio;
            
            
            public interface AudioSource {
                boolean isInitialized();

                void start(AudioReceiver audioReceiver);

                void stop();
            }
