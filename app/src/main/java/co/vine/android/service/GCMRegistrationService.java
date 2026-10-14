            package co.vine.android.service;

            import android.app.Service;
            import android.content.Context;
            import android.content.Intent;
            import android.content.SharedPreferences;
            import android.content.pm.PackageInfo;
            import android.content.pm.PackageManager;
            import android.os.AsyncTask;
            import android.os.IBinder;
            import co.vine.android.client.AppController;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.util.BuildUtil;
            import com.edisonwang.android.slog.SLog;
            import com.google.android.gms.gcm.GoogleCloudMessaging;
            import java.io.IOException;
            import java.util.ArrayList;
            import java.util.Iterator;
            
            
    public class GCMRegistrationService extends Service {
                public static final String ACTION_REGISTER = "co.vine.android.gcm.REGISTER";
                public static final String ACTION_UNREGISTER = "co.vine.android.gcm.UNREGISTER";
                public static final String PREFS = "gcmPrefs";
                public static final String PREF_APP_VERSION = "registrationVersion";
                public static final String PREF_REG_EXPIRY_TIME = "registrationExpiryTime";
                public static final String PREF_REG_ID = "registrationId";
                public static final String PREF_REG_ID_SENT = "registrationIdSent";
                public static final long REGISTRATION_EXPIRY_TIME_MS = 604800000;
                private static final String TAG = "GCMRegService";
                private static SharedPreferences sPrefs;
                private AppController mAppController;
                private GoogleCloudMessaging mGcm;
                private AppSessionListener mGcmRegistrationListener;
                private String mSenderId;
                private ArrayList<Integer> mStartIds;

        static  GoogleCloudMessaging access$100(GCMRegistrationService x0) {
            return x0.mGcm;
                }

        static  GoogleCloudMessaging access$102(GCMRegistrationService x0, GoogleCloudMessaging x1) {
            x0.mGcm = x1;
                    return x1;
                }

        static  String access$200(GCMRegistrationService x0) {
            return x0.mSenderId;
                }

        static  void access$300(GCMRegistrationService x0, String x1) {
            x0.setRegistrationId(x1);
                }

        static  AppController access$400(GCMRegistrationService x0) {
            return x0.mAppController;
                }

        static  void access$500(GCMRegistrationService x0) {
            x0.stopService();
                }

        public static Intent getRegisterIntent(Context context, long userId) {
            Intent intent = new Intent(context, (Class<?>) GCMRegistrationService.class);
            intent.setAction(ACTION_REGISTER);
            intent.putExtra("user_id", userId);
            return intent;
                }

        public static Intent getUnregisterIntent(Context context, long userId, String key) {
            Intent intent = new Intent(context, (Class<?>) GCMRegistrationService.class);
            intent.setAction(ACTION_UNREGISTER);
            intent.putExtra("user_id", userId);
            intent.putExtra("s_key", key);
            return intent;
                }

                @Override 
        public void onCreate() {
            super.onCreate();
            this.mGcm = GoogleCloudMessaging.getInstance(this);
            this.mAppController = AppController.getInstance(this);
            this.mGcmRegistrationListener = new GCMRegistrationListener(this, null);
            if (this.mGcmRegistrationListener != null) {
                this.mAppController.addListener(this.mGcmRegistrationListener);
                    }
            this.mSenderId = BuildUtil.getSenderId(this);
            this.mStartIds = new ArrayList<>();
                }

                @Override 
        public int onStartCommand(Intent intent, int flags, int startId) {
            SLog.dWithTag(TAG, "onStartCommand, startId=" + startId + ", startIds=" + this.mStartIds.toString());
            this.mStartIds.add(Integer.valueOf(startId));
            if (intent != null) {
                String regId = getRegistrationId();
                SLog.dWithTag(TAG, "Starting GCM registration service with regId=" + regId);
                long userId = intent.getLongExtra("user_id", 0L);
                String action = intent.getAction();
                if (ACTION_REGISTER.equals(action)) {
                    if (regId == null) {
                        new GCMRegisterTask(userId).execute(new Void[0]);
                            } else if (!getGCMPreferences(this).getBoolean(PREF_REG_ID_SENT, false)) {
                        this.mAppController.sendGcmRegId(regId, userId);
                            } else {
                       stopService();
                            }
                        } else if (ACTION_UNREGISTER.equals(action)) {
                   String key = intent.getStringExtra("s_key");
                   new GCMUnregisterTask(regId, userId, key).execute(new Void[0]);
                        } else {
                   stopService();
                        }
                    }
                    return 2;
                }

       private void stopService() {
           Iterator<Integer> it = this.mStartIds.iterator();
                    while (it.hasNext()) {
                        int startId = it.next().intValue();
               SLog.dWithTag(TAG, "Stopping service for startId=" + startId);
               stopSelf(startId);
                    }
                }

       private String getRegistrationId() {
           SharedPreferences prefs = getGCMPreferences(this);
           String registrationId = prefs.getString(PREF_REG_ID, null);
           if (registrationId == null) {
               SLog.dWithTag(TAG, "Registration not found.");
                        return null;
                    }
           int registeredVersion = prefs.getInt(PREF_APP_VERSION, Integer.MIN_VALUE);
           int currentVersion = getAppVersion();
           if (registeredVersion != currentVersion || isRegistrationExpired()) {
               SLog.dWithTag(TAG, "App version changed or registration expired.");
                        return null;
                    }
           return registrationId;
                }

       private int getAppVersion() {
                    try {
               PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
               return packageInfo.versionCode;
                    } catch (PackageManager.NameNotFoundException e) {
               return -1;
                    }
                }

       private boolean isRegistrationExpired() {
           SharedPreferences prefs = getGCMPreferences(this);
           long expirationTime = prefs.getLong(PREF_REG_EXPIRY_TIME, -1L);
                    return System.currentTimeMillis() > expirationTime;
                }

       private void setRegistrationId(String regId) {
           SharedPreferences prefs = getGCMPreferences(this);
           int appVersion = getAppVersion();
           long expirationTime = System.currentTimeMillis() + 604800000;
           SharedPreferences.Editor editor = prefs.edit();
           editor.putBoolean(PREF_REG_ID_SENT, false);
           editor.putInt(PREF_APP_VERSION, appVersion);
           editor.putLong(PREF_REG_EXPIRY_TIME, expirationTime);
           editor.putString(PREF_REG_ID, regId);
           editor.apply();
                }

       public static SharedPreferences getGCMPreferences(Context context) {
           if (sPrefs == null) {
               sPrefs = context.getSharedPreferences(PREFS, 0);
                    }
           return sPrefs;
                }

                @Override 
       public void onDestroy() {
           super.onDestroy();
           if (this.mGcmRegistrationListener != null) {
               this.mAppController.removeListener(this.mGcmRegistrationListener);
                    }
           this.mGcm.close();
                }

       private class GCMRegisterTask extends AsyncTask<Void, Void, Void> {
                    private long mUserId;



           public GCMRegisterTask(long userId) {
               this.mUserId = userId;
                    }

                    
           protected Void doInBackground(Void... params) {
               if (GCMRegistrationService.access$100(GCMRegistrationService.this) == null) {
                   GCMRegistrationService.access$102(GCMRegistrationService.this, GoogleCloudMessaging.getInstance(getContext()));
                        }
                        try {
                   SLog.dWithTag(GCMRegistrationService.TAG, "Registering with senderId=" + GCMRegistrationService.access$200(GCMRegistrationService.this));
                   String regId = GCMRegistrationService.access$100(GCMRegistrationService.this).register(GCMRegistrationService.access$200(GCMRegistrationService.this));
                   SLog.m447d("GCM: Registration complete with regId={}", regId);
                   GCMRegistrationService.access$300(GCMRegistrationService.this, regId);
                   GCMRegistrationService.access$400(GCMRegistrationService.this).sendGcmRegId(regId, this.mUserId);
                   return null;
                        } catch (IOException e) {
                   SLog.dWithTag(GCMRegistrationService.TAG, "Failed to register with GCM service.");
                   return null;
                        } catch (SecurityException e2) {
                   return null;
                        }
                    }

                    
           protected void onPostExecute(Void aVoid) {
               GCMRegistrationService.access$500(GCMRegistrationService.this);
                    }

           private Context getContext() {
               return GCMRegistrationService.this;
                    }
                }

       private class GCMUnregisterTask extends AsyncTask<Void, Void, Void> {
                    private String mRegId;
                    private String mSessionKey;
                    private long mUserId;



           public GCMUnregisterTask(String regId, long userId, String key) {
               this.mRegId = regId;
               this.mUserId = userId;
               this.mSessionKey = key;
                    }

                    
           protected Void doInBackground(Void... voids) {
               if (GCMRegistrationService.access$100(GCMRegistrationService.this) == null) {
                   GCMRegistrationService.access$102(GCMRegistrationService.this, GoogleCloudMessaging.getInstance(getContext()));
                        }
               SLog.dWithTag(GCMRegistrationService.TAG, "Unregistering...");
               GCMRegistrationService.access$400(GCMRegistrationService.this).clearGcmRegId(this.mRegId, this.mUserId, this.mSessionKey);
               GCMRegistrationService.getGCMPreferences(getContext()).edit().clear().commit();
               return null;
                    }

                    
           protected void onPostExecute(Void aVoid) {
               GCMRegistrationService.this.startService(GCMNotificationService.getClearNotificationIntent(getContext(), 1));
               GCMRegistrationService.this.startService(GCMNotificationService.getClearNotificationIntent(getContext(), 2));
               GCMRegistrationService.access$500(GCMRegistrationService.this);
                    }

           private Context getContext() {
               return GCMRegistrationService.this;
                    }
                }

       private class GCMRegistrationListener extends AppSessionListener {
           private GCMRegistrationListener() {
                    }

            GCMRegistrationListener(GCMRegistrationService x0, Object x1) {
               this();
                    }

                    @Override 
           public void onGcmRegistrationComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
               if (statusCode == 200 && userId > 0) {
                   SLog.dWithTag(GCMRegistrationService.TAG, "GCM registration completed successfully; saving regId and stopping service now.");
                   SharedPreferences.Editor editor = GCMRegistrationService.getGCMPreferences(GCMRegistrationService.this).edit();
                   editor.putBoolean(GCMRegistrationService.PREF_REG_ID_SENT, true);
                   editor.apply();
                        }
               GCMRegistrationService.access$500(GCMRegistrationService.this);
                    }
                }

                @Override 
       public IBinder onBind(Intent intent) {
           return null;
                }
            }
