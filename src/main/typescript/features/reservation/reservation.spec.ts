import apiModule from '../../services/reservationService'
import {AsyncThunkAction, Dispatch} from "@reduxjs/toolkit";
import {reservationCancel, reservationList, reservationReserve} from "./reservationSlice";

jest.mock('../../services/reservationService')

describe('reservation reducer', () => {
    let api: jest.Mocked<typeof apiModule>

    beforeAll(() => {
        api = apiModule as any;
    })

    afterAll(() => {
        jest.unmock('../../services/reservationService')
    })

    describe('list', () => {
        let action: AsyncThunkAction<void, {}, {}>

        let dispatch: Dispatch;
        let getState: () => unknown;

        let arg: { date: Date, roomId: number };
        let result: any;

        beforeEach(() => {
            dispatch = jest.fn();
            getState = jest.fn();

            api.list.mockClear();
            api.list.mockResolvedValue(result)

            arg = {date: new Date('2021-04-15'), roomId: 1};
            result = {
                "value": [
                    {
                        "reservableRoom": {
                            "meetingRoom": {
                                "roomId": {
                                    "value": 0
                                },
                                "roomName": {
                                    "value": "string"
                                }
                            },
                            "reservableRoomId": {
                                "reservedDate": {
                                    "value": "string"
                                },
                                "roomId": {
                                    "value": 0
                                }
                            }
                        },
                        "reservationId": {
                            "value": 0
                        },
                        "reservedDateTime": {
                            "date": {
                                "value": "string"
                            },
                            "time": {
                                "end": "string",
                                "start": "string"
                            }
                        },
                        "user": {
                            "name": {
                                "firstName": "string",
                                "lastName": "string"
                            },
                            "password": {
                                "value": "string"
                            },
                            "roleName": "ADMIN",
                            "userId": {
                                "value": "string"
                            }
                        }
                    }
                ]
            }

            action = reservationList(arg)
        })

        test('サービスを呼びだす', async () => {
            await action(dispatch, getState, undefined);
            expect(api.list).toHaveBeenCalledWith(arg);
        })

        test('予約一覧を取得する', async () => {
            const data = await action(dispatch, getState, undefined);
            expect(data.payload).toStrictEqual(result)
        })
    })

    describe('reserve', () => {
        let action: AsyncThunkAction<void, {}, {}>

        let dispatch: Dispatch;
        let getState: () => unknown;

        let arg: { date: Date, start: string, end: string, roomId: number, userid: string };
        let result: any;

        beforeEach(() => {
            dispatch = jest.fn();
            getState = jest.fn();

            api.list.mockClear();
            api.list.mockResolvedValue(result)

            arg = {date: new Date('2021-04-15'), start: '10:00', end: '11:00', roomId: 1, userid: 'aaaa'};
            result = {}

            action = reservationReserve(arg)
        })

        test('サービスを呼びだす', async () => {
            await action(dispatch, getState, undefined);
            expect(api.reserve).toHaveBeenCalledWith(arg);
        })
    })

    describe('cancel', () => {
        let action: AsyncThunkAction<void, {}, {}>

        let dispatch: Dispatch;
        let getState: () => unknown;

        let arg: { date: Date, roomId: number, reservationId: number, username: string };
        let result: any;

        beforeEach(() => {
            dispatch = jest.fn();
            getState = jest.fn();

            api.list.mockClear();
            api.list.mockResolvedValue(result)

            arg = {date: new Date('2021-04-15'), roomId: 1, reservationId: 1, username: 'aaaa'};
            result = {}

            action = reservationCancel(arg)
        })

        test('サービスを呼びだす', async () => {
            await action(dispatch, getState, undefined);
            expect(api.cancel).toHaveBeenCalledWith(arg);
        })
    })
})
