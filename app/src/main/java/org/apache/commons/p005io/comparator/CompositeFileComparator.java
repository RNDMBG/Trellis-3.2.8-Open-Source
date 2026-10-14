            package org.apache.commons.p005io.comparator;

            import java.io.File;
            import java.io.Serializable;
            import java.util.ArrayList;
            import java.util.Comparator;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 43 */    public class CompositeFileComparator extends AbstractFileComparator implements Serializable {
                private static final Comparator<?>[] NO_COMPARATORS = new Comparator[0];
                private final Comparator<File>[] delegates;




                /* JADX WARN: Multi-variable type inference failed */
/* 55 */        public CompositeFileComparator(Comparator<File>... delegates) {
/* 57 */            if (delegates == null) {
/* 58 */                this.delegates = NO_COMPARATORS;
                    } else {
/* 60 */                this.delegates = new Comparator[delegates.length];
/* 61 */                System.arraycopy(delegates, 0, this.delegates, 0, delegates.length);
                    }
                }

                /* JADX WARN: Multi-variable type inference failed */
/* 70 */        public CompositeFileComparator(Iterable<Comparator<File>> delegates) {
/* 72 */            if (delegates == null) {
/* 73 */                this.delegates = NO_COMPARATORS;
/* 81 */                return;
                    }
/* 75 */            List<Comparator<File>> list = new ArrayList<>();
                    for (Comparator<File> comparator : delegates) {
/* 77 */                list.add(comparator);
                    }
/* 79 */            this.delegates = (Comparator[]) list.toArray(new Comparator[list.size()]);
                }

                /* JADX INFO: renamed from: compare, reason: avoid collision after fix types in other method */
/* 91 */        public int compare2(File file1, File file2) {
/* 92 */            int result = 0;
                    for (Comparator<File> delegate : this.delegates) {
/* 94 */                result = delegate.compare(file1, file2);
/* 95 */                if (result != 0) {
                            break;
                        }
                    }
/* 99 */            return result;
                }

                @Override // org.apache.commons.p005io.comparator.AbstractFileComparator
/* 108 */       public String toString() {
/* 109 */           StringBuilder builder = new StringBuilder();
/* 110 */           builder.append(super.toString());
/* 111 */           builder.append('{');
                    for (int i = 0; i < this.delegates.length; i++) {
/* 113 */               if (i > 0) {
/* 114 */                   builder.append(',');
                        }
/* 116 */               builder.append(this.delegates[i]);
                    }
/* 118 */           builder.append('}');
/* 119 */           return builder.toString();
                }
            }
