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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 53 */    public class PopupEditText extends SdkEditText implements View.OnClickListener, AdapterView.OnItemClickListener, Filter.FilterListener {
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

/* 54 */        static /* synthetic */ boolean access$000(PopupEditText x0) {
/* 55 */            return x0.mShowRequested;
                }

/* 54 */        static /* synthetic */ boolean access$002(PopupEditText x0, boolean x1) {
/* 55 */            x0.mShowRequested = x1;
                    return x1;
                }

/* 54 */        static /* synthetic */ VineEntity access$100(PopupEditText x0) {
/* 55 */            return x0.mSelectedEntity;
                }

                @Override // android.widget.TextView, android.view.View, android.view.KeyEvent.Callback
/* 69 */        public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
/* 70 */            if (this.mSelectedEntity == null || event.getAction() != 0 || event.getKeyCode() != 67) {
                        return super.onKeyDown(keyCode, event);
                    }
/* 72 */            clearSelectedEntity(this.mSelectedEntity.start, true);
                    return true;
                }

                @Override // android.widget.TextView
/* 91 */        public void setTypeface(Typeface tf, int style) {
/* 92 */            Context context = getContext();
/* 94 */            super.setTypeface(Typefaces.get(context).getContentTypeface(style, 0));
                }

/* 97 */        public PopupEditText(Context context) {
/* 98 */            this(context, null);
                }

/* 101 */       public PopupEditText(Context context, AttributeSet attrs) {
/* 102 */           this(context, attrs, C0257R.attr.popupEditTextStyle);
                }

/* 105 */       public PopupEditText(Context context, AttributeSet attrs, int defStyle) {
/* 106 */           super(context, attrs, defStyle);
/* 81 */            this.mShowRequested = false;
/* 108 */           TypedArray a = context.obtainStyledAttributes(attrs, C0257R.styleable.PopupEditText, defStyle, 0);
/* 111 */           this.mDropDownVerticalOffset = a.getDimensionPixelOffset(0, 0);
/* 114 */           this.mDropDownHorizontalOffset = a.getDimensionPixelOffset(1, 0);
/* 117 */           this.mThreshold = a.getInteger(2, 0);
/* 119 */           DropDownListView listView = new DropDownListView(context, null, C0257R.attr.popupEditListStyle);
/* 121 */           listView.setDividerHeight(getResources().getDimensionPixelSize(C0257R.dimen.divider_thickness));
/* 122 */           listView.setOnItemClickListener(this);
/* 123 */           this.mDropDownList = listView;
/* 125 */           PopupWindow popupWindow = new PopupWindow(context, attrs, C0257R.attr.popupEditTextStyle);
/* 126 */           popupWindow.setSoftInputMode(16);
/* 127 */           popupWindow.setOutsideTouchable(false);
/* 128 */           popupWindow.setContentView(listView);
/* 129 */           popupWindow.setHeight(-1);
/* 130 */           popupWindow.setWindowLayoutMode(-1, -1);
/* 131 */           popupWindow.setInputMethodMode(1);
/* 132 */           this.mPopup = popupWindow;
/* 134 */           super.setOnClickListener(this);
/* 135 */           this.mHandler = new FilterHandler(Looper.getMainLooper(), this);
/* 136 */           a.recycle();
/* 138 */           this.mEntityRanges = new TreeMap<>();
                }

/* 142 */       public void setTokenizer(MultiAutoCompleteTextView.Tokenizer tokenizer, Filterable filter, long throttleDelay) {
/* 143 */           if (tokenizer == null) {
/* 144 */               throw new IllegalArgumentException("tokenizer cannot be null.");
                    }
/* 146 */           if (this.mAdapter == null) {
/* 147 */               throw new IllegalStateException("setAdapter must be called first with a non-null adapter");
                    }
/* 150 */           this.mFilterable = filter;
/* 151 */           this.mTokenizer = tokenizer;
/* 152 */           this.mThrottleDelay = throttleDelay;
/* 153 */           this.mHandler.setFilterable(filter);
                }

                @Override // android.widget.TextView
/* 157 */       protected void onSelectionChanged(int selStart, int selEnd) {
/* 158 */           super.onSelectionChanged(selStart, selEnd);
/* 159 */           atomizeEntity(selStart, selEnd);
                }

