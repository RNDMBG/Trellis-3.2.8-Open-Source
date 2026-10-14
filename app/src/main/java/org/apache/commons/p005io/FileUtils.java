            package org.apache.commons.p005io;

            import co.vine.android.util.CommonUtil;
            import com.vandalsoftware.p004io.IoUtils;
            import java.io.BufferedOutputStream;
            import java.io.File;
            import java.io.FileFilter;
            import java.io.FileInputStream;
            import java.io.FileNotFoundException;
            import java.io.FileOutputStream;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.InputStreamReader;
            import java.io.OutputStream;
            import java.io.Reader;
            import java.math.BigInteger;
            import java.net.URL;
            import java.net.URLConnection;
            import java.nio.ByteBuffer;
            import java.nio.channels.FileChannel;
            import java.nio.charset.Charset;
            import java.util.ArrayList;
            import java.util.Collection;
            import java.util.Date;
            import java.util.Iterator;
            import java.util.LinkedList;
            import java.util.List;
            import java.util.zip.CRC32;
            import java.util.zip.CheckedInputStream;
            import java.util.zip.Checksum;
            import org.apache.commons.p005io.filefilter.DirectoryFileFilter;
            import org.apache.commons.p005io.filefilter.FalseFileFilter;
            import org.apache.commons.p005io.filefilter.FileFilterUtils;
            import org.apache.commons.p005io.filefilter.IOFileFilter;
            import org.apache.commons.p005io.filefilter.SuffixFileFilter;
            import org.apache.commons.p005io.filefilter.TrueFileFilter;
            import org.apache.commons.p005io.output.NullOutputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 190 */   public class FileUtils {
                private static final long FILE_COPY_BUFFER_SIZE = 31457280;
                public static final long ONE_GB = 1073741824;
                public static final long ONE_MB = 1048576;
                public static final long ONE_PB = 1125899906842624L;
                public static final long ONE_TB = 1099511627776L;
                public static final long ONE_KB = 1024;
                public static final BigInteger ONE_KB_BI = BigInteger.valueOf(ONE_KB);
                public static final BigInteger ONE_MB_BI = ONE_KB_BI.multiply(ONE_KB_BI);
                public static final BigInteger ONE_GB_BI = ONE_KB_BI.multiply(ONE_MB_BI);
                public static final BigInteger ONE_TB_BI = ONE_KB_BI.multiply(ONE_GB_BI);
                public static final BigInteger ONE_PB_BI = ONE_KB_BI.multiply(ONE_TB_BI);
                public static final BigInteger ONE_EB_BI = ONE_KB_BI.multiply(ONE_PB_BI);
                public static final long ONE_EB = 1152921504606846976L;
                public static final BigInteger ONE_ZB = BigInteger.valueOf(ONE_KB).multiply(BigInteger.valueOf(ONE_EB));
                public static final BigInteger ONE_YB = ONE_KB_BI.multiply(ONE_ZB);
                public static final File[] EMPTY_FILE_ARRAY = new File[0];
                private static final Charset UTF8 = Charset.forName(IoUtils.UTF_8);

/* 191 */       public static File getFile(File directory, String... names) {
/* 192 */           if (directory == null) {
/* 193 */               throw new NullPointerException("directorydirectory must not be null");
                    }
/* 195 */           if (names == null) {
/* 196 */               throw new NullPointerException("names must not be null");
                    }
/* 199 */           int len$ = names.length;
                    int i$ = 0;
/* 198 */           File file = directory;
                    while (i$ < len$) {
/* 199 */               String name = names[i$];
/* 199 */               i$++;
/* 200 */               file = new File(file, name);
                    }
/* 202 */           return file;
                }

/* 212 */       public static File getFile(String... names) {
                    File file;
/* 213 */           if (names == null) {
/* 214 */               throw new NullPointerException("names must not be null");
                    }
/* 217 */           int len$ = names.length;
                    int i$ = 0;
/* 216 */           File file2 = null;
                    while (i$ < len$) {
/* 217 */               String name = names[i$];
/* 218 */               if (file2 == null) {
/* 219 */                   file = new File(name);
                        } else {
/* 221 */                   file = new File(file2, name);
                        }
/* 217 */               i$++;
                        file2 = file;
                    }
/* 224 */           return file2;
                }

/* 234 */       public static String getTempDirectoryPath() {
/* 235 */           return System.getProperty("java.io.tmpdir");
                }

/* 245 */       public static File getTempDirectory() {
/* 246 */           return new File(getTempDirectoryPath());
                }

/* 256 */       public static String getUserDirectoryPath() {
/* 257 */           return System.getProperty("user.home");
                }

/* 267 */       public static File getUserDirectory() {
/* 268 */           return new File(getUserDirectoryPath());
                }

/* 290 */       public static FileInputStream openInputStream(File file) throws IOException {
/* 291 */           if (file.exists()) {
/* 292 */               if (file.isDirectory()) {
/* 293 */                   throw new IOException("File '" + file + "' exists but is a directory");
                        }
/* 295 */               if (!file.canRead()) {
/* 296 */                   throw new IOException("File '" + file + "' cannot be read");
                        }
/* 301 */               return new FileInputStream(file);
                    }
/* 299 */           throw new FileNotFoundException("File '" + file + "' does not exist");
                }

/* 325 */       public static FileOutputStream openOutputStream(File file) throws IOException {
/* 326 */           return openOutputStream(file, false);
                }

/* 351 */       public static FileOutputStream openOutputStream(File file, boolean append) throws IOException {
/* 352 */           if (file.exists()) {
/* 353 */               if (file.isDirectory()) {
/* 354 */                   throw new IOException("File '" + file + "' exists but is a directory");
                        }
/* 356 */               if (!file.canWrite()) {
/* 357 */                   throw new IOException("File '" + file + "' cannot be written to");
                        }
                    } else {
/* 360 */               File parent = file.getParentFile();
/* 361 */               if (parent != null && !parent.mkdirs() && !parent.isDirectory()) {
/* 363 */                   throw new IOException("Directory '" + parent + "' could not be created");
                        }
                    }
/* 367 */           return new FileOutputStream(file, append);
                }

/* 390 */       public static String byteCountToDisplaySize(BigInteger size) {
/* 391 */           if (size.divide(ONE_EB_BI).compareTo(BigInteger.ZERO) > 0) {
/* 392 */               String displaySize = String.valueOf(size.divide(ONE_EB_BI)) + " EB";
/* 406 */               return displaySize;
                    }
/* 393 */           if (size.divide(ONE_PB_BI).compareTo(BigInteger.ZERO) > 0) {
/* 394 */               String displaySize2 = String.valueOf(size.divide(ONE_PB_BI)) + " PB";
/* 406 */               return displaySize2;
                    }
/* 395 */           if (size.divide(ONE_TB_BI).compareTo(BigInteger.ZERO) > 0) {
/* 396 */               String displaySize3 = String.valueOf(size.divide(ONE_TB_BI)) + " TB";
/* 406 */               return displaySize3;
                    }
/* 397 */           if (size.divide(ONE_GB_BI).compareTo(BigInteger.ZERO) > 0) {
/* 398 */               String displaySize4 = String.valueOf(size.divide(ONE_GB_BI)) + " GB";
/* 406 */               return displaySize4;
                    }
/* 399 */           if (size.divide(ONE_MB_BI).compareTo(BigInteger.ZERO) > 0) {
/* 400 */               String displaySize5 = String.valueOf(size.divide(ONE_MB_BI)) + " MB";
/* 406 */               return displaySize5;
                    }
/* 401 */           if (size.divide(ONE_KB_BI).compareTo(BigInteger.ZERO) > 0) {
/* 402 */               String displaySize6 = String.valueOf(size.divide(ONE_KB_BI)) + " KB";
/* 406 */               return displaySize6;
                    }
/* 404 */           String displaySize7 = String.valueOf(size) + " bytes";
/* 406 */           return displaySize7;
                }

/* 425 */       public static String byteCountToDisplaySize(long size) {
/* 426 */           return byteCountToDisplaySize(BigInteger.valueOf(size));
                }

/* 442 */       public static void touch(File file) throws IOException {
/* 443 */           if (!file.exists()) {
/* 444 */               OutputStream out = openOutputStream(file);
/* 445 */               IOUtils.closeQuietly(out);
                    }
/* 447 */           boolean success = file.setLastModified(System.currentTimeMillis());
/* 448 */           if (!success) {
/* 449 */               throw new IOException("Unable to set the last modification time for " + file);
                    }
                }

/* 462 */       public static File[] convertFileCollectionToFileArray(Collection<File> files) {
/* 463 */           return (File[]) files.toArray(new File[files.size()]);
                }

/* 477 */       private static void innerListFiles(Collection<File> files, File directory, IOFileFilter filter, boolean includeSubDirectories) {
/* 478 */           File[] found = directory.listFiles((FileFilter) filter);
/* 480 */           if (found != null) {
                        for (File file : found) {
/* 482 */                   if (file.isDirectory()) {
/* 483 */                       if (includeSubDirectories) {
/* 484 */                           files.add(file);
                                }
/* 486 */                       innerListFiles(files, file, filter, includeSubDirectories);
                            } else {
/* 488 */                       files.add(file);
                            }
                        }
                    }
                }

/* 520 */       public static Collection<File> listFiles(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter) {
/* 521 */           validateListFilesParameters(directory, fileFilter);
/* 523 */           IOFileFilter effFileFilter = setUpEffectiveFileFilter(fileFilter);
/* 524 */           IOFileFilter effDirFilter = setUpEffectiveDirFilter(dirFilter);
/* 527 */           Collection<File> files = new LinkedList<>();
                    innerListFiles(files, directory, FileFilterUtils.m2470or(effFileFilter, effDirFilter), false);
/* 530 */           return files;
                }

/* 543 */       private static void validateListFilesParameters(File directory, IOFileFilter fileFilter) {
/* 544 */           if (!directory.isDirectory()) {
/* 545 */               throw new IllegalArgumentException("Parameter 'directory' is not a directory");
                    }
/* 547 */           if (fileFilter == null) {
/* 548 */               throw new NullPointerException("Parameter 'fileFilter' is null");
                    }
                }

/* 558 */       private static IOFileFilter setUpEffectiveFileFilter(IOFileFilter fileFilter) {
                    return FileFilterUtils.and(fileFilter, FileFilterUtils.notFileFilter(DirectoryFileFilter.INSTANCE));
                }

/* 568 */       private static IOFileFilter setUpEffectiveDirFilter(IOFileFilter dirFilter) {
                    return dirFilter == null ? FalseFileFilter.INSTANCE : FileFilterUtils.and(dirFilter, DirectoryFileFilter.INSTANCE);
                }

/* 592 */       public static Collection<File> listFilesAndDirs(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter) {
/* 593 */           validateListFilesParameters(directory, fileFilter);
/* 595 */           IOFileFilter effFileFilter = setUpEffectiveFileFilter(fileFilter);
/* 596 */           IOFileFilter effDirFilter = setUpEffectiveDirFilter(dirFilter);
/* 599 */           Collection<File> files = new LinkedList<>();
/* 600 */           if (directory.isDirectory()) {
/* 601 */               files.add(directory);
                    }
                    innerListFiles(files, directory, FileFilterUtils.m2470or(effFileFilter, effDirFilter), true);
/* 605 */           return files;
                }

/* 627 */       public static Iterator<File> iterateFiles(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter) {
/* 628 */           return listFiles(directory, fileFilter, dirFilter).iterator();
                }

/* 651 */       public static Iterator<File> iterateFilesAndDirs(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter) {
/* 652 */           return listFilesAndDirs(directory, fileFilter, dirFilter).iterator();
                }

/* 663 */       private static String[] toSuffixes(String[] extensions) {
/* 664 */           String[] suffixes = new String[extensions.length];
                    for (int i = 0; i < extensions.length; i++) {
/* 666 */               suffixes[i] = "." + extensions[i];
                    }
/* 668 */           return suffixes;
                }

/* 684 */       public static Collection<File> listFiles(File directory, String[] extensions, boolean recursive) {
                    IOFileFilter filter;
/* 685 */           if (extensions == null) {
/* 686 */               filter = TrueFileFilter.INSTANCE;
                    } else {
/* 688 */               String[] suffixes = toSuffixes(extensions);
/* 689 */               filter = new SuffixFileFilter(suffixes);
                    }
                    return listFiles(directory, filter, recursive ? TrueFileFilter.INSTANCE : FalseFileFilter.INSTANCE);
                }

/* 709 */       public static Iterator<File> iterateFiles(File directory, String[] extensions, boolean recursive) {
/* 710 */           return listFiles(directory, extensions, recursive).iterator();
                }

/* 729 */       public static boolean contentEquals(File file1, File file2) throws Throwable {
/* 730 */           boolean file1Exists = file1.exists();
/* 731 */           if (file1Exists != file2.exists()) {
                        return false;
                    }
/* 735 */           if (!file1Exists) {
                        return true;
                    }
/* 740 */           if (file1.isDirectory() || file2.isDirectory()) {
/* 742 */               throw new IOException("Can't compare directories, only files");
                    }
                    if (file1.length() != file2.length()) {
                        return false;
                    }
/* 750 */           if (file1.getCanonicalFile().equals(file2.getCanonicalFile())) {
                        return true;
                    }
/* 755 */           InputStream input1 = null;
/* 756 */           InputStream input2 = null;
                    try {
/* 758 */               InputStream input3 = new FileInputStream(file1);
                        try {
/* 759 */                   InputStream input4 = new FileInputStream(file2);
                            try {
/* 760 */                       boolean zContentEquals = IOUtils.contentEquals(input3, input4);
/* 763 */                       IOUtils.closeQuietly(input3);
/* 764 */                       IOUtils.closeQuietly(input4);
/* 764 */                       return zContentEquals;
                            } catch (Throwable th) {
                                th = th;
                                input2 = input4;
                                input1 = input3;
                                IOUtils.closeQuietly(input1);
/* 764 */                       IOUtils.closeQuietly(input2);
                                throw th;
                            }
                        } catch (Throwable th2) {
/* 763 */                   th = th2;
                            input1 = input3;
                        }
                    } catch (Throwable th3) {
/* 763 */               th = th3;
                    }
                }

/* 786 */       public static boolean contentEqualsIgnoreEOL(File file1, File file2, String charsetName) throws Throwable {
                    Reader input1;
/* 787 */           boolean file1Exists = file1.exists();
/* 788 */           if (file1Exists != file2.exists()) {
/* 789 */               return false;
                    }
/* 792 */           if (!file1Exists) {
                        return true;
                    }
/* 797 */           if (file1.isDirectory() || file2.isDirectory()) {
/* 799 */               throw new IOException("Can't compare directories, only files");
                    }
/* 802 */           if (file1.getCanonicalFile().equals(file2.getCanonicalFile())) {
                        return true;
                    }
/* 807 */           Reader input2 = null;
/* 808 */           Reader input3 = null;
                    try {
                        try {
/* 810 */                   if (charsetName == null) {
/* 811 */                       input1 = new InputStreamReader(new FileInputStream(file1));
/* 812 */                       input3 = new InputStreamReader(new FileInputStream(file2));
                                input2 = input1;
                            } else {
/* 814 */                       input1 = new InputStreamReader(new FileInputStream(file1), charsetName);
/* 815 */                       input3 = new InputStreamReader(new FileInputStream(file2), charsetName);
                                input2 = input1;
                            }
/* 817 */                   boolean zContentEqualsIgnoreEOL = IOUtils.contentEqualsIgnoreEOL(input2, input3);
/* 820 */                   IOUtils.closeQuietly(input2);
/* 821 */                   IOUtils.closeQuietly(input3);
/* 821 */                   return zContentEqualsIgnoreEOL;
                        } catch (Throwable th) {
/* 820 */                   th = th;
                            input2 = input1;
                            IOUtils.closeQuietly(input2);
/* 821 */                   IOUtils.closeQuietly(input3);
                            throw th;
                        }
                    } catch (Throwable th2) {
/* 820 */               th = th2;
                        IOUtils.closeQuietly(input2);
/* 821 */               IOUtils.closeQuietly(input3);
                        throw th;
                    }
                }

/* 840 */       public static File toFile(URL url) {
/* 841 */           if (url == null || !CommonUtil.FILE.equalsIgnoreCase(url.getProtocol())) {
/* 842 */               return null;
                    }
/* 844 */           String filename = url.getFile().replace('/', File.separatorChar);
/* 846 */           return new File(decodeUrl(filename));
                }

/* 864 */       static String decodeUrl(String url) {
/* 866 */           if (url == null || url.indexOf(37) < 0) {
/* 865 */               return url;
                    }
/* 867 */           int n = url.length();
/* 868 */           StringBuffer buffer = new StringBuffer();
/* 869 */           ByteBuffer bytes = ByteBuffer.allocate(n);
/* 870 */           int i = 0;
                    while (i < n) {
/* 871 */               if (url.charAt(i) == '%') {
                            do {
                                try {
/* 874 */                           byte octet = (byte) Integer.parseInt(url.substring(i + 1, i + 3), 16);
/* 875 */                           bytes.put(octet);
/* 876 */                           i += 3;
/* 877 */                           if (i >= n) {
                                        break;
                                    }
                                } catch (RuntimeException e) {
/* 883 */                           if (bytes.position() > 0) {
/* 884 */                               bytes.flip();
/* 885 */                               buffer.append(UTF8.decode(bytes).toString());
/* 886 */                               bytes.clear();
                                    }
                                    buffer.append(url.charAt(i));
/* 890 */                           i++;
                                } catch (Throwable th) {
                                    if (bytes.position() > 0) {
/* 884 */                               bytes.flip();
/* 885 */                               buffer.append(UTF8.decode(bytes).toString());
/* 886 */                               bytes.clear();
                                    }
                                    throw th;
                                }
                            } while (url.charAt(i) == '%');
/* 883 */                   if (bytes.position() > 0) {
/* 884 */                       bytes.flip();
/* 885 */                       buffer.append(UTF8.decode(bytes).toString());
/* 886 */                       bytes.clear();
                            }
                        }
                        buffer.append(url.charAt(i));
/* 890 */               i++;
                    }
/* 892 */           String decoded = buffer.toString();
/* 894 */           return decoded;
                }

/* 916 */       public static File[] toFiles(URL[] urls) {
/* 917 */           if (urls == null || urls.length == 0) {
/* 918 */               return EMPTY_FILE_ARRAY;
                    }
/* 920 */           File[] files = new File[urls.length];
                    for (int i = 0; i < urls.length; i++) {
/* 922 */               URL url = urls[i];
/* 923 */               if (url != null) {
/* 924 */                   if (!url.getProtocol().equals(CommonUtil.FILE)) {
/* 925 */                       throw new IllegalArgumentException("URL could not be converted to a File: " + url);
                            }
/* 928 */                   files[i] = toFile(url);
                        }
                    }
/* 931 */           return files;
                }

/* 944 */       public static URL[] toURLs(File[] files) throws IOException {
/* 945 */           URL[] urls = new URL[files.length];
                    for (int i = 0; i < urls.length; i++) {
/* 948 */               urls[i] = files[i].toURI().toURL();
                    }
/* 951 */           return urls;
                }

/* 976 */       public static void copyFileToDirectory(File srcFile, File destDir) throws Throwable {
/* 977 */           copyFileToDirectory(srcFile, destDir, true);
                }

/* 1005 */      public static void copyFileToDirectory(File srcFile, File destDir, boolean preserveFileDate) throws Throwable {
/* 1006 */          if (destDir == null) {
/* 1007 */              throw new NullPointerException("Destination must not be null");
                    }
/* 1009 */          if (destDir.exists() && !destDir.isDirectory()) {
/* 1010 */              throw new IllegalArgumentException("Destination '" + destDir + "' is not a directory");
                    }
/* 1012 */          File destFile = new File(destDir, srcFile.getName());
/* 1013 */          copyFile(srcFile, destFile, preserveFileDate);
                }

/* 1037 */      public static void copyFile(File srcFile, File destFile) throws Throwable {
/* 1038 */          copyFile(srcFile, destFile, true);
                }

/* 1066 */      public static void copyFile(File srcFile, File destFile, boolean preserveFileDate) throws Throwable {
/* 1067 */          if (srcFile == null) {
/* 1068 */              throw new NullPointerException("Source must not be null");
                    }
/* 1070 */          if (destFile == null) {
/* 1071 */              throw new NullPointerException("Destination must not be null");
                    }
/* 1073 */          if (!srcFile.exists()) {
/* 1074 */              throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
                    }
/* 1076 */          if (srcFile.isDirectory()) {
/* 1077 */              throw new IOException("Source '" + srcFile + "' exists but is a directory");
                    }
/* 1079 */          if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath())) {
/* 1080 */              throw new IOException("Source '" + srcFile + "' and destination '" + destFile + "' are the same");
                    }
