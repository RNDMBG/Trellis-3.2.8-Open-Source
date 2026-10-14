            package com.facebook;

            import android.os.Bundle;
            
            
    public class NonCachingTokenCachingStrategy extends TokenCachingStrategy {
                @Override 
        public Bundle load() {
            return null;
                }

                @Override 
        public void save(Bundle bundle) {
                }

                @Override 
        public void clear() {
                }
            }
