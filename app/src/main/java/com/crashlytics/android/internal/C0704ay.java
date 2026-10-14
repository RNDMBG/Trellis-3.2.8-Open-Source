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
            
            
            
    public final class C0704ay {

                
                private static InterfaceC0655aB f423a = InterfaceC0655aB.f294a;

                
                private URL f425c;

                
                private final String f426d;

                
                private C0659aF f427e;

                
                private boolean f428f;

                
                private HttpURLConnection f424b = null;

                
                private boolean f429g = true;

                
                private boolean f430h = false;

                
                private int f431i = 8192;

                
        static  int m299a(C0704ay c0704ay) {
            return c0704ay.f431i;
                }

                
        static  String m309b(String str) {
            return m311c(str);
                }

                
       private static String m311c(String str) {
                    return (str == null || str.length() <= 0) ? IoUtils.UTF_8 : str;
                }

                
       private static String m310c(CharSequence charSequence) throws C0657aD {
                    try {
               URL url = new URL(charSequence.toString());
               String host = url.getHost();
               int port = url.getPort();
               if (port != -1) {
                   host = host + CommonUtil.MARKER_COLON + Integer.toString(port);
                        }
                        try {
                   String aSCIIString = new URI(url.getProtocol(), host, url.getPath(), url.getQuery(), null).toASCIIString();
                   int iIndexOf = aSCIIString.indexOf(63);
                   if (iIndexOf > 0 && iIndexOf + 1 < aSCIIString.length()) {
                       return aSCIIString.substring(0, iIndexOf + 1) + aSCIIString.substring(iIndexOf + 1).replace("+", "%2B");
                            }
                   return aSCIIString;
                        } catch (URISyntaxException e) {
                   IOException iOException = new IOException("Parsing URI failed");
                   iOException.initCause(e);
                   throw new C0657aD(iOException);
                        }
                    } catch (IOException e2) {
               throw new C0657aD(e2);
                    }
                }

                
       private static String m305a(CharSequence charSequence, Map<?, ?> map) {
           String string = charSequence.toString();
           if (map != null && !map.isEmpty()) {
               StringBuilder sb = new StringBuilder(string);
               if (string.indexOf(58) + 2 == string.lastIndexOf(47)) {
                            sb.append('/');
                        }
               int iIndexOf = string.indexOf(63);
                        int length = sb.length() - 1;
                        if (iIndexOf == -1) {
                            sb.append('?');
                        } else if (iIndexOf < length && string.charAt(length) != '&') {
                            sb.append('&');
                        }
               Iterator<Map.Entry<?, ?>> it = map.entrySet().iterator();
               Map.Entry<?, ?> next = it.next();
               sb.append(next.getKey().toString());
               sb.append('=');
               Object value = next.getValue();
               if (value != null) {
                   sb.append(value);
                        }
               while (it.hasNext()) {
                   sb.append('&');
                   Map.Entry<?, ?> next2 = it.next();
                   sb.append(next2.getKey().toString());
                   sb.append('=');
                   Object value2 = next2.getValue();
                   if (value2 != null) {
                       sb.append(value2);
                            }
                        }
               return sb.toString();
                    }
           return string;
                }

                
       public static C0704ay m302a(CharSequence charSequence, Map<?, ?> map, boolean z) {
           return new C0704ay(m310c((CharSequence) m305a(charSequence, map)), "GET");
                }

                
       public static C0704ay m307b(CharSequence charSequence, Map<?, ?> map, boolean z) {
           return new C0704ay(m310c((CharSequence) m305a(charSequence, map)), "POST");
                }

                
       public static C0704ay m301a(CharSequence charSequence) throws C0657aD {
           return new C0704ay(charSequence, "PUT");
                }

                
      public static C0704ay m306b(CharSequence charSequence) throws C0657aD {
          return new C0704ay(charSequence, "DELETE");
                }

      private C0704ay(CharSequence charSequence, String str) throws C0657aD {
                    try {
              this.f425c = new URL(charSequence.toString());
              this.f426d = str;
                    } catch (MalformedURLException e) {
              throw new C0657aD(e);
                    }
                }

                
      private HttpURLConnection m316e() {
                    try {
              HttpURLConnection httpURLConnectionMo161a = f423a.mo161a(this.f425c);
              httpURLConnectionMo161a.setRequestMethod(this.f426d);
              return httpURLConnectionMo161a;
                    } catch (IOException e) {
              throw new C0657aD(e);
                    }
                }

      public final String toString() {
          return m330a().getRequestMethod() + ' ' + m330a().getURL();
                }

                
      public final HttpURLConnection m330a() {
          if (this.f424b == null) {
              this.f424b = m316e();
                    }
          return this.f424b;
                }

                
      public final int m331b() throws C0657aD {
                    try {
              m318g();
              return m330a().getResponseCode();
                    } catch (IOException e) {
              throw new C0657aD(e);
                    }
                }

                
      private String m315d(String str) throws C0657aD {
                    m319h();
          int headerFieldInt = m330a().getHeaderFieldInt("Content-Length", -1);
          ByteArrayOutputStream byteArrayOutputStream = headerFieldInt > 0 ? new ByteArrayOutputStream(headerFieldInt) : new ByteArrayOutputStream();
                    try {
              m300a(new BufferedInputStream(m317f(), this.f431i), byteArrayOutputStream);
              return byteArrayOutputStream.toString(m311c(str));
                    } catch (IOException e) {
              throw new C0657aD(e);
                    }
                }

                
      public final String m333c() throws C0657aD {
          return m315d(m312c(m329a("Content-Type"), "charset"));
                }

                
      private InputStream m317f() throws C0657aD {
          if (m331b() < 400) {
                        try {
                  return m330a().getInputStream();
                        } catch (IOException e) {
                  throw new C0657aD(e);
                        }
                    }
          InputStream errorStream = m330a().getErrorStream();
          if (errorStream == null) {
                        try {
                  return m330a().getInputStream();
                        } catch (IOException e2) {
                  throw new C0657aD(e2);
                        }
                    }
          return errorStream;
                }

                
      public final C0704ay m322a(int i) {
          m330a().setConnectTimeout(10000);
          return this;
                }

                
      public final C0704ay m324a(String str, String str2) {
          m330a().setRequestProperty(str, str2);
          return this;
                }

                
      public final C0704ay m327a(Map.Entry<String, String> entry) {
          return m324a(entry.getKey(), entry.getValue());
                }

                
      public final String m329a(String str) throws C0657aD {
          m319h();
          return m330a().getHeaderField(str);
                }

                
      private static String m312c(String str, String str2) {
                    String strTrim;
                    int length;
          if (str == null || str.length() == 0) {
                        return null;
                    }
          int length2 = str.length();
          int iIndexOf = str.indexOf(59) + 1;
          if (iIndexOf == 0 || iIndexOf == length2) {
                        return null;
                    }
          int iIndexOf2 = str.indexOf(59, iIndexOf);
          int i = iIndexOf2 == -1 ? length2 : iIndexOf2;
          while (iIndexOf < i) {
              int iIndexOf3 = str.indexOf(61, iIndexOf);
              if (iIndexOf3 != -1 && iIndexOf3 < i && str2.equals(str.substring(iIndexOf, iIndexOf3).trim()) && (length = (strTrim = str.substring(iIndexOf3 + 1, i).trim()).length()) != 0) {
                  if (length > 2 && '\"' == strTrim.charAt(0) && '\"' == strTrim.charAt(length - 1)) {
                      return strTrim.substring(1, length - 1);
                            }
                  return strTrim;
                        }
              int i2 = i + 1;
              int iIndexOf4 = str.indexOf(59, i2);
              if (iIndexOf4 == -1) {
                            iIndexOf4 = length2;
                        }
                        int i3 = iIndexOf4;
                        iIndexOf = i2;
                        i = i3;
                    }
                    return null;
                }

                
      public final C0704ay m328a(boolean z) {
          m330a().setUseCaches(false);
          return this;
                }

                
      private C0704ay m300a(InputStream inputStream, OutputStream outputStream) throws IOException {
          return new C0705az(this, inputStream, this.f429g, inputStream, outputStream).call();
                }

                
      private C0704ay m318g() throws IOException {
          if (this.f427e != null) {
              if (this.f428f) {
                  this.f427e.m163a("\r\n--00content0boundary00--\r\n");
                        }
              if (this.f429g) {
                            try {
                      this.f427e.close();
                            } catch (IOException e) {
                            }
                        } else {
                  this.f427e.close();
                        }
              this.f427e = null;
                    }
          return this;
                }

                
      private C0704ay m319h() throws C0657aD {
                    try {
              return m318g();
                    } catch (IOException e) {
              throw new C0657aD(e);
                    }
                }

                
      private C0704ay m320i() throws IOException {
          if (this.f427e == null) {
              m330a().setDoOutput(true);
              this.f427e = new C0659aF(m330a().getOutputStream(), m312c(m330a().getRequestProperty("Content-Type"), "charset"), this.f431i);
                    }
          return this;
                }

                
      private C0704ay m321j() throws IOException {
          if (!this.f428f) {
              this.f428f = true;
              m324a("Content-Type", "multipart/form-data; boundary=00content0boundary00").m320i();
              this.f427e.m163a("--00content0boundary00\r\n");
                    } else {
              this.f427e.m163a("\r\n--00content0boundary00\r\n");
                    }
          return this;
                }

                
      private C0704ay m303a(String str, String str2, String str3) throws IOException {
          StringBuilder sb = new StringBuilder();
          sb.append("form-data; name=\"").append(str);
          if (str2 != null) {
              sb.append("\"; filename=\"").append(str2);
                    }
          sb.append(CommonUtil.MARKER_QUOTES);
          m314d("Content-Disposition", sb.toString());
          if (str3 != null) {
              m314d("Content-Type", str3);
                    }
          return m313d("\r\n");
                }

                
      public final C0704ay m332b(String str, String str2) {
          return m308b(str, (String) null, str2);
                }

                
      private C0704ay m308b(String str, String str2, String str3) throws C0657aD {
          return m304a(str, str2, (String) null, str3);
                }

                
      private C0704ay m304a(String str, String str2, String str3, String str4) throws C0657aD {
                    try {
              m321j();
              m303a(str, str2, (String) null);
              this.f427e.m163a(str4);
              return this;
                    } catch (IOException e) {
              throw new C0657aD(e);
                    }
                }

                
      public final C0704ay m323a(String str, Number number) throws C0657aD {
                    return m308b(str, (String) null, number != null ? number.toString() : null);
                }

                
                
      public final C0704ay m325a(String str, String str2, String str3, File file) throws Throwable {
                    BufferedInputStream bufferedInputStream;
                    try {
              bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                        try {
                            try {
                      C0704ay c0704ayM326a = m326a(str, str2, str3, bufferedInputStream);
                                try {
                          bufferedInputStream.close();
                                } catch (IOException e) {
                                }
                      return c0704ayM326a;
                            } catch (IOException e2) {
                      e = e2;
                      throw new C0657aD(e);
                            }
                        } catch (Throwable th) {
                  th = th;
                            if (bufferedInputStream != null) {
                                try {
                          bufferedInputStream.close();
                                } catch (IOException e3) {
                                }
                            }
                  throw th;
                        }
                    } catch (IOException e4) {
              e = e4;
              bufferedInputStream = null;
                    } catch (Throwable th2) {
              th = th2;
              bufferedInputStream = null;
                        if (bufferedInputStream != null) {
                  bufferedInputStream.close();
                        }
              throw th;
                    }
                }

                
      public final C0704ay m326a(String str, String str2, String str3, InputStream inputStream) throws C0657aD {
                    try {
              m321j();
              m303a(str, str2, str3);
              m300a(inputStream, this.f427e);
              return this;
                    } catch (IOException e) {
              throw new C0657aD(e);
                    }
                }

                
      private C0704ay m314d(String str, String str2) throws C0657aD {
          return m313d((CharSequence) str).m313d(": ").m313d((CharSequence) str2).m313d("\r\n");
                }

                
      private C0704ay m313d(CharSequence charSequence) throws C0657aD {
                    try {
              m320i();
              this.f427e.m163a(charSequence.toString());
              return this;
                    } catch (IOException e) {
              throw new C0657aD(e);
                    }
                }

                
      public final String m334d() {
          return m330a().getRequestMethod();
                }
            }
