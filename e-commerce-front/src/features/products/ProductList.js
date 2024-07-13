import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { fetchProducts, getAllProducts} from './productSlice'
import ProdcutPageCard from '../../components/Card/ProdcutPageCard'

const ProductList = () => {
    const dispatch = useDispatch()
    const products = useSelector(getAllProducts);
    useEffect(() =>{
        console.log('fertch:::product',products)
        dispatch(fetchProducts())
     },[dispatch])
     
    

    

    const renderedProducts = products.map(product =>(
        <ProdcutPageCard product={product}/>
    ))
  return (
    <div>
       
       <h3>Products</h3>
       <ul>{renderedProducts}</ul>
    </div>
  )
}

export default ProductList
