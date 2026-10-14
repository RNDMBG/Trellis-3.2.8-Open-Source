            package android.support.v7.internal.view;

            import android.content.Context;
            import android.support.v7.internal.view.menu.MenuWrapperFactory;
            import android.support.v7.view.ActionMode;
            import android.view.Menu;
            import android.view.MenuInflater;
            import android.view.MenuItem;
            import android.view.View;
            
            
    public class ActionModeWrapper extends ActionMode {
                final MenuInflater mInflater;
                final android.view.ActionMode mWrappedObject;

        public ActionModeWrapper(Context context, android.view.ActionMode frameworkActionMode) {
            this.mWrappedObject = frameworkActionMode;
            this.mInflater = new SupportMenuInflater(context);
                }

                @Override 
        public Object getTag() {
            return this.mWrappedObject.getTag();
                }

                @Override 
        public void setTag(Object tag) {
            this.mWrappedObject.setTag(tag);
                }

                @Override 
        public void setTitle(CharSequence title) {
            this.mWrappedObject.setTitle(title);
                }

                @Override 
        public void setSubtitle(CharSequence subtitle) {
            this.mWrappedObject.setSubtitle(subtitle);
                }

                @Override 
        public void invalidate() {
            this.mWrappedObject.invalidate();
                }

                @Override 
        public void finish() {
            this.mWrappedObject.finish();
                }

                @Override 
        public Menu getMenu() {
            return MenuWrapperFactory.createMenuWrapper(this.mWrappedObject.getMenu());
                }

                @Override 
        public CharSequence getTitle() {
            return this.mWrappedObject.getTitle();
                }

                @Override 
        public void setTitle(int resId) {
            this.mWrappedObject.setTitle(resId);
                }

                @Override 
        public CharSequence getSubtitle() {
            return this.mWrappedObject.getSubtitle();
                }

                @Override 
        public void setSubtitle(int resId) {
            this.mWrappedObject.setSubtitle(resId);
                }

                @Override 
        public View getCustomView() {
            return this.mWrappedObject.getCustomView();
                }

                @Override 
       public void setCustomView(View view) {
           this.mWrappedObject.setCustomView(view);
                }

                @Override 
       public MenuInflater getMenuInflater() {
           return this.mInflater;
                }

       public static class CallbackWrapper implements android.view.ActionMode.Callback {
                    final Context mContext;
                    private ActionModeWrapper mLastStartedActionMode;
                    final ActionMode.Callback mWrappedCallback;

           public CallbackWrapper(Context context, ActionMode.Callback supportCallback) {
               this.mContext = context;
               this.mWrappedCallback = supportCallback;
                    }

                    @Override 
           public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
               return this.mWrappedCallback.onCreateActionMode(getActionModeWrapper(mode), MenuWrapperFactory.createMenuWrapper(menu));
                    }

                    @Override 
           public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
               return this.mWrappedCallback.onPrepareActionMode(getActionModeWrapper(mode), MenuWrapperFactory.createMenuWrapper(menu));
                    }

                    @Override 
           public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
               return this.mWrappedCallback.onActionItemClicked(getActionModeWrapper(mode), MenuWrapperFactory.createMenuItemWrapper(item));
                    }

                    @Override 
           public void onDestroyActionMode(android.view.ActionMode mode) {
               this.mWrappedCallback.onDestroyActionMode(getActionModeWrapper(mode));
                    }

           public void setLastStartedActionMode(ActionModeWrapper modeWrapper) {
               this.mLastStartedActionMode = modeWrapper;
                    }

           private ActionMode getActionModeWrapper(android.view.ActionMode mode) {
                        return (this.mLastStartedActionMode == null || this.mLastStartedActionMode.mWrappedObject != mode) ? createActionModeWrapper(this.mContext, mode) : this.mLastStartedActionMode;
                    }

           protected ActionModeWrapper createActionModeWrapper(Context context, android.view.ActionMode mode) {
               return new ActionModeWrapper(context, mode);
                    }
                }
            }
