            package android.support.v7.widget;

            import android.app.PendingIntent;
            import android.app.SearchableInfo;
            import android.content.ActivityNotFoundException;
            import android.content.ComponentName;
            import android.content.Context;
            import android.content.Intent;
            import android.content.pm.ResolveInfo;
            import android.content.res.Resources;
            import android.content.res.TypedArray;
            import android.database.Cursor;
            import android.graphics.Rect;
            import android.graphics.drawable.Drawable;
            import android.net.Uri;
            import android.os.Build;
            import android.os.Bundle;
            import android.os.ResultReceiver;
            import android.support.v4.view.KeyEventCompat;
            import android.support.v4.widget.CursorAdapter;
            import android.support.v7.appcompat.C0084R;
            import android.support.v7.view.CollapsibleActionView;
            import android.text.Editable;
            import android.text.SpannableStringBuilder;
            import android.text.TextUtils;
            import android.text.TextWatcher;
            import android.text.style.ImageSpan;
            import android.util.AttributeSet;
            import android.util.Log;
            import android.util.TypedValue;
            import android.view.KeyEvent;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.ViewTreeObserver;
            import android.view.inputmethod.InputMethodManager;
            import android.widget.AdapterView;
            import android.widget.AutoCompleteTextView;
            import android.widget.ImageView;
            import android.widget.LinearLayout;
            import android.widget.TextView;
            import com.googlecode.javacv.cpp.swscale;
            import java.lang.reflect.Method;
            import java.util.WeakHashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 101 */   public class SearchView extends LinearLayout implements CollapsibleActionView {
                private static final boolean DBG = false;
                static final AutoCompleteTextViewReflector HIDDEN_METHOD_INVOKER = new AutoCompleteTextViewReflector();
                private static final String IME_OPTION_NO_MICROPHONE = "nm";
                private static final String LOG_TAG = "SearchView";
                private Bundle mAppSearchData;
                private boolean mClearingFocus;
                private ImageView mCloseButton;
                private int mCollapsedImeOptions;
                private View mDropDownAnchor;
                private boolean mExpandedInActionView;
                private boolean mIconified;
                private boolean mIconifiedByDefault;
                private int mMaxWidth;
                private CharSequence mOldQueryText;
                private final View.OnClickListener mOnClickListener;
                private OnCloseListener mOnCloseListener;
                private final TextView.OnEditorActionListener mOnEditorActionListener;
                private final AdapterView.OnItemClickListener mOnItemClickListener;
                private final AdapterView.OnItemSelectedListener mOnItemSelectedListener;
                private OnQueryTextListener mOnQueryChangeListener;
                private View.OnFocusChangeListener mOnQueryTextFocusChangeListener;
                private View.OnClickListener mOnSearchClickListener;
                private OnSuggestionListener mOnSuggestionListener;
                private final WeakHashMap<String, Drawable.ConstantState> mOutsideDrawablesCache;
                private CharSequence mQueryHint;
                private boolean mQueryRefinement;
                private SearchAutoComplete mQueryTextView;
                private Runnable mReleaseCursorRunnable;
                private View mSearchButton;
                private View mSearchEditFrame;
                private ImageView mSearchHintIcon;
                private View mSearchPlate;
                private SearchableInfo mSearchable;
                private Runnable mShowImeRunnable;
                private View mSubmitArea;
                private View mSubmitButton;
                private boolean mSubmitButtonEnabled;
                private CursorAdapter mSuggestionsAdapter;
                View.OnKeyListener mTextKeyListener;
                private TextWatcher mTextWatcher;
                private Runnable mUpdateDrawableStateRunnable;
                private CharSequence mUserQuery;
                private final Intent mVoiceAppSearchIntent;
                private View mVoiceButton;
                private boolean mVoiceButtonEnabled;
                private final Intent mVoiceWebSearchIntent;

                public interface OnCloseListener {
                    boolean onClose();
                }

                public interface OnQueryTextListener {
                    boolean onQueryTextChange(String str);

                    boolean onQueryTextSubmit(String str);
                }

                public interface OnSuggestionListener {
                    boolean onSuggestionClick(int i);

                    boolean onSuggestionSelect(int i);
                }

/* 102 */       static /* synthetic */ void access$000(SearchView x0) {
/* 103 */           x0.updateFocusedState();
                }

/* 102 */       static /* synthetic */ CursorAdapter access$100(SearchView x0) {
/* 103 */           return x0.mSuggestionsAdapter;
                }

/* 102 */       static /* synthetic */ View access$1000(SearchView x0) {
/* 103 */           return x0.mVoiceButton;
                }

/* 102 */       static /* synthetic */ void access$1100(SearchView x0) {
/* 103 */           x0.onVoiceClicked();
                }

/* 102 */       static /* synthetic */ SearchAutoComplete access$1200(SearchView x0) {
/* 103 */           return x0.mQueryTextView;
                }

/* 102 */       static /* synthetic */ void access$1300(SearchView x0) {
/* 103 */           x0.forceSuggestionQuery();
                }

/* 102 */       static /* synthetic */ SearchableInfo access$1400(SearchView x0) {
/* 103 */           return x0.mSearchable;
                }

/* 102 */       static /* synthetic */ boolean access$1500(SearchView x0, View x1, int x2, KeyEvent x3) {
/* 103 */           return x0.onSuggestionsKey(x1, x2, x3);
                }

/* 102 */       static /* synthetic */ void access$1700(SearchView x0, int x1, String x2, String x3) {
/* 103 */           x0.launchQuerySearch(x1, x2, x3);
                }

/* 102 */       static /* synthetic */ boolean access$1800(SearchView x0, int x1, int x2, String x3) {
/* 103 */           return x0.onItemClicked(x1, x2, x3);
                }

/* 102 */       static /* synthetic */ boolean access$1900(SearchView x0, int x1) {
/* 103 */           return x0.onItemSelected(x1);
                }

/* 102 */       static /* synthetic */ View.OnFocusChangeListener access$200(SearchView x0) {
/* 103 */           return x0.mOnQueryTextFocusChangeListener;
                }

/* 102 */       static /* synthetic */ void access$2000(SearchView x0, CharSequence x1) {
/* 103 */           x0.onTextChanged(x1);
                }

/* 102 */       static /* synthetic */ void access$2100(SearchView x0, boolean x1) {
/* 103 */           x0.setImeVisibility(x1);
                }

/* 102 */       static /* synthetic */ void access$300(SearchView x0) {
/* 103 */           x0.adjustDropDownSizeAndPosition();
                }

/* 102 */       static /* synthetic */ View access$400(SearchView x0) {
/* 103 */           return x0.mSearchButton;
                }

/* 102 */       static /* synthetic */ void access$500(SearchView x0) {
/* 103 */           x0.onSearchClicked();
                }

/* 102 */       static /* synthetic */ ImageView access$600(SearchView x0) {
/* 103 */           return x0.mCloseButton;
                }

/* 102 */       static /* synthetic */ void access$700(SearchView x0) {
/* 103 */           x0.onCloseClicked();
                }

/* 102 */       static /* synthetic */ View access$800(SearchView x0) {
/* 103 */           return x0.mSubmitButton;
                }

/* 102 */       static /* synthetic */ void access$900(SearchView x0) {
/* 103 */           x0.onSubmitQuery();
                }

                /* JADX INFO: renamed from: android.support.v7.widget.SearchView$1 */
/* 150 */       class RunnableC01091 implements Runnable {
/* 151 */           RunnableC01091() {
                    }

                    @Override // java.lang.Runnable
/* 153 */           public void run() {
/* 154 */               InputMethodManager imm = (InputMethodManager) SearchView.this.getContext().getSystemService("input_method");
/* 157 */               if (imm != null) {
/* 158 */                   SearchView.HIDDEN_METHOD_INVOKER.showSoftInputUnchecked(imm, SearchView.this, 0);
                        }
                    }
                }

                /* JADX INFO: renamed from: android.support.v7.widget.SearchView$2 */
/* 161 */       class RunnableC01132 implements Runnable {
/* 162 */           RunnableC01132() {
                    }

                    @Override // java.lang.Runnable
/* 164 */           public void run() {
/* 165 */               SearchView.access$000(SearchView.this);
                    }
                }

                /* JADX INFO: renamed from: android.support.v7.widget.SearchView$3 */
/* 167 */       class RunnableC01143 implements Runnable {
/* 168 */           RunnableC01143() {
                    }

                    @Override // java.lang.Runnable
/* 170 */           public void run() {
/* 171 */               if (SearchView.access$100(SearchView.this) != null && (SearchView.access$100(SearchView.this) instanceof SuggestionsAdapter)) {
/* 172 */                   SearchView.access$100(SearchView.this).changeCursor(null);
                        }
                    }
                }

/* 253 */       public SearchView(Context context) {
/* 254 */           this(context, null);
                }

/* 257 */       public SearchView(Context context, AttributeSet attrs) {
/* 258 */           super(context, attrs);
/* 152 */           this.mShowImeRunnable = new RunnableC01091();
/* 163 */           this.mUpdateDrawableStateRunnable = new RunnableC01132();
/* 169 */           this.mReleaseCursorRunnable = new RunnableC01143();
/* 183 */           this.mOutsideDrawablesCache = new WeakHashMap<>();
/* 883 */           this.mOnClickListener = new ViewOnClickListenerC01187();
/* 923 */           this.mTextKeyListener = new ViewOnKeyListenerC01198();
/* 1098 */          this.mOnEditorActionListener = new C01209();
/* 1274 */          this.mOnItemClickListener = new C011010();
/* 1285 */          this.mOnItemSelectedListener = new C011111();
/* 1572 */          this.mTextWatcher = new C011212();
/* 260 */           LayoutInflater inflater = (LayoutInflater) context.getSystemService("layout_inflater");
/* 262 */           inflater.inflate(C0084R.layout.abc_search_view, (ViewGroup) this, true);
/* 264 */           this.mSearchButton = findViewById(C0084R.id.search_button);
/* 265 */           this.mQueryTextView = (SearchAutoComplete) findViewById(C0084R.id.search_src_text);
/* 266 */           this.mQueryTextView.setSearchView(this);
/* 268 */           this.mSearchEditFrame = findViewById(C0084R.id.search_edit_frame);
/* 269 */           this.mSearchPlate = findViewById(C0084R.id.search_plate);
/* 270 */           this.mSubmitArea = findViewById(C0084R.id.submit_area);
/* 271 */           this.mSubmitButton = findViewById(C0084R.id.search_go_btn);
/* 272 */           this.mCloseButton = (ImageView) findViewById(C0084R.id.search_close_btn);
/* 273 */           this.mVoiceButton = findViewById(C0084R.id.search_voice_btn);
/* 274 */           this.mSearchHintIcon = (ImageView) findViewById(C0084R.id.search_mag_icon);
/* 276 */           this.mSearchButton.setOnClickListener(this.mOnClickListener);
/* 277 */           this.mCloseButton.setOnClickListener(this.mOnClickListener);
/* 278 */           this.mSubmitButton.setOnClickListener(this.mOnClickListener);
/* 279 */           this.mVoiceButton.setOnClickListener(this.mOnClickListener);
/* 280 */           this.mQueryTextView.setOnClickListener(this.mOnClickListener);
/* 282 */           this.mQueryTextView.addTextChangedListener(this.mTextWatcher);
/* 283 */           this.mQueryTextView.setOnEditorActionListener(this.mOnEditorActionListener);
/* 284 */           this.mQueryTextView.setOnItemClickListener(this.mOnItemClickListener);
/* 285 */           this.mQueryTextView.setOnItemSelectedListener(this.mOnItemSelectedListener);
/* 286 */           this.mQueryTextView.setOnKeyListener(this.mTextKeyListener);
/* 288 */           this.mQueryTextView.setOnFocusChangeListener(new ViewOnFocusChangeListenerC01154());
/* 297 */           TypedArray a = context.obtainStyledAttributes(attrs, C0084R.styleable.SearchView, 0, 0);
/* 298 */           setIconifiedByDefault(a.getBoolean(3, true));
/* 299 */           int maxWidth = a.getDimensionPixelSize(0, -1);
/* 300 */           if (maxWidth != -1) {
/* 301 */               setMaxWidth(maxWidth);
                    }
/* 303 */           CharSequence queryHint = a.getText(4);
/* 304 */           if (!TextUtils.isEmpty(queryHint)) {
/* 305 */               setQueryHint(queryHint);
                    }
/* 307 */           int imeOptions = a.getInt(2, -1);
/* 308 */           if (imeOptions != -1) {
/* 309 */               setImeOptions(imeOptions);
                    }
/* 311 */           int inputType = a.getInt(1, -1);
/* 312 */           if (inputType != -1) {
/* 313 */               setInputType(inputType);
                    }
/* 316 */           a.recycle();
/* 320 */           TypedArray a2 = context.obtainStyledAttributes(attrs, C0084R.styleable.View, 0, 0);
/* 321 */           boolean focusable = a2.getBoolean(0, true);
/* 322 */           a2.recycle();
/* 323 */           setFocusable(focusable);
/* 326 */           this.mVoiceWebSearchIntent = new Intent("android.speech.action.WEB_SEARCH");
/* 327 */           this.mVoiceWebSearchIntent.addFlags(268435456);
/* 328 */           this.mVoiceWebSearchIntent.putExtra("android.speech.extra.LANGUAGE_MODEL", "web_search");
/* 331 */           this.mVoiceAppSearchIntent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
/* 332 */           this.mVoiceAppSearchIntent.addFlags(268435456);
/* 334 */           this.mDropDownAnchor = findViewById(this.mQueryTextView.getDropDownAnchor());
/* 335 */           if (this.mDropDownAnchor != null) {
/* 336 */               if (Build.VERSION.SDK_INT >= 11) {
/* 337 */                   addOnLayoutChangeListenerToDropDownAnchorSDK11();
                        } else {
/* 339 */                   addOnLayoutChangeListenerToDropDownAnchorBase();
                        }
                    }
/* 343 */           updateViewsVisibility(this.mIconifiedByDefault);
/* 344 */           updateQueryHint();
                }

                /* JADX INFO: renamed from: android.support.v7.widget.SearchView$4 */
/* 286 */       class ViewOnFocusChangeListenerC01154 implements View.OnFocusChangeListener {
/* 287 */           ViewOnFocusChangeListenerC01154() {
                    }

                    @Override // android.view.View.OnFocusChangeListener
/* 290 */           public void onFocusChange(View v, boolean hasFocus) {
/* 291 */               if (SearchView.access$200(SearchView.this) != null) {
/* 292 */                   SearchView.access$200(SearchView.this).onFocusChange(SearchView.this, hasFocus);
                        }
                    }
                }

                /* JADX INFO: renamed from: android.support.v7.widget.SearchView$5 */
/* 346 */       class ViewOnLayoutChangeListenerC01165 implements View.OnLayoutChangeListener {
/* 347 */           ViewOnLayoutChangeListenerC01165() {
                    }

                    @Override // android.view.View.OnLayoutChangeListener
/* 351 */           public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
/* 352 */               SearchView.access$300(SearchView.this);
                    }
                }

