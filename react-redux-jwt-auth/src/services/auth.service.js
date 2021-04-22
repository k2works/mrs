import axios from "axios";
import {replace} from "react-router-redux";

const API_URL = "http://localhost:8080/api/auth/";

class AuthService {
    login(username, password) {
        return axios
            .post(API_URL + "signin", { username, password })
            .then((response) => {
                if (response.data.token) {
                    localStorage.setItem("user", JSON.stringify(response.data));
                }

                return response.data;
            });
    }

    logout() {
        localStorage.removeItem("user");
    }

    register(userId, email, password) {
        return axios.post(API_URL + "signup", {
            userId,
            email,
            password,
        });
    }
}

export default new AuthService();
