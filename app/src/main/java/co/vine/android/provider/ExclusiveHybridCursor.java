            package co.vine.android.provider;

            import android.database.Cursor;
            import co.vine.android.util.CrashUtil;
            import java.util.ArrayList;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public abstract class ExclusiveHybridCursor<T, E> extends GroupCursor<T> {
                private Mode mMode;
                private List<E> mRemoteData;

                public enum Mode {
                    CURSOR,
                    LIST
                }

                public abstract T createItemFromCursor(Cursor cursor);

                public abstract T createItemsFromRemoteData(E e);

/* 19 */        public ExclusiveHybridCursor(ArrayList<E> remoteData) {
/* 20 */            super(null);
/* 16 */            this.mMode = Mode.CURSOR;
/* 21 */            this.mRemoteData = remoteData;
                }

/* 24 */        public Mode getMode() {
/* 25 */            return this.mMode;
                }

/* 28 */        public void setMode(Mode mode) {
/* 29 */            this.mMode = mode;
                }

/* 32 */        public ExclusiveHybridCursor(Cursor cursor) {
/* 33 */            this(cursor, 20);
                }

/* 36 */        public ExclusiveHybridCursor(Cursor cursor, int limit) {
/* 37 */            super(cursor, limit);
/* 16 */            this.mMode = Mode.CURSOR;
                }

                @Override // co.vine.android.provider.GroupCursor
/* 41 */        public void onPrepare() {
                    boolean zMoveToNext;
                    switch (this.mMode) {
                        case LIST:
/* 44 */                    this.mList = new ArrayList();
/* 45 */                    int size = this.mRemoteData.size();
                            for (int i = 0; i < size && i < this.mLimit; i++) {
/* 47 */                        this.mList.add(createItemsFromRemoteData(this.mRemoteData.get(i)));
                            }
                            break;
                        case CURSOR:
/* 52 */                    Cursor cursor = this.mCursor;
/* 54 */                    if (cursor != null && cursor.moveToFirst()) {
/* 55 */                        this.mList = new ArrayList();
/* 53 */                        do {
/* 58 */                            this.mList.add(createItemFromCursor(cursor));
                                    try {
/* 60 */                                zMoveToNext = cursor.moveToNext();
                                    } catch (IllegalStateException e) {
/* 62 */                                CrashUtil.log("Failed to get cursor's row id.");
/* 63 */                                System.gc();
/* 64 */                                zMoveToNext = cursor.moveToNext();
                                    }
/* 66 */                            if (!zMoveToNext) {
                                        break;
                                    }
/* 53 */                        } while (0 < this.mLimit);
                            } else {
/* 68 */                        this.mList = null;
                            }
                            break;
                    }
                }

/* 75 */        public boolean isLastItem() {
                    return (this.mRemoteData != null && this.mRemoteData.size() >= this.mCursorPosition + 1) || (this.mCursor != null && this.mCursor.getCount() >= this.mCursorPosition);
                }
            }
