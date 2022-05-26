package one.qwq.fastfrpc;

import java.io.*;
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

            if (Config.core == Config.Core.FRP) {
                try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get("frpc.ini"), StandardCharsets.UTF_8)) {
                    bufferedWriter.write(
                            "[common]\n" +
                                    "server_addr = " + Config.serverAddr + "\n" +
                                    "server_port = " + Config.serverPort + "\n" +
                                    "protocol = " + Config.protocol.toString().toLowerCase() + "\n" +
                                    (!Config.useToken ? "" : "token = " + Config.token) + "\n" +
                                    "[" + ClientCommon.clientCommon.getPlayerName() + "]" + "\n" +
                                    "type = tcp" + "\n" +
                                    "local_port = " + Config.localPort + "\n" +
                                    "remote_port = " + Config.remotePort + "\n"
                    );
                    bufferedWriter.flush();
                }
                process = Runtime.getRuntime().exec("./" + Initialize.os.frpc + " -c frpc.ini");
            } else {
                try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get("npc.conf"), StandardCharsets.UTF_8)) {
                    bufferedWriter.write(
                            "[common]\n" +
                                    "server_addr=" + Config.serverAddr + ":" + Config.serverPort + "\n" +
                                    "conn_type=" + Config.protocol.toString().toLowerCase() + "\n" +
                                    (!Config.useToken ? "vkey=123" : "vkey=" + Config.token) + "\n" +
                                    "[" + ClientCommon.clientCommon.getPlayerName() + "]" + "\n" +
                                    "mode=tcp" + "\n" +
                                    "target_addr=127.0.0.1:" + Config.localPort + "\n" +
                                    "server_port=" + Config.remotePort + "\n"
                    );
                    bufferedWriter.flush();
                }
                process = Runtime.getRuntime().exec("./" + Initialize.os.npc + " -config=npc.conf");
            }

            InputStream inputStream = process.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String string;

            if (Config.core == Config.Core.FRP) {
                while (true) {
                    string = bufferedReader.readLine();
                    if (string == null) break;
                    ClientCommon.clientCommon.sendMsg(string.substring(24));
                }
            } else {
                while (true) {
                    string = bufferedReader.readLine();
                    if (string == null) break;
                    ClientCommon.clientCommon.sendMsg(string.substring(39));
                }
            }

            ClientCommon.clientCommon.logInfo("Reverse proxy stopped");
        } catch (Throwable throwable) {
            ClientCommon.clientCommon.logError("Failed to start: ", throwable);
        }
    }
}
