            package co.vine.android.util;

            import android.annotation.TargetApi;
            import android.app.AlarmManager;
            import android.app.PendingIntent;
            import android.content.ContentResolver;
            import android.content.Context;
            import android.content.Intent;
            import android.content.SharedPreferences;
            import android.content.pm.PackageInfo;
            import android.content.pm.PackageManager;
            import android.content.pm.ResolveInfo;
            import android.database.Cursor;
            import android.database.CursorWindow;
            import android.net.ConnectivityManager;
            import android.net.NetworkInfo;
            import android.net.Uri;
            import android.os.Build;
            import android.os.Environment;
            import android.provider.ContactsContract;
            import android.support.v4.view.MotionEventCompat;
            import android.text.TextUtils;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.inputmethod.InputMethodManager;
            import android.widget.Toast;
            import co.vine.android.Settings;
            import co.vine.android.api.VineRecipient;
            import com.edisonwang.android.slog.MessageFormatter;
            import com.edisonwang.android.slog.SLog;
            import com.fasterxml.jackson.core.JsonFactory;
            import com.fasterxml.jackson.core.JsonGenerator;
            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import com.flurry.android.Constants;
            import com.google.android.gms.plus.PlusShare;
            import java.io.ByteArrayInputStream;
            import java.io.ByteArrayOutputStream;
            import java.io.Closeable;
            import java.io.File;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.ObjectInputStream;
            import java.io.ObjectOutputStream;
            import java.io.OutputStream;
            import java.lang.reflect.Method;
            import java.net.URI;
            import java.net.URISyntaxException;
            import java.security.MessageDigest;
            import java.security.NoSuchAlgorithmException;
            import java.security.SecureRandom;
            import java.text.DecimalFormat;
            import java.text.DecimalFormatSymbols;
            import java.text.SimpleDateFormat;
            import java.util.HashMap;
            import java.util.HashSet;
            import java.util.List;
            import java.util.Locale;
            import java.util.regex.Matcher;
            import java.util.regex.Pattern;
            import org.apache.commons.io.IOUtils;
            import org.apache.http.message.BasicNameValuePair;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 108 */   public class CommonUtil {
                public static final int ALPHA_MASK = -16777216;
                public static final int CURSOR_INCREMENT = 20;
                public static final long DAY_MILLIS = 86400000;
                public static final int DEFAULT_PAGE_SIZE = 10;
                public static final int DEFAULT_SMALLER_ROW_PAGE_SIZE = 20;
                public static final String FIELD_TYPE_FAX = "fax";
                public static final String FIELD_TYPE_HOME = "home";
                public static final String FIELD_TYPE_MAIN = "main";
                public static final String FIELD_TYPE_MOBILE = "mobile";
                public static final String FIELD_TYPE_OTHER = "other";
                public static final String FIELD_TYPE_WORK = "work";
                public static final String FILE = "file";
                public static final String FILE_SCHEME = "file://";
                public static final int GLOBAL_LIMIT = 400;
                public static final int HALF_ALPHA_MASK = Integer.MIN_VALUE;
                public static final long HOUR_MILLIS = 3600000;
                public static final int HTTP_BAD_REQUEST = 400;
                public static final int HTTP_NOT_FOUND = 404;
                public static final int HTTP_OK = 200;
                public static final int HTTP_UNAUTHORIZED = 401;
                public static final int IO_BUFFER_SIZE = 4096;
                public static final int KB_BYTES = 1024;
                public static final char MARKER_COLON = ':';
                public static final char MARKER_QUOTES = '\"';
                public static final char MARKER_SEMI_COLON = ';';
                public static final int MAX_FOLLOW_REQUESTS = 5;
                public static final int MB_BYTES = 1048576;
                public static final long MINUTE_MILLIS = 60000;
                public static final long MONTH_MILLIS = 2628000000L;
                public static final double RTL_THRESHOLD = 0.3d;
                public static final long SECOND_MILLIS = 1000;
                public static final int THIRTY_FIVE_ALPHA_MASK = 1509949440;
                public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS";
                public static final int TWENTY_ALPHA_MASK = 855638016;
                public static final int TWENTY_FOUR_ALPHA_MASK = 1023410176;
                public static final long WEEK_MILLIS = 604800000;
                public static final long YEAR_MILLIS = 31536000000L;
                public static boolean DEBUG_VERBOSE = false;
                public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat();
                public static final DecimalFormatSymbols DECIMAL_FORMAT_SYMBOLS = new DecimalFormatSymbols();
                public static final Pattern PATTERN_USERNAME = Pattern.compile("<:\\s*?user\\s*?\\|\\s*?.*?:>([\\w\\s-]*?)<:>");
                private static final Pattern PATTERN_POST = Pattern.compile("<:\\s*?post\\s*?\\|\\s*?.*?:>([\\w\\s]*?)<:>");
                private static final Pattern PATTERN_COMMENT = Pattern.compile("\"(.+?)\\\\?\"");
                public static final SecureRandom sRandom = new SecureRandom();
                private static final char[] numbersAndLetters = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
                public static final SimpleDateFormat DATE_TIME_RFC1123 = new SynchronizedDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);

