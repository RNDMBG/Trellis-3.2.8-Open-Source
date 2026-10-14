            package co.vine.android.api;

            import co.vine.android.util.Util;
            import java.util.Comparator;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 12 */    public class VineComparatorFactory {

                public interface VineComparator<VinePost> extends Comparator<VinePost> {
                    long getOrderId(VinePost vinepost);
                }

/* 13 */        public static VineComparator<VinePost> get(int type) {
/* 14 */            if (Util.isPopularTimeline(type)) {
/* 15 */                return new VinePopularComparator(null);
                    }
                    switch (type) {
                        case 1:
/* 19 */                    return new HomeTimelineComparator(null);
                        case 2:
/* 22 */                    return new ProfileTimelineComparator(null);
                        default:
/* 25 */                    return new DefaultComparator(null);
                    }
                }

/* 33 */        private static class HomeTimelineComparator implements VineComparator<VinePost> {
/* 34 */            private HomeTimelineComparator() {
                    }

/* 34 */            /* synthetic */ HomeTimelineComparator(Object x0) {
/* 35 */                this();
                    }



/* 38 */            public int compare(VinePost vinePost, VinePost vinePost2) {
/* 39 */                return Long.valueOf(getOrderId(vinePost2)).compareTo(Long.valueOf(getOrderId(vinePost)));
                    }

                    /* JADX INFO: renamed from: getOrderId, reason: avoid collision after fix types in other method */
/* 52 */            public long getOrderId(VinePost post) {
/* 53 */                if (post.user != null && post.user.isFollowing()) {
/* 55 */                    return post.postId;
                        }
/* 58 */                if (post.repost != null) {
/* 59 */                    return post.repost.repostId;
                        }
/* 61 */                return post.postId;
                    }
                }

/* 63 */        private static class ProfileTimelineComparator implements VineComparator<VinePost> {
/* 64 */            private ProfileTimelineComparator() {
                    }

/* 64 */            /* synthetic */ ProfileTimelineComparator(Object x0) {
/* 65 */                this();
                    }



                    /* JADX INFO: renamed from: getOrderId, reason: avoid collision after fix types in other method */
/* 68 */            public long getOrderId(VinePost vinePost) {
                        return vinePost.repost != null ? vinePost.repost.repostId : vinePost.postId;
                    }

/* 78 */            public int compare(VinePost vp, VinePost vp2) {
/* 79 */                return Long.valueOf(getOrderId(vp2)).compareTo(Long.valueOf(getOrderId(vp)));
                    }
                }

/* 81 */        private static class VinePopularComparator implements VineComparator<VinePost> {
/* 82 */            private VinePopularComparator() {
                    }

/* 82 */            /* synthetic */ VinePopularComparator(Object x0) {
/* 83 */                this();
                    }



/* 86 */            public int compare(VinePost vinePost, VinePost vinePost2) {
/* 87 */                return Long.valueOf(getOrderId(vinePost)).compareTo(Long.valueOf(getOrderId(vinePost2)));
                    }

                    /* JADX INFO: renamed from: getOrderId, reason: avoid collision after fix types in other method */
/* 92 */            public long getOrderId(VinePost post) {
/* 93 */                return Long.valueOf(post.orderId).longValue();
                    }
                }

/* 95 */        private static class DefaultComparator implements VineComparator<VinePost> {
/* 96 */            private DefaultComparator() {
                    }

/* 96 */            /* synthetic */ DefaultComparator(Object x0) {
/* 97 */                this();
                    }



                    /* JADX INFO: renamed from: getOrderId, reason: avoid collision after fix types in other method */
/* 99 */            public long getOrderId(VinePost post) {
/* 100 */               return post.postId;
                    }

/* 105 */           public int compare(VinePost vp, VinePost vp2) {
/* 106 */               return Long.valueOf(getOrderId(vp2)).compareTo(Long.valueOf(getOrderId(vp)));
                    }
                }
            }
