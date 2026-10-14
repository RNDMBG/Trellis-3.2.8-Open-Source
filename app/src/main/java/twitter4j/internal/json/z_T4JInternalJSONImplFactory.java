            package twitter4j.internal.json;

            import twitter4j.conf.Configuration;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    public class z_T4JInternalJSONImplFactory implements z_T4JInternalFactory {
                private static final long serialVersionUID = 5217622295050444866L;
                private Configuration conf;

/* 29 */        public z_T4JInternalJSONImplFactory(Configuration conf) {
/* 31 */            this.conf = conf;
                }

/* 307 */       public boolean equals(Object o) {
/* 308 */           if (this == o) {
                        return true;
                    }
/* 309 */           if (!(o instanceof z_T4JInternalJSONImplFactory)) {
                        return false;
                    }
/* 311 */           z_T4JInternalJSONImplFactory that = (z_T4JInternalJSONImplFactory) o;
/* 313 */           if (this.conf != null) {
                        if (this.conf.equals(that.conf)) {
                            return true;
                        }
                    } else if (that.conf == null) {
                        return true;
                    }
                    return false;
                }

/* 320 */       public int hashCode() {
/* 321 */           if (this.conf != null) {
                        return this.conf.hashCode();
                    }
                    return 0;
                }

/* 325 */       public String toString() {
/* 326 */           return "JSONImplFactory{conf=" + this.conf + '}';
                }
            }
