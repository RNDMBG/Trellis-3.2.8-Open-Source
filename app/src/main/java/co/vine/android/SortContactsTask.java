            package co.vine.android;

            import android.database.Cursor;
            import android.os.AsyncTask;
            import com.edisonwang.android.slog.SLog;
            import java.util.ArrayList;
            import java.util.Collections;
            
            
    public class SortContactsTask extends AsyncTask<Cursor, Void, ArrayList<ContactEntry>> {

                
        protected final ArrayList<ContactEntry> doInBackground(Cursor... objects) {
            Cursor cursor = objects[0];
                    try {
                return prepareEntries(cursor);
                    } catch (Exception e) {
                SLog.m459e("Failed to prepare entries, cursor closed?", (Throwable) e);
                return new ArrayList<>();
                    }
                }

        protected ArrayList<ContactEntry> prepareEntries(Cursor cursor) {
                    ContactEntry entry;
            SLog.m446d("Sorting contacts.");
            ArrayList<ContactEntry> entries = new ArrayList<>(cursor.getCount());
            while (cursor.moveToNext()) {
                ContactEntry lastEntry = null;
                if (!entries.isEmpty()) {
                    ContactEntry lastEntry2 = entries.get(entries.size() - 1);
                            lastEntry = lastEntry2;
                        }
                String mimetype = cursor.getString(3);
                if (lastEntry != null && lastEntry.contactId == cursor.getLong(0)) {
                    entry = lastEntry;
                            entry.typeFlag = ("vnd.android.cursor.item/email_v2".equals(mimetype) ? 1 : 2) | entry.typeFlag;
                        } else {
                    entry = new ContactEntry(cursor);
                    entries.add(entry);
                        }
                entry.valueMimeMap.put(cursor.getString(4), Integer.valueOf(("vnd.android.cursor.item/email_v2".equals(mimetype) ? 1 : 0) | ("vnd.android.cursor.item/phone_v2".equals(mimetype) ? 2 : 0)));
                    }
            Collections.sort(entries);
            SLog.m446d("Sorting contacts complete.");
            return entries;
                }
            }
