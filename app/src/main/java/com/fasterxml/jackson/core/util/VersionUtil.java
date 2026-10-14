            package com.fasterxml.jackson.core.util;

            import com.fasterxml.jackson.core.Version;
            import com.fasterxml.jackson.core.Versioned;
            import com.vandalsoftware.p004io.IoUtils;
            import java.io.BufferedReader;
            import java.io.Closeable;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.InputStreamReader;
            import java.io.Reader;
            import java.io.UnsupportedEncodingException;
            import java.util.Properties;
            import java.util.regex.Pattern;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    public class VersionUtil {
                private static final Pattern VERSION_SEPARATOR = Pattern.compile("[-_./;:]");
                private final Version _version;

/* 35 */        protected VersionUtil() {
/* 37 */            Version versionVersionFor = null;
                    try {
/* 42 */                versionVersionFor = versionFor(getClass());
                    } catch (Exception e) {
/* 44 */                System.err.println("ERROR: Failed to load Version information from " + getClass());
                    }
/* 49 */            this._version = versionVersionFor == null ? Version.unknownVersion() : versionVersionFor;
                }

/* 51 */        public Version version() {
/* 52 */            return this._version;
                }

/* 75 */        public static Version versionFor(Class<?> cls) {
/* 76 */            Version versionPackageVersionFor = packageVersionFor(cls);
/* 77 */            if (versionPackageVersionFor == null) {
/* 80 */                InputStream resourceAsStream = cls.getResourceAsStream("VERSION.txt");
/* 81 */                if (resourceAsStream == null) {
/* 82 */                    return Version.unknownVersion();
                        }
                        try {
                            try {
/* 86 */                        Version versionDoReadVersion = doReadVersion(new InputStreamReader(resourceAsStream, IoUtils.UTF_8));
/* 90 */                        _close(resourceAsStream);
/* 90 */                        return versionDoReadVersion;
                            } catch (UnsupportedEncodingException e) {
/* 88 */                        Version versionUnknownVersion = Version.unknownVersion();
/* 90 */                        _close(resourceAsStream);
/* 90 */                        return versionUnknownVersion;
                            }
                        } catch (Throwable th) {
                            _close(resourceAsStream);
                            throw th;
                        }
                    }
/* 90 */            return versionPackageVersionFor;
                }

/* 103 */       public static Version packageVersionFor(Class<?> cls) {
                    try {
/* 105 */               Class<?> cls2 = Class.forName(cls.getPackage().getName() + ".PackageVersion", true, cls.getClassLoader());
                        try {
/* 108 */                   return ((Versioned) cls2.newInstance()).version();
                        } catch (Exception e) {
/* 110 */                   throw new IllegalArgumentException("Failed to get Versioned out of " + cls2);
                        }
                    } catch (Exception e2) {
/* 113 */               return null;
                    }
                }

/* 120 */       private static Version doReadVersion(Reader reader) {
                    String strTrim;
                    String line;
                    String strTrim2 = null;
/* 121 */           BufferedReader bufferedReader = new BufferedReader(reader);
                    try {
                        try {
/* 123 */                   line = bufferedReader.readLine();
/* 124 */                   if (line != null) {
                                try {
/* 125 */                           strTrim = bufferedReader.readLine();
/* 126 */                           if (strTrim != null) {
                                        try {
/* 127 */                                   strTrim2 = bufferedReader.readLine();
                                        } catch (IOException e) {
/* 132 */                                   _close(bufferedReader);
                                        }
                                    }
                                } catch (IOException e2) {
                                    strTrim = null;
                                }
                            } else {
                                strTrim = null;
                            }
/* 132 */                   _close(bufferedReader);
                        } catch (Throwable th) {
                            _close(bufferedReader);
                            throw th;
                        }
                    } catch (IOException e3) {
                        strTrim = null;
                        line = null;
                    }
/* 135 */           if (strTrim != null) {
/* 136 */               strTrim = strTrim.trim();
                    }
/* 138 */           if (strTrim2 != null) {
/* 139 */               strTrim2 = strTrim2.trim();
                    }
/* 141 */           return parseVersion(line, strTrim, strTrim2);
                }

/* 157 */       public static Version mavenVersionFor(ClassLoader classLoader, String str, String str2) {
/* 158 */           InputStream resourceAsStream = classLoader.getResourceAsStream("META-INF/maven/" + str.replaceAll("\\.", "/") + "/" + str2 + "/pom.properties");
/* 160 */           if (resourceAsStream != null) {
                        try {
/* 162 */                   Properties properties = new Properties();
/* 163 */                   properties.load(resourceAsStream);
/* 167 */                   Version version = parseVersion(properties.getProperty("version"), properties.getProperty("groupId"), properties.getProperty("artifactId"));
/* 171 */                   _close(resourceAsStream);
/* 174 */                   return version;
                        } catch (IOException e) {
/* 171 */                   _close(resourceAsStream);
                        } catch (Throwable th) {
                            _close(resourceAsStream);
                            throw th;
                        }
                    }
/* 174 */           return Version.unknownVersion();
                }

/* 178 */       public static Version parseVersion(String str, String str2, String str3) {
/* 179 */           if (str != null) {
                        String strTrim = str.trim();
                        if (strTrim.length() > 0) {
/* 180 */                   String[] strArrSplit = VERSION_SEPARATOR.split(strTrim);
/* 181 */                   return new Version(parseVersionPart(strArrSplit[0]), strArrSplit.length > 1 ? parseVersionPart(strArrSplit[1]) : 0, strArrSplit.length > 2 ? parseVersionPart(strArrSplit[2]) : 0, strArrSplit.length > 3 ? strArrSplit[3] : null, str2, str3);
                        }
                    }
                    return null;
                }

/* 192 */       protected static int parseVersionPart(String str) {
/* 193 */           int length = str.length();
                    int i = 0;
                    for (int i2 = 0; i2 < length; i2++) {
/* 194 */               char cCharAt = str.charAt(i2);
/* 195 */               if (cCharAt > '9' || cCharAt < '0') {
                            break;
                        }
/* 196 */               i = (i * 10) + (cCharAt - '0');
                    }
/* 198 */           return i;
                }

/* 202 */       private static final void _close(Closeable closeable) {
                    try {
/* 203 */               closeable.close();
                    } catch (IOException e) {
                    }
                }

/* 213 */       public static final void throwInternal() {
/* 214 */           throw new RuntimeException("Internal error: this code path should never get executed");
                }
            }
