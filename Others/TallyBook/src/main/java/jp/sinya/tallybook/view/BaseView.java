package jp.sinya.tallybook.view;

import java.util.List;

import jp.sinya.tallybook.model.BaseBean;

/**
 * @author Koizumi Sinya
 * @date 2018/01/02. 23:14
 * @edithor
 * @date
 */
public interface BaseView<T extends BaseBean> {
    void update(List<T> dataList);
}
