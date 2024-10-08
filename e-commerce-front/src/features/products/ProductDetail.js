import React, { useEffect, useState } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { selectProductById, fetchProductById } from './productSlice'
import { useParams } from 'react-router'
import { Box, Button, Card, CardActionArea, CardMedia, Checkbox,Grid,ImageList, ImageListItem, Stack,Typography } from '@mui/material'
import { Favorite, FavoriteBorder } from '@mui/icons-material'
import { pink } from '@mui/material/colors'
import HomeSlide2 from '../../views/Home/HomeSlide2'
import { addCartItem } from '../cart/cartSlice'

const label = { inputProps: { 'aria-label': 'Checkbox demo' } };

const ProductDetail = () => {
    const { productId } = useParams()
    const dispatch = useDispatch()
    const product = useSelector((state) => selectProductById(state, productId))
    const [loading, setLoading] = useState(true)
    
    const [productMain, setProductMain] = useState('https://lp2.hm.com/hmgoepprod?set=source[/22/9b/229b87236e1e7400a0cd0ba0da29955ba89b365f.jpg],origin[dam],category[],type[LOOKBOOK],res[z],hmver[1]&call=url[file:/product/main]')
    
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
       
        <Box sx={{display:{xs:'none',lg:'block'},marginY:'15px'}}>
            <Grid container sx={{justifyContent:'center'}}>
                <Grid 
                item 
                sx={{justifyContent:'center' ,display:'flex',alignContent:'center'}}
                xs={4}
                >
                    <CardMedia
                        component="img"
                        image={productMain}
                        alt="green iguana"
                        sx={{maxWidth:380,marginY:'5px'}}
                    />
                </Grid>
                <Grid 
                item 
                sx={{justifyContent:'center',display:'flex',alignItems:'end'}}
                
                >
                   <Stack direction={'column'} spacing={0.2}>
                    {
                        productPhoto.map((p,index) =>
                            <Card key={index} onClick={() => setProductMain(p)} elevation={0} sx={{ maxWidth: 40}}>
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
                sx={{display:'flex',justifyContent:'center'}}
                xs={5}

                >
                    <Box sx={{display:'flex',flexDirection:'column', justifyContent:'space-around',width:'70%',border:'1px solid black'}}>
                        {/* Product Detail Info */}
                    <Stack direction={'column'} spacing={3} 
                    sx={{p:'5px',margin:'12px'}}>
                        <Typography 
                        variant='p' 
                        sx={{
                            fontWeight:'bold',
                            fontSize:'2em'
                            
                        }}
                        >{product.name}</Typography>
                        <Typography variant='p' sx={{fontFamily:'sans-serif'}}>
                        Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.
                        </Typography>
                        <Typography variant='h6'>
                            {product.price} ₺
                        </Typography>
                        
                    </Stack>
                       
                        <Stack 
                        direction={'row'} 
                        
                        sx={{display:'flex',justifyContent:'space-around'}}>
                            <Button color='error' variant='contained' sx={{width:'80%'}}
                            disabled={product.stock !== 0 ? false : true}
                            onClick={() => dispatch(addCartItem(productId))}>{product.stock !== 0 ? 'ADD TO CART' : 'SOLD OUT'}</Button>
                            <Checkbox 
                            {...label} 
                            icon={<FavoriteBorder />} 
                            checkedIcon={<Favorite />}
                            sx={{
                                color: pink[800],
                                '&.Mui-checked': {
                                  color: pink[600],
                                },
                              }}
                            />
                        </Stack>
                    </Box>
                    
                </Grid>
            </Grid>
        </Box>
                                
        {/* (sm)600 px<screen< 900(md) px  */}
    <Box sx={{
    display: {md:'flex',lg:'none'},
    flexDirection: { xs: 'column', md: 'row' },
    justifyContent: 'space-around',
    p:'20px'
}}>
    <Box sx={{
        width: { xs: '100%', md: '50%' },
        overflowY: 'auto',
        height: { xs: 'auto', md: '70vh' }
    }}>
        <ImageList variant="masonry" cols={2} gap={8}>
            {productPhoto.map((item, index) => (
                <ImageListItem key={index}>
                    <img
                        srcSet={`${item}?w=248&fit=crop&auto=format&dpr=2 2x`}
                        src={`${item}?w=248&fit=crop&auto=format`}
                        loading="lazy"
                    />
                </ImageListItem>
            ))}
        </ImageList>
    </Box>
    <Box sx={{
        width: { xs: '100%', md: '40%' },
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'space-around',
        border: '1px solid black',
        position: { md: 'sticky' },
        top: 0,
        marginY:'10px'
    }}>
        {/* Product Detail Info */}
        <Stack direction={'column'} spacing={3} sx={{ p: '5px', margin: '12px' }}>
            <Typography
                variant='p'
                sx={{
                    fontWeight: 'bold',
                    fontSize: '2em'
                }}
            >
                {product.name}
            </Typography>
            <Typography variant='p' sx={{ fontFamily: 'sans-serif' }}>
                Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.
            </Typography>
            <Typography variant='h6'>
                {product.price} ₺
            </Typography>
        </Stack>

        <Stack direction={'row'} sx={{ display: 'flex', justifyContent: 'space-around' ,p:2}}>
            <Button color='error' variant='contained' sx={{ width: '80%' }}
            disabled={product.stock !== 0 ? false : true} 
            onClick={() => dispatch(addCartItem(productId))}>{product.stock !== 0 ? 'ADD TO CART' : 'SOLD OUT'}</Button>
            <Checkbox
                {...label}
                icon={<FavoriteBorder />}
                checkedIcon={<Favorite />}
                sx={{
                    color: pink[800],
                    '&.Mui-checked': {
                        color: pink[600],
                    },
                }}
            />
        </Stack>
    </Box>
</Box>
    <Box sx={{padding:'5px',margin:5}}>
        <Typography variant='h5' sx={{textAlign:'left',fontWeight:'bold'}}>Similar Product</Typography>
        <HomeSlide2/>
    </Box>
        
        
    </>
        
    )
}

export default ProductDetail