/* 347 */       private void addOnLayoutChangeListenerToDropDownAnchorSDK11() {
/* 348 */           this.mDropDownAnchor.addOnLayoutChangeListener(new ViewOnLayoutChangeListenerC01165());
                }

                /* JADX INFO: renamed from: android.support.v7.widget.SearchView$6 */
/* 357 */       class ViewTreeObserverOnGlobalLayoutListenerC01176 implements ViewTreeObserver.OnGlobalLayoutListener {
/* 358 */           ViewTreeObserverOnGlobalLayoutListenerC01176() {
                    }

                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
/* 361 */           public void onGlobalLayout() {
/* 362 */               SearchView.access$300(SearchView.this);
                    }
                }

/* 357 */       private void addOnLayoutChangeListenerToDropDownAnchorBase() {
/* 358 */           this.mDropDownAnchor.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserverOnGlobalLayoutListenerC01176());
                }

/* 375 */       public void setSearchableInfo(SearchableInfo searchable) {
/* 376 */           this.mSearchable = searchable;
/* 377 */           if (this.mSearchable != null) {
/* 378 */               updateSearchAutoComplete();
/* 379 */               updateQueryHint();
                    }
/* 382 */           this.mVoiceButtonEnabled = hasVoiceSearch();
/* 384 */           if (this.mVoiceButtonEnabled) {
/* 387 */               this.mQueryTextView.setPrivateImeOptions(IME_OPTION_NO_MICROPHONE);
                    }
/* 389 */           updateViewsVisibility(isIconified());
                }

