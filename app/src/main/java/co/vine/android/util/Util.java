            package co.vine.android.util;

            import android.app.Activity;
            import android.content.Context;
            import android.content.Intent;
            import android.content.IntentFilter;
            import android.content.res.Resources;
            import android.graphics.PorterDuff;
            import android.graphics.Typeface;
            import android.support.annotation.Nullable;
            import android.text.Editable;
            import android.text.Spannable;
            import android.text.SpannableString;
            import android.text.SpannableStringBuilder;
            import android.text.Spanned;
            import android.text.TextUtils;
            import android.text.format.DateFormat;
            import android.widget.ImageView;
            import android.widget.TextView;
            import android.widget.Toast;
            import co.vine.android.C0257R;
            import co.vine.android.Settings;
            import co.vine.android.VineLoggingException;
            import co.vine.android.api.VineEntity;
            import co.vine.android.api.VinePost;
            import co.vine.android.api.VineSingleNotification;
            import co.vine.android.api.VineVideoUrlTier;
            import co.vine.android.cache.image.PhotoImagesCache;
            import co.vine.android.cache.text.TextCache;
            import co.vine.android.cache.video.VideoCache;
            import co.vine.android.cache.video.VideoKey;
            import co.vine.android.client.VineAPI;
            import co.vine.android.network.VineNetworkUtils;
            import co.vine.android.provider.VineDatabaseHelper;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.service.VineService;
            import co.vine.android.widget.Typefaces;
            import java.io.File;
            import java.text.SimpleDateFormat;
            import java.util.ArrayList;
            import java.util.Calendar;
            import java.util.Collections;
            import java.util.Date;
            import java.util.List;
            import java.util.Locale;
            import org.apache.commons.io.FileUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 56 */    public class Util extends CommonUtil {
                private static SimpleDateFormat sDateFormatNoYear;
                private static SimpleDateFormat sDateFormatWithYear;
                private static final int[] DATE_FORMAT_LOCK = new int[0];
                public static final IntentFilter COLOR_CHANGED_INTENT_FILTER = new IntentFilter();

                public enum ProfileImageSize {
                    SMALL,
                    MEDIUM,
                    LARGE
                }

                static {
/* 65 */            COLOR_CHANGED_INTENT_FILTER.addAction(VineService.ACTION_PROFILE_COLOR_CHANGED);
                }

/* 68 */        public static void startActionOnRecordingAvailable(Activity activity, Intent intent, int requestId) {
/* 69 */            if (RecordConfigUtils.isCapableOfRecording(activity)) {
/* 70 */                activity.startActivityForResult(intent, requestId);
                    } else {
/* 72 */                showNoCameraToast(activity);
                    }
                }

/* 76 */        public static void showNoCameraToast(Context context) {
/* 77 */            Toast.makeText(context, C0257R.string.no_camera_found_toast, 0).show();
                }

/* 80 */        public static PhotoImagesCache<VineAPI> newImageCache(Context context, int imageSize, int cacheSize) {
/* 81 */            return new PhotoImagesCache<>(context, imageSize, cacheSize, RenderscriptUtils.newBlurTool(), VineAPI.getInstance(context), VineNetworkUtils.getDefaultNetworkOperationFactory());
                }

/* 87 */        public static TextCache<VineAPI> newTextCache(Context context, VineAPI api) {
/* 88 */            return new TextCache<>(context, 10485760, api, VineNetworkUtils.getDefaultNetworkOperationFactory());
                }

/* 91 */        public static VideoCache<VineAPI> newVideoCache(Context context) {
/* 92 */            return new VideoCache<>(context, VineAPI.getInstance(context), VineNetworkUtils.getDefaultNetworkOperationFactory());
                }

/* 103 */       public static long getCacheSize(Context context) throws VineLoggingException {
                    try {
/* 105 */               File cacheDir = getExternalCacheDir(context);
/* 104 */               long size = cacheDir != null ? 0 + FileUtils.sizeOfDirectory(cacheDir) : 0L;
/* 111 */               if (cacheDir != null) {
/* 112 */                   cacheDir = getExternalFilesDir(context);
                        }
/* 115 */               if (cacheDir != null) {
/* 116 */                   size += FileUtils.sizeOfDirectory(cacheDir);
                        }
/* 119 */               File f = context.getDatabasePath(VineDatabaseHelper.getDatabaseName(1));
/* 121 */               if (f != null) {
/* 122 */                   size += f.length();
                        }
/* 125 */               if (!BuildUtil.isLogsOn() && size <= 500000) {
/* 134 */                   return 0L;
                        }
/* 134 */               return size;
                    } catch (Throwable e) {
/* 138 */               throw new VineLoggingException(e);
                    }
                }

/* 142 */       public static String formatFileSize(Resources res, long bytes) {
/* 143 */           DECIMAL_FORMAT_SYMBOLS.setGroupingSeparator(res.getString(C0257R.string.number_format_separator).charAt(0));
/* 145 */           int groupingSize = 3;
                    try {
/* 147 */               groupingSize = Integer.parseInt(res.getString(C0257R.string.number_format_grouping_size));
                    } catch (NumberFormatException e) {
                    }
/* 150 */           DECIMAL_FORMAT.setGroupingSize(groupingSize);
/* 151 */           DECIMAL_FORMAT.setGroupingUsed(true);
/* 152 */           DECIMAL_FORMAT.setDecimalFormatSymbols(DECIMAL_FORMAT_SYMBOLS);
                    if (bytes / 1000000000 >= 1) {
/* 154 */               return res.getString(C0257R.string.number_format_gigabytes, DECIMAL_FORMAT.format(bytes / 1000000000));
                    }
                    if (bytes / AudioUtils.MICROS_PER_SECOND >= 1) {
/* 156 */               return res.getString(C0257R.string.number_format_megabytes, DECIMAL_FORMAT.format(bytes / AudioUtils.MICROS_PER_SECOND));
                    }
                    if (bytes / 1000 >= 1) {
/* 158 */               return res.getString(C0257R.string.number_format_kilobytes, DECIMAL_FORMAT.format(bytes / 1000));
                    }
/* 160 */           return res.getString(C0257R.string.number_format_bytes, DECIMAL_FORMAT.format(bytes));
                }

/* 164 */       public static void setTextWithSpan(Object span, String text, TextView view) {
/* 165 */           SpannableStringBuilder contentSb = new SpannableStringBuilder(text);
/* 166 */           safeSetSpan(contentSb, span, 0, contentSb.length(), 33);
/* 167 */           view.setText(contentSb);
                }

/* 171 */       public static void safeSetSpan(Spannable spannable, Object span, int start, int end, int flags) {
/* 172 */           if (BuildUtil.isLogsOn()) {
/* 173 */               spannable.setSpan(span, start, end, flags);
/* 187 */               return;
                    }
/* 175 */           if (start >= 0 && start < spannable.length() && end >= start && end < spannable.length()) {
/* 177 */               spannable.setSpan(span, start, end, flags);
/* 187 */               return;
                    }
                    try {
/* 180 */               spannable.setSpan(span, start, end, flags);
                    } catch (IndexOutOfBoundsException e) {
/* 182 */               CrashUtil.logException(e, "We got an IOOB while setting a span. {} {} {}", spannable.toString(), Integer.valueOf(start), Integer.valueOf(end));
                    }
                }

/* 189 */       public static boolean isPopularTimeline(int groupType) {
                    return groupType == 5 || groupType == 8 || groupType == 4 || groupType == 11;
                }

/* 205 */       public static Spanned getSpannedText(Object[] span, String text, char marker) {
/* 206 */           int start = text.indexOf(marker);
/* 207 */           if (start == -1) {
/* 208 */               return new SpannableString(text);
                    }
/* 210 */           int end = text.indexOf(marker, start + 1);
/* 211 */           if (end == -1) {
/* 212 */               return new SpannableString(text);
                    }
/* 215 */           int maxSpans = span.length;
/* 216 */           int spanIndex = 0;
/* 217 */           int next = 0;
/* 218 */           int offset = 0;
/* 219 */           SpannableStringBuilder ssb = new SpannableStringBuilder();
                    do {
/* 222 */               ssb.append((CharSequence) text.substring(next, start));
/* 225 */               ssb.append((CharSequence) text.substring(start + 1, end));
/* 227 */               safeSetSpan(ssb, span[spanIndex], start - offset, (end - offset) - 1, 33);
/* 230 */               spanIndex++;
/* 231 */               next = end + 1;
/* 232 */               if (spanIndex >= maxSpans) {
                            break;
                        }
/* 236 */               start = text.indexOf(marker, next);
/* 237 */               if (start != -1) {
/* 238 */                   end = text.indexOf(marker, start + 1);
/* 240 */                   offset += 2;
                        }
/* 242 */               if (start == -1) {
                            break;
                        }
                    } while (end != -1);
/* 245 */           ssb.append((CharSequence) text.substring(next));
/* 247 */           return ssb;
                }

/* 258 */       public static String getRelativeTimeString(Context context, long timestamp, boolean verbose) {
                    String quantityString;
                    synchronized (DATE_FORMAT_LOCK) {
                        try {
/* 260 */                   Resources res = context.getResources();
/* 261 */                   if (sDateFormatWithYear == null) {
/* 262 */                       char[] order = null;
                                try {
/* 264 */                           order = DateFormat.getDateFormatOrder(context);
                                } catch (Exception e) {
                                }
/* 268 */                       Locale locale = Locale.getDefault();
/* 270 */                       if (order == null || (locale != null && (locale.equals(Locale.KOREA) || locale.equals(Locale.KOREAN)))) {
/* 271 */                           sDateFormatNoYear = new SimpleDateFormat(res.getString(C0257R.string.date_format_short));
/* 272 */                           sDateFormatWithYear = new SimpleDateFormat(res.getString(C0257R.string.date_format_long));
                                } else {
/* 274 */                           StringBuilder noYear = new StringBuilder();
/* 275 */                           StringBuilder withYear = new StringBuilder();
/* 276 */                           int i = 0;
/* 277 */                           char[] arr$ = order;
                                    for (char c : arr$) {
                                        switch (c) {
                                            case 'M':
/* 289 */                                       noYear.append(c).append(c).append(c);
/* 290 */                                       withYear.append(c).append(c).append(c);
/* 291 */                                       if (i != order.length) {
/* 292 */                                           noYear.append(' ');
/* 293 */                                           withYear.append(' ');
                                                }
                                                break;
                                            case 'd':
/* 280 */                                       noYear.append(c).append(c);
/* 281 */                                       withYear.append(c).append(c);
/* 282 */                                       if (i != order.length) {
/* 283 */                                           noYear.append(' ');
/* 284 */                                           withYear.append(' ');
                                                }
                                                break;
                                            case 'y':
/* 298 */                                       withYear.append(c).append(c).append(c);
/* 299 */                                       if (i != order.length) {
/* 300 */                                           withYear.append(' ');
                                                }
                                                break;
                                        }
/* 305 */                               i++;
/* 306 */                               sDateFormatNoYear = new SimpleDateFormat(noYear.toString());
/* 307 */                               sDateFormatWithYear = new SimpleDateFormat(withYear.toString());
                                    }
                                }
                            }
/* 311 */                   long diff = System.currentTimeMillis() - timestamp;
                            if (diff < 0) {
/* 345 */                       quantityString = sDateFormatWithYear.format(new Date(timestamp));
                            } else if (diff < CommonUtil.MINUTE_MILLIS) {
/* 314 */                       int secs = (int) (diff / 1000);
/* 315 */                       quantityString = res.getQuantityString(verbose ? C0257R.plurals.time_secs_verbose_ago : C0257R.plurals.time_secs, secs, Integer.valueOf(secs));
                            } else if (diff < CommonUtil.HOUR_MILLIS) {
/* 318 */                       int mins = (int) (diff / CommonUtil.MINUTE_MILLIS);
/* 319 */                       quantityString = res.getQuantityString(verbose ? C0257R.plurals.time_mins_verbose_ago : C0257R.plurals.time_mins, mins, Integer.valueOf(mins));
                            } else if (diff < 86400000) {
/* 322 */                       int hours = (int) (diff / CommonUtil.HOUR_MILLIS);
/* 323 */                       quantityString = res.getQuantityString(verbose ? C0257R.plurals.time_hours_verbose_ago : C0257R.plurals.time_hours, hours, Integer.valueOf(hours));
                            } else if (diff < 604800000) {
/* 326 */                       int days = (int) (diff / 86400000);
/* 327 */                       quantityString = res.getQuantityString(verbose ? C0257R.plurals.time_days_verbose_ago : C0257R.plurals.time_days, days, Integer.valueOf(days));
                            } else {
/* 331 */                       Calendar now = Calendar.getInstance();
/* 333 */                       Calendar c2 = Calendar.getInstance();
/* 334 */                       Date d = new Date(timestamp);
/* 335 */                       c2.setTime(d);
/* 337 */                       if (now.get(1) == c2.get(1)) {
/* 338 */                           quantityString = sDateFormatNoYear.format(d);
                                } else {
/* 340 */                           quantityString = sDateFormatWithYear.format(d);
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 345 */           return quantityString;
                }

/* 361 */       public static String numberFormat(Resources res, long number, boolean shorten) {
/* 362 */           boolean tenThousands = Integer.parseInt(res.getString(C0257R.string.number_format_thousand_grouping)) == 4;
/* 365 */           boolean hundredMillions = Integer.parseInt(res.getString(C0257R.string.number_format_million_grouping)) == 8;
/* 367 */           int groupingSeperatorSize = 3;
                    try {
/* 369 */               groupingSeperatorSize = Integer.parseInt(res.getString(C0257R.string.number_format_grouping_size));
                    } catch (NumberFormatException e) {
                    }
/* 374 */           DECIMAL_FORMAT.setGroupingSize(groupingSeperatorSize);
/* 375 */           DECIMAL_FORMAT_SYMBOLS.setGroupingSeparator(res.getString(C0257R.string.number_format_separator).charAt(0));
/* 377 */           DECIMAL_FORMAT_SYMBOLS.setDecimalSeparator(res.getString(C0257R.string.number_format_decimal_separator).charAt(0));
/* 380 */           DECIMAL_FORMAT.setGroupingUsed(true);
/* 381 */           DECIMAL_FORMAT.setDecimalFormatSymbols(DECIMAL_FORMAT_SYMBOLS);
/* 384 */           if (!shorten || Locale.KOREA.getLanguage().equals(Locale.getDefault().getLanguage())) {
/* 385 */               return DECIMAL_FORMAT.format(number);
                    }
/* 389 */           double n = number / 1000000.0d;
                    if (n >= 1.0d) {
/* 390 */               if (hundredMillions) {
/* 391 */                   n /= 100.0d;
                        }
/* 393 */               return res.getString(C0257R.string.number_format_millions, DECIMAL_FORMAT.format(Math.floor(10.0d * n) / 10.0d));
                    }
/* 395 */           double n2 = number / 10000.0d;
                    if (n2 >= 1.0d) {
/* 396 */               if (tenThousands) {
/* 397 */                   n2 /= 10.0d;
                        }
/* 399 */               return res.getString(C0257R.string.number_format_thousands, DECIMAL_FORMAT.format(Math.floor(100.0d * n2) / 10.0d));
                    }
/* 402 */           return DECIMAL_FORMAT.format(number);
                }

/* 417 */       public static String numberFormat(Resources res, long number) {
/* 418 */           return numberFormat(res, number, true);
                }

/* 421 */       public static void styleSectionHeader(Context context, TextView sectionTitle, TextView sectionSort) {
/* 422 */           Typeface font = Typefaces.get(context).mediumContent;
/* 423 */           sectionTitle.setTypeface(font);
/* 424 */           sectionSort.setTypeface(font);
                }

/* 427 */       public static void validateAndFixEntities(ArrayList<VineEntity> entities) {
/* 428 */           Collections.sort(entities);
                }

/* 434 */       public static void adjustEntities(ArrayList<VineEntity> entities, Editable contentSb, int offset, boolean xmlEntity) {
                    int directionalityOffset;
                    for (int i = 0; i < contentSb.length(); i++) {
/* 436 */               char c = contentSb.charAt(i);
/* 437 */               if (Character.isHighSurrogate(c) || c == 8206 || c == 8207) {
/* 438 */                   if (c == 8206 || c == 8207) {
/* 439 */                       directionalityOffset = 1;
                            } else {
/* 441 */                       directionalityOffset = 0;
                            }
                            for (VineEntity entity : entities) {
/* 445 */                       if (!entity.adjusted) {
/* 448 */                           int startWithOffset = entity.start + offset + directionalityOffset;
/* 449 */                           int endWithOffset = entity.end + offset;
/* 450 */                           if (startWithOffset > i) {
/* 451 */                               if (startWithOffset < contentSb.length() - 1) {
/* 452 */                                   entity.start++;
                                        }
/* 454 */                               if (endWithOffset < contentSb.length()) {
/* 455 */                                   entity.end++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    for (VineEntity entity2 : entities) {
/* 463 */               if (!entity2.adjusted) {
/* 466 */                   if (entity2.title == null) {
/* 468 */                       entity2.adjusted = true;
                            } else {
/* 471 */                       if (entity2.isUserType()) {
/* 472 */                           entity2.start += offset;
/* 473 */                           entity2.end += offset;
/* 474 */                           if (BuildUtil.isLogsOn()) {
/* 475 */                               contentSb.replace(entity2.start, entity2.end, entity2.title);
                                    } else {
                                        try {
/* 478 */                                   contentSb.replace(entity2.start, entity2.end, entity2.title);
                                        } catch (IndexOutOfBoundsException e) {
/* 480 */                                   CrashUtil.logException(e, "Supressing IOOBE with replace", new Object[0]);
                                        }
                                    }
/* 483 */                           offset -= (entity2.end - entity2.start) - entity2.title.length();
/* 484 */                           entity2.end = entity2.start + entity2.title.length();
                                } else if (entity2.isTagType()) {
/* 486 */                           entity2.title = "#" + entity2.title;
/* 487 */                           entity2.start += offset;
/* 488 */                           entity2.end += offset;
/* 489 */                           contentSb.replace(entity2.start, entity2.end, entity2.title);
/* 491 */                           offset = (offset - (entity2.end - entity2.start)) + entity2.title.length();
/* 492 */                           entity2.end = entity2.start + entity2.title.length();
                                } else if (entity2.isUserListType() || entity2.isCommentListType()) {
/* 494 */                           entity2.start += offset;
/* 495 */                           entity2.end += offset;
/* 496 */                           contentSb.replace(entity2.start, entity2.end, entity2.title);
/* 498 */                           offset = (offset - (entity2.end - entity2.start)) + entity2.title.length();
/* 499 */                           entity2.end = entity2.start + entity2.title.length();
                                }
/* 501 */                       entity2.adjusted = true;
                            }
                        }
                    }
                }

/* 505 */       public static void safeSetDefaultAvatar(ImageView view, ProfileImageSize size, int color) {
/* 506 */           int res = -1;
                    switch (size) {
                        case SMALL:
/* 509 */                   res = C0257R.drawable.avatar_small;
                            break;
                        case MEDIUM:
/* 512 */                   res = C0257R.drawable.avatar_medium;
                            break;
                        case LARGE:
/* 515 */                   res = C0257R.drawable.avatar_large;
                            break;
                    }
/* 518 */           if (color == 0) {
/* 519 */               color = Settings.DEFAULT_PROFILE_COLOR;
                    }
/* 521 */           int color2 = color | (-16777216);
                    try {
/* 523 */               view.setImageResource(res);
/* 524 */               view.setColorFilter(color2, PorterDuff.Mode.SRC_IN);
                    } catch (OutOfMemoryError e) {
/* 526 */               CrashUtil.log("OOM has happened.");
                    }
                }

/* 530 */       public static int getUserImageSize(Resources res) {
/* 531 */           return res.getDimensionPixelOffset(C0257R.dimen.user_image_size);
                }

                @Nullable
/* 536 */       public static VideoKey getVideoRequestKey(VinePost post, boolean forceLowKey, int avgSpeed) {
/* 537 */           List<VineVideoUrlTier> tieredUrls = post.getUrls();
/* 540 */           if (forceLowKey && post.videoLowUrl != null) {
/* 541 */               return new VideoKey(post.videoLowUrl);
                    }
/* 544 */           if (tieredUrls != null && tieredUrls.size() > 0) {
/* 545 */               VineVideoUrlTier selectedTier = tieredUrls.get(0);
                        for (VineVideoUrlTier tier : tieredUrls) {
                            if (tier.rate > selectedTier.rate && avgSpeed >= tier.rate) {
/* 549 */                       selectedTier = tier;
                            }
                        }
/* 553 */               return new VideoKey(selectedTier.url);
                    }
/* 557 */           return null;
                }

/* 566 */       public static String toPrettyComment(VineSingleNotification notification) {
/* 567 */           if (notification.prettyComment == null && !TextUtils.isEmpty(notification.comment)) {
/* 568 */               SpannableStringBuilder ssb = new SpannableStringBuilder(notification.comment);
/* 569 */               if (notification.entities != null) {
/* 570 */                   adjustEntities(notification.entities, ssb, 0, true);
                        }
/* 572 */               notification.prettyComment = ssb.toString();
                    }
/* 574 */           return notification.prettyComment;
                }
            }
