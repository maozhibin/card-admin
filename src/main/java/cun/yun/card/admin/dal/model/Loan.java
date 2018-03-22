package cun.yun.card.admin.dal.model;

import java.math.BigDecimal;
import java.util.Date;

public class Loan {
    private Long id;

    private String name;

    private BigDecimal price;

    private BigDecimal limitMin;

    private BigDecimal limitMax;

    private Double moneyRate;

    private String image;

    private Integer isEmploy;

    private Date updatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getLimitMin() {
        return limitMin;
    }

    public void setLimitMin(BigDecimal limitMin) {
        this.limitMin = limitMin;
    }

    public BigDecimal getLimitMax() {
        return limitMax;
    }

    public void setLimitMax(BigDecimal limitMax) {
        this.limitMax = limitMax;
    }

    public Double getMoneyRate() {
        return moneyRate;
    }

    public void setMoneyRate(Double moneyRate) {
        this.moneyRate = moneyRate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getIsEmploy() {
        return isEmploy;
    }

    public void setIsEmploy(Integer isEmploy) {
        this.isEmploy = isEmploy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", price=").append(price);
        sb.append(", limitMin=").append(limitMin);
        sb.append(", limitMax=").append(limitMax);
        sb.append(", moneyRate=").append(moneyRate);
        sb.append(", image=").append(image);
        sb.append(", isEmploy=").append(isEmploy);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append("]");
        return sb.toString();
    }
}