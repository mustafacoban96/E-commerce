import { createAsyncThunk, createSlice } from "@reduxjs/toolkit"
import axiosConfig from "../../service/api/newApi"



const initialState = {
    order_items_list:[],
    total_price:0,
    user_id:null,
    isLoading: false,
    error: null,
    is_success_order:false, // for route order-end page after successful order
    order_id:null,
    order_total:0,
    delivery_by:null
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
            state.is_success_order = false;
        }
    },
    extraReducers(builder){
        // Add item to cart
        builder.addCase(createOrder.pending, (state) => {
            state.isLoading = true;
        })
        builder.addCase(createOrder.fulfilled, (state, action) => {
          
            state.isLoading = false;
            state.order_id = action.payload.order_id;
            state.delivery_by = action.payload.delivery_by
            state.order_total = action.payload.order_total
            state.is_success_order = action.payload.is_success_order;

        })
        builder.addCase(createOrder.rejected, (state, action) => {
            state.isLoading = false;
            state.error = action.error.message;
        });
    }
})

export const getIsLoading = (state) => state.orders.isLoading;
export const getError = (state) => state.orders.error;
export const getIsSuccessOrder = (state) => state.orders.is_success_order;
export const getOrderId = (state) => state.orders.order_id;
export const getDeliveryBy = (state) => state.orders.delivery_by;
export const getOrderTotal = (state) => state.orders.order_total;
export const {resetOrderSuccess} = orderSlice.actions;

export default orderSlice.reducer;

