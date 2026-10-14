            package android.support.v4.media;

            import android.app.Activity;
            import android.content.Context;
            import android.media.AudioManager;
            import android.os.Build;
            import android.support.v4.view.KeyEventCompat;
            import android.view.KeyEvent;
            import android.view.View;
            import com.google.android.exoplayer.util.MimeTypes;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 55 */    public class TransportMediator extends TransportController {
                public static final int FLAG_KEY_MEDIA_FAST_FORWARD = 64;
                public static final int FLAG_KEY_MEDIA_NEXT = 128;
                public static final int FLAG_KEY_MEDIA_PAUSE = 16;
                public static final int FLAG_KEY_MEDIA_PLAY = 4;
                public static final int FLAG_KEY_MEDIA_PLAY_PAUSE = 8;
                public static final int FLAG_KEY_MEDIA_PREVIOUS = 1;
                public static final int FLAG_KEY_MEDIA_REWIND = 2;
                public static final int FLAG_KEY_MEDIA_STOP = 32;
                public static final int KEYCODE_MEDIA_PAUSE = 127;
                public static final int KEYCODE_MEDIA_PLAY = 126;
                public static final int KEYCODE_MEDIA_RECORD = 130;
                final AudioManager mAudioManager;
                final TransportPerformer mCallbacks;
                final Context mContext;
                final TransportMediatorJellybeanMR2 mController;
                final Object mDispatcherState;
                final KeyEvent.Callback mKeyEventCallback;
                final ArrayList<TransportStateListener> mListeners;
                final TransportMediatorCallback mTransportKeyCallback;
                final View mView;

                /* JADX INFO: renamed from: android.support.v4.media.TransportMediator$1 */
/* 56 */        class C00251 implements TransportMediatorCallback {
/* 57 */            C00251() {
                    }

                    @Override // android.support.v4.media.TransportMediatorCallback
/* 60 */            public void handleKey(KeyEvent key) {
/* 61 */                key.dispatch(TransportMediator.this.mKeyEventCallback);
                    }

                    @Override // android.support.v4.media.TransportMediatorCallback
/* 64 */            public void handleAudioFocusChange(int focusChange) {
/* 65 */                TransportMediator.this.mCallbacks.onAudioFocusChange(focusChange);
                    }

                    @Override // android.support.v4.media.TransportMediatorCallback
/* 69 */            public long getPlaybackPosition() {
/* 70 */                return TransportMediator.this.mCallbacks.onGetCurrentPosition();
                    }

                    @Override // android.support.v4.media.TransportMediatorCallback
/* 74 */            public void playbackPositionUpdate(long newPositionMs) {
/* 75 */                TransportMediator.this.mCallbacks.onSeekTo(newPositionMs);
                    }
                }

/* 111 */       static boolean isMediaKey(int keyCode) {
                    switch (keyCode) {
                        case 79:
                        case 85:
                        case 86:
                        case 87:
                        case 88:
                        case 89:
                        case 90:
                        case 91:
                        case 126:
                        case 127:
                        case 130:
/* 124 */                   return true;
                        default:
/* 127 */                   return false;
                    }
                }

                /* JADX INFO: renamed from: android.support.v4.media.TransportMediator$2 */
/* 128 */       class KeyEventCallbackC00262 implements KeyEvent.Callback {
/* 129 */           KeyEventCallbackC00262() {
                    }

                    @Override // android.view.KeyEvent.Callback
/* 132 */           public boolean onKeyDown(int keyCode, KeyEvent event) {
/* 133 */               if (TransportMediator.isMediaKey(keyCode)) {
                            return TransportMediator.this.mCallbacks.onMediaButtonDown(keyCode, event);
                        }
                        return false;
                    }

                    @Override // android.view.KeyEvent.Callback
/* 136 */           public boolean onKeyLongPress(int keyCode, KeyEvent event) {
/* 137 */               return false;
                    }

                    @Override // android.view.KeyEvent.Callback
/* 141 */           public boolean onKeyUp(int keyCode, KeyEvent event) {
/* 142 */               if (TransportMediator.isMediaKey(keyCode)) {
                            return TransportMediator.this.mCallbacks.onMediaButtonUp(keyCode, event);
                        }
                        return false;
                    }

                    @Override // android.view.KeyEvent.Callback
/* 146 */           public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
/* 147 */               return false;
                    }
                }

/* 151 */       public TransportMediator(Activity activity, TransportPerformer callbacks) {
/* 152 */           this(activity, null, callbacks);
                }

/* 155 */       public TransportMediator(View view, TransportPerformer callbacks) {
/* 156 */           this(null, view, callbacks);
                }

