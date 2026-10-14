            package co.vine.android;

            import android.app.Activity;
            import android.content.ContentUris;
            import android.database.Cursor;
            import android.net.Uri;
            import android.support.v4.app.Fragment;
            import android.support.v4.widget.CursorAdapter;
            import android.text.TextUtils;
            import android.widget.Filter;
            import co.vine.android.provider.VineSuggestionsProvider;
            import co.vine.android.widget.PopupEditText;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public class ComposeFilter extends Filter {
                private Activity mActivity;
                private PopupEditText.PopupEditTextListener mCallback;
                private long mUserId;

                /* JADX WARN: Multi-variable type inference failed */
/* 24 */        public ComposeFilter(PopupEditText.PopupEditTextListener popupEditTextListener, long userId) {
/* 26 */            this.mActivity = ((Fragment) popupEditTextListener).getActivity();
/* 27 */            this.mCallback = popupEditTextListener;
/* 28 */            this.mUserId = userId;
                }

                @Override // android.widget.Filter
/* 32 */        protected Filter.FilterResults performFiltering(CharSequence constraint) {
                    String selection;
                    Uri uri;
                    Cursor cursor;
/* 33 */            Filter.FilterResults filterResults = new Filter.FilterResults();
/* 34 */            if (!TextUtils.isEmpty(constraint)) {
/* 35 */                CharSequence plainConstraint = constraint.subSequence(1, constraint.length());
/* 38 */                if (plainConstraint.length() > 0) {
/* 39 */                    selection = plainConstraint.toString();
                        } else {
/* 41 */                    selection = null;
                        }
/* 44 */                if (constraint.toString().startsWith("@")) {
/* 45 */                    uri = ContentUris.withAppendedId(VineSuggestionsProvider.USERS_URI, this.mUserId);
                        } else if (constraint.toString().startsWith("#")) {
/* 47 */                    uri = VineSuggestionsProvider.TAGS_URI;
                        } else {
/* 49 */                    uri = null;
                        }
/* 51 */                if (uri != null && (cursor = this.mActivity.getContentResolver().query(uri, null, selection, null, null)) != null) {
/* 59 */                    filterResults.count = cursor.getCount();
/* 60 */                    filterResults.values = cursor;
                        }
                    }
/* 64 */            return filterResults;
                }

                @Override // android.widget.Filter
/* 68 */        protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
/* 69 */            CursorAdapter typeaheadAdapter = this.mCallback.getPopupAdapter();
/* 70 */            Cursor prevCursor = typeaheadAdapter.getCursor();
/* 71 */            if (prevCursor != null) {
/* 72 */                this.mActivity.stopManagingCursor(prevCursor);
                    }
/* 74 */            Cursor cursor = (Cursor) results.values;
/* 75 */            if (cursor != null) {
/* 76 */                this.mActivity.startManagingCursor(cursor);
                    }
/* 78 */            typeaheadAdapter.changeCursor(cursor);
                }
            }
