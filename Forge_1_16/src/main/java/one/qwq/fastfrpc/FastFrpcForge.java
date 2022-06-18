package one.qwq.fastfrpc;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("fastfrpcforge")
public class FastFrpcForge extends Common{
    public FastFrpcForge() {
        setCommon(this);
        new Initialize();

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> FastFrpcForge::registerConfig);
    }

    static void registerConfig() {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY,
                () -> (client, parent) -> ClothConfigBridge.create(parent));
    }

    private static final Logger LOGGER = LogManager.getLogger();

    private final Minecraft client = Minecraft.getInstance();

    @Override
    public void log(String info) {
        LOGGER.info(info);
    }

    @Override
    public void log(String error, Throwable throwable) {
        LOGGER.error(error, throwable);
    }

    @Override
    public void sendMsg(String msg) {
        client.ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(msg));
    }

    @Override
    public void copyToClipboard(String string) {

    }

    @Override
    public String getPlayerName() {
        return client.player.getName().getString();
    }
}
