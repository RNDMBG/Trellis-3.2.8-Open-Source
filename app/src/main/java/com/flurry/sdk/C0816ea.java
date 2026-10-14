            package com.flurry.sdk;

            import java.nio.ByteBuffer;
            import java.security.MessageDigest;
            import java.util.zip.CRC32;
            
            /* JADX INFO: renamed from: com.flurry.sdk.ea */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 11 */    public class C0816ea extends MessageDigest {

                /* JADX INFO: renamed from: a */
                private CRC32 f759a;

/* 12 */        public C0816ea() {
/* 13 */            super("CRC");
/* 9 */             this.f759a = new CRC32();
                }

                @Override // java.security.MessageDigestSpi
/* 18 */        protected void engineReset() {
/* 19 */            this.f759a.reset();
                }

                @Override // java.security.MessageDigestSpi
/* 24 */        protected void engineUpdate(byte b) {
/* 25 */            this.f759a.update(b);
                }

                @Override // java.security.MessageDigestSpi
/* 30 */        protected void engineUpdate(byte[] bArr, int i, int i2) {
/* 31 */            this.f759a.update(bArr, i, i2);
                }

                @Override // java.security.MessageDigestSpi
/* 36 */        protected byte[] engineDigest() {
/* 37 */            long value = this.f759a.getValue();
/* 38 */            return new byte[]{(byte) (((-16777216) & value) >> 24), (byte) ((16711680 & value) >> 16), (byte) ((65280 & value) >> 8), (byte) ((value & 255) >> 0)};
                }

                /* JADX INFO: renamed from: a */
/* 47 */        public byte[] m822a() {
/* 48 */            return engineDigest();
                }

                /* JADX INFO: renamed from: b */
/* 52 */        public int m823b() {
/* 54 */            return ByteBuffer.wrap(engineDigest()).getInt();
                }
            }
