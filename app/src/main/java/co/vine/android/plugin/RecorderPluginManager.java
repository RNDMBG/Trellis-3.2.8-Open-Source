            package co.vine.android.plugin;

            import co.vine.android.recorder.BasicVineRecorder;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface RecorderPluginManager<T extends BasicVineRecorder> extends PluginManager<RecorderPlugin<?, T>> {
                T getRecorder();

                void onStartDrafts();
            }
