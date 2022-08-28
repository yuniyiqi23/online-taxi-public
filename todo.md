### 任务计划
- 查看司机信息日期
10. api-boss、api-driver注册到nacos
- 远程调用service-driver-user（drive-user增删改查）
11、绑定关系
12、司机登录（校验司机是否存在-->发送验证码，存redis-->校验验证码-->登录成功）
- 乘客、司机验证码标识不一样（相同手机号）

### 已完成任务
1. refrash token 刷新双token
2. 创建永久的token便于测试
3. 获取用户信息（头像等）
4. 预估价格 service-price
5. 地图服务 service-map
- 根据经纬度获取距离和时长（高德API）
6. 计价规则
- service-price数据库（3公里10元，车型）
- 数据库读取
- 计价规则不存在 1300-1399
- 价格计算（起步价+里程费+时长费）BigDecimal
- 封装BigDecimal计算方法
7. service-map
- 地区字典拉取，存储数据库
- 常量
- 性别： 0未知1男2女
8. api-boss、service-driver-user
9. 名族表、端口管理

A列|B列
---|---
111|222
222|333
