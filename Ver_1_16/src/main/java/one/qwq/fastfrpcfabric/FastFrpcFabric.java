package one.qwq.fastfrpcfabric;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.SelectionManager;
import net.minecraft.text.LiteralText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FastFrpcFabric extends ClientCommon implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        setClientCommon(this);
        new Initialize();
    }

    private static final Logger LOGGER = LogManager.getLogger();

    private final MinecraftClient client = MinecraftClient.getInstance();

    @Override
    public void logInfo(String info) {
        LOGGER.info(info);
    }

    @Override
    public void logError(String error, Throwable throwable) {
        LOGGER.error(error, throwable);
    }

    @Override
    public void sendMsg(String msg) {
        client.inGameHud.getChatHud().addMessage(new LiteralText(msg));
    }

    @Override
    public void copyToClipboard(String string) {
        SelectionManager.setClipboard(client, string);
    }

    @Override
    public String getPlayerName() {
        return client.player.getName().getString();
    }
}