package com.savio.bitcircuit;

public class AndGate implements LogicGate{
    String dataA;
    String dataB;

    public AndGate(String dataA, String dataB) {
        this.dataA = dataA;
        this.dataB = dataB;
    }

    public String getDataA() {
        return dataA;
    }

    public void setDataA(String dataA) {
        this.dataA = dataA;
    }

    public String getDataB() {
        return dataB;
    }

    public void setDataB(String dataB) {
        this.dataB = dataB;
    }

    @Override
    public String getDataExit() {
        return (this.getDataA().equals("1") && this.getDataB().equals("1")?"1":"0");
    }
}