/* 397 */       public void setAppSearchData(Bundle appSearchData) {
/* 398 */           this.mAppSearchData = appSearchData;
                }

/* 409 */       public void setImeOptions(int imeOptions) {
/* 410 */           this.mQueryTextView.setImeOptions(imeOptions);
                }

/* 420 */       public int getImeOptions() {
/* 421 */           return this.mQueryTextView.getImeOptions();
                }

/* 432 */       public void setInputType(int inputType) {
/* 433 */           this.mQueryTextView.setInputType(inputType);
                }

/* 442 */       public int getInputType() {
/* 443 */           return this.mQueryTextView.getInputType();
                }

                @Override // android.view.ViewGroup, android.view.View
/* 449 */       public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
/* 450 */           if (!this.mClearingFocus && isFocusable()) {
/* 454 */               if (!isIconified()) {
/* 455 */                   boolean result = this.mQueryTextView.requestFocus(direction, previouslyFocusedRect);
/* 456 */                   if (result) {
/* 457 */                       updateViewsVisibility(false);
/* 461 */                       return result;
                            }
/* 461 */                   return result;
                        }
/* 461 */               return super.requestFocus(direction, previouslyFocusedRect);
                    }
                    return false;
                }

                @Override // android.view.ViewGroup, android.view.View
/* 467 */       public void clearFocus() {
/* 468 */           this.mClearingFocus = true;
/* 469 */           setImeVisibility(false);
/* 470 */           super.clearFocus();
/* 471 */           this.mQueryTextView.clearFocus();
/* 472 */           this.mClearingFocus = false;
                }

/* 481 */       public void setOnQueryTextListener(OnQueryTextListener listener) {
/* 482 */           this.mOnQueryChangeListener = listener;
                }

/* 490 */       public void setOnCloseListener(OnCloseListener listener) {
/* 491 */           this.mOnCloseListener = listener;
                }

/* 499 */       public void setOnQueryTextFocusChangeListener(View.OnFocusChangeListener listener) {
/* 500 */           this.mOnQueryTextFocusChangeListener = listener;
                }

/* 508 */       public void setOnSuggestionListener(OnSuggestionListener listener) {
/* 509 */           this.mOnSuggestionListener = listener;
                }

/* 520 */       public void setOnSearchClickListener(View.OnClickListener listener) {
/* 521 */           this.mOnSearchClickListener = listener;
                }

/* 529 */       public CharSequence getQuery() {
/* 530 */           return this.mQueryTextView.getText();
                }

/* 541 */       public void setQuery(CharSequence query, boolean submit) {
/* 542 */           this.mQueryTextView.setText(query);
/* 543 */           if (query != null) {
/* 544 */               this.mQueryTextView.setSelection(this.mQueryTextView.length());
/* 545 */               this.mUserQuery = query;
                    }
/* 549 */           if (submit && !TextUtils.isEmpty(query)) {
/* 550 */               onSubmitQuery();
                    }
                }

/* 562 */       public void setQueryHint(CharSequence hint) {
/* 563 */           this.mQueryHint = hint;
/* 564 */           updateQueryHint();
                }

/* 573 */       public CharSequence getQueryHint() {
                    int hintId;
/* 574 */           if (this.mQueryHint != null) {
/* 575 */               return this.mQueryHint;
                    }
/* 576 */           if (this.mSearchable == null || (hintId = this.mSearchable.getHintId()) == 0) {
/* 577 */               return null;
                    }
/* 580 */           CharSequence hint = getContext().getString(hintId);
/* 584 */           return hint;
                }

/* 599 */       public void setIconifiedByDefault(boolean iconified) {
/* 600 */           if (this.mIconifiedByDefault != iconified) {
/* 601 */               this.mIconifiedByDefault = iconified;
/* 602 */               updateViewsVisibility(iconified);
/* 603 */               updateQueryHint();
                    }
                }

/* 612 */       public boolean isIconfiedByDefault() {
/* 613 */           return this.mIconifiedByDefault;
                }

/* 626 */       public void setIconified(boolean iconify) {
/* 627 */           if (iconify) {
/* 628 */               onCloseClicked();
                    } else {
/* 630 */               onSearchClicked();
                    }
                }

/* 640 */       public boolean isIconified() {
/* 641 */           return this.mIconified;
                }

/* 652 */       public void setSubmitButtonEnabled(boolean enabled) {
/* 653 */           this.mSubmitButtonEnabled = enabled;
/* 654 */           updateViewsVisibility(isIconified());
                }

/* 662 */       public boolean isSubmitButtonEnabled() {
/* 663 */           return this.mSubmitButtonEnabled;
                }

/* 680 */       public void setQueryRefinementEnabled(boolean enable) {
/* 681 */           this.mQueryRefinement = enable;
/* 682 */           if (this.mSuggestionsAdapter instanceof SuggestionsAdapter) {
/* 683 */               ((SuggestionsAdapter) this.mSuggestionsAdapter).setQueryRefinement(enable ? 2 : 1);
                    }
                }

/* 692 */       public boolean isQueryRefinementEnabled() {
/* 693 */           return this.mQueryRefinement;
                }

/* 702 */       public void setSuggestionsAdapter(CursorAdapter adapter) {
/* 703 */           this.mSuggestionsAdapter = adapter;
/* 705 */           this.mQueryTextView.setAdapter(this.mSuggestionsAdapter);
                }

/* 712 */       public CursorAdapter getSuggestionsAdapter() {
/* 713 */           return this.mSuggestionsAdapter;
                }

/* 721 */       public void setMaxWidth(int maxpixels) {
/* 722 */           this.mMaxWidth = maxpixels;
/* 724 */           requestLayout();
                }

