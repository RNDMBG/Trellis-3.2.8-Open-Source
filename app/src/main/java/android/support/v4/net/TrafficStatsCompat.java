            package android.support.v4.net;

            import android.os.Build;
            import java.net.Socket;
            import java.net.SocketException;
            
            
    public class TrafficStatsCompat {
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

        static class BaseTrafficStatsCompatImpl implements TrafficStatsCompatImpl {
                    private ThreadLocal<SocketTags> mThreadSocketTags = new C00321();

            private static class SocketTags {
                        public int statsTag;

                private SocketTags() {
                    this.statsTag = -1;
                        }

                 SocketTags(C00311 x0) {
                    this();
                        }
                    }

            BaseTrafficStatsCompatImpl() {
                    }

                    
            class C00321 extends ThreadLocal<SocketTags> {
                C00321() {
                        }

                        @Override 
                protected   SocketTags initialValue() {
                    return initialValue();
                        }

                        
                        @Override 
                protected SocketTags initialValue() {
                    return new SocketTags(null);
                        }
                    }

                    @Override 
            public void clearThreadStatsTag() {
                this.mThreadSocketTags.get().statsTag = -1;
                    }

                    @Override 
            public int getThreadStatsTag() {
                return this.mThreadSocketTags.get().statsTag;
                    }

                    @Override 
            public void incrementOperationCount(int operationCount) {
                    }

                    @Override 
            public void incrementOperationCount(int tag, int operationCount) {
                    }

                    @Override 
            public void setThreadStatsTag(int tag) {
                this.mThreadSocketTags.get().statsTag = tag;
                    }

                    @Override 
            public void tagSocket(Socket socket) {
                    }

                    @Override 
            public void untagSocket(Socket socket) {
                    }
                }

        static class IcsTrafficStatsCompatImpl implements TrafficStatsCompatImpl {
            IcsTrafficStatsCompatImpl() {
                    }

                    @Override 
            public void clearThreadStatsTag() {
                TrafficStatsCompatIcs.clearThreadStatsTag();
                    }

                    @Override 
            public int getThreadStatsTag() {
                return TrafficStatsCompatIcs.getThreadStatsTag();
                    }

                    @Override 
            public void incrementOperationCount(int operationCount) {
                TrafficStatsCompatIcs.incrementOperationCount(operationCount);
                    }

                    @Override 
           public void incrementOperationCount(int tag, int operationCount) {
               TrafficStatsCompatIcs.incrementOperationCount(tag, operationCount);
                    }

                    @Override 
           public void setThreadStatsTag(int tag) {
               TrafficStatsCompatIcs.setThreadStatsTag(tag);
                    }

                    @Override 
           public void tagSocket(Socket socket) throws SocketException {
               TrafficStatsCompatIcs.tagSocket(socket);
                    }

                    @Override 
           public void untagSocket(Socket socket) throws SocketException {
               TrafficStatsCompatIcs.untagSocket(socket);
                    }
                }

                static {
           if (Build.VERSION.SDK_INT >= 14) {
               IMPL = new IcsTrafficStatsCompatImpl();
                    } else {
               IMPL = new BaseTrafficStatsCompatImpl();
                    }
                }

       public static void clearThreadStatsTag() {
           IMPL.clearThreadStatsTag();
                }

       public static int getThreadStatsTag() {
           return IMPL.getThreadStatsTag();
                }

       public static void incrementOperationCount(int operationCount) {
           IMPL.incrementOperationCount(operationCount);
                }

       public static void incrementOperationCount(int tag, int operationCount) {
           IMPL.incrementOperationCount(tag, operationCount);
                }

       public static void setThreadStatsTag(int tag) {
           IMPL.setThreadStatsTag(tag);
                }

       public static void tagSocket(Socket socket) throws SocketException {
           IMPL.tagSocket(socket);
                }

       public static void untagSocket(Socket socket) throws SocketException {
           IMPL.untagSocket(socket);
                }
            }
