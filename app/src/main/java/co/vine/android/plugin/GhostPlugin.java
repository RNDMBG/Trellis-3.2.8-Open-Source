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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    public class GhostPlugin extends BaseToolPlugin<ToggleButton> {
                private CameraSetting mCameraSetting;
                private Bitmap mGhostBitmap;
                private Canvas mGhostCanvas;
                private VideoData mGhostFrame;
                private boolean mGhostModeEnabled;
                private WeakReferenceView mGhostView;
                private boolean mIsMessaging;

/* 32 */        static /* synthetic */ boolean access$000(GhostPlugin x0) {
/* 33 */            return x0.mIsMessaging;
                }

/* 32 */        static /* synthetic */ void access$100(GhostPlugin x0, boolean x1) {
/* 33 */            x0.updateGhostBitmap(x1);
                }

/* 32 */        static /* synthetic */ WeakReferenceView access$200(GhostPlugin x0) {
/* 33 */            return x0.mGhostView;
                }


/* 43 */        public GhostPlugin() {
/* 44 */            super("Ghost");
/* 35 */            this.mGhostModeEnabled = false;
/* 41 */            this.mIsMessaging = false;
                }

/* 50 */        private class OnGhostCheckedChangedListener implements CompoundButton.OnCheckedChangeListener {
                    private final int mColor;
                    private final GhostPlugin mPlugin;

/* 51 */            public OnGhostCheckedChangedListener(GhostPlugin plugin, int color) {
/* 53 */                this.mColor = color;
/* 54 */                this.mPlugin = plugin;
                    }

                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
/* 58 */            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
/* 59 */                ToggleButton child = this.mPlugin.getInflatedChild();
/* 60 */                if (child != null) {
/* 61 */                    if (isChecked) {
/* 62 */                        child.getBackground().setColorFilter(new PorterDuffColorFilter(this.mColor, PorterDuff.Mode.SRC_ATOP));
/* 63 */                        child.setAlpha(1.0f);
                            } else {
/* 65 */                        if (!GhostPlugin.access$000(GhostPlugin.this)) {
/* 66 */                            child.getBackground().setColorFilter(null);
                                }
/* 68 */                        child.setAlpha(0.35f);
                            }
                        }
                    }
                }

                @Override // co.vine.android.plugin.BaseToolPlugin
