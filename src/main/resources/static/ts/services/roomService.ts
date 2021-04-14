import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/rooms";

class RoomService {
    list(reservedDate: Date) {
        const year = reservedDate.getFullYear()
        const month = ('0' + (reservedDate.getMonth() + 1)).slice(-2)
        const day = ('0' + (reservedDate.getDate())).slice(-2)
        const url = `${API_URL}/${year}-${month}-${day}`

        if (reservedDate) return axios.get(url, {headers: authHeader()});
        return axios.get(API_URL, {headers: authHeader()});
    }
}

export default new RoomService();
