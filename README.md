[![Azure WebApp](https://img.shields.io/badge/Azure_WebApp-app--mrs-blue)](http://app-mrs.azurewebsites.net/)
[![Azure DevOps](https://img.shields.io/badge/AzureDevOps-Mrs-blue)](https://dev.azure.com/k2works/Mrs)
[![Build Status](https://dev.azure.com/k2works/Mrs/_apis/build/status/CI-Mrs?branchName=master)](https://dev.azure.com/k2works/Mrs/_build/latest?definitionId=9&branchName=master)
[![Build Status](https://dev.azure.com/k2works/Mrs/_apis/build/status/CI-Mrs?branchName=develop)](https://dev.azure.com/k2works/Mrs/_build/latest?definitionId=9&branchName=develop)
[![Java CI with Maven](https://github.com/k2works/mrs/workflows/Java%20CI%20with%20Maven/badge.svg)](https://github.com/k2works/mrs)
[![Board Status](https://dev.azure.com/k2works/e0cfa8c1-ce91-4a4d-b558-e400d21a87db/b177bb37-c9f8-4987-a7e1-48fec6080f57/_apis/work/boardbadge/f6454edf-e0d2-400b-86a9-af0f740f9d3a)](https://dev.azure.com/k2works/e0cfa8c1-ce91-4a4d-b558-e400d21a87db/_boards/board/t/b177bb37-c9f8-4987-a7e1-48fec6080f57/Microsoft.RequirementCategory/)

# MRS (Meeting room reservation system)

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/k2works/mrs)

## 概要

Spring 徹底入門 Spring Framework による Java アプリケーション開発 チュートリアルの実装例

実装にあたっては [CCSR手法](https://masuda220.hatenablog.com/entry/2020/05/27/103750) を参考にオリジナルをリファクタリングした。

### 目的

- SpringBootの学習
- CCSR手法の実践
- テスト駆動開発とリファクタリングの実践
- 継続的インテグレーションの実践

### 前提

| ソフトウェア | バージョン | 備考 |
| :----------- | :--------- | :--- |
| java         | 11         |      |
| SpringBoot   | 2.4.1      |      |
| Node.js      | 12.16.3    |      |

### Quick Start

```bash
./gradlew bootRun
```

アプリケーションは、http://localhost:8080 でログイン画面を表示できます。

APIドキュメントはログイン後に http://localhost:8080/swagger-ui.html から確認できます。

### アプリケーションの内容

会議室の予約とキャンセルを行うビジネスユースケース

- Webで会議室の予約を行う
- Webで会議室の予約をキャンセルする

![](./docs/img/diagrams/BusinessUseCase.png)

- [ドキュメント](./docs/Requirement.md)

## 構成

- [構築](#構築)
- [配置](#配置)
- [開発](#開発)
- [運用](#運用)

### 構築

#### システム構成

![](./docs/img/diagrams/SystemArchitecture.png)

- [ドキュメント](./docs/Build.md)

**[⬆ back to top](#構成)**

### 配置

- [ドキュメント](./docs/Ship.md)

**[⬆ back to top](#構成)**

### 開発

#### データモデル

![](./docs/img/erd/jig-erd-detail.png)

#### アプリケーションアーキテクチャ

![](./docs/img/jig/architecture.svg)

#### ドメインオブジェクトのモデル

![](./docs/img/jig/package-relation-depth5.svg)

![](./docs/img/jig/business-rule-relation.svg)

#### 画面とユースケース

![](./docs/img/jig/service-method-call-hierarchy.svg)

- [ドキュメント](./docs/Development.md)

**[⬆ back to top](#構成)**

### 運用

- [ドキュメント](./docs/Run.md)

**[⬆ back to top](#構成)**

## 参照

- [Spring 徹底入門 Spring Framework による Java アプリケーション開発](https://www.amazon.co.jp/dp/B01IEWNLBU/ref=dp-kindle-redirect?_encoding=UTF8&btkr=1)
- [JIG](https://github.com/dddjava/jig)
- [JIG-ERD](https://github.com/irof/jig-erd)
