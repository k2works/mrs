import {BasePage} from "../basePage";

export class LoginPage extends BasePage {
    constructor() {
        super();
        this._appId = "app";
    }

    visit() {
        cy.visit(this._url);
    }
}
