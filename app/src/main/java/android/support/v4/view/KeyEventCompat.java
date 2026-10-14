            package android.support.v4.view;

            import android.os.Build;
            import android.view.KeyEvent;
            import android.view.View;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 41 */    public class KeyEventCompat {
                static final KeyEventVersionImpl IMPL;

                interface KeyEventVersionImpl {
                    boolean dispatch(KeyEvent keyEvent, KeyEvent.Callback callback, Object obj, Object obj2);

                    Object getKeyDispatcherState(View view);

                    boolean isTracking(KeyEvent keyEvent);

                    boolean metaStateHasModifiers(int i, int i2);

                    boolean metaStateHasNoModifiers(int i);

                    int normalizeMetaState(int i);

                    void startTracking(KeyEvent keyEvent);
                }

/* 42 */        static class BaseKeyEventVersionImpl implements KeyEventVersionImpl {
                    private static final int META_ALL_MASK = 247;
                    private static final int META_MODIFIER_MASK = 247;

/* 43 */            BaseKeyEventVersionImpl() {
                    }

/* 54 */            private static int metaStateFilterDirectionalModifiers(int metaState, int modifiers, int basic, int left, int right) {
/* 55 */                boolean wantBasic = (modifiers & basic) != 0;
/* 56 */                int directional = left | right;
/* 57 */                boolean wantLeftOrRight = (modifiers & directional) != 0;
/* 59 */                if (wantBasic) {
/* 60 */                    if (wantLeftOrRight) {
/* 61 */                        throw new IllegalArgumentException("bad arguments");
                            }
/* 63 */                    return metaState & (directional ^ (-1));
                        }
/* 64 */                if (wantLeftOrRight) {
/* 65 */                    return metaState & (basic ^ (-1));
                        }
/* 67 */                return metaState;
                    }

                    @Override // android.support.v4.view.KeyEventCompat.KeyEventVersionImpl
/* 72 */            public int normalizeMetaState(int metaState) {
/* 73 */                if ((metaState & 192) != 0) {
/* 74 */                    metaState |= 1;
                        }
/* 76 */                if ((metaState & 48) != 0) {
/* 77 */                    metaState |= 2;
                        }
/* 79 */                return metaState & 247;
                    }

                    @Override // android.support.v4.view.KeyEventCompat.KeyEventVersionImpl
/* 83 */            public boolean metaStateHasModifiers(int metaState, int modifiers) {
                        return metaStateFilterDirectionalModifiers(metaStateFilterDirectionalModifiers(normalizeMetaState(metaState) & 247, modifiers, 1, 64, 128), modifiers, 2, 16, 32) == modifiers;
                    }

                    @Override // android.support.v4.view.KeyEventCompat.KeyEventVersionImpl
/* 93 */            public boolean metaStateHasNoModifiers(int metaState) {
                        return (normalizeMetaState(metaState) & 247) == 0;
                    }

                    @Override // android.support.v4.view.KeyEventCompat.KeyEventVersionImpl
/* 98 */            public void startTracking(KeyEvent event) {
                    }

                    @Override // android.support.v4.view.KeyEventCompat.KeyEventVersionImpl
/* 102 */           public boolean isTracking(KeyEvent event) {
/* 103 */               return false;
                    }

                    @Override // android.support.v4.view.KeyEventCompat.KeyEventVersionImpl
/* 107 */           public Object getKeyDispatcherState(View view) {
/* 108 */               return null;
                    }

                    @Override // android.support.v4.view.KeyEventCompat.KeyEventVersionImpl
/* 113 */           public boolean dispatch(KeyEvent event, KeyEvent.Callback receiver, Object state, Object target) {
/* 114 */               return event.dispatch(receiver);
                    }
                }

/* 116 */       static class EclairKeyEventVersionImpl extends BaseKeyEventVersionImpl {
/* 117 */           EclairKeyEventVersionImpl() {
                    }

                    @Override // android.support.v4.view.KeyEventCompat.BaseKeyEventVersionImpl, android.support.v4.view.KeyEventCompat.KeyEventVersionImpl
/* 120 */           public void startTracking(KeyEvent event) {
/* 121 */               KeyEventCompatEclair.startTracking(event);
                    }

                    @Override // android.support.v4.view.KeyEventCompat.BaseKeyEventVersionImpl, android.support.v4.view.KeyEventCompat.KeyEventVersionImpl
/* 125 */           public boolean isTracking(KeyEvent event) {
/* 126 */               return KeyEventCompatEclair.isTracking(event);
                    }

                    @Override // android.support.v4.view.KeyEventCompat.BaseKeyEventVersionImpl, android.support.v4.view.KeyEventCompat.KeyEventVersionImpl
/* 130 */           public Object getKeyDispatcherState(View view) {
/* 131 */               return KeyEventCompatEclair.getKeyDispatcherState(view);
                    }

                    @Override // android.support.v4.view.KeyEventCompat.BaseKeyEventVersionImpl, android.support.v4.view.KeyEventCompat.KeyEventVersionImpl
/* 136 */           public boolean dispatch(KeyEvent event, KeyEvent.Callback receiver, Object state, Object target) {
/* 137 */               return KeyEventCompatEclair.dispatch(event, receiver, state, target);
                    }
                }

/* 142 */       static class HoneycombKeyEventVersionImpl extends EclairKeyEventVersionImpl {
/* 143 */           HoneycombKeyEventVersionImpl() {
                    }

                    @Override // android.support.v4.view.KeyEventCompat.BaseKeyEventVersionImpl, android.support.v4.view.KeyEventCompat.KeyEventVersionImpl
/* 146 */           public int normalizeMetaState(int metaState) {
/* 147 */               return KeyEventCompatHoneycomb.normalizeMetaState(metaState);
                    }

                    @Override // android.support.v4.view.KeyEventCompat.BaseKeyEventVersionImpl, android.support.v4.view.KeyEventCompat.KeyEventVersionImpl
/* 151 */           public boolean metaStateHasModifiers(int metaState, int modifiers) {
/* 152 */               return KeyEventCompatHoneycomb.metaStateHasModifiers(metaState, modifiers);
                    }

                    @Override // android.support.v4.view.KeyEventCompat.BaseKeyEventVersionImpl, android.support.v4.view.KeyEventCompat.KeyEventVersionImpl
/* 156 */           public boolean metaStateHasNoModifiers(int metaState) {
/* 157 */               return KeyEventCompatHoneycomb.metaStateHasNoModifiers(metaState);
                    }
                }

                static {
/* 166 */           if (Build.VERSION.SDK_INT >= 11) {
/* 167 */               IMPL = new HoneycombKeyEventVersionImpl();
                    } else {
/* 169 */               IMPL = new BaseKeyEventVersionImpl();
                    }
                }

/* 175 */       public static int normalizeMetaState(int metaState) {
/* 176 */           return IMPL.normalizeMetaState(metaState);
                }

/* 179 */       public static boolean metaStateHasModifiers(int metaState, int modifiers) {
/* 180 */           return IMPL.metaStateHasModifiers(metaState, modifiers);
                }

/* 183 */       public static boolean metaStateHasNoModifiers(int metaState) {
/* 184 */           return IMPL.metaStateHasNoModifiers(metaState);
                }

/* 187 */       public static boolean hasModifiers(KeyEvent event, int modifiers) {
/* 188 */           return IMPL.metaStateHasModifiers(event.getMetaState(), modifiers);
                }

/* 191 */       public static boolean hasNoModifiers(KeyEvent event) {
/* 192 */           return IMPL.metaStateHasNoModifiers(event.getMetaState());
                }

/* 195 */       public static void startTracking(KeyEvent event) {
/* 196 */           IMPL.startTracking(event);
                }

/* 199 */       public static boolean isTracking(KeyEvent event) {
/* 200 */           return IMPL.isTracking(event);
                }

/* 203 */       public static Object getKeyDispatcherState(View view) {
/* 204 */           return IMPL.getKeyDispatcherState(view);
                }

/* 208 */       public static boolean dispatch(KeyEvent event, KeyEvent.Callback receiver, Object state, Object target) {
/* 209 */           return IMPL.dispatch(event, receiver, state, target);
                }
            }
