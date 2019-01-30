package jp.sinya.mvpdemo1.baiduhome;

import jp.sinya.mvpdemo1.bean.BaiduBean;
import jp.sinya.mvpdemo1.http.BaiduNetImp;

/**
 * @author Koizumi Sinya
 * @date 2017/02/20. 12:20
 * @edithor
 * @date
 */
public class BaiduPresenter implements BaiduContract.Presenter {

    private BaiduContract.View view;
    private BaiduInfoBiz infoBiz;

    public BaiduPresenter(BaiduContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
        infoBiz = new BaiduNetImp();
    }

    @Override
    public void onStart() {
        requestData();
    }

    @Override
    public void requestData() {
        view.showLoading();
        infoBiz.requestData(new BaiduInfoBiz.BaiduHttpCallBack() {
            @Override
            public void response(BaiduBean baiduBean) {
                view.setResult(baiduBean.getHtmlCode());
                view.closeLoading();
            }
        });
    }
}
