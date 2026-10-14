            package android.support.v7.internal.widget;

            import android.content.Context;
            import android.content.Intent;
            import android.content.pm.PackageManager;
            import android.content.pm.ResolveInfo;
            import android.content.res.Resources;
            import android.content.res.TypedArray;
            import android.database.DataSetObserver;
            import android.graphics.drawable.Drawable;
            import android.support.v4.view.ActionProvider;
            import android.support.v7.appcompat.C0084R;
            import android.util.AttributeSet;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.ViewTreeObserver;
            import android.widget.AdapterView;
            import android.widget.BaseAdapter;
            import android.widget.FrameLayout;
            import android.widget.ImageView;
            import android.widget.LinearLayout;
            import android.widget.PopupWindow;
            import android.widget.TextView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 64 */    public class ActivityChooserView extends ViewGroup implements ActivityChooserModel.ActivityChooserModelClient {
                private final LinearLayout mActivityChooserContent;
                private final Drawable mActivityChooserContentBackground;
                private final ActivityChooserViewAdapter mAdapter;
                private final Callbacks mCallbacks;
                private int mDefaultActionButtonContentDescription;
                private final FrameLayout mDefaultActivityButton;
                private final ImageView mDefaultActivityButtonImage;
                private final FrameLayout mExpandActivityOverflowButton;
                private final ImageView mExpandActivityOverflowButtonImage;
                private int mInitialActivityCount;
                private boolean mIsAttachedToWindow;
                private boolean mIsSelectingDefaultActivity;
                private final int mListPopupMaxWidth;
                private ListPopupWindow mListPopupWindow;
                private final DataSetObserver mModelDataSetOberver;
                private PopupWindow.OnDismissListener mOnDismissListener;
                private final ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener;
                ActionProvider mProvider;

/* 65 */        static /* synthetic */ ActivityChooserViewAdapter access$000(ActivityChooserView x0) {
/* 66 */            return x0.mAdapter;
                }

/* 65 */        static /* synthetic */ ListPopupWindow access$100(ActivityChooserView x0) {
/* 66 */            return x0.getListPopupWindow();
                }

/* 65 */        static /* synthetic */ PopupWindow.OnDismissListener access$1000(ActivityChooserView x0) {
/* 66 */            return x0.mOnDismissListener;
                }

/* 65 */        static /* synthetic */ DataSetObserver access$1100(ActivityChooserView x0) {
/* 66 */            return x0.mModelDataSetOberver;
                }

/* 65 */        static /* synthetic */ void access$400(ActivityChooserView x0) {
/* 66 */            x0.updateAppearance();
                }

/* 65 */        static /* synthetic */ void access$500(ActivityChooserView x0, int x1) {
/* 66 */            x0.showPopupUnchecked(x1);
                }

/* 65 */        static /* synthetic */ boolean access$600(ActivityChooserView x0) {
/* 66 */            return x0.mIsSelectingDefaultActivity;
                }

/* 65 */        static /* synthetic */ boolean access$602(ActivityChooserView x0, boolean x1) {
/* 66 */            x0.mIsSelectingDefaultActivity = x1;
                    return x1;
                }

/* 65 */        static /* synthetic */ FrameLayout access$700(ActivityChooserView x0) {
/* 66 */            return x0.mDefaultActivityButton;
                }

/* 65 */        static /* synthetic */ FrameLayout access$800(ActivityChooserView x0) {
/* 66 */            return x0.mExpandActivityOverflowButton;
                }

/* 65 */        static /* synthetic */ int access$900(ActivityChooserView x0) {
/* 66 */            return x0.mInitialActivityCount;
                }

                /* JADX INFO: renamed from: android.support.v7.internal.widget.ActivityChooserView$1 */
/* 120 */       class C00981 extends DataSetObserver {
/* 121 */           C00981() {
                    }

                    @Override // android.database.DataSetObserver
/* 125 */           public void onChanged() {
/* 126 */               super.onChanged();
/* 127 */               ActivityChooserView.access$000(ActivityChooserView.this).notifyDataSetChanged();
                    }

                    @Override // android.database.DataSetObserver
/* 130 */           public void onInvalidated() {
/* 131 */               super.onInvalidated();
/* 132 */               ActivityChooserView.access$000(ActivityChooserView.this).notifyDataSetInvalidated();
                    }
                }

                /* JADX INFO: renamed from: android.support.v7.internal.widget.ActivityChooserView$2 */
/* 134 */       class ViewTreeObserverOnGlobalLayoutListenerC00992 implements ViewTreeObserver.OnGlobalLayoutListener {
/* 135 */           ViewTreeObserverOnGlobalLayoutListenerC00992() {
                    }

                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
/* 138 */           public void onGlobalLayout() {
/* 139 */               if (ActivityChooserView.this.isShowingPopup()) {
/* 140 */                   if (!ActivityChooserView.this.isShown()) {
/* 141 */                       ActivityChooserView.access$100(ActivityChooserView.this).dismiss();
/* 149 */                       return;
                            }
/* 143 */                   ActivityChooserView.access$100(ActivityChooserView.this).show();
/* 144 */                   if (ActivityChooserView.this.mProvider != null) {
/* 145 */                       ActivityChooserView.this.mProvider.subUiVisibilityChanged(true);
                            }
                        }
                    }
                }

/* 187 */       public ActivityChooserView(Context context) {
/* 188 */           this(context, null);
                }

/* 197 */       public ActivityChooserView(Context context, AttributeSet attrs) {
/* 198 */           this(context, attrs, 0);
                }

/* 208 */       public ActivityChooserView(Context context, AttributeSet attrs, int defStyle) {
/* 209 */           super(context, attrs, defStyle);
/* 122 */           this.mModelDataSetOberver = new C00981();
/* 136 */           this.mOnGlobalLayoutListener = new ViewTreeObserverOnGlobalLayoutListenerC00992();
/* 170 */           this.mInitialActivityCount = 4;
/* 211 */           TypedArray attributesArray = context.obtainStyledAttributes(attrs, C0084R.styleable.ActivityChooserView, defStyle, 0);
/* 214 */           this.mInitialActivityCount = attributesArray.getInt(0, 4);
/* 218 */           Drawable expandActivityOverflowButtonDrawable = attributesArray.getDrawable(1);
/* 221 */           attributesArray.recycle();
/* 223 */           LayoutInflater inflater = LayoutInflater.from(getContext());
/* 224 */           inflater.inflate(C0084R.layout.abc_activity_chooser_view, (ViewGroup) this, true);
/* 226 */           this.mCallbacks = new Callbacks(this, null);
/* 228 */           this.mActivityChooserContent = (LinearLayout) findViewById(C0084R.id.activity_chooser_view_content);
/* 229 */           this.mActivityChooserContentBackground = this.mActivityChooserContent.getBackground();
/* 231 */           this.mDefaultActivityButton = (FrameLayout) findViewById(C0084R.id.default_activity_button);
/* 232 */           this.mDefaultActivityButton.setOnClickListener(this.mCallbacks);
/* 233 */           this.mDefaultActivityButton.setOnLongClickListener(this.mCallbacks);
/* 234 */           this.mDefaultActivityButtonImage = (ImageView) this.mDefaultActivityButton.findViewById(C0084R.id.image);
/* 236 */           this.mExpandActivityOverflowButton = (FrameLayout) findViewById(C0084R.id.expand_activities_button);
/* 237 */           this.mExpandActivityOverflowButton.setOnClickListener(this.mCallbacks);
/* 238 */           this.mExpandActivityOverflowButtonImage = (ImageView) this.mExpandActivityOverflowButton.findViewById(C0084R.id.image);
/* 240 */           this.mExpandActivityOverflowButtonImage.setImageDrawable(expandActivityOverflowButtonDrawable);
/* 242 */           this.mAdapter = new ActivityChooserViewAdapter(this, null);
/* 243 */           this.mAdapter.registerDataSetObserver(new C01003());
/* 251 */           Resources resources = context.getResources();
/* 252 */           this.mListPopupMaxWidth = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(C0084R.dimen.abc_config_prefDialogWidth));
                }

                /* JADX INFO: renamed from: android.support.v7.internal.widget.ActivityChooserView$3 */
