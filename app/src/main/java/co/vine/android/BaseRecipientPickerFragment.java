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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 48 */    public abstract class BaseRecipientPickerFragment extends BaseCursorListFragment implements AdapterView.OnItemClickListener {
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

                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 49 */        public void onCreate(Bundle savedInstanceState) {
/* 50 */            super.onCreate(savedInstanceState);
/* 51 */            this.mHandler = new Handler(Looper.getMainLooper());
                }

                @Override // android.support.v4.app.Fragment
/* 55 */        public void onActivityCreated(Bundle savedInstanceState) {
/* 56 */            super.onActivityCreated(savedInstanceState);
/* 57 */            this.mListView.setOnItemClickListener(this);
/* 58 */            this.mListView.setDividerHeight(0);
/* 59 */            this.mAppSessionListener = new RecipientPickerSessionListener(this, null);
                }

                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 63 */        public void onResume() {
/* 64 */            super.onResume();
/* 65 */            handleFocus();
                }

                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.widget.OnTabChangedListener
/* 69 */        public void onMoveTo(int oldPosition) {
/* 70 */            super.onMoveTo(oldPosition);
/* 71 */            handleFocus();
                }

/* 74 */        public RecipientPickerActivity getCallback() {
/* 75 */            return (RecipientPickerActivity) super.getActivity();
                }

/* 78 */        protected void addRecipient(VineRecipient recipient) {
/* 79 */            RecipientPickerActivity callback = getCallback();
/* 80 */            if (callback != null) {
/* 81 */                callback.addRecipient(recipient);
                    }
                }

/* 85 */        protected void removeRecipient(VineRecipient recipient) {
/* 86 */            RecipientPickerActivity callback = getCallback();
/* 87 */            if (callback != null) {
/* 88 */                callback.removeRecipient(recipient);
                    }
                }

/* 92 */        protected boolean isRecipientSelected(VineRecipient recipient) {
/* 93 */            RecipientPickerActivity callback = getCallback();
                    return callback != null && callback.isRecipientSelected(recipient);
                }

                @Override // co.vine.android.BaseCursorAdapterFragment, android.support.v4.app.LoaderManager.LoaderCallbacks
/* 98 */        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
/* 99 */            return new CursorLoader(getActivity(), this.mContentUri, this.mProjection, this.mSelection, this.mSelArgs, this.mSortOrder);
                }

/* 103 */       private class RecipientPickerSessionListener extends AppSessionListener {
/* 104 */           private RecipientPickerSessionListener() {
                    }

/* 104 */           /* synthetic */ RecipientPickerSessionListener(BaseRecipientPickerFragment x0, Object x1) {
/* 105 */               this();
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 108 */           public void onGetFriendsTypeAheadComplete(String reqId, int statusCode, String reasonPhrase, String query, ArrayList<VineUser> users) {
/* 109 */               BaseRecipientPickerFragment.this.onGetFriendsTypeAheadComplete(query, users);
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 115 */           public void onGetUsersComplete(Session session, String reqId, int errorCode, String reasonPhrase, int count, ArrayList<VineUser> users, int nextPage, int prevPage, long anchor) {
/* 116 */               PendingRequest req = BaseRecipientPickerFragment.this.removeRequest(reqId);
/* 117 */               if (req != null) {
/* 118 */                   BaseRecipientPickerFragment.this.onGetFriendsComplete(errorCode);
/* 119 */                   BaseRecipientPickerFragment.this.hideProgress(req.fetchType);
                            switch (errorCode) {
                                case 200:
                                    break;
                                default:
/* 125 */                           BaseRecipientPickerFragment.this.hideProgress(3);
/* 126 */                           if (!TextUtils.isEmpty(reasonPhrase)) {
/* 127 */                               Util.showCenteredToast(BaseRecipientPickerFragment.this.getActivity(), reasonPhrase);
                                    }
                                    break;
                            }
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 136 */           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
/* 137 */               if (BaseRecipientPickerFragment.this.mCursorAdapter instanceof FriendsPickerAdapter) {
/* 138 */                   ((FriendsPickerAdapter) BaseRecipientPickerFragment.this.mCursorAdapter).setUserImages(images);
                        }
                    }
                }

/* 144 */       protected void onGetFriendsComplete(int errorCode) {
                }

/* 147 */       public void setQueryText(String query) {
/* 148 */           this.mQueryString = query;
/* 149 */           this.mEncodedQueryString = VineAPI.encode(query.trim());
                }

/* 153 */       protected void onTextChanged(CharSequence s, int start, int before, int count) {
/* 154 */           this.mHandler.removeCallbacksAndMessages(null);
                }
            }
