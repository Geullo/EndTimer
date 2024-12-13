package com.geullo.gtimer.UI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Timer extends GuiScreen {
    private File timerFile = new File(Minecraft.getMinecraft().mcDataDir, "resources/GTimer/");
    private Image BGIMG;
    private int BGimgWidth,BGimgHeight,semi;
    private int[] pos = new int[2], minutePos = new int[4], secondPos = new int[4],timeSize = new int[2];
    private float[] u = new float[6],v=new float[3];
    public String hour,minute,second;

    private Minecraft mc = Minecraft.getMinecraft();
    ScaledResolution scaledresolution = new ScaledResolution(mc);
    public int WindowWidth = scaledresolution.getScaledWidth();
    public int WindowHeigth = scaledresolution.getScaledHeight();

    public Timer(){
        try {
            initSize();
        } catch (NullPointerException e ) {
            e.printStackTrace();
        }
    }
    public void initSize(){
        try {
            ScaledResolution scaledresolution = new ScaledResolution(mc);
            WindowWidth = scaledresolution.getScaledWidth();
            WindowHeigth = scaledresolution.getScaledHeight();
            this.BGIMG = ImageIO.read(new File(timerFile, "background.png"));
            BGimgWidth = (int) ((WindowWidth / 2)/2.48); // 1.8 3.98  3.55
            BGimgHeight = (int) ((WindowHeigth / 4)/1.85f); // 5.45
            pos[0] = 5;
            pos[1] = 5;
            if (pos[0] <= -1){
                pos[0] = 5;
            }
            if (pos[1] <= -1){
                pos[1] = 5;
            }
            timeSize[0] = BGimgWidth/2/3;
            timeSize[1] = (int) (BGimgHeight/1.95);

            minutePos[0] = (int) (pos[0]+(BGimgWidth/3.45));
            minutePos[1] = (int) (pos[1]+(BGimgHeight/2/2));
            minutePos[2] = (int) (minutePos[0]+(timeSize[0]/1.15f));
            minutePos[3] = minutePos[1];
            semi = (int) (minutePos[2]+(timeSize[0]/1.6));

            secondPos[0] = (int) (semi+(timeSize[0]/1.6f));
            secondPos[1] = minutePos[1];
            secondPos[2] = (int) (secondPos[0]+(timeSize[0]/1.15f));
            secondPos[3] = minutePos[1];
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //mc.currentScreen.width;
    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.VIGNETTE){
            initSize();
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            GlStateManager.translate(0,0,-2000.0);

            mc.renderEngine.bindTexture(new ResourceLocation("gtimer","background.png"));
            drawScaledCustomSizeModalRect(pos[0],pos[1], 0f,0, BGimgWidth*1,BGimgHeight*1,BGimgWidth,BGimgHeight,BGimgWidth,BGimgHeight);

            mc.renderEngine.bindTexture(new ResourceLocation("gtimer",get(minute,0)+".png"));
            drawScaledCustomSizeModalRect(minutePos[0], minutePos[1], 0f,0, timeSize[0] *1,timeSize[1]*1,timeSize[0],timeSize[1],timeSize[0],timeSize[1]);
            mc.renderEngine.bindTexture(new ResourceLocation("gtimer",get(minute,1)+".png"));
            drawScaledCustomSizeModalRect(minutePos[2], minutePos[3], 0f,0, timeSize[0] *1,timeSize[1]*1,timeSize[0],timeSize[1],timeSize[0],timeSize[1]);

            mc.renderEngine.bindTexture(new ResourceLocation("gtimer","semicolumn.png"));
            drawScaledCustomSizeModalRect(semi, secondPos[1], 0f,0, timeSize[0] *1,timeSize[1]*1,timeSize[0],timeSize[1],timeSize[0],timeSize[1]);

            mc.renderEngine.bindTexture(new ResourceLocation("gtimer",get(second,0)+".png"));
            drawScaledCustomSizeModalRect(secondPos[0], secondPos[1], 0f,0, timeSize[0] *1,timeSize[1]*1,timeSize[0],timeSize[1],timeSize[0],timeSize[1]);
            mc.renderEngine.bindTexture(new ResourceLocation("gtimer",get(second,1)+".png"));
            drawScaledCustomSizeModalRect(secondPos[2], secondPos[3], 0f,0, timeSize[0] *1,timeSize[1]*1,timeSize[0],timeSize[1],timeSize[0],timeSize[1]);


            GlStateManager.popMatrix();
        }
    }
    protected Integer get(String l,int k){
        return Integer.parseInt(l.split("")[k]);
    }
}
