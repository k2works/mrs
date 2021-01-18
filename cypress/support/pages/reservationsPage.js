import {BasePage} from "../basePage";

export class ReservationsPage extends BasePage {
    constructor() {
        super();
        this._appId = "reservations";
    }

    visit() {
        cy.visit(this._url);
    }
}
