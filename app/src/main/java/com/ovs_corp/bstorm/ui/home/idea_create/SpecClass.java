package com.ovs_corp.bstorm.ui.home.idea_create;

import java.util.ArrayList;

public class SpecClass {
    public ArrayList<Financial> financial;
    public ArrayList<NonFinancial> nonFinancial;

    public SpecClass(ArrayList<Financial> financial, ArrayList<NonFinancial> nonFinancial) {
        this.financial = financial;
        this.nonFinancial = nonFinancial;
    }
}
