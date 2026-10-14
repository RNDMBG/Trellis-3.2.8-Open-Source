            package co.vine.android.widget;

            import android.content.Context;
            import android.content.res.TypedArray;
            import android.graphics.Typeface;
            import android.support.annotation.NonNull;
            import android.util.AttributeSet;
            import android.view.KeyEvent;
            import android.view.inputmethod.EditorInfo;
            import android.view.inputmethod.InputConnection;
            import android.view.inputmethod.InputConnectionWrapper;
            import co.vine.android.C0257R;
            import co.vine.android.views.SdkEditText;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    public class TypefacesEditText extends SdkEditText {
                private KeyboardListener mKeyboardListener;
                private int mWeight;

                public interface KeyboardListener {
                    void onKeyboardDismissed();

                    boolean sendKeyEvent(KeyEvent keyEvent);
                }

/* 23 */        static /* synthetic */ KeyboardListener access$000(TypefacesEditText x0) {
/* 24 */            return x0.mKeyboardListener;
                }

/* 28 */        public TypefacesEditText(Context context) {
/* 29 */            super(context);
/* 26 */            this.mKeyboardListener = null;
                }

/* 32 */        public TypefacesEditText(Context context, AttributeSet attrs) {
/* 33 */            super(context, attrs);
/* 26 */            this.mKeyboardListener = null;
/* 34 */            init(context, attrs);
                }

/* 37 */        public TypefacesEditText(Context context, AttributeSet attrs, int defStyle) {
/* 38 */            super(context, attrs, defStyle);
/* 26 */            this.mKeyboardListener = null;
/* 39 */            init(context, attrs);
                }

/* 42 */        public void init(Context context, AttributeSet attrs) {
/* 43 */            TypedArray a = context.obtainStyledAttributes(attrs, C0257R.styleable.VineTextView, C0257R.attr.vineEditTextStyle, 0);
/* 45 */            int style = a.getInt(1, 0);
/* 46 */            this.mWeight = a.getInt(0, 2);
/* 47 */            a.recycle();
/* 48 */            setTypeface(Typefaces.get(context).getContentTypeface(style, this.mWeight), style);
                }

                @Override // android.widget.TextView
/* 52 */        public void setTypeface(Typeface tf, int style) {
/* 53 */            super.setTypeface(Typefaces.get(getContext()).getContentTypeface(style, this.mWeight));
                }

/* 56 */        public void setKeyboardListener(KeyboardListener listener) {
/* 57 */            this.mKeyboardListener = listener;
                }

                @Override // android.widget.TextView, android.view.View
/* 61 */        public boolean onKeyPreIme(int keyCode, @NonNull KeyEvent keyEvent) {
/* 62 */            if (keyCode == 4 && this.mKeyboardListener != null) {
/* 64 */                this.mKeyboardListener.onKeyboardDismissed();
                    }
/* 67 */            return super.onKeyPreIme(keyCode, keyEvent);
                }

                @Override // android.widget.TextView, android.view.View
/* 77 */        public InputConnection onCreateInputConnection(@NonNull EditorInfo outAttrs) {
/* 78 */            return new TypeFacesInputConnection(super.onCreateInputConnection(outAttrs), true);
                }

/* 81 */        private class TypeFacesInputConnection extends InputConnectionWrapper {
/* 82 */            public TypeFacesInputConnection(InputConnection target, boolean mutable) {
/* 84 */                super(target, mutable);
                    }

                    @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
/* 88 */            public boolean sendKeyEvent(KeyEvent event) {
                        return (TypefacesEditText.access$000(TypefacesEditText.this) != null && TypefacesEditText.access$000(TypefacesEditText.this).sendKeyEvent(event)) || super.sendKeyEvent(event);
                    }
                }
            }