/* 734 */       public int getMaxWidth() {
/* 735 */           return this.mMaxWidth;
                }

                @Override // android.widget.LinearLayout, android.view.View
/* 740 */       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 741 */           if (isIconified()) {
/* 742 */               super.onMeasure(widthMeasureSpec, heightMeasureSpec);
/* 771 */               return;
                    }
/* 746 */           int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
/* 747 */           int width = View.MeasureSpec.getSize(widthMeasureSpec);
                    switch (widthMode) {
                        case Integer.MIN_VALUE:
/* 755 */                   width = this.mMaxWidth > 0 ? Math.min(this.mMaxWidth, width) : Math.min(getPreferredWidth(), width);
                            break;
                        case 0:
/* 766 */                   width = this.mMaxWidth <= 0 ? getPreferredWidth() : this.mMaxWidth;
                            break;
                        case 1073741824:
/* 760 */                   if (this.mMaxWidth > 0) {
/* 761 */                       width = Math.min(this.mMaxWidth, width);
                            }
                            break;
                    }
/* 770 */           super.onMeasure(View.MeasureSpec.makeMeasureSpec(width, 1073741824), heightMeasureSpec);
                }

/* 773 */       private int getPreferredWidth() {
/* 774 */           return getContext().getResources().getDimensionPixelSize(C0084R.dimen.abc_search_view_preferred_width);
                }

/* 778 */       private void updateViewsVisibility(boolean collapsed) {
/* 779 */           this.mIconified = collapsed;
/* 781 */           int visCollapsed = collapsed ? 0 : 8;
/* 783 */           boolean hasText = !TextUtils.isEmpty(this.mQueryTextView.getText());
/* 785 */           this.mSearchButton.setVisibility(visCollapsed);
/* 786 */           updateSubmitButton(hasText);
/* 787 */           this.mSearchEditFrame.setVisibility(collapsed ? 8 : 0);
/* 788 */           this.mSearchHintIcon.setVisibility(this.mIconifiedByDefault ? 8 : 0);
/* 789 */           updateCloseButton();
/* 790 */           updateVoiceButton(hasText ? false : true);
/* 791 */           updateSubmitArea();
                }

/* 794 */       private boolean hasVoiceSearch() {
/* 795 */           if (this.mSearchable == null || !this.mSearchable.getVoiceSearchEnabled()) {
                        return false;
                    }
/* 796 */           Intent testIntent = null;
/* 797 */           if (this.mSearchable.getVoiceSearchLaunchWebSearch()) {
/* 798 */               testIntent = this.mVoiceWebSearchIntent;
                    } else if (this.mSearchable.getVoiceSearchLaunchRecognizer()) {
/* 800 */               testIntent = this.mVoiceAppSearchIntent;
                    }
/* 802 */           if (testIntent == null) {
                        return false;
                    }
/* 803 */           ResolveInfo ri = getContext().getPackageManager().resolveActivity(testIntent, 65536);
                    return ri != null;
                }

/* 811 */       private boolean isSubmitAreaEnabled() {
                    return (this.mSubmitButtonEnabled || this.mVoiceButtonEnabled) && !isIconified();
                }

/* 815 */       private void updateSubmitButton(boolean hasText) {
/* 816 */           int visibility = 8;
/* 817 */           if (this.mSubmitButtonEnabled && isSubmitAreaEnabled() && hasFocus() && (hasText || !this.mVoiceButtonEnabled)) {
/* 819 */               visibility = 0;
                    }
/* 821 */           this.mSubmitButton.setVisibility(visibility);
                }

/* 824 */       private void updateSubmitArea() {
/* 825 */           int visibility = 8;
/* 826 */           if (isSubmitAreaEnabled() && (this.mSubmitButton.getVisibility() == 0 || this.mVoiceButton.getVisibility() == 0)) {
/* 829 */               visibility = 0;
                    }
/* 831 */           this.mSubmitArea.setVisibility(visibility);
                }

/* 834 */       private void updateCloseButton() {
                    boolean showClose = true;
/* 835 */           boolean hasText = !TextUtils.isEmpty(this.mQueryTextView.getText());
/* 838 */           if (!hasText && (!this.mIconifiedByDefault || this.mExpandedInActionView)) {
                        showClose = false;
                    }
/* 839 */           this.mCloseButton.setVisibility(showClose ? 0 : 8);
/* 840 */           this.mCloseButton.getDrawable().setState(hasText ? ENABLED_STATE_SET : EMPTY_STATE_SET);
                }

/* 843 */       private void postUpdateFocusedState() {
/* 844 */           post(this.mUpdateDrawableStateRunnable);
                }

/* 847 */       private void updateFocusedState() {
/* 848 */           boolean focused = this.mQueryTextView.hasFocus();
/* 849 */           this.mSearchPlate.getBackground().setState(focused ? FOCUSED_STATE_SET : EMPTY_STATE_SET);
/* 850 */           this.mSubmitArea.getBackground().setState(focused ? FOCUSED_STATE_SET : EMPTY_STATE_SET);
/* 851 */           invalidate();
                }

                @Override // android.view.ViewGroup, android.view.View
/* 855 */       protected void onDetachedFromWindow() {
/* 856 */           removeCallbacks(this.mUpdateDrawableStateRunnable);
/* 857 */           post(this.mReleaseCursorRunnable);
/* 858 */           super.onDetachedFromWindow();
                }

/* 861 */       private void setImeVisibility(boolean visible) {
/* 862 */           if (visible) {
/* 863 */               post(this.mShowImeRunnable);
/* 873 */               return;
                    }
/* 865 */           removeCallbacks(this.mShowImeRunnable);
/* 866 */           InputMethodManager imm = (InputMethodManager) getContext().getSystemService("input_method");
/* 869 */           if (imm != null) {
/* 870 */               imm.hideSoftInputFromWindow(getWindowToken(), 0);
                    }
                }

/* 879 */       void onQueryRefine(CharSequence queryText) {
/* 880 */           setQuery(queryText);
                }

                /* JADX INFO: renamed from: android.support.v7.widget.SearchView$7 */
/* 881 */       class ViewOnClickListenerC01187 implements View.OnClickListener {
/* 882 */           ViewOnClickListenerC01187() {
                    }

                    @Override // android.view.View.OnClickListener
/* 885 */           public void onClick(View v) {
/* 886 */               if (v == SearchView.access$400(SearchView.this)) {
/* 887 */                   SearchView.access$500(SearchView.this);
/* 897 */                   return;
                        }
/* 888 */               if (v == SearchView.access$600(SearchView.this)) {
/* 889 */                   SearchView.access$700(SearchView.this);
/* 897 */                   return;
                        }
/* 890 */               if (v == SearchView.access$800(SearchView.this)) {
/* 891 */                   SearchView.access$900(SearchView.this);
                        } else if (v == SearchView.access$1000(SearchView.this)) {
/* 893 */                   SearchView.access$1100(SearchView.this);
                        } else if (v == SearchView.access$1200(SearchView.this)) {
/* 895 */                   SearchView.access$1300(SearchView.this);
                        }
                    }
                }

                @Override // android.view.View, android.view.KeyEvent.Callback
/* 910 */       public boolean onKeyDown(int keyCode, KeyEvent event) {
/* 911 */           if (this.mSearchable == null) {
/* 912 */               return false;
                    }
                    return super.onKeyDown(keyCode, event);
                }

                /* JADX INFO: renamed from: android.support.v7.widget.SearchView$8 */
