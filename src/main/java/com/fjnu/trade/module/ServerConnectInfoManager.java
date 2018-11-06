package com.fjnu.trade.module;

import com.fjnu.common.Constants;
import org.apache.http.util.TextUtils;

import java.io.*;

/**
 * 服务器连接信息管理类
 * <p>
 * Created by LCC on 2018/4/3.
 */
public class ServerConnectInfoManager {

    public static final String USER_HOME_DIRECTORY = System.getProperty("user.home");
    public static final String LOCAL_FILE_PATH = USER_HOME_DIRECTORY + File.separator + "TradingSystemClient" + File.separator + "server_connect_info.config";

    /** 文件中 key 和 value 的分割符号 */
    private static final String PARAMETER_SEPARATOR = " : ";
    /** 文件中外网服务器地址的key值 */
    private static final String KEY_SERVER_WAN_ADDRESS = "key_server_wan_address";
    /** 文件中内网服务器地址的key值 */
    private static final String KEY_SERVER_LAN_ADDRESS = "key_server_lan_address";
    /** 文件中连接服务器方式的key值 */
    private static final String KEY_SERVER_CONNECT_WAY = "key_server_connect_way";

    private ServerConnectInfo serverConnectInfo;

    private ServerConnectInfoManager() {
        serverConnectInfo = getServerConnectInfoFromLocalFile();
    }

    public static ServerConnectInfoManager getInstance() {
        return InstanceHolder.mInstance;
    }

    /**
     * 从本地化配置文件中读取服务器连接信息
     *
     * @return 如果信息不正确，则返回 地址均为127.0.0.1:8080 的信息
     */
    public ServerConnectInfo getServerConnectInfoFromLocalFile() {
        ServerConnectInfo serverConnectInfo = new ServerConnectInfo();
        // 读取文件内容
        File file = new File(LOCAL_FILE_PATH);
        if (file.exists()) {
            try (
                    FileReader fileReader = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
            ) {
                String context;
                while ((context = bufferedReader.readLine()) != null) {
                    if (TextUtils.isEmpty(context)) {
                        continue;
                    }
                    String[] tempStr = context.split(PARAMETER_SEPARATOR);
                    if (tempStr.length < 2) {
                        continue;
                    }
                    if (KEY_SERVER_WAN_ADDRESS.equals(tempStr[0])) {
                        if (!"null".equals(tempStr[1])) {
                            serverConnectInfo.setServerWANAddress(tempStr[1]);
                        }
                        continue;
                    }
                    if (KEY_SERVER_LAN_ADDRESS.equals(tempStr[0])) {
                        if (!"null".equals(tempStr[1])) {
                            serverConnectInfo.setServerLANAddress(tempStr[1]);
                        }
                        continue;
                    }
                    if (KEY_SERVER_CONNECT_WAY.equals(tempStr[0])) {
                        ServerConnectWayEnum wayEnum = ServerConnectWayEnum.fromValue(tempStr[1]);
                        serverConnectInfo.setServerConnectWay(wayEnum);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //判断数据是否正确
        if (!serverConnectInfo.checkInfoIsCorrect()) {
            serverConnectInfo.setServerLANAddress(Constants.SERVER_DEFAULT_IP);
            serverConnectInfo.setServerWANAddress(Constants.SERVER_DEFAULT_IP);
        }
        return serverConnectInfo;
    }

    /**
     * 将IP写入本地配置文件中
     *
     * @param serverConnectInfo
     * @return
     */
    public boolean writeServerConnectInfoIntoLocalFile(ServerConnectInfo serverConnectInfo) {
        if (serverConnectInfo == null) {
            return false;
        }
        System.out.println("writeServerConnectInfoIntoLocalFile(" + serverConnectInfo.toString() + ")");

        File file = new File(LOCAL_FILE_PATH);
        file.getParentFile().mkdirs();
        try (
                FileWriter fileWriter = new FileWriter(LOCAL_FILE_PATH);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)
        ) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(KEY_SERVER_CONNECT_WAY);
            stringBuilder.append(PARAMETER_SEPARATOR);
            stringBuilder.append(serverConnectInfo.getServerConnectWay().getValue());
            stringBuilder.append("\r\n");
            stringBuilder.append(KEY_SERVER_LAN_ADDRESS);
            stringBuilder.append(PARAMETER_SEPARATOR);
            stringBuilder.append(serverConnectInfo.getServerLANAddress());
            stringBuilder.append("\r\n");
            stringBuilder.append(KEY_SERVER_WAN_ADDRESS);
            stringBuilder.append(PARAMETER_SEPARATOR);
            stringBuilder.append(serverConnectInfo.getServerWANAddress());
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        this.serverConnectInfo = serverConnectInfo;
        return true;
    }

    /**
     * @param ip
     * @return true-正确 / false-错误
     */
    public static boolean checkIpFormat(String ip) {
        if (TextUtils.isEmpty(ip)) {
            return false;
        }
        String[] tempStr = ip.trim().split("\\.");
        if (tempStr.length != 4) {
            return false;
        }
        for (int i = 0; i < 4; i++) {
            int value = Integer.valueOf(tempStr[i]);
            if (value < 0 || value > 256) {
                return false;
            }
        }
        return true;
    }

    public ServerConnectInfo getServerConnectInfo() {
        return serverConnectInfo;
    }

    private static final class InstanceHolder {
        private static final ServerConnectInfoManager mInstance = new ServerConnectInfoManager();
    }

    public static class ServerConnectInfo {
        private String serverWANAddress;
        private String serverLANAddress;
        private ServerConnectWayEnum serverConnectWay = ServerConnectWayEnum.LAN_WAY;

        /**
         * 检查信息是否正确
         *
         * @return ture-正确 / false-错误
         */
        public boolean checkInfoIsCorrect() {
            switch (serverConnectWay) {
                case LAN_WAY:
                    if (TextUtils.isEmpty(serverLANAddress)) {
                        return false;
                    }
                    break;
                case WAN_WAY:
                    if (TextUtils.isEmpty(serverWANAddress)) {
                        return false;
                    }
                    break;
                default:
                    return false;
            }
            return true;
        }

        public String getServerWANAddress() {
            return serverWANAddress;
        }

        public void setServerWANAddress(String serverWANAddress) {
            this.serverWANAddress = serverWANAddress;
        }

        public String getServerLANAddress() {
            return serverLANAddress;
        }

        public void setServerLANAddress(String serverLANAddress) {
            this.serverLANAddress = serverLANAddress;
        }

        public ServerConnectWayEnum getServerConnectWay() {
            return serverConnectWay;
        }

        public void setServerConnectWay(ServerConnectWayEnum serverConnectWay) {
            this.serverConnectWay = serverConnectWay;
        }

        @Override
        public String toString() {
            return "ServerConnectInfo{" +
                    "serverWANAddress='" + serverWANAddress + '\'' +
                    ", serverLANAddress='" + serverLANAddress + '\'' +
                    ", serverConnectWay=" + serverConnectWay +
                    '}';
        }
    }

    public enum ServerConnectWayEnum {
        /**
         * 外网
         */
        WAN_WAY("WAN"),
        /**
         * 内网
         */
        LAN_WAY("LAN");

        private String value;

        ServerConnectWayEnum(String value) {
            this.value = value;
        }

        public static ServerConnectWayEnum fromValue(String value) {
            for (ServerConnectWayEnum wayEnum : ServerConnectWayEnum.values()) {
                if (wayEnum.getValue().equals(value)) {
                    return wayEnum;
                }
            }
            return null;
        }

        public String getValue() {
            return value;
        }
    }

}
