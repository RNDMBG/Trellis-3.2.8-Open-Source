            package co.vine.android;

            import android.text.SpannableString;
            import android.text.Spanned;
            import android.text.TextUtils;
            import android.widget.MultiAutoCompleteTextView;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.widget.PopupEditText;
            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import java.util.HashSet;
            import org.apache.commons.io.FilenameUtils;
            import org.apache.commons.io.IOUtils;
            
            
    public class ComposeTokenizer implements MultiAutoCompleteTextView.Tokenizer {
                public static final String TAGS_ADAPTER = "tags_adapter";
                public static final String USERS_ADAPTER = "users_adapter";
                private static final HashSet<Character> mDelimiters;
                private PopupEditText.PopupEditTextListener mCallback;

                static {
            HashSet<Character> delimiters = new HashSet<>(32);
            delimiters.add('\t');
            delimiters.add('\n');
            delimiters.add(Character.valueOf(FilenameUtils.EXTENSION_SEPARATOR));
            delimiters.add('!');
            delimiters.add('?');
            delimiters.add(',');
            delimiters.add(Character.valueOf(CommonUtil.MARKER_COLON));
            delimiters.add(Character.valueOf(CommonUtil.MARKER_SEMI_COLON));
            delimiters.add('\'');
            delimiters.add(Character.valueOf(CommonUtil.MARKER_QUOTES));
            delimiters.add('`');
            delimiters.add('/');
            delimiters.add(Character.valueOf(IOUtils.DIR_SEPARATOR_WINDOWS));
            delimiters.add('-');
            delimiters.add('=');
            delimiters.add('+');
            delimiters.add('~');
            delimiters.add('$');
            delimiters.add('%');
            delimiters.add('^');
            delimiters.add('&');
            delimiters.add('*');
            delimiters.add('(');
            delimiters.add(')');
            delimiters.add('[');
            delimiters.add(']');
            delimiters.add('{');
            delimiters.add('}');
            delimiters.add('<');
            delimiters.add('>');
            delimiters.add('|');
            mDelimiters = delimiters;
                }

        public ComposeTokenizer(PopupEditText.PopupEditTextListener callback) {
            this.mCallback = callback;
                }

                @Override 
        public int findTokenStart(CharSequence text, int cursor) {
                    char current;
            if (cursor > 0) {
                int i = cursor;
                        do {
                    i--;
                    current = text.charAt(i);
                    if (i <= 0 || mDelimiters.contains(Character.valueOf(current)) || current == '@') {
                                break;
                            }
                        } while (current != '#');
                boolean sawAt = current == '@';
                boolean sawHashtag = current == '#';
                if (sawAt || sawHashtag) {
                    if (i == 0 || mDelimiters.contains(Character.valueOf(text.charAt(i - 1))) || text.charAt(i - 1) == ' ') {
                        if (sawAt) {
                            this.mCallback.setPopupAdapter(USERS_ADAPTER);
                                } else if (sawHashtag) {
                            this.mCallback.setPopupAdapter(TAGS_ADAPTER);
                                }
                                return i;
                            }
                    return cursor;
                        }
                return cursor;
                    }
            return cursor;
                }

                @Override 
        public int findTokenEnd(CharSequence text, int cursor) {
            int len = text.length();
           for (int i = cursor; i < len; i++) {
               if (mDelimiters.contains(Character.valueOf(text.charAt(i)))) {
                   return i;
                        }
                    }
                    return len;
                }

                @Override 
       public CharSequence terminateToken(CharSequence text) {
           if (!(text instanceof Spanned)) {
                        return ((Object) text) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
                    }
           SpannableString sp = new SpannableString(((Object) text) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
           TextUtils.copySpansFrom((Spanned) text, 0, text.length(), Object.class, sp, 0);
           return sp;
                }
            }
