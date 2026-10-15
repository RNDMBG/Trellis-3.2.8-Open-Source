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
            
            
    public abstract class BaseSearchFragment extends BaseArrayListFragment implements View.OnClickListener {
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

                @Override 
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            this.mDbHelper = VineDatabaseHelper.getDatabaseHelper(activity);
            this.mExecutor = Executors.newCachedThreadPool();
            if (activity instanceof SearchActivity) {
                this.mCallback = (SearchActivity) activity;
                return;
                    }
            throw new IllegalStateException("BaseSearchFragment instances must be added to a SearchActivity.");
                }

                @Override 
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            RelativeLayout relativeLayout = (RelativeLayout) getView().findViewById(R.id.layout_root);
            ViewGroup searchHintContainer = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.search_hint_container, (ViewGroup) null);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-1, getResources().getDimensionPixelSize(R.dimen.spacing_large));
            params.addRule(10, -1);
            searchHintContainer.setLayoutParams(params);
            searchHintContainer.setOnClickListener(this.mCallback);
            relativeLayout.addView(searchHintContainer);
            this.mSearchHint = (TextView) searchHintContainer.findViewById(R.id.search_hint_title);
            this.mSearchHintContainer = searchHintContainer;
                }

                @Override 
        public void onResume() {
            super.onResume();
            this.mCallback.setCurrentTab(this);
            hideProgress(3);
            if (!TextUtils.isEmpty(this.mQueryString)) {
                this.mCallback.restoreQueryText(this.mQueryString);
                this.mSearchHintContainer.setVisibility(8);
                return;
                    }
            String newQuery = this.mCallback.getQueryText();
            if (!TextUtils.isEmpty(newQuery) && newQuery.length() >= getMinimumSearchQueryLength() && this.mAdapter != null && this.mAdapter.getCount() <= 0) {
                showSearchHint(getResources(), newQuery);
                    }
                }

                @Override 
        public void onClick(View view) {
                }

        public void showSearchHint(Resources resources, String partialQuery) {
            if (resources != null) {
                if (!this.mSearchHintContainer.isShown()) {
                    this.mSearchHintContainer.setVisibility(0);
                    this.mSearchHintContainer.bringToFront();
                        }
                this.mSearchHint.setText(resources.getString(R.string.search_hint, partialQuery));
                    }
                }

       public void hideSearchHint() {
           this.mSearchHintContainer.setVisibility(8);
                }

       public void setQueryText(String query) {
           this.mQueryString = query;
           this.mEncodedQueryString = VineAPI.encode(query.trim());
                }

       public String getQueryText() {
           return this.mQueryString;
                }

                @Override 
       public void onDestroy() {
           cleanSearchResults();
           super.onDestroy();
                }
            }
