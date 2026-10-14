            package android.support.v4.widget;

            import android.os.Build;
            import android.view.View;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 33 */    public class PopupMenuCompat {
                static final PopupMenuImpl IMPL;

                interface PopupMenuImpl {
                    View.OnTouchListener getDragToOpenListener(Object obj);
                }

/* 34 */        static class BasePopupMenuImpl implements PopupMenuImpl {
/* 35 */            BasePopupMenuImpl() {
                    }

                    @Override // android.support.v4.widget.PopupMenuCompat.PopupMenuImpl
/* 38 */            public View.OnTouchListener getDragToOpenListener(Object popupMenu) {
/* 39 */                return null;
                    }
                }

/* 44 */        static class KitKatPopupMenuImpl extends BasePopupMenuImpl {
/* 45 */            KitKatPopupMenuImpl() {
                    }

                    @Override // android.support.v4.widget.PopupMenuCompat.BasePopupMenuImpl, android.support.v4.widget.PopupMenuCompat.PopupMenuImpl
/* 48 */            public View.OnTouchListener getDragToOpenListener(Object popupMenu) {
/* 49 */                return PopupMenuCompatKitKat.getDragToOpenListener(popupMenu);
                    }
                }

                static {
/* 58 */            int version = Build.VERSION.SDK_INT;
/* 59 */            if (version >= 19) {
/* 60 */                IMPL = new KitKatPopupMenuImpl();
                    } else {
/* 62 */                IMPL = new BasePopupMenuImpl();
                    }
                }

/* 65 */        private PopupMenuCompat() {
                }

/* 89 */        public static View.OnTouchListener getDragToOpenListener(Object popupMenu) {
/* 90 */            return IMPL.getDragToOpenListener(popupMenu);
                }
            }
