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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public class ComposeTokenizer implements MultiAutoCompleteTextView.Tokenizer {
                public static final String TAGS_ADAPTER = "tags_adapter";
                public static final String USERS_ADAPTER = "users_adapter";
                private static final HashSet<Character> mDelimiters;
                private PopupEditText.PopupEditTextListener mCallback;

                static {
/* 26 */            HashSet<Character> delimiters = new HashSet<>(32);
/* 27 */            delimiters.add('\t');
/* 28 */            delimiters.add('\n');
/* 29 */            delimiters.add(Character.valueOf(FilenameUtils.EXTENSION_SEPARATOR));
/* 30 */            delimiters.add('!');
/* 31 */            delimiters.add('?');
/* 32 */            delimiters.add(',');
/* 33 */            delimiters.add(Character.valueOf(CommonUtil.MARKER_COLON));
/* 34 */            delimiters.add(Character.valueOf(CommonUtil.MARKER_SEMI_COLON));
/* 35 */            delimiters.add('\'');
/* 36 */            delimiters.add(Character.valueOf(CommonUtil.MARKER_QUOTES));
/* 37 */            delimiters.add('`');
/* 38 */            delimiters.add('/');
/* 39 */            delimiters.add(Character.valueOf(IOUtils.DIR_SEPARATOR_WINDOWS));
/* 40 */            delimiters.add('-');
/* 41 */            delimiters.add('=');
/* 42 */            delimiters.add('+');
/* 43 */            delimiters.add('~');
/* 44 */            delimiters.add('$');
/* 45 */            delimiters.add('%');
/* 46 */            delimiters.add('^');
/* 47 */            delimiters.add('&');
/* 48 */            delimiters.add('*');
/* 49 */            delimiters.add('(');
/* 50 */            delimiters.add(')');
/* 51 */            delimiters.add('[');
/* 52 */            delimiters.add(']');
/* 53 */            delimiters.add('{');
/* 54 */            delimiters.add('}');
/* 55 */            delimiters.add('<');
/* 56 */            delimiters.add('>');
/* 57 */            delimiters.add('|');
/* 58 */            mDelimiters = delimiters;
                }

/* 60 */        public ComposeTokenizer(PopupEditText.PopupEditTextListener callback) {
/* 62 */            this.mCallback = callback;
                }

                @Override // android.widget.MultiAutoCompleteTextView.Tokenizer
/* 66 */        public int findTokenStart(CharSequence text, int cursor) {
                    char current;
/* 67 */            if (cursor > 0) {
/* 71 */                int i = cursor;
                        do {
/* 74 */                    i--;
/* 75 */                    current = text.charAt(i);
/* 76 */                    if (i <= 0 || mDelimiters.contains(Character.valueOf(current)) || current == '@') {
                                break;
                            }
                        } while (current != '#');
/* 78 */                boolean sawAt = current == '@';
/* 79 */                boolean sawHashtag = current == '#';
/* 80 */                if (sawAt || sawHashtag) {
/* 81 */                    if (i == 0 || mDelimiters.contains(Character.valueOf(text.charAt(i - 1))) || text.charAt(i - 1) == ' ') {
/* 82 */                        if (sawAt) {
/* 83 */                            this.mCallback.setPopupAdapter(USERS_ADAPTER);
                                } else if (sawHashtag) {
/* 85 */                            this.mCallback.setPopupAdapter(TAGS_ADAPTER);
                                }
                                return i;
                            }
/* 92 */                    return cursor;
                        }
/* 92 */                return cursor;
                    }
/* 92 */            return cursor;
                }

                @Override // android.widget.MultiAutoCompleteTextView.Tokenizer
/* 97 */        public int findTokenEnd(CharSequence text, int cursor) {
/* 99 */            int len = text.length();
/* 100 */           for (int i = cursor; i < len; i++) {
/* 101 */               if (mDelimiters.contains(Character.valueOf(text.charAt(i)))) {
/* 108 */                   return i;
                        }
                    }
                    return len;
                }

                @Override // android.widget.MultiAutoCompleteTextView.Tokenizer
/* 112 */       public CharSequence terminateToken(CharSequence text) {
/* 113 */           if (!(text instanceof Spanned)) {
                        return ((Object) text) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
                    }
/* 114 */           SpannableString sp = new SpannableString(((Object) text) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
/* 115 */           TextUtils.copySpansFrom((Spanned) text, 0, text.length(), Object.class, sp, 0);
/* 118 */           return sp;
                }
            }
