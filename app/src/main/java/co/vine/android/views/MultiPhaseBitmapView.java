            package co.vine.android.views;

            import android.animation.TimeInterpolator;
            import android.annotation.TargetApi;
            import android.content.Context;
            import android.graphics.Bitmap;
            import android.graphics.Canvas;
            import android.graphics.Matrix;
            import android.graphics.Paint;
            import android.util.AttributeSet;
            import android.view.View;
            import android.view.animation.DecelerateInterpolator;
            import com.edisonwang.android.slog.SLog;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 40 */    public class MultiPhaseBitmapView extends View implements Runnable {
                private static final int DISAPPEAR_TIME = 6355;
                private static final int FRAME_DELAY = 16;
                private static final DrawingPhase[] PHASES = {new DrawingPhase(0, 80, 120, new DecelerateInterpolator(), null), new DrawingPhase(115, 35, 100, new DecelerateInterpolator(), null), new DrawingPhase(240, 0, 100, new DecelerateInterpolator(), null)};
                private static final int PHASE_ONE_DURATION_MS = 115;
                private static final int PHASE_TWO_DURATION_MS = 240;
                private final ArrayList<DrawingObject> mDrawingObjects;
                private final ArrayList<DrawingState> mDrawingStates;
                private final Matrix mMatrix;
                private final Paint mPaint;
                private final ArrayList<DrawingState> mToRemove;

/* 41 */        private Paint initPaint() {
/* 42 */            Paint paint = new Paint();
/* 43 */            paint.setAntiAlias(true);
/* 44 */            paint.setFilterBitmap(true);
/* 45 */            return paint;
                }

/* 48 */        public MultiPhaseBitmapView(Context context) {
/* 49 */            super(context);
/* 35 */            this.mDrawingObjects = new ArrayList<>();
/* 36 */            this.mDrawingStates = new ArrayList<>();
/* 37 */            this.mToRemove = new ArrayList<>();
/* 39 */            this.mMatrix = new Matrix();
/* 50 */            this.mPaint = initPaint();
                }

/* 53 */        public MultiPhaseBitmapView(Context context, AttributeSet attrs) {
/* 54 */            super(context, attrs);
/* 35 */            this.mDrawingObjects = new ArrayList<>();
/* 36 */            this.mDrawingStates = new ArrayList<>();
/* 37 */            this.mToRemove = new ArrayList<>();
/* 39 */            this.mMatrix = new Matrix();
/* 55 */            this.mPaint = initPaint();
                }

/* 58 */        public MultiPhaseBitmapView(Context context, AttributeSet attrs, int defStyleAttr) {
/* 59 */            super(context, attrs, defStyleAttr);
/* 35 */            this.mDrawingObjects = new ArrayList<>();
/* 36 */            this.mDrawingStates = new ArrayList<>();
/* 37 */            this.mToRemove = new ArrayList<>();
/* 39 */            this.mMatrix = new Matrix();
/* 60 */            this.mPaint = initPaint();
                }

                @TargetApi(21)
/* 64 */        public MultiPhaseBitmapView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
/* 65 */            super(context, attrs, defStyleAttr, defStyleRes);
/* 35 */            this.mDrawingObjects = new ArrayList<>();
/* 36 */            this.mDrawingStates = new ArrayList<>();
/* 37 */            this.mToRemove = new ArrayList<>();
/* 39 */            this.mMatrix = new Matrix();
/* 66 */            this.mPaint = initPaint();
                }

/* 69 */        public void setBitmaps(Bitmap... bitmaps) {
                    for (Bitmap bitmap : bitmaps) {
/* 71 */                this.mDrawingObjects.add(new DrawingObject(bitmap, null));
                    }
                }

