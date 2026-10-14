            package com.squareup.okhttp.internal;

            import com.squareup.okhttp.Route;
            import java.util.LinkedHashSet;
            import java.util.Set;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public final class RouteDatabase {
                private final Set<Route> failedRoutes = new LinkedHashSet();

/* 33 */        public synchronized void failed(Route failedRoute) {
                    try {
                        this.failedRoutes.add(failedRoute);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 38 */        public synchronized void connected(Route route) {
                    try {
                        this.failedRoutes.remove(route);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 43 */        public synchronized boolean shouldPostpone(Route route) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.failedRoutes.contains(route);
                }

/* 47 */        public synchronized int failedRoutesCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.failedRoutes.size();
                }
            }
