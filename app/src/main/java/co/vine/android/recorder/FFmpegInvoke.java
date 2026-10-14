            package co.vine.android.recorder;
            
            
     public class FFmpegInvoke {
                private String mPath;

                private native int run(String str, String[] strArr);

                public native void YUVtoRBG(int[] iArr, byte[] bArr, int i, int i2);

         public FFmpegInvoke(String path) {
            this.mPath = path;
                }

        public int run(String[] args) {
            return run(this.mPath, args);
                }
            }
