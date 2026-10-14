            package android.support.v4.media;

            import android.app.PendingIntent;
            import android.content.BroadcastReceiver;
            import android.content.Context;
            import android.content.Intent;
            import android.content.IntentFilter;
            import android.media.AudioManager;
            import android.media.RemoteControlClient;
            import android.util.Log;
            import android.view.KeyEvent;
            import android.view.View;
            import android.view.ViewTreeObserver;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 39 */    class TransportMediatorJellybeanMR2 implements RemoteControlClient.OnGetPlaybackPositionListener, RemoteControlClient.OnPlaybackPositionUpdateListener {
                boolean mAudioFocused;
                final AudioManager mAudioManager;
                final Context mContext;
                boolean mFocused;
                final Intent mIntent;
                PendingIntent mPendingIntent;
                final String mReceiverAction;
                final IntentFilter mReceiverFilter;
                RemoteControlClient mRemoteControl;
                final View mTargetView;
                final TransportMediatorCallback mTransportCallback;
                final ViewTreeObserver.OnWindowAttachListener mWindowAttachListener = new ViewTreeObserverOnWindowAttachListenerC00271();
                final ViewTreeObserver.OnWindowFocusChangeListener mWindowFocusListener = new ViewTreeObserverOnWindowFocusChangeListenerC00282();
                final BroadcastReceiver mMediaButtonReceiver = new C00293();
                AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = new C00304();
                int mPlayState = 0;

                /* JADX INFO: renamed from: android.support.v4.media.TransportMediatorJellybeanMR2$1 */
/* 40 */        class ViewTreeObserverOnWindowAttachListenerC00271 implements ViewTreeObserver.OnWindowAttachListener {
/* 41 */            ViewTreeObserverOnWindowAttachListenerC00271() {
                    }

                    @Override // android.view.ViewTreeObserver.OnWindowAttachListener
/* 44 */            public void onWindowAttached() {
/* 45 */                TransportMediatorJellybeanMR2.this.windowAttached();
                    }

                    @Override // android.view.ViewTreeObserver.OnWindowAttachListener
/* 48 */            public void onWindowDetached() {
/* 49 */                TransportMediatorJellybeanMR2.this.windowDetached();
                    }
                }

                /* JADX INFO: renamed from: android.support.v4.media.TransportMediatorJellybeanMR2$2 */
/* 51 */        class ViewTreeObserverOnWindowFocusChangeListenerC00282 implements ViewTreeObserver.OnWindowFocusChangeListener {
/* 52 */            ViewTreeObserverOnWindowFocusChangeListenerC00282() {
                    }

                    @Override // android.view.ViewTreeObserver.OnWindowFocusChangeListener
/* 55 */            public void onWindowFocusChanged(boolean hasFocus) {
/* 56 */                if (!hasFocus) {
/* 57 */                    TransportMediatorJellybeanMR2.this.loseFocus();
                        } else {
                            TransportMediatorJellybeanMR2.this.gainFocus();
                        }
                    }
                }

                /* JADX INFO: renamed from: android.support.v4.media.TransportMediatorJellybeanMR2$3 */
/* 58 */        class C00293 extends BroadcastReceiver {
/* 59 */            C00293() {
                    }

                    @Override // android.content.BroadcastReceiver
/* 63 */            public void onReceive(Context context, Intent intent) {
                        try {
/* 64 */                    KeyEvent event = (KeyEvent) intent.getParcelableExtra("android.intent.extra.KEY_EVENT");
/* 65 */                    TransportMediatorJellybeanMR2.this.mTransportCallback.handleKey(event);
                        } catch (ClassCastException e) {
/* 67 */                    Log.w("TransportController", e);
                        }
                    }
                }

                /* JADX INFO: renamed from: android.support.v4.media.TransportMediatorJellybeanMR2$4 */
/* 70 */        class C00304 implements AudioManager.OnAudioFocusChangeListener {
/* 71 */            C00304() {
                    }

                    @Override // android.media.AudioManager.OnAudioFocusChangeListener
/* 74 */            public void onAudioFocusChange(int focusChange) {
/* 75 */                TransportMediatorJellybeanMR2.this.mTransportCallback.handleAudioFocusChange(focusChange);
                    }
                }

/* 85 */        public TransportMediatorJellybeanMR2(Context context, AudioManager audioManager, View view, TransportMediatorCallback transportCallback) {
/* 87 */            this.mContext = context;
/* 88 */            this.mAudioManager = audioManager;
/* 89 */            this.mTargetView = view;
/* 90 */            this.mTransportCallback = transportCallback;
/* 91 */            this.mReceiverAction = context.getPackageName() + ":transport:" + System.identityHashCode(this);
/* 92 */            this.mIntent = new Intent(this.mReceiverAction);
/* 93 */            this.mIntent.setPackage(context.getPackageName());
/* 94 */            this.mReceiverFilter = new IntentFilter();
/* 95 */            this.mReceiverFilter.addAction(this.mReceiverAction);
/* 96 */            this.mTargetView.getViewTreeObserver().addOnWindowAttachListener(this.mWindowAttachListener);
/* 97 */            this.mTargetView.getViewTreeObserver().addOnWindowFocusChangeListener(this.mWindowFocusListener);
                }

/* 100 */       public Object getRemoteControlClient() {
/* 101 */           return this.mRemoteControl;
                }

/* 104 */       public void destroy() {
/* 105 */           windowDetached();
/* 106 */           this.mTargetView.getViewTreeObserver().removeOnWindowAttachListener(this.mWindowAttachListener);
/* 107 */           this.mTargetView.getViewTreeObserver().removeOnWindowFocusChangeListener(this.mWindowFocusListener);
                }

/* 110 */       void windowAttached() {
/* 111 */           this.mContext.registerReceiver(this.mMediaButtonReceiver, this.mReceiverFilter);
/* 112 */           this.mPendingIntent = PendingIntent.getBroadcast(this.mContext, 0, this.mIntent, 268435456);
/* 114 */           this.mRemoteControl = new RemoteControlClient(this.mPendingIntent);
/* 115 */           this.mRemoteControl.setOnGetPlaybackPositionListener(this);
/* 116 */           this.mRemoteControl.setPlaybackPositionUpdateListener(this);
                }

/* 119 */       void gainFocus() {
/* 120 */           if (!this.mFocused) {
/* 121 */               this.mFocused = true;
/* 122 */               this.mAudioManager.registerMediaButtonEventReceiver(this.mPendingIntent);
/* 123 */               this.mAudioManager.registerRemoteControlClient(this.mRemoteControl);
/* 124 */               if (this.mPlayState == 3) {
/* 125 */                   takeAudioFocus();
                        }
                    }
                }

/* 130 */       void takeAudioFocus() {
/* 131 */           if (!this.mAudioFocused) {
/* 132 */               this.mAudioFocused = true;
/* 133 */               this.mAudioManager.requestAudioFocus(this.mAudioFocusChangeListener, 3, 1);
                    }
                }

/* 138 */       public void startPlaying() {
/* 139 */           if (this.mPlayState != 3) {
/* 140 */               this.mPlayState = 3;
/* 141 */               this.mRemoteControl.setPlaybackState(3);
                    }
/* 143 */           if (this.mFocused) {
/* 144 */               takeAudioFocus();
                    }
                }

                @Override // android.media.RemoteControlClient.OnGetPlaybackPositionListener
/* 149 */       public long onGetPlaybackPosition() {
/* 150 */           return this.mTransportCallback.getPlaybackPosition();
                }

                @Override // android.media.RemoteControlClient.OnPlaybackPositionUpdateListener
/* 154 */       public void onPlaybackPositionUpdate(long newPositionMs) {
/* 155 */           this.mTransportCallback.playbackPositionUpdate(newPositionMs);
                }

/* 158 */       public void refreshState(boolean playing, long position, int transportControls) {
/* 159 */           if (this.mRemoteControl != null) {
/* 160 */               this.mRemoteControl.setPlaybackState(playing ? 3 : 1, position, playing ? 1.0f : 0.0f);
/* 162 */               this.mRemoteControl.setTransportControlFlags(transportControls);
                    }
                }

/* 166 */       public void pausePlaying() {
/* 167 */           if (this.mPlayState == 3) {
/* 168 */               this.mPlayState = 2;
/* 169 */               this.mRemoteControl.setPlaybackState(2);
                    }
/* 171 */           dropAudioFocus();
                }

/* 174 */       public void stopPlaying() {
/* 175 */           if (this.mPlayState != 1) {
/* 176 */               this.mPlayState = 1;
/* 177 */               this.mRemoteControl.setPlaybackState(1);
                    }
/* 179 */           dropAudioFocus();
                }

/* 182 */       void dropAudioFocus() {
/* 183 */           if (this.mAudioFocused) {
/* 184 */               this.mAudioFocused = false;
/* 185 */               this.mAudioManager.abandonAudioFocus(this.mAudioFocusChangeListener);
                    }
                }

/* 189 */       void loseFocus() {
/* 190 */           dropAudioFocus();
/* 191 */           if (this.mFocused) {
/* 192 */               this.mFocused = false;
/* 193 */               this.mAudioManager.unregisterRemoteControlClient(this.mRemoteControl);
/* 194 */               this.mAudioManager.unregisterMediaButtonEventReceiver(this.mPendingIntent);
                    }
                }

/* 198 */       void windowDetached() {
/* 199 */           loseFocus();
/* 200 */           if (this.mPendingIntent != null) {
/* 201 */               this.mContext.unregisterReceiver(this.mMediaButtonReceiver);
/* 202 */               this.mPendingIntent.cancel();
/* 203 */               this.mPendingIntent = null;
/* 204 */               this.mRemoteControl = null;
                    }
                }
            }