/* 109 */       public static boolean isUrlLocal(String path) {
/* 110 */           if (path == null) {
                        return false;
                    }
                    try {
/* 114 */               String scheme = Uri.parse(path).getScheme();
/* 115 */               return FILE.equals(scheme);
                    } catch (Exception e) {
                        return false;
                    }
                }

/* 122 */       public static String printCursorWindowStats() throws Throwable {
                    String str;
/* 123 */           CursorWindow window = null;
                    try {
                        try {
/* 125 */                   CursorWindow window2 = new CursorWindow(true);
                            try {
/* 126 */                       Method m = window2.getClass().getDeclaredMethod("printStats", new Class[0]);
/* 127 */                       m.setAccessible(true);
/* 128 */                       str = (String) m.invoke(window2, new Object[0]);
/* 132 */                       if (window2 != null) {
/* 133 */                           window2.close();
                                }
                            } catch (Exception e) {
/* 129 */                       e = e;
                                window = window2;
/* 130 */                       SLog.m459e("print cursor error", (Throwable) e);
/* 132 */                       if (window != null) {
/* 133 */                           window.close();
                                }
/* 136 */                       str = null;
                            } catch (Throwable th) {
/* 132 */                       th = th;
                                window = window2;
                                if (window != null) {
/* 133 */                           window.close();
                                }
                                throw th;
                            }
                        } catch (Throwable th2) {
                            throw th2;
                        }
                    } catch (Exception e2) {
                        SLog.m459e("print cursor error", (Throwable) e2);
                        return null;
                    }
/* 136 */           return str;
                }

/* 139 */       public static String stripUsernameEntities(String string) {
/* 140 */           String username = getUsernameFromActivity(string);
/* 141 */           Matcher matcher = PATTERN_USERNAME.matcher(string);
/* 142 */           return matcher.replaceAll(username);
                }

/* 143 */       public class ContentType {
                    public static final String APPLICATION_JSON = "application/json";

/* 144 */           public ContentType() {
                    }
                }

/* 195 */       public static void closeSilently(Closeable closeable) {
/* 196 */           if (closeable != null) {
                        try {
/* 198 */                   closeable.close();
                        } catch (IOException e) {
                        }
                    }
                }

/* 217 */       public static String randomString(int length) {
/* 218 */           if (length < 1) {
/* 219 */               return null;
                    }
/* 222 */           char[] randBuffer = new char[length];
                    for (int i = 0; i < randBuffer.length; i++) {
/* 224 */               randBuffer[i] = numbersAndLetters[sRandom.nextInt(71)];
                    }
/* 226 */           return new String(randBuffer);
                }

