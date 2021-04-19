import {BasePage} from "../basePage";

export class RegistPage extends BasePage {
    constructor() {
        super();
        this._appId = "register";
        this._url = `${this._url}/${this._appId}`;
    }

    visit() {
        cy.get('#register').click();
    }
}
