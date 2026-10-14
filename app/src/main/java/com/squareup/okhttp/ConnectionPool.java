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
            
            
    public final class ConnectionPool {
                private static final long DEFAULT_KEEP_ALIVE_DURATION_MS = 300000;
                private static final int MAX_CONNECTIONS_TO_CLEANUP = 2;
                private static final ConnectionPool systemDefault;
                private final long keepAliveDurationNs;
                private final int maxIdleConnections;
                private final LinkedList<Connection> connections = new LinkedList<>();
                private final ExecutorService executorService = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp ConnectionPool", true));
                private final Runnable connectionsCleanupRunnable = new RunnableC12601();

        static  LinkedList access$000(ConnectionPool x0) {
            return x0.connections;
                }

        static  long access$100(ConnectionPool x0) {
            return x0.keepAliveDurationNs;
                }

        static  int access$200(ConnectionPool x0) {
            return x0.maxIdleConnections;
                }

                static {
            String keepAlive = System.getProperty("http.keepAlive");
            String keepAliveDuration = System.getProperty("http.keepAliveDuration");
            String maxIdleConnections = System.getProperty("http.maxConnections");
            long keepAliveDurationMs = keepAliveDuration != null ? Long.parseLong(keepAliveDuration) : 300000L;
            if (keepAlive != null && !Boolean.parseBoolean(keepAlive)) {
                systemDefault = new ConnectionPool(0, keepAliveDurationMs);
                    } else if (maxIdleConnections != null) {
                systemDefault = new ConnectionPool(Integer.parseInt(maxIdleConnections), keepAliveDurationMs);
                    } else {
                systemDefault = new ConnectionPool(5, keepAliveDurationMs);
                    }
                }

                
        class RunnableC12601 implements Runnable {
            RunnableC12601() {
                    }

                    @Override 
            public void run() {
                List<Connection> expiredConnections = new ArrayList<>(2);
                int idleConnectionCount = 0;
                        synchronized (ConnectionPool.this) {
                            try {
                        ListIterator<Connection> i = ConnectionPool.access$000(ConnectionPool.this).listIterator(ConnectionPool.access$000(ConnectionPool.this).size());
                        while (i.hasPrevious()) {
                            Connection connection = i.previous();
                            if (!connection.isAlive() || connection.isExpired(ConnectionPool.access$100(ConnectionPool.this))) {
                                i.remove();
                                expiredConnections.add(connection);
                                if (expiredConnections.size() == 2) {
                                            break;
                                        }
                                    } else if (connection.isIdle()) {
                                idleConnectionCount++;
                                    }
                                }
                       ListIterator<Connection> i2 = ConnectionPool.access$000(ConnectionPool.this).listIterator(ConnectionPool.access$000(ConnectionPool.this).size());
                       while (i2.hasPrevious() && idleConnectionCount > ConnectionPool.access$200(ConnectionPool.this)) {
                           Connection connection2 = i2.previous();
                           if (connection2.isIdle()) {
                               expiredConnections.add(connection2);
                               i2.remove();
                               idleConnectionCount--;
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        for (Connection expiredConnection : expiredConnections) {
                   Util.closeQuietly(expiredConnection.getSocket());
                        }
                    }
                }

       public ConnectionPool(int maxIdleConnections, long keepAliveDurationMs) {
           this.maxIdleConnections = maxIdleConnections;
           this.keepAliveDurationNs = keepAliveDurationMs * 1000 * 1000;
                }

       List<Connection> getConnections() {
                    ArrayList arrayList;
           waitForCleanupCallableToRun();
                    synchronized (this) {
                        try {
                   arrayList = new ArrayList(this.connections);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return arrayList;
                }

                
       class RunnableC12612 implements Runnable {
           RunnableC12612() {
                    }

                    @Override 
           public void run() {
                    }
                }

       private void waitForCleanupCallableToRun() {
                    try {
               this.executorService.submit(new RunnableC12612()).get();
                    } catch (Exception e) {
               throw new AssertionError();
                    }
                }

       public static ConnectionPool getDefault() {
           return systemDefault;
                }

       public synchronized int getConnectionCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.connections.size();
                }

       public synchronized int getSpdyConnectionCount() {
                    int total;
                    total = 0;
                    try {
                        for (Connection connection : this.connections) {
                   if (connection.isSpdy()) {
                                total++;
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return total;
                }

       public synchronized int getHttpConnectionCount() {
                    int total;
                    total = 0;
                    try {
                        for (Connection connection : this.connections) {
                   if (!connection.isSpdy()) {
                                total++;
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return total;
                }

       public synchronized Connection get(Address address) {
                    Connection foundConnection;
                    foundConnection = null;
                    try {
               ListIterator<Connection> i = this.connections.listIterator(this.connections.size());
               while (i.hasPrevious()) {
                   Connection connection = i.previous();
                   if (connection.getRoute().getAddress().equals(address) && connection.isAlive() && System.nanoTime() - connection.getIdleStartTimeNs() < this.keepAliveDurationNs) {
                       i.remove();
                       if (!connection.isSpdy()) {
                                    try {
                               Platform.get().tagSocket(connection.getSocket());
                                    } catch (SocketException e) {
                               Util.closeQuietly(connection.getSocket());
                               Platform.get().logW("Unable to tagSocket(): " + e);
                                    }
                                }
                       foundConnection = connection;
                                break;
                            }
                        }
               if (foundConnection != null && foundConnection.isSpdy()) {
                   this.connections.addFirst(foundConnection);
                        }
               this.executorService.execute(this.connectionsCleanupRunnable);
                    } catch (Throwable th) {
                        throw th;
                    }
                    return foundConnection;
                }

       void recycle(Connection connection) {
           if (!connection.isSpdy() && connection.clearOwner()) {
               if (!connection.isAlive()) {
                   Util.closeQuietly(connection.getSocket());
                   return;
                        }
                        try {
                   Platform.get().untagSocket(connection.getSocket());
                            synchronized (this) {
                                try {
                           this.connections.addFirst(connection);
                           connection.incrementRecycleCount();
                           connection.resetIdleStartTime();
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                   this.executorService.execute(this.connectionsCleanupRunnable);
                        } catch (SocketException e) {
                   Platform.get().logW("Unable to untagSocket(): " + e);
                   Util.closeQuietly(connection.getSocket());
                        }
                    }
                }

       void share(Connection connection) {
           if (!connection.isSpdy()) {
                        throw new IllegalArgumentException();
                    }
           this.executorService.execute(this.connectionsCleanupRunnable);
           if (connection.isAlive()) {
                        synchronized (this) {
                            try {
                       this.connections.addFirst(connection);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

       public void evictAll() {
                    List<Connection> connections;
                    synchronized (this) {
                        try {
                   connections = new ArrayList<>(this.connections);
                   this.connections.clear();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    int size = connections.size();
                    for (int i = 0; i < size; i++) {
               Util.closeQuietly(connections.get(i).getSocket());
                    }
                }
            }