/* 236 */       public static boolean isIntentAvailable(Context context, String action) {
/* 237 */           PackageManager packageManager = context.getPackageManager();
/* 238 */           Intent intent = new Intent(action);
/* 239 */           List<ResolveInfo> list = packageManager.queryIntentActivities(intent, 65536);
                    return list.size() > 0;
                }

/* 253 */       public static File getTempFile(Context context, int fileNameSize) {
/* 254 */           File cacheDir = getCacheDir(context);
/* 255 */           if (cacheDir != null) {
/* 256 */               return new File(cacheDir, randomString(fileNameSize));
                    }
                    return null;
                }

/* 262 */       public static Uri getUriFromResouce(Context context, int res) {
/* 263 */           return Uri.parse("android.resource://" + context.getPackageName() + "/" + res);
                }

/* 274 */       public static File getTempFile(Context context) {
/* 275 */           return getTempFile(context, 6);
                }

/* 285 */       public static File getCacheDir(Context context) {
/* 286 */           File externalCacheDir = getExternalCacheDir(context);
                    return externalCacheDir != null ? externalCacheDir : context.getCacheDir();
                }

/* 295 */       public static File getExternalCacheDir(Context c) {
                    try {
/* 296 */               return c.getExternalCacheDir();
                    } catch (Throwable th) {
/* 298 */               return null;
                    }
                }

/* 313 */       public static int readFullyWriteTo(InputStream is, OutputStream os, int bufferSize) throws IOException {
/* 314 */           byte[] buf = new byte[bufferSize];
/* 316 */           int totalBytes = 0;
                    while (true) {
/* 317 */               int bytesRead = is.read(buf);
                        if (bytesRead != -1) {
/* 318 */                   if (os != null) {
/* 319 */                       os.write(buf, 0, bytesRead);
                            }
/* 321 */                   totalBytes += bytesRead;
                        } else {
/* 323 */                   return totalBytes;
                        }
                    }
                }

/* 333 */       public static File getFilesDir(Context context) {
/* 334 */           File f = null;
                    try {
/* 337 */               f = getExternalFilesDir(context);
                    } catch (Exception e) {
/* 339 */               SLog.m480w("Failed to get external dir.", (Throwable) e);
                    }
/* 342 */           if (f != null) {
/* 345 */               return f;
                    }
/* 345 */           File f2 = context.getFilesDir();
/* 345 */           return f2;
                }

                @TargetApi(8)
/* 350 */       public static File getExternalFilesDir(Context c) {
                    return Build.VERSION.SDK_INT > 7 ? c.getExternalFilesDir(null) : Environment.getExternalStorageDirectory();
                }

/* 365 */       public static float getFileSize(Context context, Uri uri) {
/* 366 */           InputStream is = null;
                    try {
/* 368 */               is = context.getContentResolver().openInputStream(uri);
/* 369 */               float fAvailable = is.available() / 1024.0f;
/* 373 */               closeSilently(is);
                        return fAvailable;
                    } catch (IOException e) {
/* 373 */               closeSilently(is);
/* 371 */               return 0.0f;
                    } catch (Throwable th) {
                        closeSilently(is);
                        throw th;
                    }
                }

/* 382 */       public static void showCenteredToast(Context context, int resId) {
/* 383 */           Toast t = Toast.makeText(context, resId, 1);
/* 384 */           t.setGravity(17, 0, 0);
/* 385 */           t.show();
                }

/* 388 */       public static boolean isDefaultAvatarUrl(String url) {
                    return TextUtils.isEmpty(url) || url.contains("default.png");
                }

/* 397 */       public static void showDefaultToast(Context context, String text) {
/* 398 */           Toast t = Toast.makeText(context, text, 1);
/* 399 */           t.show();
                }

/* 407 */       public static void showTopToast(Context context, int resId) {
/* 408 */           Toast t = Toast.makeText(context, context.getText(resId), 1);
/* 409 */           t.setGravity(48, 0, 0);
/* 410 */           t.show();
                }

