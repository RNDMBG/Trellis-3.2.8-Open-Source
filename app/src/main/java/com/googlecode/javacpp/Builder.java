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
            
            
    public class Builder {
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

        public File parse(Class cls) throws Parser.Exception, IOException {
            Parser.InfoMap infoMap = new Parser.InfoMap();
                    try {
                Object obj = cls.newInstance();
                if (obj instanceof Parser.InfoMapper) {
                    ((Parser.InfoMapper) obj).map(infoMap);
                        }
                    } catch (IllegalAccessException e) {
                    } catch (InstantiationException e2) {
                    }
            return new Parser(this.properties, infoMap).parse(this.outputDirectory, cls);
                }

        public static void includeJavaPaths(Loader.ClassProperties properties, boolean header) {
            String platformName = Loader.getPlatformName();
            String jvmlink = properties.getProperty("compiler.link.prefix", "") + "jvm" + properties.getProperty("compiler.link.suffix", "");
            String jvmlib = properties.getProperty("library.prefix", "") + "jvm" + properties.getProperty("library.suffix", "");
            String[] jnipath = new String[2];
            String[] jvmpath = new String[2];
            FilenameFilter filter = new C12201(jnipath, jvmlink, jvmpath, jvmlib);
           File javaHome = new File(System.getProperty("java.home")).getParentFile();
                    try {
               javaHome = javaHome.getCanonicalFile();
                    } catch (IOException e) {
                    }
           LinkedList<File> dirs = new LinkedList<>(Arrays.asList(javaHome.listFiles(filter)));
           while (!dirs.isEmpty()) {
               File d = dirs.pop();
               String dpath = d.getPath();
               File[] arr$ = d.listFiles(filter);
                        int len$ = arr$.length;
                        for (int i$ = 0; i$ < len$; i$++) {
                            File f = arr$[i$];
                            try {
                       f = f.getCanonicalFile();
                            } catch (IOException e2) {
                            }
                   if (!dpath.startsWith(f.getPath())) {
                       dirs.add(f);
                            }
                        }
                    }
           if (jnipath[0] != null && jnipath[0].equals(jnipath[1])) {
               jnipath[1] = null;
                    } else if (jnipath[0] == null && new File("/System/Library/Frameworks/JavaVM.framework/Headers/").isDirectory()) {
               jnipath[0] = "/System/Library/Frameworks/JavaVM.framework/Headers/";
                    }
           if (jvmpath[0] != null && jvmpath[0].equals(jvmpath[1])) {
               jvmpath[1] = null;
                    }
           properties.addAll("compiler.includepath", jnipath);
           if (platformName.equals(properties.getProperty("platform.name", platformName))) {
               if (header) {
                   properties.get("compiler.link").add(0, "jvm");
                   properties.addAll("compiler.linkpath", jvmpath);
                        }
               if (platformName.startsWith("macosx")) {
                   properties.addAll("compiler.framework", "JavaVM");
                        }
                    }
                }

                
        static class C12201 implements FilenameFilter {
                    final  String[] val$jnipath;
                    final  String val$jvmlib;
                    final  String val$jvmlink;
                    final  String[] val$jvmpath;

            C12201(String[] strArr, String str, String[] strArr2, String str2) {
                this.val$jnipath = strArr;
                        this.val$jvmlink = str;
                        this.val$jvmpath = strArr2;
                        this.val$jvmlib = str2;
                    }

                    @Override 
            public boolean accept(File dir, String name) {
                if (new File(dir, "jni.h").exists()) {
                    this.val$jnipath[0] = dir.getAbsolutePath();
                        }
                if (new File(dir, "jni_md.h").exists()) {
                   this.val$jnipath[1] = dir.getAbsolutePath();
                        }
               if (new File(dir, this.val$jvmlink).exists()) {
                   this.val$jvmpath[0] = dir.getAbsolutePath();
                        }
               if (new File(dir, this.val$jvmlib).exists()) {
                   this.val$jvmpath[1] = dir.getAbsolutePath();
                        }
               return new File(dir, name).isDirectory();
                    }
                }

       public static class Piper extends Thread {

                    
                    InputStream f2529is;

                    
                    OutputStream f2530os;

           public Piper(InputStream is, OutputStream os) {
               this.f2529is = is;
               this.f2530os = os;
                    }

                    @Override 
           public void run() {
                        try {
                   byte[] buffer = new byte[1024];
                            while (true) {
                       int length = this.f2529is.read(buffer);
                                if (length != -1) {
                           this.f2530os.write(buffer, 0, length);
                                } else {
                           return;
                                }
                            }
                        } catch (IOException e) {
                   System.err.println("Could not pipe from the InputStream to the OutputStream: " + e.getMessage());
                        }
                    }
                }

       public int compile(String sourceFilename, String outputFilename, Loader.ClassProperties properties) throws InterruptedException, IOException {
                    String s;
           LinkedList<String> command = new LinkedList<>();
           includeJavaPaths(properties, this.header);
           String platformName = Loader.getPlatformName();
           String compilerPath = properties.getProperty("compiler.path");
           command.add(compilerPath);
           String p = properties.getProperty("compiler.sysroot.prefix", "");
                    for (String s2 : properties.get("compiler.sysroot")) {
               if (new File(s2).isDirectory()) {
                   if (p.endsWith(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
                       command.add(p.trim());
                                command.add(s2);
                            } else {
                       command.add(p + s2);
                            }
                        }
                    }
           String p2 = properties.getProperty("compiler.includepath.prefix", "");
                    for (String s3 : properties.get("compiler.includepath")) {
               if (new File(s3).isDirectory()) {
                   if (p2.endsWith(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
                       command.add(p2.trim());
                                command.add(s3);
                            } else {
                       command.add(p2 + s3);
                            }
                        }
                    }
           command.add(sourceFilename);
           Collection<String> allOptions = properties.get("compiler.options");
           if (allOptions.isEmpty()) {
               allOptions.add("default");
                    }
                    for (String s4 : allOptions) {
               if (s4 != null && s4.length() != 0) {
                   String p3 = "compiler.options." + s4;
                   String options = properties.getProperty(p3);
                   if (options != null && options.length() > 0) {
                       command.addAll(Arrays.asList(options.split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)));
                            } else if (!"default".equals(s4)) {
                       System.err.println("Warning: Could not get the property named \"" + p3 + "\"");
                            }
                        }
                    }
           command.addAll(this.compilerOptions);
           String outputPrefix = properties.getProperty("compiler.output.prefix");
           if (outputPrefix != null && outputPrefix.length() > 0) {
               command.addAll(Arrays.asList(outputPrefix.split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)));
                    }
           if (outputPrefix == null || outputPrefix.length() == 0 || outputPrefix.endsWith(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
               command.add(outputFilename);
                    } else {
               command.add(command.removeLast() + outputFilename);
                    }
           String p4 = properties.getProperty("compiler.linkpath.prefix", "");
           String p5 = properties.getProperty("compiler.linkpath.prefix2");
                    for (String s5 : properties.get("compiler.linkpath")) {
               if (new File(s5).isDirectory()) {
                   if (p4.endsWith(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
                       command.add(p4.trim());
                                command.add(s5);
                            } else {
                       command.add(p4 + s5);
                            }
                   if (p5 != null) {
                       if (p5.endsWith(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
                           command.add(p5.trim());
                                    command.add(s5);
                                } else {
                           command.add(p5 + s5);
                                }
                            }
                        }
                    }
           String p6 = properties.getProperty("compiler.link.prefix", "");
           String x = properties.getProperty("compiler.link.suffix", "");
           int i = command.size();
           Iterator<String> it = properties.get("compiler.link").iterator();
                    while (it.hasNext()) {
               String[] libnameversion = it.next().split("@");
               if (libnameversion.length == 3 && libnameversion[1].length() == 0) {
                   s = libnameversion[0] + libnameversion[2];
                        } else {
                   s = libnameversion[0];
                        }
               if (p6.endsWith(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR) && x.startsWith(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
                   command.add(i, p6.trim());
                            command.add(i + 1, s);
                            command.add(i + 2, x.trim());
                        } else if (p6.endsWith(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
                   command.add(i, p6.trim());
                            command.add(i + 1, s + x);
                        } else if (x.startsWith(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
                   command.add(i, p6 + s);
                            command.add(i + 1, x.trim());
                        } else {
                   command.add(i, p6 + s + x);
                        }
                    }
           String p7 = properties.getProperty("compiler.framework.prefix", "");
           String x2 = properties.getProperty("compiler.framework.suffix", "");
                    for (String s6 : properties.get("compiler.framework")) {
               if (p7.endsWith(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR) && x2.startsWith(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
                   command.add(p7.trim());
                            command.add(s6);
                            command.add(x2.trim());
                        } else if (p7.endsWith(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
                   command.add(p7.trim());
                            command.add(s6 + x2);
                        } else if (x2.startsWith(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
                   command.add(p7 + s6);
                            command.add(x2.trim());
                        } else {
                   command.add(p7 + s6 + x2);
                        }
                    }
           boolean windows = platformName.startsWith("windows");
                    for (String s7 : command) {
               boolean hasSpaces = s7.indexOf(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR) > 0;
               if (hasSpaces) {
                   System.out.print(windows ? "\"" : "'");
                        }
               System.out.print(s7);
               if (hasSpaces) {
                   System.out.print(windows ? "\"" : "'");
                        }
               System.out.print(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                    }
           System.out.println();
           ProcessBuilder pb = new ProcessBuilder(command);
           if (this.environmentVariables != null) {
               pb.environment().putAll(this.environmentVariables);
                    }
           Process p8 = pb.start();
           new Piper(p8.getErrorStream(), System.err).start();
           new Piper(p8.getInputStream(), System.out).start();
           return p8.waitFor();
                }

       public File generateAndCompile(Class[] classes, String outputName) throws InterruptedException, IOException {
                    File outputPath;
                    String sourcePrefix;
           Loader.ClassProperties p = Loader.loadProperties(classes, this.properties, true);
           String platformName = p.getProperty("platform.name");
           String sourceSuffix = p.getProperty("source.suffix", ".cpp");
           String libraryName = p.getProperty("library.prefix", "") + outputName + p.getProperty("library.suffix", "");
           if (this.outputDirectory == null) {
                        try {
                   URL resourceURL = classes[0].getResource('/' + classes[0].getName().replace(FilenameUtils.EXTENSION_SEPARATOR, '/') + ".class");
                   File packageDir = new File(resourceURL.toURI()).getParentFile();
                   outputPath = new File(packageDir, platformName);
                   sourcePrefix = packageDir.getPath() + File.separator + outputName;
                        } catch (URISyntaxException e) {
                   throw new RuntimeException(e);
                        }
                    } else {
               outputPath = this.outputDirectory;
               sourcePrefix = outputPath.getPath() + File.separator + outputName;
                    }
           if (!outputPath.exists()) {
               outputPath.mkdirs();
                    }
           Generator generator = new Generator(p);
           String sourceFilename = sourcePrefix + sourceSuffix;
           String headerFilename = this.header ? sourcePrefix + ".h" : null;
           String classPath = System.getProperty("java.class.path");
           String[] arr$ = this.classScanner.getClassLoader().getPaths();
                    for (String s : arr$) {
               classPath = classPath + File.pathSeparator + s;
                    }
           System.out.println("Generating source file: " + sourceFilename);
           if (generator.generate(sourceFilename, headerFilename, classPath, classes)) {
               generator.close();
               if (this.compile) {
                   String libraryFilename = outputPath.getPath() + File.separator + libraryName;
                   System.out.println("Compiling library file: " + libraryFilename);
                   int exitValue = compile(sourceFilename, libraryFilename, p);
                   if (exitValue == 0) {
                       new File(sourceFilename).delete();
                       File outputFile = new File(libraryFilename);
                       return outputFile;
                            }
                   System.exit(exitValue);
                   return null;
                        }
               File outputFile2 = new File(sourceFilename);
               return outputFile2;
                    }
           System.out.println("Source file not generated: " + sourceFilename);
           return null;
                }

       public static void createJar(File jarFile, String[] classpath, File... files) throws IOException {
           System.out.println("Creating JAR file: " + jarFile);
           JarOutputStream jos = new JarOutputStream(new FileOutputStream(jarFile));
                    for (File f : files) {
               String name = f.getPath();
               if (classpath != null) {
                   String[] names = new String[classpath.length];
                            for (int i = 0; i < classpath.length; i++) {
                       String path = new File(classpath[i]).getCanonicalPath();
                       if (name.startsWith(path)) {
                           names[i] = name.substring(path.length() + 1);
                                }
                            }
                            for (int i2 = 0; i2 < names.length; i2++) {
                       if (names[i2] != null && names[i2].length() < name.length()) {
                           name = names[i2];
                                }
                            }
                        }
               ZipEntry e = new ZipEntry(name.replace(File.separatorChar, '/'));
               e.setTime(f.lastModified());
               jos.putNextEntry(e);
               FileInputStream fis = new FileInputStream(f);
               byte[] buffer = new byte[1024];
                        while (true) {
                   int length = fis.read(buffer);
                            if (length != -1) {
                       jos.write(buffer, 0, length);
                            } else {
                                break;
                            }
                        }
               fis.close();
               jos.closeEntry();
                    }
           jos.close();
                }

       public static class UserClassLoader extends URLClassLoader {
                    private LinkedList<String> paths;

           public UserClassLoader() {
               super(new URL[0]);
               this.paths = new LinkedList<>();
                    }

           public UserClassLoader(ClassLoader parent) {
               super(new URL[0], parent);
               this.paths = new LinkedList<>();
                    }

           public void addPaths(String... paths) {
               if (paths != null) {
                            for (String path : paths) {
                       File f = new File(path);
                       if (f.exists()) {
                           this.paths.add(path);
                                    try {
                               addURL(f.toURI().toURL());
                                    } catch (MalformedURLException e) {
                               throw new RuntimeException(e);
                                    }
                                }
                            }
                        }
                    }

           public String[] getPaths() {
               if (this.paths.isEmpty()) {
                   addPaths(System.getProperty("user.dir"));
                        }
               return (String[]) this.paths.toArray(new String[this.paths.size()]);
                    }

                    @Override 
           protected Class<?> findClass(String name) throws ClassNotFoundException {
               if (this.paths.isEmpty()) {
                   addPaths(System.getProperty("user.dir"));
                        }
               return super.findClass(name);
                    }
                }

       public static class ClassScanner {
                    private Collection<Class> classes;
                    private UserClassLoader loader;

           public ClassScanner(Collection<Class> classes, UserClassLoader loader) {
               this.classes = classes;
               this.loader = loader;
                    }

           public Collection<Class> getClasses() {
               return this.classes;
                    }

           public UserClassLoader getClassLoader() {
               return this.loader;
                    }

           public void addClass(String className) {
               if (className != null) {
                   if (className.endsWith(".class")) {
                       className = className.substring(0, className.length() - 6);
                            }
                            try {
                       Class<?> cls = Class.forName(className, false, this.loader);
                       if (!this.classes.contains(cls)) {
                           this.classes.add(cls);
                                }
                            } catch (ClassNotFoundException e) {
                       System.err.println("Warning: Could not find class " + className + ": " + e);
                            } catch (NoClassDefFoundError e2) {
                       System.err.println("Warning: Could not load class " + className + ": " + e2);
                            }
                        }
                    }

           public void addMatchingFile(String filename, String packagePath, boolean recursive) {
               if (filename == null || !filename.endsWith(".class")) {
                   return;
                        }
                        if (packagePath == null || ((recursive && filename.startsWith(packagePath)) || filename.regionMatches(0, packagePath, 0, Math.max(filename.lastIndexOf(47), packagePath.lastIndexOf(47))))) {
                   addClass(filename.replace('/', FilenameUtils.EXTENSION_SEPARATOR));
                        }
                    }

           public void addMatchingDir(String parentName, File dir, String packagePath, boolean recursive) {
               File[] files = dir.listFiles();
               Arrays.sort(files);
                        for (File f : files) {
                   String pathName = parentName == null ? f.getName() : parentName + f.getName();
                   if (f.isDirectory()) {
                       addMatchingDir(pathName + "/", f, packagePath, recursive);
                            } else {
                       addMatchingFile(pathName, packagePath, recursive);
                            }
                        }
                    }

           public void addPackage(String packageName, boolean recursive) throws IOException {
               String[] paths = this.loader.getPaths();
               String packagePath = packageName == null ? null : packageName.replace(FilenameUtils.EXTENSION_SEPARATOR, '/') + "/";
               int prevSize = this.classes.size();
                        for (String p : paths) {
                   File file = new File(p);
                   if (file.isDirectory()) {
                       addMatchingDir(null, file, packagePath, recursive);
                            } else {
                       JarInputStream jis = new JarInputStream(new FileInputStream(file));
                       for (ZipEntry e = jis.getNextEntry(); e != null; e = jis.getNextEntry()) {
                           addMatchingFile(e.getName(), packagePath, recursive);
                           jis.closeEntry();
                                }
                       jis.close();
                            }
                        }
               if (this.classes.size() == 0 && packageName == null) {
                   System.err.println("Warning: No classes found in the unnamed package");
                   Builder.printHelp();
                        } else if (prevSize == this.classes.size() && packageName != null) {
                   System.err.println("Warning: No classes found in package " + packageName);
                        }
                    }

           public void addClassOrPackage(String name) throws IOException {
               if (name != null) {
                   String name2 = name.replace('/', FilenameUtils.EXTENSION_SEPARATOR);
                   if (name2.endsWith(".**")) {
                       addPackage(name2.substring(0, name2.length() - 3), true);
                            } else if (name2.endsWith(".*")) {
                       addPackage(name2.substring(0, name2.length() - 2), false);
                            } else {
                       addClass(name2);
                            }
                        }
                    }
                }

       public Builder() {
           this.properties = null;
           this.classScanner = null;
           this.compilerOptions = null;
           Loader.loadLibraries = false;
           this.properties = Loader.loadProperties();
           this.classScanner = new ClassScanner(new LinkedList(), new UserClassLoader(Thread.currentThread().getContextClassLoader()));
           this.compilerOptions = new LinkedList();
                }

       public Builder classPaths(String classPaths) {
           classPaths(classPaths == null ? null : classPaths.split(File.pathSeparator));
           return this;
                }

       public Builder classPaths(String... classPaths) {
           this.classScanner.getClassLoader().addPaths(classPaths);
           return this;
                }

       public Builder outputDirectory(String outputDirectory) {
           outputDirectory(outputDirectory == null ? null : new File(outputDirectory));
           return this;
                }

       public Builder outputDirectory(File outputDirectory) {
           this.outputDirectory = outputDirectory;
           return this;
                }

       public Builder compile(boolean compile) {
           this.compile = compile;
           return this;
                }

       public Builder header(boolean header) {
           this.header = header;
           return this;
                }

       public Builder copyLibs(boolean copyLibs) {
           this.copyLibs = copyLibs;
           return this;
                }

       public Builder outputName(String outputName) {
           this.outputName = outputName;
           return this;
                }

       public Builder jarPrefix(String jarPrefix) {
           this.jarPrefix = jarPrefix;
           return this;
                }

       public Builder properties(String platformName) {
           properties(platformName == null ? null : Loader.loadProperties(platformName));
           return this;
                }

       public Builder properties(Properties properties) {
           if (properties != null) {
               this.properties.putAll(properties);
                    }
           return this;
                }

       public Builder propertyFile(String filename) throws IOException {
           propertyFile(filename == null ? null : new File(filename));
           return this;
                }

       public Builder propertyFile(File propertyFile) throws IOException {
           if (propertyFile != null) {
               FileInputStream fis = new FileInputStream(propertyFile);
               this.properties = new Properties(this.properties);
                        try {
                   this.properties.load(new InputStreamReader(fis));
                        } catch (NoSuchMethodError e) {
                   this.properties.load(fis);
                        }
               fis.close();
                    }
           return this;
                }

       public Builder property(String keyValue) {
           int equalIndex = keyValue.indexOf(61);
           if (equalIndex < 0) {
               equalIndex = keyValue.indexOf(58);
                    }
           property(keyValue.substring(2, equalIndex), keyValue.substring(equalIndex + 1));
           return this;
                }

       public Builder property(String key, String value) {
           if (key.length() > 0 && value.length() > 0) {
               this.properties.put(key, value);
                    }
           return this;
                }

       public Builder classesOrPackages(String... classesOrPackages) throws IOException {
           if (classesOrPackages == null) {
               this.classScanner.addPackage(null, true);
                    } else {
                        for (String s : classesOrPackages) {
                   this.classScanner.addClassOrPackage(s);
                        }
                    }
           return this;
                }

       public Builder environmentVariables(Map<String, String> environmentVariables) {
           this.environmentVariables = environmentVariables;
           return this;
                }

       public Builder compilerOptions(String... options) {
           if (options != null) {
               this.compilerOptions.addAll(Arrays.asList(options));
                    }
           return this;
                }

       public File[] build() throws Parser.Exception, InterruptedException, IOException {
           if (this.classScanner.getClasses().isEmpty()) {
               return null;
                    }
           LinkedList<File> outputFiles = new LinkedList<>();
           Map<String, LinkedList<Class>> map = new LinkedHashMap<>();
                    for (Class c : this.classScanner.getClasses()) {
               if (Loader.getEnclosingClass(c) == c) {
                   Loader.ClassProperties p = Loader.loadProperties(c, this.properties, false);
                   String target = p.getProperty("parser.target");
                   if (target != null && !c.getName().equals(target)) {
                       File f = parse(c);
                       if (f != null) {
                           outputFiles.add(f);
                                }
                            } else {
                       String libraryName = this.outputName != null ? this.outputName : p.getProperty("loader.library", "");
                       if (libraryName.length() != 0) {
                           LinkedList<Class> classList = map.get(libraryName);
                           if (classList == null) {
                               classList = new LinkedList<>();
                                        map.put(libraryName, classList);
                                    }
                           classList.add(c);
                                }
                            }
                        }
                    }
                    for (String libraryName2 : map.keySet()) {
               LinkedList<Class> classList2 = map.get(libraryName2);
               Class[] classArray = (Class[]) classList2.toArray(new Class[classList2.size()]);
               File f2 = generateAndCompile(classArray, libraryName2);
               if (f2 != null) {
                   outputFiles.add(f2);
                   if (this.copyLibs) {
                       Loader.ClassProperties p2 = Loader.loadProperties(classArray, this.properties, false);
                       LinkedList<String> preloads = new LinkedList<>();
                       preloads.addAll(p2.get("loader.preload"));
                       preloads.addAll(p2.get("compiler.link"));
                       Loader.ClassProperties p3 = Loader.loadProperties(classArray, this.properties, true);
                       File directory = f2.getParentFile();
                                for (String s : preloads) {
                           URL[] urls = Loader.findLibrary(null, p3, s);
                                    try {
                               File fi = new File(urls[0].toURI());
                               File fo = new File(directory, fi.getName());
                               if (fi.exists() && !outputFiles.contains(fo)) {
                                   System.out.println("Copying library file: " + fi);
                                   FileInputStream fis = new FileInputStream(fi);
                                   FileOutputStream fos = new FileOutputStream(fo);
                                   byte[] buffer = new byte[1024];
                                            while (true) {
                                       int length = fis.read(buffer);
                                                if (length == -1) {
                                                    break;
                                                }
                                       fos.write(buffer, 0, length);
                                            }
                                   fos.close();
                                   fis.close();
                                   outputFiles.add(fo);
                                        }
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        }
                    }
           File[] files = (File[]) outputFiles.toArray(new File[outputFiles.size()]);
           if (this.jarPrefix != null && files.length > 0) {
               File jarFile = new File(this.jarPrefix + "-" + this.properties.get("platform.name") + ".jar");
               File d = jarFile.getParentFile();
               if (d != null && !d.exists()) {
                   d.mkdir();
                        }
               createJar(jarFile, this.outputDirectory == null ? this.classScanner.getClassLoader().getPaths() : null, files);
               return files;
                    }
           return files;
                }

       public static void printHelp() {
           String version = Builder.class.getPackage().getImplementationVersion();
           if (version == null) {
               version = EnvironmentCompat.MEDIA_UNKNOWN;
                    }
           System.out.println("JavaCPP version " + version + IOUtils.LINE_SEPARATOR_UNIX + "Copyright (C) 2011-2013 Samuel Audet <samuel.audet@gmail.com>\nProject site: http://code.google.com/p/javacpp/\n\nLicensed under the GNU General Public License version 2 (GPLv2) with Classpath exception.\nPlease refer to LICENSE.txt or http://www.gnu.org/licenses/ for details.");
           System.out.println();
           System.out.println("Usage: java -jar javacpp.jar [options] [class or package (suffixed with .* or .**)]");
           System.out.println();
           System.out.println("where options include:");
           System.out.println();
           System.out.println("    -classpath <path>      Load user classes from path");
           System.out.println("    -d <directory>         Output all generated files to directory");
           System.out.println("    -o <name>              Output everything in a file named after given name");
           System.out.println("    -nocompile             Do not compile or delete the generated source files");
           System.out.println("    -header                Generate header file with declarations of callbacks functions");
           System.out.println("    -copylibs              Copy to output directory dependent libraries (link and preload)");
           System.out.println("    -jarprefix <prefix>    Also create a JAR file named \"<prefix>-<platform.name>.jar\"");
           System.out.println("    -properties <resource> Load all properties from resource");
           System.out.println("    -propertyfile <file>   Load all properties from file");
           System.out.println("    -D<property>=<value>   Set property to value");
           System.out.println("    -Xcompiler <option>    Pass option directly to compiler");
           System.out.println();
                }

       public static void main(String[] args) throws Exception {
           boolean addedClasses = false;
           Builder builder = new Builder();
           int i = 0;
                    while (i < args.length) {
               if ("-help".equals(args[i]) || "--help".equals(args[i])) {
                   printHelp();
                   System.exit(0);
                        } else if ("-classpath".equals(args[i]) || "-cp".equals(args[i]) || "-lib".equals(args[i])) {
                   i++;
                            builder.classPaths(args[i]);
                        } else if ("-d".equals(args[i])) {
                   i++;
                            builder.outputDirectory(args[i]);
                        } else if ("-o".equals(args[i])) {
                   i++;
                            builder.outputName(args[i]);
                        } else if ("-cpp".equals(args[i]) || "-nocompile".equals(args[i])) {
                   builder.compile(false);
                        } else if ("-header".equals(args[i])) {
                   builder.header(true);
                        } else if ("-copylibs".equals(args[i])) {
                   builder.copyLibs(true);
                        } else if ("-jarprefix".equals(args[i])) {
                   i++;
                            builder.jarPrefix(args[i]);
                        } else if ("-properties".equals(args[i])) {
                   i++;
                            builder.properties(args[i]);
                        } else if ("-propertyfile".equals(args[i])) {
                   i++;
                            builder.propertyFile(args[i]);
                        } else if (args[i].startsWith("-D")) {
                   builder.property(args[i]);
                        } else if ("-Xcompiler".equals(args[i])) {
                            i++;
                   builder.compilerOptions(args[i]);
                        } else if (args[i].startsWith("-")) {
                   System.err.println("Error: Invalid option \"" + args[i] + "\"");
                   printHelp();
                   System.exit(1);
                        } else {
                   builder.classesOrPackages(args[i]);
                   addedClasses = true;
                        }
               i++;
                    }
           if (!addedClasses) {
               builder.classesOrPackages((String[]) null);
                    }
           builder.build();
                }
            }
