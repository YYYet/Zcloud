# Zcloud

这是一个高移植性的私有云系统，开箱即用，无需额外配置。
目前规划如下：

1. 高效数据备份
2. 高效系统移植
3. 精确到ip的文件/文件夹权限访问控制



日前进度：

- 2022年8月23日

  - [x] 完成基于hutool的文件基本操作类封装

- 2022年8月24日

  - [x] 优化目录下文件初始化速度
  - [x] 初步完成MP4视频在线播放
  - [x] 初步完成视频在线分段播放
  - [ ] 视频播放相关代码待优化

- 2022年8月25日

  - [x] 调整项目结构

  - [x] 优化代码结构

  - [x] 加入本地缓存，准备缓存用作文件列表缓存

  - [x] 加入redis，用以缓存全局配置or文件列表，同时思考本地缓存是否需要剔除

  - [x] 加入bucket机制，目前的想法是以桶为区域划分存储块，通过将文件列表加入缓存以提高搜索效率

  - [x] 添加简单工具类

  - [ ] 计划在一层搜索时切入，将文件进入缓存

  - [ ] 计划文件变更时，将文件进入缓存

  - [ ] 计划定时扫描本地文件列表进入缓存

  - [ ] 计划以某种设计模式以串行方式执行一系列缓存策略，或许可以使用策略模式？

- 2022年8月27日

  - [x] 加入责任链模式处理文件

  - [x] 持久层接入sqlite、mybatisplus

  - [x] 部分代码优化
  
  - [ ] 采用flyway进行数据库版本管理

  

  

  

  
