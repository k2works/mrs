export class BasePage {
    constructor(url = "http://localhost:3000") {
        this._url = url;
    }

    login(username, password) {
        cy.visit(this._url);
        cy.get('#username').clear();
        cy.get('#password').clear();
        cy.get('#username').type(username);
        cy.get('#password').type(password);
        cy.get('#login').click();
    }
}
