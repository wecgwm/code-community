## code-community
code-community是一个微服务社区系统。采用了Spring Cloud、Spring Cloud Alibaba、Spring Boot、spring security oauth2、Mybatis Plus等相关技术。目前项目提供了博客、答题、视频等功能。
## 模块结构
以下服务均依赖于nacos
| 服务分类|服务名                     |  先决条件            |   简介      |  端口               | 
|---------------------------|-------------------------|-------------|-------------------------|
| 认证服务|cc-auth                |                 |  基于oauth2、JWT   |  http://localhost:9002  | 
| 博客服务| cc-blog              | mysql、redis            |   |  http://localhost:8002  | 
| 评论服务|cc-comment      | mysql、redis、cc-user    | 目前主要是为博客提供评论功能   |  http://localhost:8010  | 
| 通用模块| cc-common     |     |      |  http://localhost:8001  |
| 网关    |cc-gateway     |                     |  基于Spring Cloud Gateway  | http://localhost:9001     |
| 文件服务| cc-oss               | mysql、minIO       | 头像、轮播图、视频的存取。可以基本使用。待完善     |  http://localhost:8011  |
| 答题服务|cc-test             | mysql、redis            |  可以基本使用。待完善    |  http://localhost:8003  |
| 用户服务|cc-user                     | mysql、redis、cc-auth     |      |  http://localhost:8001  |

## 预览
 ![1](http://wecgwm.gitee.io/image-bed/cc-1.png)
 ![2](http://wecgwm.gitee.io/image-bed/cc-2.png)
 ![3](http://wecgwm.gitee.io/image-bed/cc-3.png)