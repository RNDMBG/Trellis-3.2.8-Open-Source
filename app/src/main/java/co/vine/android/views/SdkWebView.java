            package co.vine.android.views;

            import android.content.Context;
            import android.os.Build;
            import android.util.AttributeSet;
            import android.webkit.WebView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class SdkWebView extends WebView {
/* 14 */        public SdkWebView(Context context) {
/* 15 */            super(context);
/* 16 */            init();
                }

/* 19 */        public SdkWebView(Context context, AttributeSet attrs) {
/* 20 */            super(context, attrs);
/* 21 */            init();
                }

/* 24 */        public SdkWebView(Context context, AttributeSet attrs, int defStyle) {
/* 25 */            super(context, attrs, defStyle);
/* 26 */            init();
                }

/* 29 */        private void init() {
/* 30 */            if (Build.VERSION.SDK_INT <= 14) {
/* 31 */                setScrollBarStyle(0);
                    }
                }
            }
