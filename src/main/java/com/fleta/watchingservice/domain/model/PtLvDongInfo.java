package com.fleta.watchingservice.domain.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A PtLvDongInfo.
 */
@Entity
@Table(name = "pt_lv_dong_info", schema = "imcsuser")
public class PtLvDongInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @PrimaryKeyJoinColumn
    @Column(name = "dong_cd")
    private String dongCd;

    @Column(name = "zip_no")
    private String zipNo;

    @Column(name = "addr")
    private String addr;

    @Column(name = "detail_addr")
    private String detailAddr;

    @Column(name = "region_key")
    private String regionKey;

    @NotNull
    @PrimaryKeyJoinColumn
    @Column(name = "sub_node_cd")
    private String subNodeCd;

    @Column(name = "nsc_sub_node_cd")
    private String nscSubNodeCd;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PtLvDongInfo id(Long id) {
        this.id = id;
        return this;
    }

    public String getDongCd() {
        return this.dongCd;
    }

    public PtLvDongInfo dongCd(String dongCd) {
        this.dongCd = dongCd;
        return this;
    }

    public void setDongCd(String dongCd) {
        this.dongCd = dongCd;
    }

    public String getZipNo() {
        return this.zipNo;
    }

    public PtLvDongInfo zipNo(String zipNo) {
        this.zipNo = zipNo;
        return this;
    }

    public void setZipNo(String zipNo) {
        this.zipNo = zipNo;
    }

    public String getAddr() {
        return this.addr;
    }

    public PtLvDongInfo addr(String addr) {
        this.addr = addr;
        return this;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getDetailAddr() {
        return this.detailAddr;
    }

    public PtLvDongInfo detailAddr(String detailAddr) {
        this.detailAddr = detailAddr;
        return this;
    }

    public void setDetailAddr(String detailAddr) {
        this.detailAddr = detailAddr;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public PtLvDongInfo regionKey(String regionKey) {
        this.regionKey = regionKey;
        return this;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public String getSubNodeCd() {
        return this.subNodeCd;
    }

    public PtLvDongInfo subNodeCd(String subNodeCd) {
        this.subNodeCd = subNodeCd;
        return this;
    }

    public void setSubNodeCd(String subNodeCd) {
        this.subNodeCd = subNodeCd;
    }

    public String getNscSubNodeCd() {
        return this.nscSubNodeCd;
    }

    public PtLvDongInfo nscSubNodeCd(String nscSubNodeCd) {
        this.nscSubNodeCd = nscSubNodeCd;
        return this;
    }

    public void setNscSubNodeCd(String nscSubNodeCd) {
        this.nscSubNodeCd = nscSubNodeCd;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PtLvDongInfo)) {
            return false;
        }
        return id != null && id.equals(((PtLvDongInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PtLvDongInfo{" +
            "id=" + getId() +
            ", dongCd='" + getDongCd() + "'" +
            ", zipNo='" + getZipNo() + "'" +
            ", addr='" + getAddr() + "'" +
            ", detailAddr='" + getDetailAddr() + "'" +
            ", regionKey='" + getRegionKey() + "'" +
            ", subNodeCd='" + getSubNodeCd() + "'" +
            ", nscSubNodeCd='" + getNscSubNodeCd() + "'" +
            "}";
    }
}
