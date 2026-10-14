            package android.support.v4.media;

            import android.os.SystemClock;
            import android.view.KeyEvent;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 74 */    public abstract class TransportPerformer {
                static final int AUDIOFOCUS_GAIN = 1;
                static final int AUDIOFOCUS_GAIN_TRANSIENT = 2;
                static final int AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK = 3;
                static final int AUDIOFOCUS_LOSS = -1;
                static final int AUDIOFOCUS_LOSS_TRANSIENT = -2;
                static final int AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK = -3;

                public abstract long onGetCurrentPosition();

                public abstract long onGetDuration();

                public abstract boolean onIsPlaying();

                public abstract void onPause();

                public abstract void onSeekTo(long j);

                public abstract void onStart();

                public abstract void onStop();

/* 75 */        public int onGetBufferPercentage() {
/* 76 */            return 100;
                }

/* 97 */        public int onGetTransportControlFlags() {
/* 98 */            return 60;
                }

                /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
/* 126 */       public boolean onMediaButtonDown(int keyCode, KeyEvent event) {
                    switch (keyCode) {
                        case 79:
                        case 85:
/* 139 */                   if (onIsPlaying()) {
/* 140 */                       onPause();
                            } else {
/* 142 */                       onStart();
                            }
                            return true;
                        case 86:
/* 135 */                   onStop();
                            return true;
                        case 126:
/* 129 */                   onStart();
                            return true;
                        case 127:
/* 132 */                   onPause();
                            return true;
                        default:
                            return true;
                    }
                }

/* 163 */       public boolean onMediaButtonUp(int keyCode, KeyEvent event) {
/* 164 */           return true;
                }

/* 187 */       public void onAudioFocusChange(int focusChange) {
/* 188 */           int keyCode = 0;
                    switch (focusChange) {
                        case -1:
/* 193 */                   keyCode = 127;
                            break;
                    }
/* 196 */           if (keyCode != 0) {
/* 197 */               long now = SystemClock.uptimeMillis();
/* 198 */               onMediaButtonDown(keyCode, new KeyEvent(now, now, 0, keyCode, 0));
/* 199 */               onMediaButtonUp(keyCode, new KeyEvent(now, now, 1, keyCode, 0));
                    }
                }
            }
