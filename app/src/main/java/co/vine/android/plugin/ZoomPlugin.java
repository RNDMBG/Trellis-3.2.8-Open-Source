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
            
            
    public class ZoomPlugin extends BaseRecorderPlugin<View, VineRecorder> {
                private int mLastUpdatedZoom;
                private SeekBar mZoomSeek;
                private View mZoomSeekContainer;
                private final SeekBar.OnSeekBarChangeListener mZoomSeekListener;
                private final Runnable mZoomUpdateRunnable;

        static  SeekBar access$000(ZoomPlugin x0) {
            return x0.mZoomSeek;
                }

        static  int access$100(ZoomPlugin x0) {
            return x0.mLastUpdatedZoom;
                }

        public ZoomPlugin() {
            super("Zoom");
            this.mLastUpdatedZoom = -1;
            this.mZoomSeekListener = new ZoomSeekChangeListener();
           this.mZoomUpdateRunnable = new RunnableC04661();
                }

        public static class ZoomSeekChangeListener implements SeekBar.OnSeekBarChangeListener {
                    @Override 
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    }

                    @Override 
            public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override 
            public void onStopTrackingTouch(SeekBar seekBar) {
                CameraManager.getInstance().modifyZoom(seekBar.getProgress() + 1);
                    }
                }

        public static class PlusMinusTouchListener extends FakeKeyEventTouchListener {
                    private final int mColor;
                    private final int mDrawableId;
                    private final Resources mRes;
                    private final ImageView mTarget;

                    
            public PlusMinusTouchListener(Fragment fragment, ImageView target, int res) {
                super(fragment.getActivity(), res == 2130838217 ? avcodec.AV_CODEC_ID_VP9 : avcodec.AV_CODEC_ID_MSS2);
                this.mDrawableId = res;
                this.mRes = fragment.getResources();
                this.mTarget = target;
                if (fragment instanceof RecorderPluginSupportedFragment) {
                    this.mColor = ((RecorderPluginSupportedFragment) fragment).getColor(true);
                        } else {
                    this.mColor = 0;
                        }
                changeColor(1);
                    }

                    @Override 
            public boolean onTouch(View v, MotionEvent event) {
                changeColor(event.getAction());
                return super.onTouch(v, event);
                    }

            private void changeColor(int action) {
                        switch (action) {
                            case 0:
                        ViewUtil.fillColor(this.mRes, this.mColor, this.mDrawableId, this.mTarget);
                        this.mTarget.setAlpha(1.0f);
                                break;
                            case 1:
                        ViewUtil.fillColor(this.mRes, this.mColor, this.mDrawableId, this.mTarget);
                        this.mTarget.setAlpha(0.35f);
                                break;
                        }
                    }
                }

                @Override 
       public View onLayout(ViewGroup parent, LayoutInflater inflater, Fragment fragment) {
           ViewGroup subOptionsContainer = (ViewGroup) fragment.getView().findViewById(C0257R.id.recording_sub_options);
           this.mZoomSeekContainer = inflater.inflate(C0257R.layout.plugin_zoom_seek_container, subOptionsContainer, false);
           subOptionsContainer.addView(this.mZoomSeekContainer);
           ViewGroup trigger = (ViewGroup) fragment.getView().findViewById(C0257R.id.zoom_minus);
           trigger.setOnTouchListener(new PlusMinusTouchListener(fragment, (ImageView) trigger.getChildAt(0), C0257R.drawable.zoom_minus));
           ViewGroup trigger2 = (ViewGroup) fragment.getView().findViewById(C0257R.id.zoom_plus);
           trigger2.setOnTouchListener(new PlusMinusTouchListener(fragment, (ImageView) trigger2.getChildAt(0), C0257R.drawable.zoom_plus));
           View container = fragment.getView().findViewById(C0257R.id.zoom_seek);
           ViewGroup.LayoutParams params = container.getLayoutParams();
           params.width = (int) ((((double) SystemUtil.getDisplaySize((Activity) fragment.getActivity()).x) * 0.8d) - ((double) ((fragment.getResources().getDisplayMetrics().density * 2.0f) * 36.0f)));
           container.setLayoutParams(params);
           return null;
                }

                @Override 
       public boolean onKeyDown(int keyCode, KeyEvent event) {
                    boolean zoomingIn;
                    boolean zooming;
                    switch (keyCode) {
                        case 24:
                        case avcodec.AV_CODEC_ID_MSS2 :
                   zoomingIn = true;
                   zooming = true;
                            break;
                        case 25:
                        case avcodec.AV_CODEC_ID_VP9 :
                   zoomingIn = false;
                   zooming = true;
                            break;
                        default:
                   zooming = false;
                   zoomingIn = false;
                            break;
                    }
           return onKeyDown(zooming, zoomingIn);
                }

       public boolean onKeyDown(boolean zooming, boolean zoomingIn) {
           VineRecorder recorder = getRecorder();
           if (recorder == null || !zooming || recorder.isEditing()) {
                        return false;
                    }
           recorder.modifyZoom(zoomingIn);
           requestZoomProgressUpdate();
           return true;
                }

                @Override 
       public void onResumeCameraAsyncTaskPostExecute() {
           invalidateZoomSlider();
                }

                @Override 
       public void onResumeAsyncTaskPostExecute() {
           invalidateZoomSlider();
                }

                
       class RunnableC04661 implements Runnable {
           RunnableC04661() {
                    }

                    @Override 
           public void run() {
               if (ZoomPlugin.access$000(ZoomPlugin.this) != null && ZoomPlugin.access$100(ZoomPlugin.this) >= 0) {
                   SLog.m446d("Zoom progress updated: " + ZoomPlugin.access$100(ZoomPlugin.this));
                   ZoomPlugin.access$000(ZoomPlugin.this).setProgress(ZoomPlugin.access$100(ZoomPlugin.this) - 1);
                        }
               ZoomPlugin.this.requestZoomProgressUpdate();
                    }
                }

                @Override 
       public void onPause() {
           super.onPause();
           this.mZoomSeek = null;
                }

       public void requestZoomProgressUpdate() {
                    Handler handler;
           if (CameraManager.getInstance().isSmoothZoomingSupported() && (handler = getHandler()) != null) {
               handler.postDelayed(this.mZoomUpdateRunnable, 30L);
                    }
                }

                @Override 
       public void onZoomUpdated(int zoom, boolean stopped) {
           this.mLastUpdatedZoom = zoom;
           SLog.m446d("Zoom updated: " + zoom);
           if (this.mZoomSeek != null && stopped) {
               this.mLastUpdatedZoom = -1;
               SLog.m446d("Zoom progress updated: " + zoom);
               this.mZoomSeek.setProgress(zoom - 1);
               Handler handler = getHandler();
               if (handler != null) {
                   handler.removeCallbacks(this.mZoomUpdateRunnable);
                        }
                    }
                }

                @Override 
       public void onResume(Activity activity) {
           super.onResume(activity);
           this.mZoomSeek = (SeekBar) activity.findViewById(C0257R.id.zoom_seek);
           this.mZoomSeek.setProgress(0);
           this.mZoomSeek.setOnSeekBarChangeListener(this.mZoomSeekListener);
                }

                @Override 
       public boolean onKeyUp(int keyCode, KeyEvent event) {
                    boolean zooming;
                    VineRecorder r;
                    switch (keyCode) {
                        case 24:
                        case avcodec.AV_CODEC_ID_MSS2 :
                   zooming = true;
                            break;
                        case 25:
                        case avcodec.AV_CODEC_ID_VP9 :
                   zooming = true;
                            break;
                        default:
                   zooming = false;
                            break;
                    }
           if (zooming && (r = getRecorder()) != null && !r.isEditing()) {
               r.stopZoom(this.mZoomUpdateRunnable);
               return true;
                    }
           return false;
                }

       private void invalidateZoomSlider() {
           VineRecorder r = getRecorder();
           RecordConfigUtils.RecordConfig config = null;
           if (r != null) {
               config = r.getConfig();
                    }
           if (config != null && this.mZoomSeekContainer != null) {
               if (config.isZoomButtonEnabled && this.mZoomSeek != null) {
                   this.mZoomSeekContainer.setVisibility(0);
                   if (config.zoomEnabled && CameraManager.getInstance().isZoomSupported()) {
                       this.mZoomSeek.setEnabled(true);
                       this.mZoomSeek.setMax(CameraManager.getInstance().getMaxZoom() - 1);
                       return;
                            } else {
                       this.mZoomSeekContainer.setVisibility(8);
                       this.mZoomSeek.setProgress(0);
                       this.mZoomSeek.setEnabled(false);
                       return;
                            }
                        }
               this.mZoomSeekContainer.setVisibility(8);
                    }
                }
            }
