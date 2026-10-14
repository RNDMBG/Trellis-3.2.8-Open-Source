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
            
            
    public class TimeLapsePlugin extends BaseRecorderPlugin<View, VineRecorder> {
                private double mTimeLapseDelay;
                private final Runnable mTimeLapseRunnable;
                private boolean mTimelapsing;

        static  double access$000(TimeLapsePlugin x0) {
            return x0.mTimeLapseDelay;
                }

        public TimeLapsePlugin() {
            super("TimeLapse");
            this.mTimelapsing = false;
            this.mTimeLapseRunnable = new RunnableC04631();
                }

                
        class RunnableC04631 implements Runnable {
            RunnableC04631() {
                    }

                    @Override 
            public void run() {
                Handler handler = TimeLapsePlugin.this.getHandler();
                VineRecorder recorder = TimeLapsePlugin.this.getRecorder();
                if (handler != null && recorder != null && recorder.isWithinDurationPercentageOf(0.95d)) {
                    recorder.doOneFrame();
                    handler.postDelayed(this, (long) TimeLapsePlugin.access$000(TimeLapsePlugin.this));
                        }
                    }
                }

        public void startTimeLapse(double sec) {
            this.mTimeLapseDelay = 1000.0d * sec;
            this.mTimelapsing = true;
            this.mTimeLapseRunnable.run();
                }

                @Override 
        public View onLayout(ViewGroup parent, LayoutInflater inflater, Fragment fragment) {
            return null;
                }

                @Override 
        public boolean onSetEditMode(boolean editing, boolean hasData) {
            Handler handler = getHandler();
            if (this.mTimelapsing && handler != null) {
                this.mTimelapsing = false;
                handler.removeCallbacks(this.mTimeLapseRunnable);
                return true;
                    }
            if (hasData) {
                        return false;
                    }
            showTimeLapseDialog();
                    return false;
                }

        public void showTimeLapseDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mActivity);
            builder.setTitle("Time Lapse Mode");
            LinearLayout ll = new LinearLayout(this.mActivity);
            EditText et = new EditText(this.mActivity);
            et.setInputType(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            et.setHint("time in seconds, decimals OK.");
            ll.addView(et);
            builder.setView(ll);
            builder.setPositiveButton("Start", new DialogInterfaceOnClickListenerC04642(et));
            builder.setNegativeButton("Cancel", new DialogInterfaceOnClickListenerC04653());
            builder.show();
                }

                
        class DialogInterfaceOnClickListenerC04642 implements DialogInterface.OnClickListener {
                    final  EditText val$et;

            DialogInterfaceOnClickListenerC04642(EditText editText) {
                        this.val$et = editText;
                    }

                    @Override 
            public void onClick(DialogInterface dialog, int which) {
                VineRecorder recorder = TimeLapsePlugin.this.getRecorder();
                if (recorder != null && recorder.setAudioTrim(true)) {
                    TimeLapsePlugin.this.startTimeLapse(Double.parseDouble(this.val$et.getText().toString()));
                        }
                dialog.dismiss();
                    }
                }

                
        class DialogInterfaceOnClickListenerC04653 implements DialogInterface.OnClickListener {
            DialogInterfaceOnClickListenerC04653() {
                    }

                    @Override 
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                    }
                }
            }
