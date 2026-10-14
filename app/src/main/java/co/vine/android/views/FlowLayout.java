            package co.vine.android.views;

            import android.content.Context;
            import android.graphics.Canvas;
            import android.graphics.Paint;
            import android.util.AttributeSet;
            import android.view.View;
            import android.view.ViewGroup;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 7 */     public class FlowLayout extends ViewGroup {
                public static final int HORIZONTAL = 0;
                public static final int VERTICAL = 1;
                private boolean debugDraw;
                private int horizontalSpacing;
                private int orientation;
                private int verticalSpacing;




/* 17 */        public FlowLayout(Context context) {
/* 18 */            super(context);
/* 12 */            this.horizontalSpacing = 0;
/* 13 */            this.verticalSpacing = 0;
/* 14 */            this.orientation = 0;
/* 15 */            this.debugDraw = false;
/* 20 */            readStyleParameters(context, null);
                }

/* 23 */        public FlowLayout(Context context, AttributeSet attributeSet) {
/* 24 */            super(context, attributeSet);
/* 12 */            this.horizontalSpacing = 0;
/* 13 */            this.verticalSpacing = 0;
/* 14 */            this.orientation = 0;
/* 15 */            this.debugDraw = false;
/* 26 */            readStyleParameters(context, attributeSet);
                }

/* 29 */        public FlowLayout(Context context, AttributeSet attributeSet, int defStyle) {
/* 30 */            super(context, attributeSet, defStyle);
/* 12 */            this.horizontalSpacing = 0;
/* 13 */            this.verticalSpacing = 0;
/* 14 */            this.orientation = 0;
/* 15 */            this.debugDraw = false;
/* 32 */            readStyleParameters(context, attributeSet);
                }

                @Override // android.view.View
/* 36 */        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                    int size;
                    int mode;
                    int controlMaxLength;
                    int controlMaxThickness;
                    int childLength;
                    int childThickness;
                    int spacingLength;
                    int spacingThickness;
                    int posX;
                    int posY;
/* 37 */            int sizeWidth = (View.MeasureSpec.getSize(widthMeasureSpec) - getPaddingRight()) - getPaddingLeft();
/* 38 */            int sizeHeight = (View.MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop()) - getPaddingBottom();
/* 40 */            int modeWidth = View.MeasureSpec.getMode(widthMeasureSpec);
/* 41 */            int modeHeight = View.MeasureSpec.getMode(heightMeasureSpec);
/* 46 */            if (this.orientation == 0) {
/* 47 */                size = sizeWidth;
/* 48 */                mode = modeWidth;
                    } else {
/* 50 */                size = sizeHeight;
/* 51 */                mode = modeHeight;
                    }
/* 54 */            int lineThicknessWithSpacing = 0;
/* 55 */            int lineThickness = 0;
/* 56 */            int lineLengthWithSpacing = 0;
/* 59 */            int prevLinePosition = 0;
/* 61 */            int controlMaxLength2 = 0;
/* 62 */            int controlMaxThickness2 = 0;
/* 64 */            int count = getChildCount();
                    for (int i = 0; i < count; i++) {
/* 66 */                View child = getChildAt(i);
/* 67 */                if (child.getVisibility() != 8) {
/* 71 */                    LayoutParams lp = (LayoutParams) child.getLayoutParams();
/* 73 */                    child.measure(getChildMeasureSpec(widthMeasureSpec, getPaddingLeft() + getPaddingRight(), lp.width), getChildMeasureSpec(heightMeasureSpec, getPaddingTop() + getPaddingBottom(), lp.height));
/* 78 */                    int hSpacing = getHorizontalSpacing(lp);
/* 79 */                    int vSpacing = getVerticalSpacing(lp);
/* 81 */                    int childWidth = child.getMeasuredWidth();
/* 82 */                    int childHeight = child.getMeasuredHeight();
/* 89 */                    if (this.orientation == 0) {
/* 90 */                        childLength = childWidth;
/* 91 */                        childThickness = childHeight;
/* 92 */                        spacingLength = hSpacing;
/* 93 */                        spacingThickness = vSpacing;
                            } else {
/* 95 */                        childLength = childHeight;
/* 96 */                        childThickness = childWidth;
/* 97 */                        spacingLength = vSpacing;
/* 98 */                        spacingThickness = hSpacing;
                            }
/* 101 */                   int lineLength = lineLengthWithSpacing + childLength;
/* 102 */                   lineLengthWithSpacing = lineLength + spacingLength;
/* 104 */                   boolean newLine = LayoutParams.access$000(lp) || (mode != 0 && lineLength > size);
/* 105 */                   if (newLine) {
/* 106 */                       prevLinePosition += lineThicknessWithSpacing;
/* 108 */                       lineThickness = childThickness;
/* 109 */                       lineLength = childLength;
/* 110 */                       lineThicknessWithSpacing = childThickness + spacingThickness;
/* 111 */                       lineLengthWithSpacing = lineLength + spacingLength;
                            }
/* 114 */                   lineThicknessWithSpacing = Math.max(lineThicknessWithSpacing, childThickness + spacingThickness);
/* 115 */                   lineThickness = Math.max(lineThickness, childThickness);
/* 119 */                   if (this.orientation == 0) {
/* 120 */                       posX = (getPaddingLeft() + lineLength) - childLength;
/* 121 */                       posY = getPaddingTop() + prevLinePosition;
                            } else {
/* 123 */                       posX = getPaddingLeft() + prevLinePosition;
/* 124 */                       posY = (getPaddingTop() + lineLength) - childHeight;
                            }
/* 126 */                   lp.setPosition(posX, posY);
/* 128 */                   controlMaxLength2 = Math.max(controlMaxLength2, lineLength);
/* 129 */                   controlMaxThickness2 = prevLinePosition + lineThickness;
                        }
                    }
/* 133 */           if (this.orientation == 0) {
/* 134 */               controlMaxLength = controlMaxLength2 + getPaddingLeft() + getPaddingRight();
/* 135 */               controlMaxThickness = controlMaxThickness2 + getPaddingBottom() + getPaddingTop();
                    } else {
/* 137 */               controlMaxLength = controlMaxLength2 + getPaddingBottom() + getPaddingTop();
/* 138 */               controlMaxThickness = controlMaxThickness2 + getPaddingLeft() + getPaddingRight();
                    }
/* 141 */           if (this.orientation == 0) {
/* 142 */               setMeasuredDimension(resolveSize(controlMaxLength, widthMeasureSpec), resolveSize(controlMaxThickness, heightMeasureSpec));
                    } else {
/* 144 */               setMeasuredDimension(resolveSize(controlMaxThickness, widthMeasureSpec), resolveSize(controlMaxLength, heightMeasureSpec));
                    }
                }

