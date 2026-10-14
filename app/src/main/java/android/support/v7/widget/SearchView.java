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
            
            
   public class SearchView extends LinearLayout implements CollapsibleActionView {
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

       static  void access$000(SearchView x0) {
           x0.updateFocusedState();
                }

       static  CursorAdapter access$100(SearchView x0) {
           return x0.mSuggestionsAdapter;
                }

       static  View access$1000(SearchView x0) {
           return x0.mVoiceButton;
                }

       static  void access$1100(SearchView x0) {
           x0.onVoiceClicked();
                }

       static  SearchAutoComplete access$1200(SearchView x0) {
           return x0.mQueryTextView;
                }

       static  void access$1300(SearchView x0) {
           x0.forceSuggestionQuery();
                }

       static  SearchableInfo access$1400(SearchView x0) {
           return x0.mSearchable;
                }

       static  boolean access$1500(SearchView x0, View x1, int x2, KeyEvent x3) {
           return x0.onSuggestionsKey(x1, x2, x3);
                }

       static  void access$1700(SearchView x0, int x1, String x2, String x3) {
           x0.launchQuerySearch(x1, x2, x3);
                }

       static  boolean access$1800(SearchView x0, int x1, int x2, String x3) {
           return x0.onItemClicked(x1, x2, x3);
                }

       static  boolean access$1900(SearchView x0, int x1) {
           return x0.onItemSelected(x1);
                }

       static  View.OnFocusChangeListener access$200(SearchView x0) {
           return x0.mOnQueryTextFocusChangeListener;
                }

       static  void access$2000(SearchView x0, CharSequence x1) {
           x0.onTextChanged(x1);
                }

       static  void access$2100(SearchView x0, boolean x1) {
           x0.setImeVisibility(x1);
                }

       static  void access$300(SearchView x0) {
           x0.adjustDropDownSizeAndPosition();
                }

       static  View access$400(SearchView x0) {
           return x0.mSearchButton;
                }

       static  void access$500(SearchView x0) {
           x0.onSearchClicked();
                }

       static  ImageView access$600(SearchView x0) {
           return x0.mCloseButton;
                }

       static  void access$700(SearchView x0) {
           x0.onCloseClicked();
                }

       static  View access$800(SearchView x0) {
           return x0.mSubmitButton;
                }

       static  void access$900(SearchView x0) {
           x0.onSubmitQuery();
                }

                
       class RunnableC01091 implements Runnable {
           RunnableC01091() {
                    }

                    @Override 
           public void run() {
               InputMethodManager imm = (InputMethodManager) SearchView.this.getContext().getSystemService("input_method");
               if (imm != null) {
                   SearchView.HIDDEN_METHOD_INVOKER.showSoftInputUnchecked(imm, SearchView.this, 0);
                        }
                    }
                }

                
       class RunnableC01132 implements Runnable {
           RunnableC01132() {
                    }

                    @Override 
           public void run() {
               SearchView.access$000(SearchView.this);
                    }
                }

                
       class RunnableC01143 implements Runnable {
           RunnableC01143() {
                    }

                    @Override 
           public void run() {
               if (SearchView.access$100(SearchView.this) != null && (SearchView.access$100(SearchView.this) instanceof SuggestionsAdapter)) {
                   SearchView.access$100(SearchView.this).changeCursor(null);
                        }
                    }
                }

       public SearchView(Context context) {
           this(context, null);
                }

       public SearchView(Context context, AttributeSet attrs) {
           super(context, attrs);
           this.mShowImeRunnable = new RunnableC01091();
           this.mUpdateDrawableStateRunnable = new RunnableC01132();
           this.mReleaseCursorRunnable = new RunnableC01143();
           this.mOutsideDrawablesCache = new WeakHashMap<>();
           this.mOnClickListener = new ViewOnClickListenerC01187();
           this.mTextKeyListener = new ViewOnKeyListenerC01198();
          this.mOnEditorActionListener = new C01209();
          this.mOnItemClickListener = new C011010();
          this.mOnItemSelectedListener = new C011111();
          this.mTextWatcher = new C011212();
           LayoutInflater inflater = (LayoutInflater) context.getSystemService("layout_inflater");
           inflater.inflate(C0084R.layout.abc_search_view, (ViewGroup) this, true);
           this.mSearchButton = findViewById(C0084R.id.search_button);
           this.mQueryTextView = (SearchAutoComplete) findViewById(C0084R.id.search_src_text);
           this.mQueryTextView.setSearchView(this);
           this.mSearchEditFrame = findViewById(C0084R.id.search_edit_frame);
           this.mSearchPlate = findViewById(C0084R.id.search_plate);
           this.mSubmitArea = findViewById(C0084R.id.submit_area);
           this.mSubmitButton = findViewById(C0084R.id.search_go_btn);
           this.mCloseButton = (ImageView) findViewById(C0084R.id.search_close_btn);
           this.mVoiceButton = findViewById(C0084R.id.search_voice_btn);
           this.mSearchHintIcon = (ImageView) findViewById(C0084R.id.search_mag_icon);
           this.mSearchButton.setOnClickListener(this.mOnClickListener);
           this.mCloseButton.setOnClickListener(this.mOnClickListener);
           this.mSubmitButton.setOnClickListener(this.mOnClickListener);
           this.mVoiceButton.setOnClickListener(this.mOnClickListener);
           this.mQueryTextView.setOnClickListener(this.mOnClickListener);
           this.mQueryTextView.addTextChangedListener(this.mTextWatcher);
           this.mQueryTextView.setOnEditorActionListener(this.mOnEditorActionListener);
           this.mQueryTextView.setOnItemClickListener(this.mOnItemClickListener);
           this.mQueryTextView.setOnItemSelectedListener(this.mOnItemSelectedListener);
           this.mQueryTextView.setOnKeyListener(this.mTextKeyListener);
           this.mQueryTextView.setOnFocusChangeListener(new ViewOnFocusChangeListenerC01154());
           TypedArray a = context.obtainStyledAttributes(attrs, C0084R.styleable.SearchView, 0, 0);
           setIconifiedByDefault(a.getBoolean(3, true));
           int maxWidth = a.getDimensionPixelSize(0, -1);
           if (maxWidth != -1) {
               setMaxWidth(maxWidth);
                    }
           CharSequence queryHint = a.getText(4);
           if (!TextUtils.isEmpty(queryHint)) {
               setQueryHint(queryHint);
                    }
           int imeOptions = a.getInt(2, -1);
           if (imeOptions != -1) {
               setImeOptions(imeOptions);
                    }
           int inputType = a.getInt(1, -1);
           if (inputType != -1) {
               setInputType(inputType);
                    }
           a.recycle();
           TypedArray a2 = context.obtainStyledAttributes(attrs, C0084R.styleable.View, 0, 0);
           boolean focusable = a2.getBoolean(0, true);
           a2.recycle();
           setFocusable(focusable);
           this.mVoiceWebSearchIntent = new Intent("android.speech.action.WEB_SEARCH");
           this.mVoiceWebSearchIntent.addFlags(268435456);
           this.mVoiceWebSearchIntent.putExtra("android.speech.extra.LANGUAGE_MODEL", "web_search");
           this.mVoiceAppSearchIntent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
           this.mVoiceAppSearchIntent.addFlags(268435456);
           this.mDropDownAnchor = findViewById(this.mQueryTextView.getDropDownAnchor());
           if (this.mDropDownAnchor != null) {
               if (Build.VERSION.SDK_INT >= 11) {
                   addOnLayoutChangeListenerToDropDownAnchorSDK11();
                        } else {
                   addOnLayoutChangeListenerToDropDownAnchorBase();
                        }
                    }
           updateViewsVisibility(this.mIconifiedByDefault);
           updateQueryHint();
                }

                
       class ViewOnFocusChangeListenerC01154 implements View.OnFocusChangeListener {
           ViewOnFocusChangeListenerC01154() {
                    }

                    @Override 
           public void onFocusChange(View v, boolean hasFocus) {
               if (SearchView.access$200(SearchView.this) != null) {
                   SearchView.access$200(SearchView.this).onFocusChange(SearchView.this, hasFocus);
                        }
                    }
                }

                
       class ViewOnLayoutChangeListenerC01165 implements View.OnLayoutChangeListener {
           ViewOnLayoutChangeListenerC01165() {
                    }

                    @Override 
           public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
               SearchView.access$300(SearchView.this);
                    }
                }

       private void addOnLayoutChangeListenerToDropDownAnchorSDK11() {
           this.mDropDownAnchor.addOnLayoutChangeListener(new ViewOnLayoutChangeListenerC01165());
                }

                
       class ViewTreeObserverOnGlobalLayoutListenerC01176 implements ViewTreeObserver.OnGlobalLayoutListener {
           ViewTreeObserverOnGlobalLayoutListenerC01176() {
                    }

                    @Override 
           public void onGlobalLayout() {
               SearchView.access$300(SearchView.this);
                    }
                }

       private void addOnLayoutChangeListenerToDropDownAnchorBase() {
           this.mDropDownAnchor.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserverOnGlobalLayoutListenerC01176());
                }

       public void setSearchableInfo(SearchableInfo searchable) {
           this.mSearchable = searchable;
           if (this.mSearchable != null) {
               updateSearchAutoComplete();
               updateQueryHint();
                    }
           this.mVoiceButtonEnabled = hasVoiceSearch();
           if (this.mVoiceButtonEnabled) {
               this.mQueryTextView.setPrivateImeOptions(IME_OPTION_NO_MICROPHONE);
                    }
           updateViewsVisibility(isIconified());
                }

       public void setAppSearchData(Bundle appSearchData) {
           this.mAppSearchData = appSearchData;
                }

       public void setImeOptions(int imeOptions) {
           this.mQueryTextView.setImeOptions(imeOptions);
                }

       public int getImeOptions() {
           return this.mQueryTextView.getImeOptions();
                }

       public void setInputType(int inputType) {
           this.mQueryTextView.setInputType(inputType);
                }

       public int getInputType() {
           return this.mQueryTextView.getInputType();
                }

                @Override 
       public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
           if (!this.mClearingFocus && isFocusable()) {
               if (!isIconified()) {
                   boolean result = this.mQueryTextView.requestFocus(direction, previouslyFocusedRect);
                   if (result) {
                       updateViewsVisibility(false);
                       return result;
                            }
                   return result;
                        }
               return super.requestFocus(direction, previouslyFocusedRect);
                    }
                    return false;
                }

                @Override 
       public void clearFocus() {
           this.mClearingFocus = true;
           setImeVisibility(false);
           super.clearFocus();
           this.mQueryTextView.clearFocus();
           this.mClearingFocus = false;
                }

       public void setOnQueryTextListener(OnQueryTextListener listener) {
           this.mOnQueryChangeListener = listener;
                }

       public void setOnCloseListener(OnCloseListener listener) {
           this.mOnCloseListener = listener;
                }

       public void setOnQueryTextFocusChangeListener(View.OnFocusChangeListener listener) {
           this.mOnQueryTextFocusChangeListener = listener;
                }

       public void setOnSuggestionListener(OnSuggestionListener listener) {
           this.mOnSuggestionListener = listener;
                }

       public void setOnSearchClickListener(View.OnClickListener listener) {
           this.mOnSearchClickListener = listener;
                }

       public CharSequence getQuery() {
           return this.mQueryTextView.getText();
                }

       public void setQuery(CharSequence query, boolean submit) {
           this.mQueryTextView.setText(query);
           if (query != null) {
               this.mQueryTextView.setSelection(this.mQueryTextView.length());
               this.mUserQuery = query;
                    }
           if (submit && !TextUtils.isEmpty(query)) {
               onSubmitQuery();
                    }
                }

       public void setQueryHint(CharSequence hint) {
           this.mQueryHint = hint;
           updateQueryHint();
                }

       public CharSequence getQueryHint() {
                    int hintId;
           if (this.mQueryHint != null) {
               return this.mQueryHint;
                    }
           if (this.mSearchable == null || (hintId = this.mSearchable.getHintId()) == 0) {
               return null;
                    }
           CharSequence hint = getContext().getString(hintId);
           return hint;
                }

       public void setIconifiedByDefault(boolean iconified) {
           if (this.mIconifiedByDefault != iconified) {
               this.mIconifiedByDefault = iconified;
               updateViewsVisibility(iconified);
               updateQueryHint();
                    }
                }

       public boolean isIconfiedByDefault() {
           return this.mIconifiedByDefault;
                }

       public void setIconified(boolean iconify) {
           if (iconify) {
               onCloseClicked();
                    } else {
               onSearchClicked();
                    }
                }

       public boolean isIconified() {
           return this.mIconified;
                }

       public void setSubmitButtonEnabled(boolean enabled) {
           this.mSubmitButtonEnabled = enabled;
           updateViewsVisibility(isIconified());
                }

       public boolean isSubmitButtonEnabled() {
           return this.mSubmitButtonEnabled;
                }

       public void setQueryRefinementEnabled(boolean enable) {
           this.mQueryRefinement = enable;
           if (this.mSuggestionsAdapter instanceof SuggestionsAdapter) {
               ((SuggestionsAdapter) this.mSuggestionsAdapter).setQueryRefinement(enable ? 2 : 1);
                    }
                }

       public boolean isQueryRefinementEnabled() {
           return this.mQueryRefinement;
                }

       public void setSuggestionsAdapter(CursorAdapter adapter) {
           this.mSuggestionsAdapter = adapter;
           this.mQueryTextView.setAdapter(this.mSuggestionsAdapter);
                }

       public CursorAdapter getSuggestionsAdapter() {
           return this.mSuggestionsAdapter;
                }

       public void setMaxWidth(int maxpixels) {
           this.mMaxWidth = maxpixels;
           requestLayout();
                }

       public int getMaxWidth() {
           return this.mMaxWidth;
                }

                @Override 
       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
           if (isIconified()) {
               super.onMeasure(widthMeasureSpec, heightMeasureSpec);
               return;
                    }
           int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
           int width = View.MeasureSpec.getSize(widthMeasureSpec);
                    switch (widthMode) {
                        case Integer.MIN_VALUE:
                   width = this.mMaxWidth > 0 ? Math.min(this.mMaxWidth, width) : Math.min(getPreferredWidth(), width);
                            break;
                        case 0:
                   width = this.mMaxWidth <= 0 ? getPreferredWidth() : this.mMaxWidth;
                            break;
                        case 1073741824:
                   if (this.mMaxWidth > 0) {
                       width = Math.min(this.mMaxWidth, width);
                            }
                            break;
                    }
           super.onMeasure(View.MeasureSpec.makeMeasureSpec(width, 1073741824), heightMeasureSpec);
                }

       private int getPreferredWidth() {
           return getContext().getResources().getDimensionPixelSize(C0084R.dimen.abc_search_view_preferred_width);
                }

       private void updateViewsVisibility(boolean collapsed) {
           this.mIconified = collapsed;
           int visCollapsed = collapsed ? 0 : 8;
           boolean hasText = !TextUtils.isEmpty(this.mQueryTextView.getText());
           this.mSearchButton.setVisibility(visCollapsed);
           updateSubmitButton(hasText);
           this.mSearchEditFrame.setVisibility(collapsed ? 8 : 0);
           this.mSearchHintIcon.setVisibility(this.mIconifiedByDefault ? 8 : 0);
           updateCloseButton();
           updateVoiceButton(hasText ? false : true);
           updateSubmitArea();
                }

       private boolean hasVoiceSearch() {
           if (this.mSearchable == null || !this.mSearchable.getVoiceSearchEnabled()) {
                        return false;
                    }
           Intent testIntent = null;
           if (this.mSearchable.getVoiceSearchLaunchWebSearch()) {
               testIntent = this.mVoiceWebSearchIntent;
                    } else if (this.mSearchable.getVoiceSearchLaunchRecognizer()) {
               testIntent = this.mVoiceAppSearchIntent;
                    }
           if (testIntent == null) {
                        return false;
                    }
           ResolveInfo ri = getContext().getPackageManager().resolveActivity(testIntent, 65536);
                    return ri != null;
                }

       private boolean isSubmitAreaEnabled() {
                    return (this.mSubmitButtonEnabled || this.mVoiceButtonEnabled) && !isIconified();
                }

       private void updateSubmitButton(boolean hasText) {
           int visibility = 8;
           if (this.mSubmitButtonEnabled && isSubmitAreaEnabled() && hasFocus() && (hasText || !this.mVoiceButtonEnabled)) {
               visibility = 0;
                    }
           this.mSubmitButton.setVisibility(visibility);
                }

       private void updateSubmitArea() {
           int visibility = 8;
           if (isSubmitAreaEnabled() && (this.mSubmitButton.getVisibility() == 0 || this.mVoiceButton.getVisibility() == 0)) {
               visibility = 0;
                    }
           this.mSubmitArea.setVisibility(visibility);
                }

       private void updateCloseButton() {
                    boolean showClose = true;
           boolean hasText = !TextUtils.isEmpty(this.mQueryTextView.getText());
           if (!hasText && (!this.mIconifiedByDefault || this.mExpandedInActionView)) {
                        showClose = false;
                    }
           this.mCloseButton.setVisibility(showClose ? 0 : 8);
           this.mCloseButton.getDrawable().setState(hasText ? ENABLED_STATE_SET : EMPTY_STATE_SET);
                }

       private void postUpdateFocusedState() {
           post(this.mUpdateDrawableStateRunnable);
                }

       private void updateFocusedState() {
           boolean focused = this.mQueryTextView.hasFocus();
           this.mSearchPlate.getBackground().setState(focused ? FOCUSED_STATE_SET : EMPTY_STATE_SET);
           this.mSubmitArea.getBackground().setState(focused ? FOCUSED_STATE_SET : EMPTY_STATE_SET);
           invalidate();
                }

                @Override 
       protected void onDetachedFromWindow() {
           removeCallbacks(this.mUpdateDrawableStateRunnable);
           post(this.mReleaseCursorRunnable);
           super.onDetachedFromWindow();
                }

       private void setImeVisibility(boolean visible) {
           if (visible) {
               post(this.mShowImeRunnable);
               return;
                    }
           removeCallbacks(this.mShowImeRunnable);
           InputMethodManager imm = (InputMethodManager) getContext().getSystemService("input_method");
           if (imm != null) {
               imm.hideSoftInputFromWindow(getWindowToken(), 0);
                    }
                }

       void onQueryRefine(CharSequence queryText) {
           setQuery(queryText);
                }

                
       class ViewOnClickListenerC01187 implements View.OnClickListener {
           ViewOnClickListenerC01187() {
                    }

                    @Override 
           public void onClick(View v) {
               if (v == SearchView.access$400(SearchView.this)) {
                   SearchView.access$500(SearchView.this);
                   return;
                        }
               if (v == SearchView.access$600(SearchView.this)) {
                   SearchView.access$700(SearchView.this);
                   return;
                        }
               if (v == SearchView.access$800(SearchView.this)) {
                   SearchView.access$900(SearchView.this);
                        } else if (v == SearchView.access$1000(SearchView.this)) {
                   SearchView.access$1100(SearchView.this);
                        } else if (v == SearchView.access$1200(SearchView.this)) {
                   SearchView.access$1300(SearchView.this);
                        }
                    }
                }

                @Override 
       public boolean onKeyDown(int keyCode, KeyEvent event) {
           if (this.mSearchable == null) {
               return false;
                    }
                    return super.onKeyDown(keyCode, event);
                }

                
       class ViewOnKeyListenerC01198 implements View.OnKeyListener {
           ViewOnKeyListenerC01198() {
                    }

                    @Override 
           public boolean onKey(View v, int keyCode, KeyEvent event) {
               if (SearchView.access$1400(SearchView.this) == null) {
                            return false;
                        }
               if (SearchView.access$1200(SearchView.this).isPopupShowing() && SearchView.access$1200(SearchView.this).getListSelection() != -1) {
                   return SearchView.access$1500(SearchView.this, v, keyCode, event);
                        }
               if (SearchAutoComplete.access$1600(SearchView.access$1200(SearchView.this)) || !KeyEventCompat.hasNoModifiers(event) || event.getAction() != 1 || keyCode != 66) {
                            return false;
                        }
               v.cancelLongPress();
               SearchView.access$1700(SearchView.this, 0, null, SearchView.access$1200(SearchView.this).getText().toString());
                        return true;
                    }
                }

       private boolean onSuggestionsKey(View v, int keyCode, KeyEvent event) {
           if (this.mSearchable == null || this.mSuggestionsAdapter == null || event.getAction() != 0 || !KeyEventCompat.hasNoModifiers(event)) {
                        return false;
                    }
           if (keyCode == 66 || keyCode == 84 || keyCode == 61) {
               int position = this.mQueryTextView.getListSelection();
               return onItemClicked(position, 0, null);
                    }
           if (keyCode == 21 || keyCode == 22) {
               int selPoint = keyCode == 21 ? 0 : this.mQueryTextView.length();
               this.mQueryTextView.setSelection(selPoint);
               this.mQueryTextView.setListSelection(0);
               this.mQueryTextView.clearListSelection();
               HIDDEN_METHOD_INVOKER.ensureImeVisible(this.mQueryTextView, true);
                        return true;
                    }
           if (keyCode != 19 || this.mQueryTextView.getListSelection() != 0) {
                    }
                    return false;
                }

      private int getSearchIconId() {
          TypedValue outValue = new TypedValue();
          getContext().getTheme().resolveAttribute(C0084R.attr.searchViewSearchIcon, outValue, true);
          return outValue.resourceId;
                }

      private CharSequence getDecoratedHint(CharSequence hintText) {
          if (this.mIconifiedByDefault) {
              SpannableStringBuilder ssb = new SpannableStringBuilder("   ");
              ssb.append(hintText);
              Drawable searchIcon = getContext().getResources().getDrawable(getSearchIconId());
              int textSize = (int) (((double) this.mQueryTextView.getTextSize()) * 1.25d);
              searchIcon.setBounds(0, 0, textSize, textSize);
              ssb.setSpan(new ImageSpan(searchIcon), 1, 2, 33);
                        return ssb;
                    }
          return hintText;
                }

      private void updateQueryHint() {
          if (this.mQueryHint != null) {
              this.mQueryTextView.setHint(getDecoratedHint(this.mQueryHint));
              return;
                    }
          if (this.mSearchable != null) {
              CharSequence hint = null;
              int hintId = this.mSearchable.getHintId();
              if (hintId != 0) {
                  hint = getContext().getString(hintId);
                        }
              if (hint != null) {
                  this.mQueryTextView.setHint(getDecoratedHint(hint));
                  return;
                        }
              return;
                    }
          this.mQueryTextView.setHint(getDecoratedHint(""));
                }

      private void updateSearchAutoComplete() {
          this.mQueryTextView.setThreshold(this.mSearchable.getSuggestThreshold());
          this.mQueryTextView.setImeOptions(this.mSearchable.getImeOptions());
          int inputType = this.mSearchable.getInputType();
          if ((inputType & 15) == 1) {
              inputType &= -65537;
              if (this.mSearchable.getSuggestAuthority() != null) {
                  inputType = inputType | 65536 | 524288;
                        }
                    }
          this.mQueryTextView.setInputType(inputType);
          if (this.mSuggestionsAdapter != null) {
              this.mSuggestionsAdapter.changeCursor(null);
                    }
          if (this.mSearchable.getSuggestAuthority() != null) {
              this.mSuggestionsAdapter = new SuggestionsAdapter(getContext(), this, this.mSearchable, this.mOutsideDrawablesCache);
              this.mQueryTextView.setAdapter(this.mSuggestionsAdapter);
              ((SuggestionsAdapter) this.mSuggestionsAdapter).setQueryRefinement(this.mQueryRefinement ? 2 : 1);
                    }
                }

      private void updateVoiceButton(boolean empty) {
          int visibility = 8;
          if (this.mVoiceButtonEnabled && !isIconified() && empty) {
              visibility = 0;
              this.mSubmitButton.setVisibility(8);
                    }
          this.mVoiceButton.setVisibility(visibility);
                }

                
      class C01209 implements TextView.OnEditorActionListener {
          C01209() {
                    }

                    @Override 
          public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
              SearchView.access$900(SearchView.this);
              return true;
                    }
                }

      private void onTextChanged(CharSequence newText) {
          CharSequence text = this.mQueryTextView.getText();
          this.mUserQuery = text;
          boolean hasText = !TextUtils.isEmpty(text);
          updateSubmitButton(hasText);
          updateVoiceButton(hasText ? false : true);
          updateCloseButton();
          updateSubmitArea();
          if (this.mOnQueryChangeListener != null && !TextUtils.equals(newText, this.mOldQueryText)) {
              this.mOnQueryChangeListener.onQueryTextChange(newText.toString());
                    }
          this.mOldQueryText = newText.toString();
                }

      private void onSubmitQuery() {
          CharSequence query = this.mQueryTextView.getText();
          if (query != null && TextUtils.getTrimmedLength(query) > 0) {
              if (this.mOnQueryChangeListener == null || !this.mOnQueryChangeListener.onQueryTextSubmit(query.toString())) {
                  if (this.mSearchable != null) {
                      launchQuerySearch(0, null, query.toString());
                      setImeVisibility(false);
                            }
                  dismissSuggestions();
                        }
                    }
                }

      private void dismissSuggestions() {
          this.mQueryTextView.dismissDropDown();
                }

      private void onCloseClicked() {
          CharSequence text = this.mQueryTextView.getText();
          if (TextUtils.isEmpty(text)) {
              if (this.mIconifiedByDefault) {
                  if (this.mOnCloseListener == null || !this.mOnCloseListener.onClose()) {
                      clearFocus();
                      updateViewsVisibility(true);
                      return;
                            }
                  return;
                        }
              return;
                    }
          this.mQueryTextView.setText("");
          this.mQueryTextView.requestFocus();
          setImeVisibility(true);
                }

      private void onSearchClicked() {
          updateViewsVisibility(false);
          this.mQueryTextView.requestFocus();
          setImeVisibility(true);
          if (this.mOnSearchClickListener != null) {
              this.mOnSearchClickListener.onClick(this);
                    }
                }

      private void onVoiceClicked() {
          if (this.mSearchable != null) {
              SearchableInfo searchable = this.mSearchable;
                        try {
                  if (searchable.getVoiceSearchLaunchWebSearch()) {
                      Intent webSearchIntent = createVoiceWebSearchIntent(this.mVoiceWebSearchIntent, searchable);
                      getContext().startActivity(webSearchIntent);
                            } else if (searchable.getVoiceSearchLaunchRecognizer()) {
                      Intent appSearchIntent = createVoiceAppSearchIntent(this.mVoiceAppSearchIntent, searchable);
                      getContext().startActivity(appSearchIntent);
                            }
                        } catch (ActivityNotFoundException e) {
                  Log.w(LOG_TAG, "Could not find voice search activity");
                        }
                    }
                }

      void onTextFocusChanged() {
          updateViewsVisibility(isIconified());
          postUpdateFocusedState();
          if (this.mQueryTextView.hasFocus()) {
              forceSuggestionQuery();
                    }
                }

                @Override 
      public void onWindowFocusChanged(boolean hasWindowFocus) {
          super.onWindowFocusChanged(hasWindowFocus);
          postUpdateFocusedState();
                }

                @Override 
      public void onActionViewCollapsed() {
          clearFocus();
          updateViewsVisibility(true);
          this.mQueryTextView.setImeOptions(this.mCollapsedImeOptions);
          this.mExpandedInActionView = false;
                }

                @Override 
      public void onActionViewExpanded() {
          if (!this.mExpandedInActionView) {
              this.mExpandedInActionView = true;
              this.mCollapsedImeOptions = this.mQueryTextView.getImeOptions();
              this.mQueryTextView.setImeOptions(this.mCollapsedImeOptions | swscale.SWS_CPU_CAPS_SSE2);
              this.mQueryTextView.setText("");
              setIconified(false);
                    }
                }

      private void adjustDropDownSizeAndPosition() {
          if (this.mDropDownAnchor.getWidth() > 1) {
              Resources res = getContext().getResources();
              int anchorPadding = this.mSearchPlate.getPaddingLeft();
              Rect dropDownPadding = new Rect();
              int iconOffset = this.mIconifiedByDefault ? res.getDimensionPixelSize(C0084R.dimen.abc_dropdownitem_icon_width) + res.getDimensionPixelSize(C0084R.dimen.abc_dropdownitem_text_padding_left) : 0;
              this.mQueryTextView.getDropDownBackground().getPadding(dropDownPadding);
              int offset = anchorPadding - (dropDownPadding.left + iconOffset);
              this.mQueryTextView.setDropDownHorizontalOffset(offset);
              int width = (((this.mDropDownAnchor.getWidth() + dropDownPadding.left) + dropDownPadding.right) + iconOffset) - anchorPadding;
              this.mQueryTextView.setDropDownWidth(width);
                    }
                }

      private boolean onItemClicked(int position, int actionKey, String actionMsg) {
          if (this.mOnSuggestionListener != null && this.mOnSuggestionListener.onSuggestionClick(position)) {
                        return false;
                    }
          launchSuggestion(position, 0, null);
          setImeVisibility(false);
          dismissSuggestions();
          return true;
                }

      private boolean onItemSelected(int position) {
          if (this.mOnSuggestionListener != null && this.mOnSuggestionListener.onSuggestionSelect(position)) {
                        return false;
                    }
          rewriteQueryFromSuggestion(position);
          return true;
                }

                
      class C011010 implements AdapterView.OnItemClickListener {
          C011010() {
                    }

                    @Override 
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              SearchView.access$1800(SearchView.this, position, 0, null);
                    }
                }

                
      class C011111 implements AdapterView.OnItemSelectedListener {
          C011111() {
                    }

                    @Override 
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              SearchView.access$1900(SearchView.this, position);
                    }

                    @Override 
          public void onNothingSelected(AdapterView<?> parent) {
                    }
                }

      private void rewriteQueryFromSuggestion(int position) {
          CharSequence oldQuery = this.mQueryTextView.getText();
          Cursor c = this.mSuggestionsAdapter.getCursor();
          if (c != null) {
              if (c.moveToPosition(position)) {
                  CharSequence newQuery = this.mSuggestionsAdapter.convertToString(c);
                  if (newQuery != null) {
                      setQuery(newQuery);
                      return;
                            } else {
                      setQuery(oldQuery);
                      return;
                            }
                        }
              setQuery(oldQuery);
                    }
                }

      private boolean launchSuggestion(int position, int actionKey, String actionMsg) {
          Cursor c = this.mSuggestionsAdapter.getCursor();
          if (c == null || !c.moveToPosition(position)) {
                        return false;
                    }
          Intent intent = createIntentFromSuggestion(c, actionKey, actionMsg);
          launchIntent(intent);
          return true;
                }

      private void launchIntent(Intent intent) {
          if (intent != null) {
                        try {
                  getContext().startActivity(intent);
                        } catch (RuntimeException ex) {
                  Log.e(LOG_TAG, "Failed launch activity: " + intent, ex);
                        }
                    }
                }

      private void setQuery(CharSequence query) {
          this.mQueryTextView.setText(query);
          this.mQueryTextView.setSelection(TextUtils.isEmpty(query) ? 0 : query.length());
                }

      private void launchQuerySearch(int actionKey, String actionMsg, String query) {
          Intent intent = createIntent("android.intent.action.SEARCH", null, null, query, actionKey, actionMsg);
          getContext().startActivity(intent);
                }

      private Intent createIntent(String action, Uri data, String extraData, String query, int actionKey, String actionMsg) {
          Intent intent = new Intent(action);
          intent.addFlags(268435456);
          if (data != null) {
              intent.setData(data);
                    }
          intent.putExtra("user_query", this.mUserQuery);
          if (query != null) {
              intent.putExtra("query", query);
                    }
          if (extraData != null) {
              intent.putExtra("intent_extra_data_key", extraData);
                    }
          if (this.mAppSearchData != null) {
              intent.putExtra("app_data", this.mAppSearchData);
                    }
          if (actionKey != 0) {
              intent.putExtra("action_key", actionKey);
              intent.putExtra("action_msg", actionMsg);
                    }
          intent.setComponent(this.mSearchable.getSearchActivity());
          return intent;
                }

      private Intent createVoiceWebSearchIntent(Intent baseIntent, SearchableInfo searchable) {
          Intent voiceIntent = new Intent(baseIntent);
          ComponentName searchActivity = searchable.getSearchActivity();
          voiceIntent.putExtra("calling_package", searchActivity == null ? null : searchActivity.flattenToShortString());
          return voiceIntent;
                }

      private Intent createVoiceAppSearchIntent(Intent baseIntent, SearchableInfo searchable) {
          ComponentName searchActivity = searchable.getSearchActivity();
          Intent queryIntent = new Intent("android.intent.action.SEARCH");
          queryIntent.setComponent(searchActivity);
          PendingIntent pending = PendingIntent.getActivity(getContext(), 0, queryIntent, 1073741824);
          Bundle queryExtras = new Bundle();
          if (this.mAppSearchData != null) {
              queryExtras.putParcelable("app_data", this.mAppSearchData);
                    }
          Intent voiceIntent = new Intent(baseIntent);
          String languageModel = "free_form";
          String prompt = null;
          String language = null;
          int maxResults = 1;
          Resources resources = getResources();
          if (searchable.getVoiceLanguageModeId() != 0) {
              languageModel = resources.getString(searchable.getVoiceLanguageModeId());
                    }
          if (searchable.getVoicePromptTextId() != 0) {
              prompt = resources.getString(searchable.getVoicePromptTextId());
                    }
          if (searchable.getVoiceLanguageId() != 0) {
              language = resources.getString(searchable.getVoiceLanguageId());
                    }
          if (searchable.getVoiceMaxResults() != 0) {
              maxResults = searchable.getVoiceMaxResults();
                    }
          voiceIntent.putExtra("android.speech.extra.LANGUAGE_MODEL", languageModel);
          voiceIntent.putExtra("android.speech.extra.PROMPT", prompt);
          voiceIntent.putExtra("android.speech.extra.LANGUAGE", language);
          voiceIntent.putExtra("android.speech.extra.MAX_RESULTS", maxResults);
          voiceIntent.putExtra("calling_package", searchActivity == null ? null : searchActivity.flattenToShortString());
          voiceIntent.putExtra("android.speech.extra.RESULTS_PENDINGINTENT", pending);
          voiceIntent.putExtra("android.speech.extra.RESULTS_PENDINGINTENT_BUNDLE", queryExtras);
          return voiceIntent;
                }

      private Intent createIntentFromSuggestion(Cursor c, int actionKey, String actionMsg) {
                    int rowNum;
                    String id;
                    try {
              String action = SuggestionsAdapter.getColumnString(c, "suggest_intent_action");
              if (action == null) {
                  action = this.mSearchable.getSuggestIntentAction();
                        }
              if (action == null) {
                  action = "android.intent.action.SEARCH";
                        }
              String data = SuggestionsAdapter.getColumnString(c, "suggest_intent_data");
              if (data == null) {
                  data = this.mSearchable.getSuggestIntentData();
                        }
              if (data != null && (id = SuggestionsAdapter.getColumnString(c, "suggest_intent_data_id")) != null) {
                  data = data + "/" + Uri.encode(id);
                        }
              Uri dataUri = data == null ? null : Uri.parse(data);
              String query = SuggestionsAdapter.getColumnString(c, "suggest_intent_query");
              String extraData = SuggestionsAdapter.getColumnString(c, "suggest_intent_extra_data");
              return createIntent(action, dataUri, extraData, query, actionKey, actionMsg);
                    } catch (RuntimeException e) {
                        try {
                  rowNum = c.getPosition();
                        } catch (RuntimeException e2) {
                  rowNum = -1;
                        }
              Log.w(LOG_TAG, "Search suggestions cursor at row " + rowNum + " returned exception.", e);
              return null;
                    }
                }

      private void forceSuggestionQuery() {
          HIDDEN_METHOD_INVOKER.doBeforeTextChanged(this.mQueryTextView);
          HIDDEN_METHOD_INVOKER.doAfterTextChanged(this.mQueryTextView);
                }

      static boolean isLandscapeMode(Context context) {
                    return context.getResources().getConfiguration().orientation == 2;
                }

                
      class C011212 implements TextWatcher {
          C011212() {
                    }

                    @Override 
          public void beforeTextChanged(CharSequence s, int start, int before, int after) {
                    }

                    @Override 
          public void onTextChanged(CharSequence s, int start, int before, int after) {
              SearchView.access$2000(SearchView.this, s);
                    }

                    @Override 
          public void afterTextChanged(Editable s) {
                    }
                }

      public static class SearchAutoComplete extends AutoCompleteTextView {
                    private SearchView mSearchView;
                    private int mThreshold;

          static  boolean access$1600(SearchAutoComplete x0) {
              return x0.isEmpty();
                    }

          public SearchAutoComplete(Context context) {
              super(context);
              this.mThreshold = getThreshold();
                    }

          public SearchAutoComplete(Context context, AttributeSet attrs) {
              super(context, attrs);
              this.mThreshold = getThreshold();
                    }

          public SearchAutoComplete(Context context, AttributeSet attrs, int defStyle) {
              super(context, attrs, defStyle);
              this.mThreshold = getThreshold();
                    }

          void setSearchView(SearchView searchView) {
              this.mSearchView = searchView;
                    }

                    @Override 
          public void setThreshold(int threshold) {
              super.setThreshold(threshold);
              this.mThreshold = threshold;
                    }

          private boolean isEmpty() {
                        return TextUtils.getTrimmedLength(getText()) == 0;
                    }

                    @Override 
          protected void replaceText(CharSequence text) {
                    }

                    @Override 
          public void performCompletion() {
                    }

                    @Override 
          public void onWindowFocusChanged(boolean hasWindowFocus) {
              super.onWindowFocusChanged(hasWindowFocus);
              if (hasWindowFocus && this.mSearchView.hasFocus() && getVisibility() == 0) {
                  InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService("input_method");
                  inputManager.showSoftInput(this, 0);
                  if (SearchView.isLandscapeMode(getContext())) {
                      SearchView.HIDDEN_METHOD_INVOKER.ensureImeVisible(this, true);
                            }
                        }
                    }

                    @Override 
          protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
              super.onFocusChanged(focused, direction, previouslyFocusedRect);
              this.mSearchView.onTextFocusChanged();
                    }

                    @Override 
          public boolean enoughToFilter() {
                        return this.mThreshold <= 0 || super.enoughToFilter();
                    }

                    @Override 
          public boolean onKeyPreIme(int keyCode, KeyEvent event) {
              if (keyCode == 4) {
                  if (event.getAction() == 0 && event.getRepeatCount() == 0) {
                      KeyEvent.DispatcherState state = getKeyDispatcherState();
                      if (state == null) {
                                    return true;
                                }
                      state.startTracking(event, this);
                                return true;
                            }
                  if (event.getAction() == 1) {
                      KeyEvent.DispatcherState state2 = getKeyDispatcherState();
                      if (state2 != null) {
                          state2.handleUpEvent(event);
                                }
                      if (event.isTracking() && !event.isCanceled()) {
                          this.mSearchView.clearFocus();
                          SearchView.access$2100(this.mSearchView, false);
                                    return true;
                                }
                            }
                        }
              return super.onKeyPreIme(keyCode, event);
                    }
                }

      private static class AutoCompleteTextViewReflector {
                    private Method doAfterTextChanged;
                    private Method doBeforeTextChanged;
                    private Method ensureImeVisible;
                    private Method showSoftInputUnchecked;

          AutoCompleteTextViewReflector() {
                        try {
                  this.doBeforeTextChanged = AutoCompleteTextView.class.getDeclaredMethod("doBeforeTextChanged", new Class[0]);
                  this.doBeforeTextChanged.setAccessible(true);
                        } catch (NoSuchMethodException e) {
                        }
                        try {
                  this.doAfterTextChanged = AutoCompleteTextView.class.getDeclaredMethod("doAfterTextChanged", new Class[0]);
                  this.doAfterTextChanged.setAccessible(true);
                        } catch (NoSuchMethodException e2) {
                        }
                        try {
                  this.ensureImeVisible = AutoCompleteTextView.class.getMethod("ensureImeVisible", Boolean.TYPE);
                  this.ensureImeVisible.setAccessible(true);
                        } catch (NoSuchMethodException e3) {
                        }
                        try {
                  this.showSoftInputUnchecked = InputMethodManager.class.getMethod("showSoftInputUnchecked", Integer.TYPE, ResultReceiver.class);
                  this.showSoftInputUnchecked.setAccessible(true);
                        } catch (NoSuchMethodException e4) {
                        }
                    }

          void doBeforeTextChanged(AutoCompleteTextView view) {
              if (this.doBeforeTextChanged != null) {
                            try {
                      this.doBeforeTextChanged.invoke(view, new Object[0]);
                            } catch (Exception e) {
                            }
                        }
                    }

          void doAfterTextChanged(AutoCompleteTextView view) {
              if (this.doAfterTextChanged != null) {
                            try {
                      this.doAfterTextChanged.invoke(view, new Object[0]);
                            } catch (Exception e) {
                            }
                        }
                    }

          void ensureImeVisible(AutoCompleteTextView view, boolean visible) {
              if (this.ensureImeVisible != null) {
                            try {
                      this.ensureImeVisible.invoke(view, Boolean.valueOf(visible));
                            } catch (Exception e) {
                            }
                        }
                    }

          void showSoftInputUnchecked(InputMethodManager imm, View view, int flags) {
              if (this.showSoftInputUnchecked != null) {
                            try {
                      this.showSoftInputUnchecked.invoke(imm, Integer.valueOf(flags), null);
                      return;
                            } catch (Exception e) {
                            }
                        }
              imm.showSoftInput(view, flags);
                    }
                }
            }
