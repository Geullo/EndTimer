package com.geullo.gtimer;

import com.geullo.gtimer.UI.Timer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

public class Tmr {
    public Minecraft mc = null;

    private static Tmr instance;
    private static Timer timerInstance;

    public static Tmr getInstance(){
        if(instance == null){
            instance = new Tmr();
        }
        return instance;
    }
    private Tmr() {
        mc = Minecraft.getMinecraft();
    }

    //Packet Handle
    public void handleMessage(PacketMessage message) {
        if (message.data.equals("stoptimer")){
            if (timerInstance!=null) {
                MinecraftForge.EVENT_BUS.unregister(timerInstance);
                timerInstance = null;
            }
        }
        else if (message.data.equals("showtimer")){
            if (timerInstance==null) {timerInstance = new Timer();
                MinecraftForge.EVENT_BUS.register(timerInstance);}
        }
        else if (message.data.contains(":")){
            if (timerInstance!=null){
                String[] time = message.data.split(":");
                timerInstance.minute = time[0];
                timerInstance.second = time[1];
            }
        }
        return;
    }
}
