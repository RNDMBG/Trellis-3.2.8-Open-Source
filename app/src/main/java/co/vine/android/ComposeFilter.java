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
            
            
    public class ComposeFilter extends Filter {
                private Activity mActivity;
                private PopupEditText.PopupEditTextListener mCallback;
                private long mUserId;

                
        public ComposeFilter(PopupEditText.PopupEditTextListener popupEditTextListener, long userId) {
            this.mActivity = ((Fragment) popupEditTextListener).getActivity();
            this.mCallback = popupEditTextListener;
            this.mUserId = userId;
                }

                @Override 
        protected Filter.FilterResults performFiltering(CharSequence constraint) {
                    String selection;
                    Uri uri;
                    Cursor cursor;
            Filter.FilterResults filterResults = new Filter.FilterResults();
            if (!TextUtils.isEmpty(constraint)) {
                CharSequence plainConstraint = constraint.subSequence(1, constraint.length());
                if (plainConstraint.length() > 0) {
                    selection = plainConstraint.toString();
                        } else {
                    selection = null;
                        }
                if (constraint.toString().startsWith("@")) {
                    uri = ContentUris.withAppendedId(VineSuggestionsProvider.USERS_URI, this.mUserId);
                        } else if (constraint.toString().startsWith("#")) {
                    uri = VineSuggestionsProvider.TAGS_URI;
                        } else {
                    uri = null;
                        }
                if (uri != null && (cursor = this.mActivity.getContentResolver().query(uri, null, selection, null, null)) != null) {
                    filterResults.count = cursor.getCount();
                    filterResults.values = cursor;
                        }
                    }
            return filterResults;
                }

                @Override 
        protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
            CursorAdapter typeaheadAdapter = this.mCallback.getPopupAdapter();
            Cursor prevCursor = typeaheadAdapter.getCursor();
            if (prevCursor != null) {
                this.mActivity.stopManagingCursor(prevCursor);
                    }
            Cursor cursor = (Cursor) results.values;
            if (cursor != null) {
                this.mActivity.startManagingCursor(cursor);
                    }
            typeaheadAdapter.changeCursor(cursor);
                }
            }