/* 418 */       public static Toast showCenteredToast(Context context, String text) {
/* 419 */           Toast t = Toast.makeText(context, text, 1);
/* 420 */           ViewGroup toastView = (ViewGroup) t.getView();
/* 421 */           if (toastView != null) {
/* 422 */               View textView = toastView.getChildAt(0);
/* 423 */               textView.setBackgroundColor(0);
                    }
/* 425 */           t.setGravity(17, 0, 0);
/* 426 */           t.show();
/* 427 */           return t;
                }

/* 435 */       public static Toast showCenteredToast(Context context, String format, Object... args) {
/* 436 */           Toast t = Toast.makeText(context, MessageFormatter.toStringMessage(format, args), 1);
/* 437 */           ViewGroup toastView = (ViewGroup) t.getView();
/* 438 */           if (toastView != null) {
/* 439 */               View textView = toastView.getChildAt(0);
/* 440 */               textView.setBackgroundColor(0);
                    }
/* 442 */           t.setGravity(17, 0, 0);
/* 443 */           t.show();
/* 444 */           return t;
                }

/* 453 */       public static void showShortCenteredToast(Context context, String text) {
/* 454 */           Toast t = Toast.makeText(context, text, 0);
/* 455 */           t.setGravity(17, 0, 0);
/* 456 */           t.show();
                }

/* 460 */       public static URI parseURI(String uri) {
                    try {
/* 461 */               return new URI(uri);
                    } catch (URISyntaxException e) {
/* 465 */               int colonIndex = uri.indexOf(58);
/* 466 */               if (colonIndex != -1 && colonIndex != uri.length()) {
/* 467 */                   String scheme = uri.substring(0, colonIndex);
/* 468 */                   String ssp = uri.substring(colonIndex + 1);
                            try {
/* 470 */                       return new URI(scheme, ssp, null);
                            } catch (URISyntaxException e2) {
                                return null;
                            }
                        }
                        return null;
                    }
                }

/* 479 */       public static String md5Digest(String msg) {
                    try {
/* 480 */               MessageDigest messageDigest = MessageDigest.getInstance("MD5");
/* 481 */               byte[] md5bytes = messageDigest.digest(msg.getBytes());
/* 482 */               StringBuilder hexString = new StringBuilder();
                        for (byte md5byte : md5bytes) {
/* 484 */                   String hex = Integer.toHexString(md5byte & Constants.UNKNOWN);
/* 485 */                   if (hex.length() < 2) {
/* 486 */                       hexString.append('0').append(hex);
                            } else {
/* 488 */                       hexString.append(hex);
                            }
                        }
/* 491 */               return hexString.toString();
                    } catch (NoSuchAlgorithmException e) {
/* 493 */               return null;
                    }
                }