/* 241 */       class C01003 extends DataSetObserver {
/* 242 */           C01003() {
                    }

                    @Override // android.database.DataSetObserver
/* 245 */           public void onChanged() {
/* 246 */               super.onChanged();
/* 247 */               ActivityChooserView.access$400(ActivityChooserView.this);
                    }
                }

                @Override // android.support.v7.internal.widget.ActivityChooserModel.ActivityChooserModelClient
/* 259 */       public void setActivityChooserModel(ActivityChooserModel dataModel) {
/* 260 */           this.mAdapter.setDataModel(dataModel);
/* 261 */           if (isShowingPopup()) {
/* 262 */               dismissPopup();
/* 263 */               showPopup();
                    }
                }

/* 278 */       public void setExpandActivityOverflowButtonDrawable(Drawable drawable) {
/* 279 */           this.mExpandActivityOverflowButtonImage.setImageDrawable(drawable);
                }

/* 292 */       public void setExpandActivityOverflowButtonContentDescription(int resourceId) {
/* 293 */           CharSequence contentDescription = getContext().getString(resourceId);
/* 294 */           this.mExpandActivityOverflowButtonImage.setContentDescription(contentDescription);
                }

/* 301 */       public void setProvider(ActionProvider provider) {
/* 302 */           this.mProvider = provider;
                }

