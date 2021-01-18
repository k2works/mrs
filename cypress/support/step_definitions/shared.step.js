const {
    Before,
    After,
    Given,
    Then,
} = require("cypress-cucumber-preprocessor/steps");

import {AppPage} from "../pages/appPage";

// this will get called before each scenario
let page;
Before(() => {
    cy.wait(1000);
});

Given(`{string} ページにアクセスする`, (pageName) => {
    switch (pageName) {
        case "アプリケーション":
            page = new AppPage();
            break;
        default:
            console.log("該当するページが存在しません");
    }
    page.visit();
});

Then(`機能名 {string} が表示される`, (funcName) => {
    cy.get("body > :nth-child(3)").should("contain", funcName);
});
