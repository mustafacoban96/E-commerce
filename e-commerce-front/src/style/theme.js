import { createTheme } from "@mui/material";

const theme = createTheme({
    
    palette:{
       mode:'light',
        commerceOrange:{
            main:'#f27a1a',
            light:'#f27a1a',
            dark:'#f27a1a',
            contrastText:'#ffff'
        },

        myBlack:{
            main:'#f27a1a',
            light:'#212121',
            dark:'#ffd600',
            contrastText:'#ffff'
        },

        footerColor:{
            main:'#f5f5f5',
            light:'#f5f5f5',
            dark:'#171616',
            contrastText:'#ffff'
        },
        saleColor:{
            main:'#c9002e',//sale area background color
            light:'#fff',
            dark:'#ffd600',
            textColor:'#fff'
        },
        individualPic:{
            main:'#fff',
            light:'#fff',
            dark:'#ffd600',
            textColor:'#fff'
        }
    }
});




export default theme;