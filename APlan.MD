学习路径
1. 先学习如何使用（下载官网binary包，运行并写各种demo；模仿官方example代码内容）
2. 学习概念
   * 经典论文-[The Log: What every software engineer should know about real-time data's unifying abstraction](https://engineering.linkedin.com/distributed-systems/log-what-every-software-engineer-should-know-about-real-time-datas-unifying)
   * [官网](https://kafka.apache.org)
   * [深入理解Kafka：核心设计与实践原理](https://book.douban.com/subject/30437872/)
   * Kafka权威指南等

3. 源码环境准备
   * 外部系统：zk直接从[zk官网](https://zookeeper.apache.org)下载运行，kafka学习期不考虑，在根目录运行` .\bin\zkServer.cmd`
   * 代码准备：github上fork官网代码到个人仓库，本次使用**2.8**分支，之前用3.0构建后，写入日志有AccessDeniedException，该版本NIO在windows系统上有bug
   * 环境准备：本地jdk1.8, gradle6.8.1, scala2.13.6, idea并安装scala插件
   * dependencies.gradle中修改
     * scala版本修改： scala我本地之前安装的2.13.6，所以在`dependencies.gradle`中修改`def defaultScala213Version = '2.13.6'`，同时修改默认版本为2.13版本`versions["scala"] = defaultScala213Version`
     * grgit版本报错，修改4.1.0为4.0.2
     * zookeeper版本，因为我此时下载的官网最新版为3.7.0，同样也在dependencies.gradle中修改zookeeper版本
   * server.properties中
     * 修改本地日志路径`log.dirs`
   * build.gradle修改
     * 注释掉对log4j的exclude `    compile(libs.zookeeper) {
       //      exclude module: 'slf4j-log4j12'
       //      exclude module: 'log4j'
       }`
   * idea运行
     * 在项目根目录运行`gradle idea`,构建很慢，成功后，导入idea
     * `core`包中为服务端代码，直接运行kafka.scala会报错，提示需要设置server.properties
     * 所以在idea中点击`edit configuration`修改`program arguments`中加入`config/server.properties`
     * 在`config`包中拷贝`log4j.properties`到`core`包中`src/main/resources`目录下
     * 修改后运行成功
     * 可能会报cluster id不同的错误，去server.properties中设置的日志路径下清空所有文件并重启kafka即可

4. producer源码-从消息的生产开始

    * 重点
      * kafka客户端如何去设计一个优秀的生产级别的保证高吞吐的缓冲机制
      * 生产端的sender线程池的网络通信的模块

5. server端
   * 网络通信架构
   * 磁盘读写-写入磁盘，存储结构，page cache的使用，怎么实现顺序写
   * 多副本-冗余及高可用架构设计，数据同步，副本如何传输，offset变更，主从切换
   * broker选举，切换，故障恢复，集群元数据管理
   * 负载均衡以及伸缩架构，数据均匀分布，topic partition扩容，如何横向伸缩

6. consumer端

    * 消费组主控节点如何选择， 协调者如何选择， group leader选择，分区分配的方法，分布式消费机制，拉取消息的原理，offset提交原理
   