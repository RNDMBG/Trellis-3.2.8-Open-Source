            package android.support.v7.internal.view;

            import android.content.Context;
            import android.view.ActionMode;
            
            
    public class ActionModeWrapperJB extends ActionModeWrapper {
        public ActionModeWrapperJB(Context context, ActionMode frameworkActionMode) {
            super(context, frameworkActionMode);
                }

                @Override 
        public boolean getTitleOptionalHint() {
            return this.mWrappedObject.getTitleOptionalHint();
                }

                @Override 
        public void setTitleOptionalHint(boolean titleOptional) {
            this.mWrappedObject.setTitleOptionalHint(titleOptional);
                }

                @Override 
        public boolean isTitleOptional() {
            return this.mWrappedObject.isTitleOptional();
                }

        public static class CallbackWrapper extends ActionModeWrapper.CallbackWrapper {
            public CallbackWrapper(Context context, android.support.v7.view.ActionMode.Callback supportCallback) {
                super(context, supportCallback);
                    }

                    @Override 
            protected ActionModeWrapper createActionModeWrapper(Context context, ActionMode mode) {
                return new ActionModeWrapperJB(context, mode);
                    }
                }
            }
