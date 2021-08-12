let BASE_API_URL = "http://localhost:8080/api";
if (process.env.NODE_ENV === 'production') {
    BASE_API_URL = "http://app-mrs-blue.ap-northeast-1.elasticbeanstalk.com/api";
} else if (process.env.NODE_ENV === 'staging') {
    BASE_API_URL = "https://app-mrs.azurewebsites.net/api";
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