/* 158 */       private TransportMediator(Activity activity, View view, TransportPerformer callbacks) {
/* 55 */            this.mListeners = new ArrayList<>();
/* 57 */            this.mTransportKeyCallback = new C00251();
/* 130 */           this.mKeyEventCallback = new KeyEventCallbackC00262();
/* 160 */           this.mContext = activity != null ? activity : view.getContext();
/* 161 */           this.mCallbacks = callbacks;
/* 162 */           this.mAudioManager = (AudioManager) this.mContext.getSystemService(MimeTypes.BASE_TYPE_AUDIO);
/* 163 */           this.mView = activity != null ? activity.getWindow().getDecorView() : view;
/* 164 */           this.mDispatcherState = KeyEventCompat.getKeyDispatcherState(this.mView);
/* 165 */           if (Build.VERSION.SDK_INT >= 18) {
/* 166 */               this.mController = new TransportMediatorJellybeanMR2(this.mContext, this.mAudioManager, this.mView, this.mTransportKeyCallback);
                    } else {
/* 169 */               this.mController = null;
                    }
                }

/* 189 */       public Object getRemoteControlClient() {
/* 190 */           if (this.mController != null) {
                        return this.mController.getRemoteControlClient();
                    }
                    return null;
                }

/* 199 */       public boolean dispatchKeyEvent(KeyEvent event) {
/* 200 */           return KeyEventCompat.dispatch(event, this.mKeyEventCallback, this.mDispatcherState, this);
                }

                @Override // android.support.v4.media.TransportController
/* 203 */       public void registerStateListener(TransportStateListener listener) {
/* 204 */           this.mListeners.add(listener);
                }

                @Override // android.support.v4.media.TransportController
/* 207 */       public void unregisterStateListener(TransportStateListener listener) {
/* 208 */           this.mListeners.remove(listener);
                }

/* 211 */       private TransportStateListener[] getListeners() {
/* 212 */           if (this.mListeners.size() <= 0) {
/* 213 */               return null;
                    }
/* 215 */           TransportStateListener[] listeners = new TransportStateListener[this.mListeners.size()];
/* 216 */           this.mListeners.toArray(listeners);
/* 217 */           return listeners;
                }

/* 220 */       private void reportPlayingChanged() {
/* 221 */           TransportStateListener[] listeners = getListeners();
/* 222 */           if (listeners != null) {
                        for (TransportStateListener listener : listeners) {
/* 224 */                   listener.onPlayingChanged(this);
                        }
                    }
                }

/* 229 */       private void reportTransportControlsChanged() {
/* 230 */           TransportStateListener[] listeners = getListeners();
/* 231 */           if (listeners != null) {
                        for (TransportStateListener listener : listeners) {
/* 233 */                   listener.onTransportControlsChanged(this);
                        }
                    }
                }

/* 238 */       private void pushControllerState() {
/* 239 */           if (this.mController != null) {
/* 240 */               this.mController.refreshState(this.mCallbacks.onIsPlaying(), this.mCallbacks.onGetCurrentPosition(), this.mCallbacks.onGetTransportControlFlags());
                    }
                }

/* 246 */       public void refreshState() {
/* 247 */           pushControllerState();
/* 248 */           reportPlayingChanged();
/* 249 */           reportTransportControlsChanged();
                }

                @Override // android.support.v4.media.TransportController
/* 257 */       public void startPlaying() {
/* 258 */           if (this.mController != null) {
/* 259 */               this.mController.startPlaying();
                    }
/* 261 */           this.mCallbacks.onStart();
/* 262 */           pushControllerState();
/* 263 */           reportPlayingChanged();
                }

                @Override // android.support.v4.media.TransportController
/* 271 */       public void pausePlaying() {
/* 272 */           if (this.mController != null) {
/* 273 */               this.mController.pausePlaying();
                    }
/* 275 */           this.mCallbacks.onPause();
/* 276 */           pushControllerState();
/* 277 */           reportPlayingChanged();
                }

                @Override // android.support.v4.media.TransportController
/* 285 */       public void stopPlaying() {
/* 286 */           if (this.mController != null) {
/* 287 */               this.mController.stopPlaying();
                    }
/* 289 */           this.mCallbacks.onStop();
/* 290 */           pushControllerState();
/* 291 */           reportPlayingChanged();
                }

                @Override // android.support.v4.media.TransportController
/* 295 */       public long getDuration() {
/* 296 */           return this.mCallbacks.onGetDuration();
                }

                @Override // android.support.v4.media.TransportController
/* 300 */       public long getCurrentPosition() {
/* 301 */           return this.mCallbacks.onGetCurrentPosition();
                }

                @Override // android.support.v4.media.TransportController
/* 305 */       public void seekTo(long pos) {
/* 306 */           this.mCallbacks.onSeekTo(pos);
                }

                @Override // android.support.v4.media.TransportController
/* 310 */       public boolean isPlaying() {
/* 311 */           return this.mCallbacks.onIsPlaying();
                }

                @Override // android.support.v4.media.TransportController
/* 315 */       public int getBufferPercentage() {
/* 316 */           return this.mCallbacks.onGetBufferPercentage();
                }

                @Override // android.support.v4.media.TransportController
/* 331 */       public int getTransportControlFlags() {
/* 332 */           return this.mCallbacks.onGetTransportControlFlags();
                }

/* 340 */       public void destroy() {
/* 341 */           this.mController.destroy();
                }
            }
