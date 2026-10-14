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
            
            
    public abstract class RecorderPluginSubManager<T extends View, K extends BasicVineRecorder> extends BaseRecorderPluginManager<K> implements RecorderPlugin<T, K> {
                private WeakReference<Fragment> mFragment;
                public int mId;
                private RecorderPluginManager<K> mParent;
                private WeakReference<T> mView;
                private final String name;
                private final ArrayList<RecorderPlugin<?, K>> plugins;

                public abstract T onLayout(ViewGroup viewGroup, LayoutInflater layoutInflater, Fragment fragment);

        public RecorderPluginSubManager(String name, ArrayList<RecorderPlugin<?, K>> plugins) {
            this.name = name;
            this.plugins = plugins;
                }

                @Override 
        public K getRecorder() {
            return (K) this.mParent.getRecorder();
                }

                @Override 
        public int onAdded(PluginManager manager, int id) {
            super.addPlugins(id, this.plugins);
            this.mId = this.mNextPluginId;
            this.mParent = (RecorderPluginManager) manager;
            return (this.mId - id) + 1;
                }

                @Override 
        public String getName() {
            return this.name;
                }

                @Override 
        public int getId() {
            return this.mId;
                }

                @Override 
        public void createLayout(ViewGroup parent, LayoutInflater inflater, Fragment fragment) {
            this.mFragment = new WeakReference<>(fragment);
            T viewOnLayout = onLayout(parent, inflater, fragment);
            if (viewOnLayout != null) {
                parent.addView(viewOnLayout);
                this.mView = new WeakReference<>(viewOnLayout);
                    } else {
                this.mView = null;
                    }
                }

                @Override 
                @Nullable
        public Fragment getFragment() {
            if (this.mFragment != null) {
                        return this.mFragment.get();
                    }
                    return null;
                }

                @Override 
                @Nullable
        public T getView() {
            if (this.mView != null) {
                        return this.mView.get();
                    }
                    return null;
                }

                @Override 
        public void onRecorderSet(BasicVineRecorder recorder) {
                }

                @Override 
        public boolean onBackButtonPressed(boolean isEditing) {
            Iterator<RecorderPlugin<?, K>> it = this.plugins.iterator();
                    while (it.hasNext()) {
                if (it.next().onBackButtonPressed(isEditing)) {
                    return true;
                        }
                    }
                    return false;
                }
            }
