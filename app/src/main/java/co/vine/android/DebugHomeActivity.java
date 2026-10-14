            package co.vine.android;

            import android.accounts.Account;
            import android.accounts.AccountManager;
            import android.annotation.TargetApi;
            import android.app.ActivityManager;
            import android.content.Intent;
            import android.content.SharedPreferences;
            import android.content.pm.PackageInfo;
            import android.content.pm.PackageManager;
            import android.os.Build;
            import android.os.Bundle;
            import android.support.v7.app.ActionBar;
            import android.text.TextUtils;
            import android.util.DisplayMetrics;
            import android.util.Log;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.Button;
            import android.widget.EditText;
            import android.widget.LinearLayout;
            import android.widget.TextView;
            import android.widget.Toast;
            import co.vine.android.client.Session;
            import co.vine.android.client.VineAPI;
            import co.vine.android.client.VineAccountHelper;
            import co.vine.android.network.NetworkOperation;
            import co.vine.android.player.SdkVideoView;
            import co.vine.android.provider.VineDatabaseHelper;
            import co.vine.android.service.GCMRegistrationService;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.SystemUtil;
            import co.vine.android.util.Util;
            import com.edisonwang.android.slog.SLog;
            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import com.googlecode.javacv.cpp.avcodec;
            import java.io.File;
            import java.io.FileFilter;
            import java.io.FileOutputStream;
            import java.io.IOException;
            import java.io.OutputStreamWriter;
            import java.util.Date;
            import java.util.regex.Pattern;
            import org.apache.commons.io.IOUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 47 */    public class DebugHomeActivity extends BaseControllerActionBarActivity {
                private static final boolean HIDE_SENSITIVE_DATA;

/* 48 */        static /* synthetic */ void access$000(DebugHomeActivity x0, String x1) {
/* 49 */            x0.handleDebugQuery(x1);
                }

                static {
/* 50 */            HIDE_SENSITIVE_DATA = !BuildUtil.isLogsOn();
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 53 */        protected void onCreate(Bundle savedInstanceState) {
/* 54 */            super.onCreate(savedInstanceState, C0257R.layout.debug_home, true);
/* 56 */            Session session = this.mAppController.getActiveSession();
/* 58 */            TextView tv = (TextView) findViewById(C0257R.id.debuginfo);
/* 59 */            Button b = (Button) findViewById(C0257R.id.writeSessionKeyToLog);
/* 60 */            if (!HIDE_SENSITIVE_DATA) {
/* 61 */                b.setOnClickListener(new ViewOnClickListenerC01971(session));
                    } else {
/* 68 */                b.setVisibility(8);
                    }
/* 71 */            setupActionBar((Boolean) true, (Boolean) true, (String) null, (Boolean) true, (Boolean) true);
/* 73 */            ActionBar ab = getSupportActionBar();
/* 74 */            if (ab != null) {
/* 75 */                ab.setTitle("Debug Screen");
                    }
/* 77 */            StringBuilder debugInfo = new StringBuilder();
/* 79 */            debugInfo.append("Device: " + Build.DEVICE);
/* 80 */            debugInfo.append("\nModel: " + Build.MODEL);
/* 81 */            debugInfo.append("\nCPU ABI - ABI2: " + Build.CPU_ABI + " - " + Build.CPU_ABI2);
/* 82 */            debugInfo.append("\nHardware: " + Build.HARDWARE);
/* 83 */            debugInfo.append("\nBrand: " + Build.BRAND);
/* 84 */            debugInfo.append("\nProduct: " + Build.PRODUCT);
/* 85 */            debugInfo.append("\nManufacturer: " + Build.MANUFACTURER);
/* 86 */            debugInfo.append("\nBoard: " + Build.BOARD);
/* 87 */            debugInfo.append("\nCores: " + getNumCores() + IOUtils.LINE_SEPARATOR_UNIX);
/* 89 */            debugInfo.append("\nAndroid OS Version: " + Build.VERSION.RELEASE + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + Build.VERSION.SDK_INT + Build.VERSION.INCREMENTAL + " (" + Build.VERSION.CODENAME + ")");
/* 93 */            debugInfo.append("\nApp: ");
/* 94 */            if (BuildUtil.isExplore()) {
/* 95 */                debugInfo.append("Explore\n");
                    } else if (BuildUtil.isRegular()) {
/* 97 */                debugInfo.append("Main\n");
                    } else {
/* 99 */                debugInfo.append("Other\n");
                    }
/* 102 */           long dbSize = VineDatabaseHelper.getDatabasePath(this).length();
/* 104 */           debugInfo.append("Main database size: " + (dbSize / 1000000.0d) + " MB\n");
/* 105 */           debugInfo.append("Authority: " + BuildUtil.getAuthority("") + IOUtils.LINE_SEPARATOR_UNIX);
/* 106 */           debugInfo.append("Vine Player Enabled: " + SdkVideoView.getGlobalUseVinePlayer() + IOUtils.LINE_SEPARATOR_UNIX);
/* 108 */           appendMemoryInfo(debugInfo);
                    try {
/* 111 */               PackageInfo pi = getPackageManager().getPackageInfo("co.vine.android", 0);
/* 112 */               debugInfo.append("\nVine for Android Version: ").append(pi.versionName);
/* 113 */               debugInfo.append("\nVine for Android Build: ").append(pi.versionCode);
/* 114 */               if (!HIDE_SENSITIVE_DATA) {
/* 115 */                   debugInfo.append("\nInstalled: ").append(Util.DATE_TIME_RFC1123.format(new Date(pi.firstInstallTime)));
/* 116 */                   debugInfo.append("\nUpdated: ").append(Util.DATE_TIME_RFC1123.format(new Date(pi.lastUpdateTime)));
                        }
                    } catch (PackageManager.NameNotFoundException e) {
/* 119 */               SLog.m459e("Cannot parse some info.", (Throwable) e);
                    }
/* 122 */           debugInfo.append("\nHW Encoding Mode: " + BuildUtil.isIsHwEncodingEnabled() + IOUtils.LINE_SEPARATOR_UNIX);
/* 124 */           debugInfo.append("Build Market: ");
                    switch (BuildUtil.getMarket()) {
                        case 1:
/* 127 */                   debugInfo.append("Google\n");
                            break;
                        case 2:
/* 131 */                   debugInfo.append("Amazon\n");
                            break;
                        case 3:
/* 135 */                   debugInfo.append("Astar\n");
                            break;
                        default:
/* 139 */                   debugInfo.append("Other\n");
                            break;
                    }
/* 144 */           DisplayMetrics metrics = new DisplayMetrics();
/* 145 */           getWindowManager().getDefaultDisplay().getMetrics(metrics);
/* 147 */           debugInfo.append("\nScreen Density: ");
                    switch (metrics.densityDpi) {
                        case 120:
/* 150 */                   debugInfo.append(" LDPI");
                            break;
                        case avcodec.AV_CODEC_ID_CDXL /* 160 */:
/* 154 */                   debugInfo.append(" MDPI");
                            break;
                        case 213:
/* 162 */                   debugInfo.append(" TVDPI");
                            break;
                        case 240:
/* 158 */                   debugInfo.append(" HDPI");
                            break;
                        case 320:
/* 166 */                   debugInfo.append(" XHDPI");
                            break;
                        case 480:
/* 170 */                   debugInfo.append(" XXHDPI");
                            break;
                        default:
/* 174 */                   debugInfo.append(" UNKNOWN: " + metrics.densityDpi);
                            break;
                    }
/* 179 */           debugInfo.append("\nScreen Size: " + metrics.widthPixels + "x" + metrics.heightPixels);
/* 181 */           debugInfo.append("\nNormal Video Playable: " + SystemUtil.isNormalVideoPlayable(this) + " account: " + VineAccountHelper.isNormalVideoPlayable(this));
/* 183 */           debugInfo.append("\nLogged in user: ").append(session.getLoginEmail());
/* 184 */           if (!HIDE_SENSITIVE_DATA) {
/* 185 */               debugInfo.append("\nLogged in user id: ").append(session.getUserId());
/* 186 */               debugInfo.append("\nLogged in session key: ").append(session.getSessionKey());
                    }
/* 188 */           debugInfo.append("\nLogged in name: ").append(session.getName());
/* 189 */           if (!HIDE_SENSITIVE_DATA) {
/* 190 */               debugInfo.append("\nLogged in avatar: ").append(session.getAvatarUrl());
                    }
/* 193 */           AccountManager am = AccountManager.get(this);
/* 194 */           Account acc = VineAccountHelper.getAccount(this, this.mAppController.getActiveSession().getLoginEmail());
/* 196 */           if (acc != null) {
/* 197 */               if (Integer.parseInt(am.getUserData(acc, VineAccountHelper.KEY_LOGIN_TYPE)) == 2) {
/* 199 */                   debugInfo.append("\nLogged in with Twitter.");
                        } else {
/* 201 */                   debugInfo.append("\nNot logged in with Twitter.");
                        }
/* 203 */               if (TextUtils.isEmpty(VineAccountHelper.getTwitterToken(am, acc))) {
/* 204 */                   debugInfo.append("\nNot connected to Twitter.");
                        } else {
/* 206 */                   debugInfo.append("\nConnected to Twitter.");
                        }
                    }
/* 210 */           if (!HIDE_SENSITIVE_DATA) {
/* 211 */               SharedPreferences prefs = GCMRegistrationService.getGCMPreferences(this);
/* 212 */               debugInfo.append("\n\nGCM: " + prefs.getString(GCMRegistrationService.PREF_REG_ID, "Not registered"));
/* 213 */               debugInfo.append("\nGCM sent to vinepi: " + prefs.getBoolean(GCMRegistrationService.PREF_REG_ID_SENT, false));
/* 214 */               debugInfo.append("\n\nAPI: " + VineAPI.getInstance(this).getBaseUrl());
/* 215 */               debugInfo.append("\nAmazon: " + getString(C0257R.string.amazon_s3));
/* 216 */               debugInfo.append("\nAmazon Bucket: " + BuildUtil.getAmazonBucket(this));
/* 217 */               debugInfo.append("\nTwitter: " + getString(C0257R.string.twitter_api));
/* 218 */               debugInfo.append("\nRTC: " + VineAPI.getInstance(this).getRtcUrl());
/* 219 */               debugInfo.append("\nMedia: " + VineAPI.getInstance(this).getMediaUrl());
/* 220 */               debugInfo.append("\nExplore: " + VineAPI.getInstance(this).getExploreUrl());
                    }
/* 223 */           AppImpl.getInstance().appendDebugInfo(this, debugInfo, HIDE_SENSITIVE_DATA);
/* 225 */           debugInfo.append("Network Usages:\n");
/* 226 */           debugInfo.append("Data from HTTP Stack: " + Util.formatFileSize(getResources(), NetworkOperation.sNetworkDataUsed) + IOUtils.LINE_SEPARATOR_UNIX);
/* 227 */           debugInfo.append("Data from CACHE Layer: " + Util.formatFileSize(getResources(), NetworkOperation.sSavedDataSize) + IOUtils.LINE_SEPARATOR_UNIX);
/* 229 */           tv.setText(debugInfo.toString());
/* 231 */           Button button = new Button(this);
/* 232 */           if (BuildUtil.isLogsOn()) {
/* 233 */               button.setText("Send Logs");
/* 234 */               button.setOnClickListener(new ViewOnClickListenerC01982(debugInfo));
                    } else {
/* 251 */               button.setText("Cause Crash");
/* 252 */               button.setOnClickListener(new ViewOnClickListenerC01993());
                    }
/* 266 */           ((ViewGroup) tv.getParent()).addView(button);
/* 268 */           if (!HIDE_SENSITIVE_DATA) {
/* 269 */               EditText query = (EditText) findViewById(C0257R.id.debug_query);
/* 270 */               findViewById(C0257R.id.debug_query_submit).setOnClickListener(new ViewOnClickListenerC02004(query));
                    } else {
/* 277 */               LinearLayout debugQueryParent = (LinearLayout) findViewById(C0257R.id.debug_query_parent);
/* 278 */               debugQueryParent.setVisibility(8);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.DebugHomeActivity$1 */
/* 59 */        class ViewOnClickListenerC01971 implements View.OnClickListener {
                    final /* synthetic */ Session val$session;

/* 60 */            ViewOnClickListenerC01971(Session session) {
                        this.val$session = session;
                    }

                    @Override // android.view.View.OnClickListener
/* 63 */            public void onClick(View view) {
/* 64 */                Log.d("VINEDEBUG", "vine-session-id: " + this.val$session.getSessionKey());
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.DebugHomeActivity$2 */
/* 232 */       class ViewOnClickListenerC01982 implements View.OnClickListener {
                    final /* synthetic */ StringBuilder val$debugInfo;

/* 233 */           ViewOnClickListenerC01982(StringBuilder sb) {
                        this.val$debugInfo = sb;
                    }

                    @Override // android.view.View.OnClickListener
/* 236 */           public void onClick(View v) {
/* 237 */               File file = new File(DebugHomeActivity.this.getExternalCacheDir(), "vine_log.txt");
                        try {
/* 239 */                   OutputStreamWriter stream = new OutputStreamWriter(new FileOutputStream(file));
/* 240 */                   stream.write(this.val$debugInfo.toString());
/* 241 */                   stream.flush();
/* 242 */                   stream.close();
/* 243 */                   Intent intent = new Intent("co.vine.android.intent.action.SEND_LOGS");
/* 244 */                   DebugHomeActivity.this.startActivity(intent);
                        } catch (IOException e) {
/* 246 */                   Toast.makeText(DebugHomeActivity.this, "Failed to send log.", 0).show();
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.DebugHomeActivity$3 */
/* 250 */       class ViewOnClickListenerC01993 implements View.OnClickListener {

                    /* JADX INFO: renamed from: i */
                    int f80i = 0;

/* 251 */           ViewOnClickListenerC01993() {
                    }

                    @Override // android.view.View.OnClickListener
/* 257 */           public void onClick(View v) {
/* 258 */               this.f80i++;
/* 259 */               if (this.f80i >= 3) {
/* 260 */                   throw new RuntimeException(new VineLoggingException());
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.DebugHomeActivity$4 */
/* 268 */       class ViewOnClickListenerC02004 implements View.OnClickListener {
                    final /* synthetic */ EditText val$query;

/* 269 */           ViewOnClickListenerC02004(EditText editText) {
                        this.val$query = editText;
                    }

                    @Override // android.view.View.OnClickListener
/* 272 */           public void onClick(View v) {
/* 273 */               DebugHomeActivity.access$000(DebugHomeActivity.this, this.val$query.getText().toString());
                    }
                }

                @TargetApi(11)
/* 283 */       private void appendMemoryInfo(StringBuilder debugInfo) {
                    int mb;
/* 284 */           ActivityManager activityManager = (ActivityManager) getSystemService(HomeTabActivity.TAG_ACTIVITY);
/* 285 */           ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
/* 286 */           activityManager.getMemoryInfo(info);
/* 288 */           if (Build.VERSION.SDK_INT >= 11 && !BuildUtil.isExplore()) {
/* 289 */               mb = activityManager.getLargeMemoryClass();
                    } else {
/* 291 */               mb = activityManager.getMemoryClass();
                    }
/* 293 */           debugInfo.append("\nMemory Info: " + mb + "MB");
                }

/* 305 */       private void handleDebugQuery(String query) {
/* 306 */           if (query != null) {
/* 307 */               if (query.startsWith("p:")) {
/* 308 */                   SingleActivity.start(this, Long.parseLong(query.substring(2)));
                        }
/* 310 */               if (query.startsWith("s:")) {
/* 311 */                   SingleActivity.start(this, query.substring(2));
                        }
/* 313 */               if (query.startsWith("u:")) {
/* 314 */                   ProfileActivity.start(this, Long.parseLong(query.substring(2)), "Debug Screen");
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.DebugHomeActivity$1CpuFilter, reason: invalid class name */
/* 319 */       class C1CpuFilter implements FileFilter {
/* 320 */           C1CpuFilter() {
                    }

                    @Override // java.io.FileFilter
/* 324 */           public boolean accept(File pathname) {
                        return Pattern.matches("cpu[0-9]+", pathname.getName());
                    }
                }

/* 333 */       private int getNumCores() {
                    try {
/* 334 */               File dir = new File("/sys/devices/system/cpu/");
/* 336 */               File[] files = dir.listFiles(new C1CpuFilter());
/* 338 */               return files.length;
                    } catch (Exception e) {
/* 341 */               return 1;
                    }
                }
            }
