import express from "express";

import { connectRabbitMq } from './src/config/rabbitmq/rabbitConfig.js';
import OrderRoutes from "./src/modules/sales/routes/OrderRoutes.js";
import { createInitialData } from './src/config/db/initialData.js';
import { connectMongoDb } from "./src/config/db/mongoDbConfig.js";
import checkToken from "./src/config/auth/checkToken.js";

const app = express();
const env = process.env;
const PORT = env.PORT || 8082;

connectMongoDb();
createInitialData();
connectRabbitMq();

app.use(express.json());
app.use(checkToken);
app.use(OrderRoutes);

app.get('/api/status', async (req, res)=> {
    return res.status(200).json({
        service: 'Sales-API',
        status: 'UP',
        httpStatus: 200
    });
});

app.listen(PORT, () => {
    console.log(`Server started sucessfully at port ${PORT}`);
});