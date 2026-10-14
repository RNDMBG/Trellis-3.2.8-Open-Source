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
            
            
    public class VersionUtil {
                private static final Pattern VERSION_SEPARATOR = Pattern.compile("[-_./;:]");
                private final Version _version;

        protected VersionUtil() {
            Version versionVersionFor = null;
                    try {
                versionVersionFor = versionFor(getClass());
                    } catch (Exception e) {
                System.err.println("ERROR: Failed to load Version information from " + getClass());
                    }
            this._version = versionVersionFor == null ? Version.unknownVersion() : versionVersionFor;
                }

        public Version version() {
            return this._version;
                }

        public static Version versionFor(Class<?> cls) {
            Version versionPackageVersionFor = packageVersionFor(cls);
            if (versionPackageVersionFor == null) {
                InputStream resourceAsStream = cls.getResourceAsStream("VERSION.txt");
                if (resourceAsStream == null) {
                    return Version.unknownVersion();
                        }
                        try {
                            try {
                        Version versionDoReadVersion = doReadVersion(new InputStreamReader(resourceAsStream, IoUtils.UTF_8));
                        _close(resourceAsStream);
                        return versionDoReadVersion;
                            } catch (UnsupportedEncodingException e) {
                        Version versionUnknownVersion = Version.unknownVersion();
                        _close(resourceAsStream);
                        return versionUnknownVersion;
                            }
                        } catch (Throwable th) {
                            _close(resourceAsStream);
                            throw th;
                        }
                    }
            return versionPackageVersionFor;
                }

       public static Version packageVersionFor(Class<?> cls) {
                    try {
               Class<?> cls2 = Class.forName(cls.getPackage().getName() + ".PackageVersion", true, cls.getClassLoader());
                        try {
                   return ((Versioned) cls2.newInstance()).version();
                        } catch (Exception e) {
                   throw new IllegalArgumentException("Failed to get Versioned out of " + cls2);
                        }
                    } catch (Exception e2) {
               return null;
                    }
                }

       private static Version doReadVersion(Reader reader) {
                    String strTrim;
                    String line;
                    String strTrim2 = null;
           BufferedReader bufferedReader = new BufferedReader(reader);
                    try {
                        try {
                   line = bufferedReader.readLine();
                   if (line != null) {
                                try {
                           strTrim = bufferedReader.readLine();
                           if (strTrim != null) {
                                        try {
                                   strTrim2 = bufferedReader.readLine();
                                        } catch (IOException e) {
                                   _close(bufferedReader);
                                        }
                                    }
                                } catch (IOException e2) {
                                    strTrim = null;
                                }
                            } else {
                                strTrim = null;
                            }
                   _close(bufferedReader);
                        } catch (Throwable th) {
                            _close(bufferedReader);
                            throw th;
                        }
                    } catch (IOException e3) {
                        strTrim = null;
                        line = null;
                    }
           if (strTrim != null) {
               strTrim = strTrim.trim();
                    }
           if (strTrim2 != null) {
               strTrim2 = strTrim2.trim();
                    }
           return parseVersion(line, strTrim, strTrim2);
                }

       public static Version mavenVersionFor(ClassLoader classLoader, String str, String str2) {
           InputStream resourceAsStream = classLoader.getResourceAsStream("META-INF/maven/" + str.replaceAll("\\.", "/") + "/" + str2 + "/pom.properties");
           if (resourceAsStream != null) {
                        try {
                   Properties properties = new Properties();
                   properties.load(resourceAsStream);
                   Version version = parseVersion(properties.getProperty("version"), properties.getProperty("groupId"), properties.getProperty("artifactId"));
                   _close(resourceAsStream);
                   return version;
                        } catch (IOException e) {
                   _close(resourceAsStream);
                        } catch (Throwable th) {
                            _close(resourceAsStream);
                            throw th;
                        }
                    }
           return Version.unknownVersion();
                }

       public static Version parseVersion(String str, String str2, String str3) {
           if (str != null) {
                        String strTrim = str.trim();
                        if (strTrim.length() > 0) {
                   String[] strArrSplit = VERSION_SEPARATOR.split(strTrim);
                   return new Version(parseVersionPart(strArrSplit[0]), strArrSplit.length > 1 ? parseVersionPart(strArrSplit[1]) : 0, strArrSplit.length > 2 ? parseVersionPart(strArrSplit[2]) : 0, strArrSplit.length > 3 ? strArrSplit[3] : null, str2, str3);
                        }
                    }
                    return null;
                }

       protected static int parseVersionPart(String str) {
           int length = str.length();
                    int i = 0;
                    for (int i2 = 0; i2 < length; i2++) {
               char cCharAt = str.charAt(i2);
               if (cCharAt > '9' || cCharAt < '0') {
                            break;
                        }
               i = (i * 10) + (cCharAt - '0');
                    }
           return i;
                }

       private static final void _close(Closeable closeable) {
                    try {
               closeable.close();
                    } catch (IOException e) {
                    }
                }

       public static final void throwInternal() {
           throw new RuntimeException("Internal error: this code path should never get executed");
                }
            }
