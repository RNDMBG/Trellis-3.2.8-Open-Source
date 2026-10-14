            package co.vine.android.plugin;

            import android.os.Handler;
            import android.support.v4.app.Fragment;
            import android.view.KeyEvent;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.Toast;
            import co.vine.android.recorder.BasicVineRecorder;
            import co.vine.android.recorder.VineRecorder;
            import co.vine.android.util.Util;
            import com.edisonwang.android.slog.SLog;
            import com.googlecode.javacv.cpp.avcodec;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class SecretModePlugin extends BaseRecorderPlugin<View, VineRecorder> {
                private static final int[] SECRETS = {25, 24, 24, 24, 24, 24, 25};
                private boolean isSecretModeOn;
                private int mCurrentSecret;
                private int mCurrentSteadyCount;
                private SecretModes mSecretMode;
                private Toast mSecretToast;

                public enum SecretModes {
                    STOP_MOTION,
                    WHITE_BALANCE,
                    EXPOSURE,
                    SCENE_MODE,
                    COLOR_EFFECTS,
                    ANTI_BANDING,
                    IMAGE_STAB
                }

/* 19 */        static /* synthetic */ int access$008(SecretModePlugin x0) {
/* 20 */            int i = x0.mCurrentSteadyCount;
                    x0.mCurrentSteadyCount = i + 1;
                    return i;
                }

/* 19 */        static /* synthetic */ void access$100(SecretModePlugin x0) {
/* 20 */            x0.doStopMotion();
                }

/* 49 */        public SecretModePlugin() {
/* 50 */            super("Secret Mode");
/* 42 */            this.mCurrentSecret = 0;
/* 43 */            this.isSecretModeOn = false;
/* 47 */            this.mCurrentSteadyCount = 0;
                }

                @Override // co.vine.android.plugin.BasePlugin, co.vine.android.plugin.Plugin
/* 57 */        public boolean onKeyDown(int keyCode, KeyEvent event) {
                    boolean zoomingIn;
                    boolean zooming;
                    switch (keyCode) {
                        case 24:
                        case avcodec.AV_CODEC_ID_MSS2 /* 168 */:
/* 61 */                    zoomingIn = true;
/* 62 */                    zooming = true;
                            break;
                        case 25:
                        case avcodec.AV_CODEC_ID_VP9 /* 169 */:
/* 67 */                    zoomingIn = false;
/* 68 */                    zooming = true;
                            break;
                        default:
/* 72 */                    zooming = false;
/* 73 */                    zoomingIn = false;
                            break;
                    }
/* 76 */            return onKeyDown(keyCode, zooming, zoomingIn);
                }

/* 79 */        public boolean onKeyDown(int keyCode, boolean zooming, boolean zoomingIn) {
/* 80 */            if (!this.isSecretModeOn) {
/* 81 */                if (keyCode == SECRETS[this.mCurrentSecret] && SLog.sLogsOn) {
/* 82 */                    this.mCurrentSecret++;
/* 83 */                    if (this.mCurrentSecret >= SECRETS.length) {
/* 84 */                        if (this.mActivity != null) {
/* 85 */                            enableSecretMode(Util.showCenteredToast(this.mActivity, "Secret mode enabled."));
                                }
/* 87 */                        this.isSecretModeOn = true;
/* 88 */                        this.mCurrentSecret = 0;
                                return true;
                            }
                        } else {
/* 92 */                    this.mCurrentSecret = 0;
                        }
                    } else if (zooming) {
/* 96 */                VineRecorder recorder = getRecorder();
/* 97 */                if (recorder == null) {
                            return true;
                        }
/* 98 */                if (!recorder.isEditing()) {
/* 99 */                    if (zoomingIn) {
/* 100 */                       doSecretVolumeUp();
                                return true;
                            }
/* 102 */                   doSecretVolumeDown();
                            return true;
                        }
/* 105 */               Util.showCenteredToast(this.mActivity, "Reversed.");
/* 106 */               reverseFrames();
                        return true;
                    }
                    return false;
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 116 */       public boolean onBackButtonPressed(boolean isEditing) {
/* 117 */           if (!this.isSecretModeOn) {
                        return false;
                    }
/* 118 */           changeSecretMode();
/* 119 */           return true;
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 126 */       public View onLayout(ViewGroup parent, LayoutInflater inflater, Fragment fragment) {
/* 127 */           return null;
                }

/* 130 */       public void changeSecretMode() {
/* 131 */           if (this.mSecretMode != null) {
/* 132 */               SecretModes[] values = SecretModes.values();
/* 133 */               this.mSecretMode = values[(this.mSecretMode.ordinal() + 1) % values.length];
/* 134 */               this.mSecretToast.setText("Secret Mode: " + this.mSecretMode.name());
/* 135 */               this.mSecretToast.show();
                    }
                }

/* 139 */       public void enableSecretMode(Toast toast) {
/* 140 */           this.mSecretToast = toast;
/* 141 */           this.mSecretMode = SecretModes.STOP_MOTION;
                }

/* 144 */       public void doSecretVolumeUp() {
/* 145 */           VineRecorder recorder = getRecorder();
/* 146 */           if (recorder != null && recorder.canKeepRecording()) {
                        switch (this.mSecretMode) {
                            case STOP_MOTION:
/* 149 */                       doOneFrame();
                                break;
                            case WHITE_BALANCE:
/* 152 */                       recorder.modifyWhiteBalance(true);
                                break;
                            case EXPOSURE:
/* 155 */                       recorder.modifyExposure(true);
                                break;
                            case SCENE_MODE:
/* 158 */                       recorder.modifySceneMode(true);
                                break;
                            case COLOR_EFFECTS:
/* 161 */                       recorder.modifyColorEffects(true);
                                break;
                            case ANTI_BANDING:
/* 164 */                       recorder.modifyAntiBanding(true);
                                break;
                            case IMAGE_STAB:
/* 167 */                       recorder.switchImageStabilization();
                                break;
                        }
                    }
                }

/* 173 */       public void doOneFrame() {
/* 174 */           VineRecorder recorder = getRecorder();
/* 175 */           if (recorder != null && recorder.canKeepRecording()) {
/* 176 */               recorder.doOneFrame();
                    }
                }

/* 180 */       private void doStopMotion() {
                    Handler handler;
/* 181 */           BasicVineRecorder recorder = getRecorder();
/* 182 */           if (recorder != null && recorder.canKeepRecording() && (handler = getHandler()) != null && this.mCurrentSteadyCount < 40) {
/* 185 */               handler.postDelayed(new RunnableC04611(), 50L);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.SecretModePlugin$1 */
/* 183 */       class RunnableC04611 implements Runnable {
/* 184 */           RunnableC04611() {
                    }

                    @Override // java.lang.Runnable
/* 187 */           public void run() {
/* 188 */               SecretModePlugin.this.doOneFrame();
/* 189 */               SecretModePlugin.access$008(SecretModePlugin.this);
/* 190 */               SecretModePlugin.access$100(SecretModePlugin.this);
                    }
                }

/* 197 */       public void reverseFrames() {
/* 198 */           VineRecorder recorder = getRecorder();
/* 199 */           if (recorder != null) {
/* 200 */               recorder.reverseFrames();
                    }
                }

/* 204 */       public void doSecretVolumeDown() {
/* 205 */           VineRecorder recorder = getRecorder();
/* 206 */           if (recorder != null) {
                        switch (this.mSecretMode) {
                            case STOP_MOTION:
/* 209 */                       doStopMotion();
                                break;
                            case WHITE_BALANCE:
/* 212 */                       recorder.modifyWhiteBalance(false);
                                break;
                            case EXPOSURE:
/* 215 */                       recorder.modifyExposure(false);
                                break;
                            case SCENE_MODE:
/* 218 */                       recorder.modifySceneMode(false);
                                break;
                            case COLOR_EFFECTS:
/* 221 */                       recorder.modifyColorEffects(false);
                                break;
                            case ANTI_BANDING:
/* 224 */                       recorder.modifyAntiBanding(false);
                                break;
                            case IMAGE_STAB:
/* 227 */                       recorder.switchImageStabilization();
                                break;
                        }
                    }
                }
            }
