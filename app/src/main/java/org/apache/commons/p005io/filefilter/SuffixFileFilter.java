            package org.apache.commons.p005io.filefilter;

            import java.io.File;
            import java.io.Serializable;
            import java.util.List;
            import org.apache.commons.p005io.IOCase;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 58 */    public class SuffixFileFilter extends AbstractFileFilter implements Serializable {
                private final IOCase caseSensitivity;
                private final String[] suffixes;

/* 59 */        public SuffixFileFilter(String suffix) {
/* 60 */            this(suffix, IOCase.SENSITIVE);
                }

/* 71 */        public SuffixFileFilter(String suffix, IOCase caseSensitivity) {
/* 73 */            if (suffix == null) {
/* 74 */                throw new IllegalArgumentException("The suffix must not be null");
                    }
/* 76 */            this.suffixes = new String[]{suffix};
/* 77 */            this.caseSensitivity = caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity;
                }

/* 89 */        public SuffixFileFilter(String[] suffixes) {
/* 90 */            this(suffixes, IOCase.SENSITIVE);
                }

/* 104 */       public SuffixFileFilter(String[] suffixes, IOCase caseSensitivity) {
/* 106 */           if (suffixes == null) {
/* 107 */               throw new IllegalArgumentException("The array of suffixes must not be null");
                    }
/* 109 */           this.suffixes = new String[suffixes.length];
/* 110 */           System.arraycopy(suffixes, 0, this.suffixes, 0, suffixes.length);
/* 111 */           this.caseSensitivity = caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity;
                }

/* 121 */       public SuffixFileFilter(List<String> suffixes) {
/* 122 */           this(suffixes, IOCase.SENSITIVE);
                }

/* 134 */       public SuffixFileFilter(List<String> suffixes, IOCase caseSensitivity) {
/* 136 */           if (suffixes == null) {
/* 137 */               throw new IllegalArgumentException("The list of suffixes must not be null");
                    }
/* 139 */           this.suffixes = (String[]) suffixes.toArray(new String[suffixes.size()]);
/* 140 */           this.caseSensitivity = caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity;
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter, org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FileFilter
/* 150 */       public boolean accept(File file) {
/* 151 */           String name = file.getName();
/* 152 */           String[] arr$ = this.suffixes;
                    for (String suffix : arr$) {
/* 153 */               if (this.caseSensitivity.checkEndsWith(name, suffix)) {
/* 154 */                   return true;
                        }
                    }
/* 157 */           return false;
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter, org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FilenameFilter
/* 168 */       public boolean accept(File file, String name) {
/* 169 */           String[] arr$ = this.suffixes;
                    for (String suffix : arr$) {
/* 170 */               if (this.caseSensitivity.checkEndsWith(name, suffix)) {
/* 171 */                   return true;
                        }
                    }
/* 174 */           return false;
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter
/* 183 */       public String toString() {
/* 184 */           StringBuilder buffer = new StringBuilder();
/* 185 */           buffer.append(super.toString());
/* 186 */           buffer.append("(");
/* 187 */           if (this.suffixes != null) {
                        for (int i = 0; i < this.suffixes.length; i++) {
/* 189 */                   if (i > 0) {
/* 190 */                       buffer.append(",");
                            }
/* 192 */                   buffer.append(this.suffixes[i]);
                        }
                    }
/* 195 */           buffer.append(")");
/* 196 */           return buffer.toString();
                }
            }
