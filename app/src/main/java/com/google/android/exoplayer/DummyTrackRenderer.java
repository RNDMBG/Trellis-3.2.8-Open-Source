            package com.google.android.exoplayer;
            
            
    public class DummyTrackRenderer extends TrackRenderer {
                @Override 
        protected int doPrepare() throws ExoPlaybackException {
            return -1;
                }

                @Override 
        protected boolean isEnded() {
            throw new IllegalStateException();
                }

                @Override 
        protected boolean isReady() {
            throw new IllegalStateException();
                }

                @Override 
        protected void seekTo(long timeUs) {
            throw new IllegalStateException();
                }

                @Override 
        protected void doSomeWork(long timeUs) {
            throw new IllegalStateException();
                }

                @Override 
        protected long getDurationUs() {
            throw new IllegalStateException();
                }

                @Override 
        protected long getBufferedPositionUs() {
            throw new IllegalStateException();
                }

                @Override 
        protected long getCurrentPositionUs() {
            throw new IllegalStateException();
                }
            }
