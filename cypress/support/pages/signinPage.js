import {BasePage} from "../basePage";

export class SigninPage extends BasePage {
    constructor() {
        super();
        this._appId = "signin";
        this._url = `${this._url}/${this._appId}`;
    }

    visit() {
        cy.visit(this._url);
    }
}