/* 497 */       public static void setSoftKeyboardVisibility(Context context, View view, boolean visible) {
/* 498 */           InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
/* 501 */           if (inputMethodManager != null) {
/* 502 */               if (visible) {
/* 503 */                   inputMethodManager.showSoftInput(view, 0);
                        } else {
/* 505 */                   inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    }
                }

/* 517 */       public static String getUsernameFromActivity(String body) {
/* 518 */           Matcher m = PATTERN_USERNAME.matcher(body);
/* 519 */           if (m.find()) {
/* 520 */               return m.group(1).trim();
                    }
                    return null;
                }

/* 528 */       public static String getAddressBookJson(ContentResolver resolver) throws IOException {
/* 529 */           HashMap<String, AddressBookContact> contactsHashMap = new HashMap<>();
/* 530 */           JsonFactory jf = new JsonFactory();
/* 531 */           ByteArrayOutputStream ba = new ByteArrayOutputStream();
/* 532 */           JsonGenerator jg = jf.createJsonGenerator(ba);
/* 534 */           String[] projection = {"starred", "times_contacted", "display_name", "display_name", "display_name_alt", "data1", "data2"};
/* 544 */           Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null, null);
/* 546 */           if (cursor != null) {
/* 547 */               while (cursor.moveToNext()) {
/* 548 */                   boolean starred = cursor.getInt(0) > 0;
/* 549 */                   int timesContacted = cursor.getInt(1);
/* 550 */                   String name = cursor.getString(2);
/* 551 */                   if (TextUtils.isEmpty(name)) {
                                name = cursor.getString(3);
                            }
/* 552 */                   if (TextUtils.isEmpty(name)) {
                                name = cursor.getString(4);
                            }
/* 553 */                   String phone = cursor.getString(5);
/* 554 */                   String label = getPhoneLabelFromType(cursor.getInt(6));
/* 556 */                   if (!TextUtils.isEmpty(phone)) {
/* 557 */                       if (TextUtils.isEmpty(name)) {
                                    name = phone;
                                }
/* 558 */                       AddressBookContact contact = contactsHashMap.containsKey(name) ? contactsHashMap.get(name) : new AddressBookContact(name);
/* 560 */                       contact.addPhone(label, phone);
/* 561 */                       if (starred) {
                                    contact.setStarred();
                                }
/* 562 */                       if (timesContacted > 0) {
                                    contact.setTimesContacted(timesContacted);
                                }
/* 563 */                       contactsHashMap.put(name, contact);
                            }
                        }
/* 566 */               cursor.close();
                    }
/* 569 */           String[] projection2 = {"starred", "times_contacted", "display_name", "data4", "display_name_alt", "data1", "data2"};
/* 579 */           Cursor cursor2 = resolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, projection2, null, null, null);
/* 581 */           if (cursor2 != null) {
/* 582 */               while (cursor2.moveToNext()) {
/* 583 */                   boolean starred2 = cursor2.getInt(0) > 0;
/* 584 */                   int timesContacted2 = cursor2.getInt(1);
/* 585 */                   String name2 = cursor2.getString(2);
/* 586 */                   if (TextUtils.isEmpty(name2)) {
                                name2 = cursor2.getString(3);
                            }
/* 587 */                   if (TextUtils.isEmpty(name2)) {
                                name2 = cursor2.getString(4);
                            }
/* 588 */                   String email = cursor2.getString(5);
/* 589 */                   String label2 = getEmailLabelFromType(cursor2.getInt(6));
/* 591 */                   if (!TextUtils.isEmpty(email)) {
/* 592 */                       if (TextUtils.isEmpty(name2)) {
                                    name2 = email;
                                }
/* 593 */                       AddressBookContact contact2 = contactsHashMap.containsKey(name2) ? contactsHashMap.get(name2) : new AddressBookContact(name2);
/* 595 */                       if (starred2) {
                                    contact2.setStarred();
                                }
/* 596 */                       if (timesContacted2 > 0) {
                                    contact2.setTimesContacted(timesContacted2);
                                }
/* 597 */                       contact2.addEmail(label2, email);
/* 598 */                       contactsHashMap.put(name2, contact2);
                            }
                        }
/* 601 */               cursor2.close();
                    }
/* 604 */           if (!contactsHashMap.isEmpty()) {
/* 605 */               jg.writeStartArray();
                        for (AddressBookContact contact3 : contactsHashMap.values()) {
/* 607 */                   jg.writeStartObject();
/* 608 */                   jg.writeStringField("name", contact3.name);
/* 609 */                   if (contact3.starred) {
/* 610 */                       jg.writeBooleanField("starred", true);
                            }
/* 612 */                   if (contact3.timesContacted > 0) {
/* 613 */                       jg.writeNumberField("timesContacted", contact3.timesContacted);
                            }
/* 615 */                   if (contact3.emails != null) {
/* 616 */                       jg.writeFieldName("emailAddresses");
/* 617 */                       jg.writeStartArray();
                                for (HashSet<BasicNameValuePair> fieldSet : contact3.emails) {
/* 619 */                           jg.writeStartObject();
                                    for (BasicNameValuePair field : fieldSet) {
/* 621 */                               jg.writeObjectField(field.getName(), field.getValue());
                                    }
/* 623 */                           jg.writeEndObject();
                                }
/* 625 */                       jg.writeEndArray();
                            }
/* 627 */                   if (contact3.phones != null) {
/* 628 */                       jg.writeFieldName("phoneNumbers");
/* 629 */                       jg.writeStartArray();
                                for (HashSet<BasicNameValuePair> fieldSet2 : contact3.phones) {
/* 631 */                           jg.writeStartObject();
                                    for (BasicNameValuePair field2 : fieldSet2) {
/* 633 */                               jg.writeObjectField(field2.getName(), field2.getValue());
                                    }
/* 635 */                           jg.writeEndObject();
                                }
/* 637 */                       jg.writeEndArray();
                            }
/* 639 */                   jg.writeEndObject();
                        }
/* 641 */               jg.writeEndArray();
/* 642 */               jg.close();
                    }
