            package co.vine.android.util;

            import java.io.IOException;
            
            
     public class ExceptionUtil {
        public static boolean isNoSpaceLeftException(Exception e) {
                    String msg;
            if (e == null || (msg = e.getMessage()) == null) {
                        return false;
                    }
            if (e instanceof IOException) {
                        return msg.contains("ENOSPC") || msg.contains("EDQUOT");
                    }
                    return msg.contains("No space left on device") || msg.contains("Quota exceeded");
                }
            }
