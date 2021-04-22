export default function authHeader() {
    const session = JSON.parse(<string>localStorage.getItem('session'));

    if (session && session.token) {
        return {Authorization: 'Bearer ' + session.token};
    } else {
        return {};
    }
}
