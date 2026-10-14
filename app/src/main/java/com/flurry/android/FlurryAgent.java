            package com.flurry.android;

            import android.content.Context;
            import android.location.Criteria;
            import android.os.Build;
            import com.flurry.sdk.C0784cu;
            import com.flurry.sdk.C0798di;
            import com.flurry.sdk.C0800dk;
            import com.flurry.sdk.C0802dm;
            import com.flurry.sdk.C0812dw;
            import com.flurry.sdk.C0827el;
            import com.flurry.sdk.C0843fb;
            import java.util.Date;
            import java.util.Map;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 38 */    public final class FlurryAgent {

                /* JADX INFO: renamed from: a */
                private static final String f527a = FlurryAgent.class.getSimpleName();

/* 39 */        private FlurryAgent() {
                }

/* 57 */        public static void setVersionName(String str) {
/* 58 */            if (Build.VERSION.SDK_INT < 10) {
/* 59 */                C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else if (str == null) {
/* 63 */                C0827el.m893b(f527a, "String versionName passed to setVersionName was null.");
                    } else {
/* 67 */                C0802dm.m742a().m748a("VersionName", (Object) str);
                    }
                }

/* 74 */        public static int getAgentVersion() {
/* 75 */            return C0800dk.m720a().m721b();
                }

/* 83 */        public static String getReleaseVersion() {
/* 84 */            return C0800dk.m720a().m726g();
                }

/* 91 */        public static void setReportLocation(boolean z) {
/* 92 */            if (Build.VERSION.SDK_INT < 10) {
/* 93 */                C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else {
/* 96 */                C0802dm.m742a().m748a("ReportLocation", (Object) Boolean.valueOf(z));
                    }
                }

/* 107 */       public static void setLocation(float f, float f2) {
/* 108 */           if (Build.VERSION.SDK_INT < 10) {
/* 109 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else {
/* 112 */               C0812dw.m781a().m789a(f, f2);
                    }
                }

/* 118 */       public static void clearLocation() {
/* 119 */           if (Build.VERSION.SDK_INT < 10) {
/* 120 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else {
/* 123 */               C0812dw.m781a().m793e();
                    }
                }

/* 130 */       public static void setLogEnabled(boolean z) {
/* 131 */           if (Build.VERSION.SDK_INT < 10) {
/* 132 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else if (z) {
/* 136 */               C0827el.m890b();
                    } else {
/* 138 */               C0827el.m883a();
                    }
                }

/* 146 */       public static void setLogLevel(int i) {
/* 147 */           if (Build.VERSION.SDK_INT < 10) {
/* 148 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else {
/* 151 */               C0827el.m884a(i);
                    }
                }

/* 168 */       public static void setContinueSessionMillis(long j) {
/* 169 */           if (Build.VERSION.SDK_INT < 10) {
/* 170 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else if (j < 5000) {
/* 174 */               C0827el.m893b(f527a, "Invalid time set for session resumption: " + j);
                    } else {
/* 178 */               C0802dm.m742a().m748a("ContinueSessionMillis", (Object) Long.valueOf(j));
                    }
                }

/* 185 */       public static void setLogEvents(boolean z) {
/* 186 */           if (Build.VERSION.SDK_INT < 10) {
/* 187 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else {
/* 190 */               C0802dm.m742a().m748a("LogEvents", (Object) Boolean.valueOf(z));
                    }
                }

/* 198 */       public static void setUseHttps(boolean z) {
/* 199 */           if (Build.VERSION.SDK_INT < 10) {
/* 200 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else {
/* 203 */               C0802dm.m742a().m748a("UseHttps", (Object) Boolean.valueOf(z));
                    }
                }

/* 207 */       public static boolean getUseHttps() {
/* 208 */           if (Build.VERSION.SDK_INT < 10) {
/* 209 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
/* 210 */               return false;
                    }
/* 212 */           return ((Boolean) C0802dm.m742a().m746a("UseHttps")).booleanValue();
                }

/* 223 */       public static void setCaptureUncaughtExceptions(boolean z) {
/* 224 */           if (Build.VERSION.SDK_INT < 10) {
/* 225 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else {
/* 228 */               C0802dm.m742a().m748a("CaptureUncaughtExceptions", (Object) Boolean.valueOf(z));
                    }
                }

/* 240 */       public static void addOrigin(String str, String str2) {
/* 241 */           addOrigin(str, str2, null);
                }

/* 261 */       public static void addOrigin(String str, String str2, Map<String, String> map) {
/* 262 */           if (Build.VERSION.SDK_INT < 10) {
/* 263 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
/* 278 */               return;
                    }
/* 266 */           if (str == null || str.length() == 0) {
/* 267 */               throw new IllegalArgumentException("originName not specified");
                    }
/* 269 */           if (str2 == null || str2.length() == 0) {
/* 270 */               throw new IllegalArgumentException("originVersion not specified");
                    }
                    try {
/* 274 */               C0784cu.m532a().m538a(str, str2, map);
                    } catch (Throwable th) {
/* 276 */               C0827el.m888a(f527a, "", th);
                    }
                }

/* 313 */       public static void onStartSession(Context context, String str) {
/* 314 */           if (Build.VERSION.SDK_INT < 10) {
/* 315 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
/* 330 */               return;
                    }
/* 318 */           if (context == null) {
/* 319 */               throw new NullPointerException("Null context");
                    }
/* 321 */           if (str == null || str.length() == 0) {
/* 322 */               throw new IllegalArgumentException("Api key not specified");
                    }
                    try {
/* 326 */               C0798di.m709a().m711a(context, str);
                    } catch (Throwable th) {
/* 328 */               C0827el.m888a(f527a, "", th);
                    }
                }

/* 354 */       public static void onEndSession(Context context) {
/* 355 */           if (Build.VERSION.SDK_INT < 10) {
/* 356 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else {
/* 359 */               if (context == null) {
/* 360 */                   throw new NullPointerException("Null context");
                        }
                        try {
/* 364 */                   C0798di.m709a().m710a(context);
                        } catch (Throwable th) {
/* 366 */                   C0827el.m888a(f527a, "", th);
                        }
                    }
                }

/* 388 */       public static void logEvent(String str) {
/* 389 */           if (Build.VERSION.SDK_INT < 10) {
/* 390 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
/* 402 */               return;
                    }
/* 393 */           if (str == null) {
/* 394 */               C0827el.m893b(f527a, "String eventId passed to logEvent was null.");
/* 402 */               return;
                    }
                    try {
/* 398 */               C0784cu.m532a().m534a(str);
                    } catch (Throwable th) {
/* 400 */               C0827el.m888a(f527a, "Failed to log event: " + str, th);
                    }
                }

/* 422 */       public static void logEvent(String str, Map<String, String> map) {
/* 423 */           if (Build.VERSION.SDK_INT < 10) {
/* 424 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
/* 440 */               return;
                    }
/* 427 */           if (str == null) {
/* 428 */               C0827el.m893b(f527a, "String eventId passed to logEvent was null.");
/* 440 */               return;
                    }
/* 431 */           if (map == null) {
/* 432 */               C0827el.m893b(f527a, "String parameters passed to logEvent was null.");
/* 440 */               return;
                    }
                    try {
/* 436 */               C0784cu.m532a().m539a(str, map);
                    } catch (Throwable th) {
/* 438 */               C0827el.m888a(f527a, "Failed to log event: " + str, th);
                    }
                }

/* 451 */       public static void logEvent(String str, boolean z) {
/* 452 */           if (Build.VERSION.SDK_INT < 10) {
/* 453 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
/* 465 */               return;
                    }
/* 456 */           if (str == null) {
/* 457 */               C0827el.m893b(f527a, "String eventId passed to logEvent was null.");
/* 465 */               return;
                    }
                    try {
/* 461 */               C0784cu.m532a().m541a(str, z);
                    } catch (Throwable th) {
/* 463 */               C0827el.m888a(f527a, "Failed to log event: " + str, th);
                    }
                }

/* 478 */       public static void logEvent(String str, Map<String, String> map, boolean z) {
/* 479 */           if (Build.VERSION.SDK_INT < 10) {
/* 480 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
/* 496 */               return;
                    }
/* 483 */           if (str == null) {
/* 484 */               C0827el.m893b(f527a, "String eventId passed to logEvent was null.");
/* 496 */               return;
                    }
/* 487 */           if (map == null) {
/* 488 */               C0827el.m893b(f527a, "String parameters passed to logEvent was null.");
/* 496 */               return;
                    }
                    try {
/* 492 */               C0784cu.m532a().m540a(str, map, z);
                    } catch (Throwable th) {
/* 494 */               C0827el.m888a(f527a, "Failed to log event: " + str, th);
                    }
                }

/* 503 */       public static void endTimedEvent(String str) {
/* 504 */           if (Build.VERSION.SDK_INT < 10) {
/* 505 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
/* 517 */               return;
                    }
/* 508 */           if (str == null) {
/* 509 */               C0827el.m893b(f527a, "String eventId passed to endTimedEvent was null.");
/* 517 */               return;
                    }
                    try {
/* 513 */               C0784cu.m532a().m544b(str);
                    } catch (Throwable th) {
/* 515 */               C0827el.m888a(f527a, "Failed to signify the end of event: " + str, th);
                    }
                }

/* 528 */       public static void endTimedEvent(String str, Map<String, String> map) {
/* 529 */           if (Build.VERSION.SDK_INT < 10) {
/* 530 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
/* 546 */               return;
                    }
/* 533 */           if (str == null) {
/* 534 */               C0827el.m893b(f527a, "String eventId passed to endTimedEvent was null.");
/* 546 */               return;
                    }
/* 537 */           if (map == null) {
/* 538 */               C0827el.m893b(f527a, "String eventId passed to endTimedEvent was null.");
/* 546 */               return;
                    }
                    try {
/* 542 */               C0784cu.m532a().m545b(str, map);
                    } catch (Throwable th) {
/* 544 */               C0827el.m888a(f527a, "Failed to signify the end of event: " + str, th);
                    }
                }

                @Deprecated
/* 559 */       public static void onError(String str, String str2, String str3) {
/* 560 */           if (Build.VERSION.SDK_INT < 10) {
/* 561 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
/* 581 */               return;
                    }
/* 564 */           if (str == null) {
/* 565 */               C0827el.m893b(f527a, "String errorId passed to onError was null.");
/* 581 */               return;
                    }
/* 568 */           if (str2 == null) {
/* 569 */               C0827el.m893b(f527a, "String message passed to onError was null.");
/* 581 */               return;
                    }
/* 572 */           if (str3 == null) {
/* 573 */               C0827el.m893b(f527a, "String errorClass passed to onError was null.");
/* 581 */               return;
                    }
                    try {
/* 577 */               C0784cu.m532a().m536a(str, str2, str3);
                    } catch (Throwable th) {
/* 579 */               C0827el.m888a(f527a, "", th);
                    }
                }

/* 583 */       public static void onError(String str, String str2, Throwable th) {
/* 584 */           if (Build.VERSION.SDK_INT < 10) {
/* 585 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
/* 605 */               return;
                    }
/* 588 */           if (str == null) {
/* 589 */               C0827el.m893b(f527a, "String errorId passed to onError was null.");
/* 605 */               return;
                    }
/* 592 */           if (str2 == null) {
/* 593 */               C0827el.m893b(f527a, "String message passed to onError was null.");
/* 605 */               return;
                    }
/* 596 */           if (th == null) {
/* 597 */               C0827el.m893b(f527a, "Throwable passed to onError was null.");
/* 605 */               return;
                    }
                    try {
/* 601 */               C0784cu.m532a().m537a(str, str2, th);
                    } catch (Throwable th2) {
/* 603 */               C0827el.m888a(f527a, "", th2);
                    }
                }

                @Deprecated
/* 612 */       public static void onEvent(String str) {
/* 613 */           if (Build.VERSION.SDK_INT < 10) {
/* 614 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
/* 626 */               return;
                    }
/* 617 */           if (str == null) {
/* 618 */               C0827el.m893b(f527a, "String eventId passed to onEvent was null.");
/* 626 */               return;
                    }
                    try {
/* 622 */               C0784cu.m532a().m547c(str);
                    } catch (Throwable th) {
/* 624 */               C0827el.m888a(f527a, "", th);
                    }
                }

                @Deprecated
/* 634 */       public static void onEvent(String str, Map<String, String> map) {
/* 635 */           if (Build.VERSION.SDK_INT < 10) {
/* 636 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
/* 652 */               return;
                    }
/* 639 */           if (str == null) {
/* 640 */               C0827el.m893b(f527a, "String eventId passed to onEvent was null.");
/* 652 */               return;
                    }
/* 643 */           if (map == null) {
/* 644 */               C0827el.m893b(f527a, "Parameters Map passed to onEvent was null.");
/* 652 */               return;
                    }
                    try {
/* 648 */               C0784cu.m532a().m548c(str, map);
                    } catch (Throwable th) {
/* 650 */               C0827el.m888a(f527a, "", th);
                    }
                }

/* 663 */       public static void onPageView() {
/* 664 */           if (Build.VERSION.SDK_INT < 10) {
/* 665 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
/* 673 */               return;
                    }
                    try {
/* 669 */               C0784cu.m532a().m546c();
                    } catch (Throwable th) {
/* 671 */               C0827el.m888a(f527a, "", th);
                    }
                }

/* 677 */       public static void setReportUrl(String str) {
/* 678 */           if (Build.VERSION.SDK_INT < 10) {
/* 679 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else {
/* 682 */               C0802dm.m742a().m748a("ReportUrl", (Object) str);
                    }
                }

/* 695 */       public static void setLocationCriteria(Criteria criteria) {
/* 696 */           if (Build.VERSION.SDK_INT < 10) {
/* 697 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else {
/* 700 */               C0802dm.m742a().m748a("LocationCriteria", (Object) criteria);
                    }
                }

/* 715 */       public static void setAge(int i) {
/* 716 */           if (Build.VERSION.SDK_INT < 10) {
/* 717 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else if (i > 0 && i < 110) {
/* 728 */               C0802dm.m742a().m748a("Age", (Object) Long.valueOf(new Date(new Date(System.currentTimeMillis() - (((long) i) * 31449600000L)).getYear(), 1, 1).getTime()));
                    }
                }

/* 737 */       public static void setGender(byte b) {
/* 738 */           if (Build.VERSION.SDK_INT < 10) {
/* 739 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    }
                    switch (b) {
                        case 0:
                        case 1:
/* 745 */                   C0802dm.m742a().m748a("Gender", (Object) Byte.valueOf(b));
                            break;
                        default:
/* 749 */                   C0802dm.m742a().m748a("Gender", (Object) (byte) -1);
                            break;
                    }
                }

/* 759 */       public static void setUserId(String str) {
/* 760 */           if (Build.VERSION.SDK_INT < 10) {
/* 761 */               C0827el.m893b(f527a, "Device SDK Version older than 10");
                    } else if (str == null) {
/* 765 */               C0827el.m893b(f527a, "String userId passed to setUserId was null.");
                    } else {
/* 769 */               C0802dm.m742a().m748a("UserId", (Object) C0843fb.m959a(str));
                    }
                }
            }
