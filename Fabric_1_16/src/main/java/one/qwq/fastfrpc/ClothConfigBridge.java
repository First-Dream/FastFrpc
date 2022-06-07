package one.qwq.fastfrpc;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;

import java.util.Random;

public class ClothConfigBridge implements ConfigScreenFactory<Screen> {
    @Override
    public Screen create(Screen parent) {
        ConfigBuilder config = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(new LiteralText("内网穿透设置"))
                .setSavingRunnable(Config::save);
        ConfigEntryBuilder entry = config.entryBuilder();
        config.getOrCreateCategory(new LiteralText("内网穿透设置"))
                .addEntry(entry.startEnumSelector(new LiteralText("核心类型"), Config.Core.class, Config.core).setDefaultValue(Config.Core.FRP).setSaveConsumer(value -> Config.core = value).build())
                .addEntry(entry.startEnumSelector(new LiteralText("传输协议"), Config.Protocol.class, Config.protocol).setDefaultValue(Config.Protocol.TCP).setSaveConsumer(value -> Config.protocol = value).build())
                .addEntry(entry.startStrField(new LiteralText("服务地址"), Config.serverAddr).setDefaultValue("sz.qwq.one").setSaveConsumer(value -> Config.serverAddr = value).build())
                .addEntry(entry.startIntField(new LiteralText("服务端口"), Config.serverPort).setDefaultValue(Config.core == Config.Core.FRP ? 8000 : 9024).setSaveConsumer(value -> Config.serverPort = value).build())
                .addEntry(entry.startIntField(new LiteralText("远程端口"), Config.remotePort).setDefaultValue(new Random().nextInt(55296) + 10240).setSaveConsumer(value -> Config.remotePort = value).build())
                .addEntry(entry.startBooleanToggle(new LiteralText("需要密码"), Config.useToken).setDefaultValue(Config.core != Config.Core.FRP).setSaveConsumer(value -> Config.useToken = value).build())
                .addEntry(entry.startStrField(new LiteralText("密码"), Config.token).setDefaultValue(Config.core == Config.Core.FRP ? "" : "123").setSaveConsumer(value -> Config.token = value).build());
        return config.build();
    }
}