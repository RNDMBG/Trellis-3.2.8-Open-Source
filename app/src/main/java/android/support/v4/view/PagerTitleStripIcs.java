            package android.support.v4.view;

            import android.content.Context;
            import android.text.method.SingleLineTransformationMethod;
            import android.view.View;
            import android.widget.TextView;
            import java.util.Locale;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    class PagerTitleStripIcs {
/* 25 */        PagerTitleStripIcs() {
                }

/* 27 */        public static void setSingleLineAllCaps(TextView text) {
/* 28 */            text.setTransformationMethod(new SingleLineAllCapsTransform(text.getContext()));
                }

/* 34 */        private static class SingleLineAllCapsTransform extends SingleLineTransformationMethod {
                    private static final String TAG = "SingleLineAllCapsTransform";
                    private Locale mLocale;

/* 35 */            public SingleLineAllCapsTransform(Context context) {
/* 37 */                this.mLocale = context.getResources().getConfiguration().locale;
                    }

                    @Override // android.text.method.ReplacementTransformationMethod, android.text.method.TransformationMethod
/* 41 */            public CharSequence getTransformation(CharSequence source, View view) {
/* 42 */                CharSequence source2 = super.getTransformation(source, view);
/* 43 */                if (source2 != null) {
                            return source2.toString().toUpperCase(this.mLocale);
                        }
                        return null;
                    }
                }
            }
