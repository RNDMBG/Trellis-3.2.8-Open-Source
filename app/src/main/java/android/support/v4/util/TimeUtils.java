            package android.support.v4.util;

            import co.vine.android.network.apache.MultipartEntity;
            import java.io.PrintWriter;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 37 */    public class TimeUtils {
                public static final int HUNDRED_DAY_FIELD_LEN = 19;
                private static final int SECONDS_PER_DAY = 86400;
                private static final int SECONDS_PER_HOUR = 3600;
                private static final int SECONDS_PER_MINUTE = 60;
                private static final Object sFormatSync = new Object();
                private static char[] sFormatStr = new char[24];

/* 38 */        private static int accumField(int amt, int suffix, boolean always, int zeropad) {
/* 39 */            if (amt > 99 || (always && zeropad >= 3)) {
/* 40 */                return suffix + 3;
                    }
/* 42 */            if (amt > 9 || (always && zeropad >= 2)) {
/* 43 */                return suffix + 2;
                    }
/* 45 */            if (always || amt > 0) {
/* 46 */                return suffix + 1;
                    }
/* 48 */            return 0;
                }

/* 52 */        private static int printField(char[] formatStr, int amt, char suffix, int pos, boolean always, int zeropad) {
/* 53 */            if (always || amt > 0) {
/* 55 */                if ((always && zeropad >= 3) || amt > 99) {
/* 56 */                    int dig = amt / 100;
/* 57 */                    formatStr[pos] = (char) (dig + 48);
/* 58 */                    pos++;
/* 59 */                    amt -= dig * 100;
                        }
/* 61 */                if ((always && zeropad >= 2) || amt > 9 || pos != pos) {
/* 62 */                    int dig2 = amt / 10;
/* 63 */                    formatStr[pos] = (char) (dig2 + 48);
/* 64 */                    pos++;
/* 65 */                    amt -= dig2 * 10;
                        }
/* 67 */                formatStr[pos] = (char) (amt + 48);
/* 68 */                int pos2 = pos + 1;
/* 69 */                formatStr[pos2] = suffix;
/* 70 */                return pos2 + 1;
                    }
/* 72 */            return pos;
                }

/* 75 */        private static int formatDurationLocked(long duration, int fieldLen) {
                    char prefix;
/* 76 */            if (sFormatStr.length < fieldLen) {
/* 77 */                sFormatStr = new char[fieldLen];
                    }
/* 80 */            char[] formatStr = sFormatStr;
                    if (duration == 0) {
/* 84 */                int fieldLen2 = fieldLen - 1;
/* 85 */                while (0 < fieldLen2) {
/* 83 */                    formatStr[0] = ' ';
                        }
/* 83 */                formatStr[0] = '0';
/* 89 */                return 1;
                    }
                    if (duration > 0) {
/* 94 */                prefix = '+';
                    } else {
/* 96 */                prefix = '-';
/* 97 */                duration = -duration;
                    }
/* 100 */           int millis = (int) (duration % 1000);
/* 101 */           int seconds = (int) Math.floor(duration / 1000);
/* 102 */           int days = 0;
                    int hours = 0;
                    int minutes = 0;
/* 104 */           if (seconds > SECONDS_PER_DAY) {
/* 105 */               days = seconds / SECONDS_PER_DAY;
/* 106 */               seconds -= SECONDS_PER_DAY * days;
                    }
/* 108 */           if (seconds > SECONDS_PER_HOUR) {
/* 109 */               hours = seconds / SECONDS_PER_HOUR;
/* 110 */               seconds -= hours * SECONDS_PER_HOUR;
                    }
/* 112 */           if (seconds > 60) {
/* 113 */               minutes = seconds / 60;
/* 114 */               seconds -= minutes * 60;
                    }
/* 117 */           int pos = 0;
/* 119 */           if (fieldLen != 0) {
/* 120 */               int myLen = accumField(days, 1, false, 0);
/* 121 */               int myLen2 = myLen + accumField(hours, 1, myLen > 0, 2);
/* 122 */               int myLen3 = myLen2 + accumField(minutes, 1, myLen2 > 0, 2);
/* 123 */               int myLen4 = myLen3 + accumField(seconds, 1, myLen3 > 0, 2);
/* 125 */               for (int myLen5 = myLen4 + accumField(millis, 2, true, myLen4 > 0 ? 3 : 0) + 1; myLen5 < fieldLen; myLen5++) {
/* 126 */                   formatStr[pos] = ' ';
/* 127 */                   pos++;
                        }
                    }
/* 132 */           formatStr[pos] = prefix;
/* 133 */           int pos2 = pos + 1;
/* 136 */           boolean zeropad = fieldLen != 0;
/* 137 */           int pos3 = printField(formatStr, days, 'd', pos2, false, 0);
/* 138 */           int pos4 = printField(formatStr, hours, 'h', pos3, pos3 != pos2, zeropad ? 2 : 0);
/* 139 */           int pos5 = printField(formatStr, minutes, 'm', pos4, pos4 != pos2, zeropad ? 2 : 0);
/* 140 */           int pos6 = printField(formatStr, seconds, 's', pos5, pos5 != pos2, zeropad ? 2 : 0);
/* 141 */           int pos7 = printField(formatStr, millis, 'm', pos6, true, (!zeropad || pos6 == pos2) ? 0 : 3);
/* 142 */           formatStr[pos7] = 's';
/* 143 */           return pos7 + 1;
                }

/* 147 */       public static void formatDuration(long duration, StringBuilder builder) {
                    synchronized (sFormatSync) {
                        try {
/* 149 */                   int len = formatDurationLocked(duration, 0);
/* 150 */                   builder.append(sFormatStr, 0, len);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 155 */       public static void formatDuration(long duration, PrintWriter pw, int fieldLen) {
                    synchronized (sFormatSync) {
                        try {
/* 157 */                   int len = formatDurationLocked(duration, fieldLen);
/* 158 */                   pw.print(new String(sFormatStr, 0, len));
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 163 */       public static void formatDuration(long duration, PrintWriter pw) {
/* 164 */           formatDuration(duration, pw, 0);
                }

/* 168 */       public static void formatDuration(long time, long now, PrintWriter pw) {
                    if (time == 0) {
/* 170 */               pw.print(MultipartEntity.DASH_DASH);
                    } else {
/* 173 */               formatDuration(time - now, pw, 0);
                    }
                }
            }
