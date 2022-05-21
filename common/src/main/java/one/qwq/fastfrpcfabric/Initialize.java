package one.qwq.fastfrpcfabric;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class Initialize {

    public static OS os;

    public Initialize() {
        try {
            String osName = System.getProperty("os.name");
            ClientCommon.clientCommon.logInfo("System: " + osName);
            osName = osName.toLowerCase();
            if (osName.contains("linux")) {
                os = OS.LINUX;
            } else if (osName.contains("mac")) {
                os = OS.MACOS;
            } else if (osName.contains("windows")) {
                os = OS.WINDOWS;
            }
        } catch (Throwable throwable) {
            ClientCommon.clientCommon.logError("Failed to get system: ", throwable);
        }

        try {
            if (!new File(os.frpc).exists()) {
                ClientCommon.clientCommon.logInfo("Extracting " + os.frpc);
                BufferedInputStream bufferedInputStream;
                BufferedOutputStream bufferedOutputStream;
                bufferedInputStream = new BufferedInputStream(Initialize.class.getClassLoader().getResourceAsStream(os.frpc));
                bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(os.frpc));
                byte[] bytes = new byte[4096];
                while (bufferedInputStream.read(bytes) != -1) {
                    bufferedOutputStream.write(bytes);
                }
                bufferedOutputStream.flush();
                bufferedOutputStream.close();

                ClientCommon.clientCommon.logInfo("Extracted" + os.frpc);
            }
        } catch (Throwable throwable) {
            ClientCommon.clientCommon.logError("Failed to extract " + os.frpc + ": ", throwable);
        }

        try {
            if (!new File(os.npc).exists()) {
                ClientCommon.clientCommon.logInfo("Extracting " + os.npc);
                BufferedInputStream bufferedInputStream;
                BufferedOutputStream bufferedOutputStream;
                bufferedInputStream = new BufferedInputStream(Initialize.class.getClassLoader().getResourceAsStream(os.npc));
                bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(os.npc));
                byte[] bytes = new byte[4096];
                while (bufferedInputStream.read(bytes) != -1) {
                    bufferedOutputStream.write(bytes);
                }
                bufferedOutputStream.flush();
                bufferedOutputStream.close();

                ClientCommon.clientCommon.logInfo("Extracted" + os.npc);
            }
        } catch (Throwable throwable) {
            ClientCommon.clientCommon.logError("Failed to extract " + os.npc + ": ", throwable);
        }
    }
}

enum OS {
    LINUX("frpc_linux", "npc_linux"),
    MACOS("frpc_mac", "npc_mac"),
    WINDOWS("frpc.exe", "npc.exe"),
    ;

    final String frpc;
    final String npc;

    OS(String frpc, String npc) {
        this.frpc = frpc;
        this.npc = npc;
    }
}