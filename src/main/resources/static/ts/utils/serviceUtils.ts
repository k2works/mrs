let BASE_API_URL = "http://localhost:8080/api";
if (process) {
    if (process.env.NODENV === 'production') {
        BASE_API_URL = "https://app-mrs.azurewebsites.net/api";
    }
}

export namespace authConst {
    export const API_URL = `${BASE_API_URL}/auth`
}

export namespace roomConst {
    export const API_URL = `${BASE_API_URL}/rooms`
}

export namespace reservationConst {
    export const API_URL = `${BASE_API_URL}/reservations`
}

