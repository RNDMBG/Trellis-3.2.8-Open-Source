            package co.vine.android.widget;

            import android.content.Context;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.animation.AlphaAnimation;
            import android.view.animation.Animation;
            import android.widget.RelativeLayout;
            import co.vine.android.animation.SimpleAnimationListener;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class ModalView extends RelativeLayout {
                public static final long ANIM_DURATION_LONG = 500;
                public static final long ANIM_DURATION_SHORT = 300;
                public static final long DURATION_DEFAULT = 3000;
                private Runnable hideCallback;

/* 14 */        static /* synthetic */ Runnable access$000(ModalView x0) {
/* 15 */            return x0.hideCallback;
                }

/* 21 */        public ModalView(Context context, ViewGroup parent, int layout) {
/* 22 */            super(context);
/* 37 */            this.hideCallback = new RunnableC05642();
/* 23 */            parent.addView(this);
/* 25 */            ViewGroup body = (ViewGroup) inflate(context, layout, this);
/* 26 */            body.setOnClickListener(new ViewOnClickListenerC05631());
                }

                /* JADX INFO: renamed from: co.vine.android.widget.ModalView$1 */
/* 24 */        class ViewOnClickListenerC05631 implements View.OnClickListener {
/* 25 */            ViewOnClickListenerC05631() {
                    }

                    @Override // android.view.View.OnClickListener
/* 28 */            public void onClick(View view) {
/* 29 */                if (ModalView.this.getAnimation() == null) {
/* 30 */                    ModalView.this.removeCallbacks(ModalView.access$000(ModalView.this));
/* 31 */                    ModalView.access$000(ModalView.this).run();
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.widget.ModalView$2 */
/* 35 */        class RunnableC05642 implements Runnable {
/* 36 */            RunnableC05642() {
                    }

                    @Override // java.lang.Runnable
/* 39 */            public void run() {
/* 40 */                Animation hide = new AlphaAnimation(1.0f, 0.0f);
/* 41 */                hide.setDuration(300L);
/* 42 */                hide.setAnimationListener(new AnonymousClass1());
/* 52 */                ModalView.this.startAnimation(hide);
                    }

                    /* JADX INFO: renamed from: co.vine.android.widget.ModalView$2$1, reason: invalid class name */
/* 40 */            class AnonymousClass1 extends SimpleAnimationListener {
/* 41 */                AnonymousClass1() {
                        }

                        @Override // co.vine.android.animation.SimpleAnimationListener, android.view.animation.Animation.AnimationListener
/* 44 */                public void onAnimationEnd(Animation animation) {
/* 45 */                    ModalView.this.setVisibility(8);
/* 46 */                    ViewGroup parent = (ViewGroup) ModalView.this.getParent();
/* 47 */                    if (parent != null) {
/* 48 */                        parent.removeView(ModalView.this);
                            }
                        }
                    }
                }

                @Override // android.view.ViewGroup, android.view.View
/* 57 */        protected void onAttachedToWindow() {
/* 58 */            super.onAttachedToWindow();
/* 59 */            Animation show = new AlphaAnimation(0.0f, 1.0f);
/* 60 */            show.setDuration(500L);
/* 61 */            show.setAnimationListener(new C05653());
/* 68 */            startAnimation(show);
/* 71 */            postDelayed(this.hideCallback, DURATION_DEFAULT);
                }

                /* JADX INFO: renamed from: co.vine.android.widget.ModalView$3 */
/* 59 */        class C05653 extends SimpleAnimationListener {
/* 60 */            C05653() {
                    }

                    @Override // co.vine.android.animation.SimpleAnimationListener, android.view.animation.Animation.AnimationListener
/* 63 */            public void onAnimationEnd(Animation animation) {
/* 64 */                ModalView.this.clearAnimation();
/* 65 */                ModalView.this.setVisibility(0);
                    }
                }

                @Override // android.widget.RelativeLayout, android.view.View
/* 75 */        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 76 */            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
/* 77 */            getLayoutParams().width = -1;
/* 78 */            getLayoutParams().height = -1;
/* 79 */            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
                }
            }