/* 149 */       private int getVerticalSpacing(LayoutParams lp) {
/* 150 */           if (lp.verticalSpacingSpecified()) {
/* 151 */               int vSpacing = LayoutParams.access$100(lp);
/* 155 */               return vSpacing;
                    }
/* 153 */           int vSpacing2 = this.verticalSpacing;
/* 155 */           return vSpacing2;
                }

/* 159 */       private int getHorizontalSpacing(LayoutParams lp) {
/* 160 */           if (lp.horizontalSpacingSpecified()) {
/* 161 */               int hSpacing = LayoutParams.access$200(lp);
/* 165 */               return hSpacing;
                    }
/* 163 */           int hSpacing2 = this.horizontalSpacing;
/* 165 */           return hSpacing2;
                }

                @Override // android.view.ViewGroup, android.view.View
/* 169 */       protected void onLayout(boolean changed, int l, int t, int r, int b) {
/* 170 */           int count = getChildCount();
                    for (int i = 0; i < count; i++) {
/* 172 */               View child = getChildAt(i);
/* 173 */               LayoutParams lp = (LayoutParams) child.getLayoutParams();
/* 174 */               child.layout(LayoutParams.access$300(lp), LayoutParams.access$400(lp), LayoutParams.access$300(lp) + child.getMeasuredWidth(), LayoutParams.access$400(lp) + child.getMeasuredHeight());
                    }
                }

                @Override // android.view.ViewGroup
