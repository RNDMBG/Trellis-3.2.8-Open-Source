            package android.support.v7.view;

            import android.view.Menu;
            import android.view.MenuInflater;
            import android.view.MenuItem;
            import android.view.View;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 57 */    public abstract class ActionMode {
                private Object mTag;
                private boolean mTitleOptionalHint;

                public interface Callback {
                    boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem);

                    boolean onCreateActionMode(ActionMode actionMode, Menu menu);

                    void onDestroyActionMode(ActionMode actionMode);

                    boolean onPrepareActionMode(ActionMode actionMode, Menu menu);
                }

                public abstract void finish();

                public abstract View getCustomView();

                public abstract Menu getMenu();

                public abstract MenuInflater getMenuInflater();

                public abstract CharSequence getSubtitle();

                public abstract CharSequence getTitle();

                public abstract void invalidate();

                public abstract void setCustomView(View view);

                public abstract void setSubtitle(int i);

                public abstract void setSubtitle(CharSequence charSequence);

                public abstract void setTitle(int i);

                public abstract void setTitle(CharSequence charSequence);

/* 58 */        public void setTag(Object tag) {
/* 59 */            this.mTag = tag;
                }

/* 71 */        public Object getTag() {
/* 72 */            return this.mTag;
                }

/* 128 */       public void setTitleOptionalHint(boolean titleOptional) {
/* 129 */           this.mTitleOptionalHint = titleOptional;
                }

/* 138 */       public boolean getTitleOptionalHint() {
/* 139 */           return this.mTitleOptionalHint;
                }

/* 146 */       public boolean isTitleOptional() {
/* 147 */           return false;
                }

/* 215 */       public boolean isUiFocusable() {
/* 216 */           return true;
                }
            }
