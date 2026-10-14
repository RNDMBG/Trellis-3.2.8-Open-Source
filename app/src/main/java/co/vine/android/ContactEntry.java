            package co.vine.android;

            import android.database.Cursor;
            import android.net.Uri;
            import android.provider.ContactsContract;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class ContactEntry implements Comparable<ContactEntry> {
                public static final String sSelection = "mimetype=? OR mimetype=?";
                public static final String sSortOrder = "contact_id ASC ";
                public String alternateName;
                public long contactId;
                public String displayName;
                public int typeFlag;
                public HashMap<String, Integer> valueMimeMap = new HashMap<>();
                public static final Uri sUri = ContactsContract.Data.CONTENT_URI;
                public static final String[] sProjection = {"contact_id", "display_name", "display_name_alt", "mimetype", "data1"};
                public static final String[] sSelectionArgs = {"vnd.android.cursor.item/email_v2", "vnd.android.cursor.item/phone_v2"};


/* 40 */        public ContactEntry(Cursor cursor) {
/* 42 */            this.contactId = cursor.getLong(0);
/* 43 */            this.displayName = cursor.getString(1);
/* 44 */            this.alternateName = cursor.getString(2);
/* 45 */            this.typeFlag = "vnd.android.cursor.item/email_v2".equals(cursor.getString(3)) ? 1 : 2;
                }

                /* JADX INFO: renamed from: compareTo, reason: avoid collision after fix types in other method */
/* 50 */        public int compareTo(ContactEntry contactEntry) {
                    return (this.alternateName == null || contactEntry.alternateName == null) ? getCompareResult(this.displayName.toLowerCase(), contactEntry.displayName.toLowerCase()) : getCompareResult(this.alternateName.toLowerCase(), contactEntry.alternateName.toLowerCase());
                }

/* 61 */        private int getCompareResult(String lhs, String rhs) {
/* 62 */            boolean thisStartsWithLetter = Character.isLetter(lhs.charAt(0));
/* 63 */            boolean thatStartsWithLetter = Character.isLetter(rhs.charAt(0));
/* 65 */            if ((thisStartsWithLetter && thatStartsWithLetter) || (!thisStartsWithLetter && !thatStartsWithLetter)) {
/* 67 */                return lhs.compareTo(rhs);
                    }
/* 68 */            if (thisStartsWithLetter) {
/* 69 */                return -1;
                    }
/* 71 */            return 1;
                }

/* 75 */        public boolean isSameSection(ContactEntry that) {
/* 76 */            if (this.alternateName != null && that.alternateName != null) {
/* 77 */                if (this.alternateName.toLowerCase().charAt(0) != that.alternateName.toLowerCase().charAt(0)) {
                            return false;
                        }
                    } else if (this.displayName.toLowerCase().charAt(0) != that.displayName.toLowerCase().charAt(0)) {
                        return false;
                    }
/* 87 */            return true;
                }
            }
