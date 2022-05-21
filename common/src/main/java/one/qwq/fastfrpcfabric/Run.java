package one.qwq.fastfrpcfabric;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Run implements Runnable {
    public static Process process;

    @Override
    public void run() {
        try {
            ClientCommon.clientCommon.sendMsg("使用 " + Config.serverAddr + ":" + Config.remotePort + " 连接到世界");
            ClientCommon.clientCommon.copyToClipboard(Config.serverAddr + ":" + Config.remotePort);
            ClientCommon.clientCommon.sendMsg("连接地址已自动复制到剪贴板");

            if (Config.engine == Core.FRP) {
                try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get("frpc.ini"), StandardCharsets.UTF_8)) {
                    bufferedWriter.write(
                            "[common]\n" +
                                    "server_addr = " + Config.serverAddr + "\n" +
                                    "server_port = " + Config.serverPort + "\n" +
                                    (!Config.useToken ? "" : "token = " + Config.token + "\n") +
                                    "[" + ClientCommon.clientCommon.getPlayerName() + "]" + "\n" +
                                    "type = tcp" + "\n" +
                                    "local_port = " + Config.localPort + "\n" +
                                    "remote_port = " + Config.remotePort + "\n"
                    );
                    bufferedWriter.flush();
                }
                process = Runtime.getRuntime().exec("frpc.exe -c frpc.ini");
            } else {
                ClientCommon.clientCommon.sendMsg("Coming soon");
                return;
            }

            InputStream inputStream;
            InputStreamReader inputStreamReader;
            BufferedReader bufferedReader;
            String string;

            inputStream = process.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(inputStreamReader);
            string = bufferedReader.readLine();

            while (string != null) {
                ClientCommon.clientCommon.sendMsg(string);
                string = bufferedReader.readLine();
            }

            ClientCommon.clientCommon.logInfo("Reverse proxy stopped");
        } catch (Throwable throwable) {
            ClientCommon.clientCommon.logError("Failed to start: ", throwable);
        }
    }
}