/* 310 */       public boolean showPopup() {
/* 311 */           if (isShowingPopup() || !this.mIsAttachedToWindow) {
                        return false;
                    }
/* 314 */           this.mIsSelectingDefaultActivity = false;
/* 315 */           showPopupUnchecked(this.mInitialActivityCount);
/* 316 */           return true;
                }

/* 324 */       private void showPopupUnchecked(int maxActivityCount) {
/* 325 */           if (this.mAdapter.getDataModel() == null) {
/* 326 */               throw new IllegalStateException("No data model. Did you call #setDataModel?");
                    }
/* 329 */           getViewTreeObserver().addOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
/* 331 */           boolean defaultActivityButtonShown = this.mDefaultActivityButton.getVisibility() == 0;
/* 334 */           int activityCount = this.mAdapter.getActivityCount();
/* 335 */           int maxActivityCountOffset = defaultActivityButtonShown ? 1 : 0;
/* 336 */           if (maxActivityCount != Integer.MAX_VALUE && activityCount > maxActivityCount + maxActivityCountOffset) {
/* 338 */               this.mAdapter.setShowFooterView(true);
/* 339 */               this.mAdapter.setMaxActivityCount(maxActivityCount - 1);
                    } else {
/* 341 */               this.mAdapter.setShowFooterView(false);
/* 342 */               this.mAdapter.setMaxActivityCount(maxActivityCount);
                    }
/* 345 */           ListPopupWindow popupWindow = getListPopupWindow();
/* 346 */           if (!popupWindow.isShowing()) {
/* 347 */               if (this.mIsSelectingDefaultActivity || !defaultActivityButtonShown) {
/* 348 */                   this.mAdapter.setShowDefaultActivity(true, defaultActivityButtonShown);
                        } else {
/* 350 */                   this.mAdapter.setShowDefaultActivity(false, false);
                        }
/* 352 */               int contentWidth = Math.min(this.mAdapter.measureContentWidth(), this.mListPopupMaxWidth);
/* 353 */               popupWindow.setContentWidth(contentWidth);
/* 354 */               popupWindow.show();
/* 355 */               if (this.mProvider != null) {
/* 356 */                   this.mProvider.subUiVisibilityChanged(true);
                        }
/* 358 */               popupWindow.getListView().setContentDescription(getContext().getString(C0084R.string.abc_activitychooserview_choose_application));
                    }
                }

/* 368 */       public boolean dismissPopup() {
/* 369 */           if (isShowingPopup()) {
/* 370 */               getListPopupWindow().dismiss();
/* 371 */               ViewTreeObserver viewTreeObserver = getViewTreeObserver();
/* 372 */               if (viewTreeObserver.isAlive()) {
/* 373 */                   viewTreeObserver.removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener);
/* 376 */                   return true;
                        }
/* 376 */               return true;
                    }
/* 376 */           return true;
                }

/* 384 */       public boolean isShowingPopup() {
/* 385 */           return getListPopupWindow().isShowing();
                }

                @Override // android.view.ViewGroup, android.view.View
