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
            
            
    public class DebugHomeActivity extends BaseControllerActionBarActivity {
                private static final boolean HIDE_SENSITIVE_DATA;

        static  void access$000(DebugHomeActivity x0, String x1) {
            x0.handleDebugQuery(x1);
                }

                static {
            HIDE_SENSITIVE_DATA = !BuildUtil.isLogsOn();
                }

                @Override 
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, C0257R.layout.debug_home, true);
            Session session = this.mAppController.getActiveSession();
            TextView tv = (TextView) findViewById(C0257R.id.debuginfo);
            Button b = (Button) findViewById(C0257R.id.writeSessionKeyToLog);
            if (!HIDE_SENSITIVE_DATA) {
                b.setOnClickListener(new ViewOnClickListenerC01971(session));
                    } else {
                b.setVisibility(8);
                    }
            setupActionBar((Boolean) true, (Boolean) true, (String) null, (Boolean) true, (Boolean) true);
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setTitle("Debug Screen");
                    }
            StringBuilder debugInfo = new StringBuilder();
            debugInfo.append("Device: " + Build.DEVICE);
            debugInfo.append("\nModel: " + Build.MODEL);
            debugInfo.append("\nCPU ABI - ABI2: " + Build.CPU_ABI + " - " + Build.CPU_ABI2);
            debugInfo.append("\nHardware: " + Build.HARDWARE);
            debugInfo.append("\nBrand: " + Build.BRAND);
            debugInfo.append("\nProduct: " + Build.PRODUCT);
            debugInfo.append("\nManufacturer: " + Build.MANUFACTURER);
            debugInfo.append("\nBoard: " + Build.BOARD);
            debugInfo.append("\nCores: " + getNumCores() + IOUtils.LINE_SEPARATOR_UNIX);
            debugInfo.append("\nAndroid OS Version: " + Build.VERSION.RELEASE + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + Build.VERSION.SDK_INT + Build.VERSION.INCREMENTAL + " (" + Build.VERSION.CODENAME + ")");
            debugInfo.append("\nApp: ");
            if (BuildUtil.isExplore()) {
                debugInfo.append("Explore\n");
                    } else if (BuildUtil.isRegular()) {
                debugInfo.append("Main\n");
                    } else {
                debugInfo.append("Other\n");
                    }
           long dbSize = VineDatabaseHelper.getDatabasePath(this).length();
           debugInfo.append("Main database size: " + (dbSize / 1000000.0d) + " MB\n");
           debugInfo.append("Authority: " + BuildUtil.getAuthority("") + IOUtils.LINE_SEPARATOR_UNIX);
           debugInfo.append("Vine Player Enabled: " + SdkVideoView.getGlobalUseVinePlayer() + IOUtils.LINE_SEPARATOR_UNIX);
           appendMemoryInfo(debugInfo);
                    try {
               PackageInfo pi = getPackageManager().getPackageInfo("co.vine.android", 0);
               debugInfo.append("\nVine for Android Version: ").append(pi.versionName);
               debugInfo.append("\nVine for Android Build: ").append(pi.versionCode);
               if (!HIDE_SENSITIVE_DATA) {
                   debugInfo.append("\nInstalled: ").append(Util.DATE_TIME_RFC1123.format(new Date(pi.firstInstallTime)));
                   debugInfo.append("\nUpdated: ").append(Util.DATE_TIME_RFC1123.format(new Date(pi.lastUpdateTime)));
                        }
                    } catch (PackageManager.NameNotFoundException e) {
               SLog.m459e("Cannot parse some info.", (Throwable) e);
                    }
           debugInfo.append("\nHW Encoding Mode: " + BuildUtil.isIsHwEncodingEnabled() + IOUtils.LINE_SEPARATOR_UNIX);
           debugInfo.append("Build Market: ");
                    switch (BuildUtil.getMarket()) {
                        case 1:
                   debugInfo.append("Google\n");
                            break;
                        case 2:
                   debugInfo.append("Amazon\n");
                            break;
                        case 3:
                   debugInfo.append("Astar\n");
                            break;
                        default:
                   debugInfo.append("Other\n");
                            break;
                    }
           DisplayMetrics metrics = new DisplayMetrics();
           getWindowManager().getDefaultDisplay().getMetrics(metrics);
           debugInfo.append("\nScreen Density: ");
                    switch (metrics.densityDpi) {
                        case 120:
                   debugInfo.append(" LDPI");
                            break;
                        case avcodec.AV_CODEC_ID_CDXL :
                   debugInfo.append(" MDPI");
                            break;
                        case 213:
                   debugInfo.append(" TVDPI");
                            break;
                        case 240:
                   debugInfo.append(" HDPI");
                            break;
                        case 320:
                   debugInfo.append(" XHDPI");
                            break;
                        case 480:
                   debugInfo.append(" XXHDPI");
                            break;
                        default:
                   debugInfo.append(" UNKNOWN: " + metrics.densityDpi);
                            break;
                    }
           debugInfo.append("\nScreen Size: " + metrics.widthPixels + "x" + metrics.heightPixels);
           debugInfo.append("\nNormal Video Playable: " + SystemUtil.isNormalVideoPlayable(this) + " account: " + VineAccountHelper.isNormalVideoPlayable(this));
           debugInfo.append("\nLogged in user: ").append(session.getLoginEmail());
           if (!HIDE_SENSITIVE_DATA) {
               debugInfo.append("\nLogged in user id: ").append(session.getUserId());
               debugInfo.append("\nLogged in session key: ").append(session.getSessionKey());
                    }
           debugInfo.append("\nLogged in name: ").append(session.getName());
           if (!HIDE_SENSITIVE_DATA) {
               debugInfo.append("\nLogged in avatar: ").append(session.getAvatarUrl());
                    }
           AccountManager am = AccountManager.get(this);
           Account acc = VineAccountHelper.getAccount(this, this.mAppController.getActiveSession().getLoginEmail());
           if (acc != null) {
               if (Integer.parseInt(am.getUserData(acc, VineAccountHelper.KEY_LOGIN_TYPE)) == 2) {
                   debugInfo.append("\nLogged in with Twitter.");
                        } else {
                   debugInfo.append("\nNot logged in with Twitter.");
                        }
               if (TextUtils.isEmpty(VineAccountHelper.getTwitterToken(am, acc))) {
                   debugInfo.append("\nNot connected to Twitter.");
                        } else {
                   debugInfo.append("\nConnected to Twitter.");
                        }
                    }
           if (!HIDE_SENSITIVE_DATA) {
               SharedPreferences prefs = GCMRegistrationService.getGCMPreferences(this);
               debugInfo.append("\n\nGCM: " + prefs.getString(GCMRegistrationService.PREF_REG_ID, "Not registered"));
               debugInfo.append("\nGCM sent to vinepi: " + prefs.getBoolean(GCMRegistrationService.PREF_REG_ID_SENT, false));
               debugInfo.append("\n\nAPI: " + VineAPI.getInstance(this).getBaseUrl());
               debugInfo.append("\nAmazon: " + getString(C0257R.string.amazon_s3));
               debugInfo.append("\nAmazon Bucket: " + BuildUtil.getAmazonBucket(this));
               debugInfo.append("\nTwitter: " + getString(C0257R.string.twitter_api));
               debugInfo.append("\nRTC: " + VineAPI.getInstance(this).getRtcUrl());
               debugInfo.append("\nMedia: " + VineAPI.getInstance(this).getMediaUrl());
               debugInfo.append("\nExplore: " + VineAPI.getInstance(this).getExploreUrl());
                    }
           AppImpl.getInstance().appendDebugInfo(this, debugInfo, HIDE_SENSITIVE_DATA);
           debugInfo.append("Network Usages:\n");
           debugInfo.append("Data from HTTP Stack: " + Util.formatFileSize(getResources(), NetworkOperation.sNetworkDataUsed) + IOUtils.LINE_SEPARATOR_UNIX);
           debugInfo.append("Data from CACHE Layer: " + Util.formatFileSize(getResources(), NetworkOperation.sSavedDataSize) + IOUtils.LINE_SEPARATOR_UNIX);
           tv.setText(debugInfo.toString());
           Button button = new Button(this);
           if (BuildUtil.isLogsOn()) {
               button.setText("Send Logs");
               button.setOnClickListener(new ViewOnClickListenerC01982(debugInfo));
                    } else {
               button.setText("Cause Crash");
               button.setOnClickListener(new ViewOnClickListenerC01993());
                    }
           ((ViewGroup) tv.getParent()).addView(button);
           if (!HIDE_SENSITIVE_DATA) {
               EditText query = (EditText) findViewById(C0257R.id.debug_query);
               findViewById(C0257R.id.debug_query_submit).setOnClickListener(new ViewOnClickListenerC02004(query));
                    } else {
               LinearLayout debugQueryParent = (LinearLayout) findViewById(C0257R.id.debug_query_parent);
               debugQueryParent.setVisibility(8);
                    }
                }

                
        class ViewOnClickListenerC01971 implements View.OnClickListener {
                    final  Session val$session;

            ViewOnClickListenerC01971(Session session) {
                        this.val$session = session;
                    }

                    @Override 
            public void onClick(View view) {
                Log.d("VINEDEBUG", "vine-session-id: " + this.val$session.getSessionKey());
                    }
                }

                
       class ViewOnClickListenerC01982 implements View.OnClickListener {
                    final  StringBuilder val$debugInfo;

           ViewOnClickListenerC01982(StringBuilder sb) {
                        this.val$debugInfo = sb;
                    }

                    @Override 
           public void onClick(View v) {
               File file = new File(DebugHomeActivity.this.getExternalCacheDir(), "vine_log.txt");
                        try {
                   OutputStreamWriter stream = new OutputStreamWriter(new FileOutputStream(file));
                   stream.write(this.val$debugInfo.toString());
                   stream.flush();
                   stream.close();
                   Intent intent = new Intent("co.vine.android.intent.action.SEND_LOGS");
                   DebugHomeActivity.this.startActivity(intent);
                        } catch (IOException e) {
                   Toast.makeText(DebugHomeActivity.this, "Failed to send log.", 0).show();
                        }
                    }
                }

                
       class ViewOnClickListenerC01993 implements View.OnClickListener {

                    
                    int f80i = 0;

           ViewOnClickListenerC01993() {
                    }

                    @Override 
           public void onClick(View v) {
               this.f80i++;
               if (this.f80i >= 3) {
                   throw new RuntimeException(new VineLoggingException());
                        }
                    }
                }

                
       class ViewOnClickListenerC02004 implements View.OnClickListener {
                    final  EditText val$query;

           ViewOnClickListenerC02004(EditText editText) {
                        this.val$query = editText;
                    }

                    @Override 
           public void onClick(View v) {
               DebugHomeActivity.access$000(DebugHomeActivity.this, this.val$query.getText().toString());
                    }
                }

                @TargetApi(11)
       private void appendMemoryInfo(StringBuilder debugInfo) {
                    int mb;
           ActivityManager activityManager = (ActivityManager) getSystemService(HomeTabActivity.TAG_ACTIVITY);
           ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
           activityManager.getMemoryInfo(info);
           if (Build.VERSION.SDK_INT >= 11 && !BuildUtil.isExplore()) {
               mb = activityManager.getLargeMemoryClass();
                    } else {
               mb = activityManager.getMemoryClass();
                    }
           debugInfo.append("\nMemory Info: " + mb + "MB");
                }

       private void handleDebugQuery(String query) {
           if (query != null) {
               if (query.startsWith("p:")) {
                   SingleActivity.start(this, Long.parseLong(query.substring(2)));
                        }
               if (query.startsWith("s:")) {
                   SingleActivity.start(this, query.substring(2));
                        }
               if (query.startsWith("u:")) {
                   ProfileActivity.start(this, Long.parseLong(query.substring(2)), "Debug Screen");
                        }
                    }
                }

                
       class C1CpuFilter implements FileFilter {
           C1CpuFilter() {
                    }

                    @Override 
           public boolean accept(File pathname) {
                        return Pattern.matches("cpu[0-9]+", pathname.getName());
                    }
                }

       private int getNumCores() {
                    try {
               File dir = new File("/sys/devices/system/cpu/");
               File[] files = dir.listFiles(new C1CpuFilter());
               return files.length;
                    } catch (Exception e) {
               return 1;
                    }
                }
            }
