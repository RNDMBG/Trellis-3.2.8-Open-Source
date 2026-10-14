            package android.support.v7.widget;

            import android.content.Context;
            import android.content.Intent;
            import android.content.pm.PackageManager;
            import android.content.pm.ResolveInfo;
            import android.graphics.drawable.Drawable;
            import android.support.v4.view.ActionProvider;
            import android.support.v7.appcompat.C0084R;
            import android.support.v7.internal.widget.ActivityChooserModel;
            import android.support.v7.internal.widget.ActivityChooserView;
            import android.util.TypedValue;
            import android.view.MenuItem;
            import android.view.SubMenu;
            import android.view.View;
            
            
    public class ShareActionProvider extends ActionProvider {
                private static final int DEFAULT_INITIAL_ACTIVITY_COUNT = 4;
                public static final String DEFAULT_SHARE_HISTORY_FILE_NAME = "share_history.xml";
                private final Context mContext;
                private int mMaxShownActivityCount;
                private ActivityChooserModel.OnChooseActivityListener mOnChooseActivityListener;
                private final ShareMenuItemOnMenuItemClickListener mOnMenuItemClickListener;
                private OnShareTargetSelectedListener mOnShareTargetSelectedListener;
                private String mShareHistoryFileName;

                public interface OnShareTargetSelectedListener {
                    boolean onShareTargetSelected(ShareActionProvider shareActionProvider, Intent intent);
                }

        static  Context access$100(ShareActionProvider x0) {
            return x0.mContext;
                }

        static  String access$200(ShareActionProvider x0) {
            return x0.mShareHistoryFileName;
                }

        static  OnShareTargetSelectedListener access$400(ShareActionProvider x0) {
            return x0.mOnShareTargetSelectedListener;
                }

       public ShareActionProvider(Context context) {
           super(context);
           this.mMaxShownActivityCount = 4;
           this.mOnMenuItemClickListener = new ShareMenuItemOnMenuItemClickListener(this, null);
           this.mShareHistoryFileName = DEFAULT_SHARE_HISTORY_FILE_NAME;
           this.mContext = context;
                }

       public void setOnShareTargetSelectedListener(OnShareTargetSelectedListener listener) {
           this.mOnShareTargetSelectedListener = listener;
           setActivityChooserPolicyIfNeeded();
                }

                @Override 
       public View onCreateActionView() {
           ActivityChooserModel dataModel = ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName);
           ActivityChooserView activityChooserView = new ActivityChooserView(this.mContext);
           activityChooserView.setActivityChooserModel(dataModel);
           TypedValue outTypedValue = new TypedValue();
           this.mContext.getTheme().resolveAttribute(C0084R.attr.actionModeShareDrawable, outTypedValue, true);
           Drawable drawable = this.mContext.getResources().getDrawable(outTypedValue.resourceId);
           activityChooserView.setExpandActivityOverflowButtonDrawable(drawable);
           activityChooserView.setProvider(this);
           activityChooserView.setDefaultActionButtonContentDescription(C0084R.string.abc_shareactionprovider_share_with_application);
           activityChooserView.setExpandActivityOverflowButtonContentDescription(C0084R.string.abc_shareactionprovider_share_with);
           return activityChooserView;
                }

                @Override 
       public boolean hasSubMenu() {
           return true;
                }

                @Override 
       public void onPrepareSubMenu(SubMenu subMenu) {
           subMenu.clear();
           ActivityChooserModel dataModel = ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName);
           PackageManager packageManager = this.mContext.getPackageManager();
           int expandedActivityCount = dataModel.getActivityCount();
           int collapsedActivityCount = Math.min(expandedActivityCount, this.mMaxShownActivityCount);
                    for (int i = 0; i < collapsedActivityCount; i++) {
               ResolveInfo activity = dataModel.getActivity(i);
               subMenu.add(0, i, i, activity.loadLabel(packageManager)).setIcon(activity.loadIcon(packageManager)).setOnMenuItemClickListener(this.mOnMenuItemClickListener);
                    }
           if (collapsedActivityCount < expandedActivityCount) {
               SubMenu expandedSubMenu = subMenu.addSubMenu(0, collapsedActivityCount, collapsedActivityCount, this.mContext.getString(C0084R.string.abc_activity_chooser_view_see_all));
                        for (int i2 = 0; i2 < expandedActivityCount; i2++) {
                   ResolveInfo activity2 = dataModel.getActivity(i2);
                   expandedSubMenu.add(0, i2, i2, activity2.loadLabel(packageManager)).setIcon(activity2.loadIcon(packageManager)).setOnMenuItemClickListener(this.mOnMenuItemClickListener);
                        }
                    }
                }

       public void setShareHistoryFileName(String shareHistoryFile) {
           this.mShareHistoryFileName = shareHistoryFile;
           setActivityChooserPolicyIfNeeded();
                }

       public void setShareIntent(Intent shareIntent) {
           ActivityChooserModel dataModel = ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName);
           dataModel.setIntent(shareIntent);
                }

       private class ShareMenuItemOnMenuItemClickListener implements MenuItem.OnMenuItemClickListener {
           private ShareMenuItemOnMenuItemClickListener() {
                    }

            ShareMenuItemOnMenuItemClickListener(ShareActionProvider x0, C01211 x1) {
               this();
                    }

                    @Override 
           public boolean onMenuItemClick(MenuItem item) {
               ActivityChooserModel dataModel = ActivityChooserModel.get(ShareActionProvider.access$100(ShareActionProvider.this), ShareActionProvider.access$200(ShareActionProvider.this));
               int itemId = item.getItemId();
               Intent launchIntent = dataModel.chooseActivity(itemId);
               if (launchIntent != null) {
                   launchIntent.addFlags(524288);
                   ShareActionProvider.access$100(ShareActionProvider.this).startActivity(launchIntent);
                   return true;
                        }
               return true;
                    }
                }

       private void setActivityChooserPolicyIfNeeded() {
           if (this.mOnShareTargetSelectedListener != null) {
               if (this.mOnChooseActivityListener == null) {
                   this.mOnChooseActivityListener = new ShareActivityChooserModelPolicy(this, null);
                        }
               ActivityChooserModel dataModel = ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName);
               dataModel.setOnChooseActivityListener(this.mOnChooseActivityListener);
                    }
                }

       private class ShareActivityChooserModelPolicy implements ActivityChooserModel.OnChooseActivityListener {
           private ShareActivityChooserModelPolicy() {
                    }

            ShareActivityChooserModelPolicy(ShareActionProvider x0, C01211 x1) {
               this();
                    }

                    @Override 
           public boolean onChooseActivity(ActivityChooserModel host, Intent intent) {
               if (ShareActionProvider.access$400(ShareActionProvider.this) != null) {
                   ShareActionProvider.access$400(ShareActionProvider.this).onShareTargetSelected(ShareActionProvider.this, intent);
                   return false;
                        }
               return false;
                    }
                }
            }