/* 179 */       protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
/* 180 */           boolean more = super.drawChild(canvas, child, drawingTime);
/* 181 */           drawDebugInfo(canvas, child);
/* 182 */           return more;
                }

                @Override // android.view.ViewGroup
/* 186 */       protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
/* 187 */           return p instanceof LayoutParams;
                }

                @Override // android.view.ViewGroup
/* 191 */       protected LayoutParams generateDefaultLayoutParams() {
/* 192 */           return new LayoutParams(-2, -2);
                }

                @Override // android.view.ViewGroup
/* 196 */       public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
/* 197 */           return new LayoutParams(getContext(), attributeSet);
                }

                @Override // android.view.ViewGroup
/* 201 */       protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
/* 202 */           return new LayoutParams(p);
                }

/* 205 */       private void readStyleParameters(Context context, AttributeSet attributeSet) {
/* 206 */           this.horizontalSpacing = 0;
/* 207 */           this.verticalSpacing = 0;
/* 208 */           this.orientation = 0;
/* 209 */           this.debugDraw = false;
                }

/* 212 */       private void drawDebugInfo(Canvas canvas, View child) {
/* 213 */           if (this.debugDraw) {
/* 217 */               Paint childPaint = createPaint(-256);
/* 218 */               Paint layoutPaint = createPaint(-16711936);
/* 219 */               Paint newLinePaint = createPaint(-65536);
/* 221 */               LayoutParams lp = (LayoutParams) child.getLayoutParams();
/* 223 */               if (LayoutParams.access$200(lp) > 0) {
/* 224 */                   float x = child.getRight();
/* 225 */                   float y = child.getTop() + (child.getHeight() / 2.0f);
/* 226 */                   canvas.drawLine(x, y, x + LayoutParams.access$200(lp), y, childPaint);
/* 227 */                   canvas.drawLine((LayoutParams.access$200(lp) + x) - 4.0f, y - 4.0f, x + LayoutParams.access$200(lp), y, childPaint);
/* 228 */                   canvas.drawLine((LayoutParams.access$200(lp) + x) - 4.0f, y + 4.0f, x + LayoutParams.access$200(lp), y, childPaint);
                        } else if (this.horizontalSpacing > 0) {
/* 230 */                   float x2 = child.getRight();
/* 231 */                   float y2 = child.getTop() + (child.getHeight() / 2.0f);
/* 232 */                   canvas.drawLine(x2, y2, x2 + this.horizontalSpacing, y2, layoutPaint);
/* 233 */                   canvas.drawLine((this.horizontalSpacing + x2) - 4.0f, y2 - 4.0f, x2 + this.horizontalSpacing, y2, layoutPaint);
/* 234 */                   canvas.drawLine((this.horizontalSpacing + x2) - 4.0f, y2 + 4.0f, x2 + this.horizontalSpacing, y2, layoutPaint);
                        }
/* 237 */               if (LayoutParams.access$100(lp) > 0) {
/* 238 */                   float x3 = child.getLeft() + (child.getWidth() / 2.0f);
/* 239 */                   float y3 = child.getBottom();
/* 240 */                   canvas.drawLine(x3, y3, x3, y3 + LayoutParams.access$100(lp), childPaint);
/* 241 */                   canvas.drawLine(x3 - 4.0f, (LayoutParams.access$100(lp) + y3) - 4.0f, x3, y3 + LayoutParams.access$100(lp), childPaint);
/* 242 */                   canvas.drawLine(x3 + 4.0f, (LayoutParams.access$100(lp) + y3) - 4.0f, x3, y3 + LayoutParams.access$100(lp), childPaint);
                        } else if (this.verticalSpacing > 0) {
/* 244 */                   float x4 = child.getLeft() + (child.getWidth() / 2.0f);
/* 245 */                   float y4 = child.getBottom();
/* 246 */                   canvas.drawLine(x4, y4, x4, y4 + this.verticalSpacing, layoutPaint);
/* 247 */                   canvas.drawLine(x4 - 4.0f, (this.verticalSpacing + y4) - 4.0f, x4, y4 + this.verticalSpacing, layoutPaint);
/* 248 */                   canvas.drawLine(x4 + 4.0f, (this.verticalSpacing + y4) - 4.0f, x4, y4 + this.verticalSpacing, layoutPaint);
                        }
/* 251 */               if (LayoutParams.access$000(lp)) {
/* 252 */                   if (this.orientation == 0) {
/* 253 */                       float x5 = child.getLeft();
/* 254 */                       float y5 = child.getTop() + (child.getHeight() / 2.0f);
/* 255 */                       canvas.drawLine(x5, y5 - 6.0f, x5, y5 + 6.0f, newLinePaint);
                            } else {
/* 257 */                       float x6 = child.getLeft() + (child.getWidth() / 2.0f);
/* 258 */                       float y6 = child.getTop();
/* 259 */                       canvas.drawLine(x6 - 6.0f, y6, x6 + 6.0f, y6, newLinePaint);
                            }
                        }
                    }
                }

