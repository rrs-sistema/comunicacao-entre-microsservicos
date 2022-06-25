import { v4 as uuidv4 } from 'uuid';

import Order from "../../modules/sales/model/Order.js";

export async function createInitialData() {
    await Order.collection.drop();

    await Order.create({
        products: [
            {
                productId: 1000,
                quantity: 3
              },
              {
                productId: 1002,
                quantity: 1
              },
              {
                productId: 1003,
                quantity: 2
            },                       
        ],
        user: {
            id: 'a1d2df5ase1dsd5dfd',
            name: 'Riva Robert',
            email: 'riva.robert@gmail.com',
        },
        status: 'APPROVED',
        createAt: new Date(),
        updatedAt: new Date(),
        transactionid: uuidv4(),
        serviceid: uuidv4()
    });
    await Order.create({
        products: [
            {
            productId: 1002,
            quantity: 5
            },
            {
            productId: 1003,
            quantity: 4
            },                       
        ],
        user: {
            id: 'b6a1df9bnew9nbf6my',
            name: 'Kamilly Ketuly',
            email: 'kamilly.ke@gmail.com',
        },
        status: 'REJECTED',
        createAt: new Date(),
        updatedAt: new Date(),
        transactionid: uuidv4(),
        serviceid: uuidv4()        
    });    

    let initialData = await Order.find();
    console.log(`Initial data was created: ${JSON.stringify(initialData, undefined, 4)}`);
}