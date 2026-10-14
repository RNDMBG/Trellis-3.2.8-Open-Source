            package android.support.v7.widget;

            import android.R;
            import android.app.SearchManager;
            import android.app.SearchableInfo;
            import android.content.ComponentName;
            import android.content.Context;
            import android.content.pm.ActivityInfo;
            import android.content.pm.PackageManager;
            import android.content.res.ColorStateList;
            import android.content.res.Resources;
            import android.database.Cursor;
            import android.graphics.drawable.Drawable;
            import android.net.Uri;
            import android.os.Bundle;
            import android.support.v4.widget.ResourceCursorAdapter;
            import android.support.v7.appcompat.C0084R;
            import android.text.SpannableString;
            import android.text.TextUtils;
            import android.text.style.TextAppearanceSpan;
            import android.util.Log;
            import android.util.TypedValue;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ImageView;
            import android.widget.TextView;
            import co.vine.android.provider.Vine;
            import java.io.FileNotFoundException;
            import java.io.IOException;
            import java.io.InputStream;
            import java.util.List;
            import java.util.WeakHashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 92 */    class SuggestionsAdapter extends ResourceCursorAdapter implements View.OnClickListener {
                private static final boolean DBG = false;
                static final int INVALID_INDEX = -1;
                private static final String LOG_TAG = "SuggestionsAdapter";
                private static final int QUERY_LIMIT = 50;
                static final int REFINE_ALL = 2;
                static final int REFINE_BY_ENTRY = 1;
                static final int REFINE_NONE = 0;
                private boolean mClosed;
                private int mFlagsCol;
                private int mIconName1Col;
                private int mIconName2Col;
                private WeakHashMap<String, Drawable.ConstantState> mOutsideDrawablesCache;
                private Context mProviderContext;
                private int mQueryRefinement;
                private SearchManager mSearchManager;
                private SearchView mSearchView;
                private SearchableInfo mSearchable;
                private int mText1Col;
                private int mText2Col;
                private int mText2UrlCol;
                private ColorStateList mUrlColor;

/* 93 */        public SuggestionsAdapter(Context context, SearchView searchView, SearchableInfo searchable, WeakHashMap<String, Drawable.ConstantState> outsideDrawablesCache) {
/* 94 */            super(context, C0084R.layout.abc_search_dropdown_item_icons_2line, (Cursor) null, true);
/* 72 */            this.mClosed = false;
/* 73 */            this.mQueryRefinement = 1;
/* 81 */            this.mText1Col = -1;
/* 82 */            this.mText2Col = -1;
/* 83 */            this.mText2UrlCol = -1;
/* 84 */            this.mIconName1Col = -1;
/* 85 */            this.mIconName2Col = -1;
/* 86 */            this.mFlagsCol = -1;
/* 97 */            this.mSearchManager = (SearchManager) this.mContext.getSystemService("search");
/* 98 */            this.mSearchView = searchView;
/* 99 */            this.mSearchable = searchable;
/* 101 */           this.mProviderContext = context;
/* 103 */           this.mOutsideDrawablesCache = outsideDrawablesCache;
                }

/* 115 */       public void setQueryRefinement(int refineWhat) {
/* 116 */           this.mQueryRefinement = refineWhat;
                }

/* 123 */       public int getQueryRefinement() {
/* 124 */           return this.mQueryRefinement;
                }

                @Override // android.support.v4.widget.CursorAdapter, android.widget.BaseAdapter, android.widget.Adapter
/* 132 */       public boolean hasStableIds() {
/* 133 */           return false;
                }

                @Override // android.support.v4.widget.CursorAdapter, android.support.v4.widget.CursorFilter.CursorFilterClient
/* 143 */       public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
/* 144 */           String query = constraint == null ? "" : constraint.toString();
/* 150 */           if (this.mSearchView.getVisibility() != 0 || this.mSearchView.getWindowVisibility() != 0) {
                        return null;
                    }
                    try {
/* 155 */               Cursor cursor = getSearchManagerSuggestions(this.mSearchable, query, 50);
/* 158 */               if (cursor == null) {
                            return null;
                        }
/* 159 */               cursor.getCount();
                        return cursor;
                    } catch (RuntimeException e) {
/* 163 */               Log.w(LOG_TAG, "Search suggestions query threw an exception.", e);
                        return null;
                    }
                }

/* 171 */       public void close() {
/* 172 */           changeCursor(null);
/* 173 */           this.mClosed = true;
                }

                @Override // android.widget.BaseAdapter
