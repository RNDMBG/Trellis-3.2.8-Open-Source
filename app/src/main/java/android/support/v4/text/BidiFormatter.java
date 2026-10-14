            package android.support.v4.text;

            import co.vine.android.util.CommonUtil;
            import java.util.Locale;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 76 */    public final class BidiFormatter {
                private static final int DEFAULT_FLAGS = 2;
                private static final int DIR_LTR = -1;
                private static final int DIR_RTL = 1;
                private static final int DIR_UNKNOWN = 0;
                private static final String EMPTY_STRING = "";
                private static final int FLAG_STEREO_RESET = 2;
                private static final char LRE = 8234;
                private static final char PDF = 8236;
                private static final char RLE = 8235;
                private final TextDirectionHeuristicCompat mDefaultTextDirectionHeuristicCompat;
                private final int mFlags;
                private final boolean mIsRtlContext;
                private static TextDirectionHeuristicCompat DEFAULT_TEXT_DIRECTION_HEURISTIC = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
                private static final char LRM = 8206;
                private static final String LRM_STRING = Character.toString(LRM);
                private static final char RLM = 8207;
                private static final String RLM_STRING = Character.toString(RLM);
                private static final BidiFormatter DEFAULT_LTR_INSTANCE = new BidiFormatter(false, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC);
                private static final BidiFormatter DEFAULT_RTL_INSTANCE = new BidiFormatter(true, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC);

/* 77 */        /* synthetic */ BidiFormatter(boolean x0, int x1, TextDirectionHeuristicCompat x2, C00361 x3) {
/* 78 */            this(x0, x1, x2);
                }

/* 77 */        static /* synthetic */ boolean access$000(Locale x0) {
/* 78 */            return isRtlLocale(x0);
                }

/* 77 */        static /* synthetic */ TextDirectionHeuristicCompat access$100() {
/* 78 */            return DEFAULT_TEXT_DIRECTION_HEURISTIC;
                }

/* 77 */        static /* synthetic */ BidiFormatter access$200() {
/* 78 */            return DEFAULT_RTL_INSTANCE;
                }

/* 77 */        static /* synthetic */ BidiFormatter access$300() {
/* 78 */            return DEFAULT_LTR_INSTANCE;
                }

/* 135 */       public static final class Builder {
                    private int mFlags;
                    private boolean mIsRtlContext;
                    private TextDirectionHeuristicCompat mTextDirectionHeuristicCompat;

/* 136 */           public Builder() {
/* 138 */               initialize(BidiFormatter.access$000(Locale.getDefault()));
                    }

/* 145 */           public Builder(boolean rtlContext) {
/* 147 */               initialize(rtlContext);
                    }

/* 154 */           public Builder(Locale locale) {
/* 156 */               initialize(BidiFormatter.access$000(locale));
                    }

/* 164 */           private void initialize(boolean isRtlContext) {
/* 165 */               this.mIsRtlContext = isRtlContext;
/* 166 */               this.mTextDirectionHeuristicCompat = BidiFormatter.access$100();
/* 167 */               this.mFlags = 2;
                    }

/* 174 */           public Builder stereoReset(boolean stereoReset) {
/* 175 */               if (stereoReset) {
/* 176 */                   this.mFlags |= 2;
                        } else {
/* 178 */                   this.mFlags &= -3;
                        }
/* 180 */               return this;
                    }

/* 190 */           public Builder setTextDirectionHeuristic(TextDirectionHeuristicCompat heuristic) {
/* 191 */               this.mTextDirectionHeuristicCompat = heuristic;
/* 192 */               return this;
                    }

/* 195 */           private static BidiFormatter getDefaultInstanceFromContext(boolean isRtlContext) {
                        return isRtlContext ? BidiFormatter.access$200() : BidiFormatter.access$300();
                    }

/* 202 */           public BidiFormatter build() {
                        return (this.mFlags == 2 && this.mTextDirectionHeuristicCompat == BidiFormatter.access$100()) ? getDefaultInstanceFromContext(this.mIsRtlContext) : new BidiFormatter(this.mIsRtlContext, this.mFlags, this.mTextDirectionHeuristicCompat, null);
                    }
                }

/* 233 */       public static BidiFormatter getInstance() {
/* 234 */           return new Builder().build();
                }

/* 242 */       public static BidiFormatter getInstance(boolean rtlContext) {
/* 243 */           return new Builder(rtlContext).build();
                }

/* 251 */       public static BidiFormatter getInstance(Locale locale) {
/* 252 */           return new Builder(locale).build();
                }

/* 259 */       private BidiFormatter(boolean isRtlContext, int flags, TextDirectionHeuristicCompat heuristic) {
/* 261 */           this.mIsRtlContext = isRtlContext;
/* 262 */           this.mFlags = flags;
/* 263 */           this.mDefaultTextDirectionHeuristicCompat = heuristic;
                }

/* 269 */       public boolean isRtlContext() {
/* 270 */           return this.mIsRtlContext;
                }

/* 277 */       public boolean getStereoReset() {
                    return (this.mFlags & 2) != 0;
                }

/* 296 */       private String markAfter(String str, TextDirectionHeuristicCompat heuristic) {
/* 297 */           boolean isRtl = heuristic.isRtl(str, 0, str.length());
/* 299 */           if (!this.mIsRtlContext && (isRtl || getExitDir(str) == 1)) {
/* 300 */               return LRM_STRING;
                    }
/* 302 */           if (this.mIsRtlContext && (!isRtl || getExitDir(str) == -1)) {
/* 303 */               return RLM_STRING;
                    }
/* 305 */           return "";
                }

/* 323 */       private String markBefore(String str, TextDirectionHeuristicCompat heuristic) {
/* 324 */           boolean isRtl = heuristic.isRtl(str, 0, str.length());
/* 326 */           if (!this.mIsRtlContext && (isRtl || getEntryDir(str) == 1)) {
/* 327 */               return LRM_STRING;
                    }
/* 329 */           if (this.mIsRtlContext && (!isRtl || getEntryDir(str) == -1)) {
/* 330 */               return RLM_STRING;
                    }
/* 332 */           return "";
                }

/* 342 */       public boolean isRtl(String str) {
/* 343 */           return this.mDefaultTextDirectionHeuristicCompat.isRtl(str, 0, str.length());
                }

/* 373 */       public String unicodeWrap(String str, TextDirectionHeuristicCompat heuristic, boolean isolate) {
/* 374 */           boolean isRtl = heuristic.isRtl(str, 0, str.length());
/* 375 */           StringBuilder result = new StringBuilder();
/* 376 */           if (getStereoReset() && isolate) {
                        result.append(markBefore(str, isRtl ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR));
                    }
/* 380 */           if (isRtl != this.mIsRtlContext) {
/* 381 */               result.append(isRtl ? RLE : LRE);
/* 382 */               result.append(str);
/* 383 */               result.append(PDF);
                    } else {
/* 385 */               result.append(str);
                    }
/* 387 */           if (isolate) {
                        result.append(markAfter(str, isRtl ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR));
                    }
/* 391 */           return result.toString();
                }

/* 402 */       public String unicodeWrap(String str, TextDirectionHeuristicCompat heuristic) {
/* 403 */           return unicodeWrap(str, heuristic, true);
                }

/* 415 */       public String unicodeWrap(String str, boolean isolate) {
/* 416 */           return unicodeWrap(str, this.mDefaultTextDirectionHeuristicCompat, isolate);
                }

/* 426 */       public String unicodeWrap(String str) {
/* 427 */           return unicodeWrap(str, this.mDefaultTextDirectionHeuristicCompat, true);
                }

/* 436 */       private static boolean isRtlLocale(Locale locale) {
                    return TextUtilsCompat.getLayoutDirectionFromLocale(locale) == 1;
                }

/* 461 */       private static int getExitDir(String str) {
/* 462 */           return new DirectionalityEstimator(str, false).getExitDir();
                }

/* 478 */       private static int getEntryDir(String str) {
/* 479 */           return new DirectionalityEstimator(str, false).getEntryDir();
                }

/* 503 */       private static class DirectionalityEstimator {
                    private int charIndex;
                    private final boolean isHtml;
                    private char lastChar;
                    private final int length;
                    private final String text;
                    private static final int DIR_TYPE_CACHE_SIZE = 1792;
                    private static final byte[] DIR_TYPE_CACHE = new byte[DIR_TYPE_CACHE_SIZE];

                    static {
                        for (int i = 0; i < DIR_TYPE_CACHE_SIZE; i++) {
/* 507 */                   DIR_TYPE_CACHE[i] = Character.getDirectionality(i);
                        }
                    }

/* 548 */           DirectionalityEstimator(String text, boolean isHtml) {
/* 550 */               this.text = text;
/* 551 */               this.isHtml = isHtml;
/* 552 */               this.length = text.length();
                    }

/* 566 */           int getEntryDir() {
/* 567 */               this.charIndex = 0;
/* 568 */               int embeddingLevel = 0;
/* 569 */               int embeddingLevelDir = 0;
/* 570 */               int firstNonEmptyEmbeddingLevel = 0;
/* 571 */               while (this.charIndex < this.length && firstNonEmptyEmbeddingLevel == 0) {
                            switch (dirTypeForward()) {
                                case 0:
/* 593 */                           if (embeddingLevel == 0) {
                                        return -1;
                                    }
/* 596 */                           firstNonEmptyEmbeddingLevel = embeddingLevel;
                                    break;
                                case 1:
                                case 2:
/* 600 */                           if (embeddingLevel == 0) {
                                        return 1;
                                    }
/* 603 */                           firstNonEmptyEmbeddingLevel = embeddingLevel;
                                    break;
                                case 3:
                                case 4:
                                case 5:
                                case 6:
                                case 7:
                                case 8:
                                case 10:
                                case 11:
                                case 12:
                                case 13:
                                default:
/* 606 */                           firstNonEmptyEmbeddingLevel = embeddingLevel;
                                    break;
                                case 9:
                                    break;
                                case 14:
                                case 15:
/* 575 */                           embeddingLevel++;
/* 576 */                           embeddingLevelDir = -1;
                                    break;
                                case 16:
                                case 17:
/* 580 */                           embeddingLevel++;
/* 581 */                           embeddingLevelDir = 1;
                                    break;
                                case 18:
/* 584 */                           embeddingLevel--;
/* 588 */                           embeddingLevelDir = 0;
                                    break;
                            }
                        }
/* 613 */               if (firstNonEmptyEmbeddingLevel == 0) {
                            return 0;
                        }
/* 620 */               if (embeddingLevelDir == 0) {
/* 627 */                   while (this.charIndex > 0) {
                                switch (dirTypeBackward()) {
                                    case 14:
                                    case 15:
/* 631 */                               if (firstNonEmptyEmbeddingLevel == embeddingLevel) {
                                            return -1;
                                        }
/* 634 */                               embeddingLevel--;
                                        break;
                                    case 16:
                                    case 17:
/* 638 */                               if (firstNonEmptyEmbeddingLevel == embeddingLevel) {
                                            return 1;
                                        }
/* 641 */                               embeddingLevel--;
                                        break;
                                    case 18:
/* 644 */                               embeddingLevel++;
                                        break;
                                }
                            }
                            return 0;
                        }
/* 649 */               return embeddingLevelDir;
                    }

/* 664 */           int getExitDir() {
/* 665 */               this.charIndex = this.length;
/* 666 */               int embeddingLevel = 0;
/* 667 */               int lastNonEmptyEmbeddingLevel = 0;
/* 668 */               while (this.charIndex > 0) {
                            switch (dirTypeBackward()) {
                                case 0:
/* 671 */                           if (embeddingLevel == 0) {
                                        return -1;
                                    }
/* 674 */                           if (lastNonEmptyEmbeddingLevel == 0) {
/* 675 */                               lastNonEmptyEmbeddingLevel = embeddingLevel;
                                    }
                                    break;
                                case 1:
                                case 2:
/* 687 */                           if (embeddingLevel == 0) {
                                        return 1;
                                    }
/* 690 */                           if (lastNonEmptyEmbeddingLevel == 0) {
/* 691 */                               lastNonEmptyEmbeddingLevel = embeddingLevel;
                                    }
                                    break;
                                case 3:
                                case 4:
                                case 5:
                                case 6:
                                case 7:
                                case 8:
                                case 10:
                                case 11:
                                case 12:
                                case 13:
                                default:
/* 707 */                           if (lastNonEmptyEmbeddingLevel == 0) {
/* 708 */                               lastNonEmptyEmbeddingLevel = embeddingLevel;
                                    }
                                    break;
                                case 9:
                                    break;
                                case 14:
                                case 15:
/* 680 */                           if (lastNonEmptyEmbeddingLevel == embeddingLevel) {
                                        return -1;
                                    }
/* 683 */                           embeddingLevel--;
                                    break;
                                    break;
                                case 16:
                                case 17:
/* 696 */                           if (lastNonEmptyEmbeddingLevel == embeddingLevel) {
                                        return 1;
                                    }
/* 699 */                           embeddingLevel--;
                                    break;
                                case 18:
/* 702 */                           embeddingLevel++;
                                    break;
                            }
                        }
/* 713 */               return 0;
                    }

/* 723 */           private static byte getCachedDirectionality(char c) {
                        return c < DIR_TYPE_CACHE_SIZE ? DIR_TYPE_CACHE[c] : Character.getDirectionality(c);
                    }

/* 736 */           byte dirTypeForward() {
/* 737 */               this.lastChar = this.text.charAt(this.charIndex);
/* 738 */               if (Character.isHighSurrogate(this.lastChar)) {
/* 739 */                   int codePoint = Character.codePointAt(this.text, this.charIndex);
/* 740 */                   this.charIndex += Character.charCount(codePoint);
/* 741 */                   return Character.getDirectionality(codePoint);
                        }
/* 743 */               this.charIndex++;
/* 744 */               byte cachedDirectionality = getCachedDirectionality(this.lastChar);
/* 745 */               if (this.isHtml) {
/* 747 */                   if (this.lastChar == '<') {
/* 748 */                       byte dirType = skipTagForward();
/* 753 */                       return dirType;
                            }
/* 749 */                   if (this.lastChar == '&') {
/* 750 */                       byte dirType2 = skipEntityForward();
/* 753 */                       return dirType2;
                            }
/* 753 */                   return cachedDirectionality;
                        }
/* 753 */               return cachedDirectionality;
                    }

/* 766 */           byte dirTypeBackward() {
/* 767 */               this.lastChar = this.text.charAt(this.charIndex - 1);
/* 768 */               if (Character.isLowSurrogate(this.lastChar)) {
/* 769 */                   int codePoint = Character.codePointBefore(this.text, this.charIndex);
/* 770 */                   this.charIndex -= Character.charCount(codePoint);
/* 771 */                   return Character.getDirectionality(codePoint);
                        }
/* 773 */               this.charIndex--;
/* 774 */               byte cachedDirectionality = getCachedDirectionality(this.lastChar);
/* 775 */               if (this.isHtml) {
/* 777 */                   if (this.lastChar == '>') {
/* 778 */                       byte dirType = skipTagBackward();
/* 783 */                       return dirType;
                            }
/* 779 */                   if (this.lastChar == ';') {
/* 780 */                       byte dirType2 = skipEntityBackward();
/* 783 */                       return dirType2;
                            }
/* 783 */                   return cachedDirectionality;
                        }
/* 783 */               return cachedDirectionality;
                    }

/* 792 */           private byte skipTagForward() {
/* 793 */               int initialCharIndex = this.charIndex;
/* 794 */               while (this.charIndex < this.length) {
/* 795 */                   String str = this.text;
                            int i = this.charIndex;
                            this.charIndex = i + 1;
                            this.lastChar = str.charAt(i);
/* 796 */                   if (this.lastChar == '>') {
/* 798 */                       return (byte) 12;
                            }
/* 800 */                   if (this.lastChar == '\"' || this.lastChar == '\'') {
/* 802 */                       char quote = this.lastChar;
/* 803 */                       while (this.charIndex < this.length) {
                                    String str2 = this.text;
                                    int i2 = this.charIndex;
                                    this.charIndex = i2 + 1;
                                    char cCharAt = str2.charAt(i2);
                                    this.lastChar = cCharAt;
                                    if (cCharAt == quote) {
                                        break;
                                    }
                                }
                            }
                        }
/* 807 */               this.charIndex = initialCharIndex;
/* 808 */               this.lastChar = '<';
/* 809 */               return (byte) 13;
                    }

/* 821 */           private byte skipTagBackward() {
/* 822 */               int initialCharIndex = this.charIndex;
/* 823 */               while (this.charIndex > 0) {
/* 824 */                   String str = this.text;
                            int i = this.charIndex - 1;
                            this.charIndex = i;
                            this.lastChar = str.charAt(i);
/* 825 */                   if (this.lastChar == '<') {
/* 827 */                       return (byte) 12;
                            }
/* 829 */                   if (this.lastChar == '>') {
                                break;
                            }
/* 832 */                   if (this.lastChar == '\"' || this.lastChar == '\'') {
/* 834 */                       char quote = this.lastChar;
/* 835 */                       while (this.charIndex > 0) {
                                    String str2 = this.text;
                                    int i2 = this.charIndex - 1;
                                    this.charIndex = i2;
                                    char cCharAt = str2.charAt(i2);
                                    this.lastChar = cCharAt;
                                    if (cCharAt == quote) {
                                        break;
                                    }
                                }
                            }
                        }
/* 839 */               this.charIndex = initialCharIndex;
/* 840 */               this.lastChar = '>';
/* 841 */               return (byte) 13;
                    }

/* 849 */           private byte skipEntityForward() {
/* 850 */               while (this.charIndex < this.length) {
                            String str = this.text;
                            int i = this.charIndex;
                            this.charIndex = i + 1;
                            char cCharAt = str.charAt(i);
                            this.lastChar = cCharAt;
                            if (cCharAt == ';') {
/* 851 */                       return (byte) 12;
                            }
                        }
/* 851 */               return (byte) 12;
                    }

/* 864 */           private byte skipEntityBackward() {
/* 865 */               int initialCharIndex = this.charIndex;
/* 866 */               while (this.charIndex > 0) {
/* 867 */                   String str = this.text;
                            int i = this.charIndex - 1;
                            this.charIndex = i;
                            this.lastChar = str.charAt(i);
/* 868 */                   if (this.lastChar == '&') {
/* 869 */                       return (byte) 12;
                            }
/* 871 */                   if (this.lastChar == ';') {
                                break;
                            }
                        }
/* 875 */               this.charIndex = initialCharIndex;
/* 876 */               this.lastChar = CommonUtil.MARKER_SEMI_COLON;
/* 877 */               return (byte) 13;
                    }
                }
            }
