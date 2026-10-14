            package co.vine.android.provider;

            import android.database.ContentObservable;
            import android.database.ContentObserver;
            import android.database.Cursor;
            import android.database.CursorWrapper;
            import android.database.DataSetObservable;
            import android.database.DataSetObserver;
            import java.util.List;
            
            
    public abstract class GroupCursor<T> extends CursorWrapper {
                public static final int LIMIT_DEFAULT = 20;
                private final ContentObservable mContentObservable;
                protected Cursor mCursor;
                protected int mCursorPosition;
                protected int mLimit;
                protected List<T> mList;
                protected final GroupCursor<T>.LocalDataSetObservable mLocalDataSetObservable;
                volatile boolean mNeedsDispatchChange;
                volatile boolean mPreparing;
                volatile boolean mSelfChange;

                public abstract void onPrepare();

        static  ContentObservable access$000(GroupCursor x0) {
            return x0.mContentObservable;
                }

        public GroupCursor(Cursor cursor) {
            this(cursor, 20);
                }

        public GroupCursor(Cursor cursor, int limit) {
            super(cursor);
            this.mCursorPosition = -1;
            this.mLocalDataSetObservable = new LocalDataSetObservable();
            this.mContentObservable = new ContentObservable();
            this.mCursor = cursor;
            if (limit <= 0) {
                this.mLimit = 20;
                    } else {
                this.mLimit = limit;
                    }
            if (cursor != null && !cursor.isClosed()) {
                cursor.registerDataSetObserver(new LocalDataSetObserver());
                cursor.registerContentObserver(new LocalContentObserver());
                    }
                }

        public final void prepare() {
            this.mPreparing = true;
            onPrepare();
            this.mPreparing = false;
            if (this.mNeedsDispatchChange) {
                this.mNeedsDispatchChange = false;
                this.mContentObservable.dispatchChange(this.mSelfChange);
                    }
                }

                @Override 
        public int getCount() {
            if (this.mList != null) {
                return this.mList.size();
                    }
                    return 0;
                }

                @Override 
        public int getPosition() {
            return this.mCursorPosition;
                }

                @Override 
        public boolean moveToFirst() {
            return moveToPosition(0);
                }

                @Override 
        public boolean moveToLast() {
            return moveToPosition(this.mList.size() - 1);
                }

                @Override 
        public boolean moveToNext() {
            return moveToPosition(this.mCursorPosition + 1);
                }

                @Override 
        public boolean moveToPrevious() {
            return moveToPosition(this.mCursorPosition - 1);
                }

                @Override 
       public boolean moveToPosition(int position) {
           int size = getCount();
           if (position <= -1) {
               this.mCursorPosition = -1;
                        return false;
                    }
           if (position >= size) {
               this.mCursorPosition = size;
                        return false;
                    }
           this.mCursorPosition = position;
           return true;
                }

                @Override 
       public void registerDataSetObserver(DataSetObserver observer) {
           if (observer != null) {
               this.mLocalDataSetObservable.registerObserver(observer);
                    }
                }

                @Override 
       public void unregisterDataSetObserver(DataSetObserver observer) {
           if (observer != null) {
               this.mLocalDataSetObservable.unregisterObserver(observer);
                    }
                }

                @Override 
       public void registerContentObserver(ContentObserver observer) {
           if (observer != null) {
               this.mContentObservable.registerObserver(observer);
                    }
                }

                @Override 
       public void unregisterContentObserver(ContentObserver observer) {
           if (observer != null) {
               this.mContentObservable.unregisterObserver(observer);
                    }
                }

       public T getItem(int position) {
           if (this.mList == null) {
               return null;
                    }
                    return this.mList.get(position);
                }

                @Override 
       public boolean requery() {
                    boolean result = false;
           GroupCursor<T>.LocalDataSetObservable localDataSetObservable = this.mLocalDataSetObservable;
           localDataSetObservable.setNotifyOnChange(false);
           if (!this.mCursor.isClosed() && this.mCursor.requery()) {
                        result = true;
                    }
           prepare();
           localDataSetObservable.setNotifyOnChange(true);
           localDataSetObservable.notifyChanged();
           return result;
                }

       protected class LocalDataSetObservable extends DataSetObservable {
                    private boolean mNotifyOnChange = true;

           public LocalDataSetObservable() {
                    }

           public void setNotifyOnChange(boolean notifyOnChange) {
               this.mNotifyOnChange = notifyOnChange;
                    }

                    @Override 
           public void notifyChanged() {
               if (this.mNotifyOnChange) {
                   super.notifyChanged();
                        }
                    }
                }

       private class LocalDataSetObserver extends DataSetObserver {
           public LocalDataSetObserver() {
                    }

                    @Override 
           public void onInvalidated() {
               GroupCursor.this.mLocalDataSetObservable.notifyInvalidated();
                    }

                    @Override 
           public void onChanged() {
               GroupCursor.this.mLocalDataSetObservable.notifyChanged();
                    }
                }

       private class LocalContentObserver extends ContentObserver {
           public LocalContentObserver() {
               super(null);
                    }

                    @Override 
           public void onChange(boolean selfChange) {
               GroupCursor.this.mSelfChange = selfChange;
               if (GroupCursor.this.mPreparing) {
                   GroupCursor.this.mNeedsDispatchChange = true;
                        } else {
                   GroupCursor.access$000(GroupCursor.this).dispatchChange(selfChange);
                        }
                    }
                }
            }
