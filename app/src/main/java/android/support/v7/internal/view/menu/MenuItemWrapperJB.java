            package android.support.v7.internal.view.menu;

            import android.support.v4.view.ActionProvider;
            import android.view.MenuItem;
            import android.view.View;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    class MenuItemWrapperJB extends MenuItemWrapperICS {
/* 25 */        MenuItemWrapperJB(MenuItem object) {
/* 26 */            super(object, false);
                }

                @Override // android.support.v7.internal.view.menu.MenuItemWrapperICS
/* 30 */        MenuItemWrapperICS.ActionProviderWrapper createActionProviderWrapper(ActionProvider provider) {
/* 31 */            return new ActionProviderWrapperJB(provider);
                }

/* 36 */        class ActionProviderWrapperJB extends MenuItemWrapperICS.ActionProviderWrapper implements ActionProvider.VisibilityListener {
                    android.view.ActionProvider.VisibilityListener mListener;

/* 37 */            public ActionProviderWrapperJB(ActionProvider inner) {
/* 39 */                super(inner);
                    }

                    @Override // android.view.ActionProvider
/* 43 */            public View onCreateActionView(MenuItem forItem) {
/* 44 */                return this.mInner.onCreateActionView(forItem);
                    }

                    @Override // android.view.ActionProvider
/* 48 */            public boolean overridesItemVisibility() {
/* 49 */                return this.mInner.overridesItemVisibility();
                    }

                    @Override // android.view.ActionProvider
/* 53 */            public boolean isVisible() {
/* 54 */                return this.mInner.isVisible();
                    }

                    @Override // android.view.ActionProvider
/* 58 */            public void refreshVisibility() {
/* 59 */                this.mInner.refreshVisibility();
                    }

                    @Override // android.view.ActionProvider
/* 64 */            public void setVisibilityListener(android.view.ActionProvider.VisibilityListener listener) {
/* 65 */                this.mListener = listener;
/* 66 */                ActionProvider actionProvider = this.mInner;
                        if (listener == null) {
/* 66 */                    this = null;
                        }
                        actionProvider.setVisibilityListener(this);
                    }

                    @Override // android.support.v4.view.ActionProvider.VisibilityListener
/* 70 */            public void onActionProviderVisibilityChanged(boolean isVisible) {
/* 71 */                if (this.mListener != null) {
/* 72 */                    this.mListener.onActionProviderVisibilityChanged(isVisible);
                        }
                    }
                }
            }
