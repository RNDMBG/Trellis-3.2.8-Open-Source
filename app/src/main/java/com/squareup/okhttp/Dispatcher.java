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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 50 */    public final class Dispatcher {
                private ExecutorService executorService;
                private int maxRequests = 64;
                private int maxRequestsPerHost = 5;
                private final Deque<Call.AsyncCall> readyCalls = new ArrayDeque();
                private final Deque<Call.AsyncCall> runningCalls = new ArrayDeque();
                private final Deque<Call> executedCalls = new ArrayDeque();

/* 51 */        public Dispatcher(ExecutorService executorService) {
/* 53 */            this.executorService = executorService;
                }

/* 55 */        public Dispatcher() {
                }

/* 59 */        public synchronized ExecutorService getExecutorService() {
                    try {
                        if (this.executorService == null) {
/* 61 */                    this.executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp Dispatcher", false));
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 64 */            return this.executorService;
                }

/* 74 */        public synchronized void setMaxRequests(int maxRequests) {
                    try {
                        if (maxRequests < 1) {
/* 76 */                    throw new IllegalArgumentException("max < 1: " + maxRequests);
                        }
/* 78 */                this.maxRequests = maxRequests;
/* 79 */                promoteCalls();
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 82 */        public synchronized int getMaxRequests() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.maxRequests;
                }

/* 95 */        public synchronized void setMaxRequestsPerHost(int maxRequestsPerHost) {
                    try {
                        if (maxRequestsPerHost < 1) {
/* 97 */                    throw new IllegalArgumentException("max < 1: " + maxRequestsPerHost);
                        }
/* 99 */                this.maxRequestsPerHost = maxRequestsPerHost;
/* 100 */               promoteCalls();
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 103 */       public synchronized int getMaxRequestsPerHost() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.maxRequestsPerHost;
                }

/* 107 */       synchronized void enqueue(Call.AsyncCall call) {
                    try {
                        if (this.runningCalls.size() < this.maxRequests && runningCallsForHost(call) < this.maxRequestsPerHost) {
/* 109 */                   this.runningCalls.add(call);
/* 110 */                   getExecutorService().execute(call);
                        } else {
/* 112 */                   this.readyCalls.add(call);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 117 */       public synchronized void cancel(Object tag) {
                    try {
                        for (Call.AsyncCall call : this.readyCalls) {
/* 119 */                   if (Util.equal(tag, call.tag())) {
/* 120 */                       call.cancel();
                            }
                        }
                        for (Call.AsyncCall call2 : this.runningCalls) {
/* 125 */                   if (Util.equal(tag, call2.tag())) {
/* 126 */                       call2.get().canceled = true;
/* 127 */                       HttpEngine engine = call2.get().engine;
/* 128 */                       if (engine != null) {
                                    engine.disconnect();
                                }
                            }
                        }
                        for (Call call3 : this.executedCalls) {
/* 133 */                   if (Util.equal(tag, call3.tag())) {
/* 134 */                       call3.cancel();
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 140 */       synchronized void finished(Call.AsyncCall call) {
                    try {
                        if (!this.runningCalls.remove(call)) {
                            throw new AssertionError("AsyncCall wasn't running!");
                        }
/* 142 */               promoteCalls();
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 145 */       private void promoteCalls() {
/* 146 */           if (this.runningCalls.size() < this.maxRequests && !this.readyCalls.isEmpty()) {
/* 149 */               Iterator<Call.AsyncCall> i = this.readyCalls.iterator();
                        while (i.hasNext()) {
/* 150 */                   Call.AsyncCall call = i.next();
/* 152 */                   if (runningCallsForHost(call) < this.maxRequestsPerHost) {
/* 153 */                       i.remove();
/* 154 */                       this.runningCalls.add(call);
/* 155 */                       getExecutorService().execute(call);
                            }
/* 158 */                   if (this.runningCalls.size() >= this.maxRequests) {
/* 160 */                       return;
                            }
                        }
                    }
                }

/* 163 */       private int runningCallsForHost(Call.AsyncCall call) {
/* 164 */           int result = 0;
                    for (Call.AsyncCall c : this.runningCalls) {
/* 166 */               if (c.host().equals(call.host())) {
                            result++;
                        }
                    }
/* 168 */           return result;
                }

/* 172 */       synchronized void executed(Call call) {
                    try {
                        this.executedCalls.add(call);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 177 */       synchronized void finished(Call call) {
                    try {
                        if (!this.executedCalls.remove(call)) {
                            throw new AssertionError("Call wasn't in-flight!");
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
