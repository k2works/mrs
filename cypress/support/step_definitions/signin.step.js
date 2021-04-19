const {
    Before,
    After,
    Given,
    Then,
} = require("cypress-cucumber-preprocessor/steps");

import {SigninPage} from "../pages/signinPage";

// this will get called before each scenario
let page;
Before(() => {
    page = new SigninPage();
    cy.wait(0);
});

Then(`{string} が表示される`, (value) => {
    cy.get('h2').should("contain", value);
});
