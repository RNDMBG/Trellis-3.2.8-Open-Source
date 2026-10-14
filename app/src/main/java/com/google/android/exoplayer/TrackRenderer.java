            package com.google.android.exoplayer;

            import com.google.android.exoplayer.util.Assertions;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 93 */    public abstract class TrackRenderer implements ExoPlayer.ExoPlayerComponent {
                public static final long END_OF_TRACK_US = -3;
                public static final long MATCH_LONGEST_US = -2;
                protected static final int STATE_ENABLED = 2;
                protected static final int STATE_IGNORE = -1;
                protected static final int STATE_PREPARED = 1;
                protected static final int STATE_RELEASED = -2;
                protected static final int STATE_STARTED = 3;
                protected static final int STATE_UNPREPARED = 0;
                public static final long UNKNOWN_TIME_US = -1;
                private int state;

                protected abstract int doPrepare() throws ExoPlaybackException;

                protected abstract void doSomeWork(long j) throws ExoPlaybackException;

                protected abstract long getBufferedPositionUs();

                protected abstract long getCurrentPositionUs();

                protected abstract long getDurationUs();

                protected abstract boolean isEnded();

                protected abstract boolean isReady();

                protected abstract void seekTo(long j) throws ExoPlaybackException;

/* 94 */        protected boolean isTimeSource() {
/* 95 */            return false;
                }

/* 103 */       protected final int getState() {
/* 104 */           return this.state;
                }

/* 113 */       final int prepare() throws ExoPlaybackException {
/* 114 */           Assertions.checkState(this.state == 0);
/* 115 */           this.state = doPrepare();
/* 116 */           Assertions.checkState(this.state == 0 || this.state == 1 || this.state == -1);
/* 119 */           return this.state;
                }

/* 144 */       final void enable(long timeUs, boolean joining) throws ExoPlaybackException {
/* 145 */           Assertions.checkState(this.state == 1);
/* 146 */           this.state = 2;
/* 147 */           onEnabled(timeUs, joining);
                }

/* 162 */       protected void onEnabled(long timeUs, boolean joining) throws ExoPlaybackException {
                }

/* 169 */       final void start() throws ExoPlaybackException {
/* 170 */           Assertions.checkState(this.state == 2);
/* 171 */           this.state = 3;
/* 172 */           onStarted();
                }

/* 183 */       protected void onStarted() throws ExoPlaybackException {
                }

/* 189 */       final void stop() throws ExoPlaybackException {
/* 190 */           Assertions.checkState(this.state == 3);
/* 191 */           this.state = 2;
/* 192 */           onStopped();
                }

/* 203 */       protected void onStopped() throws ExoPlaybackException {
                }

/* 209 */       final void disable() throws ExoPlaybackException {
/* 210 */           Assertions.checkState(this.state == 2);
/* 211 */           this.state = 1;
/* 212 */           onDisabled();
                }

/* 223 */       protected void onDisabled() throws ExoPlaybackException {
                }

/* 229 */       final void release() throws ExoPlaybackException {
/* 230 */           Assertions.checkState((this.state == 2 || this.state == 3 || this.state == -2) ? false : true);
/* 233 */           this.state = -2;
/* 234 */           onReleased();
                }

/* 245 */       protected void onReleased() throws ExoPlaybackException {
                }

                @Override // com.google.android.exoplayer.ExoPlayer.ExoPlayerComponent
/* 344 */       public void handleMessage(int what, Object object) throws ExoPlaybackException {
                }
            }
