            package com.lifevibes.mediacoder;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 43 */    class LVMediaCoderWriterJNI {
                public static final native int LVMediaCoderInitWriter(String str);

                public static final native int LVMediaCoderWriterAddStreamAudio(int i, byte[] bArr, int i2, int i3, int i4, int i5);

                public static final native int LVMediaCoderWriterAddStreamVideo(int i, byte[] bArr, int i2, int i3, int i4);

                public static final native int LVMediaCoderWriterClose(int i);

                public static final native int LVMediaCoderWriterProcess(int i, int i2, double d, double d2, byte[] bArr, int i3);

                public static final native char RGB888toYUV420WithPlanar(byte[] bArr, byte[] bArr2, int i, int i2, int i3);

                public static final native char RGB888toYUV420WithSemiPlanar(byte[] bArr, byte[] bArr2, int i, int i2, int i3);

                public static final native char RGB888toYUV420WithStride512Aligned(byte[] bArr, byte[] bArr2, int i, int i2, int i3, int i4, int i5);

                public static final native char RGB888toYUV420WithUVInterchanged(byte[] bArr, byte[] bArr2, int i, int i2, int i3);

                public static final native char RGB888toYUV420WithUVStride2KAligned(byte[] bArr, byte[] bArr2, int i, int i2, int i3);

/* 44 */        LVMediaCoderWriterJNI() {
                }
            }
