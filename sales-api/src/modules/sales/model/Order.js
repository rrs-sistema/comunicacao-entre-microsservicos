import mongoose from "mongoose";

const Schema = mongoose.Schema;
const model = mongoose.model;

const OrderSchema = new Schema({
    products: {
        type: Array,
        require: true,
    },
    user: {
        type: Object,
        require: true,
    },
    status: {
        type: String,
        require: true,
    },
    createAt: {
        type: Date,
        require: true,
    },
    updatedAt: {
        type: Date,
        require: true,
    }   
});

export default model("Order", OrderSchema);