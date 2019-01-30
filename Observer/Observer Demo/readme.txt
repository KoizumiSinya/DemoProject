观察者模式“定义对象间的一种一对多的依赖关系，当一个对象的状态发生改变的时候，所有依赖于它的对象都将得到通知，并自动更新”，可以理解成多个观察者向一个被观察者订阅消息，当被观察者发生改变时通知相应的观察者去作自己的事情。Android 中的观察者 实现Observer接口，被观察者继承Observable类。使用步骤如下：

   1.观察者DataWatcher 实现Observer 接口。
   2.被观察者DataChange继承Observable 类。
   3.观察者DataWatcher 向被观察者 DataChange 订阅事件（将观察者DataWatcher添加到被观察者DataChange里面去）。
   4.被观察者DataChange数据有改变，通知观察者DataWathcer相应的数据有改变，请作出相应的操作。
   5.观察者DataWather 向被观察者 DataChange取消订阅事件（将观察者DataWatcher从被观察者DataChange中移除）。