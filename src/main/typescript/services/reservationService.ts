import axios from "axios";
import authHeader from "./auth-header";
import {reservationConst} from "../utils/serviceUtils";

const API_URL = reservationConst.API_URL

class ReservationService {
    list(params: { date: Date, roomId: number }) {
        const year = params.date.getFullYear()
        const month = ('0' + (params.date.getMonth() + 1)).slice(-2)
        const day = ('0' + (params.date.getDate())).slice(-2)
        const url = `${API_URL}/${year}-${month}-${day}/${params.roomId}`

        return axios.get(url, {headers: authHeader()});
    }

    reserve(params: { date: Date, start: string, end: string, roomId: number }) {
        const year = params.date.getFullYear()
        const month = ('0' + (params.date.getMonth() + 1)).slice(-2)
        const day = ('0' + (params.date.getDate())).slice(-2)
        const url = `${API_URL}/${year}-${month}-${day}/${params.roomId}?end=${params.end}&start=${params.start}`

        return axios
            .post(url, {}, {headers: authHeader()})
            .then((response) => {
                return response.data;
            })
            .catch(error => {
                throw error
            })
    }

    cancel(params: { date: Date, roomId: number, reservationId: number, username: string }) {
        const year = params.date.getFullYear()
        const month = ('0' + (params.date.getMonth() + 1)).slice(-2)
        const day = ('0' + (params.date.getDate())).slice(-2)
        const url = `${API_URL}/${year}-${month}-${day}/${params.roomId}?reservationId=${params.reservationId}&username=${params.username}`

        console.log(params)
        return axios
            .delete(url, {headers: authHeader()})
            .then((response) => {
                return response.data;
            })
            .catch(error => {
                throw error
            })
    }
}

export default new ReservationService()