/* 166 */       private void atomizeEntity(int selStart, int selEnd) {
/* 167 */           VineEntity.Range range = lookupRange(selStart, selEnd);
/* 168 */           if (range != null && range.entity != null) {
/* 169 */               this.mSelectedEntity = range.entity;
/* 170 */               Editable editable = getText();
/* 171 */               int spanStart = editable.getSpanStart(range.span);
/* 172 */               int spanEnd = editable.getSpanEnd(range.span);
/* 173 */               setSelection(spanStart, spanEnd);
/* 177 */               return;
                    }
/* 175 */           this.mSelectedEntity = null;
                }

                @Override // android.widget.TextView
/* 180 */       protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
/* 181 */           super.onTextChanged(text, start, lengthBefore, lengthAfter);
/* 183 */           NavigableMap<Integer, VineEntity.Range> ranges = this.mEntityRanges;
/* 184 */           if (ranges != null && !ranges.isEmpty()) {
/* 185 */               Map.Entry<Integer, VineEntity.Range> entry = ranges.ceilingEntry(Integer.valueOf(start));
/* 186 */               while (entry != null) {
/* 187 */                   VineEntity.Range range = entry.getValue();
/* 188 */                   Editable editable = getText();
/* 189 */                   int newStart = editable.getSpanStart(range.span);
/* 190 */                   int newEnd = editable.getSpanEnd(range.span);
/* 191 */                   range.entity.start = newStart;
/* 192 */                   range.entity.end = newEnd;
/* 194 */                   VineEntity.Range newRange = new VineEntity.Range(newEnd, range.entity, range.span);
/* 195 */                   ranges.remove(entry.getKey());
/* 196 */                   ranges.put(Integer.valueOf(newStart), newRange);
/* 197 */                   entry = ranges.ceilingEntry(Integer.valueOf(range.entity.end));
                        }
                    }
/* 201 */           if (this.mSelectedEntity != null && this.mSelectedEntity.start == start) {
/* 203 */               clearSelectedEntity(this.mSelectedEntity.start, false);
                    } else {
/* 205 */               performFilter(true);
                    }
/* 207 */           if (Build.VERSION.SDK_INT < 16) {
/* 209 */               atomizeEntity(getSelectionStart(), getSelectionEnd());
                    }
                }

/* 213 */       public void performFilter(boolean triggerCallback) {
                    int end;
/* 214 */           if (this.mTokenizer != null && this.mFilterable != null && (end = getSelectionEnd()) >= 0) {
/* 223 */               CharSequence text = getText();
/* 224 */               int tokenStart = this.mTokenizer.findTokenStart(text, end);
/* 225 */               Handler handler = this.mHandler;
/* 226 */               handler.removeMessages(0);
                        handler.sendMessageDelayed(handler.obtainMessage(0, triggerCallback ? 1 : 0, 0, text.subSequence(tokenStart, end)), this.mThrottleDelay);
                    }
                }

                @Override // android.widget.Filter.FilterListener
/* 235 */       public void onFilterComplete(int count) {
/* 236 */           int end = getSelectionEnd();
/* 237 */           int tokenStart = this.mTokenizer.findTokenStart(getText(), end);
/* 238 */           if (end - tokenStart >= this.mThreshold) {
/* 239 */               showDropDown();
                    } else {
/* 241 */               dismissDropDown();
                    }
                }

                @Override // android.widget.TextView
/* 246 */       protected boolean setFrame(int l, int t, int r, int b) {
/* 247 */           boolean result = super.setFrame(l, t, r, b);
/* 248 */           if (isPopupShowing()) {
/* 249 */               showDropDown();
                    }
/* 251 */           return result;
                }

                @Override // android.widget.TextView, android.view.View
/* 255 */       protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
/* 256 */           super.onFocusChanged(focused, direction, previouslyFocusedRect);
/* 257 */           if (!focused) {
/* 258 */               dismissDropDown();
                    }
                }

                @Override // android.widget.TextView, android.view.View
/* 263 */       public void onWindowFocusChanged(boolean hasWindowFocus) {
/* 264 */           super.onWindowFocusChanged(hasWindowFocus);
/* 265 */           if (!hasWindowFocus) {
/* 266 */               dismissDropDown();
                    }
                }

                @Override // android.view.View
/* 271 */       protected void onDetachedFromWindow() {
/* 272 */           super.onDetachedFromWindow();
/* 273 */           dismissDropDown();
                }

                @Override // android.view.View
/* 277 */       public void setOnClickListener(View.OnClickListener l) {
/* 278 */           this.mClick = l;
                }

