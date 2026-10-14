            package co.vine.android;

            import android.app.Activity;
            import android.content.res.Resources;
            import android.os.Bundle;
            import android.text.TextUtils;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.RelativeLayout;
            import android.widget.TextView;
            import co.vine.android.client.VineAPI;
            import co.vine.android.provider.VineDatabaseHelper;
            import java.util.concurrent.ExecutorService;
            import java.util.concurrent.Executors;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 39 */    public abstract class BaseSearchFragment extends BaseArrayListFragment implements View.OnClickListener {
                protected SearchActivity mCallback;
                protected VineDatabaseHelper mDbHelper;
                protected String mEncodedQueryString;
                protected ExecutorService mExecutor;
                protected String[] mProjection;
                protected String mQueryString;
                private TextView mSearchHint;
                private ViewGroup mSearchHintContainer;
                protected String mSortOrder;

                public abstract void cleanSearchResults();

                protected abstract void fetchContent(int i);

                public abstract int getMinimumSearchQueryLength();

                protected abstract void performSearch();

                @Override // android.support.v4.app.Fragment
/* 40 */        public void onAttach(Activity activity) {
/* 41 */            super.onAttach(activity);
/* 42 */            this.mDbHelper = VineDatabaseHelper.getDatabaseHelper(activity);
/* 43 */            this.mExecutor = Executors.newCachedThreadPool();
/* 44 */            if (activity instanceof SearchActivity) {
/* 45 */                this.mCallback = (SearchActivity) activity;
/* 49 */                return;
                    }
/* 47 */            throw new IllegalStateException("BaseSearchFragment instances must be added to a SearchActivity.");
                }

                @Override // android.support.v4.app.Fragment
/* 52 */        public void onActivityCreated(Bundle savedInstanceState) {
/* 53 */            super.onActivityCreated(savedInstanceState);
/* 55 */            RelativeLayout relativeLayout = (RelativeLayout) getView().findViewById(C0257R.id.layout_root);
/* 56 */            ViewGroup searchHintContainer = (ViewGroup) LayoutInflater.from(getActivity()).inflate(C0257R.layout.search_hint_container, (ViewGroup) null);
/* 58 */            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-1, getResources().getDimensionPixelSize(C0257R.dimen.spacing_large));
/* 60 */            params.addRule(10, -1);
/* 61 */            searchHintContainer.setLayoutParams(params);
/* 62 */            searchHintContainer.setOnClickListener(this.mCallback);
/* 63 */            relativeLayout.addView(searchHintContainer);
/* 64 */            this.mSearchHint = (TextView) searchHintContainer.findViewById(C0257R.id.search_hint_title);
/* 65 */            this.mSearchHintContainer = searchHintContainer;
                }

                @Override // co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 69 */        public void onResume() {
/* 70 */            super.onResume();
/* 71 */            this.mCallback.setCurrentTab(this);
/* 72 */            hideProgress(3);
/* 74 */            if (!TextUtils.isEmpty(this.mQueryString)) {
/* 75 */                this.mCallback.restoreQueryText(this.mQueryString);
/* 76 */                this.mSearchHintContainer.setVisibility(8);
/* 84 */                return;
                    }
/* 78 */            String newQuery = this.mCallback.getQueryText();
/* 79 */            if (!TextUtils.isEmpty(newQuery) && newQuery.length() >= getMinimumSearchQueryLength() && this.mAdapter != null && this.mAdapter.getCount() <= 0) {
/* 81 */                showSearchHint(getResources(), newQuery);
                    }
                }

                @Override // android.view.View.OnClickListener
/* 87 */        public void onClick(View view) {
                }

/* 90 */        public void showSearchHint(Resources resources, String partialQuery) {
/* 91 */            if (resources != null) {
/* 92 */                if (!this.mSearchHintContainer.isShown()) {
/* 93 */                    this.mSearchHintContainer.setVisibility(0);
/* 94 */                    this.mSearchHintContainer.bringToFront();
                        }
/* 96 */                this.mSearchHint.setText(resources.getString(C0257R.string.search_hint, partialQuery));
                    }
                }

/* 100 */       public void hideSearchHint() {
/* 101 */           this.mSearchHintContainer.setVisibility(8);
                }

/* 104 */       public void setQueryText(String query) {
/* 105 */           this.mQueryString = query;
/* 106 */           this.mEncodedQueryString = VineAPI.encode(query.trim());
                }

/* 109 */       public String getQueryText() {
/* 110 */           return this.mQueryString;
                }

                @Override // android.support.v4.app.Fragment
/* 116 */       public void onDestroy() {
/* 117 */           cleanSearchResults();
/* 118 */           super.onDestroy();
                }
            }