/* 921 */       class ViewOnKeyListenerC01198 implements View.OnKeyListener {
/* 922 */           ViewOnKeyListenerC01198() {
                    }

                    @Override // android.view.View.OnKeyListener
/* 925 */           public boolean onKey(View v, int keyCode, KeyEvent event) {
/* 926 */               if (SearchView.access$1400(SearchView.this) == null) {
                            return false;
                        }
/* 937 */               if (SearchView.access$1200(SearchView.this).isPopupShowing() && SearchView.access$1200(SearchView.this).getListSelection() != -1) {
/* 939 */                   return SearchView.access$1500(SearchView.this, v, keyCode, event);
                        }
/* 944 */               if (SearchAutoComplete.access$1600(SearchView.access$1200(SearchView.this)) || !KeyEventCompat.hasNoModifiers(event) || event.getAction() != 1 || keyCode != 66) {
                            return false;
                        }
/* 947 */               v.cancelLongPress();
/* 950 */               SearchView.access$1700(SearchView.this, 0, null, SearchView.access$1200(SearchView.this).getText().toString());
                        return true;
                    }
                }

/* 966 */       private boolean onSuggestionsKey(View v, int keyCode, KeyEvent event) {
/* 967 */           if (this.mSearchable == null || this.mSuggestionsAdapter == null || event.getAction() != 0 || !KeyEventCompat.hasNoModifiers(event)) {
                        return false;
                    }
/* 976 */           if (keyCode == 66 || keyCode == 84 || keyCode == 61) {
/* 978 */               int position = this.mQueryTextView.getListSelection();
/* 979 */               return onItemClicked(position, 0, null);
                    }
/* 984 */           if (keyCode == 21 || keyCode == 22) {
/* 987 */               int selPoint = keyCode == 21 ? 0 : this.mQueryTextView.length();
/* 989 */               this.mQueryTextView.setSelection(selPoint);
/* 990 */               this.mQueryTextView.setListSelection(0);
/* 991 */               this.mQueryTextView.clearListSelection();
/* 992 */               HIDDEN_METHOD_INVOKER.ensureImeVisible(this.mQueryTextView, true);
                        return true;
                    }
/* 998 */           if (keyCode != 19 || this.mQueryTextView.getListSelection() != 0) {
                    }
                    return false;
                }

/* 1007 */      private int getSearchIconId() {
/* 1008 */          TypedValue outValue = new TypedValue();
/* 1009 */          getContext().getTheme().resolveAttribute(C0084R.attr.searchViewSearchIcon, outValue, true);
/* 1010 */          return outValue.resourceId;
                }

/* 1014 */      private CharSequence getDecoratedHint(CharSequence hintText) {
/* 1015 */          if (this.mIconifiedByDefault) {
/* 1017 */              SpannableStringBuilder ssb = new SpannableStringBuilder("   ");
/* 1018 */              ssb.append(hintText);
/* 1019 */              Drawable searchIcon = getContext().getResources().getDrawable(getSearchIconId());
/* 1020 */              int textSize = (int) (((double) this.mQueryTextView.getTextSize()) * 1.25d);
/* 1021 */              searchIcon.setBounds(0, 0, textSize, textSize);
/* 1022 */              ssb.setSpan(new ImageSpan(searchIcon), 1, 2, 33);
                        return ssb;
                    }
/* 1023 */          return hintText;
                }

/* 1026 */      private void updateQueryHint() {
/* 1027 */          if (this.mQueryHint != null) {
/* 1028 */              this.mQueryTextView.setHint(getDecoratedHint(this.mQueryHint));
/* 1041 */              return;
                    }
/* 1029 */          if (this.mSearchable != null) {
/* 1030 */              CharSequence hint = null;
/* 1031 */              int hintId = this.mSearchable.getHintId();
/* 1032 */              if (hintId != 0) {
/* 1033 */                  hint = getContext().getString(hintId);
                        }
/* 1035 */              if (hint != null) {
/* 1036 */                  this.mQueryTextView.setHint(getDecoratedHint(hint));
/* 1041 */                  return;
                        }
/* 1041 */              return;
                    }
/* 1039 */          this.mQueryTextView.setHint(getDecoratedHint(""));
                }

/* 1046 */      private void updateSearchAutoComplete() {
/* 1047 */          this.mQueryTextView.setThreshold(this.mSearchable.getSuggestThreshold());
/* 1048 */          this.mQueryTextView.setImeOptions(this.mSearchable.getImeOptions());
/* 1049 */          int inputType = this.mSearchable.getInputType();
/* 1052 */          if ((inputType & 15) == 1) {
/* 1055 */              inputType &= -65537;
/* 1056 */              if (this.mSearchable.getSuggestAuthority() != null) {
/* 1064 */                  inputType = inputType | 65536 | 524288;
                        }
                    }
/* 1067 */          this.mQueryTextView.setInputType(inputType);
/* 1068 */          if (this.mSuggestionsAdapter != null) {
/* 1069 */              this.mSuggestionsAdapter.changeCursor(null);
                    }
/* 1073 */          if (this.mSearchable.getSuggestAuthority() != null) {
/* 1074 */              this.mSuggestionsAdapter = new SuggestionsAdapter(getContext(), this, this.mSearchable, this.mOutsideDrawablesCache);
/* 1076 */              this.mQueryTextView.setAdapter(this.mSuggestionsAdapter);
/* 1077 */              ((SuggestionsAdapter) this.mSuggestionsAdapter).setQueryRefinement(this.mQueryRefinement ? 2 : 1);
                    }
                }

/* 1089 */      private void updateVoiceButton(boolean empty) {
/* 1090 */          int visibility = 8;
/* 1091 */          if (this.mVoiceButtonEnabled && !isIconified() && empty) {
/* 1092 */              visibility = 0;
/* 1093 */              this.mSubmitButton.setVisibility(8);
                    }
/* 1095 */          this.mVoiceButton.setVisibility(visibility);
                }

                /* JADX INFO: renamed from: android.support.v7.widget.SearchView$9 */
/* 1096 */      class C01209 implements TextView.OnEditorActionListener {
/* 1097 */          C01209() {
                    }

                    @Override // android.widget.TextView.OnEditorActionListener
/* 1103 */          public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
/* 1104 */              SearchView.access$900(SearchView.this);
/* 1105 */              return true;
                    }
                }

/* 1109 */      private void onTextChanged(CharSequence newText) {
/* 1110 */          CharSequence text = this.mQueryTextView.getText();
/* 1111 */          this.mUserQuery = text;
/* 1112 */          boolean hasText = !TextUtils.isEmpty(text);
/* 1113 */          updateSubmitButton(hasText);
/* 1114 */          updateVoiceButton(hasText ? false : true);
/* 1115 */          updateCloseButton();
/* 1116 */          updateSubmitArea();
/* 1117 */          if (this.mOnQueryChangeListener != null && !TextUtils.equals(newText, this.mOldQueryText)) {
/* 1118 */              this.mOnQueryChangeListener.onQueryTextChange(newText.toString());
                    }
/* 1120 */          this.mOldQueryText = newText.toString();
                }

