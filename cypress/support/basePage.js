export class BasePage {
    constructor(url = "http://localhost:8080") {
        this._url = url;
    }

    login() {
        cy.visit(this._url);
        cy.get('button').click()
    }
}
