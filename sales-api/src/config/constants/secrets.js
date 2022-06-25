const env = process.env;

export const MONGO_DB_URL = env.MONGO_DB_URL ? env.MONGO_DB_URL : 'mongodb://127.0.0.1:27017/sales-db';
export const API_SECRET =  env.API_SECRET ? env.API_SECRET : 'YXV0aC1hcGktc2VjcmV0LWRldi0xNTMxMTE='; //auth-api-secret-dev-153111
export const RABBIT_MQ_URL = env.RABBIT_MQ_URL ? env.RABBIT_MQ_URL : 'amqp://localhost:5672';
export const PRODUCT_API_URL = env.PRODUCT_API_URL ? env.PRODUCT_API_URL : 'http://localhost:8081/api/product';