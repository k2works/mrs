# 構築

## システム構成

### 全体構成

![](./img/diagrams/SystemArchitecture.png)

### AWS Type1

![](../ops/build/terraform/type01/arctecture.png)

### AWS Type2

![](../ops/build/terraform/type02/arctecture.png)

### AWS Type3

![](../ops/build/terraform/type03/arctecture.png)

### AWS Type4

![](../ops/build/terraform/type04/arctecture.png)

## AWSセットアップ

[インフラ](../ops/README.md)

## E2Eセットアップ

```
npm init -y
npm install --save-dev npm-run-all watch foreman cpx rimraf
npm install cypress
npm install --save-dev cypress-cucumber-preprocessor
npx cypress open
touch cypress/Procfile
```

## Webpackセットアップ

```
npm install --save-dev browser-sync jest @babel/core @babel/cli @babel/preset-env @babel/register
npm install webpack webpack-cli html-webpack-plugin webpack-serve --save-dev
npm install --save-dev style-loader css-loader
npm install --save-dev npm-run-all watch foreman cpx rimraf marked
```
