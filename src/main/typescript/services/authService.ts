import axios from "axios";
import {authConst} from "../utils/serviceUtils";

const API_URL = authConst.API_URL

class AuthService {
    signin(username: any, password: any) {
        return axios
            .post(API_URL + "/" + "signin", {username, password})
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

    signout() {
        localStorage.removeItem("session");
    }

    signup(userId: any, email: any, password: any, firstName: string, lastName: string) {
        return axios
            .post(API_URL + "signup", {
                userId,
                email,
                password,
                firstName,
                lastName
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
