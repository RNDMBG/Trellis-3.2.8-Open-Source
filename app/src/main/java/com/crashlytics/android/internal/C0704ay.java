            package com.crashlytics.android.internal;

            import co.vine.android.util.CommonUtil;
            import com.vandalsoftware.p004io.IoUtils;
            import java.io.BufferedInputStream;
            import java.io.ByteArrayOutputStream;
            import java.io.File;
            import java.io.FileInputStream;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            import java.net.HttpURLConnection;
            import java.net.MalformedURLException;
            import java.net.URI;
            import java.net.URISyntaxException;
            import java.net.URL;
            import java.util.Iterator;
            import java.util.Map;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.ay */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 92 */    public final class C0704ay {

                /* JADX INFO: renamed from: a */
                private static InterfaceC0655aB f423a = InterfaceC0655aB.f294a;

                /* JADX INFO: renamed from: c */
                private URL f425c;

                /* JADX INFO: renamed from: d */
                private final String f426d;

                /* JADX INFO: renamed from: e */
                private C0659aF f427e;

                /* JADX INFO: renamed from: f */
                private boolean f428f;

                /* JADX INFO: renamed from: b */
                private HttpURLConnection f424b = null;

                /* JADX INFO: renamed from: g */
                private boolean f429g = true;

                /* JADX INFO: renamed from: h */
                private boolean f430h = false;

                /* JADX INFO: renamed from: i */
                private int f431i = 8192;

                /* JADX INFO: renamed from: a */
/* 93 */        static /* synthetic */ int m299a(C0704ay c0704ay) {
/* 94 */            return c0704ay.f431i;
                }

                /* JADX INFO: renamed from: b */
/* 93 */        static /* synthetic */ String m309b(String str) {
/* 94 */            return m311c(str);
                }

                /* JADX INFO: renamed from: c */
/* 258 */       private static String m311c(String str) {
                    return (str == null || str.length() <= 0) ? IoUtils.UTF_8 : str;
                }

                /* JADX INFO: renamed from: c */
/* 755 */       private static String m310c(CharSequence charSequence) throws C0657aD {
                    try {
/* 756 */               URL url = new URL(charSequence.toString());
/* 761 */               String host = url.getHost();
/* 762 */               int port = url.getPort();
/* 763 */               if (port != -1) {
/* 764 */                   host = host + CommonUtil.MARKER_COLON + Integer.toString(port);
                        }
                        try {
/* 767 */                   String aSCIIString = new URI(url.getProtocol(), host, url.getPath(), url.getQuery(), null).toASCIIString();
/* 768 */                   int iIndexOf = aSCIIString.indexOf(63);
/* 769 */                   if (iIndexOf > 0 && iIndexOf + 1 < aSCIIString.length()) {
/* 770 */                       return aSCIIString.substring(0, iIndexOf + 1) + aSCIIString.substring(iIndexOf + 1).replace("+", "%2B");
                            }
/* 772 */                   return aSCIIString;
                        } catch (URISyntaxException e) {
/* 774 */                   IOException iOException = new IOException("Parsing URI failed");
/* 775 */                   iOException.initCause(e);
/* 776 */                   throw new C0657aD(iOException);
                        }
                    } catch (IOException e2) {
/* 758 */               throw new C0657aD(e2);
                    }
                }

                /* JADX INFO: renamed from: a */
/* 790 */       private static String m305a(CharSequence charSequence, Map<?, ?> map) {
/* 791 */           String string = charSequence.toString();
/* 792 */           if (map != null && !map.isEmpty()) {
/* 795 */               StringBuilder sb = new StringBuilder(string);
/* 797 */               if (string.indexOf(58) + 2 == string.lastIndexOf(47)) {
                            sb.append('/');
                        }
/* 798 */               int iIndexOf = string.indexOf(63);
                        int length = sb.length() - 1;
                        if (iIndexOf == -1) {
                            sb.append('?');
                        } else if (iIndexOf < length && string.charAt(length) != '&') {
                            sb.append('&');
                        }
/* 802 */               Iterator<Map.Entry<?, ?>> it = map.entrySet().iterator();
/* 803 */               Map.Entry<?, ?> next = it.next();
/* 804 */               sb.append(next.getKey().toString());
/* 805 */               sb.append('=');
/* 806 */               Object value = next.getValue();
/* 807 */               if (value != null) {
/* 808 */                   sb.append(value);
                        }
/* 810 */               while (it.hasNext()) {
/* 811 */                   sb.append('&');
/* 812 */                   Map.Entry<?, ?> next2 = it.next();
/* 813 */                   sb.append(next2.getKey().toString());
/* 814 */                   sb.append('=');
/* 815 */                   Object value2 = next2.getValue();
/* 816 */                   if (value2 != null) {
/* 817 */                       sb.append(value2);
                            }
                        }
/* 820 */               return sb.toString();
                    }
/* 820 */           return string;
                }

                /* JADX INFO: renamed from: a */
/* 899 */       public static C0704ay m302a(CharSequence charSequence, Map<?, ?> map, boolean z) {
/* 901 */           return new C0704ay(m310c((CharSequence) m305a(charSequence, map)), "GET");
                }

                /* JADX INFO: renamed from: b */
/* 955 */       public static C0704ay m307b(CharSequence charSequence, Map<?, ?> map, boolean z) {
/* 957 */           return new C0704ay(m310c((CharSequence) m305a(charSequence, map)), "POST");
                }

                /* JADX INFO: renamed from: a */
/* 984 */       public static C0704ay m301a(CharSequence charSequence) throws C0657aD {
/* 985 */           return new C0704ay(charSequence, "PUT");
                }

                /* JADX INFO: renamed from: b */
/* 1040 */      public static C0704ay m306b(CharSequence charSequence) throws C0657aD {
/* 1041 */          return new C0704ay(charSequence, "DELETE");
                }

/* 1302 */      private C0704ay(CharSequence charSequence, String str) throws C0657aD {
                    try {
/* 1305 */              this.f425c = new URL(charSequence.toString());
/* 1309 */              this.f426d = str;
                    } catch (MalformedURLException e) {
/* 1307 */              throw new C0657aD(e);
                    }
                }

                /* JADX INFO: renamed from: e */
/* 1330 */      private HttpURLConnection m316e() {
                    try {
/* 1331 */              HttpURLConnection httpURLConnectionMo161a = f423a.mo161a(this.f425c);
/* 1335 */              httpURLConnectionMo161a.setRequestMethod(this.f426d);
/* 1336 */              return httpURLConnectionMo161a;
                    } catch (IOException e) {
/* 1338 */              throw new C0657aD(e);
                    }
                }

/* 1343 */      public final String toString() {
/* 1344 */          return m330a().getRequestMethod() + ' ' + m330a().getURL();
                }

                /* JADX INFO: renamed from: a */
/* 1352 */      public final HttpURLConnection m330a() {
/* 1353 */          if (this.f424b == null) {
/* 1354 */              this.f424b = m316e();
                    }
/* 1355 */          return this.f424b;
                }

                /* JADX INFO: renamed from: b */
/* 1387 */      public final int m331b() throws C0657aD {
                    try {
/* 1388 */              m318g();
/* 1389 */              return m330a().getResponseCode();
                    } catch (IOException e) {
/* 1391 */              throw new C0657aD(e);
                    }
                }

                /* JADX INFO: renamed from: d */
/* 1573 */      private String m315d(String str) throws C0657aD {
                    m319h();
/* 1574 */          int headerFieldInt = m330a().getHeaderFieldInt("Content-Length", -1);
/* 1574 */          ByteArrayOutputStream byteArrayOutputStream = headerFieldInt > 0 ? new ByteArrayOutputStream(headerFieldInt) : new ByteArrayOutputStream();
                    try {
/* 1576 */              m300a(new BufferedInputStream(m317f(), this.f431i), byteArrayOutputStream);
/* 1577 */              return byteArrayOutputStream.toString(m311c(str));
                    } catch (IOException e) {
/* 1579 */              throw new C0657aD(e);
                    }
                }

                /* JADX INFO: renamed from: c */
/* 1589 */      public final String m333c() throws C0657aD {
/* 1590 */          return m315d(m312c(m329a("Content-Type"), "charset"));
                }

                /* JADX INFO: renamed from: f */
/* 1663 */      private InputStream m317f() throws C0657aD {
/* 1664 */          if (m331b() < 400) {
                        try {
/* 1666 */                  return m330a().getInputStream();
                        } catch (IOException e) {
/* 1668 */                  throw new C0657aD(e);
                        }
                    }
/* 1671 */          InputStream errorStream = m330a().getErrorStream();
/* 1672 */          if (errorStream == null) {
                        try {
/* 1674 */                  return m330a().getInputStream();
                        } catch (IOException e2) {
/* 1676 */                  throw new C0657aD(e2);
                        }
                    }
/* 1680 */          return errorStream;
                }

                /* JADX INFO: renamed from: a */
/* 1851 */      public final C0704ay m322a(int i) {
/* 1852 */          m330a().setConnectTimeout(10000);
/* 1853 */          return this;
                }

                /* JADX INFO: renamed from: a */
/* 1863 */      public final C0704ay m324a(String str, String str2) {
/* 1864 */          m330a().setRequestProperty(str, str2);
/* 1865 */          return this;
                }

                /* JADX INFO: renamed from: a */
/* 1899 */      public final C0704ay m327a(Map.Entry<String, String> entry) {
/* 1900 */          return m324a(entry.getKey(), entry.getValue());
                }

                /* JADX INFO: renamed from: a */
/* 1910 */      public final String m329a(String str) throws C0657aD {
/* 1911 */          m319h();
/* 1912 */          return m330a().getHeaderField(str);
                }

                /* JADX INFO: renamed from: c */
/* 2070 */      private static String m312c(String str, String str2) {
                    String strTrim;
                    int length;
/* 2071 */          if (str == null || str.length() == 0) {
                        return null;
                    }
/* 2074 */          int length2 = str.length();
/* 2075 */          int iIndexOf = str.indexOf(59) + 1;
/* 2076 */          if (iIndexOf == 0 || iIndexOf == length2) {
                        return null;
                    }
/* 2079 */          int iIndexOf2 = str.indexOf(59, iIndexOf);
/* 2080 */          int i = iIndexOf2 == -1 ? length2 : iIndexOf2;
/* 2083 */          while (iIndexOf < i) {
/* 2084 */              int iIndexOf3 = str.indexOf(61, iIndexOf);
/* 2085 */              if (iIndexOf3 != -1 && iIndexOf3 < i && str2.equals(str.substring(iIndexOf, iIndexOf3).trim()) && (length = (strTrim = str.substring(iIndexOf3 + 1, i).trim()).length()) != 0) {
/* 2089 */                  if (length > 2 && '\"' == strTrim.charAt(0) && '\"' == strTrim.charAt(length - 1)) {
/* 2090 */                      return strTrim.substring(1, length - 1);
                            }
/* 2101 */                  return strTrim;
                        }
/* 2095 */              int i2 = i + 1;
/* 2096 */              int iIndexOf4 = str.indexOf(59, i2);
/* 2097 */              if (iIndexOf4 == -1) {
                            iIndexOf4 = length2;
                        }
                        int i3 = iIndexOf4;
                        iIndexOf = i2;
                        i = i3;
                    }
                    return null;
                }

                /* JADX INFO: renamed from: a */
/* 2139 */      public final C0704ay m328a(boolean z) {
/* 2140 */          m330a().setUseCaches(false);
/* 2141 */          return this;
                }

                /* JADX INFO: renamed from: a */
/* 2400 */      private C0704ay m300a(InputStream inputStream, OutputStream outputStream) throws IOException {
/* 2401 */          return new C0705az(this, inputStream, this.f429g, inputStream, outputStream).call();
                }

                /* JADX INFO: renamed from: g */
/* 2443 */      private C0704ay m318g() throws IOException {
/* 2444 */          if (this.f427e != null) {
/* 2446 */              if (this.f428f) {
/* 2447 */                  this.f427e.m163a("\r\n--00content0boundary00--\r\n");
                        }
/* 2448 */              if (this.f429g) {
                            try {
/* 2450 */                      this.f427e.close();
                            } catch (IOException e) {
                            }
                        } else {
/* 2455 */                  this.f427e.close();
                        }
/* 2456 */              this.f427e = null;
                    }
/* 2457 */          return this;
                }

                /* JADX INFO: renamed from: h */
/* 2468 */      private C0704ay m319h() throws C0657aD {
                    try {
/* 2469 */              return m318g();
                    } catch (IOException e) {
/* 2471 */              throw new C0657aD(e);
                    }
                }

                /* JADX INFO: renamed from: i */
/* 2481 */      private C0704ay m320i() throws IOException {
/* 2482 */          if (this.f427e == null) {
/* 2484 */              m330a().setDoOutput(true);
/* 2486 */              this.f427e = new C0659aF(m330a().getOutputStream(), m312c(m330a().getRequestProperty("Content-Type"), "charset"), this.f431i);
                    }
/* 2487 */          return this;
                }

                /* JADX INFO: renamed from: j */
/* 2496 */      private C0704ay m321j() throws IOException {
/* 2497 */          if (!this.f428f) {
/* 2498 */              this.f428f = true;
/* 2499 */              m324a("Content-Type", "multipart/form-data; boundary=00content0boundary00").m320i();
/* 2500 */              this.f427e.m163a("--00content0boundary00\r\n");
                    } else {
/* 2502 */              this.f427e.m163a("\r\n--00content0boundary00\r\n");
                    }
/* 2503 */          return this;
                }

                /* JADX INFO: renamed from: a */
/* 2528 */      private C0704ay m303a(String str, String str2, String str3) throws IOException {
/* 2529 */          StringBuilder sb = new StringBuilder();
/* 2530 */          sb.append("form-data; name=\"").append(str);
/* 2531 */          if (str2 != null) {
/* 2532 */              sb.append("\"; filename=\"").append(str2);
                    }
/* 2533 */          sb.append(CommonUtil.MARKER_QUOTES);
/* 2534 */          m314d("Content-Disposition", sb.toString());
/* 2535 */          if (str3 != null) {
/* 2536 */              m314d("Content-Type", str3);
                    }
/* 2537 */          return m313d("\r\n");
                }

                /* JADX INFO: renamed from: b */
/* 2547 */      public final C0704ay m332b(String str, String str2) {
/* 2548 */          return m308b(str, (String) null, str2);
                }

                /* JADX INFO: renamed from: b */
/* 2561 */      private C0704ay m308b(String str, String str2, String str3) throws C0657aD {
/* 2562 */          return m304a(str, str2, (String) null, str3);
                }

                /* JADX INFO: renamed from: a */
/* 2577 */      private C0704ay m304a(String str, String str2, String str3, String str4) throws C0657aD {
                    try {
/* 2578 */              m321j();
/* 2579 */              m303a(str, str2, (String) null);
/* 2580 */              this.f427e.m163a(str4);
/* 2584 */              return this;
                    } catch (IOException e) {
/* 2582 */              throw new C0657aD(e);
                    }
                }

                /* JADX INFO: renamed from: a */
/* 2595 */      public final C0704ay m323a(String str, Number number) throws C0657aD {
                    return m308b(str, (String) null, number != null ? number.toString() : null);
                }

                /* JADX WARN: Code duplicated, block: B:25:0x001e A[EXC_TOP_SPLITTER, SYNTHETIC] */
                /* JADX INFO: renamed from: a */
/* 2649 */      public final C0704ay m325a(String str, String str2, String str3, File file) throws Throwable {
                    BufferedInputStream bufferedInputStream;
                    try {
/* 2652 */              bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                        try {
                            try {
/* 2653 */                      C0704ay c0704ayM326a = m326a(str, str2, str3, bufferedInputStream);
                                try {
/* 2657 */                          bufferedInputStream.close();
                                } catch (IOException e) {
                                }
/* 2661 */                      return c0704ayM326a;
                            } catch (IOException e2) {
/* 2654 */                      e = e2;
/* 2655 */                      throw new C0657aD(e);
                            }
                        } catch (Throwable th) {
/* 2657 */                  th = th;
                            if (bufferedInputStream != null) {
                                try {
/* 2659 */                          bufferedInputStream.close();
                                } catch (IOException e3) {
                                }
                            }
/* 2661 */                  throw th;
                        }
                    } catch (IOException e4) {
/* 2654 */              e = e4;
/* 2650 */              bufferedInputStream = null;
                    } catch (Throwable th2) {
/* 2657 */              th = th2;
/* 2650 */              bufferedInputStream = null;
                        if (bufferedInputStream != null) {
/* 2659 */                  bufferedInputStream.close();
                        }
/* 2661 */              throw th;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 2690 */      public final C0704ay m326a(String str, String str2, String str3, InputStream inputStream) throws C0657aD {
                    try {
/* 2691 */              m321j();
/* 2692 */              m303a(str, str2, str3);
/* 2693 */              m300a(inputStream, this.f427e);
/* 2697 */              return this;
                    } catch (IOException e) {
/* 2695 */              throw new C0657aD(e);
                    }
                }

                /* JADX INFO: renamed from: d */
/* 2708 */      private C0704ay m314d(String str, String str2) throws C0657aD {
/* 2709 */          return m313d((CharSequence) str).m313d(": ").m313d((CharSequence) str2).m313d("\r\n");
                }

                /* JADX INFO: renamed from: d */
/* 2795 */      private C0704ay m313d(CharSequence charSequence) throws C0657aD {
                    try {
/* 2796 */              m320i();
/* 2797 */              this.f427e.m163a(charSequence.toString());
/* 2801 */              return this;
                    } catch (IOException e) {
/* 2799 */              throw new C0657aD(e);
                    }
                }

                /* JADX INFO: renamed from: d */
/* 2969 */      public final String m334d() {
/* 2970 */          return m330a().getRequestMethod();
                }
            }
