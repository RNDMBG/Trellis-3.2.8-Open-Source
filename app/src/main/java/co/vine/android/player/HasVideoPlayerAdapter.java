            package co.vine.android.player;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface HasVideoPlayerAdapter {
                SdkVideoView getLastPlayer();

                SdkVideoView getPlayer(int i, boolean z);

                void getVideoPathAndPlayForPosition(int i, boolean z);

                void pauseCurrentPlayer();

                void pausePlayer(SdkVideoView sdkVideoView);

                void playCurrentPosition();

                void playFile(int i, boolean z);

                void resumePlayer(SdkVideoView sdkVideoView);
            }
