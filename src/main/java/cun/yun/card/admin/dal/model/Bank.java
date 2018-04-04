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

    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", iamge='" + iamge + '\'' +
                ", price=" + price +
                ", sort=" + sort +
                ", isEmploy=" + isEmploy +
                ", updatedTime=" + updatedTime +
                ", createTime=" + createTime +
                ", introduce='" + introduce + '\'' +
                '}';
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {

        return createTime;
    }

    private Date createTime;

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

}