/* 282 */       public void showDropDown() {
/* 283 */           if (getWindowVisibility() != 8) {
/* 287 */               int count = this.mAdapter.getCount();
/* 288 */               if (count == 0) {
/* 290 */                   dismissDropDown();
/* 291 */                   this.mShowRequested = true;
/* 306 */                   return;
                        }
/* 296 */               int width = ((View) getParent()).getWidth();
/* 297 */               PopupWindow p = this.mPopup;
/* 298 */               if (!p.isShowing()) {
/* 299 */                   p.setWidth(width);
/* 300 */                   p.showAsDropDown(this, this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset);
/* 301 */                   if (this.mListener != null) {
/* 302 */                       this.mListener.onPopupShown();
                            }
                        }
/* 305 */               this.mDropDownList.setSelectionAfterHeaderView();
                    }
                }

/* 308 */       public void dismissDropDown() {
/* 309 */           this.mPopup.dismiss();
/* 310 */           this.mShowRequested = false;
/* 311 */           this.mHandler.removeMessages(0);
                }

/* 314 */       public boolean clearSelectedEntity(int start, boolean usingBackspace) {
/* 315 */           VineEntity selectedEntity = this.mSelectedEntity;
/* 316 */           if (selectedEntity != null) {
/* 317 */               this.mSelectedEntity = null;
/* 318 */               Editable editable = getText();
/* 319 */               if (!TextUtils.isEmpty(editable)) {
/* 320 */                   VineEntity.Range range = this.mEntityRanges.remove(Integer.valueOf(start));
/* 321 */                   int spanStart = editable.getSpanStart(range.span);
/* 322 */                   int spanEnd = editable.getSpanEnd(range.span);
/* 323 */                   editable.removeSpan(range.span);
/* 324 */                   if (usingBackspace) {
/* 325 */                       editable.delete(spanStart, spanEnd);
/* 326 */                       setSelection(spanStart);
                            } else {
/* 328 */                       setSelection(spanStart + 1);
                            }
                        }
/* 331 */               return true;
                    }
/* 333 */           return false;
                }

/* 336 */       public ArrayList<VineEntity> getEntities() {
/* 337 */           Editable text = getText();
/* 338 */           if (text != null && text.length() > 0) {
/* 342 */               while (Character.isWhitespace(text.charAt(0))) {
                            try {
/* 343 */                       text.replace(0, 1, "");
                            } catch (IndexOutOfBoundsException e) {
                            }
                        }
                    }
/* 349 */           ArrayList<VineEntity> entities = new ArrayList<>();
/* 350 */           TreeMap<Integer, VineEntity.Range> ranges = this.mEntityRanges;
/* 352 */           if (text != null) {
/* 353 */               int adjustedRanges = 1;
                        for (int i = 0; i < text.length(); i++) {
/* 355 */                   if (Character.isHighSurrogate(text.charAt(i))) {
/* 356 */                       Map.Entry<Integer, VineEntity.Range> entry = ranges.ceilingEntry(Integer.valueOf(i));
/* 357 */                       while (entry != null) {
/* 358 */                           VineEntity.Range range = entry.getValue();
/* 359 */                           int newStart = text.getSpanStart(range.span) - adjustedRanges;
/* 360 */                           int newEnd = text.getSpanEnd(range.span) - adjustedRanges;
/* 361 */                           range.entity.start = newStart;
/* 362 */                           range.entity.end = newEnd;
/* 364 */                           VineEntity.Range newRange = new VineEntity.Range(newEnd, range.entity, range.span);
/* 366 */                           ranges.remove(entry.getKey());
/* 367 */                           ranges.put(Integer.valueOf(newStart), newRange);
/* 368 */                           entry = ranges.ceilingEntry(Integer.valueOf(range.entity.end));
                                }
/* 370 */                       adjustedRanges++;
                            }
                        }
                    }
/* 374 */           Iterator<VineEntity.Range> it = ranges.values().iterator();
                    while (it.hasNext()) {
/* 375 */               entities.add(it.next().entity);
                    }
/* 377 */           ranges.clear();
/* 378 */           return entities;
                }

                @Override // android.view.View.OnClickListener
/* 381 */       public void onClick(View view) {
/* 382 */           if (isPopupShowing()) {
/* 383 */               this.mPopup.setInputMethodMode(1);
/* 384 */               showDropDown();
                    }
/* 386 */           if (this.mClick != null) {
/* 387 */               this.mClick.onClick(view);
                    }
                }

