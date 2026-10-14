            package com.squareup.okhttp;

            import com.squareup.okhttp.internal.Util;
            import com.squareup.okhttp.internal.http.HttpEngine;
            import java.util.ArrayDeque;
            import java.util.Deque;
            import java.util.Iterator;
            import java.util.concurrent.ExecutorService;
            import java.util.concurrent.LinkedBlockingQueue;
            import java.util.concurrent.ThreadPoolExecutor;
            import java.util.concurrent.TimeUnit;
            
            
    public final class Dispatcher {
                private ExecutorService executorService;
                private int maxRequests = 64;
                private int maxRequestsPerHost = 5;
                private final Deque<Call.AsyncCall> readyCalls = new ArrayDeque();
                private final Deque<Call.AsyncCall> runningCalls = new ArrayDeque();
                private final Deque<Call> executedCalls = new ArrayDeque();

        public Dispatcher(ExecutorService executorService) {
            this.executorService = executorService;
                }

        public Dispatcher() {
                }

        public synchronized ExecutorService getExecutorService() {
                    try {
                        if (this.executorService == null) {
                    this.executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp Dispatcher", false));
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
            return this.executorService;
                }

        public synchronized void setMaxRequests(int maxRequests) {
                    try {
                        if (maxRequests < 1) {
                    throw new IllegalArgumentException("max < 1: " + maxRequests);
                        }
                this.maxRequests = maxRequests;
                promoteCalls();
                    } catch (Throwable th) {
                        throw th;
                    }
                }

        public synchronized int getMaxRequests() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.maxRequests;
                }

        public synchronized void setMaxRequestsPerHost(int maxRequestsPerHost) {
                    try {
                        if (maxRequestsPerHost < 1) {
                    throw new IllegalArgumentException("max < 1: " + maxRequestsPerHost);
                        }
                this.maxRequestsPerHost = maxRequestsPerHost;
               promoteCalls();
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       public synchronized int getMaxRequestsPerHost() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.maxRequestsPerHost;
                }

       synchronized void enqueue(Call.AsyncCall call) {
                    try {
                        if (this.runningCalls.size() < this.maxRequests && runningCallsForHost(call) < this.maxRequestsPerHost) {
                   this.runningCalls.add(call);
                   getExecutorService().execute(call);
                        } else {
                   this.readyCalls.add(call);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       public synchronized void cancel(Object tag) {
                    try {
                        for (Call.AsyncCall call : this.readyCalls) {
                   if (Util.equal(tag, call.tag())) {
                       call.cancel();
                            }
                        }
                        for (Call.AsyncCall call2 : this.runningCalls) {
                   if (Util.equal(tag, call2.tag())) {
                       call2.get().canceled = true;
                       HttpEngine engine = call2.get().engine;
                       if (engine != null) {
                                    engine.disconnect();
                                }
                            }
                        }
                        for (Call call3 : this.executedCalls) {
                   if (Util.equal(tag, call3.tag())) {
                       call3.cancel();
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void finished(Call.AsyncCall call) {
                    try {
                        if (!this.runningCalls.remove(call)) {
                            throw new AssertionError("AsyncCall wasn't running!");
                        }
               promoteCalls();
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       private void promoteCalls() {
           if (this.runningCalls.size() < this.maxRequests && !this.readyCalls.isEmpty()) {
               Iterator<Call.AsyncCall> i = this.readyCalls.iterator();
                        while (i.hasNext()) {
                   Call.AsyncCall call = i.next();
                   if (runningCallsForHost(call) < this.maxRequestsPerHost) {
                       i.remove();
                       this.runningCalls.add(call);
                       getExecutorService().execute(call);
                            }
                   if (this.runningCalls.size() >= this.maxRequests) {
                       return;
                            }
                        }
                    }
                }

       private int runningCallsForHost(Call.AsyncCall call) {
           int result = 0;
                    for (Call.AsyncCall c : this.runningCalls) {
               if (c.host().equals(call.host())) {
                            result++;
                        }
                    }
           return result;
                }

       synchronized void executed(Call call) {
                    try {
                        this.executedCalls.add(call);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void finished(Call call) {
                    try {
                        if (!this.executedCalls.remove(call)) {
                            throw new AssertionError("Call wasn't in-flight!");
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