/* 389 */       protected void onAttachedToWindow() {
/* 390 */           super.onAttachedToWindow();
/* 391 */           ActivityChooserModel dataModel = this.mAdapter.getDataModel();
/* 392 */           if (dataModel != null) {
/* 393 */               dataModel.registerObserver(this.mModelDataSetOberver);
                    }
/* 395 */           this.mIsAttachedToWindow = true;
                }

                @Override // android.view.ViewGroup, android.view.View
/* 399 */       protected void onDetachedFromWindow() {
/* 400 */           super.onDetachedFromWindow();
/* 401 */           ActivityChooserModel dataModel = this.mAdapter.getDataModel();
/* 402 */           if (dataModel != null) {
/* 403 */               dataModel.unregisterObserver(this.mModelDataSetOberver);
                    }
/* 405 */           ViewTreeObserver viewTreeObserver = getViewTreeObserver();
/* 406 */           if (viewTreeObserver.isAlive()) {
/* 407 */               viewTreeObserver.removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener);
                    }
/* 409 */           if (isShowingPopup()) {
/* 410 */               dismissPopup();
                    }
/* 412 */           this.mIsAttachedToWindow = false;
                }

                @Override // android.view.View
/* 416 */       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 417 */           View child = this.mActivityChooserContent;
/* 421 */           if (this.mDefaultActivityButton.getVisibility() != 0) {
/* 422 */               heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(heightMeasureSpec), 1073741824);
                    }
/* 425 */           measureChild(child, widthMeasureSpec, heightMeasureSpec);
/* 426 */           setMeasuredDimension(child.getMeasuredWidth(), child.getMeasuredHeight());
                }

                @Override // android.view.ViewGroup, android.view.View
/* 430 */       protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
/* 431 */           this.mActivityChooserContent.layout(0, 0, right - left, bottom - top);
/* 432 */           if (!isShowingPopup()) {
/* 433 */               dismissPopup();
                    }
                }

/* 437 */       public ActivityChooserModel getDataModel() {
/* 438 */           return this.mAdapter.getDataModel();
                }

/* 446 */       public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
/* 447 */           this.mOnDismissListener = listener;
                }

/* 458 */       public void setInitialActivityCount(int itemCount) {
/* 459 */           this.mInitialActivityCount = itemCount;
                }

/* 472 */       public void setDefaultActionButtonContentDescription(int resourceId) {
/* 473 */           this.mDefaultActionButtonContentDescription = resourceId;
                }

/* 481 */       private ListPopupWindow getListPopupWindow() {
/* 482 */           if (this.mListPopupWindow == null) {
/* 483 */               this.mListPopupWindow = new ListPopupWindow(getContext());
/* 484 */               this.mListPopupWindow.setAdapter(this.mAdapter);
/* 485 */               this.mListPopupWindow.setAnchorView(this);
/* 486 */               this.mListPopupWindow.setModal(true);
/* 487 */               this.mListPopupWindow.setOnItemClickListener(this.mCallbacks);
/* 488 */               this.mListPopupWindow.setOnDismissListener(this.mCallbacks);
                    }
/* 490 */           return this.mListPopupWindow;
                }

/* 497 */       private void updateAppearance() {
/* 498 */           if (this.mAdapter.getCount() > 0) {
/* 499 */               this.mExpandActivityOverflowButton.setEnabled(true);
                    } else {
/* 501 */               this.mExpandActivityOverflowButton.setEnabled(false);
                    }
/* 504 */           int activityCount = this.mAdapter.getActivityCount();
/* 505 */           int historySize = this.mAdapter.getHistorySize();
/* 506 */           if (activityCount == 1 || (activityCount > 1 && historySize > 0)) {
/* 507 */               this.mDefaultActivityButton.setVisibility(0);
/* 508 */               ResolveInfo activity = this.mAdapter.getDefaultActivity();
/* 509 */               PackageManager packageManager = getContext().getPackageManager();
/* 510 */               this.mDefaultActivityButtonImage.setImageDrawable(activity.loadIcon(packageManager));
/* 511 */               if (this.mDefaultActionButtonContentDescription != 0) {
/* 512 */                   CharSequence label = activity.loadLabel(packageManager);
/* 513 */                   String contentDescription = getContext().getString(this.mDefaultActionButtonContentDescription, label);
/* 515 */                   this.mDefaultActivityButton.setContentDescription(contentDescription);
                        }
                    } else {
/* 518 */               this.mDefaultActivityButton.setVisibility(8);
                    }
/* 521 */           if (this.mDefaultActivityButton.getVisibility() == 0) {
/* 522 */               this.mActivityChooserContent.setBackgroundDrawable(this.mActivityChooserContentBackground);
                    } else {
/* 524 */               this.mActivityChooserContent.setBackgroundDrawable(null);
                    }
                }

