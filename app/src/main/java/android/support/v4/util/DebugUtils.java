            package android.support.v4.util;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    public class DebugUtils {
/* 27 */        public static void buildShortClassTag(Object cls, StringBuilder out) {
                    int end;
/* 28 */            if (cls == null) {
/* 29 */                out.append("null");
/* 43 */                return;
                    }
/* 31 */            String simpleName = cls.getClass().getSimpleName();
/* 32 */            if ((simpleName == null || simpleName.length() <= 0) && (end = (simpleName = cls.getClass().getName()).lastIndexOf(46)) > 0) {
/* 36 */                simpleName = simpleName.substring(end + 1);
                    }
/* 39 */            out.append(simpleName);
/* 40 */            out.append('{');
/* 41 */            out.append(Integer.toHexString(System.identityHashCode(cls)));
                }
            }
