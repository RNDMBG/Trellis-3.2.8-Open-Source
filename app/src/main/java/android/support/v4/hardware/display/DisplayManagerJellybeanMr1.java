            package android.support.v4.hardware.display;

            import android.content.Context;
            import android.hardware.display.DisplayManager;
            import android.view.Display;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    final class DisplayManagerJellybeanMr1 {
/* 21 */        DisplayManagerJellybeanMr1() {
                }

/* 23 */        public static Object getDisplayManager(Context context) {
/* 24 */            return context.getSystemService("display");
                }

/* 27 */        public static Display getDisplay(Object displayManagerObj, int displayId) {
/* 28 */            return ((DisplayManager) displayManagerObj).getDisplay(displayId);
                }

/* 31 */        public static Display[] getDisplays(Object displayManagerObj) {
/* 32 */            return ((DisplayManager) displayManagerObj).getDisplays();
                }

/* 35 */        public static Display[] getDisplays(Object displayManagerObj, String category) {
/* 36 */            return ((DisplayManager) displayManagerObj).getDisplays(category);
                }
            }