/* 391 */       public void setPopupEditTextListener(PopupEditTextListener listener) {
/* 392 */           this.mListener = listener;
/* 393 */           this.mHandler.setOnFilterListener(listener);
                }

                @Override // android.widget.AdapterView.OnItemClickListener
/* 396 */       public void onItemClick(AdapterView<?> adapterView, View view, int pos, long userId) {
/* 397 */           clearComposingText();
/* 399 */           if (adapterView.getItemAtPosition(pos) instanceof VineTypeAhead) {
/* 400 */               VineTypeAhead item = (VineTypeAhead) adapterView.getItemAtPosition(pos);
/* 405 */               if (this.mFilterable == null) {
/* 406 */                   CrashUtil.log("Invalid state: mFilterable=null, type={}, token={}", item.type, item.token);
                        } else {
/* 409 */                   Editable editable = getText();
/* 410 */                   int end = getSelectionEnd();
/* 411 */                   int tokenStart = this.mTokenizer.findTokenStart(editable, end);
/* 412 */                   CharSequence replacement = this.mTokenizer.terminateToken(this.mFilterable.getFilter().convertResultToString(item.token));
/* 414 */                   int tokenEnd = tokenStart + item.token.length();
/* 416 */                   VineEntity entity = new VineEntity(item.type, item.token, null, tokenStart, tokenEnd, userId);
/* 419 */                   SpannableStringBuilder ssb = new SpannableStringBuilder(replacement);
/* 420 */                   ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(C0257R.color.vine_green));
/* 422 */                   Util.safeSetSpan(ssb, span, 0, item.token.length(), 0);
/* 423 */                   editable.replace(tokenStart, end, ssb);
/* 424 */                   this.mEntityRanges.put(Integer.valueOf(tokenStart), new VineEntity.Range(tokenEnd, entity, span));
/* 426 */                   if (this.mListener != null) {
/* 427 */                       this.mListener.onPopupItemSelected(tokenStart, end, replacement, userId);
                            }
                        }
/* 430 */               dismissDropDown();
                    }
                }

/* 433 */       public void putEntity(int start, int end, VineEntity e) {
/* 434 */           Editable editable = getText();
/* 435 */           ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(C0257R.color.vine_green));
/* 437 */           SpannableStringBuilder ssb = new SpannableStringBuilder(e.title);
/* 438 */           Util.safeSetSpan(ssb, span, 0, e.end - e.start, 0);
/* 439 */           editable.replace(start, end, ssb);
/* 440 */           this.mEntityRanges.put(Integer.valueOf(e.start), new VineEntity.Range(e.end, e, span));
                }

/* 443 */       private VineEntity.Range lookupRange(int selStart, int selEnd) {
/* 444 */           TreeMap<Integer, VineEntity.Range> ranges = this.mEntityRanges;
/* 446 */           if (ranges != null && !ranges.isEmpty()) {
/* 447 */               Map.Entry<Integer, VineEntity.Range> entry = ranges.floorEntry(Integer.valueOf(selStart));
/* 448 */               if (entry == null) {
/* 449 */                   entry = ranges.floorEntry(Integer.valueOf(selEnd));
                        }
/* 451 */               if (entry != null && (selStart <= entry.getValue().upper || selEnd <= entry.getValue().upper)) {
/* 453 */                   return entry.getValue();
                        }
                    }
                    return null;
                }

/* 459 */       public void setAdapter(ListAdapter adapter) {
/* 460 */           if (this.mAdapter != null) {
/* 461 */               this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
                    } else {
/* 463 */               this.mDataSetObserver = new AdapterObserver();
                    }
/* 465 */           this.mDropDownList.setAdapter(adapter);
/* 466 */           adapter.registerDataSetObserver(this.mDataSetObserver);
/* 467 */           this.mAdapter = adapter;
                }

                @Override // android.widget.TextView, android.view.View
/* 471 */       public boolean onKeyPreIme(int keyCode, @NonNull KeyEvent event) {
/* 472 */           if (keyCode == 4 && isPopupShowing()) {
/* 475 */               if (event.getAction() == 0 && event.getRepeatCount() == 0) {
/* 476 */                   getKeyDispatcherState().startTracking(event, this);
                            return true;
                        }
/* 478 */               if (event.getAction() == 1) {
/* 479 */                   getKeyDispatcherState().handleUpEvent(event);
/* 480 */                   if (event.isTracking() && !event.isCanceled()) {
/* 481 */                       dismissDropDown();
                                return true;
                            }
                        }
                    }
/* 486 */           return super.onKeyPreIme(keyCode, event);
                }

