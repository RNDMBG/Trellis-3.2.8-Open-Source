            package android.support.v4.app;

            import android.app.Activity;
            import android.content.ComponentName;
            import android.content.Intent;
            import android.content.pm.PackageManager;
            import android.graphics.drawable.Drawable;
            import android.net.Uri;
            import android.os.Build;
            import android.support.v4.content.IntentCompat;
            import android.text.Html;
            import android.text.Spanned;
            import android.util.Log;
            import android.view.Menu;
            import android.view.MenuItem;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 56 */    public class ShareCompat {
                public static final String EXTRA_CALLING_ACTIVITY = "android.support.v4.app.EXTRA_CALLING_ACTIVITY";
                public static final String EXTRA_CALLING_PACKAGE = "android.support.v4.app.EXTRA_CALLING_PACKAGE";
                private static ShareCompatImpl IMPL;

                interface ShareCompatImpl {
                    void configureMenuItem(MenuItem menuItem, IntentBuilder intentBuilder);

                    String escapeHtml(CharSequence charSequence);
                }

/* 57 */        static /* synthetic */ ShareCompatImpl access$000() {
/* 58 */            return IMPL;
                }

/* 83 */        static class ShareCompatImplBase implements ShareCompatImpl {
/* 84 */            ShareCompatImplBase() {
                    }

                    @Override // android.support.v4.app.ShareCompat.ShareCompatImpl
/* 86 */            public void configureMenuItem(MenuItem item, IntentBuilder shareIntent) {
/* 87 */                item.setIntent(shareIntent.createChooserIntent());
                    }

                    @Override // android.support.v4.app.ShareCompat.ShareCompatImpl
/* 90 */            public String escapeHtml(CharSequence text) {
/* 91 */                StringBuilder out = new StringBuilder();
/* 92 */                withinStyle(out, text, 0, text.length());
/* 93 */                return out.toString();
                    }

/* 97 */            private static void withinStyle(StringBuilder out, CharSequence text, int start, int end) {
/* 98 */                int i = start;
                        while (i < end) {
/* 99 */                    char c = text.charAt(i);
/* 101 */                   if (c == '<') {
/* 102 */                       out.append("&lt;");
                            } else if (c == '>') {
/* 104 */                       out.append("&gt;");
                            } else if (c == '&') {
/* 106 */                       out.append("&amp;");
                            } else if (c > '~' || c < ' ') {
/* 108 */                       out.append("&#" + ((int) c) + ";");
                            } else if (c == ' ') {
/* 110 */                       while (i + 1 < end && text.charAt(i + 1) == ' ') {
/* 111 */                           out.append("&nbsp;");
/* 112 */                           i++;
                                }
/* 115 */                       out.append(' ');
                            } else {
/* 117 */                       out.append(c);
                            }
/* 98 */                    i++;
                        }
                    }
                }

/* 121 */       static class ShareCompatImplICS extends ShareCompatImplBase {
/* 122 */           ShareCompatImplICS() {
                    }

                    @Override // android.support.v4.app.ShareCompat.ShareCompatImplBase, android.support.v4.app.ShareCompat.ShareCompatImpl
/* 124 */           public void configureMenuItem(MenuItem item, IntentBuilder shareIntent) {
/* 125 */               ShareCompatICS.configureMenuItem(item, shareIntent.getActivity(), shareIntent.getIntent());
/* 127 */               if (shouldAddChooserIntent(item)) {
/* 128 */                   item.setIntent(shareIntent.createChooserIntent());
                        }
                    }

/* 132 */           boolean shouldAddChooserIntent(MenuItem item) {
                        return !item.hasSubMenu();
                    }
                }

/* 135 */       static class ShareCompatImplJB extends ShareCompatImplICS {
/* 136 */           ShareCompatImplJB() {
                    }

                    @Override // android.support.v4.app.ShareCompat.ShareCompatImplBase, android.support.v4.app.ShareCompat.ShareCompatImpl
/* 138 */           public String escapeHtml(CharSequence html) {
/* 139 */               return ShareCompatJB.escapeHtml(html);
                    }

                    @Override // android.support.v4.app.ShareCompat.ShareCompatImplICS
/* 143 */           boolean shouldAddChooserIntent(MenuItem item) {
/* 144 */               return false;
                    }
                }

                static {
/* 151 */           if (Build.VERSION.SDK_INT >= 16) {
/* 152 */               IMPL = new ShareCompatImplJB();
                    } else if (Build.VERSION.SDK_INT >= 14) {
/* 154 */               IMPL = new ShareCompatImplICS();
                    } else {
/* 156 */               IMPL = new ShareCompatImplBase();
                    }
                }

/* 172 */       public static String getCallingPackage(Activity calledActivity) {
/* 173 */           String result = calledActivity.getCallingPackage();
/* 174 */           if (result == null) {
/* 175 */               return calledActivity.getIntent().getStringExtra(EXTRA_CALLING_PACKAGE);
                    }
/* 177 */           return result;
                }

/* 192 */       public static ComponentName getCallingActivity(Activity calledActivity) {
/* 193 */           ComponentName result = calledActivity.getCallingActivity();
/* 194 */           if (result == null) {
/* 195 */               return (ComponentName) calledActivity.getIntent().getParcelableExtra(EXTRA_CALLING_ACTIVITY);
                    }
/* 197 */           return result;
                }

/* 231 */       public static void configureMenuItem(MenuItem item, IntentBuilder shareIntent) {
/* 232 */           IMPL.configureMenuItem(item, shareIntent);
                }

/* 243 */       public static void configureMenuItem(Menu menu, int menuItemId, IntentBuilder shareIntent) {
/* 244 */           MenuItem item = menu.findItem(menuItemId);
/* 245 */           if (item == null) {
/* 246 */               throw new IllegalArgumentException("Could not find menu item with id " + menuItemId + " in the supplied menu");
                    }
/* 249 */           configureMenuItem(item, shareIntent);
                }

/* 273 */       public static class IntentBuilder {
                    private Activity mActivity;
                    private ArrayList<String> mBccAddresses;
                    private ArrayList<String> mCcAddresses;
                    private CharSequence mChooserTitle;
                    private Intent mIntent = new Intent().setAction("android.intent.action.SEND");
                    private ArrayList<Uri> mStreams;
                    private ArrayList<String> mToAddresses;

/* 274 */           public static IntentBuilder from(Activity launchingActivity) {
/* 275 */               return new IntentBuilder(launchingActivity);
                    }

/* 277 */           private IntentBuilder(Activity launchingActivity) {
/* 279 */               this.mActivity = launchingActivity;
/* 281 */               this.mIntent.putExtra(ShareCompat.EXTRA_CALLING_PACKAGE, launchingActivity.getPackageName());
/* 282 */               this.mIntent.putExtra(ShareCompat.EXTRA_CALLING_ACTIVITY, launchingActivity.getComponentName());
/* 283 */               this.mIntent.addFlags(524288);
                    }

/* 295 */           public Intent getIntent() {
/* 296 */               if (this.mToAddresses != null) {
/* 297 */                   combineArrayExtra("android.intent.extra.EMAIL", this.mToAddresses);
/* 298 */                   this.mToAddresses = null;
                        }
/* 300 */               if (this.mCcAddresses != null) {
/* 301 */                   combineArrayExtra("android.intent.extra.CC", this.mCcAddresses);
/* 302 */                   this.mCcAddresses = null;
                        }
/* 304 */               if (this.mBccAddresses != null) {
/* 305 */                   combineArrayExtra("android.intent.extra.BCC", this.mBccAddresses);
/* 306 */                   this.mBccAddresses = null;
                        }
/* 310 */               boolean needsSendMultiple = this.mStreams != null && this.mStreams.size() > 1;
/* 311 */               boolean isSendMultiple = this.mIntent.getAction().equals("android.intent.action.SEND_MULTIPLE");
/* 313 */               if (!needsSendMultiple && isSendMultiple) {
/* 316 */                   this.mIntent.setAction("android.intent.action.SEND");
/* 317 */                   if (this.mStreams != null && !this.mStreams.isEmpty()) {
/* 318 */                       this.mIntent.putExtra("android.intent.extra.STREAM", this.mStreams.get(0));
                            } else {
/* 320 */                       this.mIntent.removeExtra("android.intent.extra.STREAM");
                            }
/* 322 */                   this.mStreams = null;
                        }
/* 325 */               if (needsSendMultiple && !isSendMultiple) {
/* 328 */                   this.mIntent.setAction("android.intent.action.SEND_MULTIPLE");
/* 329 */                   if (this.mStreams != null && !this.mStreams.isEmpty()) {
/* 330 */                       this.mIntent.putParcelableArrayListExtra("android.intent.extra.STREAM", this.mStreams);
                            } else {
/* 332 */                       this.mIntent.removeExtra("android.intent.extra.STREAM");
                            }
                        }
/* 336 */               return this.mIntent;
                    }

/* 339 */           Activity getActivity() {
/* 340 */               return this.mActivity;
                    }

/* 343 */           private void combineArrayExtra(String extra, ArrayList<String> add) {
/* 344 */               String[] currentAddresses = this.mIntent.getStringArrayExtra(extra);
/* 345 */               int currentLength = currentAddresses != null ? currentAddresses.length : 0;
/* 346 */               String[] finalAddresses = new String[add.size() + currentLength];
/* 347 */               add.toArray(finalAddresses);
/* 348 */               if (currentAddresses != null) {
/* 349 */                   System.arraycopy(currentAddresses, 0, finalAddresses, add.size(), currentLength);
                        }
/* 351 */               this.mIntent.putExtra(extra, finalAddresses);
                    }

/* 355 */           private void combineArrayExtra(String extra, String[] add) {
/* 356 */               Intent intent = getIntent();
/* 357 */               String[] old = intent.getStringArrayExtra(extra);
/* 358 */               int oldLength = old != null ? old.length : 0;
/* 359 */               String[] result = new String[add.length + oldLength];
/* 360 */               if (old != null) {
                            System.arraycopy(old, 0, result, 0, oldLength);
                        }
/* 361 */               System.arraycopy(add, 0, result, oldLength, add.length);
/* 362 */               intent.putExtra(extra, result);
                    }

/* 372 */           public Intent createChooserIntent() {
/* 373 */               return Intent.createChooser(getIntent(), this.mChooserTitle);
                    }

/* 385 */           public void startChooser() {
/* 386 */               this.mActivity.startActivity(createChooserIntent());
                    }

/* 395 */           public IntentBuilder setChooserTitle(CharSequence title) {
/* 396 */               this.mChooserTitle = title;
/* 397 */               return this;
                    }

/* 406 */           public IntentBuilder setChooserTitle(int resId) {
/* 407 */               return setChooserTitle(this.mActivity.getText(resId));
                    }

/* 417 */           public IntentBuilder setType(String mimeType) {
/* 418 */               this.mIntent.setType(mimeType);
/* 419 */               return this;
                    }

/* 430 */           public IntentBuilder setText(CharSequence text) {
/* 431 */               this.mIntent.putExtra("android.intent.extra.TEXT", text);
/* 432 */               return this;
                    }

/* 446 */           public IntentBuilder setHtmlText(String htmlText) {
/* 447 */               this.mIntent.putExtra(IntentCompat.EXTRA_HTML_TEXT, htmlText);
/* 448 */               if (!this.mIntent.hasExtra("android.intent.extra.TEXT")) {
/* 450 */                   setText(Html.fromHtml(htmlText));
                        }
/* 452 */               return this;
                    }

/* 465 */           public IntentBuilder setStream(Uri streamUri) {
/* 466 */               if (!this.mIntent.getAction().equals("android.intent.action.SEND")) {
/* 467 */                   this.mIntent.setAction("android.intent.action.SEND");
                        }
/* 469 */               this.mStreams = null;
/* 470 */               this.mIntent.putExtra("android.intent.extra.STREAM", streamUri);
/* 471 */               return this;
                    }

/* 485 */           public IntentBuilder addStream(Uri streamUri) {
/* 486 */               Uri currentStream = (Uri) this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
/* 487 */               if (currentStream == null) {
/* 488 */                   return setStream(streamUri);
                        }
/* 490 */               if (this.mStreams == null) {
/* 491 */                   this.mStreams = new ArrayList<>();
                        }
/* 493 */               if (currentStream != null) {
/* 494 */                   this.mIntent.removeExtra("android.intent.extra.STREAM");
/* 495 */                   this.mStreams.add(currentStream);
                        }
/* 497 */               this.mStreams.add(streamUri);
/* 498 */               return this;
                    }

/* 509 */           public IntentBuilder setEmailTo(String[] addresses) {
/* 510 */               if (this.mToAddresses != null) {
/* 511 */                   this.mToAddresses = null;
                        }
/* 513 */               this.mIntent.putExtra("android.intent.extra.EMAIL", addresses);
/* 514 */               return this;
                    }

/* 524 */           public IntentBuilder addEmailTo(String address) {
/* 525 */               if (this.mToAddresses == null) {
/* 526 */                   this.mToAddresses = new ArrayList<>();
                        }
/* 528 */               this.mToAddresses.add(address);
/* 529 */               return this;
                    }

/* 539 */           public IntentBuilder addEmailTo(String[] addresses) {
/* 540 */               combineArrayExtra("android.intent.extra.EMAIL", addresses);
/* 541 */               return this;
                    }

/* 552 */           public IntentBuilder setEmailCc(String[] addresses) {
/* 553 */               this.mIntent.putExtra("android.intent.extra.CC", addresses);
/* 554 */               return this;
                    }

/* 564 */           public IntentBuilder addEmailCc(String address) {
/* 565 */               if (this.mCcAddresses == null) {
/* 566 */                   this.mCcAddresses = new ArrayList<>();
                        }
/* 568 */               this.mCcAddresses.add(address);
/* 569 */               return this;
                    }

/* 579 */           public IntentBuilder addEmailCc(String[] addresses) {
/* 580 */               combineArrayExtra("android.intent.extra.CC", addresses);
/* 581 */               return this;
                    }

/* 592 */           public IntentBuilder setEmailBcc(String[] addresses) {
/* 593 */               this.mIntent.putExtra("android.intent.extra.BCC", addresses);
/* 594 */               return this;
                    }

/* 604 */           public IntentBuilder addEmailBcc(String address) {
/* 605 */               if (this.mBccAddresses == null) {
/* 606 */                   this.mBccAddresses = new ArrayList<>();
                        }
/* 608 */               this.mBccAddresses.add(address);
/* 609 */               return this;
                    }

/* 619 */           public IntentBuilder addEmailBcc(String[] addresses) {
/* 620 */               combineArrayExtra("android.intent.extra.BCC", addresses);
/* 621 */               return this;
                    }

/* 631 */           public IntentBuilder setSubject(String subject) {
/* 632 */               this.mIntent.putExtra("android.intent.extra.SUBJECT", subject);
/* 633 */               return this;
                    }
                }

/* 670 */       public static class IntentReader {
                    private static final String TAG = "IntentReader";
                    private Activity mActivity;
                    private ComponentName mCallingActivity;
                    private String mCallingPackage;
                    private Intent mIntent;
                    private ArrayList<Uri> mStreams;

/* 671 */           public static IntentReader from(Activity activity) {
/* 672 */               return new IntentReader(activity);
                    }

/* 674 */           private IntentReader(Activity activity) {
/* 676 */               this.mActivity = activity;
/* 677 */               this.mIntent = activity.getIntent();
/* 678 */               this.mCallingPackage = ShareCompat.getCallingPackage(activity);
/* 679 */               this.mCallingActivity = ShareCompat.getCallingActivity(activity);
                    }

/* 690 */           public boolean isShareIntent() {
/* 691 */               String action = this.mIntent.getAction();
                        return "android.intent.action.SEND".equals(action) || "android.intent.action.SEND_MULTIPLE".equals(action);
                    }

/* 703 */           public boolean isSingleShare() {
/* 704 */               return "android.intent.action.SEND".equals(this.mIntent.getAction());
                    }

/* 714 */           public boolean isMultipleShare() {
/* 715 */               return "android.intent.action.SEND_MULTIPLE".equals(this.mIntent.getAction());
                    }

/* 724 */           public String getType() {
/* 725 */               return this.mIntent.getType();
                    }

/* 734 */           public CharSequence getText() {
/* 735 */               return this.mIntent.getCharSequenceExtra("android.intent.extra.TEXT");
                    }

/* 748 */           public String getHtmlText() {
/* 749 */               String result = this.mIntent.getStringExtra(IntentCompat.EXTRA_HTML_TEXT);
/* 750 */               if (result == null) {
/* 751 */                   CharSequence text = getText();
/* 752 */                   if (text instanceof Spanned) {
/* 753 */                       return Html.toHtml((Spanned) text);
                            }
/* 754 */                   if (text != null) {
/* 755 */                       return ShareCompat.access$000().escapeHtml(text);
                            }
/* 758 */                   return result;
                        }
/* 758 */               return result;
                    }

/* 772 */           public Uri getStream() {
/* 773 */               return (Uri) this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
                    }

/* 785 */           public Uri getStream(int index) {
/* 786 */               if (this.mStreams == null && isMultipleShare()) {
/* 787 */                   this.mStreams = this.mIntent.getParcelableArrayListExtra("android.intent.extra.STREAM");
                        }
/* 789 */               if (this.mStreams != null) {
/* 790 */                   return this.mStreams.get(index);
                        }
/* 792 */               if (index == 0) {
/* 793 */                   return (Uri) this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
                        }
/* 795 */               throw new IndexOutOfBoundsException("Stream items available: " + getStreamCount() + " index requested: " + index);
                    }

/* 806 */           public int getStreamCount() {
/* 807 */               if (this.mStreams == null && isMultipleShare()) {
/* 808 */                   this.mStreams = this.mIntent.getParcelableArrayListExtra("android.intent.extra.STREAM");
                        }
/* 810 */               if (this.mStreams != null) {
/* 811 */                   return this.mStreams.size();
                        }
                        return this.mIntent.hasExtra("android.intent.extra.STREAM") ? 1 : 0;
                    }

/* 822 */           public String[] getEmailTo() {
/* 823 */               return this.mIntent.getStringArrayExtra("android.intent.extra.EMAIL");
                    }

/* 832 */           public String[] getEmailCc() {
/* 833 */               return this.mIntent.getStringArrayExtra("android.intent.extra.CC");
                    }

/* 842 */           public String[] getEmailBcc() {
/* 843 */               return this.mIntent.getStringArrayExtra("android.intent.extra.BCC");
                    }

/* 852 */           public String getSubject() {
/* 853 */               return this.mIntent.getStringExtra("android.intent.extra.SUBJECT");
                    }

/* 869 */           public String getCallingPackage() {
/* 870 */               return this.mCallingPackage;
                    }

/* 886 */           public ComponentName getCallingActivity() {
/* 887 */               return this.mCallingActivity;
                    }

/* 900 */           public Drawable getCallingActivityIcon() {
/* 901 */               if (this.mCallingActivity == null) {
                            return null;
                        }
/* 903 */               PackageManager pm = this.mActivity.getPackageManager();
                        try {
/* 905 */                   return pm.getActivityIcon(this.mCallingActivity);
                        } catch (PackageManager.NameNotFoundException e) {
/* 907 */                   Log.e(TAG, "Could not retrieve icon for calling activity", e);
                            return null;
                        }
                    }

/* 922 */           public Drawable getCallingApplicationIcon() {
/* 923 */               if (this.mCallingPackage == null) {
                            return null;
                        }
/* 925 */               PackageManager pm = this.mActivity.getPackageManager();
                        try {
/* 927 */                   return pm.getApplicationIcon(this.mCallingPackage);
                        } catch (PackageManager.NameNotFoundException e) {
/* 929 */                   Log.e(TAG, "Could not retrieve icon for calling application", e);
                            return null;
                        }
                    }

/* 944 */           public CharSequence getCallingApplicationLabel() {
/* 945 */               if (this.mCallingPackage == null) {
                            return null;
                        }
/* 947 */               PackageManager pm = this.mActivity.getPackageManager();
                        try {
/* 949 */                   return pm.getApplicationLabel(pm.getApplicationInfo(this.mCallingPackage, 0));
                        } catch (PackageManager.NameNotFoundException e) {
/* 951 */                   Log.e(TAG, "Could not retrieve label for calling application", e);
                            return null;
                        }
                    }
                }
            }
