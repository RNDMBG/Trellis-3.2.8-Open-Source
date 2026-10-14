            package android.support.v4.app;

            import android.app.ActivityOptions;
            import android.content.Context;
            import android.graphics.Bitmap;
            import android.os.Bundle;
            import android.view.View;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    class ActivityOptionsCompatJB {
                private final ActivityOptions mActivityOptions;

/* 28 */        public static ActivityOptionsCompatJB makeCustomAnimation(Context context, int enterResId, int exitResId) {
/* 29 */            return new ActivityOptionsCompatJB(ActivityOptions.makeCustomAnimation(context, enterResId, exitResId));
                }

/* 34 */        public static ActivityOptionsCompatJB makeScaleUpAnimation(View source, int startX, int startY, int startWidth, int startHeight) {
/* 35 */            return new ActivityOptionsCompatJB(ActivityOptions.makeScaleUpAnimation(source, startX, startY, startWidth, startHeight));
                }

/* 40 */        public static ActivityOptionsCompatJB makeThumbnailScaleUpAnimation(View source, Bitmap thumbnail, int startX, int startY) {
/* 41 */            return new ActivityOptionsCompatJB(ActivityOptions.makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY));
                }

/* 46 */        private ActivityOptionsCompatJB(ActivityOptions activityOptions) {
/* 48 */            this.mActivityOptions = activityOptions;
                }

/* 51 */        public Bundle toBundle() {
/* 52 */            return this.mActivityOptions.toBundle();
                }

/* 55 */        public void update(ActivityOptionsCompatJB otherOptions) {
/* 56 */            this.mActivityOptions.update(otherOptions.mActivityOptions);
                }
            }
