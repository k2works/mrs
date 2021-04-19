import {RegistPage} from "../pages/registPage";
import {LoginPage} from "../pages/loginPage";
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
        case "ユーザー登録":
            page = new RegistPage();
            break;
        case "ログイン":
            page = new LoginPage();
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
            page = new LoginPage()
            page.visit()
            page.login()
            break
        default:
            throw new Error('該当するページが存在しません')
    }
})
