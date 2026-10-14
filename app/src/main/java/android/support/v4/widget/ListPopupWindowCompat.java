            package android.support.v4.widget;

            import android.os.Build;
            import android.view.View;
            
            
    public class ListPopupWindowCompat {
                static final ListPopupWindowImpl IMPL;

                interface ListPopupWindowImpl {
                    View.OnTouchListener createDragToOpenListener(Object obj, View view);
                }

        static class BaseListPopupWindowImpl implements ListPopupWindowImpl {
            BaseListPopupWindowImpl() {
                    }

                    @Override 
            public View.OnTouchListener createDragToOpenListener(Object listPopupWindow, View src) {
                return null;
                    }
                }

        static class KitKatListPopupWindowImpl extends BaseListPopupWindowImpl {
            KitKatListPopupWindowImpl() {
                    }

                    @Override 
            public View.OnTouchListener createDragToOpenListener(Object listPopupWindow, View src) {
                return ListPopupWindowCompatKitKat.createDragToOpenListener(listPopupWindow, src);
                    }
                }

                static {
            int version = Build.VERSION.SDK_INT;
            if (version >= 19) {
                IMPL = new KitKatListPopupWindowImpl();
                    } else {
                IMPL = new BaseListPopupWindowImpl();
                    }
                }

        private ListPopupWindowCompat() {
                }

        public static View.OnTouchListener createDragToOpenListener(Object listPopupWindow, View src) {
            return IMPL.createDragToOpenListener(listPopupWindow, src);
                }
            }
