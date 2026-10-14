            package co.vine.android.widgets;

            import android.annotation.TargetApi;
            import android.app.Activity;
            import android.app.AlertDialog;
            import android.app.Dialog;
            import android.app.DialogFragment;
            import android.app.FragmentManager;
            import android.content.ComponentCallbacks2;
            import android.content.DialogInterface;
            import android.os.Bundle;
            
            
            @TargetApi(11)
    public class PromptDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {
                private static final String ARG_DIALOG_ICON = "icon";
                private static final String ARG_DIALOG_ID = "id";
                private static final String ARG_DIALOG_ITEMS = "items";
                private static final String ARG_DIALOG_ITEMS_STRING = "items_string";
                private static final String ARG_DIALOG_MESSAGE = "message";
                private static final String ARG_DIALOG_NEGATIVE_BUTTON = "negative_button";
                private static final String ARG_DIALOG_NEUTRAL_BUTTON = "neutral_button";
                private static final String ARG_DIALOG_POSITIVE_BUTTON = "positive_button";
                private static final String ARG_DIALOG_TITLE = "title";
                public static final int DIALOG_CANCELLED = Integer.MIN_VALUE;
                private OnDialogDoneListener mListener;

                public interface OnDialogDoneListener {
                    void onDialogDone(DialogInterface dialogInterface, int i, int i2);
                }

        public static PromptDialogFragment newInstance(int id) {
            PromptDialogFragment f = new PromptDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(ARG_DIALOG_ID, id);
            f.setArguments(bundle);
            return f;
                }

                
                @Override 
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            if (this.mListener == null) {
                ComponentCallbacks2 targetFragment = getTargetFragment();
                if (targetFragment instanceof OnDialogDoneListener) {
                    this.mListener = (OnDialogDoneListener) targetFragment;
                        } else if (activity instanceof OnDialogDoneListener) {
                    this.mListener = (OnDialogDoneListener) activity;
                        }
                    }
                }

                @Override 
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            super.onCreateDialog(savedInstanceState);
            Bundle args = getArguments();
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), 2);
            if (args.containsKey("icon")) {
                builder.setIcon(args.getInt("icon"));
                    }
            if (args.containsKey("title")) {
                builder.setTitle(args.getInt("title"));
                    }
            if (args.containsKey("message")) {
                builder.setMessage(args.getInt("message"));
                    }
            if (args.containsKey(ARG_DIALOG_POSITIVE_BUTTON)) {
                builder.setPositiveButton(args.getInt(ARG_DIALOG_POSITIVE_BUTTON), this);
                    }
            if (args.containsKey(ARG_DIALOG_NEUTRAL_BUTTON)) {
                builder.setNeutralButton(args.getInt(ARG_DIALOG_NEUTRAL_BUTTON), this);
                    }
            if (args.containsKey(ARG_DIALOG_NEGATIVE_BUTTON)) {
                builder.setNegativeButton(args.getInt(ARG_DIALOG_NEGATIVE_BUTTON), this);
                    }
           if (args.containsKey(ARG_DIALOG_ITEMS)) {
               builder.setItems(getResources().getTextArray(args.getInt(ARG_DIALOG_ITEMS)), this);
                    }
           if (args.containsKey(ARG_DIALOG_ITEMS_STRING)) {
               builder.setItems(args.getStringArray(ARG_DIALOG_ITEMS_STRING), this);
                    }
           return builder.create();
                }

                @Override 
       public void onCancel(DialogInterface dialog) {
           super.onCancel(dialog);
           if (this.mListener != null) {
               this.mListener.onDialogDone(dialog, getArguments().getInt(ARG_DIALOG_ID), Integer.MIN_VALUE);
                    }
                }

                @Override 
       public void onClick(DialogInterface dialog, int which) {
           if (this.mListener != null) {
               this.mListener.onDialogDone(dialog, getArguments().getInt(ARG_DIALOG_ID), which);
                    }
                }

       public void setListener(OnDialogDoneListener listener) {
           this.mListener = listener;
                }

       public PromptDialogFragment setIcon(int icon) {
           getArguments().putInt("icon", icon);
           return this;
                }

       public PromptDialogFragment setTitle(int title) {
           getArguments().putInt("title", title);
           return this;
                }

       public PromptDialogFragment setMessage(int message) {
           getArguments().putInt("message", message);
           return this;
                }

       public PromptDialogFragment setItems(int items) {
           getArguments().putInt(ARG_DIALOG_ITEMS, items);
           return this;
                }

       public PromptDialogFragment setItems(String[] items) {
           getArguments().putStringArray(ARG_DIALOG_ITEMS_STRING, items);
           return this;
                }

       public PromptDialogFragment setPositiveButton(int positiveButton) {
           getArguments().putInt(ARG_DIALOG_POSITIVE_BUTTON, positiveButton);
           return this;
                }

       public PromptDialogFragment setNeutralButton(int neutralButton) {
           getArguments().putInt(ARG_DIALOG_NEUTRAL_BUTTON, neutralButton);
           return this;
                }

       public PromptDialogFragment setNegativeButton(int negativeButton) {
           getArguments().putInt(ARG_DIALOG_NEGATIVE_BUTTON, negativeButton);
           return this;
                }

       public void show(FragmentManager fragmentManager) {
           show(fragmentManager, (String) null);
                }
            }
