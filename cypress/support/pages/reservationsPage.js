import {BasePage} from "../basePage";

export class ReservationsPage extends BasePage {
    constructor() {
        super();
        this._appId = "reservations";
        this._url = `${this._url}/${this._appId}`;
    }

    visit() {
        cy.visit(this._url);
    }
}
