            package co.vine.android.recorder;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 8 */     public class FFmpegInvoke {
                private String mPath;

                private native int run(String str, String[] strArr);

                public native void YUVtoRBG(int[] iArr, byte[] bArr, int i, int i2);

/* 9 */         public FFmpegInvoke(String path) {
/* 11 */            this.mPath = path;
                }

/* 14 */        public int run(String[] args) {
/* 15 */            return run(this.mPath, args);
                }
            }
