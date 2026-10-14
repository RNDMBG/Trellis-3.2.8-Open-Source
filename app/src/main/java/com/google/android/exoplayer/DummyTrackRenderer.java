            package com.google.android.exoplayer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public class DummyTrackRenderer extends TrackRenderer {
                @Override // com.google.android.exoplayer.TrackRenderer
/* 28 */        protected int doPrepare() throws ExoPlaybackException {
/* 29 */            return -1;
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 33 */        protected boolean isEnded() {
/* 34 */            throw new IllegalStateException();
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 38 */        protected boolean isReady() {
/* 39 */            throw new IllegalStateException();
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 43 */        protected void seekTo(long timeUs) {
/* 44 */            throw new IllegalStateException();
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 48 */        protected void doSomeWork(long timeUs) {
/* 49 */            throw new IllegalStateException();
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 53 */        protected long getDurationUs() {
/* 54 */            throw new IllegalStateException();
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 58 */        protected long getBufferedPositionUs() {
/* 59 */            throw new IllegalStateException();
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 63 */        protected long getCurrentPositionUs() {
/* 64 */            throw new IllegalStateException();
                }
            }
