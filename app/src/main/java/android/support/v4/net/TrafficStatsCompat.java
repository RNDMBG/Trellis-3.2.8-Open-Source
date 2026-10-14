            package android.support.v4.net;

            import android.os.Build;
            import java.net.Socket;
            import java.net.SocketException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 37 */    public class TrafficStatsCompat {
                private static final TrafficStatsCompatImpl IMPL;

                interface TrafficStatsCompatImpl {
                    void clearThreadStatsTag();

                    int getThreadStatsTag();

                    void incrementOperationCount(int i);

                    void incrementOperationCount(int i, int i2);

                    void setThreadStatsTag(int i);

                    void tagSocket(Socket socket) throws SocketException;

                    void untagSocket(Socket socket) throws SocketException;
                }

/* 38 */        static class BaseTrafficStatsCompatImpl implements TrafficStatsCompatImpl {
                    private ThreadLocal<SocketTags> mThreadSocketTags = new C00321();

/* 39 */            private static class SocketTags {
                        public int statsTag;

/* 40 */                private SocketTags() {
/* 42 */                    this.statsTag = -1;
                        }

/* 40 */                /* synthetic */ SocketTags(C00311 x0) {
/* 41 */                    this();
                        }
                    }

/* 39 */            BaseTrafficStatsCompatImpl() {
                    }

                    /* JADX INFO: renamed from: android.support.v4.net.TrafficStatsCompat$BaseTrafficStatsCompatImpl$1 */
/* 43 */            class C00321 extends ThreadLocal<SocketTags> {
/* 44 */                C00321() {
                        }

                        @Override // java.lang.ThreadLocal
/* 44 */                protected /* bridge */ /* synthetic */ SocketTags initialValue() {
/* 45 */                    return initialValue();
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // java.lang.ThreadLocal
/* 47 */                protected SocketTags initialValue() {
/* 48 */                    return new SocketTags(null);
                        }
                    }

                    @Override // android.support.v4.net.TrafficStatsCompat.TrafficStatsCompatImpl
/* 53 */            public void clearThreadStatsTag() {
/* 54 */                this.mThreadSocketTags.get().statsTag = -1;
                    }

                    @Override // android.support.v4.net.TrafficStatsCompat.TrafficStatsCompatImpl
/* 58 */            public int getThreadStatsTag() {
/* 59 */                return this.mThreadSocketTags.get().statsTag;
                    }

                    @Override // android.support.v4.net.TrafficStatsCompat.TrafficStatsCompatImpl
/* 63 */            public void incrementOperationCount(int operationCount) {
                    }

                    @Override // android.support.v4.net.TrafficStatsCompat.TrafficStatsCompatImpl
/* 67 */            public void incrementOperationCount(int tag, int operationCount) {
                    }

                    @Override // android.support.v4.net.TrafficStatsCompat.TrafficStatsCompatImpl
/* 71 */            public void setThreadStatsTag(int tag) {
/* 72 */                this.mThreadSocketTags.get().statsTag = tag;
                    }

                    @Override // android.support.v4.net.TrafficStatsCompat.TrafficStatsCompatImpl
/* 76 */            public void tagSocket(Socket socket) {
                    }

                    @Override // android.support.v4.net.TrafficStatsCompat.TrafficStatsCompatImpl
/* 80 */            public void untagSocket(Socket socket) {
                    }
                }

/* 82 */        static class IcsTrafficStatsCompatImpl implements TrafficStatsCompatImpl {
/* 83 */            IcsTrafficStatsCompatImpl() {
                    }

                    @Override // android.support.v4.net.TrafficStatsCompat.TrafficStatsCompatImpl
/* 86 */            public void clearThreadStatsTag() {
/* 87 */                TrafficStatsCompatIcs.clearThreadStatsTag();
                    }

                    @Override // android.support.v4.net.TrafficStatsCompat.TrafficStatsCompatImpl
/* 91 */            public int getThreadStatsTag() {
/* 92 */                return TrafficStatsCompatIcs.getThreadStatsTag();
                    }

                    @Override // android.support.v4.net.TrafficStatsCompat.TrafficStatsCompatImpl
/* 96 */            public void incrementOperationCount(int operationCount) {
/* 97 */                TrafficStatsCompatIcs.incrementOperationCount(operationCount);
                    }

                    @Override // android.support.v4.net.TrafficStatsCompat.TrafficStatsCompatImpl
/* 101 */           public void incrementOperationCount(int tag, int operationCount) {
/* 102 */               TrafficStatsCompatIcs.incrementOperationCount(tag, operationCount);
                    }

                    @Override // android.support.v4.net.TrafficStatsCompat.TrafficStatsCompatImpl
/* 106 */           public void setThreadStatsTag(int tag) {
/* 107 */               TrafficStatsCompatIcs.setThreadStatsTag(tag);
                    }

                    @Override // android.support.v4.net.TrafficStatsCompat.TrafficStatsCompatImpl
/* 111 */           public void tagSocket(Socket socket) throws SocketException {
/* 112 */               TrafficStatsCompatIcs.tagSocket(socket);
                    }

                    @Override // android.support.v4.net.TrafficStatsCompat.TrafficStatsCompatImpl
/* 116 */           public void untagSocket(Socket socket) throws SocketException {
/* 117 */               TrafficStatsCompatIcs.untagSocket(socket);
                    }
                }

                static {
/* 124 */           if (Build.VERSION.SDK_INT >= 14) {
/* 125 */               IMPL = new IcsTrafficStatsCompatImpl();
                    } else {
/* 127 */               IMPL = new BaseTrafficStatsCompatImpl();
                    }
                }

/* 135 */       public static void clearThreadStatsTag() {
/* 136 */           IMPL.clearThreadStatsTag();
                }

/* 144 */       public static int getThreadStatsTag() {
/* 145 */           return IMPL.getThreadStatsTag();
                }

/* 155 */       public static void incrementOperationCount(int operationCount) {
/* 156 */           IMPL.incrementOperationCount(operationCount);
                }

/* 166 */       public static void incrementOperationCount(int tag, int operationCount) {
/* 167 */           IMPL.incrementOperationCount(tag, operationCount);
                }

/* 181 */       public static void setThreadStatsTag(int tag) {
/* 182 */           IMPL.setThreadStatsTag(tag);
                }

/* 193 */       public static void tagSocket(Socket socket) throws SocketException {
/* 194 */           IMPL.tagSocket(socket);
                }

/* 200 */       public static void untagSocket(Socket socket) throws SocketException {
/* 201 */           IMPL.untagSocket(socket);
                }
            }