/* 1082 */          File parentFile = destFile.getParentFile();
/* 1083 */          if (parentFile != null && !parentFile.mkdirs() && !parentFile.isDirectory()) {
/* 1085 */              throw new IOException("Destination '" + parentFile + "' directory cannot be created");
                    }
/* 1088 */          if (destFile.exists() && !destFile.canWrite()) {
/* 1089 */              throw new IOException("Destination '" + destFile + "' exists but is read-only");
                    }
/* 1091 */          doCopyFile(srcFile, destFile, preserveFileDate);
                }

/* 1111 */      public static long copyFile(File input, OutputStream output) throws IOException {
/* 1112 */          FileInputStream fis = new FileInputStream(input);
                    try {
/* 1114 */              long jCopyLarge = IOUtils.copyLarge(fis, output);
/* 1116 */              fis.close();
                        return jCopyLarge;
                    } catch (Throwable th) {
                        fis.close();
                        throw th;
                    }
                }

/* 1128 */      private static void doCopyFile(File srcFile, File destFile, boolean preserveFileDate) throws Throwable {
/* 1129 */          if (destFile.exists() && destFile.isDirectory()) {
/* 1130 */              throw new IOException("Destination '" + destFile + "' exists but is a directory");
                    }
/* 1133 */          FileInputStream fis = null;
/* 1134 */          FileOutputStream fos = null;
/* 1135 */          FileChannel input = null;
/* 1136 */          FileChannel output = null;
                    try {
/* 1138 */              FileInputStream fis2 = new FileInputStream(srcFile);
                        try {
/* 1139 */                  FileOutputStream fos2 = new FileOutputStream(destFile);
                            try {
/* 1140 */                      input = fis2.getChannel();
/* 1141 */                      output = fos2.getChannel();
/* 1142 */                      long size = input.size();
/* 1143 */                      long pos = 0;
                                while (pos < size) {
/* 1146 */                          long count = size - pos > FILE_COPY_BUFFER_SIZE ? FILE_COPY_BUFFER_SIZE : size - pos;
/* 1147 */                          pos += output.transferFrom(input, pos, count);
                                }
/* 1150 */                      IOUtils.closeQuietly(output);
/* 1151 */                      IOUtils.closeQuietly((OutputStream) fos2);
/* 1152 */                      IOUtils.closeQuietly(input);
/* 1153 */                      IOUtils.closeQuietly((InputStream) fis2);
                                if (srcFile.length() != destFile.length()) {
/* 1157 */                          throw new IOException("Failed to copy full contents from '" + srcFile + "' to '" + destFile + "'");
                                }
/* 1160 */                      if (preserveFileDate) {
/* 1161 */                          destFile.setLastModified(srcFile.lastModified());
                                }
                            } catch (Throwable th) {
                                th = th;
                                fos = fos2;
                                fis = fis2;
                                IOUtils.closeQuietly(output);
/* 1151 */                      IOUtils.closeQuietly((OutputStream) fos);
/* 1152 */                      IOUtils.closeQuietly(input);
/* 1153 */                      IOUtils.closeQuietly((InputStream) fis);
                                throw th;
                            }
                        } catch (Throwable th2) {
/* 1150 */                  th = th2;
                            fis = fis2;
                        }
                    } catch (Throwable th3) {
/* 1150 */              th = th3;
                    }
                }

