            package twitter4j;

            import java.io.Serializable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface TwitterResponse extends Serializable {
                public static final int NONE = 0;
                public static final int READ = 1;
                public static final int READ_WRITE = 2;
                public static final int READ_WRITE_DIRECTMESSAGES = 3;

                int getAccessLevel();
            }
