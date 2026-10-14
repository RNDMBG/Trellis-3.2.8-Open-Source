            package co.vine.android.widget;

            import android.content.Context;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.animation.AlphaAnimation;
            import android.view.animation.Animation;
            import android.widget.RelativeLayout;
            import co.vine.android.animation.SimpleAnimationListener;
            
            
    public class ModalView extends RelativeLayout {
                public static final long ANIM_DURATION_LONG = 500;
                public static final long ANIM_DURATION_SHORT = 300;
                public static final long DURATION_DEFAULT = 3000;
                private Runnable hideCallback;

        static  Runnable access$000(ModalView x0) {
            return x0.hideCallback;
                }

        public ModalView(Context context, ViewGroup parent, int layout) {
            super(context);
            this.hideCallback = new RunnableC05642();
            parent.addView(this);
            ViewGroup body = (ViewGroup) inflate(context, layout, this);
            body.setOnClickListener(new ViewOnClickListenerC05631());
                }

                
        class ViewOnClickListenerC05631 implements View.OnClickListener {
            ViewOnClickListenerC05631() {
                    }

                    @Override 
            public void onClick(View view) {
                if (ModalView.this.getAnimation() == null) {
                    ModalView.this.removeCallbacks(ModalView.access$000(ModalView.this));
                    ModalView.access$000(ModalView.this).run();
                        }
                    }
                }

                
        class RunnableC05642 implements Runnable {
            RunnableC05642() {
                    }

                    @Override 
            public void run() {
                Animation hide = new AlphaAnimation(1.0f, 0.0f);
                hide.setDuration(300L);
                hide.setAnimationListener(new AnonymousClass1());
                ModalView.this.startAnimation(hide);
                    }

                    
            class AnonymousClass1 extends SimpleAnimationListener {
                AnonymousClass1() {
                        }

                        @Override 
                public void onAnimationEnd(Animation animation) {
                    ModalView.this.setVisibility(8);
                    ViewGroup parent = (ViewGroup) ModalView.this.getParent();
                    if (parent != null) {
                        parent.removeView(ModalView.this);
                            }
                        }
                    }
                }

                @Override 
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            Animation show = new AlphaAnimation(0.0f, 1.0f);
            show.setDuration(500L);
            show.setAnimationListener(new C05653());
            startAnimation(show);
            postDelayed(this.hideCallback, DURATION_DEFAULT);
                }

                
        class C05653 extends SimpleAnimationListener {
            C05653() {
                    }

                    @Override 
            public void onAnimationEnd(Animation animation) {
                ModalView.this.clearAnimation();
                ModalView.this.setVisibility(0);
                    }
                }

                @Override 
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            getLayoutParams().width = -1;
            getLayoutParams().height = -1;
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
                }
            }
