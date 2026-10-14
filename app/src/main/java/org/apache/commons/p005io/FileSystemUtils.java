            package org.apache.commons.p005io;

            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import java.io.BufferedReader;
            import java.io.File;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.InputStreamReader;
            import java.io.OutputStream;
            import java.io.Reader;
            import java.util.ArrayList;
            import java.util.Arrays;
            import java.util.List;
            import java.util.Locale;
            import java.util.StringTokenizer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 46 */    public class FileSystemUtils {

                /* JADX INFO: renamed from: DF */
                private static final String f2536DF;
                private static final int INIT_PROBLEM = -1;
                private static final FileSystemUtils INSTANCE = new FileSystemUtils();

                /* JADX INFO: renamed from: OS */
                private static final int f2537OS;
                private static final int OTHER = 0;
                private static final int POSIX_UNIX = 3;
                private static final int UNIX = 2;
                private static final int WINDOWS = 1;

                static {
                    int os;
/* 69 */            String dfPath = "df";
                    try {
/* 71 */                String osName = System.getProperty("os.name");
/* 72 */                if (osName == null) {
/* 73 */                    throw new IOException("os.name not found");
                        }
/* 75 */                String osName2 = osName.toLowerCase(Locale.ENGLISH);
/* 77 */                if (osName2.indexOf("windows") != -1) {
/* 78 */                    os = 1;
                        } else if (osName2.indexOf("linux") != -1 || osName2.indexOf("mpe/ix") != -1 || osName2.indexOf("freebsd") != -1 || osName2.indexOf("irix") != -1 || osName2.indexOf("digital unix") != -1 || osName2.indexOf("unix") != -1 || osName2.indexOf("mac os x") != -1) {
/* 86 */                    os = 2;
                        } else if (osName2.indexOf("sun os") != -1 || osName2.indexOf("sunos") != -1 || osName2.indexOf("solaris") != -1) {
/* 90 */                    os = 3;
/* 91 */                    dfPath = "/usr/xpg4/bin/df";
                        } else if (osName2.indexOf("hp-ux") != -1 || osName2.indexOf("aix") != -1) {
/* 94 */                    os = 3;
                        } else {
/* 96 */                    os = 0;
                        }
/* 102 */               f2537OS = os;
/* 103 */               f2536DF = dfPath;
                    } catch (Exception e) {
/* 100 */               os = -1;
                    }
                }

                @Deprecated
/* 141 */       public static long freeSpace(String path) throws IOException {
/* 142 */           return INSTANCE.freeSpaceOS(path, f2537OS, false, -1L);
                }

/* 170 */       public static long freeSpaceKb(String path) throws IOException {
/* 171 */           return freeSpaceKb(path, -1L);
                }

/* 199 */       public static long freeSpaceKb(String path, long timeout) throws IOException {
/* 200 */           return INSTANCE.freeSpaceOS(path, f2537OS, true, timeout);
                }

/* 215 */       public static long freeSpaceKb() throws IOException {
/* 216 */           return freeSpaceKb(-1L);
                }

/* 233 */       public static long freeSpaceKb(long timeout) throws IOException {
/* 234 */           return freeSpaceKb(new File(".").getAbsolutePath(), timeout);
                }

/* 258 */       long freeSpaceOS(String path, int os, boolean kb, long timeout) throws IOException {
/* 259 */           if (path == null) {
/* 260 */               throw new IllegalArgumentException("Path must not be empty");
                    }
                    switch (os) {
                        case 0:
/* 270 */                   throw new IllegalStateException("Unsupported operating system");
                        case 1:
                            return kb ? freeSpaceWindows(path, timeout) / FileUtils.ONE_KB : freeSpaceWindows(path, timeout);
                        case 2:
/* 266 */                   return freeSpaceUnix(path, kb, false, timeout);
                        case 3:
/* 268 */                   return freeSpaceUnix(path, kb, true, timeout);
                        default:
/* 272 */                   throw new IllegalStateException("Exception caught when determining operating system");
                    }
                }

/* 287 */       long freeSpaceWindows(String path, long timeout) throws Throwable {
/* 288 */           String path2 = FilenameUtils.normalize(path, false);
/* 289 */           if (path2.length() > 0 && path2.charAt(0) != '\"') {
/* 290 */               path2 = "\"" + path2 + "\"";
                    }
/* 294 */           String[] cmdAttribs = {"cmd.exe", "/C", "dir /a /-c " + path2};
/* 297 */           List<String> lines = performCommand(cmdAttribs, Integer.MAX_VALUE, timeout);
                    for (int i = lines.size() - 1; i >= 0; i--) {
/* 304 */               String line = lines.get(i);
/* 305 */               if (line.length() > 0) {
/* 306 */                   return parseDir(line, path2);
                        }
                    }
/* 310 */           throw new IOException("Command line 'dir /-c' did not return any info for path '" + path2 + "'");
                }

/* 327 */       long parseDir(String line, String path) throws IOException {
/* 328 */           int bytesStart = 0;
/* 329 */           int bytesEnd = 0;
/* 330 */           int j = line.length() - 1;
/* 331 */           while (j >= 0) {
/* 333 */               if (Character.isDigit(line.charAt(j))) {
/* 336 */                   bytesEnd = j + 1;
                            break;
                        }
/* 339 */               j--;
                    }
/* 341 */           while (j >= 0) {
/* 342 */               char c = line.charAt(j);
/* 343 */               if (!Character.isDigit(c) && c != ',' && c != '.') {
/* 346 */                   bytesStart = j + 1;
                            break;
                        }
/* 349 */               j--;
                    }
/* 351 */           if (j < 0) {
/* 352 */               throw new IOException("Command line 'dir /-c' did not return valid info for path '" + path + "'");
                    }
/* 358 */           StringBuilder buf = new StringBuilder(line.substring(bytesStart, bytesEnd));
/* 359 */           int k = 0;
                    while (k < buf.length()) {
/* 360 */               if (buf.charAt(k) == ',' || buf.charAt(k) == '.') {
                            buf.deleteCharAt(k);
/* 361 */                   k--;
                        }
/* 359 */               k++;
                    }
/* 364 */           return parseBytes(buf.toString(), path);
                }

/* 379 */       long freeSpaceUnix(String path, boolean kb, boolean posix, long timeout) throws Throwable {
/* 380 */           if (path.length() == 0) {
/* 381 */               throw new IllegalArgumentException("Path must not be empty");
                    }
/* 385 */           String flags = kb ? "-k" : "-";
/* 389 */           if (posix) {
/* 390 */               flags = flags + "P";
                    }
/* 392 */           String[] cmdAttribs = flags.length() > 1 ? new String[]{f2536DF, flags, path} : new String[]{f2536DF, path};
/* 396 */           List<String> lines = performCommand(cmdAttribs, 3, timeout);
/* 397 */           if (lines.size() < 2) {
/* 399 */               throw new IOException("Command line '" + f2536DF + "' did not return info as expected for path '" + path + "'- response was " + lines);
                    }
/* 403 */           String line2 = lines.get(1);
/* 406 */           StringTokenizer tok = new StringTokenizer(line2, MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
/* 407 */           if (tok.countTokens() < 4) {
/* 409 */               if (tok.countTokens() == 1 && lines.size() >= 3) {
/* 410 */                   String line3 = lines.get(2);
/* 411 */                   tok = new StringTokenizer(line3, MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                        } else {
/* 413 */                   throw new IOException("Command line '" + f2536DF + "' did not return data as expected for path '" + path + "'- check path is valid");
                        }
                    } else {
/* 418 */               tok.nextToken();
                    }
/* 420 */           tok.nextToken();
/* 421 */           tok.nextToken();
/* 422 */           String freeSpace = tok.nextToken();
/* 423 */           return parseBytes(freeSpace, path);
                }

/* 436 */       long parseBytes(String freeSpace, String path) throws IOException {
                    try {
/* 437 */               long bytes = Long.parseLong(freeSpace);
                        if (bytes < 0) {
/* 439 */                   throw new IOException("Command line '" + f2536DF + "' did not find free space in response for path '" + path + "'- check path is valid");
                        }
/* 443 */               return bytes;
                    } catch (NumberFormatException ex) {
/* 446 */               throw new IOExceptionWithCause("Command line '" + f2536DF + "' did not return numeric data as expected for path '" + path + "'- check path is valid", ex);
                    }
                }

/* 471 */       List<String> performCommand(String[] cmdAttribs, int max, long timeout) throws Throwable {
/* 472 */           List<String> lines = new ArrayList<>(20);
/* 473 */           Process proc = null;
/* 474 */           InputStream in = null;
/* 475 */           OutputStream out = null;
/* 476 */           InputStream err = null;
/* 477 */           BufferedReader inr = null;
                    try {
                        try {
/* 480 */                   Thread monitor = ThreadMonitor.start(timeout);
/* 482 */                   proc = openProcess(cmdAttribs);
/* 483 */                   in = proc.getInputStream();
/* 484 */                   out = proc.getOutputStream();
/* 485 */                   err = proc.getErrorStream();
/* 486 */                   BufferedReader inr2 = new BufferedReader(new InputStreamReader(in));
                            try {
/* 488 */                       for (String line = inr2.readLine(); line != null && lines.size() < max; line = inr2.readLine()) {
/* 490 */                           lines.add(line.toLowerCase(Locale.ENGLISH).trim());
                                }
/* 494 */                       proc.waitFor();
/* 496 */                       ThreadMonitor.stop(monitor);
/* 498 */                       if (proc.exitValue() != 0) {
/* 500 */                           throw new IOException("Command line returned OS error code '" + proc.exitValue() + "' for command " + Arrays.asList(cmdAttribs));
                                }
/* 504 */                       if (lines.isEmpty()) {
/* 506 */                           throw new IOException("Command line did not return any info for command " + Arrays.asList(cmdAttribs));
                                }
                                IOUtils.closeQuietly(in);
/* 518 */                       IOUtils.closeQuietly(out);
/* 519 */                       IOUtils.closeQuietly(err);
/* 520 */                       IOUtils.closeQuietly((Reader) inr2);
/* 521 */                       if (proc != null) {
/* 522 */                           proc.destroy();
                                }
                                return lines;
                            } catch (InterruptedException e) {
/* 512 */                       ex = e;
/* 513 */                       throw new IOExceptionWithCause("Command line threw an InterruptedException for command " + Arrays.asList(cmdAttribs) + " timeout=" + timeout, ex);
                            } catch (Throwable th) {
/* 517 */                       th = th;
                                inr = inr2;
                                IOUtils.closeQuietly(in);
/* 518 */                       IOUtils.closeQuietly(out);
/* 519 */                       IOUtils.closeQuietly(err);
/* 520 */                       IOUtils.closeQuietly((Reader) inr);
/* 521 */                       if (proc != null) {
/* 522 */                           proc.destroy();
                                }
                                throw th;
                            }
                        } catch (Throwable th2) {
/* 517 */                   th = th2;
                        }
                    } catch (InterruptedException e2) {
/* 512 */               ex = e2;
                    }
                }

/* 534 */       Process openProcess(String[] cmdAttribs) throws IOException {
/* 535 */           return Runtime.getRuntime().exec(cmdAttribs);
                }
            }
