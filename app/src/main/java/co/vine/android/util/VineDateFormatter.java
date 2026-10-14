            package co.vine.android.util;

            import android.content.res.Resources;
            import android.text.SpannableStringBuilder;
            import android.text.style.TypefaceSpan;
            import co.vine.android.C0257R;
            import java.text.SimpleDateFormat;
            import java.util.Calendar;
            import java.util.Date;
            import java.util.HashSet;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 29 */    public class VineDateFormatter {
                private final SimpleDateFormat mDateFormatLong;
                private final SimpleDateFormat mDateFormatShort;
                private final HashSet<TypefaceSpan> mDateSectionSpans;
                private final String mDateTimePlacerPart1;
                private final boolean mIsDateBeforeTime;
                private Calendar mStartOfToday;
                private long mStartOfYesterday;
                private final SimpleDateFormat mTimeFormat;
                private final HashSet<TypefaceSpan> mTimeSectionSpans;
                private final String mYesterday;

/* 30 */        public VineDateFormatter(Resources res) {
/* 32 */            this.mTimeFormat = new SimpleDateFormat(res.getString(C0257R.string.vm_time_format));
/* 33 */            String dateTimePlacer = res.getString(C0257R.string.vm_date_format);
/* 36 */            int dateTimePlacerStart1 = dateTimePlacer.indexOf("%1$s");
/* 37 */            int dateTimePlacerStart2 = dateTimePlacer.indexOf("%2$s");
/* 39 */            this.mIsDateBeforeTime = dateTimePlacerStart1 < dateTimePlacerStart2;
/* 41 */            if (this.mIsDateBeforeTime) {
/* 42 */                this.mDateTimePlacerPart1 = dateTimePlacer.substring(dateTimePlacerStart1, dateTimePlacerStart2);
                    } else {
/* 44 */                this.mDateTimePlacerPart1 = dateTimePlacer.substring(dateTimePlacerStart1);
                    }
/* 47 */            this.mDateFormatLong = new SimpleDateFormat(res.getString(C0257R.string.date_format_long));
/* 48 */            this.mDateFormatShort = new SimpleDateFormat(res.getString(C0257R.string.date_format_short));
/* 50 */            this.mDateSectionSpans = new HashSet<>();
/* 51 */            this.mTimeSectionSpans = new HashSet<>();
/* 53 */            this.mYesterday = res.getString(C0257R.string.yesterday);
                }

/* 56 */        public void refreshDates() {
/* 57 */            this.mStartOfToday = DateTimeUtil.getStartOfDay();
/* 58 */            this.mStartOfYesterday = this.mStartOfToday.getTimeInMillis() - 86400000;
                }

/* 61 */        public CharSequence format(long time) {
/* 62 */            Date createdDate = new Date(time);
/* 63 */            Calendar createdCal = Calendar.getInstance();
/* 64 */            createdCal.setTimeInMillis(time);
                    if (time > this.mStartOfToday.getTimeInMillis()) {
/* 66 */                return this.mTimeFormat.format(createdDate);
                    }
/* 68 */            SpannableStringBuilder ssb = new SpannableStringBuilder();
/* 69 */            if (this.mIsDateBeforeTime) {
                        if (time > this.mStartOfYesterday) {
/* 71 */                    ssb.append((CharSequence) this.mDateTimePlacerPart1.replace("%1$s", this.mYesterday));
                        } else if (createdCal.get(1) == this.mStartOfToday.get(1)) {
/* 74 */                    ssb.append((CharSequence) this.mDateTimePlacerPart1.replace("%1$s", this.mDateFormatShort.format(createdDate)));
                        } else {
/* 76 */                    ssb.append((CharSequence) this.mDateTimePlacerPart1.replace("%1$s", this.mDateFormatLong.format(createdDate)));
                        }
/* 79 */                int end1 = ssb.length();
/* 80 */                ssb.append((CharSequence) this.mTimeFormat.format(createdDate));
/* 81 */                int end2 = ssb.length();
                        for (TypefaceSpan span : this.mDateSectionSpans) {
/* 83 */                    Util.safeSetSpan(ssb, span, 0, end1, 33);
                        }
                        for (TypefaceSpan span2 : this.mTimeSectionSpans) {
/* 86 */                    Util.safeSetSpan(ssb, span2, end1, end2, 33);
                        }
/* 108 */               return ssb;
                    }
/* 89 */            ssb.append((CharSequence) this.mTimeFormat.format(createdDate));
/* 90 */            int start = ssb.length();
                    if (time > this.mStartOfYesterday) {
/* 92 */                ssb.append((CharSequence) this.mDateTimePlacerPart1.replace("%1$s", this.mYesterday));
                    } else if (createdCal.get(1) == this.mStartOfToday.get(1)) {
/* 95 */                ssb.append((CharSequence) this.mDateTimePlacerPart1.replace("%1$s", this.mDateFormatShort.format(createdDate)));
                    } else {
/* 97 */                ssb.append((CharSequence) this.mDateTimePlacerPart1.replace("%1$s", this.mDateFormatLong.format(createdDate)));
                    }
/* 100 */           int end = ssb.length();
                    for (TypefaceSpan span3 : this.mDateSectionSpans) {
/* 102 */               Util.safeSetSpan(ssb, span3, start, end, 33);
                    }
                    for (TypefaceSpan span4 : this.mTimeSectionSpans) {
/* 105 */               Util.safeSetSpan(ssb, span4, 0, start, 33);
                    }
/* 108 */           return ssb;
                }

/* 112 */       public void addSpanForDateSection(TypefaceSpan span) {
/* 113 */           this.mDateSectionSpans.add(span);
                }

/* 116 */       public void addSpanForTimeSection(TypefaceSpan span) {
/* 117 */           this.mTimeSectionSpans.add(span);
                }
            }
