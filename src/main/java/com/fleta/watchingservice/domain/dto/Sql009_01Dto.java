package com.fleta.watchingservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * nxvod211_TOBE_SQL009_01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sql009_01Dto {
    private String dongCd;
    private String stbPlayIp1;
    private String cdnLocalTyp;
    private String ipv6PlayIp;
}
