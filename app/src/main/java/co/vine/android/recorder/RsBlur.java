            package co.vine.android.recorder;

            import android.graphics.Bitmap;
            import android.support.v8.renderscript.Allocation;
            import android.support.v8.renderscript.Element;
            import android.support.v8.renderscript.RenderScript;
            import android.support.v8.renderscript.ScriptIntrinsicBlur;
            import android.support.v8.renderscript.Type;
            import com.edisonwang.android.slog.SLog;
            
            
    public class RsBlur {
                private Allocation mAllocationIn;
                private Allocation mAllocationOut;
                private ScriptIntrinsicBlur mBlur;
                private final int mHeight;
                private final int mWidth;

        public RsBlur(RenderScript rs, int width, int height, float radius) {
            this.mWidth = width;
            this.mHeight = height;
            Type.Builder tb = new Type.Builder(rs, Element.RGBA_8888(rs));
            tb.setX(this.mWidth);
            tb.setY(this.mHeight);
            this.mAllocationIn = Allocation.createTyped(rs, tb.create());
            this.mAllocationOut = Allocation.createTyped(rs, tb.create());
            this.mBlur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            this.mBlur.setRadius(radius);
            this.mBlur.setInput(this.mAllocationIn);
                }

        public RsBlur(RenderScript rs, Bitmap output, float radius) {
            this.mWidth = output.getWidth();
            this.mHeight = output.getHeight();
            this.mAllocationOut = Allocation.createFromBitmap(rs, output, Allocation.MipmapControl.MIPMAP_NONE, 128);
            this.mAllocationIn = Allocation.createTyped(rs, this.mAllocationOut.getType());
            this.mBlur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            this.mBlur.setRadius(radius);
            this.mBlur.setInput(this.mAllocationIn);
                }

        public void setRadius(float radius) {
            this.mBlur.setRadius(radius);
                }

        public void execute(Bitmap input, Bitmap output) {
            long start = System.currentTimeMillis();
            this.mAllocationIn.copyFrom(input);
            this.mBlur.forEach(this.mAllocationOut);
            if (output != null) {
                this.mAllocationOut.copyTo(output);
                    }
            SLog.m465i("RenderScriptBlur took {}ms", Long.valueOf(System.currentTimeMillis() - start));
                }

        public void execute(Bitmap input, Bitmap output, int loop) {
            long start = System.currentTimeMillis();
            execute(input, output);
                    for (int i = 1; i < loop; i++) {
                execute(output, output);
                    }
            SLog.m465i("RenderScriptBlur loop took {}ms.", Long.valueOf(System.currentTimeMillis() - start));
                }
            }
