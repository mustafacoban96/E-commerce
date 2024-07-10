import { useTheme } from '@emotion/react'
import { Box, Link, List, ListItem, Stack, Typography, styled } from '@mui/material'
import React, { useState } from 'react'

const Footer = () => {
    console.log('footer')
    const theme = useTheme()
    const [mode] = useState(theme.palette.mode);
    const myTextColor= (mode) =>{
        return (mode === 'light' ? theme.palette.myBlack.light : theme.palette.myBlack.dark)
      }
    const myFooterBackgroundColor = (mode) =>{
        return (mode === 'light' ? theme.palette.footerColor.light : theme.palette.footerColor.dark)
    }


    const FooterLink = styled(Link)(({theme}) =>({
        fontSize:'0.65em',
        textDecoration:'none',
        color:`${myTextColor(mode)}`,
        [theme.breakpoints.up('sm')]: {
            fontSize: '0.7em',
        },
        [theme.breakpoints.up('md')]: {
            fontSize: '0.8em',
        }
    }))

  return (
    <Box 
    sx={{
        width:'100%',
        padding:'15px',
        mt:'10px',
        backgroundColor:`${myFooterBackgroundColor(mode)}`

    }}
    >
      <Stack 
      spacing={1} 
      direction='row'
      sx={{flexWrap:'wrap',justifyContent:'left'}}
      >
        <List sx={{lineHeight:'0.9em'}}>
            <ListItem>
                <Typography variant='p' 
                sx={{
                    fontWeight:'bold',
                    fontSize:{xs:'0.75em',sm:'0.85em',md:'0.95em'}
                }}
                >HELP</Typography>
            </ListItem>
            <ListItem>
                <FooterLink href='#' underline='hover'>ZARA ACCOUNT</FooterLink>
            </ListItem>
            <ListItem>
                <FooterLink href='#' underline='hover'>PRODUCTS</FooterLink>
            </ListItem>
            <ListItem>
                <FooterLink href='#' underline='hover'>GIFT OPTIONS</FooterLink>
            </ListItem>
            <ListItem>
                <FooterLink href='#' underline='hover'>POSTS</FooterLink>
            </ListItem>
            <ListItem>
                <FooterLink href='#' underline='hover'>ORDERS</FooterLink>
            </ListItem>
            <ListItem>
                <FooterLink href='#' underline='hover'>PAYMENT AND FEES</FooterLink>
            </ListItem>
            <ListItem>
                <FooterLink href='#' underline='hover'>ENCHANGES, RETURNS AND REFUNDS</FooterLink>
            </ListItem>
        </List>
        <List sx={{lineHeight:'0.9em'}}>
            <ListItem>
                <Typography variant='p' 
                sx={{
                    fontWeight:'bold',
                    fontSize:{xs:'0.75em',sm:'0.85em',md:'0.95em'}
                }}
                >FOLLOW US</Typography>
            </ListItem>
            <ListItem>
                <FooterLink href='#' underline='hover'>INSTGRAM</FooterLink>
            </ListItem>
            <ListItem>
                <FooterLink href='#' underline='hover'>FACEBOOK</FooterLink>
            </ListItem>
            <ListItem>
                <FooterLink href='#' underline='hover'>X</FooterLink>
            </ListItem>
            <ListItem>
                <FooterLink href='#' underline='hover'>PINTEREST</FooterLink>
            </ListItem>
            <ListItem>
                <FooterLink href='#' underline='hover'>YOUTUBE</FooterLink>
            </ListItem>
            <ListItem>
                <FooterLink href='#' underline='hover'>TIKTOK</FooterLink>
            </ListItem>
            
        </List>
        <List sx={{lineHeight:'0.9em'}}>
            <ListItem>
                <Typography variant='p' 
                sx={{
                    fontWeight:'bold',
                    fontSize:{xs:'0.75em',sm:'0.85em',md:'0.95em'}
                }}
                >COMPANY</Typography>
            </ListItem>
            <ListItem>
                <FooterLink href='#' underline='hover'>ABOUT US</FooterLink>
            </ListItem>
            <ListItem>
                <FooterLink href='#' underline='hover'>JOIN LIFE</FooterLink>
            </ListItem>
            <ListItem>
                <FooterLink href='#' underline='hover'>OFFICES</FooterLink>
            </ListItem>
            <ListItem>
                <FooterLink href='#' underline='hover'>STORES</FooterLink>
            </ListItem>
            <ListItem>
                <FooterLink href='#' underline='hover'>WORK WITH US</FooterLink>
            </ListItem>
            
        </List>
        <List sx={{lineHeight:'0.9em'}}>
            <ListItem>
                <Typography variant='p' 
                sx={{
                    fontWeight:'bold',
                    fontSize:{xs:'0.75em',sm:'0.85em',md:'0.95em'}
                }}
                >POLICIES</Typography>
            </ListItem>
            <ListItem>
                <FooterLink href='#' underline='hover'>PRIVACY POLICY</FooterLink>
            </ListItem>
            <ListItem>
                <FooterLink href='#' underline='hover'>TERMS OF PURCHASE</FooterLink>
            </ListItem>
            <ListItem>
                <FooterLink href='#' underline='hover'>RIGHT OF WITHDRAWAL</FooterLink>
            </ListItem>
            <ListItem>
                <FooterLink href='#' underline='hover'>GIFT CARD TERMS</FooterLink>
            </ListItem>
            <ListItem>
                <FooterLink href='#' underline='hover'>COOKIE SETTINGS</FooterLink>
            </ListItem>
            
        </List>
      </Stack>
    </Box>
  )
}

export default Footer
