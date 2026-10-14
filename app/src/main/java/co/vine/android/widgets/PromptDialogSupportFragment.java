            package co.vine.android.widgets;

            import android.annotation.TargetApi;
            import android.app.Activity;
            import android.app.AlertDialog;
            import android.app.Dialog;
            import android.content.ComponentCallbacks;
            import android.content.DialogInterface;
            import android.os.Build;
            import android.os.Bundle;
            import android.support.v4.app.DialogFragment;
            import android.support.v4.app.FragmentManager;
            
            
    public class PromptDialogSupportFragment extends DialogFragment implements DialogInterface.OnClickListener {
                private static final String ARG_DIALOG_ICON = "icon";
                private static final String ARG_DIALOG_ID = "id";
                private static final String ARG_DIALOG_ITEMS = "items";
                private static final String ARG_DIALOG_ITEMS_STRING = "items_string";
                private static final String ARG_DIALOG_MESSAGE = "message";
                private static final String ARG_DIALOG_MESSAGE_STRING = "message_string";
                private static final String ARG_DIALOG_NEGATIVE_BUTTON = "negative_button";
                private static final String ARG_DIALOG_NEUTRAL_BUTTON = "neutral_button";
                private static final String ARG_DIALOG_POSITIVE_BUTTON = "positive_button";
                private static final String ARG_DIALOG_THEME = "theme";
                private static final String ARG_DIALOG_TITLE = "title";
                private OnDialogDoneListener mListener;

                public interface OnDialogDoneListener extends PromptDialogFragment.OnDialogDoneListener {
                }

        public static PromptDialogSupportFragment newInstance(int id, int theme) {
            PromptDialogSupportFragment f = new PromptDialogSupportFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(ARG_DIALOG_ID, id);
            bundle.putInt(ARG_DIALOG_THEME, theme);
            f.setArguments(bundle);
            return f;
                }

        public static PromptDialogSupportFragment newInstance(int id) {
            return newInstance(id, 0);
                }

                
                @Override 
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            if (this.mListener == null) {
                ComponentCallbacks targetFragment = getTargetFragment();
                if (targetFragment instanceof OnDialogDoneListener) {
                    this.mListener = (OnDialogDoneListener) targetFragment;
                        } else if (activity instanceof OnDialogDoneListener) {
                    this.mListener = (OnDialogDoneListener) activity;
                        }
                    }
                }

                @Override 
                @TargetApi(11)
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            super.onCreateDialog(savedInstanceState);
            Bundle args = getArguments();
            int theme = args.getInt(ARG_DIALOG_THEME, 2);
            AlertDialog.Builder builder = Build.VERSION.SDK_INT >= 11 ? new AlertDialog.Builder(getActivity(), theme) : new AlertDialog.Builder(getActivity());
            if (args.containsKey("icon")) {
                builder.setIcon(args.getInt("icon"));
                    }
            if (args.containsKey("title")) {
                builder.setTitle(args.getInt("title"));
                    }
            if (args.containsKey("message")) {
                builder.setMessage(args.getInt("message"));
                    }
            if (args.containsKey(ARG_DIALOG_MESSAGE_STRING)) {
                builder.setMessage(args.getString(ARG_DIALOG_MESSAGE_STRING));
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

       public PromptDialogSupportFragment setIcon(int icon) {
           getArguments().putInt("icon", icon);
           return this;
                }

       public PromptDialogSupportFragment setTitle(int title) {
           getArguments().putInt("title", title);
           return this;
                }

       public PromptDialogSupportFragment setMessage(int message) {
           getArguments().putInt("message", message);
           return this;
                }

       public PromptDialogSupportFragment setMessage(String message) {
           getArguments().putString(ARG_DIALOG_MESSAGE_STRING, message);
           return this;
                }

       public PromptDialogSupportFragment setItems(int items) {
           getArguments().putInt(ARG_DIALOG_ITEMS, items);
           return this;
                }

       public PromptDialogSupportFragment setItems(String[] items) {
           getArguments().putStringArray(ARG_DIALOG_ITEMS_STRING, items);
           return this;
                }

       public PromptDialogSupportFragment setPositiveButton(int positiveButton) {
           getArguments().putInt(ARG_DIALOG_POSITIVE_BUTTON, positiveButton);
           return this;
                }

       public PromptDialogSupportFragment setNeutralButton(int neutralButton) {
           getArguments().putInt(ARG_DIALOG_NEUTRAL_BUTTON, neutralButton);
           return this;
                }

       public PromptDialogSupportFragment setNegativeButton(int negativeButton) {
           getArguments().putInt(ARG_DIALOG_NEGATIVE_BUTTON, negativeButton);
           return this;
                }

       public void show(FragmentManager fragmentManager) {
           show(fragmentManager, (String) null);
                }
            }
