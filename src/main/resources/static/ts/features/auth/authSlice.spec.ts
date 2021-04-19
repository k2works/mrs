import apiModule from '../../services/authService';
import {AsyncThunkAction, Dispatch} from "@reduxjs/toolkit";
import {authLogin, authRegister, register, RegistUser} from "./authSlice";

jest.mock('../../services/authService')

describe('auth reducer', () => {
    let api: jest.Mocked<typeof apiModule>

    beforeAll(() => {
        api = apiModule as any;
    })

    afterAll(() => {
        jest.unmock('../../services/authService')
    })

    describe('register', () => {
        let action: AsyncThunkAction<void, RegistUser, {}>

        let dispatch: Dispatch;
        let getState: () => unknown;

        let arg: RegistUser;
        let result: any;

        beforeEach(() => {
            dispatch = jest.fn();
            getState = jest.fn();

            api.register.mockClear();
            api.register.mockResolvedValue(result)

            arg = {id: 'aaa', password: 'demo', email: 'aaa@example.com'};
            result = {data: {message: 'User registered successfully!'}}

            action = authRegister(arg)
        })

        test('サービスを呼びだす', async () => {
            await action(dispatch, getState, undefined);
            expect(api.register).toHaveBeenCalledWith('aaa', 'aaa@example.com', 'demo');
        })

        test('登録に成功する', async () => {
            const data = await action(dispatch, getState, undefined);
            expect(data.payload).toStrictEqual(result)
        })
    })

    describe('login', () => {
        let action: AsyncThunkAction<void, RegistUser, {}>

        let dispatch: Dispatch;
        let getState: () => unknown

        let arg: RegistUser;
        let result: any;

        beforeEach(() => {
            dispatch = jest.fn();
            getState = jest.fn();

            api.login.mockClear();
            api.login.mockResolvedValue(result)

            arg = {id: 'aaa', password: 'demo'};
            result = {data: {message: 'User registered successfully!'}}

            action = authLogin(arg)
        })

        test('サービスを呼びだす', async () => {
            await action(dispatch, getState, undefined);
            expect(api.login).toHaveBeenCalledWith('aaa', 'demo');
        })

        test('ログインに成功する', async () => {
            const data = await action(dispatch, getState, undefined);
            expect(data.payload).toStrictEqual(result)
        })
    })
})
