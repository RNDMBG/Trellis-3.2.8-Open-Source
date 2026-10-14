            package com.googlecode.javacpp;

            import android.support.v4.os.EnvironmentCompat;
            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import java.io.File;
            import java.io.FileInputStream;
            import java.io.FileOutputStream;
            import java.io.FilenameFilter;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.InputStreamReader;
            import java.io.OutputStream;
            import java.net.MalformedURLException;
            import java.net.URISyntaxException;
            import java.net.URL;
            import java.net.URLClassLoader;
            import java.util.Arrays;
            import java.util.Collection;
            import java.util.Iterator;
            import java.util.LinkedHashMap;
            import java.util.LinkedList;
            import java.util.Map;
            import java.util.Properties;
            import java.util.jar.JarInputStream;
            import java.util.jar.JarOutputStream;
            import java.util.zip.ZipEntry;
            import org.apache.commons.io.FilenameUtils;
            import org.apache.commons.io.IOUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 64 */    public class Builder {
                ClassScanner classScanner;
                Collection<String> compilerOptions;
                Properties properties;
                File outputDirectory = null;
                String outputName = null;
                String jarPrefix = null;
                boolean compile = true;
                boolean header = false;
                boolean copyLibs = false;
                Map<String, String> environmentVariables = null;

/* 65 */        public File parse(Class cls) throws Parser.Exception, IOException {
/* 66 */            Parser.InfoMap infoMap = new Parser.InfoMap();
                    try {
/* 68 */                Object obj = cls.newInstance();
/* 69 */                if (obj instanceof Parser.InfoMapper) {
/* 70 */                    ((Parser.InfoMapper) obj).map(infoMap);
                        }
                    } catch (IllegalAccessException e) {
                    } catch (InstantiationException e2) {
                    }
/* 76 */            return new Parser(this.properties, infoMap).parse(this.outputDirectory, cls);
                }

/* 86 */        public static void includeJavaPaths(Loader.ClassProperties properties, boolean header) {
/* 87 */            String platformName = Loader.getPlatformName();
/* 88 */            String jvmlink = properties.getProperty("compiler.link.prefix", "") + "jvm" + properties.getProperty("compiler.link.suffix", "");
/* 90 */            String jvmlib = properties.getProperty("library.prefix", "") + "jvm" + properties.getProperty("library.suffix", "");
/* 92 */            String[] jnipath = new String[2];
/* 93 */            String[] jvmpath = new String[2];
/* 94 */            FilenameFilter filter = new C12201(jnipath, jvmlink, jvmpath, jvmlib);
/* 111 */           File javaHome = new File(System.getProperty("java.home")).getParentFile();
                    try {
/* 113 */               javaHome = javaHome.getCanonicalFile();
                    } catch (IOException e) {
                    }
/* 115 */           LinkedList<File> dirs = new LinkedList<>(Arrays.asList(javaHome.listFiles(filter)));
/* 116 */           while (!dirs.isEmpty()) {
/* 117 */               File d = dirs.pop();
/* 118 */               String dpath = d.getPath();
/* 119 */               File[] arr$ = d.listFiles(filter);
                        int len$ = arr$.length;
                        for (int i$ = 0; i$ < len$; i$++) {
                            File f = arr$[i$];
                            try {
/* 121 */                       f = f.getCanonicalFile();
                            } catch (IOException e2) {
                            }
/* 123 */                   if (!dpath.startsWith(f.getPath())) {
/* 124 */                       dirs.add(f);
                            }
                        }
                    }
/* 128 */           if (jnipath[0] != null && jnipath[0].equals(jnipath[1])) {
/* 129 */               jnipath[1] = null;
                    } else if (jnipath[0] == null && new File("/System/Library/Frameworks/JavaVM.framework/Headers/").isDirectory()) {
/* 131 */               jnipath[0] = "/System/Library/Frameworks/JavaVM.framework/Headers/";
                    }
/* 136 */           if (jvmpath[0] != null && jvmpath[0].equals(jvmpath[1])) {
/* 137 */               jvmpath[1] = null;
                    }
/* 139 */           properties.addAll("compiler.includepath", jnipath);
/* 140 */           if (platformName.equals(properties.getProperty("platform.name", platformName))) {
/* 141 */               if (header) {
/* 143 */                   properties.get("compiler.link").add(0, "jvm");
/* 144 */                   properties.addAll("compiler.linkpath", jvmpath);
                        }
/* 146 */               if (platformName.startsWith("macosx")) {
/* 147 */                   properties.addAll("compiler.framework", "JavaVM");
                        }
                    }
                }

                /* JADX INFO: renamed from: com.googlecode.javacpp.Builder$1 */
/* 92 */        static class C12201 implements FilenameFilter {
                    final /* synthetic */ String[] val$jnipath;
                    final /* synthetic */ String val$jvmlib;
                    final /* synthetic */ String val$jvmlink;
                    final /* synthetic */ String[] val$jvmpath;

/* 93 */            C12201(String[] strArr, String str, String[] strArr2, String str2) {
/* 94 */                this.val$jnipath = strArr;
                        this.val$jvmlink = str;
                        this.val$jvmpath = strArr2;
                        this.val$jvmlib = str2;
                    }

                    @Override // java.io.FilenameFilter
/* 95 */            public boolean accept(File dir, String name) {
/* 96 */                if (new File(dir, "jni.h").exists()) {
/* 97 */                    this.val$jnipath[0] = dir.getAbsolutePath();
                        }
/* 99 */                if (new File(dir, "jni_md.h").exists()) {
/* 100 */                   this.val$jnipath[1] = dir.getAbsolutePath();
                        }
/* 102 */               if (new File(dir, this.val$jvmlink).exists()) {
/* 103 */                   this.val$jvmpath[0] = dir.getAbsolutePath();
                        }
/* 105 */               if (new File(dir, this.val$jvmlib).exists()) {
/* 106 */                   this.val$jvmpath[1] = dir.getAbsolutePath();
                        }
/* 108 */               return new File(dir, name).isDirectory();
                    }
                }

/* 156 */       public static class Piper extends Thread {

                    /* JADX INFO: renamed from: is */
                    InputStream f2529is;

                    /* JADX INFO: renamed from: os */
                    OutputStream f2530os;

/* 157 */           public Piper(InputStream is, OutputStream os) {
/* 159 */               this.f2529is = is;
/* 160 */               this.f2530os = os;
                    }

                    @Override // java.lang.Thread, java.lang.Runnable
/* 167 */           public void run() {
                        try {
/* 168 */                   byte[] buffer = new byte[1024];
                            while (true) {
/* 170 */                       int length = this.f2529is.read(buffer);
                                if (length != -1) {
/* 171 */                           this.f2530os.write(buffer, 0, length);
                                } else {
/* 176 */                           return;
                                }
                            }
                        } catch (IOException e) {
/* 174 */                   System.err.println("Could not pipe from the InputStream to the OutputStream: " + e.getMessage());
                        }
                    }
                }

/* 190 */       public int compile(String sourceFilename, String outputFilename, Loader.ClassProperties properties) throws InterruptedException, IOException {
                    String s;
/* 191 */           LinkedList<String> command = new LinkedList<>();
/* 193 */           includeJavaPaths(properties, this.header);
/* 195 */           String platformName = Loader.getPlatformName();
/* 196 */           String compilerPath = properties.getProperty("compiler.path");
/* 197 */           command.add(compilerPath);
/* 200 */           String p = properties.getProperty("compiler.sysroot.prefix", "");
                    for (String s2 : properties.get("compiler.sysroot")) {
/* 202 */               if (new File(s2).isDirectory()) {
/* 203 */                   if (p.endsWith(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
/* 204 */                       command.add(p.trim());
                                command.add(s2);
                            } else {
/* 206 */                       command.add(p + s2);
                            }
                        }
                    }
/* 213 */           String p2 = properties.getProperty("compiler.includepath.prefix", "");
                    for (String s3 : properties.get("compiler.includepath")) {
/* 215 */               if (new File(s3).isDirectory()) {
/* 216 */                   if (p2.endsWith(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
/* 217 */                       command.add(p2.trim());
                                command.add(s3);
                            } else {
/* 219 */                       command.add(p2 + s3);
                            }
                        }
                    }
/* 225 */           command.add(sourceFilename);
/* 227 */           Collection<String> allOptions = properties.get("compiler.options");
/* 228 */           if (allOptions.isEmpty()) {
/* 229 */               allOptions.add("default");
                    }
                    for (String s4 : allOptions) {
/* 232 */               if (s4 != null && s4.length() != 0) {
/* 235 */                   String p3 = "compiler.options." + s4;
/* 236 */                   String options = properties.getProperty(p3);
/* 237 */                   if (options != null && options.length() > 0) {
/* 238 */                       command.addAll(Arrays.asList(options.split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)));
                            } else if (!"default".equals(s4)) {
/* 240 */                       System.err.println("Warning: Could not get the property named \"" + p3 + "\"");
                            }
                        }
                    }
/* 244 */           command.addAll(this.compilerOptions);
/* 246 */           String outputPrefix = properties.getProperty("compiler.output.prefix");
/* 247 */           if (outputPrefix != null && outputPrefix.length() > 0) {
/* 248 */               command.addAll(Arrays.asList(outputPrefix.split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)));
                    }
/* 251 */           if (outputPrefix == null || outputPrefix.length() == 0 || outputPrefix.endsWith(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
/* 252 */               command.add(outputFilename);
                    } else {
/* 254 */               command.add(command.removeLast() + outputFilename);
                    }
/* 258 */           String p4 = properties.getProperty("compiler.linkpath.prefix", "");
/* 259 */           String p5 = properties.getProperty("compiler.linkpath.prefix2");
                    for (String s5 : properties.get("compiler.linkpath")) {
/* 261 */               if (new File(s5).isDirectory()) {
/* 262 */                   if (p4.endsWith(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
/* 263 */                       command.add(p4.trim());
                                command.add(s5);
                            } else {
/* 265 */                       command.add(p4 + s5);
                            }
/* 267 */                   if (p5 != null) {
/* 268 */                       if (p5.endsWith(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
/* 269 */                           command.add(p5.trim());
                                    command.add(s5);
                                } else {
/* 271 */                           command.add(p5 + s5);
                                }
                            }
                        }
                    }
/* 279 */           String p6 = properties.getProperty("compiler.link.prefix", "");
/* 280 */           String x = properties.getProperty("compiler.link.suffix", "");
/* 281 */           int i = command.size();
/* 282 */           Iterator<String> it = properties.get("compiler.link").iterator();
                    while (it.hasNext()) {
/* 283 */               String[] libnameversion = it.next().split("@");
/* 284 */               if (libnameversion.length == 3 && libnameversion[1].length() == 0) {
/* 286 */                   s = libnameversion[0] + libnameversion[2];
                        } else {
/* 288 */                   s = libnameversion[0];
                        }
/* 290 */               if (p6.endsWith(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR) && x.startsWith(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
/* 291 */                   command.add(i, p6.trim());
                            command.add(i + 1, s);
                            command.add(i + 2, x.trim());
                        } else if (p6.endsWith(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
/* 293 */                   command.add(i, p6.trim());
                            command.add(i + 1, s + x);
                        } else if (x.startsWith(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
/* 295 */                   command.add(i, p6 + s);
                            command.add(i + 1, x.trim());
                        } else {
/* 297 */                   command.add(i, p6 + s + x);
                        }
                    }
/* 303 */           String p7 = properties.getProperty("compiler.framework.prefix", "");
/* 304 */           String x2 = properties.getProperty("compiler.framework.suffix", "");
                    for (String s6 : properties.get("compiler.framework")) {
/* 306 */               if (p7.endsWith(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR) && x2.startsWith(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
/* 307 */                   command.add(p7.trim());
                            command.add(s6);
                            command.add(x2.trim());
                        } else if (p7.endsWith(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
/* 309 */                   command.add(p7.trim());
                            command.add(s6 + x2);
                        } else if (x2.startsWith(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
/* 311 */                   command.add(p7 + s6);
                            command.add(x2.trim());
                        } else {
/* 313 */                   command.add(p7 + s6 + x2);
                        }
                    }
/* 318 */           boolean windows = platformName.startsWith("windows");
                    for (String s7 : command) {
/* 320 */               boolean hasSpaces = s7.indexOf(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR) > 0;
/* 321 */               if (hasSpaces) {
/* 322 */                   System.out.print(windows ? "\"" : "'");
                        }
/* 324 */               System.out.print(s7);
/* 325 */               if (hasSpaces) {
/* 326 */                   System.out.print(windows ? "\"" : "'");
                        }
/* 328 */               System.out.print(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                    }
/* 330 */           System.out.println();
/* 332 */           ProcessBuilder pb = new ProcessBuilder(command);
/* 333 */           if (this.environmentVariables != null) {
/* 334 */               pb.environment().putAll(this.environmentVariables);
                    }
/* 336 */           Process p8 = pb.start();
/* 337 */           new Piper(p8.getErrorStream(), System.err).start();
/* 338 */           new Piper(p8.getInputStream(), System.out).start();
/* 339 */           return p8.waitFor();
                }

/* 352 */       public File generateAndCompile(Class[] classes, String outputName) throws InterruptedException, IOException {
                    File outputPath;
                    String sourcePrefix;
/* 354 */           Loader.ClassProperties p = Loader.loadProperties(classes, this.properties, true);
/* 355 */           String platformName = p.getProperty("platform.name");
/* 356 */           String sourceSuffix = p.getProperty("source.suffix", ".cpp");
/* 357 */           String libraryName = p.getProperty("library.prefix", "") + outputName + p.getProperty("library.suffix", "");
/* 359 */           if (this.outputDirectory == null) {
                        try {
/* 361 */                   URL resourceURL = classes[0].getResource('/' + classes[0].getName().replace(FilenameUtils.EXTENSION_SEPARATOR, '/') + ".class");
/* 362 */                   File packageDir = new File(resourceURL.toURI()).getParentFile();
/* 363 */                   outputPath = new File(packageDir, platformName);
/* 364 */                   sourcePrefix = packageDir.getPath() + File.separator + outputName;
                        } catch (URISyntaxException e) {
/* 366 */                   throw new RuntimeException(e);
                        }
                    } else {
/* 369 */               outputPath = this.outputDirectory;
/* 370 */               sourcePrefix = outputPath.getPath() + File.separator + outputName;
                    }
/* 372 */           if (!outputPath.exists()) {
/* 373 */               outputPath.mkdirs();
                    }
/* 375 */           Generator generator = new Generator(p);
/* 376 */           String sourceFilename = sourcePrefix + sourceSuffix;
/* 377 */           String headerFilename = this.header ? sourcePrefix + ".h" : null;
/* 378 */           String classPath = System.getProperty("java.class.path");
/* 379 */           String[] arr$ = this.classScanner.getClassLoader().getPaths();
                    for (String s : arr$) {
/* 380 */               classPath = classPath + File.pathSeparator + s;
                    }
/* 382 */           System.out.println("Generating source file: " + sourceFilename);
/* 383 */           if (generator.generate(sourceFilename, headerFilename, classPath, classes)) {
/* 384 */               generator.close();
/* 385 */               if (this.compile) {
/* 386 */                   String libraryFilename = outputPath.getPath() + File.separator + libraryName;
/* 387 */                   System.out.println("Compiling library file: " + libraryFilename);
/* 388 */                   int exitValue = compile(sourceFilename, libraryFilename, p);
/* 389 */                   if (exitValue == 0) {
/* 390 */                       new File(sourceFilename).delete();
/* 391 */                       File outputFile = new File(libraryFilename);
/* 401 */                       return outputFile;
                            }
/* 393 */                   System.exit(exitValue);
/* 353 */                   return null;
                        }
/* 396 */               File outputFile2 = new File(sourceFilename);
/* 401 */               return outputFile2;
                    }
/* 399 */           System.out.println("Source file not generated: " + sourceFilename);
/* 353 */           return null;
                }

/* 413 */       public static void createJar(File jarFile, String[] classpath, File... files) throws IOException {
/* 414 */           System.out.println("Creating JAR file: " + jarFile);
/* 415 */           JarOutputStream jos = new JarOutputStream(new FileOutputStream(jarFile));
                    for (File f : files) {
/* 417 */               String name = f.getPath();
/* 418 */               if (classpath != null) {
/* 422 */                   String[] names = new String[classpath.length];
                            for (int i = 0; i < classpath.length; i++) {
/* 424 */                       String path = new File(classpath[i]).getCanonicalPath();
/* 425 */                       if (name.startsWith(path)) {
/* 426 */                           names[i] = name.substring(path.length() + 1);
                                }
                            }
                            for (int i2 = 0; i2 < names.length; i2++) {
/* 431 */                       if (names[i2] != null && names[i2].length() < name.length()) {
/* 432 */                           name = names[i2];
                                }
                            }
                        }
/* 436 */               ZipEntry e = new ZipEntry(name.replace(File.separatorChar, '/'));
/* 437 */               e.setTime(f.lastModified());
/* 438 */               jos.putNextEntry(e);
/* 439 */               FileInputStream fis = new FileInputStream(f);
/* 440 */               byte[] buffer = new byte[1024];
                        while (true) {
/* 442 */                   int length = fis.read(buffer);
                            if (length != -1) {
/* 443 */                       jos.write(buffer, 0, length);
                            } else {
                                break;
                            }
                        }
/* 445 */               fis.close();
/* 446 */               jos.closeEntry();
                    }
/* 450 */           jos.close();
                }

/* 458 */       public static class UserClassLoader extends URLClassLoader {
                    private LinkedList<String> paths;

/* 459 */           public UserClassLoader() {
/* 460 */               super(new URL[0]);
/* 458 */               this.paths = new LinkedList<>();
                    }

/* 462 */           public UserClassLoader(ClassLoader parent) {
/* 463 */               super(new URL[0], parent);
/* 458 */               this.paths = new LinkedList<>();
                    }

/* 465 */           public void addPaths(String... paths) {
/* 466 */               if (paths != null) {
                            for (String path : paths) {
/* 470 */                       File f = new File(path);
/* 471 */                       if (f.exists()) {
/* 474 */                           this.paths.add(path);
                                    try {
/* 476 */                               addURL(f.toURI().toURL());
                                    } catch (MalformedURLException e) {
/* 478 */                               throw new RuntimeException(e);
                                    }
                                }
                            }
                        }
                    }

/* 482 */           public String[] getPaths() {
/* 483 */               if (this.paths.isEmpty()) {
/* 484 */                   addPaths(System.getProperty("user.dir"));
                        }
/* 486 */               return (String[]) this.paths.toArray(new String[this.paths.size()]);
                    }

                    @Override // java.net.URLClassLoader, java.lang.ClassLoader
/* 489 */           protected Class<?> findClass(String name) throws ClassNotFoundException {
/* 490 */               if (this.paths.isEmpty()) {
/* 491 */                   addPaths(System.getProperty("user.dir"));
                        }
/* 493 */               return super.findClass(name);
                    }
                }

/* 500 */       public static class ClassScanner {
                    private Collection<Class> classes;
                    private UserClassLoader loader;

/* 501 */           public ClassScanner(Collection<Class> classes, UserClassLoader loader) {
/* 503 */               this.classes = classes;
/* 504 */               this.loader = loader;
                    }

/* 510 */           public Collection<Class> getClasses() {
/* 511 */               return this.classes;
                    }

/* 513 */           public UserClassLoader getClassLoader() {
/* 514 */               return this.loader;
                    }

/* 517 */           public void addClass(String className) {
/* 518 */               if (className != null) {
/* 520 */                   if (className.endsWith(".class")) {
/* 521 */                       className = className.substring(0, className.length() - 6);
                            }
                            try {
/* 524 */                       Class<?> cls = Class.forName(className, false, this.loader);
/* 525 */                       if (!this.classes.contains(cls)) {
/* 526 */                           this.classes.add(cls);
                                }
                            } catch (ClassNotFoundException e) {
/* 529 */                       System.err.println("Warning: Could not find class " + className + ": " + e);
                            } catch (NoClassDefFoundError e2) {
/* 531 */                       System.err.println("Warning: Could not load class " + className + ": " + e2);
                            }
                        }
                    }

/* 535 */           public void addMatchingFile(String filename, String packagePath, boolean recursive) {
/* 536 */               if (filename == null || !filename.endsWith(".class")) {
/* 541 */                   return;
                        }
                        if (packagePath == null || ((recursive && filename.startsWith(packagePath)) || filename.regionMatches(0, packagePath, 0, Math.max(filename.lastIndexOf(47), packagePath.lastIndexOf(47))))) {
/* 539 */                   addClass(filename.replace('/', FilenameUtils.EXTENSION_SEPARATOR));
                        }
                    }

/* 543 */           public void addMatchingDir(String parentName, File dir, String packagePath, boolean recursive) {
/* 544 */               File[] files = dir.listFiles();
/* 545 */               Arrays.sort(files);
                        for (File f : files) {
/* 547 */                   String pathName = parentName == null ? f.getName() : parentName + f.getName();
/* 548 */                   if (f.isDirectory()) {
/* 549 */                       addMatchingDir(pathName + "/", f, packagePath, recursive);
                            } else {
/* 551 */                       addMatchingFile(pathName, packagePath, recursive);
                            }
                        }
                    }

/* 556 */           public void addPackage(String packageName, boolean recursive) throws IOException {
/* 557 */               String[] paths = this.loader.getPaths();
/* 558 */               String packagePath = packageName == null ? null : packageName.replace(FilenameUtils.EXTENSION_SEPARATOR, '/') + "/";
/* 559 */               int prevSize = this.classes.size();
                        for (String p : paths) {
/* 561 */                   File file = new File(p);
/* 562 */                   if (file.isDirectory()) {
/* 563 */                       addMatchingDir(null, file, packagePath, recursive);
                            } else {
/* 565 */                       JarInputStream jis = new JarInputStream(new FileInputStream(file));
/* 567 */                       for (ZipEntry e = jis.getNextEntry(); e != null; e = jis.getNextEntry()) {
/* 568 */                           addMatchingFile(e.getName(), packagePath, recursive);
/* 569 */                           jis.closeEntry();
                                }
/* 572 */                       jis.close();
                            }
                        }
/* 575 */               if (this.classes.size() == 0 && packageName == null) {
/* 576 */                   System.err.println("Warning: No classes found in the unnamed package");
/* 577 */                   Builder.printHelp();
                        } else if (prevSize == this.classes.size() && packageName != null) {
/* 579 */                   System.err.println("Warning: No classes found in package " + packageName);
                        }
                    }

/* 583 */           public void addClassOrPackage(String name) throws IOException {
/* 584 */               if (name != null) {
/* 587 */                   String name2 = name.replace('/', FilenameUtils.EXTENSION_SEPARATOR);
/* 588 */                   if (name2.endsWith(".**")) {
/* 589 */                       addPackage(name2.substring(0, name2.length() - 3), true);
                            } else if (name2.endsWith(".*")) {
/* 591 */                       addPackage(name2.substring(0, name2.length() - 2), false);
                            } else {
/* 593 */                       addClass(name2);
                            }
                        }
                    }
                }

/* 600 */       public Builder() {
/* 624 */           this.properties = null;
/* 626 */           this.classScanner = null;
/* 630 */           this.compilerOptions = null;
/* 602 */           Loader.loadLibraries = false;
/* 603 */           this.properties = Loader.loadProperties();
/* 604 */           this.classScanner = new ClassScanner(new LinkedList(), new UserClassLoader(Thread.currentThread().getContextClassLoader()));
/* 606 */           this.compilerOptions = new LinkedList();
                }

/* 633 */       public Builder classPaths(String classPaths) {
/* 634 */           classPaths(classPaths == null ? null : classPaths.split(File.pathSeparator));
/* 635 */           return this;
                }

/* 638 */       public Builder classPaths(String... classPaths) {
/* 639 */           this.classScanner.getClassLoader().addPaths(classPaths);
/* 640 */           return this;
                }

/* 643 */       public Builder outputDirectory(String outputDirectory) {
/* 644 */           outputDirectory(outputDirectory == null ? null : new File(outputDirectory));
/* 645 */           return this;
                }

/* 648 */       public Builder outputDirectory(File outputDirectory) {
/* 649 */           this.outputDirectory = outputDirectory;
/* 650 */           return this;
                }

/* 653 */       public Builder compile(boolean compile) {
/* 654 */           this.compile = compile;
/* 655 */           return this;
                }

/* 658 */       public Builder header(boolean header) {
/* 659 */           this.header = header;
/* 660 */           return this;
                }

/* 663 */       public Builder copyLibs(boolean copyLibs) {
/* 664 */           this.copyLibs = copyLibs;
/* 665 */           return this;
                }

/* 668 */       public Builder outputName(String outputName) {
/* 669 */           this.outputName = outputName;
/* 670 */           return this;
                }

/* 673 */       public Builder jarPrefix(String jarPrefix) {
/* 674 */           this.jarPrefix = jarPrefix;
/* 675 */           return this;
                }

/* 678 */       public Builder properties(String platformName) {
/* 679 */           properties(platformName == null ? null : Loader.loadProperties(platformName));
/* 680 */           return this;
                }

/* 683 */       public Builder properties(Properties properties) {
/* 684 */           if (properties != null) {
/* 685 */               this.properties.putAll(properties);
                    }
/* 687 */           return this;
                }

/* 690 */       public Builder propertyFile(String filename) throws IOException {
/* 691 */           propertyFile(filename == null ? null : new File(filename));
/* 692 */           return this;
                }

/* 695 */       public Builder propertyFile(File propertyFile) throws IOException {
/* 696 */           if (propertyFile != null) {
/* 699 */               FileInputStream fis = new FileInputStream(propertyFile);
/* 700 */               this.properties = new Properties(this.properties);
                        try {
/* 702 */                   this.properties.load(new InputStreamReader(fis));
                        } catch (NoSuchMethodError e) {
/* 704 */                   this.properties.load(fis);
                        }
/* 706 */               fis.close();
                    }
/* 707 */           return this;
                }

/* 710 */       public Builder property(String keyValue) {
/* 711 */           int equalIndex = keyValue.indexOf(61);
/* 712 */           if (equalIndex < 0) {
/* 713 */               equalIndex = keyValue.indexOf(58);
                    }
/* 715 */           property(keyValue.substring(2, equalIndex), keyValue.substring(equalIndex + 1));
/* 717 */           return this;
                }

/* 720 */       public Builder property(String key, String value) {
/* 721 */           if (key.length() > 0 && value.length() > 0) {
/* 722 */               this.properties.put(key, value);
                    }
/* 724 */           return this;
                }

/* 728 */       public Builder classesOrPackages(String... classesOrPackages) throws IOException {
/* 729 */           if (classesOrPackages == null) {
/* 730 */               this.classScanner.addPackage(null, true);
                    } else {
                        for (String s : classesOrPackages) {
/* 732 */                   this.classScanner.addClassOrPackage(s);
                        }
                    }
/* 734 */           return this;
                }

/* 737 */       public Builder environmentVariables(Map<String, String> environmentVariables) {
/* 738 */           this.environmentVariables = environmentVariables;
/* 739 */           return this;
                }

/* 742 */       public Builder compilerOptions(String... options) {
/* 743 */           if (options != null) {
/* 744 */               this.compilerOptions.addAll(Arrays.asList(options));
                    }
/* 746 */           return this;
                }

/* 756 */       public File[] build() throws Parser.Exception, InterruptedException, IOException {
/* 757 */           if (this.classScanner.getClasses().isEmpty()) {
/* 758 */               return null;
                    }
/* 761 */           LinkedList<File> outputFiles = new LinkedList<>();
/* 762 */           Map<String, LinkedList<Class>> map = new LinkedHashMap<>();
                    for (Class c : this.classScanner.getClasses()) {
/* 764 */               if (Loader.getEnclosingClass(c) == c) {
/* 767 */                   Loader.ClassProperties p = Loader.loadProperties(c, this.properties, false);
/* 768 */                   String target = p.getProperty("parser.target");
/* 769 */                   if (target != null && !c.getName().equals(target)) {
/* 770 */                       File f = parse(c);
/* 771 */                       if (f != null) {
/* 772 */                           outputFiles.add(f);
                                }
                            } else {
/* 776 */                       String libraryName = this.outputName != null ? this.outputName : p.getProperty("loader.library", "");
/* 777 */                       if (libraryName.length() != 0) {
/* 780 */                           LinkedList<Class> classList = map.get(libraryName);
/* 781 */                           if (classList == null) {
/* 782 */                               classList = new LinkedList<>();
                                        map.put(libraryName, classList);
                                    }
/* 784 */                           classList.add(c);
                                }
                            }
                        }
                    }
                    for (String libraryName2 : map.keySet()) {
/* 787 */               LinkedList<Class> classList2 = map.get(libraryName2);
/* 788 */               Class[] classArray = (Class[]) classList2.toArray(new Class[classList2.size()]);
/* 789 */               File f2 = generateAndCompile(classArray, libraryName2);
/* 790 */               if (f2 != null) {
/* 791 */                   outputFiles.add(f2);
/* 792 */                   if (this.copyLibs) {
/* 794 */                       Loader.ClassProperties p2 = Loader.loadProperties(classArray, this.properties, false);
/* 795 */                       LinkedList<String> preloads = new LinkedList<>();
/* 796 */                       preloads.addAll(p2.get("loader.preload"));
/* 797 */                       preloads.addAll(p2.get("compiler.link"));
/* 799 */                       Loader.ClassProperties p3 = Loader.loadProperties(classArray, this.properties, true);
/* 801 */                       File directory = f2.getParentFile();
                                for (String s : preloads) {
/* 803 */                           URL[] urls = Loader.findLibrary(null, p3, s);
                                    try {
/* 806 */                               File fi = new File(urls[0].toURI());
/* 810 */                               File fo = new File(directory, fi.getName());
/* 811 */                               if (fi.exists() && !outputFiles.contains(fo)) {
/* 812 */                                   System.out.println("Copying library file: " + fi);
/* 813 */                                   FileInputStream fis = new FileInputStream(fi);
/* 814 */                                   FileOutputStream fos = new FileOutputStream(fo);
/* 815 */                                   byte[] buffer = new byte[1024];
                                            while (true) {
/* 817 */                                       int length = fis.read(buffer);
                                                if (length == -1) {
                                                    break;
                                                }
/* 818 */                                       fos.write(buffer, 0, length);
                                            }
/* 820 */                                   fos.close();
/* 821 */                                   fis.close();
/* 822 */                                   outputFiles.add(fo);
                                        }
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        }
                    }
/* 829 */           File[] files = (File[]) outputFiles.toArray(new File[outputFiles.size()]);
/* 830 */           if (this.jarPrefix != null && files.length > 0) {
/* 831 */               File jarFile = new File(this.jarPrefix + "-" + this.properties.get("platform.name") + ".jar");
/* 832 */               File d = jarFile.getParentFile();
/* 833 */               if (d != null && !d.exists()) {
/* 834 */                   d.mkdir();
                        }
/* 836 */               createJar(jarFile, this.outputDirectory == null ? this.classScanner.getClassLoader().getPaths() : null, files);
/* 838 */               return files;
                    }
/* 838 */           return files;
                }

/* 844 */       public static void printHelp() {
/* 845 */           String version = Builder.class.getPackage().getImplementationVersion();
/* 846 */           if (version == null) {
/* 847 */               version = EnvironmentCompat.MEDIA_UNKNOWN;
                    }
/* 849 */           System.out.println("JavaCPP version " + version + IOUtils.LINE_SEPARATOR_UNIX + "Copyright (C) 2011-2013 Samuel Audet <samuel.audet@gmail.com>\nProject site: http://code.google.com/p/javacpp/\n\nLicensed under the GNU General Public License version 2 (GPLv2) with Classpath exception.\nPlease refer to LICENSE.txt or http://www.gnu.org/licenses/ for details.");
/* 856 */           System.out.println();
/* 857 */           System.out.println("Usage: java -jar javacpp.jar [options] [class or package (suffixed with .* or .**)]");
/* 858 */           System.out.println();
/* 859 */           System.out.println("where options include:");
/* 860 */           System.out.println();
/* 861 */           System.out.println("    -classpath <path>      Load user classes from path");
/* 862 */           System.out.println("    -d <directory>         Output all generated files to directory");
/* 863 */           System.out.println("    -o <name>              Output everything in a file named after given name");
/* 864 */           System.out.println("    -nocompile             Do not compile or delete the generated source files");
/* 865 */           System.out.println("    -header                Generate header file with declarations of callbacks functions");
/* 866 */           System.out.println("    -copylibs              Copy to output directory dependent libraries (link and preload)");
/* 867 */           System.out.println("    -jarprefix <prefix>    Also create a JAR file named \"<prefix>-<platform.name>.jar\"");
/* 868 */           System.out.println("    -properties <resource> Load all properties from resource");
/* 869 */           System.out.println("    -propertyfile <file>   Load all properties from file");
/* 870 */           System.out.println("    -D<property>=<value>   Set property to value");
/* 871 */           System.out.println("    -Xcompiler <option>    Pass option directly to compiler");
/* 872 */           System.out.println();
                }

/* 881 */       public static void main(String[] args) throws Exception {
/* 882 */           boolean addedClasses = false;
/* 883 */           Builder builder = new Builder();
/* 884 */           int i = 0;
                    while (i < args.length) {
/* 885 */               if ("-help".equals(args[i]) || "--help".equals(args[i])) {
/* 886 */                   printHelp();
/* 887 */                   System.exit(0);
                        } else if ("-classpath".equals(args[i]) || "-cp".equals(args[i]) || "-lib".equals(args[i])) {
/* 889 */                   i++;
                            builder.classPaths(args[i]);
                        } else if ("-d".equals(args[i])) {
/* 891 */                   i++;
                            builder.outputDirectory(args[i]);
                        } else if ("-o".equals(args[i])) {
/* 893 */                   i++;
                            builder.outputName(args[i]);
                        } else if ("-cpp".equals(args[i]) || "-nocompile".equals(args[i])) {
/* 895 */                   builder.compile(false);
                        } else if ("-header".equals(args[i])) {
/* 897 */                   builder.header(true);
                        } else if ("-copylibs".equals(args[i])) {
/* 899 */                   builder.copyLibs(true);
                        } else if ("-jarprefix".equals(args[i])) {
/* 901 */                   i++;
                            builder.jarPrefix(args[i]);
                        } else if ("-properties".equals(args[i])) {
/* 903 */                   i++;
                            builder.properties(args[i]);
                        } else if ("-propertyfile".equals(args[i])) {
/* 905 */                   i++;
                            builder.propertyFile(args[i]);
                        } else if (args[i].startsWith("-D")) {
/* 907 */                   builder.property(args[i]);
                        } else if ("-Xcompiler".equals(args[i])) {
                            i++;
/* 909 */                   builder.compilerOptions(args[i]);
                        } else if (args[i].startsWith("-")) {
/* 911 */                   System.err.println("Error: Invalid option \"" + args[i] + "\"");
/* 912 */                   printHelp();
/* 913 */                   System.exit(1);
                        } else {
/* 915 */                   builder.classesOrPackages(args[i]);
/* 916 */                   addedClasses = true;
                        }
/* 884 */               i++;
                    }
/* 919 */           if (!addedClasses) {
/* 920 */               builder.classesOrPackages((String[]) null);
                    }
/* 922 */           builder.build();
                }
            }
