package jp.sinya.dagger2.demo.module;

import dagger.Module;
import dagger.Provides;
import jp.sinya.dagger2.demo.bean.B;

/**
 * @author Sinya
 * @date 2018/07/13. 13:17
 * @edithor
 * @date
 */
@Module
public class BModule {
    @Provides
    B providerB() {
        return new B();
    }
}
