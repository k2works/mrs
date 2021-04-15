import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/reservations";

class ReservationService {
    list(params: { date: Date, roomId: number }) {
        const year = params.date.getFullYear()
        const month = ('0' + (params.date.getMonth() + 1)).slice(-2)
        const day = ('0' + (params.date.getDate())).slice(-2)
        const url = `${API_URL}/${year}-${month}-${day}/${params.roomId}`
        console.log(url)

        return axios.get(url, {headers: authHeader()});
    }

    reserve() {
        return new Promise(() => {
        })
    }

    cancel() {
        return new Promise(() => {
        })
    }
}

export default new ReservationService()