/* 644 */           return ba.toString();
                }

/* 647 */       private static String getEmailLabelFromType(int type) {
                    switch (type) {
                        case 1:
/* 650 */                   return "home";
                        case 2:
/* 653 */                   return FIELD_TYPE_WORK;
                        default:
/* 656 */                   return FIELD_TYPE_OTHER;
                    }
                }

/* 661 */       private static String getPhoneLabelFromType(int type) {
                    switch (type) {
                        case 1:
/* 664 */                   return "home";
                        case 2:
/* 670 */                   return FIELD_TYPE_MOBILE;
                        case 3:
/* 667 */                   return FIELD_TYPE_WORK;
                        case 4:
                        case 5:
                        case 13:
/* 678 */                   return FIELD_TYPE_FAX;
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                        case 11:
                        default:
/* 681 */                   return FIELD_TYPE_OTHER;
                        case 12:
/* 673 */                   return FIELD_TYPE_MAIN;
                    }
                }

/* 691 */       private static class AddressBookContact {
                    public HashSet<HashSet<BasicNameValuePair>> emails;
                    public String name;
                    public HashSet<HashSet<BasicNameValuePair>> phones;
                    public boolean starred = false;
                    public int timesContacted = 0;

/* 692 */           public AddressBookContact(String name) {
/* 694 */               this.name = name;
                    }

/* 699 */           public void addEmail(String label, String email) {
/* 700 */               if (this.emails == null) {
/* 701 */                   this.emails = new HashSet<>();
                        }
/* 703 */               HashSet<BasicNameValuePair> fields = new HashSet<>();
/* 704 */               fields.add(new BasicNameValuePair("emailAddress", email));
/* 705 */               if (!TextUtils.isEmpty(label)) {
/* 706 */                   fields.add(new BasicNameValuePair(PlusShare.KEY_CALL_TO_ACTION_LABEL, label));
                        }
/* 708 */               this.emails.add(fields);
                    }

/* 711 */           public void addPhone(String label, String phone) {
/* 712 */               if (this.phones == null) {
/* 713 */                   this.phones = new HashSet<>();
                        }
/* 715 */               HashSet<BasicNameValuePair> fields = new HashSet<>();
/* 716 */               fields.add(new BasicNameValuePair(VineRecipient.KEY_PHONE, phone));
/* 717 */               if (!TextUtils.isEmpty(label)) {
/* 718 */                   fields.add(new BasicNameValuePair(PlusShare.KEY_CALL_TO_ACTION_LABEL, label));
                        }
/* 720 */               this.phones.add(fields);
                    }

/* 723 */           public void setStarred() {
/* 724 */               this.starred = true;
                    }

/* 727 */           public void setTimesContacted(int timesContacted) {
/* 728 */               this.timesContacted = timesContacted;
                    }
                }

/* 732 */       public static Object fromByteArray(byte[] bytes) {
                    if (bytes == null) {
                        return null;
                    }
                    ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                    try {
                        ObjectInputStream ois = new ObjectInputStream(bis);
                        try {
                            return ois.readObject();
                        } finally {
                            closeSilently(ois);
                            closeSilently(bis);
                        }
                    } catch (IOException e) {
                        return null;
                    } catch (ClassNotFoundException e) {
                        return null;
                    }
                }

