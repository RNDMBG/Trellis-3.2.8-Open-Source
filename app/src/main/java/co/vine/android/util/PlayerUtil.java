            package co.vine.android.util;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 9 */     public class PlayerUtil {
                private static String sAuthority;

/* 10 */        public static void setup(String authority) {
/* 11 */            sAuthority = authority;
                }

/* 14 */        public static String getAuthority(String suffix) {
/* 15 */            return sAuthority + suffix;
                }
            }