/* 75 */        protected ToggleButton onLayoutInflated(LinearLayout view, LayoutInflater inflater, Fragment fragment) {
/* 76 */            ViewGroup rootLayout = (ViewGroup) fragment.getView().findViewById(C0257R.id.root_layout);
/* 77 */            View ghostView = inflater.inflate(C0257R.layout.plugin_ghost_view, rootLayout, false);
/* 78 */            ghostView.setAlpha(0.35f);
/* 79 */            rootLayout.addView(ghostView);
/* 80 */            this.mGhostView = new WeakReferenceView(ghostView);
/* 81 */            ToggleButton button = (ToggleButton) inflater.inflate(C0257R.layout.plugin_tool_toggle, (ViewGroup) view, false);
/* 82 */            button.setText((CharSequence) null);
/* 83 */            button.setTextOn(null);
/* 84 */            button.setTextOff(null);
/* 85 */            button.setAlpha(0.35f);
/* 86 */            button.setBackgroundResource(C0257R.drawable.ic_ghost);
/* 87 */            button.setOnCheckedChangeListener(new OnGhostCheckedChangedListener(this, this.mSecondaryColor));
/* 88 */            return button;
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 92 */        public void onSessionSwapped() {
/* 93 */            this.mGhostFrame = null;
/* 94 */            invalidateGhostView();
/* 95 */            setGhostMode(false);
                }

/* 98 */        private void updateGhostBitmap(boolean updateForParent) {
/* 99 */            PictureConverter pictureConverter = ResourceService.getPictureConverter();
                    synchronized (pictureConverter.LOCK) {
                        try {
/* 101 */                   boolean hasChanged = pictureConverter.updateSettingsIfNeeded(this.mCameraSetting);
/* 102 */                   pictureConverter.giveMatrixNewValuesWithScaleIfDegreeHasChangedWithKnownConfigs(this.mCameraSetting.degrees, this.mCameraSetting.frontFacing);
/* 103 */                   if (this.mGhostFrame != null && pictureConverter.convert(this.mCameraSetting, this.mGhostFrame, hasChanged)) {
/* 104 */                       pictureConverter.draw(this.mGhostCanvas);
/* 105 */                       if (updateForParent) {
/* 106 */                           updateGhostBitmap(this.mGhostBitmap, true);
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 112 */       protected void initializeGhostBitmapsIfNeeded() {
/* 113 */           if (this.mGhostBitmap == null) {
/* 114 */               this.mGhostBitmap = RecordConfigUtils.createDefaultSizeBitmap();
/* 115 */               this.mGhostCanvas = new Canvas(this.mGhostBitmap);
                    }
                }

/* 119 */       public void invalidateGhostFrame() {
/* 120 */           this.mGhostFrame = null;
                }

/* 121 */       public class GhostUpdateRunnable implements Runnable {
/* 122 */           public GhostUpdateRunnable() {
                    }

                    @Override // java.lang.Runnable
/* 126 */           public void run() {
/* 127 */               GhostPlugin.this.initializeGhostBitmapsIfNeeded();
/* 128 */               GhostPlugin.access$100(GhostPlugin.this, true);
                    }
                }

/* 133 */       public void requestGhostDrawing(boolean synchronous) {
/* 134 */           if (this.mGhostFrame != null) {
/* 135 */               if (synchronous) {
/* 136 */                   initializeGhostBitmapsIfNeeded();
/* 137 */                   updateGhostBitmap(false);
                        } else {
/* 139 */                   new Thread(new GhostUpdateRunnable()).start();
                        }
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 146 */       public boolean onSetEditMode(boolean editing, boolean hasData) {
/* 147 */           if (hasData) {
/* 148 */               if (this.mGhostModeEnabled) {
/* 149 */                   setGhostMode(false);
                        }
/* 151 */               invalidateGhostView();
                    }
                    return false;
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 157 */       public void onStartEditMode() {
/* 158 */           setGhostMode(false);
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 162 */       public void onSegmentDataChanged(ArrayList<RecordSegment> editedSegments) {
/* 163 */           if (this.mGhostModeEnabled) {
/* 164 */               requestGhostDrawing(false);
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 170 */       public void onOfferLastFrame(Bitmap bitmap, CameraSetting cameraSetting) {
                    try {
/* 171 */               this.mGhostFrame = null;
/* 172 */               updateGhostBitmap(Bitmap.createBitmap(bitmap), false);
/* 173 */               this.mCameraSetting = cameraSetting;
                    } catch (OutOfMemoryError e) {
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 180 */       public void onOfferLastFrame(byte[] array, CameraSetting cameraSetting) {
/* 181 */           this.mGhostFrame = new VideoData(0L, array);
/* 182 */           this.mCameraSetting = cameraSetting;
                }

                @Override // android.view.View.OnClickListener
/* 186 */       public void onClick(View v) {
/* 187 */           BasicVineRecorder recorder = getRecorder();
/* 188 */           if (recorder != null) {
/* 189 */               FlurryUtils.trackGhostSwitchPressed(v);
/* 190 */               setGhostMode(!this.mGhostModeEnabled);
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 195 */       public void onStartDrafts() {
/* 196 */           ToggleButton child = getInflatedChild();
/* 197 */           if (child != null && child.isChecked()) {
/* 198 */               setGhostMode(false);
                    }
                }

/* 202 */       public boolean setGhostMode(boolean enable) {
/* 203 */           if (this.mGhostView.get() == null) {
                        return false;
                    }
/* 204 */           if (enable) {
/* 205 */               this.mGhostView.setVisibility(0);
                    } else {
/* 207 */               this.mGhostView.setVisibility(8);
                    }
/* 209 */           this.mGhostModeEnabled = enable;
/* 210 */           if (this.mGhostModeEnabled) {
/* 211 */               requestGhostDrawing(false);
                    } else {
/* 213 */               updateGhostBitmap(null, true);
                    }
/* 215 */           ToggleButton child = getInflatedChild();
/* 216 */           if (child != null) {
/* 217 */               child.setChecked(enable);
/* 221 */               return enable;
                    }
/* 221 */           return enable;
                }

/* 225 */       private void invalidateGhostView() {
                    ImageView view;
/* 226 */           BasicVineRecorder recorder = getRecorder();
/* 227 */           if (recorder != null) {
/* 228 */               RecordingFile file = recorder.getCurrentRecordingFile();
/* 229 */               ToggleButton button = getInflatedChild();
/* 230 */               if (file != null && recorder.hasEditedSegments() && button != null && this.mActivity != null && (view = (ImageView) this.mGhostView.get()) != null) {
/* 233 */                   view.setImageDrawable(new BitmapDrawable(this.mActivity.getResources(), file.getLastFramePath()));
                        }
/* 236 */               invalidateGhostFrame();
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.GhostPlugin$1 */
/* 240 */       class RunnableC04431 implements Runnable {
                    final /* synthetic */ Bitmap val$ghostBitmap;
                    final /* synthetic */ boolean val$makeVisible;

/* 241 */           RunnableC04431(Bitmap bitmap, boolean z) {
                        this.val$ghostBitmap = bitmap;
                        this.val$makeVisible = z;
                    }

                    @Override // java.lang.Runnable
/* 244 */           public void run() {
/* 245 */               ImageView view = (ImageView) GhostPlugin.access$200(GhostPlugin.this).get();
/* 246 */               if (view != null) {
/* 247 */                   if (this.val$ghostBitmap == null) {
/* 248 */                       view.setVisibility(8);
/* 265 */                       return;
                            }
/* 250 */                   BasicVineRecorder recorder = GhostPlugin.this.getRecorder();
/* 251 */                   if (GhostPlugin.this.mActivity != null && recorder != null) {
/* 252 */                       if (!recorder.hasEditedSegments()) {
/* 253 */                           view.setVisibility(8);
/* 265 */                           return;
                                }
/* 255 */                       if (this.val$makeVisible && view.getVisibility() != 0) {
/* 257 */                           view.setVisibility(0);
                                }
/* 260 */                       view.setImageDrawable(new BitmapDrawable(GhostPlugin.this.mActivity.getResources(), this.val$ghostBitmap));
                            }
                        }
                    }
                }

/* 240 */       public void updateGhostBitmap(Bitmap ghostBitmap, boolean makeVisible) {
/* 241 */           if (this.mActivity != null) {
/* 242 */               this.mActivity.runOnUiThread(new RunnableC04431(ghostBitmap, makeVisible));
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 271 */       public void setMessagingMode(boolean messagingMode) {
/* 272 */           super.setMessagingMode(messagingMode);
/* 273 */           this.mIsMessaging = messagingMode;
/* 274 */           ToggleButton button = getInflatedChild();
/* 275 */           if (messagingMode) {
/* 276 */               button.getBackground().setColorFilter(this.mSecondaryColor, PorterDuff.Mode.SRC_ATOP);
                    } else {
/* 278 */               button.getBackground().setColorFilter(null);
                    }
                }
            }
