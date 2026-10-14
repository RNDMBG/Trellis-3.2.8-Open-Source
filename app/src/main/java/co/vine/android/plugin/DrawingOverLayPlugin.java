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
            
            
    public class DrawingOverLayPlugin extends BaseToolPlugin<ImageButton> implements AbstractEncodingRunnable.Drawer {
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

        static  DrawingView access$000(DrawingOverLayPlugin x0) {
            return x0.mDrawingView;
                }

        static  View access$100(DrawingOverLayPlugin x0) {
            return x0.mEditTools;
                }

        static  int access$200(DrawingOverLayPlugin x0) {
            return x0.mCurrentSize;
                }

        static  int access$202(DrawingOverLayPlugin x0, int x1) {
            x0.mCurrentSize = x1;
                    return x1;
                }

        static  int[] access$300(DrawingOverLayPlugin x0) {
            return x0.mSizes;
                }

        static  int access$400(DrawingOverLayPlugin x0) {
            return x0.mCurrentColor;
                }

        static  int access$402(DrawingOverLayPlugin x0, int x1) {
            x0.mCurrentColor = x1;
                    return x1;
                }

        static  int[] access$500(DrawingOverLayPlugin x0) {
            return x0.mColors;
                }

        static  int access$600(DrawingOverLayPlugin x0) {
            return x0.mCurrentAdvanceValue;
                }

        static  int access$602(DrawingOverLayPlugin x0, int x1) {
            x0.mCurrentAdvanceValue = x1;
                    return x1;
                }

        static  int[] access$700(DrawingOverLayPlugin x0) {
            return x0.mAdvanceFrames;
                }


        public DrawingOverLayPlugin() {
            super("DrawingOverlay");
            this.mSizes = new int[]{5, 10, 15, 20, 25, 30, 35, 40, 45, 50};
            this.mAdvanceFrames = new int[]{1, 5, 15, 30};
            this.mCurrentColor = 0;
            this.mCurrentSize = 3;
            this.mCurrentAdvanceValue = 1;
            this.mColors = new int[Settings.PROFILE_BACKGROUND_COLORS.length + 1];
            this.mMatrix = new Matrix();
                }

                @Override 
        protected ImageButton onLayoutInflated(LinearLayout view, LayoutInflater inflater, Fragment fragment) {
            this.mButton = (ImageButton) inflater.inflate(C0257R.layout.plugin_overlay_image_button, (ViewGroup) view, false);
            this.mButton.setOnClickListener(this);
            this.mButton.setAlpha(0.35f);
            ViewGroup vg = (ViewGroup) fragment.getView();
            ViewGroup overlay = (ViewGroup) inflater.inflate(C0257R.layout.plugin_overlay_layout, vg, false);
            this.mDrawingView = (DrawingView) overlay.findViewById(C0257R.id.drawing_view);
            this.mDrawingView.setDrawingEnabled(false);
            this.mDrawingView.setEditEnabled(false);
            this.mDrawTools = overlay.findViewById(C0257R.id.drawTools);
            this.mDrawTools.setVisibility(8);
            this.mEditTools = overlay.findViewById(C0257R.id.editTools);
            this.mEditTools.setVisibility(8);
            this.mEditTools.findViewById(C0257R.id.save).setOnClickListener(new ViewOnClickListenerC04321());
            this.mEditTools.findViewById(C0257R.id.restore).setOnClickListener(new ViewOnClickListenerC04332());
            this.mColors[0] = fragment.getResources().getColor(C0257R.color.vine_green);
                    for (int i = 1; i < Settings.PROFILE_BACKGROUND_COLORS.length; i++) {
                this.mColors[i] = Settings.PROFILE_BACKGROUND_COLORS[i] | (-16777216);
                    }
            Button sizeButton = (Button) overlay.findViewById(C0257R.id.size);
            sizeButton.setText(String.valueOf(this.mSizes[this.mCurrentSize]));
            sizeButton.setOnClickListener(new ViewOnClickListenerC04343(sizeButton));
            Button colorButton = (Button) overlay.findViewById(C0257R.id.color);
           colorButton.setBackgroundColor(this.mColors[this.mCurrentColor]);
           colorButton.setOnClickListener(new ViewOnClickListenerC04354(colorButton));
           overlay.findViewById(C0257R.id.undo).setOnClickListener(new ViewOnClickListenerC04365());
           overlay.findViewById(C0257R.id.clear).setOnClickListener(new ViewOnClickListenerC04376());
           Button advanceButton = (Button) overlay.findViewById(C0257R.id.advance);
           advanceButton.setText("+" + this.mAdvanceFrames[this.mCurrentAdvanceValue]);
           advanceButton.setLongClickable(true);
           advanceButton.setOnLongClickListener(new ViewOnLongClickListenerC04387(advanceButton));
           advanceButton.setOnClickListener(new ViewOnClickListenerC04398());
           vg.addView(overlay);
           this.mPaint = new Paint(1);
           this.mPaint.setFilterBitmap(true);
           this.mPaint.setDither(true);
           return this.mButton;
                }

                
        class ViewOnClickListenerC04321 implements View.OnClickListener {
            ViewOnClickListenerC04321() {
                    }

                    @Override 
            public void onClick(View v) {
                DrawingOverLayPlugin.access$000(DrawingOverLayPlugin.this).saveCurrent();
                DrawingOverLayPlugin.access$000(DrawingOverLayPlugin.this).setEditEnabled(false);
                DrawingOverLayPlugin.access$100(DrawingOverLayPlugin.this).setVisibility(8);
                    }
                }

                
        class ViewOnClickListenerC04332 implements View.OnClickListener {
            ViewOnClickListenerC04332() {
                    }

                    @Override 
            public void onClick(View v) {
                DrawingOverLayPlugin.access$000(DrawingOverLayPlugin.this).restoreEditMatrix();
                    }
                }

                
        class ViewOnClickListenerC04343 implements View.OnClickListener {
                    final  Button val$sizeButton;

            ViewOnClickListenerC04343(Button button) {
                        this.val$sizeButton = button;
                    }

                    @Override 
            public void onClick(View v) {
                DrawingOverLayPlugin.access$202(DrawingOverLayPlugin.this, (DrawingOverLayPlugin.access$200(DrawingOverLayPlugin.this) + 1) % DrawingOverLayPlugin.access$300(DrawingOverLayPlugin.this).length);
                DrawingOverLayPlugin.access$000(DrawingOverLayPlugin.this).setStrokeWidth(DrawingOverLayPlugin.access$300(DrawingOverLayPlugin.this)[DrawingOverLayPlugin.access$200(DrawingOverLayPlugin.this)]);
                this.val$sizeButton.setText(String.valueOf(DrawingOverLayPlugin.access$300(DrawingOverLayPlugin.this)[DrawingOverLayPlugin.access$200(DrawingOverLayPlugin.this)]));
                    }
                }

                
        class ViewOnClickListenerC04354 implements View.OnClickListener {
                    final  Button val$colorButton;

           ViewOnClickListenerC04354(Button button) {
                        this.val$colorButton = button;
                    }

                    @Override 
           public void onClick(View v) {
               DrawingOverLayPlugin.access$402(DrawingOverLayPlugin.this, (DrawingOverLayPlugin.access$400(DrawingOverLayPlugin.this) + 1) % DrawingOverLayPlugin.access$500(DrawingOverLayPlugin.this).length);
               DrawingOverLayPlugin.access$000(DrawingOverLayPlugin.this).setColor(DrawingOverLayPlugin.access$500(DrawingOverLayPlugin.this)[DrawingOverLayPlugin.access$400(DrawingOverLayPlugin.this)]);
               this.val$colorButton.setBackgroundColor(DrawingOverLayPlugin.access$500(DrawingOverLayPlugin.this)[DrawingOverLayPlugin.access$400(DrawingOverLayPlugin.this)]);
                    }
                }

                
       class ViewOnClickListenerC04365 implements View.OnClickListener {
           ViewOnClickListenerC04365() {
                    }

                    @Override 
           public void onClick(View v) {
               DrawingOverLayPlugin.access$000(DrawingOverLayPlugin.this).removeLast();
                    }
                }

                
       class ViewOnClickListenerC04376 implements View.OnClickListener {
           ViewOnClickListenerC04376() {
                    }

                    @Override 
           public void onClick(View v) {
               DrawingOverLayPlugin.access$000(DrawingOverLayPlugin.this).clear();
                    }
                }

                
       class ViewOnLongClickListenerC04387 implements View.OnLongClickListener {
                    final  Button val$advanceButton;

           ViewOnLongClickListenerC04387(Button button) {
                        this.val$advanceButton = button;
                    }

                    @Override 
           public boolean onLongClick(View v) {
               DrawingOverLayPlugin.access$602(DrawingOverLayPlugin.this, (DrawingOverLayPlugin.access$600(DrawingOverLayPlugin.this) + 1) % DrawingOverLayPlugin.access$700(DrawingOverLayPlugin.this).length);
               this.val$advanceButton.setText("+" + DrawingOverLayPlugin.access$700(DrawingOverLayPlugin.this)[DrawingOverLayPlugin.access$600(DrawingOverLayPlugin.this)]);
               return true;
                    }
                }

                
       class ViewOnClickListenerC04398 implements View.OnClickListener {
           ViewOnClickListenerC04398() {
                    }

                    @Override 
           public void onClick(View v) {
               VineRecorder recorder = DrawingOverLayPlugin.this.getRecorder();
               if (recorder != null) {
                   recorder.doTime((DrawingOverLayPlugin.access$700(DrawingOverLayPlugin.this)[DrawingOverLayPlugin.access$600(DrawingOverLayPlugin.this)] * 30) - 5);
                        }
                    }
                }

                @Override 
       public void onClick(View v) {
           if (this.mDrawingView.isDrawingEnabled()) {
               this.mDrawingView.setDrawingEnabled(false);
               this.mDrawingView.buildDrawingCache(true);
               this.mDrawTools.setVisibility(8);
               this.mButton.setAlpha(0.35f);
               return;
                    }
           this.mDrawingView.setDrawingEnabled(true);
           this.mDrawingView.setEditEnabled(false);
           this.mDrawTools.setVisibility(0);
           this.mEditTools.setVisibility(8);
           this.mButton.setAlpha(1.0f);
                }

                @Override 
       public void draw(Canvas canvas, boolean frontFacing) {
           canvas.save();
           canvas.setMatrix(null);
           Bitmap drawingCache = this.mDrawingView.getDrawingCache(true);
           this.mMatrix.setScale((frontFacing ? -1 : 1) * (canvas.getWidth() / drawingCache.getWidth()), canvas.getHeight() / drawingCache.getHeight());
           if (frontFacing) {
               this.mMatrix.postTranslate(canvas.getWidth(), 0.0f);
                    }
           canvas.drawBitmap(drawingCache, this.mMatrix, this.mPaint);
           canvas.restore();
                }

                @Override 
       public void onAdjustingLayoutParamsComplete(int topMaskHeightPx, int size) {
           RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
           params.topMargin = topMaskHeightPx;
           this.mDrawingView.setLayoutParams(params);
                }
            }
