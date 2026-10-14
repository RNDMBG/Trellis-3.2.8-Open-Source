            package android.support.v4.widget;

            import android.os.Build;
            import android.view.View;
            
            
    public class PopupMenuCompat {
                static final PopupMenuImpl IMPL;

                interface PopupMenuImpl {
                    View.OnTouchListener getDragToOpenListener(Object obj);
                }

        static class BasePopupMenuImpl implements PopupMenuImpl {
            BasePopupMenuImpl() {
                    }

                    @Override 
            public View.OnTouchListener getDragToOpenListener(Object popupMenu) {
                return null;
                    }
                }

        static class KitKatPopupMenuImpl extends BasePopupMenuImpl {
            KitKatPopupMenuImpl() {
                    }

                    @Override 
            public View.OnTouchListener getDragToOpenListener(Object popupMenu) {
                return PopupMenuCompatKitKat.getDragToOpenListener(popupMenu);
                    }
                }

                static {
            int version = Build.VERSION.SDK_INT;
            if (version >= 19) {
                IMPL = new KitKatPopupMenuImpl();
                    } else {
                IMPL = new BasePopupMenuImpl();
                    }
                }

        private PopupMenuCompat() {
                }

        public static View.OnTouchListener getDragToOpenListener(Object popupMenu) {
            return IMPL.getDragToOpenListener(popupMenu);
                }
            }
