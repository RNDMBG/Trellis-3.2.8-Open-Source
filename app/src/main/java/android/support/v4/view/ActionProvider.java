            package android.support.v4.view;

            import android.content.Context;
            import android.util.Log;
            import android.view.MenuItem;
            import android.view.SubMenu;
            import android.view.View;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 76 */    public abstract class ActionProvider {
                private static final String TAG = "ActionProvider(support)";
                private final Context mContext;
                private SubUiVisibilityListener mSubUiVisibilityListener;
                private VisibilityListener mVisibilityListener;

                public interface SubUiVisibilityListener {
                    void onSubUiVisibilityChanged(boolean z);
                }

                public interface VisibilityListener {
                    void onActionProviderVisibilityChanged(boolean z);
                }

                public abstract View onCreateActionView();

/* 77 */        public ActionProvider(Context context) {
/* 79 */            this.mContext = context;
                }

/* 85 */        public Context getContext() {
/* 86 */            return this.mContext;
                }

/* 108 */       public View onCreateActionView(MenuItem forItem) {
/* 109 */           return onCreateActionView();
                }

/* 120 */       public boolean overridesItemVisibility() {
/* 121 */           return false;
                }

/* 134 */       public boolean isVisible() {
/* 135 */           return true;
                }

/* 144 */       public void refreshVisibility() {
/* 145 */           if (this.mVisibilityListener != null && overridesItemVisibility()) {
/* 146 */               this.mVisibilityListener.onActionProviderVisibilityChanged(isVisible());
                    }
                }

/* 181 */       public boolean onPerformDefaultAction() {
/* 182 */           return false;
                }

/* 194 */       public boolean hasSubMenu() {
/* 195 */           return false;
                }

/* 207 */       public void onPrepareSubMenu(SubMenu subMenu) {
                }

/* 216 */       public void subUiVisibilityChanged(boolean isVisible) {
/* 217 */           if (this.mSubUiVisibilityListener != null) {
/* 218 */               this.mSubUiVisibilityListener.onSubUiVisibilityChanged(isVisible);
                    }
                }

/* 225 */       public void setSubUiVisibilityListener(SubUiVisibilityListener listener) {
/* 226 */           this.mSubUiVisibilityListener = listener;
                }

/* 235 */       public void setVisibilityListener(VisibilityListener listener) {
/* 236 */           if (this.mVisibilityListener != null && listener != null) {
/* 237 */               Log.w(TAG, "setVisibilityListener: Setting a new ActionProvider.VisibilityListener when one is already set. Are you reusing this " + getClass().getSimpleName() + " instance while it is still in use somewhere else?");
                    }
/* 241 */           this.mVisibilityListener = listener;
                }
            }
