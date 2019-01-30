package jp.sinya.dagger2.demo.module;

import dagger.Module;
import dagger.Provides;
import jp.sinya.dagger2.demo.bean.A2;
import jp.sinya.dagger2.demo.bean.B;

/**
 * @author Sinya
 * @date 2018/07/13. 13:17
 * @edithor
 * @date
 */
@Module(includes = {BModule.class})
public class A2Module {
    @Provides
    A2 providerA2(B b) {
        return new A2(b);
    }

}
