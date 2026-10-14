            package com.google.android.exoplayer;

            import android.annotation.TargetApi;
            import android.media.MediaCodec;
            import android.media.MediaExtractor;
            import com.google.android.exoplayer.util.Util;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 53 */    public class CryptoInfo {
                private final MediaCodec.CryptoInfo frameworkCryptoInfo;

                /* JADX INFO: renamed from: iv */
                public byte[] f874iv;
                public byte[] key;
                public int mode;
                public int[] numBytesOfClearData;
                public int[] numBytesOfEncryptedData;
                public int numSubSamples;

/* 54 */        public CryptoInfo() {
/* 56 */            this.frameworkCryptoInfo = Util.SDK_INT >= 16 ? newFrameworkCryptoInfoV16() : null;
                }

/* 63 */        public void set(int numSubSamples, int[] numBytesOfClearData, int[] numBytesOfEncryptedData, byte[] key, byte[] iv, int mode) {
/* 64 */            this.numSubSamples = numSubSamples;
/* 65 */            this.numBytesOfClearData = numBytesOfClearData;
/* 66 */            this.numBytesOfEncryptedData = numBytesOfEncryptedData;
/* 67 */            this.key = key;
/* 68 */            this.f874iv = iv;
/* 69 */            this.mode = mode;
/* 70 */            if (Util.SDK_INT >= 16) {
/* 71 */                updateFrameworkCryptoInfoV16();
                    }
                }

                @TargetApi(16)
/* 81 */        public void setFromExtractorV16(MediaExtractor extractor) {
/* 82 */            extractor.getSampleCryptoInfo(this.frameworkCryptoInfo);
/* 83 */            this.numSubSamples = this.frameworkCryptoInfo.numSubSamples;
/* 84 */            this.numBytesOfClearData = this.frameworkCryptoInfo.numBytesOfClearData;
/* 85 */            this.numBytesOfEncryptedData = this.frameworkCryptoInfo.numBytesOfEncryptedData;
/* 86 */            this.key = this.frameworkCryptoInfo.key;
/* 87 */            this.f874iv = this.frameworkCryptoInfo.iv;
/* 88 */            this.mode = this.frameworkCryptoInfo.mode;
                }

                @TargetApi(16)
/* 101 */       public MediaCodec.CryptoInfo getFrameworkCryptoInfoV16() {
/* 102 */           return this.frameworkCryptoInfo;
                }

                @TargetApi(16)
/* 106 */       private MediaCodec.CryptoInfo newFrameworkCryptoInfoV16() {
/* 107 */           return new MediaCodec.CryptoInfo();
                }

                @TargetApi(16)
/* 111 */       private void updateFrameworkCryptoInfoV16() {
/* 112 */           this.frameworkCryptoInfo.set(this.numSubSamples, this.numBytesOfClearData, this.numBytesOfEncryptedData, this.key, this.f874iv, this.mode);
                }
            }
