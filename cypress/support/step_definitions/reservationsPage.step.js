const {
    Before,
    After,
    Given,
    Then,
} = require("cypress-cucumber-preprocessor/steps");

import {ReservationsPage} from "../pages/reservationsPage";

// this will get called before each scenario
let page;
Before(() => {
    page = new ReservationsPage();
    cy.wait(1000);
});

Given(`選択した会議室を予約する`, () => {
    cy.get('button').click();
})

Then(`予約者 {string} が表示される`, (name) => {
    cy.get('table').should('contain', name);
})

Then(`会議室予約画面に {string} が表示される`, (message) => {
    cy.get('p').should('contain', message)
})
