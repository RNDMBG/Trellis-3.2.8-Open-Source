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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    public class GCMRegistrationService extends Service {
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

/* 26 */        static /* synthetic */ GoogleCloudMessaging access$100(GCMRegistrationService x0) {
/* 27 */            return x0.mGcm;
                }

/* 26 */        static /* synthetic */ GoogleCloudMessaging access$102(GCMRegistrationService x0, GoogleCloudMessaging x1) {
/* 27 */            x0.mGcm = x1;
                    return x1;
                }

/* 26 */        static /* synthetic */ String access$200(GCMRegistrationService x0) {
/* 27 */            return x0.mSenderId;
                }

/* 26 */        static /* synthetic */ void access$300(GCMRegistrationService x0, String x1) {
/* 27 */            x0.setRegistrationId(x1);
                }

/* 26 */        static /* synthetic */ AppController access$400(GCMRegistrationService x0) {
/* 27 */            return x0.mAppController;
                }

/* 26 */        static /* synthetic */ void access$500(GCMRegistrationService x0) {
/* 27 */            x0.stopService();
                }

/* 50 */        public static Intent getRegisterIntent(Context context, long userId) {
/* 51 */            Intent intent = new Intent(context, (Class<?>) GCMRegistrationService.class);
/* 52 */            intent.setAction(ACTION_REGISTER);
/* 53 */            intent.putExtra("user_id", userId);
/* 54 */            return intent;
                }

/* 57 */        public static Intent getUnregisterIntent(Context context, long userId, String key) {
/* 58 */            Intent intent = new Intent(context, (Class<?>) GCMRegistrationService.class);
/* 59 */            intent.setAction(ACTION_UNREGISTER);
/* 60 */            intent.putExtra("user_id", userId);
/* 61 */            intent.putExtra("s_key", key);
/* 62 */            return intent;
                }

                @Override // android.app.Service
/* 66 */        public void onCreate() {
/* 67 */            super.onCreate();
/* 68 */            this.mGcm = GoogleCloudMessaging.getInstance(this);
/* 69 */            this.mAppController = AppController.getInstance(this);
/* 70 */            this.mGcmRegistrationListener = new GCMRegistrationListener(this, null);
/* 71 */            if (this.mGcmRegistrationListener != null) {
/* 72 */                this.mAppController.addListener(this.mGcmRegistrationListener);
                    }
/* 74 */            this.mSenderId = BuildUtil.getSenderId(this);
/* 75 */            this.mStartIds = new ArrayList<>();
                }

                @Override // android.app.Service
/* 79 */        public int onStartCommand(Intent intent, int flags, int startId) {
/* 80 */            SLog.dWithTag(TAG, "onStartCommand, startId=" + startId + ", startIds=" + this.mStartIds.toString());
/* 81 */            this.mStartIds.add(Integer.valueOf(startId));
/* 83 */            if (intent != null) {
/* 88 */                String regId = getRegistrationId();
/* 89 */                SLog.dWithTag(TAG, "Starting GCM registration service with regId=" + regId);
/* 90 */                long userId = intent.getLongExtra("user_id", 0L);
/* 91 */                String action = intent.getAction();
/* 93 */                if (ACTION_REGISTER.equals(action)) {
/* 94 */                    if (regId == null) {
/* 95 */                        new GCMRegisterTask(userId).execute(new Void[0]);
                            } else if (!getGCMPreferences(this).getBoolean(PREF_REG_ID_SENT, false)) {
/* 98 */                        this.mAppController.sendGcmRegId(regId, userId);
                            } else {
/* 100 */                       stopService();
                            }
                        } else if (ACTION_UNREGISTER.equals(action)) {
/* 103 */                   String key = intent.getStringExtra("s_key");
/* 104 */                   new GCMUnregisterTask(regId, userId, key).execute(new Void[0]);
                        } else {
/* 106 */                   stopService();
                        }
                    }
                    return 2;
                }

/* 111 */       private void stopService() {
/* 112 */           Iterator<Integer> it = this.mStartIds.iterator();
                    while (it.hasNext()) {
                        int startId = it.next().intValue();
/* 113 */               SLog.dWithTag(TAG, "Stopping service for startId=" + startId);
/* 114 */               stopSelf(startId);
                    }
                }

/* 123 */       private String getRegistrationId() {
/* 124 */           SharedPreferences prefs = getGCMPreferences(this);
/* 125 */           String registrationId = prefs.getString(PREF_REG_ID, null);
/* 126 */           if (registrationId == null) {
/* 127 */               SLog.dWithTag(TAG, "Registration not found.");
                        return null;
                    }
/* 132 */           int registeredVersion = prefs.getInt(PREF_APP_VERSION, Integer.MIN_VALUE);
/* 133 */           int currentVersion = getAppVersion();
/* 134 */           if (registeredVersion != currentVersion || isRegistrationExpired()) {
/* 135 */               SLog.dWithTag(TAG, "App version changed or registration expired.");
                        return null;
                    }
/* 139 */           return registrationId;
                }

/* 143 */       private int getAppVersion() {
                    try {
/* 144 */               PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
/* 145 */               return packageInfo.versionCode;
                    } catch (PackageManager.NameNotFoundException e) {
/* 148 */               return -1;
                    }
                }

/* 152 */       private boolean isRegistrationExpired() {
/* 153 */           SharedPreferences prefs = getGCMPreferences(this);
/* 154 */           long expirationTime = prefs.getLong(PREF_REG_EXPIRY_TIME, -1L);
                    return System.currentTimeMillis() > expirationTime;
                }

/* 164 */       private void setRegistrationId(String regId) {
/* 165 */           SharedPreferences prefs = getGCMPreferences(this);
/* 166 */           int appVersion = getAppVersion();
/* 167 */           long expirationTime = System.currentTimeMillis() + 604800000;
/* 169 */           SharedPreferences.Editor editor = prefs.edit();
/* 170 */           editor.putBoolean(PREF_REG_ID_SENT, false);
/* 171 */           editor.putInt(PREF_APP_VERSION, appVersion);
/* 172 */           editor.putLong(PREF_REG_EXPIRY_TIME, expirationTime);
/* 173 */           editor.putString(PREF_REG_ID, regId);
/* 174 */           editor.apply();
                }

/* 177 */       public static SharedPreferences getGCMPreferences(Context context) {
/* 178 */           if (sPrefs == null) {
/* 179 */               sPrefs = context.getSharedPreferences(PREFS, 0);
                    }
/* 181 */           return sPrefs;
                }

                @Override // android.app.Service
/* 185 */       public void onDestroy() {
/* 186 */           super.onDestroy();
/* 187 */           if (this.mGcmRegistrationListener != null) {
/* 188 */               this.mAppController.removeListener(this.mGcmRegistrationListener);
                    }
/* 190 */           this.mGcm.close();
                }

/* 195 */       private class GCMRegisterTask extends AsyncTask<Void, Void, Void> {
                    private long mUserId;



/* 200 */           public GCMRegisterTask(long userId) {
/* 202 */               this.mUserId = userId;
                    }

                    /* JADX INFO: renamed from: doInBackground, reason: avoid collision after fix types in other method */
/* 206 */           protected Void doInBackground(Void... params) {
/* 207 */               if (GCMRegistrationService.access$100(GCMRegistrationService.this) == null) {
/* 208 */                   GCMRegistrationService.access$102(GCMRegistrationService.this, GoogleCloudMessaging.getInstance(getContext()));
                        }
                        try {
/* 212 */                   SLog.dWithTag(GCMRegistrationService.TAG, "Registering with senderId=" + GCMRegistrationService.access$200(GCMRegistrationService.this));
/* 214 */                   String regId = GCMRegistrationService.access$100(GCMRegistrationService.this).register(GCMRegistrationService.access$200(GCMRegistrationService.this));
/* 215 */                   SLog.m447d("GCM: Registration complete with regId={}", regId);
/* 216 */                   GCMRegistrationService.access$300(GCMRegistrationService.this, regId);
/* 217 */                   GCMRegistrationService.access$400(GCMRegistrationService.this).sendGcmRegId(regId, this.mUserId);
/* 223 */                   return null;
                        } catch (IOException e) {
/* 219 */                   SLog.dWithTag(GCMRegistrationService.TAG, "Failed to register with GCM service.");
/* 223 */                   return null;
                        } catch (SecurityException e2) {
/* 223 */                   return null;
                        }
                    }

                    /* JADX INFO: renamed from: onPostExecute, reason: avoid collision after fix types in other method */
/* 227 */           protected void onPostExecute(Void aVoid) {
/* 228 */               GCMRegistrationService.access$500(GCMRegistrationService.this);
                    }

/* 231 */           private Context getContext() {
/* 232 */               return GCMRegistrationService.this;
                    }
                }

/* 239 */       private class GCMUnregisterTask extends AsyncTask<Void, Void, Void> {
                    private String mRegId;
                    private String mSessionKey;
                    private long mUserId;



/* 246 */           public GCMUnregisterTask(String regId, long userId, String key) {
/* 248 */               this.mRegId = regId;
/* 249 */               this.mUserId = userId;
/* 250 */               this.mSessionKey = key;
                    }

                    /* JADX INFO: renamed from: doInBackground, reason: avoid collision after fix types in other method */
/* 254 */           protected Void doInBackground(Void... voids) {
/* 255 */               if (GCMRegistrationService.access$100(GCMRegistrationService.this) == null) {
/* 256 */                   GCMRegistrationService.access$102(GCMRegistrationService.this, GoogleCloudMessaging.getInstance(getContext()));
                        }
/* 259 */               SLog.dWithTag(GCMRegistrationService.TAG, "Unregistering...");
/* 260 */               GCMRegistrationService.access$400(GCMRegistrationService.this).clearGcmRegId(this.mRegId, this.mUserId, this.mSessionKey);
/* 261 */               GCMRegistrationService.getGCMPreferences(getContext()).edit().clear().commit();
/* 262 */               return null;
                    }

                    /* JADX INFO: renamed from: onPostExecute, reason: avoid collision after fix types in other method */
/* 266 */           protected void onPostExecute(Void aVoid) {
/* 267 */               GCMRegistrationService.this.startService(GCMNotificationService.getClearNotificationIntent(getContext(), 1));
/* 268 */               GCMRegistrationService.this.startService(GCMNotificationService.getClearNotificationIntent(getContext(), 2));
/* 269 */               GCMRegistrationService.access$500(GCMRegistrationService.this);
                    }

/* 272 */           private Context getContext() {
/* 273 */               return GCMRegistrationService.this;
                    }
                }

/* 275 */       private class GCMRegistrationListener extends AppSessionListener {
/* 276 */           private GCMRegistrationListener() {
                    }

/* 276 */           /* synthetic */ GCMRegistrationListener(GCMRegistrationService x0, Object x1) {
/* 277 */               this();
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 279 */           public void onGcmRegistrationComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
/* 280 */               if (statusCode == 200 && userId > 0) {
/* 281 */                   SLog.dWithTag(GCMRegistrationService.TAG, "GCM registration completed successfully; saving regId and stopping service now.");
/* 282 */                   SharedPreferences.Editor editor = GCMRegistrationService.getGCMPreferences(GCMRegistrationService.this).edit();
/* 283 */                   editor.putBoolean(GCMRegistrationService.PREF_REG_ID_SENT, true);
/* 284 */                   editor.apply();
                        }
/* 286 */               GCMRegistrationService.access$500(GCMRegistrationService.this);
                    }
                }

                @Override // android.app.Service
/* 291 */       public IBinder onBind(Intent intent) {
/* 292 */           return null;
                }
            }
