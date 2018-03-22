package cun.yun.card.admin.dal.model;

import java.math.BigDecimal;
import java.util.Date;

public class Bank {
    private Long id;

    private String name;

    private String iamge;

    private BigDecimal price;

    private Integer sort;

    private Integer isEmploy;

    private Date updatedTime;

    private String introduce;

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

    public String getIamge() {
        return iamge;
    }

    public void setIamge(String iamge) {
        this.iamge = iamge;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", iamge=").append(iamge);
        sb.append(", price=").append(price);
        sb.append(", sort=").append(sort);
        sb.append(", isEmploy=").append(isEmploy);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", introduce=").append(introduce);
        sb.append("]");
        return sb.toString();
    }
}