/* 178 */       public void notifyDataSetChanged() {
/* 179 */           super.notifyDataSetChanged();
/* 181 */           updateSpinnerState(getCursor());
                }

                @Override // android.widget.BaseAdapter
/* 186 */       public void notifyDataSetInvalidated() {
/* 187 */           super.notifyDataSetInvalidated();
/* 189 */           updateSpinnerState(getCursor());
                }

/* 192 */       private void updateSpinnerState(Cursor cursor) {
/* 193 */           Bundle extras = cursor != null ? cursor.getExtras() : null;
/* 201 */           if (extras == null || extras.getBoolean("in_progress")) {
                    }
                }

                @Override // android.support.v4.widget.CursorAdapter, android.support.v4.widget.CursorFilter.CursorFilterClient
/* 214 */       public void changeCursor(Cursor c) {
/* 215 */           if (this.mClosed) {
/* 216 */               Log.w(LOG_TAG, "Tried to change cursor after adapter was closed.");
/* 217 */               if (c != null) {
                            c.close();
/* 235 */                   return;
                        }
/* 235 */               return;
                    }
                    try {
/* 222 */               super.changeCursor(c);
/* 224 */               if (c != null) {
/* 225 */                   this.mText1Col = c.getColumnIndex("suggest_text_1");
/* 226 */                   this.mText2Col = c.getColumnIndex("suggest_text_2");
/* 227 */                   this.mText2UrlCol = c.getColumnIndex("suggest_text_2_url");
/* 228 */                   this.mIconName1Col = c.getColumnIndex("suggest_icon_1");
/* 229 */                   this.mIconName2Col = c.getColumnIndex("suggest_icon_2");
/* 230 */                   this.mFlagsCol = c.getColumnIndex("suggest_flags");
                        }
                    } catch (Exception e) {
/* 233 */               Log.e(LOG_TAG, "error changing cursor and caching columns", e);
                    }
                }

                @Override // android.support.v4.widget.ResourceCursorAdapter, android.support.v4.widget.CursorAdapter
/* 241 */       public View newView(Context context, Cursor cursor, ViewGroup parent) {
/* 242 */           View v = super.newView(context, cursor, parent);
/* 243 */           v.setTag(new ChildViewCache(v));
/* 244 */           return v;
                }

/* 256 */       private static final class ChildViewCache {
                    public final ImageView mIcon1;
                    public final ImageView mIcon2;
                    public final ImageView mIconRefine;
                    public final TextView mText1;
                    public final TextView mText2;

/* 257 */           public ChildViewCache(View v) {
/* 259 */               this.mText1 = (TextView) v.findViewById(R.id.text1);
/* 260 */               this.mText2 = (TextView) v.findViewById(R.id.text2);
/* 261 */               this.mIcon1 = (ImageView) v.findViewById(R.id.icon1);
/* 262 */               this.mIcon2 = (ImageView) v.findViewById(R.id.icon2);
/* 263 */               this.mIconRefine = (ImageView) v.findViewById(C0084R.id.edit_query);
                    }
                }

                @Override // android.support.v4.widget.CursorAdapter
/* 268 */       public void bindView(View view, Context context, Cursor cursor) {
                    CharSequence text2;
/* 269 */           ChildViewCache views = (ChildViewCache) view.getTag();
/* 271 */           int flags = 0;
/* 272 */           if (this.mFlagsCol != -1) {
/* 273 */               flags = cursor.getInt(this.mFlagsCol);
                    }
/* 275 */           if (views.mText1 != null) {
/* 276 */               String text1 = getStringOrNull(cursor, this.mText1Col);
/* 277 */               setViewText(views.mText1, text1);
                    }
/* 279 */           if (views.mText2 != null) {
/* 281 */               CharSequence text3 = getStringOrNull(cursor, this.mText2UrlCol);
/* 282 */               if (text3 != null) {
/* 283 */                   text2 = formatUrl(text3);
                        } else {
/* 285 */                   text2 = getStringOrNull(cursor, this.mText2Col);
                        }
/* 290 */               if (TextUtils.isEmpty(text2)) {
/* 291 */                   if (views.mText1 != null) {
/* 292 */                       views.mText1.setSingleLine(false);
/* 293 */                       views.mText1.setMaxLines(2);
                            }
                        } else if (views.mText1 != null) {
/* 297 */                   views.mText1.setSingleLine(true);
/* 298 */                   views.mText1.setMaxLines(1);
                        }
/* 301 */               setViewText(views.mText2, text2);
                    }
/* 304 */           if (views.mIcon1 != null) {
/* 305 */               setViewDrawable(views.mIcon1, getIcon1(cursor), 4);
                    }
/* 307 */           if (views.mIcon2 != null) {
/* 308 */               setViewDrawable(views.mIcon2, getIcon2(cursor), 8);
                    }
/* 310 */           if (this.mQueryRefinement == 2 || (this.mQueryRefinement == 1 && (flags & 1) != 0)) {
/* 313 */               views.mIconRefine.setVisibility(0);
/* 314 */               views.mIconRefine.setTag(views.mText1.getText());
/* 315 */               views.mIconRefine.setOnClickListener(this);
/* 319 */               return;
                    }
/* 317 */           views.mIconRefine.setVisibility(8);
                }

                @Override // android.view.View.OnClickListener
