            package co.vine.android.widget;

            import android.content.Context;
            import android.content.res.TypedArray;
            import android.database.DataSetObserver;
            import android.graphics.Rect;
            import android.graphics.Typeface;
            import android.os.Build;
            import android.os.Handler;
            import android.os.Looper;
            import android.os.Message;
            import android.support.annotation.NonNull;
            import android.support.v4.widget.CursorAdapter;
            import android.text.Editable;
            import android.text.SpannableStringBuilder;
            import android.text.TextUtils;
            import android.text.style.ForegroundColorSpan;
            import android.util.AttributeSet;
            import android.view.KeyEvent;
            import android.view.View;
            import android.view.inputmethod.EditorInfo;
            import android.view.inputmethod.InputConnection;
            import android.view.inputmethod.InputConnectionWrapper;
            import android.widget.AdapterView;
            import android.widget.Filter;
            import android.widget.Filterable;
            import android.widget.ListAdapter;
            import android.widget.MultiAutoCompleteTextView;
            import android.widget.PopupWindow;
            import co.vine.android.C0257R;
            import co.vine.android.api.VineEntity;
            import co.vine.android.api.VineTypeAhead;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.Util;
            import co.vine.android.views.SdkEditText;
            import co.vine.android.views.SdkListView;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            import java.util.Iterator;
            import java.util.Map;
            import java.util.NavigableMap;
            import java.util.TreeMap;
            
            
    public class PopupEditText extends SdkEditText implements View.OnClickListener, AdapterView.OnItemClickListener, Filter.FilterListener {
                private static final int MSG_FILTER = 0;
                private static final int NO_CALLBACK = 0;
                private static final int TRIGGER_CALLBACK = 1;
                private ListAdapter mAdapter;
                private View.OnClickListener mClick;
                private DataSetObserver mDataSetObserver;
                private final int mDropDownHorizontalOffset;
                private final DropDownListView mDropDownList;
                private final int mDropDownVerticalOffset;
                private TreeMap<Integer, VineEntity.Range> mEntityRanges;
                private Filterable mFilterable;
                private final FilterHandler mHandler;
                private PopupEditTextListener mListener;
                private final PopupWindow mPopup;
                private VineEntity mSelectedEntity;
                private boolean mShowRequested;
                private final int mThreshold;
                private long mThrottleDelay;
                private MultiAutoCompleteTextView.Tokenizer mTokenizer;

                public interface PopupEditTextListener {
                    CursorAdapter getPopupAdapter();

                    void onFiltering(CharSequence charSequence);

                    void onPopupItemSelected(int i, int i2, CharSequence charSequence, long j);

                    void onPopupShown();

                    void setPopupAdapter(String str);
                }

        static  boolean access$000(PopupEditText x0) {
            return x0.mShowRequested;
                }

        static  boolean access$002(PopupEditText x0, boolean x1) {
            x0.mShowRequested = x1;
                    return x1;
                }

        static  VineEntity access$100(PopupEditText x0) {
            return x0.mSelectedEntity;
                }

                @Override 
        public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
            if (this.mSelectedEntity == null || event.getAction() != 0 || event.getKeyCode() != 67) {
                        return super.onKeyDown(keyCode, event);
                    }
            clearSelectedEntity(this.mSelectedEntity.start, true);
                    return true;
                }

                @Override 
        public void setTypeface(Typeface tf, int style) {
            Context context = getContext();
            super.setTypeface(Typefaces.get(context).getContentTypeface(style, 0));
                }

        public PopupEditText(Context context) {
            this(context, null);
                }

       public PopupEditText(Context context, AttributeSet attrs) {
           this(context, attrs, C0257R.attr.popupEditTextStyle);
                }

       public PopupEditText(Context context, AttributeSet attrs, int defStyle) {
           super(context, attrs, defStyle);
            this.mShowRequested = false;
           TypedArray a = context.obtainStyledAttributes(attrs, C0257R.styleable.PopupEditText, defStyle, 0);
           this.mDropDownVerticalOffset = a.getDimensionPixelOffset(0, 0);
           this.mDropDownHorizontalOffset = a.getDimensionPixelOffset(1, 0);
           this.mThreshold = a.getInteger(2, 0);
           DropDownListView listView = new DropDownListView(context, null, C0257R.attr.popupEditListStyle);
           listView.setDividerHeight(getResources().getDimensionPixelSize(C0257R.dimen.divider_thickness));
           listView.setOnItemClickListener(this);
           this.mDropDownList = listView;
           PopupWindow popupWindow = new PopupWindow(context, attrs, C0257R.attr.popupEditTextStyle);
           popupWindow.setSoftInputMode(16);
           popupWindow.setOutsideTouchable(false);
           popupWindow.setContentView(listView);
           popupWindow.setHeight(-1);
           popupWindow.setWindowLayoutMode(-1, -1);
           popupWindow.setInputMethodMode(1);
           this.mPopup = popupWindow;
           super.setOnClickListener(this);
           this.mHandler = new FilterHandler(Looper.getMainLooper(), this);
           a.recycle();
           this.mEntityRanges = new TreeMap<>();
                }

       public void setTokenizer(MultiAutoCompleteTextView.Tokenizer tokenizer, Filterable filter, long throttleDelay) {
           if (tokenizer == null) {
               throw new IllegalArgumentException("tokenizer cannot be null.");
                    }
           if (this.mAdapter == null) {
               throw new IllegalStateException("setAdapter must be called first with a non-null adapter");
                    }
           this.mFilterable = filter;
           this.mTokenizer = tokenizer;
           this.mThrottleDelay = throttleDelay;
           this.mHandler.setFilterable(filter);
                }

                @Override 
       protected void onSelectionChanged(int selStart, int selEnd) {
           super.onSelectionChanged(selStart, selEnd);
           atomizeEntity(selStart, selEnd);
                }

       private void atomizeEntity(int selStart, int selEnd) {
           VineEntity.Range range = lookupRange(selStart, selEnd);
           if (range != null && range.entity != null) {
               this.mSelectedEntity = range.entity;
               Editable editable = getText();
               int spanStart = editable.getSpanStart(range.span);
               int spanEnd = editable.getSpanEnd(range.span);
               setSelection(spanStart, spanEnd);
               return;
                    }
           this.mSelectedEntity = null;
                }

                @Override 
       protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
           super.onTextChanged(text, start, lengthBefore, lengthAfter);
           NavigableMap<Integer, VineEntity.Range> ranges = this.mEntityRanges;
           if (ranges != null && !ranges.isEmpty()) {
               Map.Entry<Integer, VineEntity.Range> entry = ranges.ceilingEntry(Integer.valueOf(start));
               while (entry != null) {
                   VineEntity.Range range = entry.getValue();
                   Editable editable = getText();
                   int newStart = editable.getSpanStart(range.span);
                   int newEnd = editable.getSpanEnd(range.span);
                   range.entity.start = newStart;
                   range.entity.end = newEnd;
                   VineEntity.Range newRange = new VineEntity.Range(newEnd, range.entity, range.span);
                   ranges.remove(entry.getKey());
                   ranges.put(Integer.valueOf(newStart), newRange);
                   entry = ranges.ceilingEntry(Integer.valueOf(range.entity.end));
                        }
                    }
           if (this.mSelectedEntity != null && this.mSelectedEntity.start == start) {
               clearSelectedEntity(this.mSelectedEntity.start, false);
                    } else {
               performFilter(true);
                    }
           if (Build.VERSION.SDK_INT < 16) {
               atomizeEntity(getSelectionStart(), getSelectionEnd());
                    }
                }

       public void performFilter(boolean triggerCallback) {
                    int end;
           if (this.mTokenizer != null && this.mFilterable != null && (end = getSelectionEnd()) >= 0) {
               CharSequence text = getText();
               int tokenStart = this.mTokenizer.findTokenStart(text, end);
               Handler handler = this.mHandler;
               handler.removeMessages(0);
                        handler.sendMessageDelayed(handler.obtainMessage(0, triggerCallback ? 1 : 0, 0, text.subSequence(tokenStart, end)), this.mThrottleDelay);
                    }
                }

                @Override 
       public void onFilterComplete(int count) {
           int end = getSelectionEnd();
           int tokenStart = this.mTokenizer.findTokenStart(getText(), end);
           if (end - tokenStart >= this.mThreshold) {
               showDropDown();
                    } else {
               dismissDropDown();
                    }
                }

                @Override 
       protected boolean setFrame(int l, int t, int r, int b) {
           boolean result = super.setFrame(l, t, r, b);
           if (isPopupShowing()) {
               showDropDown();
                    }
           return result;
                }

                @Override 
       protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
           super.onFocusChanged(focused, direction, previouslyFocusedRect);
           if (!focused) {
               dismissDropDown();
                    }
                }

                @Override 
       public void onWindowFocusChanged(boolean hasWindowFocus) {
           super.onWindowFocusChanged(hasWindowFocus);
           if (!hasWindowFocus) {
               dismissDropDown();
                    }
                }

                @Override 
       protected void onDetachedFromWindow() {
           super.onDetachedFromWindow();
           dismissDropDown();
                }

                @Override 
       public void setOnClickListener(View.OnClickListener l) {
           this.mClick = l;
                }

       public void showDropDown() {
           if (getWindowVisibility() != 8) {
               int count = this.mAdapter.getCount();
               if (count == 0) {
                   dismissDropDown();
                   this.mShowRequested = true;
                   return;
                        }
               int width = ((View) getParent()).getWidth();
               PopupWindow p = this.mPopup;
               if (!p.isShowing()) {
                   p.setWidth(width);
                   p.showAsDropDown(this, this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset);
                   if (this.mListener != null) {
                       this.mListener.onPopupShown();
                            }
                        }
               this.mDropDownList.setSelectionAfterHeaderView();
                    }
                }

       public void dismissDropDown() {
           this.mPopup.dismiss();
           this.mShowRequested = false;
           this.mHandler.removeMessages(0);
                }

       public boolean clearSelectedEntity(int start, boolean usingBackspace) {
           VineEntity selectedEntity = this.mSelectedEntity;
           if (selectedEntity != null) {
               this.mSelectedEntity = null;
               Editable editable = getText();
               if (!TextUtils.isEmpty(editable)) {
                   VineEntity.Range range = this.mEntityRanges.remove(Integer.valueOf(start));
                   int spanStart = editable.getSpanStart(range.span);
                   int spanEnd = editable.getSpanEnd(range.span);
                   editable.removeSpan(range.span);
                   if (usingBackspace) {
                       editable.delete(spanStart, spanEnd);
                       setSelection(spanStart);
                            } else {
                       setSelection(spanStart + 1);
                            }
                        }
               return true;
                    }
           return false;
                }

       public ArrayList<VineEntity> getEntities() {
           Editable text = getText();
           if (text != null && text.length() > 0) {
               while (Character.isWhitespace(text.charAt(0))) {
                            try {
                       text.replace(0, 1, "");
                            } catch (IndexOutOfBoundsException e) {
                            }
                        }
                    }
           ArrayList<VineEntity> entities = new ArrayList<>();
           TreeMap<Integer, VineEntity.Range> ranges = this.mEntityRanges;
           if (text != null) {
               int adjustedRanges = 1;
                        for (int i = 0; i < text.length(); i++) {
                   if (Character.isHighSurrogate(text.charAt(i))) {
                       Map.Entry<Integer, VineEntity.Range> entry = ranges.ceilingEntry(Integer.valueOf(i));
                       while (entry != null) {
                           VineEntity.Range range = entry.getValue();
                           int newStart = text.getSpanStart(range.span) - adjustedRanges;
                           int newEnd = text.getSpanEnd(range.span) - adjustedRanges;
                           range.entity.start = newStart;
                           range.entity.end = newEnd;
                           VineEntity.Range newRange = new VineEntity.Range(newEnd, range.entity, range.span);
                           ranges.remove(entry.getKey());
                           ranges.put(Integer.valueOf(newStart), newRange);
                           entry = ranges.ceilingEntry(Integer.valueOf(range.entity.end));
                                }
                       adjustedRanges++;
                            }
                        }
                    }
           Iterator<VineEntity.Range> it = ranges.values().iterator();
                    while (it.hasNext()) {
               entities.add(it.next().entity);
                    }
           ranges.clear();
           return entities;
                }

                @Override 
       public void onClick(View view) {
           if (isPopupShowing()) {
               this.mPopup.setInputMethodMode(1);
               showDropDown();
                    }
           if (this.mClick != null) {
               this.mClick.onClick(view);
                    }
                }

       public void setPopupEditTextListener(PopupEditTextListener listener) {
           this.mListener = listener;
           this.mHandler.setOnFilterListener(listener);
                }

                @Override 
       public void onItemClick(AdapterView<?> adapterView, View view, int pos, long userId) {
           clearComposingText();
           if (adapterView.getItemAtPosition(pos) instanceof VineTypeAhead) {
               VineTypeAhead item = (VineTypeAhead) adapterView.getItemAtPosition(pos);
               if (this.mFilterable == null) {
                   CrashUtil.log("Invalid state: mFilterable=null, type={}, token={}", item.type, item.token);
                        } else {
                   Editable editable = getText();
                   int end = getSelectionEnd();
                   int tokenStart = this.mTokenizer.findTokenStart(editable, end);
                   CharSequence replacement = this.mTokenizer.terminateToken(this.mFilterable.getFilter().convertResultToString(item.token));
                   int tokenEnd = tokenStart + item.token.length();
                   VineEntity entity = new VineEntity(item.type, item.token, null, tokenStart, tokenEnd, userId);
                   SpannableStringBuilder ssb = new SpannableStringBuilder(replacement);
                   ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(C0257R.color.vine_green));
                   Util.safeSetSpan(ssb, span, 0, item.token.length(), 0);
                   editable.replace(tokenStart, end, ssb);
                   this.mEntityRanges.put(Integer.valueOf(tokenStart), new VineEntity.Range(tokenEnd, entity, span));
                   if (this.mListener != null) {
                       this.mListener.onPopupItemSelected(tokenStart, end, replacement, userId);
                            }
                        }
               dismissDropDown();
                    }
                }

       public void putEntity(int start, int end, VineEntity e) {
           Editable editable = getText();
           ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(C0257R.color.vine_green));
           SpannableStringBuilder ssb = new SpannableStringBuilder(e.title);
           Util.safeSetSpan(ssb, span, 0, e.end - e.start, 0);
           editable.replace(start, end, ssb);
           this.mEntityRanges.put(Integer.valueOf(e.start), new VineEntity.Range(e.end, e, span));
                }

       private VineEntity.Range lookupRange(int selStart, int selEnd) {
           TreeMap<Integer, VineEntity.Range> ranges = this.mEntityRanges;
           if (ranges != null && !ranges.isEmpty()) {
               Map.Entry<Integer, VineEntity.Range> entry = ranges.floorEntry(Integer.valueOf(selStart));
               if (entry == null) {
                   entry = ranges.floorEntry(Integer.valueOf(selEnd));
                        }
               if (entry != null && (selStart <= entry.getValue().upper || selEnd <= entry.getValue().upper)) {
                   return entry.getValue();
                        }
                    }
                    return null;
                }

       public void setAdapter(ListAdapter adapter) {
           if (this.mAdapter != null) {
               this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
                    } else {
               this.mDataSetObserver = new AdapterObserver();
                    }
           this.mDropDownList.setAdapter(adapter);
           adapter.registerDataSetObserver(this.mDataSetObserver);
           this.mAdapter = adapter;
                }

                @Override 
       public boolean onKeyPreIme(int keyCode, @NonNull KeyEvent event) {
           if (keyCode == 4 && isPopupShowing()) {
               if (event.getAction() == 0 && event.getRepeatCount() == 0) {
                   getKeyDispatcherState().startTracking(event, this);
                            return true;
                        }
               if (event.getAction() == 1) {
                   getKeyDispatcherState().handleUpEvent(event);
                   if (event.isTracking() && !event.isCanceled()) {
                       dismissDropDown();
                                return true;
                            }
                        }
                    }
           return super.onKeyPreIme(keyCode, event);
                }

       private boolean isPopupShowing() {
           return this.mPopup.isShowing();
                }

       private static class DropDownListView extends SdkListView {
           public DropDownListView(Context context, AttributeSet attrs, int defStyle) {
               super(context, attrs, defStyle);
                    }

                    @Override 
           public boolean hasWindowFocus() {
               return true;
                    }

                    @Override 
           public boolean isFocused() {
               return true;
                    }

                    @Override 
           public boolean hasFocus() {
               return true;
                    }
                }

       private class AdapterObserver extends DataSetObserver {
           public AdapterObserver() {
                    }

                    @Override 
           public void onChanged() {
               super.onChanged();
               if (PopupEditText.access$000(PopupEditText.this)) {
                   PopupEditText.this.showDropDown();
                   PopupEditText.access$002(PopupEditText.this, false);
                        }
                    }

                    @Override 
           public void onInvalidated() {
               super.onInvalidated();
               PopupEditText.this.dismissDropDown();
                    }
                }

       private static class FilterHandler extends Handler {
                    private Filterable mFilterable;
                    private final WeakReference<Filter.FilterListener> mListenerRef;
                    private PopupEditTextListener mOnFilterListener;

           public FilterHandler(Looper looper, Filter.FilterListener listener) {
               super(looper);
               this.mListenerRef = new WeakReference<>(listener);
                    }

                    @Override 
           public void handleMessage(Message msg) {
               Filter.FilterListener listener = this.mListenerRef.get();
               if (this.mFilterable != null && listener != null) {
                   CharSequence token = (CharSequence) msg.obj;
                   this.mFilterable.getFilter().filter(token, listener);
                   if (msg.arg1 == 1 && this.mOnFilterListener != null) {
                       this.mOnFilterListener.onFiltering(token);
                            }
                        }
                    }

           public void setFilterable(Filterable filterable) {
               this.mFilterable = filterable;
                    }

           public void setOnFilterListener(PopupEditTextListener listener) {
               this.mOnFilterListener = listener;
                    }
                }

                @Override 
       public InputConnection onCreateInputConnection(@NonNull EditorInfo outAttrs) {
           return new PopupInputConnection(super.onCreateInputConnection(outAttrs), true);
                }

       private class PopupInputConnection extends InputConnectionWrapper {
           public PopupInputConnection(InputConnection target, boolean mutable) {
               super(target, mutable);
                    }

                    @Override 
           public boolean sendKeyEvent(KeyEvent event) {
               if (PopupEditText.access$100(PopupEditText.this) == null || event.getAction() != 0 || event.getKeyCode() != 67) {
                            return super.sendKeyEvent(event);
                        }
               PopupEditText.this.clearSelectedEntity(PopupEditText.access$100(PopupEditText.this).start, true);
                        return true;
                    }

                    @Override 
           public boolean deleteSurroundingText(int beforeLength, int afterLength) {
                        return (PopupEditText.access$100(PopupEditText.this) == null || beforeLength <= 0 || afterLength != 0) ? super.deleteSurroundingText(beforeLength, afterLength) : sendKeyEvent(new KeyEvent(0, 67));
                    }
                }

                @Override 
       public boolean onTextContextMenuItem(int id) {
           if (id == 16908320 && this.mSelectedEntity != null) {
               clearSelectedEntity(this.mSelectedEntity.start, true);
                    }
           return super.onTextContextMenuItem(id);
                }
            }
