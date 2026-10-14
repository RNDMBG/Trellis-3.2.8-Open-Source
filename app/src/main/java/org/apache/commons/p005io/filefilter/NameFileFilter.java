            package org.apache.commons.p005io.filefilter;

            import java.io.File;
            import java.io.Serializable;
            import java.util.List;
            import org.apache.commons.p005io.IOCase;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 56 */    public class NameFileFilter extends AbstractFileFilter implements Serializable {
                private final IOCase caseSensitivity;
                private final String[] names;

/* 57 */        public NameFileFilter(String name) {
/* 58 */            this(name, (IOCase) null);
                }

/* 67 */        public NameFileFilter(String name, IOCase caseSensitivity) {
/* 69 */            if (name == null) {
/* 70 */                throw new IllegalArgumentException("The wildcard must not be null");
                    }
/* 72 */            this.names = new String[]{name};
/* 73 */            this.caseSensitivity = caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity;
                }

/* 85 */        public NameFileFilter(String[] names) {
/* 86 */            this(names, (IOCase) null);
                }

/* 98 */        public NameFileFilter(String[] names, IOCase caseSensitivity) {
/* 100 */           if (names == null) {
/* 101 */               throw new IllegalArgumentException("The array of names must not be null");
                    }
/* 103 */           this.names = new String[names.length];
/* 104 */           System.arraycopy(names, 0, this.names, 0, names.length);
/* 105 */           this.caseSensitivity = caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity;
                }

/* 115 */       public NameFileFilter(List<String> names) {
/* 116 */           this(names, (IOCase) null);
                }

/* 126 */       public NameFileFilter(List<String> names, IOCase caseSensitivity) {
/* 128 */           if (names == null) {
/* 129 */               throw new IllegalArgumentException("The list of names must not be null");
                    }
/* 131 */           this.names = (String[]) names.toArray(new String[names.size()]);
/* 132 */           this.caseSensitivity = caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity;
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter, org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FileFilter
/* 143 */       public boolean accept(File file) {
/* 144 */           String name = file.getName();
/* 145 */           String[] arr$ = this.names;
                    for (String name2 : arr$) {
/* 146 */               if (this.caseSensitivity.checkEquals(name, name2)) {
/* 147 */                   return true;
                        }
                    }
/* 150 */           return false;
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter, org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FilenameFilter
/* 161 */       public boolean accept(File dir, String name) {
/* 162 */           String[] arr$ = this.names;
                    for (String name2 : arr$) {
/* 163 */               if (this.caseSensitivity.checkEquals(name, name2)) {
/* 164 */                   return true;
                        }
                    }
/* 167 */           return false;
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter
/* 176 */       public String toString() {
/* 177 */           StringBuilder buffer = new StringBuilder();
/* 178 */           buffer.append(super.toString());
/* 179 */           buffer.append("(");
/* 180 */           if (this.names != null) {
                        for (int i = 0; i < this.names.length; i++) {
/* 182 */                   if (i > 0) {
/* 183 */                       buffer.append(",");
                            }
/* 185 */                   buffer.append(this.names[i]);
                        }
                    }
/* 188 */           buffer.append(")");
/* 189 */           return buffer.toString();
                }
            }
