import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axiosConfig from "../../service/api/newApi";



const initialState = {
    products:[],
    isLoading:false,
    error:null
};

// toolkit promise status artcile:::https://medium.com/lamalab/async-operations-in-redux-with-the-redux-toolkit-thunk-e7d024cbf875
export const fetchProducts = createAsyncThunk('fetchProducts',async () =>{
    
    const response = await axiosConfig.get("/products/");

    // console.log("fetch product:::::",response.data)
    return response.data;
});


const productSlice = createSlice({
    name:'products',
    initialState,
    reducers:{},
    extraReducers(builder){
        builder.addCase(fetchProducts.pending, (state) =>{
            state.isLoading = true
        })
        builder.addCase(fetchProducts.fulfilled,(state,action) =>{
            state.isLoading = false
            state.products = action.payload
        })
        builder.addCase(fetchProducts.rejected,(state,action) =>{
            state.isLoading = false
            state.error = action.error.message
        })
        // builder.addCase(fetchProducts.fulfilled,(state,action) =>{
        //     return action.payload;
        // })
    }
})


export const getAllProducts = (state) => state.products;


export default productSlice.reducer;