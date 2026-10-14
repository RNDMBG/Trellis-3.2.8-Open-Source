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
            
            
    public class ActivityChooserView extends ViewGroup implements ActivityChooserModel.ActivityChooserModelClient {
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

        static  ActivityChooserViewAdapter access$000(ActivityChooserView x0) {
            return x0.mAdapter;
                }

        static  ListPopupWindow access$100(ActivityChooserView x0) {
            return x0.getListPopupWindow();
                }

        static  PopupWindow.OnDismissListener access$1000(ActivityChooserView x0) {
            return x0.mOnDismissListener;
                }

        static  DataSetObserver access$1100(ActivityChooserView x0) {
            return x0.mModelDataSetOberver;
                }

        static  void access$400(ActivityChooserView x0) {
            x0.updateAppearance();
                }

        static  void access$500(ActivityChooserView x0, int x1) {
            x0.showPopupUnchecked(x1);
                }

        static  boolean access$600(ActivityChooserView x0) {
            return x0.mIsSelectingDefaultActivity;
                }

        static  boolean access$602(ActivityChooserView x0, boolean x1) {
            x0.mIsSelectingDefaultActivity = x1;
                    return x1;
                }

        static  FrameLayout access$700(ActivityChooserView x0) {
            return x0.mDefaultActivityButton;
                }

        static  FrameLayout access$800(ActivityChooserView x0) {
            return x0.mExpandActivityOverflowButton;
                }

        static  int access$900(ActivityChooserView x0) {
            return x0.mInitialActivityCount;
                }

                
       class C00981 extends DataSetObserver {
           C00981() {
                    }

                    @Override 
           public void onChanged() {
               super.onChanged();
               ActivityChooserView.access$000(ActivityChooserView.this).notifyDataSetChanged();
                    }

                    @Override 
           public void onInvalidated() {
               super.onInvalidated();
               ActivityChooserView.access$000(ActivityChooserView.this).notifyDataSetInvalidated();
                    }
                }

                
       class ViewTreeObserverOnGlobalLayoutListenerC00992 implements ViewTreeObserver.OnGlobalLayoutListener {
           ViewTreeObserverOnGlobalLayoutListenerC00992() {
                    }

                    @Override 
           public void onGlobalLayout() {
               if (ActivityChooserView.this.isShowingPopup()) {
                   if (!ActivityChooserView.this.isShown()) {
                       ActivityChooserView.access$100(ActivityChooserView.this).dismiss();
                       return;
                            }
                   ActivityChooserView.access$100(ActivityChooserView.this).show();
                   if (ActivityChooserView.this.mProvider != null) {
                       ActivityChooserView.this.mProvider.subUiVisibilityChanged(true);
                            }
                        }
                    }
                }

       public ActivityChooserView(Context context) {
           this(context, null);
                }

       public ActivityChooserView(Context context, AttributeSet attrs) {
           this(context, attrs, 0);
                }

       public ActivityChooserView(Context context, AttributeSet attrs, int defStyle) {
           super(context, attrs, defStyle);
           this.mModelDataSetOberver = new C00981();
           this.mOnGlobalLayoutListener = new ViewTreeObserverOnGlobalLayoutListenerC00992();
           this.mInitialActivityCount = 4;
           TypedArray attributesArray = context.obtainStyledAttributes(attrs, C0084R.styleable.ActivityChooserView, defStyle, 0);
           this.mInitialActivityCount = attributesArray.getInt(0, 4);
           Drawable expandActivityOverflowButtonDrawable = attributesArray.getDrawable(1);
           attributesArray.recycle();
           LayoutInflater inflater = LayoutInflater.from(getContext());
           inflater.inflate(C0084R.layout.abc_activity_chooser_view, (ViewGroup) this, true);
           this.mCallbacks = new Callbacks(this, null);
           this.mActivityChooserContent = (LinearLayout) findViewById(C0084R.id.activity_chooser_view_content);
           this.mActivityChooserContentBackground = this.mActivityChooserContent.getBackground();
           this.mDefaultActivityButton = (FrameLayout) findViewById(C0084R.id.default_activity_button);
           this.mDefaultActivityButton.setOnClickListener(this.mCallbacks);
           this.mDefaultActivityButton.setOnLongClickListener(this.mCallbacks);
           this.mDefaultActivityButtonImage = (ImageView) this.mDefaultActivityButton.findViewById(C0084R.id.image);
           this.mExpandActivityOverflowButton = (FrameLayout) findViewById(C0084R.id.expand_activities_button);
           this.mExpandActivityOverflowButton.setOnClickListener(this.mCallbacks);
           this.mExpandActivityOverflowButtonImage = (ImageView) this.mExpandActivityOverflowButton.findViewById(C0084R.id.image);
           this.mExpandActivityOverflowButtonImage.setImageDrawable(expandActivityOverflowButtonDrawable);
           this.mAdapter = new ActivityChooserViewAdapter(this, null);
           this.mAdapter.registerDataSetObserver(new C01003());
           Resources resources = context.getResources();
           this.mListPopupMaxWidth = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(C0084R.dimen.abc_config_prefDialogWidth));
                }

                
       class C01003 extends DataSetObserver {
           C01003() {
                    }

                    @Override 
           public void onChanged() {
               super.onChanged();
               ActivityChooserView.access$400(ActivityChooserView.this);
                    }
                }

                @Override 
       public void setActivityChooserModel(ActivityChooserModel dataModel) {
           this.mAdapter.setDataModel(dataModel);
           if (isShowingPopup()) {
               dismissPopup();
               showPopup();
                    }
                }

       public void setExpandActivityOverflowButtonDrawable(Drawable drawable) {
           this.mExpandActivityOverflowButtonImage.setImageDrawable(drawable);
                }

       public void setExpandActivityOverflowButtonContentDescription(int resourceId) {
           CharSequence contentDescription = getContext().getString(resourceId);
           this.mExpandActivityOverflowButtonImage.setContentDescription(contentDescription);
                }

       public void setProvider(ActionProvider provider) {
           this.mProvider = provider;
                }

       public boolean showPopup() {
           if (isShowingPopup() || !this.mIsAttachedToWindow) {
                        return false;
                    }
           this.mIsSelectingDefaultActivity = false;
           showPopupUnchecked(this.mInitialActivityCount);
           return true;
                }

       private void showPopupUnchecked(int maxActivityCount) {
           if (this.mAdapter.getDataModel() == null) {
               throw new IllegalStateException("No data model. Did you call #setDataModel?");
                    }
           getViewTreeObserver().addOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
           boolean defaultActivityButtonShown = this.mDefaultActivityButton.getVisibility() == 0;
           int activityCount = this.mAdapter.getActivityCount();
           int maxActivityCountOffset = defaultActivityButtonShown ? 1 : 0;
           if (maxActivityCount != Integer.MAX_VALUE && activityCount > maxActivityCount + maxActivityCountOffset) {
               this.mAdapter.setShowFooterView(true);
               this.mAdapter.setMaxActivityCount(maxActivityCount - 1);
                    } else {
               this.mAdapter.setShowFooterView(false);
               this.mAdapter.setMaxActivityCount(maxActivityCount);
                    }
           ListPopupWindow popupWindow = getListPopupWindow();
           if (!popupWindow.isShowing()) {
               if (this.mIsSelectingDefaultActivity || !defaultActivityButtonShown) {
                   this.mAdapter.setShowDefaultActivity(true, defaultActivityButtonShown);
                        } else {
                   this.mAdapter.setShowDefaultActivity(false, false);
                        }
               int contentWidth = Math.min(this.mAdapter.measureContentWidth(), this.mListPopupMaxWidth);
               popupWindow.setContentWidth(contentWidth);
               popupWindow.show();
               if (this.mProvider != null) {
                   this.mProvider.subUiVisibilityChanged(true);
                        }
               popupWindow.getListView().setContentDescription(getContext().getString(C0084R.string.abc_activitychooserview_choose_application));
                    }
                }

       public boolean dismissPopup() {
           if (isShowingPopup()) {
               getListPopupWindow().dismiss();
               ViewTreeObserver viewTreeObserver = getViewTreeObserver();
               if (viewTreeObserver.isAlive()) {
                   viewTreeObserver.removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener);
                   return true;
                        }
               return true;
                    }
           return true;
                }

       public boolean isShowingPopup() {
           return getListPopupWindow().isShowing();
                }

                @Override 
       protected void onAttachedToWindow() {
           super.onAttachedToWindow();
           ActivityChooserModel dataModel = this.mAdapter.getDataModel();
           if (dataModel != null) {
               dataModel.registerObserver(this.mModelDataSetOberver);
                    }
           this.mIsAttachedToWindow = true;
                }

                @Override 
       protected void onDetachedFromWindow() {
           super.onDetachedFromWindow();
           ActivityChooserModel dataModel = this.mAdapter.getDataModel();
           if (dataModel != null) {
               dataModel.unregisterObserver(this.mModelDataSetOberver);
                    }
           ViewTreeObserver viewTreeObserver = getViewTreeObserver();
           if (viewTreeObserver.isAlive()) {
               viewTreeObserver.removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener);
                    }
           if (isShowingPopup()) {
               dismissPopup();
                    }
           this.mIsAttachedToWindow = false;
                }

                @Override 
       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
           View child = this.mActivityChooserContent;
           if (this.mDefaultActivityButton.getVisibility() != 0) {
               heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(heightMeasureSpec), 1073741824);
                    }
           measureChild(child, widthMeasureSpec, heightMeasureSpec);
           setMeasuredDimension(child.getMeasuredWidth(), child.getMeasuredHeight());
                }

                @Override 
       protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
           this.mActivityChooserContent.layout(0, 0, right - left, bottom - top);
           if (!isShowingPopup()) {
               dismissPopup();
                    }
                }

       public ActivityChooserModel getDataModel() {
           return this.mAdapter.getDataModel();
                }

       public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
           this.mOnDismissListener = listener;
                }

       public void setInitialActivityCount(int itemCount) {
           this.mInitialActivityCount = itemCount;
                }

       public void setDefaultActionButtonContentDescription(int resourceId) {
           this.mDefaultActionButtonContentDescription = resourceId;
                }

       private ListPopupWindow getListPopupWindow() {
           if (this.mListPopupWindow == null) {
               this.mListPopupWindow = new ListPopupWindow(getContext());
               this.mListPopupWindow.setAdapter(this.mAdapter);
               this.mListPopupWindow.setAnchorView(this);
               this.mListPopupWindow.setModal(true);
               this.mListPopupWindow.setOnItemClickListener(this.mCallbacks);
               this.mListPopupWindow.setOnDismissListener(this.mCallbacks);
                    }
           return this.mListPopupWindow;
                }

       private void updateAppearance() {
           if (this.mAdapter.getCount() > 0) {
               this.mExpandActivityOverflowButton.setEnabled(true);
                    } else {
               this.mExpandActivityOverflowButton.setEnabled(false);
                    }
           int activityCount = this.mAdapter.getActivityCount();
           int historySize = this.mAdapter.getHistorySize();
           if (activityCount == 1 || (activityCount > 1 && historySize > 0)) {
               this.mDefaultActivityButton.setVisibility(0);
               ResolveInfo activity = this.mAdapter.getDefaultActivity();
               PackageManager packageManager = getContext().getPackageManager();
               this.mDefaultActivityButtonImage.setImageDrawable(activity.loadIcon(packageManager));
               if (this.mDefaultActionButtonContentDescription != 0) {
                   CharSequence label = activity.loadLabel(packageManager);
                   String contentDescription = getContext().getString(this.mDefaultActionButtonContentDescription, label);
                   this.mDefaultActivityButton.setContentDescription(contentDescription);
                        }
                    } else {
               this.mDefaultActivityButton.setVisibility(8);
                    }
           if (this.mDefaultActivityButton.getVisibility() == 0) {
               this.mActivityChooserContent.setBackgroundDrawable(this.mActivityChooserContentBackground);
                    } else {
               this.mActivityChooserContent.setBackgroundDrawable(null);
                    }
                }

       private class Callbacks implements AdapterView.OnItemClickListener, View.OnClickListener, View.OnLongClickListener, PopupWindow.OnDismissListener {
           private Callbacks() {
                    }

            Callbacks(ActivityChooserView x0, C00981 x1) {
               this();
                    }

                    @Override 
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               ActivityChooserViewAdapter adapter = (ActivityChooserViewAdapter) parent.getAdapter();
               int itemViewType = adapter.getItemViewType(position);
                        switch (itemViewType) {
                            case 0:
                       ActivityChooserView.this.dismissPopup();
                       if (ActivityChooserView.access$600(ActivityChooserView.this)) {
                           if (position > 0) {
                               ActivityChooserView.access$000(ActivityChooserView.this).getDataModel().setDefaultActivity(position);
                               return;
                                    }
                           return;
                                }
                       if (!ActivityChooserView.access$000(ActivityChooserView.this).getShowDefaultActivity()) {
                           position++;
                                }
                       Intent launchIntent = ActivityChooserView.access$000(ActivityChooserView.this).getDataModel().chooseActivity(position);
                       if (launchIntent != null) {
                           launchIntent.addFlags(524288);
                           ActivityChooserView.this.getContext().startActivity(launchIntent);
                           return;
                                }
                       return;
                            case 1:
                       ActivityChooserView.access$500(ActivityChooserView.this, Integer.MAX_VALUE);
                       return;
                            default:
                       throw new IllegalArgumentException();
                        }
                    }

                    @Override 
           public void onClick(View view) {
               if (view == ActivityChooserView.access$700(ActivityChooserView.this)) {
                   ActivityChooserView.this.dismissPopup();
                   ResolveInfo defaultActivity = ActivityChooserView.access$000(ActivityChooserView.this).getDefaultActivity();
                   int index = ActivityChooserView.access$000(ActivityChooserView.this).getDataModel().getActivityIndex(defaultActivity);
                   Intent launchIntent = ActivityChooserView.access$000(ActivityChooserView.this).getDataModel().chooseActivity(index);
                   if (launchIntent != null) {
                       launchIntent.addFlags(524288);
                       ActivityChooserView.this.getContext().startActivity(launchIntent);
                       return;
                            }
                   return;
                        }
               if (view == ActivityChooserView.access$800(ActivityChooserView.this)) {
                   ActivityChooserView.access$602(ActivityChooserView.this, false);
                   ActivityChooserView.access$500(ActivityChooserView.this, ActivityChooserView.access$900(ActivityChooserView.this));
                   return;
                        }
               throw new IllegalArgumentException();
                    }

                    @Override 
           public boolean onLongClick(View view) {
               if (view == ActivityChooserView.access$700(ActivityChooserView.this)) {
                   if (ActivityChooserView.access$000(ActivityChooserView.this).getCount() > 0) {
                       ActivityChooserView.access$602(ActivityChooserView.this, true);
                       ActivityChooserView.access$500(ActivityChooserView.this, ActivityChooserView.access$900(ActivityChooserView.this));
                            }
                            return true;
                        }
               throw new IllegalArgumentException();
                    }

                    @Override 
           public void onDismiss() {
               notifyOnDismissListener();
               if (ActivityChooserView.this.mProvider != null) {
                   ActivityChooserView.this.mProvider.subUiVisibilityChanged(false);
                        }
                    }

           private void notifyOnDismissListener() {
               if (ActivityChooserView.access$1000(ActivityChooserView.this) != null) {
                   ActivityChooserView.access$1000(ActivityChooserView.this).onDismiss();
                        }
                    }
                }

       private class ActivityChooserViewAdapter extends BaseAdapter {
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

           private ActivityChooserViewAdapter() {
               this.mMaxActivityCount = 4;
                    }

            ActivityChooserViewAdapter(ActivityChooserView x0, C00981 x1) {
               this();
                    }

           public void setDataModel(ActivityChooserModel dataModel) {
               ActivityChooserModel oldDataModel = ActivityChooserView.access$000(ActivityChooserView.this).getDataModel();
               if (oldDataModel != null && ActivityChooserView.this.isShown()) {
                   oldDataModel.unregisterObserver(ActivityChooserView.access$1100(ActivityChooserView.this));
                        }
               this.mDataModel = dataModel;
               if (dataModel != null && ActivityChooserView.this.isShown()) {
                   dataModel.registerObserver(ActivityChooserView.access$1100(ActivityChooserView.this));
                        }
               notifyDataSetChanged();
                    }

                    @Override 
           public int getItemViewType(int position) {
                        return (this.mShowFooterView && position == getCount() + (-1)) ? 1 : 0;
                    }

                    @Override 
           public int getViewTypeCount() {
               return 3;
                    }

                    @Override 
           public int getCount() {
               int activityCount = this.mDataModel.getActivityCount();
               if (!this.mShowDefaultActivity && this.mDataModel.getDefaultActivity() != null) {
                   activityCount--;
                        }
               int count = Math.min(activityCount, this.mMaxActivityCount);
               if (this.mShowFooterView) {
                   return count + 1;
                        }
               return count;
                    }

                    @Override 
           public Object getItem(int position) {
               int itemViewType = getItemViewType(position);
                        switch (itemViewType) {
                            case 0:
                       if (!this.mShowDefaultActivity && this.mDataModel.getDefaultActivity() != null) {
                           position++;
                                }
                       return this.mDataModel.getActivity(position);
                            case 1:
                       return null;
                            default:
                       throw new IllegalArgumentException();
                        }
                    }

                    @Override 
           public long getItemId(int position) {
               return position;
                    }

                    @Override 
           public View getView(int position, View convertView, ViewGroup parent) {
               int itemViewType = getItemViewType(position);
                        switch (itemViewType) {
                            case 0:
                       if (convertView == null || convertView.getId() != C0084R.id.list_item) {
                           convertView = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(C0084R.layout.abc_activity_chooser_view_list_item, parent, false);
                                }
                       PackageManager packageManager = ActivityChooserView.this.getContext().getPackageManager();
                       ImageView iconView = (ImageView) convertView.findViewById(C0084R.id.icon);
                       ResolveInfo activity = (ResolveInfo) getItem(position);
                       iconView.setImageDrawable(activity.loadIcon(packageManager));
                       TextView titleView = (TextView) convertView.findViewById(C0084R.id.title);
                       titleView.setText(activity.loadLabel(packageManager));
                       if (!this.mShowDefaultActivity || position != 0 || this.mHighlightDefaultActivity) {
                                }
                                return convertView;
                            case 1:
                       if (convertView == null || convertView.getId() != 1) {
                           convertView = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(C0084R.layout.abc_activity_chooser_view_list_item, parent, false);
                           convertView.setId(1);
                           TextView titleView2 = (TextView) convertView.findViewById(C0084R.id.title);
                           titleView2.setText(ActivityChooserView.this.getContext().getString(C0084R.string.abc_activity_chooser_view_see_all));
                                }
                                return convertView;
                            default:
                       throw new IllegalArgumentException();
                        }
                    }

           public int measureContentWidth() {
               int oldMaxActivityCount = this.mMaxActivityCount;
               this.mMaxActivityCount = Integer.MAX_VALUE;
               int contentWidth = 0;
               View itemView = null;
               int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
               int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
               int count = getCount();
                        for (int i = 0; i < count; i++) {
                   itemView = getView(i, itemView, null);
                   itemView.measure(widthMeasureSpec, heightMeasureSpec);
                   contentWidth = Math.max(contentWidth, itemView.getMeasuredWidth());
                        }
               this.mMaxActivityCount = oldMaxActivityCount;
               return contentWidth;
                    }

           public void setMaxActivityCount(int maxActivityCount) {
               if (this.mMaxActivityCount != maxActivityCount) {
                   this.mMaxActivityCount = maxActivityCount;
                   notifyDataSetChanged();
                        }
                    }

           public ResolveInfo getDefaultActivity() {
               return this.mDataModel.getDefaultActivity();
                    }

           public void setShowFooterView(boolean showFooterView) {
               if (this.mShowFooterView != showFooterView) {
                   this.mShowFooterView = showFooterView;
                   notifyDataSetChanged();
                        }
                    }

           public int getActivityCount() {
               return this.mDataModel.getActivityCount();
                    }

           public int getHistorySize() {
               return this.mDataModel.getHistorySize();
                    }

           public int getMaxActivityCount() {
               return this.mMaxActivityCount;
                    }

           public ActivityChooserModel getDataModel() {
               return this.mDataModel;
                    }

           public void setShowDefaultActivity(boolean showDefaultActivity, boolean highlightDefaultActivity) {
               if (this.mShowDefaultActivity != showDefaultActivity || this.mHighlightDefaultActivity != highlightDefaultActivity) {
                   this.mShowDefaultActivity = showDefaultActivity;
                   this.mHighlightDefaultActivity = highlightDefaultActivity;
                   notifyDataSetChanged();
                        }
                    }

           public boolean getShowDefaultActivity() {
               return this.mShowDefaultActivity;
                    }
                }
            }