/* 321 */       public void onClick(View v) {
/* 322 */           Object tag = v.getTag();
/* 323 */           if (tag instanceof CharSequence) {
/* 324 */               this.mSearchView.onQueryRefine((CharSequence) tag);
                    }
                }

/* 328 */       private CharSequence formatUrl(CharSequence url) {
/* 329 */           if (this.mUrlColor == null) {
/* 331 */               TypedValue colorValue = new TypedValue();
/* 332 */               this.mContext.getTheme().resolveAttribute(C0084R.attr.textColorSearchUrl, colorValue, true);
/* 333 */               this.mUrlColor = this.mContext.getResources().getColorStateList(colorValue.resourceId);
                    }
/* 336 */           SpannableString text = new SpannableString(url);
/* 337 */           text.setSpan(new TextAppearanceSpan(null, 0, 0, this.mUrlColor, null), 0, url.length(), 33);
/* 340 */           return text;
                }

/* 344 */       private void setViewText(TextView v, CharSequence text) {
/* 345 */           v.setText(text);
/* 347 */           if (TextUtils.isEmpty(text)) {
/* 348 */               v.setVisibility(8);
                    } else {
/* 350 */               v.setVisibility(0);
                    }
                }

/* 354 */       private Drawable getIcon1(Cursor cursor) {
/* 355 */           if (this.mIconName1Col == -1) {
/* 356 */               return null;
                    }
/* 358 */           String value = cursor.getString(this.mIconName1Col);
/* 359 */           Drawable drawable = getDrawableFromResourceValue(value);
                    return drawable == null ? getDefaultIcon1(cursor) : drawable;
                }

/* 366 */       private Drawable getIcon2(Cursor cursor) {
/* 367 */           if (this.mIconName2Col == -1) {
/* 368 */               return null;
                    }
/* 370 */           String value = cursor.getString(this.mIconName2Col);
/* 371 */           return getDrawableFromResourceValue(value);
                }

/* 380 */       private void setViewDrawable(ImageView v, Drawable drawable, int nullVisibility) {
/* 381 */           v.setImageDrawable(drawable);
/* 383 */           if (drawable == null) {
/* 384 */               v.setVisibility(nullVisibility);
/* 396 */               return;
                    }
/* 386 */           v.setVisibility(0);
/* 393 */           drawable.setVisible(false, false);
/* 394 */           drawable.setVisible(true, false);
                }

                @Override // android.support.v4.widget.CursorAdapter, android.support.v4.widget.CursorFilter.CursorFilterClient
/* 407 */       public CharSequence convertToString(Cursor cursor) {
                    String text1;
                    String data;
/* 408 */           if (cursor == null) {
                        return null;
                    }
/* 412 */           String query = getColumnString(cursor, "suggest_intent_query");
/* 413 */           if (query == null) {
/* 417 */               if (this.mSearchable.shouldRewriteQueryFromData() && (data = getColumnString(cursor, "suggest_intent_data")) != null) {
                            return data;
                        }
/* 424 */               if (!this.mSearchable.shouldRewriteQueryFromText() || (text1 = getColumnString(cursor, "suggest_text_1")) == null) {
                            return null;
                        }
                        return text1;
                    }
/* 431 */           return query;
                }

                @Override // android.support.v4.widget.CursorAdapter, android.widget.Adapter
/* 442 */       public View getView(int position, View convertView, ViewGroup parent) {
                    try {
/* 443 */               return super.getView(position, convertView, parent);
                    } catch (RuntimeException e) {
/* 445 */               Log.w(LOG_TAG, "Search suggestions cursor threw exception.", e);
/* 447 */               View v = newView(this.mContext, this.mCursor, parent);
/* 448 */               if (v != null) {
/* 449 */                   ChildViewCache views = (ChildViewCache) v.getTag();
/* 450 */                   TextView tv = views.mText1;
/* 451 */                   tv.setText(e.toString());
/* 453 */                   return v;
                        }
/* 453 */               return v;
                    }
                }

