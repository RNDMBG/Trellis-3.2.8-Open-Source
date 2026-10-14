            package com.squareup.okhttp;

            import com.squareup.okhttp.internal.Platform;
            import com.squareup.okhttp.internal.Util;
            import java.net.SocketException;
            import java.util.ArrayList;
            import java.util.LinkedList;
            import java.util.List;
            import java.util.ListIterator;
            import java.util.concurrent.ExecutorService;
            import java.util.concurrent.LinkedBlockingQueue;
            import java.util.concurrent.ThreadPoolExecutor;
            import java.util.concurrent.TimeUnit;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 52 */    public final class ConnectionPool {
                private static final long DEFAULT_KEEP_ALIVE_DURATION_MS = 300000;
                private static final int MAX_CONNECTIONS_TO_CLEANUP = 2;
                private static final ConnectionPool systemDefault;
                private final long keepAliveDurationNs;
                private final int maxIdleConnections;
                private final LinkedList<Connection> connections = new LinkedList<>();
                private final ExecutorService executorService = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp ConnectionPool", true));
                private final Runnable connectionsCleanupRunnable = new RunnableC12601();

/* 53 */        static /* synthetic */ LinkedList access$000(ConnectionPool x0) {
/* 54 */            return x0.connections;
                }

/* 53 */        static /* synthetic */ long access$100(ConnectionPool x0) {
/* 54 */            return x0.keepAliveDurationNs;
                }

/* 53 */        static /* synthetic */ int access$200(ConnectionPool x0) {
/* 54 */            return x0.maxIdleConnections;
                }

                static {
/* 61 */            String keepAlive = System.getProperty("http.keepAlive");
/* 62 */            String keepAliveDuration = System.getProperty("http.keepAliveDuration");
/* 63 */            String maxIdleConnections = System.getProperty("http.maxConnections");
/* 64 */            long keepAliveDurationMs = keepAliveDuration != null ? Long.parseLong(keepAliveDuration) : 300000L;
/* 66 */            if (keepAlive != null && !Boolean.parseBoolean(keepAlive)) {
/* 67 */                systemDefault = new ConnectionPool(0, keepAliveDurationMs);
                    } else if (maxIdleConnections != null) {
/* 69 */                systemDefault = new ConnectionPool(Integer.parseInt(maxIdleConnections), keepAliveDurationMs);
                    } else {
/* 71 */                systemDefault = new ConnectionPool(5, keepAliveDurationMs);
                    }
                }

                /* JADX INFO: renamed from: com.squareup.okhttp.ConnectionPool$1 */
/* 83 */        class RunnableC12601 implements Runnable {
/* 84 */            RunnableC12601() {
                    }

                    @Override // java.lang.Runnable
/* 86 */            public void run() {
/* 87 */                List<Connection> expiredConnections = new ArrayList<>(2);
/* 88 */                int idleConnectionCount = 0;
                        synchronized (ConnectionPool.this) {
                            try {
/* 90 */                        ListIterator<Connection> i = ConnectionPool.access$000(ConnectionPool.this).listIterator(ConnectionPool.access$000(ConnectionPool.this).size());
/* 91 */                        while (i.hasPrevious()) {
/* 92 */                            Connection connection = i.previous();
/* 93 */                            if (!connection.isAlive() || connection.isExpired(ConnectionPool.access$100(ConnectionPool.this))) {
/* 94 */                                i.remove();
/* 95 */                                expiredConnections.add(connection);
/* 96 */                                if (expiredConnections.size() == 2) {
                                            break;
                                        }
                                    } else if (connection.isIdle()) {
/* 98 */                                idleConnectionCount++;
                                    }
                                }
/* 102 */                       ListIterator<Connection> i2 = ConnectionPool.access$000(ConnectionPool.this).listIterator(ConnectionPool.access$000(ConnectionPool.this).size());
/* 103 */                       while (i2.hasPrevious() && idleConnectionCount > ConnectionPool.access$200(ConnectionPool.this)) {
/* 104 */                           Connection connection2 = i2.previous();
/* 105 */                           if (connection2.isIdle()) {
/* 106 */                               expiredConnections.add(connection2);
/* 107 */                               i2.remove();
/* 108 */                               idleConnectionCount--;
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        for (Connection expiredConnection : expiredConnections) {
/* 113 */                   Util.closeQuietly(expiredConnection.getSocket());
                        }
                    }
                }

/* 117 */       public ConnectionPool(int maxIdleConnections, long keepAliveDurationMs) {
/* 119 */           this.maxIdleConnections = maxIdleConnections;
/* 120 */           this.keepAliveDurationNs = keepAliveDurationMs * 1000 * 1000;
                }

/* 127 */       List<Connection> getConnections() {
                    ArrayList arrayList;
/* 128 */           waitForCleanupCallableToRun();
                    synchronized (this) {
                        try {
/* 130 */                   arrayList = new ArrayList(this.connections);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return arrayList;
                }

                /* JADX INFO: renamed from: com.squareup.okhttp.ConnectionPool$2 */
/* 138 */       class RunnableC12612 implements Runnable {
/* 139 */           RunnableC12612() {
                    }

                    @Override // java.lang.Runnable
/* 141 */           public void run() {
                    }
                }

/* 139 */       private void waitForCleanupCallableToRun() {
                    try {
/* 140 */               this.executorService.submit(new RunnableC12612()).get();
                    } catch (Exception e) {
/* 145 */               throw new AssertionError();
                    }
                }

/* 149 */       public static ConnectionPool getDefault() {
/* 150 */           return systemDefault;
                }

/* 154 */       public synchronized int getConnectionCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.connections.size();
                }

/* 159 */       public synchronized int getSpdyConnectionCount() {
                    int total;
                    total = 0;
                    try {
                        for (Connection connection : this.connections) {
/* 162 */                   if (connection.isSpdy()) {
                                total++;
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return total;
                }

/* 168 */       public synchronized int getHttpConnectionCount() {
                    int total;
                    total = 0;
                    try {
                        for (Connection connection : this.connections) {
/* 171 */                   if (!connection.isSpdy()) {
                                total++;
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return total;
                }

/* 177 */       public synchronized Connection get(Address address) {
                    Connection foundConnection;
                    foundConnection = null;
                    try {
/* 179 */               ListIterator<Connection> i = this.connections.listIterator(this.connections.size());
/* 180 */               while (i.hasPrevious()) {
/* 181 */                   Connection connection = i.previous();
/* 182 */                   if (connection.getRoute().getAddress().equals(address) && connection.isAlive() && System.nanoTime() - connection.getIdleStartTimeNs() < this.keepAliveDurationNs) {
/* 187 */                       i.remove();
/* 188 */                       if (!connection.isSpdy()) {
                                    try {
/* 190 */                               Platform.get().tagSocket(connection.getSocket());
                                    } catch (SocketException e) {
/* 192 */                               Util.closeQuietly(connection.getSocket());
/* 194 */                               Platform.get().logW("Unable to tagSocket(): " + e);
                                    }
                                }
/* 198 */                       foundConnection = connection;
                                break;
                            }
                        }
/* 202 */               if (foundConnection != null && foundConnection.isSpdy()) {
/* 203 */                   this.connections.addFirst(foundConnection);
                        }
/* 206 */               this.executorService.execute(this.connectionsCleanupRunnable);
                    } catch (Throwable th) {
                        throw th;
                    }
                    return foundConnection;
                }

/* 216 */       void recycle(Connection connection) {
/* 217 */           if (!connection.isSpdy() && connection.clearOwner()) {
/* 225 */               if (!connection.isAlive()) {
/* 226 */                   Util.closeQuietly(connection.getSocket());
/* 246 */                   return;
                        }
                        try {
/* 231 */                   Platform.get().untagSocket(connection.getSocket());
                            synchronized (this) {
                                try {
/* 240 */                           this.connections.addFirst(connection);
/* 241 */                           connection.incrementRecycleCount();
/* 242 */                           connection.resetIdleStartTime();
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
/* 245 */                   this.executorService.execute(this.connectionsCleanupRunnable);
                        } catch (SocketException e) {
/* 234 */                   Platform.get().logW("Unable to untagSocket(): " + e);
/* 235 */                   Util.closeQuietly(connection.getSocket());
                        }
                    }
                }

/* 252 */       void share(Connection connection) {
/* 253 */           if (!connection.isSpdy()) {
                        throw new IllegalArgumentException();
                    }
/* 254 */           this.executorService.execute(this.connectionsCleanupRunnable);
/* 255 */           if (connection.isAlive()) {
                        synchronized (this) {
                            try {
/* 257 */                       this.connections.addFirst(connection);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

/* 264 */       public void evictAll() {
                    List<Connection> connections;
                    synchronized (this) {
                        try {
/* 266 */                   connections = new ArrayList<>(this.connections);
/* 267 */                   this.connections.clear();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    int size = connections.size();
                    for (int i = 0; i < size; i++) {
/* 271 */               Util.closeQuietly(connections.get(i).getSocket());
                    }
                }
            }
