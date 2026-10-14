            package co.vine.android.animation;

            import android.os.SystemClock;
            import android.view.View;
            
            
    public class SmoothAnimator implements Runnable {

                
                private float f92mA;
                private float mAlpha;

                
                private float f93mB;

                
                private float f94mC;
                private boolean mCanceled;

                
                private float f95mD;
                protected float mDurationF;
                private final View mPoster;
                protected long mStartTime;

                public interface AnimationListener {
                    void onAnimationFinish(int i, Object obj);
                }

        public SmoothAnimator(View poster, float smoothness, int duration) {
            this.mPoster = poster;
            this.mAlpha = smoothness;
            this.mDurationF = duration;
            float f = 1.0f / ((this.mAlpha * 2.0f) * (1.0f - this.mAlpha));
                    this.f95mD = f;
                    this.f92mA = f;
            this.f93mB = this.mAlpha / ((this.mAlpha - 1.0f) * 2.0f);
            this.f94mC = 1.0f / (1.0f - this.mAlpha);
                }

        public float transform(float frac) {
                    if (frac < this.mAlpha) {
                return this.f92mA * frac * frac;
                    }
                    if (frac < 1.0f - this.mAlpha) {
                return this.f93mB + (this.f94mC * frac);
                    }
            return 1.0f - ((this.f95mD * (frac - 1.0f)) * (frac - 1.0f));
                }

        public void start() {
            this.mStartTime = SystemClock.uptimeMillis();
            this.mCanceled = false;
            onStart();
            this.mPoster.post(this);
                }

        public void cancel() {
            this.mCanceled = true;
                }

        public void onCancel() {
                }

        public void onStart() {
                }

        public void onUpdate(float frac, float smoothFrac) {
                }

        public void onStop() {
                }

                @Override 
        public void run() {
            if (this.mCanceled) {
                onCancel();
                return;
                    }
            float fraction = (SystemClock.uptimeMillis() - this.mStartTime) / this.mDurationF;
                    if (fraction >= 1.0f) {
                onUpdate(1.0f, 1.0f);
                onStop();
                    } else {
                onUpdate(fraction, transform(fraction));
                this.mPoster.post(this);
                    }
                }
            }
