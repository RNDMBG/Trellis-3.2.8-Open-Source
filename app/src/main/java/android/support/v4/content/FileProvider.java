            package android.support.v4.content;

            import android.content.ContentProvider;
            import android.content.ContentValues;
            import android.content.Context;
            import android.content.pm.ProviderInfo;
            import android.content.res.XmlResourceParser;
            import android.database.Cursor;
            import android.database.MatrixCursor;
            import android.net.Uri;
            import android.os.Environment;
            import android.os.ParcelFileDescriptor;
            import android.text.TextUtils;
            import android.webkit.MimeTypeMap;
            import java.io.File;
            import java.io.FileNotFoundException;
            import java.io.IOException;
            import java.util.HashMap;
            import java.util.Map;
            import org.xmlpull.v1.XmlPullParserException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 328 */   public class FileProvider extends ContentProvider {
                private static final String ATTR_NAME = "name";
                private static final String ATTR_PATH = "path";
                private static final String META_DATA_FILE_PROVIDER_PATHS = "android.support.FILE_PROVIDER_PATHS";
                private static final String TAG_CACHE_PATH = "cache-path";
                private static final String TAG_EXTERNAL = "external-path";
                private static final String TAG_FILES_PATH = "files-path";
                private static final String TAG_ROOT_PATH = "root-path";
                private PathStrategy mStrategy;
                private static final String[] COLUMNS = {"_display_name", "_size"};
                private static final File DEVICE_ROOT = new File("/");
                private static HashMap<String, PathStrategy> sCache = new HashMap<>();

                interface PathStrategy {
                    File getFileForUri(Uri uri);

                    Uri getUriForFile(File file);
                }

                @Override // android.content.ContentProvider
/* 329 */       public boolean onCreate() {
/* 330 */           return true;
                }

                @Override // android.content.ContentProvider
/* 341 */       public void attachInfo(Context context, ProviderInfo info) {
/* 342 */           super.attachInfo(context, info);
/* 345 */           if (info.exported) {
/* 346 */               throw new SecurityException("Provider must not be exported");
                    }
/* 348 */           if (!info.grantUriPermissions) {
/* 349 */               throw new SecurityException("Provider must grant uri permissions");
                    }
/* 352 */           this.mStrategy = getPathStrategy(context, info.authority);
                }

/* 375 */       public static Uri getUriForFile(Context context, String authority, File file) {
/* 376 */           PathStrategy strategy = getPathStrategy(context, authority);
/* 377 */           return strategy.getUriForFile(file);
                }

                @Override // android.content.ContentProvider
/* 411 */       public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
                    int i;
/* 412 */           File file = this.mStrategy.getFileForUri(uri);
/* 414 */           if (projection == null) {
/* 415 */               projection = COLUMNS;
                    }
/* 418 */           String[] cols = new String[projection.length];
/* 419 */           Object[] values = new Object[projection.length];
/* 421 */           String[] arr$ = projection;
                    int len$ = arr$.length;
                    int i$ = 0;
/* 420 */           int i2 = 0;
                    while (i$ < len$) {
                        String col = arr$[i$];
/* 422 */               if ("_display_name".equals(col)) {
/* 423 */                   cols[i2] = "_display_name";
/* 424 */                   i = i2 + 1;
                            values[i2] = file.getName();
                        } else if ("_size".equals(col)) {
/* 426 */                   cols[i2] = "_size";
/* 427 */                   i = i2 + 1;
                            values[i2] = Long.valueOf(file.length());
                        } else {
                            i = i2;
                        }
/* 421 */               i$++;
                        i2 = i;
                    }
/* 431 */           String[] cols2 = copyOf(cols, i2);
/* 432 */           Object[] values2 = copyOf(values, i2);
/* 434 */           MatrixCursor cursor = new MatrixCursor(cols2, 1);
/* 435 */           cursor.addRow(values2);
/* 436 */           return cursor;
                }

                @Override // android.content.ContentProvider
