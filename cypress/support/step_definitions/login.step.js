const {
    Before,
    After,
    Given,
    Then,
} = require("cypress-cucumber-preprocessor/steps");

import {LoginPage} from "../pages/loginPage";

// this will get called before each scenario
let page;
Before(() => {
    page = new LoginPage();
    cy.wait(0);
});

Then(`{string} が表示される`, (value) => {
    cy.get('h2').should("contain", value);
});
