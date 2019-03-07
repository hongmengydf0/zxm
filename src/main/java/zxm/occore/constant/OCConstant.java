package zxm.occore.constant;

public class OCConstant {

    public static final String SERVICE_ID = "INFO";
    public static final String METHOD = "Control";

    public static final String APP_ID = "HYPZ07wBOzhxCaIkLr_qC1a7yWwa";
    public static final String SECRET = "BMtGxhS0ipDt5JYjCAFtPiA9dSca";
    public static final String IP = "139.159.133.59";
    public static final String PORT = "8743";

    public enum NotifyType {
        BIND_DEVICE("bindDevice"),//绑定设备
        DEVICE_ADDED("deviceAdded"),//添加新设备
        DEVICE_INFO_CHANGED("deviceInfoChanged"),//设备信息变化
        DEVICE_DATA_CHANGED("deviceDataChanged"),//设备数据变化
        DEVICE_DATAS_CHANGED("deviceDatasChanged"),//设备数据批量变化
        DEVICE_DELETED("deviceDeleted"),//删除设备
        MESSAGE_CONFIRM("messageConfirm"),//消息确认
        COMMAND_RSP("commandRsp"),//命令响应
        DEVICE_EVENT("deviceEvent"),//设备事件
        APP_DELETED("appDeleted"),//应用删除
        SERVICE_INFO_CHANGED("serviceInfoChanged"),//服务信息变化
        RULE_EVENT("ruleEvent"),//规则事件
        LOCATION_CHANGED("locationChanged"),//位置变化
        DEVICE_MODEL_ADDED("deviceModelAdded"),//添加设备模型
        DEVICE_MODEL_DELETED("deviceModelDeleted"),//删除设备模型
        VEHICLE_INFO_CHANGED("vehicleInfoChanged"),//车辆信息变更
        DEVICE_ALARM_CHANGED("deviceAlarmChanged"),//设备告警变更
        DEVICE_REALTIME_DATA_QUERY_STATUS_CHANGED("deviceRealTimeDataQueryStatusChanged"),//获取设备实时数据状态变更
        DEVICE_DESIRED_PROPERTIES_MODIFY_STATUS_CHANGED("deviceDesiredPropertiesModifyStatusChanged"),//设备影子状态变更
        APP_AUTHORIZATION("appAuthorization"),//应用授权通知
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
