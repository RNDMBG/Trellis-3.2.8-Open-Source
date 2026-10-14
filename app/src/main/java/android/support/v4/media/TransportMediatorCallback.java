            package android.support.v4.media;

            import android.view.KeyEvent;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            interface TransportMediatorCallback {
                long getPlaybackPosition();

                void handleAudioFocusChange(int i);

                void handleKey(KeyEvent keyEvent);

                void playbackPositionUpdate(long j);
            }
