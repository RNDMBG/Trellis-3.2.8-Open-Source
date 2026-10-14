            package android.support.v4.accessibilityservice;

            import android.accessibilityservice.AccessibilityServiceInfo;
            import android.content.pm.ResolveInfo;
            import android.os.Build;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 37 */    public class AccessibilityServiceInfoCompat {
                public static final int CAPABILITY_CAN_FILTER_KEY_EVENTS = 8;
                public static final int CAPABILITY_CAN_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 4;
                public static final int CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION = 2;
                public static final int CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT = 1;
                public static final int DEFAULT = 1;
                public static final int FEEDBACK_ALL_MASK = -1;
                public static final int FEEDBACK_BRAILLE = 32;
                public static final int FLAG_INCLUDE_NOT_IMPORTANT_VIEWS = 2;
                public static final int FLAG_REPORT_VIEW_IDS = 16;
                public static final int FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 8;
                public static final int FLAG_REQUEST_FILTER_KEY_EVENTS = 32;
                public static final int FLAG_REQUEST_TOUCH_EXPLORATION_MODE = 4;
                private static final AccessibilityServiceInfoVersionImpl IMPL;

                interface AccessibilityServiceInfoVersionImpl {
                    boolean getCanRetrieveWindowContent(AccessibilityServiceInfo accessibilityServiceInfo);

                    int getCapabilities(AccessibilityServiceInfo accessibilityServiceInfo);

                    String getDescription(AccessibilityServiceInfo accessibilityServiceInfo);

                    String getId(AccessibilityServiceInfo accessibilityServiceInfo);

                    ResolveInfo getResolveInfo(AccessibilityServiceInfo accessibilityServiceInfo);

                    String getSettingsActivityName(AccessibilityServiceInfo accessibilityServiceInfo);
                }

/* 38 */        static class AccessibilityServiceInfoStubImpl implements AccessibilityServiceInfoVersionImpl {
/* 39 */            AccessibilityServiceInfoStubImpl() {
                    }

                    @Override // android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat.AccessibilityServiceInfoVersionImpl
/* 42 */            public boolean getCanRetrieveWindowContent(AccessibilityServiceInfo info) {
/* 43 */                return false;
                    }

                    @Override // android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat.AccessibilityServiceInfoVersionImpl
/* 46 */            public String getDescription(AccessibilityServiceInfo info) {
/* 47 */                return null;
                    }

                    @Override // android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat.AccessibilityServiceInfoVersionImpl
/* 50 */            public String getId(AccessibilityServiceInfo info) {
/* 51 */                return null;
                    }

                    @Override // android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat.AccessibilityServiceInfoVersionImpl
/* 54 */            public ResolveInfo getResolveInfo(AccessibilityServiceInfo info) {
/* 55 */                return null;
                    }

                    @Override // android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat.AccessibilityServiceInfoVersionImpl
/* 58 */            public String getSettingsActivityName(AccessibilityServiceInfo info) {
/* 59 */                return null;
                    }

                    @Override // android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat.AccessibilityServiceInfoVersionImpl
/* 62 */            public int getCapabilities(AccessibilityServiceInfo info) {
/* 63 */                return 0;
                    }
                }

/* 65 */        static class AccessibilityServiceInfoIcsImpl extends AccessibilityServiceInfoStubImpl {
/* 66 */            AccessibilityServiceInfoIcsImpl() {
                    }

                    @Override // android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat.AccessibilityServiceInfoStubImpl, android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat.AccessibilityServiceInfoVersionImpl
/* 70 */            public boolean getCanRetrieveWindowContent(AccessibilityServiceInfo info) {
/* 71 */                return AccessibilityServiceInfoCompatIcs.getCanRetrieveWindowContent(info);
                    }

                    @Override // android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat.AccessibilityServiceInfoStubImpl, android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat.AccessibilityServiceInfoVersionImpl
/* 75 */            public String getDescription(AccessibilityServiceInfo info) {
/* 76 */                return AccessibilityServiceInfoCompatIcs.getDescription(info);
                    }

                    @Override // android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat.AccessibilityServiceInfoStubImpl, android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat.AccessibilityServiceInfoVersionImpl
/* 80 */            public String getId(AccessibilityServiceInfo info) {
/* 81 */                return AccessibilityServiceInfoCompatIcs.getId(info);
                    }

                    @Override // android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat.AccessibilityServiceInfoStubImpl, android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat.AccessibilityServiceInfoVersionImpl
/* 85 */            public ResolveInfo getResolveInfo(AccessibilityServiceInfo info) {
/* 86 */                return AccessibilityServiceInfoCompatIcs.getResolveInfo(info);
                    }

                    @Override // android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat.AccessibilityServiceInfoStubImpl, android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat.AccessibilityServiceInfoVersionImpl
/* 90 */            public String getSettingsActivityName(AccessibilityServiceInfo info) {
/* 91 */                return AccessibilityServiceInfoCompatIcs.getSettingsActivityName(info);
                    }

                    @Override // android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat.AccessibilityServiceInfoStubImpl, android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat.AccessibilityServiceInfoVersionImpl
/* 95 */            public int getCapabilities(AccessibilityServiceInfo info) {
                        return getCanRetrieveWindowContent(info) ? 1 : 0;
                    }
                }

/* 101 */       static class AccessibilityServiceInfoJellyBeanMr2 extends AccessibilityServiceInfoIcsImpl {
/* 102 */           AccessibilityServiceInfoJellyBeanMr2() {
                    }

                    @Override // android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat.AccessibilityServiceInfoIcsImpl, android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat.AccessibilityServiceInfoStubImpl, android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat.AccessibilityServiceInfoVersionImpl
/* 105 */           public int getCapabilities(AccessibilityServiceInfo info) {
/* 106 */               return AccessibilityServiceInfoCompatJellyBeanMr2.getCapabilities(info);
                    }
                }

                static {
/* 111 */           if (Build.VERSION.SDK_INT >= 18) {
/* 112 */               IMPL = new AccessibilityServiceInfoJellyBeanMr2();
                    } else if (Build.VERSION.SDK_INT >= 14) {
/* 114 */               IMPL = new AccessibilityServiceInfoIcsImpl();
                    } else {
/* 116 */               IMPL = new AccessibilityServiceInfoStubImpl();
                    }
                }

/* 279 */       private AccessibilityServiceInfoCompat() {
                }

/* 292 */       public static String getId(AccessibilityServiceInfo info) {
/* 293 */           return IMPL.getId(info);
                }

/* 304 */       public static ResolveInfo getResolveInfo(AccessibilityServiceInfo info) {
/* 305 */           return IMPL.getResolveInfo(info);
                }

/* 317 */       public static String getSettingsActivityName(AccessibilityServiceInfo info) {
/* 318 */           return IMPL.getSettingsActivityName(info);
                }

/* 330 */       public static boolean getCanRetrieveWindowContent(AccessibilityServiceInfo info) {
/* 331 */           return IMPL.getCanRetrieveWindowContent(info);
                }

/* 343 */       public static String getDescription(AccessibilityServiceInfo info) {
/* 344 */           return IMPL.getDescription(info);
                }

/* 355 */       public static String feedbackTypeToString(int feedbackType) {
/* 356 */           StringBuilder builder = new StringBuilder();
/* 357 */           builder.append("[");
/* 358 */           while (feedbackType > 0) {
/* 359 */               int feedbackTypeFlag = 1 << Integer.numberOfTrailingZeros(feedbackType);
/* 360 */               feedbackType &= feedbackTypeFlag ^ (-1);
/* 361 */               if (builder.length() > 1) {
/* 362 */                   builder.append(", ");
                        }
                        switch (feedbackTypeFlag) {
                            case 1:
/* 375 */                       builder.append("FEEDBACK_SPOKEN");
                                break;
                            case 2:
/* 369 */                       builder.append("FEEDBACK_HAPTIC");
                                break;
                            case 4:
/* 366 */                       builder.append("FEEDBACK_AUDIBLE");
                                break;
                            case 8:
/* 378 */                       builder.append("FEEDBACK_VISUAL");
                                break;
                            case 16:
/* 372 */                       builder.append("FEEDBACK_GENERIC");
                                break;
                        }
                    }
/* 382 */           builder.append("]");
/* 383 */           return builder.toString();
                }

/* 394 */       public static String flagToString(int flag) {
                    switch (flag) {
                        case 1:
/* 397 */                   return "DEFAULT";
                        case 2:
/* 399 */                   return "FLAG_INCLUDE_NOT_IMPORTANT_VIEWS";
                        case 4:
/* 401 */                   return "FLAG_REQUEST_TOUCH_EXPLORATION_MODE";
                        case 8:
/* 403 */                   return "FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY";
                        case 16:
/* 405 */                   return "FLAG_REPORT_VIEW_IDS";
                        case 32:
/* 407 */                   return "FLAG_REQUEST_FILTER_KEY_EVENTS";
                        default:
/* 409 */                   return null;
                    }
                }

/* 425 */       public static int getCapabilities(AccessibilityServiceInfo info) {
/* 426 */           return IMPL.getCapabilities(info);
                }

/* 437 */       public static String capabilityToString(int capability) {
                    switch (capability) {
                        case 1:
/* 440 */                   return "CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT";
                        case 2:
/* 442 */                   return "CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION";
                        case 3:
                        case 5:
                        case 6:
                        case 7:
                        default:
/* 448 */                   return "UNKNOWN";
                        case 4:
/* 444 */                   return "CAPABILITY_CAN_REQUEST_ENHANCED_WEB_ACCESSIBILITY";
                        case 8:
/* 446 */                   return "CAPABILITY_CAN_FILTER_KEY_EVENTS";
                    }
                }
            }
