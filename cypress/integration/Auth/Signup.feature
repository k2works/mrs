Feature: 認証

  利用者として
  アプリケーション利用者の登録をしたい
  なぜなら登録された利用者が認証されるから

  Scenario: アプリケーション:ユーザー登録画面
    Given "利用者登録" ページにアクセスする
    Then "利用者登録" が表示される

  Scenario: アプリケーション:ユーザー登録
    Given "利用者登録" ページにアクセスする
    Given 利用者を登録する
    Then 利用者登録画面に "User registered successfully!" が表示される
