            package com.flurry.sdk;

            import java.nio.ByteBuffer;
            import java.security.MessageDigest;
            import java.util.zip.CRC32;
            
            
            
    public class C0816ea extends MessageDigest {

                
                private CRC32 f759a;

        public C0816ea() {
            super("CRC");
             this.f759a = new CRC32();
                }

                @Override 
        protected void engineReset() {
            this.f759a.reset();
                }

                @Override 
        protected void engineUpdate(byte b) {
            this.f759a.update(b);
                }

                @Override 
        protected void engineUpdate(byte[] bArr, int i, int i2) {
            this.f759a.update(bArr, i, i2);
                }

                @Override 
        protected byte[] engineDigest() {
            long value = this.f759a.getValue();
            return new byte[]{(byte) (((-16777216) & value) >> 24), (byte) ((16711680 & value) >> 16), (byte) ((65280 & value) >> 8), (byte) ((value & 255) >> 0)};
                }

                
        public byte[] m822a() {
            return engineDigest();
                }

                
        public int m823b() {
            return ByteBuffer.wrap(engineDigest()).getInt();
                }
            }
