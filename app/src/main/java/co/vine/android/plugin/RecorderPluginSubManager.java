            package co.vine.android.plugin;

            import android.support.annotation.Nullable;
            import android.support.v4.app.Fragment;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import co.vine.android.recorder.BasicVineRecorder;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            import java.util.Iterator;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public abstract class RecorderPluginSubManager<T extends View, K extends BasicVineRecorder> extends BaseRecorderPluginManager<K> implements RecorderPlugin<T, K> {
                private WeakReference<Fragment> mFragment;
                public int mId;
                private RecorderPluginManager<K> mParent;
                private WeakReference<T> mView;
                private final String name;
                private final ArrayList<RecorderPlugin<?, K>> plugins;

                public abstract T onLayout(ViewGroup viewGroup, LayoutInflater layoutInflater, Fragment fragment);

/* 25 */        public RecorderPluginSubManager(String name, ArrayList<RecorderPlugin<?, K>> plugins) {
/* 27 */            this.name = name;
/* 28 */            this.plugins = plugins;
                }

                @Override // co.vine.android.plugin.BaseRecorderPluginManager, co.vine.android.plugin.RecorderPluginManager
/* 31 */        public K getRecorder() {
/* 32 */            return (K) this.mParent.getRecorder();
                }

                @Override // co.vine.android.plugin.Plugin
/* 36 */        public int onAdded(PluginManager manager, int id) {
/* 37 */            super.addPlugins(id, this.plugins);
/* 38 */            this.mId = this.mNextPluginId;
/* 39 */            this.mParent = (RecorderPluginManager) manager;
/* 40 */            return (this.mId - id) + 1;
                }

                @Override // co.vine.android.plugin.Plugin
/* 44 */        public String getName() {
/* 45 */            return this.name;
                }

                @Override // co.vine.android.plugin.Plugin
/* 49 */        public int getId() {
/* 50 */            return this.mId;
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 54 */        public void createLayout(ViewGroup parent, LayoutInflater inflater, Fragment fragment) {
/* 55 */            this.mFragment = new WeakReference<>(fragment);
/* 56 */            T viewOnLayout = onLayout(parent, inflater, fragment);
/* 57 */            if (viewOnLayout != null) {
/* 58 */                parent.addView(viewOnLayout);
/* 59 */                this.mView = new WeakReference<>(viewOnLayout);
                    } else {
/* 61 */                this.mView = null;
                    }
                }

                @Override // co.vine.android.plugin.RecorderPlugin
                @Nullable
/* 67 */        public Fragment getFragment() {
/* 68 */            if (this.mFragment != null) {
                        return this.mFragment.get();
                    }
                    return null;
                }

                @Override // co.vine.android.plugin.RecorderPlugin
                @Nullable
/* 73 */        public T getView() {
/* 74 */            if (this.mView != null) {
                        return this.mView.get();
                    }
                    return null;
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 82 */        public void onRecorderSet(BasicVineRecorder recorder) {
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 86 */        public boolean onBackButtonPressed(boolean isEditing) {
/* 87 */            Iterator<RecorderPlugin<?, K>> it = this.plugins.iterator();
                    while (it.hasNext()) {
/* 88 */                if (it.next().onBackButtonPressed(isEditing)) {
/* 89 */                    return true;
                        }
                    }
                    return false;
                }
            }
