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
            
            
    public class DrawingView extends View implements View.OnTouchListener, ScaleGestureDetector.OnScaleGestureListener {
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

        public void setDrawingEnabled(boolean enabled) {
            this.mDrawingEnabled = enabled;
                }

        public void clear() {
            this.mPaints.clear();
            this.mPaths.clear();
            this.mCurrentPoints.clear();
            destroyDrawingCache();
            this.mBitmap = null;
            invalidate();
                }

        public void removeLast() {
            this.mCurrentPoints.clear();
            if (this.mPaints.size() > 0) {
                this.mPaths.remove(this.mPaths.size() - 1);
                this.mPaints.remove(this.mPaints.size() - 1);
                    }
            destroyDrawingCache();
            invalidate();
                }

        public void setEditEnabled(boolean enabled) {
            this.mEditEnabled = enabled;
                }

        public void restoreEditMatrix() {
            this.mScaleFactor = 1.0f;
                }

                @Override 
        public boolean onScale(ScaleGestureDetector detector) {
            this.mScaleFactor = detector.getScaleFactor();
            this.mScaleFactor = Math.max(0.1f, Math.min(this.mScaleFactor, 5.0f));
            invalidate();
            return true;
                }

                @Override 
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return false;
                }

                @Override 
        public void onScaleEnd(ScaleGestureDetector detector) {
                }

        public void saveCurrent() {
            this.mBitmap = getDrawingCache(false);
            this.mPaints.clear();
            this.mPaths.clear();
            this.mCurrentPoints.clear();
            restoreEditMatrix();
                }

       public boolean isDrawingEnabled() {
           return this.mDrawingEnabled;
                }

       public static class Point {

                    
                    float f115dx;

                    
                    float f116dy;

                    
                    float f117x;

                    
                    float f118y;

           public String toString() {
               return this.f117x + ", " + this.f118y;
                    }
                }

       public DrawingView(Context context) {
           super(context);
            this.mPaths = new ArrayList<>();
            this.mPaints = new ArrayList<>();
            this.mCurrentPoints = new ArrayList<>();
            this.mCurrentPaint = newPaint();
            this.mPaint = new Paint(1);
            this.mPaint.setFilterBitmap(true);
            this.mPaint.setDither(true);
            this.mScaleFactor = 1.0f;
           init();
           this.mScaleDetector = new ScaleGestureDetector(context, this);
                }

       public DrawingView(Context context, AttributeSet attrs) {
           super(context, attrs);
            this.mPaths = new ArrayList<>();
            this.mPaints = new ArrayList<>();
            this.mCurrentPoints = new ArrayList<>();
            this.mCurrentPaint = newPaint();
            this.mPaint = new Paint(1);
            this.mPaint.setFilterBitmap(true);
            this.mPaint.setDither(true);
            this.mScaleFactor = 1.0f;
           init();
           this.mScaleDetector = new ScaleGestureDetector(context, this);
                }

       public DrawingView(Context context, AttributeSet attrs, int defStyleAttr) {
           super(context, attrs, defStyleAttr);
            this.mPaths = new ArrayList<>();
            this.mPaints = new ArrayList<>();
            this.mCurrentPoints = new ArrayList<>();
            this.mCurrentPaint = newPaint();
            this.mPaint = new Paint(1);
            this.mPaint.setFilterBitmap(true);
            this.mPaint.setDither(true);
            this.mScaleFactor = 1.0f;
           init();
           this.mScaleDetector = new ScaleGestureDetector(context, this);
                }

       private void init() {
           setOnTouchListener(this);
           setDrawingCacheEnabled(true);
           setDrawingCacheQuality(1048576);
                }

       private Paint newPaint() {
           Paint paint = new Paint(0);
           paint.setFilterBitmap(true);
           paint.setStyle(Paint.Style.STROKE);
           paint.setStrokeJoin(Paint.Join.ROUND);
           paint.setStrokeCap(Paint.Cap.ROUND);
           paint.setStrokeWidth(15.0f);
           paint.setColor(CrossConstants.RES_VINE_GREEN);
           return paint;
                }

       public void setColor(int color) {
           this.mCurrentPaint.setColor(color);
                }

       public void setStrokeWidth(float width) {
           this.mCurrentPaint.setStrokeWidth(width);
                }

       public void setMaskFilter(MaskFilter filter) {
           this.mCurrentPaint.setMaskFilter(filter);
                }

                @Override 
       public void onDraw(Canvas canvas) {
           super.onDraw(canvas);
           canvas.save();
           canvas.scale(this.mScaleFactor, this.mScaleFactor);
           canvas.drawColor(0, PorterDuff.Mode.CLEAR);
           int i = 0;
                    for (Path path : this.mPaths) {
               canvas.drawPath(path, this.mPaints.get(i));
               i++;
                    }
           if (this.mCurrentPoints.size() > 0) {
               canvas.drawPath(getPathFromCurrentPoints(), this.mCurrentPaint);
                    }
           canvas.restore();
           if (this.mBitmap != null) {
               canvas.drawBitmap(this.mBitmap, 0.0f, 0.0f, this.mPaint);
                    }
                }

                @Override 
       public boolean onTouch(View v, MotionEvent event) {
           if (this.mDrawingEnabled) {
               processDrawTouch(event);
                        return true;
                    }
           if (this.mEditEnabled) {
               processEditTouch(event);
                        return true;
                    }
           return false;
                }

       private void processEditTouch(MotionEvent event) {
           this.mScaleDetector.onTouchEvent(event);
                }

       private void processDrawTouch(MotionEvent event) {
           if (event.getAction() == 0) {
               this.mCurrentPoints.clear();
                    }
           Point p = new Point();
           p.f117x = event.getX();
           p.f118y = event.getY();
           this.mCurrentPoints.add(p);
           if (event.getAction() == 1) {
               this.mPaths.add(getPathFromCurrentPoints());
               this.mPaints.add(this.mCurrentPaint);
               this.mCurrentPoints.clear();
               Paint paint = newPaint();
               paint.setStrokeWidth(this.mCurrentPaint.getStrokeWidth());
               paint.setColor(this.mCurrentPaint.getColor());
               this.mCurrentPaint = paint;
               destroyDrawingCache();
                    }
           invalidate();
                }

       private Path getPathFromCurrentPoints() {
           ArrayList<Point> points = this.mCurrentPoints;
           Path path = new Path();
           if (points.size() > 1) {
                        for (int i = points.size() - 2; i < points.size(); i++) {
                   if (i >= 0) {
                       Point point = points.get(i);
                       if (i == 0) {
                           Point next = points.get(i + 1);
                           point.f115dx = (next.f117x - point.f117x) / 3.0f;
                           point.f116dy = (next.f118y - point.f118y) / 3.0f;
                                } else if (i == points.size() - 1) {
                           Point prev = points.get(i - 1);
                           point.f115dx = (point.f117x - prev.f117x) / 3.0f;
                           point.f116dy = (point.f118y - prev.f118y) / 3.0f;
                                } else {
                           Point next2 = points.get(i + 1);
                           Point prev2 = points.get(i - 1);
                           point.f115dx = (next2.f117x - prev2.f117x) / 3.0f;
                           point.f116dy = (next2.f118y - prev2.f118y) / 3.0f;
                                }
                            }
                        }
                    }
           boolean first = true;
                    for (int i2 = 0; i2 < points.size(); i2++) {
               Point point2 = points.get(i2);
               if (first) {
                   first = false;
                   path.moveTo(point2.f117x, point2.f118y);
                        } else {
                   Point prev3 = points.get(i2 - 1);
                   path.cubicTo(prev3.f117x + prev3.f115dx, prev3.f118y + prev3.f116dy, point2.f117x - point2.f115dx, point2.f118y - point2.f116dy, point2.f117x, point2.f118y);
                        }
                    }
           return path;
                }
            }