/* 529 */       private class Callbacks implements AdapterView.OnItemClickListener, View.OnClickListener, View.OnLongClickListener, PopupWindow.OnDismissListener {
/* 530 */           private Callbacks() {
                    }

/* 530 */           /* synthetic */ Callbacks(ActivityChooserView x0, C00981 x1) {
/* 531 */               this();
                    }

                    @Override // android.widget.AdapterView.OnItemClickListener
/* 535 */           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/* 536 */               ActivityChooserViewAdapter adapter = (ActivityChooserViewAdapter) parent.getAdapter();
/* 537 */               int itemViewType = adapter.getItemViewType(position);
                        switch (itemViewType) {
                            case 0:
/* 543 */                       ActivityChooserView.this.dismissPopup();
/* 544 */                       if (ActivityChooserView.access$600(ActivityChooserView.this)) {
/* 546 */                           if (position > 0) {
/* 547 */                               ActivityChooserView.access$000(ActivityChooserView.this).getDataModel().setDefaultActivity(position);
/* 563 */                               return;
                                    }
/* 563 */                           return;
                                }
/* 552 */                       if (!ActivityChooserView.access$000(ActivityChooserView.this).getShowDefaultActivity()) {
/* 552 */                           position++;
                                }
/* 553 */                       Intent launchIntent = ActivityChooserView.access$000(ActivityChooserView.this).getDataModel().chooseActivity(position);
/* 554 */                       if (launchIntent != null) {
/* 555 */                           launchIntent.addFlags(524288);
/* 556 */                           ActivityChooserView.this.getContext().startActivity(launchIntent);
/* 563 */                           return;
                                }
/* 563 */                       return;
                            case 1:
/* 540 */                       ActivityChooserView.access$500(ActivityChooserView.this, Integer.MAX_VALUE);
/* 563 */                       return;
                            default:
/* 561 */                       throw new IllegalArgumentException();
                        }
                    }

                    @Override // android.view.View.OnClickListener
/* 566 */           public void onClick(View view) {
/* 567 */               if (view == ActivityChooserView.access$700(ActivityChooserView.this)) {
/* 568 */                   ActivityChooserView.this.dismissPopup();
/* 569 */                   ResolveInfo defaultActivity = ActivityChooserView.access$000(ActivityChooserView.this).getDefaultActivity();
/* 570 */                   int index = ActivityChooserView.access$000(ActivityChooserView.this).getDataModel().getActivityIndex(defaultActivity);
/* 571 */                   Intent launchIntent = ActivityChooserView.access$000(ActivityChooserView.this).getDataModel().chooseActivity(index);
/* 572 */                   if (launchIntent != null) {
/* 573 */                       launchIntent.addFlags(524288);
/* 574 */                       ActivityChooserView.this.getContext().startActivity(launchIntent);
/* 582 */                       return;
                            }
/* 582 */                   return;
                        }
/* 576 */               if (view == ActivityChooserView.access$800(ActivityChooserView.this)) {
/* 577 */                   ActivityChooserView.access$602(ActivityChooserView.this, false);
/* 578 */                   ActivityChooserView.access$500(ActivityChooserView.this, ActivityChooserView.access$900(ActivityChooserView.this));
/* 582 */                   return;
                        }
/* 580 */               throw new IllegalArgumentException();
                    }

                    @Override // android.view.View.OnLongClickListener
/* 586 */           public boolean onLongClick(View view) {
/* 587 */               if (view == ActivityChooserView.access$700(ActivityChooserView.this)) {
/* 588 */                   if (ActivityChooserView.access$000(ActivityChooserView.this).getCount() > 0) {
/* 589 */                       ActivityChooserView.access$602(ActivityChooserView.this, true);
/* 590 */                       ActivityChooserView.access$500(ActivityChooserView.this, ActivityChooserView.access$900(ActivityChooserView.this));
                            }
                            return true;
                        }
/* 593 */               throw new IllegalArgumentException();
                    }

                    @Override // android.widget.PopupWindow.OnDismissListener
