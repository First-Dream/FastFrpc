package one.qwq.fastfrpcfabric;

public abstract class ClientCommon {
    public static ClientCommon clientCommon;

    public void setClientCommon(ClientCommon clientCommon) {
        ClientCommon.clientCommon = clientCommon;
    }

    /**
     * 记录普通日志
     * @param info 日志
     */
    abstract void logInfo(String info);

    /**
     * 记录错误日志
     * @param error 日志
     * @param throwable 堆栈跟踪
     */
    abstract void logError(String error, Throwable throwable);

    /**
     * 在客户端显示消息
     * @param msg 消息
     */
    abstract void sendMsg(String msg);

    /**
     * 复制内容到剪贴板
     * @param string 内容
     */
    abstract void copyToClipboard(String string);

    /**
     * 获取客户端玩家名称
     * @return 客户端玩家名称
     */
    abstract String getPlayerName();
}
