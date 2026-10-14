            package com.google.android.gms.internal;

            import android.database.CharArrayBuffer;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.eg */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C1049eg {
                /* JADX INFO: renamed from: b */
                public static void m1731b(String str, CharArrayBuffer charArrayBuffer) {
                    if (charArrayBuffer.data == null || charArrayBuffer.data.length < str.length()) {
                        charArrayBuffer.data = str.toCharArray();
                    } else {
                        str.getChars(0, str.length(), charArrayBuffer.data, 0);
                    }
                    charArrayBuffer.sizeCopied = str.length();
                }
            }
