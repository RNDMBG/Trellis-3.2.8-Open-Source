            package android.support.v4.internal.view;

            import android.support.v4.view.ActionProvider;
            import android.support.v4.view.MenuItemCompat;
            import android.view.MenuItem;
            import android.view.View;
            
            
            public interface SupportMenuItem extends MenuItem {
                public static final int SHOW_AS_ACTION_ALWAYS = 2;
                public static final int SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW = 8;
                public static final int SHOW_AS_ACTION_IF_ROOM = 1;
                public static final int SHOW_AS_ACTION_NEVER = 0;
                public static final int SHOW_AS_ACTION_WITH_TEXT = 4;

                @Override 
                boolean collapseActionView();

                @Override 
                boolean expandActionView();

                @Override 
                View getActionView();

                ActionProvider getSupportActionProvider();

                @Override 
                boolean isActionViewExpanded();

                @Override 
                MenuItem setActionView(int i);

                @Override 
                MenuItem setActionView(View view);

                @Override 
                void setShowAsAction(int i);

                @Override 
                MenuItem setShowAsActionFlags(int i);

                SupportMenuItem setSupportActionProvider(ActionProvider actionProvider);

                SupportMenuItem setSupportOnActionExpandListener(MenuItemCompat.OnActionExpandListener onActionExpandListener);
            }
