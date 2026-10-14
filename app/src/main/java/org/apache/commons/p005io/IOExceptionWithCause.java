            package org.apache.commons.p005io;

            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 47 */    public class IOExceptionWithCause extends IOException {
                private static final long serialVersionUID = 1;

/* 48 */        public IOExceptionWithCause(String message, Throwable cause) {
/* 49 */            super(message);
/* 50 */            initCause(cause);
                }

/* 63 */        public IOExceptionWithCause(Throwable cause) {
/* 64 */            super(cause == null ? null : cause.toString());
/* 65 */            initCause(cause);
                }
            }
