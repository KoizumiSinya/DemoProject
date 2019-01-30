慕课网的直播demo小案例

推荐学习材料
1.mac辅助软件 VLC
2.直播源论坛： http://www.hdpfans.com/

1. 直播的相关协议
RTMP 组成: Message Type, Payload Length, Time Stamp, Stream ID, Message Body（消息会被拆分成 Chunk）
FLV 组成: 大规模并发，更适合手机移动
HLS 组成: 苹果公司推出的方法，分成5-10秒的小分片， 用m3u8索引表进行管理

2.直播播放流程

视频源 → MediaPlayer → 是否解码播放 → 画面渲染在SurfaceView

3.视频播放第三方框架 vitamio
https://www.vitamio.org/Download/


直播源：http://hefeng.live.tempsource.cjyun.org/videotmp/s10100-hftv.m3u8