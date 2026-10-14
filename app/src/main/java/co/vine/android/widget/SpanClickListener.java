            package co.vine.android.widget;

            import android.view.View;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface SpanClickListener {
                public static final int COMMENT_LIST_SPAN = 5;
                public static final int COMMENT_SPAN = 2;
                public static final int TAG_SPAN = 3;
                public static final int USERNAME_SPAN = 1;
                public static final int USER_LIST_SPAN = 4;

                void onSpanClicked(View view, int i, Object obj);
            }