/* 758 */       public static byte[] toByteArray(Object o) {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream(512);
                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(bos);
                        try {
                            oos.writeObject(o);
                            return bos.toByteArray();
                        } finally {
                            closeSilently(oos);
                            closeSilently(bos);
                        }
                    } catch (IOException e) {
                        return null;
                    }
                }

/* 774 */       public static String getVersionName(Context context) throws PackageManager.NameNotFoundException {
/* 775 */           String packageName = context.getPackageName();
/* 776 */           PackageInfo pInfo = context.getPackageManager().getPackageInfo(packageName, 0);
/* 777 */           return pInfo.versionName;
                }

/* 780 */       public static String cleanse(String text) {
/* 781 */           return text.replace(IOUtils.LINE_SEPARATOR_UNIX, MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).trim();
                }

/* 784 */       public static void removeCache(Context context) {
/* 785 */           File dir = getExternalCacheDir(context);
/* 786 */           if (dir != null) {
/* 789 */               deleteFileRecursively(dir);
/* 791 */               File dir2 = getExternalFilesDir(context);
/* 792 */               if (dir2 != null) {
/* 795 */                   deleteFileRecursively(dir2);
                        }
                    }
                }

/* 798 */       public static void deleteFileRecursively(File file) {
/* 799 */           if (file != null) {
/* 800 */               if (file.isDirectory()) {
/* 801 */                   File[] files = file.listFiles();
/* 802 */                   if (files != null) {
                                for (File f : files) {
/* 804 */                           deleteFileRecursively(f);
                                }
/* 811 */                       return;
                            }
/* 811 */                   return;
                        }
/* 808 */               file.delete();
                    }
                }

/* 813 */       public static int getNextCursorSize(int currentSize) {
/* 814 */           return currentSize + 20;
                }

/* 825 */       public static SharedPreferences getDefaultSharedPrefs(Context context) {
/* 826 */           return context.getSharedPreferences(context.getPackageName() + "_preferences", 4);
                }

/* 836 */       public static SharedPreferences getSharedPrefs(Context context, String name) {
/* 837 */           return context.getSharedPreferences(context.getPackageName() + "_preferences_" + name, 4);
                }

/* 841 */       public static boolean isXauth2FactorError(Exception e) {
/* 842 */           String message = e.getCause().getMessage();
                    return message.contains("231") && message.contains("User must verify login");
                }

/* 846 */       public static boolean isRtlLanguage(String text) {
/* 847 */           if (text.length() <= 0) {
                        return false;
                    }
/* 851 */           float totalCount = 0.0f;
                    float rtlCount = 0.0f;
/* 852 */           char[] arr$ = text.toCharArray();
                    for (char c : arr$) {
/* 853 */               if (Character.UnicodeBlock.of(c) != Character.UnicodeBlock.GENERAL_PUNCTUATION) {
/* 857 */                   if (isRtlCharacter(c)) {
/* 858 */                       rtlCount += 1.0f;
                            }
/* 860 */                   if (!isEmojiOrSymbol(c)) {
/* 861 */                       totalCount += 1.0f;
                            }
                        }
                    }
                    return ((double) (rtlCount / totalCount)) > 0.3d;
                }

/* 872 */       public static boolean isRtlCharacter(char c) {
/* 873 */           Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
                    return block == Character.UnicodeBlock.ARABIC || block == Character.UnicodeBlock.ARABIC_PRESENTATION_FORMS_A || block == Character.UnicodeBlock.ARABIC_PRESENTATION_FORMS_B || block == Character.UnicodeBlock.HEBREW;
                }

