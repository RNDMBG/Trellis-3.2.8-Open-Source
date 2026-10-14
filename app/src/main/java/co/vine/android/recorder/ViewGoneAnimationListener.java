            package co.vine.android.recorder;

            import android.animation.Animator;
            import android.view.View;
            import co.vine.android.animation.SimpleAnimatorListener;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 9 */     public class ViewGoneAnimationListener extends SimpleAnimatorListener {
                private View mView;

/* 10 */        public ViewGoneAnimationListener(View toRemove) {
/* 12 */            this.mView = toRemove;
                }

                @Override // co.vine.android.animation.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
/* 16 */        public void onAnimationEnd(Animator animation) {
/* 17 */            if (this.mView != null) {
/* 18 */                this.mView.setVisibility(4);
/* 19 */                this.mView.animate().setListener(null);
/* 20 */                this.mView.setAlpha(1.0f);
/* 21 */                this.mView = null;
                    }
                }
            }
