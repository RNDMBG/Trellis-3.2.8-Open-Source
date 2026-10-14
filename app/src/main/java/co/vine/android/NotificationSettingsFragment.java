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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    public class NotificationSettingsFragment extends BaseArrayListFragment {
                private int mBackgroundColor;
                private boolean mFetched = false;
                private boolean mSucceeded = false;

/* 35 */        static /* synthetic */ int access$000(NotificationSettingsFragment x0) {
/* 36 */            return x0.mBackgroundColor;
                }

/* 35 */        static /* synthetic */ boolean access$102(NotificationSettingsFragment x0, boolean x1) {
/* 36 */            x0.mFetched = x1;
                    return x1;
                }

/* 35 */        static /* synthetic */ boolean access$202(NotificationSettingsFragment x0, boolean x1) {
/* 36 */            x0.mSucceeded = x1;
                    return x1;
                }

                @Override // co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 42 */        public void onCreate(Bundle savedInstanceState) {
/* 43 */            super.onCreate(savedInstanceState);
/* 44 */            this.mRefreshable = false;
/* 45 */            this.mAppSessionListener = new NotificationSettingsListener();
/* 46 */            setHasOptionsMenu(true);
/* 47 */            this.mBackgroundColor = getArguments().getInt("color");
                }

                @Override // co.vine.android.BaseArrayListFragment, android.support.v4.app.Fragment
/* 51 */        public void onViewCreated(View view, Bundle savedInstanceState) {
/* 52 */            super.onViewCreated(view, savedInstanceState);
/* 53 */            View alertsRowHeader = LayoutInflater.from(getActivity()).inflate(C0257R.layout.notification_alerts_row, (ViewGroup) null);
/* 55 */            alertsRowHeader.setOnClickListener(new ViewOnClickListenerC02451());
/* 63 */            this.mListView.addHeaderView(alertsRowHeader, null, false);
                }

                /* JADX INFO: renamed from: co.vine.android.NotificationSettingsFragment$1 */
/* 53 */        class ViewOnClickListenerC02451 implements View.OnClickListener {
/* 54 */            ViewOnClickListenerC02451() {
                    }

                    @Override // android.view.View.OnClickListener
/* 57 */            public void onClick(View v) {
/* 58 */                Intent i = new Intent(NotificationSettingsFragment.this.getActivity(), (Class<?>) NotificationAlertsSettingsActivity.class);
/* 59 */                i.putExtra("color", NotificationSettingsFragment.access$000(NotificationSettingsFragment.this));
/* 60 */                NotificationSettingsFragment.this.startActivity(i);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 67 */        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
/* 68 */            if (this.mSucceeded) {
/* 69 */                inflater.inflate(C0257R.menu.save, menu);
/* 70 */                MenuItem menuItem = menu.findItem(C0257R.id.done);
/* 71 */                menuItem.setEnabled(true);
/* 72 */                menu.findItem(C0257R.id.done).setEnabled(true);
                    }
                }

                @Override // co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 76 */        public void onResume() {
/* 77 */            super.onResume();
/* 78 */            this.mListView.setDividerHeight(0);
/* 79 */            ((RefreshableListView) this.mListView).disablePTR();
/* 80 */            if (!this.mFetched) {
/* 81 */                addRequest(this.mAppController.fetchNotificationSettings());
                    }
                }

