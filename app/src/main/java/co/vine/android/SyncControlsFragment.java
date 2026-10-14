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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public class SyncControlsFragment extends BaseControllerFragment {
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

/* 28 */        static /* synthetic */ PrefetchManager.PrefetchFilter access$000(SyncControlsFragment x0) {
/* 29 */            return x0.mFilter;
                }

/* 28 */        static /* synthetic */ boolean access$1000(SyncControlsFragment x0) {
/* 29 */            return x0.isChargingRequired();
                }

/* 28 */        static /* synthetic */ int access$102(SyncControlsFragment x0, int x1) {
/* 29 */            x0.mNext = x1;
                    return x1;
                }

/* 28 */        static /* synthetic */ boolean access$1100(SyncControlsFragment x0) {
/* 29 */            return x0.isWifiOnly();
                }

/* 28 */        static /* synthetic */ boolean access$1200(SyncControlsFragment x0) {
/* 29 */            return x0.isRoamingAllowed();
                }

/* 28 */        static /* synthetic */ void access$200(SyncControlsFragment x0) {
/* 29 */            x0.updateSyncDescription();
                }

/* 28 */        static /* synthetic */ Handler access$300(SyncControlsFragment x0) {
/* 29 */            return x0.mHandler;
                }

/* 28 */        static /* synthetic */ boolean access$400(SyncControlsFragment x0) {
/* 29 */            return x0.initPrefetchManager();
                }

/* 28 */        static /* synthetic */ boolean access$500(SyncControlsFragment x0) {
/* 29 */            return x0.isEnabled();
                }

/* 28 */        static /* synthetic */ PrefetchManager access$600(SyncControlsFragment x0) {
/* 29 */            return x0.mPrefetchManager;
                }

/* 28 */        static /* synthetic */ String[] access$700(SyncControlsFragment x0) {
/* 29 */            return x0.mIntervalStrings;
                }

/* 28 */        static /* synthetic */ long[] access$800() {
/* 29 */            return INTERVALS;
                }

/* 28 */        static /* synthetic */ void access$900(SyncControlsFragment x0) {
/* 29 */            x0.updateIntervalText();
                }

                /* JADX INFO: renamed from: co.vine.android.SyncControlsFragment$1 */
/* 48 */        class C03021 extends BroadcastReceiver {
/* 49 */            C03021() {
                    }

                    @Override // android.content.BroadcastReceiver
/* 52 */            public void onReceive(Context context, Intent intent) {
/* 53 */                int next = SyncControlsFragment.access$000(SyncControlsFragment.this).getScheduledTimeInSeconds(intent.getExtras());
/* 54 */                if (next >= -1) {
/* 55 */                    SyncControlsFragment.access$102(SyncControlsFragment.this, next);
                        }
/* 57 */                if (SyncControlsFragment.access$000(SyncControlsFragment.this).isEnd(intent.getAction())) {
/* 58 */                    SyncControlsFragment.access$300(SyncControlsFragment.this).postDelayed(new AnonymousClass1(), SyncControlsFragment.SYNC_UPDATE_DELAY);
                        } else {
/* 65 */                    SyncControlsFragment.access$200(SyncControlsFragment.this);
                        }
                    }

                    /* JADX INFO: renamed from: co.vine.android.SyncControlsFragment$1$1, reason: invalid class name */
/* 56 */            class AnonymousClass1 implements Runnable {
/* 57 */                AnonymousClass1() {
                        }

                        @Override // java.lang.Runnable
/* 60 */                public void run() {
/* 61 */                    SyncControlsFragment.access$200(SyncControlsFragment.this);
                        }
                    }
                }

                @Override // co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 71 */        public void onResume() {
/* 72 */            super.onResume();
                    try {
/* 74 */                getActivity().registerReceiver(this.mReceiver, this.mFilter);
                    } catch (Exception e) {
/* 76 */                SLog.m459e("Receiver may have already been registered.", (Throwable) e);
                    }
                }

                @Override // co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 81 */        public void onPause() {
