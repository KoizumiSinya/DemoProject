package jp.sinya.mvpdemo1.baiduhome;

import jp.sinya.mvpdemo1.bean.BaiduBean;

/**
 * @author Koizumi Sinya
 * @date 2017/02/20. 12:28
 * @edithor
 * @date
 */
public interface BaiduInfoBiz {

    public interface BaiduHttpCallBack {
        public void response(BaiduBean baiduBean);
    }

    public void requestData(BaiduHttpCallBack callBack);
}