/* 75 */        public void add(boolean special, float x, float y, int angle) {
                    synchronized (this) {
                        try {
/* 77 */                    SLog.m467i("Adding X: {}, Y: {}, A: {}", Float.valueOf(x), Float.valueOf(y), Integer.valueOf(angle));
/* 78 */                    this.mDrawingStates.add(new DrawingState(special, System.currentTimeMillis(), x, y, angle, PHASES, 6355L, null));
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override // android.view.View
/* 84 */        protected void onDraw(Canvas canvas) {
                    float scale;
                    synchronized (this) {
                        try {
                            for (DrawingState state : this.mDrawingStates) {
/* 87 */                        DrawingObject drawingObject = null;
/* 88 */                        if (DrawingState.access$300(state) && DrawingState.access$400(state) + 1 < this.mDrawingObjects.size()) {
/* 89 */                            DrawingObject drawingObject2 = this.mDrawingObjects.get(DrawingState.access$400(state) + 1);
                                    drawingObject = drawingObject2;
                                } else if (this.mDrawingObjects.size() >= 1) {
/* 91 */                            DrawingObject drawingObject3 = this.mDrawingObjects.get(0);
                                    drawingObject = drawingObject3;
                                }
/* 93 */                        if (drawingObject != null) {
/* 94 */                            int alpha = (DrawingState.access$500(state) * 255) / 100;
/* 96 */                            if (DrawingState.access$300(state)) {
/* 97 */                                scale = DrawingState.access$600(state) / 30.0f;
                                    } else {
/* 99 */                                scale = DrawingState.access$600(state) / 100.0f;
                                    }
/* 101 */                           float offsetX = drawingObject.centerX * scale;
/* 102 */                           float offsetY = drawingObject.centerY * scale;
/* 103 */                           this.mMatrix.reset();
/* 104 */                           this.mMatrix.postScale(scale, scale);
/* 105 */                           this.mMatrix.postRotate(DrawingState.access$700(state), offsetX, offsetY);
/* 106 */                           this.mMatrix.postTranslate(DrawingState.access$800(state) - offsetX, DrawingState.access$900(state) - offsetY);
/* 107 */                           this.mPaint.setAlpha(alpha);
/* 108 */                           canvas.drawBitmap(drawingObject.bitmap, this.mMatrix, this.mPaint);
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 112 */           postDelayed(this, 16L);
                }

                @Override // java.lang.Runnable
/* 116 */       public void run() {
/* 117 */           long time = System.currentTimeMillis();
                    synchronized (this) {
                        try {
/* 119 */                   this.mToRemove.clear();
                            for (DrawingState state : this.mDrawingStates) {
/* 121 */                       state.updatePhase(time);
/* 122 */                       if (DrawingState.access$1000(state)) {
/* 123 */                           this.mToRemove.add(state);
                                }
                            }
/* 126 */                   this.mDrawingStates.removeAll(this.mToRemove);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 128 */           if (isShown()) {
/* 129 */               postInvalidate();
                    }
                }

/* 133 */       public int getCount() {
                    int size;
                    synchronized (this) {
                        try {
/* 135 */                   size = this.mDrawingStates.size();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return size;
                }

/* 139 */       public void reset() {
                    synchronized (this) {
                        try {
/* 141 */                   this.mToRemove.clear();
/* 142 */                   this.mDrawingStates.clear();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 144 */       private static class DrawingObject {
                    public final Bitmap bitmap;
                    public final int centerX;
                    public final int centerY;

/* 145 */           /* synthetic */ DrawingObject(Bitmap x0, Object x1) {
/* 146 */               this(x0);
                    }

/* 150 */           private DrawingObject(Bitmap image) {
/* 152 */               this.bitmap = image;
/* 153 */               this.centerX = this.bitmap.getWidth() / 2;
/* 154 */               this.centerY = this.bitmap.getHeight() / 2;
                    }
                }

/* 156 */       private static class DrawingPhase {
                    public final TimeInterpolator interpolator;
                    public final int opacity;
                    public final int size;
                    public final int start;

/* 157 */           /* synthetic */ DrawingPhase(int x0, int x1, int x2, TimeInterpolator x3, Object x4) {
/* 158 */               this(x0, x1, x2, x3);
                    }

/* 163 */           private DrawingPhase(int start, int opacity, int size, TimeInterpolator interpolator) {
/* 165 */               this.start = start;
/* 166 */               this.opacity = opacity;
/* 167 */               this.size = size;
/* 168 */               this.interpolator = interpolator;
                    }
                }

/* 170 */       private static class DrawingState {

                    /* JADX INFO: renamed from: mA */
                    private final int f121mA;
                    private final long mDisappearTime;
                    private boolean mGone;
                    private int mOpacity;
                    private int mPhase;
                    private final DrawingPhase[] mPhases;
                    private int mScale;
                    private final boolean mSpecial;
                    private final long mStartTime;

                    /* JADX INFO: renamed from: mX */
                    private final float f122mX;

                    /* JADX INFO: renamed from: mY */
                    private final float f123mY;

/* 171 */           /* synthetic */ DrawingState(boolean x0, long x1, float x2, float x3, int x4, DrawingPhase[] x5, long x6, Object x7) {
/* 172 */               this(x0, x1, x2, x3, x4, x5, x6);
                    }

/* 171 */           static /* synthetic */ boolean access$1000(DrawingState x0) {
/* 172 */               return x0.mGone;
                    }

/* 171 */           static /* synthetic */ boolean access$300(DrawingState x0) {
/* 172 */               return x0.mSpecial;
                    }

/* 171 */           static /* synthetic */ int access$400(DrawingState x0) {
/* 172 */               return x0.mPhase;
                    }

/* 171 */           static /* synthetic */ int access$500(DrawingState x0) {
/* 172 */               return x0.mOpacity;
                    }

/* 171 */           static /* synthetic */ int access$600(DrawingState x0) {
/* 172 */               return x0.mScale;
                    }

/* 171 */           static /* synthetic */ int access$700(DrawingState x0) {
/* 172 */               return x0.f121mA;
                    }

/* 171 */           static /* synthetic */ float access$800(DrawingState x0) {
/* 172 */               return x0.f122mX;
                    }

/* 171 */           static /* synthetic */ float access$900(DrawingState x0) {
/* 172 */               return x0.f123mY;
                    }

/* 185 */           private DrawingState(boolean special, long startTime, float x, float y, int angle, DrawingPhase[] phases, long disappearTime) {
/* 187 */               this.mSpecial = special;
/* 188 */               this.mStartTime = startTime;
/* 189 */               this.f122mX = x;
/* 190 */               this.f123mY = y;
/* 191 */               this.f121mA = angle;
/* 192 */               this.mPhases = phases;
/* 193 */               this.mDisappearTime = disappearTime;
                    }

/* 200 */           public void updatePhase(long time) {
                        boolean lastPhase;
                        long nextStart;
                        long lapsedTime;
/* 211 */               do {
/* 201 */                   lastPhase = this.mPhase >= this.mPhases.length + (-1);
/* 202 */                   nextStart = lastPhase ? this.mDisappearTime : this.mPhases[this.mPhase + 1].start;
/* 205 */                   lapsedTime = time - this.mStartTime;
                            if (lapsedTime < nextStart) {
                                break;
                            } else {
/* 207 */                       this.mPhase++;
                            }
/* 211 */               } while (!lastPhase);
/* 213 */               if (this.mPhase >= this.mPhases.length) {
/* 214 */                   this.mGone = true;
/* 226 */                   return;
                        }
/* 216 */               float timePercentage = (lapsedTime * 1.0f) / nextStart;
/* 217 */               float interp = this.mPhases[this.mPhase].interpolator.getInterpolation(timePercentage);
/* 218 */               if (this.mPhase == 0) {
/* 219 */                   this.mOpacity = (int) (this.mPhases[this.mPhase].opacity * interp);
/* 220 */                   this.mScale = ((int) ((this.mPhases[this.mPhase].size - 100) * interp)) + 100;
                        } else {
/* 222 */                   this.mOpacity = this.mPhases[this.mPhase - 1].opacity + ((int) ((this.mPhases[this.mPhase].opacity - this.mPhases[this.mPhase - 1].opacity) * interp));
/* 223 */                   this.mScale = this.mPhases[this.mPhase - 1].size + ((int) ((this.mPhases[this.mPhase].size - this.mPhases[this.mPhase - 1].size) * interp));
                        }
                    }
                }
            }
