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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 83 */    public class ShareActionProvider extends ActionProvider {
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

/* 84 */        static /* synthetic */ Context access$100(ShareActionProvider x0) {
/* 85 */            return x0.mContext;
                }

/* 84 */        static /* synthetic */ String access$200(ShareActionProvider x0) {
/* 85 */            return x0.mShareHistoryFileName;
                }

/* 84 */        static /* synthetic */ OnShareTargetSelectedListener access$400(ShareActionProvider x0) {
/* 85 */            return x0.mOnShareTargetSelectedListener;
                }

/* 154 */       public ShareActionProvider(Context context) {
/* 155 */           super(context);
/* 122 */           this.mMaxShownActivityCount = 4;
/* 127 */           this.mOnMenuItemClickListener = new ShareMenuItemOnMenuItemClickListener(this, null);
/* 143 */           this.mShareHistoryFileName = DEFAULT_SHARE_HISTORY_FILE_NAME;
/* 156 */           this.mContext = context;
                }

/* 169 */       public void setOnShareTargetSelectedListener(OnShareTargetSelectedListener listener) {
/* 170 */           this.mOnShareTargetSelectedListener = listener;
/* 171 */           setActivityChooserPolicyIfNeeded();
                }

                @Override // android.support.v4.view.ActionProvider
/* 179 */       public View onCreateActionView() {
/* 180 */           ActivityChooserModel dataModel = ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName);
/* 181 */           ActivityChooserView activityChooserView = new ActivityChooserView(this.mContext);
/* 182 */           activityChooserView.setActivityChooserModel(dataModel);
/* 185 */           TypedValue outTypedValue = new TypedValue();
/* 186 */           this.mContext.getTheme().resolveAttribute(C0084R.attr.actionModeShareDrawable, outTypedValue, true);
/* 187 */           Drawable drawable = this.mContext.getResources().getDrawable(outTypedValue.resourceId);
/* 188 */           activityChooserView.setExpandActivityOverflowButtonDrawable(drawable);
/* 189 */           activityChooserView.setProvider(this);
/* 192 */           activityChooserView.setDefaultActionButtonContentDescription(C0084R.string.abc_shareactionprovider_share_with_application);
/* 194 */           activityChooserView.setExpandActivityOverflowButtonContentDescription(C0084R.string.abc_shareactionprovider_share_with);
/* 197 */           return activityChooserView;
                }

                @Override // android.support.v4.view.ActionProvider
/* 204 */       public boolean hasSubMenu() {
/* 205 */           return true;
                }

                @Override // android.support.v4.view.ActionProvider
/* 213 */       public void onPrepareSubMenu(SubMenu subMenu) {
/* 214 */           subMenu.clear();
/* 216 */           ActivityChooserModel dataModel = ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName);
/* 217 */           PackageManager packageManager = this.mContext.getPackageManager();
/* 219 */           int expandedActivityCount = dataModel.getActivityCount();
/* 220 */           int collapsedActivityCount = Math.min(expandedActivityCount, this.mMaxShownActivityCount);
                    for (int i = 0; i < collapsedActivityCount; i++) {
/* 224 */               ResolveInfo activity = dataModel.getActivity(i);
/* 225 */               subMenu.add(0, i, i, activity.loadLabel(packageManager)).setIcon(activity.loadIcon(packageManager)).setOnMenuItemClickListener(this.mOnMenuItemClickListener);
                    }
/* 230 */           if (collapsedActivityCount < expandedActivityCount) {
/* 232 */               SubMenu expandedSubMenu = subMenu.addSubMenu(0, collapsedActivityCount, collapsedActivityCount, this.mContext.getString(C0084R.string.abc_activity_chooser_view_see_all));
                        for (int i2 = 0; i2 < expandedActivityCount; i2++) {
/* 236 */                   ResolveInfo activity2 = dataModel.getActivity(i2);
/* 237 */                   expandedSubMenu.add(0, i2, i2, activity2.loadLabel(packageManager)).setIcon(activity2.loadIcon(packageManager)).setOnMenuItemClickListener(this.mOnMenuItemClickListener);
                        }
                    }
                }

/* 275 */       public void setShareHistoryFileName(String shareHistoryFile) {
/* 276 */           this.mShareHistoryFileName = shareHistoryFile;
/* 277 */           setActivityChooserPolicyIfNeeded();
                }

/* 299 */       public void setShareIntent(Intent shareIntent) {
/* 300 */           ActivityChooserModel dataModel = ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName);
/* 302 */           dataModel.setIntent(shareIntent);
                }

/* 306 */       private class ShareMenuItemOnMenuItemClickListener implements MenuItem.OnMenuItemClickListener {
/* 307 */           private ShareMenuItemOnMenuItemClickListener() {
                    }

/* 307 */           /* synthetic */ ShareMenuItemOnMenuItemClickListener(ShareActionProvider x0, C01211 x1) {
/* 308 */               this();
                    }

                    @Override // android.view.MenuItem.OnMenuItemClickListener
/* 310 */           public boolean onMenuItemClick(MenuItem item) {
/* 311 */               ActivityChooserModel dataModel = ActivityChooserModel.get(ShareActionProvider.access$100(ShareActionProvider.this), ShareActionProvider.access$200(ShareActionProvider.this));
/* 313 */               int itemId = item.getItemId();
/* 314 */               Intent launchIntent = dataModel.chooseActivity(itemId);
/* 315 */               if (launchIntent != null) {
/* 316 */                   launchIntent.addFlags(524288);
/* 317 */                   ShareActionProvider.access$100(ShareActionProvider.this).startActivity(launchIntent);
/* 319 */                   return true;
                        }
/* 319 */               return true;
                    }
                }

/* 327 */       private void setActivityChooserPolicyIfNeeded() {
/* 328 */           if (this.mOnShareTargetSelectedListener != null) {
/* 331 */               if (this.mOnChooseActivityListener == null) {
/* 332 */                   this.mOnChooseActivityListener = new ShareActivityChooserModelPolicy(this, null);
                        }
/* 334 */               ActivityChooserModel dataModel = ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName);
/* 335 */               dataModel.setOnChooseActivityListener(this.mOnChooseActivityListener);
                    }
                }

/* 339 */       private class ShareActivityChooserModelPolicy implements ActivityChooserModel.OnChooseActivityListener {
/* 340 */           private ShareActivityChooserModelPolicy() {
                    }

/* 340 */           /* synthetic */ ShareActivityChooserModelPolicy(ShareActionProvider x0, C01211 x1) {
/* 341 */               this();
                    }

                    @Override // android.support.v7.internal.widget.ActivityChooserModel.OnChooseActivityListener
/* 343 */           public boolean onChooseActivity(ActivityChooserModel host, Intent intent) {
/* 344 */               if (ShareActionProvider.access$400(ShareActionProvider.this) != null) {
/* 345 */                   ShareActionProvider.access$400(ShareActionProvider.this).onShareTargetSelected(ShareActionProvider.this, intent);
/* 348 */                   return false;
                        }
/* 348 */               return false;
                    }
                }
            }
