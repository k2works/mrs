import {BasePage} from "../basePage";

export class SignupPage extends BasePage {
    constructor() {
        super();
        this._appId = "signup";
        this._url = `${this._url}/${this._appId}`;
    }

    visit() {
        cy.visit(this._url);
        cy.get('#signup').click();
    }
}
