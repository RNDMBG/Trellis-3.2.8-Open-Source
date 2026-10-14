            package org.scribe.services;

            import java.util.Random;
            
            
    public class TimestampServiceImpl implements TimestampService {
                private Timer timer = new Timer();

                @Override 
        public String getNonce() {
            Long ts = getTs();
            return String.valueOf(ts.longValue() + ((long) this.timer.getRandomInteger().intValue()));
                }

                @Override 
        public String getTimestampInSeconds() {
            return String.valueOf(getTs());
                }

        private Long getTs() {
            return Long.valueOf(this.timer.getMilis().longValue() / 1000);
                }

        void setTimer(Timer timer) {
            this.timer = timer;
                }

        static class Timer {
                    private final Random rand = new Random();

            Timer() {
                    }

            Long getMilis() {
                return Long.valueOf(System.currentTimeMillis());
                    }

            Integer getRandomInteger() {
                return Integer.valueOf(this.rand.nextInt());
                    }
                }
            }
