            package android.support.v7.internal.widget;

            import android.content.Context;
            import android.util.AttributeSet;
            import android.view.ActionMode;
            import android.view.View;
            import android.widget.LinearLayout;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    public class NativeActionModeAwareLayout extends LinearLayout {
                private OnActionModeForChildListener mActionModeForChildListener;

                public interface OnActionModeForChildListener {
                    ActionMode.Callback onActionModeForChild(ActionMode.Callback callback);
                }

/* 32 */        public NativeActionModeAwareLayout(Context context, AttributeSet attrs) {
/* 33 */            super(context, attrs);
                }

/* 36 */        public void setActionModeForChildListener(OnActionModeForChildListener listener) {
/* 37 */            this.mActionModeForChildListener = listener;
                }

                @Override // android.view.ViewGroup, android.view.ViewParent
/* 41 */        public ActionMode startActionModeForChild(View originalView, ActionMode.Callback callback) {
/* 42 */            if (this.mActionModeForChildListener != null) {
/* 43 */                callback = this.mActionModeForChildListener.onActionModeForChild(callback);
                    }
/* 45 */            return super.startActionModeForChild(originalView, callback);
                }
            }
