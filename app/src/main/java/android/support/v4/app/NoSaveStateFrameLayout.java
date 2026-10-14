            package android.support.v4.app;

            import android.content.Context;
            import android.os.Parcelable;
            import android.util.SparseArray;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.FrameLayout;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 30 */    class NoSaveStateFrameLayout extends FrameLayout {
/* 31 */        static ViewGroup wrap(View child) {
/* 32 */            NoSaveStateFrameLayout wrapper = new NoSaveStateFrameLayout(child.getContext());
/* 33 */            ViewGroup.LayoutParams childParams = child.getLayoutParams();
/* 34 */            if (childParams != null) {
/* 35 */                wrapper.setLayoutParams(childParams);
                    }
/* 37 */            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-1, -1);
/* 39 */            child.setLayoutParams(lp);
/* 40 */            wrapper.addView(child);
/* 41 */            return wrapper;
                }

/* 44 */        public NoSaveStateFrameLayout(Context context) {
/* 45 */            super(context);
                }

                @Override // android.view.ViewGroup, android.view.View
/* 52 */        protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
/* 53 */            dispatchFreezeSelfOnly(container);
                }

                @Override // android.view.ViewGroup, android.view.View
/* 60 */        protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
/* 61 */            dispatchThawSelfOnly(container);
                }
            }
