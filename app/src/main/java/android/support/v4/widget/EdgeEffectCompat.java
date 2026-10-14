            package android.support.v4.widget;

            import android.content.Context;
            import android.graphics.Canvas;
            import android.os.Build;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 35 */    public class EdgeEffectCompat {
                private static final EdgeEffectImpl IMPL;
                private Object mEdgeEffect;

                interface EdgeEffectImpl {
                    boolean draw(Object obj, Canvas canvas);

                    void finish(Object obj);

                    boolean isFinished(Object obj);

                    Object newEdgeEffect(Context context);

                    boolean onAbsorb(Object obj, int i);

                    boolean onPull(Object obj, float f);

                    boolean onRelease(Object obj);

                    void setSize(Object obj, int i, int i2);
                }

                static {
/* 37 */            if (Build.VERSION.SDK_INT >= 14) {
/* 38 */                IMPL = new EdgeEffectIcsImpl();
                    } else {
/* 40 */                IMPL = new BaseEdgeEffectImpl();
                    }
                }

/* 56 */        static class BaseEdgeEffectImpl implements EdgeEffectImpl {
/* 57 */            BaseEdgeEffectImpl() {
                    }

                    @Override // android.support.v4.widget.EdgeEffectCompat.EdgeEffectImpl
/* 59 */            public Object newEdgeEffect(Context context) {
/* 60 */                return null;
                    }

                    @Override // android.support.v4.widget.EdgeEffectCompat.EdgeEffectImpl
/* 63 */            public void setSize(Object edgeEffect, int width, int height) {
                    }

                    @Override // android.support.v4.widget.EdgeEffectCompat.EdgeEffectImpl
/* 66 */            public boolean isFinished(Object edgeEffect) {
/* 67 */                return true;
                    }

                    @Override // android.support.v4.widget.EdgeEffectCompat.EdgeEffectImpl
/* 70 */            public void finish(Object edgeEffect) {
                    }

                    @Override // android.support.v4.widget.EdgeEffectCompat.EdgeEffectImpl
/* 73 */            public boolean onPull(Object edgeEffect, float deltaDistance) {
/* 74 */                return false;
                    }

                    @Override // android.support.v4.widget.EdgeEffectCompat.EdgeEffectImpl
/* 77 */            public boolean onRelease(Object edgeEffect) {
/* 78 */                return false;
                    }

                    @Override // android.support.v4.widget.EdgeEffectCompat.EdgeEffectImpl
/* 81 */            public boolean onAbsorb(Object edgeEffect, int velocity) {
/* 82 */                return false;
                    }

                    @Override // android.support.v4.widget.EdgeEffectCompat.EdgeEffectImpl
/* 85 */            public boolean draw(Object edgeEffect, Canvas canvas) {
/* 86 */                return false;
                    }
                }

/* 88 */        static class EdgeEffectIcsImpl implements EdgeEffectImpl {
/* 89 */            EdgeEffectIcsImpl() {
                    }

                    @Override // android.support.v4.widget.EdgeEffectCompat.EdgeEffectImpl
/* 91 */            public Object newEdgeEffect(Context context) {
/* 92 */                return EdgeEffectCompatIcs.newEdgeEffect(context);
                    }

                    @Override // android.support.v4.widget.EdgeEffectCompat.EdgeEffectImpl
/* 95 */            public void setSize(Object edgeEffect, int width, int height) {
/* 96 */                EdgeEffectCompatIcs.setSize(edgeEffect, width, height);
                    }

                    @Override // android.support.v4.widget.EdgeEffectCompat.EdgeEffectImpl
/* 99 */            public boolean isFinished(Object edgeEffect) {
/* 100 */               return EdgeEffectCompatIcs.isFinished(edgeEffect);
                    }

                    @Override // android.support.v4.widget.EdgeEffectCompat.EdgeEffectImpl
/* 103 */           public void finish(Object edgeEffect) {
/* 104 */               EdgeEffectCompatIcs.finish(edgeEffect);
                    }

                    @Override // android.support.v4.widget.EdgeEffectCompat.EdgeEffectImpl
/* 107 */           public boolean onPull(Object edgeEffect, float deltaDistance) {
/* 108 */               return EdgeEffectCompatIcs.onPull(edgeEffect, deltaDistance);
                    }

                    @Override // android.support.v4.widget.EdgeEffectCompat.EdgeEffectImpl
/* 111 */           public boolean onRelease(Object edgeEffect) {
/* 112 */               return EdgeEffectCompatIcs.onRelease(edgeEffect);
                    }

                    @Override // android.support.v4.widget.EdgeEffectCompat.EdgeEffectImpl
/* 115 */           public boolean onAbsorb(Object edgeEffect, int velocity) {
/* 116 */               return EdgeEffectCompatIcs.onAbsorb(edgeEffect, velocity);
                    }

                    @Override // android.support.v4.widget.EdgeEffectCompat.EdgeEffectImpl
/* 119 */           public boolean draw(Object edgeEffect, Canvas canvas) {
/* 120 */               return EdgeEffectCompatIcs.draw(edgeEffect, canvas);
                    }
                }

/* 131 */       public EdgeEffectCompat(Context context) {
/* 133 */           this.mEdgeEffect = IMPL.newEdgeEffect(context);
                }

/* 142 */       public void setSize(int width, int height) {
/* 143 */           IMPL.setSize(this.mEdgeEffect, width, height);
                }

/* 153 */       public boolean isFinished() {
/* 154 */           return IMPL.isFinished(this.mEdgeEffect);
                }

/* 161 */       public void finish() {
/* 162 */           IMPL.finish(this.mEdgeEffect);
                }

/* 176 */       public boolean onPull(float deltaDistance) {
/* 177 */           return IMPL.onPull(this.mEdgeEffect, deltaDistance);
                }

/* 188 */       public boolean onRelease() {
/* 189 */           return IMPL.onRelease(this.mEdgeEffect);
                }

/* 203 */       public boolean onAbsorb(int velocity) {
/* 204 */           return IMPL.onAbsorb(this.mEdgeEffect, velocity);
                }

/* 217 */       public boolean draw(Canvas canvas) {
/* 218 */           return IMPL.draw(this.mEdgeEffect, canvas);
                }
            }
