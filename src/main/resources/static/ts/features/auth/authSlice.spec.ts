import apiModule from '../../services/authService';
import {AsyncThunkAction, Dispatch} from "@reduxjs/toolkit";
import {authSignin, authSignup, SigninUser, signup, SignupUser} from "./authSlice";

jest.mock('../../services/authService')

describe('auth reducer', () => {
    let api: jest.Mocked<typeof apiModule>

    beforeAll(() => {
        api = apiModule as any;
    })

    afterAll(() => {
        jest.unmock('../../services/authService')
    })

    describe('signup', () => {
        let action: AsyncThunkAction<void, SignupUser, {}>

        let dispatch: Dispatch;
        let getState: () => unknown;

        let arg: SignupUser;
        let result: any;

        beforeEach(() => {
            dispatch = jest.fn();
            getState = jest.fn();

            api.signup.mockClear();
            api.signup.mockResolvedValue(result)

            arg = {id: 'aaa', password: 'demo', email: 'aaa@example.com', name: {first: 'Aaaa', last: 'Aaaa'}};
            result = {data: {message: 'User registered successfully!'}}

            action = authSignup(arg)
        })

        test('サービスを呼びだす', async () => {
            await action(dispatch, getState, undefined);
            expect(api.signup).toHaveBeenCalledWith('aaa', 'aaa@example.com', 'demo');
        })

        test('登録に成功する', async () => {
            const data = await action(dispatch, getState, undefined);
            expect(data.payload).toStrictEqual(result)
        })
    })

    describe('signin', () => {
        let action: AsyncThunkAction<void, SigninUser, {}>

        let dispatch: Dispatch;
        let getState: () => unknown

        let arg: SigninUser;
        let result: any;

        beforeEach(() => {
            dispatch = jest.fn();
            getState = jest.fn();

            api.signin.mockClear();
            api.signin.mockResolvedValue(result)

            arg = {id: 'aaa', password: 'demo'};
            result = {data: {message: 'User registered successfully!'}}

            action = authSignin(arg)
        })

        test('サービスを呼びだす', async () => {
            await action(dispatch, getState, undefined);
            expect(api.signin).toHaveBeenCalledWith('aaa', 'demo');
        })

        test('ログインに成功する', async () => {
            const data = await action(dispatch, getState, undefined);
            expect(data.payload).toStrictEqual(result)
        })
    })
})
