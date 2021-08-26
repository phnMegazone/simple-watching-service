package com.fleta.watchingservice.domain.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A PtLvNodeInfo.
 */
@Entity
@Table(name = "pt_lv_node_info", schema = "imcsuser")
public class PtLvNodeInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @PrimaryKeyJoinColumn
    @Column(name = "sub_node_cd")
    private String subNodeCd;

    @Column(name = "sub_node_name")
    private String subNodeName;

    @Column(name = "sub_node_ip_1")
    private String subNodeIp1;

    @Column(name = "sub_node_ip_2")
    private String subNodeIp2;

    @Column(name = "stb_play_ip_1")
    private String stbPlayIp1;

    @Column(name = "stb_play_ip_2")
    private String stbPlayIp2;

    @Column(name = "service_yn")
    private String serviceYn;

    @Column(name = "use_yn")
    private String useYn;

    @Column(name = "storage_size")
    private Integer storageSize;

    @Column(name = "sub_node_port_1")
    private String subNodePort1;

    @Column(name = "sub_node_port_2")
    private String subNodePort2;

    @Column(name = "stb_play_ip_3")
    private String stbPlayIp3;

    @Column(name = "node_prior")
    private String nodePrior;

    @Column(name = "node_level")
    private String nodeLevel;

    @Column(name = "initial_yn")
    private String initialYn;

    @NotNull
    @PrimaryKeyJoinColumn
    @Column(name = "cdn_policy")
    private String cdnPolicy;

    @Column(name = "cdn_local_typ")
    private String cdnLocalTyp;

    @Column(name = "cdn_near_typ")
    private String cdnNearTyp;

    @Column(name = "cdn_center_typ")
    private String cdnCenterTyp;

    @Column(name = "exception_flag")
    private String exceptionFlag;

    @Column(name = "node_trans_yn")
    private String nodeTransYn;

    @Column(name = "cm_node_yn")
    private String cmNodeYn;

    @Column(name = "time_node_yn")
    private String timeNodeYn;

    @Column(name = "node_group")
    private String nodeGroup;

    @Column(name = "ipv_6_flag_1")
    private String ipv6Flag1;

    @Column(name = "ipv_6_flag_2")
    private String ipv6Flag2;

    @Column(name = "ipv_6_flag_3")
    private String ipv6Flag3;

    @Column(name = "ipv_6_play_ip_1")
    private String ipv6PlayIp1;

    @Column(name = "ipv_6_play_ip_2")
    private String ipv6PlayIp2;

    @Column(name = "ipv_6_play_ip_3")
    private String ipv6PlayIp3;

    @Column(name = "chnl_service_yn")
    private String chnlServiceYn;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PtLvNodeInfo id(Long id) {
        this.id = id;
        return this;
    }

    public String getSubNodeCd() {
        return this.subNodeCd;
    }

    public PtLvNodeInfo subNodeCd(String subNodeCd) {
        this.subNodeCd = subNodeCd;
        return this;
    }

    public void setSubNodeCd(String subNodeCd) {
        this.subNodeCd = subNodeCd;
    }

    public String getSubNodeName() {
        return this.subNodeName;
    }

    public PtLvNodeInfo subNodeName(String subNodeName) {
        this.subNodeName = subNodeName;
        return this;
    }

    public void setSubNodeName(String subNodeName) {
        this.subNodeName = subNodeName;
    }

    public String getSubNodeIp1() {
        return this.subNodeIp1;
    }

    public PtLvNodeInfo subNodeIp1(String subNodeIp1) {
        this.subNodeIp1 = subNodeIp1;
        return this;
    }

    public void setSubNodeIp1(String subNodeIp1) {
        this.subNodeIp1 = subNodeIp1;
    }

    public String getSubNodeIp2() {
        return this.subNodeIp2;
    }

    public PtLvNodeInfo subNodeIp2(String subNodeIp2) {
        this.subNodeIp2 = subNodeIp2;
        return this;
    }

    public void setSubNodeIp2(String subNodeIp2) {
        this.subNodeIp2 = subNodeIp2;
    }

    public String getStbPlayIp1() {
        return this.stbPlayIp1;
    }

    public PtLvNodeInfo stbPlayIp1(String stbPlayIp1) {
        this.stbPlayIp1 = stbPlayIp1;
        return this;
    }

    public void setStbPlayIp1(String stbPlayIp1) {
        this.stbPlayIp1 = stbPlayIp1;
    }

    public String getStbPlayIp2() {
        return this.stbPlayIp2;
    }

    public PtLvNodeInfo stbPlayIp2(String stbPlayIp2) {
        this.stbPlayIp2 = stbPlayIp2;
        return this;
    }

    public void setStbPlayIp2(String stbPlayIp2) {
        this.stbPlayIp2 = stbPlayIp2;
    }

    public String getServiceYn() {
        return this.serviceYn;
    }

    public PtLvNodeInfo serviceYn(String serviceYn) {
        this.serviceYn = serviceYn;
        return this;
    }

    public void setServiceYn(String serviceYn) {
        this.serviceYn = serviceYn;
    }

    public String getUseYn() {
        return this.useYn;
    }

    public PtLvNodeInfo useYn(String useYn) {
        this.useYn = useYn;
        return this;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public Integer getStorageSize() {
        return this.storageSize;
    }

    public PtLvNodeInfo storageSize(Integer storageSize) {
        this.storageSize = storageSize;
        return this;
    }

    public void setStorageSize(Integer storageSize) {
        this.storageSize = storageSize;
    }

    public String getSubNodePort1() {
        return this.subNodePort1;
    }

    public PtLvNodeInfo subNodePort1(String subNodePort1) {
        this.subNodePort1 = subNodePort1;
        return this;
    }

    public void setSubNodePort1(String subNodePort1) {
        this.subNodePort1 = subNodePort1;
    }

    public String getSubNodePort2() {
        return this.subNodePort2;
    }

    public PtLvNodeInfo subNodePort2(String subNodePort2) {
        this.subNodePort2 = subNodePort2;
        return this;
    }

    public void setSubNodePort2(String subNodePort2) {
        this.subNodePort2 = subNodePort2;
    }

    public String getStbPlayIp3() {
        return this.stbPlayIp3;
    }

    public PtLvNodeInfo stbPlayIp3(String stbPlayIp3) {
        this.stbPlayIp3 = stbPlayIp3;
        return this;
    }

    public void setStbPlayIp3(String stbPlayIp3) {
        this.stbPlayIp3 = stbPlayIp3;
    }

    public String getNodePrior() {
        return this.nodePrior;
    }

    public PtLvNodeInfo nodePrior(String nodePrior) {
        this.nodePrior = nodePrior;
        return this;
    }

    public void setNodePrior(String nodePrior) {
        this.nodePrior = nodePrior;
    }

    public String getNodeLevel() {
        return this.nodeLevel;
    }

    public PtLvNodeInfo nodeLevel(String nodeLevel) {
        this.nodeLevel = nodeLevel;
        return this;
    }

    public void setNodeLevel(String nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    public String getInitialYn() {
        return this.initialYn;
    }

    public PtLvNodeInfo initialYn(String initialYn) {
        this.initialYn = initialYn;
        return this;
    }

    public void setInitialYn(String initialYn) {
        this.initialYn = initialYn;
    }

    public String getCdnPolicy() {
        return this.cdnPolicy;
    }

    public PtLvNodeInfo cdnPolicy(String cdnPolicy) {
        this.cdnPolicy = cdnPolicy;
        return this;
    }

    public void setCdnPolicy(String cdnPolicy) {
        this.cdnPolicy = cdnPolicy;
    }

    public String getCdnLocalTyp() {
        return this.cdnLocalTyp;
    }

    public PtLvNodeInfo cdnLocalTyp(String cdnLocalTyp) {
        this.cdnLocalTyp = cdnLocalTyp;
        return this;
    }

    public void setCdnLocalTyp(String cdnLocalTyp) {
        this.cdnLocalTyp = cdnLocalTyp;
    }

    public String getCdnNearTyp() {
        return this.cdnNearTyp;
    }

    public PtLvNodeInfo cdnNearTyp(String cdnNearTyp) {
        this.cdnNearTyp = cdnNearTyp;
        return this;
    }

    public void setCdnNearTyp(String cdnNearTyp) {
        this.cdnNearTyp = cdnNearTyp;
    }

    public String getCdnCenterTyp() {
        return this.cdnCenterTyp;
    }

    public PtLvNodeInfo cdnCenterTyp(String cdnCenterTyp) {
        this.cdnCenterTyp = cdnCenterTyp;
        return this;
    }

    public void setCdnCenterTyp(String cdnCenterTyp) {
        this.cdnCenterTyp = cdnCenterTyp;
    }

    public String getExceptionFlag() {
        return this.exceptionFlag;
    }

    public PtLvNodeInfo exceptionFlag(String exceptionFlag) {
        this.exceptionFlag = exceptionFlag;
        return this;
    }

    public void setExceptionFlag(String exceptionFlag) {
        this.exceptionFlag = exceptionFlag;
    }

    public String getNodeTransYn() {
        return this.nodeTransYn;
    }

    public PtLvNodeInfo nodeTransYn(String nodeTransYn) {
        this.nodeTransYn = nodeTransYn;
        return this;
    }

    public void setNodeTransYn(String nodeTransYn) {
        this.nodeTransYn = nodeTransYn;
    }

    public String getCmNodeYn() {
        return this.cmNodeYn;
    }

    public PtLvNodeInfo cmNodeYn(String cmNodeYn) {
        this.cmNodeYn = cmNodeYn;
        return this;
    }

    public void setCmNodeYn(String cmNodeYn) {
        this.cmNodeYn = cmNodeYn;
    }

    public String getTimeNodeYn() {
        return this.timeNodeYn;
    }

    public PtLvNodeInfo timeNodeYn(String timeNodeYn) {
        this.timeNodeYn = timeNodeYn;
        return this;
    }

    public void setTimeNodeYn(String timeNodeYn) {
        this.timeNodeYn = timeNodeYn;
    }

    public String getNodeGroup() {
        return this.nodeGroup;
    }

    public PtLvNodeInfo nodeGroup(String nodeGroup) {
        this.nodeGroup = nodeGroup;
        return this;
    }

    public void setNodeGroup(String nodeGroup) {
        this.nodeGroup = nodeGroup;
    }

    public String getIpv6Flag1() {
        return this.ipv6Flag1;
    }

    public PtLvNodeInfo ipv6Flag1(String ipv6Flag1) {
        this.ipv6Flag1 = ipv6Flag1;
        return this;
    }

    public void setIpv6Flag1(String ipv6Flag1) {
        this.ipv6Flag1 = ipv6Flag1;
    }

    public String getIpv6Flag2() {
        return this.ipv6Flag2;
    }

    public PtLvNodeInfo ipv6Flag2(String ipv6Flag2) {
        this.ipv6Flag2 = ipv6Flag2;
        return this;
    }

    public void setIpv6Flag2(String ipv6Flag2) {
        this.ipv6Flag2 = ipv6Flag2;
    }

    public String getIpv6Flag3() {
        return this.ipv6Flag3;
    }

    public PtLvNodeInfo ipv6Flag3(String ipv6Flag3) {
        this.ipv6Flag3 = ipv6Flag3;
        return this;
    }

    public void setIpv6Flag3(String ipv6Flag3) {
        this.ipv6Flag3 = ipv6Flag3;
    }

    public String getIpv6PlayIp1() {
        return this.ipv6PlayIp1;
    }

    public PtLvNodeInfo ipv6PlayIp1(String ipv6PlayIp1) {
        this.ipv6PlayIp1 = ipv6PlayIp1;
        return this;
    }

    public void setIpv6PlayIp1(String ipv6PlayIp1) {
        this.ipv6PlayIp1 = ipv6PlayIp1;
    }

    public String getIpv6PlayIp2() {
        return this.ipv6PlayIp2;
    }

    public PtLvNodeInfo ipv6PlayIp2(String ipv6PlayIp2) {
        this.ipv6PlayIp2 = ipv6PlayIp2;
        return this;
    }

    public void setIpv6PlayIp2(String ipv6PlayIp2) {
        this.ipv6PlayIp2 = ipv6PlayIp2;
    }

    public String getIpv6PlayIp3() {
        return this.ipv6PlayIp3;
    }

    public PtLvNodeInfo ipv6PlayIp3(String ipv6PlayIp3) {
        this.ipv6PlayIp3 = ipv6PlayIp3;
        return this;
    }

    public void setIpv6PlayIp3(String ipv6PlayIp3) {
        this.ipv6PlayIp3 = ipv6PlayIp3;
    }

    public String getChnlServiceYn() {
        return this.chnlServiceYn;
    }

    public PtLvNodeInfo chnlServiceYn(String chnlServiceYn) {
        this.chnlServiceYn = chnlServiceYn;
        return this;
    }

    public void setChnlServiceYn(String chnlServiceYn) {
        this.chnlServiceYn = chnlServiceYn;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PtLvNodeInfo)) {
            return false;
        }
        return id != null && id.equals(((PtLvNodeInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PtLvNodeInfo{" +
            "id=" + getId() +
            ", subNodeCd='" + getSubNodeCd() + "'" +
            ", subNodeName='" + getSubNodeName() + "'" +
            ", subNodeIp1='" + getSubNodeIp1() + "'" +
            ", subNodeIp2='" + getSubNodeIp2() + "'" +
            ", stbPlayIp1='" + getStbPlayIp1() + "'" +
            ", stbPlayIp2='" + getStbPlayIp2() + "'" +
            ", serviceYn='" + getServiceYn() + "'" +
            ", useYn='" + getUseYn() + "'" +
            ", storageSize=" + getStorageSize() +
            ", subNodePort1='" + getSubNodePort1() + "'" +
            ", subNodePort2='" + getSubNodePort2() + "'" +
            ", stbPlayIp3='" + getStbPlayIp3() + "'" +
            ", nodePrior='" + getNodePrior() + "'" +
            ", nodeLevel='" + getNodeLevel() + "'" +
            ", initialYn='" + getInitialYn() + "'" +
            ", cdnPolicy='" + getCdnPolicy() + "'" +
            ", cdnLocalTyp='" + getCdnLocalTyp() + "'" +
            ", cdnNearTyp='" + getCdnNearTyp() + "'" +
            ", cdnCenterTyp='" + getCdnCenterTyp() + "'" +
            ", exceptionFlag='" + getExceptionFlag() + "'" +
            ", nodeTransYn='" + getNodeTransYn() + "'" +
            ", cmNodeYn='" + getCmNodeYn() + "'" +
            ", timeNodeYn='" + getTimeNodeYn() + "'" +
            ", nodeGroup='" + getNodeGroup() + "'" +
            ", ipv6Flag1='" + getIpv6Flag1() + "'" +
            ", ipv6Flag2='" + getIpv6Flag2() + "'" +
            ", ipv6Flag3='" + getIpv6Flag3() + "'" +
            ", ipv6PlayIp1='" + getIpv6PlayIp1() + "'" +
            ", ipv6PlayIp2='" + getIpv6PlayIp2() + "'" +
            ", ipv6PlayIp3='" + getIpv6PlayIp3() + "'" +
            ", chnlServiceYn='" + getChnlServiceYn() + "'" +
            "}";
    }
}
