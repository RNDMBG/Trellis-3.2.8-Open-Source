            package co.vine.android.plugin;

            import android.view.View;
            import android.view.ViewGroup;
            import android.view.animation.Animation;
            import java.lang.ref.WeakReference;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class WeakReferenceView extends WeakReference<View> {
/* 14 */        public WeakReferenceView(View r) {
/* 15 */            super(r);
                }

/* 18 */        public void setVisibility(int visibility) {
/* 19 */            View grid = (View) get();
/* 20 */            if (grid != null) {
/* 21 */                grid.setVisibility(visibility);
                    }
                }

/* 25 */        public void startAnimation(Animation animation) {
/* 26 */            View grid = (View) get();
/* 27 */            if (grid != null) {
/* 28 */                grid.startAnimation(animation);
                    }
                }

/* 32 */        public int getVisibility() {
/* 33 */            View grid = (View) get();
/* 34 */            if (grid != null) {
/* 35 */                return grid.getVisibility();
                    }
                    return -1;
                }

/* 40 */        public ViewGroup.LayoutParams getLayoutParams() {
/* 41 */            View grid = (View) get();
/* 42 */            if (grid != null) {
/* 43 */                return grid.getLayoutParams();
                    }
                    return null;
                }
            }
