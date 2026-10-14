            package com.googlecode.javacpp;

            import com.googlecode.javacpp.annotation.Platform;
            import java.io.File;
            import java.io.FileOutputStream;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.InputStreamReader;
            import java.net.URL;
            import java.util.Arrays;
            import java.util.Collection;
            import java.util.Collections;
            import java.util.HashMap;
            import java.util.LinkedList;
            import java.util.Map;
            import java.util.MissingResourceException;
            import java.util.Properties;
            import java.util.WeakHashMap;
            import org.apache.commons.io.FilenameUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 55 */    public class Loader {
                static boolean loadLibraries;
                static Map<String, String> loadedLibraries;
                static WeakHashMap<Class<? extends Pointer>, HashMap<String, Integer>> memberOffsets;
                private static final String platformName;
                private static Properties platformProperties = null;
                static File tempDir;

                static {
/* 60 */            String jvmName = System.getProperty("java.vm.name").toLowerCase();
/* 61 */            String osName = System.getProperty("os.name").toLowerCase();
/* 62 */            String osArch = System.getProperty("os.arch").toLowerCase();
/* 63 */            if (jvmName.startsWith("dalvik") && osName.startsWith("linux")) {
/* 64 */                osName = "android";
                    } else if (osName.startsWith("mac os x")) {
/* 66 */                osName = "macosx";
                    } else {
/* 68 */                int spaceIndex = osName.indexOf(32);
/* 69 */                if (spaceIndex > 0) {
/* 70 */                    osName = osName.substring(0, spaceIndex);
                        }
                    }
/* 73 */            if (osArch.equals("i386") || osArch.equals("i486") || osArch.equals("i586") || osArch.equals("i686")) {
/* 74 */                osArch = "x86";
                    } else if (osArch.equals("amd64") || osArch.equals("x86-64") || osArch.equals("x64")) {
/* 76 */                osArch = "x86_64";
                    } else if (osArch.startsWith("arm")) {
/* 78 */                osArch = "arm";
                    }
/* 80 */            platformName = osName + "-" + osArch;
/* 491 */           tempDir = null;
/* 493 */           loadLibraries = true;
/* 495 */           loadedLibraries = Collections.synchronizedMap(new HashMap());
/* 727 */           if (getPlatformName().startsWith("windows")) {
/* 728 */               Runtime.getRuntime().addShutdownHook(new C12232());
                    }
/* 776 */           memberOffsets = new WeakHashMap<>();
                }

/* 90 */        public static String getPlatformName() {
/* 91 */            return System.getProperty("com.googlecode.javacpp.platform.name", platformName);
                }

/* 99 */        public static Properties loadProperties() {
/* 100 */           String name = getPlatformName();
/* 101 */           if (platformProperties != null && name.equals(platformProperties.getProperty("platform.name"))) {
/* 102 */               return platformProperties;
                    }
                    Properties propertiesLoadProperties = loadProperties(name);
                    platformProperties = propertiesLoadProperties;
/* 104 */           return propertiesLoadProperties;
                }

/* 112 */       public static Properties loadProperties(String name) {
/* 113 */           Properties p = new Properties();
/* 114 */           p.put("platform.name", name);
/* 116 */           InputStream is = Loader.class.getResourceAsStream("properties/" + name + ".properties");
                    try {
                        try {
/* 119 */                   p.load(new InputStreamReader(is));
                        } catch (NoSuchMethodError e) {
/* 121 */                   p.load(is);
                        }
                    } catch (Exception e2) {
/* 124 */               InputStream is2 = Loader.class.getResourceAsStream("properties/generic.properties");
                        try {
                            try {
/* 128 */                       p.load(new InputStreamReader(is2));
                            } catch (NoSuchMethodError e3) {
/* 130 */                       p.load(is2);
                            }
                        } catch (Exception e4) {
/* 133 */                   throw new MissingResourceException("Could not even get generic properties: " + e4.getMessage(), Loader.class.getName(), "properties/generic.properties");
                        }
                    }
/* 137 */           return p;
                }

/* 150 */       public static Class getEnclosingClass(Class cls) {
/* 151 */           Class declaringClass = cls;
/* 153 */           while (declaringClass.getDeclaringClass() != null && !declaringClass.isAnnotationPresent(com.googlecode.javacpp.annotation.Properties.class)) {
/* 157 */               if (declaringClass.isAnnotationPresent(Platform.class)) {
/* 158 */                   Platform p = (Platform) declaringClass.getAnnotation(Platform.class);
/* 159 */                   if (p.define().length > 0 || p.include().length > 0 || p.cinclude().length > 0 || p.includepath().length > 0 || p.options().length > 0 || p.linkpath().length > 0 || p.link().length > 0 || p.framework().length > 0 || p.preloadpath().length > 0 || p.preload().length > 0 || p.library().length() > 0) {
                                break;
                            }
                        }
/* 166 */               declaringClass = declaringClass.getDeclaringClass();
                    }
/* 168 */           return declaringClass;
                }

/* 178 */       public static class ClassProperties extends HashMap<String, LinkedList<String>> {
                    String pathSeparator;
                    String platformName;
                    String platformRoot;

/* 179 */           public ClassProperties() {
                    }

/* 180 */           public ClassProperties(Properties properties) {
/* 182 */               this.platformName = properties.getProperty("platform.name");
/* 183 */               this.platformRoot = properties.getProperty("platform.root");
/* 184 */               this.pathSeparator = properties.getProperty("path.separator");
/* 185 */               if (this.platformRoot == null || this.platformRoot.length() == 0) {
/* 186 */                   this.platformRoot = ".";
                        }
/* 188 */               if (!this.platformRoot.endsWith(File.separator)) {
/* 189 */                   this.platformRoot += File.separator;
                        }
                        for (Map.Entry e : properties.entrySet()) {
/* 192 */                   String k = (String) e.getKey();
                            String v = (String) e.getValue();
/* 193 */                   if (v != null && v.length() != 0) {
/* 196 */                       if (k.equals("compiler.includepath") || k.equals("compiler.include") || k.equals("compiler.linkpath") || k.equals("compiler.link") || k.equals("compiler.framework")) {
/* 199 */                           addAll(k, v.split(this.pathSeparator));
                                } else {
/* 201 */                           setProperty(k, v);
                                }
                            }
                        }
                    }

/* 208 */           public LinkedList<String> get(String key) {
/* 209 */               LinkedList<String> list = (LinkedList) super.get((Object) key);
/* 210 */               if (list == null) {
/* 211 */                   LinkedList<String> list2 = new LinkedList<>();
                            put(key, list2);
/* 213 */                   return list2;
                        }
/* 213 */               return list;
                    }

/* 216 */           public void addAll(String key, String... values) {
/* 217 */               if (values != null) {
/* 218 */                   addAll(key, Arrays.asList(values));
                        }
                    }

/* 221 */           public void addAll(String key, Collection<String> values) {
/* 222 */               if (values != null) {
/* 223 */                   String root = null;
/* 224 */                   if (key.equals("compiler.path") || key.equals("compiler.sysroot") || key.equals("compiler.includepath") || key.equals("compiler.linkpath")) {
/* 226 */                       root = this.platformRoot;
                            }
/* 229 */                   LinkedList<String> values2 = get(key);
                            for (String value : values) {
/* 231 */                       if (value != null && !values2.contains(value)) {
/* 232 */                           if (root != null && !new File(value).isAbsolute() && new File(root + value).exists()) {
/* 234 */                               value = root + value;
                                    }
/* 236 */                           values2.add(value);
                                }
                            }
                        }
                    }

/* 242 */           public String getProperty(String key) {
/* 243 */               return getProperty(key, null);
                    }

/* 245 */           public String getProperty(String key, String defaultValue) {
/* 246 */               LinkedList<String> values = get(key);
/* 247 */               if (values.isEmpty()) {
                            return defaultValue;
                        }
                        String defaultValue2 = values.get(0);
                        return defaultValue2;
                    }

/* 249 */           public String setProperty(String key, String value) {
/* 250 */               LinkedList<String> values = get(key);
/* 251 */               String oldValue = values.isEmpty() ? null : values.get(0);
/* 252 */               values.clear();
/* 253 */               addAll(key, value);
/* 254 */               return oldValue;
                    }

/* 257 */           public void load(Class cls, boolean inherit) {
                        Platform[] platforms;
/* 258 */               Class<?> c = Loader.getEnclosingClass(cls);
/* 260 */               while (!c.isAnnotationPresent(com.googlecode.javacpp.annotation.Properties.class) && !c.isAnnotationPresent(Platform.class) && c.getSuperclass() != null) {
/* 261 */                   c = c.getSuperclass();
                        }
/* 263 */               com.googlecode.javacpp.annotation.Properties classProperties = (com.googlecode.javacpp.annotation.Properties) c.getAnnotation(com.googlecode.javacpp.annotation.Properties.class);
/* 266 */               if (classProperties == null) {
/* 267 */                   Platform platform = (Platform) c.getAnnotation(Platform.class);
/* 268 */                   if (platform != null) {
/* 271 */                       platforms = new Platform[]{platform};
                            } else {
/* 329 */                       return;
                            }
                        } else {
/* 274 */                   Class[] classes = classProperties.inherit();
/* 275 */                   if (inherit && classes != null) {
                                for (Class c2 : classes) {
/* 277 */                           load(c2, inherit);
                                }
                            }
/* 280 */                   String target = classProperties.target();
/* 281 */                   if (target.length() > 0) {
/* 282 */                       addAll("parser.target", target);
                            }
/* 284 */                   platforms = classProperties.value();
/* 285 */                   if (platforms == null) {
/* 329 */                       return;
                            }
                        }
/* 290 */               String[] define = new String[0];
                        String[] include = new String[0];
                        String[] cinclude = new String[0];
                        String[] includepath = new String[0];
                        String[] options = new String[0];
/* 291 */               String[] linkpath = new String[0];
                        String[] link = new String[0];
                        String[] framework = new String[0];
                        String[] preloadpath = new String[0];
                        String[] preload = new String[0];
/* 292 */               String library = "jni" + c.getSimpleName();
/* 293 */               Platform[] arr$ = platforms;
                        int len$ = arr$.length;
                        int i$ = 0;
                        while (true) {
                            int i$2 = i$;
                            if (i$2 < len$) {
                                Platform p = arr$[i$2];
/* 294 */                       String[][] names = {p.value(), p.not()};
/* 295 */                       boolean[] matches = {false, false};
                                for (int i = 0; i < names.length; i++) {
/* 297 */                           String[] arr$2 = names[i];
                                    for (String s : arr$2) {
/* 298 */                               if (this.platformName.startsWith(s)) {
/* 299 */                                   matches[i] = true;
                                            break;
                                        }
                                    }
                                }
/* 304 */                       if ((names[0].length == 0 || matches[0]) && (names[1].length == 0 || !matches[1])) {
/* 305 */                           if (p.define().length > 0) {
                                        define = p.define();
                                    }
/* 306 */                           if (p.include().length > 0) {
                                        include = p.include();
                                    }
/* 307 */                           if (p.cinclude().length > 0) {
                                        cinclude = p.cinclude();
                                    }
/* 308 */                           if (p.includepath().length > 0) {
                                        includepath = p.includepath();
                                    }
/* 309 */                           if (p.options().length > 0) {
                                        options = p.options();
                                    }
/* 310 */                           if (p.linkpath().length > 0) {
                                        linkpath = p.linkpath();
                                    }
/* 311 */                           if (p.link().length > 0) {
                                        link = p.link();
                                    }
/* 312 */                           if (p.framework().length > 0) {
                                        framework = p.framework();
                                    }
/* 313 */                           if (p.preloadpath().length > 0) {
                                        preloadpath = p.preloadpath();
                                    }
/* 314 */                           if (p.preload().length > 0) {
                                        preload = p.preload();
                                    }
/* 315 */                           if (p.library().length() > 0) {
                                        library = p.library();
                                    }
                                }
/* 293 */                       i$ = i$2 + 1;
                            } else {
/* 318 */                       addAll("generator.define", define);
/* 319 */                       addAll("generator.include", include);
/* 320 */                       addAll("generator.cinclude", cinclude);
/* 321 */                       addAll("compiler.includepath", includepath);
/* 322 */                       addAll("compiler.options", options);
/* 323 */                       addAll("compiler.linkpath", linkpath);
/* 324 */                       addAll("compiler.link", link);
/* 325 */                       addAll("compiler.framework", framework);
/* 326 */                       addAll("loader.preloadpath", preloadpath);
/* 327 */                       addAll("loader.preload", preload);
/* 328 */                       setProperty("loader.library", library);
/* 329 */                       return;
                            }
                        }
                    }

/* 331 */           LinkedList<File> getHeaderFiles() {
/* 332 */               LinkedList<String> paths = get("compiler.includepath");
/* 333 */               LinkedList<String> includes = new LinkedList<>();
/* 334 */               includes.addAll(get("generator.include"));
/* 335 */               includes.addAll(get("generator.cinclude"));
/* 336 */               LinkedList<File> files = new LinkedList<>();
                        for (String include : includes) {
/* 338 */                   if (include.startsWith("<") && include.endsWith(">")) {
/* 339 */                       include = include.substring(1, include.length() - 1);
                            } else {
/* 341 */                       File f = new File(include);
/* 342 */                       if (f.exists()) {
/* 343 */                           files.add(f);
                                }
                            }
                            for (String path : paths) {
/* 348 */                       File f2 = new File(path, include);
/* 349 */                       if (f2.exists()) {
/* 350 */                           files.add(f2);
                                    break;
                                }
                            }
                        }
/* 355 */               return files;
                    }
                }

/* 363 */       public static ClassProperties loadProperties(Class[] cls, Properties properties, boolean inherit) {
/* 364 */           ClassProperties cp = new ClassProperties(properties);
                    for (Class c : cls) {
/* 366 */               cp.load(c, inherit);
                    }
/* 368 */           return cp;
                }

/* 382 */       public static ClassProperties loadProperties(Class cls, Properties properties, boolean inherit) {
/* 383 */           ClassProperties cp = new ClassProperties(properties);
/* 384 */           cp.load(cls, inherit);
/* 385 */           return cp;
                }

                /* JADX INFO: renamed from: com.googlecode.javacpp.Loader$1 */
/* 393 */       static class C12221 extends SecurityManager {
/* 394 */           C12221() {
                    }

                    @Override // java.lang.SecurityManager
/* 396 */           public Class[] getClassContext() {
/* 397 */               return super.getClassContext();
                    }
                }

/* 394 */       public static Class getCallerClass(int i) {
/* 395 */           Class[] classContext = new C12221().getClassContext();
/* 400 */           if (classContext != null) {
                        for (int j = 0; j < classContext.length; j++) {
/* 402 */                   if (classContext[j] == Loader.class) {
/* 403 */                       return classContext[i + j];
                            }
                        }
                    } else {
                        try {
/* 409 */                   StackTraceElement[] classNames = Thread.currentThread().getStackTrace();
                            for (int j2 = 0; j2 < classNames.length; j2++) {
/* 411 */                       if (Class.forName(classNames[j2].getClassName()) == Loader.class) {
/* 412 */                           return Class.forName(classNames[i + j2].getClassName());
                                }
                            }
                        } catch (ClassNotFoundException e) {
                        }
                    }
/* 417 */           return null;
                }

/* 427 */       public static File extractResource(String name, File directory, String prefix, String suffix) throws IOException {
/* 428 */           Class cls = getCallerClass(2);
/* 429 */           return extractResource(cls, name, directory, prefix, suffix);
                }

/* 439 */       public static File extractResource(Class cls, String name, File directory, String prefix, String suffix) throws IOException {
/* 440 */           return extractResource(cls.getResource(name), directory, prefix, suffix);
                }

/* 455 */       public static File extractResource(URL resourceURL, File directory, String prefix, String suffix) throws IOException {
                    File file = null;
/* 456 */           InputStream is = resourceURL != null ? resourceURL.openStream() : null;
/* 457 */           if (is != null) {
/* 460 */               file = null;
/* 461 */               boolean fileExisted = false;
                        try {
/* 463 */                   if (prefix == null && suffix == null) {
/* 464 */                       if (directory == null) {
/* 465 */                           directory = new File(System.getProperty("java.io.tmpdir"));
                                }
/* 467 */                       File file2 = new File(directory, new File(resourceURL.getPath()).getName());
/* 468 */                       fileExisted = file2.exists();
                                file = file2;
                            } else {
/* 470 */                       file = File.createTempFile(prefix, suffix, directory);
                            }
/* 472 */                   FileOutputStream os = new FileOutputStream(file);
/* 473 */                   byte[] buffer = new byte[1024];
                            while (true) {
/* 475 */                       int length = is.read(buffer);
                                if (length == -1) {
                                    break;
                                }
/* 476 */                       os.write(buffer, 0, length);
/* 481 */                       if (file != null && !fileExisted) {
/* 482 */                           file.delete();
                                }
                            }
/* 478 */                   is.close();
/* 479 */                   os.close();
                        } catch (IOException e2) {
                        }
                    }
/* 486 */           return file;
                }

/* 503 */       public static File getTempDir() {
/* 504 */           if (tempDir == null) {
/* 505 */               File tmpdir = new File(System.getProperty("java.io.tmpdir"));
                        for (int i = 0; i < 1000; i++) {
/* 508 */                   File f = new File(tmpdir, "javacpp" + System.nanoTime());
/* 509 */                   if (f.mkdir()) {
/* 510 */                       tempDir = f;
/* 511 */                       tempDir.deleteOnExit();
                                break;
                            }
                        }
                    }
/* 516 */           return tempDir;
                }

/* 520 */       public static boolean isLoadLibraries() {
/* 521 */           return loadLibraries;
                }

/* 530 */       public static String load() {
/* 531 */           Class cls = getCallerClass(2);
/* 532 */           return load(cls);
                }

/* 543 */       public static String load(Class cls) {
/* 544 */           if (!loadLibraries || cls == null) {
/* 545 */               return null;
                    }
/* 549 */           Class cls2 = getEnclosingClass(cls);
                    try {
/* 553 */               Class<?> cls3 = Class.forName(cls2.getName(), true, cls2.getClassLoader());
/* 561 */               ClassProperties p = loadProperties((Class) cls3, loadProperties(), true);
/* 562 */               LinkedList<String> preloads = new LinkedList<>();
/* 563 */               preloads.addAll(p.get("loader.preload"));
/* 564 */               preloads.addAll(p.get("compiler.link"));
/* 565 */               UnsatisfiedLinkError preloadError = null;
                        for (String preload : preloads) {
                            try {
/* 568 */                       URL[] urls = findLibrary(cls3, p, preload);
/* 569 */                       loadLibrary(urls, preload);
                            } catch (UnsatisfiedLinkError e) {
/* 571 */                       preloadError = e;
                            }
                        }
                        try {
/* 576 */                   String library = p.getProperty("loader.library");
/* 577 */                   URL[] urls2 = findLibrary(cls3, p, library);
/* 578 */                   return loadLibrary(urls2, library);
                        } catch (UnsatisfiedLinkError e2) {
/* 580 */                   if (preloadError != null) {
/* 581 */                       e2.initCause(preloadError);
                            }
/* 583 */                   throw e2;
                        }
                    } catch (ClassNotFoundException ex) {
/* 555 */               Error e3 = new NoClassDefFoundError(ex.toString());
/* 556 */               e3.initCause(ex);
/* 557 */               throw e3;
                    }
                }

/* 597 */       public static URL[] findLibrary(Class cls, ClassProperties properties, String libnameversion) {
                    int k;
/* 598 */           String[] s = libnameversion.split("@");
/* 599 */           String libname = s[0];
/* 600 */           String version = s.length > 1 ? s[s.length - 1] : "";
/* 603 */           String filename = loadedLibraries.get(libnameversion);
/* 604 */           if (filename != null) {
                        try {
/* 606 */                   return new URL[]{new File(filename).toURI().toURL()};
                        } catch (IOException e) {
/* 608 */                   return new URL[0];
                        }
                    }
/* 612 */           String subdir = properties.getProperty("platform.name") + '/';
/* 613 */           String prefix = properties.getProperty("library.prefix", "") + libname;
/* 614 */           String suffix = properties.getProperty("library.suffix", "");
/* 615 */           String[] styles = {prefix + suffix + version, prefix + version + suffix, prefix + suffix};
/* 622 */           LinkedList<String> paths = new LinkedList<>();
/* 623 */           paths.addAll(properties.get("loader.preloadpath"));
/* 624 */           paths.addAll(properties.get("compiler.linkpath"));
/* 625 */           URL[] urls = new URL[styles.length * (paths.size() + 1)];
/* 626 */           int i = 0;
/* 621 */           int k2 = 0;
                    while (cls != null && i < styles.length) {
/* 628 */               URL u = cls.getResource(subdir + styles[i]);
/* 629 */               if (u != null) {
/* 630 */                   k = k2 + 1;
                            urls[k2] = u;
                        } else {
                            k = k2;
                        }
/* 626 */               i++;
                        k2 = k;
                    }
                    int k3 = k2;
                    for (int i2 = 0; paths.size() > 0 && i2 < styles.length; i2++) {
                        for (String path : paths) {
/* 636 */                   File file = new File(path, styles[i2]);
/* 637 */                   if (file.exists()) {
/* 639 */                       int k4 = k3 + 1;
                                try {
                                    urls[k3] = file.toURI().toURL();
                                    k3 = k4;
                                } catch (IOException ex) {
/* 641 */                           throw new RuntimeException(ex);
                                }
                            }
                        }
                    }
/* 646 */           return (URL[]) Arrays.copyOf(urls, k3);
                }

/* 659 */       public static String loadLibrary(URL[] urls, String libnameversion) {
                    File file;
/* 660 */           if (!loadLibraries) {
                        return null;
                    }
/* 665 */           String filename = loadedLibraries.get(libnameversion);
/* 666 */           if (filename != null) {
/* 719 */               return filename;
                    }
/* 670 */           File tempFile = null;
/* 671 */           UnsatisfiedLinkError loadError = null;
                    try {
                        try {
                            try {
                                for (URL url : urls) {
                                    try {
/* 677 */                               file = new File(url.toURI());
                                    } catch (Exception e) {
/* 679 */                               if (tempFile != null && tempFile.exists()) {
/* 680 */                                   tempFile.deleteOnExit();
                                        }
/* 683 */                               tempFile = extractResource(url, getTempDir(), (String) null, (String) null);
                                        file = tempFile;
                                    }
/* 685 */                           if (file != null && file.exists()) {
/* 686 */                               String filename2 = file.getAbsolutePath();
                                        try {
/* 689 */                                   loadedLibraries.put(libnameversion, filename2);
/* 690 */                                   System.load(filename2);
/* 718 */                                   if (tempFile == null || !tempFile.exists()) {
/* 719 */                                       return filename2;
                                            }
/* 719 */                                   tempFile.deleteOnExit();
/* 719 */                                   return filename2;
                                        } catch (UnsatisfiedLinkError e2) {
/* 693 */                                   loadError = e2;
/* 694 */                                   loadedLibraries.remove(libnameversion);
                                        }
                                    }
                                }
/* 699 */                       String libname = libnameversion.split("@")[0];
/* 700 */                       loadedLibraries.put(libnameversion, libname);
/* 701 */                       System.loadLibrary(libname);
/* 718 */                       if (tempFile != null && tempFile.exists()) {
/* 719 */                           tempFile.deleteOnExit();
                                }
                                return libname;
                            } catch (UnsatisfiedLinkError e3) {
/* 704 */                       loadedLibraries.remove(libnameversion);
/* 705 */                       if (0 != 0) {
/* 706 */                           e3.initCause(null);
                                }
/* 708 */                       throw e3;
                            }
                        } catch (IOException ex) {
/* 710 */                   loadedLibraries.remove(libnameversion);
/* 711 */                   if (loadError != null) {
/* 712 */                       ex.initCause(loadError);
                            }
/* 714 */                   Error e4 = new UnsatisfiedLinkError(ex.toString());
/* 715 */                   e4.initCause(ex);
/* 716 */                   throw e4;
                        }
                    } catch (Throwable th) {
                        if (tempFile != null && tempFile.exists()) {
/* 719 */                   tempFile.deleteOnExit();
                        }
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: com.googlecode.javacpp.Loader$2 */
/* 726 */       static class C12232 extends Thread {
/* 727 */           C12232() {
                    }

                    @Override // java.lang.Thread, java.lang.Runnable
/* 729 */           public void run() {
/* 730 */               if (Loader.tempDir != null) {
                            try {
/* 735 */                       LinkedList<String> command = new LinkedList<>();
/* 736 */                       command.add(System.getProperty("java.home") + "/bin/java");
/* 737 */                       command.add("-classpath");
/* 738 */                       command.add(System.getProperty("java.class.path"));
/* 739 */                       command.add(Loader.class.getName());
/* 740 */                       command.add(Loader.tempDir.getAbsolutePath());
/* 741 */                       new ProcessBuilder(command).start();
                            } catch (IOException e) {
/* 743 */                       throw new RuntimeException(e);
                            }
                        }
                    }
                }

/* 751 */       public static void main(String[] args) {
/* 752 */           File tmpdir = new File(System.getProperty("java.io.tmpdir"));
/* 753 */           File tempDir2 = new File(args[0]);
/* 754 */           if (tmpdir.equals(tempDir2.getParentFile()) && tempDir2.getName().startsWith("javacpp")) {
/* 759 */               File[] arr$ = tempDir2.listFiles();
                        for (File file : arr$) {
/* 760 */                   while (file.exists() && !file.delete()) {
                                try {
/* 762 */                           Thread.sleep(100L);
                                } catch (InterruptedException e) {
                                }
                            }
                        }
/* 766 */               tempDir2.delete();
                    }
                }

/* 788 */       static void putMemberOffset(String typeName, String member, int offset) throws ClassNotFoundException {
/* 789 */           Class<?> c = Class.forName(typeName.replace('/', FilenameUtils.EXTENSION_SEPARATOR), false, Loader.class.getClassLoader());
/* 790 */           putMemberOffset((Class<? extends Pointer>) c.asSubclass(Pointer.class), member, offset);
                }

/* 799 */       static synchronized void putMemberOffset(Class<? extends Pointer> type, String member, int offset) {
                    try {
                        HashMap<String, Integer> offsets = memberOffsets.get(type);
/* 801 */               if (offsets == null) {
/* 802 */                   WeakHashMap<Class<? extends Pointer>, HashMap<String, Integer>> weakHashMap = memberOffsets;
                            offsets = new HashMap<>();
                            weakHashMap.put(type, offsets);
                        }
/* 804 */               offsets.put(member, Integer.valueOf(offset));
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 815 */       public static int offsetof(Class<? extends Pointer> type, String member) {
/* 816 */           return memberOffsets.get(type).get(member).intValue();
                }

/* 826 */       public static int sizeof(Class<? extends Pointer> type) {
/* 827 */           return memberOffsets.get(type).get("sizeof").intValue();
                }
            }
