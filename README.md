<div align="middle">
    <img alt="AoE Logo" src="./images/aoe_logo_01.png" width="300" align="middle">
</div>

[![Build Status](https://travis-ci.org/didi/AoE.svg?branch=master)](https://travis-ci.org/didi/AoE)
[![Android](https://api.bintray.com/packages/aoe/maven/library-core/images/download.svg) ](https://bintray.com/aoe/maven/library-core/_latestVersion)
[![CocoaPods Compatible](https://img.shields.io/cocoapods/v/AoE.svg)](https://cocoapods.org/pods/AoE)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/didi/aoe/blob/master/LICENSE)

 [文档](https://didi.github.io/AoE/) |
 [发布记录](./CHANGELOG.md) |
 [路线图](./ROADMAP.md) |
 [English](./README_en.md)

## 一、背景
### 1. AoE 是什么?
**AoE** (AI on Edge，终端智能，边缘计算) 是一个滴滴开源的 **终端侧 AI 集成运行时环境 ( IRE )**。以 **“稳定性、易用性、安全性”** 为设计原则，帮助开发者将不同框架的深度学习算法轻松部署到终端高效执行。

为什么要做一个终端 AI 集成运行时框架：

* **框架多样性**，随着人工智能技术快速发展，这两年涌现出了许多运行在终端的推理框架，一方面给开发者带来更多选择，另外一方面也增加了将 AI 布署到终端的成本。
* **流程繁琐**，通过推理框架直接接入 AI 的流程比较繁琐，涉及到动态库接入、资源加载、前处理、后处理、资源释放、模型升级，以及如何保障稳定性等问题。

### 2. AoE支持哪些平台
目前，AoE 提供了 Android 和 iOS 的实现，Linux 平台运行时环境 SDK 正在紧锣密鼓地开发中，预计在 9 月底发布，方便智能终端设备上落地 AI 业务。

## 二、使用文档&示例
- [Android用户指南](./Android/README.md)
- [iOS用户指南](./iOS/README.md)
- [Android Demo](./Android/demo)
- [iOS Demo](./iOS/Demo)
- [更多内容请移步 aoe-open](https://github.com/aoe-open)

| MNIST 手写数字识别 | SqueezeNet 物体识别 |
|---|---|
|  <img alt="MNIST" src="./images/mnist_android.jpeg" width="196"> |<img alt="Squeeze" src="./images/squeeze_android.jpeg" width="196">|

## 三、Q&A

* `欢迎直接提交 issues 和 PRs`  [>>>🔥PR激励活动进行中～](https://github.com/didi/AoE/issues/14)


| QQ群号： 815254379 | 加微信入群：xKuloud（备注*AoE*） |
|---|---|
|  <img alt="AoE QQ交流群" src="./images/aoe_qq.jpeg" width="196"> |<img alt="xKuloud" src="https://img0.didiglobal.com/static/gstar/img/eSO6tSjbsB1568173760427.jpeg" width="196">|
    


## 四、项目成员
### 核心成员

[kuloud](https://github.com/Kuloud)、
[dingc](https://github.com/qtdc1229) 、
[coleman.zou](https://github.com/zouyuefu) 、
[yangke1120](https://github.com/yangke1120) 、
[tangjiaxu](https://github.com/shupiankuaile) 

## 五、友情链接
1. [Dokit](https://github.com/didi/DoraemonKit)，一款功能齐全的客户端（ iOS 、Android ）研发助手，你值得拥有 :)
2. 普惠出行产品技术公众号，欢迎关注。<img alt="普惠出行产品技术公众号" src="https://img0.didiglobal.com/static/gstar/img/NlLuFeiqKU1570690897784.jpg" width="196">
