            package android.support.v4.net;

            import android.net.TrafficStats;
            import java.net.Socket;
            import java.net.SocketException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    class TrafficStatsCompatIcs {
/* 26 */        TrafficStatsCompatIcs() {
                }

/* 28 */        public static void clearThreadStatsTag() {
/* 29 */            TrafficStats.clearThreadStatsTag();
                }

/* 32 */        public static int getThreadStatsTag() {
/* 33 */            return TrafficStats.getThreadStatsTag();
                }

/* 36 */        public static void incrementOperationCount(int operationCount) {
/* 37 */            TrafficStats.incrementOperationCount(operationCount);
                }

/* 40 */        public static void incrementOperationCount(int tag, int operationCount) {
/* 41 */            TrafficStats.incrementOperationCount(tag, operationCount);
                }

/* 44 */        public static void setThreadStatsTag(int tag) {
/* 45 */            TrafficStats.setThreadStatsTag(tag);
                }

/* 48 */        public static void tagSocket(Socket socket) throws SocketException {
/* 49 */            TrafficStats.tagSocket(socket);
                }

/* 52 */        public static void untagSocket(Socket socket) throws SocketException {
/* 53 */            TrafficStats.untagSocket(socket);
                }
            }
