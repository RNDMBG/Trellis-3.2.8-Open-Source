            package co.vine.android.views;

            import android.content.Context;
            import android.graphics.Bitmap;
            import android.graphics.Canvas;
            import android.graphics.MaskFilter;
            import android.graphics.Paint;
            import android.graphics.Path;
            import android.graphics.PorterDuff;
            import android.util.AttributeSet;
            import android.view.MotionEvent;
            import android.view.ScaleGestureDetector;
            import android.view.View;
            import co.vine.android.util.CrossConstants;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 42 */    public class DrawingView extends View implements View.OnTouchListener, ScaleGestureDetector.OnScaleGestureListener {
                private Bitmap mBitmap;
                public Paint mCurrentPaint;
                public final ArrayList<Point> mCurrentPoints;
                private boolean mDrawingEnabled;
                private boolean mEditEnabled;
                private final Paint mPaint;
                private final ArrayList<Paint> mPaints;
                private final ArrayList<Path> mPaths;
                private final ScaleGestureDetector mScaleDetector;
                private float mScaleFactor;

/* 43 */        public void setDrawingEnabled(boolean enabled) {
/* 44 */            this.mDrawingEnabled = enabled;
                }

/* 47 */        public void clear() {
/* 48 */            this.mPaints.clear();
/* 49 */            this.mPaths.clear();
/* 50 */            this.mCurrentPoints.clear();
/* 51 */            destroyDrawingCache();
/* 52 */            this.mBitmap = null;
/* 53 */            invalidate();
                }

/* 56 */        public void removeLast() {
/* 57 */            this.mCurrentPoints.clear();
/* 58 */            if (this.mPaints.size() > 0) {
/* 59 */                this.mPaths.remove(this.mPaths.size() - 1);
/* 60 */                this.mPaints.remove(this.mPaints.size() - 1);
                    }
/* 62 */            destroyDrawingCache();
/* 63 */            invalidate();
                }

/* 66 */        public void setEditEnabled(boolean enabled) {
/* 67 */            this.mEditEnabled = enabled;
                }

/* 70 */        public void restoreEditMatrix() {
/* 71 */            this.mScaleFactor = 1.0f;
                }

                @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
/* 75 */        public boolean onScale(ScaleGestureDetector detector) {
/* 76 */            this.mScaleFactor = detector.getScaleFactor();
/* 78 */            this.mScaleFactor = Math.max(0.1f, Math.min(this.mScaleFactor, 5.0f));
/* 79 */            invalidate();
/* 80 */            return true;
                }

                @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
/* 84 */        public boolean onScaleBegin(ScaleGestureDetector detector) {
/* 85 */            return false;
                }

                @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
/* 90 */        public void onScaleEnd(ScaleGestureDetector detector) {
                }

/* 93 */        public void saveCurrent() {
/* 94 */            this.mBitmap = getDrawingCache(false);
/* 95 */            this.mPaints.clear();
/* 96 */            this.mPaths.clear();
/* 97 */            this.mCurrentPoints.clear();
/* 98 */            restoreEditMatrix();
                }

/* 101 */       public boolean isDrawingEnabled() {
/* 102 */           return this.mDrawingEnabled;
                }

/* 109 */       public static class Point {

                    /* JADX INFO: renamed from: dx */
                    float f115dx;

                    /* JADX INFO: renamed from: dy */
                    float f116dy;

                    /* JADX INFO: renamed from: x */
                    float f117x;

                    /* JADX INFO: renamed from: y */
                    float f118y;

/* 110 */           public String toString() {
/* 111 */               return this.f117x + ", " + this.f118y;
                    }
                }

/* 115 */       public DrawingView(Context context) {
/* 116 */           super(context);
/* 25 */            this.mPaths = new ArrayList<>();
/* 26 */            this.mPaints = new ArrayList<>();
/* 28 */            this.mCurrentPoints = new ArrayList<>();
/* 30 */            this.mCurrentPaint = newPaint();
/* 32 */            this.mPaint = new Paint(1);
/* 34 */            this.mPaint.setFilterBitmap(true);
/* 35 */            this.mPaint.setDither(true);
/* 40 */            this.mScaleFactor = 1.0f;
/* 117 */           init();
/* 118 */           this.mScaleDetector = new ScaleGestureDetector(context, this);
                }

/* 121 */       public DrawingView(Context context, AttributeSet attrs) {
/* 122 */           super(context, attrs);
/* 25 */            this.mPaths = new ArrayList<>();
/* 26 */            this.mPaints = new ArrayList<>();
/* 28 */            this.mCurrentPoints = new ArrayList<>();
/* 30 */            this.mCurrentPaint = newPaint();
/* 32 */            this.mPaint = new Paint(1);
/* 34 */            this.mPaint.setFilterBitmap(true);
/* 35 */            this.mPaint.setDither(true);
/* 40 */            this.mScaleFactor = 1.0f;
/* 123 */           init();
/* 124 */           this.mScaleDetector = new ScaleGestureDetector(context, this);
                }

/* 127 */       public DrawingView(Context context, AttributeSet attrs, int defStyleAttr) {
/* 128 */           super(context, attrs, defStyleAttr);
/* 25 */            this.mPaths = new ArrayList<>();
/* 26 */            this.mPaints = new ArrayList<>();
/* 28 */            this.mCurrentPoints = new ArrayList<>();
/* 30 */            this.mCurrentPaint = newPaint();
/* 32 */            this.mPaint = new Paint(1);
/* 34 */            this.mPaint.setFilterBitmap(true);
/* 35 */            this.mPaint.setDither(true);
/* 40 */            this.mScaleFactor = 1.0f;
/* 129 */           init();
/* 130 */           this.mScaleDetector = new ScaleGestureDetector(context, this);
                }

/* 133 */       private void init() {
/* 134 */           setOnTouchListener(this);
/* 135 */           setDrawingCacheEnabled(true);
/* 136 */           setDrawingCacheQuality(1048576);
                }

/* 139 */       private Paint newPaint() {
/* 140 */           Paint paint = new Paint(0);
/* 141 */           paint.setFilterBitmap(true);
/* 142 */           paint.setStyle(Paint.Style.STROKE);
/* 143 */           paint.setStrokeJoin(Paint.Join.ROUND);
/* 144 */           paint.setStrokeCap(Paint.Cap.ROUND);
/* 145 */           paint.setStrokeWidth(15.0f);
/* 146 */           paint.setColor(CrossConstants.RES_VINE_GREEN);
/* 147 */           return paint;
                }

/* 150 */       public void setColor(int color) {
/* 151 */           this.mCurrentPaint.setColor(color);
                }

/* 154 */       public void setStrokeWidth(float width) {
/* 155 */           this.mCurrentPaint.setStrokeWidth(width);
                }

/* 159 */       public void setMaskFilter(MaskFilter filter) {
/* 160 */           this.mCurrentPaint.setMaskFilter(filter);
                }

                @Override // android.view.View
/* 163 */       public void onDraw(Canvas canvas) {
/* 164 */           super.onDraw(canvas);
/* 166 */           canvas.save();
/* 167 */           canvas.scale(this.mScaleFactor, this.mScaleFactor);
/* 169 */           canvas.drawColor(0, PorterDuff.Mode.CLEAR);
/* 170 */           int i = 0;
                    for (Path path : this.mPaths) {
/* 172 */               canvas.drawPath(path, this.mPaints.get(i));
/* 173 */               i++;
                    }
/* 175 */           if (this.mCurrentPoints.size() > 0) {
/* 176 */               canvas.drawPath(getPathFromCurrentPoints(), this.mCurrentPaint);
                    }
/* 178 */           canvas.restore();
/* 180 */           if (this.mBitmap != null) {
/* 181 */               canvas.drawBitmap(this.mBitmap, 0.0f, 0.0f, this.mPaint);
                    }
                }

                @Override // android.view.View.OnTouchListener
/* 186 */       public boolean onTouch(View v, MotionEvent event) {
/* 187 */           if (this.mDrawingEnabled) {
/* 188 */               processDrawTouch(event);
                        return true;
                    }
/* 191 */           if (this.mEditEnabled) {
/* 192 */               processEditTouch(event);
                        return true;
                    }
/* 195 */           return false;
                }

/* 199 */       private void processEditTouch(MotionEvent event) {
/* 200 */           this.mScaleDetector.onTouchEvent(event);
                }

/* 203 */       private void processDrawTouch(MotionEvent event) {
/* 204 */           if (event.getAction() == 0) {
/* 205 */               this.mCurrentPoints.clear();
                    }
/* 207 */           Point p = new Point();
/* 208 */           p.f117x = event.getX();
/* 209 */           p.f118y = event.getY();
/* 210 */           this.mCurrentPoints.add(p);
/* 211 */           if (event.getAction() == 1) {
/* 212 */               this.mPaths.add(getPathFromCurrentPoints());
/* 213 */               this.mPaints.add(this.mCurrentPaint);
/* 214 */               this.mCurrentPoints.clear();
/* 215 */               Paint paint = newPaint();
/* 216 */               paint.setStrokeWidth(this.mCurrentPaint.getStrokeWidth());
/* 217 */               paint.setColor(this.mCurrentPaint.getColor());
/* 218 */               this.mCurrentPaint = paint;
/* 219 */               destroyDrawingCache();
                    }
/* 221 */           invalidate();
                }

/* 224 */       private Path getPathFromCurrentPoints() {
/* 225 */           ArrayList<Point> points = this.mCurrentPoints;
/* 226 */           Path path = new Path();
/* 227 */           if (points.size() > 1) {
                        for (int i = points.size() - 2; i < points.size(); i++) {
/* 229 */                   if (i >= 0) {
/* 230 */                       Point point = points.get(i);
/* 231 */                       if (i == 0) {
/* 232 */                           Point next = points.get(i + 1);
/* 233 */                           point.f115dx = (next.f117x - point.f117x) / 3.0f;
/* 234 */                           point.f116dy = (next.f118y - point.f118y) / 3.0f;
                                } else if (i == points.size() - 1) {
/* 237 */                           Point prev = points.get(i - 1);
/* 238 */                           point.f115dx = (point.f117x - prev.f117x) / 3.0f;
/* 239 */                           point.f116dy = (point.f118y - prev.f118y) / 3.0f;
                                } else {
/* 242 */                           Point next2 = points.get(i + 1);
/* 243 */                           Point prev2 = points.get(i - 1);
/* 244 */                           point.f115dx = (next2.f117x - prev2.f117x) / 3.0f;
/* 245 */                           point.f116dy = (next2.f118y - prev2.f118y) / 3.0f;
                                }
                            }
                        }
                    }
/* 250 */           boolean first = true;
                    for (int i2 = 0; i2 < points.size(); i2++) {
/* 252 */               Point point2 = points.get(i2);
/* 253 */               if (first) {
/* 254 */                   first = false;
/* 255 */                   path.moveTo(point2.f117x, point2.f118y);
                        } else {
/* 258 */                   Point prev3 = points.get(i2 - 1);
/* 259 */                   path.cubicTo(prev3.f117x + prev3.f115dx, prev3.f118y + prev3.f116dy, point2.f117x - point2.f115dx, point2.f118y - point2.f116dy, point2.f117x, point2.f118y);
                        }
                    }
/* 262 */           return path;
                }
            }