/* 1189 */      public static void copyDirectoryToDirectory(File srcDir, File destDir) throws Throwable {
/* 1190 */          if (srcDir == null) {
/* 1191 */              throw new NullPointerException("Source must not be null");
                    }
/* 1193 */          if (srcDir.exists() && !srcDir.isDirectory()) {
/* 1194 */              throw new IllegalArgumentException("Source '" + destDir + "' is not a directory");
                    }
/* 1196 */          if (destDir == null) {
/* 1197 */              throw new NullPointerException("Destination must not be null");
                    }
/* 1199 */          if (destDir.exists() && !destDir.isDirectory()) {
/* 1200 */              throw new IllegalArgumentException("Destination '" + destDir + "' is not a directory");
                    }
/* 1202 */          copyDirectory(srcDir, new File(destDir, srcDir.getName()), true);
                }

/* 1229 */      public static void copyDirectory(File srcDir, File destDir) throws Throwable {
/* 1230 */          copyDirectory(srcDir, destDir, true);
                }

/* 1260 */      public static void copyDirectory(File srcDir, File destDir, boolean preserveFileDate) throws Throwable {
/* 1261 */          copyDirectory(srcDir, destDir, null, preserveFileDate);
                }

/* 1309 */      public static void copyDirectory(File srcDir, File destDir, FileFilter filter) throws Throwable {
/* 1310 */          copyDirectory(srcDir, destDir, filter, true);
                }

