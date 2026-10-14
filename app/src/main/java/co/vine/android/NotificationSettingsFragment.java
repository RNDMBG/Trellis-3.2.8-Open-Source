            package co.vine.android;

            import android.content.Context;
            import android.content.Intent;
            import android.content.res.Resources;
            import android.graphics.PorterDuff;
            import android.graphics.PorterDuffColorFilter;
            import android.graphics.drawable.Drawable;
            import android.os.Bundle;
            import android.support.annotation.Nullable;
            import android.text.TextUtils;
            import android.util.Pair;
            import android.view.LayoutInflater;
            import android.view.Menu;
            import android.view.MenuInflater;
            import android.view.MenuItem;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.AdapterView;
            import android.widget.BaseAdapter;
            import android.widget.ListAdapter;
            import android.widget.Spinner;
            import android.widget.SpinnerAdapter;
            import android.widget.TextView;
            import android.widget.Toast;
            import co.vine.android.api.VineNotificationSetting;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.util.ViewUtil;
            import com.twitter.android.widget.RefreshableListView;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            
    public class NotificationSettingsFragment extends BaseArrayListFragment {
                private int mBackgroundColor;
                private boolean mFetched = false;
                private boolean mSucceeded = false;

        static  int access$000(NotificationSettingsFragment x0) {
            return x0.mBackgroundColor;
                }

        static  boolean access$102(NotificationSettingsFragment x0, boolean x1) {
            x0.mFetched = x1;
                    return x1;
                }

        static  boolean access$202(NotificationSettingsFragment x0, boolean x1) {
            x0.mSucceeded = x1;
                    return x1;
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.mRefreshable = false;
            this.mAppSessionListener = new NotificationSettingsListener();
            setHasOptionsMenu(true);
            this.mBackgroundColor = getArguments().getInt("color");
                }

                @Override 
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            View alertsRowHeader = LayoutInflater.from(getActivity()).inflate(C0257R.layout.notification_alerts_row, (ViewGroup) null);
            alertsRowHeader.setOnClickListener(new ViewOnClickListenerC02451());
            this.mListView.addHeaderView(alertsRowHeader, null, false);
                }

                
        class ViewOnClickListenerC02451 implements View.OnClickListener {
            ViewOnClickListenerC02451() {
                    }

                    @Override 
            public void onClick(View v) {
                Intent i = new Intent(NotificationSettingsFragment.this.getActivity(), (Class<?>) NotificationAlertsSettingsActivity.class);
                i.putExtra("color", NotificationSettingsFragment.access$000(NotificationSettingsFragment.this));
                NotificationSettingsFragment.this.startActivity(i);
                    }
                }

                @Override 
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            if (this.mSucceeded) {
                inflater.inflate(C0257R.menu.save, menu);
                MenuItem menuItem = menu.findItem(C0257R.id.done);
                menuItem.setEnabled(true);
                menu.findItem(C0257R.id.done).setEnabled(true);
                    }
                }

                @Override 
        public void onResume() {
            super.onResume();
            this.mListView.setDividerHeight(0);
            ((RefreshableListView) this.mListView).disablePTR();
            if (!this.mFetched) {
                addRequest(this.mAppController.fetchNotificationSettings());
                    }
                }

        public class NotificationSettingsListener extends AppSessionListener {
            public NotificationSettingsListener() {
                    }

                    @Override 
            public void onGetNotificationSettingsComplete(String reqId, ArrayList<VineNotificationSetting> settings) {
                NotificationSettingsFragment.access$102(NotificationSettingsFragment.this, true);
                PendingRequest request = NotificationSettingsFragment.this.removeRequest(reqId);
                if (request != null) {
                    if (settings != null) {
                        NotificationSettingsFragment.this.mAdapter = NotificationSettingsFragment.this.new NotificationSettingsAdapter(NotificationSettingsFragment.this.getActivity(), settings);
                        NotificationSettingsFragment.this.mListView.setAdapter((ListAdapter) NotificationSettingsFragment.this.mAdapter);
                        NotificationSettingsFragment.access$202(NotificationSettingsFragment.this, true);
                        NotificationSettingsFragment.this.getActivity().invalidateOptionsMenu();
                       return;
                            }
                    NotificationSettingsFragment.this.showSadface(true);
                    NotificationSettingsFragment.access$202(NotificationSettingsFragment.this, false);
                        }
                    }

                    @Override 
           public void onSaveNotificationSettingsComplete(String reqId, int statusCode, String reasonPhrase) {
               if (statusCode != 200) {
                   if (TextUtils.isEmpty(reasonPhrase)) {
                       Toast.makeText(NotificationSettingsFragment.this.getActivity(), C0257R.string.error_server, 1).show();
                       return;
                            } else {
                       Toast.makeText(NotificationSettingsFragment.this.getActivity(), reasonPhrase, 1).show();
                       return;
                            }
                        }
               NotificationSettingsFragment.this.getActivity().finish();
                    }
                }

       public class NotificationSettingsAdapter extends BaseAdapter {
                    private static final String CHECK_VALUE_OFF = "off";
                    private static final String CHECK_VALUE_ON = "on";
                    private static final int TYPE_CHECK = 0;
                    private static final int TYPE_SPINNER = 1;
                    private Context mContext;
                    private ArrayList<VineNotificationSetting> mSettings;

           public NotificationSettingsAdapter(Context context, ArrayList<VineNotificationSetting> settings) {
               this.mSettings = new ArrayList<>();
               this.mContext = context;
               this.mSettings = settings;
                    }

                    @Override 
           public int getCount() {
               return this.mSettings.size();
                    }

                    @Override 
           public int getViewTypeCount() {
               return 2;
                    }

                    @Override 
           public int getItemViewType(int position) {
                        return this.mSettings.get(position).isBooleanSetting ? 0 : 1;
                    }

                    @Override 
           public Object getItem(int position) {
               return this.mSettings.get(position);
                    }

                    @Override 
           public long getItemId(int position) {
               return position;
                    }

                    @Override 
                    @Nullable
           public View getView(int position, View convertView, ViewGroup parent) {
               int layout = C0257R.layout.notification_setting_check;
               int type = getItemViewType(position);
               if (convertView == null) {
                   if (type == 1) {
                       layout = C0257R.layout.notification_settings_spinner;
                            }
                   convertView = LayoutInflater.from(this.mContext).inflate(layout, (ViewGroup) null);
                        }
               bindView(convertView, position);
               return convertView;
                    }

           private void bindView(View view, int position) {
               VineNotificationSetting setting = this.mSettings.get(position);
               NotificationSettingsViewHolder h = new NotificationSettingsViewHolder(view);
               h.title.setText(setting.title);
               if (setting.isBooleanSetting) {
                   setSelected(NotificationSettingsFragment.this.getResources(), CHECK_VALUE_ON.equals(setting.value), h.selectionIndicator);
                   View.OnClickListener listener = new ViewOnClickListenerC02461(h, setting);
                   h.selectionIndicator.setOnClickListener(listener);
                   view.setOnClickListener(listener);
                        } else {
                   NotificationSettingsSpinnerAdapter spinnerAdapter = NotificationSettingsFragment.this.new NotificationSettingsSpinnerAdapter(this.mContext, setting);
                   h.spinner.setAdapter((SpinnerAdapter) spinnerAdapter);
                   h.spinner.setOnItemSelectedListener(spinnerAdapter);
                            for (int i = 0; i < setting.choices.size(); i++) {
                       if (((String) setting.choices.get(i).second).equals(setting.value)) {
                           h.spinner.setSelection(i);
                                    break;
                                }
                            }
                        }
               if (shouldShowHeader(position)) {
                   h.header.setVisibility(0);
                   h.headerText.setText(setting.section);
                        } else {
                   h.header.setVisibility(8);
                        }
                    }

                    
           class ViewOnClickListenerC02461 implements View.OnClickListener {
                        final  NotificationSettingsViewHolder val$h;
                        final  VineNotificationSetting val$setting;

               ViewOnClickListenerC02461(NotificationSettingsViewHolder notificationSettingsViewHolder, VineNotificationSetting vineNotificationSetting) {
                            this.val$h = notificationSettingsViewHolder;
                            this.val$setting = vineNotificationSetting;
                        }

                        @Override 
               public void onClick(View v) {
                   NotificationSettingsAdapter.this.setSelected(NotificationSettingsFragment.this.getResources(), !v.isSelected(), this.val$h.selectionIndicator);
                   this.val$setting.value = v.isSelected() ? NotificationSettingsAdapter.CHECK_VALUE_ON : NotificationSettingsAdapter.CHECK_VALUE_OFF;
                        }
                    }

           private boolean shouldShowHeader(int position) {
               if (position == 0) {
                            return true;
                        }
               VineNotificationSetting currentSetting = this.mSettings.get(position);
               VineNotificationSetting previousSetting = this.mSettings.get(position - 1);
                        return (TextUtils.isEmpty(currentSetting.section) || currentSetting.section.equals(previousSetting.section)) ? false : true;
                    }

           public void setSelected(Resources res, boolean selected, View view) {
               if (selected) {
                   Drawable bg = res.getDrawable(C0257R.drawable.ic_circle_selected).mutate();
                   int color = res.getColor(C0257R.color.vine_green);
                   bg.setColorFilter(new PorterDuffColorFilter((-16777216) | color, PorterDuff.Mode.SRC_IN));
                   ViewUtil.setBackground(view, bg);
                        } else {
                   Drawable bg2 = res.getDrawable(C0257R.drawable.ic_circle_default);
                   bg2.setColorFilter(new PorterDuffColorFilter(res.getColor(C0257R.color.btn_circle_stroke), PorterDuff.Mode.SRC_IN));
                   ViewUtil.setBackground(view, bg2);
                        }
               view.setSelected(selected);
                    }

           public HashMap<String, String> getMap() {
               HashMap<String, String> map = new HashMap<>();
                        for (VineNotificationSetting setting : this.mSettings) {
                   setting.addToMap(map);
                        }
               return map;
                    }

           class NotificationSettingsViewHolder {
                        View header;
                        TextView headerText;
                        View selectionIndicator;
                        Spinner spinner;
                        TextView title;

               public NotificationSettingsViewHolder(View view) {
                   this.header = view.findViewById(C0257R.id.header);
                   this.headerText = (TextView) view.findViewById(C0257R.id.header_text);
                   this.title = (TextView) view.findViewById(2131427387);
                   this.spinner = (Spinner) view.findViewById(C0257R.id.spinner);
                   this.selectionIndicator = view.findViewById(C0257R.id.selection_indicator);
                        }
                    }
                }

       public class NotificationSettingsSpinnerAdapter extends BaseAdapter implements SpinnerAdapter, AdapterView.OnItemSelectedListener {
                    private Context mContext;
                    private VineNotificationSetting mSetting;

           public NotificationSettingsSpinnerAdapter(Context context, VineNotificationSetting setting) {
               this.mSetting = setting;
               this.mContext = context;
                    }

                    @Override 
           public int getCount() {
               return this.mSetting.choices.size();
                    }

                    @Override 
           public Object getItem(int position) {
               return this.mSetting.choices.get(position);
                    }

                    @Override 
           public long getItemId(int position) {
               return position;
                    }

                    @Override 
                    @Nullable
           public View getView(int position, View convertView, ViewGroup parent) {
                        View res;
               Pair<String, String> choice = this.mSetting.choices.get(position);
               if (convertView != null) {
                   res = convertView;
                        } else {
                   res = LayoutInflater.from(this.mContext).inflate(C0257R.layout.settings_spinner_row_item, (ViewGroup) null);
                        }
               TextView t = (TextView) res.findViewById(2131427387);
               t.setText((CharSequence) choice.first);
               t.setSingleLine(false);
               res.invalidate();
               return res;
                    }

                    @Override 
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               this.mSetting.value = (String) this.mSetting.choices.get(position).second;
               parent.setSelection(position);
                    }

                    @Override 
           public void onNothingSelected(AdapterView<?> parent) {
                    }
                }

                @Override 
       public boolean onOptionsItemSelected(MenuItem item) {
           if (item.getItemId() != 2131427939) {
                        return super.onOptionsItemSelected(item);
                    }
           if (this.mAdapter != null) {
               HashMap<String, String> map = ((NotificationSettingsAdapter) this.mAdapter).getMap();
               addRequest(this.mAppController.saveNotificationSettings(map));
                    }
           return true;
                }
            }
