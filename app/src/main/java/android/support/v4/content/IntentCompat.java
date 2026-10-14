            package android.support.v4.content;

            import android.content.ComponentName;
            import android.content.Intent;
            import android.os.Build;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 33 */    public class IntentCompat {
                public static final String ACTION_EXTERNAL_APPLICATIONS_AVAILABLE = "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE";
                public static final String ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE = "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE";
                public static final String EXTRA_CHANGED_PACKAGE_LIST = "android.intent.extra.changed_package_list";
                public static final String EXTRA_CHANGED_UID_LIST = "android.intent.extra.changed_uid_list";
                public static final String EXTRA_HTML_TEXT = "android.intent.extra.HTML_TEXT";
                public static final int FLAG_ACTIVITY_CLEAR_TASK = 32768;
                public static final int FLAG_ACTIVITY_TASK_ON_HOME = 16384;
                private static final IntentCompatImpl IMPL;

                interface IntentCompatImpl {
                    Intent makeMainActivity(ComponentName componentName);

                    Intent makeMainSelectorActivity(String str, String str2);

                    Intent makeRestartActivityTask(ComponentName componentName);
                }

/* 34 */        static class IntentCompatImplBase implements IntentCompatImpl {
/* 35 */            IntentCompatImplBase() {
                    }

                    @Override // android.support.v4.content.IntentCompat.IntentCompatImpl
/* 38 */            public Intent makeMainActivity(ComponentName componentName) {
/* 39 */                Intent intent = new Intent("android.intent.action.MAIN");
/* 40 */                intent.setComponent(componentName);
/* 41 */                intent.addCategory("android.intent.category.LAUNCHER");
/* 42 */                return intent;
                    }

                    @Override // android.support.v4.content.IntentCompat.IntentCompatImpl
/* 50 */            public Intent makeMainSelectorActivity(String selectorAction, String selectorCategory) {
/* 51 */                Intent intent = new Intent(selectorAction);
/* 52 */                intent.addCategory(selectorCategory);
/* 53 */                return intent;
                    }

                    @Override // android.support.v4.content.IntentCompat.IntentCompatImpl
/* 57 */            public Intent makeRestartActivityTask(ComponentName mainActivity) {
/* 58 */                Intent intent = makeMainActivity(mainActivity);
/* 59 */                intent.addFlags(268468224);
/* 61 */                return intent;
                    }
                }

/* 63 */        static class IntentCompatImplHC extends IntentCompatImplBase {
/* 64 */            IntentCompatImplHC() {
                    }

                    @Override // android.support.v4.content.IntentCompat.IntentCompatImplBase, android.support.v4.content.IntentCompat.IntentCompatImpl
/* 67 */            public Intent makeMainActivity(ComponentName componentName) {
/* 68 */                return IntentCompatHoneycomb.makeMainActivity(componentName);
                    }

                    @Override // android.support.v4.content.IntentCompat.IntentCompatImplBase, android.support.v4.content.IntentCompat.IntentCompatImpl
/* 71 */            public Intent makeRestartActivityTask(ComponentName componentName) {
/* 72 */                return IntentCompatHoneycomb.makeRestartActivityTask(componentName);
                    }
                }

/* 74 */        static class IntentCompatImplIcsMr1 extends IntentCompatImplHC {
/* 75 */            IntentCompatImplIcsMr1() {
                    }

                    @Override // android.support.v4.content.IntentCompat.IntentCompatImplBase, android.support.v4.content.IntentCompat.IntentCompatImpl
/* 78 */            public Intent makeMainSelectorActivity(String selectorAction, String selectorCategory) {
/* 79 */                return IntentCompatIcsMr1.makeMainSelectorActivity(selectorAction, selectorCategory);
                    }
                }

                static {
/* 85 */            int version = Build.VERSION.SDK_INT;
/* 86 */            if (version >= 15) {
/* 87 */                IMPL = new IntentCompatImplIcsMr1();
                    } else if (version >= 11) {
/* 89 */                IMPL = new IntentCompatImplHC();
                    } else {
/* 91 */                IMPL = new IntentCompatImplBase();
                    }
                }

/* 94 */        private IntentCompat() {
                }

/* 220 */       public static Intent makeMainActivity(ComponentName mainActivity) {
/* 221 */           return IMPL.makeMainActivity(mainActivity);
                }

/* 248 */       public static Intent makeMainSelectorActivity(String selectorAction, String selectorCategory) {
/* 249 */           return IMPL.makeMainSelectorActivity(selectorAction, selectorCategory);
                }

/* 265 */       public static Intent makeRestartActivityTask(ComponentName mainActivity) {
/* 266 */           return IMPL.makeRestartActivityTask(mainActivity);
                }
            }
