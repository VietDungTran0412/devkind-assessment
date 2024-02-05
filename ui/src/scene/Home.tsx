import {Box, Button, Divider, Grid, Typography, useMediaQuery, useTheme} from "@mui/material";
import { useNavigate } from "react-router-dom";
import SystemUpdateAltIcon from '@mui/icons-material/SystemUpdateAlt';
import LockIcon from '@mui/icons-material/Lock';
import ContentPasteSearchIcon from '@mui/icons-material/ContentPasteSearch';
import LoginIcon from '@mui/icons-material/Login';
import {shades} from "../theme";

// Home page
const Home: React.FC = () => {
    const navigate = useNavigate();
    const theme = useTheme();
    const matches = useMediaQuery(theme.breakpoints.up('md'));
    return (
        <Box width={'100%'} height={'100%'} >
            {/* Cover background for the home page */}
            <Box width={'100%'} sx={{ backgroundImage: `url(${require('../assets/bgprimary.jpg')})`, height: '36em',backgroundAttachment: 'fixed', backgroundBlendMode: 'multiply', backgroundColor: 'rgba(0,0,0,0.7)'}} color={'neutral.light'} pt={24} pb={16} display={'flex'} justifyContent={'center'} >
                <Box width={{lg: '50%', sx: '100%', color: shades.neutral[400]}} textAlign={'center'}>
                    <Typography variant={matches ? 'h1' : 'h2'}  fontWeight={'bold'}>Full-Stack Modern Web Application</Typography>
                    <Typography variant="h5" component={'p'} mt={4} fontSize={matches ? 20 : 14}>
                        The web application has been created as a core requirement on behalf of the recruitment process at Devkind. The application has been fully developed and Owned by Dung Tran or Daniel Tran
                    </Typography>
                        <Box mx={'auto'} width={{xl: '50%',lg: '70%', md: '70%'}} px={2} display={'flex'} mt={3} justifyContent={'center'} alignItems={'center'}>
                        <Button sx={{width: '16em'}} startIcon={<LoginIcon/>}  color="secondary" onClick={() => navigate('/log-in')} variant="contained" type="button">Getting Started</Button>
                    </Box>
                </Box>
            </Box>
            {/* Detail information of the web page */}
            <Box display={'flex'} flexDirection={'column'} justifyContent={'center'} alignItems={'center'} textAlign={'center'} mb={20}>
                <Typography variant="h2" mb={3} mt={12}>Our Features</Typography>
                <Box sx={{ width: '75%' }}>
                    <Grid container spacing={2} sx={{mt: 4}}>
                        <Grid item xs={12} md={4} display={'flex'} justifyContent={'flex-start'} flexDirection={'column'} alignItems={'center'} textAlign={'center'}>
                            <LockIcon fontSize="large" sx={{ fontSize: '4em'}}/>
                            <Box width={'100%'} mt={2}>
                                <Typography variant="h3" fontWeight={'bold'}>Security</Typography>
                            </Box>
                            <Box width={'100%'} mt={1} px={4}>
                                <Typography >Enhance the safe of your experience by implementing authentication and authorization using Json Web Token</Typography>
                            </Box>
                        </Grid>
                        <Grid item xs={12} md={4} display={'flex'} justifyContent={'flex=start'} flexDirection={'column'} alignItems={'center'} textAlign={'center'}>
                            <SystemUpdateAltIcon fontSize="large" sx={{ fontSize: '4em'}}/>
                            <Box width={'100%'} mt={2}>
                                <Typography variant="h3" fontWeight={'bold'}>Easy Update</Typography>
                            </Box>
                            <Box width={'100%'} mt={1} px={4}>
                                <Typography>Easy and Safety to update your critical information about your account</Typography>
                            </Box>
                        </Grid>
                        <Grid item xs={12} md={4} display={'flex'} justifyContent={'flex-start'} flexDirection={'column'} alignItems={'center'} textAlign={'center'}>
                            <ContentPasteSearchIcon fontSize="large" sx={{ fontSize: '4em'}}/>
                            <Box width={'100%'} mt={2}>
                                <Typography variant="h3" fontWeight={'bold'}>Tracing Activity</Typography>
                            </Box>
                            <Box width={'100%'} mt={1} px={4}>
                                <Typography>Easy tracing the modifications which had been made on behalf of your account</Typography>
                            </Box>
                        </Grid>
                    </Grid>
                </Box>

            </Box>
        </Box>
    )
}


export default Home;