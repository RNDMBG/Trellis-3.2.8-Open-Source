            package co.vine.android.views;

            import android.content.Context;
            import android.util.AttributeSet;
            import android.widget.Switch;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 11 */    public class SwitchImple extends Switch implements SwitchInterface {
/* 12 */        public SwitchImple(Context context) {
/* 13 */            super(context);
                initializeLegacyLabels();
                }

/* 16 */        public SwitchImple(Context context, AttributeSet attrs) {
/* 17 */            super(context, attrs);
                initializeLegacyLabels();
                }

/* 20 */        public SwitchImple(Context context, AttributeSet attrs, int defStyle) {
/* 21 */            super(context, attrs, defStyle);
                initializeLegacyLabels();
                }

                private void initializeLegacyLabels() {
                    setTextOn("");
                    setTextOff("");
                }
            }
