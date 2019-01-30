package jp.sinya.dagger2.demo.module;

import dagger.Module;
import dagger.Provides;
import jp.sinya.dagger2.demo.bean.A;

/**
 * @author Sinya
 * @date 2018/07/13. 13:19
 * @edithor
 * @date
 */
@Module
public class AModule {

    @Provides
    A provider() {
        return new A();
    }
}
