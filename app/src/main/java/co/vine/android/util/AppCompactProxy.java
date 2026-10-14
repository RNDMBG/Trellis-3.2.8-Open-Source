            package co.vine.android.util;

            import java.lang.reflect.Field;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 10 */    public class AppCompactProxy {
/* 11 */        public static int getBackIcon(String appPackageName, boolean dark) {
/* 12 */            String fieldName = dark ? "abc_ic_ab_back_holo_dark" : "abc_ic_ab_back_holo_light";
/* 13 */            Field field = null;
                    try {
                        try {
/* 17 */                    field = Class.forName(appPackageName + ".R$drawable").getField(fieldName);
                        } catch (NoSuchFieldException e) {
                        }
/* 24 */                if (field == null) {
                            try {
                                try {
/* 28 */                            field = Class.forName("android.support.v7.appcompat.R$drawable").getField(fieldName);
                                } catch (NoSuchFieldException e2) {
/* 30 */                            throw new RuntimeException(e2);
                                }
                            } catch (ClassNotFoundException e3) {
/* 33 */                        throw new RuntimeException(e3);
                            }
                        }
                        try {
/* 37 */                    return field.getInt(null);
                        } catch (IllegalAccessException e4) {
/* 39 */                    throw new RuntimeException(e4);
                        }
                    } catch (ClassNotFoundException e5) {
/* 22 */                throw new IllegalArgumentException("Invalid pacakge name: " + appPackageName);
                    }
                }
            }
