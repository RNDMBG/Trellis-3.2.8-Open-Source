            package com.google.android.exoplayer.util;

            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.Comparator;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    public class SlidingPercentile {
                private static final int MAX_RECYCLED_SAMPLES = 5;
                private static final int SORT_ORDER_BY_INDEX = 1;
                private static final int SORT_ORDER_BY_VALUE = 0;
                private static final int SORT_ORDER_NONE = -1;
                private final int maxWeight;
                private int nextSampleIndex;
                private int recycledSampleCount;
                private int totalWeight;
                private static final Comparator<Sample> INDEX_COMPARATOR = new C08771();
                private static final Comparator<Sample> VALUE_COMPARATOR = new C08782();
                private final Sample[] recycledSamples = new Sample[5];
                private final ArrayList<Sample> samples = new ArrayList<>();
                private int currentSortOrder = -1;

                /* JADX INFO: renamed from: com.google.android.exoplayer.util.SlidingPercentile$1 */
/* 35 */        static class C08771 implements Comparator<Sample> {
/* 36 */            C08771() {
                    }


                    /* JADX INFO: renamed from: compare, reason: avoid collision after fix types in other method */
/* 39 */            public int compare(Sample a, Sample b) {
/* 40 */                return a.index - b.index;
                    }
                }

                /* JADX INFO: renamed from: com.google.android.exoplayer.util.SlidingPercentile$2 */
/* 42 */        static class C08782 implements Comparator<Sample> {
/* 43 */            C08782() {
                    }


                    /* JADX INFO: renamed from: compare, reason: avoid collision after fix types in other method */
/* 46 */            public int compare(Sample a, Sample b) {
                        if (a.value < b.value) {
                            return -1;
                        }
                        return b.value < a.value ? 1 : 0;
                    }
                }

/* 66 */        public SlidingPercentile(int maxWeight) {
/* 68 */            this.maxWeight = maxWeight;
                }

/* 81 */        public void addSample(int weight, float value) {
                    Sample newSample;
/* 82 */            ensureSortedByIndex();
/* 84 */            if (this.recycledSampleCount > 0) {
                        Sample[] sampleArr = this.recycledSamples;
                        int i = this.recycledSampleCount - 1;
                        this.recycledSampleCount = i;
                        newSample = sampleArr[i];
                    } else {
/* 84 */                newSample = new Sample(null);
                    }
/* 86 */            int i2 = this.nextSampleIndex;
                    this.nextSampleIndex = i2 + 1;
                    newSample.index = i2;
/* 87 */            newSample.weight = weight;
/* 88 */            newSample.value = value;
/* 89 */            this.samples.add(newSample);
/* 90 */            this.totalWeight += weight;
/* 92 */            while (this.totalWeight > this.maxWeight) {
/* 93 */                int excessWeight = this.totalWeight - this.maxWeight;
/* 94 */                Sample oldestSample = this.samples.get(0);
/* 95 */                if (oldestSample.weight <= excessWeight) {
/* 96 */                    this.totalWeight -= oldestSample.weight;
/* 97 */                    this.samples.remove(0);
/* 98 */                    if (this.recycledSampleCount < 5) {
/* 99 */                        Sample[] sampleArr2 = this.recycledSamples;
                                int i3 = this.recycledSampleCount;
                                this.recycledSampleCount = i3 + 1;
                                sampleArr2[i3] = oldestSample;
                            }
                        } else {
/* 102 */                   oldestSample.weight -= excessWeight;
/* 103 */                   this.totalWeight -= excessWeight;
                        }
                    }
                }

/* 114 */       public float getPercentile(float percentile) {
/* 115 */           ensureSortedByValue();
/* 116 */           float desiredWeight = percentile * this.totalWeight;
/* 117 */           int accumulatedWeight = 0;
                    for (int i = 0; i < this.samples.size(); i++) {
/* 119 */               Sample currentSample = this.samples.get(i);
/* 120 */               accumulatedWeight += currentSample.weight;
                        if (accumulatedWeight >= desiredWeight) {
/* 122 */                   return currentSample.value;
                        }
                    }
/* 126 */           if (this.samples.isEmpty()) {
                        return Float.NaN;
                    }
                    return this.samples.get(this.samples.size() - 1).value;
                }

/* 132 */       private void ensureSortedByIndex() {
/* 133 */           if (this.currentSortOrder != 1) {
/* 134 */               Collections.sort(this.samples, INDEX_COMPARATOR);
/* 135 */               this.currentSortOrder = 1;
                    }
                }

/* 142 */       private void ensureSortedByValue() {
/* 143 */           if (this.currentSortOrder != 0) {
/* 144 */               Collections.sort(this.samples, VALUE_COMPARATOR);
/* 145 */               this.currentSortOrder = 0;
                    }
                }

/* 147 */       private static class Sample {
                    public int index;
                    public float value;
                    public int weight;

/* 148 */           private Sample() {
                    }

/* 148 */           /* synthetic */ Sample(C08771 x0) {
/* 149 */               this();
                    }
                }
            }
