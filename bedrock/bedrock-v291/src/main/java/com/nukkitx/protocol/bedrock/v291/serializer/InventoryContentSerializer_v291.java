package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.packet.InventoryContentPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryContentSerializer_v291 implements BedrockPacketSerializer<InventoryContentPacket> {
    public static final InventoryContentSerializer_v291 INSTANCE = new InventoryContentSerializer_v291();

    private static final ItemData[] EMPTY = new ItemData[0];

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, InventoryContentPacket packet, BedrockSession session) {
        VarInts.writeUnsignedInt(buffer, packet.getContainerId());
        helper.writeArray(buffer, packet.getContents(), (buf, item) -> helper.writeItem(buf, item, session));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, InventoryContentPacket packet, BedrockSession session) {
        packet.setContainerId(VarInts.readUnsignedInt(buffer));
        packet.setContents(helper.readArray(buffer, EMPTY, buf -> helper.readItem(buf, session)));
    }
}