/* 1360 */      public static void copyDirectory(File srcDir, File destDir, FileFilter filter, boolean preserveFileDate) throws Throwable {
/* 1361 */          if (srcDir == null) {
/* 1362 */              throw new NullPointerException("Source must not be null");
                    }
/* 1364 */          if (destDir == null) {
/* 1365 */              throw new NullPointerException("Destination must not be null");
                    }
/* 1367 */          if (!srcDir.exists()) {
/* 1368 */              throw new FileNotFoundException("Source '" + srcDir + "' does not exist");
                    }
/* 1370 */          if (!srcDir.isDirectory()) {
/* 1371 */              throw new IOException("Source '" + srcDir + "' exists but is not a directory");
                    }
/* 1373 */          if (srcDir.getCanonicalPath().equals(destDir.getCanonicalPath())) {
/* 1374 */              throw new IOException("Source '" + srcDir + "' and destination '" + destDir + "' are the same");
                    }
/* 1378 */          List<String> exclusionList = null;
/* 1379 */          if (destDir.getCanonicalPath().startsWith(srcDir.getCanonicalPath())) {
/* 1380 */              File[] srcFiles = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
/* 1381 */              if (srcFiles != null && srcFiles.length > 0) {
/* 1382 */                  exclusionList = new ArrayList<>(srcFiles.length);
/* 1383 */                  File[] arr$ = srcFiles;
                            for (File srcFile : arr$) {
/* 1384 */                      File copiedFile = new File(destDir, srcFile.getName());
/* 1385 */                      exclusionList.add(copiedFile.getCanonicalPath());
                            }
                        }
                    }
/* 1389 */          doCopyDirectory(srcDir, destDir, filter, preserveFileDate, exclusionList);
                }

