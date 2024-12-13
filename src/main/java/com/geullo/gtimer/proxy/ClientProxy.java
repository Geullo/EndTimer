package com.geullo.gtimer.proxy;

import com.geullo.gtimer.PacketMessage;
import com.geullo.gtimer.util.ModResourcePack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class ClientProxy extends CommonProxy{
    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel("gtimer");

    @Override
    public void preInit(File configFile) throws IOException {
        File modFolder = new File(Minecraft.getMinecraft().mcDataDir, "resources/GTimer");
        modFolder.mkdirs();
        String[] textureArr = {
            "background.png","0.png","1.png","2.png","3.png","4.png","5.png","6.png","7.png","8.png","9.png","semicolumn.png"
        };
        for (int i = 0; i < textureArr.length; i++) {
            if (new File(modFolder, textureArr[i]).exists()){
                Files.delete(new File(modFolder, textureArr[i]).toPath());}
            if (!new File(modFolder, textureArr[i]).exists()) {
                Files.copy(getClass().getResourceAsStream("/assets/gtimer/" + textureArr[i]), new File(modFolder, textureArr[i]).toPath());
            }
        }
    }
    @Override
    public void init() {
        List<IResourcePack> defaultResourcePacks = ObfuscationReflectionHelper.getPrivateValue(FMLClientHandler.class, FMLClientHandler.instance(), "resourcePackList");
        IResourcePack pack = new ModResourcePack(new File(Minecraft.getMinecraft().mcDataDir, "resources/CLUE"));
        defaultResourcePacks.add(pack);

        ((SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).reloadResourcePack(pack);
        NETWORK.registerMessage(PacketMessage.Handle.class, PacketMessage.class, 0 , Side.CLIENT);
    }

    @Override
    public void postInit() {

    }

}
