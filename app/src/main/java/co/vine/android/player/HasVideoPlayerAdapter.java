            package co.vine.android.player;
            
            
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