/* 450 */       public String getType(Uri uri) {
/* 451 */           File file = this.mStrategy.getFileForUri(uri);
/* 453 */           int lastDot = file.getName().lastIndexOf(46);
/* 454 */           if (lastDot >= 0) {
/* 455 */               String extension = file.getName().substring(lastDot + 1);
/* 456 */               String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
/* 457 */               if (mime != null) {
/* 462 */                   return mime;
                        }
                    }
                    return "application/octet-stream";
                }

                @Override // android.content.ContentProvider
/* 470 */       public Uri insert(Uri uri, ContentValues values) {
/* 471 */           throw new UnsupportedOperationException("No external inserts");
                }

                @Override // android.content.ContentProvider
/* 479 */       public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
/* 480 */           throw new UnsupportedOperationException("No external updates");
                }

                @Override // android.content.ContentProvider
/* 496 */       public int delete(Uri uri, String selection, String[] selectionArgs) {
/* 497 */           File file = this.mStrategy.getFileForUri(uri);
                    return file.delete() ? 1 : 0;
                }

                @Override // android.content.ContentProvider
/* 518 */       public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
/* 519 */           File file = this.mStrategy.getFileForUri(uri);
/* 520 */           int fileMode = modeToMode(mode);
/* 521 */           return ParcelFileDescriptor.open(file, fileMode);
                }