/* 880 */       public static boolean isEmojiOrSymbol(char c) {
/* 881 */           Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
                    return block == Character.UnicodeBlock.DINGBATS || block == Character.UnicodeBlock.MISCELLANEOUS_MATHEMATICAL_SYMBOLS_A || block == Character.UnicodeBlock.SUPPLEMENTAL_ARROWS_A || block == Character.UnicodeBlock.BRAILLE_PATTERNS || block == Character.UnicodeBlock.SUPPLEMENTAL_ARROWS_B || block == Character.UnicodeBlock.MISCELLANEOUS_MATHEMATICAL_SYMBOLS_B || block == Character.UnicodeBlock.SUPPLEMENTAL_MATHEMATICAL_OPERATORS || block == Character.UnicodeBlock.MISCELLANEOUS_SYMBOLS_AND_ARROWS || block == Character.UnicodeBlock.PRIVATE_USE_AREA;
                }

/* 899 */       public static String addDirectionalMarkers(CharSequence text) {
/* 900 */           StringBuilder builder = new StringBuilder();
/* 901 */           char prev = 0;
/* 902 */           boolean isFirst = true;
/* 904 */           char[] arr$ = text.toString().toCharArray();
                    for (char c : arr$) {
/* 905 */               if (prev == '#') {
/* 906 */                   if (isRtlCharacter(c)) {
/* 907 */                       builder.append("\u200f");
                            } else {
/* 909 */                       builder.append("\u200e");
                            }
                        }
/* 913 */               if (isFirst) {
/* 914 */                   isFirst = false;
                        } else {
/* 916 */                   builder.append(prev);
                        }
/* 918 */               prev = c;
                    }
/* 920 */           builder.append(prev);
/* 922 */           return builder.toString();
                }

/* 930 */       public static void restartApp(Context context) {
/* 931 */           Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
/* 933 */           AlarmManager am = (AlarmManager) context.getSystemService("alarm");
/* 934 */           am.set(1, System.currentTimeMillis() + 1000, PendingIntent.getActivity(context, 0, launchIntent, 0));
/* 936 */           System.exit(0);
                }

/* 944 */       public static String getLocale() {
/* 945 */           Locale current = Locale.getDefault();
                    return ((Locale.TRADITIONAL_CHINESE.getLanguage().equals(current.getLanguage()) && Locale.TRADITIONAL_CHINESE.getCountry().equals(current.getCountry())) || (Locale.SIMPLIFIED_CHINESE.getLanguage().equals(current.getLanguage()) && Locale.SIMPLIFIED_CHINESE.getCountry().equals(current.getCountry()))) ? current.toString() : current.getLanguage();
                }

/* 956 */       public static String getCountryCode() {
/* 957 */           Locale locale = Locale.getDefault();
/* 958 */           return locale.getCountry();
                }

/* 961 */       public static int getProfileColor(Context context) {
/* 962 */           return getDefaultSharedPrefs(context).getInt("profile_background", Settings.DEFAULT_PROFILE_COLOR);
                }

/* 974 */       public static int getColorWithBlackPercentage(int color, float percentBlack) {
/* 975 */           float inversePercent = 1.0f - percentBlack;
/* 976 */           int r = (color >> 16) & 255;
/* 977 */           int g = (color >> 8) & 255;
/* 978 */           int b = color & 255;
/* 980 */           int rFloat = Math.round(r * inversePercent);
/* 981 */           int gFloat = Math.round(g * inversePercent);
/* 982 */           int bFloat = Math.round(b * inversePercent);
/* 984 */           int r2 = (rFloat << 16) & 16711680;
/* 985 */           int g2 = (gFloat << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK;
/* 986 */           int b2 = bFloat & 255;
/* 988 */           return (-16777216) | r2 | g2 | b2;
                }

/* 991 */       public static int getActiveNetworkType(Context context) {
/* 992 */           ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
/* 994 */           NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
/* 995 */           if (activeNetwork != null) {
                        return activeNetwork.getType();
                    }
                    return -1;
                }

/* 1000 */      public static boolean isDoingRobolectricTest() {
                    try {
/* 1001 */              Class<?> robolectric = Class.forName("org.robolectric.Robolectric");
                        return robolectric != null;
                    } catch (ClassNotFoundException e) {
/* 1004 */              return false;
                    }
                }
            }
