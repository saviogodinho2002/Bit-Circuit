package com.savio.bitcircuit;

public class XorGate implements LogicGate{
    String dataA;
    String dataB;

    public XorGate(String dataA, String dataB) {
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
        return !(this.getDataA().equals(this.getDataB()))?"1":"0"; // no caso de duas entradas isso é valido
    }
}
