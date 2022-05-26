package one.qwq.fastfrpc;

public abstract class ClientCommon {
    public static ClientCommon clientCommon;

    public void setClientCommon(ClientCommon clientCommon) {
        ClientCommon.clientCommon = clientCommon;
    }

    /**
     * 记录普通日志
     * @param info 日志
     */
    public abstract void logInfo(String info);

    /**
     * 记录错误日志
     * @param error 日志
     * @param throwable 堆栈跟踪
     */
    public abstract void logError(String error, Throwable throwable);

    /**
     * 在客户端显示消息
     * @param msg 消息
     */
    public abstract void sendMsg(String msg);

    /**
     * 复制内容到剪贴板
     * @param string 内容
     */
    public abstract void copyToClipboard(String string);

    /**
     * 获取客户端玩家名称
     * @return 客户端玩家名称
     */
    public abstract String getPlayerName();
}
