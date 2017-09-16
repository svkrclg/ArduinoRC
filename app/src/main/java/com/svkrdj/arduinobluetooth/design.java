package com.svkrdj.arduinobluetooth;

/**
 * Created by skdj on 9/9/17.
 */

public class design {
        private String name;
        private String mac;

        public design(String Default, String miwok)
        {
            name=Default;
            mac=miwok;
        }

        public String getDefaultTranslation(){
            return name;
        }
        public String getMiwokTranslation(){
            return mac;
        }


}
