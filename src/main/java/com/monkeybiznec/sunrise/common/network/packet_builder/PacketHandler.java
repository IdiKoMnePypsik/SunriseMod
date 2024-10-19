package com.monkeybiznec.sunrise.common.network.packet_builder;

import net.minecraftforge.network.NetworkEvent;

@FunctionalInterface
public interface PacketHandler {
    void handle(int pPacketId, Object[] pData, NetworkEvent.Context pContext);
}
