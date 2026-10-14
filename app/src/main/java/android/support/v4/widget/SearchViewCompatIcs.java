            package android.support.v4.widget;

            import android.content.Context;
            import android.view.View;
            import android.widget.SearchView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    class SearchViewCompatIcs {
/* 25 */        SearchViewCompatIcs() {
                }

/* 28 */        public static class MySearchView extends SearchView {
/* 29 */            public MySearchView(Context context) {
/* 30 */                super(context);
                    }

                    @Override // android.widget.SearchView, android.view.CollapsibleActionView
/* 36 */            public void onActionViewCollapsed() {
/* 37 */                setQuery("", false);
/* 38 */                super.onActionViewCollapsed();
                    }
                }

/* 42 */        public static View newSearchView(Context context) {
/* 43 */            return new MySearchView(context);
                }

/* 46 */        public static void setImeOptions(View searchView, int imeOptions) {
/* 47 */            ((SearchView) searchView).setImeOptions(imeOptions);
                }

/* 50 */        public static void setInputType(View searchView, int inputType) {
/* 51 */            ((SearchView) searchView).setInputType(inputType);
                }
            }
