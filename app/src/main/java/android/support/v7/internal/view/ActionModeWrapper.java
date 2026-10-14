            package android.support.v7.internal.view;

            import android.content.Context;
            import android.support.v7.internal.view.menu.MenuWrapperFactory;
            import android.support.v7.view.ActionMode;
            import android.view.Menu;
            import android.view.MenuInflater;
            import android.view.MenuItem;
            import android.view.View;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public class ActionModeWrapper extends ActionMode {
                final MenuInflater mInflater;
                final android.view.ActionMode mWrappedObject;

/* 33 */        public ActionModeWrapper(Context context, android.view.ActionMode frameworkActionMode) {
/* 35 */            this.mWrappedObject = frameworkActionMode;
/* 36 */            this.mInflater = new SupportMenuInflater(context);
                }

                @Override // android.support.v7.view.ActionMode
/* 40 */        public Object getTag() {
/* 41 */            return this.mWrappedObject.getTag();
                }

                @Override // android.support.v7.view.ActionMode
/* 45 */        public void setTag(Object tag) {
/* 46 */            this.mWrappedObject.setTag(tag);
                }

                @Override // android.support.v7.view.ActionMode
/* 50 */        public void setTitle(CharSequence title) {
/* 51 */            this.mWrappedObject.setTitle(title);
                }

                @Override // android.support.v7.view.ActionMode
/* 55 */        public void setSubtitle(CharSequence subtitle) {
/* 56 */            this.mWrappedObject.setSubtitle(subtitle);
                }

                @Override // android.support.v7.view.ActionMode
/* 60 */        public void invalidate() {
/* 61 */            this.mWrappedObject.invalidate();
                }

                @Override // android.support.v7.view.ActionMode
/* 65 */        public void finish() {
/* 66 */            this.mWrappedObject.finish();
                }

                @Override // android.support.v7.view.ActionMode
/* 70 */        public Menu getMenu() {
/* 71 */            return MenuWrapperFactory.createMenuWrapper(this.mWrappedObject.getMenu());
                }

                @Override // android.support.v7.view.ActionMode
/* 75 */        public CharSequence getTitle() {
/* 76 */            return this.mWrappedObject.getTitle();
                }

                @Override // android.support.v7.view.ActionMode
/* 80 */        public void setTitle(int resId) {
/* 81 */            this.mWrappedObject.setTitle(resId);
                }

                @Override // android.support.v7.view.ActionMode
/* 85 */        public CharSequence getSubtitle() {
/* 86 */            return this.mWrappedObject.getSubtitle();
                }

                @Override // android.support.v7.view.ActionMode
/* 90 */        public void setSubtitle(int resId) {
/* 91 */            this.mWrappedObject.setSubtitle(resId);
                }

                @Override // android.support.v7.view.ActionMode
/* 95 */        public View getCustomView() {
/* 96 */            return this.mWrappedObject.getCustomView();
                }

                @Override // android.support.v7.view.ActionMode
/* 100 */       public void setCustomView(View view) {
/* 101 */           this.mWrappedObject.setCustomView(view);
                }

                @Override // android.support.v7.view.ActionMode
/* 105 */       public MenuInflater getMenuInflater() {
/* 106 */           return this.mInflater;
                }

/* 116 */       public static class CallbackWrapper implements android.view.ActionMode.Callback {
                    final Context mContext;
                    private ActionModeWrapper mLastStartedActionMode;
                    final ActionMode.Callback mWrappedCallback;

/* 117 */           public CallbackWrapper(Context context, ActionMode.Callback supportCallback) {
/* 119 */               this.mContext = context;
/* 120 */               this.mWrappedCallback = supportCallback;
                    }

                    @Override // android.view.ActionMode.Callback
/* 124 */           public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
/* 125 */               return this.mWrappedCallback.onCreateActionMode(getActionModeWrapper(mode), MenuWrapperFactory.createMenuWrapper(menu));
                    }

                    @Override // android.view.ActionMode.Callback
/* 130 */           public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
/* 131 */               return this.mWrappedCallback.onPrepareActionMode(getActionModeWrapper(mode), MenuWrapperFactory.createMenuWrapper(menu));
                    }

                    @Override // android.view.ActionMode.Callback
/* 137 */           public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
/* 138 */               return this.mWrappedCallback.onActionItemClicked(getActionModeWrapper(mode), MenuWrapperFactory.createMenuItemWrapper(item));
                    }

                    @Override // android.view.ActionMode.Callback
/* 143 */           public void onDestroyActionMode(android.view.ActionMode mode) {
/* 144 */               this.mWrappedCallback.onDestroyActionMode(getActionModeWrapper(mode));
                    }

/* 147 */           public void setLastStartedActionMode(ActionModeWrapper modeWrapper) {
/* 148 */               this.mLastStartedActionMode = modeWrapper;
                    }

/* 151 */           private ActionMode getActionModeWrapper(android.view.ActionMode mode) {
                        return (this.mLastStartedActionMode == null || this.mLastStartedActionMode.mWrappedObject != mode) ? createActionModeWrapper(this.mContext, mode) : this.mLastStartedActionMode;
                    }

/* 161 */           protected ActionModeWrapper createActionModeWrapper(Context context, android.view.ActionMode mode) {
/* 162 */               return new ActionModeWrapper(context, mode);
                    }
                }
            }
