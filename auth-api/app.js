import express from 'express';

import { createInitialData } from './src/config/db/initialData.js';
import userRoutes from './src/modules/user/routes/UserRoutes.js'

import trancing from './src/config/trancing.js';

const app = express();
const env = process.env;
const PORT = env.PORT || 8080;

createInitialData();

app.use(trancing);

app.get('/api/status', (req, res) => {
    return res.status(200).json({
        service: 'Auth-API',
        status: 'UP',
        httpStatus: 200
    });
});

app.use(express.json());
app.use(userRoutes);

app.listen(PORT, () =>{
    console.log(`Server started sucessfully at port ${PORT}`);
});