/* 1405 */      private static void doCopyDirectory(File srcDir, File destDir, FileFilter filter, boolean preserveFileDate, List<String> exclusionList) throws Throwable {
/* 1406 */          File[] srcFiles = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
/* 1407 */          if (srcFiles == null) {
/* 1408 */              throw new IOException("Failed to list contents of " + srcDir);
                    }
/* 1410 */          if (destDir.exists()) {
/* 1411 */              if (!destDir.isDirectory()) {
/* 1412 */                  throw new IOException("Destination '" + destDir + "' exists but is not a directory");
                        }
                    } else if (!destDir.mkdirs() && !destDir.isDirectory()) {
/* 1416 */              throw new IOException("Destination '" + destDir + "' directory cannot be created");
                    }
/* 1419 */          if (!destDir.canWrite()) {
/* 1420 */              throw new IOException("Destination '" + destDir + "' cannot be written to");
                    }
/* 1422 */          File[] arr$ = srcFiles;
                    for (File srcFile : arr$) {
/* 1423 */              File dstFile = new File(destDir, srcFile.getName());
/* 1424 */              if (exclusionList == null || !exclusionList.contains(srcFile.getCanonicalPath())) {
/* 1425 */                  if (srcFile.isDirectory()) {
/* 1426 */                      doCopyDirectory(srcFile, dstFile, filter, preserveFileDate, exclusionList);
                            } else {
/* 1428 */                      doCopyFile(srcFile, dstFile, preserveFileDate);
                            }
                        }
                    }
/* 1434 */          if (preserveFileDate) {
/* 1435 */              destDir.setLastModified(srcDir.lastModified());
                    }
                }

/* 1459 */      public static void copyURLToFile(URL source, File destination) throws IOException {
/* 1460 */          InputStream input = source.openStream();
/* 1461 */          copyInputStreamToFile(input, destination);
                }

/* 1485 */      public static void copyURLToFile(URL source, File destination, int connectionTimeout, int readTimeout) throws IOException {
/* 1486 */          URLConnection connection = source.openConnection();
/* 1487 */          connection.setConnectTimeout(connectionTimeout);
/* 1488 */          connection.setReadTimeout(readTimeout);
/* 1489 */          InputStream input = connection.getInputStream();
/* 1490 */          copyInputStreamToFile(input, destination);
                }

