            package android.support.v4.widget;

            import android.view.View;
            import android.widget.PopupMenu;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    class PopupMenuCompatKitKat {
/* 24 */        PopupMenuCompatKitKat() {
                }

/* 26 */        public static View.OnTouchListener getDragToOpenListener(Object popupMenu) {
/* 27 */            return ((PopupMenu) popupMenu).getDragToOpenListener();
                }
            }
