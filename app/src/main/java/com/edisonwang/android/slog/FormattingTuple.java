            package com.edisonwang.android.slog;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 42 */    public class FormattingTuple {
                public static FormattingTuple NULL = new FormattingTuple(null);
                private Object[] argArray;
                private String message;
                private Throwable throwable;

/* 43 */        public FormattingTuple(String message) {
/* 44 */            this(message, null, null);
                }

/* 46 */        public FormattingTuple(String message, Object[] argArray, Throwable throwable) {
/* 48 */            this.message = message;
/* 49 */            this.throwable = throwable;
/* 50 */            if (throwable == null) {
/* 51 */                this.argArray = argArray;
                    } else {
/* 53 */                this.argArray = trimmedCopy(argArray);
                    }
                }

/* 57 */        static Object[] trimmedCopy(Object[] argArray) {
/* 58 */            if (argArray == null || argArray.length == 0) {
/* 59 */                throw new IllegalStateException("non-sensical empty or null argument array");
                    }
/* 61 */            int trimemdLen = argArray.length - 1;
/* 62 */            Object[] trimmed = new Object[trimemdLen];
/* 63 */            System.arraycopy(argArray, 0, trimmed, 0, trimemdLen);
/* 64 */            return trimmed;
                }

/* 67 */        public String getMessage() {
/* 68 */            return this.message;
                }

/* 71 */        public Object[] getArgArray() {
/* 72 */            return this.argArray;
                }

/* 75 */        public Throwable getThrowable() {
/* 76 */            return this.throwable;
                }
            }
