import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axiosConfig from "../../service/api/newApi";



const initialState = [];


export const fetchProducts = createAsyncThunk('fetchProducts',async () =>{
    
    const response = await axiosConfig.get("/products/");

    console.log("fetch product:::::",response.data)
    return response.data;
});


const productSlice = createSlice({
    name:'products',
    initialState,
    reducers:{},
    extraReducers(builder){
        builder.addCase(fetchProducts.fulfilled,(state,action) =>{
            return action.payload;
        })
    }
})


export const getAllProducts = (state) => state.products;


export default productSlice.reducer;