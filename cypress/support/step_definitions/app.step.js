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
    page = new AppPage();
    cy.wait(1000);
});

Then(`{string} が表示される`, (value) => {
    cy.get('h3').should("contain", value);
});
