            package com.google.android.exoplayer;

            import java.nio.ByteBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 61 */    public final class SampleHolder {
                public final boolean allowDataBufferReplacement;
                public final CryptoInfo cryptoInfo = new CryptoInfo();
                public ByteBuffer data;
                public boolean decodeOnly;
                public int flags;
                public int size;
                public long timeUs;

/* 62 */        public SampleHolder(boolean allowDataBufferReplacement) {
/* 65 */            this.allowDataBufferReplacement = allowDataBufferReplacement;
                }
            }