/* 1123 */      private void onSubmitQuery() {
/* 1124 */          CharSequence query = this.mQueryTextView.getText();
/* 1125 */          if (query != null && TextUtils.getTrimmedLength(query) > 0) {
/* 1126 */              if (this.mOnQueryChangeListener == null || !this.mOnQueryChangeListener.onQueryTextSubmit(query.toString())) {
/* 1128 */                  if (this.mSearchable != null) {
/* 1129 */                      launchQuerySearch(0, null, query.toString());
/* 1130 */                      setImeVisibility(false);
                            }
/* 1132 */                  dismissSuggestions();
                        }
                    }
                }

/* 1137 */      private void dismissSuggestions() {
/* 1138 */          this.mQueryTextView.dismissDropDown();
                }

/* 1141 */      private void onCloseClicked() {
/* 1142 */          CharSequence text = this.mQueryTextView.getText();
/* 1143 */          if (TextUtils.isEmpty(text)) {
/* 1144 */              if (this.mIconifiedByDefault) {
/* 1146 */                  if (this.mOnCloseListener == null || !this.mOnCloseListener.onClose()) {
/* 1148 */                      clearFocus();
/* 1150 */                      updateViewsVisibility(true);
/* 1159 */                      return;
                            }
/* 1159 */                  return;
                        }
/* 1159 */              return;
                    }
/* 1154 */          this.mQueryTextView.setText("");
/* 1155 */          this.mQueryTextView.requestFocus();
/* 1156 */          setImeVisibility(true);
                }

/* 1161 */      private void onSearchClicked() {
/* 1162 */          updateViewsVisibility(false);
/* 1163 */          this.mQueryTextView.requestFocus();
/* 1164 */          setImeVisibility(true);
/* 1165 */          if (this.mOnSearchClickListener != null) {
/* 1166 */              this.mOnSearchClickListener.onClick(this);
                    }
                }

/* 1171 */      private void onVoiceClicked() {
/* 1172 */          if (this.mSearchable != null) {
/* 1175 */              SearchableInfo searchable = this.mSearchable;
                        try {
/* 1177 */                  if (searchable.getVoiceSearchLaunchWebSearch()) {
/* 1178 */                      Intent webSearchIntent = createVoiceWebSearchIntent(this.mVoiceWebSearchIntent, searchable);
/* 1180 */                      getContext().startActivity(webSearchIntent);
                            } else if (searchable.getVoiceSearchLaunchRecognizer()) {
/* 1182 */                      Intent appSearchIntent = createVoiceAppSearchIntent(this.mVoiceAppSearchIntent, searchable);
/* 1184 */                      getContext().startActivity(appSearchIntent);
                            }
                        } catch (ActivityNotFoundException e) {
/* 1189 */                  Log.w(LOG_TAG, "Could not find voice search activity");
                        }
                    }
                }

/* 1193 */      void onTextFocusChanged() {
/* 1194 */          updateViewsVisibility(isIconified());
/* 1197 */          postUpdateFocusedState();
/* 1198 */          if (this.mQueryTextView.hasFocus()) {
/* 1199 */              forceSuggestionQuery();
                    }
                }

                @Override // android.view.View
/* 1204 */      public void onWindowFocusChanged(boolean hasWindowFocus) {
/* 1205 */          super.onWindowFocusChanged(hasWindowFocus);
/* 1207 */          postUpdateFocusedState();
                }

                @Override // android.support.v7.view.CollapsibleActionView
/* 1214 */      public void onActionViewCollapsed() {
/* 1215 */          clearFocus();
/* 1216 */          updateViewsVisibility(true);
/* 1217 */          this.mQueryTextView.setImeOptions(this.mCollapsedImeOptions);
/* 1218 */          this.mExpandedInActionView = false;
                }

                @Override // android.support.v7.view.CollapsibleActionView
/* 1225 */      public void onActionViewExpanded() {
/* 1226 */          if (!this.mExpandedInActionView) {
/* 1228 */              this.mExpandedInActionView = true;
/* 1229 */              this.mCollapsedImeOptions = this.mQueryTextView.getImeOptions();
/* 1230 */              this.mQueryTextView.setImeOptions(this.mCollapsedImeOptions | swscale.SWS_CPU_CAPS_SSE2);
/* 1231 */              this.mQueryTextView.setText("");
/* 1232 */              setIconified(false);
                    }
                }

/* 1236 */      private void adjustDropDownSizeAndPosition() {
/* 1237 */          if (this.mDropDownAnchor.getWidth() > 1) {
/* 1238 */              Resources res = getContext().getResources();
/* 1239 */              int anchorPadding = this.mSearchPlate.getPaddingLeft();
/* 1240 */              Rect dropDownPadding = new Rect();
/* 1241 */              int iconOffset = this.mIconifiedByDefault ? res.getDimensionPixelSize(C0084R.dimen.abc_dropdownitem_icon_width) + res.getDimensionPixelSize(C0084R.dimen.abc_dropdownitem_text_padding_left) : 0;
/* 1245 */              this.mQueryTextView.getDropDownBackground().getPadding(dropDownPadding);
/* 1246 */              int offset = anchorPadding - (dropDownPadding.left + iconOffset);
/* 1247 */              this.mQueryTextView.setDropDownHorizontalOffset(offset);
/* 1248 */              int width = (((this.mDropDownAnchor.getWidth() + dropDownPadding.left) + dropDownPadding.right) + iconOffset) - anchorPadding;
/* 1250 */              this.mQueryTextView.setDropDownWidth(width);
                    }
                }

/* 1254 */      private boolean onItemClicked(int position, int actionKey, String actionMsg) {
/* 1255 */          if (this.mOnSuggestionListener != null && this.mOnSuggestionListener.onSuggestionClick(position)) {
                        return false;
                    }
/* 1257 */          launchSuggestion(position, 0, null);
/* 1258 */          setImeVisibility(false);
/* 1259 */          dismissSuggestions();
/* 1260 */          return true;
                }

/* 1265 */      private boolean onItemSelected(int position) {
/* 1266 */          if (this.mOnSuggestionListener != null && this.mOnSuggestionListener.onSuggestionSelect(position)) {
                        return false;
                    }
/* 1268 */          rewriteQueryFromSuggestion(position);
/* 1269 */          return true;
                }

                /* JADX INFO: renamed from: android.support.v7.widget.SearchView$10 */
/* 1272 */      class C011010 implements AdapterView.OnItemClickListener {
/* 1273 */          C011010() {
                    }

                    @Override // android.widget.AdapterView.OnItemClickListener
/* 1280 */          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/* 1281 */              SearchView.access$1800(SearchView.this, position, 0, null);
                    }
                }

                /* JADX INFO: renamed from: android.support.v7.widget.SearchView$11 */
