            package co.vine.android.plugin;

            import android.graphics.Bitmap;
            import android.graphics.Canvas;
            import android.graphics.Matrix;
            import android.graphics.Paint;
            import android.support.v4.app.Fragment;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.Button;
            import android.widget.ImageButton;
            import android.widget.LinearLayout;
            import android.widget.RelativeLayout;
            import co.vine.android.C0257R;
            import co.vine.android.Settings;
            import co.vine.android.recorder.AbstractEncodingRunnable;
            import co.vine.android.recorder.VineRecorder;
            import co.vine.android.views.DrawingView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public class DrawingOverLayPlugin extends BaseToolPlugin<ImageButton> implements AbstractEncodingRunnable.Drawer {
                private final int[] mAdvanceFrames;
                private ImageButton mButton;
                private final int[] mColors;
                private int mCurrentAdvanceValue;
                private int mCurrentColor;
                private int mCurrentSize;
                private View mDrawTools;
                private DrawingView mDrawingView;
                private View mEditTools;
                private final Matrix mMatrix;
                private Paint mPaint;
                private final int[] mSizes;

/* 25 */        static /* synthetic */ DrawingView access$000(DrawingOverLayPlugin x0) {
/* 26 */            return x0.mDrawingView;
                }

/* 25 */        static /* synthetic */ View access$100(DrawingOverLayPlugin x0) {
/* 26 */            return x0.mEditTools;
                }

/* 25 */        static /* synthetic */ int access$200(DrawingOverLayPlugin x0) {
/* 26 */            return x0.mCurrentSize;
                }

/* 25 */        static /* synthetic */ int access$202(DrawingOverLayPlugin x0, int x1) {
/* 26 */            x0.mCurrentSize = x1;
                    return x1;
                }

/* 25 */        static /* synthetic */ int[] access$300(DrawingOverLayPlugin x0) {
/* 26 */            return x0.mSizes;
                }

/* 25 */        static /* synthetic */ int access$400(DrawingOverLayPlugin x0) {
/* 26 */            return x0.mCurrentColor;
                }

/* 25 */        static /* synthetic */ int access$402(DrawingOverLayPlugin x0, int x1) {
/* 26 */            x0.mCurrentColor = x1;
                    return x1;
                }

/* 25 */        static /* synthetic */ int[] access$500(DrawingOverLayPlugin x0) {
/* 26 */            return x0.mColors;
                }

/* 25 */        static /* synthetic */ int access$600(DrawingOverLayPlugin x0) {
/* 26 */            return x0.mCurrentAdvanceValue;
                }

/* 25 */        static /* synthetic */ int access$602(DrawingOverLayPlugin x0, int x1) {
/* 26 */            x0.mCurrentAdvanceValue = x1;
                    return x1;
                }

/* 25 */        static /* synthetic */ int[] access$700(DrawingOverLayPlugin x0) {
/* 26 */            return x0.mAdvanceFrames;
                }


/* 43 */        public DrawingOverLayPlugin() {
/* 44 */            super("DrawingOverlay");
/* 31 */            this.mSizes = new int[]{5, 10, 15, 20, 25, 30, 35, 40, 45, 50};
/* 32 */            this.mAdvanceFrames = new int[]{1, 5, 15, 30};
/* 33 */            this.mCurrentColor = 0;
/* 34 */            this.mCurrentSize = 3;
/* 35 */            this.mCurrentAdvanceValue = 1;
/* 45 */            this.mColors = new int[Settings.PROFILE_BACKGROUND_COLORS.length + 1];
/* 46 */            this.mMatrix = new Matrix();
                }

                @Override // co.vine.android.plugin.BaseToolPlugin
/* 50 */        protected ImageButton onLayoutInflated(LinearLayout view, LayoutInflater inflater, Fragment fragment) {
/* 51 */            this.mButton = (ImageButton) inflater.inflate(C0257R.layout.plugin_overlay_image_button, (ViewGroup) view, false);
/* 52 */            this.mButton.setOnClickListener(this);
/* 53 */            this.mButton.setAlpha(0.35f);
/* 55 */            ViewGroup vg = (ViewGroup) fragment.getView();
/* 56 */            ViewGroup overlay = (ViewGroup) inflater.inflate(C0257R.layout.plugin_overlay_layout, vg, false);
/* 57 */            this.mDrawingView = (DrawingView) overlay.findViewById(C0257R.id.drawing_view);
/* 58 */            this.mDrawingView.setDrawingEnabled(false);
/* 59 */            this.mDrawingView.setEditEnabled(false);
/* 61 */            this.mDrawTools = overlay.findViewById(C0257R.id.drawTools);
/* 62 */            this.mDrawTools.setVisibility(8);
/* 64 */            this.mEditTools = overlay.findViewById(C0257R.id.editTools);
/* 65 */            this.mEditTools.setVisibility(8);
/* 66 */            this.mEditTools.findViewById(C0257R.id.save).setOnClickListener(new ViewOnClickListenerC04321());
/* 76 */            this.mEditTools.findViewById(C0257R.id.restore).setOnClickListener(new ViewOnClickListenerC04332());
/* 83 */            this.mColors[0] = fragment.getResources().getColor(C0257R.color.vine_green);
                    for (int i = 1; i < Settings.PROFILE_BACKGROUND_COLORS.length; i++) {
/* 85 */                this.mColors[i] = Settings.PROFILE_BACKGROUND_COLORS[i] | (-16777216);
                    }
/* 88 */            Button sizeButton = (Button) overlay.findViewById(C0257R.id.size);
/* 89 */            sizeButton.setText(String.valueOf(this.mSizes[this.mCurrentSize]));
/* 90 */            sizeButton.setOnClickListener(new ViewOnClickListenerC04343(sizeButton));
/* 99 */            Button colorButton = (Button) overlay.findViewById(C0257R.id.color);
/* 100 */           colorButton.setBackgroundColor(this.mColors[this.mCurrentColor]);
/* 101 */           colorButton.setOnClickListener(new ViewOnClickListenerC04354(colorButton));
/* 110 */           overlay.findViewById(C0257R.id.undo).setOnClickListener(new ViewOnClickListenerC04365());
/* 117 */           overlay.findViewById(C0257R.id.clear).setOnClickListener(new ViewOnClickListenerC04376());
/* 124 */           Button advanceButton = (Button) overlay.findViewById(C0257R.id.advance);
/* 126 */           advanceButton.setText("+" + this.mAdvanceFrames[this.mCurrentAdvanceValue]);
/* 128 */           advanceButton.setLongClickable(true);
/* 130 */           advanceButton.setOnLongClickListener(new ViewOnLongClickListenerC04387(advanceButton));
/* 139 */           advanceButton.setOnClickListener(new ViewOnClickListenerC04398());
/* 149 */           vg.addView(overlay);
/* 151 */           this.mPaint = new Paint(1);
/* 152 */           this.mPaint.setFilterBitmap(true);
/* 153 */           this.mPaint.setDither(true);
/* 155 */           return this.mButton;
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.DrawingOverLayPlugin$1 */
/* 64 */        class ViewOnClickListenerC04321 implements View.OnClickListener {
/* 65 */            ViewOnClickListenerC04321() {
                    }

                    @Override // android.view.View.OnClickListener
/* 69 */            public void onClick(View v) {
/* 70 */                DrawingOverLayPlugin.access$000(DrawingOverLayPlugin.this).saveCurrent();
/* 71 */                DrawingOverLayPlugin.access$000(DrawingOverLayPlugin.this).setEditEnabled(false);
/* 72 */                DrawingOverLayPlugin.access$100(DrawingOverLayPlugin.this).setVisibility(8);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.DrawingOverLayPlugin$2 */
/* 74 */        class ViewOnClickListenerC04332 implements View.OnClickListener {
/* 75 */            ViewOnClickListenerC04332() {
                    }

                    @Override // android.view.View.OnClickListener
/* 78 */            public void onClick(View v) {
/* 79 */                DrawingOverLayPlugin.access$000(DrawingOverLayPlugin.this).restoreEditMatrix();
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.DrawingOverLayPlugin$3 */
/* 88 */        class ViewOnClickListenerC04343 implements View.OnClickListener {
                    final /* synthetic */ Button val$sizeButton;

/* 89 */            ViewOnClickListenerC04343(Button button) {
                        this.val$sizeButton = button;
                    }

                    @Override // android.view.View.OnClickListener
/* 92 */            public void onClick(View v) {
/* 93 */                DrawingOverLayPlugin.access$202(DrawingOverLayPlugin.this, (DrawingOverLayPlugin.access$200(DrawingOverLayPlugin.this) + 1) % DrawingOverLayPlugin.access$300(DrawingOverLayPlugin.this).length);
/* 94 */                DrawingOverLayPlugin.access$000(DrawingOverLayPlugin.this).setStrokeWidth(DrawingOverLayPlugin.access$300(DrawingOverLayPlugin.this)[DrawingOverLayPlugin.access$200(DrawingOverLayPlugin.this)]);
/* 95 */                this.val$sizeButton.setText(String.valueOf(DrawingOverLayPlugin.access$300(DrawingOverLayPlugin.this)[DrawingOverLayPlugin.access$200(DrawingOverLayPlugin.this)]));
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.DrawingOverLayPlugin$4 */
/* 99 */        class ViewOnClickListenerC04354 implements View.OnClickListener {
                    final /* synthetic */ Button val$colorButton;

/* 100 */           ViewOnClickListenerC04354(Button button) {
                        this.val$colorButton = button;
                    }

                    @Override // android.view.View.OnClickListener
/* 103 */           public void onClick(View v) {
/* 104 */               DrawingOverLayPlugin.access$402(DrawingOverLayPlugin.this, (DrawingOverLayPlugin.access$400(DrawingOverLayPlugin.this) + 1) % DrawingOverLayPlugin.access$500(DrawingOverLayPlugin.this).length);
/* 105 */               DrawingOverLayPlugin.access$000(DrawingOverLayPlugin.this).setColor(DrawingOverLayPlugin.access$500(DrawingOverLayPlugin.this)[DrawingOverLayPlugin.access$400(DrawingOverLayPlugin.this)]);
/* 106 */               this.val$colorButton.setBackgroundColor(DrawingOverLayPlugin.access$500(DrawingOverLayPlugin.this)[DrawingOverLayPlugin.access$400(DrawingOverLayPlugin.this)]);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.DrawingOverLayPlugin$5 */
/* 108 */       class ViewOnClickListenerC04365 implements View.OnClickListener {
/* 109 */           ViewOnClickListenerC04365() {
                    }

                    @Override // android.view.View.OnClickListener
/* 112 */           public void onClick(View v) {
/* 113 */               DrawingOverLayPlugin.access$000(DrawingOverLayPlugin.this).removeLast();
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.DrawingOverLayPlugin$6 */
/* 115 */       class ViewOnClickListenerC04376 implements View.OnClickListener {
/* 116 */           ViewOnClickListenerC04376() {
                    }

                    @Override // android.view.View.OnClickListener
/* 119 */           public void onClick(View v) {
/* 120 */               DrawingOverLayPlugin.access$000(DrawingOverLayPlugin.this).clear();
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.DrawingOverLayPlugin$7 */
/* 128 */       class ViewOnLongClickListenerC04387 implements View.OnLongClickListener {
                    final /* synthetic */ Button val$advanceButton;

/* 129 */           ViewOnLongClickListenerC04387(Button button) {
                        this.val$advanceButton = button;
                    }

                    @Override // android.view.View.OnLongClickListener
/* 132 */           public boolean onLongClick(View v) {
/* 133 */               DrawingOverLayPlugin.access$602(DrawingOverLayPlugin.this, (DrawingOverLayPlugin.access$600(DrawingOverLayPlugin.this) + 1) % DrawingOverLayPlugin.access$700(DrawingOverLayPlugin.this).length);
/* 134 */               this.val$advanceButton.setText("+" + DrawingOverLayPlugin.access$700(DrawingOverLayPlugin.this)[DrawingOverLayPlugin.access$600(DrawingOverLayPlugin.this)]);
/* 135 */               return true;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.DrawingOverLayPlugin$8 */
/* 137 */       class ViewOnClickListenerC04398 implements View.OnClickListener {
/* 138 */           ViewOnClickListenerC04398() {
                    }

                    @Override // android.view.View.OnClickListener
/* 141 */           public void onClick(View v) {
/* 142 */               VineRecorder recorder = DrawingOverLayPlugin.this.getRecorder();
/* 143 */               if (recorder != null) {
/* 144 */                   recorder.doTime((DrawingOverLayPlugin.access$700(DrawingOverLayPlugin.this)[DrawingOverLayPlugin.access$600(DrawingOverLayPlugin.this)] * 30) - 5);
                        }
                    }
                }

                @Override // android.view.View.OnClickListener
/* 159 */       public void onClick(View v) {
/* 160 */           if (this.mDrawingView.isDrawingEnabled()) {
/* 161 */               this.mDrawingView.setDrawingEnabled(false);
/* 162 */               this.mDrawingView.buildDrawingCache(true);
/* 163 */               this.mDrawTools.setVisibility(8);
/* 164 */               this.mButton.setAlpha(0.35f);
/* 172 */               return;
                    }
/* 166 */           this.mDrawingView.setDrawingEnabled(true);
/* 167 */           this.mDrawingView.setEditEnabled(false);
/* 168 */           this.mDrawTools.setVisibility(0);
/* 169 */           this.mEditTools.setVisibility(8);
/* 170 */           this.mButton.setAlpha(1.0f);
                }

                @Override // co.vine.android.recorder.AbstractEncodingRunnable.Drawer
/* 175 */       public void draw(Canvas canvas, boolean frontFacing) {
/* 176 */           canvas.save();
/* 177 */           canvas.setMatrix(null);
/* 178 */           Bitmap drawingCache = this.mDrawingView.getDrawingCache(true);
/* 179 */           this.mMatrix.setScale((frontFacing ? -1 : 1) * (canvas.getWidth() / drawingCache.getWidth()), canvas.getHeight() / drawingCache.getHeight());
/* 180 */           if (frontFacing) {
/* 181 */               this.mMatrix.postTranslate(canvas.getWidth(), 0.0f);
                    }
/* 183 */           canvas.drawBitmap(drawingCache, this.mMatrix, this.mPaint);
/* 184 */           canvas.restore();
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 188 */       public void onAdjustingLayoutParamsComplete(int topMaskHeightPx, int size) {
/* 189 */           RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
/* 190 */           params.topMargin = topMaskHeightPx;
/* 191 */           this.mDrawingView.setLayoutParams(params);
                }
            }
