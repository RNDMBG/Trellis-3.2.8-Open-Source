            package com.squareup.okhttp.internal;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public abstract class NamedRunnable implements Runnable {
                private final String name;

                protected abstract void execute();

/* 24 */        public NamedRunnable(String format, Object... args) {
/* 26 */            this.name = String.format(format, args);
                }

                @Override // java.lang.Runnable
/* 29 */        public final void run() {
/* 30 */            String oldName = Thread.currentThread().getName();
/* 31 */            Thread.currentThread().setName(this.name);
                    try {
/* 33 */                execute();
/* 35 */                Thread.currentThread().setName(oldName);
                    } catch (Throwable th) {
                        Thread.currentThread().setName(oldName);
                        throw th;
                    }
                }
            }
