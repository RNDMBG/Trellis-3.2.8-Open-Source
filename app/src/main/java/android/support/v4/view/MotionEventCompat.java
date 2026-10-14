            package android.support.v4.view;

            import android.os.Build;
            import android.view.MotionEvent;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 37 */    public class MotionEventCompat {
                public static final int ACTION_HOVER_ENTER = 9;
                public static final int ACTION_HOVER_EXIT = 10;
                public static final int ACTION_HOVER_MOVE = 7;
                public static final int ACTION_MASK = 255;
                public static final int ACTION_POINTER_DOWN = 5;
                public static final int ACTION_POINTER_INDEX_MASK = 65280;
                public static final int ACTION_POINTER_INDEX_SHIFT = 8;
                public static final int ACTION_POINTER_UP = 6;
                public static final int ACTION_SCROLL = 8;
                static final MotionEventVersionImpl IMPL;

                interface MotionEventVersionImpl {
                    int findPointerIndex(MotionEvent motionEvent, int i);

                    int getPointerCount(MotionEvent motionEvent);

                    int getPointerId(MotionEvent motionEvent, int i);

                    float getX(MotionEvent motionEvent, int i);

                    float getY(MotionEvent motionEvent, int i);
                }

/* 38 */        static class BaseMotionEventVersionImpl implements MotionEventVersionImpl {
/* 39 */            BaseMotionEventVersionImpl() {
                    }

                    @Override // android.support.v4.view.MotionEventCompat.MotionEventVersionImpl
/* 42 */            public int findPointerIndex(MotionEvent event, int pointerId) {
                        return pointerId == 0 ? 0 : -1;
                    }

                    @Override // android.support.v4.view.MotionEventCompat.MotionEventVersionImpl
/* 50 */            public int getPointerId(MotionEvent event, int pointerIndex) {
/* 51 */                if (pointerIndex == 0) {
/* 53 */                    return 0;
                        }
/* 55 */                throw new IndexOutOfBoundsException("Pre-Eclair does not support multiple pointers");
                    }

                    @Override // android.support.v4.view.MotionEventCompat.MotionEventVersionImpl
/* 58 */            public float getX(MotionEvent event, int pointerIndex) {
/* 59 */                if (pointerIndex == 0) {
/* 60 */                    return event.getX();
                        }
/* 62 */                throw new IndexOutOfBoundsException("Pre-Eclair does not support multiple pointers");
                    }

                    @Override // android.support.v4.view.MotionEventCompat.MotionEventVersionImpl
/* 65 */            public float getY(MotionEvent event, int pointerIndex) {
/* 66 */                if (pointerIndex == 0) {
/* 67 */                    return event.getY();
                        }
/* 69 */                throw new IndexOutOfBoundsException("Pre-Eclair does not support multiple pointers");
                    }

                    @Override // android.support.v4.view.MotionEventCompat.MotionEventVersionImpl
/* 72 */            public int getPointerCount(MotionEvent event) {
/* 73 */                return 1;
                    }
                }

/* 78 */        static class EclairMotionEventVersionImpl implements MotionEventVersionImpl {
/* 79 */            EclairMotionEventVersionImpl() {
                    }

                    @Override // android.support.v4.view.MotionEventCompat.MotionEventVersionImpl
/* 82 */            public int findPointerIndex(MotionEvent event, int pointerId) {
/* 83 */                return MotionEventCompatEclair.findPointerIndex(event, pointerId);
                    }

                    @Override // android.support.v4.view.MotionEventCompat.MotionEventVersionImpl
/* 86 */            public int getPointerId(MotionEvent event, int pointerIndex) {
/* 87 */                return MotionEventCompatEclair.getPointerId(event, pointerIndex);
                    }

                    @Override // android.support.v4.view.MotionEventCompat.MotionEventVersionImpl
/* 90 */            public float getX(MotionEvent event, int pointerIndex) {
/* 91 */                return MotionEventCompatEclair.getX(event, pointerIndex);
                    }

                    @Override // android.support.v4.view.MotionEventCompat.MotionEventVersionImpl
/* 94 */            public float getY(MotionEvent event, int pointerIndex) {
/* 95 */                return MotionEventCompatEclair.getY(event, pointerIndex);
                    }

                    @Override // android.support.v4.view.MotionEventCompat.MotionEventVersionImpl
/* 98 */            public int getPointerCount(MotionEvent event) {
/* 99 */                return MotionEventCompatEclair.getPointerCount(event);
                    }
                }

                static {
/* 108 */           if (Build.VERSION.SDK_INT >= 5) {
/* 109 */               IMPL = new EclairMotionEventVersionImpl();
                    } else {
/* 111 */               IMPL = new BaseMotionEventVersionImpl();
                    }
                }

/* 182 */       public static int getActionMasked(MotionEvent event) {
/* 183 */           return event.getAction() & 255;
                }

/* 190 */       public static int getActionIndex(MotionEvent event) {
/* 191 */           return (event.getAction() & ACTION_POINTER_INDEX_MASK) >> 8;
                }

/* 200 */       public static int findPointerIndex(MotionEvent event, int pointerId) {
/* 201 */           return IMPL.findPointerIndex(event, pointerId);
                }

/* 209 */       public static int getPointerId(MotionEvent event, int pointerIndex) {
/* 210 */           return IMPL.getPointerId(event, pointerIndex);
                }

/* 218 */       public static float getX(MotionEvent event, int pointerIndex) {
/* 219 */           return IMPL.getX(event, pointerIndex);
                }

/* 227 */       public static float getY(MotionEvent event, int pointerIndex) {
/* 228 */           return IMPL.getY(event, pointerIndex);
                }

/* 235 */       public static int getPointerCount(MotionEvent event) {
/* 236 */           return IMPL.getPointerCount(event);
                }
            }
