import {BasePage} from "../basePage";

export class LoginPage extends BasePage {
    constructor() {
        super();
        this._appId = "login";
        this._url = `${this._url}/${this._appId}`;
    }

    visit() {
        cy.visit(this._url);
    }
}
