            package co.vine.android.widget;

            import android.content.Context;
            import android.os.Handler;
            import android.os.Message;
            import android.text.Editable;
            import android.text.TextWatcher;
            import android.util.AttributeSet;
            import android.view.View;
            import co.vine.android.views.SdkEditText;
            
            
    public class TypingEditText extends SdkEditText {
                public static final int DEFAULT_TIMEOUT = 500;
                private Handler mHandler;
                private TypingListener mListener;
                private long mTimeout;

                public interface TypingListener {
                    void onTypingTimeout(View view);
                }

        static  Handler access$100(TypingEditText x0) {
            return x0.mHandler;
                }

        static  long access$200(TypingEditText x0) {
            return x0.mTimeout;
                }

        static  TypingListener access$300(TypingEditText x0) {
            return x0.mListener;
                }

        public TypingEditText(Context context) {
            super(context);
            setup();
                }

        public TypingEditText(Context context, AttributeSet attrs) {
            super(context, attrs);
            setup();
                }

        public TypingEditText(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            setup();
                }

        public void setTypingListener(TypingListener listener) {
            this.mListener = listener;
                }

        public void setTimeout(long timeout) {
            this.mTimeout = timeout;
                }

        private void setup() {
            this.mTimeout = 500L;
            this.mHandler = new TypingHandler();
            addTextChangedListener(new TypingTextWatcher(this, null));
                }

        private class TypingTextWatcher implements TextWatcher {
            private TypingTextWatcher() {
                    }

             TypingTextWatcher(TypingEditText x0, Object x1) {
                this();
                    }

                    @Override 
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override 
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TypingEditText.access$100(TypingEditText.this) != null) {
                    TypingEditText.access$100(TypingEditText.this).removeMessages(1);
                    TypingEditText.access$100(TypingEditText.this).sendMessageDelayed(TypingEditText.access$100(TypingEditText.this).obtainMessage(1), TypingEditText.access$200(TypingEditText.this));
                        }
                    }

                    @Override 
            public void afterTextChanged(Editable s) {
                    }
                }

        private class TypingHandler extends Handler {
                    public static final int TYPING = 1;

            public TypingHandler() {
                    }

                    @Override 
            public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 1:
                        if (TypingEditText.access$300(TypingEditText.this) != null) {
                            TypingEditText.access$300(TypingEditText.this).onTypingTimeout(TypingEditText.this);
                                }
                                break;
                        }
                    }
                }
            }