/* 476 */       private Drawable getDrawableFromResourceValue(String drawableId) {
/* 477 */           if (drawableId == null || drawableId.length() == 0 || "0".equals(drawableId)) {
                        return null;
                    }
                    try {
/* 482 */               int resourceId = Integer.parseInt(drawableId);
/* 484 */               String drawableUri = "android.resource://" + this.mProviderContext.getPackageName() + "/" + resourceId;
/* 487 */               Drawable drawable = checkIconCache(drawableUri);
/* 488 */               if (drawable == null) {
/* 492 */                   Drawable drawable2 = this.mProviderContext.getResources().getDrawable(resourceId);
/* 494 */                   storeInIconCache(drawableUri, drawable2);
/* 509 */                   return drawable2;
                        }
/* 509 */               return drawable;
                    } catch (Resources.NotFoundException e) {
/* 508 */               Log.w(LOG_TAG, "Icon resource not found: " + drawableId);
                        return null;
                    } catch (NumberFormatException e2) {
/* 498 */               Drawable drawable3 = checkIconCache(drawableId);
/* 499 */               if (drawable3 == null) {
/* 502 */                   Uri uri = Uri.parse(drawableId);
/* 503 */                   Drawable drawable4 = getDrawable(uri);
/* 504 */                   storeInIconCache(drawableId, drawable4);
/* 509 */                   return drawable4;
                        }
/* 509 */               return drawable3;
                    }
                }

/* 519 */       private Drawable getDrawable(Uri uri) {
                    try {
/* 520 */               String scheme = uri.getScheme();
/* 521 */               if ("android.resource".equals(scheme)) {
                            try {
/* 524 */                       return getDrawableFromResourceUri(uri);
                            } catch (Resources.NotFoundException e) {
/* 526 */                       throw new FileNotFoundException("Resource does not exist: " + uri);
                            }
                        }
/* 530 */               InputStream stream = this.mProviderContext.getContentResolver().openInputStream(uri);
/* 531 */               if (stream == null) {
/* 532 */                   throw new FileNotFoundException("Failed to open " + uri);
                        }
                        try {
/* 535 */                   Drawable drawableCreateFromStream = Drawable.createFromStream(stream, null);
                            try {
/* 538 */                       stream.close();
/* 546 */                       return drawableCreateFromStream;
                            } catch (IOException ex) {
/* 540 */                       Log.e(LOG_TAG, "Error closing icon stream for " + uri, ex);
/* 546 */                       return drawableCreateFromStream;
                            }
                        } catch (Throwable th) {
                            try {
/* 538 */                       stream.close();
                            } catch (IOException ex2) {
/* 540 */                       Log.e(LOG_TAG, "Error closing icon stream for " + uri, ex2);
                            }
/* 541 */                   throw th;
                        }
                    } catch (FileNotFoundException fnfe) {
/* 545 */               Log.w(LOG_TAG, "Icon not found: " + uri + ", " + fnfe.getMessage());
                        return null;
                    }
/* 545 */           Log.w(LOG_TAG, "Icon not found: " + uri + ", " + fnfe.getMessage());
                    return null;
                }

/* 552 */       private Drawable checkIconCache(String resourceUri) {
/* 553 */           Drawable.ConstantState cached = this.mOutsideDrawablesCache.get(resourceUri);
/* 554 */           if (cached == null) {
/* 555 */               return null;
                    }
                    return cached.newDrawable();
                }

/* 561 */       private void storeInIconCache(String resourceUri, Drawable drawable) {
/* 562 */           if (drawable != null) {
/* 563 */               this.mOutsideDrawablesCache.put(resourceUri, drawable.getConstantState());
                    }
                }

/* 575 */       private Drawable getDefaultIcon1(Cursor cursor) {
/* 576 */           Drawable drawable = getActivityIconWithCache(this.mSearchable.getSearchActivity());
                    return drawable != null ? drawable : this.mContext.getPackageManager().getDefaultActivityIcon();
                }

/* 594 */       private Drawable getActivityIconWithCache(ComponentName component) {
/* 595 */           String componentIconKey = component.flattenToShortString();
/* 597 */           if (this.mOutsideDrawablesCache.containsKey(componentIconKey)) {
/* 598 */               Drawable.ConstantState cached = this.mOutsideDrawablesCache.get(componentIconKey);
/* 599 */               if (cached == null) {
                            return null;
                        }
/* 599 */               return cached.newDrawable(this.mProviderContext.getResources());
                    }
/* 602 */           Drawable drawable = getActivityIcon(component);
/* 604 */           Drawable.ConstantState toCache = drawable == null ? null : drawable.getConstantState();
/* 605 */           this.mOutsideDrawablesCache.put(componentIconKey, toCache);
                    return drawable;
                }

