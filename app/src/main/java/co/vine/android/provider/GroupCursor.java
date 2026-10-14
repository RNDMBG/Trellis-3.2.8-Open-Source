            package co.vine.android.provider;

            import android.database.ContentObservable;
            import android.database.ContentObserver;
            import android.database.Cursor;
            import android.database.CursorWrapper;
            import android.database.DataSetObservable;
            import android.database.DataSetObserver;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 10 */    public abstract class GroupCursor<T> extends CursorWrapper {
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

/* 11 */        static /* synthetic */ ContentObservable access$000(GroupCursor x0) {
/* 12 */            return x0.mContentObservable;
                }

/* 30 */        public GroupCursor(Cursor cursor) {
/* 31 */            this(cursor, 20);
                }

/* 34 */        public GroupCursor(Cursor cursor, int limit) {
/* 35 */            super(cursor);
/* 22 */            this.mCursorPosition = -1;
/* 36 */            this.mLocalDataSetObservable = new LocalDataSetObservable();
/* 37 */            this.mContentObservable = new ContentObservable();
/* 38 */            this.mCursor = cursor;
/* 39 */            if (limit <= 0) {
/* 40 */                this.mLimit = 20;
                    } else {
/* 42 */                this.mLimit = limit;
                    }
/* 44 */            if (cursor != null && !cursor.isClosed()) {
/* 45 */                cursor.registerDataSetObserver(new LocalDataSetObserver());
/* 46 */                cursor.registerContentObserver(new LocalContentObserver());
                    }
                }

/* 53 */        public final void prepare() {
/* 54 */            this.mPreparing = true;
/* 55 */            onPrepare();
/* 56 */            this.mPreparing = false;
/* 57 */            if (this.mNeedsDispatchChange) {
/* 58 */                this.mNeedsDispatchChange = false;
/* 59 */                this.mContentObservable.dispatchChange(this.mSelfChange);
                    }
                }

                @Override // android.database.CursorWrapper, android.database.Cursor
/* 66 */        public int getCount() {
/* 67 */            if (this.mList != null) {
/* 68 */                return this.mList.size();
                    }
                    return 0;
                }

                @Override // android.database.CursorWrapper, android.database.Cursor
/* 75 */        public int getPosition() {
/* 76 */            return this.mCursorPosition;
                }

                @Override // android.database.CursorWrapper, android.database.Cursor
/* 80 */        public boolean moveToFirst() {
/* 81 */            return moveToPosition(0);
                }

                @Override // android.database.CursorWrapper, android.database.Cursor
/* 85 */        public boolean moveToLast() {
/* 86 */            return moveToPosition(this.mList.size() - 1);
                }

                @Override // android.database.CursorWrapper, android.database.Cursor
/* 90 */        public boolean moveToNext() {
/* 91 */            return moveToPosition(this.mCursorPosition + 1);
                }

                @Override // android.database.CursorWrapper, android.database.Cursor
/* 95 */        public boolean moveToPrevious() {
/* 96 */            return moveToPosition(this.mCursorPosition - 1);
                }

                @Override // android.database.CursorWrapper, android.database.Cursor
/* 100 */       public boolean moveToPosition(int position) {
/* 101 */           int size = getCount();
/* 102 */           if (position <= -1) {
/* 103 */               this.mCursorPosition = -1;
                        return false;
                    }
/* 105 */           if (position >= size) {
/* 106 */               this.mCursorPosition = size;
                        return false;
                    }
/* 109 */           this.mCursorPosition = position;
/* 110 */           return true;
                }

                @Override // android.database.CursorWrapper, android.database.Cursor
/* 115 */       public void registerDataSetObserver(DataSetObserver observer) {
/* 116 */           if (observer != null) {
/* 117 */               this.mLocalDataSetObservable.registerObserver(observer);
                    }
                }

                @Override // android.database.CursorWrapper, android.database.Cursor
/* 122 */       public void unregisterDataSetObserver(DataSetObserver observer) {
/* 123 */           if (observer != null) {
/* 124 */               this.mLocalDataSetObservable.unregisterObserver(observer);
                    }
                }

                @Override // android.database.CursorWrapper, android.database.Cursor
/* 129 */       public void registerContentObserver(ContentObserver observer) {
/* 130 */           if (observer != null) {
/* 131 */               this.mContentObservable.registerObserver(observer);
                    }
                }

                @Override // android.database.CursorWrapper, android.database.Cursor
/* 136 */       public void unregisterContentObserver(ContentObserver observer) {
/* 137 */           if (observer != null) {
/* 138 */               this.mContentObservable.unregisterObserver(observer);
                    }
                }

/* 142 */       public T getItem(int position) {
/* 143 */           if (this.mList == null) {
/* 144 */               return null;
                    }
                    return this.mList.get(position);
                }

                @Override // android.database.CursorWrapper, android.database.Cursor
/* 156 */       public boolean requery() {
                    boolean result = false;
/* 157 */           GroupCursor<T>.LocalDataSetObservable localDataSetObservable = this.mLocalDataSetObservable;
/* 158 */           localDataSetObservable.setNotifyOnChange(false);
/* 159 */           if (!this.mCursor.isClosed() && this.mCursor.requery()) {
                        result = true;
                    }
/* 160 */           prepare();
/* 161 */           localDataSetObservable.setNotifyOnChange(true);
/* 162 */           localDataSetObservable.notifyChanged();
/* 163 */           return result;
                }

/* 171 */       protected class LocalDataSetObservable extends DataSetObservable {
                    private boolean mNotifyOnChange = true;

/* 172 */           public LocalDataSetObservable() {
                    }

/* 176 */           public void setNotifyOnChange(boolean notifyOnChange) {
/* 177 */               this.mNotifyOnChange = notifyOnChange;
                    }

                    @Override // android.database.DataSetObservable
/* 181 */           public void notifyChanged() {
/* 182 */               if (this.mNotifyOnChange) {
/* 183 */                   super.notifyChanged();
                        }
                    }
                }

/* 187 */       private class LocalDataSetObserver extends DataSetObserver {
/* 188 */           public LocalDataSetObserver() {
                    }

                    @Override // android.database.DataSetObserver
/* 193 */           public void onInvalidated() {
/* 194 */               GroupCursor.this.mLocalDataSetObservable.notifyInvalidated();
                    }

                    @Override // android.database.DataSetObserver
/* 198 */           public void onChanged() {
/* 199 */               GroupCursor.this.mLocalDataSetObservable.notifyChanged();
                    }
                }

/* 202 */       private class LocalContentObserver extends ContentObserver {
/* 203 */           public LocalContentObserver() {
/* 205 */               super(null);
                    }

                    @Override // android.database.ContentObserver
/* 209 */           public void onChange(boolean selfChange) {
/* 210 */               GroupCursor.this.mSelfChange = selfChange;
/* 211 */               if (GroupCursor.this.mPreparing) {
/* 212 */                   GroupCursor.this.mNeedsDispatchChange = true;
                        } else {
/* 214 */                   GroupCursor.access$000(GroupCursor.this).dispatchChange(selfChange);
                        }
                    }
                }
            }
