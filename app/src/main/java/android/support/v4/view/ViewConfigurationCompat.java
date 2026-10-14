            package android.support.v4.view;

            import android.os.Build;
            import android.view.ViewConfiguration;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 33 */    public class ViewConfigurationCompat {
                static final ViewConfigurationVersionImpl IMPL;

                interface ViewConfigurationVersionImpl {
                    int getScaledPagingTouchSlop(ViewConfiguration viewConfiguration);
                }

/* 34 */        static class BaseViewConfigurationVersionImpl implements ViewConfigurationVersionImpl {
/* 35 */            BaseViewConfigurationVersionImpl() {
                    }

                    @Override // android.support.v4.view.ViewConfigurationCompat.ViewConfigurationVersionImpl
/* 38 */            public int getScaledPagingTouchSlop(ViewConfiguration config) {
/* 39 */                return config.getScaledTouchSlop();
                    }
                }

/* 44 */        static class FroyoViewConfigurationVersionImpl implements ViewConfigurationVersionImpl {
/* 45 */            FroyoViewConfigurationVersionImpl() {
                    }

                    @Override // android.support.v4.view.ViewConfigurationCompat.ViewConfigurationVersionImpl
/* 48 */            public int getScaledPagingTouchSlop(ViewConfiguration config) {
/* 49 */                return ViewConfigurationCompatFroyo.getScaledPagingTouchSlop(config);
                    }
                }

                static {
/* 58 */            if (Build.VERSION.SDK_INT >= 11) {
/* 59 */                IMPL = new FroyoViewConfigurationVersionImpl();
                    } else {
/* 61 */                IMPL = new BaseViewConfigurationVersionImpl();
                    }
                }

/* 72 */        public static int getScaledPagingTouchSlop(ViewConfiguration config) {
/* 73 */            return IMPL.getScaledPagingTouchSlop(config);
                }
            }
