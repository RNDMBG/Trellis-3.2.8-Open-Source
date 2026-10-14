            package com.google.android.exoplayer;

            import com.google.android.exoplayer.util.Assertions;
            
            
    public abstract class TrackRenderer implements ExoPlayer.ExoPlayerComponent {
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

        protected boolean isTimeSource() {
            return false;
                }

       protected final int getState() {
           return this.state;
                }

       final int prepare() throws ExoPlaybackException {
           Assertions.checkState(this.state == 0);
           this.state = doPrepare();
           Assertions.checkState(this.state == 0 || this.state == 1 || this.state == -1);
           return this.state;
                }

       final void enable(long timeUs, boolean joining) throws ExoPlaybackException {
           Assertions.checkState(this.state == 1);
           this.state = 2;
           onEnabled(timeUs, joining);
                }

       protected void onEnabled(long timeUs, boolean joining) throws ExoPlaybackException {
                }

       final void start() throws ExoPlaybackException {
           Assertions.checkState(this.state == 2);
           this.state = 3;
           onStarted();
                }

       protected void onStarted() throws ExoPlaybackException {
                }

       final void stop() throws ExoPlaybackException {
           Assertions.checkState(this.state == 3);
           this.state = 2;
           onStopped();
                }

       protected void onStopped() throws ExoPlaybackException {
                }

       final void disable() throws ExoPlaybackException {
           Assertions.checkState(this.state == 2);
           this.state = 1;
           onDisabled();
                }

       protected void onDisabled() throws ExoPlaybackException {
                }

       final void release() throws ExoPlaybackException {
           Assertions.checkState((this.state == 2 || this.state == 3 || this.state == -2) ? false : true);
           this.state = -2;
           onReleased();
                }

       protected void onReleased() throws ExoPlaybackException {
                }

                @Override 
       public void handleMessage(int what, Object object) throws ExoPlaybackException {
                }
            }
