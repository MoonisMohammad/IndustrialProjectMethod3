package com.company;

import java.text.*;

public class UnboundedKnapsack {
    public class Item {
        protected String name = "";
        protected int value = 0;
        protected double weight = 0;
        protected double aluminum = 0;
        protected double silicon = 0;
        protected double glass = 0;
        protected double rubber = 0;
        protected double plastic = 0;
        protected double steel =0;

        public Item() {
        }

        public Item(String name, int value, double weight,double silicon,double glass,double rubber, double aluminum,double plastic,double steel) {
            setName(name);
            setValue(value);
            setWeight(weight);
            setAluminum(aluminum);
            setGlass(glass);
            setPlastic(plastic);
            setSilicon(silicon);
            setRubber(rubber);
            setSteel(steel);
        }

        public double getSteel() {return steel;}

        public void setSteel(double steel) {this.steel = steel;}

        public double getPlastic() {return plastic;}

        public void setPlastic(double plastic) {this.plastic = plastic;}

        public double getSilicon() {return silicon;}

        public void setSilicon(double silicon) {this.silicon = silicon;}

        public double getRubber() {return rubber;}

        public void setRubber(double rubber) {this.rubber = rubber;}

        public double getGlass() {return glass;}

        public void setGlass(double glass) {this.glass = glass;}

        public double getAluminum() {return aluminum;}

        public void setAluminum(double aluminum) {this.aluminum = aluminum;}

        public int getValue() {return value;}

        public void setValue(int value) {this.value = Math.max(value, 0);}

        public double getWeight() {return weight;}

        public void setWeight(double weight) {this.weight = Math.max(weight, 0);}

        public String getName() {return name;}

        public void setName(String name) {this.name = name;}

    } // class

    protected Item []  items = {
            new Item("Hybrid", 3100,  2638.6, 1700, 55, 20, 160, 140, 790),
            new Item("Electric" , 2750,  800.0,2750.0 , 80.0,25.0,550.0,113.3,410.0),
            new Item("Gas"   , 3800,3186.8,  1200,45.3,22.6,150,151, 625)
    };
    protected final int    n = items.length; // the number of items
    protected Item      sack = new Item("sack"   ,    0, 576000, 1200000,200000, 95000,132000,130000,600000);
    protected Item      best = new Item("best"   ,    0,  0.0, 0.000, 0.000, 0.000, 0.000, 0.000, 0.000);
    protected int  []  maxIt = new int [n];  // maximum number of items
    protected int  []    iIt = new int [n];  // current indexes of items
    protected int  [] bestAm = new int [n];  // best amounts

    public UnboundedKnapsack(){}
    public void solve() {
        // initializing:
        for (int i = 0; i < n; i++) {
            maxIt [i] = Math.min(
                    (int)(sack.getWeight() / items[i].getWeight()),
                    (int)(sack.getSilicon() / items[i].getSilicon())

            );
            maxIt [i] = Math.min(
                    maxIt[i],
                    (int)(sack.getGlass() / items[i].getGlass())
            );
            maxIt [i] = Math.min(
                    maxIt[i],
                    (int)(sack.getRubber() / items[i].getRubber())

            );
            maxIt [i] = Math.min(
                    maxIt[i],
                    (int)(sack.getAluminum() / items[i].getAluminum())

            );
            maxIt [i] = Math.min(
                    maxIt[i],
                    (int)(sack.getPlastic() / items[i].getPlastic() )

            );
            maxIt [i] = Math.min(
                    maxIt[i],
                    (int)(sack.getSteel() / items[i].getSteel() )

            );
        } // for (i)
        double capacity = 600000 * 0.96;
        sack = new Item("sack"   ,    0, capacity, 1200000,200000, 95000,132000,130000,600000);
        // calc the solution:
        calcWithRecursion(0);

        // Print out the solution:
        NumberFormat nf = NumberFormat.getInstance();
        System.out.println("Maximum value achievable is: " + best.getValue());
        System.out.print("This is achieved by carrying (one solution): ");
        for (int i = 0; i < n; i++) {
            System.out.print(bestAm[i] + " " + items[i].getName() + ", ");
        }
        System.out.println();
        System.out.println("The weight to carry is: " + nf.format(best.getWeight())
                +"   and the silicone used is: " + nf.format(best.getSilicon())
                +"   and the glass used is: " + nf.format(best.getGlass())
                +"   and the rubber used is: " + nf.format(best.getRubber())
                +"   and the aluminum used is: " + nf.format(best.getAluminum())
                +"   and the plastic used is: " + nf.format(best.getPlastic())
                +"   and the steel used is: " + nf.format(best.getSteel()));

    }

    // calculation the solution with recursion method
    // item : the number of item in the "items" array
    public void calcWithRecursion(int item) {
        for (int i = 0; i <= maxIt[item]; i++) {
            iIt[item] = i;
            if (item < n-1) {
                calcWithRecursion(item+1);
            } else {
                int    currVal = 0;   // current value
                double currWei = 0.0; // current weight
                double currSilicone = 0.0; // current Volume
                double currGlass = 0.0; // current Volume
                double currRubber = 0.0; // current Volume
                double currAluminum = 0.0; // current Volume
                double currPlastic = 0.0; // current Volume
                double currSteel = 0.0;

                for (int j = 0; j < n; j++) {
                    currVal += iIt[j] * items[j].getValue();
                    currWei += iIt[j] * items[j].getWeight();
                    currSilicone += iIt[j] * items[j].getSilicon();
                    currGlass +=  iIt[j] * items[j].getGlass();
                    currRubber += iIt[j] * items[j].getRubber();
                    currAluminum += iIt[j] * items[j].getAluminum();
                    currPlastic += iIt[j] * items[j].getPlastic();
                    currSteel += iIt[j] * items[j].getSteel();
                }

                if (currVal > best.getValue()
                        &&
                        currWei <= sack.getWeight()
                        &&
                        currSilicone <= sack.getSilicon()
                        &&
                        currGlass <= sack.getGlass()
                        &&
                        currRubber <= sack.getRubber()
                        &&
                        currAluminum <= sack.getAluminum()
                        &&
                        currPlastic <= sack.getPlastic()
                        &&
                        currSteel <= sack.getSteel()
                        &&
                        1.5 * iIt[1] >= iIt[0] + iIt[2]
                        &&
                        2*iIt[2] <= iIt[0]

                ) {
                    best.setValue (currVal);
                    best.setWeight(currWei);
                    best.setSilicon(currSilicone);
                    best.setPlastic(currPlastic);
                    best.setGlass(currGlass);
                    best.setRubber(currRubber);
                    best.setAluminum(currAluminum);
                    best.setPlastic(currPlastic);
                    best.setSteel(currSteel);
                    for (int j = 0; j < n; j++) bestAm[j] = iIt[j];
                }
            }
        }
    }


} // class
