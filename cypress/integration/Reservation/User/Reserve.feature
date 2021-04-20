Feature: 利用者:会議室予約

  利用者として
  会議室を予約したい
  なぜなら会議室を利用したいから

  Background:
    Given "利用者" としてログインしている

  Scenario: 会議室予約業務:会議室予約一覧表示
    Given "会議室予約一覧" ページにアクセスする
    Then 会議室予約一覧に "新木場" が表示される

  Scenario: 会議室予約業務:会議室予約
    Given "会議室予約一覧" ページにアクセスする
    Given "有楽町" 会議室を選択する
    Given 選択した会議室を予約する
    Then 予約者 "Aaa" が表示される