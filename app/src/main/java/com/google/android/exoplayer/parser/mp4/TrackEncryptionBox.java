            package com.google.android.exoplayer.parser.mp4;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 42 */    public final class TrackEncryptionBox {
                public final int initializationVectorSize;
                public final boolean isEncrypted;
                public final byte[] keyId;

/* 43 */        public TrackEncryptionBox(boolean isEncrypted, int initializationVectorSize, byte[] keyId) {
/* 45 */            this.isEncrypted = isEncrypted;
/* 46 */            this.initializationVectorSize = initializationVectorSize;
/* 47 */            this.keyId = keyId;
                }
            }
