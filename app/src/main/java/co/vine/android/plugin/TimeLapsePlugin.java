            package co.vine.android.plugin;

            import android.app.AlertDialog;
            import android.content.DialogInterface;
            import android.os.Handler;
            import android.support.v4.app.Fragment;
            import android.support.v4.app.FragmentTransaction;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.EditText;
            import android.widget.LinearLayout;
            import co.vine.android.recorder.VineRecorder;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    public class TimeLapsePlugin extends BaseRecorderPlugin<View, VineRecorder> {
                private double mTimeLapseDelay;
                private final Runnable mTimeLapseRunnable;
                private boolean mTimelapsing;

/* 18 */        static /* synthetic */ double access$000(TimeLapsePlugin x0) {
/* 19 */            return x0.mTimeLapseDelay;
                }

/* 24 */        public TimeLapsePlugin() {
/* 25 */            super("TimeLapse");
/* 22 */            this.mTimelapsing = false;
/* 28 */            this.mTimeLapseRunnable = new RunnableC04631();
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.TimeLapsePlugin$1 */
/* 26 */        class RunnableC04631 implements Runnable {
/* 27 */            RunnableC04631() {
                    }

                    @Override // java.lang.Runnable
/* 29 */            public void run() {
/* 30 */                Handler handler = TimeLapsePlugin.this.getHandler();
/* 31 */                VineRecorder recorder = TimeLapsePlugin.this.getRecorder();
/* 32 */                if (handler != null && recorder != null && recorder.isWithinDurationPercentageOf(0.95d)) {
/* 33 */                    recorder.doOneFrame();
/* 34 */                    handler.postDelayed(this, (long) TimeLapsePlugin.access$000(TimeLapsePlugin.this));
                        }
                    }
                }

/* 39 */        public void startTimeLapse(double sec) {
/* 40 */            this.mTimeLapseDelay = 1000.0d * sec;
/* 41 */            this.mTimelapsing = true;
/* 42 */            this.mTimeLapseRunnable.run();
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 46 */        public View onLayout(ViewGroup parent, LayoutInflater inflater, Fragment fragment) {
/* 47 */            return null;
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 51 */        public boolean onSetEditMode(boolean editing, boolean hasData) {
/* 52 */            Handler handler = getHandler();
/* 53 */            if (this.mTimelapsing && handler != null) {
/* 54 */                this.mTimelapsing = false;
/* 55 */                handler.removeCallbacks(this.mTimeLapseRunnable);
/* 56 */                return true;
                    }
/* 58 */            if (hasData) {
                        return false;
                    }
/* 59 */            showTimeLapseDialog();
                    return false;
                }

/* 64 */        public void showTimeLapseDialog() {
/* 65 */            AlertDialog.Builder builder = new AlertDialog.Builder(this.mActivity);
/* 66 */            builder.setTitle("Time Lapse Mode");
/* 67 */            LinearLayout ll = new LinearLayout(this.mActivity);
/* 68 */            EditText et = new EditText(this.mActivity);
/* 69 */            et.setInputType(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
/* 70 */            et.setHint("time in seconds, decimals OK.");
/* 71 */            ll.addView(et);
/* 72 */            builder.setView(ll);
/* 73 */            builder.setPositiveButton("Start", new DialogInterfaceOnClickListenerC04642(et));
/* 83 */            builder.setNegativeButton("Cancel", new DialogInterfaceOnClickListenerC04653());
/* 89 */            builder.show();
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.TimeLapsePlugin$2 */
/* 71 */        class DialogInterfaceOnClickListenerC04642 implements DialogInterface.OnClickListener {
                    final /* synthetic */ EditText val$et;

/* 72 */            DialogInterfaceOnClickListenerC04642(EditText editText) {
                        this.val$et = editText;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
/* 75 */            public void onClick(DialogInterface dialog, int which) {
/* 76 */                VineRecorder recorder = TimeLapsePlugin.this.getRecorder();
/* 77 */                if (recorder != null && recorder.setAudioTrim(true)) {
/* 78 */                    TimeLapsePlugin.this.startTimeLapse(Double.parseDouble(this.val$et.getText().toString()));
                        }
/* 80 */                dialog.dismiss();
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.TimeLapsePlugin$3 */
/* 81 */        class DialogInterfaceOnClickListenerC04653 implements DialogInterface.OnClickListener {
/* 82 */            DialogInterfaceOnClickListenerC04653() {
                    }

                    @Override // android.content.DialogInterface.OnClickListener
/* 85 */            public void onClick(DialogInterface dialog, int which) {
/* 86 */                dialog.dismiss();
                    }
                }
            }
