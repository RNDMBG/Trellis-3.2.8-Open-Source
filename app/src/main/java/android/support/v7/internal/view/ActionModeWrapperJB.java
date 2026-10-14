            package android.support.v7.internal.view;

            import android.content.Context;
            import android.view.ActionMode;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    public class ActionModeWrapperJB extends ActionModeWrapper {
/* 27 */        public ActionModeWrapperJB(Context context, ActionMode frameworkActionMode) {
/* 28 */            super(context, frameworkActionMode);
                }

                @Override // android.support.v7.view.ActionMode
/* 32 */        public boolean getTitleOptionalHint() {
/* 33 */            return this.mWrappedObject.getTitleOptionalHint();
                }

                @Override // android.support.v7.view.ActionMode
/* 37 */        public void setTitleOptionalHint(boolean titleOptional) {
/* 38 */            this.mWrappedObject.setTitleOptionalHint(titleOptional);
                }

                @Override // android.support.v7.view.ActionMode
/* 42 */        public boolean isTitleOptional() {
/* 43 */            return this.mWrappedObject.isTitleOptional();
                }

/* 50 */        public static class CallbackWrapper extends ActionModeWrapper.CallbackWrapper {
/* 51 */            public CallbackWrapper(Context context, android.support.v7.view.ActionMode.Callback supportCallback) {
/* 52 */                super(context, supportCallback);
                    }

                    @Override // android.support.v7.internal.view.ActionModeWrapper.CallbackWrapper
/* 56 */            protected ActionModeWrapper createActionModeWrapper(Context context, ActionMode mode) {
/* 57 */                return new ActionModeWrapperJB(context, mode);
                    }
                }
            }
