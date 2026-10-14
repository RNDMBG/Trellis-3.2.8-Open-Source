            package com.google.android.exoplayer.upstream;

            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 38 */    public final class UnexpectedLengthException extends IOException {
                public final long actualLength;
                public final long expectedLength;

/* 39 */        public UnexpectedLengthException(long expectedLength, long actualLength) {
/* 40 */            super("Expected: " + expectedLength + ", got: " + actualLength);
/* 41 */            this.expectedLength = expectedLength;
/* 42 */            this.actualLength = actualLength;
                }
            }
