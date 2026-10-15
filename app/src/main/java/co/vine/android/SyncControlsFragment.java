            package co.vine.android;

            import android.content.BroadcastReceiver;
            import android.content.Context;
            import android.content.DialogInterface;
            import android.content.Intent;
            import android.content.res.Resources;
            import android.os.Bundle;
            import android.os.Handler;
            import android.os.Looper;
            import android.support.v4.app.FragmentActivity;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.CompoundButton;
            import android.widget.TextView;
            import co.vine.android.prefetch.PrefetchManager;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.Util;
            import co.vine.android.views.SwitchInterface;
            import co.vine.android.widgets.PromptDialogFragment;
            import com.edisonwang.android.slog.SLog;
            
            
    public class SyncControlsFragment extends BaseControllerFragment {
                private static final int DIALOG_SYNC_INTERVAL = 1;
                private static final long[] INTERVALS = PrefetchManager.INTERVALS;
                private static final long SYNC_UPDATE_DELAY = 2000;
                private SwitchInterface mChargingRequiredSwitcher;
                private TextView mIntervalDescription;
                private String[] mIntervalStrings;
                private PrefetchManager mPrefetchManager;
                private SwitchInterface mRoamingAllowedSwitcher;
                private TextView mSyncDescription;
                private SwitchInterface mSyncOnOffSwitcher;
                private SwitchInterface mWifiOnlySwitcher;
                private Handler mHandler = new Handler(Looper.getMainLooper());
                private final PrefetchManager.PrefetchFilter mFilter = PrefetchManager.getStateChangeFilter();
                private int mNext = -2;
                private BroadcastReceiver mReceiver = new C03021();

        static  PrefetchManager.PrefetchFilter access$000(SyncControlsFragment x0) {
            return x0.mFilter;
                }

        static  boolean access$1000(SyncControlsFragment x0) {
            return x0.isChargingRequired();
                }

        static  int access$102(SyncControlsFragment x0, int x1) {
            x0.mNext = x1;
                    return x1;
                }

        static  boolean access$1100(SyncControlsFragment x0) {
            return x0.isWifiOnly();
                }

        static  boolean access$1200(SyncControlsFragment x0) {
            return x0.isRoamingAllowed();
                }

        static  void access$200(SyncControlsFragment x0) {
            x0.updateSyncDescription();
                }

        static  Handler access$300(SyncControlsFragment x0) {
            return x0.mHandler;
                }

        static  boolean access$400(SyncControlsFragment x0) {
            return x0.initPrefetchManager();
                }

        static  boolean access$500(SyncControlsFragment x0) {
            return x0.isEnabled();
                }

        static  PrefetchManager access$600(SyncControlsFragment x0) {
            return x0.mPrefetchManager;
                }

        static  String[] access$700(SyncControlsFragment x0) {
            return x0.mIntervalStrings;
                }

        static  long[] access$800() {
            return INTERVALS;
                }

        static  void access$900(SyncControlsFragment x0) {
            x0.updateIntervalText();
                }

                
        class C03021 extends BroadcastReceiver {
            C03021() {
                    }

                    @Override 
            public void onReceive(Context context, Intent intent) {
                int next = SyncControlsFragment.access$000(SyncControlsFragment.this).getScheduledTimeInSeconds(intent.getExtras());
                if (next >= -1) {
                    SyncControlsFragment.access$102(SyncControlsFragment.this, next);
                        }
                if (SyncControlsFragment.access$000(SyncControlsFragment.this).isEnd(intent.getAction())) {
                    SyncControlsFragment.access$300(SyncControlsFragment.this).postDelayed(new AnonymousClass1(), SyncControlsFragment.SYNC_UPDATE_DELAY);
                        } else {
                    SyncControlsFragment.access$200(SyncControlsFragment.this);
                        }
                    }

                    
            class AnonymousClass1 implements Runnable {
                AnonymousClass1() {
                        }

                        @Override 
                public void run() {
                    SyncControlsFragment.access$200(SyncControlsFragment.this);
                        }
                    }
                }

                @Override 
        public void onResume() {
            super.onResume();
                    try {
                getActivity().registerReceiver(this.mReceiver, this.mFilter);
                    } catch (Exception e) {
                SLog.m459e("Receiver may have already been registered.", (Throwable) e);
                    }
                }

                @Override 
        public void onPause() {
            super.onPause();
                    try {
                getActivity().unregisterReceiver(this.mReceiver);
                    } catch (Exception e) {
                    }
                }

                @Override 
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            initPrefetchManager();
            initIntervalArray(getActivity());
            this.mSyncOnOffSwitcher.setChecked(isEnabled());
            this.mChargingRequiredSwitcher.setChecked(isChargingRequired());
            this.mWifiOnlySwitcher.setChecked(isWifiOnly());
            this.mRoamingAllowedSwitcher.setChecked(isRoamingAllowed());
            updateSyncDescription();
           updateIntervalText();
                }

       private void updateIntervalText() {
                    String text;
           FragmentActivity activity = getActivity();
           if (activity != null) {
               Resources res = activity.getResources();
               int minutes = (int) (getInterval() / CommonUtil.MINUTE_MILLIS);
               if (minutes >= 60) {
                   text = res.getQuantityString(R.plurals.time_hours_verbose, minutes / 60, Integer.valueOf(minutes / 60));
                        } else {
                   text = res.getQuantityString(R.plurals.time_mins_verbose, minutes, Integer.valueOf(minutes));
                        }
               this.mIntervalDescription.setText(res.getString(R.string.sync_interval_desc, text));
                    }
                }

       private void initIntervalArray(FragmentActivity activity) {
           if (this.mIntervalStrings == null) {
               this.mIntervalStrings = new String[INTERVALS.length];
               Resources res = activity.getResources();
                        for (int i = 0; i < INTERVALS.length; i++) {
                   int minutes = (int) (INTERVALS[i] / CommonUtil.MINUTE_MILLIS);
                   if (minutes >= 60) {
                       this.mIntervalStrings[i] = res.getString(R.string.sync_interval_desc, res.getQuantityString(R.plurals.time_hours_verbose, minutes / 60, Integer.valueOf(minutes / 60)));
                            } else {
                       this.mIntervalStrings[i] = res.getString(R.string.sync_interval_desc, res.getQuantityString(R.plurals.time_mins_verbose, minutes, Integer.valueOf(minutes)));
                            }
                        }
                    }
                }

       private void updateSyncDescription() {
           FragmentActivity activity = getActivity();
           if (this.mSyncDescription != null && this.mPrefetchManager != null && activity != null) {
               if (this.mPrefetchManager.isSyncPending()) {
                   this.mSyncDescription.setText(activity.getString(R.string.sync_now_pending));
                   return;
                        }
               long startTime = this.mPrefetchManager.getCurrentSyncStartTime();
                        if (startTime > 0) {
                   this.mSyncDescription.setText(activity.getString(R.string.sync_now_in_progress));
                   return;
                        }
               long lastSync = this.mPrefetchManager.getLastSync();
               String text = null;
                        if (lastSync > 0) {
                   text = activity.getResources().getString(R.string.sync_now_desc, Util.getRelativeTimeString(activity, lastSync, true));
                        }
               if (SLog.sLogsOn) {
                   long next = this.mPrefetchManager.getNextSync();
                            if (next > -1) {
                       this.mNext = (int) (next - System.currentTimeMillis());
                            } else {
                       this.mNext = (int) next;
                            }
                   if (this.mNext >= 0) {
                       if (text == null) {
                           text = "";
                                }
                       text = text + " , next sync: " + (((long) this.mNext) / CommonUtil.MINUTE_MILLIS) + "m later";
                            }
                        }
               this.mSyncDescription.setText(text);
                    }
                }

       private boolean initPrefetchManager() {
           if (this.mPrefetchManager == null) {
               VineApplication va = VineApplication.getInstance();
               if (va != null) {
                   this.mPrefetchManager = PrefetchManager.getInstance(va.getApplicationContext());
                        } else {
                   FragmentActivity activity = getActivity();
                   if (activity != null) {
                       this.mPrefetchManager = PrefetchManager.getInstance(activity);
                            }
                        }
                    }
                    return this.mPrefetchManager != null;
                }

       private boolean isEnabled() {
                    return this.mPrefetchManager != null && this.mPrefetchManager.isEnabled();
                }

       private long getInterval() {
           if (this.mPrefetchManager != null) {
                        return this.mPrefetchManager.getInterval();
                    }
                    return -1L;
                }

       private long getLastSync() {
           if (this.mPrefetchManager != null) {
                        return this.mPrefetchManager.getLastSync();
                    }
                    return -1L;
                }

       private boolean isWifiOnly() {
                    return this.mPrefetchManager == null || this.mPrefetchManager.isWifiOnly();
                }

       private boolean isRoamingAllowed() {
                    return this.mPrefetchManager == null || this.mPrefetchManager.isRoamingAllowed();
                }

       private boolean isChargingRequired() {
                    return this.mPrefetchManager == null || this.mPrefetchManager.isChargingRequired();
                }

                
       class C03032 implements CompoundButton.OnCheckedChangeListener {
           C03032() {
                    }

                    @Override 
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               SyncControlsFragment.access$400(SyncControlsFragment.this);
               if (isChecked != SyncControlsFragment.access$500(SyncControlsFragment.this) && SyncControlsFragment.access$600(SyncControlsFragment.this) != null) {
                   SyncControlsFragment.access$600(SyncControlsFragment.this).setEnabled(isChecked);
                   SyncControlsFragment.access$200(SyncControlsFragment.this);
                        }
                    }
                }

                @Override 
       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.settings_sub, container, false);
           this.mSyncOnOffSwitcher = addSwitcher(inflater, vg, R.string.sync_enabled, R.string.sync_enabled_desc, new C03032());
           View intervalView = addText(inflater, vg, R.string.sync_interval, R.string.sync_interval_desc);
           this.mIntervalDescription = (TextView) intervalView.findViewById(R.id.description);
           intervalView.setOnClickListener(new ViewOnClickListenerC03043());
           this.mChargingRequiredSwitcher = addSwitcher(inflater, vg, R.string.while_charging_only, R.string.while_charging_only_desc, new C03054());
           this.mWifiOnlySwitcher = addSwitcher(inflater, vg, R.string.while_wifi_only, R.string.while_wifi_only_desc, new C03065());
           this.mRoamingAllowedSwitcher = addSwitcher(inflater, vg, R.string.roaming, R.string.roaming_desc, new C03076());
           ViewGroup doitNow = (ViewGroup) addText(inflater, vg, R.string.sync_now, R.string.sync_now_never);
           doitNow.setOnClickListener(new ViewOnClickListenerC03087());
           this.mSyncDescription = (TextView) doitNow.findViewById(R.id.description);
           return vg;
                }

                
       class ViewOnClickListenerC03043 implements View.OnClickListener {
           ViewOnClickListenerC03043() {
                    }

                    @Override 
           public void onClick(View v) {
               FragmentActivity activity = SyncControlsFragment.this.getActivity();
               if (activity != null && SyncControlsFragment.access$700(SyncControlsFragment.this) != null) {
                   PromptDialogFragment p = PromptDialogFragment.newInstance(1);
                   p.setItems(SyncControlsFragment.access$700(SyncControlsFragment.this));
                   p.setListener(new AnonymousClass1());
                   p.show(activity.getFragmentManager());
                        }
                    }

                    
           class AnonymousClass1 implements PromptDialogFragment.OnDialogDoneListener {
               AnonymousClass1() {
                        }

                        @Override 
               public void onDialogDone(DialogInterface dialog, int id, int which) {
                   if (which >= 0 && id == 1 && which < SyncControlsFragment.access$800().length) {
                       if (SyncControlsFragment.access$600(SyncControlsFragment.this) != null) {
                           SyncControlsFragment.access$600(SyncControlsFragment.this).setInterval(SyncControlsFragment.access$800()[which]);
                           SyncControlsFragment.access$600(SyncControlsFragment.this).cancelNextPrefetch();
                           SyncControlsFragment.access$600(SyncControlsFragment.this).scheduleNextPrefetch(true);
                                }
                       SyncControlsFragment.access$900(SyncControlsFragment.this);
                            }
                        }
                    }
                }

                
       class C03054 implements CompoundButton.OnCheckedChangeListener {
           C03054() {
                    }

                    @Override 
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               SyncControlsFragment.access$400(SyncControlsFragment.this);
               if (isChecked != SyncControlsFragment.access$1000(SyncControlsFragment.this)) {
                   if (SyncControlsFragment.access$600(SyncControlsFragment.this) != null) {
                       SyncControlsFragment.access$600(SyncControlsFragment.this).setChargingRequired(isChecked);
                            } else {
                       buttonView.setChecked(SyncControlsFragment.access$1000(SyncControlsFragment.this));
                            }
                        }
                    }
                }

                
       class C03065 implements CompoundButton.OnCheckedChangeListener {
           C03065() {
                    }

                    @Override 
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               SyncControlsFragment.access$400(SyncControlsFragment.this);
               if (isChecked != SyncControlsFragment.access$1100(SyncControlsFragment.this)) {
                   if (SyncControlsFragment.access$600(SyncControlsFragment.this) != null) {
                       SyncControlsFragment.access$600(SyncControlsFragment.this).setWifiOnly(isChecked);
                            } else {
                       buttonView.setChecked(SyncControlsFragment.access$1100(SyncControlsFragment.this));
                            }
                        }
                    }
                }

                
       class C03076 implements CompoundButton.OnCheckedChangeListener {
           C03076() {
                    }

                    @Override 
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               SyncControlsFragment.access$400(SyncControlsFragment.this);
               if (isChecked != SyncControlsFragment.access$1200(SyncControlsFragment.this)) {
                   if (SyncControlsFragment.access$600(SyncControlsFragment.this) != null) {
                       SyncControlsFragment.access$600(SyncControlsFragment.this).setRoamingAllowed(isChecked);
                            } else {
                       buttonView.setChecked(SyncControlsFragment.access$1200(SyncControlsFragment.this));
                            }
                        }
                    }
                }

                
       class ViewOnClickListenerC03087 implements View.OnClickListener {
           ViewOnClickListenerC03087() {
                    }

                    @Override 
           public void onClick(View v) {
               SyncControlsFragment.access$400(SyncControlsFragment.this);
               if (SyncControlsFragment.access$600(SyncControlsFragment.this) != null) {
                   SyncControlsFragment.access$600(SyncControlsFragment.this).run(0L);
                   SyncControlsFragment.access$300(SyncControlsFragment.this).postDelayed(new AnonymousClass1(), 100L);
                        }
                    }

                    
           class AnonymousClass1 implements Runnable {
               AnonymousClass1() {
                        }

                        @Override 
               public void run() {
                   SyncControlsFragment.access$200(SyncControlsFragment.this);
                        }
                    }
                }

       private View addText(LayoutInflater inflater, ViewGroup parent, int labelString, int descString) {
           View view = inflater.inflate(R.layout.settings_text_row, parent, false);
           ((TextView) view.findViewById(R.id.label)).setText(labelString);
           ((TextView) view.findViewById(R.id.description)).setText(descString);
           parent.addView(view);
           inflater.inflate(R.layout.settings_sub_divider, parent, true);
           return view;
                }

       private SwitchInterface addSwitcher(LayoutInflater inflater, ViewGroup parent, int labelString, int descString, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
           View view = inflater.inflate(R.layout.settings_switch_row, parent, false);
           SwitchInterface switcher = (SwitchInterface) view.findViewById(R.id.switcher);
           switcher.setOnCheckedChangeListener(onCheckedChangeListener);
           ((TextView) view.findViewById(R.id.label)).setText(labelString);
           ((TextView) view.findViewById(R.id.description)).setText(descString);
           parent.addView(view);
           inflater.inflate(R.layout.settings_sub_divider, parent, true);
           return switcher;
                }
            }