/* 1283 */      class C011111 implements AdapterView.OnItemSelectedListener {
/* 1284 */          C011111() {
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
/* 1291 */          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
/* 1292 */              SearchView.access$1900(SearchView.this, position);
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
/* 1300 */          public void onNothingSelected(AdapterView<?> parent) {
                    }
                }

/* 1307 */      private void rewriteQueryFromSuggestion(int position) {
/* 1308 */          CharSequence oldQuery = this.mQueryTextView.getText();
/* 1309 */          Cursor c = this.mSuggestionsAdapter.getCursor();
/* 1310 */          if (c != null) {
/* 1313 */              if (c.moveToPosition(position)) {
/* 1315 */                  CharSequence newQuery = this.mSuggestionsAdapter.convertToString(c);
/* 1316 */                  if (newQuery != null) {
/* 1319 */                      setQuery(newQuery);
/* 1328 */                      return;
                            } else {
/* 1322 */                      setQuery(oldQuery);
/* 1328 */                      return;
                            }
                        }
/* 1326 */              setQuery(oldQuery);
                    }
                }

/* 1340 */      private boolean launchSuggestion(int position, int actionKey, String actionMsg) {
/* 1341 */          Cursor c = this.mSuggestionsAdapter.getCursor();
/* 1342 */          if (c == null || !c.moveToPosition(position)) {
                        return false;
                    }
/* 1344 */          Intent intent = createIntentFromSuggestion(c, actionKey, actionMsg);
/* 1347 */          launchIntent(intent);
/* 1349 */          return true;
                }

/* 1357 */      private void launchIntent(Intent intent) {
/* 1358 */          if (intent != null) {
                        try {
/* 1364 */                  getContext().startActivity(intent);
                        } catch (RuntimeException ex) {
/* 1366 */                  Log.e(LOG_TAG, "Failed launch activity: " + intent, ex);
                        }
                    }
                }

/* 1373 */      private void setQuery(CharSequence query) {
/* 1374 */          this.mQueryTextView.setText(query);
/* 1376 */          this.mQueryTextView.setSelection(TextUtils.isEmpty(query) ? 0 : query.length());
                }

/* 1379 */      private void launchQuerySearch(int actionKey, String actionMsg, String query) {
/* 1381 */          Intent intent = createIntent("android.intent.action.SEARCH", null, null, query, actionKey, actionMsg);
/* 1382 */          getContext().startActivity(intent);
                }

/* 1400 */      private Intent createIntent(String action, Uri data, String extraData, String query, int actionKey, String actionMsg) {
/* 1401 */          Intent intent = new Intent(action);
/* 1402 */          intent.addFlags(268435456);
/* 1406 */          if (data != null) {
/* 1407 */              intent.setData(data);
                    }
/* 1409 */          intent.putExtra("user_query", this.mUserQuery);
/* 1410 */          if (query != null) {
/* 1411 */              intent.putExtra("query", query);
                    }
/* 1413 */          if (extraData != null) {
/* 1414 */              intent.putExtra("intent_extra_data_key", extraData);
                    }
/* 1416 */          if (this.mAppSearchData != null) {
/* 1417 */              intent.putExtra("app_data", this.mAppSearchData);
                    }
/* 1419 */          if (actionKey != 0) {
/* 1420 */              intent.putExtra("action_key", actionKey);
/* 1421 */              intent.putExtra("action_msg", actionMsg);
                    }
/* 1423 */          intent.setComponent(this.mSearchable.getSearchActivity());
/* 1424 */          return intent;
                }

/* 1430 */      private Intent createVoiceWebSearchIntent(Intent baseIntent, SearchableInfo searchable) {
/* 1431 */          Intent voiceIntent = new Intent(baseIntent);
/* 1432 */          ComponentName searchActivity = searchable.getSearchActivity();
/* 1433 */          voiceIntent.putExtra("calling_package", searchActivity == null ? null : searchActivity.flattenToShortString());
/* 1435 */          return voiceIntent;
                }

/* 1445 */      private Intent createVoiceAppSearchIntent(Intent baseIntent, SearchableInfo searchable) {
/* 1446 */          ComponentName searchActivity = searchable.getSearchActivity();
/* 1451 */          Intent queryIntent = new Intent("android.intent.action.SEARCH");
/* 1452 */          queryIntent.setComponent(searchActivity);
/* 1453 */          PendingIntent pending = PendingIntent.getActivity(getContext(), 0, queryIntent, 1073741824);
/* 1460 */          Bundle queryExtras = new Bundle();
/* 1461 */          if (this.mAppSearchData != null) {
/* 1462 */              queryExtras.putParcelable("app_data", this.mAppSearchData);
                    }
/* 1468 */          Intent voiceIntent = new Intent(baseIntent);
/* 1471 */          String languageModel = "free_form";
/* 1472 */          String prompt = null;
/* 1473 */          String language = null;
/* 1474 */          int maxResults = 1;
/* 1476 */          Resources resources = getResources();
/* 1477 */          if (searchable.getVoiceLanguageModeId() != 0) {
/* 1478 */              languageModel = resources.getString(searchable.getVoiceLanguageModeId());
                    }
/* 1480 */          if (searchable.getVoicePromptTextId() != 0) {
/* 1481 */              prompt = resources.getString(searchable.getVoicePromptTextId());
                    }
/* 1483 */          if (searchable.getVoiceLanguageId() != 0) {
/* 1484 */              language = resources.getString(searchable.getVoiceLanguageId());
                    }
/* 1486 */          if (searchable.getVoiceMaxResults() != 0) {
/* 1487 */              maxResults = searchable.getVoiceMaxResults();
                    }
/* 1489 */          voiceIntent.putExtra("android.speech.extra.LANGUAGE_MODEL", languageModel);
/* 1490 */          voiceIntent.putExtra("android.speech.extra.PROMPT", prompt);
/* 1491 */          voiceIntent.putExtra("android.speech.extra.LANGUAGE", language);
/* 1492 */          voiceIntent.putExtra("android.speech.extra.MAX_RESULTS", maxResults);
/* 1493 */          voiceIntent.putExtra("calling_package", searchActivity == null ? null : searchActivity.flattenToShortString());
/* 1497 */          voiceIntent.putExtra("android.speech.extra.RESULTS_PENDINGINTENT", pending);
/* 1498 */          voiceIntent.putExtra("android.speech.extra.RESULTS_PENDINGINTENT_BUNDLE", queryExtras);
/* 1500 */          return voiceIntent;
                }

/* 1518 */      private Intent createIntentFromSuggestion(Cursor c, int actionKey, String actionMsg) {
                    int rowNum;
                    String id;
                    try {
/* 1519 */              String action = SuggestionsAdapter.getColumnString(c, "suggest_intent_action");
/* 1521 */              if (action == null) {
/* 1522 */                  action = this.mSearchable.getSuggestIntentAction();
                        }
/* 1524 */              if (action == null) {
/* 1525 */                  action = "android.intent.action.SEARCH";
                        }
/* 1529 */              String data = SuggestionsAdapter.getColumnString(c, "suggest_intent_data");
/* 1530 */              if (data == null) {
/* 1531 */                  data = this.mSearchable.getSuggestIntentData();
                        }
/* 1534 */              if (data != null && (id = SuggestionsAdapter.getColumnString(c, "suggest_intent_data_id")) != null) {
/* 1537 */                  data = data + "/" + Uri.encode(id);
                        }
/* 1540 */              Uri dataUri = data == null ? null : Uri.parse(data);
/* 1542 */              String query = SuggestionsAdapter.getColumnString(c, "suggest_intent_query");
/* 1543 */              String extraData = SuggestionsAdapter.getColumnString(c, "suggest_intent_extra_data");
/* 1545 */              return createIntent(action, dataUri, extraData, query, actionKey, actionMsg);
                    } catch (RuntimeException e) {
                        try {
/* 1549 */                  rowNum = c.getPosition();
                        } catch (RuntimeException e2) {
/* 1551 */                  rowNum = -1;
                        }
/* 1553 */              Log.w(LOG_TAG, "Search suggestions cursor at row " + rowNum + " returned exception.", e);
/* 1555 */              return null;
                    }
                }

/* 1559 */      private void forceSuggestionQuery() {
/* 1560 */          HIDDEN_METHOD_INVOKER.doBeforeTextChanged(this.mQueryTextView);
/* 1561 */          HIDDEN_METHOD_INVOKER.doAfterTextChanged(this.mQueryTextView);
                }

/* 1564 */      static boolean isLandscapeMode(Context context) {
                    return context.getResources().getConfiguration().orientation == 2;
                }

                /* JADX INFO: renamed from: android.support.v7.widget.SearchView$12 */
/* 1570 */      class C011212 implements TextWatcher {
/* 1571 */          C011212() {
                    }

                    @Override // android.text.TextWatcher
/* 1573 */          public void beforeTextChanged(CharSequence s, int start, int before, int after) {
                    }

                    @Override // android.text.TextWatcher
/* 1577 */          public void onTextChanged(CharSequence s, int start, int before, int after) {
/* 1578 */              SearchView.access$2000(SearchView.this, s);
                    }

                    @Override // android.text.TextWatcher
/* 1581 */          public void afterTextChanged(Editable s) {
                    }
                }

/* 1587 */      public static class SearchAutoComplete extends AutoCompleteTextView {
                    private SearchView mSearchView;
                    private int mThreshold;

/* 1588 */          static /* synthetic */ boolean access$1600(SearchAutoComplete x0) {
/* 1589 */              return x0.isEmpty();
                    }

/* 1594 */          public SearchAutoComplete(Context context) {
/* 1595 */              super(context);
/* 1596 */              this.mThreshold = getThreshold();
                    }

/* 1599 */          public SearchAutoComplete(Context context, AttributeSet attrs) {
/* 1600 */              super(context, attrs);
/* 1601 */              this.mThreshold = getThreshold();
                    }

/* 1604 */          public SearchAutoComplete(Context context, AttributeSet attrs, int defStyle) {
/* 1605 */              super(context, attrs, defStyle);
/* 1606 */              this.mThreshold = getThreshold();
                    }

/* 1609 */          void setSearchView(SearchView searchView) {
/* 1610 */              this.mSearchView = searchView;
                    }

                    @Override // android.widget.AutoCompleteTextView
/* 1614 */          public void setThreshold(int threshold) {
/* 1615 */              super.setThreshold(threshold);
/* 1616 */              this.mThreshold = threshold;
                    }

/* 1622 */          private boolean isEmpty() {
                        return TextUtils.getTrimmedLength(getText()) == 0;
                    }

                    @Override // android.widget.AutoCompleteTextView
/* 1631 */          protected void replaceText(CharSequence text) {
                    }

                    @Override // android.widget.AutoCompleteTextView
/* 1641 */          public void performCompletion() {
                    }

                    @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
/* 1649 */          public void onWindowFocusChanged(boolean hasWindowFocus) {
/* 1650 */              super.onWindowFocusChanged(hasWindowFocus);
/* 1652 */              if (hasWindowFocus && this.mSearchView.hasFocus() && getVisibility() == 0) {
/* 1653 */                  InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService("input_method");
/* 1655 */                  inputManager.showSoftInput(this, 0);
/* 1658 */                  if (SearchView.isLandscapeMode(getContext())) {
/* 1659 */                      SearchView.HIDDEN_METHOD_INVOKER.ensureImeVisible(this, true);
                            }
                        }
                    }

                    @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
/* 1665 */          protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
/* 1666 */              super.onFocusChanged(focused, direction, previouslyFocusedRect);
/* 1667 */              this.mSearchView.onTextFocusChanged();
                    }

                    @Override // android.widget.AutoCompleteTextView
/* 1675 */          public boolean enoughToFilter() {
                        return this.mThreshold <= 0 || super.enoughToFilter();
                    }

                    @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
/* 1680 */          public boolean onKeyPreIme(int keyCode, KeyEvent event) {
/* 1681 */              if (keyCode == 4) {
/* 1684 */                  if (event.getAction() == 0 && event.getRepeatCount() == 0) {
/* 1685 */                      KeyEvent.DispatcherState state = getKeyDispatcherState();
/* 1686 */                      if (state == null) {
                                    return true;
                                }
/* 1687 */                      state.startTracking(event, this);
                                return true;
                            }
/* 1690 */                  if (event.getAction() == 1) {
/* 1691 */                      KeyEvent.DispatcherState state2 = getKeyDispatcherState();
/* 1692 */                      if (state2 != null) {
/* 1693 */                          state2.handleUpEvent(event);
                                }
/* 1695 */                      if (event.isTracking() && !event.isCanceled()) {
/* 1696 */                          this.mSearchView.clearFocus();
/* 1697 */                          SearchView.access$2100(this.mSearchView, false);
                                    return true;
                                }
                            }
                        }
/* 1702 */              return super.onKeyPreIme(keyCode, event);
                    }
                }

