import {BasePage} from "../basePage";

export class RoomsPage extends BasePage {
    constructor() {
        super();
        this._appId = "rooms";
        this._url = `${this._url}/${this._appId}`;
    }

    visit() {
        cy.visit(this._url);
    }
}
