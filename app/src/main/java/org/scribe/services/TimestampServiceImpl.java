            package org.scribe.services;

            import java.util.Random;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    public class TimestampServiceImpl implements TimestampService {
                private Timer timer = new Timer();

                @Override // org.scribe.services.TimestampService
/* 26 */        public String getNonce() {
/* 27 */            Long ts = getTs();
/* 28 */            return String.valueOf(ts.longValue() + ((long) this.timer.getRandomInteger().intValue()));
                }

                @Override // org.scribe.services.TimestampService
/* 35 */        public String getTimestampInSeconds() {
/* 36 */            return String.valueOf(getTs());
                }

/* 40 */        private Long getTs() {
/* 41 */            return Long.valueOf(this.timer.getMilis().longValue() / 1000);
                }

/* 45 */        void setTimer(Timer timer) {
/* 46 */            this.timer = timer;
                }

/* 52 */        static class Timer {
                    private final Random rand = new Random();

/* 53 */            Timer() {
                    }

/* 58 */            Long getMilis() {
/* 59 */                return Long.valueOf(System.currentTimeMillis());
                    }

/* 63 */            Integer getRandomInteger() {
/* 64 */                return Integer.valueOf(this.rand.nextInt());
                    }
                }
            }