/* 1709 */      private static class AutoCompleteTextViewReflector {
                    private Method doAfterTextChanged;
                    private Method doBeforeTextChanged;
                    private Method ensureImeVisible;
                    private Method showSoftInputUnchecked;

/* 1710 */          AutoCompleteTextViewReflector() {
                        try {
/* 1713 */                  this.doBeforeTextChanged = AutoCompleteTextView.class.getDeclaredMethod("doBeforeTextChanged", new Class[0]);
/* 1715 */                  this.doBeforeTextChanged.setAccessible(true);
                        } catch (NoSuchMethodException e) {
                        }
                        try {
/* 1720 */                  this.doAfterTextChanged = AutoCompleteTextView.class.getDeclaredMethod("doAfterTextChanged", new Class[0]);
/* 1722 */                  this.doAfterTextChanged.setAccessible(true);
                        } catch (NoSuchMethodException e2) {
                        }
                        try {
/* 1727 */                  this.ensureImeVisible = AutoCompleteTextView.class.getMethod("ensureImeVisible", Boolean.TYPE);
/* 1729 */                  this.ensureImeVisible.setAccessible(true);
                        } catch (NoSuchMethodException e3) {
                        }
                        try {
/* 1734 */                  this.showSoftInputUnchecked = InputMethodManager.class.getMethod("showSoftInputUnchecked", Integer.TYPE, ResultReceiver.class);
/* 1736 */                  this.showSoftInputUnchecked.setAccessible(true);
                        } catch (NoSuchMethodException e4) {
                        }
                    }

/* 1742 */          void doBeforeTextChanged(AutoCompleteTextView view) {
/* 1743 */              if (this.doBeforeTextChanged != null) {
                            try {
/* 1745 */                      this.doBeforeTextChanged.invoke(view, new Object[0]);
                            } catch (Exception e) {
                            }
                        }
                    }

/* 1751 */          void doAfterTextChanged(AutoCompleteTextView view) {
/* 1752 */              if (this.doAfterTextChanged != null) {
                            try {
/* 1754 */                      this.doAfterTextChanged.invoke(view, new Object[0]);
                            } catch (Exception e) {
                            }
                        }
                    }

/* 1760 */          void ensureImeVisible(AutoCompleteTextView view, boolean visible) {
/* 1761 */              if (this.ensureImeVisible != null) {
                            try {
/* 1763 */                      this.ensureImeVisible.invoke(view, Boolean.valueOf(visible));
                            } catch (Exception e) {
                            }
                        }
                    }

/* 1769 */          void showSoftInputUnchecked(InputMethodManager imm, View view, int flags) {
/* 1770 */              if (this.showSoftInputUnchecked != null) {
                            try {
/* 1772 */                      this.showSoftInputUnchecked.invoke(imm, Integer.valueOf(flags), null);
/* 1780 */                      return;
                            } catch (Exception e) {
                            }
                        }
/* 1779 */              imm.showSoftInput(view, flags);
                    }
                }
            }
