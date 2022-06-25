import axios from "axios";

import { PRODUCT_API_URL } from "../../../config/constants/secrets.js";

class ProductClient {
    async checkProductStock(products, token, transactionid) {
        try {
            const body = JSON.stringify(products);
            const haaders = {
                Authorization: token,
                transactionid
            }
            console.info(`Sending request to Product API with data: ${body} and transactionID ${transactionid}`);
      let response = false;
      await axios
        .post(
          `${PRODUCT_API_URL}/check-stock`,
          { products: productsData.products },
          { headers }
        )
        .then((res) => {
          console.info(
            `Success response from Product-API. TransactionID: ${transactionid}`
          );
          response = true;
        })
        .catch((err) => {
          console.error(
            `Error response from Product-API. TransactionID: ${transactionid} AND ERROR ${err}`
          );
          response = false;
        });
            return response;
        } catch (err) {
            `Error response from Product-API. TransactionID: ${transactionid} AND ERROR GENERAL ${err}`
            return false;             
        }
    }
}

export default new ProductClient();