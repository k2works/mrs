# インフラ

# 概要

## 目的

## 前提

# 構成

+ 概要
+ 構築
+ 配置
+ 運用

## 概要

## 構築

## セットアップ(WSL)

WSL環境構築に関しては以下を参照

- [環境構築から始めるテスト駆動開発 ~プログラミング環境の共通基盤を構築する~](https://k2works.github.io/2020/04/07/2020-04-08-1/)

[web.yml](ops/build/ansible/group_vars/docker.yml) の `user:` をWSLアカウントユーザーにする

```
sudo apt update
sudo apt install software-properties-common -y
sudo apt-add-repository --yes --update ppa:ansible/ansible 
sudo apt-get install ansible -y
sudo ansible-playbook -v -i ./ops/build/ansible/inventory/hosts ./ops/build/ansible/site_wsl.yml
```

## 配置

## 運用
