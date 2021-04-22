const {
    Before,
    After,
    Given,
    Then,
} = require("cypress-cucumber-preprocessor/steps");

import {SignupPage} from "../pages/signupPage";

// this will get called before each scenario
let page;
Before(() => {
    page = new SignupPage();
    cy.wait(0);
});

Given(`利用者を登録する`, () => {
    cy.get(':nth-child(1) > div > input').type('xxxx')
    cy.get(':nth-child(2) > div > input').type('姓')
    cy.get(':nth-child(3) > div > input').type('名')
    cy.get(':nth-child(4) > div > input').type('xxxx@example.com')
    cy.get('.form-group > div > input').type('123456')
    cy.get(':nth-child(6) > button').click()
})

Then(`利用者登録画面に {string} が表示される`, (value) => {
    cy.get('.alert').should("contain", value);
});
