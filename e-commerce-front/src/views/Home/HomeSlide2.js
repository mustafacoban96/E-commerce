import React, { useState } from 'react';
import { Box, Container, Grid, IconButton } from '@mui/material';
import { data } from '../../data';
import ArrowForwardIcon from '@mui/icons-material/ArrowForward';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import MyCard from '../../components/Card/MyCard';
import CircleIcon from '@mui/icons-material/Circle';
import { Link } from 'react-router-dom';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import { useTheme } from '@emotion/react';
//npm install react-slick slick-carousel (for slider)
const NextArrow = ({ onClick,mTextColor }) => {
  return (
    <IconButton
      onClick={onClick}
      sx={{
        display:{xs:'none',sm:'inline-flex'},
        position: 'absolute',
        top: '50%',
        right:'-25px',
        transform: 'translateY(-50%)',
        zIndex: 1,
      }}
    >
      <ArrowForwardIcon sx={{ fontSize: '0.9em',color: `${mTextColor}` }} />
    </IconButton>
  );
};

const PrevArrow = ({ onClick,mTextColor }) => {
  return (
    <IconButton
      onClick={onClick}
      sx={{
        display:{xs:'none',sm:'inline-flex'},
        position: 'absolute',
        top: '50%',
        left: '-25px',
        transform: 'translateY(-50%)',
        zIndex: 1,
      }}
    >
      <ArrowBackIcon sx={{ fontSize: '0.9em', color: `${mTextColor}` }} />
    </IconButton>
  );
};

const HomeSlide2 = () => {
    const theme = useTheme();
    const [mode] = useState(theme.palette.mode);
    const myTextColor= (mode) =>{
        return (mode === 'light' ? theme.palette.myBlack.light : theme.palette.myBlack.dark)
      }
    const settings = {
        dots: true,
        infinite: true,
        speed: 500,
        slidesToShow: 4,
        slidesToScroll: 4,
        nextArrow: <NextArrow mTextColor={myTextColor(mode)}/>,
        prevArrow: <PrevArrow mTextColor={myTextColor(mode)}/>,
        responsive: [
        {
            breakpoint: 1024,
            settings: {
            slidesToShow: 4,
            slidesToScroll: 4,
            },
        },
        {
            breakpoint: 600,
            settings: {
            slidesToShow: 3,
            slidesToScroll: 3,
            },
        },
        {
            breakpoint: 480,
            settings: {
            slidesToShow: 2,
            slidesToScroll: 2,
            },
        },
        ],
        appendDots: dots => (
        <Box
            sx={{
                display:'flex',
                justifyContent:'center',
                bottom: '-30px',
                display: 'flex',
                width: '100%',
               
            }}
        >
            <ul style={{margin: '0px', padding: '0px', display: 'flex', justifyContent:'center'}}> {dots} </ul>
        </Box>
        ),
        customPaging: (i) => (
        <IconButton>
            <CircleIcon sx={{ fontSize: '0.5em'}} />
        </IconButton>
        ),
    };
  
  return (
    <Container sx={{ marginY: '5px', position: 'relative', paddingBottom: '50px' }}>
      <Slider {...settings}>
        {data.map((p, index) => (
        <Box key={index} sx={{paddingLeft:'22px'}}>
            <Link to={'/products'} key={index} style={{ textDecoration: 'none' }}>
              <MyCard
                id={p.id}
                imgSource={p.src}
                description={p.description}
                price={p.price}
              />
              </Link>
        </Box>
          
        ))}
      </Slider>
    </Container>
  );
};

export default HomeSlide2;