/* 1509 */      public static void copyInputStreamToFile(InputStream source, File destination) throws IOException {
                    try {
/* 1510 */              FileOutputStream output = openOutputStream(destination);
                        try {
/* 1512 */                  IOUtils.copy(source, output);
/* 1513 */                  output.close();
/* 1515 */                  IOUtils.closeQuietly((OutputStream) output);
/* 1518 */                  IOUtils.closeQuietly(source);
                        } catch (Throwable th) {
                            IOUtils.closeQuietly((OutputStream) output);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        IOUtils.closeQuietly(source);
                        throw th2;
                    }
                }

/* 1529 */      public static void deleteDirectory(File directory) throws IOException {
/* 1530 */          if (directory.exists()) {
/* 1534 */              if (!isSymlink(directory)) {
/* 1535 */                  cleanDirectory(directory);
                        }
/* 1538 */              if (!directory.delete()) {
/* 1539 */                  String message = "Unable to delete directory " + directory + ".";
/* 1541 */                  throw new IOException(message);
                        }
                    }
                }

/* 1560 */      public static boolean deleteQuietly(File file) {
/* 1561 */          if (file == null) {
                        return false;
                    }
                    try {
/* 1565 */              if (file.isDirectory()) {
/* 1566 */                  cleanDirectory(file);
                        }
                    } catch (Exception e) {
                    }
                    try {
/* 1572 */              return file.delete();
                    } catch (Exception e2) {
                        return false;
                    }
                }

/* 1604 */      public static boolean directoryContains(File directory, File child) throws IOException {
/* 1605 */          if (directory == null) {
/* 1606 */              throw new IllegalArgumentException("Directory must not be null");
                    }
/* 1609 */          if (!directory.isDirectory()) {
/* 1610 */              throw new IllegalArgumentException("Not a directory: " + directory);
                    }
/* 1613 */          if (child == null || !directory.exists() || !child.exists()) {
                        return false;
                    }
/* 1622 */          String canonicalParent = directory.getCanonicalPath();
/* 1623 */          String canonicalChild = child.getCanonicalPath();
/* 1625 */          return FilenameUtils.directoryContains(canonicalParent, canonicalChild);
                }

/* 1634 */      public static void cleanDirectory(File directory) throws IOException {
/* 1635 */          if (!directory.exists()) {
/* 1636 */              String message = directory + " does not exist";
/* 1637 */              throw new IllegalArgumentException(message);
                    }
/* 1640 */          if (!directory.isDirectory()) {
/* 1641 */              String message2 = directory + " is not a directory";
/* 1642 */              throw new IllegalArgumentException(message2);
                    }
/* 1645 */          File[] files = directory.listFiles();
/* 1646 */          if (files == null) {
/* 1647 */              throw new IOException("Failed to list contents of " + directory);
                    }
/* 1650 */          IOException exception = null;
                    for (File file : files) {
                        try {
/* 1653 */                  forceDelete(file);
                        } catch (IOException ioe) {
/* 1655 */                  exception = ioe;
                        }
                    }
/* 1659 */          if (exception != null) {
/* 1660 */              throw exception;
                    }
                }

/* 1676 */      public static boolean waitFor(File file, int seconds) {
/* 1677 */          int timeout = 0;
/* 1678 */          int tick = 0;
/* 1679 */          while (!file.exists()) {
/* 1680 */              int tick2 = tick + 1;
                        if (tick >= 10) {
/* 1681 */                  tick = 0;
/* 1682 */                  int timeout2 = timeout + 1;
                            if (timeout > seconds) {
/* 1683 */                      return false;
                            }
                            timeout = timeout2;
                        } else {
                            tick = tick2;
                        }
                        try {
/* 1687 */                  Thread.sleep(100L);
                        } catch (InterruptedException e) {
                        } catch (Exception e2) {
                        }
                    }
/* 1694 */          return true;
                }

/* 1708 */      public static String readFileToString(File file, Charset encoding) throws IOException {
/* 1709 */          InputStream in = null;
                    try {
/* 1711 */              in = openInputStream(file);
/* 1712 */              String string = IOUtils.toString(in, Charsets.toCharset(encoding));
/* 1714 */              IOUtils.closeQuietly(in);
                        return string;
                    } catch (Throwable th) {
                        IOUtils.closeQuietly(in);
                        throw th;
                    }
                }

/* 1733 */      public static String readFileToString(File file, String encoding) throws IOException {
/* 1734 */          return readFileToString(file, Charsets.toCharset(encoding));
                }

/* 1747 */      public static String readFileToString(File file) throws IOException {
/* 1748 */          return readFileToString(file, Charset.defaultCharset());
                }

/* 1760 */      public static byte[] readFileToByteArray(File file) throws IOException {
/* 1761 */          InputStream in = null;
                    try {
/* 1763 */              in = openInputStream(file);
/* 1764 */              byte[] byteArray = IOUtils.toByteArray(in, file.length());
/* 1766 */              IOUtils.closeQuietly(in);
                        return byteArray;
                    } catch (Throwable th) {
                        IOUtils.closeQuietly(in);
                        throw th;
                    }
                }

/* 1780 */      public static List<String> readLines(File file, Charset encoding) throws IOException {
/* 1781 */          InputStream in = null;
                    try {
/* 1783 */              in = openInputStream(file);
/* 1784 */              List<String> lines = IOUtils.readLines(in, Charsets.toCharset(encoding));
/* 1786 */              IOUtils.closeQuietly(in);
                        return lines;
                    } catch (Throwable th) {
                        IOUtils.closeQuietly(in);
                        throw th;
                    }
                }

/* 1805 */      public static List<String> readLines(File file, String encoding) throws IOException {
/* 1806 */          return readLines(file, Charsets.toCharset(encoding));
                }

/* 1818 */      public static List<String> readLines(File file) throws IOException {
/* 1819 */          return readLines(file, Charset.defaultCharset());
                }

/* 1853 */      public static LineIterator lineIterator(File file, String encoding) throws IOException {
/* 1854 */          InputStream in = null;
                    try {
/* 1856 */              in = openInputStream(file);
/* 1857 */              return IOUtils.lineIterator(in, encoding);
                    } catch (IOException ex) {
/* 1859 */              IOUtils.closeQuietly(in);
/* 1860 */              throw ex;
                    } catch (RuntimeException ex2) {
/* 1862 */              IOUtils.closeQuietly(in);
/* 1863 */              throw ex2;
                    }
                }

/* 1876 */      public static LineIterator lineIterator(File file) throws IOException {
/* 1877 */          return lineIterator(file, null);
                }

/* 1894 */      public static void writeStringToFile(File file, String data, Charset encoding) throws IOException {
/* 1895 */          writeStringToFile(file, data, encoding, false);
                }

/* 1910 */      public static void writeStringToFile(File file, String data, String encoding) throws IOException {
/* 1911 */          writeStringToFile(file, data, encoding, false);
                }

/* 1925 */      public static void writeStringToFile(File file, String data, Charset encoding, boolean append) throws IOException {
/* 1926 */          OutputStream out = null;
                    try {
/* 1928 */              out = openOutputStream(file, append);
/* 1929 */              IOUtils.write(data, out, encoding);
/* 1930 */              out.close();
/* 1932 */              IOUtils.closeQuietly(out);
                    } catch (Throwable th) {
                        IOUtils.closeQuietly(out);
                        throw th;
                    }
                }

/* 1950 */      public static void writeStringToFile(File file, String data, String encoding, boolean append) throws IOException {
/* 1951 */          writeStringToFile(file, data, Charsets.toCharset(encoding), append);
                }

/* 1961 */      public static void writeStringToFile(File file, String data) throws IOException {
/* 1962 */          writeStringToFile(file, data, Charset.defaultCharset(), false);
                }

/* 1975 */      public static void writeStringToFile(File file, String data, boolean append) throws IOException {
/* 1976 */          writeStringToFile(file, data, Charset.defaultCharset(), append);
                }

/* 1987 */      public static void write(File file, CharSequence data) throws IOException {
/* 1988 */          write(file, data, Charset.defaultCharset(), false);
                }

/* 2001 */      public static void write(File file, CharSequence data, boolean append) throws IOException {
/* 2002 */          write(file, data, Charset.defaultCharset(), append);
                }

/* 2014 */      public static void write(File file, CharSequence data, Charset encoding) throws IOException {
/* 2015 */          write(file, data, encoding, false);
                }

/* 2028 */      public static void write(File file, CharSequence data, String encoding) throws IOException {
/* 2029 */          write(file, data, encoding, false);
                }

/* 2043 */      public static void write(File file, CharSequence data, Charset encoding, boolean append) throws IOException {
/* 2044 */          String str = data == null ? null : data.toString();
/* 2045 */          writeStringToFile(file, str, encoding, append);
                }

/* 2062 */      public static void write(File file, CharSequence data, String encoding, boolean append) throws IOException {
/* 2063 */          write(file, data, Charsets.toCharset(encoding), append);
                }

/* 2077 */      public static void writeByteArrayToFile(File file, byte[] data) throws IOException {
/* 2078 */          writeByteArrayToFile(file, data, false);
                }

/* 2091 */      public static void writeByteArrayToFile(File file, byte[] data, boolean append) throws IOException {
/* 2092 */          OutputStream out = null;
                    try {
/* 2094 */              out = openOutputStream(file, append);
/* 2095 */              out.write(data);
/* 2096 */              out.close();
/* 2098 */              IOUtils.closeQuietly(out);
                    } catch (Throwable th) {
                        IOUtils.closeQuietly(out);
                        throw th;
                    }
                }

/* 2117 */      public static void writeLines(File file, String encoding, Collection<?> lines) throws IOException {
/* 2118 */          writeLines(file, encoding, lines, null, false);
                }

/* 2135 */      public static void writeLines(File file, String encoding, Collection<?> lines, boolean append) throws IOException {
/* 2136 */          writeLines(file, encoding, lines, null, append);
                }

/* 2149 */      public static void writeLines(File file, Collection<?> lines) throws IOException {
/* 2150 */          writeLines(file, null, lines, null, false);
                }

/* 2165 */      public static void writeLines(File file, Collection<?> lines, boolean append) throws IOException {
/* 2166 */          writeLines(file, null, lines, null, append);
                }

/* 2186 */      public static void writeLines(File file, String encoding, Collection<?> lines, String lineEnding) throws IOException {
/* 2187 */          writeLines(file, encoding, lines, lineEnding, false);
                }

/* 2206 */      public static void writeLines(File file, String encoding, Collection<?> lines, String lineEnding, boolean append) throws IOException {
/* 2207 */          FileOutputStream out = null;
                    try {
/* 2209 */              out = openOutputStream(file, append);
/* 2210 */              BufferedOutputStream buffer = new BufferedOutputStream(out);
/* 2211 */              IOUtils.writeLines(lines, lineEnding, buffer, encoding);
/* 2212 */              buffer.flush();
/* 2213 */              out.close();
/* 2215 */              IOUtils.closeQuietly((OutputStream) out);
                    } catch (Throwable th) {
                        IOUtils.closeQuietly((OutputStream) out);
                        throw th;
                    }
                }

/* 2230 */      public static void writeLines(File file, Collection<?> lines, String lineEnding) throws IOException {
/* 2231 */          writeLines(file, null, lines, lineEnding, false);
                }

/* 2248 */      public static void writeLines(File file, Collection<?> lines, String lineEnding, boolean append) throws IOException {
/* 2249 */          writeLines(file, null, lines, lineEnding, append);
                }

/* 2268 */      public static void forceDelete(File file) throws IOException {
/* 2269 */          if (file.isDirectory()) {
/* 2270 */              deleteDirectory(file);
/* 2282 */              return;
                    }
/* 2272 */          boolean filePresent = file.exists();
/* 2273 */          if (!file.delete()) {
/* 2274 */              if (!filePresent) {
/* 2275 */                  throw new FileNotFoundException("File does not exist: " + file);
                        }
/* 2277 */              String message = "Unable to delete file: " + file;
/* 2279 */              throw new IOException(message);
                    }
                }

/* 2292 */      public static void forceDeleteOnExit(File file) throws IOException {
/* 2293 */          if (file.isDirectory()) {
/* 2294 */              deleteDirectoryOnExit(file);
                    } else {
/* 2296 */              file.deleteOnExit();
                    }
                }

/* 2307 */      private static void deleteDirectoryOnExit(File directory) throws IOException {
/* 2308 */          if (directory.exists()) {
/* 2312 */              directory.deleteOnExit();
/* 2313 */              if (!isSymlink(directory)) {
/* 2314 */                  cleanDirectoryOnExit(directory);
                        }
                    }
                }

/* 2325 */      private static void cleanDirectoryOnExit(File directory) throws IOException {
/* 2326 */          if (!directory.exists()) {
/* 2327 */              String message = directory + " does not exist";
/* 2328 */              throw new IllegalArgumentException(message);
                    }
/* 2331 */          if (!directory.isDirectory()) {
/* 2332 */              String message2 = directory + " is not a directory";
/* 2333 */              throw new IllegalArgumentException(message2);
                    }
/* 2336 */          File[] files = directory.listFiles();
/* 2337 */          if (files == null) {
/* 2338 */              throw new IOException("Failed to list contents of " + directory);
                    }
/* 2341 */          IOException exception = null;
                    for (File file : files) {
                        try {
/* 2344 */                  forceDeleteOnExit(file);
                        } catch (IOException ioe) {
/* 2346 */                  exception = ioe;
                        }
                    }
/* 2350 */          if (exception != null) {
/* 2351 */              throw exception;
                    }
                }

/* 2366 */      public static void forceMkdir(File directory) throws IOException {
/* 2367 */          if (directory.exists()) {
/* 2368 */              if (!directory.isDirectory()) {
/* 2369 */                  String message = "File " + directory + " exists and is not a directory. Unable to create directory.";
/* 2374 */                  throw new IOException(message);
                        }
                    } else if (!directory.mkdirs() && !directory.isDirectory()) {
/* 2382 */              String message2 = "Unable to create directory " + directory;
/* 2384 */              throw new IOException(message2);
                    }
                }

/* 2410 */      public static long sizeOf(File file) {
/* 2411 */          if (file.exists()) {
                        return file.isDirectory() ? sizeOfDirectory(file) : file.length();
                    }
/* 2412 */          String message = file + " does not exist";
/* 2413 */          throw new IllegalArgumentException(message);
                }

/* 2443 */      public static BigInteger sizeOfAsBigInteger(File file) {
/* 2444 */          if (file.exists()) {
                        return file.isDirectory() ? sizeOfDirectoryAsBigInteger(file) : BigInteger.valueOf(file.length());
                    }
/* 2445 */          String message = file + " does not exist";
/* 2446 */          throw new IllegalArgumentException(message);
                }

/* 2467 */      public static long sizeOfDirectory(File directory) {
/* 2468 */          checkDirectory(directory);
/* 2470 */          File[] files = directory.listFiles();
/* 2471 */          if (files == null) {
                        return 0L;
                    }
/* 2474 */          long size = 0;
                    for (File file : files) {
                        try {
/* 2478 */                  if (isSymlink(file)) {
                                continue;
                            } else {
/* 2479 */                      size += sizeOf(file);
                                if (size < 0) {
/* 2489 */                          return size;
                                }
                            }
                        } catch (IOException e) {
                        }
                    }
/* 2489 */          return size;
                }

/* 2502 */      public static BigInteger sizeOfDirectoryAsBigInteger(File directory) {
/* 2503 */          checkDirectory(directory);
/* 2505 */          File[] files = directory.listFiles();
/* 2506 */          if (files == null) {
/* 2507 */              return BigInteger.ZERO;
                    }
/* 2509 */          BigInteger size = BigInteger.ZERO;
                    for (File file : files) {
                        try {
/* 2513 */                  if (!isSymlink(file)) {
/* 2514 */                      size = size.add(BigInteger.valueOf(sizeOf(file)));
                            }
                        } catch (IOException e) {
                        }
                    }
/* 2521 */          return size;
                }

/* 2530 */      private static void checkDirectory(File directory) {
/* 2531 */          if (!directory.exists()) {
/* 2532 */              throw new IllegalArgumentException(directory + " does not exist");
                    }
/* 2534 */          if (!directory.isDirectory()) {
/* 2535 */              throw new IllegalArgumentException(directory + " is not a directory");
                    }
                }

/* 2553 */      public static boolean isFileNewer(File file, File reference) {
/* 2554 */          if (reference == null) {
/* 2555 */              throw new IllegalArgumentException("No specified reference file");
                    }
/* 2557 */          if (!reference.exists()) {
/* 2558 */              throw new IllegalArgumentException("The reference file '" + reference + "' doesn't exist");
                    }
/* 2561 */          return isFileNewer(file, reference.lastModified());
                }

/* 2576 */      public static boolean isFileNewer(File file, Date date) {
/* 2577 */          if (date == null) {
/* 2578 */              throw new IllegalArgumentException("No specified date");
                    }
/* 2580 */          return isFileNewer(file, date.getTime());
                }

/* 2595 */      public static boolean isFileNewer(File file, long timeMillis) {
/* 2596 */          if (file == null) {
/* 2597 */              throw new IllegalArgumentException("No specified file");
                    }
                    return file.exists() && file.lastModified() > timeMillis;
                }

/* 2620 */      public static boolean isFileOlder(File file, File reference) {
/* 2621 */          if (reference == null) {
/* 2622 */              throw new IllegalArgumentException("No specified reference file");
                    }
/* 2624 */          if (!reference.exists()) {
/* 2625 */              throw new IllegalArgumentException("The reference file '" + reference + "' doesn't exist");
                    }
/* 2628 */          return isFileOlder(file, reference.lastModified());
                }

/* 2643 */      public static boolean isFileOlder(File file, Date date) {
/* 2644 */          if (date == null) {
/* 2645 */              throw new IllegalArgumentException("No specified date");
                    }
/* 2647 */          return isFileOlder(file, date.getTime());
                }

/* 2662 */      public static boolean isFileOlder(File file, long timeMillis) {
/* 2663 */          if (file == null) {
/* 2664 */              throw new IllegalArgumentException("No specified file");
                    }
                    return file.exists() && file.lastModified() < timeMillis;
                }

/* 2684 */      public static long checksumCRC32(File file) throws Throwable {
/* 2685 */          CRC32 crc = new CRC32();
/* 2686 */          checksum(file, crc);
/* 2687 */          return crc.getValue();
                }

/* 2707 */      public static Checksum checksum(File file, Checksum checksum) throws Throwable {
/* 2708 */          if (file.isDirectory()) {
/* 2709 */              throw new IllegalArgumentException("Checksums can't be computed on directories");
                    }
/* 2711 */          InputStream in = null;
                    try {
/* 2713 */              InputStream in2 = new CheckedInputStream(new FileInputStream(file), checksum);
                        try {
/* 2714 */                  IOUtils.copy(in2, new NullOutputStream());
/* 2716 */                  IOUtils.closeQuietly(in2);
/* 2718 */                  return checksum;
                        } catch (Throwable th) {
                            th = th;
                            in = in2;
                            IOUtils.closeQuietly(in);
                            throw th;
                        }
                    } catch (Throwable th2) {
/* 2716 */              th = th2;
                    }
                }

/* 2734 */      public static void moveDirectory(File srcDir, File destDir) throws Throwable {
/* 2735 */          if (srcDir == null) {
/* 2736 */              throw new NullPointerException("Source must not be null");
                    }
/* 2738 */          if (destDir == null) {
/* 2739 */              throw new NullPointerException("Destination must not be null");
                    }
/* 2741 */          if (!srcDir.exists()) {
/* 2742 */              throw new FileNotFoundException("Source '" + srcDir + "' does not exist");
                    }
/* 2744 */          if (!srcDir.isDirectory()) {
/* 2745 */              throw new IOException("Source '" + srcDir + "' is not a directory");
                    }
/* 2747 */          if (destDir.exists()) {
/* 2748 */              throw new FileExistsException("Destination '" + destDir + "' already exists");
                    }
/* 2750 */          boolean rename = srcDir.renameTo(destDir);
/* 2751 */          if (!rename) {
/* 2752 */              if (destDir.getCanonicalPath().startsWith(srcDir.getCanonicalPath())) {
/* 2753 */                  throw new IOException("Cannot move directory: " + srcDir + " to a subdirectory of itself: " + destDir);
                        }
/* 2755 */              copyDirectory(srcDir, destDir);
/* 2756 */              deleteDirectory(srcDir);
/* 2757 */              if (srcDir.exists()) {
/* 2758 */                  throw new IOException("Failed to delete original directory '" + srcDir + "' after copy to '" + destDir + "'");
                        }
                    }
                }

/* 2777 */      public static void moveDirectoryToDirectory(File src, File destDir, boolean createDestDir) throws Throwable {
/* 2778 */          if (src == null) {
/* 2779 */              throw new NullPointerException("Source must not be null");
                    }
/* 2781 */          if (destDir == null) {
/* 2782 */              throw new NullPointerException("Destination directory must not be null");
                    }
/* 2784 */          if (!destDir.exists() && createDestDir) {
/* 2785 */              destDir.mkdirs();
                    }
/* 2787 */          if (!destDir.exists()) {
/* 2788 */              throw new FileNotFoundException("Destination directory '" + destDir + "' does not exist [createDestDir=" + createDestDir + "]");
                    }
/* 2791 */          if (!destDir.isDirectory()) {
/* 2792 */              throw new IOException("Destination '" + destDir + "' is not a directory");
                    }
/* 2794 */          moveDirectory(src, new File(destDir, src.getName()));
                }

/* 2811 */      public static void moveFile(File srcFile, File destFile) throws Throwable {
/* 2812 */          if (srcFile == null) {
/* 2813 */              throw new NullPointerException("Source must not be null");
                    }
/* 2815 */          if (destFile == null) {
/* 2816 */              throw new NullPointerException("Destination must not be null");
                    }
/* 2818 */          if (!srcFile.exists()) {
/* 2819 */              throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
                    }
/* 2821 */          if (srcFile.isDirectory()) {
/* 2822 */              throw new IOException("Source '" + srcFile + "' is a directory");
                    }
/* 2824 */          if (destFile.exists()) {
/* 2825 */              throw new FileExistsException("Destination '" + destFile + "' already exists");
                    }
/* 2827 */          if (destFile.isDirectory()) {
/* 2828 */              throw new IOException("Destination '" + destFile + "' is a directory");
                    }
/* 2830 */          boolean rename = srcFile.renameTo(destFile);
/* 2831 */          if (!rename) {
/* 2832 */              copyFile(srcFile, destFile);
/* 2833 */              if (!srcFile.delete()) {
/* 2834 */                  deleteQuietly(destFile);
/* 2835 */                  throw new IOException("Failed to delete original file '" + srcFile + "' after copy to '" + destFile + "'");
                        }
                    }
                }

/* 2854 */      public static void moveFileToDirectory(File srcFile, File destDir, boolean createDestDir) throws Throwable {
/* 2855 */          if (srcFile == null) {
/* 2856 */              throw new NullPointerException("Source must not be null");
                    }
/* 2858 */          if (destDir == null) {
/* 2859 */              throw new NullPointerException("Destination directory must not be null");
                    }
/* 2861 */          if (!destDir.exists() && createDestDir) {
/* 2862 */              destDir.mkdirs();
                    }
/* 2864 */          if (!destDir.exists()) {
/* 2865 */              throw new FileNotFoundException("Destination directory '" + destDir + "' does not exist [createDestDir=" + createDestDir + "]");
                    }
/* 2868 */          if (!destDir.isDirectory()) {
/* 2869 */              throw new IOException("Destination '" + destDir + "' is not a directory");
                    }
/* 2871 */          moveFile(srcFile, new File(destDir, srcFile.getName()));
                }

/* 2889 */      public static void moveToDirectory(File src, File destDir, boolean createDestDir) throws Throwable {
/* 2890 */          if (src == null) {
/* 2891 */              throw new NullPointerException("Source must not be null");
                    }
/* 2893 */          if (destDir == null) {
/* 2894 */              throw new NullPointerException("Destination must not be null");
                    }
/* 2896 */          if (!src.exists()) {
/* 2897 */              throw new FileNotFoundException("Source '" + src + "' does not exist");
                    }
/* 2899 */          if (src.isDirectory()) {
/* 2900 */              moveDirectoryToDirectory(src, destDir, createDestDir);
                    } else {
/* 2902 */              moveFileToDirectory(src, destDir, createDestDir);
                    }
                }

/* 2920 */      public static boolean isSymlink(File file) throws IOException {
                    File fileInCanonicalDir;
/* 2921 */          if (file == null) {
/* 2922 */              throw new NullPointerException("File must not be null");
                    }
/* 2924 */          if (FilenameUtils.isSystemWindows()) {
                        return false;
                    }
/* 2928 */          if (file.getParent() == null) {
/* 2929 */              fileInCanonicalDir = file;
                    } else {
/* 2931 */              File canonicalDir = file.getParentFile().getCanonicalFile();
/* 2932 */              fileInCanonicalDir = new File(canonicalDir, file.getName());
                    }
                    return !fileInCanonicalDir.getCanonicalFile().equals(fileInCanonicalDir.getAbsoluteFile());
                }
            }
