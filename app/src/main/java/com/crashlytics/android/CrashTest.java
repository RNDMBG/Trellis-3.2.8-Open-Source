            package com.crashlytics.android;

            import com.crashlytics.android.internal.C0727v;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 19 */    public class CrashTest {
/* 20 */        public void throwRuntimeException(String message) {
/* 21 */            throw new RuntimeException(message);
                }

/* 24 */        public void stackOverflow() {
/* 25 */            stackOverflow();
                }

/* 28 */        public void indexOutOfBounds() {
/* 34 */            C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Out of bounds value: " + new int[2][10]);
                }

/* 41 */        public void crashAsyncTask(long delayMs) {
/* 54 */            new AsyncTaskC0732j(this, delayMs).execute(null);
                }

/* 61 */        public void throwFiveChainedExceptions() {
                    try {
/* 62 */                throw new RuntimeException("1");
                    } catch (Exception e) {
                        try {
/* 64 */                    throw new RuntimeException("2", e);
                        } catch (Exception e2) {
                            try {
/* 67 */                        throw new RuntimeException("3", e2);
                            } catch (Exception e3) {
                                try {
/* 70 */                            throw new RuntimeException("4", e3);
                                } catch (Exception e4) {
/* 73 */                            throw new RuntimeException("5", e4);
                                }
                            }
                        }
                    }
                }
            }
