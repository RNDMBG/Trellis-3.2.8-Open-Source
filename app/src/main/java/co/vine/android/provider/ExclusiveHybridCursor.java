            package co.vine.android.provider;

            import android.database.Cursor;
            import co.vine.android.util.CrashUtil;
            import java.util.ArrayList;
            import java.util.List;
            
            
    public abstract class ExclusiveHybridCursor<T, E> extends GroupCursor<T> {
                private Mode mMode;
                private List<E> mRemoteData;

                public enum Mode {
                    CURSOR,
                    LIST
                }

                public abstract T createItemFromCursor(Cursor cursor);

                public abstract T createItemsFromRemoteData(E e);

        public ExclusiveHybridCursor(ArrayList<E> remoteData) {
            super(null);
            this.mMode = Mode.CURSOR;
            this.mRemoteData = remoteData;
                }

        public Mode getMode() {
            return this.mMode;
                }

        public void setMode(Mode mode) {
            this.mMode = mode;
                }

        public ExclusiveHybridCursor(Cursor cursor) {
            this(cursor, 20);
                }

        public ExclusiveHybridCursor(Cursor cursor, int limit) {
            super(cursor, limit);
            this.mMode = Mode.CURSOR;
                }

                @Override 
        public void onPrepare() {
                    boolean zMoveToNext;
                    switch (this.mMode) {
                        case LIST:
                    this.mList = new ArrayList();
                    int size = this.mRemoteData.size();
                            for (int i = 0; i < size && i < this.mLimit; i++) {
                        this.mList.add(createItemsFromRemoteData(this.mRemoteData.get(i)));
                            }
                            break;
                        case CURSOR:
                    Cursor cursor = this.mCursor;
                    if (cursor != null && cursor.moveToFirst()) {
                        this.mList = new ArrayList();
                        do {
                            this.mList.add(createItemFromCursor(cursor));
                                    try {
                                zMoveToNext = cursor.moveToNext();
                                    } catch (IllegalStateException e) {
                                CrashUtil.log("Failed to get cursor's row id.");
                                System.gc();
                                zMoveToNext = cursor.moveToNext();
                                    }
                            if (!zMoveToNext) {
                                        break;
                                    }
                        } while (0 < this.mLimit);
                            } else {
                        this.mList = null;
                            }
                            break;
                    }
                }

        public boolean isLastItem() {
                    return (this.mRemoteData != null && this.mRemoteData.size() >= this.mCursorPosition + 1) || (this.mCursor != null && this.mCursor.getCount() >= this.mCursorPosition);
                }
            }
