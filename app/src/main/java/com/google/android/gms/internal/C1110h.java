            package com.google.android.gms.internal;

            import android.content.Context;
            import android.net.Uri;
            import android.view.MotionEvent;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.h */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1110h {

                /* JADX INFO: renamed from: dz */
                private InterfaceC1015d f2130dz;

                /* JADX INFO: renamed from: dw */
                private String f2127dw = "googleads.g.doubleclick.net";

                /* JADX INFO: renamed from: dx */
                private String f2128dx = "/pagead/ads";

                /* JADX INFO: renamed from: dy */
                private String[] f2129dy = {".doubleclick.net", ".googleadservices.com", ".googlesyndication.com"};

                /* JADX INFO: renamed from: dA */
                private final C0988c f2126dA = new C0988c();

                public C1110h(InterfaceC1015d interfaceC1015d) {
                    this.f2130dz = interfaceC1015d;
                }

                /* JADX INFO: renamed from: a */
                private Uri m2123a(Uri uri, Context context, String str, boolean z) throws C1111i {
                    try {
                        if (uri.getQueryParameter("ms") != null) {
                            throw new C1111i("Query parameter already exists: ms");
                        }
                        return m2124a(uri, "ms", z ? this.f2130dz.mo1517a(context, str) : this.f2130dz.mo1516a(context));
                    } catch (UnsupportedOperationException e) {
                        throw new C1111i("Provided Uri is not in a valid state");
                    }
                }

                /* JADX INFO: renamed from: a */
                private Uri m2124a(Uri uri, String str, String str2) throws UnsupportedOperationException {
                    String string = uri.toString();
                    int iIndexOf = string.indexOf("&adurl");
                    if (iIndexOf == -1) {
                        iIndexOf = string.indexOf("?adurl");
                    }
                    return iIndexOf != -1 ? Uri.parse(string.substring(0, iIndexOf + 1) + str + "=" + str2 + "&" + string.substring(iIndexOf + 1)) : uri.buildUpon().appendQueryParameter(str, str2).build();
                }

                /* JADX INFO: renamed from: a */
                public Uri m2125a(Uri uri, Context context) throws C1111i {
                    try {
                        return m2123a(uri, context, uri.getQueryParameter("ai"), true);
                    } catch (UnsupportedOperationException e) {
                        throw new C1111i("Provided Uri is not in a valid state");
                    }
                }

                /* JADX INFO: renamed from: a */
                public void m2126a(MotionEvent motionEvent) {
                    this.f2130dz.mo1519a(motionEvent);
                }

                /* JADX INFO: renamed from: a */
                public boolean m2127a(Uri uri) {
                    if (uri == null) {
                        throw new NullPointerException();
                    }
                    try {
                        String host = uri.getHost();
                        for (String str : this.f2129dy) {
                            if (host.endsWith(str)) {
                                return true;
                            }
                        }
                        return false;
                    } catch (NullPointerException e) {
                        return false;
                    }
                }

                /* JADX INFO: renamed from: g */
                public InterfaceC1015d m2128g() {
                    return this.f2130dz;
                }
            }
