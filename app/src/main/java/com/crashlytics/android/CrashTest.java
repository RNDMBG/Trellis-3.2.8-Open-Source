            package com.crashlytics.android;

            import com.crashlytics.android.internal.C0727v;
            
            
    public class CrashTest {
        public void throwRuntimeException(String message) {
            throw new RuntimeException(message);
                }

        public void stackOverflow() {
            stackOverflow();
                }

        public void indexOutOfBounds() {
            C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Out of bounds value: " + new int[2][10]);
                }

        public void crashAsyncTask(long delayMs) {
            new AsyncTaskC0732j(this, delayMs).execute(null);
                }

        public void throwFiveChainedExceptions() {
                    try {
                throw new RuntimeException("1");
                    } catch (Exception e) {
                        try {
                    throw new RuntimeException("2", e);
                        } catch (Exception e2) {
                            try {
                        throw new RuntimeException("3", e2);
                            } catch (Exception e3) {
                                try {
                            throw new RuntimeException("4", e3);
                                } catch (Exception e4) {
                            throw new RuntimeException("5", e4);
                                }
                            }
                        }
                    }
                }
            }
