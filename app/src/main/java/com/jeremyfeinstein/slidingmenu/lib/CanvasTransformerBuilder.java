            package com.jeremyfeinstein.slidingmenu.lib;

            import android.graphics.Canvas;
            import android.view.animation.Interpolator;
            
            
     public class CanvasTransformerBuilder {
                private static Interpolator lin = new InterpolatorC12351();
                private SlidingMenu.CanvasTransformer mTrans;

         static  SlidingMenu.CanvasTransformer access$000(CanvasTransformerBuilder x0) {
             return x0.mTrans;
                }

                
        static class InterpolatorC12351 implements Interpolator {
            InterpolatorC12351() {
                    }

                    @Override 
            public float getInterpolation(float t) {
                return t;
                    }
                }

                
        class C12362 implements SlidingMenu.CanvasTransformer {
            C12362() {
                    }

                    @Override 
            public void transformCanvas(Canvas canvas, float percentOpen) {
                    }
                }

        private void initTransformer() {
            if (this.mTrans == null) {
                this.mTrans = new C12362();
                    }
                }

        public SlidingMenu.CanvasTransformer zoom(int openedX, int closedX, int openedY, int closedY, int px, int py) {
            return zoom(openedX, closedX, openedY, closedY, px, py, lin);
                }

                
        class C12373 implements SlidingMenu.CanvasTransformer {
                    final  int val$closedX;
                    final  int val$closedY;
                    final  Interpolator val$interp;
                    final  int val$openedX;
                    final  int val$openedY;
                    final  int val$px;
                    final  int val$py;

            C12373(Interpolator interpolator, int i, int i2, int i3, int i4, int i5, int i6) {
                        this.val$interp = interpolator;
                        this.val$openedX = i;
                        this.val$closedX = i2;
                        this.val$openedY = i3;
                        this.val$closedY = i4;
                        this.val$px = i5;
                        this.val$py = i6;
                    }

                    @Override 
            public void transformCanvas(Canvas canvas, float percentOpen) {
                CanvasTransformerBuilder.access$000(CanvasTransformerBuilder.this).transformCanvas(canvas, percentOpen);
                float f = this.val$interp.getInterpolation(percentOpen);
                canvas.scale(((this.val$openedX - this.val$closedX) * f) + this.val$closedX, ((this.val$openedY - this.val$closedY) * f) + this.val$closedY, this.val$px, this.val$py);
                    }
                }

        public SlidingMenu.CanvasTransformer zoom(int openedX, int closedX, int openedY, int closedY, int px, int py, Interpolator interp) {
            initTransformer();
            this.mTrans = new C12373(interp, openedX, closedX, openedY, closedY, px, py);
            return this.mTrans;
                }

        public SlidingMenu.CanvasTransformer rotate(int openedDeg, int closedDeg, int px, int py) {
            return rotate(openedDeg, closedDeg, px, py, lin);
                }

                
        class C12384 implements SlidingMenu.CanvasTransformer {
                    final  int val$closedDeg;
                    final  Interpolator val$interp;
                    final  int val$openedDeg;
                    final  int val$px;
                    final  int val$py;

            C12384(Interpolator interpolator, int i, int i2, int i3, int i4) {
                        this.val$interp = interpolator;
                        this.val$openedDeg = i;
                        this.val$closedDeg = i2;
                        this.val$px = i3;
                        this.val$py = i4;
                    }

                    @Override 
            public void transformCanvas(Canvas canvas, float percentOpen) {
                CanvasTransformerBuilder.access$000(CanvasTransformerBuilder.this).transformCanvas(canvas, percentOpen);
                float f = this.val$interp.getInterpolation(percentOpen);
                canvas.rotate(((this.val$openedDeg - this.val$closedDeg) * f) + this.val$closedDeg, this.val$px, this.val$py);
                    }
                }

        public SlidingMenu.CanvasTransformer rotate(int openedDeg, int closedDeg, int px, int py, Interpolator interp) {
            initTransformer();
            this.mTrans = new C12384(interp, openedDeg, closedDeg, px, py);
            return this.mTrans;
                }

        public SlidingMenu.CanvasTransformer translate(int openedX, int closedX, int openedY, int closedY) {
            return translate(openedX, closedX, openedY, closedY, lin);
                }

                
        class C12395 implements SlidingMenu.CanvasTransformer {
                    final  int val$closedX;
                    final  int val$closedY;
                    final  Interpolator val$interp;
                    final  int val$openedX;
                    final  int val$openedY;

            C12395(Interpolator interpolator, int i, int i2, int i3, int i4) {
                        this.val$interp = interpolator;
                        this.val$openedX = i;
                        this.val$closedX = i2;
                        this.val$openedY = i3;
                        this.val$closedY = i4;
                    }

                    @Override 
            public void transformCanvas(Canvas canvas, float percentOpen) {
                CanvasTransformerBuilder.access$000(CanvasTransformerBuilder.this).transformCanvas(canvas, percentOpen);
                float f = this.val$interp.getInterpolation(percentOpen);
                canvas.translate(((this.val$openedX - this.val$closedX) * f) + this.val$closedX, ((this.val$openedY - this.val$closedY) * f) + this.val$closedY);
                    }
                }

        public SlidingMenu.CanvasTransformer translate(int openedX, int closedX, int openedY, int closedY, Interpolator interp) {
            initTransformer();
            this.mTrans = new C12395(interp, openedX, closedX, openedY, closedY);
            return this.mTrans;
                }

                
        class C12406 implements SlidingMenu.CanvasTransformer {
                    final  SlidingMenu.CanvasTransformer val$t;

            C12406(SlidingMenu.CanvasTransformer canvasTransformer) {
                        this.val$t = canvasTransformer;
                    }

                    @Override 
            public void transformCanvas(Canvas canvas, float percentOpen) {
                CanvasTransformerBuilder.access$000(CanvasTransformerBuilder.this).transformCanvas(canvas, percentOpen);
                this.val$t.transformCanvas(canvas, percentOpen);
                    }
                }

        public SlidingMenu.CanvasTransformer concatTransformer(SlidingMenu.CanvasTransformer t) {
            initTransformer();
            this.mTrans = new C12406(t);
            return this.mTrans;
                }
            }
