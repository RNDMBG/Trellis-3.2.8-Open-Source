            package co.vine.android.client;

            import android.accounts.Account;
            import android.accounts.AccountManager;
            import android.accounts.AccountManagerFuture;
            import android.content.Context;
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
            import com.edisonwang.android.slog.SLog;
            import com.vandalsoftware.p004io.IoUtils;
            import java.security.MessageDigest;
            import javax.crypto.Cipher;
            import javax.crypto.spec.SecretKeySpec;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 87 */    public class VineAccountHelper {
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
/* 88 */        public static Account getAccount(Context context, String accountName) {
                    Account[] accounts;
/* 89 */            AccountManager am = AccountManager.get(context);
/* 90 */            if (am != null && (accounts = am.getAccountsByType(ACCOUNT_TYPE)) != null) {
                        for (Account account : accounts) {
/* 94 */                    if (account.name.equals(accountName)) {
/* 100 */                       return account;
                            }
                        }
                    }
/* 100 */           return null;
                }

/* 109 */       public static int getAccountsCount(Context context) {
/* 110 */           AccountManager am = AccountManager.get(context);
/* 111 */           return am.getAccountsByType(ACCOUNT_TYPE).length;
                }

/* 121 */       public static Account createAccount(Context context, String uniqueLogin, VineLogin vl, String password, String avatar, String accountName) throws VineLoggingException {
/* 122 */           AccountManager am = AccountManager.get(context);
/* 123 */           Account account = getAccount(context, uniqueLogin);
/* 124 */           if (account == null) {
/* 125 */               account = new Account(uniqueLogin, ACCOUNT_TYPE);
/* 127 */               if (am == null) {
                            return null;
                        }
                        try {
                            if (!am.addAccountExplicitly(account, null, null)) {
                                return null;
                            }
                        } catch (SecurityException e) {
/* 131 */                   throw new VineLoggingException(e);
                        }
                    }
                    switch (vl.loginType) {
                        case 1:
                            try {
/* 137 */                       am.setPassword(account, encrypt(getDeviceId(context), password));
/* 139 */                       am.setUserData(account, KEY_PASSWORD_ENCRYPTED, String.valueOf(true));
                            } catch (Exception e2) {
/* 141 */                       SLog.m459e("Error encrypting password.", (Throwable) e2);
/* 143 */                       am.setPassword(account, password);
                            }
/* 145 */                   am.setUserData(account, KEY_LOGIN_TYPE, String.valueOf(1));
                            break;
                        case 2:
/* 149 */                   am.setUserData(account, KEY_TWITTER_TOKEN, vl.twitterToken);
/* 150 */                   am.setUserData(account, KEY_TWITTER_SECRET, vl.twitterSecret);
/* 151 */                   am.setUserData(account, KEY_TWITTER_USER_ID, String.valueOf(vl.twitterUserId));
/* 152 */                   am.setUserData(account, KEY_TWITTER_USERNAME, vl.twitterUsername);
/* 153 */                   am.setUserData(account, KEY_LOGIN_TYPE, String.valueOf(2));
                            break;
                    }
/* 158 */           am.setUserData(account, KEY_USER_NAME, accountName);
/* 159 */           am.setUserData(account, KEY_USER_AVATAR, avatar);
/* 160 */           am.setUserData(account, KEY_USER_ID, String.valueOf(vl.userId));
/* 161 */           am.setAuthToken(account, SESSION_TOKEN_KEY, vl.key);
                    return account;
                }

/* 175 */       public static AccountManagerFuture<Boolean> removeAccount(Context context, String accountName) {
/* 176 */           AccountManager am = AccountManager.get(context);
/* 177 */           Account[] accounts = am.getAccountsByType(ACCOUNT_TYPE);
                    for (Account account : accounts) {
/* 180 */               if (account.name.equals(accountName)) {
/* 181 */                   return am.removeAccount(account, null, null);
                        }
                    }
                    return null;
                }

/* 194 */       public static void saveUserId(Context context, String accountName, long userId) {
/* 195 */           Account account = getAccount(context, accountName);
/* 196 */           if (account != null) {
/* 197 */               AccountManager am = AccountManager.get(context);
                        if (userId > 0) {
/* 199 */                   am.setUserData(account, KEY_USER_ID, String.valueOf(userId));
                        }
                    }
                }

/* 205 */       public static void saveTwitterInfo(Context context, String accountName, String username, String token, String secret, long twitterUserId) {
/* 206 */           Account account = getAccount(context, accountName);
/* 207 */           if (account != null) {
/* 208 */               AccountManager am = AccountManager.get(context);
/* 209 */               am.setUserData(account, KEY_TWITTER_TOKEN, token);
/* 210 */               am.setUserData(account, KEY_TWITTER_SECRET, secret);
/* 211 */               am.setUserData(account, KEY_TWITTER_USER_ID, String.valueOf(twitterUserId));
/* 213 */               am.setUserData(account, KEY_TWITTER_USERNAME, username);
/* 214 */               am.setUserData(account, KEY_LOGIN_TYPE, String.valueOf(2));
                    }
                }

/* 219 */       public static void removeTwitterInfo(Context context, String accountName) {
/* 220 */           Account account = getAccount(context, accountName);
/* 221 */           if (account != null) {
/* 222 */               AccountManager am = AccountManager.get(context);
/* 223 */               am.setUserData(account, KEY_TWITTER_TOKEN, null);
/* 224 */               am.setUserData(account, KEY_TWITTER_SECRET, null);
/* 225 */               am.setUserData(account, KEY_TWITTER_USER_ID, null);
/* 226 */               am.setUserData(account, KEY_TWITTER_USERNAME, null);
/* 227 */               am.setUserData(account, KEY_LOGIN_TYPE, String.valueOf(1));
                    }
                }

/* 232 */       public static void setSessionKey(AccountManager am, Account account, String key) {
/* 233 */           am.setAuthToken(account, SESSION_TOKEN_KEY, key);
                }

/* 243 */       public static String getSessionKey(AccountManager am, Account account) {
/* 244 */           return am.peekAuthToken(account, SESSION_TOKEN_KEY);
                }

/* 254 */       public static long getUserId(AccountManager am, Account account) {
/* 255 */           String userIdS = am.getUserData(account, KEY_USER_ID);
/* 256 */           if (userIdS != null) {
/* 257 */               return Long.parseLong(userIdS);
                    }
                    return 0L;
                }

/* 270 */       public static String getAvatarUrl(AccountManager am, Account account) {
/* 271 */           return am.getUserData(account, KEY_USER_AVATAR);
                }

/* 281 */       public static String getName(AccountManager am, Account account) {
/* 282 */           return am.getUserData(account, KEY_USER_NAME);
                }

/* 288 */       public static Integer getLoginType(AccountManager am, Account account) {
/* 289 */           String data = am.getUserData(account, KEY_LOGIN_TYPE);
/* 290 */           if (data != null) {
/* 291 */               return Integer.valueOf(Integer.parseInt(data));
                    }
                    return null;
                }

/* 300 */       public static String getPassword(AccountManager am, Account account, Context context) {
/* 301 */           String password = am.getPassword(account);
/* 303 */           String passwordFlag = am.getUserData(account, KEY_PASSWORD_ENCRYPTED);
/* 304 */           if (passwordFlag != null && Boolean.parseBoolean(passwordFlag)) {
                        try {
/* 306 */                   password = decrypt(getDeviceId(context), password);
                        } catch (Exception e) {
/* 308 */                   SLog.m459e("Error decrypting password.", (Throwable) e);
/* 309 */                   CrashUtil.logException(e);
/* 310 */                   return null;
                        }
                    } else if (password != null) {
/* 314 */               migratePassword(am, account, context);
                    }
                    return password;
                }

/* 323 */       public static String getTwitterToken(AccountManager am, Account account) {
/* 324 */           return am.getUserData(account, KEY_TWITTER_TOKEN);
                }

/* 330 */       public static String getTwitterSecret(AccountManager am, Account account) {
/* 331 */           return am.getUserData(account, KEY_TWITTER_SECRET);
                }

/* 337 */       public static String getTwitterUsername(AccountManager am, Account account) {
/* 338 */           return am.getUserData(account, KEY_TWITTER_USERNAME);
                }

/* 344 */       public static boolean needsAddwidget(AccountManager am, Account account) {
/* 345 */           String data = am.getUserData(account, KEY_ADD_WIDGET);
                    return data == null;
                }

/* 352 */       public static void setDidShowAddWidget(AccountManager am, Account account) {
/* 353 */           am.setUserData(account, KEY_ADD_WIDGET, "YES");
                }

/* 356 */       public static boolean shouldShowStoreContactsPrompt(AccountManager am, Account account) {
/* 357 */           if (am == null || account == null) {
                        return true;
                    }
/* 358 */           String data = am.getUserData(account, KEY_DID_SHOW_STORE_CONTACTS_PROMPT);
                    return data == null;
                }

/* 365 */       public static void setDidShowStoreContactsPrompt(AccountManager am, Account account) {
/* 366 */           if (am != null && account != null) {
/* 367 */               am.setUserData(account, KEY_DID_SHOW_STORE_CONTACTS_PROMPT, "YES");
                    }
                }

/* 376 */       public static void syncNormalVideoPlayable(Context context) {
/* 377 */           SystemUtil.PrefBooleanState accountStatus = isNormalVideoPlayable(context);
/* 378 */           SystemUtil.PrefBooleanState preferenceStatus = SystemUtil.isNormalVideoPlayable(context);
/* 379 */           if (accountStatus != preferenceStatus) {
/* 380 */               if (preferenceStatus != SystemUtil.PrefBooleanState.UNKNOWN) {
/* 382 */                   AccountManager am = AccountManager.get(context);
/* 383 */                   Account account = getActiveAccount(context);
/* 384 */                   if (am != null && account != null) {
/* 385 */                       am.setUserData(account, KEY_NORMAL_VIDEO_PLAYABLE, preferenceStatus.name());
/* 392 */                       return;
                            }
/* 392 */                   return;
                        }
/* 389 */               SystemUtil.setNormalVideoPlayable(context, accountStatus == SystemUtil.PrefBooleanState.TRUE);
                    }
                }

/* 394 */       public static SystemUtil.PrefBooleanState isNormalVideoPlayable(Context activity) {
/* 395 */           AccountManager am = AccountManager.get(activity);
/* 396 */           if (am == null) {
/* 397 */               return SystemUtil.PrefBooleanState.UNKNOWN;
                    }
/* 399 */           Account account = getActiveAccount(activity);
/* 400 */           String data = null;
/* 401 */           if (account != null) {
/* 402 */               data = am.getUserData(account, KEY_NORMAL_VIDEO_PLAYABLE);
                    }
/* 404 */           if (data != null) {
/* 405 */               return SystemUtil.PrefBooleanState.valueOf(data);
                    }
/* 407 */           return SystemUtil.PrefBooleanState.UNKNOWN;
                }

/* 412 */       public static Session createSession(Context context, Account account) {
/* 413 */           AccountManager am = AccountManager.get(context);
/* 414 */           long userId = getUserId(am, account);
/* 415 */           String edition = SettingsManager.getEdition(context);
/* 416 */           return new Session(getSessionKey(am, account), userId, getName(am, account), getAvatarUrl(am, account), edition);
                }

/* 421 */       public static Session getCachedActiveSession() {
/* 422 */           return mActiveSession;
                }

/* 425 */       public static Session getActiveSession(Context context, boolean readOnly) {
/* 427 */           if (mActiveSession == null) {
/* 428 */               AccountManager am = AccountManager.get(context);
/* 429 */               Account[] accounts = am.getAccountsByType(ACCOUNT_TYPE);
/* 431 */               if (accounts.length > 0) {
/* 433 */                   String accountName = SettingsManager.getCurrentAccount(context);
                            for (Account account : accounts) {
/* 437 */                       if (accountName == null || account.name.equals(accountName)) {
/* 440 */                           Session session = getSession(context, account.name);
/* 441 */                           session.setLoginEmail(account.name);
/* 442 */                           session.setLoginStatus(Session.LoginStatus.LOGGED_IN);
/* 443 */                           if (!readOnly) {
/* 444 */                               setActiveSession(context, session);
                                    }
/* 446 */                           mActiveSession = session;
                                    return session;
                                }
                            }
                        }
/* 454 */               Session session2 = createSession();
/* 457 */               if (!readOnly) {
/* 458 */                   setActiveSession(context, session2);
                        }
/* 460 */               mActiveSession = session2;
                    }
/* 463 */           return mActiveSession;
                }

/* 466 */       private static Session createSession() {
/* 467 */           return new Session(null, 0L, null, null, null);
                }

/* 477 */       public static void setActiveSession(Context context, Session session) {
/* 478 */           SettingsManager.setCurrentAccount(context, session.getLoginEmail(), session.getName(), session.getUserId());
                }

/* 482 */       public static Session getSession(Context context, String accountName) {
/* 483 */           if (accountName == null) {
/* 484 */               return createSession();
                    }
/* 486 */           if (mActiveSession == null) {
/* 487 */               Account account = getAccount(context, accountName);
/* 488 */               if (account == null) {
/* 489 */                   mActiveSession = createSession();
                        } else {
/* 491 */                   mActiveSession = createSession(context, account);
                        }
                    }
/* 494 */           return mActiveSession;
                }

/* 497 */       public static void resetActiveSession() {
/* 498 */           mActiveSession = null;
                }

/* 501 */       public static Account getActiveAccount(Context context) {
/* 502 */           return getAccount(context, AppController.getInstance(context).getActiveSession().getLoginEmail());
                }

/* 506 */       public static Account getActiveAccountReadOnly(Context context) {
/* 507 */           return getAccount(context, AppController.getInstance(context).getActiveSessionReadOnly().getLoginEmail());
                }

/* 511 */       public static boolean isLoggedIn(Context context, boolean readOnly) {
/* 512 */           return getActiveSession(context, readOnly).isLoggedIn();
                }

/* 518 */       private static String encrypt(String key, String cleartext) throws Exception {
/* 519 */           SecretKeySpec secretKeySpec = new SecretKeySpec(getKeyDigest(key), "AES");
/* 520 */           Cipher cipher = Cipher.getInstance("AES");
/* 521 */           cipher.init(1, secretKeySpec);
/* 522 */           byte[] encrypted = cipher.doFinal(cleartext.getBytes(IoUtils.UTF_8));
/* 524 */           return Base64.encodeToString(encrypted, 0);
                }

/* 527 */       private static String decrypt(String key, String encrypted) throws Exception {
/* 528 */           SecretKeySpec secretKeySpec = new SecretKeySpec(getKeyDigest(key), "AES");
/* 529 */           Cipher cipher = Cipher.getInstance("AES");
/* 530 */           cipher.init(2, secretKeySpec);
/* 532 */           byte[] decrypted = cipher.doFinal(Base64.decode(encrypted, 0));
/* 533 */           return new String(decrypted, IoUtils.UTF_8);
                }

/* 536 */       private static byte[] getKeyDigest(String key) throws Exception {
/* 537 */           MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
/* 538 */           messageDigest.update(key.getBytes(IoUtils.UTF_8));
/* 539 */           return messageDigest.digest();
                }

/* 542 */       private static String getDeviceId(Context context) {
/* 543 */           // Android 10+ blocks access to the device IMEI/serial for ordinary apps.
/* 544 */           // This value is only used to encrypt the legacy account password; use a
/* 545 */           // stable app/device identifier and keep the old telephony path as a fallback.
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

/* 552 */       public static void migratePassword(AccountManager am, Account account, Context context) {
                    String password;
/* 553 */           if (am == null || account == null) {
/* 554 */               SLog.m464i("Skipping migrate password check due to null AccountManager or Account");
/* 574 */               return;
                    }
/* 558 */           String passwordFlag = am.getUserData(account, KEY_PASSWORD_ENCRYPTED);
/* 559 */           if ((passwordFlag == null || !Boolean.parseBoolean(passwordFlag)) && (password = am.getPassword(account)) != null) {
                        try {
/* 564 */                   am.setPassword(account, encrypt(getDeviceId(context), password));
/* 566 */                   am.setUserData(account, KEY_PASSWORD_ENCRYPTED, String.valueOf(true));
                        } catch (Exception e) {
/* 568 */                   SLog.m459e("Error migrating password.", (Throwable) e);
/* 569 */                   CrashUtil.logException(e);
                        }
                    }
                }
            }
