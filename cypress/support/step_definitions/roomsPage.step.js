const {
    Before,
    After,
    Given,
    Then,
} = require("cypress-cucumber-preprocessor/steps");

import {RoomsPage} from "../pages/roomsPage";

// this will get called before each scenario
let page;
Before(() => {
    page = new RoomsPage();
    cy.wait(1000);
});

Given(`{string} 会議室を選択する`, (name) => {
    cy.get(':nth-child(1) > a').click();
})

Then(`会議室予約一覧に {string} が表示される`, (name) => {
    cy.get('ul > :nth-child(1)').should('contain', name);
})
