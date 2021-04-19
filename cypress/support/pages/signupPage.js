import {BasePage} from "../basePage";

export class SignupPage extends BasePage {
    constructor() {
        super();
        this._appId = "register";
        this._url = `${this._url}/${this._appId}`;
    }

    visit() {
        cy.get('#signup').click();
    }
}
