            package com.squareup.okhttp.internal;
            
            
    public abstract class NamedRunnable implements Runnable {
                private final String name;

                protected abstract void execute();

        public NamedRunnable(String format, Object... args) {
            this.name = String.format(format, args);
                }

                @Override 
        public final void run() {
            String oldName = Thread.currentThread().getName();
            Thread.currentThread().setName(this.name);
                    try {
                execute();
                Thread.currentThread().setName(oldName);
                    } catch (Throwable th) {
                        Thread.currentThread().setName(oldName);
                        throw th;
                    }
                }
            }
