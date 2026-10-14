            package android.support.v7.internal.widget;

            import android.content.Context;
            import android.content.res.TypedArray;
            import android.graphics.Rect;
            import android.support.v7.appcompat.C0084R;
            import android.text.method.TransformationMethod;
            import android.util.AttributeSet;
            import android.view.View;
            import android.widget.TextView;
            import java.util.Locale;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    public class CompatTextView extends TextView {
/* 35 */        public CompatTextView(Context context) {
/* 36 */            this(context, null);
                }

/* 39 */        public CompatTextView(Context context, AttributeSet attrs) {
/* 40 */            this(context, attrs, 0);
                }

/* 43 */        public CompatTextView(Context context, AttributeSet attrs, int defStyle) {
/* 44 */            super(context, attrs, defStyle);
/* 48 */            TypedArray style = context.obtainStyledAttributes(attrs, C0084R.styleable.CompatTextView, defStyle, 0);
/* 50 */            boolean allCaps = style.getBoolean(0, false);
/* 51 */            style.recycle();
/* 56 */            if (allCaps) {
/* 57 */                setTransformationMethod(new AllCapsTransformationMethod(context));
                    }
                }

/* 66 */        private static class AllCapsTransformationMethod implements TransformationMethod {
                    private final Locale mLocale;

/* 67 */            public AllCapsTransformationMethod(Context context) {
/* 69 */                this.mLocale = context.getResources().getConfiguration().locale;
                    }

                    @Override // android.text.method.TransformationMethod
/* 73 */            public CharSequence getTransformation(CharSequence source, View view) {
/* 74 */                if (source != null) {
                            return source.toString().toUpperCase(this.mLocale);
                        }
                        return null;
                    }

                    @Override // android.text.method.TransformationMethod
/* 79 */            public void onFocusChanged(View view, CharSequence charSequence, boolean b, int i, Rect rect) {
                    }
                }
            }
