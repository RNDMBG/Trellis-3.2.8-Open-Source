            package co.vine.android.recorder;

            import android.graphics.Bitmap;
            import android.support.v8.renderscript.Allocation;
            import android.support.v8.renderscript.Element;
            import android.support.v8.renderscript.RenderScript;
            import android.support.v8.renderscript.ScriptIntrinsicYuvToRGB;
            import android.support.v8.renderscript.Type;
            import co.vine.android.VineNotSupportedException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    public class RsYuv {
                private Allocation mAllocationIn;
                private Allocation mAllocationOut;
                private ScriptIntrinsicYuvToRGB mYuv;

/* 32 */        public RsYuv(RenderScript rs, int width, int height) throws VineNotSupportedException {
                    try {
/* 35 */                this.mYuv = ScriptIntrinsicYuvToRGB.create(rs, Element.RGBA_8888(rs));
/* 37 */                Type.Builder tb = new Type.Builder(rs, Element.RGBA_8888(rs));
/* 38 */                tb.setX(width);
/* 39 */                tb.setY(height);
/* 41 */                this.mAllocationOut = Allocation.createTyped(rs, tb.create());
/* 42 */                this.mAllocationIn = Allocation.createSized(rs, Element.U8(rs), (height * width) + ((height / 2) * (width / 2) * 2));
/* 47 */                this.mYuv.setInput(this.mAllocationIn);
                    } catch (Throwable th) {
/* 45 */                throw new VineNotSupportedException();
                    }
                }

/* 50 */        public void execute(byte[] yuv, Bitmap b) {
/* 51 */            this.mAllocationIn.copyFrom(yuv);
/* 52 */            this.mYuv.forEach(this.mAllocationOut);
/* 53 */            this.mAllocationOut.copyTo(b);
                }
            }
