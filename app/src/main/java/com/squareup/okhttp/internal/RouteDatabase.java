            package com.squareup.okhttp.internal;

            import com.squareup.okhttp.Route;
            import java.util.LinkedHashSet;
            import java.util.Set;
            
            
    public final class RouteDatabase {
                private final Set<Route> failedRoutes = new LinkedHashSet();

        public synchronized void failed(Route failedRoute) {
                    try {
                        this.failedRoutes.add(failedRoute);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

        public synchronized void connected(Route route) {
                    try {
                        this.failedRoutes.remove(route);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

        public synchronized boolean shouldPostpone(Route route) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.failedRoutes.contains(route);
                }

        public synchronized int failedRoutesCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.failedRoutes.size();
                }
            }
