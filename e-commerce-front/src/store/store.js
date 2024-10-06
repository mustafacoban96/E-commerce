
import { configureStore } from "@reduxjs/toolkit";
import productsReducer from '../features/products/productSlice'
import cartReducer from '../features/cart/cartSlice'
import orderReducer from '../features/order/orderSlice'
import profileReducer from '../features/profileSettings/profileSlice'



export const store = configureStore({
    reducer: {
        //add Slice
        products:productsReducer,
        cart:cartReducer,
        orders:orderReducer,
        profile:profileReducer
    }
})




