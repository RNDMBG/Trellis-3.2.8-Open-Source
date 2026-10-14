            package co.vine.android.provider;

            import android.database.Cursor;
            import co.vine.android.api.VineRecipient;
            import co.vine.android.api.VineUser;
            import java.util.ArrayList;
            
            
    public class FriendsPickerExclusiveCursor extends ExclusiveHybridCursor<VineRecipient, VineUser> {


        public FriendsPickerExclusiveCursor(Cursor cursor) {
            super(cursor);
            setMode(ExclusiveHybridCursor.Mode.CURSOR);
                }

        public FriendsPickerExclusiveCursor(ArrayList<VineUser> users) {
            super(users);
            setMode(ExclusiveHybridCursor.Mode.LIST);
                }

                @Override 
        public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
            if (this.mCursor != null) {
                return this.mCursor.getColumnIndexOrThrow(columnName);
                    }
                    return -1;
                }

        public VineRecipient createItemsFromRemoteData(VineUser remoteItem) {
            VineRecipient vr = VineRecipient.fromUser(remoteItem.username, remoteItem.userId, 0, -1L);
            vr.avatarUrl = remoteItem.avatarUrl;
            return vr;
                }

                
                @Override 
        public VineRecipient createItemFromCursor(Cursor cursor) {
            return VineDatabaseSQL.UsersQuery.getVineRecipient(cursor);
                }
            }