/* 616 */       private Drawable getActivityIcon(ComponentName component) {
/* 617 */           PackageManager pm = this.mContext.getPackageManager();
                    try {
/* 620 */               ActivityInfo activityInfo = pm.getActivityInfo(component, 128);
/* 625 */               int iconId = activityInfo.getIconResource();
/* 626 */               if (iconId == 0) {
                            return null;
                        }
/* 627 */               String pkg = component.getPackageName();
/* 628 */               Drawable drawable = pm.getDrawable(pkg, iconId, activityInfo.applicationInfo);
/* 629 */               if (drawable == null) {
/* 630 */                   Log.w(LOG_TAG, "Invalid icon resource " + iconId + " for " + component.flattenToShortString());
                            return null;
                        }
/* 634 */               return drawable;
                    } catch (PackageManager.NameNotFoundException ex) {
/* 622 */               Log.w(LOG_TAG, ex.toString());
                        return null;
                    }
                }

/* 645 */       public static String getColumnString(Cursor cursor, String columnName) {
/* 646 */           int col = cursor.getColumnIndex(columnName);
/* 647 */           return getStringOrNull(cursor, col);
                }

/* 650 */       private static String getStringOrNull(Cursor cursor, int col) {
/* 651 */           if (col == -1) {
                        return null;
                    }
                    try {
/* 655 */               return cursor.getString(col);
                    } catch (Exception e) {
/* 657 */               Log.e(LOG_TAG, "unexpected error retrieving valid column from cursor, did the remote process die?", e);
                        return null;
                    }
                }

/* 668 */       Drawable getDrawableFromResourceUri(Uri uri) throws FileNotFoundException {
                    int id;
/* 669 */           String authority = uri.getAuthority();
/* 671 */           if (TextUtils.isEmpty(authority)) {
/* 672 */               throw new FileNotFoundException("No authority: " + uri);
                    }
                    try {
/* 675 */               Resources r = this.mContext.getPackageManager().getResourcesForApplication(authority);
/* 680 */               List<String> path = uri.getPathSegments();
/* 681 */               if (path == null) {
/* 682 */                   throw new FileNotFoundException("No path: " + uri);
                        }
/* 684 */               int len = path.size();
/* 686 */               if (len == 1) {
                            try {
/* 688 */                       id = Integer.parseInt(path.get(0));
                            } catch (NumberFormatException e) {
/* 690 */                       throw new FileNotFoundException("Single path segment is not a resource ID: " + uri);
                            }
                        } else if (len == 2) {
/* 693 */                   id = r.getIdentifier(path.get(1), path.get(0), authority);
                        } else {
/* 695 */                   throw new FileNotFoundException("More than two path segments: " + uri);
                        }
/* 697 */               if (id == 0) {
/* 698 */                   throw new FileNotFoundException("No resource found for: " + uri);
                        }
/* 700 */               return r.getDrawable(id);
                    } catch (PackageManager.NameNotFoundException e2) {
/* 677 */               throw new FileNotFoundException("No package found for authority: " + uri);
                    }
                }

/* 706 */       Cursor getSearchManagerSuggestions(SearchableInfo searchable, String query, int limit) {
                    String authority;
/* 707 */           if (searchable == null || (authority = searchable.getSuggestAuthority()) == null) {
                        return null;
                    }
/* 716 */           Uri.Builder uriBuilder = new Uri.Builder().scheme("content").authority(authority).query("").fragment("");
/* 723 */           String contentPath = searchable.getSuggestPath();
/* 724 */           if (contentPath != null) {
/* 725 */               uriBuilder.appendEncodedPath(contentPath);
                    }
/* 729 */           uriBuilder.appendPath("search_suggest_query");
/* 732 */           String selection = searchable.getSuggestSelection();
/* 734 */           String[] selArgs = null;
/* 735 */           if (selection != null) {
/* 736 */               selArgs = new String[]{query};
                    } else {
/* 738 */               uriBuilder.appendPath(query);
                    }
/* 741 */           if (limit > 0) {
/* 742 */               uriBuilder.appendQueryParameter(Vine.QUERY_PARAMETER_LIMIT, String.valueOf(limit));
                    }
/* 745 */           Uri uri = uriBuilder.build();
/* 748 */           return this.mContext.getContentResolver().query(uri, null, selection, selArgs, null);
                }
            }
