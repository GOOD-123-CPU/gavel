# 安全策略

## 支持的版本

| 版本 | 支持状态 |
|------|----------|
| 2.x  | ✅ 持续维护 |
| 1.x（原毕设版） | ❌ 不再维护，存在已知漏洞，请勿部署 |

## 报告漏洞

如果你发现了安全漏洞，**请勿直接在 GitHub Issue 中公开描述细节**。

请通过以下方式私下报告：

1. 使用 GitHub 的 [Private vulnerability reporting](https://docs.github.com/en/code-security/security-advisories/guidance-on-reporting-and-writing-information-about-vulnerabilities/privately-reporting-a-security-vulnerability) 功能（仓库 Security 页签）
2. 或联系仓库维护者（见仓库主页）

我们会在 **72 小时内**确认收到，并在确认后尽快发布修复版本。

## 已知安全设计边界

- 默认账号密码仅用于本地演示，部署前必须修改
- CORS 默认仅允许 `localhost:8080`，生产环境需在 `application.yml` 的 `app.cors.allowed-origins` 配置真实域名
- 文件上传限制在白名单扩展名内，若需扩展请同步评估服务端风险
- 数据库演示数据均为虚构，不包含任何真实个人信息
