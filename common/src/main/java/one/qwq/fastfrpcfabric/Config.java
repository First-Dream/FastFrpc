package one.qwq.fastfrpcfabric;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Random;

public class Config {
    /**
     * 核心类型
     */
    public static Core engine = Core.FRP;

    /**
     * 传输协议
     */
    public static Protocol protocol = Protocol.TCP;

    /**
     * 服务地址
     */
    public static String serverAddr = "sz.qwq.one";

    /**
     * 服务端口
     */
    public static int serverPort = 8000;

    /**
     * 本地端口
     */
    public static int localPort = 25565;

    /**
     * 远程端口
     */
    public static int remotePort = new Random().nextInt(55296) + 10240;

    /**
     * 需要密码
     */
    public static boolean useToken = false;

    /**
     * 密码
     */
    public static String token = "";

    private static final Path path = Paths.get("config", "fastfrpc.properties");

    static {
        try (BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            Properties properties = new Properties();
            properties.load(bufferedReader);

            Config.engine = Core.valueOf((properties.getProperty("engine")).toUpperCase());
            Config.protocol = Protocol.valueOf(properties.getProperty("protocol").toUpperCase());
            Config.serverAddr = properties.getProperty("server_addr");
            Config.serverPort = Integer.parseInt(properties.getProperty("server_port"));
//            FrpcConfig.localPort = Integer.parseInt(properties.getProperty("local_port"));
            Config.remotePort = Integer.parseInt(properties.getProperty("remote_port"));
            Config.useToken = Boolean.parseBoolean(properties.getProperty("use_token"));
            Config.token = properties.getProperty("token");
        } catch (Exception exception) {
            save();
        }
    }

    static void save() {
        Properties properties = new Properties();
        properties.put("engine", String.valueOf(Config.engine));
        properties.put("protocol", String.valueOf(Config.protocol));
        properties.put("server_addr", String.valueOf(Config.serverAddr));
        properties.put("server_port", String.valueOf(Config.serverPort));
//        properties.put("local_port", String.valueOf(FrpcConfig.localPort));
        properties.put("remote_port", String.valueOf(Config.remotePort));
        properties.put("use_token", String.valueOf(Config.useToken));
        properties.put("token", String.valueOf(Config.token));

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            properties.store(bufferedWriter, "FastFrpc Config File");
        } catch (Exception exception) {
//            Logger.error("Failed to write config file: ", exception);
//            FastFrpcFabric.LOGGER.error("Failed to write config file: ", exception);
        }
    }
}

enum Core {
    FRP,
    NPS,
}

enum Protocol {
    TCP,
    KCP,
}