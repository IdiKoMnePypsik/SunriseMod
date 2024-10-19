package com.monkeybiznec.sunrise.common.network.packet_builder;

import net.minecraftforge.network.NetworkEvent;
import org.jline.utils.Log;

import java.util.HashMap;
import java.util.Map;

public class PacketProcessor {
    private static final Map<Integer, PacketHandler> handlers = new HashMap<>();

    public static void registerHandler(int pPacketId, PacketHandler pHandler) {
        handlers.put(pPacketId, pHandler);
    }

    public static void process(int pPacketId, Object[] pData, NetworkEvent.Context pContext) {
        PacketHandler handler = handlers.get(pPacketId);
        if (handler != null) {
            handler.handle(pPacketId, pData, pContext);
        } else {
            Log.error("No handler registered for packetId: " + pPacketId);
        }
    }
}
