            package com.google.android.gms.internal;

            import android.text.TextUtils;
            import java.util.regex.Matcher;
            import java.util.regex.Pattern;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.ei */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C1051ei {

                /* JADX INFO: renamed from: lT */
                private static final Pattern f1763lT = Pattern.compile("\\\\.");

                /* JADX INFO: renamed from: lU */
                private static final Pattern f1764lU = Pattern.compile("[\\\\\"/\b\f\n\r\t]");

                /* JADX INFO: renamed from: I */
                public static String m1733I(String str) {
                    if (TextUtils.isEmpty(str)) {
                        return str;
                    }
                    Matcher matcher = f1764lU.matcher(str);
                    StringBuffer stringBuffer = null;
                    while (matcher.find()) {
                        if (stringBuffer == null) {
                            stringBuffer = new StringBuffer();
                        }
                        switch (matcher.group().charAt(0)) {
                            case '\b':
                                matcher.appendReplacement(stringBuffer, "\\\\b");
                                break;
                            case '\t':
                                matcher.appendReplacement(stringBuffer, "\\\\t");
                                break;
                            case '\n':
                                matcher.appendReplacement(stringBuffer, "\\\\n");
                                break;
                            case '\f':
                                matcher.appendReplacement(stringBuffer, "\\\\f");
                                break;
                            case '\r':
                                matcher.appendReplacement(stringBuffer, "\\\\r");
                                break;
                            case '\"':
                                matcher.appendReplacement(stringBuffer, "\\\\\\\"");
                                break;
                            case '/':
                                matcher.appendReplacement(stringBuffer, "\\\\/");
                                break;
                            case '\\':
                                matcher.appendReplacement(stringBuffer, "\\\\\\\\");
                                break;
                        }
                    }
                    if (stringBuffer == null) {
                        return str;
                    }
                    matcher.appendTail(stringBuffer);
                    return stringBuffer.toString();
                }
            }
