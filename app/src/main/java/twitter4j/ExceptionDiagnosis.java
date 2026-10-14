            package twitter4j;

            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import java.io.Serializable;
            
            
    final class ExceptionDiagnosis implements Serializable {
                private static final long serialVersionUID = 453958937114285988L;
                String hexString;
                int lineNumberHash;
                int stackLineHash;

                
                Throwable f2540th;

        ExceptionDiagnosis(Throwable th) {
            this(th, new String[0]);
                }

        ExceptionDiagnosis(Throwable th, String[] inclusionFilter) {
            this.hexString = "";
            this.f2540th = th;
            StackTraceElement[] stackTrace = th.getStackTrace();
            this.stackLineHash = 0;
            this.lineNumberHash = 0;
                    for (int i = stackTrace.length - 1; i >= 0; i--) {
                StackTraceElement line = stackTrace[i];
                        for (String filter : inclusionFilter) {
                    if (line.getClassName().startsWith(filter)) {
                        int hash = line.getClassName().hashCode() + line.getMethodName().hashCode();
                        this.stackLineHash = (this.stackLineHash * 31) + hash;
                        this.lineNumberHash = (this.lineNumberHash * 31) + line.getLineNumber();
                                break;
                            }
                        }
                    }
            this.hexString += toHexString(this.stackLineHash) + "-" + toHexString(this.lineNumberHash);
            if (th.getCause() != null) {
                this.hexString += MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + new ExceptionDiagnosis(th.getCause(), inclusionFilter).asHexString();
                    }
                }

        int getStackLineHash() {
            return this.stackLineHash;
                }

        String getStackLineHashAsHex() {
            return toHexString(this.stackLineHash);
                }

        int getLineNumberHash() {
            return this.lineNumberHash;
                }

        String getLineNumberHashAsHex() {
            return toHexString(this.lineNumberHash);
                }

        String asHexString() {
            return this.hexString;
                }

        private String toHexString(int value) {
            String str = "0000000" + Integer.toHexString(value);
            return str.substring(str.length() - 8, str.length());
                }

        public boolean equals(Object o) {
            if (this == o) {
                        return true;
                    }
            if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
            ExceptionDiagnosis that = (ExceptionDiagnosis) o;
                    return this.lineNumberHash == that.lineNumberHash && this.stackLineHash == that.stackLineHash;
                }

        public int hashCode() {
            int result = this.stackLineHash;
           return (result * 31) + this.lineNumberHash;
                }

       public String toString() {
           return "ExceptionDiagnosis{stackLineHash=" + this.stackLineHash + ", lineNumberHash=" + this.lineNumberHash + '}';
                }
            }
