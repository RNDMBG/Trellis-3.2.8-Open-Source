            package com.twitter.android.sdk;

            import android.app.Activity;
            import android.content.ActivityNotFoundException;
            import android.content.ComponentName;
            import android.content.Context;
            import android.content.Intent;
            import android.content.pm.PackageInfo;
            import android.content.pm.PackageManager;
            import android.content.pm.Signature;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 81 */    public class Twitter {
                private static final String APP_SIGNATURE = "3082025d308201c6a00302010202044bd76cce300d06092a864886f70d01010505003073310b3009060355040613025553310b3009060355040813024341311630140603550407130d53616e204672616e636973636f31163014060355040a130d547769747465722c20496e632e310f300d060355040b13064d6f62696c65311630140603550403130d4c656c616e6420526563686973301e170d3130303432373233303133345a170d3438303832353233303133345a3073310b3009060355040613025553310b3009060355040813024341311630140603550407130d53616e204672616e636973636f31163014060355040a130d547769747465722c20496e632e310f300d060355040b13064d6f62696c65311630140603550403130d4c656c616e642052656368697330819f300d06092a864886f70d010101050003818d003081890281810086233c2e51c62232d49cc932e470713d63a6a1106b38f9e442e01bc79ca4f95c72b2cb3f1369ef7dea6036bff7c4b2828cb3787e7657ad83986751ced5b131fcc6f413efb7334e32ed9787f9e9a249ae108fa66009ac7a7932c25d37e1e07d4f9f66aa494c270dbac87d261c9668d321c2fba4ef2800e46671a597ff2eac5d7f0203010001300d06092a864886f70d0101050500038181003e1f01cb6ea8be8d2cecef5cd2a64c97ba8728aa5f08f8275d00508d64d139b6a72c5716b40a040df0eeeda04de9361107e123ee8d3dc05e70c8a355f46dbadf1235443b0b214c57211afd4edd147451c443d49498d2a7ff27e45a99c39b9e47429a1dae843ba233bf8ca81296dbe1dc5c5434514d995b0279246809392a219b";
                private static final String EXTRA_CONSUMER_KEY = "ck";
                private static final String EXTRA_CONSUMER_SECRET = "cs";
                public static final String EXTRA_SCREEN_NAME = "screen_name";
                public static final String EXTRA_TOKEN = "tk";
                public static final String EXTRA_TOKEN_SECRET = "ts";
                public static final String EXTRA_USER_ID = "user_id";
                private String mConsumerKey;
                private String mConsumerSecret;
                private static final String PACKAGE_NAME = "com.twitter.android";
                private static final String AUTH_CLASS_NAME = "com.twitter.android.AuthorizeAppActivity";
                private static final ComponentName AUTH_ACTIVITY = new ComponentName(PACKAGE_NAME, AUTH_CLASS_NAME);

/* 82 */        public Twitter(String consumerKey, String consumerSecret) {
/* 84 */            if (consumerKey == null || consumerSecret == null) {
/* 85 */                throw new IllegalArgumentException("consumerKey, consumerSecret, and getTokenListener must be non-null.");
                    }
/* 89 */            this.mConsumerKey = consumerKey;
/* 90 */            this.mConsumerSecret = consumerSecret;
                }

/* 103 */       public boolean startAuthActivityForResult(Activity activity, int requestCode) {
/* 104 */           PackageManager pm = activity.getPackageManager();
/* 106 */           if (!checkAppSignature(pm)) {
                        return false;
                    }
/* 110 */           Intent intent = new Intent().setComponent(AUTH_ACTIVITY);
/* 111 */           ComponentName cn = intent.resolveActivity(pm);
/* 113 */           if (!cn.equals(AUTH_ACTIVITY)) {
                        return false;
                    }
/* 117 */           intent.putExtra(EXTRA_CONSUMER_KEY, this.mConsumerKey).putExtra(EXTRA_CONSUMER_SECRET, this.mConsumerSecret);
                    try {
/* 121 */               activity.startActivityForResult(intent, requestCode);
/* 122 */               return true;
                    } catch (ActivityNotFoundException e) {
                        return false;
                    }
                }

/* 134 */       public static boolean isAvailable(Context context) {
/* 135 */           return checkAppSignature(context.getPackageManager());
                }

/* 141 */       private static boolean checkAppSignature(PackageManager pm) {
                    try {
/* 142 */               PackageInfo p = pm.getPackageInfo(PACKAGE_NAME, 64);
/* 147 */               Signature[] arr$ = p.signatures;
                        for (Signature s : arr$) {
/* 148 */                   if (APP_SIGNATURE.equals(s.toCharsString())) {
/* 149 */                       return true;
                            }
                        }
                        return false;
                    } catch (PackageManager.NameNotFoundException e) {
                        return false;
                    }
                }
            }
