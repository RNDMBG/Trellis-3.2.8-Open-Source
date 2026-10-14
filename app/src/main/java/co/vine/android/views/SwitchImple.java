            package co.vine.android.views;

            import android.content.Context;
            import android.util.AttributeSet;
            import android.widget.Switch;
            
            
    public class SwitchImple extends Switch implements SwitchInterface {
        public SwitchImple(Context context) {
            super(context);
                initializeLegacyLabels();
                }

        public SwitchImple(Context context, AttributeSet attrs) {
            super(context, attrs);
                initializeLegacyLabels();
                }

        public SwitchImple(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
                initializeLegacyLabels();
                }

                private void initializeLegacyLabels() {
                    setTextOn("");
                    setTextOff("");
                }
            }
