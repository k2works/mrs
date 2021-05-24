import {SignupPage} from "../pages/signupPage";
import {SigninPage} from "../pages/signinPage";
import {RoomsPage} from "../pages/roomsPage";

const {
    Before,
    After,
    Given,
    Then,
} = require("cypress-cucumber-preprocessor/steps");

// this will get called before each scenario
let page;
Before(() => {
    cy.wait(0);
});

Given(`{string} ページにアクセスする`, (pageName) => {
    switch (pageName) {
        case "利用者登録":
            page = new SignupPage();
            break;
        case "ログイン":
            page = new SigninPage();
            break;
        case "会議室予約一覧":
            page = new RoomsPage();
            break;
        default:
            console.log("該当するページが存在しません");
    }
    page.visit();
});

Then(`機能名 {string} が表示される`, (funcName) => {
    cy.get("body > :nth-child(3)").should("contain", funcName);
});

Given(`{string} としてログインしている`, (user) => {
    switch (user) {
        case ("利用者"):
            page = new SigninPage()
            page.visit()
            page.login('aaaa', 'demo')
            break
        case ("管理者"):
            page = new SigninPage()
            page.visit()
            page.login('cccc', 'demo')
            break
        default:
            throw new Error('該当するページが存在しません')
    }
})

Given(`読み込みが完了するまで待つ`, (user) => {
    cy.wait(10000);
})
