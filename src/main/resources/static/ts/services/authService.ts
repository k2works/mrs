import axios from "axios";

const API_URL = "http://localhost:8080/api/auth/";

class AuthService {
    login(username: any, password: any) {
        return axios
            .post(API_URL + "signin", {username, password})
            .then((response) => {
                if (response.data.token) {
                    localStorage.setItem("session", JSON.stringify(response.data));
                }

                return response.data;
            })
            .catch(error => {
                throw error
            })
    }

    logout() {
        localStorage.removeItem("session");
    }

    register(userId: any, email: any, password: any) {
        return axios
            .post(API_URL + "signup", {
                userId,
                email,
                password,
            })
            .then(response => {
                return response.data
            })
            .catch(error => {
                throw error
            })
    }
}

export default new AuthService();
