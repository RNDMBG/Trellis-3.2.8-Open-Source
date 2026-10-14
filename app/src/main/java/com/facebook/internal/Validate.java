            package com.facebook.internal;

            import java.util.Collection;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    public final class Validate {
/* 27 */        public static void notNull(Object arg, String name) {
/* 28 */            if (arg == null) {
/* 29 */                throw new NullPointerException("Argument '" + name + "' cannot be null");
                    }
                }

/* 33 */        public static <T> void notEmpty(Collection<T> container, String name) {
/* 34 */            if (container.isEmpty()) {
/* 35 */                throw new IllegalArgumentException("Container '" + name + "' cannot be empty");
                    }
                }

/* 39 */        public static <T> void containsNoNulls(Collection<T> container, String name) {
/* 40 */            notNull(container, name);
                    for (T item : container) {
/* 42 */                if (item == null) {
/* 43 */                    throw new NullPointerException("Container '" + name + "' cannot contain null values");
                        }
                    }
                }

/* 48 */        public static <T> void notEmptyAndContainsNoNulls(Collection<T> container, String name) {
/* 49 */            containsNoNulls(container, name);
/* 50 */            notEmpty(container, name);
                }

/* 53 */        public static void notNullOrEmpty(String arg, String name) {
/* 54 */            if (Utility.isNullOrEmpty(arg)) {
/* 55 */                throw new IllegalArgumentException("Argument '" + name + "' cannot be null or empty");
                    }
                }

/* 59 */        public static void oneOf(Object arg, String name, Object... values) {
                    for (Object value : values) {
/* 61 */                if (value != null) {
/* 62 */                    if (value.equals(arg)) {
/* 67 */                        return;
                            }
                        } else if (arg == null) {
/* 67 */                    return;
                        }
                    }
/* 71 */            throw new IllegalArgumentException("Argument '" + name + "' was not one of the allowed values");
                }
            }
