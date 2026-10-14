            package android.support.v4.view;

            import android.view.KeyEvent;
            import android.view.View;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    class KeyEventCompatEclair {
/* 22 */        KeyEventCompatEclair() {
                }

/* 24 */        public static Object getKeyDispatcherState(View view) {
/* 25 */            return view.getKeyDispatcherState();
                }

/* 29 */        public static boolean dispatch(KeyEvent event, KeyEvent.Callback receiver, Object state, Object target) {
/* 30 */            return event.dispatch(receiver, (KeyEvent.DispatcherState) state, target);
                }

/* 33 */        public static void startTracking(KeyEvent event) {
/* 34 */            event.startTracking();
                }

/* 37 */        public static boolean isTracking(KeyEvent event) {
/* 38 */            return event.isTracking();
                }
            }
