            package com.facebook.buck.android.support.exopackage;

            import android.annotation.TargetApi;
            import android.app.Application;
            import android.content.Context;
            import android.content.res.Configuration;
            import com.facebook.buck.android.support.exopackage.ApplicationLike;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 40 */    public abstract class ExopackageApplication<T extends ApplicationLike> extends Application {
                private T delegate;
                private final String delegateClassName;
                private final boolean isExopackage;

/* 41 */        protected ExopackageApplication(boolean isExopackage) {
/* 42 */            this(DefaultApplicationLike.class.getName(), isExopackage);
                }

/* 50 */        protected ExopackageApplication(String delegateClassName, boolean isExopackage) {
/* 52 */            this.delegateClassName = delegateClassName;
/* 53 */            this.isExopackage = isExopackage;
                }

/* 56 */        private T createDelegate() {
/* 57 */            if (this.isExopackage) {
/* 58 */                ExopackageDexLoader.loadExopackageJars(this);
                    }
                    try {
                        return (T) Class.forName(this.delegateClassName).getConstructor(Application.class).newInstance(this);
                    } catch (Exception e) {
/* 67 */                throw new RuntimeException(e);
                    }
                }

/* 71 */        private synchronized void ensureDelegate() {
                    try {
                        if (this.delegate == null) {
/* 73 */                    this.delegate = (T) createDelegate();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 82 */        protected void onBaseContextAttached() {
                }

/* 89 */        public final T getDelegateIfPresent() {
/* 90 */            return this.delegate;
                }

                @Override // android.content.ContextWrapper
/* 94 */        protected final void attachBaseContext(Context base) {
/* 95 */            super.attachBaseContext(base);
/* 96 */            onBaseContextAttached();
/* 97 */            ensureDelegate();
                }

                @Override // android.app.Application
/* 101 */       public final void onCreate() {
/* 102 */           super.onCreate();
/* 103 */           ensureDelegate();
/* 104 */           this.delegate.onCreate();
                }

                @Override // android.app.Application
/* 108 */       public final void onTerminate() {
/* 109 */           super.onTerminate();
/* 110 */           if (this.delegate != null) {
/* 111 */               this.delegate.onTerminate();
                    }
                }

                @Override // android.app.Application, android.content.ComponentCallbacks
/* 116 */       public final void onLowMemory() {
/* 117 */           super.onLowMemory();
/* 118 */           if (this.delegate != null) {
/* 119 */               this.delegate.onLowMemory();
                    }
                }

                @Override // android.app.Application, android.content.ComponentCallbacks2
                @TargetApi(14)
/* 125 */       public final void onTrimMemory(int level) {
/* 126 */           super.onTrimMemory(level);
/* 127 */           if (this.delegate != null) {
/* 128 */               this.delegate.onTrimMemory(level);
                    }
                }

                @Override // android.app.Application, android.content.ComponentCallbacks
/* 133 */       public void onConfigurationChanged(Configuration newConfig) {
/* 134 */           super.onConfigurationChanged(newConfig);
/* 135 */           if (this.delegate != null) {
/* 136 */               this.delegate.onConfigurationChanged(newConfig);
                    }
                }
            }
