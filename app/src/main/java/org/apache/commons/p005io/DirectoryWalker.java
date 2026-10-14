            package org.apache.commons.p005io;

            import java.io.File;
            import java.io.FileFilter;
            import java.io.IOException;
            import java.util.Collection;
            import org.apache.commons.p005io.filefilter.FileFilterUtils;
            import org.apache.commons.p005io.filefilter.IOFileFilter;
            import org.apache.commons.p005io.filefilter.TrueFileFilter;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 264 */   public abstract class DirectoryWalker<T> {
                private final int depthLimit;
                private final FileFilter filter;

/* 265 */       protected DirectoryWalker() {
/* 266 */           this(null, -1);
                }

/* 280 */       protected DirectoryWalker(FileFilter filter, int depthLimit) {
/* 282 */           this.filter = filter;
/* 283 */           this.depthLimit = depthLimit;
                }

/* 299 */       protected DirectoryWalker(IOFileFilter directoryFilter, IOFileFilter fileFilter, int depthLimit) {
/* 301 */           if (directoryFilter == null && fileFilter == null) {
/* 302 */               this.filter = null;
                    } else {
                        this.filter = FileFilterUtils.m2470or(FileFilterUtils.makeDirectoryOnly(directoryFilter == null ? TrueFileFilter.TRUE : directoryFilter), FileFilterUtils.makeFileOnly(fileFilter == null ? TrueFileFilter.TRUE : fileFilter));
                    }
/* 310 */           this.depthLimit = depthLimit;
                }

/* 329 */       protected final void walk(File startDirectory, Collection<T> results) throws IOException {
/* 330 */           if (startDirectory == null) {
/* 331 */               throw new NullPointerException("Start Directory is null");
                    }
                    try {
/* 334 */               handleStart(startDirectory, results);
/* 335 */               walk(startDirectory, 0, results);
/* 336 */               handleEnd(results);
                    } catch (CancelException cancel) {
/* 338 */               handleCancelled(startDirectory, results, cancel);
                    }
                }

/* 350 */       private void walk(File directory, int depth, Collection<T> results) throws IOException {
/* 351 */           checkIfCancelled(directory, depth, results);
/* 352 */           if (handleDirectory(directory, depth, results)) {
/* 353 */               handleDirectoryStart(directory, depth, results);
/* 354 */               int childDepth = depth + 1;
/* 355 */               if (this.depthLimit < 0 || childDepth <= this.depthLimit) {
/* 356 */                   checkIfCancelled(directory, depth, results);
/* 358 */                   File[] childFiles = filterDirectoryContents(directory, depth, this.filter == null ? directory.listFiles() : directory.listFiles(this.filter));
/* 359 */                   if (childFiles == null) {
/* 360 */                       handleRestricted(directory, childDepth, results);
                            } else {
                                for (File childFile : childFiles) {
/* 363 */                           if (childFile.isDirectory()) {
/* 364 */                               walk(childFile, childDepth, results);
                                    } else {
/* 366 */                               checkIfCancelled(childFile, childDepth, results);
/* 367 */                               handleFile(childFile, childDepth, results);
/* 368 */                               checkIfCancelled(childFile, childDepth, results);
                                    }
                                }
                            }
                        }
/* 373 */               handleDirectoryEnd(directory, depth, results);
                    }
/* 375 */           checkIfCancelled(directory, depth, results);
                }

/* 393 */       protected final void checkIfCancelled(File file, int depth, Collection<T> results) throws IOException {
/* 394 */           if (handleIsCancelled(file, depth, results)) {
/* 395 */               throw new CancelException(file, depth);
                    }
                }

/* 436 */       protected boolean handleIsCancelled(File file, int depth, Collection<T> results) throws IOException {
/* 437 */           return false;
                }

/* 455 */       protected void handleCancelled(File startDirectory, Collection<T> results, CancelException cancel) throws IOException {
/* 456 */           throw cancel;
                }

/* 470 */       protected void handleStart(File startDirectory, Collection<T> results) throws IOException {
                }

/* 489 */       protected boolean handleDirectory(File directory, int depth, Collection<T> results) throws IOException {
/* 490 */           return true;
                }

/* 504 */       protected void handleDirectoryStart(File directory, int depth, Collection<T> results) throws IOException {
                }

/* 519 */       protected File[] filterDirectoryContents(File directory, int depth, File[] files) throws IOException {
/* 520 */           return files;
                }

/* 534 */       protected void handleFile(File file, int depth, Collection<T> results) throws IOException {
                }

/* 548 */       protected void handleRestricted(File directory, int depth, Collection<T> results) throws IOException {
                }

/* 562 */       protected void handleDirectoryEnd(File directory, int depth, Collection<T> results) throws IOException {
                }

/* 574 */       protected void handleEnd(Collection<T> results) throws IOException {
                }

/* 598 */       public static class CancelException extends IOException {
                    private static final long serialVersionUID = 1347339620135041008L;
                    private final int depth;
                    private final File file;

/* 599 */           public CancelException(File file, int depth) {
/* 600 */               this("Operation Cancelled", file, depth);
                    }

/* 612 */           public CancelException(String message, File file, int depth) {
/* 613 */               super(message);
/* 614 */               this.file = file;
/* 615 */               this.depth = depth;
                    }

/* 623 */           public File getFile() {
/* 624 */               return this.file;
                    }

/* 632 */           public int getDepth() {
/* 633 */               return this.depth;
                    }
                }
            }
