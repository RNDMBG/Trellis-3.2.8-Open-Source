            package co.vine.android.util;

            import android.content.Context;
            import android.content.res.Resources;
            import android.graphics.Bitmap;
            import android.graphics.drawable.BitmapDrawable;
            import android.view.View;
            import android.widget.RelativeLayout;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 15 */    public class FloatingViewUtils {
/* 16 */        public static Bitmap buildFloatingViewFor(Context context, View source, View target, int oldWidth, int left, int top) {
/* 17 */            Resources res = context.getResources();
/* 18 */            int oldHeight = source.getMeasuredHeight();
/* 19 */            source.setDrawingCacheQuality(1048576);
/* 20 */            source.setDrawingCacheEnabled(true);
/* 22 */            Bitmap sourceCached = Bitmap.createBitmap(source.getDrawingCache(true));
/* 23 */            source.setDrawingCacheEnabled(false);
/* 24 */            ViewUtil.setBackground(target, new BitmapDrawable(res, sourceCached));
/* 26 */            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) target.getLayoutParams();
/* 28 */            params.width = oldWidth;
/* 29 */            params.height = oldHeight;
/* 30 */            params.leftMargin = left;
/* 31 */            params.topMargin = top;
/* 33 */            target.setLayoutParams(params);
/* 34 */            return sourceCached;
                }
            }
