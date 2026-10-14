            package co.vine.android.util;

            import android.app.Dialog;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 4 */     public class DialogUtils {
/* 5 */         private DialogUtils() {
                }

/* 13 */        public static void showDialogUnsafe(Dialog dialog) {
                    try {
/* 14 */                dialog.show();
                    } catch (Exception e) {
                    }
                }
            }
