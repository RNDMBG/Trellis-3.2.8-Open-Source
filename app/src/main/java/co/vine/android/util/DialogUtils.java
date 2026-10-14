            package co.vine.android.util;

            import android.app.Dialog;
            
            
     public class DialogUtils {
         private DialogUtils() {
                }

        public static void showDialogUnsafe(Dialog dialog) {
                    try {
                dialog.show();
                    } catch (Exception e) {
                    }
                }
            }