/* 82 */            super.onPause();
                    try {
/* 84 */                getActivity().unregisterReceiver(this.mReceiver);
                    } catch (Exception e) {
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 91 */        public void onActivityCreated(Bundle savedInstanceState) {
/* 92 */            super.onActivityCreated(savedInstanceState);
/* 93 */            initPrefetchManager();
/* 94 */            initIntervalArray(getActivity());
/* 95 */            this.mSyncOnOffSwitcher.setChecked(isEnabled());
/* 96 */            this.mChargingRequiredSwitcher.setChecked(isChargingRequired());
/* 97 */            this.mWifiOnlySwitcher.setChecked(isWifiOnly());
/* 98 */            this.mRoamingAllowedSwitcher.setChecked(isRoamingAllowed());
/* 99 */            updateSyncDescription();
/* 100 */           updateIntervalText();
                }

/* 103 */       private void updateIntervalText() {
                    String text;
/* 104 */           FragmentActivity activity = getActivity();
/* 105 */           if (activity != null) {
/* 107 */               Resources res = activity.getResources();
/* 108 */               int minutes = (int) (getInterval() / CommonUtil.MINUTE_MILLIS);
/* 109 */               if (minutes >= 60) {
/* 110 */                   text = res.getQuantityString(C0257R.plurals.time_hours_verbose, minutes / 60, Integer.valueOf(minutes / 60));
                        } else {
/* 113 */                   text = res.getQuantityString(C0257R.plurals.time_mins_verbose, minutes, Integer.valueOf(minutes));
                        }
/* 116 */               this.mIntervalDescription.setText(res.getString(C0257R.string.sync_interval_desc, text));
                    }
                }

/* 120 */       private void initIntervalArray(FragmentActivity activity) {
/* 121 */           if (this.mIntervalStrings == null) {
/* 122 */               this.mIntervalStrings = new String[INTERVALS.length];
/* 123 */               Resources res = activity.getResources();
                        for (int i = 0; i < INTERVALS.length; i++) {
/* 125 */                   int minutes = (int) (INTERVALS[i] / CommonUtil.MINUTE_MILLIS);
/* 126 */                   if (minutes >= 60) {
/* 127 */                       this.mIntervalStrings[i] = res.getString(C0257R.string.sync_interval_desc, res.getQuantityString(C0257R.plurals.time_hours_verbose, minutes / 60, Integer.valueOf(minutes / 60)));
                            } else {
/* 131 */                       this.mIntervalStrings[i] = res.getString(C0257R.string.sync_interval_desc, res.getQuantityString(C0257R.plurals.time_mins_verbose, minutes, Integer.valueOf(minutes)));
                            }
                        }
                    }
                }

/* 139 */       private void updateSyncDescription() {
/* 140 */           FragmentActivity activity = getActivity();
/* 141 */           if (this.mSyncDescription != null && this.mPrefetchManager != null && activity != null) {
/* 142 */               if (this.mPrefetchManager.isSyncPending()) {
/* 143 */                   this.mSyncDescription.setText(activity.getString(C0257R.string.sync_now_pending));
/* 175 */                   return;
                        }
/* 146 */               long startTime = this.mPrefetchManager.getCurrentSyncStartTime();
                        if (startTime > 0) {
/* 148 */                   this.mSyncDescription.setText(activity.getString(C0257R.string.sync_now_in_progress));
/* 175 */                   return;
                        }
/* 151 */               long lastSync = this.mPrefetchManager.getLastSync();
/* 152 */               String text = null;
                        if (lastSync > 0) {
/* 154 */                   text = activity.getResources().getString(C0257R.string.sync_now_desc, Util.getRelativeTimeString(activity, lastSync, true));
                        }
/* 157 */               if (SLog.sLogsOn) {
/* 158 */                   long next = this.mPrefetchManager.getNextSync();
                            if (next > -1) {
/* 160 */                       this.mNext = (int) (next - System.currentTimeMillis());
                            } else {
/* 162 */                       this.mNext = (int) next;
                            }
/* 164 */                   if (this.mNext >= 0) {
/* 165 */                       if (text == null) {
/* 166 */                           text = "";
                                }
/* 168 */                       text = text + " , next sync: " + (((long) this.mNext) / CommonUtil.MINUTE_MILLIS) + "m later";
                            }
                        }
/* 171 */               this.mSyncDescription.setText(text);
                    }
                }

/* 177 */       private boolean initPrefetchManager() {
/* 178 */           if (this.mPrefetchManager == null) {
/* 179 */               VineApplication va = VineApplication.getInstance();
/* 180 */               if (va != null) {
/* 181 */                   this.mPrefetchManager = PrefetchManager.getInstance(va.getApplicationContext());
                        } else {
/* 183 */                   FragmentActivity activity = getActivity();
/* 184 */                   if (activity != null) {
/* 185 */                       this.mPrefetchManager = PrefetchManager.getInstance(activity);
                            }
                        }
                    }
                    return this.mPrefetchManager != null;
                }

/* 192 */       private boolean isEnabled() {
                    return this.mPrefetchManager != null && this.mPrefetchManager.isEnabled();
                }

/* 196 */       private long getInterval() {
/* 197 */           if (this.mPrefetchManager != null) {
                        return this.mPrefetchManager.getInterval();
                    }
                    return -1L;
                }

/* 200 */       private long getLastSync() {
/* 201 */           if (this.mPrefetchManager != null) {
                        return this.mPrefetchManager.getLastSync();
                    }
                    return -1L;
                }

/* 204 */       private boolean isWifiOnly() {
                    return this.mPrefetchManager == null || this.mPrefetchManager.isWifiOnly();
                }

/* 208 */       private boolean isRoamingAllowed() {
                    return this.mPrefetchManager == null || this.mPrefetchManager.isRoamingAllowed();
                }

/* 212 */       private boolean isChargingRequired() {
                    return this.mPrefetchManager == null || this.mPrefetchManager.isChargingRequired();
                }

                /* JADX INFO: renamed from: co.vine.android.SyncControlsFragment$2 */
/* 218 */       class C03032 implements CompoundButton.OnCheckedChangeListener {
/* 219 */           C03032() {
                    }

                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
/* 223 */           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
/* 224 */               SyncControlsFragment.access$400(SyncControlsFragment.this);
/* 225 */               if (isChecked != SyncControlsFragment.access$500(SyncControlsFragment.this) && SyncControlsFragment.access$600(SyncControlsFragment.this) != null) {
/* 227 */                   SyncControlsFragment.access$600(SyncControlsFragment.this).setEnabled(isChecked);
/* 228 */                   SyncControlsFragment.access$200(SyncControlsFragment.this);
                        }
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 218 */       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 219 */           ViewGroup vg = (ViewGroup) inflater.inflate(C0257R.layout.settings_sub, container, false);
/* 220 */           this.mSyncOnOffSwitcher = addSwitcher(inflater, vg, C0257R.string.sync_enabled, C0257R.string.sync_enabled_desc, new C03032());
/* 233 */           View intervalView = addText(inflater, vg, C0257R.string.sync_interval, C0257R.string.sync_interval_desc);
/* 234 */           this.mIntervalDescription = (TextView) intervalView.findViewById(C0257R.id.description);
/* 235 */           intervalView.setOnClickListener(new ViewOnClickListenerC03043());
/* 259 */           this.mChargingRequiredSwitcher = addSwitcher(inflater, vg, C0257R.string.while_charging_only, C0257R.string.while_charging_only_desc, new C03054());
/* 273 */           this.mWifiOnlySwitcher = addSwitcher(inflater, vg, C0257R.string.while_wifi_only, C0257R.string.while_wifi_only_desc, new C03065());
/* 287 */           this.mRoamingAllowedSwitcher = addSwitcher(inflater, vg, C0257R.string.roaming, C0257R.string.roaming_desc, new C03076());
/* 301 */           ViewGroup doitNow = (ViewGroup) addText(inflater, vg, C0257R.string.sync_now, C0257R.string.sync_now_never);
/* 302 */           doitNow.setOnClickListener(new ViewOnClickListenerC03087());
/* 317 */           this.mSyncDescription = (TextView) doitNow.findViewById(C0257R.id.description);
/* 318 */           return vg;
                }

                /* JADX INFO: renamed from: co.vine.android.SyncControlsFragment$3 */
/* 233 */       class ViewOnClickListenerC03043 implements View.OnClickListener {
/* 234 */           ViewOnClickListenerC03043() {
                    }

                    @Override // android.view.View.OnClickListener
/* 237 */           public void onClick(View v) {
/* 238 */               FragmentActivity activity = SyncControlsFragment.this.getActivity();
/* 239 */               if (activity != null && SyncControlsFragment.access$700(SyncControlsFragment.this) != null) {
/* 240 */                   PromptDialogFragment p = PromptDialogFragment.newInstance(1);
/* 241 */                   p.setItems(SyncControlsFragment.access$700(SyncControlsFragment.this));
/* 242 */                   p.setListener(new AnonymousClass1());
/* 255 */                   p.show(activity.getFragmentManager());
                        }
                    }

                    /* JADX INFO: renamed from: co.vine.android.SyncControlsFragment$3$1, reason: invalid class name */
/* 240 */           class AnonymousClass1 implements PromptDialogFragment.OnDialogDoneListener {
/* 241 */               AnonymousClass1() {
                        }

                        @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 244 */               public void onDialogDone(DialogInterface dialog, int id, int which) {
/* 245 */                   if (which >= 0 && id == 1 && which < SyncControlsFragment.access$800().length) {
/* 246 */                       if (SyncControlsFragment.access$600(SyncControlsFragment.this) != null) {
/* 247 */                           SyncControlsFragment.access$600(SyncControlsFragment.this).setInterval(SyncControlsFragment.access$800()[which]);
/* 248 */                           SyncControlsFragment.access$600(SyncControlsFragment.this).cancelNextPrefetch();
/* 249 */                           SyncControlsFragment.access$600(SyncControlsFragment.this).scheduleNextPrefetch(true);
                                }
/* 251 */                       SyncControlsFragment.access$900(SyncControlsFragment.this);
                            }
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.SyncControlsFragment$4 */
/* 257 */       class C03054 implements CompoundButton.OnCheckedChangeListener {
/* 258 */           C03054() {
                    }

                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
/* 262 */           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
/* 263 */               SyncControlsFragment.access$400(SyncControlsFragment.this);
/* 264 */               if (isChecked != SyncControlsFragment.access$1000(SyncControlsFragment.this)) {
/* 265 */                   if (SyncControlsFragment.access$600(SyncControlsFragment.this) != null) {
/* 266 */                       SyncControlsFragment.access$600(SyncControlsFragment.this).setChargingRequired(isChecked);
                            } else {
/* 268 */                       buttonView.setChecked(SyncControlsFragment.access$1000(SyncControlsFragment.this));
                            }
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.SyncControlsFragment$5 */
/* 271 */       class C03065 implements CompoundButton.OnCheckedChangeListener {
/* 272 */           C03065() {
                    }

                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
/* 276 */           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
/* 277 */               SyncControlsFragment.access$400(SyncControlsFragment.this);
/* 278 */               if (isChecked != SyncControlsFragment.access$1100(SyncControlsFragment.this)) {
/* 279 */                   if (SyncControlsFragment.access$600(SyncControlsFragment.this) != null) {
/* 280 */                       SyncControlsFragment.access$600(SyncControlsFragment.this).setWifiOnly(isChecked);
                            } else {
/* 282 */                       buttonView.setChecked(SyncControlsFragment.access$1100(SyncControlsFragment.this));
                            }
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.SyncControlsFragment$6 */
/* 285 */       class C03076 implements CompoundButton.OnCheckedChangeListener {
/* 286 */           C03076() {
                    }

                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
/* 290 */           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
/* 291 */               SyncControlsFragment.access$400(SyncControlsFragment.this);
/* 292 */               if (isChecked != SyncControlsFragment.access$1200(SyncControlsFragment.this)) {
/* 293 */                   if (SyncControlsFragment.access$600(SyncControlsFragment.this) != null) {
/* 294 */                       SyncControlsFragment.access$600(SyncControlsFragment.this).setRoamingAllowed(isChecked);
                            } else {
/* 296 */                       buttonView.setChecked(SyncControlsFragment.access$1200(SyncControlsFragment.this));
                            }
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.SyncControlsFragment$7 */
/* 300 */       class ViewOnClickListenerC03087 implements View.OnClickListener {
/* 301 */           ViewOnClickListenerC03087() {
                    }

                    @Override // android.view.View.OnClickListener
/* 304 */           public void onClick(View v) {
/* 305 */               SyncControlsFragment.access$400(SyncControlsFragment.this);
/* 306 */               if (SyncControlsFragment.access$600(SyncControlsFragment.this) != null) {
/* 307 */                   SyncControlsFragment.access$600(SyncControlsFragment.this).run(0L);
/* 308 */                   SyncControlsFragment.access$300(SyncControlsFragment.this).postDelayed(new AnonymousClass1(), 100L);
                        }
                    }

                    /* JADX INFO: renamed from: co.vine.android.SyncControlsFragment$7$1, reason: invalid class name */
/* 306 */           class AnonymousClass1 implements Runnable {
/* 307 */               AnonymousClass1() {
                        }

                        @Override // java.lang.Runnable
/* 310 */               public void run() {
/* 311 */                   SyncControlsFragment.access$200(SyncControlsFragment.this);
                        }
                    }
                }

/* 322 */       private View addText(LayoutInflater inflater, ViewGroup parent, int labelString, int descString) {
/* 323 */           View view = inflater.inflate(C0257R.layout.settings_text_row, parent, false);
/* 324 */           ((TextView) view.findViewById(C0257R.id.label)).setText(labelString);
/* 325 */           ((TextView) view.findViewById(C0257R.id.description)).setText(descString);
/* 326 */           parent.addView(view);
/* 327 */           inflater.inflate(C0257R.layout.settings_sub_divider, parent, true);
/* 328 */           return view;
                }

/* 332 */       private SwitchInterface addSwitcher(LayoutInflater inflater, ViewGroup parent, int labelString, int descString, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
/* 333 */           View view = inflater.inflate(C0257R.layout.settings_switch_row, parent, false);
/* 334 */           SwitchInterface switcher = (SwitchInterface) view.findViewById(C0257R.id.switcher);
/* 335 */           switcher.setOnCheckedChangeListener(onCheckedChangeListener);
/* 336 */           ((TextView) view.findViewById(C0257R.id.label)).setText(labelString);
/* 337 */           ((TextView) view.findViewById(C0257R.id.description)).setText(descString);
/* 338 */           parent.addView(view);
/* 339 */           inflater.inflate(C0257R.layout.settings_sub_divider, parent, true);
/* 340 */           return switcher;
                }
            }
