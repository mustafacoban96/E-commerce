import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { fetchProducts, getAllProducts} from './productSlice'
import AddProduct from '../../views/Product/AddProduct'

const ProductList = () => {
    const dispatch = useDispatch()
    const products = useSelector(getAllProducts);
    useEffect(() =>{
        dispatch(fetchProducts())
     },[dispatch])
   

    
   
    console.log('prodcuts:::::',products)

    const renderedProducts = products.map(p =>(
        <li key={p.id}>
            {p.name}
        </li>
    ))
  return (
    <div>
        <AddProduct/>
       <h3>Products</h3>
       <ul>{renderedProducts}</ul>
    </div>
  )
}

export default ProductList
