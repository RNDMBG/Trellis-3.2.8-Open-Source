            package android.support.v4.app;

            import android.content.Context;
            import android.graphics.Bitmap;
            import android.os.Build;
            import android.os.Bundle;
            import android.view.View;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 43 */    public class ActivityOptionsCompat {
/* 44 */        public static ActivityOptionsCompat makeCustomAnimation(Context context, int enterResId, int exitResId) {
                    return Build.VERSION.SDK_INT >= 16 ? new ActivityOptionsImplJB(ActivityOptionsCompatJB.makeCustomAnimation(context, enterResId, exitResId)) : new ActivityOptionsCompat();
                }

/* 73 */        public static ActivityOptionsCompat makeScaleUpAnimation(View source, int startX, int startY, int startWidth, int startHeight) {
                    return Build.VERSION.SDK_INT >= 16 ? new ActivityOptionsImplJB(ActivityOptionsCompatJB.makeScaleUpAnimation(source, startX, startY, startWidth, startHeight)) : new ActivityOptionsCompat();
                }

/* 102 */       public static ActivityOptionsCompat makeThumbnailScaleUpAnimation(View source, Bitmap thumbnail, int startX, int startY) {
                    return Build.VERSION.SDK_INT >= 16 ? new ActivityOptionsImplJB(ActivityOptionsCompatJB.makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY)) : new ActivityOptionsCompat();
                }

/* 113 */       private static class ActivityOptionsImplJB extends ActivityOptionsCompat {
                    private final ActivityOptionsCompatJB mImpl;

/* 114 */           ActivityOptionsImplJB(ActivityOptionsCompatJB impl) {
/* 116 */               this.mImpl = impl;
                    }

                    @Override // android.support.v4.app.ActivityOptionsCompat
/* 120 */           public Bundle toBundle() {
/* 121 */               return this.mImpl.toBundle();
                    }

                    @Override // android.support.v4.app.ActivityOptionsCompat
/* 125 */           public void update(ActivityOptionsCompat otherOptions) {
/* 126 */               if (otherOptions instanceof ActivityOptionsImplJB) {
/* 127 */                   ActivityOptionsImplJB otherImpl = (ActivityOptionsImplJB) otherOptions;
/* 128 */                   this.mImpl.update(otherImpl.mImpl);
                        }
                    }
                }

/* 133 */       protected ActivityOptionsCompat() {
                }

/* 144 */       public Bundle toBundle() {
/* 145 */           return null;
                }

/* 154 */       public void update(ActivityOptionsCompat otherOptions) {
                }
            }
