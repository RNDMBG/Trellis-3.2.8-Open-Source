            package org.apache.commons.p005io.filefilter;

            import java.io.File;
            import java.io.FileFilter;
            import java.io.FilenameFilter;
            import java.util.ArrayList;
            import java.util.Arrays;
            import java.util.Collection;
            import java.util.Date;
            import java.util.HashSet;
            import java.util.List;
            import java.util.Set;
            import org.apache.commons.p005io.IOCase;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 75 */    public class FileFilterUtils {
                private static final IOFileFilter cvsFilter = notFileFilter(and(directoryFileFilter(), nameFileFilter("CVS")));
                private static final IOFileFilter svnFilter = notFileFilter(and(directoryFileFilter(), nameFileFilter(".svn")));

/* 76 */        public static File[] filter(IOFileFilter filter, File... files) {
/* 77 */            if (filter == null) {
/* 78 */                throw new IllegalArgumentException("file filter is null");
                    }
/* 80 */            if (files == null) {
/* 81 */                return new File[0];
                    }
/* 83 */            List<File> acceptedFiles = new ArrayList<>();
                    for (File file : files) {
/* 85 */                if (file == null) {
/* 86 */                    throw new IllegalArgumentException("file array contains null");
                        }
/* 88 */                if (filter.accept(file)) {
/* 89 */                    acceptedFiles.add(file);
                        }
                    }
/* 92 */            return (File[]) acceptedFiles.toArray(new File[acceptedFiles.size()]);
                }

/* 121 */       public static File[] filter(IOFileFilter filter, Iterable<File> files) {
/* 122 */           List<File> acceptedFiles = filterList(filter, files);
/* 123 */           return (File[]) acceptedFiles.toArray(new File[acceptedFiles.size()]);
                }

/* 151 */       public static List<File> filterList(IOFileFilter filter, Iterable<File> files) {
/* 152 */           return (List) filter(filter, files, new ArrayList());
                }

/* 180 */       public static List<File> filterList(IOFileFilter filter, File... files) {
/* 181 */           File[] acceptedFiles = filter(filter, files);
/* 182 */           return Arrays.asList(acceptedFiles);
                }

/* 211 */       public static Set<File> filterSet(IOFileFilter filter, File... files) {
/* 212 */           File[] acceptedFiles = filter(filter, files);
/* 213 */           return new HashSet(Arrays.asList(acceptedFiles));
                }

/* 242 */       public static Set<File> filterSet(IOFileFilter filter, Iterable<File> files) {
/* 243 */           return (Set) filter(filter, files, new HashSet());
                }

/* 269 */       private static <T extends Collection<File>> T filter(IOFileFilter filter, Iterable<File> files, T acceptedFiles) {
/* 270 */           if (filter == null) {
/* 271 */               throw new IllegalArgumentException("file filter is null");
                    }
/* 273 */           if (files != null) {
                        for (File file : files) {
/* 275 */                   if (file == null) {
/* 276 */                       throw new IllegalArgumentException("file collection contains null");
                            }
/* 278 */                   if (filter.accept(file)) {
/* 279 */                       acceptedFiles.add(file);
                            }
                        }
                    }
/* 283 */           return acceptedFiles;
                }

/* 293 */       public static IOFileFilter prefixFileFilter(String prefix) {
/* 294 */           return new PrefixFileFilter(prefix);
                }

/* 306 */       public static IOFileFilter prefixFileFilter(String prefix, IOCase caseSensitivity) {
/* 307 */           return new PrefixFileFilter(prefix, caseSensitivity);
                }

/* 317 */       public static IOFileFilter suffixFileFilter(String suffix) {
/* 318 */           return new SuffixFileFilter(suffix);
                }

/* 330 */       public static IOFileFilter suffixFileFilter(String suffix, IOCase caseSensitivity) {
/* 331 */           return new SuffixFileFilter(suffix, caseSensitivity);
                }

/* 341 */       public static IOFileFilter nameFileFilter(String name) {
/* 342 */           return new NameFileFilter(name);
                }

/* 354 */       public static IOFileFilter nameFileFilter(String name, IOCase caseSensitivity) {
/* 355 */           return new NameFileFilter(name, caseSensitivity);
                }

/* 364 */       public static IOFileFilter directoryFileFilter() {
/* 365 */           return DirectoryFileFilter.DIRECTORY;
                }

/* 374 */       public static IOFileFilter fileFileFilter() {
/* 375 */           return FileFileFilter.FILE;
                }

                @Deprecated
/* 390 */       public static IOFileFilter andFileFilter(IOFileFilter filter1, IOFileFilter filter2) {
/* 391 */           return new AndFileFilter(filter1, filter2);
                }

                @Deprecated
/* 405 */       public static IOFileFilter orFileFilter(IOFileFilter filter1, IOFileFilter filter2) {
/* 406 */           return new OrFileFilter(filter1, filter2);
                }

/* 420 */       public static IOFileFilter and(IOFileFilter... filters) {
/* 421 */           return new AndFileFilter(toList(filters));
                }

                /* JADX INFO: renamed from: or */
/* 435 */       public static IOFileFilter m2470or(IOFileFilter... filters) {
/* 436 */           return new OrFileFilter(toList(filters));
                }

/* 448 */       public static List<IOFileFilter> toList(IOFileFilter... filters) {
/* 449 */           if (filters == null) {
/* 450 */               throw new IllegalArgumentException("The filters must not be null");
                    }
/* 452 */           List<IOFileFilter> list = new ArrayList<>(filters.length);
                    for (int i = 0; i < filters.length; i++) {
/* 454 */               if (filters[i] == null) {
/* 455 */                   throw new IllegalArgumentException("The filter[" + i + "] is null");
                        }
/* 457 */               list.add(filters[i]);
                    }
/* 459 */           return list;
                }

/* 469 */       public static IOFileFilter notFileFilter(IOFileFilter filter) {
/* 470 */           return new NotFileFilter(filter);
                }

/* 480 */       public static IOFileFilter trueFileFilter() {
/* 481 */           return TrueFileFilter.TRUE;
                }

/* 490 */       public static IOFileFilter falseFileFilter() {
/* 491 */           return FalseFileFilter.FALSE;
                }

/* 503 */       public static IOFileFilter asFileFilter(FileFilter filter) {
/* 504 */           return new DelegateFileFilter(filter);
                }

/* 515 */       public static IOFileFilter asFileFilter(FilenameFilter filter) {
/* 516 */           return new DelegateFileFilter(filter);
                }

/* 529 */       public static IOFileFilter ageFileFilter(long cutoff) {
/* 530 */           return new AgeFileFilter(cutoff);
                }

/* 542 */       public static IOFileFilter ageFileFilter(long cutoff, boolean acceptOlder) {
/* 543 */           return new AgeFileFilter(cutoff, acceptOlder);
                }

/* 555 */       public static IOFileFilter ageFileFilter(Date cutoffDate) {
/* 556 */           return new AgeFileFilter(cutoffDate);
                }

/* 568 */       public static IOFileFilter ageFileFilter(Date cutoffDate, boolean acceptOlder) {
/* 569 */           return new AgeFileFilter(cutoffDate, acceptOlder);
                }

/* 582 */       public static IOFileFilter ageFileFilter(File cutoffReference) {
/* 583 */           return new AgeFileFilter(cutoffReference);
                }

/* 596 */       public static IOFileFilter ageFileFilter(File cutoffReference, boolean acceptOlder) {
/* 597 */           return new AgeFileFilter(cutoffReference, acceptOlder);
                }

/* 609 */       public static IOFileFilter sizeFileFilter(long threshold) {
/* 610 */           return new SizeFileFilter(threshold);
                }

/* 622 */       public static IOFileFilter sizeFileFilter(long threshold, boolean acceptLarger) {
/* 623 */           return new SizeFileFilter(threshold, acceptLarger);
                }

/* 636 */       public static IOFileFilter sizeRangeFileFilter(long minSizeInclusive, long maxSizeInclusive) {
/* 637 */           IOFileFilter minimumFilter = new SizeFileFilter(minSizeInclusive, true);
/* 638 */           IOFileFilter maximumFilter = new SizeFileFilter(1 + maxSizeInclusive, false);
/* 639 */           return new AndFileFilter(minimumFilter, maximumFilter);
                }

/* 657 */       public static IOFileFilter magicNumberFileFilter(String magicNumber) {
/* 658 */           return new MagicNumberFileFilter(magicNumber);
                }

/* 678 */       public static IOFileFilter magicNumberFileFilter(String magicNumber, long offset) {
/* 679 */           return new MagicNumberFileFilter(magicNumber, offset);
                }

/* 697 */       public static IOFileFilter magicNumberFileFilter(byte[] magicNumber) {
/* 698 */           return new MagicNumberFileFilter(magicNumber);
                }

/* 718 */       public static IOFileFilter magicNumberFileFilter(byte[] magicNumber, long offset) {
/* 719 */           return new MagicNumberFileFilter(magicNumber, offset);
                }

/* 740 */       public static IOFileFilter makeCVSAware(IOFileFilter filter) {
                    return filter == null ? cvsFilter : and(filter, cvsFilter);
                }

/* 757 */       public static IOFileFilter makeSVNAware(IOFileFilter filter) {
                    return filter == null ? svnFilter : and(filter, svnFilter);
                }

/* 774 */       public static IOFileFilter makeDirectoryOnly(IOFileFilter filter) {
                    return filter == null ? DirectoryFileFilter.DIRECTORY : new AndFileFilter(DirectoryFileFilter.DIRECTORY, filter);
                }

/* 789 */       public static IOFileFilter makeFileOnly(IOFileFilter filter) {
                    return filter == null ? FileFileFilter.FILE : new AndFileFilter(FileFileFilter.FILE, filter);
                }
            }
