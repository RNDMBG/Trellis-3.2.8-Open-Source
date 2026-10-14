            package android.support.v4.widget;

            import android.database.Cursor;
            import android.widget.Filter;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 37 */    class CursorFilter extends Filter {
                CursorFilterClient mClient;

                interface CursorFilterClient {
                    void changeCursor(Cursor cursor);

                    CharSequence convertToString(Cursor cursor);

                    Cursor getCursor();

                    Cursor runQueryOnBackgroundThread(CharSequence charSequence);
                }

/* 38 */        CursorFilter(CursorFilterClient client) {
/* 40 */            this.mClient = client;
                }

                @Override // android.widget.Filter
/* 44 */        public CharSequence convertResultToString(Object resultValue) {
/* 45 */            return this.mClient.convertToString((Cursor) resultValue);
                }

                @Override // android.widget.Filter
/* 49 */        protected Filter.FilterResults performFiltering(CharSequence constraint) {
/* 50 */            Cursor cursor = this.mClient.runQueryOnBackgroundThread(constraint);
/* 52 */            Filter.FilterResults results = new Filter.FilterResults();
/* 53 */            if (cursor != null) {
/* 54 */                results.count = cursor.getCount();
/* 55 */                results.values = cursor;
                    } else {
/* 57 */                results.count = 0;
/* 58 */                results.values = null;
                    }
/* 60 */            return results;
                }

                @Override // android.widget.Filter
/* 64 */        protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
/* 65 */            Cursor oldCursor = this.mClient.getCursor();
/* 67 */            if (results.values != null && results.values != oldCursor) {
/* 68 */                this.mClient.changeCursor((Cursor) results.values);
                    }
                }
            }
