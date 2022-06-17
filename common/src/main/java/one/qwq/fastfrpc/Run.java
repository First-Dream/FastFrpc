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
            Common.common.sendMsg("使用 " + Config.serverAddr + ":" + Config.remotePort + " 连接到世界");
            Common.common.copyToClipboard(Config.serverAddr + ":" + Config.remotePort);
            Common.common.sendMsg("连接地址已自动复制到剪贴板");

            if (Config.core == Config.Core.FRP) {
                try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get("frpc.ini"), StandardCharsets.UTF_8)) {
                    bufferedWriter.write(
                            "[common]\n" +
                                    "server_addr = " + Config.serverAddr + "\n" +
                                    "server_port = " + Config.serverPort + "\n" +
                                    "protocol = " + Config.protocol.toString().toLowerCase() + "\n" +
                                    (!Config.useToken ? "" : "token = " + Config.token) + "\n" +
                                    "[" + Common.common.getPlayerName() + "]" + "\n" +
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
                                    "[" + Common.common.getPlayerName() + "]" + "\n" +
                                    "mode=tcp" + "\n" +
                                    "target_addr=127.0.0.1:" + Config.localPort + "\n" +
                                    "server_port=" + Config.remotePort + "\n"
                    );
                    bufferedWriter.flush();
                }
                process = Runtime.getRuntime().exec("./" + Initialize.os.npc + " -config=npc.conf");
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            String string;
            if (Config.core == Config.Core.FRP) {
                while (true) {
                    string = bufferedReader.readLine();
                    if (string == null) break;
                    Common.common.sendMsg(string.substring(24));
                }
            } else {
                while (true) {
                    string = bufferedReader.readLine();
                    if (string == null) break;
                    Common.common.sendMsg(string.substring(39));
                }
            }

            Common.common.log("Reverse proxy stopped");
        } catch (Throwable throwable) {
            Common.common.log("Failed to start: ", throwable);
            Common.common.sendMsg("内网穿透启动失败，请向开发者提交日志");
        }
    }
}
