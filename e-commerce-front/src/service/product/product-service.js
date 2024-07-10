import { toast } from "react-toastify";
import useApi from "../api"
//import axiosClient from "../auth/test-auth";




const useProductService = () =>{
    const api = useApi();

    const addProduct = (addProductPayload) =>{
        return api.post("/products/create-product",addProductPayload).then((response) => {
           console.log('product-response:',response);
           let message = response.data
           toast.success(message , {
            autoClose: 2000,
            position: 'top-right',
        });
           
        })
        .catch((err) => {
            console.log('product-error:',err);
           
        });
    }



    return {
        addProduct
    }

}

export default useProductService;