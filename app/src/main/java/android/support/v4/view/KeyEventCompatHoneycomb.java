            package android.support.v4.view;

            import android.view.KeyEvent;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    class KeyEventCompatHoneycomb {
/* 23 */        KeyEventCompatHoneycomb() {
                }

/* 25 */        public static int normalizeMetaState(int metaState) {
/* 26 */            return KeyEvent.normalizeMetaState(metaState);
                }

/* 29 */        public static boolean metaStateHasModifiers(int metaState, int modifiers) {
/* 30 */            return KeyEvent.metaStateHasModifiers(metaState, modifiers);
                }

/* 33 */        public static boolean metaStateHasNoModifiers(int metaState) {
/* 34 */            return KeyEvent.metaStateHasNoModifiers(metaState);
                }
            }
