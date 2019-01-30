package javaproject.wifidemo1.bean;

public enum CipherType {
        WIFI_CIPHER_NOPASS(0), WIFI_CIPHER_WEP(1), WIFI_CIPHER_WPA(2), WIFI_CIPHER_WPA2(3);

        private final int value;

        //构造器默认也只能是private, 从而保证构造函数只能在内部使用
        CipherType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }