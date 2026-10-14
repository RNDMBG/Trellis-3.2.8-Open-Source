            package com.fasterxml.jackson.core.sym;

            import com.fasterxml.jackson.core.util.ArraysCompat;
            import com.fasterxml.jackson.core.util.InternCache;
            import com.flurry.android.Constants;
            import java.util.Arrays;
            import java.util.concurrent.atomic.AtomicReference;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 239 */   public final class BytesToNameCanonicalizer {
                protected static final int DEFAULT_TABLE_SIZE = 64;
                static final int INITIAL_COLLISION_LEN = 32;
                static final int LAST_VALID_BUCKET = 254;
                static final int MAX_COLL_CHAIN_FOR_REUSE = 63;
                static final int MAX_COLL_CHAIN_LENGTH = 255;
                static final int MAX_ENTRIES_FOR_REUSE = 6000;
                protected static final int MAX_TABLE_SIZE = 65536;
                static final int MIN_HASH_SIZE = 16;
                private static final int MULT = 33;
                private static final int MULT2 = 65599;
                private static final int MULT3 = 31;
                protected int _collCount;
                protected int _collEnd;
                protected Bucket[] _collList;
                private boolean _collListShared;
                protected int _count;
                private final int _hashSeed;
                protected final boolean _intern;
                protected int _longestCollisionList;
                protected int[] _mainHash;
                protected int _mainHashMask;
                private boolean _mainHashShared;
                protected Name[] _mainNames;
                private boolean _mainNamesShared;
                private transient boolean _needRehash;
                protected final BytesToNameCanonicalizer _parent;
                protected final AtomicReference<TableInfo> _tableInfo;

/* 240 */       private BytesToNameCanonicalizer(int i, boolean z, int i2) {
                    int i3 = 16;
/* 242 */           this._parent = null;
/* 243 */           this._hashSeed = i2;
/* 244 */           this._intern = z;
/* 246 */           if (i < 16) {
                        i = 16;
                    } else if (((i - 1) & i) != 0) {
/* 254 */               while (i3 < i) {
/* 255 */                   i3 += i3;
                        }
                        i = i3;
                    }
/* 260 */           this._tableInfo = new AtomicReference<>(initTableInfo(i));
                }

/* 267 */       private BytesToNameCanonicalizer(BytesToNameCanonicalizer bytesToNameCanonicalizer, boolean z, int i, TableInfo tableInfo) {
/* 269 */           this._parent = bytesToNameCanonicalizer;
/* 270 */           this._hashSeed = i;
/* 271 */           this._intern = z;
/* 272 */           this._tableInfo = null;
/* 275 */           this._count = tableInfo.count;
/* 276 */           this._mainHashMask = tableInfo.mainHashMask;
/* 277 */           this._mainHash = tableInfo.mainHash;
/* 278 */           this._mainNames = tableInfo.mainNames;
/* 279 */           this._collList = tableInfo.collList;
/* 280 */           this._collCount = tableInfo.collCount;
/* 281 */           this._collEnd = tableInfo.collEnd;
/* 282 */           this._longestCollisionList = tableInfo.longestCollisionList;
/* 285 */           this._needRehash = false;
/* 286 */           this._mainHashShared = true;
/* 287 */           this._mainNamesShared = true;
/* 288 */           this._collListShared = true;
                }

/* 296 */       private TableInfo initTableInfo(int i) {
/* 297 */           return new TableInfo(0, i - 1, new int[i], new Name[i], null, 0, 0, 0);
                }

/* 322 */       public static BytesToNameCanonicalizer createRoot() {
/* 323 */           long jCurrentTimeMillis = System.currentTimeMillis();
/* 326 */           return createRoot((((int) (jCurrentTimeMillis >>> 32)) + ((int) jCurrentTimeMillis)) | 1);
                }

/* 333 */       protected static BytesToNameCanonicalizer createRoot(int i) {
/* 334 */           return new BytesToNameCanonicalizer(64, true, i);
                }

/* 346 */       public BytesToNameCanonicalizer makeChild(boolean z, boolean z2) {
/* 347 */           return new BytesToNameCanonicalizer(this, z2, this._hashSeed, this._tableInfo.get());
                }

/* 359 */       public void release() {
/* 360 */           if (this._parent != null && maybeDirty()) {
/* 361 */               this._parent.mergeChild(new TableInfo(this));
/* 365 */               this._mainHashShared = true;
/* 366 */               this._mainNamesShared = true;
/* 367 */               this._collListShared = true;
                    }
                }

/* 372 */       private void mergeChild(TableInfo tableInfo) {
/* 373 */           int i = tableInfo.count;
/* 374 */           TableInfo tableInfo2 = this._tableInfo.get();
/* 377 */           if (i > tableInfo2.count) {
/* 387 */               if (i > 6000 || tableInfo.longestCollisionList > 63) {
/* 394 */                   tableInfo = initTableInfo(64);
                        }
/* 396 */               this._tableInfo.compareAndSet(tableInfo2, tableInfo);
                    }
                }

/* 406 */       public int size() {
                    return this._tableInfo != null ? this._tableInfo.get().count : this._count;
                }

/* 416 */       public int bucketCount() {
/* 417 */           return this._mainHash.length;
                }

/* 424 */       public boolean maybeDirty() {
                    return !this._mainHashShared;
                }

/* 430 */       public int hashSeed() {
/* 431 */           return this._hashSeed;
                }

/* 440 */       public int collisionCount() {
/* 441 */           return this._collCount;
                }

/* 451 */       public int maxCollisionLength() {
/* 452 */           return this._longestCollisionList;
                }

/* 462 */       public static Name getEmptyName() {
/* 463 */           return Name1.getEmptyName();
                }

/* 482 */       public Name findName(int i) {
/* 483 */           int iCalcHash = calcHash(i);
/* 484 */           int i2 = this._mainHashMask & iCalcHash;
/* 485 */           int i3 = this._mainHash[i2];
/* 490 */           if ((((i3 >> 8) ^ iCalcHash) << 8) == 0) {
/* 492 */               Name name = this._mainNames[i2];
/* 493 */               if (name == null) {
                            return null;
                        }
/* 496 */               if (name.equals(i)) {
                            return name;
                        }
                    } else if (i3 == 0) {
                        return null;
                    }
/* 503 */           int i4 = i3 & 255;
/* 504 */           if (i4 <= 0) {
                        return null;
                    }
/* 505 */           Bucket bucket = this._collList[i4 - 1];
/* 507 */           if (bucket != null) {
/* 508 */               return bucket.find(iCalcHash, i, 0);
                    }
                    return null;
                }

/* 531 */       public Name findName(int i, int i2) {
/* 532 */           int iCalcHash = i2 == 0 ? calcHash(i) : calcHash(i, i2);
/* 533 */           int i3 = this._mainHashMask & iCalcHash;
/* 534 */           int i4 = this._mainHash[i3];
/* 539 */           if ((((i4 >> 8) ^ iCalcHash) << 8) == 0) {
/* 541 */               Name name = this._mainNames[i3];
/* 542 */               if (name == null) {
                            return null;
                        }
/* 545 */               if (name.equals(i, i2)) {
                            return name;
                        }
                    } else if (i4 == 0) {
                        return null;
                    }
/* 552 */           int i5 = i4 & 255;
/* 553 */           if (i5 > 0) {
/* 554 */               Bucket bucket = this._collList[i5 - 1];
/* 556 */               if (bucket != null) {
/* 557 */                   return bucket.find(iCalcHash, i, i2);
                        }
                    }
                    return null;
                }

/* 582 */       public Name findName(int[] iArr, int i) {
/* 583 */           if (i < 3) {
                        return findName(iArr[0], i >= 2 ? iArr[1] : 0);
                    }
/* 586 */           int iCalcHash = calcHash(iArr, i);
/* 588 */           int i2 = this._mainHashMask & iCalcHash;
/* 589 */           int i3 = this._mainHash[i2];
/* 590 */           if ((((i3 >> 8) ^ iCalcHash) << 8) == 0) {
/* 591 */               Name name = this._mainNames[i2];
/* 592 */               if (name == null || name.equals(iArr, i)) {
/* 607 */                   return name;
                        }
                    } else if (i3 == 0) {
                        return null;
                    }
/* 599 */           int i4 = i3 & 255;
/* 600 */           if (i4 > 0) {
/* 601 */               Bucket bucket = this._collList[i4 - 1];
/* 603 */               if (bucket != null) {
/* 604 */                   return bucket.find(iCalcHash, iArr, i);
                        }
                    }
                    return null;
                }

/* 617 */       public Name addName(String str, int i, int i2) {
/* 618 */           if (this._intern) {
/* 619 */               str = InternCache.instance.intern(str);
                    }
/* 621 */           int iCalcHash = i2 == 0 ? calcHash(i) : calcHash(i, i2);
/* 622 */           Name nameConstructName = constructName(iCalcHash, str, i, i2);
/* 623 */           _addSymbol(iCalcHash, nameConstructName);
/* 624 */           return nameConstructName;
                }

/* 628 */       public Name addName(String str, int[] iArr, int i) {
                    int iCalcHash;
/* 629 */           if (this._intern) {
/* 630 */               str = InternCache.instance.intern(str);
                    }
/* 633 */           if (i < 3) {
/* 634 */               iCalcHash = i == 1 ? calcHash(iArr[0]) : calcHash(iArr[0], iArr[1]);
                    } else {
/* 636 */               iCalcHash = calcHash(iArr, i);
                    }
/* 638 */           Name nameConstructName = constructName(iCalcHash, str, iArr, i);
/* 639 */           _addSymbol(iCalcHash, nameConstructName);
/* 640 */           return nameConstructName;
                }

/* 664 */       public int calcHash(int i) {
/* 665 */           int i2 = this._hashSeed ^ i;
/* 666 */           int i3 = i2 + (i2 >>> 15);
/* 667 */           return i3 ^ (i3 >>> 9);
                }

/* 676 */       public int calcHash(int i, int i2) {
/* 679 */           int i3 = (((i >>> 15) ^ i) + (i2 * 33)) ^ this._hashSeed;
/* 680 */           return i3 + (i3 >>> 7);
                }

/* 686 */       public int calcHash(int[] iArr, int i) {
/* 687 */           if (i < 3) {
/* 688 */               throw new IllegalArgumentException();
                    }
/* 696 */           int i2 = iArr[0] ^ this._hashSeed;
/* 700 */           int i3 = (((i2 + (i2 >>> 9)) * 33) + iArr[1]) * MULT2;
/* 702 */           int i4 = (i3 + (i3 >>> 15)) ^ iArr[2];
/* 703 */           int i5 = i4 + (i4 >>> 17);
/* 705 */           for (int i6 = 3; i6 < i; i6++) {
/* 706 */               int i7 = (i5 * 31) ^ iArr[i6];
/* 708 */               int i8 = i7 + (i7 >>> 3);
/* 709 */               i5 = i8 ^ (i8 << 7);
                    }
/* 712 */           int i9 = (i5 >>> 15) + i5;
/* 713 */           return i9 ^ (i9 << 9);
                }

/* 719 */       protected static int[] calcQuads(byte[] bArr) {
/* 720 */           int length = bArr.length;
/* 721 */           int[] iArr = new int[(length + 3) / 4];
/* 722 */           int i = 0;
                    while (i < length) {
/* 723 */               int i2 = bArr[i] & Constants.UNKNOWN;
/* 725 */               int i3 = i + 1;
                        if (i3 < length) {
/* 726 */                   i2 = (i2 << 8) | (bArr[i3] & Constants.UNKNOWN);
/* 727 */                   i3++;
                            if (i3 < length) {
/* 728 */                       i2 = (i2 << 8) | (bArr[i3] & Constants.UNKNOWN);
/* 729 */                       i3++;
                                if (i3 < length) {
/* 730 */                           i2 = (i2 << 8) | (bArr[i3] & Constants.UNKNOWN);
                                }
                            }
                        }
/* 734 */               iArr[i3 >> 2] = i2;
/* 722 */               i = i3 + 1;
                    }
/* 736 */           return iArr;
                }

/* 791 */       private void _addSymbol(int i, Name name) {
                    int iFindBestBucket;
/* 792 */           if (this._mainHashShared) {
/* 793 */               unshareMain();
                    }
/* 796 */           if (this._needRehash) {
/* 797 */               rehash();
                    }
/* 800 */           this._count++;
/* 805 */           int i2 = i & this._mainHashMask;
/* 806 */           if (this._mainNames[i2] == null) {
/* 807 */               this._mainHash[i2] = i << 8;
/* 808 */               if (this._mainNamesShared) {
/* 809 */                   unshareNames();
                        }
/* 811 */               this._mainNames[i2] = name;
                    } else {
/* 816 */               if (this._collListShared) {
/* 817 */                   unshareCollision();
                        }
/* 819 */               this._collCount++;
/* 820 */               int i3 = this._mainHash[i2];
/* 821 */               int i4 = i3 & 255;
/* 822 */               if (i4 == 0) {
/* 823 */                   if (this._collEnd <= LAST_VALID_BUCKET) {
/* 824 */                       iFindBestBucket = this._collEnd;
/* 825 */                       this._collEnd++;
/* 827 */                       if (iFindBestBucket >= this._collList.length) {
/* 828 */                           expandCollision();
                                }
                            } else {
/* 831 */                       iFindBestBucket = findBestBucket();
                            }
/* 834 */                   this._mainHash[i2] = (i3 & (-256)) | (iFindBestBucket + 1);
                        } else {
/* 836 */                   iFindBestBucket = i4 - 1;
                        }
/* 840 */               Bucket bucket = new Bucket(name, this._collList[iFindBestBucket]);
/* 841 */               this._collList[iFindBestBucket] = bucket;
/* 843 */               this._longestCollisionList = Math.max(bucket.length(), this._longestCollisionList);
/* 844 */               if (this._longestCollisionList > 255) {
/* 845 */                   reportTooManyCollisions(255);
                        }
                    }
/* 853 */           int length = this._mainHash.length;
/* 854 */           if (this._count > (length >> 1)) {
/* 855 */               int i5 = length >> 2;
/* 859 */               if (this._count > length - i5) {
/* 860 */                   this._needRehash = true;
                        } else if (this._collCount >= i5) {
/* 862 */                   this._needRehash = true;
                        }
                    }
                }

/* 869 */       private void rehash() {
                    int iFindBestBucket;
                    int iMax;
                    int i = 0;
/* 870 */           this._needRehash = false;
/* 872 */           this._mainNamesShared = false;
/* 879 */           int length = this._mainHash.length;
/* 880 */           int i2 = length + length;
/* 885 */           if (i2 > 65536) {
/* 886 */               nukeSymbols();
/* 966 */               return;
                    }
/* 890 */           this._mainHash = new int[i2];
/* 891 */           this._mainHashMask = i2 - 1;
/* 892 */           Name[] nameArr = this._mainNames;
/* 893 */           this._mainNames = new Name[i2];
                    int i3 = 0;
/* 895 */           for (int i4 = 0; i4 < length; i4++) {
/* 896 */               Name name = nameArr[i4];
/* 897 */               if (name != null) {
/* 898 */                   i3++;
/* 899 */                   int iHashCode = name.hashCode();
/* 900 */                   int i5 = this._mainHashMask & iHashCode;
/* 901 */                   this._mainNames[i5] = name;
/* 902 */                   this._mainHash[i5] = iHashCode << 8;
                        }
                    }
/* 910 */           int i6 = this._collEnd;
/* 911 */           if (i6 == 0) {
/* 912 */               this._longestCollisionList = 0;
/* 966 */               return;
                    }
/* 916 */           this._collCount = 0;
/* 917 */           this._collEnd = 0;
/* 918 */           this._collListShared = false;
/* 922 */           Bucket[] bucketArr = this._collList;
/* 923 */           this._collList = new Bucket[bucketArr.length];
                    int i7 = 0;
                    int i8 = i3;
/* 924 */           while (i7 < i6) {
                        int i9 = i8;
/* 925 */               Bucket bucket = bucketArr[i7];
                        while (bucket != null) {
/* 926 */                   int i10 = i9 + 1;
/* 927 */                   Name name2 = bucket._name;
/* 928 */                   int iHashCode2 = name2.hashCode();
/* 929 */                   int i11 = this._mainHashMask & iHashCode2;
/* 930 */                   int i12 = this._mainHash[i11];
/* 931 */                   if (this._mainNames[i11] == null) {
/* 932 */                       this._mainHash[i11] = iHashCode2 << 8;
/* 933 */                       this._mainNames[i11] = name2;
                                iMax = i;
                            } else {
/* 935 */                       this._collCount++;
/* 936 */                       int i13 = i12 & 255;
/* 937 */                       if (i13 == 0) {
/* 938 */                           if (this._collEnd <= LAST_VALID_BUCKET) {
/* 939 */                               iFindBestBucket = this._collEnd;
/* 940 */                               this._collEnd++;
/* 942 */                               if (iFindBestBucket >= this._collList.length) {
/* 943 */                                   expandCollision();
                                        }
                                    } else {
/* 946 */                               iFindBestBucket = findBestBucket();
                                    }
/* 949 */                           this._mainHash[i11] = (i12 & (-256)) | (iFindBestBucket + 1);
                                } else {
/* 951 */                           iFindBestBucket = i13 - 1;
                                }
/* 954 */                       Bucket bucket2 = new Bucket(name2, this._collList[iFindBestBucket]);
/* 955 */                       this._collList[iFindBestBucket] = bucket2;
/* 956 */                       iMax = Math.max(i, bucket2.length());
                            }
/* 925 */                   bucket = bucket._next;
                            i = iMax;
                            i9 = i10;
                        }
/* 924 */               i7++;
                        i8 = i9;
                    }
/* 961 */           this._longestCollisionList = i;
/* 963 */           if (i8 != this._count) {
/* 964 */               throw new RuntimeException("Internal error: count after rehash " + i8 + "; should be " + this._count);
                    }
                }

/* 973 */       private void nukeSymbols() {
/* 974 */           this._count = 0;
/* 975 */           this._longestCollisionList = 0;
/* 976 */           Arrays.fill(this._mainHash, 0);
/* 977 */           Arrays.fill(this._mainNames, (Object) null);
/* 978 */           Arrays.fill(this._collList, (Object) null);
/* 979 */           this._collCount = 0;
/* 980 */           this._collEnd = 0;
                }

/* 989 */       private int findBestBucket() {
/* 990 */           Bucket[] bucketArr = this._collList;
/* 991 */           int i = Integer.MAX_VALUE;
/* 992 */           int i2 = -1;
/* 994 */           int i3 = 0;
                    int i4 = this._collEnd;
                    while (i3 < i4) {
/* 995 */               int length = bucketArr[i3].length();
/* 996 */               if (length >= i) {
                            length = i;
                        } else {
/* 997 */                   if (length == 1) {
/* 1004 */                      return i3;
                            }
                            i2 = i3;
                        }
/* 994 */               i3++;
                        i = length;
                    }
                    return i2;
                }

/* 1014 */      private void unshareMain() {
/* 1015 */          int[] iArr = this._mainHash;
/* 1016 */          this._mainHash = ArraysCompat.copyOf(iArr, iArr.length);
/* 1017 */          this._mainHashShared = false;
                }

/* 1021 */      private void unshareCollision() {
/* 1022 */          Bucket[] bucketArr = this._collList;
/* 1023 */          if (bucketArr == null) {
/* 1024 */              this._collList = new Bucket[32];
                    } else {
/* 1026 */              this._collList = (Bucket[]) ArraysCompat.copyOf(bucketArr, bucketArr.length);
                    }
/* 1028 */          this._collListShared = false;
                }

/* 1032 */      private void unshareNames() {
/* 1033 */          Name[] nameArr = this._mainNames;
/* 1034 */          this._mainNames = (Name[]) ArraysCompat.copyOf(nameArr, nameArr.length);
/* 1035 */          this._mainNamesShared = false;
                }

/* 1039 */      private void expandCollision() {
/* 1040 */          Bucket[] bucketArr = this._collList;
/* 1041 */          this._collList = (Bucket[]) ArraysCompat.copyOf(bucketArr, bucketArr.length * 2);
                }

/* 1051 */      private static Name constructName(int i, String str, int i2, int i3) {
                    return i3 == 0 ? new Name1(str, i, i2) : new Name2(str, i, i2, i3);
                }

/* 1059 */      private static Name constructName(int i, String str, int[] iArr, int i2) {
/* 1060 */          if (i2 < 4) {
                        switch (i2) {
                            case 1:
/* 1063 */                      return new Name1(str, i, iArr[0]);
                            case 2:
/* 1065 */                      return new Name2(str, i, iArr[0], iArr[1]);
                            case 3:
/* 1067 */                      return new Name3(str, i, iArr[0], iArr[1], iArr[2]);
                        }
                    }
/* 1072 */          int[] iArr2 = new int[i2];
/* 1073 */          for (int i3 = 0; i3 < i2; i3++) {
/* 1074 */              iArr2[i3] = iArr[i3];
                    }
/* 1076 */          return new NameN(str, i, iArr2, i2);
                }

/* 1089 */      protected void reportTooManyCollisions(int i) {
/* 1090 */          throw new IllegalStateException("Longest collision chain in symbol table (of size " + this._count + ") now exceeds maximum, " + i + " -- suspect a DoS attack based on hash collisions");
                }

/* 1118 */      private static final class TableInfo {
                    public final int collCount;
                    public final int collEnd;
                    public final Bucket[] collList;
                    public final int count;
                    public final int longestCollisionList;
                    public final int[] mainHash;
                    public final int mainHashMask;
                    public final Name[] mainNames;

/* 1119 */          public TableInfo(int i, int i2, int[] iArr, Name[] nameArr, Bucket[] bucketArr, int i3, int i4, int i5) {
/* 1121 */              this.count = i;
/* 1122 */              this.mainHashMask = i2;
/* 1123 */              this.mainHash = iArr;
/* 1124 */              this.mainNames = nameArr;
/* 1125 */              this.collList = bucketArr;
/* 1126 */              this.collCount = i3;
/* 1127 */              this.collEnd = i4;
/* 1128 */              this.longestCollisionList = i5;
                    }

/* 1131 */          public TableInfo(BytesToNameCanonicalizer bytesToNameCanonicalizer) {
/* 1133 */              this.count = bytesToNameCanonicalizer._count;
/* 1134 */              this.mainHashMask = bytesToNameCanonicalizer._mainHashMask;
/* 1135 */              this.mainHash = bytesToNameCanonicalizer._mainHash;
/* 1136 */              this.mainNames = bytesToNameCanonicalizer._mainNames;
/* 1137 */              this.collList = bytesToNameCanonicalizer._collList;
/* 1138 */              this.collCount = bytesToNameCanonicalizer._collCount;
/* 1139 */              this.collEnd = bytesToNameCanonicalizer._collEnd;
/* 1140 */              this.longestCollisionList = bytesToNameCanonicalizer._longestCollisionList;
                    }
                }

/* 1153 */      static final class Bucket {
                    private final int _length;
                    protected final Name _name;
                    protected final Bucket _next;

/* 1154 */          Bucket(Name name, Bucket bucket) {
/* 1156 */              this._name = name;
/* 1157 */              this._next = bucket;
/* 1158 */              this._length = bucket == null ? 1 : bucket._length + 1;
                    }

/* 1160 */          public int length() {
/* 1161 */              return this._length;
                    }

/* 1164 */          public Name find(int i, int i2, int i3) {
/* 1165 */              if (this._name.hashCode() == i && this._name.equals(i2, i3)) {
/* 1167 */                  return this._name;
                        }
                        for (Bucket bucket = this._next; bucket != null; bucket = bucket._next) {
/* 1171 */                  Name name = bucket._name;
/* 1172 */                  if (name.hashCode() == i && name.equals(i2, i3)) {
/* 1178 */                      return name;
                            }
                        }
/* 1178 */              return null;
                    }

/* 1182 */          public Name find(int i, int[] iArr, int i2) {
/* 1183 */              if (this._name.hashCode() == i && this._name.equals(iArr, i2)) {
/* 1185 */                  return this._name;
                        }
                        for (Bucket bucket = this._next; bucket != null; bucket = bucket._next) {
/* 1189 */                  Name name = bucket._name;
/* 1190 */                  if (name.hashCode() == i && name.equals(iArr, i2)) {
/* 1196 */                      return name;
                            }
                        }
/* 1196 */              return null;
                    }
                }
            }
