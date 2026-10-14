            package com.google.android.exoplayer.util;

            import android.os.Looper;
            import android.text.TextUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    public final class Assertions {
/* 27 */        private Assertions() {
                }

/* 37 */        public static void checkArgument(boolean expression) {
/* 38 */            if (!expression) {
/* 39 */                throw new IllegalArgumentException();
                    }
                }

/* 52 */        public static void checkArgument(boolean expression, Object errorMessage) {
/* 53 */            if (!expression) {
/* 54 */                throw new IllegalArgumentException(String.valueOf(errorMessage));
                    }
                }

/* 64 */        public static void checkState(boolean expression) {
/* 65 */            if (!expression) {
/* 66 */                throw new IllegalStateException();
                    }
                }

/* 78 */        public static void checkState(boolean expression, Object errorMessage) {
/* 79 */            if (!expression) {
/* 80 */                throw new IllegalStateException(String.valueOf(errorMessage));
                    }
                }

/* 91 */        public static <T> T checkNotNull(T reference) {
/* 92 */            if (reference == null) {
/* 93 */                throw new NullPointerException();
                    }
/* 95 */            return reference;
                }

/* 107 */       public static <T> T checkNotNull(T reference, Object errorMessage) {
/* 108 */           if (reference == null) {
/* 109 */               throw new NullPointerException(String.valueOf(errorMessage));
                    }
/* 111 */           return reference;
                }

/* 121 */       public static String checkNotEmpty(String string) {
/* 122 */           if (TextUtils.isEmpty(string)) {
/* 123 */               throw new IllegalArgumentException();
                    }
/* 125 */           return string;
                }

/* 137 */       public static String checkNotEmpty(String string, Object errorMessage) {
/* 138 */           if (TextUtils.isEmpty(string)) {
/* 139 */               throw new IllegalArgumentException(String.valueOf(errorMessage));
                    }
/* 141 */           return string;
                }

/* 149 */       public static void checkMainThread() {
/* 150 */           if (Looper.myLooper() != Looper.getMainLooper()) {
/* 151 */               throw new IllegalStateException("Not in applications main thread");
                    }
                }
            }
