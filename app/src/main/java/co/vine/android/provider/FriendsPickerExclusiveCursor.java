            package co.vine.android.provider;

            import android.database.Cursor;
            import co.vine.android.api.VineRecipient;
            import co.vine.android.api.VineUser;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 11 */    public class FriendsPickerExclusiveCursor extends ExclusiveHybridCursor<VineRecipient, VineUser> {


/* 15 */        public FriendsPickerExclusiveCursor(Cursor cursor) {
/* 16 */            super(cursor);
/* 17 */            setMode(ExclusiveHybridCursor.Mode.CURSOR);
                }

/* 20 */        public FriendsPickerExclusiveCursor(ArrayList<VineUser> users) {
/* 21 */            super(users);
/* 22 */            setMode(ExclusiveHybridCursor.Mode.LIST);
                }

                @Override // android.database.CursorWrapper, android.database.Cursor
/* 26 */        public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
/* 27 */            if (this.mCursor != null) {
/* 28 */                return this.mCursor.getColumnIndexOrThrow(columnName);
                    }
                    return -1;
                }

/* 35 */        public VineRecipient createItemsFromRemoteData(VineUser remoteItem) {
/* 36 */            VineRecipient vr = VineRecipient.fromUser(remoteItem.username, remoteItem.userId, 0, -1L);
/* 37 */            vr.avatarUrl = remoteItem.avatarUrl;
/* 38 */            return vr;
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // co.vine.android.provider.ExclusiveHybridCursor
/* 42 */        public VineRecipient createItemFromCursor(Cursor cursor) {
/* 43 */            return VineDatabaseSQL.UsersQuery.getVineRecipient(cursor);
                }
            }
