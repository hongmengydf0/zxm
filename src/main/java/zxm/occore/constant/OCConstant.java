package zxm.occore.constant;

public class OCConstant {

    public static final String SERVICE_ID = "Watermonitor";
    public static final String METHOD = "Control";

    public static final String APP_ID = "HYPZ07wBOzhxCaIkLr_qC1a7yWwa";
    public static final String SECRET = "BMtGxhS0ipDt5JYjCAFtPiA9dSca";
    public static final String IP = "139.159.133.59";
    public static final String PORT = "8743";

    public enum NotifyType {
        BIND_DEVICE("bindDevice"),//绑定设备
        DEVICE_ADDED("deviceAdded"),//添加新设备
        DEVICE_INFO_CHANGED("deviceInfoChanged"),//设备信息变化
        APP_DEAUTHORIZATION("appDeauthorization");//应用取消授权通知
        String name;

        NotifyType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
