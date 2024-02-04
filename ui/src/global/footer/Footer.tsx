import {Box, Divider, Grid, IconButton, Typography, useMediaQuery, useTheme} from "@mui/material";
import FacebookIcon from '@mui/icons-material/Facebook';
import LinkedInIcon from '@mui/icons-material/LinkedIn';
import TwitterIcon from '@mui/icons-material/Twitter';
import YouTubeIcon from '@mui/icons-material/YouTube';
import InstagramIcon from '@mui/icons-material/Instagram';
import DropDownItems from "./DropDownItems";
import ListItems from "./ListItems";

// Icon Size
const iconSize = 36

// Social Media buttons component
const SocialMediaButtons = () => {
    return (
        <Box width={'100%'} display={'flex'} justifyContent={'center'} alignItems={'center'}>
            <IconButton
                sx={{ color: '#1877F2', fontSize: iconSize }} // Facebook blue color
                aria-label="Facebook"
                onClick={() => window.open('https://www.facebook.com/', '_blank')}
            >
                <FacebookIcon />
            </IconButton>
            <IconButton
                sx={{ color: '#0A66C2', fontSize: iconSize }} // LinkedIn blue color
                aria-label="LinkedIn"
                onClick={() => window.open('https://www.linkedin.com/in/viet-dung-tran-daniel/', '_blank')}
            >
                <LinkedInIcon />
            </IconButton>
            <IconButton
                style={{ color: '#1DA1F2', fontSize: iconSize }} // Twitter blue color
                aria-label="Twitter"
                onClick={() => window.open('https://www.twitter.com/', '_blank')}
            >
                <TwitterIcon />
            </IconButton>
            <IconButton
                style={{ color: '#FF0000', fontSize: iconSize }} // YouTube red color
                aria-label="YouTube"
                onClick={() => window.open('https://www.youtube.com/', '_blank')}
            >
                <YouTubeIcon />
            </IconButton>
            <IconButton
                style={{ color: '#E4405F', fontSize: iconSize }} // Instagram pink color
                aria-label="Instagram"
                onClick={() => window.open('https://www.instagram.com/', '_blank')}
            >
                <InstagramIcon />
            </IconButton>
        </Box>
    );
};



// Footer of the application
const Footer: React.FunctionComponent = () => {
    //  Query media using useMediaQuery from MUI
    const theme = useTheme();
    const matches = useMediaQuery(theme.breakpoints.up('md'));
    return (
        <Box width={'100%'} py={8} left={0} mb={0} color={'white'} mt={32} bgcolor={'primary.main'}>
            <Grid container color={'neutral.main'} width={'100%'} >
                <Grid item xs={12} mb={4}>
                    <SocialMediaButtons />
                </Grid>
                <Grid item xs={12} md={3} mb={2} display={'flex'} justifyContent={'start'} mx={'auto'} flexDirection={'column'} alignItems={'center'}>
                    {
                        !matches ? <DropDownItems width={'90%'} title={"About Us"} contentList={["Daniel Tran"]} /> : (<ListItems width={'60%'} title={'About Us'} contentList={["Dung Tran"]} />)
                    }
                </Grid>
                <Grid xs={12} item md={3} mb={2} display={'flex'} justifyContent={'center'} mx={'auto'} flexDirection={'column'} alignItems={'center'}>
                    {
                        !matches ? <DropDownItems width={'90%'} title={"DevOps"} contentList={["Amazon Web Services", "Jenkins", "ArgoCD"]} /> : (<ListItems width={'60%'} title={"DevOps"} contentList={["Amazon Web Services", "Jenkins", "ArgoCD"]} />)
                    }
                </Grid>
                <Grid xs={12} item md={3} mb={2} display={'flex'} justifyContent={'center'} mx={'auto'} flexDirection={'column'} alignItems={'center'}>
                    {
                        !matches ? <DropDownItems width={'90%'} title={"Technologies"} contentList={["Amazon Web Services", "Web Development", "GraphQL"]} /> : (<ListItems width={'60%'} title={'Technologies'} contentList={["Amazon Web Services", "Web Development", "GraphQL"]}  />)
                    }
                </Grid>
                <Grid xs={12} item md={3} mb={2} display={'flex'} justifyContent={'center'} mx={'auto'} flexDirection={'column'} alignItems={'center'}>
                    {
                        !matches ? <DropDownItems width={'90%'} title={"Resources"} contentList={["Read Our Blog", "Our Newsletter", "Free Guidelines"]} /> : (<ListItems width={'60%'} title={'Resources'} contentList={["Read Our Blog", "Our Newsletter", "Free Guidelines"]} />)
                    }
                </Grid>
                <Grid item xs={12} px={2} mt={4} mx={6}>
                    <Divider sx={{ bgcolor: 'neutral.main' }} flexItem variant="fullWidth" />
                    <Box width={'100%'} display={'flex'} mt={2} justifyContent={'center'} alignItems={'center'} textAlign={'center'}>
                        <Typography mx={'auto'} component={'p'}>&copy; 2024 Daniel Tran - All rights reserved</Typography>
                    </Box>
                </Grid>
            </Grid>
        </Box>
    )
}


export default Footer;