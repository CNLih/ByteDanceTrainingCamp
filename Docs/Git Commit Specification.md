### Git 提交规范

格式：<type>(<scope>): <subject> 

**type**包括：

- sync：同步主线或分支的bug。
- merge：代码合并。
- revert：回滚到上一个版本。
- chore：构建过程或辅助工具的变动。
- test：增加测试。
- perf：优化相关，比如提升性能、体验。
- refactor：重构（既不是新增功能，也不是修改bug的代码变动）。
- style：格式（不影响代码运行的变动）。
- docs：文档（documentation）。
- fix / to：修复bug，可以是QA(Quality Assurance)发现的bug，也可以是研发自己发现的bug。
- feat：新功能（feature）。

**scope**：影响范围，具体到包即可。

**subject**：具体修改内容



举例：

>  feat(service) :添加账户注册功能



说明：

​		Git 提交的粒度要足够小（原则上每次 commit 不超过 200 行，要求在 Pull Request 中看到的每个commit 都是细粒度的，比如每完成一个功能或修复一个 bug 尽量都进行提交。