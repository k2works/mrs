export class BasePage {
    constructor(url = "http://localhost:3000") {
        this._url = url;
    }

    login() {
        cy.visit(this._url);
        cy.get('#login').click()
    }
}
