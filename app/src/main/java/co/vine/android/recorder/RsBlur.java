            package co.vine.android.recorder;

            import android.graphics.Bitmap;
            import android.support.v8.renderscript.Allocation;
            import android.support.v8.renderscript.Element;
            import android.support.v8.renderscript.RenderScript;
            import android.support.v8.renderscript.ScriptIntrinsicBlur;
            import android.support.v8.renderscript.Type;
            import com.edisonwang.android.slog.SLog;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 29 */    public class RsBlur {
                private Allocation mAllocationIn;
                private Allocation mAllocationOut;
                private ScriptIntrinsicBlur mBlur;
                private final int mHeight;
                private final int mWidth;

/* 30 */        public RsBlur(RenderScript rs, int width, int height, float radius) {
/* 32 */            this.mWidth = width;
/* 33 */            this.mHeight = height;
/* 34 */            Type.Builder tb = new Type.Builder(rs, Element.RGBA_8888(rs));
/* 35 */            tb.setX(this.mWidth);
/* 36 */            tb.setY(this.mHeight);
/* 37 */            this.mAllocationIn = Allocation.createTyped(rs, tb.create());
/* 38 */            this.mAllocationOut = Allocation.createTyped(rs, tb.create());
/* 39 */            this.mBlur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
/* 40 */            this.mBlur.setRadius(radius);
/* 41 */            this.mBlur.setInput(this.mAllocationIn);
                }

/* 51 */        public RsBlur(RenderScript rs, Bitmap output, float radius) {
/* 53 */            this.mWidth = output.getWidth();
/* 54 */            this.mHeight = output.getHeight();
/* 55 */            this.mAllocationOut = Allocation.createFromBitmap(rs, output, Allocation.MipmapControl.MIPMAP_NONE, 128);
/* 56 */            this.mAllocationIn = Allocation.createTyped(rs, this.mAllocationOut.getType());
/* 57 */            this.mBlur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
/* 58 */            this.mBlur.setRadius(radius);
/* 59 */            this.mBlur.setInput(this.mAllocationIn);
                }

/* 67 */        public void setRadius(float radius) {
/* 68 */            this.mBlur.setRadius(radius);
                }

/* 78 */        public void execute(Bitmap input, Bitmap output) {
/* 79 */            long start = System.currentTimeMillis();
/* 80 */            this.mAllocationIn.copyFrom(input);
/* 81 */            this.mBlur.forEach(this.mAllocationOut);
/* 82 */            if (output != null) {
/* 83 */                this.mAllocationOut.copyTo(output);
                    }
/* 85 */            SLog.m465i("RenderScriptBlur took {}ms", Long.valueOf(System.currentTimeMillis() - start));
                }

/* 88 */        public void execute(Bitmap input, Bitmap output, int loop) {
/* 89 */            long start = System.currentTimeMillis();
/* 90 */            execute(input, output);
                    for (int i = 1; i < loop; i++) {
/* 92 */                execute(output, output);
                    }
/* 94 */            SLog.m465i("RenderScriptBlur loop took {}ms.", Long.valueOf(System.currentTimeMillis() - start));
                }
            }
