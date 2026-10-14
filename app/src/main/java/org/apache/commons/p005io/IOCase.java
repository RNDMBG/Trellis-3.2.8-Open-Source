            package org.apache.commons.p005io;

            import java.io.Serializable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 40 */    public final class IOCase implements Serializable {
                public static final IOCase SYSTEM;
                private static final long serialVersionUID = -6343169151696340687L;
                private final String name;
                private final transient boolean sensitive;
                public static final IOCase SENSITIVE = new IOCase("Sensitive", true);
                public static final IOCase INSENSITIVE = new IOCase("Insensitive", false);

                static {
/* 61 */            SYSTEM = new IOCase("System", FilenameUtils.isSystemWindows() ? false : true);
                }

/* 80 */        public static IOCase forName(String name) {
/* 81 */            if (SENSITIVE.name.equals(name)) {
/* 82 */                return SENSITIVE;
                    }
/* 84 */            if (INSENSITIVE.name.equals(name)) {
/* 85 */                return INSENSITIVE;
                    }
/* 87 */            if (SYSTEM.name.equals(name)) {
/* 88 */                return SYSTEM;
                    }
/* 90 */            throw new IllegalArgumentException("Invalid IOCase name: " + name);
                }

/* 99 */        private IOCase(String name, boolean sensitive) {
/* 101 */           this.name = name;
/* 102 */           this.sensitive = sensitive;
                }

/* 111 */       private Object readResolve() {
/* 112 */           return forName(this.name);
                }

/* 121 */       public String getName() {
/* 122 */           return this.name;
                }

/* 130 */       public boolean isCaseSensitive() {
/* 131 */           return this.sensitive;
                }

/* 146 */       public int checkCompareTo(String str1, String str2) {
/* 147 */           if (str1 == null || str2 == null) {
/* 148 */               throw new NullPointerException("The strings must not be null");
                    }
                    return this.sensitive ? str1.compareTo(str2) : str1.compareToIgnoreCase(str2);
                }

/* 164 */       public boolean checkEquals(String str1, String str2) {
/* 165 */           if (str1 == null || str2 == null) {
/* 166 */               throw new NullPointerException("The strings must not be null");
                    }
                    return this.sensitive ? str1.equals(str2) : str1.equalsIgnoreCase(str2);
                }

/* 182 */       public boolean checkStartsWith(String str, String start) {
                    return str.regionMatches(!this.sensitive, 0, start, 0, start.length());
                }

/* 197 */       public boolean checkEndsWith(String str, String end) {
/* 198 */           int endLen = end.length();
                    return str.regionMatches(!this.sensitive, str.length() - endLen, end, 0, endLen);
                }

/* 217 */       public int checkIndexOf(String str, int strStartIndex, String search) {
/* 218 */           int endIndex = str.length() - search.length();
/* 219 */           if (endIndex >= strStartIndex) {
                        for (int i = strStartIndex; i <= endIndex; i++) {
/* 221 */                   if (checkRegionMatches(str, i, search)) {
/* 226 */                       return i;
                            }
                        }
                    }
/* 226 */           return -1;
                }

/* 241 */       public boolean checkRegionMatches(String str, int strStartIndex, String search) {
                    return str.regionMatches(!this.sensitive, strStartIndex, search, 0, search.length());
                }

/* 252 */       public String toString() {
/* 253 */           return this.name;
                }
            }
