            package co.vine.android.views;

            import android.annotation.TargetApi;
            import android.content.Context;
            import android.os.Looper;
            import android.util.AttributeSet;
            import android.webkit.WebView;
            import android.widget.FrameLayout;
            import co.vine.android.util.CrashUtil;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    public class WebViewContainerLayout extends FrameLayout {
                private static final int ADD_DELAY_MS = 1000;
                private static final int MAX_NO_OP_COUNT = 20;
                private OnWebViewInitializedListener mInitializeListener;
                private boolean mIsResumed;
                private final Runnable mTryAddRunnable;
                private WebView mWebView;
                private int noOpCount;

                public interface OnWebViewInitializedListener {
                    void onInitialized(WebView webView);
                }

/* 21 */        static /* synthetic */ void access$000(WebViewContainerLayout x0) {
/* 22 */            x0.tryAddWebView();
                }

                /* JADX INFO: renamed from: co.vine.android.views.WebViewContainerLayout$1 */
/* 38 */        class RunnableC05471 implements Runnable {
/* 39 */            RunnableC05471() {
                    }

                    @Override // java.lang.Runnable
/* 42 */            public void run() {
/* 43 */                WebViewContainerLayout.access$000(WebViewContainerLayout.this);
                    }
                }

/* 47 */        public WebViewContainerLayout(Context context) {
/* 48 */            super(context);
/* 40 */            this.mTryAddRunnable = new RunnableC05471();
                }

/* 51 */        public WebViewContainerLayout(Context context, AttributeSet attrs) {
/* 52 */            super(context, attrs);
/* 40 */            this.mTryAddRunnable = new RunnableC05471();
                }

/* 55 */        public WebViewContainerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
/* 56 */            super(context, attrs, defStyleAttr);
/* 40 */            this.mTryAddRunnable = new RunnableC05471();
                }

                @TargetApi(21)
/* 60 */        public WebViewContainerLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
/* 61 */            super(context, attrs, defStyleAttr, defStyleRes);
/* 40 */            this.mTryAddRunnable = new RunnableC05471();
                }

/* 64 */        public WebView getWebView() {
/* 65 */            return this.mWebView;
                }

/* 68 */        public void onResume() {
/* 69 */            checkLooper();
/* 70 */            this.mIsResumed = true;
/* 71 */            tryAddWebView();
                }

/* 74 */        public void onPause() {
/* 75 */            checkLooper();
/* 76 */            this.mIsResumed = false;
                }

/* 79 */        public void onDestroy() {
/* 80 */            checkLooper();
/* 81 */            if (this.mWebView != null) {
/* 82 */                removeView(this.mWebView);
/* 83 */                this.mWebView.destroy();
/* 84 */                this.mWebView = null;
                    }
                }

/* 88 */        private void checkLooper() {
/* 89 */            if (Looper.getMainLooper() != Looper.myLooper()) {
/* 90 */                throw new RuntimeException("You cannot call this method on non-UI thread.");
                    }
                }

/* 94 */        public void onViewCreated() {
/* 95 */            this.mIsResumed = true;
/* 96 */            tryAddWebView();
                }

/* 99 */        private void tryAddWebView() {
/* 100 */           checkLooper();
/* 101 */           removeCallbacks(this.mTryAddRunnable);
/* 102 */           if (this.mIsResumed && this.mWebView == null) {
/* 103 */               SdkWebView webView = null;
                        try {
/* 105 */                   SdkWebView webView2 = new SdkWebView(getContext());
                            try {
/* 106 */                       this.noOpCount = 0;
                                webView = webView2;
                            } catch (Exception e) {
/* 107 */                       e = e;
                                webView = webView2;
/* 108 */                       this.noOpCount++;
/* 109 */                       CrashUtil.logException(e);
/* 110 */                       if (this.noOpCount < 20) {
/* 111 */                           postDelayed(this.mTryAddRunnable, 1000L);
                                }
                            }
                        } catch (Exception e2) {
                            CrashUtil.logException(e2);
                            if (this.noOpCount < 20) {
                                postDelayed(this.mTryAddRunnable, 1000L);
                            }
                        }
/* 114 */               if (webView != null) {
/* 115 */                   this.mWebView = webView;
/* 116 */                   addView(webView);
/* 117 */                   if (this.mInitializeListener != null) {
/* 118 */                       this.mInitializeListener.onInitialized(webView);
                            }
                        }
                    }
                }

/* 124 */       public void setInitializationListener(OnWebViewInitializedListener listener) {
/* 125 */           this.mInitializeListener = listener;
                }
            }
