let BASE_API_URL = "http://localhost:8080/api";
if (process.env.NODE_ENV === 'production') {
    BASE_API_URL = process.env.API_URL || BASE_API_URL
} else if (process.env.NODE_ENV === 'staging') {
    BASE_API_URL = process.env.API_URL || BASE_API_URL
}

console.log(process.env.NODE_ENV)
console.log(process.env.API_URL)
console.log(BASE_API_URL)

export namespace authConst {
    export const API_URL = `${BASE_API_URL}/auth`
}

export namespace roomConst {
    export const API_URL = `${BASE_API_URL}/rooms`
}

export namespace reservationConst {
    export const API_URL = `${BASE_API_URL}/reservations`
}

