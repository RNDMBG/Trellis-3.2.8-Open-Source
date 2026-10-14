            package co.vine.android.provider;

            import android.content.ContentResolver;
            import android.database.Cursor;
            import android.net.Uri;
            import android.text.TextUtils;
            import co.vine.android.api.VineRecipient;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 16 */    public class VineProviderHelper {
/* 17 */        public static ArrayList<VineRecipient> getConversationRecipients(ContentResolver resolver, long conversationRowId) {
/* 18 */            ArrayList<VineRecipient> recipients = new ArrayList<>();
/* 19 */            Cursor cursor = queryConversationRecipientsUsersView(resolver, conversationRowId);
/* 20 */            if (cursor != null) {
/* 21 */                while (cursor.moveToNext()) {
/* 23 */                    long userRowId = cursor.getLong(2);
/* 24 */                    long userId = cursor.getLong(6);
/* 25 */                    String email = cursor.getString(5);
/* 26 */                    String phone = cursor.getString(4);
/* 27 */                    String display = cursor.getString(3);
                            if (userId > 0) {
/* 29 */                        recipients.add(VineRecipient.fromUser(display, userId, 0, userRowId));
                            } else if (!TextUtils.isEmpty(email)) {
/* 31 */                        recipients.add(VineRecipient.fromEmail(display, -1L, email, userRowId));
                            } else if (!TextUtils.isEmpty(phone)) {
/* 33 */                        recipients.add(VineRecipient.fromPhone(display, -1L, phone, userRowId));
                            }
                        }
/* 36 */                cursor.close();
                    }
/* 38 */            return recipients;
                }

/* 44 */        public static Cursor queryConversationRecipientsUsersView(ContentResolver resolver, long conversationRowId) {
/* 45 */            String conversationRowIdString = String.valueOf(conversationRowId);
/* 46 */            Uri contentUri = Vine.ConversationRecipientsUsersView.CONTENT_URI_CONVERSATION.buildUpon().appendQueryParameter("conversation_row_id", conversationRowIdString).build();
/* 50 */            return resolver.query(contentUri, VineDatabaseSQL.ConversationRecipientsUsersViewQuery.PROJECTION, null, null, null);
                }
            }
