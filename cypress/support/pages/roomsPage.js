import {BasePage} from "../basePage";

export class RoomsPage extends BasePage {
    constructor() {
        super();
        this._appId = "rooms";
    }

    visit() {
        cy.visit(this._url);
    }
}
