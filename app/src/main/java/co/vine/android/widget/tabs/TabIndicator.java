            package co.vine.android.widget.tabs;

            import android.R;
            import android.content.Context;
            import android.content.res.ColorStateList;
            import android.graphics.ColorFilter;
            import android.graphics.PorterDuff;
            import android.util.AttributeSet;
            import android.view.LayoutInflater;
            import android.view.ViewGroup;
            import android.widget.ImageView;
            import android.widget.RelativeLayout;
            import android.widget.TabHost;
            import android.widget.TextView;
            import co.vine.android.C0257R;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public class TabIndicator extends RelativeLayout {
                private ImageView mIcon;
                private int mIconNewResId;
                private int mIconResId;
                private boolean mNew;
                private ImageView mNewIndicator;
                private TextView mTabText;

/* 28 */        public TabIndicator(Context context) {
/* 29 */            super(context);
                }

/* 32 */        public static ColorStateList createTextColorList(int on, int off) {
/* 33 */            return new ColorStateList(new int[][]{new int[]{R.attr.state_selected}, new int[]{R.attr.state_pressed}, new int[0]}, new int[]{on, on, off});
                }

/* 43 */        public TabIndicator(Context context, AttributeSet attrs) {
/* 44 */            super(context, attrs);
                }

/* 47 */        public TabIndicator(Context context, AttributeSet attrs, int defStyle) {
/* 48 */            super(context, attrs, defStyle);
                }

/* 52 */        public static TabIndicator newTextIndicator(LayoutInflater inflater, int layout, TabHost tabHost, int tabTextId, boolean caps) {
/* 53 */            TabIndicator indicator = (TabIndicator) inflater.inflate(layout, (ViewGroup) tabHost.getTabWidget(), false);
/* 55 */            if (tabTextId > 0) {
/* 56 */                indicator.setTabText(tabTextId, caps);
                    }
/* 58 */            return indicator;
                }

/* 62 */        public static TabIndicator newIconIndicator(LayoutInflater inflater, int layout, TabHost tabHost, int iconResId, int newResId) {
/* 63 */            TabIndicator indicator = (TabIndicator) inflater.inflate(layout, (ViewGroup) tabHost.getTabWidget(), false);
/* 65 */            if (iconResId > 0) {
/* 66 */                indicator.setTabIcon(iconResId, newResId);
                    }
/* 68 */            return indicator;
                }

                @Override // android.view.View
/* 73 */        protected void onFinishInflate() {
/* 74 */            super.onFinishInflate();
/* 75 */            this.mTabText = (TextView) findViewById(C0257R.id.tab_text);
/* 76 */            this.mNewIndicator = (ImageView) findViewById(C0257R.id.new_indicator);
/* 77 */            this.mIcon = (ImageView) findViewById(C0257R.id.icon);
                }

/* 80 */        public ImageView getNewIndicator() {
/* 81 */            return this.mNewIndicator;
                }

/* 84 */        public void setNew(boolean isNew) {
/* 85 */            if (isNew != this.mNew) {
/* 88 */                if (this.mNewIndicator != null) {
/* 89 */                    if (isNew) {
/* 90 */                        this.mNewIndicator.setVisibility(0);
                            } else {
/* 92 */                        this.mNewIndicator.setVisibility(8);
                            }
                        } else if (this.mIconNewResId > 0) {
/* 95 */                    if (isNew) {
/* 96 */                        this.mIcon.setImageResource(this.mIconNewResId);
                            } else {
/* 98 */                        this.mIcon.setImageResource(this.mIconResId);
                            }
                        }
/* 101 */               this.mNew = isNew;
                    }
                }

/* 104 */       public void setColor(int color) {
/* 105 */           if (this.mIcon != null) {
/* 106 */               this.mIcon.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
/* 107 */               this.mIcon.invalidate();
                    }
                }

/* 109 */       public void unSetColor() {
/* 110 */           if (this.mIcon != null) {
/* 111 */               this.mIcon.setColorFilter((ColorFilter) null);
/* 112 */               this.mIcon.invalidate();
                    }
                }

/* 114 */       public void setTabText(int resId, boolean caps) {
/* 115 */           if (this.mTabText != null) {
/* 116 */               String text = getResources().getString(resId);
/* 117 */               if (caps) {
/* 118 */                   text = text.toUpperCase();
                        }
/* 120 */               if (resId > 0) {
/* 121 */                   this.mTabText.setVisibility(0);
/* 122 */                   this.mTabText.setText(text);
                        } else {
/* 124 */                   this.mTabText.setVisibility(8);
                        }
                    }
                }

/* 129 */       public void setTabIcon(int resId, int newResId) {
/* 130 */           if (this.mIcon != null) {
/* 131 */               if (resId > 0) {
/* 132 */                   this.mIcon.setImageResource(resId);
/* 133 */                   this.mIcon.setVisibility(0);
                        } else {
/* 135 */                   this.mIcon.setVisibility(8);
                        }
                    }
/* 138 */           this.mIconResId = resId;
/* 139 */           this.mIconNewResId = newResId;
                }

                @Override // android.view.ViewGroup, android.view.View
/* 143 */       protected void drawableStateChanged() {
/* 144 */           super.drawableStateChanged();
/* 145 */           if (this.mTabText != null) {
                    }
                }

/* 152 */       public TextView getIndicatorText() {
/* 153 */           return this.mTabText;
                }
            }
