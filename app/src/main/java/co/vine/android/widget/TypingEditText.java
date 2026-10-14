            package co.vine.android.widget;

            import android.content.Context;
            import android.os.Handler;
            import android.os.Message;
            import android.text.Editable;
            import android.text.TextWatcher;
            import android.util.AttributeSet;
            import android.view.View;
            import co.vine.android.views.SdkEditText;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 16 */    public class TypingEditText extends SdkEditText {
                public static final int DEFAULT_TIMEOUT = 500;
                private Handler mHandler;
                private TypingListener mListener;
                private long mTimeout;

                public interface TypingListener {
                    void onTypingTimeout(View view);
                }

/* 17 */        static /* synthetic */ Handler access$100(TypingEditText x0) {
/* 18 */            return x0.mHandler;
                }

/* 17 */        static /* synthetic */ long access$200(TypingEditText x0) {
/* 18 */            return x0.mTimeout;
                }

/* 17 */        static /* synthetic */ TypingListener access$300(TypingEditText x0) {
/* 18 */            return x0.mListener;
                }

/* 30 */        public TypingEditText(Context context) {
/* 31 */            super(context);
/* 32 */            setup();
                }

/* 36 */        public TypingEditText(Context context, AttributeSet attrs) {
/* 37 */            super(context, attrs);
/* 38 */            setup();
                }

/* 42 */        public TypingEditText(Context context, AttributeSet attrs, int defStyle) {
/* 43 */            super(context, attrs, defStyle);
/* 44 */            setup();
                }

/* 47 */        public void setTypingListener(TypingListener listener) {
/* 48 */            this.mListener = listener;
                }

/* 55 */        public void setTimeout(long timeout) {
/* 56 */            this.mTimeout = timeout;
                }

/* 59 */        private void setup() {
/* 60 */            this.mTimeout = 500L;
/* 61 */            this.mHandler = new TypingHandler();
/* 62 */            addTextChangedListener(new TypingTextWatcher(this, null));
                }

/* 63 */        private class TypingTextWatcher implements TextWatcher {
/* 64 */            private TypingTextWatcher() {
                    }

/* 64 */            /* synthetic */ TypingTextWatcher(TypingEditText x0, Object x1) {
/* 65 */                this();
                    }

                    @Override // android.text.TextWatcher
/* 68 */            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override // android.text.TextWatcher
/* 72 */            public void onTextChanged(CharSequence s, int start, int before, int count) {
/* 73 */                if (TypingEditText.access$100(TypingEditText.this) != null) {
/* 74 */                    TypingEditText.access$100(TypingEditText.this).removeMessages(1);
/* 75 */                    TypingEditText.access$100(TypingEditText.this).sendMessageDelayed(TypingEditText.access$100(TypingEditText.this).obtainMessage(1), TypingEditText.access$200(TypingEditText.this));
                        }
                    }

                    @Override // android.text.TextWatcher
/* 80 */            public void afterTextChanged(Editable s) {
                    }
                }

/* 85 */        private class TypingHandler extends Handler {
                    public static final int TYPING = 1;

/* 86 */            public TypingHandler() {
                    }

                    @Override // android.os.Handler
/* 91 */            public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 1:
/* 94 */                        if (TypingEditText.access$300(TypingEditText.this) != null) {
/* 95 */                            TypingEditText.access$300(TypingEditText.this).onTypingTimeout(TypingEditText.this);
                                }
                                break;
                        }
                    }
                }
            }
