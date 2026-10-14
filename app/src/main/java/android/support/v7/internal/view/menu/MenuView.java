            package android.support.v7.internal.view.menu;

            import android.graphics.drawable.Drawable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface MenuView {

                public interface ItemView {
                    MenuItemImpl getItemData();

                    void initialize(MenuItemImpl menuItemImpl, int i);

                    boolean prefersCondensedTitle();

                    void setCheckable(boolean z);

                    void setChecked(boolean z);

                    void setEnabled(boolean z);

                    void setIcon(Drawable drawable);

                    void setShortcut(boolean z, char c);

                    void setTitle(CharSequence charSequence);

                    boolean showsIcon();
                }

                int getWindowAnimations();

                void initialize(MenuBuilder menuBuilder);
            }
