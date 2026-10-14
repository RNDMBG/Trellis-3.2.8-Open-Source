            package com.jeremyfeinstein.slidingmenu.lib;

            import android.graphics.Canvas;
            import android.view.animation.Interpolator;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 6 */     public class CanvasTransformerBuilder {
                private static Interpolator lin = new InterpolatorC12351();
                private SlidingMenu.CanvasTransformer mTrans;

/* 7 */         static /* synthetic */ SlidingMenu.CanvasTransformer access$000(CanvasTransformerBuilder x0) {
/* 8 */             return x0.mTrans;
                }

                /* JADX INFO: renamed from: com.jeremyfeinstein.slidingmenu.lib.CanvasTransformerBuilder$1 */
/* 10 */        static class InterpolatorC12351 implements Interpolator {
/* 11 */            InterpolatorC12351() {
                    }

                    @Override // android.animation.TimeInterpolator
/* 13 */            public float getInterpolation(float t) {
/* 14 */                return t;
                    }
                }

                /* JADX INFO: renamed from: com.jeremyfeinstein.slidingmenu.lib.CanvasTransformerBuilder$2 */
/* 18 */        class C12362 implements SlidingMenu.CanvasTransformer {
/* 19 */            C12362() {
                    }

                    @Override // com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer
/* 20 */            public void transformCanvas(Canvas canvas, float percentOpen) {
                    }
                }

/* 18 */        private void initTransformer() {
/* 19 */            if (this.mTrans == null) {
/* 20 */                this.mTrans = new C12362();
                    }
                }

/* 27 */        public SlidingMenu.CanvasTransformer zoom(int openedX, int closedX, int openedY, int closedY, int px, int py) {
/* 28 */            return zoom(openedX, closedX, openedY, closedY, px, py, lin);
                }

                /* JADX INFO: renamed from: com.jeremyfeinstein.slidingmenu.lib.CanvasTransformerBuilder$3 */
/* 33 */        class C12373 implements SlidingMenu.CanvasTransformer {
                    final /* synthetic */ int val$closedX;
                    final /* synthetic */ int val$closedY;
                    final /* synthetic */ Interpolator val$interp;
                    final /* synthetic */ int val$openedX;
                    final /* synthetic */ int val$openedY;
                    final /* synthetic */ int val$px;
                    final /* synthetic */ int val$py;

/* 34 */            C12373(Interpolator interpolator, int i, int i2, int i3, int i4, int i5, int i6) {
                        this.val$interp = interpolator;
                        this.val$openedX = i;
                        this.val$closedX = i2;
                        this.val$openedY = i3;
                        this.val$closedY = i4;
                        this.val$px = i5;
                        this.val$py = i6;
                    }

                    @Override // com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer
/* 36 */            public void transformCanvas(Canvas canvas, float percentOpen) {
/* 37 */                CanvasTransformerBuilder.access$000(CanvasTransformerBuilder.this).transformCanvas(canvas, percentOpen);
/* 38 */                float f = this.val$interp.getInterpolation(percentOpen);
/* 39 */                canvas.scale(((this.val$openedX - this.val$closedX) * f) + this.val$closedX, ((this.val$openedY - this.val$closedY) * f) + this.val$closedY, this.val$px, this.val$py);
                    }
                }

/* 33 */        public SlidingMenu.CanvasTransformer zoom(int openedX, int closedX, int openedY, int closedY, int px, int py, Interpolator interp) {
/* 34 */            initTransformer();
/* 35 */            this.mTrans = new C12373(interp, openedX, closedX, openedY, closedY, px, py);
/* 43 */            return this.mTrans;
                }

/* 47 */        public SlidingMenu.CanvasTransformer rotate(int openedDeg, int closedDeg, int px, int py) {
/* 48 */            return rotate(openedDeg, closedDeg, px, py, lin);
                }

                /* JADX INFO: renamed from: com.jeremyfeinstein.slidingmenu.lib.CanvasTransformerBuilder$4 */
/* 52 */        class C12384 implements SlidingMenu.CanvasTransformer {
                    final /* synthetic */ int val$closedDeg;
                    final /* synthetic */ Interpolator val$interp;
                    final /* synthetic */ int val$openedDeg;
                    final /* synthetic */ int val$px;
                    final /* synthetic */ int val$py;

/* 53 */            C12384(Interpolator interpolator, int i, int i2, int i3, int i4) {
                        this.val$interp = interpolator;
                        this.val$openedDeg = i;
                        this.val$closedDeg = i2;
                        this.val$px = i3;
                        this.val$py = i4;
                    }

                    @Override // com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer
/* 55 */            public void transformCanvas(Canvas canvas, float percentOpen) {
/* 56 */                CanvasTransformerBuilder.access$000(CanvasTransformerBuilder.this).transformCanvas(canvas, percentOpen);
/* 57 */                float f = this.val$interp.getInterpolation(percentOpen);
/* 58 */                canvas.rotate(((this.val$openedDeg - this.val$closedDeg) * f) + this.val$closedDeg, this.val$px, this.val$py);
                    }
                }

/* 52 */        public SlidingMenu.CanvasTransformer rotate(int openedDeg, int closedDeg, int px, int py, Interpolator interp) {
/* 53 */            initTransformer();
/* 54 */            this.mTrans = new C12384(interp, openedDeg, closedDeg, px, py);
/* 62 */            return this.mTrans;
                }

/* 66 */        public SlidingMenu.CanvasTransformer translate(int openedX, int closedX, int openedY, int closedY) {
/* 67 */            return translate(openedX, closedX, openedY, closedY, lin);
                }

                /* JADX INFO: renamed from: com.jeremyfeinstein.slidingmenu.lib.CanvasTransformerBuilder$5 */
/* 71 */        class C12395 implements SlidingMenu.CanvasTransformer {
                    final /* synthetic */ int val$closedX;
                    final /* synthetic */ int val$closedY;
                    final /* synthetic */ Interpolator val$interp;
                    final /* synthetic */ int val$openedX;
                    final /* synthetic */ int val$openedY;

/* 72 */            C12395(Interpolator interpolator, int i, int i2, int i3, int i4) {
                        this.val$interp = interpolator;
                        this.val$openedX = i;
                        this.val$closedX = i2;
                        this.val$openedY = i3;
                        this.val$closedY = i4;
                    }

                    @Override // com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer
/* 74 */            public void transformCanvas(Canvas canvas, float percentOpen) {
/* 75 */                CanvasTransformerBuilder.access$000(CanvasTransformerBuilder.this).transformCanvas(canvas, percentOpen);
/* 76 */                float f = this.val$interp.getInterpolation(percentOpen);
/* 77 */                canvas.translate(((this.val$openedX - this.val$closedX) * f) + this.val$closedX, ((this.val$openedY - this.val$closedY) * f) + this.val$closedY);
                    }
                }

/* 71 */        public SlidingMenu.CanvasTransformer translate(int openedX, int closedX, int openedY, int closedY, Interpolator interp) {
/* 72 */            initTransformer();
/* 73 */            this.mTrans = new C12395(interp, openedX, closedX, openedY, closedY);
/* 81 */            return this.mTrans;
                }

                /* JADX INFO: renamed from: com.jeremyfeinstein.slidingmenu.lib.CanvasTransformerBuilder$6 */
/* 84 */        class C12406 implements SlidingMenu.CanvasTransformer {
                    final /* synthetic */ SlidingMenu.CanvasTransformer val$t;

/* 85 */            C12406(SlidingMenu.CanvasTransformer canvasTransformer) {
                        this.val$t = canvasTransformer;
                    }

                    @Override // com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer
/* 87 */            public void transformCanvas(Canvas canvas, float percentOpen) {
/* 88 */                CanvasTransformerBuilder.access$000(CanvasTransformerBuilder.this).transformCanvas(canvas, percentOpen);
/* 89 */                this.val$t.transformCanvas(canvas, percentOpen);
                    }
                }

/* 84 */        public SlidingMenu.CanvasTransformer concatTransformer(SlidingMenu.CanvasTransformer t) {
/* 85 */            initTransformer();
/* 86 */            this.mTrans = new C12406(t);
/* 92 */            return this.mTrans;
                }
            }
