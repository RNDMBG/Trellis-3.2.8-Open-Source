            package co.vine.android.recorder.audio;

            import android.support.annotation.NonNull;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface AudioReceiver {
                void beforeDataCollection();

                @NonNull
                AudioArrays.AudioArrayType getType();

                boolean needsMoreData();

                boolean onAudioDataReceived(int i, @NonNull AudioArray audioArray);

                void onAudioSourceStopped();
            }
