            package android.support.v4.widget;

            import android.content.ComponentName;
            import android.content.Context;
            import android.os.Build;
            import android.view.View;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    public class SearchViewCompat {
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

/* 29 */        static /* synthetic */ SearchViewCompatImpl access$000() {
/* 30 */            return IMPL;
                }

/* 51 */        static class SearchViewCompatStubImpl implements SearchViewCompatImpl {
/* 52 */            SearchViewCompatStubImpl() {
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 56 */            public View newSearchView(Context context) {
/* 57 */                return null;
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 61 */            public void setSearchableInfo(View searchView, ComponentName searchableComponent) {
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 65 */            public void setImeOptions(View searchView, int imeOptions) {
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 69 */            public void setInputType(View searchView, int inputType) {
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 73 */            public Object newOnQueryTextListener(OnQueryTextListenerCompat listener) {
/* 74 */                return null;
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 78 */            public void setOnQueryTextListener(Object searchView, Object listener) {
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 82 */            public Object newOnCloseListener(OnCloseListenerCompat listener) {
/* 83 */                return null;
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 87 */            public void setOnCloseListener(Object searchView, Object listener) {
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 91 */            public CharSequence getQuery(View searchView) {
/* 92 */                return null;
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 96 */            public void setQuery(View searchView, CharSequence query, boolean submit) {
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 100 */           public void setQueryHint(View searchView, CharSequence hint) {
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 104 */           public void setIconified(View searchView, boolean iconify) {
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 108 */           public boolean isIconified(View searchView) {
/* 109 */               return true;
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 113 */           public void setSubmitButtonEnabled(View searchView, boolean enabled) {
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 117 */           public boolean isSubmitButtonEnabled(View searchView) {
/* 118 */               return false;
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 122 */           public void setQueryRefinementEnabled(View searchView, boolean enable) {
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 126 */           public boolean isQueryRefinementEnabled(View searchView) {
/* 127 */               return false;
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 131 */           public void setMaxWidth(View searchView, int maxpixels) {
                    }
                }

/* 133 */       static class SearchViewCompatHoneycombImpl extends SearchViewCompatStubImpl {
/* 134 */           SearchViewCompatHoneycombImpl() {
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatStubImpl, android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 138 */           public View newSearchView(Context context) {
/* 139 */               return SearchViewCompatHoneycomb.newSearchView(context);
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatStubImpl, android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 143 */           public void setSearchableInfo(View searchView, ComponentName searchableComponent) {
/* 144 */               SearchViewCompatHoneycomb.setSearchableInfo(searchView, searchableComponent);
                    }

                    /* JADX INFO: renamed from: android.support.v4.widget.SearchViewCompat$SearchViewCompatHoneycombImpl$1 */
/* 148 */           class C00681 implements SearchViewCompatHoneycomb.OnQueryTextListenerCompatBridge {
                        final /* synthetic */ OnQueryTextListenerCompat val$listener;

/* 149 */               C00681(OnQueryTextListenerCompat onQueryTextListenerCompat) {
                            this.val$listener = onQueryTextListenerCompat;
                        }

                        @Override // android.support.v4.widget.SearchViewCompatHoneycomb.OnQueryTextListenerCompatBridge
/* 152 */               public boolean onQueryTextSubmit(String query) {
/* 153 */                   return this.val$listener.onQueryTextSubmit(query);
                        }

                        @Override // android.support.v4.widget.SearchViewCompatHoneycomb.OnQueryTextListenerCompatBridge
/* 156 */               public boolean onQueryTextChange(String newText) {
/* 157 */                   return this.val$listener.onQueryTextChange(newText);
                        }
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatStubImpl, android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 148 */           public Object newOnQueryTextListener(OnQueryTextListenerCompat listener) {
/* 149 */               return SearchViewCompatHoneycomb.newOnQueryTextListener(new C00681(listener));
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatStubImpl, android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 163 */           public void setOnQueryTextListener(Object searchView, Object listener) {
/* 164 */               SearchViewCompatHoneycomb.setOnQueryTextListener(searchView, listener);
                    }

                    /* JADX INFO: renamed from: android.support.v4.widget.SearchViewCompat$SearchViewCompatHoneycombImpl$2 */
/* 168 */           class C00692 implements SearchViewCompatHoneycomb.OnCloseListenerCompatBridge {
                        final /* synthetic */ OnCloseListenerCompat val$listener;

/* 169 */               C00692(OnCloseListenerCompat onCloseListenerCompat) {
                            this.val$listener = onCloseListenerCompat;
                        }

                        @Override // android.support.v4.widget.SearchViewCompatHoneycomb.OnCloseListenerCompatBridge
/* 172 */               public boolean onClose() {
/* 173 */                   return this.val$listener.onClose();
                        }
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatStubImpl, android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 168 */           public Object newOnCloseListener(OnCloseListenerCompat listener) {
/* 169 */               return SearchViewCompatHoneycomb.newOnCloseListener(new C00692(listener));
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatStubImpl, android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 179 */           public void setOnCloseListener(Object searchView, Object listener) {
/* 180 */               SearchViewCompatHoneycomb.setOnCloseListener(searchView, listener);
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatStubImpl, android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 184 */           public CharSequence getQuery(View searchView) {
/* 185 */               return SearchViewCompatHoneycomb.getQuery(searchView);
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatStubImpl, android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 189 */           public void setQuery(View searchView, CharSequence query, boolean submit) {
/* 190 */               SearchViewCompatHoneycomb.setQuery(searchView, query, submit);
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatStubImpl, android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 194 */           public void setQueryHint(View searchView, CharSequence hint) {
/* 195 */               SearchViewCompatHoneycomb.setQueryHint(searchView, hint);
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatStubImpl, android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 199 */           public void setIconified(View searchView, boolean iconify) {
/* 200 */               SearchViewCompatHoneycomb.setIconified(searchView, iconify);
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatStubImpl, android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 204 */           public boolean isIconified(View searchView) {
/* 205 */               return SearchViewCompatHoneycomb.isIconified(searchView);
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatStubImpl, android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 209 */           public void setSubmitButtonEnabled(View searchView, boolean enabled) {
/* 210 */               SearchViewCompatHoneycomb.setSubmitButtonEnabled(searchView, enabled);
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatStubImpl, android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 214 */           public boolean isSubmitButtonEnabled(View searchView) {
/* 215 */               return SearchViewCompatHoneycomb.isSubmitButtonEnabled(searchView);
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatStubImpl, android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 219 */           public void setQueryRefinementEnabled(View searchView, boolean enable) {
/* 220 */               SearchViewCompatHoneycomb.setQueryRefinementEnabled(searchView, enable);
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatStubImpl, android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 224 */           public boolean isQueryRefinementEnabled(View searchView) {
/* 225 */               return SearchViewCompatHoneycomb.isQueryRefinementEnabled(searchView);
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatStubImpl, android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 229 */           public void setMaxWidth(View searchView, int maxpixels) {
/* 230 */               SearchViewCompatHoneycomb.setMaxWidth(searchView, maxpixels);
                    }
                }

/* 232 */       static class SearchViewCompatIcsImpl extends SearchViewCompatHoneycombImpl {
/* 233 */           SearchViewCompatIcsImpl() {
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatHoneycombImpl, android.support.v4.widget.SearchViewCompat.SearchViewCompatStubImpl, android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 237 */           public View newSearchView(Context context) {
/* 238 */               return SearchViewCompatIcs.newSearchView(context);
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatStubImpl, android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 242 */           public void setImeOptions(View searchView, int imeOptions) {
/* 243 */               SearchViewCompatIcs.setImeOptions(searchView, imeOptions);
                    }

                    @Override // android.support.v4.widget.SearchViewCompat.SearchViewCompatStubImpl, android.support.v4.widget.SearchViewCompat.SearchViewCompatImpl
/* 247 */           public void setInputType(View searchView, int inputType) {
/* 248 */               SearchViewCompatIcs.setInputType(searchView, inputType);
                    }
                }

                static {
/* 255 */           if (Build.VERSION.SDK_INT >= 14) {
/* 256 */               IMPL = new SearchViewCompatIcsImpl();
                    } else if (Build.VERSION.SDK_INT >= 11) {
/* 258 */               IMPL = new SearchViewCompatHoneycombImpl();
                    } else {
/* 260 */               IMPL = new SearchViewCompatStubImpl();
                    }
                }

/* 263 */       private SearchViewCompat(Context context) {
                }

/* 275 */       public static View newSearchView(Context context) {
/* 276 */           return IMPL.newSearchView(context);
                }

/* 289 */       public static void setSearchableInfo(View searchView, ComponentName searchableComponent) {
/* 290 */           IMPL.setSearchableInfo(searchView, searchableComponent);
                }

/* 302 */       public static void setImeOptions(View searchView, int imeOptions) {
/* 303 */           IMPL.setImeOptions(searchView, imeOptions);
                }

/* 315 */       public static void setInputType(View searchView, int inputType) {
/* 316 */           IMPL.setInputType(searchView, inputType);
                }

/* 326 */       public static void setOnQueryTextListener(View searchView, OnQueryTextListenerCompat listener) {
/* 327 */           IMPL.setOnQueryTextListener(searchView, listener.mListener);
                }

/* 351 */       public static abstract class OnQueryTextListenerCompat {
                    final Object mListener = SearchViewCompat.access$000().newOnQueryTextListener(this);

/* 352 */           public boolean onQueryTextSubmit(String query) {
/* 353 */               return false;
                    }

/* 364 */           public boolean onQueryTextChange(String newText) {
/* 365 */               return false;
                    }
                }

/* 375 */       public static void setOnCloseListener(View searchView, OnCloseListenerCompat listener) {
/* 376 */           IMPL.setOnCloseListener(searchView, listener.mListener);
                }

/* 394 */       public static abstract class OnCloseListenerCompat {
                    final Object mListener = SearchViewCompat.access$000().newOnCloseListener(this);

/* 395 */           public boolean onClose() {
/* 396 */               return false;
                    }
                }

/* 407 */       public static CharSequence getQuery(View searchView) {
/* 408 */           return IMPL.getQuery(searchView);
                }

/* 420 */       public static void setQuery(View searchView, CharSequence query, boolean submit) {
/* 421 */           IMPL.setQuery(searchView, query, submit);
                }

/* 431 */       public static void setQueryHint(View searchView, CharSequence hint) {
/* 432 */           IMPL.setQueryHint(searchView, hint);
                }

/* 446 */       public static void setIconified(View searchView, boolean iconify) {
/* 447 */           IMPL.setIconified(searchView, iconify);
                }

/* 457 */       public static boolean isIconified(View searchView) {
/* 458 */           return IMPL.isIconified(searchView);
                }

/* 470 */       public static void setSubmitButtonEnabled(View searchView, boolean enabled) {
/* 471 */           IMPL.setSubmitButtonEnabled(searchView, enabled);
                }

/* 480 */       public static boolean isSubmitButtonEnabled(View searchView) {
/* 481 */           return IMPL.isSubmitButtonEnabled(searchView);
                }

/* 499 */       public static void setQueryRefinementEnabled(View searchView, boolean enable) {
/* 500 */           IMPL.setQueryRefinementEnabled(searchView, enable);
                }

/* 508 */       public static boolean isQueryRefinementEnabled(View searchView) {
/* 509 */           return IMPL.isQueryRefinementEnabled(searchView);
                }

/* 516 */       public static void setMaxWidth(View searchView, int maxpixels) {
/* 517 */           IMPL.setMaxWidth(searchView, maxpixels);
                }
            }
