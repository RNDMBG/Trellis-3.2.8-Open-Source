            package co.vine.android.client;

            import android.accounts.Account;
            import android.accounts.AccountManager;
            import android.accounts.AccountManagerFuture;
            import android.content.Context;
            import android.content.SharedPreferences;
            import android.os.Build;
            import android.provider.Settings;
            import android.support.annotation.Nullable;
            import android.telephony.TelephonyManager;
            import android.util.Base64;
            import co.vine.android.VineLoggingException;
            import co.vine.android.api.VineLogin;
            import co.vine.android.provider.SettingsManager;
            import co.vine.android.service.VineService;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.SystemUtil;
            import co.vine.android.util.Util;
            import com.edisonwang.android.slog.SLog;
            import com.vandalsoftware.p004io.IoUtils;
            import java.security.MessageDigest;
            import javax.crypto.Cipher;
            import javax.crypto.spec.SecretKeySpec;
            
            
    public class VineAccountHelper {
                public static final String ACCOUNT_TYPE = BuildUtil.getAuthority(".auth");
                public static final String KEY_ADD_WIDGET = "account_add_widget";
                public static final String KEY_DID_SHOW_STORE_CONTACTS_PROMPT = "account_did_show_store_contacts";
                public static final String KEY_LOGIN_TYPE = "account_login_type";
                public static final String KEY_NORMAL_VIDEO_PLAYABLE = "account_normal_video_playable";
                public static final String KEY_PASSWORD_ENCRYPTED = "account_password_encrypted";
                public static final String KEY_SETTINGS = "account_settings";
                public static final String KEY_TWITTER_SECRET = "account_t_secret";
                public static final String KEY_TWITTER_TOKEN = "account_t_token";
                public static final String KEY_TWITTER_USERNAME = "account_t_username";
                public static final String KEY_TWITTER_USER_ID = "account_t_id";
                public static final String KEY_USER_AVATAR = "account_user_avatar";
                public static final String KEY_USER_DATA = "account_user_data";
                public static final String KEY_USER_ID = "account_user_info";
                public static final String KEY_USER_NAME = "account_user_name";
                public static final int LOGIN_TYPE_EMAIL = 1;
                public static final int LOGIN_TYPE_TWITTER = 2;
                public static final String SESSION_TOKEN_KEY = "co.vine.android.basic_auth.token.secret";
                private static Session mActiveSession;

                @Nullable
        public static Account getAccount(Context context, String accountName) {
                    Account[] accounts;
            AccountManager am = AccountManager.get(context);
            if (am != null && (accounts = am.getAccountsByType(ACCOUNT_TYPE)) != null) {
                        for (Account account : accounts) {
                    if (account.name.equals(accountName)) {
                       return account;
                            }
                        }
                    }
           return null;
                }

       public static int getAccountsCount(Context context) {
           AccountManager am = AccountManager.get(context);
           return am.getAccountsByType(ACCOUNT_TYPE).length;
                }

       public static Account createAccount(Context context, String uniqueLogin, VineLogin vl, String password, String avatar, String accountName) throws VineLoggingException {
           AccountManager am = AccountManager.get(context);
           Account account = getAccount(context, uniqueLogin);
           if (account == null) {
               account = new Account(uniqueLogin, ACCOUNT_TYPE);
               if (am == null) {
                            return null;
                        }
                        try {
                            if (!am.addAccountExplicitly(account, null, null)) {
                                return null;
                            }
                        } catch (SecurityException e) {
                   throw new VineLoggingException(e);
                        }
                    }
                    switch (vl.loginType) {
                        case 1:
                            try {
                       am.setPassword(account, encrypt(getDeviceId(context), password));
                       am.setUserData(account, KEY_PASSWORD_ENCRYPTED, String.valueOf(true));
                            } catch (Exception e2) {
                       SLog.m459e("Error encrypting password.", (Throwable) e2);
                       am.setPassword(account, password);
                            }
                   am.setUserData(account, KEY_LOGIN_TYPE, String.valueOf(1));
                            break;
                        case 2:
                   am.setUserData(account, KEY_TWITTER_TOKEN, vl.twitterToken);
                   am.setUserData(account, KEY_TWITTER_SECRET, vl.twitterSecret);
                   am.setUserData(account, KEY_TWITTER_USER_ID, String.valueOf(vl.twitterUserId));
                   am.setUserData(account, KEY_TWITTER_USERNAME, vl.twitterUsername);
                   am.setUserData(account, KEY_LOGIN_TYPE, String.valueOf(2));
                            break;
                    }
            am.setUserData(account, KEY_USER_NAME, accountName);
            am.setUserData(account, KEY_USER_AVATAR, avatar);
            am.setUserData(account, KEY_USER_ID, String.valueOf(vl.userId));
            am.setAuthToken(account, SESSION_TOKEN_KEY, vl.key);
            markAccountKnown(context);
            return account;
                }

       public static AccountManagerFuture<Boolean> removeAccount(Context context, String accountName) {
           AccountManager am = AccountManager.get(context);
           Account[] accounts = am.getAccountsByType(ACCOUNT_TYPE);
                    for (Account account : accounts) {
               if (account.name.equals(accountName)) {
                   return am.removeAccount(account, null, null);
                        }
                    }
                    return null;
                }

       public static void saveUserId(Context context, String accountName, long userId) {
           Account account = getAccount(context, accountName);
           if (account != null) {
               AccountManager am = AccountManager.get(context);
                        if (userId > 0) {
                   am.setUserData(account, KEY_USER_ID, String.valueOf(userId));
                        }
                    }
                }

       public static void saveTwitterInfo(Context context, String accountName, String username, String token, String secret, long twitterUserId) {
           Account account = getAccount(context, accountName);
           if (account != null) {
               AccountManager am = AccountManager.get(context);
               am.setUserData(account, KEY_TWITTER_TOKEN, token);
               am.setUserData(account, KEY_TWITTER_SECRET, secret);
               am.setUserData(account, KEY_TWITTER_USER_ID, String.valueOf(twitterUserId));
               am.setUserData(account, KEY_TWITTER_USERNAME, username);
               am.setUserData(account, KEY_LOGIN_TYPE, String.valueOf(2));
                    }
                }

       public static void removeTwitterInfo(Context context, String accountName) {
           Account account = getAccount(context, accountName);
           if (account != null) {
               AccountManager am = AccountManager.get(context);
               am.setUserData(account, KEY_TWITTER_TOKEN, null);
               am.setUserData(account, KEY_TWITTER_SECRET, null);
               am.setUserData(account, KEY_TWITTER_USER_ID, null);
               am.setUserData(account, KEY_TWITTER_USERNAME, null);
               am.setUserData(account, KEY_LOGIN_TYPE, String.valueOf(1));
                    }
                }

       public static void setSessionKey(AccountManager am, Account account, String key) {
           am.setAuthToken(account, SESSION_TOKEN_KEY, key);
                }

       public static String getSessionKey(AccountManager am, Account account) {
           return am.peekAuthToken(account, SESSION_TOKEN_KEY);
                }

       public static long getUserId(AccountManager am, Account account) {
           String userIdS = am.getUserData(account, KEY_USER_ID);
           if (userIdS != null) {
               return Long.parseLong(userIdS);
                    }
                    return 0L;
                }

       public static String getAvatarUrl(AccountManager am, Account account) {
           return am.getUserData(account, KEY_USER_AVATAR);
                }

       public static String getName(AccountManager am, Account account) {
           return am.getUserData(account, KEY_USER_NAME);
                }

       public static Integer getLoginType(AccountManager am, Account account) {
           String data = am.getUserData(account, KEY_LOGIN_TYPE);
           if (data != null) {
               return Integer.valueOf(Integer.parseInt(data));
                    }
                    return null;
                }

       public static String getPassword(AccountManager am, Account account, Context context) {
           String password = am.getPassword(account);
           String passwordFlag = am.getUserData(account, KEY_PASSWORD_ENCRYPTED);
           if (passwordFlag != null && Boolean.parseBoolean(passwordFlag)) {
                        try {
                   password = decrypt(getDeviceId(context), password);
                        } catch (Exception e) {
                   SLog.m459e("Error decrypting password.", (Throwable) e);
                   CrashUtil.logException(e);
                   return null;
                        }
                    } else if (password != null) {
               migratePassword(am, account, context);
                    }
                    return password;
                }

       public static String getTwitterToken(AccountManager am, Account account) {
           return am.getUserData(account, KEY_TWITTER_TOKEN);
                }

       public static String getTwitterSecret(AccountManager am, Account account) {
           return am.getUserData(account, KEY_TWITTER_SECRET);
                }

       public static String getTwitterUsername(AccountManager am, Account account) {
           return am.getUserData(account, KEY_TWITTER_USERNAME);
                }

       public static boolean needsAddwidget(AccountManager am, Account account) {
           String data = am.getUserData(account, KEY_ADD_WIDGET);
                    return data == null;
                }

       public static void setDidShowAddWidget(AccountManager am, Account account) {
           am.setUserData(account, KEY_ADD_WIDGET, "YES");
                }

       public static boolean shouldShowStoreContactsPrompt(AccountManager am, Account account) {
           if (am == null || account == null) {
                        return true;
                    }
           String data = am.getUserData(account, KEY_DID_SHOW_STORE_CONTACTS_PROMPT);
                    return data == null;
                }

       public static void setDidShowStoreContactsPrompt(AccountManager am, Account account) {
           if (am != null && account != null) {
               am.setUserData(account, KEY_DID_SHOW_STORE_CONTACTS_PROMPT, "YES");
                    }
                }

       public static void syncNormalVideoPlayable(Context context) {
           SystemUtil.PrefBooleanState accountStatus = isNormalVideoPlayable(context);
           SystemUtil.PrefBooleanState preferenceStatus = SystemUtil.isNormalVideoPlayable(context);
           if (accountStatus != preferenceStatus) {
               if (preferenceStatus != SystemUtil.PrefBooleanState.UNKNOWN) {
                   AccountManager am = AccountManager.get(context);
                   Account account = getActiveAccount(context);
                   if (am != null && account != null) {
                       am.setUserData(account, KEY_NORMAL_VIDEO_PLAYABLE, preferenceStatus.name());
                       return;
                            }
                   return;
                        }
               SystemUtil.setNormalVideoPlayable(context, accountStatus == SystemUtil.PrefBooleanState.TRUE);
                    }
                }

       public static SystemUtil.PrefBooleanState isNormalVideoPlayable(Context activity) {
           AccountManager am = AccountManager.get(activity);
           if (am == null) {
               return SystemUtil.PrefBooleanState.UNKNOWN;
                    }
           Account account = getActiveAccount(activity);
           String data = null;
           if (account != null) {
               data = am.getUserData(account, KEY_NORMAL_VIDEO_PLAYABLE);
                    }
           if (data != null) {
               return SystemUtil.PrefBooleanState.valueOf(data);
                    }
           return SystemUtil.PrefBooleanState.UNKNOWN;
                }

       public static Session createSession(Context context, Account account) {
           AccountManager am = AccountManager.get(context);
           long userId = getUserId(am, account);
           String edition = SettingsManager.getEdition(context);
           return new Session(getSessionKey(am, account), userId, getName(am, account), getAvatarUrl(am, account), edition);
                }

       public static Session getCachedActiveSession() {
           return mActiveSession;
                }

       public static Session getActiveSession(Context context, boolean readOnly) {
           if (mActiveSession == null) {
               AccountManager am = AccountManager.get(context);
               Account[] accounts = am.getAccountsByType(ACCOUNT_TYPE);
               if (accounts.length > 0) {
                   String accountName = SettingsManager.getCurrentAccount(context);
                            for (Account account : accounts) {
                       if (accountName == null || account.name.equals(accountName)) {
                           Session session = getSession(context, account.name);
                           session.setLoginEmail(account.name);
                           session.setLoginStatus(Session.LoginStatus.LOGGED_IN);
                           if (!readOnly) {
                               setActiveSession(context, session);
                                    }
                           mActiveSession = session;
                                    return session;
                                }
                            }
                        }
               Session session2 = createSession();
               if (!readOnly) {
                   setActiveSession(context, session2);
                        }
               mActiveSession = session2;
                    }
           return mActiveSession;
                }

       private static Session createSession() {
           return new Session(null, 0L, null, null, null);
                }

       public static void setActiveSession(Context context, Session session) {
           SettingsManager.setCurrentAccount(context, session.getLoginEmail(), session.getName(), session.getUserId());
                }

       public static Session getSession(Context context, String accountName) {
           if (accountName == null) {
               return createSession();
                    }
           if (mActiveSession == null) {
               Account account = getAccount(context, accountName);
               if (account == null) {
                   mActiveSession = createSession();
                        } else {
                   mActiveSession = createSession(context, account);
                        }
                    }
           return mActiveSession;
                }

        public static void resetActiveSession() {
            mActiveSession = null;
                }

        private static final String PREF_KNOWN_ACCOUNT = "known_account";

        public static void markAccountKnown(Context context) {
                     try {
                Util.getDefaultSharedPrefs(context).edit().putBoolean(PREF_KNOWN_ACCOUNT, true).apply();
                     } catch (Exception ignored) {
                     }
                 }

        public static void clearAccountsIfDataWiped(Context context) {
                     try {
                AccountManager am = AccountManager.get(context);
                         if (am == null) {
                             return;
                         }
                Account[] accounts = am.getAccountsByType(ACCOUNT_TYPE);
                         if (accounts == null || accounts.length == 0) {
                             return;
                         }
                SharedPreferences prefs = Util.getDefaultSharedPrefs(context);
                         if (prefs.getBoolean(PREF_KNOWN_ACCOUNT, false)) {
                             return;
                         }
                         if (!prefs.getAll().isEmpty()) {
                    markAccountKnown(context);
                    return;
                         }
                         for (Account account : accounts) {
                             try {
                        am.removeAccount(account, null, null);
                             } catch (Exception ignored) {
                             }
                         }
                     } catch (Exception e) {
                CrashUtil.logException(e);
                     }
                 }

       public static Account getActiveAccount(Context context) {
           return getAccount(context, AppController.getInstance(context).getActiveSession().getLoginEmail());
                }

       public static Account getActiveAccountReadOnly(Context context) {
           return getAccount(context, AppController.getInstance(context).getActiveSessionReadOnly().getLoginEmail());
                }

       public static boolean isLoggedIn(Context context, boolean readOnly) {
           return getActiveSession(context, readOnly).isLoggedIn();
                }

       private static String encrypt(String key, String cleartext) throws Exception {
           SecretKeySpec secretKeySpec = new SecretKeySpec(getKeyDigest(key), "AES");
           Cipher cipher = Cipher.getInstance("AES");
           cipher.init(1, secretKeySpec);
           byte[] encrypted = cipher.doFinal(cleartext.getBytes(IoUtils.UTF_8));
           return Base64.encodeToString(encrypted, 0);
                }

       private static String decrypt(String key, String encrypted) throws Exception {
           SecretKeySpec secretKeySpec = new SecretKeySpec(getKeyDigest(key), "AES");
           Cipher cipher = Cipher.getInstance("AES");
           cipher.init(2, secretKeySpec);
           byte[] decrypted = cipher.doFinal(Base64.decode(encrypted, 0));
           return new String(decrypted, IoUtils.UTF_8);
                }

       private static byte[] getKeyDigest(String key) throws Exception {
           MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
           messageDigest.update(key.getBytes(IoUtils.UTF_8));
           return messageDigest.digest();
                }

       private static String getDeviceId(Context context) {
           
           
           
                    try {
                        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                        if (androidId != null && !androidId.isEmpty()) {
                            return androidId;
                        }
                    } catch (Exception ignored) {
                    }
                    try {
                        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(VineService.EXTRA_PHONE);
                        if (telephonyManager != null) {
                            String deviceId = telephonyManager.getDeviceId();
                            if (deviceId != null && !deviceId.isEmpty()) {
                                return deviceId;
                            }
                        }
                    } catch (SecurityException ignored) {
                    }
                    return Build.SERIAL == null ? "vine-device" : Build.SERIAL;
                }

       public static void migratePassword(AccountManager am, Account account, Context context) {
                    String password;
           if (am == null || account == null) {
               SLog.m464i("Skipping migrate password check due to null AccountManager or Account");
               return;
                    }
           String passwordFlag = am.getUserData(account, KEY_PASSWORD_ENCRYPTED);
           if ((passwordFlag == null || !Boolean.parseBoolean(passwordFlag)) && (password = am.getPassword(account)) != null) {
                        try {
                   am.setPassword(account, encrypt(getDeviceId(context), password));
                   am.setUserData(account, KEY_PASSWORD_ENCRYPTED, String.valueOf(true));
                        } catch (Exception e) {
                   SLog.m459e("Error migrating password.", (Throwable) e);
                   CrashUtil.logException(e);
                        }
                    }
                }
            }
