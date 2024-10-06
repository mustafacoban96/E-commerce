import { createAsyncThunk, createSlice } from "@reduxjs/toolkit"
import axiosConfig from "../../service/api/newApi";


const initialState = {
    id:null,
    username:null,
    email:null,
    isLoading:false,
    error:null
}


// export const updateProfileInfo = createAsyncThunk('profile/updateUserInfo',async(user_id,user_info) =>{
//     console.log(':::::',user_info);
//     const response = await axiosConfig.put(`users/update-user/${user_id}`,user_info)
//     console.log(response)
//     //return response.data;
// })

export const updateProfileInfo = createAsyncThunk('profile/updateUserInfo', async ({ user_id, user_info }, thunkAPI) => {
    try {
        console.log('User Info:', user_info);
        const response = await axiosConfig.put(`/users/update-user/${user_id}`, user_info);
        console.log('Response:', response);
        return response.data;
    } catch (error) {
        console.error('Error updating profile:', error);
        return thunkAPI.rejectWithValue(error.response?.data || 'Failed to update profile');
    }
});



const profileSlice = createSlice({
    name:'profile',
    initialState,
    reducers:{},
    extraReducers(builder){

        builder.addCase(updateProfileInfo.pending, (state) =>{
            state.isLoading = true;
        })

        builder.addCase(updateProfileInfo.fulfilled, (state,action) =>{
            state.isLoading = false;
            state.id = action.payload.id;
            state.username = action.payload.username;
            state.email = action.payload.email;
        })

        builder.addCase(updateProfileInfo.rejected, (state,action) =>{
            console.log('cann:',action.payload.message)
            console.log('cann2:',action.payload.errors)
            state.isLoading = false;
            state.error = action.payload.message  ?? action.payload.errors[0];
        })


    }

})



export const getIsLoading = (state) => state.profile.isLoading;
export const getError = (state) => state.profile.error;
export default profileSlice.reducer;