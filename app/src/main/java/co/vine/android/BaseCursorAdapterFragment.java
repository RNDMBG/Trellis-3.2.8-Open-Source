            package co.vine.android;

            import android.database.ContentObserver;
            import android.database.Cursor;
            import android.os.Bundle;
            import android.os.Handler;
            import android.support.v4.app.LoaderManager;
            import android.support.v4.content.Loader;
            import android.support.v4.widget.CursorAdapter;
            import co.vine.android.util.CrashUtil;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 15 */    public abstract class BaseCursorAdapterFragment extends BaseAdapterFragment implements LoaderManager.LoaderCallbacks<Cursor> {
                protected static final int LOADER_ID_DEFAULT = 0;
                protected static final int PROGRESS_FIRST_USER = 6;
                protected static final int PROGRESS_LOAD_DATA = 5;
                protected static final int PROGRESS_LOAD_DATA_OLDER = 7;
                protected ChangeObserver mChangeObserver;
                protected CursorAdapter mCursorAdapter;
                private int mLoaderId = 0;
                protected boolean mRefreshing;

                protected abstract void handleContentChanged();


                @Override // android.support.v4.app.Fragment
/* 33 */        public void onDestroy() {
                    Cursor cursor;
/* 34 */            if (this.mCursorAdapter != null && (cursor = this.mCursorAdapter.swapCursor(null)) != null && this.mChangeObserver != null) {
                        try {
/* 41 */                    cursor.unregisterContentObserver(this.mChangeObserver);
                        } catch (IllegalStateException e) {
/* 43 */                    CrashUtil.logException(e, "This is ok, because this cursor probably didn't need a change observer.", new Object[0]);
                        }
                    }
/* 48 */            super.onDestroy();
                }

/* 50 */        protected class ChangeObserver extends ContentObserver {
/* 51 */            public ChangeObserver(Handler handler) {
/* 53 */                super(handler);
                    }

                    @Override // android.database.ContentObserver
/* 57 */            public boolean deliverSelfNotifications() {
/* 58 */                return true;
                    }

                    @Override // android.database.ContentObserver
/* 62 */            public void onChange(boolean selfChange) {
/* 63 */                if (!BaseCursorAdapterFragment.this.mRefreshing) {
/* 64 */                    BaseCursorAdapterFragment.this.handleContentChanged();
                        }
                    }
                }

/* 76 */        protected void initLoader() {
/* 77 */            initLoader(0);
                }

/* 87 */        protected final void initLoader(int loaderId) {
/* 88 */            if (this.mChangeObserver == null) {
/* 89 */                this.mChangeObserver = new ChangeObserver(this.mHandler);
/* 90 */                this.mLoaderId = loaderId;
                    }
/* 92 */            getLoaderManager().initLoader(loaderId, null, this);
                }

/* 101 */       protected boolean restartLoader() {
/* 102 */           return restartLoader(this.mLoaderId);
                }

/* 105 */       protected boolean restartLoader(int loaderId) {
/* 106 */           if (this.mChangeObserver == null) {
/* 107 */               return false;
                    }
/* 109 */           getLoaderManager().restartLoader(loaderId, null, this);
/* 110 */           return true;
                }

/* 118 */       protected void loadCursor(boolean restart) {
                }

                @Override // android.support.v4.app.LoaderManager.LoaderCallbacks
/* 129 */       public Loader<Cursor> onCreateLoader(int id, Bundle args) {
/* 130 */           return null;
                }

/* 140 */       public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
/* 141 */           Cursor oldCursor = this.mCursorAdapter.getCursor();
/* 142 */           if (oldCursor != null) {
                        try {
/* 144 */                   oldCursor.unregisterContentObserver(this.mChangeObserver);
                        } catch (IllegalStateException e) {
/* 147 */                   CrashUtil.logException(e);
                        }
                    }
/* 150 */           if (cursor != null) {
/* 151 */               cursor.registerContentObserver(this.mChangeObserver);
                    }
/* 153 */           this.mCursorAdapter.swapCursor(cursor);
                }

                @Override // android.support.v4.app.LoaderManager.LoaderCallbacks
/* 162 */       public void onLoaderReset(Loader<Cursor> loader) {
/* 163 */           if (this.mCursorAdapter != null) {
/* 164 */               Cursor cursor = this.mCursorAdapter.getCursor();
/* 165 */               if (cursor != null) {
/* 166 */                   cursor.unregisterContentObserver(this.mChangeObserver);
                        }
/* 168 */               this.mCursorAdapter.swapCursor(null);
                    }
                }
            }
