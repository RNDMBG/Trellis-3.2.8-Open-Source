            package android.support.v4.widget;

            import android.view.View;
            import android.widget.ListPopupWindow;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    class ListPopupWindowCompatKitKat {
/* 25 */        ListPopupWindowCompatKitKat() {
                }

/* 27 */        public static View.OnTouchListener createDragToOpenListener(Object listPopupWindow, View src) {
/* 28 */            return ((ListPopupWindow) listPopupWindow).createDragToOpenListener(src);
                }
            }