/* 83 */        public class NotificationSettingsListener extends AppSessionListener {
/* 84 */            public NotificationSettingsListener() {
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 86 */            public void onGetNotificationSettingsComplete(String reqId, ArrayList<VineNotificationSetting> settings) {
/* 87 */                NotificationSettingsFragment.access$102(NotificationSettingsFragment.this, true);
/* 88 */                PendingRequest request = NotificationSettingsFragment.this.removeRequest(reqId);
/* 89 */                if (request != null) {
/* 90 */                    if (settings != null) {
/* 91 */                        NotificationSettingsFragment.this.mAdapter = NotificationSettingsFragment.this.new NotificationSettingsAdapter(NotificationSettingsFragment.this.getActivity(), settings);
/* 92 */                        NotificationSettingsFragment.this.mListView.setAdapter((ListAdapter) NotificationSettingsFragment.this.mAdapter);
/* 93 */                        NotificationSettingsFragment.access$202(NotificationSettingsFragment.this, true);
/* 94 */                        NotificationSettingsFragment.this.getActivity().invalidateOptionsMenu();
/* 100 */                       return;
                            }
/* 96 */                    NotificationSettingsFragment.this.showSadface(true);
/* 97 */                    NotificationSettingsFragment.access$202(NotificationSettingsFragment.this, false);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 102 */           public void onSaveNotificationSettingsComplete(String reqId, int statusCode, String reasonPhrase) {
/* 103 */               if (statusCode != 200) {
/* 104 */                   if (TextUtils.isEmpty(reasonPhrase)) {
/* 105 */                       Toast.makeText(NotificationSettingsFragment.this.getActivity(), C0257R.string.error_server, 1).show();
/* 112 */                       return;
                            } else {
/* 107 */                       Toast.makeText(NotificationSettingsFragment.this.getActivity(), reasonPhrase, 1).show();
/* 112 */                       return;
                            }
                        }
/* 110 */               NotificationSettingsFragment.this.getActivity().finish();
                    }
                }

/* 124 */       public class NotificationSettingsAdapter extends BaseAdapter {
                    private static final String CHECK_VALUE_OFF = "off";
                    private static final String CHECK_VALUE_ON = "on";
                    private static final int TYPE_CHECK = 0;
                    private static final int TYPE_SPINNER = 1;
                    private Context mContext;
                    private ArrayList<VineNotificationSetting> mSettings;

/* 125 */           public NotificationSettingsAdapter(Context context, ArrayList<VineNotificationSetting> settings) {
/* 124 */               this.mSettings = new ArrayList<>();
/* 127 */               this.mContext = context;
/* 128 */               this.mSettings = settings;
                    }

                    @Override // android.widget.Adapter
/* 132 */           public int getCount() {
/* 133 */               return this.mSettings.size();
                    }

                    @Override // android.widget.BaseAdapter, android.widget.Adapter
/* 137 */           public int getViewTypeCount() {
/* 138 */               return 2;
                    }

                    @Override // android.widget.BaseAdapter, android.widget.Adapter
/* 142 */           public int getItemViewType(int position) {
                        return this.mSettings.get(position).isBooleanSetting ? 0 : 1;
                    }

                    @Override // android.widget.Adapter
/* 147 */           public Object getItem(int position) {
/* 148 */               return this.mSettings.get(position);
                    }

                    @Override // android.widget.Adapter
/* 152 */           public long getItemId(int position) {
/* 153 */               return position;
                    }

                    @Override // android.widget.Adapter
                    @Nullable
/* 158 */           public View getView(int position, View convertView, ViewGroup parent) {
/* 159 */               int layout = C0257R.layout.notification_setting_check;
/* 160 */               int type = getItemViewType(position);
/* 161 */               if (convertView == null) {
/* 162 */                   if (type == 1) {
/* 163 */                       layout = C0257R.layout.notification_settings_spinner;
                            }
/* 165 */                   convertView = LayoutInflater.from(this.mContext).inflate(layout, (ViewGroup) null);
                        }
/* 167 */               bindView(convertView, position);
/* 168 */               return convertView;
                    }

/* 171 */           private void bindView(View view, int position) {
/* 172 */               VineNotificationSetting setting = this.mSettings.get(position);
/* 173 */               NotificationSettingsViewHolder h = new NotificationSettingsViewHolder(view);
/* 175 */               h.title.setText(setting.title);
/* 177 */               if (setting.isBooleanSetting) {
/* 178 */                   setSelected(NotificationSettingsFragment.this.getResources(), CHECK_VALUE_ON.equals(setting.value), h.selectionIndicator);
/* 179 */                   View.OnClickListener listener = new ViewOnClickListenerC02461(h, setting);
/* 186 */                   h.selectionIndicator.setOnClickListener(listener);
/* 187 */                   view.setOnClickListener(listener);
                        } else {
/* 189 */                   NotificationSettingsSpinnerAdapter spinnerAdapter = NotificationSettingsFragment.this.new NotificationSettingsSpinnerAdapter(this.mContext, setting);
/* 190 */                   h.spinner.setAdapter((SpinnerAdapter) spinnerAdapter);
/* 191 */                   h.spinner.setOnItemSelectedListener(spinnerAdapter);
                            for (int i = 0; i < setting.choices.size(); i++) {
/* 193 */                       if (((String) setting.choices.get(i).second).equals(setting.value)) {
/* 194 */                           h.spinner.setSelection(i);
                                    break;
                                }
                            }
                        }
/* 200 */               if (shouldShowHeader(position)) {
/* 201 */                   h.header.setVisibility(0);
/* 202 */                   h.headerText.setText(setting.section);
                        } else {
/* 204 */                   h.header.setVisibility(8);
                        }
                    }

                    /* JADX INFO: renamed from: co.vine.android.NotificationSettingsFragment$NotificationSettingsAdapter$1 */
/* 177 */           class ViewOnClickListenerC02461 implements View.OnClickListener {
                        final /* synthetic */ NotificationSettingsViewHolder val$h;
                        final /* synthetic */ VineNotificationSetting val$setting;

/* 178 */               ViewOnClickListenerC02461(NotificationSettingsViewHolder notificationSettingsViewHolder, VineNotificationSetting vineNotificationSetting) {
                            this.val$h = notificationSettingsViewHolder;
                            this.val$setting = vineNotificationSetting;
                        }

                        @Override // android.view.View.OnClickListener
/* 181 */               public void onClick(View v) {
/* 182 */                   NotificationSettingsAdapter.this.setSelected(NotificationSettingsFragment.this.getResources(), !v.isSelected(), this.val$h.selectionIndicator);
/* 183 */                   this.val$setting.value = v.isSelected() ? NotificationSettingsAdapter.CHECK_VALUE_ON : NotificationSettingsAdapter.CHECK_VALUE_OFF;
                        }
                    }

/* 208 */           private boolean shouldShowHeader(int position) {
/* 209 */               if (position == 0) {
                            return true;
                        }
/* 212 */               VineNotificationSetting currentSetting = this.mSettings.get(position);
/* 213 */               VineNotificationSetting previousSetting = this.mSettings.get(position - 1);
                        return (TextUtils.isEmpty(currentSetting.section) || currentSetting.section.equals(previousSetting.section)) ? false : true;
                    }

/* 220 */           public void setSelected(Resources res, boolean selected, View view) {
/* 221 */               if (selected) {
/* 222 */                   Drawable bg = res.getDrawable(C0257R.drawable.ic_circle_selected).mutate();
/* 223 */                   int color = res.getColor(C0257R.color.vine_green);
/* 224 */                   bg.setColorFilter(new PorterDuffColorFilter((-16777216) | color, PorterDuff.Mode.SRC_IN));
/* 225 */                   ViewUtil.setBackground(view, bg);
                        } else {
/* 227 */                   Drawable bg2 = res.getDrawable(C0257R.drawable.ic_circle_default);
/* 228 */                   bg2.setColorFilter(new PorterDuffColorFilter(res.getColor(C0257R.color.btn_circle_stroke), PorterDuff.Mode.SRC_IN));
/* 229 */                   ViewUtil.setBackground(view, bg2);
                        }
/* 231 */               view.setSelected(selected);
                    }

/* 234 */           public HashMap<String, String> getMap() {
/* 235 */               HashMap<String, String> map = new HashMap<>();
                        for (VineNotificationSetting setting : this.mSettings) {
/* 237 */                   setting.addToMap(map);
                        }
/* 239 */               return map;
                    }

/* 247 */           class NotificationSettingsViewHolder {
                        View header;
                        TextView headerText;
                        View selectionIndicator;
                        Spinner spinner;
                        TextView title;

/* 248 */               public NotificationSettingsViewHolder(View view) {
/* 250 */                   this.header = view.findViewById(C0257R.id.header);
/* 251 */                   this.headerText = (TextView) view.findViewById(C0257R.id.header_text);
/* 252 */                   this.title = (TextView) view.findViewById(2131427387);
/* 253 */                   this.spinner = (Spinner) view.findViewById(C0257R.id.spinner);
/* 254 */                   this.selectionIndicator = view.findViewById(C0257R.id.selection_indicator);
                        }
                    }
                }

/* 263 */       public class NotificationSettingsSpinnerAdapter extends BaseAdapter implements SpinnerAdapter, AdapterView.OnItemSelectedListener {
                    private Context mContext;
                    private VineNotificationSetting mSetting;

/* 264 */           public NotificationSettingsSpinnerAdapter(Context context, VineNotificationSetting setting) {
/* 266 */               this.mSetting = setting;
/* 267 */               this.mContext = context;
                    }

                    @Override // android.widget.Adapter
/* 271 */           public int getCount() {
/* 272 */               return this.mSetting.choices.size();
                    }

                    @Override // android.widget.Adapter
/* 276 */           public Object getItem(int position) {
/* 277 */               return this.mSetting.choices.get(position);
                    }

                    @Override // android.widget.Adapter
/* 281 */           public long getItemId(int position) {
/* 282 */               return position;
                    }

                    @Override // android.widget.Adapter
                    @Nullable
/* 287 */           public View getView(int position, View convertView, ViewGroup parent) {
                        View res;
/* 288 */               Pair<String, String> choice = this.mSetting.choices.get(position);
/* 290 */               if (convertView != null) {
/* 291 */                   res = convertView;
                        } else {
/* 293 */                   res = LayoutInflater.from(this.mContext).inflate(C0257R.layout.settings_spinner_row_item, (ViewGroup) null);
                        }
/* 295 */               TextView t = (TextView) res.findViewById(2131427387);
/* 296 */               t.setText((CharSequence) choice.first);
/* 297 */               t.setSingleLine(false);
/* 298 */               res.invalidate();
/* 299 */               return res;
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
/* 303 */           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
/* 304 */               this.mSetting.value = (String) this.mSetting.choices.get(position).second;
/* 305 */               parent.setSelection(position);
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
/* 309 */           public void onNothingSelected(AdapterView<?> parent) {
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 314 */       public boolean onOptionsItemSelected(MenuItem item) {
/* 315 */           if (item.getItemId() != 2131427939) {
                        return super.onOptionsItemSelected(item);
                    }
/* 316 */           if (this.mAdapter != null) {
/* 317 */               HashMap<String, String> map = ((NotificationSettingsAdapter) this.mAdapter).getMap();
/* 318 */               addRequest(this.mAppController.saveNotificationSettings(map));
                    }
/* 320 */           return true;
                }
            }
