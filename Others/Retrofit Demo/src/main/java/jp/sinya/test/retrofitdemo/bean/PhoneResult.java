package jp.sinya.test.retrofitdemo.bean;

/**
 * @author KoizumiSinya
 * @date 2016/6/29.
 */
public class PhoneResult extends BaseResult {
    public RetPhoneEntity result;

    public static class RetPhoneEntity {
        public String province;
        public String city;
        public String company;
        public String cardtype;

        @Override
        public String toString() {
            return "RetPhoneEntity{" +
                    "province='" + province + '\'' +
                    ", city='" + city + '\'' +
                    ", company='" + company + '\'' +
                    ", cardtype='" + cardtype + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return super.toString() + "PhoneResult{" +
                "result=" + result +
                '}';
    }
}
