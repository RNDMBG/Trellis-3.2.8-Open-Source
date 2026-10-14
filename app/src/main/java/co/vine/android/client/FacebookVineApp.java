            package co.vine.android.client;

            import android.os.Build;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 11 */    public class FacebookVineApp {
                public static final String APP_ID = transform();

/* 12 */        public static String transform() {
/* 13 */            if (Build.VERSION.SDK_INT < 10) {
/* 14 */                return "";
                    }
/* 16 */            byte[] m = {-28, -28, -35, -32, -31, -34, -32, -36, -29, -28, -32, -36, -29, -28, -34, -27};
/* 17 */            StringBuilder sb = new StringBuilder(m.length);
                    for (byte b : m) {
/* 19 */                sb.append((char) (21 - b));
                    }
/* 21 */            return sb.toString();
                }
            }
