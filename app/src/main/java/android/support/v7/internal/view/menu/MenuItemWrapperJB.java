            package android.support.v7.internal.view.menu;

            import android.support.v4.view.ActionProvider;
            import android.view.MenuItem;
            import android.view.View;
            
            
    class MenuItemWrapperJB extends MenuItemWrapperICS {
        MenuItemWrapperJB(MenuItem object) {
            super(object, false);
                }

                @Override 
        MenuItemWrapperICS.ActionProviderWrapper createActionProviderWrapper(ActionProvider provider) {
            return new ActionProviderWrapperJB(provider);
                }

        class ActionProviderWrapperJB extends MenuItemWrapperICS.ActionProviderWrapper implements ActionProvider.VisibilityListener {
                    android.view.ActionProvider.VisibilityListener mListener;

            public ActionProviderWrapperJB(ActionProvider inner) {
                super(inner);
                    }

                    @Override 
            public View onCreateActionView(MenuItem forItem) {
                return this.mInner.onCreateActionView(forItem);
                    }

                    @Override 
            public boolean overridesItemVisibility() {
                return this.mInner.overridesItemVisibility();
                    }

                    @Override 
            public boolean isVisible() {
                return this.mInner.isVisible();
                    }

                    @Override 
            public void refreshVisibility() {
                this.mInner.refreshVisibility();
                    }

                    @Override 
            public void setVisibilityListener(android.view.ActionProvider.VisibilityListener listener) {
                this.mListener = listener;
                ActionProvider actionProvider = this.mInner;
                        if (listener == null) {
                    this = null;
                        }
                        actionProvider.setVisibilityListener(this);
                    }

                    @Override 
            public void onActionProviderVisibilityChanged(boolean isVisible) {
                if (this.mListener != null) {
                    this.mListener.onActionProviderVisibilityChanged(isVisible);
                        }
                    }
                }
            }
