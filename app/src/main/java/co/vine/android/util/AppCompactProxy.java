            package co.vine.android.util;

            import java.lang.reflect.Field;
            
            
    public class AppCompactProxy {
        public static int getBackIcon(String appPackageName, boolean dark) {
            String fieldName = dark ? "abc_ic_ab_back_holo_dark" : "abc_ic_ab_back_holo_light";
            Field field = null;
                    try {
                        try {
                    field = Class.forName(appPackageName + ".R$drawable").getField(fieldName);
                        } catch (NoSuchFieldException e) {
                        }
                if (field == null) {
                            try {
                                try {
                            field = Class.forName("android.support.v7.appcompat.R$drawable").getField(fieldName);
                                } catch (NoSuchFieldException e2) {
                            throw new RuntimeException(e2);
                                }
                            } catch (ClassNotFoundException e3) {
                        throw new RuntimeException(e3);
                            }
                        }
                        try {
                    return field.getInt(null);
                        } catch (IllegalAccessException e4) {
                    throw new RuntimeException(e4);
                        }
                    } catch (ClassNotFoundException e5) {
                throw new IllegalArgumentException("Invalid pacakge name: " + appPackageName);
                    }
                }
            }
