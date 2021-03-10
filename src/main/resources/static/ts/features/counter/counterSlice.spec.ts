import counter, {increment, decrement, incrementByAmount, incrementAsync} from "./counterSlice";

describe('counter reducer', () => {
    test('カウンターを増やす', () => {
        expect(counter({value:0},increment())).toEqual({value:1})
    })

    test('カウンターを減らす', () => {
        expect(counter({value:1},decrement())).toEqual({value:0})
    })

    test('指定した値でカウンターを増やす', () => {
        const result = incrementByAmount(20)
        expect(result.payload).toEqual(20)
    })

    test('非同期で指定した値でカウンターを増やす', () => {
        const result = new Promise(resolve => { resolve(incrementByAmount(10))})
        return result.then(result => {
            // @ts-ignore
            expect(result.payload).toEqual(10)
        })
    })
})
