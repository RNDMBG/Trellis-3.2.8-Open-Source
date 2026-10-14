            package co.vine.android.views;

            import android.widget.CompoundButton;
            
            
            public interface SwitchInterface {
                boolean isChecked();

                void setChecked(boolean z);

                void setEnabled(boolean z);

                void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener);
            }
