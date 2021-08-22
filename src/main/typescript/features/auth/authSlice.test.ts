import * as authSlice from "./authSlice"
// @ponicode
describe("authSlice.signupAsync", () => {
    test("0", () => {
        let callFunction: any = () => {
            authSlice.signupAsync({ id: "03ea49f8-1d96-4cd0-b279-0684e3eec3a9", password: "!Lov3MyPianoPony", name: { first: "Mon Aug 03 12:45:00", last: "2017-09-29T23:01:00.000Z" }, email: "something@example.com" })
        }
    
        expect(callFunction).not.toThrow()
    })

    test("1", () => {
        let callFunction: any = () => {
            authSlice.signupAsync({ id: "03ea49f8-1d96-4cd0-b279-0684e3eec3a9", password: "!Lov3MyPianoPony", name: { first: "Mon Aug 03 12:45:00", last: "Mon Aug 03 12:45:00" }, email: "email@Google.com" })
        }
    
        expect(callFunction).not.toThrow()
    })

    test("2", () => {
        let callFunction: any = () => {
            authSlice.signupAsync({ id: "03ea49f8-1d96-4cd0-b279-0684e3eec3a9", password: "$p3onyycat", name: { first: "Mon Aug 03 12:45:00", last: "Mon Aug 03 12:45:00" }, email: "email@Google.com" })
        }
    
        expect(callFunction).not.toThrow()
    })

    test("3", () => {
        let callFunction: any = () => {
            authSlice.signupAsync({ id: "a85a8e6b-348b-4011-a1ec-1e78e9620782", password: "!Lov3MyPianoPony", name: { first: "2017-09-29T19:01:00.000", last: "2017-09-29T19:01:00.000" }, email: "something@example.com" })
        }
    
        expect(callFunction).not.toThrow()
    })

    test("4", () => {
        let callFunction: any = () => {
            authSlice.signupAsync({ id: "a85a8e6b-348b-4011-a1ec-1e78e9620782", password: "!Lov3MyPianoPony", name: { first: "2017-09-29T19:01:00.000", last: "2017-09-29T23:01:00.000Z" }, email: "email@Google.com" })
        }
    
        expect(callFunction).not.toThrow()
    })

    test("5", () => {
        let callFunction: any = () => {
            authSlice.signupAsync({ id: "", password: "", name: { first: "", last: "" }, email: "" })
        }
    
        expect(callFunction).not.toThrow()
    })
})