/* 489 */       private boolean isPopupShowing() {
/* 490 */           return this.mPopup.isShowing();
                }

/* 506 */       private static class DropDownListView extends SdkListView {
/* 507 */           public DropDownListView(Context context, AttributeSet attrs, int defStyle) {
/* 508 */               super(context, attrs, defStyle);
                    }

                    @Override // android.view.View
/* 517 */           public boolean hasWindowFocus() {
/* 518 */               return true;
                    }

                    @Override // android.view.View
/* 527 */           public boolean isFocused() {
/* 528 */               return true;
                    }

                    @Override // android.view.ViewGroup, android.view.View
/* 537 */           public boolean hasFocus() {
/* 538 */               return true;
                    }
                }

/* 541 */       private class AdapterObserver extends DataSetObserver {
/* 542 */           public AdapterObserver() {
                    }

                    @Override // android.database.DataSetObserver
/* 547 */           public void onChanged() {
/* 548 */               super.onChanged();
/* 549 */               if (PopupEditText.access$000(PopupEditText.this)) {
/* 550 */                   PopupEditText.this.showDropDown();
/* 551 */                   PopupEditText.access$002(PopupEditText.this, false);
                        }
                    }

                    @Override // android.database.DataSetObserver
/* 556 */           public void onInvalidated() {
/* 557 */               super.onInvalidated();
/* 558 */               PopupEditText.this.dismissDropDown();
                    }
                }

/* 566 */       private static class FilterHandler extends Handler {
                    private Filterable mFilterable;
                    private final WeakReference<Filter.FilterListener> mListenerRef;
                    private PopupEditTextListener mOnFilterListener;

/* 567 */           public FilterHandler(Looper looper, Filter.FilterListener listener) {
/* 568 */               super(looper);
/* 569 */               this.mListenerRef = new WeakReference<>(listener);
                    }

                    @Override // android.os.Handler
/* 573 */           public void handleMessage(Message msg) {
/* 574 */               Filter.FilterListener listener = this.mListenerRef.get();
/* 575 */               if (this.mFilterable != null && listener != null) {
/* 576 */                   CharSequence token = (CharSequence) msg.obj;
/* 577 */                   this.mFilterable.getFilter().filter(token, listener);
/* 578 */                   if (msg.arg1 == 1 && this.mOnFilterListener != null) {
/* 579 */                       this.mOnFilterListener.onFiltering(token);
                            }
                        }
                    }

/* 584 */           public void setFilterable(Filterable filterable) {
/* 585 */               this.mFilterable = filterable;
                    }

/* 588 */           public void setOnFilterListener(PopupEditTextListener listener) {
/* 589 */               this.mOnFilterListener = listener;
                    }
                }

                @Override // android.widget.TextView, android.view.View
/* 606 */       public InputConnection onCreateInputConnection(@NonNull EditorInfo outAttrs) {
/* 607 */           return new PopupInputConnection(super.onCreateInputConnection(outAttrs), true);
                }

/* 610 */       private class PopupInputConnection extends InputConnectionWrapper {
/* 611 */           public PopupInputConnection(InputConnection target, boolean mutable) {
/* 613 */               super(target, mutable);
                    }

                    @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
/* 617 */           public boolean sendKeyEvent(KeyEvent event) {
/* 618 */               if (PopupEditText.access$100(PopupEditText.this) == null || event.getAction() != 0 || event.getKeyCode() != 67) {
                            return super.sendKeyEvent(event);
                        }
/* 620 */               PopupEditText.this.clearSelectedEntity(PopupEditText.access$100(PopupEditText.this).start, true);
                        return true;
                    }

                    @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
/* 628 */           public boolean deleteSurroundingText(int beforeLength, int afterLength) {
                        return (PopupEditText.access$100(PopupEditText.this) == null || beforeLength <= 0 || afterLength != 0) ? super.deleteSurroundingText(beforeLength, afterLength) : sendKeyEvent(new KeyEvent(0, 67));
                    }
                }

                @Override // android.widget.EditText, android.widget.TextView
/* 639 */       public boolean onTextContextMenuItem(int id) {
/* 640 */           if (id == 16908320 && this.mSelectedEntity != null) {
/* 641 */               clearSelectedEntity(this.mSelectedEntity.start, true);
                    }
/* 644 */           return super.onTextContextMenuItem(id);
                }
            }
