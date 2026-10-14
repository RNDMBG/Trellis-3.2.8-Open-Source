            package twitter4j;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public final class Version {
                private static final String TITLE = "Twitter4J";
                private static final String VERSION = "3.0.6-SNAPSHOT";

/* 25 */        private Version() {
/* 27 */            throw new AssertionError();
                }

/* 30 */        public static String getVersion() {
/* 31 */            return VERSION;
                }

/* 39 */        public static void main(String[] args) {
/* 40 */            System.out.println("Twitter4J 3.0.6-SNAPSHOT");
                }
            }
