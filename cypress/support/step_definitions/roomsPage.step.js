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
    cy.wait(0);
});

Given(`{string} 会議室を選択する`, (room) => {
    switch (room) {
        case '新木場':
            cy.get(':nth-child(1) > a').click();
            break;
        case '有楽町':
            cy.get('ul > :nth-child(2) > a').click();
            break;
        default:
            console.log("該当するページが存在しません");
    }
})

Then(`会議室予約一覧に {string} が表示される`, (name) => {
    cy.get('ul > :nth-child(1)').should('contain', name);
})
