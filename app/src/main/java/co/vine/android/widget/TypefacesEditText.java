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
            
            
    public class TypefacesEditText extends SdkEditText {
                private KeyboardListener mKeyboardListener;
                private int mWeight;

                public interface KeyboardListener {
                    void onKeyboardDismissed();

                    boolean sendKeyEvent(KeyEvent keyEvent);
                }

        static  KeyboardListener access$000(TypefacesEditText x0) {
            return x0.mKeyboardListener;
                }

        public TypefacesEditText(Context context) {
            super(context);
            this.mKeyboardListener = null;
                }

        public TypefacesEditText(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.mKeyboardListener = null;
            init(context, attrs);
                }

        public TypefacesEditText(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            this.mKeyboardListener = null;
            init(context, attrs);
                }

        public void init(Context context, AttributeSet attrs) {
            TypedArray a = context.obtainStyledAttributes(attrs, C0257R.styleable.VineTextView, C0257R.attr.vineEditTextStyle, 0);
            int style = a.getInt(1, 0);
            this.mWeight = a.getInt(0, 2);
            a.recycle();
            setTypeface(Typefaces.get(context).getContentTypeface(style, this.mWeight), style);
                }

                @Override 
        public void setTypeface(Typeface tf, int style) {
            super.setTypeface(Typefaces.get(getContext()).getContentTypeface(style, this.mWeight));
                }

        public void setKeyboardListener(KeyboardListener listener) {
            this.mKeyboardListener = listener;
                }

                @Override 
        public boolean onKeyPreIme(int keyCode, @NonNull KeyEvent keyEvent) {
            if (keyCode == 4 && this.mKeyboardListener != null) {
                this.mKeyboardListener.onKeyboardDismissed();
                    }
            return super.onKeyPreIme(keyCode, keyEvent);
                }

                @Override 
        public InputConnection onCreateInputConnection(@NonNull EditorInfo outAttrs) {
            return new TypeFacesInputConnection(super.onCreateInputConnection(outAttrs), true);
                }

        private class TypeFacesInputConnection extends InputConnectionWrapper {
            public TypeFacesInputConnection(InputConnection target, boolean mutable) {
                super(target, mutable);
                    }

                    @Override 
            public boolean sendKeyEvent(KeyEvent event) {
                        return (TypefacesEditText.access$000(TypefacesEditText.this) != null && TypefacesEditText.access$000(TypefacesEditText.this).sendKeyEvent(event)) || super.sendKeyEvent(event);
                    }
                }
            }
