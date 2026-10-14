            package android.support.v4.media;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public abstract class TransportController {
                public abstract int getBufferPercentage();

                public abstract long getCurrentPosition();

                public abstract long getDuration();

                public abstract int getTransportControlFlags();

                public abstract boolean isPlaying();

                public abstract void pausePlaying();

                public abstract void registerStateListener(TransportStateListener transportStateListener);

                public abstract void seekTo(long j);

                public abstract void startPlaying();

                public abstract void stopPlaying();

                public abstract void unregisterStateListener(TransportStateListener transportStateListener);
            }
