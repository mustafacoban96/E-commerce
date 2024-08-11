import { createAsyncThunk, createSlice } from "@reduxjs/toolkit"
import axiosConfig from "../../service/api/newApi"



const initialState = {
    order_items_list:[],
    total_price:0,
    user_id:null,
    isLoading: false,
    error: null,
    isSuccess:false, // for route order-end page after successful order
}

export const createOrder = createAsyncThunk('orders/createOrder', async(orderData) =>{
    const response = await axiosConfig.post("/order/create-order",orderData)
   
    return response.data
})


const orderSlice = createSlice({
    name:'orders',
    initialState,
    reducers:{
        resetOrderSuccess: (state) => {
            state.isSuccess = false;
        }
    },
    extraReducers(builder){
        // Add item to cart
        builder.addCase(createOrder.pending, (state) => {
            state.isLoading = true;
        })
        builder.addCase(createOrder.fulfilled, (state, action) => {
          
            state.isLoading = false;
            state.order_items_list = action.meta.arg.order_items_list;
            state.total_price = action.meta.arg.total_price;
            state.user_id = action.meta.arg.user_id;
            state.isSuccess = true; 
        })
        builder.addCase(createOrder.rejected, (state, action) => {
            state.isLoading = false;
            state.error = action.error.message;
        });
    }
})

export const getIsLoading = (state) => state.orders.isLoading;
export const getError = (state) => state.orders.error;
export const getIsSuccessOrder = (state) => state.orders.isSuccess;
export const {resetOrderSuccess} = orderSlice.actions;

export default orderSlice.reducer;








/*

"{
"order_items_list":[
    {"product_id":"95116eb9-89a1-46b4-b40c-604302d63820","product_name":"Lenovo ideapad1","unit_price":11000,"total_price_per_product":11000,"quantity":1},
    {"product_id":"322ecf6b-6b1b-4747-943c-0f97980d9df8","product_name":"Apple","unit_price":11000,"total_price_per_product":11000,"quantity":1}
    ],
    "total_price":{"requestId":"YuzhcCTxwKVru8WTKgWpT","signal":{}}}"
*/ 
