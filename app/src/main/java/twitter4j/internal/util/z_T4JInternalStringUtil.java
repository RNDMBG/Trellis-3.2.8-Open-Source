            package twitter4j.internal.util;

            import java.util.ArrayList;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    public class z_T4JInternalStringUtil {
/* 26 */        private z_T4JInternalStringUtil() {
/* 28 */            throw new AssertionError();
                }

/* 31 */        public static String maskString(String str) {
/* 32 */            StringBuilder buf = new StringBuilder(str.length());
                    for (int i = 0; i < str.length(); i++) {
/* 34 */                buf.append("*");
                    }
/* 36 */            return buf.toString();
                }

/* 42 */        public static String[] split(String str, String separator) {
/* 43 */            int index = str.indexOf(separator);
/* 44 */            if (index == -1) {
/* 45 */                String[] returnValue = {str};
/* 61 */                return returnValue;
                    }
/* 47 */            List<String> strList = new ArrayList<>();
/* 48 */            int oldIndex = 0;
/* 49 */            while (index != -1) {
/* 50 */                String subStr = str.substring(oldIndex, index);
/* 51 */                strList.add(subStr);
/* 52 */                oldIndex = index + separator.length();
/* 53 */                index = str.indexOf(separator, oldIndex);
                    }
/* 55 */            if (oldIndex != str.length()) {
/* 56 */                strList.add(str.substring(oldIndex));
                    }
/* 58 */            return (String[]) strList.toArray(new String[strList.size()]);
                }

/* 64 */        public static String join(int[] follows) {
/* 65 */            StringBuilder buf = new StringBuilder(follows.length * 11);
                    for (int follow : follows) {
/* 67 */                if (buf.length() != 0) {
/* 68 */                    buf.append(",");
                        }
/* 70 */                buf.append(follow);
                    }
/* 72 */            return buf.toString();
                }

/* 75 */        public static String join(long[] follows) {
/* 76 */            StringBuilder buf = new StringBuilder(follows.length * 11);
                    for (long follow : follows) {
/* 78 */                if (buf.length() != 0) {
/* 79 */                    buf.append(",");
                        }
/* 81 */                buf.append(follow);
                    }
/* 83 */            return buf.toString();
                }

/* 86 */        public static String join(String[] track) {
/* 87 */            StringBuilder buf = new StringBuilder(track.length * 11);
                    for (String str : track) {
/* 89 */                if (buf.length() != 0) {
/* 90 */                    buf.append(",");
                        }
/* 92 */                buf.append(str);
                    }
/* 94 */            return buf.toString();
                }

/* 97 */        public static String join(List<String> strs) {
/* 98 */            StringBuilder buf = new StringBuilder(strs.size() * 11);
                    for (String str : strs) {
/* 100 */               if (buf.length() != 0) {
/* 101 */                   buf.append(",");
                        }
/* 103 */               buf.append(str);
                    }
/* 105 */           return buf.toString();
                }
            }