/* 264 */       private Paint createPaint(int color) {
/* 265 */           Paint paint = new Paint();
/* 266 */           paint.setAntiAlias(true);
/* 267 */           paint.setColor(color);
/* 268 */           paint.setStrokeWidth(2.0f);
/* 269 */           return paint;
                }

/* 270 */       public static class LayoutParams extends ViewGroup.LayoutParams {
                    private static int NO_SPACING = -1;
                    private int horizontalSpacing;
                    private boolean newLine;
                    private int verticalSpacing;

                    /* JADX INFO: renamed from: x */
                    private int f119x;

                    /* JADX INFO: renamed from: y */
                    private int f120y;

/* 271 */           static /* synthetic */ boolean access$000(LayoutParams x0) {
/* 272 */               return x0.newLine;
                    }

/* 271 */           static /* synthetic */ int access$100(LayoutParams x0) {
/* 272 */               return x0.verticalSpacing;
                    }

/* 271 */           static /* synthetic */ int access$200(LayoutParams x0) {
/* 272 */               return x0.horizontalSpacing;
                    }

/* 271 */           static /* synthetic */ int access$300(LayoutParams x0) {
/* 272 */               return x0.f119x;
                    }

/* 271 */           static /* synthetic */ int access$400(LayoutParams x0) {
/* 272 */               return x0.f120y;
                    }

/* 280 */           public LayoutParams(Context context, AttributeSet attributeSet) {
/* 281 */               super(context, attributeSet);
/* 276 */               this.horizontalSpacing = NO_SPACING;
/* 277 */               this.verticalSpacing = NO_SPACING;
/* 278 */               this.newLine = false;
/* 282 */               readStyleParameters(context, attributeSet);
                    }

/* 285 */           public LayoutParams(int width, int height) {
/* 286 */               super(width, height);
/* 276 */               this.horizontalSpacing = NO_SPACING;
/* 277 */               this.verticalSpacing = NO_SPACING;
/* 278 */               this.newLine = false;
                    }

/* 289 */           public LayoutParams(ViewGroup.LayoutParams layoutParams) {
/* 290 */               super(layoutParams);
/* 276 */               this.horizontalSpacing = NO_SPACING;
/* 277 */               this.verticalSpacing = NO_SPACING;
/* 278 */               this.newLine = false;
                    }

/* 293 */           public boolean horizontalSpacingSpecified() {
                        return this.horizontalSpacing != NO_SPACING;
                    }

/* 297 */           public boolean verticalSpacingSpecified() {
                        return this.verticalSpacing != NO_SPACING;
                    }

/* 301 */           public void setPosition(int x, int y) {
/* 302 */               this.f119x = x;
/* 303 */               this.f120y = y;
                    }

/* 306 */           private void readStyleParameters(Context context, AttributeSet attributeSet) {
/* 307 */               this.horizontalSpacing = NO_SPACING;
/* 308 */               this.verticalSpacing = NO_SPACING;
/* 309 */               this.newLine = false;
                    }
                }
            }
