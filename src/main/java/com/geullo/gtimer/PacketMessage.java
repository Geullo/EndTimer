package com.geullo.gtimer;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketMessage implements IMessage {

    public String data;

    public PacketMessage(){}

    public PacketMessage(String data){ this.data = data;}

    @Override
    public void fromBytes(ByteBuf buf) { data = ByteBufUtils.readUTF8String(buf);}

    @Override
    public void toBytes(ByteBuf buf) {ByteBufUtils.writeUTF8String(buf, data);}

    public static class Handle implements IMessageHandler<PacketMessage, IMessage>{
        @Override
        public IMessage onMessage(PacketMessage message, MessageContext ctx) {
            try {
                Tmr.getInstance().handleMessage(message);
            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
