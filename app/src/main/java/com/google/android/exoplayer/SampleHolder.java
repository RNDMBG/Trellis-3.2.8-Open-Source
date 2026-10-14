            package com.google.android.exoplayer;

            import java.nio.ByteBuffer;
            
            
    public final class SampleHolder {
                public final boolean allowDataBufferReplacement;
                public final CryptoInfo cryptoInfo = new CryptoInfo();
                public ByteBuffer data;
                public boolean decodeOnly;
                public int flags;
                public int size;
                public long timeUs;

        public SampleHolder(boolean allowDataBufferReplacement) {
            this.allowDataBufferReplacement = allowDataBufferReplacement;
                }
            }
