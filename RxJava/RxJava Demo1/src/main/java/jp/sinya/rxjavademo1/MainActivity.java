package jp.sinya.rxjavademo1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //transfor1_map();
        //transfor2_flatMap();
        //transfor3_groupBy();
        //transfor4_buffer();
        //transfor5_scan();

        //filter1_Debounce();
        //filter2_Distinst();
        //filter3_ElementAt();
        //filter4_Filter();
        //filter5_First();
        //filter5_Last();

        //combin1_zip();
        //combin2_merge();
        //combin3_startWith();
    }

    /**
     * 正常的创建rxjava 创建Observable 有10种操作符
     * ①create 一般的方式创建
     * ②just 可以直接发送传递
     * ③from 传递数组/集合类型
     * ④defer 延迟加载创建
     * ⑤empty/never/throw 创建空的、从不发送的、错误类型的
     * ⑥interval 有一定间隔时间的
     * ⑦Range 一定范围的
     * ⑧Repeat 重复的
     * ⑨start
     * ⑩timer 定时
     */
    private void create1() {

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        };

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }

        };

        subscriber.onStart();
        subscriber.unsubscribe();
        subscriber.isUnsubscribed();

        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("How are you");
                subscriber.onCompleted();
            }
        });

        //还有just的方法， 提供了快捷的创建事件队列 将传入的参数依次发送出来
        observable.just("How do you do", "How's it going", "Nice to see you");

        //将传入的数组或 Iterable 拆分成具体对象后，依次发送出来
        String[] result = {"Tom", "Jack", "Cindy"};
        Observable.from(result);

        //创建了 Observable 和 Observer 之后，再用 subscribe() 方法将它们联结起来，整条链子就可以工作了。
        observable.subscribe(observer);
        //或者
        //observable.subscribe(subscriber);
    }

    /**
     * create
     */
    private void create2() {

        //创建观察者
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i("Sinya", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("Sinya", "onError");
            }

            @Override
            public void onNext(String s) {
                Log.i("Sinya", "onNext:" + s);
            }
        };

        //创建被观察者
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello world");
                subscriber.onCompleted();
            }
        });

        //事件订阅
        observable.subscribe(subscriber);
    }

    /**
     * 使用create创建
     */
    private void create3() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("How are you");
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i("Sinya", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("Sinya", "onError");
            }

            @Override
            public void onNext(String s) {
                Log.i("Sinya", "onNext:" + s);
            }
        });
    }

    /**
     * 使用just创建
     */
    private void create4_just() {
        Observable.just("just创建，可以直接放里面传递数据").subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i("Sinya", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("Sinya", "onError");
            }

            @Override
            public void onNext(String s) {
                Log.i("Sinya", "onNext:" + s);
            }
        });
    }

    /**
     * 使用from创建，一般就是用于传递数组/集合类型的数据
     */
    private void create5_from() {
        Observable.from(new String[]{"AAA", "BBB", "CCC", "DDD", "EEE"}).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i("Sinya", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("Sinya", "onError");
            }

            @Override
            public void onNext(String s) {
                Log.i("Sinya", "onNext:" + s);
            }
        });
    }

    /**
     * 转换符
     * 例子中使用map把int类型的参数转换成string
     */
    private void transfor1_map() {
        Observable.just(123).map(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                return integer + "";
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i("Sinya", "transfor1_map-onCompleted:");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("Sinya", "transfor1_map-onError:");
            }

            @Override
            public void onNext(String s) {
                Log.i("Sinya", "transfor1_map-onNext:" + s);
            }
        });
    }

    /**
     *
     */
    private void transfor2_flatMap() {
        Observable.just(1, 2, 3, 4, 5).flatMap(new Func1<Integer, Observable<? extends String>>() {
            @Override
            public Observable<? extends String> call(Integer integer) {
                return Observable.just(integer + "");
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i("Sinya", "transfor2_map-onCompleted:");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("Sinya", "transfor2_map-onError:");
            }

            @Override
            public void onNext(String s) {
                Log.i("Sinya", "transfor2_map-onNext:" + s);
            }
        });
    }

    /**
     * 分组
     */
    private void transfor3_groupBy() {
        Observable.just(1, 2, 3, 4, 5).groupBy(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                return integer % 2;
            }
        }).subscribe(new Observer<GroupedObservable<Integer, Integer>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final GroupedObservable<Integer, Integer> observable) {
                observable.subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i("Sinya", "transfor3_groupBy-onNext: " + observable.getKey() + " - " + integer);
                    }
                });
            }
        });
    }

    /**
     * 设置范围 1~10的数值，然后按照2个为一组，发送输出
     */
    private void transfor4_buffer() {
        Observable.range(1, 10).buffer(2).subscribe(new Observer<List<Integer>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Integer> integers) {
                Log.i("Sinya", "transfor4_buffer-onNext: " + integers);
            }
        });
    }

    /**
     * scan可以用来累加发射
     */
    private void transfor5_scan() {
        Observable.range(1, 10).scan(new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer sum, Integer value) {
                return sum + value;
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.i("Sinya", "transfor5_scan-onNext: " + integer);
            }
        });
    }

    /**
     * 在操作的一定间隔内没有在操作，才会触发
     */
    private void filter1_Debounce() {

        //被观察者
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                try {
                    for (int i = 0; i < 10; i++) {
                        Thread.sleep(1000);
                        subscriber.onNext(i);
                    }
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        })//
                //过滤器
                .debounce(1, TimeUnit.SECONDS) //

                //观察者
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.i("Sinya", "filter1_Debounce onCompleted:");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Sinya", "filter1_Debounce onError:");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i("Sinya", "filter1_Debounce onNext:" + integer);
                    }
                });

    }

    /**
     * 去重
     */
    private void filter2_Distinst() {
        Observable.just(1, 2, 3, 4, 5, 7, 4, 4, 2, 9).distinct().subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Integer integer) {
                Log.i("Sinya", "filter2_Distinst onNext:" + integer);
            }
        });
    }

    /**
     * 去除指定位置的
     */
    private void filter3_ElementAt() {
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)//
                .elementAt(5).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.i("Sinya", "filter3_ElementAt onNext:" + integer);
            }
        });
    }

    /**
     * 提供过滤条件
     */
    private void filter4_Filter() {
        Observable.just(1, 2, 3, 3, 4, 4, 5, 6, 7, 8)//

                .distinct() //去重，可以多条件组合使用
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer < 5;
                    }
                }).subscribe(new Subscriber<Integer>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.i("Sinya", "filter4_Filter onNext:" + integer);
            }
        });
    }

    private void filter5_First() {
        Observable.just(9, 7, 5, 3, 1).distinct().first().subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.i("Sinya", "filter5_FirstLast onNext:" + integer);
            }
        });
    }

    private void filter5_Last() {
        Observable.just(9, 7, 5, 3, 1).distinct().last().subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.i("Sinya", "filter5_FirstLast onNext:" + integer);
            }
        });
    }

    /**
     * 忽略掉指定的元素之后就直接回调 onCompleted
     * 如果会发生错误就直接回调 onError
     */
    private void filter6_ignoreElements() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(132);
                throw new NullPointerException();
            }
        }).ignoreElements().subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.i("Sinya", "filter6_ignoreElements onCompleted:");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("Sinya", "filter6_ignoreElements onError:");
            }

            @Override
            public void onNext(Integer integer) {
                Log.i("Sinya", "filter6_ignoreElements onNext:" + integer);
            }
        });
    }

    /**
     * 根据规则组合发送
     * 注意：如果发送过程只要其中一个出现错误，另外一个也会停止发送
     */
    private void combin1_zip() {
        Observable<Integer> ob1 = Observable.just(1, 2, 3);
        Observable<Integer> ob2 = Observable.just(4, 5, 6);

        Observable.zip(ob1, ob2, //
                //定义这两个组合内的数据的规则
                new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer, Integer integer2) {
                        // 1 + 4
                        // 2 + 5
                        // 3 + 6
                        return integer + integer2;//这了的规则是 ob1中的数和ob2中的数值相加
                    }
                }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.i("Sinya", "combin1_zip onNext: " + integer);
            }
        });
    }

    /**
     * 按照时间的顺序组合起来发送
     */
    private void combin2_merge() {
        Observable<Integer> ob1 = Observable.just(1, 2, 3);
        Observable<Integer> ob2 = Observable.just(4, 5, 6);

        Observable.merge(ob1, ob2).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.i("Sinya", "combin2_merge onNext: " + integer);
            }
        });
    }

    /**
     * 在已有的数据之前插入另外一个数据
     */
    private void combin3_startWith() {
        Observable<Integer> ob1 = Observable.just(9, 8, 7);
        Observable<Integer> ob2 = Observable.just(4, 5, 6);

        ob1.startWith(ob2).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.i("Sinya", "combin2_merge onNext: " + integer);
            }
        });
    }

    /**
     * 邻近的组合 无这个方法？
     */
    private void combin4_comblieLatest() {

    }

    /**
     * 指定的结合一定时间内的数据（比较难理解）
     */
    private void combin5_join() {
        Observable<Integer> ob1 = Observable.just(1, 2, 3);
        Observable<Integer> ob2 = Observable.just(4, 5, 6);
        ob1.startWith(ob2).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.i("Sinya", "combin2_merge onNext: " + integer);
            }
        });
    }

    /**
     * 把细小的分支数据，在同一个时间点上的对象组成一个新的对象
     */
    private void combin6_switchOnNext() {

    }

    private void error1_catch() {

    }

    private void error2_retry() {

    }

    /**
     * 耗时的io操作
     */
    private void schedulers1_io() {

    }

    /**
     * 计算类型，例如 buffer、debounce、delay、interval、sample、skip
     */
    private void schedulers2_computation() {

    }

    /**
     * 立即在当前线程执行需要的操作
     */
    private void schedulers3_immediate() {

    }

    /**
     * 开启一个新的线程执行任务
     */
    private void schedulers4_newThread() {

    }

    /**
     * 按照处理队列的方式 顺序处理每一个任务
     * repeat  retry
     */
    private void schedulers5_trampoline() {

    }

    /**
     * 提供在安卓平台的调度器
     * 例如 .onbserveOn(AndroidScheduler.mainThread())
     */
    private void androidSchedulers1() {

    }


}
