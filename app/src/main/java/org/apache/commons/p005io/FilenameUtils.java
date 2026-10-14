            package org.apache.commons.p005io;

            import java.io.File;
            import java.io.IOException;
            import java.util.ArrayList;
            import java.util.Collection;
            import java.util.Stack;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 93 */    public class FilenameUtils {
                private static final char OTHER_SEPARATOR;
                private static final char UNIX_SEPARATOR = '/';
                private static final char WINDOWS_SEPARATOR = '\\';
                public static final char EXTENSION_SEPARATOR = '.';
                public static final String EXTENSION_SEPARATOR_STR = Character.toString(EXTENSION_SEPARATOR);
                private static final char SYSTEM_SEPARATOR = File.separatorChar;

                static {
/* 117 */           if (isSystemWindows()) {
/* 118 */               OTHER_SEPARATOR = '/';
                    } else {
/* 120 */               OTHER_SEPARATOR = '\\';
                    }
                }

/* 137 */       static boolean isSystemWindows() {
                    return SYSTEM_SEPARATOR == '\\';
                }

/* 148 */       private static boolean isSeparator(char ch) {
                    return ch == '/' || ch == '\\';
                }

/* 193 */       public static String normalize(String filename) {
/* 194 */           return doNormalize(filename, SYSTEM_SEPARATOR, true);
                }

/* 240 */       public static String normalize(String filename, boolean unixSeparator) {
/* 241 */           char separator = unixSeparator ? '/' : '\\';
/* 242 */           return doNormalize(filename, separator, true);
                }

/* 287 */       public static String normalizeNoEndSeparator(String filename) {
/* 288 */           return doNormalize(filename, SYSTEM_SEPARATOR, false);
                }

/* 334 */       public static String normalizeNoEndSeparator(String filename, boolean unixSeparator) {
/* 335 */           char separator = unixSeparator ? '/' : '\\';
/* 336 */           return doNormalize(filename, separator, false);
                }

/* 347 */       private static String doNormalize(String filename, char separator, boolean keepSeparator) {
/* 348 */           if (filename == null) {
/* 349 */               return null;
                    }
/* 351 */           int size = filename.length();
/* 352 */           if (size != 0) {
/* 355 */               int prefix = getPrefixLength(filename);
/* 356 */               if (prefix < 0) {
/* 357 */                   return null;
                        }
/* 360 */               char[] array = new char[size + 2];
/* 361 */               filename.getChars(0, filename.length(), array, 0);
/* 364 */               char otherSeparator = separator == SYSTEM_SEPARATOR ? OTHER_SEPARATOR : SYSTEM_SEPARATOR;
                        for (int i = 0; i < array.length; i++) {
/* 366 */                   if (array[i] == otherSeparator) {
/* 367 */                       array[i] = separator;
                            }
                        }
/* 372 */               boolean lastIsDirectory = true;
/* 373 */               if (array[size - 1] != separator) {
                            array[size] = separator;
/* 375 */                   lastIsDirectory = false;
/* 374 */                   size++;
                        }
/* 379 */               int i2 = prefix + 1;
                        while (i2 < size) {
/* 380 */                   if (array[i2] == separator && array[i2 - 1] == separator) {
/* 381 */                       System.arraycopy(array, i2, array, i2 - 1, size - i2);
/* 382 */                       size--;
/* 383 */                       i2--;
                            }
/* 379 */                   i2++;
                        }
/* 388 */               int i3 = prefix + 1;
                        while (i3 < size) {
/* 389 */                   if (array[i3] == separator && array[i3 - 1] == '.' && (i3 == prefix + 1 || array[i3 - 2] == separator)) {
/* 391 */                       if (i3 == size - 1) {
/* 392 */                           lastIsDirectory = true;
                                }
/* 394 */                       System.arraycopy(array, i3 + 1, array, i3 - 1, size - i3);
/* 395 */                       size -= 2;
/* 396 */                       i3--;
                            }
/* 388 */                   i3++;
                        }
/* 402 */               int i4 = prefix + 2;
                        while (i4 < size) {
/* 403 */                   if (array[i4] == separator && array[i4 - 1] == '.' && array[i4 - 2] == '.' && (i4 == prefix + 2 || array[i4 - 3] == separator)) {
/* 405 */                       if (i4 == prefix + 2) {
/* 406 */                           return null;
                                }
/* 408 */                       if (i4 == size - 1) {
/* 409 */                           lastIsDirectory = true;
                                }
/* 412 */                       int j = i4 - 4;
                                while (true) {
                                    if (j >= prefix) {
/* 413 */                               if (array[j] != separator) {
/* 412 */                                   j--;
                                        } else {
/* 415 */                                   System.arraycopy(array, i4 + 1, array, j + 1, size - i4);
/* 416 */                                   size -= i4 - j;
/* 417 */                                   i4 = j + 1;
                                            break;
                                        }
                                    } else {
/* 422 */                               System.arraycopy(array, i4 + 1, array, prefix, size - i4);
/* 423 */                               size -= (i4 + 1) - prefix;
/* 424 */                               i4 = prefix + 1;
                                        break;
                                    }
                                }
                            }
/* 402 */                   i4++;
                        }
/* 428 */               if (size <= 0) {
/* 429 */                   return "";
                        }
/* 431 */               if (size <= prefix) {
/* 432 */                   return new String(array, 0, size);
                        }
/* 434 */               if (lastIsDirectory && keepSeparator) {
/* 435 */                   return new String(array, 0, size);
                        }
/* 437 */               return new String(array, 0, size - 1);
                    }
/* 437 */           return filename;
                }

/* 481 */       public static String concat(String basePath, String fullFilenameToAdd) {
/* 482 */           int prefix = getPrefixLength(fullFilenameToAdd);
/* 483 */           if (prefix < 0) {
                        return null;
                    }
/* 486 */           if (prefix > 0) {
/* 487 */               return normalize(fullFilenameToAdd);
                    }
/* 489 */           if (basePath == null) {
                        return null;
                    }
/* 492 */           int len = basePath.length();
/* 493 */           if (len == 0) {
/* 494 */               return normalize(fullFilenameToAdd);
                    }
/* 496 */           char ch = basePath.charAt(len - 1);
/* 497 */           if (isSeparator(ch)) {
/* 498 */               return normalize(basePath + fullFilenameToAdd);
                    }
/* 500 */           return normalize(basePath + '/' + fullFilenameToAdd);
                }

/* 530 */       public static boolean directoryContains(String canonicalParent, String canonicalChild) throws IOException {
/* 531 */           if (canonicalParent == null) {
/* 532 */               throw new IllegalArgumentException("Directory must not be null");
                    }
/* 535 */           if (canonicalChild == null || IOCase.SYSTEM.checkEquals(canonicalParent, canonicalChild)) {
                        return false;
                    }
/* 543 */           return IOCase.SYSTEM.checkStartsWith(canonicalChild, canonicalParent);
                }

/* 553 */       public static String separatorsToUnix(String path) {
                    return (path == null || path.indexOf(92) == -1) ? path : path.replace('\\', '/');
                }

/* 566 */       public static String separatorsToWindows(String path) {
                    return (path == null || path.indexOf(47) == -1) ? path : path.replace('/', '\\');
                }

/* 579 */       public static String separatorsToSystem(String path) {
/* 580 */           if (path == null) {
/* 581 */               return null;
                    }
/* 583 */           if (isSystemWindows()) {
/* 584 */               return separatorsToWindows(path);
                    }
/* 586 */           return separatorsToUnix(path);
                }

/* 622 */       public static int getPrefixLength(String filename) {
/* 623 */           if (filename == null) {
                        return -1;
                    }
/* 626 */           int len = filename.length();
/* 627 */           if (len == 0) {
                        return 0;
                    }
/* 630 */           char ch0 = filename.charAt(0);
/* 631 */           if (ch0 == ':') {
                        return -1;
                    }
/* 634 */           if (len == 1) {
/* 635 */               if (ch0 == '~') {
                            return 2;
                        }
                        return !isSeparator(ch0) ? 0 : 1;
                    }
/* 640 */           if (ch0 == '~') {
/* 641 */               int posUnix = filename.indexOf(47, 1);
/* 642 */               int posWin = filename.indexOf(92, 1);
/* 643 */               if (posUnix == -1 && posWin == -1) {
/* 644 */                   return len + 1;
                        }
/* 646 */               if (posUnix == -1) {
                            posUnix = posWin;
                        }
/* 647 */               if (posWin == -1) {
                            posWin = posUnix;
                        }
/* 648 */               return Math.min(posUnix, posWin) + 1;
                    }
/* 650 */           char ch1 = filename.charAt(1);
/* 651 */           if (ch1 == ':') {
/* 652 */               char ch2 = Character.toUpperCase(ch0);
/* 653 */               if (ch2 < 'A' || ch2 > 'Z') {
                            return -1;
                        }
                        return (len == 2 || !isSeparator(filename.charAt(2))) ? 2 : 3;
                    }
/* 661 */           if (!isSeparator(ch0) || !isSeparator(ch1)) {
                        return !isSeparator(ch0) ? 0 : 1;
                    }
/* 662 */           int posUnix2 = filename.indexOf(47, 2);
/* 663 */           int posWin2 = filename.indexOf(92, 2);
/* 664 */           if ((posUnix2 == -1 && posWin2 == -1) || posUnix2 == 2 || posWin2 == 2) {
                        return -1;
                    }
/* 667 */           if (posUnix2 == -1) {
                        posUnix2 = posWin2;
                    }
/* 668 */           if (posWin2 == -1) {
                        posWin2 = posUnix2;
                    }
/* 669 */           return Math.min(posUnix2, posWin2) + 1;
                }

/* 688 */       public static int indexOfLastSeparator(String filename) {
/* 689 */           if (filename == null) {
/* 690 */               return -1;
                    }
/* 692 */           int lastUnixPos = filename.lastIndexOf(47);
/* 693 */           int lastWindowsPos = filename.lastIndexOf(92);
/* 694 */           return Math.max(lastUnixPos, lastWindowsPos);
                }

/* 710 */       public static int indexOfExtension(String filename) {
/* 711 */           if (filename == null) {
                        return -1;
                    }
/* 714 */           int extensionPos = filename.lastIndexOf(46);
/* 715 */           int lastSeparator = indexOfLastSeparator(filename);
/* 716 */           if (lastSeparator > extensionPos) {
                        extensionPos = -1;
                    }
                    return extensionPos;
                }

/* 749 */       public static String getPrefix(String filename) {
                    int len;
/* 750 */           if (filename == null || (len = getPrefixLength(filename)) < 0) {
                        return null;
                    }
/* 757 */           if (len > filename.length()) {
/* 758 */               return filename + '/';
                    }
/* 760 */           return filename.substring(0, len);
                }

/* 785 */       public static String getPath(String filename) {
/* 786 */           return doGetPath(filename, 1);
                }

/* 812 */       public static String getPathNoEndSeparator(String filename) {
/* 813 */           return doGetPath(filename, 0);
                }

/* 823 */       private static String doGetPath(String filename, int separatorAdd) {
                    int prefix;
/* 824 */           if (filename == null || (prefix = getPrefixLength(filename)) < 0) {
                        return null;
                    }
/* 831 */           int index = indexOfLastSeparator(filename);
/* 832 */           int endIndex = index + separatorAdd;
/* 833 */           if (prefix >= filename.length() || index < 0 || prefix >= endIndex) {
/* 834 */               return "";
                    }
/* 836 */           return filename.substring(prefix, endIndex);
                }

/* 864 */       public static String getFullPath(String filename) {
/* 865 */           return doGetFullPath(filename, true);
                }

/* 894 */       public static String getFullPathNoEndSeparator(String filename) {
/* 895 */           return doGetFullPath(filename, false);
                }

/* 905 */       private static String doGetFullPath(String filename, boolean includeSeparator) {
                    int prefix;
/* 906 */           if (filename != null && (prefix = getPrefixLength(filename)) >= 0) {
/* 913 */               if (prefix >= filename.length()) {
/* 914 */                   if (includeSeparator) {
/* 915 */                       return getPrefix(filename);
                            }
/* 928 */                   return filename;
                        }
/* 920 */               int index = indexOfLastSeparator(filename);
/* 921 */               if (index < 0) {
/* 922 */                   return filename.substring(0, prefix);
                        }
/* 924 */               int end = index + (includeSeparator ? 1 : 0);
/* 925 */               if (end == 0) {
/* 926 */                   end++;
                        }
/* 928 */               return filename.substring(0, end);
                    }
                    return null;
                }

/* 948 */       public static String getName(String filename) {
/* 949 */           if (filename == null) {
/* 950 */               return null;
                    }
/* 952 */           int index = indexOfLastSeparator(filename);
/* 953 */           return filename.substring(index + 1);
                }

/* 973 */       public static String getBaseName(String filename) {
/* 974 */           return removeExtension(getName(filename));
                }

/* 995 */       public static String getExtension(String filename) {
/* 996 */           if (filename == null) {
/* 997 */               return null;
                    }
/* 999 */           int index = indexOfExtension(filename);
/* 1000 */          if (index == -1) {
/* 1001 */              return "";
                    }
/* 1003 */          return filename.substring(index + 1);
                }

/* 1025 */      public static String removeExtension(String filename) {
/* 1026 */          if (filename == null) {
/* 1027 */              return null;
                    }
/* 1029 */          int index = indexOfExtension(filename);
                    return index != -1 ? filename.substring(0, index) : filename;
                }

/* 1049 */      public static boolean equals(String filename1, String filename2) {
/* 1050 */          return equals(filename1, filename2, false, IOCase.SENSITIVE);
                }

/* 1064 */      public static boolean equalsOnSystem(String filename1, String filename2) {
/* 1065 */          return equals(filename1, filename2, false, IOCase.SYSTEM);
                }

/* 1080 */      public static boolean equalsNormalized(String filename1, String filename2) {
/* 1081 */          return equals(filename1, filename2, true, IOCase.SENSITIVE);
                }

/* 1097 */      public static boolean equalsNormalizedOnSystem(String filename1, String filename2) {
/* 1098 */          return equals(filename1, filename2, true, IOCase.SYSTEM);
                }

/* 1115 */      public static boolean equals(String filename1, String filename2, boolean normalized, IOCase caseSensitivity) {
/* 1116 */          if (filename1 == null || filename2 == null) {
                        return filename1 == null && filename2 == null;
                    }
/* 1119 */          if (normalized) {
/* 1120 */              filename1 = normalize(filename1);
/* 1121 */              filename2 = normalize(filename2);
/* 1122 */              if (filename1 == null || filename2 == null) {
/* 1123 */                  throw new NullPointerException("Error normalizing one or both of the file names");
                        }
                    }
/* 1127 */          if (caseSensitivity == null) {
/* 1128 */              caseSensitivity = IOCase.SENSITIVE;
                    }
/* 1130 */          return caseSensitivity.checkEquals(filename1, filename2);
                }

/* 1145 */      public static boolean isExtension(String filename, String extension) {
/* 1146 */          if (filename == null) {
                        return false;
                    }
/* 1149 */          if (extension == null || extension.length() == 0) {
                        return indexOfExtension(filename) == -1;
                    }
/* 1152 */          String fileExt = getExtension(filename);
/* 1153 */          return fileExt.equals(extension);
                }

/* 1167 */      public static boolean isExtension(String filename, String[] extensions) {
/* 1168 */          if (filename == null) {
                        return false;
                    }
/* 1171 */          if (extensions == null || extensions.length == 0) {
/* 1172 */              return indexOfExtension(filename) == -1;
                    }
/* 1174 */          String fileExt = getExtension(filename);
                    for (String extension : extensions) {
/* 1176 */              if (fileExt.equals(extension)) {
                            return true;
                        }
                    }
                    return false;
                }

/* 1194 */      public static boolean isExtension(String filename, Collection<String> extensions) {
/* 1195 */          if (filename == null) {
                        return false;
                    }
/* 1198 */          if (extensions == null || extensions.isEmpty()) {
/* 1199 */              return indexOfExtension(filename) == -1;
                    }
/* 1201 */          String fileExt = getExtension(filename);
                    for (String extension : extensions) {
/* 1203 */              if (fileExt.equals(extension)) {
                            return true;
                        }
                    }
                    return false;
                }

/* 1233 */      public static boolean wildcardMatch(String filename, String wildcardMatcher) {
/* 1234 */          return wildcardMatch(filename, wildcardMatcher, IOCase.SENSITIVE);
                }

/* 1259 */      public static boolean wildcardMatchOnSystem(String filename, String wildcardMatcher) {
/* 1260 */          return wildcardMatch(filename, wildcardMatcher, IOCase.SYSTEM);
                }

/* 1277 */      public static boolean wildcardMatch(String filename, String wildcardMatcher, IOCase caseSensitivity) {
/* 1278 */          if (filename == null && wildcardMatcher == null) {
                        return true;
                    }
/* 1281 */          if (filename == null || wildcardMatcher == null) {
                        return false;
                    }
/* 1284 */          if (caseSensitivity == null) {
/* 1285 */              caseSensitivity = IOCase.SENSITIVE;
                    }
/* 1287 */          String[] wcs = splitOnTokens(wildcardMatcher);
/* 1288 */          boolean anyChars = false;
/* 1289 */          int textIdx = 0;
/* 1290 */          int wcsIdx = 0;
/* 1291 */          Stack<int[]> backtrack = new Stack<>();
/* 1354 */          do {
/* 1295 */              if (backtrack.size() > 0) {
/* 1296 */                  int[] array = backtrack.pop();
/* 1297 */                  wcsIdx = array[0];
/* 1298 */                  textIdx = array[1];
/* 1299 */                  anyChars = true;
                        }
/* 1303 */              while (wcsIdx < wcs.length) {
/* 1305 */                  if (wcs[wcsIdx].equals("?")) {
/* 1307 */                      textIdx++;
/* 1308 */                      if (textIdx > filename.length()) {
                                    break;
                                }
/* 1311 */                      anyChars = false;
/* 1346 */                      wcsIdx++;
                            } else {
/* 1313 */                      if (wcs[wcsIdx].equals("*")) {
/* 1315 */                          anyChars = true;
/* 1316 */                          if (wcsIdx == wcs.length - 1) {
/* 1317 */                              textIdx = filename.length();
                                    }
                                } else if (anyChars) {
/* 1324 */                          textIdx = caseSensitivity.checkIndexOf(filename, textIdx, wcs[wcsIdx]);
/* 1325 */                          if (textIdx == -1) {
                                        break;
                                    }
/* 1329 */                          int repeat = caseSensitivity.checkIndexOf(filename, textIdx + 1, wcs[wcsIdx]);
/* 1330 */                          if (repeat >= 0) {
/* 1331 */                              backtrack.push(new int[]{wcsIdx, repeat});
                                    }
/* 1342 */                          textIdx += wcs[wcsIdx].length();
/* 1343 */                          anyChars = false;
                                } else {
/* 1335 */                          if (!caseSensitivity.checkRegionMatches(filename, textIdx, wcs[wcsIdx])) {
                                        break;
                                    }
/* 1342 */                          textIdx += wcs[wcsIdx].length();
/* 1343 */                          anyChars = false;
                                }
/* 1346 */                      wcsIdx++;
                            }
                        }
/* 1350 */              if (wcsIdx == wcs.length && textIdx == filename.length()) {
                            return true;
                        }
/* 1354 */          } while (backtrack.size() > 0);
                    return false;
                }

/* 1370 */      static String[] splitOnTokens(String text) {
/* 1371 */          if (text.indexOf(63) == -1 && text.indexOf(42) == -1) {
/* 1372 */              return new String[]{text};
                    }
/* 1375 */          char[] array = text.toCharArray();
/* 1376 */          ArrayList<String> list = new ArrayList<>();
/* 1377 */          StringBuilder buffer = new StringBuilder();
                    for (int i = 0; i < array.length; i++) {
/* 1379 */              if (array[i] == '?' || array[i] == '*') {
/* 1380 */                  if (buffer.length() != 0) {
/* 1381 */                      list.add(buffer.toString());
/* 1382 */                      buffer.setLength(0);
                            }
/* 1384 */                  if (array[i] == '?') {
/* 1385 */                      list.add("?");
                            } else if (list.isEmpty() || (i > 0 && !list.get(list.size() - 1).equals("*"))) {
/* 1388 */                      list.add("*");
                            }
                        } else {
/* 1391 */                  buffer.append(array[i]);
                        }
                    }
/* 1394 */          if (buffer.length() != 0) {
/* 1395 */              list.add(buffer.toString());
                    }
/* 1398 */          return (String[]) list.toArray(new String[list.size()]);
                }
            }
