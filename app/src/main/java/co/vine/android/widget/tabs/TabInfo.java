            package co.vine.android.widget.tabs;

            import android.os.Bundle;
            import android.support.v4.app.Fragment;
            import java.lang.ref.WeakReference;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 15 */    public class TabInfo {
                public final Bundle bundle;
                public final Class<?> clss;
                private WeakReference<Fragment> mRef;
                protected String mTag;

/* 16 */        public TabInfo(Class<?> clss, Bundle bundle, String tag) {
/* 18 */            this.clss = clss;
/* 19 */            this.bundle = bundle;
/* 20 */            this.mTag = tag;
                }

/* 23 */        public void setFragment(Fragment f) {
/* 24 */            if (f != null) {
/* 25 */                this.mRef = new WeakReference<>(f);
                    } else {
/* 27 */                this.mRef = null;
                    }
                }

/* 31 */        public Fragment fragment() {
/* 32 */            if (this.mRef != null) {
/* 33 */                return this.mRef.get();
                    }
                    return null;
                }

/* 39 */        public String getTag() {
/* 40 */            return this.mTag;
                }
            }
