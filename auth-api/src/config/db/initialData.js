import bcrypt from "bcrypt";

import User from "../../modules/user/model/User.js";

export async function createInitialData() {
    try {
        await User.sync({force: true});

        let password = await bcrypt.hash('153111', 10)
    
        await User.create({
            name: 'Riva Robert',
            email: 'riva.robert@gmail.com',
            password: password
        });
        await User.create({
            name: 'Kamilly Ketuly',
            email: 'kamilly.ke@gmail.com',
            password: password
        });        
    } catch (error) {
        console.log(error);
        const status = err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR;
        return res.status(status).json({status, message: err.message});         
    }
}