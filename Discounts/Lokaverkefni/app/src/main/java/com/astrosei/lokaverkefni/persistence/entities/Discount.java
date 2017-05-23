package com.astrosei.lokaverkefni.persistence.entities;

/**
 * Created by astrosei on 01/04/2017.
 */

public class Discount {

    private String title;
    private String discountInfo;
    private String companyName;
    private String discountProvider;
    private String category;
    private Boolean isCondition;
    private String condition;

    public Discount(String title, String discountInfo, String companyName, String discountProvider,
                    String category, Boolean isCondition, String condition){

        this.title = title;
        this.discountInfo = discountInfo;
        this.companyName = companyName;
        this.discountProvider = discountProvider;
        this.category = category;
        this.isCondition = isCondition;
        this.condition = condition;
    }

    public String getTitle() {
        return title;
    }

    public String getDiscountInfo() {
        return discountInfo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getDiscountProvider() {
        return discountProvider;
    }

    public String getCategory() {
        return category;
    }

    public Boolean getIsCondition() {
        return isCondition;
    }
    public  String getCondition(){
        return condition;
    }

}
