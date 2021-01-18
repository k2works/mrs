# 構築

## システム構成

![](./img/diagrams/SystemArchitecture.png)

## E2Eセットアップ

```
npm init -y
npm install --save-dev npm-run-all watch foreman cpx rimraf
npm install cypress
npm install --save-dev cypress-cucumber-preprocessor
npx cypress open
touch cypress/Procfile
```