/* 599 */           public void onDismiss() {
/* 600 */               notifyOnDismissListener();
/* 601 */               if (ActivityChooserView.this.mProvider != null) {
/* 602 */                   ActivityChooserView.this.mProvider.subUiVisibilityChanged(false);
                        }
                    }

/* 606 */           private void notifyOnDismissListener() {
/* 607 */               if (ActivityChooserView.access$1000(ActivityChooserView.this) != null) {
/* 608 */                   ActivityChooserView.access$1000(ActivityChooserView.this).onDismiss();
                        }
                    }
                }

/* 614 */       private class ActivityChooserViewAdapter extends BaseAdapter {
                    private static final int ITEM_VIEW_TYPE_ACTIVITY = 0;
                    private static final int ITEM_VIEW_TYPE_COUNT = 3;
                    private static final int ITEM_VIEW_TYPE_FOOTER = 1;
                    public static final int MAX_ACTIVITY_COUNT_DEFAULT = 4;
                    public static final int MAX_ACTIVITY_COUNT_UNLIMITED = Integer.MAX_VALUE;
                    private ActivityChooserModel mDataModel;
                    private boolean mHighlightDefaultActivity;
                    private int mMaxActivityCount;
                    private boolean mShowDefaultActivity;
                    private boolean mShowFooterView;

/* 615 */           private ActivityChooserViewAdapter() {
/* 630 */               this.mMaxActivityCount = 4;
                    }

/* 615 */           /* synthetic */ ActivityChooserViewAdapter(ActivityChooserView x0, C00981 x1) {
/* 616 */               this();
                    }

/* 638 */           public void setDataModel(ActivityChooserModel dataModel) {
/* 639 */               ActivityChooserModel oldDataModel = ActivityChooserView.access$000(ActivityChooserView.this).getDataModel();
/* 640 */               if (oldDataModel != null && ActivityChooserView.this.isShown()) {
/* 641 */                   oldDataModel.unregisterObserver(ActivityChooserView.access$1100(ActivityChooserView.this));
                        }
/* 643 */               this.mDataModel = dataModel;
/* 644 */               if (dataModel != null && ActivityChooserView.this.isShown()) {
/* 645 */                   dataModel.registerObserver(ActivityChooserView.access$1100(ActivityChooserView.this));
                        }
/* 647 */               notifyDataSetChanged();
                    }

                    @Override // android.widget.BaseAdapter, android.widget.Adapter
/* 651 */           public int getItemViewType(int position) {
                        return (this.mShowFooterView && position == getCount() + (-1)) ? 1 : 0;
                    }

                    @Override // android.widget.BaseAdapter, android.widget.Adapter
/* 660 */           public int getViewTypeCount() {
/* 661 */               return 3;
                    }

                    @Override // android.widget.Adapter
/* 664 */           public int getCount() {
/* 666 */               int activityCount = this.mDataModel.getActivityCount();
/* 667 */               if (!this.mShowDefaultActivity && this.mDataModel.getDefaultActivity() != null) {
/* 668 */                   activityCount--;
                        }
/* 670 */               int count = Math.min(activityCount, this.mMaxActivityCount);
/* 671 */               if (this.mShowFooterView) {
/* 672 */                   return count + 1;
                        }
/* 674 */               return count;
                    }

                    @Override // android.widget.Adapter
/* 677 */           public Object getItem(int position) {
/* 678 */               int itemViewType = getItemViewType(position);
                        switch (itemViewType) {
                            case 0:
/* 683 */                       if (!this.mShowDefaultActivity && this.mDataModel.getDefaultActivity() != null) {
/* 684 */                           position++;
                                }
/* 686 */                       return this.mDataModel.getActivity(position);
                            case 1:
/* 681 */                       return null;
                            default:
/* 688 */                       throw new IllegalArgumentException();
                        }
                    }

                    @Override // android.widget.Adapter
/* 692 */           public long getItemId(int position) {
/* 693 */               return position;
                    }

                    @Override // android.widget.Adapter
/* 696 */           public View getView(int position, View convertView, ViewGroup parent) {
/* 697 */               int itemViewType = getItemViewType(position);
                        switch (itemViewType) {
                            case 0:
/* 710 */                       if (convertView == null || convertView.getId() != C0084R.id.list_item) {
/* 711 */                           convertView = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(C0084R.layout.abc_activity_chooser_view_list_item, parent, false);
                                }
/* 714 */                       PackageManager packageManager = ActivityChooserView.this.getContext().getPackageManager();
/* 716 */                       ImageView iconView = (ImageView) convertView.findViewById(C0084R.id.icon);
/* 717 */                       ResolveInfo activity = (ResolveInfo) getItem(position);
/* 718 */                       iconView.setImageDrawable(activity.loadIcon(packageManager));
/* 720 */                       TextView titleView = (TextView) convertView.findViewById(C0084R.id.title);
/* 721 */                       titleView.setText(activity.loadLabel(packageManager));
/* 723 */                       if (!this.mShowDefaultActivity || position != 0 || this.mHighlightDefaultActivity) {
                                }
                                return convertView;
                            case 1:
/* 700 */                       if (convertView == null || convertView.getId() != 1) {
/* 701 */                           convertView = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(C0084R.layout.abc_activity_chooser_view_list_item, parent, false);
/* 703 */                           convertView.setId(1);
/* 704 */                           TextView titleView2 = (TextView) convertView.findViewById(C0084R.id.title);
/* 705 */                           titleView2.setText(ActivityChooserView.this.getContext().getString(C0084R.string.abc_activity_chooser_view_see_all));
                                }
                                return convertView;
                            default:
/* 730 */                       throw new IllegalArgumentException();
                        }
                    }

/* 736 */           public int measureContentWidth() {
/* 737 */               int oldMaxActivityCount = this.mMaxActivityCount;
/* 738 */               this.mMaxActivityCount = Integer.MAX_VALUE;
/* 740 */               int contentWidth = 0;
/* 741 */               View itemView = null;
/* 743 */               int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
/* 744 */               int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
/* 745 */               int count = getCount();
                        for (int i = 0; i < count; i++) {
/* 748 */                   itemView = getView(i, itemView, null);
/* 749 */                   itemView.measure(widthMeasureSpec, heightMeasureSpec);
/* 750 */                   contentWidth = Math.max(contentWidth, itemView.getMeasuredWidth());
                        }
/* 753 */               this.mMaxActivityCount = oldMaxActivityCount;
/* 755 */               return contentWidth;
                    }

/* 758 */           public void setMaxActivityCount(int maxActivityCount) {
/* 759 */               if (this.mMaxActivityCount != maxActivityCount) {
/* 760 */                   this.mMaxActivityCount = maxActivityCount;
/* 761 */                   notifyDataSetChanged();
                        }
                    }

/* 765 */           public ResolveInfo getDefaultActivity() {
/* 766 */               return this.mDataModel.getDefaultActivity();
                    }

/* 769 */           public void setShowFooterView(boolean showFooterView) {
/* 770 */               if (this.mShowFooterView != showFooterView) {
/* 771 */                   this.mShowFooterView = showFooterView;
/* 772 */                   notifyDataSetChanged();
                        }
                    }

/* 776 */           public int getActivityCount() {
/* 777 */               return this.mDataModel.getActivityCount();
                    }

/* 780 */           public int getHistorySize() {
/* 781 */               return this.mDataModel.getHistorySize();
                    }

/* 784 */           public int getMaxActivityCount() {
/* 785 */               return this.mMaxActivityCount;
                    }

/* 788 */           public ActivityChooserModel getDataModel() {
/* 789 */               return this.mDataModel;
                    }

/* 793 */           public void setShowDefaultActivity(boolean showDefaultActivity, boolean highlightDefaultActivity) {
/* 794 */               if (this.mShowDefaultActivity != showDefaultActivity || this.mHighlightDefaultActivity != highlightDefaultActivity) {
/* 796 */                   this.mShowDefaultActivity = showDefaultActivity;
/* 797 */                   this.mHighlightDefaultActivity = highlightDefaultActivity;
/* 798 */                   notifyDataSetChanged();
                        }
                    }

/* 802 */           public boolean getShowDefaultActivity() {
/* 803 */               return this.mShowDefaultActivity;
                    }
                }
            }
