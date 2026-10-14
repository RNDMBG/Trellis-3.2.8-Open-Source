            package twitter4j;

            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import java.io.Serializable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 29 */    final class ExceptionDiagnosis implements Serializable {
                private static final long serialVersionUID = 453958937114285988L;
                String hexString;
                int lineNumberHash;
                int stackLineHash;

                /* JADX INFO: renamed from: th */
                Throwable f2540th;

/* 30 */        ExceptionDiagnosis(Throwable th) {
/* 31 */            this(th, new String[0]);
                }

/* 33 */        ExceptionDiagnosis(Throwable th, String[] inclusionFilter) {
/* 26 */            this.hexString = "";
/* 35 */            this.f2540th = th;
/* 37 */            StackTraceElement[] stackTrace = th.getStackTrace();
/* 38 */            this.stackLineHash = 0;
/* 39 */            this.lineNumberHash = 0;
                    for (int i = stackTrace.length - 1; i >= 0; i--) {
/* 41 */                StackTraceElement line = stackTrace[i];
                        for (String filter : inclusionFilter) {
/* 43 */                    if (line.getClassName().startsWith(filter)) {
/* 44 */                        int hash = line.getClassName().hashCode() + line.getMethodName().hashCode();
/* 45 */                        this.stackLineHash = (this.stackLineHash * 31) + hash;
/* 46 */                        this.lineNumberHash = (this.lineNumberHash * 31) + line.getLineNumber();
                                break;
                            }
                        }
                    }
/* 51 */            this.hexString += toHexString(this.stackLineHash) + "-" + toHexString(this.lineNumberHash);
/* 52 */            if (th.getCause() != null) {
/* 53 */                this.hexString += MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + new ExceptionDiagnosis(th.getCause(), inclusionFilter).asHexString();
                    }
                }

/* 59 */        int getStackLineHash() {
/* 60 */            return this.stackLineHash;
                }

/* 63 */        String getStackLineHashAsHex() {
/* 64 */            return toHexString(this.stackLineHash);
                }

/* 67 */        int getLineNumberHash() {
/* 68 */            return this.lineNumberHash;
                }

/* 71 */        String getLineNumberHashAsHex() {
/* 72 */            return toHexString(this.lineNumberHash);
                }

/* 75 */        String asHexString() {
/* 76 */            return this.hexString;
                }

/* 79 */        private String toHexString(int value) {
/* 80 */            String str = "0000000" + Integer.toHexString(value);
/* 81 */            return str.substring(str.length() - 8, str.length());
                }

/* 85 */        public boolean equals(Object o) {
/* 86 */            if (this == o) {
                        return true;
                    }
/* 87 */            if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
/* 89 */            ExceptionDiagnosis that = (ExceptionDiagnosis) o;
                    return this.lineNumberHash == that.lineNumberHash && this.stackLineHash == that.stackLineHash;
                }

/* 98 */        public int hashCode() {
/* 99 */            int result = this.stackLineHash;
/* 100 */           return (result * 31) + this.lineNumberHash;
                }

/* 105 */       public String toString() {
/* 106 */           return "ExceptionDiagnosis{stackLineHash=" + this.stackLineHash + ", lineNumberHash=" + this.lineNumberHash + '}';
                }
            }