/* 529 */       private static PathStrategy getPathStrategy(Context context, String authority) {
                    PathStrategy strat;
                    synchronized (sCache) {
                        try {
/* 531 */                   strat = sCache.get(authority);
/* 532 */                   if (strat == null) {
                                try {
                                    try {
/* 534 */                               strat = parsePathStrategy(context, authority);
/* 542 */                               sCache.put(authority, strat);
                                    } catch (IOException e) {
/* 536 */                               throw new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", e);
                                    }
                                } catch (XmlPullParserException e2) {
/* 539 */                           throw new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", e2);
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 545 */           return strat;
                }

/* 555 */       private static PathStrategy parsePathStrategy(Context context, String authority) throws XmlPullParserException, IOException {
/* 556 */           SimplePathStrategy strat = new SimplePathStrategy(authority);
/* 558 */           ProviderInfo info = context.getPackageManager().resolveContentProvider(authority, 128);
/* 560 */           XmlResourceParser in = info.loadXmlMetaData(context.getPackageManager(), META_DATA_FILE_PROVIDER_PATHS);
/* 562 */           if (in == null) {
/* 563 */               throw new IllegalArgumentException("Missing android.support.FILE_PROVIDER_PATHS meta-data");
                    }
                    while (true) {
/* 568 */               int type = in.next();
                        if (type != 1) {
/* 569 */                   if (type == 2) {
/* 570 */                       String tag = in.getName();
/* 572 */                       String name = in.getAttributeValue(null, "name");
/* 573 */                       String path = in.getAttributeValue(null, "path");
/* 575 */                       File target = null;
/* 576 */                       if (TAG_ROOT_PATH.equals(tag)) {
/* 577 */                           target = buildPath(DEVICE_ROOT, path);
                                } else if (TAG_FILES_PATH.equals(tag)) {
/* 579 */                           target = buildPath(context.getFilesDir(), path);
                                } else if (TAG_CACHE_PATH.equals(tag)) {
/* 581 */                           target = buildPath(context.getCacheDir(), path);
                                } else if (TAG_EXTERNAL.equals(tag)) {
/* 583 */                           target = buildPath(Environment.getExternalStorageDirectory(), path);
                                }
/* 586 */                       if (target != null) {
/* 587 */                           strat.addRoot(name, target);
                                }
                            }
                        } else {
/* 592 */                   return strat;
                        }
                    }
                }

/* 632 */       static class SimplePathStrategy implements PathStrategy {
                    private final String mAuthority;
                    private final HashMap<String, File> mRoots = new HashMap<>();

/* 633 */           public SimplePathStrategy(String authority) {
/* 635 */               this.mAuthority = authority;
                    }

/* 642 */           public void addRoot(String name, File root) {
/* 643 */               if (TextUtils.isEmpty(name)) {
/* 644 */                   throw new IllegalArgumentException("Name must not be empty");
                        }
                        try {
/* 655 */                   this.mRoots.put(name, root.getCanonicalFile());
                        } catch (IOException e) {
/* 651 */                   throw new IllegalArgumentException("Failed to resolve canonical path for " + root, e);
                        }
                    }

                    @Override // android.support.v4.content.FileProvider.PathStrategy
/* 661 */           public Uri getUriForFile(File file) {
                        String path;
                        try {
/* 662 */                   String path2 = file.getCanonicalPath();
/* 668 */                   Map.Entry<String, File> mostSpecific = null;
                            for (Map.Entry<String, File> root : this.mRoots.entrySet()) {
/* 670 */                       String rootPath = root.getValue().getPath();
/* 671 */                       if (path2.startsWith(rootPath) && (mostSpecific == null || rootPath.length() > mostSpecific.getValue().getPath().length())) {
/* 673 */                           mostSpecific = root;
                                }
                            }
/* 677 */                   if (mostSpecific == null) {
/* 678 */                       throw new IllegalArgumentException("Failed to find configured root that contains " + path2);
                            }
/* 683 */                   String rootPath2 = mostSpecific.getValue().getPath();
/* 684 */                   if (rootPath2.endsWith("/")) {
/* 685 */                       path = path2.substring(rootPath2.length());
                            } else {
/* 687 */                       path = path2.substring(rootPath2.length() + 1);
                            }
/* 692 */                   return new Uri.Builder().scheme("content").authority(this.mAuthority).encodedPath(Uri.encode(mostSpecific.getKey()) + '/' + Uri.encode(path, "/")).build();
                        } catch (IOException e) {
/* 664 */                   throw new IllegalArgumentException("Failed to resolve canonical path for " + file);
                        }
                    }

                    @Override // android.support.v4.content.FileProvider.PathStrategy
/* 697 */           public File getFileForUri(Uri uri) {
/* 698 */               String path = uri.getEncodedPath();
/* 700 */               int splitIndex = path.indexOf(47, 1);
/* 701 */               String tag = Uri.decode(path.substring(1, splitIndex));
/* 702 */               String path2 = Uri.decode(path.substring(splitIndex + 1));
/* 704 */               File root = this.mRoots.get(tag);
/* 705 */               if (root == null) {
/* 706 */                   throw new IllegalArgumentException("Unable to find configured root for " + uri);
                        }
/* 709 */               File file = new File(root, path2);
                        try {
/* 711 */                   File file2 = file.getCanonicalFile();
/* 716 */                   if (!file2.getPath().startsWith(root.getPath())) {
/* 717 */                       throw new SecurityException("Resolved path jumped beyond configured root");
                            }
/* 720 */                   return file2;
                        } catch (IOException e) {
/* 713 */                   throw new IllegalArgumentException("Failed to resolve canonical path for " + file);
                        }
                    }
                }

/* 728 */       private static int modeToMode(String mode) {
/* 729 */           if ("r".equals(mode)) {
/* 730 */               return 268435456;
                    }
/* 731 */           if ("w".equals(mode) || "wt".equals(mode)) {
/* 732 */               return 738197504;
                    }
/* 735 */           if ("wa".equals(mode)) {
/* 736 */               return 704643072;
                    }
/* 739 */           if ("rw".equals(mode)) {
/* 740 */               return 939524096;
                    }
/* 742 */           if ("rwt".equals(mode)) {
/* 743 */               return 1006632960;
                    }
/* 747 */           throw new IllegalArgumentException("Invalid mode: " + mode);
                }

/* 752 */       private static File buildPath(File base, String... segments) {
/* 754 */           int len$ = segments.length;
                    int i$ = 0;
/* 753 */           File cur = base;
                    while (i$ < len$) {
/* 754 */               String segment = segments[i$];
/* 754 */               i$++;
/* 756 */               cur = segment != null ? new File(cur, segment) : cur;
                    }
/* 759 */           return cur;
                }

/* 762 */       private static String[] copyOf(String[] original, int newLength) {
/* 763 */           String[] result = new String[newLength];
/* 764 */           System.arraycopy(original, 0, result, 0, newLength);
/* 765 */           return result;
                }

/* 768 */       private static Object[] copyOf(Object[] original, int newLength) {
/* 769 */           Object[] result = new Object[newLength];
/* 770 */           System.arraycopy(original, 0, result, 0, newLength);
/* 771 */           return result;
                }
            }
