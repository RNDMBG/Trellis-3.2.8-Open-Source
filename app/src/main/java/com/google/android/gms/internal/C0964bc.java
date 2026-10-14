            package com.google.android.gms.internal;

            import android.content.ActivityNotFoundException;
            import android.content.Context;
            import android.content.Intent;
            import android.net.Uri;
            import android.text.TextUtils;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.bc */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0964bc {
                /* JADX INFO: renamed from: a */
                public static boolean m1296a(Context context, C0966be c0966be, InterfaceC0973bl interfaceC0973bl) {
                    if (c0966be == null) {
                        C1002cn.m1458q("No intent data for launcher overlay.");
                        return false;
                    }
                    Intent intent = new Intent();
                    if (TextUtils.isEmpty(c0966be.f1427fz)) {
                        C1002cn.m1458q("Open GMSG did not contain a URL.");
                        return false;
                    }
                    if (TextUtils.isEmpty(c0966be.mimeType)) {
                        intent.setData(Uri.parse(c0966be.f1427fz));
                    } else {
                        intent.setDataAndType(Uri.parse(c0966be.f1427fz), c0966be.mimeType);
                    }
                    intent.setAction("android.intent.action.VIEW");
                    if (!TextUtils.isEmpty(c0966be.packageName)) {
                        intent.setPackage(c0966be.packageName);
                    }
                    if (!TextUtils.isEmpty(c0966be.f1423fA)) {
                        String[] strArrSplit = c0966be.f1423fA.split("/", 2);
                        if (strArrSplit.length < 2) {
                            C1002cn.m1458q("Could not parse component name from open GMSG: " + c0966be.f1423fA);
                            return false;
                        }
                        intent.setClassName(strArrSplit[0], strArrSplit[1]);
                    }
                    try {
                        C1002cn.m1457p("Launching an intent: " + intent);
                        context.startActivity(intent);
                        interfaceC0973bl.mo1337A();
                        return true;
                    } catch (ActivityNotFoundException e) {
                        C1002cn.m1458q(e.getMessage());
                        return false;
                    }
                }
            }
