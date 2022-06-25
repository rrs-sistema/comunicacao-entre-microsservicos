import Order from '../model/Order.js';

class OrderRepository {
    async save(order) {
        try {
            return await Order.create(order);
        } catch (err) {
            console.error(err.message);
            return null;
        }
    }

    async findById(id) {
        try {
            return await Order.findById(id);
        } catch (error) {
            console.error(error.message);
            return null;
        }
    }

    async findAll() {
        try {
            return await Order.find();
        } catch (err) {
            console.error(err.message);
            return null;
        }
    }    

    async findByProductId(productId) {
        try {
            return await Order.find({"products.productId": Number(productId)});
        } catch (err) {
            console.error(err.message);
            return null;
        }
    }  

}

export default new OrderRepository();