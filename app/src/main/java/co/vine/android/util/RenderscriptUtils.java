            package co.vine.android.util;

            import android.content.Context;
            import android.graphics.Bitmap;
            import android.support.v8.renderscript.Allocation;
            import android.support.v8.renderscript.Element;
            import android.support.v8.renderscript.RSRuntimeException;
            import android.support.v8.renderscript.RenderScript;
            import android.support.v8.renderscript.ScriptIntrinsicBlur;
            import android.support.v8.renderscript.ScriptIntrinsicColorMatrix;
            import co.vine.android.cache.image.PhotoImagesCache;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    public class RenderscriptUtils {

                /* JADX INFO: renamed from: co.vine.android.util.RenderscriptUtils$1 */
/* 18 */        static class C05311 implements PhotoImagesCache.BlurTool {
/* 19 */            C05311() {
                    }

                    @Override // co.vine.android.cache.image.PhotoImagesCache.BlurTool
/* 23 */            public ImageUtils.BitmapInfo blur(Context mContext, Bitmap bitmap, int blurRadius, boolean desaturated, int r, int w, int h) {
                        try {
/* 24 */                    return new ImageUtils.BitmapInfo(RenderscriptUtils.getBlurredBitmap(mContext, bitmap, blurRadius, desaturated), r, w, h);
                        } catch (RSRuntimeException e) {
/* 28 */                    CrashUtil.logException(e);
/* 30 */                    return null;
                        }
                    }
                }

/* 19 */        public static PhotoImagesCache.BlurTool newBlurTool() {
/* 20 */            return new C05311();
                }

/* 36 */        public static Bitmap getBlurredBitmap(Context context, Bitmap bmp, int radius, boolean desaturate) {
                    Allocation allocationIn;
                    Allocation allocationOut;
                    try {
/* 37 */                RenderScript rs = RenderScript.create(context);
/* 38 */                ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
/* 39 */                blur.setRadius(radius);
/* 43 */                if (bmp.getConfig() == null) {
/* 44 */                    allocationIn = Allocation.createFromBitmap(rs, bmp, Allocation.MipmapControl.MIPMAP_NONE, 1);
/* 46 */                    allocationOut = Allocation.createFromBitmap(rs, bmp, Allocation.MipmapControl.MIPMAP_NONE, 1);
                        } else {
/* 49 */                    allocationIn = Allocation.createFromBitmap(rs, bmp, Allocation.MipmapControl.MIPMAP_NONE, 128);
/* 51 */                    allocationOut = Allocation.createFromBitmap(rs, bmp, Allocation.MipmapControl.MIPMAP_NONE, 128);
                        }
/* 55 */                allocationIn.copyFrom(bmp);
/* 56 */                blur.setInput(allocationIn);
/* 57 */                blur.forEach(allocationOut);
/* 59 */                if (desaturate) {
/* 60 */                    ScriptIntrinsicColorMatrix desat = ScriptIntrinsicColorMatrix.create(rs, Element.U8_4(rs));
/* 61 */                    desat.setGreyscale();
/* 63 */                    allocationIn.copyFrom(bmp);
/* 64 */                    desat.forEach(allocationOut, allocationIn);
/* 65 */                    allocationIn.copyTo(bmp);
                        } else {
/* 67 */                    allocationOut.copyTo(bmp);
                        }
                    } catch (Throwable e) {
/* 70 */                CrashUtil.logException(e);
                    }
/* 73 */            return bmp;
                }
            }
