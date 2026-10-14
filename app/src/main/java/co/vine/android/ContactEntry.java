            package co.vine.android;

            import android.database.Cursor;
            import android.net.Uri;
            import android.provider.ContactsContract;
            import java.util.HashMap;
            
            
    public class ContactEntry implements Comparable<ContactEntry> {
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


        public ContactEntry(Cursor cursor) {
            this.contactId = cursor.getLong(0);
            this.displayName = cursor.getString(1);
            this.alternateName = cursor.getString(2);
            this.typeFlag = "vnd.android.cursor.item/email_v2".equals(cursor.getString(3)) ? 1 : 2;
                }

                
        public int compareTo(ContactEntry contactEntry) {
                    return (this.alternateName == null || contactEntry.alternateName == null) ? getCompareResult(this.displayName.toLowerCase(), contactEntry.displayName.toLowerCase()) : getCompareResult(this.alternateName.toLowerCase(), contactEntry.alternateName.toLowerCase());
                }

        private int getCompareResult(String lhs, String rhs) {
            boolean thisStartsWithLetter = Character.isLetter(lhs.charAt(0));
            boolean thatStartsWithLetter = Character.isLetter(rhs.charAt(0));
            if ((thisStartsWithLetter && thatStartsWithLetter) || (!thisStartsWithLetter && !thatStartsWithLetter)) {
                return lhs.compareTo(rhs);
                    }
            if (thisStartsWithLetter) {
                return -1;
                    }
            return 1;
                }

        public boolean isSameSection(ContactEntry that) {
            if (this.alternateName != null && that.alternateName != null) {
                if (this.alternateName.toLowerCase().charAt(0) != that.alternateName.toLowerCase().charAt(0)) {
                            return false;
                        }
                    } else if (this.displayName.toLowerCase().charAt(0) != that.displayName.toLowerCase().charAt(0)) {
                        return false;
                    }
            return true;
                }
            }
