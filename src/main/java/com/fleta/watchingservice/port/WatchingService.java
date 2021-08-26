package com.fleta.watchingservice.port;

import com.fleta.watchingservice.grpc.common.WatchingInput1;
import com.fleta.watchingservice.grpc.common.WatchingInput2;
import com.fleta.watchingservice.grpc.common.WatchingOutput1;
import com.fleta.watchingservice.grpc.common.WatchingOutput2;

import java.util.List;

public interface WatchingService {
    List<WatchingOutput1> nxvod211TobeSql00901(WatchingInput1 input);
    List<WatchingOutput2> nxvod211TobeSql01801(WatchingInput2 input);
}
