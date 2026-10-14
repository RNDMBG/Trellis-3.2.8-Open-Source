            package android.support.v4.widget;

            import android.content.ComponentName;
            import android.content.Context;
            import android.os.Build;
            import android.view.View;
            
            
    public class SearchViewCompat {
                private static final SearchViewCompatImpl IMPL;

                interface SearchViewCompatImpl {
                    CharSequence getQuery(View view);

                    boolean isIconified(View view);

                    boolean isQueryRefinementEnabled(View view);

                    boolean isSubmitButtonEnabled(View view);

                    Object newOnCloseListener(OnCloseListenerCompat onCloseListenerCompat);

                    Object newOnQueryTextListener(OnQueryTextListenerCompat onQueryTextListenerCompat);

                    View newSearchView(Context context);

                    void setIconified(View view, boolean z);

                    void setImeOptions(View view, int i);

                    void setInputType(View view, int i);

                    void setMaxWidth(View view, int i);

                    void setOnCloseListener(Object obj, Object obj2);

                    void setOnQueryTextListener(Object obj, Object obj2);

                    void setQuery(View view, CharSequence charSequence, boolean z);

                    void setQueryHint(View view, CharSequence charSequence);

                    void setQueryRefinementEnabled(View view, boolean z);

                    void setSearchableInfo(View view, ComponentName componentName);

                    void setSubmitButtonEnabled(View view, boolean z);
                }

        static  SearchViewCompatImpl access$000() {
            return IMPL;
                }

        static class SearchViewCompatStubImpl implements SearchViewCompatImpl {
            SearchViewCompatStubImpl() {
                    }

                    @Override 
            public View newSearchView(Context context) {
                return null;
                    }

                    @Override 
            public void setSearchableInfo(View searchView, ComponentName searchableComponent) {
                    }

                    @Override 
            public void setImeOptions(View searchView, int imeOptions) {
                    }

                    @Override 
            public void setInputType(View searchView, int inputType) {
                    }

                    @Override 
            public Object newOnQueryTextListener(OnQueryTextListenerCompat listener) {
                return null;
                    }

                    @Override 
            public void setOnQueryTextListener(Object searchView, Object listener) {
                    }

                    @Override 
            public Object newOnCloseListener(OnCloseListenerCompat listener) {
                return null;
                    }

                    @Override 
            public void setOnCloseListener(Object searchView, Object listener) {
                    }

                    @Override 
            public CharSequence getQuery(View searchView) {
                return null;
                    }

                    @Override 
            public void setQuery(View searchView, CharSequence query, boolean submit) {
                    }

                    @Override 
           public void setQueryHint(View searchView, CharSequence hint) {
                    }

                    @Override 
           public void setIconified(View searchView, boolean iconify) {
                    }

                    @Override 
           public boolean isIconified(View searchView) {
               return true;
                    }

                    @Override 
           public void setSubmitButtonEnabled(View searchView, boolean enabled) {
                    }

                    @Override 
           public boolean isSubmitButtonEnabled(View searchView) {
               return false;
                    }

                    @Override 
           public void setQueryRefinementEnabled(View searchView, boolean enable) {
                    }

                    @Override 
           public boolean isQueryRefinementEnabled(View searchView) {
               return false;
                    }

                    @Override 
           public void setMaxWidth(View searchView, int maxpixels) {
                    }
                }

       static class SearchViewCompatHoneycombImpl extends SearchViewCompatStubImpl {
           SearchViewCompatHoneycombImpl() {
                    }

                    @Override 
           public View newSearchView(Context context) {
               return SearchViewCompatHoneycomb.newSearchView(context);
                    }

                    @Override 
           public void setSearchableInfo(View searchView, ComponentName searchableComponent) {
               SearchViewCompatHoneycomb.setSearchableInfo(searchView, searchableComponent);
                    }

                    
           class C00681 implements SearchViewCompatHoneycomb.OnQueryTextListenerCompatBridge {
                        final  OnQueryTextListenerCompat val$listener;

               C00681(OnQueryTextListenerCompat onQueryTextListenerCompat) {
                            this.val$listener = onQueryTextListenerCompat;
                        }

                        @Override 
               public boolean onQueryTextSubmit(String query) {
                   return this.val$listener.onQueryTextSubmit(query);
                        }

                        @Override 
               public boolean onQueryTextChange(String newText) {
                   return this.val$listener.onQueryTextChange(newText);
                        }
                    }

                    @Override 
           public Object newOnQueryTextListener(OnQueryTextListenerCompat listener) {
               return SearchViewCompatHoneycomb.newOnQueryTextListener(new C00681(listener));
                    }

                    @Override 
           public void setOnQueryTextListener(Object searchView, Object listener) {
               SearchViewCompatHoneycomb.setOnQueryTextListener(searchView, listener);
                    }

                    
           class C00692 implements SearchViewCompatHoneycomb.OnCloseListenerCompatBridge {
                        final  OnCloseListenerCompat val$listener;

               C00692(OnCloseListenerCompat onCloseListenerCompat) {
                            this.val$listener = onCloseListenerCompat;
                        }

                        @Override 
               public boolean onClose() {
                   return this.val$listener.onClose();
                        }
                    }

                    @Override 
           public Object newOnCloseListener(OnCloseListenerCompat listener) {
               return SearchViewCompatHoneycomb.newOnCloseListener(new C00692(listener));
                    }

                    @Override 
           public void setOnCloseListener(Object searchView, Object listener) {
               SearchViewCompatHoneycomb.setOnCloseListener(searchView, listener);
                    }

                    @Override 
           public CharSequence getQuery(View searchView) {
               return SearchViewCompatHoneycomb.getQuery(searchView);
                    }

                    @Override 
           public void setQuery(View searchView, CharSequence query, boolean submit) {
               SearchViewCompatHoneycomb.setQuery(searchView, query, submit);
                    }

                    @Override 
           public void setQueryHint(View searchView, CharSequence hint) {
               SearchViewCompatHoneycomb.setQueryHint(searchView, hint);
                    }

                    @Override 
           public void setIconified(View searchView, boolean iconify) {
               SearchViewCompatHoneycomb.setIconified(searchView, iconify);
                    }

                    @Override 
           public boolean isIconified(View searchView) {
               return SearchViewCompatHoneycomb.isIconified(searchView);
                    }

                    @Override 
           public void setSubmitButtonEnabled(View searchView, boolean enabled) {
               SearchViewCompatHoneycomb.setSubmitButtonEnabled(searchView, enabled);
                    }

                    @Override 
           public boolean isSubmitButtonEnabled(View searchView) {
               return SearchViewCompatHoneycomb.isSubmitButtonEnabled(searchView);
                    }

                    @Override 
           public void setQueryRefinementEnabled(View searchView, boolean enable) {
               SearchViewCompatHoneycomb.setQueryRefinementEnabled(searchView, enable);
                    }

                    @Override 
           public boolean isQueryRefinementEnabled(View searchView) {
               return SearchViewCompatHoneycomb.isQueryRefinementEnabled(searchView);
                    }

                    @Override 
           public void setMaxWidth(View searchView, int maxpixels) {
               SearchViewCompatHoneycomb.setMaxWidth(searchView, maxpixels);
                    }
                }

       static class SearchViewCompatIcsImpl extends SearchViewCompatHoneycombImpl {
           SearchViewCompatIcsImpl() {
                    }

                    @Override 
           public View newSearchView(Context context) {
               return SearchViewCompatIcs.newSearchView(context);
                    }

                    @Override 
           public void setImeOptions(View searchView, int imeOptions) {
               SearchViewCompatIcs.setImeOptions(searchView, imeOptions);
                    }

                    @Override 
           public void setInputType(View searchView, int inputType) {
               SearchViewCompatIcs.setInputType(searchView, inputType);
                    }
                }

                static {
           if (Build.VERSION.SDK_INT >= 14) {
               IMPL = new SearchViewCompatIcsImpl();
                    } else if (Build.VERSION.SDK_INT >= 11) {
               IMPL = new SearchViewCompatHoneycombImpl();
                    } else {
               IMPL = new SearchViewCompatStubImpl();
                    }
                }

       private SearchViewCompat(Context context) {
                }

       public static View newSearchView(Context context) {
           return IMPL.newSearchView(context);
                }

       public static void setSearchableInfo(View searchView, ComponentName searchableComponent) {
           IMPL.setSearchableInfo(searchView, searchableComponent);
                }

       public static void setImeOptions(View searchView, int imeOptions) {
           IMPL.setImeOptions(searchView, imeOptions);
                }

       public static void setInputType(View searchView, int inputType) {
           IMPL.setInputType(searchView, inputType);
                }

       public static void setOnQueryTextListener(View searchView, OnQueryTextListenerCompat listener) {
           IMPL.setOnQueryTextListener(searchView, listener.mListener);
                }

       public static abstract class OnQueryTextListenerCompat {
                    final Object mListener = SearchViewCompat.access$000().newOnQueryTextListener(this);

           public boolean onQueryTextSubmit(String query) {
               return false;
                    }

           public boolean onQueryTextChange(String newText) {
               return false;
                    }
                }

       public static void setOnCloseListener(View searchView, OnCloseListenerCompat listener) {
           IMPL.setOnCloseListener(searchView, listener.mListener);
                }

       public static abstract class OnCloseListenerCompat {
                    final Object mListener = SearchViewCompat.access$000().newOnCloseListener(this);

           public boolean onClose() {
               return false;
                    }
                }

       public static CharSequence getQuery(View searchView) {
           return IMPL.getQuery(searchView);
                }

       public static void setQuery(View searchView, CharSequence query, boolean submit) {
           IMPL.setQuery(searchView, query, submit);
                }

       public static void setQueryHint(View searchView, CharSequence hint) {
           IMPL.setQueryHint(searchView, hint);
                }

       public static void setIconified(View searchView, boolean iconify) {
           IMPL.setIconified(searchView, iconify);
                }

       public static boolean isIconified(View searchView) {
           return IMPL.isIconified(searchView);
                }

       public static void setSubmitButtonEnabled(View searchView, boolean enabled) {
           IMPL.setSubmitButtonEnabled(searchView, enabled);
                }

       public static boolean isSubmitButtonEnabled(View searchView) {
           return IMPL.isSubmitButtonEnabled(searchView);
                }

       public static void setQueryRefinementEnabled(View searchView, boolean enable) {
           IMPL.setQueryRefinementEnabled(searchView, enable);
                }

       public static boolean isQueryRefinementEnabled(View searchView) {
           return IMPL.isQueryRefinementEnabled(searchView);
                }

       public static void setMaxWidth(View searchView, int maxpixels) {
           IMPL.setMaxWidth(searchView, maxpixels);
                }
            }
