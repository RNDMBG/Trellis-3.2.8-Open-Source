            package co.vine.android;

            import android.database.Cursor;
            import android.os.AsyncTask;
            import com.edisonwang.android.slog.SLog;
            import java.util.ArrayList;
            import java.util.Collections;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class SortContactsTask extends AsyncTask<Cursor, Void, ArrayList<ContactEntry>> {

                /* JADX INFO: renamed from: doInBackground, reason: avoid collision after fix types in other method */
/* 18 */        protected final ArrayList<ContactEntry> doInBackground(Cursor... objects) {
/* 19 */            Cursor cursor = objects[0];
                    try {
/* 21 */                return prepareEntries(cursor);
                    } catch (Exception e) {
/* 23 */                SLog.m459e("Failed to prepare entries, cursor closed?", (Throwable) e);
/* 24 */                return new ArrayList<>();
                    }
                }

/* 28 */        protected ArrayList<ContactEntry> prepareEntries(Cursor cursor) {
                    ContactEntry entry;
/* 29 */            SLog.m446d("Sorting contacts.");
/* 30 */            ArrayList<ContactEntry> entries = new ArrayList<>(cursor.getCount());
/* 31 */            while (cursor.moveToNext()) {
/* 33 */                ContactEntry lastEntry = null;
/* 34 */                if (!entries.isEmpty()) {
/* 35 */                    ContactEntry lastEntry2 = entries.get(entries.size() - 1);
                            lastEntry = lastEntry2;
                        }
/* 38 */                String mimetype = cursor.getString(3);
/* 40 */                if (lastEntry != null && lastEntry.contactId == cursor.getLong(0)) {
/* 42 */                    entry = lastEntry;
                            entry.typeFlag = ("vnd.android.cursor.item/email_v2".equals(mimetype) ? 1 : 2) | entry.typeFlag;
                        } else {
/* 45 */                    entry = new ContactEntry(cursor);
/* 46 */                    entries.add(entry);
                        }
/* 48 */                entry.valueMimeMap.put(cursor.getString(4), Integer.valueOf(("vnd.android.cursor.item/email_v2".equals(mimetype) ? 1 : 0) | ("vnd.android.cursor.item/phone_v2".equals(mimetype) ? 2 : 0)));
                    }
/* 56 */            Collections.sort(entries);
/* 57 */            SLog.m446d("Sorting contacts complete.");
/* 58 */            return entries;
                }
            }
