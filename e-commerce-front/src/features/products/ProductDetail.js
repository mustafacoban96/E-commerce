import React, { useEffect, useState } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { selectProductById, fetchProductById } from './productSlice'
import { useParams } from 'react-router'
import { Box, Card, CardActionArea, CardMedia, Container, Grid, Stack, Typography } from '@mui/material'
import ProductList from './ProductList'

const ProductDetail = () => {
    const { productId } = useParams()
    const dispatch = useDispatch()
    const product = useSelector((state) => selectProductById(state, productId))
    const [loading, setLoading] = useState(true)
//     <div key={product.id}>
//     <p>Name: {product.name}</p>
//     <p>Description: {product.description}</p>
//     <p>Price: {product.price}</p>
//     <p>Stock: {product.stock}</p>
// </div>

const productPhoto = [
    'https://lp2.hm.com/hmgoepprod?set=source[/22/9b/229b87236e1e7400a0cd0ba0da29955ba89b365f.jpg],origin[dam],category[],type[LOOKBOOK],res[z],hmver[1]&call=url[file:/product/main]',
    'https://lp2.hm.com/hmgoepprod?set=source[/81/58/8158f42892dad8e81f9c58e74faa1d44fc7b3a9f.jpg],origin[dam],category[],type[LOOKBOOK],res[z],hmver[1]&call=url[file:/product/main]',
    'https://lp2.hm.com/hmgoepprod?set=source[/7a/8d/7a8d452e18cf2a48f01124cdc051d0fa383e0f9d.jpg],origin[dam],category[ladies_dresses_shortdresses],type[LOOKBOOK],res[z],hmver[1]&call=url[file:/product/main]',
    'https://lp2.hm.com/hmgoepprod?set=source[/ac/74/ac7495fa2aa2a0239a36e19102381cee9b5a0466.jpg],origin[dam],category[],type[LOOKBOOK],res[z],hmver[1]&call=url[file:/product/main]',
    'https://lp2.hm.com/hmgoepprod?set=source[/fd/96/fd96ea3313d394c33334ce82d94e583c2d151fbe.jpg],origin[dam],category[],type[LOOKBOOK],res[z],hmver[1]&call=url[file:/product/main]',
    'https://lp2.hm.com/hmgoepprod?set=source[/4a/5c/4a5ce8d61808d8c35ed90ce3ab4833d52dcfbc4f.jpg],origin[dam],category[],type[LOOKBOOK],res[z],hmver[1]&call=url[file:/product/main]',
]

    useEffect(() => {
        if (!product) {
            dispatch(fetchProductById(productId)).finally(() => setLoading(false))
        } else {
            setLoading(false)
        }
    }, [dispatch, product, productId])

    if (loading) {
        return <p>Loading...</p>
    }

    if (!product) {
        return <p>Product not found</p>
    }

    return (
        <>
       
        <Box sx={{backgroundColor:'red'}}>
            <Grid container sx={{justifyContent:'center'}}>
                <Grid 
                item 
                sx={{backgroundColor:'pink',justifyContent:'center' ,display:'flex',alignContent:'center'}}
                xs={6}
                >
                    <CardMedia
                        component="img"
                        image={'https://lp2.hm.com/hmgoepprod?set=source[/22/9b/229b87236e1e7400a0cd0ba0da29955ba89b365f.jpg],origin[dam],category[],type[LOOKBOOK],res[z],hmver[1]&call=url[file:/product/main]'}
                        alt="green iguana"
                        sx={{maxWidth:380,marginY:'5px'}}
                    />
                </Grid>
                <Grid 
                item 
                sx={{justifyContent:'center',display:'flex',backgroundColor:'lightblue',alignItems:'end'}}
                xs={0.5}
                >
                   <Stack direction={'column'} spacing={0.2}>
                    {
                        productPhoto.map((p) =>
                            <Card elevation={0} sx={{ maxWidth: 40}}>
                            <CardActionArea>
                                <CardMedia
                                component="img"
                                    image={p}
                                    alt="green iguana" 
                                />
                            </CardActionArea>
                        </Card>
                        )
                    }
                   </Stack>
                </Grid>
                <Grid 
                item 
                sx={{backgroundColor:'green'}}
                xs={5}
                >
                    <p>{product.name}</p>
                    <p>{product.description}</p>
                    <p>{product.price}</p>
                    
                </Grid>
            </Grid>
        </Box>
        
        
        </>
        
    )
}

export default ProductDetail
