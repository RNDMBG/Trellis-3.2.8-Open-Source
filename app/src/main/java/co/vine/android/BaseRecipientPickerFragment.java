            package co.vine.android;

            import android.database.Cursor;
            import android.net.Uri;
            import android.os.Bundle;
            import android.os.Handler;
            import android.os.Looper;
            import android.support.v4.content.CursorLoader;
            import android.support.v4.content.Loader;
            import android.text.TextUtils;
            import android.widget.AdapterView;
            import co.vine.android.api.VineRecipient;
            import co.vine.android.api.VineUser;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.client.Session;
            import co.vine.android.client.VineAPI;
            import co.vine.android.util.Util;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            
    public abstract class BaseRecipientPickerFragment extends BaseCursorListFragment implements AdapterView.OnItemClickListener {
                protected static final String ARG_QUERY = "filter";
                public static final String ARG_TAG = "tag";
                protected static final long FILTER_THRESHOLD_MS = 150;
                protected Uri mContentUri;
                protected String mEncodedQueryString;
                protected Handler mHandler;
                protected String[] mProjection;
                protected String mQueryString;
                protected String[] mSelArgs;
                protected String mSelection;
                protected String mSortOrder;

                protected abstract void handleFocus();

                abstract void onGetFriendsTypeAheadComplete(String str, ArrayList<VineUser> arrayList);

                public abstract void onRecipientItemRemoved(VineRecipient vineRecipient);

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.mHandler = new Handler(Looper.getMainLooper());
                }

                @Override 
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            this.mListView.setOnItemClickListener(this);
            this.mListView.setDividerHeight(0);
            this.mAppSessionListener = new RecipientPickerSessionListener(this, null);
                }

                @Override 
        public void onResume() {
            super.onResume();
            handleFocus();
                }

                @Override 
        public void onMoveTo(int oldPosition) {
            super.onMoveTo(oldPosition);
            handleFocus();
                }

        public RecipientPickerActivity getCallback() {
            return (RecipientPickerActivity) super.getActivity();
                }

        protected void addRecipient(VineRecipient recipient) {
            RecipientPickerActivity callback = getCallback();
            if (callback != null) {
                callback.addRecipient(recipient);
                    }
                }

        protected void removeRecipient(VineRecipient recipient) {
            RecipientPickerActivity callback = getCallback();
            if (callback != null) {
                callback.removeRecipient(recipient);
                    }
                }

        protected boolean isRecipientSelected(VineRecipient recipient) {
            RecipientPickerActivity callback = getCallback();
                    return callback != null && callback.isRecipientSelected(recipient);
                }

                @Override 
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CursorLoader(getActivity(), this.mContentUri, this.mProjection, this.mSelection, this.mSelArgs, this.mSortOrder);
                }

       private class RecipientPickerSessionListener extends AppSessionListener {
           private RecipientPickerSessionListener() {
                    }

            RecipientPickerSessionListener(BaseRecipientPickerFragment x0, Object x1) {
               this();
                    }

                    @Override 
           public void onGetFriendsTypeAheadComplete(String reqId, int statusCode, String reasonPhrase, String query, ArrayList<VineUser> users) {
               BaseRecipientPickerFragment.this.onGetFriendsTypeAheadComplete(query, users);
                    }

                    @Override 
           public void onGetUsersComplete(Session session, String reqId, int errorCode, String reasonPhrase, int count, ArrayList<VineUser> users, int nextPage, int prevPage, long anchor) {
               PendingRequest req = BaseRecipientPickerFragment.this.removeRequest(reqId);
               if (req != null) {
                   BaseRecipientPickerFragment.this.onGetFriendsComplete(errorCode);
                   BaseRecipientPickerFragment.this.hideProgress(req.fetchType);
                            switch (errorCode) {
                                case 200:
                                    break;
                                default:
                           BaseRecipientPickerFragment.this.hideProgress(3);
                           if (!TextUtils.isEmpty(reasonPhrase)) {
                               Util.showCenteredToast(BaseRecipientPickerFragment.this.getActivity(), reasonPhrase);
                                    }
                                    break;
                            }
                        }
                    }

                    @Override 
           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
               if (BaseRecipientPickerFragment.this.mCursorAdapter instanceof FriendsPickerAdapter) {
                   ((FriendsPickerAdapter) BaseRecipientPickerFragment.this.mCursorAdapter).setUserImages(images);
                        }
                    }
                }

       protected void onGetFriendsComplete(int errorCode) {
                }

       public void setQueryText(String query) {
           this.mQueryString = query;
           this.mEncodedQueryString = VineAPI.encode(query.trim());
                }

       protected void onTextChanged(CharSequence s, int start, int before, int count) {
           this.mHandler.removeCallbacksAndMessages(null);
                }
            }
