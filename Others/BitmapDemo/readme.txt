慕课网 Bitmap缓存策略笔记

Bitmap有如下几种加载方式
1. BitmapFactory.decodeByteArray(); 字节数组
2. BitmapFactory.decodeFile(); 文件路径
3. BitmapFactory.decodeResource(); 资源id
4. BitmapFactory.decodeStream(); 流

Bitmap本身的构造函数是私有的，所以需要使用BitmapFactory来创建对象。

BitmapFactory.Options 重要属性之一
1.inJustDecodeBounds
2.outWidth 和 outHeight
3.inSampleSize

LruCache 内存缓存策略，由android提供
DiskLruCache  square团队开发的，实现硬盘缓存，google也已经支持
SQLite 实现数据库缓存（不推荐）


Lru算法：计算机科学中 一种近期最少使用算法
1.LruCache内部采用的是 LinkedHashMap来实现的
2.LruCache的出现是为了替代SoftReference

注意问题：
1.内存缓存策略始终还是使用手机中的内存，所以为了保证内存的开销，需要动态的去改变缓存大小。
2.使用v4包中的LruCache作为适配。


DiskLruCache 核心操作之一
1.使用DiskLruCache.open初始化一个缓存对象
2.DiskLruCache.get(String key) 获取对应key下的缓存数据
3.DiskLruCache.Editor 将数据保存到本地

注意问题
1.需要配置好缓存路径，放在安装的路径中
有外置SD /sdcard/Android/data/<application package>/cache
无外置SD /data/data/Android/data/<application package>/cache
2.缓存的key只能是用字母和数字的


