            package co.vine.android.util;

            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 9 */     public class ExceptionUtil {
/* 10 */        public static boolean isNoSpaceLeftException(Exception e) {
                    String msg;
/* 11 */            if (e == null || (msg = e.getMessage()) == null) {
                        return false;
                    }
/* 14 */            if (e instanceof IOException) {
                        return msg.contains("ENOSPC") || msg.contains("EDQUOT");
                    }
                    return msg.contains("No space left on device") || msg.contains("Quota exceeded");
                }
            }
