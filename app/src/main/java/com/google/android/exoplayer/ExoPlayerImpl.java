            package com.google.android.exoplayer;

            import android.annotation.SuppressLint;
            import android.os.Handler;
            import android.os.Looper;
            import android.os.Message;
            import android.util.Log;
            import java.util.concurrent.CopyOnWriteArraySet;
            
            
    final class ExoPlayerImpl implements ExoPlayer {
                private static final String TAG = "ExoPlayerImpl";
                private final Handler eventHandler;
                private final ExoPlayerImplInternal internalPlayer;
                private final CopyOnWriteArraySet<ExoPlayer.Listener> listeners;
                private int pendingPlayWhenReadyAcks;
                private boolean playWhenReady;
                private int playbackState;
                private final boolean[] rendererEnabledFlags;

                @SuppressLint({"HandlerLeak"})
        public ExoPlayerImpl(int rendererCount, int minBufferMs, int minRebufferMs) {
            Log.i(TAG, "Init 1.0.13");
            this.playbackState = 1;
            this.listeners = new CopyOnWriteArraySet<>();
            this.rendererEnabledFlags = new boolean[rendererCount];
                    for (int i = 0; i < this.rendererEnabledFlags.length; i++) {
                this.rendererEnabledFlags[i] = true;
                    }
            this.eventHandler = new HandlerC08521();
            this.internalPlayer = new ExoPlayerImplInternal(this.eventHandler, this.playWhenReady, this.rendererEnabledFlags, minBufferMs, minRebufferMs);
                }

                
        class HandlerC08521 extends Handler {
            HandlerC08521() {
                    }

                    @Override 
            public void handleMessage(Message msg) {
                ExoPlayerImpl.this.handleEvent(msg);
                    }
                }

                @Override 
        public Looper getPlaybackLooper() {
            return this.internalPlayer.getPlaybackLooper();
                }

                @Override 
        public void addListener(ExoPlayer.Listener listener) {
            this.listeners.add(listener);
                }

                @Override 
        public void removeListener(ExoPlayer.Listener listener) {
            this.listeners.remove(listener);
                }

                @Override 
        public int getPlaybackState() {
            return this.playbackState;
                }

                @Override 
        public void prepare(TrackRenderer... renderers) {
            this.internalPlayer.prepare(renderers);
                }

                @Override 
        public void setRendererEnabled(int index, boolean enabled) {
            if (this.rendererEnabledFlags[index] != enabled) {
               this.rendererEnabledFlags[index] = enabled;
               this.internalPlayer.setRendererEnabled(index, enabled);
                    }
                }

                @Override 
       public boolean getRendererEnabled(int index) {
           return this.rendererEnabledFlags[index];
                }

                @Override 
       public void setPlayWhenReady(boolean playWhenReady) {
           if (this.playWhenReady != playWhenReady) {
               this.playWhenReady = playWhenReady;
               this.pendingPlayWhenReadyAcks++;
               this.internalPlayer.setPlayWhenReady(playWhenReady);
                        for (ExoPlayer.Listener listener : this.listeners) {
                   listener.onPlayerStateChanged(playWhenReady, this.playbackState);
                        }
                    }
                }

                @Override 
       public boolean getPlayWhenReady() {
           return this.playWhenReady;
                }

                @Override 
       public boolean isPlayWhenReadyCommitted() {
                    return this.pendingPlayWhenReadyAcks == 0;
                }

                @Override 
       public void seekTo(int positionMs) {
           this.internalPlayer.seekTo(positionMs);
                }

                @Override 
       public void stop() {
           this.internalPlayer.stop();
                }

                @Override 
       public void release() {
           this.internalPlayer.release();
           this.eventHandler.removeCallbacksAndMessages(null);
                }

                @Override 
       public void sendMessage(ExoPlayer.ExoPlayerComponent target, int messageType, Object message) {
           this.internalPlayer.sendMessage(target, messageType, message);
                }

                @Override 
       public void blockingSendMessage(ExoPlayer.ExoPlayerComponent target, int messageType, Object message) {
           this.internalPlayer.blockingSendMessage(target, messageType, message);
                }

                @Override 
       public int getDuration() {
           return this.internalPlayer.getDuration();
                }

                @Override 
       public int getCurrentPosition() {
           return this.internalPlayer.getCurrentPosition();
                }

                @Override 
       public int getBufferedPosition() {
           return this.internalPlayer.getBufferedPosition();
                }

                @Override 
       public int getBufferedPercentage() {
           int bufferedPosition = getBufferedPosition();
           int duration = getDuration();
           if (bufferedPosition == -1 || duration == -1) {
                        return 0;
                    }
                    if (duration == 0) {
                        return 100;
                    }
                    return (bufferedPosition * 100) / duration;
                }

       void handleEvent(Message msg) {
                    switch (msg.what) {
                        case 1:
                   this.playbackState = msg.arg1;
                            for (ExoPlayer.Listener listener : this.listeners) {
                       listener.onPlayerStateChanged(this.playWhenReady, this.playbackState);
                            }
                            break;
                        case 2:
                   this.pendingPlayWhenReadyAcks--;
                   if (this.pendingPlayWhenReadyAcks == 0) {
                                for (ExoPlayer.Listener listener2 : this.listeners) {
                           listener2.onPlayWhenReadyCommitted();
                                }
                            }
                            break;
                        case 3:
                   ExoPlaybackException exception = (ExoPlaybackException) msg.obj;
                            for (ExoPlayer.Listener listener3 : this.listeners) {
                       listener3.onPlayerError(exception);
                            }
                            break;
                    }
                }
            }
