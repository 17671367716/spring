package com.example.springboot.bean;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @TableName account
 */
@Data
public class Account implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     *
     */
    private String companyTitle;

    /**
     *
     */
    private String legalUser;

    /**
     *
     */
    private String accountUser;

    /**
     *
     */
    private String iphone;

    /**
     *
     */
    private String companySite;

    /**
     *
     */
    private String accountType;

    /**
     *
     */
    private String accountSite;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Account other = (Account) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getCompanyTitle() == null ? other.getCompanyTitle() == null : this.getCompanyTitle().equals(other.getCompanyTitle()))
                && (this.getLegalUser() == null ? other.getLegalUser() == null : this.getLegalUser().equals(other.getLegalUser()))
                && (this.getAccountUser() == null ? other.getAccountUser() == null : this.getAccountUser().equals(other.getAccountUser()))
                && (this.getIphone() == null ? other.getIphone() == null : this.getIphone().equals(other.getIphone()))
                && (this.getCompanySite() == null ? other.getCompanySite() == null : this.getCompanySite().equals(other.getCompanySite()))
                && (this.getAccountType() == null ? other.getAccountType() == null : this.getAccountType().equals(other.getAccountType()))
                && (this.getAccountSite() == null ? other.getAccountSite() == null : this.getAccountSite().equals(other.getAccountSite()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCompanyTitle() == null) ? 0 : getCompanyTitle().hashCode());
        result = prime * result + ((getLegalUser() == null) ? 0 : getLegalUser().hashCode());
        result = prime * result + ((getAccountUser() == null) ? 0 : getAccountUser().hashCode());
        result = prime * result + ((getIphone() == null) ? 0 : getIphone().hashCode());
        result = prime * result + ((getCompanySite() == null) ? 0 : getCompanySite().hashCode());
        result = prime * result + ((getAccountType() == null) ? 0 : getAccountType().hashCode());
        result = prime * result + ((getAccountSite() == null) ? 0 : getAccountSite().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", companyTitle=").append(companyTitle);
        sb.append(", legalUser=").append(legalUser);
        sb.append(", accountUser=").append(accountUser);
        sb.append(", iphone=").append(iphone);
        sb.append(", companySite=").append(companySite);
        sb.append(", accountType=").append(accountType);
        sb.append(", accountSite=").append(accountSite);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
