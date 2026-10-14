            package co.vine.android.plugin;

            import android.graphics.Bitmap;
            import android.graphics.Canvas;
            import android.graphics.PorterDuff;
            import android.graphics.PorterDuffColorFilter;
            import android.graphics.drawable.BitmapDrawable;
            import android.support.v4.app.Fragment;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.CompoundButton;
            import android.widget.ImageView;
            import android.widget.LinearLayout;
            import android.widget.ToggleButton;
            import co.vine.android.C0257R;
            import co.vine.android.recorder.BasicVineRecorder;
            import co.vine.android.recorder.PictureConverter;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.recorder.RecordSegment;
            import co.vine.android.recorder.RecordingFile;
            import co.vine.android.recorder.camera.CameraSetting;
            import co.vine.android.recorder.video.VideoData;
            import co.vine.android.service.ResourceService;
            import co.vine.android.util.analytics.FlurryUtils;
            import java.util.ArrayList;
            
            
    public class GhostPlugin extends BaseToolPlugin<ToggleButton> {
                private CameraSetting mCameraSetting;
                private Bitmap mGhostBitmap;
                private Canvas mGhostCanvas;
                private VideoData mGhostFrame;
                private boolean mGhostModeEnabled;
                private WeakReferenceView mGhostView;
                private boolean mIsMessaging;

        static  boolean access$000(GhostPlugin x0) {
            return x0.mIsMessaging;
                }

        static  void access$100(GhostPlugin x0, boolean x1) {
            x0.updateGhostBitmap(x1);
                }

        static  WeakReferenceView access$200(GhostPlugin x0) {
            return x0.mGhostView;
                }


        public GhostPlugin() {
            super("Ghost");
            this.mGhostModeEnabled = false;
            this.mIsMessaging = false;
                }

        private class OnGhostCheckedChangedListener implements CompoundButton.OnCheckedChangeListener {
                    private final int mColor;
                    private final GhostPlugin mPlugin;

            public OnGhostCheckedChangedListener(GhostPlugin plugin, int color) {
                this.mColor = color;
                this.mPlugin = plugin;
                    }

                    @Override 
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ToggleButton child = this.mPlugin.getInflatedChild();
                if (child != null) {
                    if (isChecked) {
                        child.getBackground().setColorFilter(new PorterDuffColorFilter(this.mColor, PorterDuff.Mode.SRC_ATOP));
                        child.setAlpha(1.0f);
                            } else {
                        if (!GhostPlugin.access$000(GhostPlugin.this)) {
                            child.getBackground().setColorFilter(null);
                                }
                        child.setAlpha(0.35f);
                            }
                        }
                    }
                }

                @Override 
        protected ToggleButton onLayoutInflated(LinearLayout view, LayoutInflater inflater, Fragment fragment) {
            ViewGroup rootLayout = (ViewGroup) fragment.getView().findViewById(C0257R.id.root_layout);
            View ghostView = inflater.inflate(C0257R.layout.plugin_ghost_view, rootLayout, false);
            ghostView.setAlpha(0.35f);
            rootLayout.addView(ghostView);
            this.mGhostView = new WeakReferenceView(ghostView);
            ToggleButton button = (ToggleButton) inflater.inflate(C0257R.layout.plugin_tool_toggle, (ViewGroup) view, false);
            button.setText((CharSequence) null);
            button.setTextOn(null);
            button.setTextOff(null);
            button.setAlpha(0.35f);
            button.setBackgroundResource(C0257R.drawable.ic_ghost);
            button.setOnCheckedChangeListener(new OnGhostCheckedChangedListener(this, this.mSecondaryColor));
            return button;
                }

                @Override 
        public void onSessionSwapped() {
            this.mGhostFrame = null;
            invalidateGhostView();
            setGhostMode(false);
                }

        private void updateGhostBitmap(boolean updateForParent) {
            PictureConverter pictureConverter = ResourceService.getPictureConverter();
                    synchronized (pictureConverter.LOCK) {
                        try {
                   boolean hasChanged = pictureConverter.updateSettingsIfNeeded(this.mCameraSetting);
                   pictureConverter.giveMatrixNewValuesWithScaleIfDegreeHasChangedWithKnownConfigs(this.mCameraSetting.degrees, this.mCameraSetting.frontFacing);
                   if (this.mGhostFrame != null && pictureConverter.convert(this.mCameraSetting, this.mGhostFrame, hasChanged)) {
                       pictureConverter.draw(this.mGhostCanvas);
                       if (updateForParent) {
                           updateGhostBitmap(this.mGhostBitmap, true);
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       protected void initializeGhostBitmapsIfNeeded() {
           if (this.mGhostBitmap == null) {
               this.mGhostBitmap = RecordConfigUtils.createDefaultSizeBitmap();
               this.mGhostCanvas = new Canvas(this.mGhostBitmap);
                    }
                }

       public void invalidateGhostFrame() {
           this.mGhostFrame = null;
                }

       public class GhostUpdateRunnable implements Runnable {
           public GhostUpdateRunnable() {
                    }

                    @Override 
           public void run() {
               GhostPlugin.this.initializeGhostBitmapsIfNeeded();
               GhostPlugin.access$100(GhostPlugin.this, true);
                    }
                }

       public void requestGhostDrawing(boolean synchronous) {
           if (this.mGhostFrame != null) {
               if (synchronous) {
                   initializeGhostBitmapsIfNeeded();
                   updateGhostBitmap(false);
                        } else {
                   new Thread(new GhostUpdateRunnable()).start();
                        }
                    }
                }

                @Override 
       public boolean onSetEditMode(boolean editing, boolean hasData) {
           if (hasData) {
               if (this.mGhostModeEnabled) {
                   setGhostMode(false);
                        }
               invalidateGhostView();
                    }
                    return false;
                }

                @Override 
       public void onStartEditMode() {
           setGhostMode(false);
                }

                @Override 
       public void onSegmentDataChanged(ArrayList<RecordSegment> editedSegments) {
           if (this.mGhostModeEnabled) {
               requestGhostDrawing(false);
                    }
                }

                @Override 
       public void onOfferLastFrame(Bitmap bitmap, CameraSetting cameraSetting) {
                    try {
               this.mGhostFrame = null;
               updateGhostBitmap(Bitmap.createBitmap(bitmap), false);
               this.mCameraSetting = cameraSetting;
                    } catch (OutOfMemoryError e) {
                    }
                }

                @Override 
       public void onOfferLastFrame(byte[] array, CameraSetting cameraSetting) {
           this.mGhostFrame = new VideoData(0L, array);
           this.mCameraSetting = cameraSetting;
                }

                @Override 
       public void onClick(View v) {
           BasicVineRecorder recorder = getRecorder();
           if (recorder != null) {
               FlurryUtils.trackGhostSwitchPressed(v);
               setGhostMode(!this.mGhostModeEnabled);
                    }
                }

                @Override 
       public void onStartDrafts() {
           ToggleButton child = getInflatedChild();
           if (child != null && child.isChecked()) {
               setGhostMode(false);
                    }
                }

       public boolean setGhostMode(boolean enable) {
           if (this.mGhostView.get() == null) {
                        return false;
                    }
           if (enable) {
               this.mGhostView.setVisibility(0);
                    } else {
               this.mGhostView.setVisibility(8);
                    }
           this.mGhostModeEnabled = enable;
           if (this.mGhostModeEnabled) {
               requestGhostDrawing(false);
                    } else {
               updateGhostBitmap(null, true);
                    }
           ToggleButton child = getInflatedChild();
           if (child != null) {
               child.setChecked(enable);
               return enable;
                    }
           return enable;
                }

       private void invalidateGhostView() {
                    ImageView view;
           BasicVineRecorder recorder = getRecorder();
           if (recorder != null) {
               RecordingFile file = recorder.getCurrentRecordingFile();
               ToggleButton button = getInflatedChild();
               if (file != null && recorder.hasEditedSegments() && button != null && this.mActivity != null && (view = (ImageView) this.mGhostView.get()) != null) {
                   view.setImageDrawable(new BitmapDrawable(this.mActivity.getResources(), file.getLastFramePath()));
                        }
               invalidateGhostFrame();
                    }
                }

                
       class RunnableC04431 implements Runnable {
                    final  Bitmap val$ghostBitmap;
                    final  boolean val$makeVisible;

           RunnableC04431(Bitmap bitmap, boolean z) {
                        this.val$ghostBitmap = bitmap;
                        this.val$makeVisible = z;
                    }

                    @Override 
           public void run() {
               ImageView view = (ImageView) GhostPlugin.access$200(GhostPlugin.this).get();
               if (view != null) {
                   if (this.val$ghostBitmap == null) {
                       view.setVisibility(8);
                       return;
                            }
                   BasicVineRecorder recorder = GhostPlugin.this.getRecorder();
                   if (GhostPlugin.this.mActivity != null && recorder != null) {
                       if (!recorder.hasEditedSegments()) {
                           view.setVisibility(8);
                           return;
                                }
                       if (this.val$makeVisible && view.getVisibility() != 0) {
                           view.setVisibility(0);
                                }
                       view.setImageDrawable(new BitmapDrawable(GhostPlugin.this.mActivity.getResources(), this.val$ghostBitmap));
                            }
                        }
                    }
                }

       public void updateGhostBitmap(Bitmap ghostBitmap, boolean makeVisible) {
           if (this.mActivity != null) {
               this.mActivity.runOnUiThread(new RunnableC04431(ghostBitmap, makeVisible));
                    }
                }

                @Override 
       public void setMessagingMode(boolean messagingMode) {
           super.setMessagingMode(messagingMode);
           this.mIsMessaging = messagingMode;
           ToggleButton button = getInflatedChild();
           if (messagingMode) {
               button.getBackground().setColorFilter(this.mSecondaryColor, PorterDuff.Mode.SRC_ATOP);
                    } else {
               button.getBackground().setColorFilter(null);
                    }
                }
            }
