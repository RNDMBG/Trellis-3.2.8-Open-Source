            package android.support.v4.widget;

            import android.app.SearchManager;
            import android.content.ComponentName;
            import android.content.Context;
            import android.view.View;
            import android.widget.SearchView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    class SearchViewCompatHoneycomb {

                interface OnCloseListenerCompatBridge {
                    boolean onClose();
                }

                interface OnQueryTextListenerCompatBridge {
                    boolean onQueryTextChange(String str);

                    boolean onQueryTextSubmit(String str);
                }

/* 29 */        SearchViewCompatHoneycomb() {
                }

/* 41 */        public static View newSearchView(Context context) {
/* 42 */            return new SearchView(context);
                }

/* 45 */        public static void setSearchableInfo(View searchView, ComponentName searchableComponent) {
/* 46 */            SearchView sv = (SearchView) searchView;
/* 47 */            SearchManager searchManager = (SearchManager) sv.getContext().getSystemService("search");
/* 49 */            sv.setSearchableInfo(searchManager.getSearchableInfo(searchableComponent));
                }

                /* JADX INFO: renamed from: android.support.v4.widget.SearchViewCompatHoneycomb$1 */
/* 51 */        static class C00701 implements SearchView.OnQueryTextListener {
                    final /* synthetic */ OnQueryTextListenerCompatBridge val$listener;

/* 52 */            C00701(OnQueryTextListenerCompatBridge onQueryTextListenerCompatBridge) {
/* 53 */                this.val$listener = onQueryTextListenerCompatBridge;
                    }

                    @Override // android.widget.SearchView.OnQueryTextListener
/* 55 */            public boolean onQueryTextSubmit(String query) {
/* 56 */                return this.val$listener.onQueryTextSubmit(query);
                    }

                    @Override // android.widget.SearchView.OnQueryTextListener
/* 60 */            public boolean onQueryTextChange(String newText) {
/* 61 */                return this.val$listener.onQueryTextChange(newText);
                    }
                }

/* 52 */        public static Object newOnQueryTextListener(OnQueryTextListenerCompatBridge listener) {
/* 53 */            return new C00701(listener);
                }

/* 66 */        public static void setOnQueryTextListener(Object searchView, Object listener) {
/* 67 */            ((SearchView) searchView).setOnQueryTextListener((SearchView.OnQueryTextListener) listener);
                }

                /* JADX INFO: renamed from: android.support.v4.widget.SearchViewCompatHoneycomb$2 */
/* 69 */        static class C00712 implements SearchView.OnCloseListener {
                    final /* synthetic */ OnCloseListenerCompatBridge val$listener;

/* 70 */            C00712(OnCloseListenerCompatBridge onCloseListenerCompatBridge) {
/* 71 */                this.val$listener = onCloseListenerCompatBridge;
                    }

                    @Override // android.widget.SearchView.OnCloseListener
/* 73 */            public boolean onClose() {
/* 74 */                return this.val$listener.onClose();
                    }
                }

/* 70 */        public static Object newOnCloseListener(OnCloseListenerCompatBridge listener) {
/* 71 */            return new C00712(listener);
                }

/* 79 */        public static void setOnCloseListener(Object searchView, Object listener) {
/* 80 */            ((SearchView) searchView).setOnCloseListener((SearchView.OnCloseListener) listener);
                }

/* 83 */        public static CharSequence getQuery(View searchView) {
/* 84 */            return ((SearchView) searchView).getQuery();
                }

/* 87 */        public static void setQuery(View searchView, CharSequence query, boolean submit) {
/* 88 */            ((SearchView) searchView).setQuery(query, submit);
                }

/* 91 */        public static void setQueryHint(View searchView, CharSequence hint) {
/* 92 */            ((SearchView) searchView).setQueryHint(hint);
                }

/* 95 */        public static void setIconified(View searchView, boolean iconify) {
/* 96 */            ((SearchView) searchView).setIconified(iconify);
                }

/* 99 */        public static boolean isIconified(View searchView) {
/* 100 */           return ((SearchView) searchView).isIconified();
                }

/* 103 */       public static void setSubmitButtonEnabled(View searchView, boolean enabled) {
/* 104 */           ((SearchView) searchView).setSubmitButtonEnabled(enabled);
                }

/* 107 */       public static boolean isSubmitButtonEnabled(View searchView) {
/* 108 */           return ((SearchView) searchView).isSubmitButtonEnabled();
                }

/* 111 */       public static void setQueryRefinementEnabled(View searchView, boolean enable) {
/* 112 */           ((SearchView) searchView).setQueryRefinementEnabled(enable);
                }

/* 115 */       public static boolean isQueryRefinementEnabled(View searchView) {
/* 116 */           return ((SearchView) searchView).isQueryRefinementEnabled();
                }

/* 119 */       public static void setMaxWidth(View searchView, int maxpixels) {
/* 120 */           ((SearchView) searchView).setMaxWidth(maxpixels);
                }
            }
