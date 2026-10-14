            package co.vine.android.views;

            import android.app.Activity;
            import android.view.KeyEvent;
            import android.view.MotionEvent;
            import android.view.View;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class FakeKeyEventTouchListener implements View.OnTouchListener {
                private final Activity mActivity;
                private final int mCode;

/* 15 */        public FakeKeyEventTouchListener(Activity activity, int code) {
/* 17 */            this.mActivity = activity;
/* 18 */            this.mCode = code;
                }

                /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                @Override // android.view.View.OnTouchListener
/* 22 */        public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case 0:
/* 25 */                    this.mActivity.onKeyDown(this.mCode, new KeyEvent(0, this.mCode));
                            return true;
                        case 1:
/* 29 */                    this.mActivity.onKeyUp(this.mCode, new KeyEvent(1, this.mCode));
                            return true;
                        default:
                            return true;
                    }
                }
            }
