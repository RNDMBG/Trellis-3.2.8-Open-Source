            package android.support.v4.widget;

            import android.content.Context;
            import android.os.Build;
            import android.view.animation.Interpolator;
            import android.widget.Scroller;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 52 */    public class ScrollerCompat {
                static final ScrollerCompatImpl IMPL;
                Object mScroller;

                interface ScrollerCompatImpl {
                    void abortAnimation(Object obj);

                    boolean computeScrollOffset(Object obj);

                    Object createScroller(Context context, Interpolator interpolator);

                    void fling(Object obj, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);

                    void fling(Object obj, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10);

                    float getCurrVelocity(Object obj);

                    int getCurrX(Object obj);

                    int getCurrY(Object obj);

                    int getFinalX(Object obj);

                    int getFinalY(Object obj);

                    boolean isFinished(Object obj);

                    boolean isOverScrolled(Object obj);

                    void notifyHorizontalEdgeReached(Object obj, int i, int i2, int i3);

                    void notifyVerticalEdgeReached(Object obj, int i, int i2, int i3);

                    void startScroll(Object obj, int i, int i2, int i3, int i4);

                    void startScroll(Object obj, int i, int i2, int i3, int i4, int i5);
                }

/* 53 */        static class ScrollerCompatImplBase implements ScrollerCompatImpl {
/* 54 */            ScrollerCompatImplBase() {
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 57 */            public Object createScroller(Context context, Interpolator interpolator) {
                        return interpolator != null ? new Scroller(context, interpolator) : new Scroller(context);
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 63 */            public boolean isFinished(Object scroller) {
/* 64 */                return ((Scroller) scroller).isFinished();
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 68 */            public int getCurrX(Object scroller) {
/* 69 */                return ((Scroller) scroller).getCurrX();
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 73 */            public int getCurrY(Object scroller) {
/* 74 */                return ((Scroller) scroller).getCurrY();
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 78 */            public float getCurrVelocity(Object scroller) {
/* 79 */                return 0.0f;
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 83 */            public boolean computeScrollOffset(Object scroller) {
/* 84 */                return ((Scroller) scroller).computeScrollOffset();
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 88 */            public void startScroll(Object scroller, int startX, int startY, int dx, int dy) {
/* 89 */                ((Scroller) scroller).startScroll(startX, startY, dx, dy);
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 94 */            public void startScroll(Object scroller, int startX, int startY, int dx, int dy, int duration) {
/* 95 */                ((Scroller) scroller).startScroll(startX, startY, dx, dy, duration);
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 100 */           public void fling(Object scroller, int startX, int startY, int velX, int velY, int minX, int maxX, int minY, int maxY) {
/* 101 */               ((Scroller) scroller).fling(startX, startY, velX, velY, minX, maxX, minY, maxY);
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 106 */           public void fling(Object scroller, int startX, int startY, int velX, int velY, int minX, int maxX, int minY, int maxY, int overX, int overY) {
/* 107 */               ((Scroller) scroller).fling(startX, startY, velX, velY, minX, maxX, minY, maxY);
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 111 */           public void abortAnimation(Object scroller) {
/* 112 */               ((Scroller) scroller).abortAnimation();
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 118 */           public void notifyHorizontalEdgeReached(Object scroller, int startX, int finalX, int overX) {
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 123 */           public void notifyVerticalEdgeReached(Object scroller, int startY, int finalY, int overY) {
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 128 */           public boolean isOverScrolled(Object scroller) {
/* 129 */               return false;
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 133 */           public int getFinalX(Object scroller) {
/* 134 */               return ((Scroller) scroller).getFinalX();
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 138 */           public int getFinalY(Object scroller) {
/* 139 */               return ((Scroller) scroller).getFinalY();
                    }
                }

/* 141 */       static class ScrollerCompatImplGingerbread implements ScrollerCompatImpl {
/* 142 */           ScrollerCompatImplGingerbread() {
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 145 */           public Object createScroller(Context context, Interpolator interpolator) {
/* 146 */               return ScrollerCompatGingerbread.createScroller(context, interpolator);
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 150 */           public boolean isFinished(Object scroller) {
/* 151 */               return ScrollerCompatGingerbread.isFinished(scroller);
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 155 */           public int getCurrX(Object scroller) {
/* 156 */               return ScrollerCompatGingerbread.getCurrX(scroller);
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 160 */           public int getCurrY(Object scroller) {
/* 161 */               return ScrollerCompatGingerbread.getCurrY(scroller);
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 165 */           public float getCurrVelocity(Object scroller) {
/* 166 */               return 0.0f;
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 170 */           public boolean computeScrollOffset(Object scroller) {
/* 171 */               return ScrollerCompatGingerbread.computeScrollOffset(scroller);
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 175 */           public void startScroll(Object scroller, int startX, int startY, int dx, int dy) {
/* 176 */               ScrollerCompatGingerbread.startScroll(scroller, startX, startY, dx, dy);
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 181 */           public void startScroll(Object scroller, int startX, int startY, int dx, int dy, int duration) {
/* 182 */               ScrollerCompatGingerbread.startScroll(scroller, startX, startY, dx, dy, duration);
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 187 */           public void fling(Object scroller, int startX, int startY, int velX, int velY, int minX, int maxX, int minY, int maxY) {
/* 188 */               ScrollerCompatGingerbread.fling(scroller, startX, startY, velX, velY, minX, maxX, minY, maxY);
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 194 */           public void fling(Object scroller, int startX, int startY, int velX, int velY, int minX, int maxX, int minY, int maxY, int overX, int overY) {
/* 195 */               ScrollerCompatGingerbread.fling(scroller, startX, startY, velX, velY, minX, maxX, minY, maxY, overX, overY);
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 200 */           public void abortAnimation(Object scroller) {
/* 201 */               ScrollerCompatGingerbread.abortAnimation(scroller);
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 206 */           public void notifyHorizontalEdgeReached(Object scroller, int startX, int finalX, int overX) {
/* 207 */               ScrollerCompatGingerbread.notifyHorizontalEdgeReached(scroller, startX, finalX, overX);
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 211 */           public void notifyVerticalEdgeReached(Object scroller, int startY, int finalY, int overY) {
/* 212 */               ScrollerCompatGingerbread.notifyVerticalEdgeReached(scroller, startY, finalY, overY);
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 216 */           public boolean isOverScrolled(Object scroller) {
/* 217 */               return ScrollerCompatGingerbread.isOverScrolled(scroller);
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 221 */           public int getFinalX(Object scroller) {
/* 222 */               return ScrollerCompatGingerbread.getFinalX(scroller);
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 226 */           public int getFinalY(Object scroller) {
/* 227 */               return ScrollerCompatGingerbread.getFinalY(scroller);
                    }
                }

/* 229 */       static class ScrollerCompatImplIcs extends ScrollerCompatImplGingerbread {
/* 230 */           ScrollerCompatImplIcs() {
                    }

                    @Override // android.support.v4.widget.ScrollerCompat.ScrollerCompatImplGingerbread, android.support.v4.widget.ScrollerCompat.ScrollerCompatImpl
/* 233 */           public float getCurrVelocity(Object scroller) {
/* 234 */               return ScrollerCompatIcs.getCurrVelocity(scroller);
                    }
                }

                static {
/* 240 */           int version = Build.VERSION.SDK_INT;
/* 241 */           if (version >= 14) {
/* 242 */               IMPL = new ScrollerCompatImplIcs();
                    } else if (version >= 9) {
/* 244 */               IMPL = new ScrollerCompatImplGingerbread();
                    } else {
/* 246 */               IMPL = new ScrollerCompatImplBase();
                    }
                }

/* 250 */       public static ScrollerCompat create(Context context) {
/* 251 */           return create(context, null);
                }

/* 254 */       public static ScrollerCompat create(Context context, Interpolator interpolator) {
/* 255 */           return new ScrollerCompat(context, interpolator);
                }

/* 257 */       ScrollerCompat(Context context, Interpolator interpolator) {
/* 259 */           this.mScroller = IMPL.createScroller(context, interpolator);
                }

/* 267 */       public boolean isFinished() {
/* 268 */           return IMPL.isFinished(this.mScroller);
                }

/* 276 */       public int getCurrX() {
/* 277 */           return IMPL.getCurrX(this.mScroller);
                }

/* 285 */       public int getCurrY() {
/* 286 */           return IMPL.getCurrY(this.mScroller);
                }

/* 292 */       public int getFinalX() {
/* 293 */           return IMPL.getFinalX(this.mScroller);
                }

/* 299 */       public int getFinalY() {
/* 300 */           return IMPL.getFinalY(this.mScroller);
                }

/* 313 */       public float getCurrVelocity() {
/* 314 */           return IMPL.getCurrVelocity(this.mScroller);
                }

/* 322 */       public boolean computeScrollOffset() {
/* 323 */           return IMPL.computeScrollOffset(this.mScroller);
                }

/* 340 */       public void startScroll(int startX, int startY, int dx, int dy) {
/* 341 */           IMPL.startScroll(this.mScroller, startX, startY, dx, dy);
                }

/* 357 */       public void startScroll(int startX, int startY, int dx, int dy, int duration) {
/* 358 */           IMPL.startScroll(this.mScroller, startX, startY, dx, dy, duration);
                }

/* 381 */       public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY) {
/* 382 */           IMPL.fling(this.mScroller, startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
                }

/* 409 */       public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY, int overX, int overY) {
/* 410 */           IMPL.fling(this.mScroller, startX, startY, velocityX, velocityY, minX, maxX, minY, maxY, overX, overY);
                }

/* 418 */       public void abortAnimation() {
/* 419 */           IMPL.abortAnimation(this.mScroller);
                }

/* 436 */       public void notifyHorizontalEdgeReached(int startX, int finalX, int overX) {
/* 437 */           IMPL.notifyHorizontalEdgeReached(this.mScroller, startX, finalX, overX);
                }

/* 453 */       public void notifyVerticalEdgeReached(int startY, int finalY, int overY) {
/* 454 */           IMPL.notifyVerticalEdgeReached(this.mScroller, startY, finalY, overY);
                }

/* 470 */       public boolean isOverScrolled() {
/* 471 */           return IMPL.isOverScrolled(this.mScroller);
                }
            }
