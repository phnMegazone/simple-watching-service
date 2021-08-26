package com.fleta.watchingservice.domain.dto;

import com.fleta.watchingservice.grpc.common.WatchingOutput1;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@ToString
public class WatchingDto1 {
    private String stbPlayIp1;
    private String cdnLocalTyp;
    private String ipv6PlayIp;

    public WatchingOutput1 toWatchingOutput1() {
        return WatchingOutput1.newBuilder()
            .setStbPlayIp1(this.getStbPlayIp1())
            .setCdnLocalTyp(this.getCdnLocalTyp())
            .setIpv6PlayIp(this.getIpv6PlayIp())
            .build();
    }

    public static WatchingDto1 buildFromWatchingOutput1(WatchingOutput1 watchingOutput1) {
        return new WatchingDto1(
            watchingOutput1.getStbPlayIp1(),
            watchingOutput1.getCdnLocalTyp(),
            watchingOutput1.getIpv6PlayIp());
    }
}
