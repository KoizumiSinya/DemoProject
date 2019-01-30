package jp.sinya.dagger2.demo.component;

import dagger.Component;
import jp.sinya.dagger2.demo.MainActivity;
import jp.sinya.dagger2.demo.module.A2Module;
import jp.sinya.dagger2.demo.module.AModule;
import jp.sinya.dagger2.demo.module.BModule;

/**
 * @author Sinya
 * @date 2018/07/13. 12:50
 * @edithor
 * @date
 */
@Component(modules = {AModule.class, A2Module.class, BModule.class})
public interface MainComponent {
    void inject(MainActivity activity);
}
