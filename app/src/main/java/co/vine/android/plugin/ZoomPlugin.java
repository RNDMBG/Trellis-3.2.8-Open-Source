            package co.vine.android.plugin;

            import android.app.Activity;
            import android.content.res.Resources;
            import android.os.Handler;
            import android.support.v4.app.Fragment;
            import android.view.KeyEvent;
            import android.view.LayoutInflater;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ImageView;
            import android.widget.SeekBar;
            import co.vine.android.C0257R;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.recorder.VineRecorder;
            import co.vine.android.recorder.camera.CameraManager;
            import co.vine.android.util.SystemUtil;
            import co.vine.android.util.ViewUtil;
            import co.vine.android.views.FakeKeyEventTouchListener;
            import com.edisonwang.android.slog.SLog;
            import com.googlecode.javacv.cpp.avcodec;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    public class ZoomPlugin extends BaseRecorderPlugin<View, VineRecorder> {
                private int mLastUpdatedZoom;
                private SeekBar mZoomSeek;
                private View mZoomSeekContainer;
                private final SeekBar.OnSeekBarChangeListener mZoomSeekListener;
                private final Runnable mZoomUpdateRunnable;

/* 27 */        static /* synthetic */ SeekBar access$000(ZoomPlugin x0) {
/* 28 */            return x0.mZoomSeek;
                }

/* 27 */        static /* synthetic */ int access$100(ZoomPlugin x0) {
/* 28 */            return x0.mLastUpdatedZoom;
                }

/* 34 */        public ZoomPlugin() {
/* 35 */            super("Zoom");
/* 32 */            this.mLastUpdatedZoom = -1;
/* 38 */            this.mZoomSeekListener = new ZoomSeekChangeListener();
/* 164 */           this.mZoomUpdateRunnable = new RunnableC04661();
                }

/* 41 */        public static class ZoomSeekChangeListener implements SeekBar.OnSeekBarChangeListener {
                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
/* 42 */            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
/* 47 */            public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
/* 51 */            public void onStopTrackingTouch(SeekBar seekBar) {
/* 52 */                CameraManager.getInstance().modifyZoom(seekBar.getProgress() + 1);
                    }
                }

/* 62 */        public static class PlusMinusTouchListener extends FakeKeyEventTouchListener {
                    private final int mColor;
                    private final int mDrawableId;
                    private final Resources mRes;
                    private final ImageView mTarget;

                    /* JADX WARN: Multi-variable type inference failed */
/* 63 */            public PlusMinusTouchListener(Fragment fragment, ImageView target, int res) {
/* 64 */                super(fragment.getActivity(), res == 2130838217 ? avcodec.AV_CODEC_ID_VP9 : avcodec.AV_CODEC_ID_MSS2);
/* 65 */                this.mDrawableId = res;
/* 66 */                this.mRes = fragment.getResources();
/* 67 */                this.mTarget = target;
/* 69 */                if (fragment instanceof RecorderPluginSupportedFragment) {
/* 70 */                    this.mColor = ((RecorderPluginSupportedFragment) fragment).getColor(true);
                        } else {
/* 72 */                    this.mColor = 0;
                        }
/* 74 */                changeColor(1);
                    }

                    @Override // co.vine.android.views.FakeKeyEventTouchListener, android.view.View.OnTouchListener
/* 78 */            public boolean onTouch(View v, MotionEvent event) {
/* 79 */                changeColor(event.getAction());
/* 80 */                return super.onTouch(v, event);
                    }

/* 83 */            private void changeColor(int action) {
                        switch (action) {
                            case 0:
/* 86 */                        ViewUtil.fillColor(this.mRes, this.mColor, this.mDrawableId, this.mTarget);
/* 88 */                        this.mTarget.setAlpha(1.0f);
                                break;
                            case 1:
/* 92 */                        ViewUtil.fillColor(this.mRes, this.mColor, this.mDrawableId, this.mTarget);
/* 94 */                        this.mTarget.setAlpha(0.35f);
                                break;
                        }
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 102 */       public View onLayout(ViewGroup parent, LayoutInflater inflater, Fragment fragment) {
/* 103 */           ViewGroup subOptionsContainer = (ViewGroup) fragment.getView().findViewById(C0257R.id.recording_sub_options);
/* 104 */           this.mZoomSeekContainer = inflater.inflate(C0257R.layout.plugin_zoom_seek_container, subOptionsContainer, false);
/* 105 */           subOptionsContainer.addView(this.mZoomSeekContainer);
/* 106 */           ViewGroup trigger = (ViewGroup) fragment.getView().findViewById(C0257R.id.zoom_minus);
/* 107 */           trigger.setOnTouchListener(new PlusMinusTouchListener(fragment, (ImageView) trigger.getChildAt(0), C0257R.drawable.zoom_minus));
/* 108 */           ViewGroup trigger2 = (ViewGroup) fragment.getView().findViewById(C0257R.id.zoom_plus);
/* 109 */           trigger2.setOnTouchListener(new PlusMinusTouchListener(fragment, (ImageView) trigger2.getChildAt(0), C0257R.drawable.zoom_plus));
/* 110 */           View container = fragment.getView().findViewById(C0257R.id.zoom_seek);
/* 111 */           ViewGroup.LayoutParams params = container.getLayoutParams();
/* 112 */           params.width = (int) ((((double) SystemUtil.getDisplaySize((Activity) fragment.getActivity()).x) * 0.8d) - ((double) ((fragment.getResources().getDisplayMetrics().density * 2.0f) * 36.0f)));
/* 113 */           container.setLayoutParams(params);
/* 114 */           return null;
                }

                @Override // co.vine.android.plugin.BasePlugin, co.vine.android.plugin.Plugin
/* 120 */       public boolean onKeyDown(int keyCode, KeyEvent event) {
                    boolean zoomingIn;
                    boolean zooming;
                    switch (keyCode) {
                        case 24:
                        case avcodec.AV_CODEC_ID_MSS2 /* 168 */:
/* 124 */                   zoomingIn = true;
/* 125 */                   zooming = true;
                            break;
                        case 25:
                        case avcodec.AV_CODEC_ID_VP9 /* 169 */:
/* 130 */                   zoomingIn = false;
/* 131 */                   zooming = true;
                            break;
                        default:
/* 135 */                   zooming = false;
/* 136 */                   zoomingIn = false;
                            break;
                    }
/* 139 */           return onKeyDown(zooming, zoomingIn);
                }

/* 142 */       public boolean onKeyDown(boolean zooming, boolean zoomingIn) {
/* 143 */           VineRecorder recorder = getRecorder();
/* 144 */           if (recorder == null || !zooming || recorder.isEditing()) {
                        return false;
                    }
/* 146 */           recorder.modifyZoom(zoomingIn);
/* 147 */           requestZoomProgressUpdate();
/* 148 */           return true;
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 155 */       public void onResumeCameraAsyncTaskPostExecute() {
/* 156 */           invalidateZoomSlider();
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 160 */       public void onResumeAsyncTaskPostExecute() {
/* 161 */           invalidateZoomSlider();
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.ZoomPlugin$1 */
/* 162 */       class RunnableC04661 implements Runnable {
/* 163 */           RunnableC04661() {
                    }

                    @Override // java.lang.Runnable
/* 166 */           public void run() {
/* 167 */               if (ZoomPlugin.access$000(ZoomPlugin.this) != null && ZoomPlugin.access$100(ZoomPlugin.this) >= 0) {
/* 168 */                   SLog.m446d("Zoom progress updated: " + ZoomPlugin.access$100(ZoomPlugin.this));
/* 169 */                   ZoomPlugin.access$000(ZoomPlugin.this).setProgress(ZoomPlugin.access$100(ZoomPlugin.this) - 1);
                        }
/* 171 */               ZoomPlugin.this.requestZoomProgressUpdate();
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.BasePlugin, co.vine.android.plugin.Plugin
/* 176 */       public void onPause() {
/* 177 */           super.onPause();
/* 178 */           this.mZoomSeek = null;
                }

/* 181 */       public void requestZoomProgressUpdate() {
                    Handler handler;
/* 182 */           if (CameraManager.getInstance().isSmoothZoomingSupported() && (handler = getHandler()) != null) {
/* 185 */               handler.postDelayed(this.mZoomUpdateRunnable, 30L);
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 191 */       public void onZoomUpdated(int zoom, boolean stopped) {
/* 192 */           this.mLastUpdatedZoom = zoom;
/* 193 */           SLog.m446d("Zoom updated: " + zoom);
/* 194 */           if (this.mZoomSeek != null && stopped) {
/* 195 */               this.mLastUpdatedZoom = -1;
/* 196 */               SLog.m446d("Zoom progress updated: " + zoom);
/* 197 */               this.mZoomSeek.setProgress(zoom - 1);
/* 198 */               Handler handler = getHandler();
/* 199 */               if (handler != null) {
/* 200 */                   handler.removeCallbacks(this.mZoomUpdateRunnable);
                        }
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.BasePlugin, co.vine.android.plugin.Plugin
/* 206 */       public void onResume(Activity activity) {
/* 207 */           super.onResume(activity);
/* 208 */           this.mZoomSeek = (SeekBar) activity.findViewById(C0257R.id.zoom_seek);
/* 209 */           this.mZoomSeek.setProgress(0);
/* 210 */           this.mZoomSeek.setOnSeekBarChangeListener(this.mZoomSeekListener);
                }

                @Override // co.vine.android.plugin.BasePlugin, co.vine.android.plugin.Plugin
/* 216 */       public boolean onKeyUp(int keyCode, KeyEvent event) {
                    boolean zooming;
                    VineRecorder r;
                    switch (keyCode) {
                        case 24:
                        case avcodec.AV_CODEC_ID_MSS2 /* 168 */:
/* 220 */                   zooming = true;
                            break;
                        case 25:
                        case avcodec.AV_CODEC_ID_VP9 /* 169 */:
/* 225 */                   zooming = true;
                            break;
                        default:
/* 229 */                   zooming = false;
                            break;
                    }
/* 232 */           if (zooming && (r = getRecorder()) != null && !r.isEditing()) {
/* 236 */               r.stopZoom(this.mZoomUpdateRunnable);
/* 237 */               return true;
                    }
/* 241 */           return false;
                }

/* 244 */       private void invalidateZoomSlider() {
/* 245 */           VineRecorder r = getRecorder();
/* 246 */           RecordConfigUtils.RecordConfig config = null;
/* 247 */           if (r != null) {
/* 248 */               config = r.getConfig();
                    }
/* 250 */           if (config != null && this.mZoomSeekContainer != null) {
/* 251 */               if (config.isZoomButtonEnabled && this.mZoomSeek != null) {
/* 252 */                   this.mZoomSeekContainer.setVisibility(0);
/* 253 */                   if (config.zoomEnabled && CameraManager.getInstance().isZoomSupported()) {
/* 254 */                       this.mZoomSeek.setEnabled(true);
/* 255 */                       this.mZoomSeek.setMax(CameraManager.getInstance().getMaxZoom() - 1);
/* 265 */                       return;
                            } else {
/* 257 */                       this.mZoomSeekContainer.setVisibility(8);
/* 258 */                       this.mZoomSeek.setProgress(0);
/* 259 */                       this.mZoomSeek.setEnabled(false);
/* 265 */                       return;
                            }
                        }
/* 262 */               this.mZoomSeekContainer.setVisibility(8);
                    }
                }
            }
