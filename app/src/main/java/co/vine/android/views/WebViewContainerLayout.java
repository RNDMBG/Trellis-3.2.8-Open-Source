            package co.vine.android.views;

            import android.annotation.TargetApi;
            import android.content.Context;
            import android.os.Looper;
            import android.util.AttributeSet;
            import android.webkit.WebView;
            import android.widget.FrameLayout;
            import co.vine.android.util.CrashUtil;
            
            
    public class WebViewContainerLayout extends FrameLayout {
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

        static  void access$000(WebViewContainerLayout x0) {
            x0.tryAddWebView();
                }

                
        class RunnableC05471 implements Runnable {
            RunnableC05471() {
                    }

                    @Override 
            public void run() {
                WebViewContainerLayout.access$000(WebViewContainerLayout.this);
                    }
                }

        public WebViewContainerLayout(Context context) {
            super(context);
            this.mTryAddRunnable = new RunnableC05471();
                }

        public WebViewContainerLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.mTryAddRunnable = new RunnableC05471();
                }

        public WebViewContainerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            this.mTryAddRunnable = new RunnableC05471();
                }

                @TargetApi(21)
        public WebViewContainerLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            this.mTryAddRunnable = new RunnableC05471();
                }

        public WebView getWebView() {
            return this.mWebView;
                }

        public void onResume() {
            checkLooper();
            this.mIsResumed = true;
            tryAddWebView();
                }

        public void onPause() {
            checkLooper();
            this.mIsResumed = false;
                }

        public void onDestroy() {
            checkLooper();
            if (this.mWebView != null) {
                removeView(this.mWebView);
                this.mWebView.destroy();
                this.mWebView = null;
                    }
                }

        private void checkLooper() {
            if (Looper.getMainLooper() != Looper.myLooper()) {
                throw new RuntimeException("You cannot call this method on non-UI thread.");
                    }
                }

        public void onViewCreated() {
            this.mIsResumed = true;
            tryAddWebView();
                }

        private void tryAddWebView() {
           checkLooper();
           removeCallbacks(this.mTryAddRunnable);
           if (this.mIsResumed && this.mWebView == null) {
               SdkWebView webView = null;
                        try {
                   SdkWebView webView2 = new SdkWebView(getContext());
                            try {
                       this.noOpCount = 0;
                                webView = webView2;
                            } catch (Exception e) {
                       e = e;
                                webView = webView2;
                       this.noOpCount++;
                       CrashUtil.logException(e);
                       if (this.noOpCount < 20) {
                           postDelayed(this.mTryAddRunnable, 1000L);
                                }
                            }
                        } catch (Exception e2) {
                            CrashUtil.logException(e2);
                            if (this.noOpCount < 20) {
                                postDelayed(this.mTryAddRunnable, 1000L);
                            }
                        }
               if (webView != null) {
                   this.mWebView = webView;
                   addView(webView);
                   if (this.mInitializeListener != null) {
                       this.mInitializeListener.onInitialized(webView);
                            }
                        }
                    }
                }

       public void setInitializationListener(OnWebViewInitializedListener listener) {
           this.mInitializeListener = listener;
                }
            }
