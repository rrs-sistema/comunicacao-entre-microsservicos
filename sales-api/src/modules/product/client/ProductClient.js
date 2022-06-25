import axios from "axios";

import { PRODUC_API_URL } from "../../../config/constants/secrets.js";
import { BAD_REQUEST } from "../../../config/constants/httpStatus.js";

class ProductClient {
    async checkProductStock(products, token) {
        try {
            const body = JSON.stringify(products);
            const haaders = {
                Authorization: token
            }
            console.log(`Sending request to Product API with data: ${body}`);
            let response = false;

            await axios.post(`${PRODUC_API_URL}/check-stock`, { haaders }, products)
            .then((res) => {
                console.info(res);
                response = true;
            }).catch((err) => {
                console.error(err.response.message);
                response = false; 
            });
            return response;
        } catch (err) {
            return false;             
        }
    }
}

export default new ProductClient();