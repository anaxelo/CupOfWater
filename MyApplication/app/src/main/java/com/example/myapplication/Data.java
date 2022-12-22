package com.example.myapplication;

public class Data {
    String activity;
    int sex;
    int weightsData;
    double water;
    int cups;
    int act;
    public Data(String activityFromMain, int sexFromMain, String weightFromMain){
        activity = activityFromMain;
        sex=sexFromMain;//male=2 fmale=1
        weightsData=Integer.parseInt(weightFromMain);

        cups=0;
        act=0;
    }




    private int getAct() {
        switch (activity){
            case "Once a week":
                act=1;
                break;
            case "Three times a week":
                act=3;
                break;
            case "Twice a week":
                act=2;
                break;
            case "Four times a week":
                act=4;
                break;
            case "Five times a week":
                act=5;
                break;
            case "Six times a week":
                act=6;
                break;
            case "Every day":
                act=7;
                break;
            default:
                act=1;
                break;
        }
        return act;
    }

    public int CalculationWater() {
        act=getAct();
        if (sex == 2) {
            water = ((weightsData*0.04) + (act*0.6))*1000;
        } else if(sex==1) {
            water = ((weightsData*0.03) + (act*0.4))*1000;
        }
        return (int)water/8;
    }

}
