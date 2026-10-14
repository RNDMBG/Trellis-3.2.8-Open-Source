            package android.support.v4.widget;

            import android.os.Build;
            import android.view.View;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    public class ListPopupWindowCompat {
                static final ListPopupWindowImpl IMPL;

                interface ListPopupWindowImpl {
                    View.OnTouchListener createDragToOpenListener(Object obj, View view);
                }

/* 35 */        static class BaseListPopupWindowImpl implements ListPopupWindowImpl {
/* 36 */            BaseListPopupWindowImpl() {
                    }

                    @Override // android.support.v4.widget.ListPopupWindowCompat.ListPopupWindowImpl
/* 39 */            public View.OnTouchListener createDragToOpenListener(Object listPopupWindow, View src) {
/* 40 */                return null;
                    }
                }

/* 45 */        static class KitKatListPopupWindowImpl extends BaseListPopupWindowImpl {
/* 46 */            KitKatListPopupWindowImpl() {
                    }

                    @Override // android.support.v4.widget.ListPopupWindowCompat.BaseListPopupWindowImpl, android.support.v4.widget.ListPopupWindowCompat.ListPopupWindowImpl
/* 49 */            public View.OnTouchListener createDragToOpenListener(Object listPopupWindow, View src) {
/* 50 */                return ListPopupWindowCompatKitKat.createDragToOpenListener(listPopupWindow, src);
                    }
                }

                static {
/* 59 */            int version = Build.VERSION.SDK_INT;
/* 60 */            if (version >= 19) {
/* 61 */                IMPL = new KitKatListPopupWindowImpl();
                    } else {
/* 63 */                IMPL = new BaseListPopupWindowImpl();
                    }
                }

/* 66 */        private ListPopupWindowCompat() {
                }

/* 96 */        public static View.OnTouchListener createDragToOpenListener(Object listPopupWindow, View src) {
/* 97 */            return IMPL.createDragToOpenListener(listPopupWindow, src);
                }
            }
