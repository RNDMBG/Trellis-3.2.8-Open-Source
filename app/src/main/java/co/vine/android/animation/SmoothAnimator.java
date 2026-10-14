            package co.vine.android.animation;

            import android.os.SystemClock;
            import android.view.View;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    public class SmoothAnimator implements Runnable {

                /* JADX INFO: renamed from: mA */
                private float f92mA;
                private float mAlpha;

                /* JADX INFO: renamed from: mB */
                private float f93mB;

                /* JADX INFO: renamed from: mC */
                private float f94mC;
                private boolean mCanceled;

                /* JADX INFO: renamed from: mD */
                private float f95mD;
                protected float mDurationF;
                private final View mPoster;
                protected long mStartTime;

                public interface AnimationListener {
                    void onAnimationFinish(int i, Object obj);
                }

/* 27 */        public SmoothAnimator(View poster, float smoothness, int duration) {
/* 29 */            this.mPoster = poster;
/* 30 */            this.mAlpha = smoothness;
/* 31 */            this.mDurationF = duration;
/* 32 */            float f = 1.0f / ((this.mAlpha * 2.0f) * (1.0f - this.mAlpha));
                    this.f95mD = f;
                    this.f92mA = f;
/* 33 */            this.f93mB = this.mAlpha / ((this.mAlpha - 1.0f) * 2.0f);
/* 34 */            this.f94mC = 1.0f / (1.0f - this.mAlpha);
                }

/* 37 */        public float transform(float frac) {
                    if (frac < this.mAlpha) {
/* 39 */                return this.f92mA * frac * frac;
                    }
                    if (frac < 1.0f - this.mAlpha) {
/* 41 */                return this.f93mB + (this.f94mC * frac);
                    }
/* 43 */            return 1.0f - ((this.f95mD * (frac - 1.0f)) * (frac - 1.0f));
                }

/* 47 */        public void start() {
/* 48 */            this.mStartTime = SystemClock.uptimeMillis();
/* 49 */            this.mCanceled = false;
/* 50 */            onStart();
/* 51 */            this.mPoster.post(this);
                }

/* 54 */        public void cancel() {
/* 55 */            this.mCanceled = true;
                }

/* 58 */        public void onCancel() {
                }

/* 62 */        public void onStart() {
                }

/* 66 */        public void onUpdate(float frac, float smoothFrac) {
                }

/* 70 */        public void onStop() {
                }

                @Override // java.lang.Runnable
/* 74 */        public void run() {
/* 75 */            if (this.mCanceled) {
/* 76 */                onCancel();
/* 89 */                return;
                    }
/* 80 */            float fraction = (SystemClock.uptimeMillis() - this.mStartTime) / this.mDurationF;
                    if (fraction >= 1.0f) {
/* 83 */                onUpdate(1.0f, 1.0f);
/* 84 */                onStop();
                    } else {
/* 86 */                onUpdate(fraction, transform(fraction));
/* 87 */                this.mPoster.post(this);
                    }
                }
            }
