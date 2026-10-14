            package org.apache.commons.p005io.filefilter;

            import java.io.File;
            import java.io.Serializable;
            import java.util.List;
            import org.apache.commons.p005io.IOCase;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 57 */    public class PrefixFileFilter extends AbstractFileFilter implements Serializable {
                private final IOCase caseSensitivity;
                private final String[] prefixes;

/* 58 */        public PrefixFileFilter(String prefix) {
/* 59 */            this(prefix, IOCase.SENSITIVE);
                }

/* 70 */        public PrefixFileFilter(String prefix, IOCase caseSensitivity) {
/* 72 */            if (prefix == null) {
/* 73 */                throw new IllegalArgumentException("The prefix must not be null");
                    }
/* 75 */            this.prefixes = new String[]{prefix};
/* 76 */            this.caseSensitivity = caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity;
                }

/* 88 */        public PrefixFileFilter(String[] prefixes) {
/* 89 */            this(prefixes, IOCase.SENSITIVE);
                }

/* 103 */       public PrefixFileFilter(String[] prefixes, IOCase caseSensitivity) {
/* 105 */           if (prefixes == null) {
/* 106 */               throw new IllegalArgumentException("The array of prefixes must not be null");
                    }
/* 108 */           this.prefixes = new String[prefixes.length];
/* 109 */           System.arraycopy(prefixes, 0, this.prefixes, 0, prefixes.length);
/* 110 */           this.caseSensitivity = caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity;
                }

/* 120 */       public PrefixFileFilter(List<String> prefixes) {
/* 121 */           this(prefixes, IOCase.SENSITIVE);
                }

/* 133 */       public PrefixFileFilter(List<String> prefixes, IOCase caseSensitivity) {
/* 135 */           if (prefixes == null) {
/* 136 */               throw new IllegalArgumentException("The list of prefixes must not be null");
                    }
/* 138 */           this.prefixes = (String[]) prefixes.toArray(new String[prefixes.size()]);
/* 139 */           this.caseSensitivity = caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity;
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter, org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FileFilter
/* 149 */       public boolean accept(File file) {
/* 150 */           String name = file.getName();
/* 151 */           String[] arr$ = this.prefixes;
                    for (String prefix : arr$) {
/* 152 */               if (this.caseSensitivity.checkStartsWith(name, prefix)) {
/* 153 */                   return true;
                        }
                    }
/* 156 */           return false;
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter, org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FilenameFilter
/* 167 */       public boolean accept(File file, String name) {
/* 168 */           String[] arr$ = this.prefixes;
                    for (String prefix : arr$) {
/* 169 */               if (this.caseSensitivity.checkStartsWith(name, prefix)) {
/* 170 */                   return true;
                        }
                    }
/* 173 */           return false;
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter
/* 182 */       public String toString() {
/* 183 */           StringBuilder buffer = new StringBuilder();
/* 184 */           buffer.append(super.toString());
/* 185 */           buffer.append("(");
/* 186 */           if (this.prefixes != null) {
                        for (int i = 0; i < this.prefixes.length; i++) {
/* 188 */                   if (i > 0) {
/* 189 */                       buffer.append(",");
                            }
/* 191 */                   buffer.append(this.prefixes[i]);
                        }
                    }
/* 194 */           buffer.append(")");
/* 195 */           return buffer.toString();
                }
            }
