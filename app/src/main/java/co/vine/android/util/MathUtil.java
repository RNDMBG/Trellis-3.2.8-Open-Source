            package co.vine.android.util;

            import android.graphics.Matrix;
            import android.graphics.Rect;
            import android.graphics.RectF;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 11 */    public class MathUtil {
/* 12 */        public static int clamp(int x, int min, int max) {
/* 13 */            if (x > max) {
/* 15 */                return max;
                    }
                    return x < min ? min : x;
                }

/* 18 */        public static void rectFToRect(RectF rectF, Rect rect) {
/* 19 */            rect.left = Math.round(rectF.left);
/* 20 */            rect.top = Math.round(rectF.top);
/* 21 */            rect.right = Math.round(rectF.right);
/* 22 */            rect.bottom = Math.round(rectF.bottom);
                }

/* 27 */        public static void prepareMatrix(Matrix matrix, boolean mirror, int displayOrientation, int viewWidth, int viewHeight) {
/* 28 */            matrix.setScale(mirror ? -1.0f : 1.0f, 1.0f);
/* 30 */            matrix.postRotate(displayOrientation);
/* 33 */            matrix.postScale(viewWidth / 2000.0f, viewHeight / 2000.0f);
/* 34 */            matrix.postTranslate(viewWidth / 2.0f, viewHeight / 2.0f);
                }
            }
