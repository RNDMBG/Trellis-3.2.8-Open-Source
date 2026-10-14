            package org.apache.commons.p005io.filefilter;

            import java.io.File;
            import java.io.Serializable;
            import java.util.List;
            import org.apache.commons.p005io.FilenameUtils;
            import org.apache.commons.p005io.IOCase;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 63 */    public class WildcardFileFilter extends AbstractFileFilter implements Serializable {
                private final IOCase caseSensitivity;
                private final String[] wildcards;

/* 64 */        public WildcardFileFilter(String wildcard) {
/* 65 */            this(wildcard, (IOCase) null);
                }

/* 74 */        public WildcardFileFilter(String wildcard, IOCase caseSensitivity) {
/* 76 */            if (wildcard == null) {
/* 77 */                throw new IllegalArgumentException("The wildcard must not be null");
                    }
/* 79 */            this.wildcards = new String[]{wildcard};
/* 80 */            this.caseSensitivity = caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity;
                }

/* 92 */        public WildcardFileFilter(String[] wildcards) {
/* 93 */            this(wildcards, (IOCase) null);
                }

/* 105 */       public WildcardFileFilter(String[] wildcards, IOCase caseSensitivity) {
/* 107 */           if (wildcards == null) {
/* 108 */               throw new IllegalArgumentException("The wildcard array must not be null");
                    }
/* 110 */           this.wildcards = new String[wildcards.length];
/* 111 */           System.arraycopy(wildcards, 0, this.wildcards, 0, wildcards.length);
/* 112 */           this.caseSensitivity = caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity;
                }

/* 122 */       public WildcardFileFilter(List<String> wildcards) {
/* 123 */           this(wildcards, (IOCase) null);
                }

/* 133 */       public WildcardFileFilter(List<String> wildcards, IOCase caseSensitivity) {
/* 135 */           if (wildcards == null) {
/* 136 */               throw new IllegalArgumentException("The wildcard list must not be null");
                    }
/* 138 */           this.wildcards = (String[]) wildcards.toArray(new String[wildcards.size()]);
/* 139 */           this.caseSensitivity = caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity;
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter, org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FilenameFilter
/* 151 */       public boolean accept(File dir, String name) {
/* 152 */           String[] arr$ = this.wildcards;
                    for (String wildcard : arr$) {
/* 153 */               if (FilenameUtils.wildcardMatch(name, wildcard, this.caseSensitivity)) {
/* 154 */                   return true;
                        }
                    }
/* 157 */           return false;
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter, org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FileFilter
/* 167 */       public boolean accept(File file) {
/* 168 */           String name = file.getName();
/* 169 */           String[] arr$ = this.wildcards;
                    for (String wildcard : arr$) {
/* 170 */               if (FilenameUtils.wildcardMatch(name, wildcard, this.caseSensitivity)) {
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
/* 187 */           if (this.wildcards != null) {
                        for (int i = 0; i < this.wildcards.length; i++) {
/* 189 */                   if (i > 0) {
/* 190 */                       buffer.append(",");
                            }
/* 192 */                   buffer.append(this.wildcards[i]);
                        }
                    }
/* 195 */           buffer.append(")");
/* 196 */           return buffer.toString();
                }
            }
