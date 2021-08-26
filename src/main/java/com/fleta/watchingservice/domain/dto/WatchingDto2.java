package com.fleta.watchingservice.domain.dto;

import com.fleta.watchingservice.grpc.common.WatchingOutput2;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@ToString
public class WatchingDto2 {
    private String adiAlbumId;
    private String watchDate;

    public WatchingOutput2 toWatchingOutput2() {
        return WatchingOutput2.newBuilder()
            .setAdiAlbumId(this.getAdiAlbumId())
            .setWatchDate(this.getWatchDate())
            .build();
    }

    public static WatchingDto2 buildFromWatchingOutput2(WatchingOutput2 watchingOutput2) {
        return new WatchingDto2(
            watchingOutput2.getAdiAlbumId(),
            watchingOutput2.getWatchDate());
    }
}
