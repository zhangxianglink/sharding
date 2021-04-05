##  mysql 主从复制搭建

```sh
# 安装
$ rpm -ivh http://repo.mysql.com/yum/mysql-5.7-community/el/7/x86_64/mysql57-community-release-el7-10.noarch.rpm 
$  yum install mysql-community-server -y
# 系统启动
$ systemctl start mysqld
$ systemctl status mysqld
# 连接客户端
$ grep "password" /var/log/mysqld.log 或者下面方式
$ mysql -uroot -p$(awk '/temporary password/{print $NF}' /var/log/mysqld.log )
# 修改密码
$ set global validate_password_policy=0;
$ set password  for root@localhost = password('shard@#WE23we');
# 开放账户的权限
$ grant all on *.* to root@'%' identified by 'shard@#WE23we';
$ flush privileges;
# 再次连接
$ mysql -uroot -pshard@#WE23we
```

**master**

```sh
vim /etc/my.cnf
#在mysqld标签下配置
[mysqld]
#主库server-id为1，从库不等于1
server_id=11
#开启binlog日志
log_bin=mysql-bin
# 过滤不需要复制的数据库
binlog-ignore-db=mysql
# 为每个session分配内存，事务过程中用来存储而二进制日志的缓存
binlog_cache_size=1M
# 主从复制的格式
binlog_format=mixed
```

**slave**

```sh
vim /etc/my.cnf
#在mysqld标签下配置
[mysqld]
#主库server-id为1，从库不等于1
server_id=12
#开启binlog日志
log_bin=mysql-slave-bin
#中继日志
relay_log=edu-mysql-relay-bin
# 过滤不需要复制的数据库
binlog-ignore-db=mysql
# 如果需要去同步存储过程，和函数
log_bin_trust_function_creators=true
# 为每个session分配内存，事务过程中用来存储而二进制日志的缓存
binlog_cache_size=1M
# 主从复制的格式
binlog_format=mixed
# 跳过一些错误，避免同步失败
slave_skip_errors=1062
```

**重启上述文件，登陆进入客户端**

```shell
# master 授权 slave 服务器
$ GRANT REPLICATION SLAVE, REPLICATION CLIENT ON *.*  TO  'root'@'slaveIP'  identified by 'shard@#WE23we';
$ flush privileges;
$ select user,host from mysql.user;
$ show master status;
# slave 节点操作
$ change master to master_host='masterIP', master_user='root', master_password='shard@#WE23we', master_port=3306, master_log_file='mysql-bin.000002',master_log_pos=1449;
$ start slave;
```

**检查**

```shell
# slave 
$ show slave status\G;
   # Slave_IO_Running: Yes
   # Slave_SQL_Running: Yes  
两个yes 代表配置成功。
# master
$ CREATE DATABASE jacklove DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
$ mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| jacklove           |
| mysql              |
| performance_schema |
| sys                |
如上所示，去slave看一下。
# PS（TMD，mysql密码一定要配置复杂一点！！！）
```

**关闭**

```shell
# 主数据库
$ reset master;
# 从数据库
$ stop slave;
$ reset slave all;
```

