import React, {useEffect} from 'react';
import Box from '@mui/material/Box';
import {
    Avatar,
    Button,
    Divider,
    Grid,
    Paper,
    TextField, Tooltip,
    Typography,
    useMediaQuery,
    useTheme
} from "@mui/material";
import {ErrorMessage, Field, Form, Formik} from "formik";
import ErrorText from "../component/ErrorText";
import * as Yup from 'yup';
import {useAuth} from "../context/AuthContext";
import useUpdateUser from "../hooks/useUpdateUser";
import {useNotification} from "../context/NotificationProvider";
import {useNavigate} from "react-router-dom";

// Validate Form Schema using Yup
const validationSchema = Yup.object({
    firstname: Yup.string().required('First Name is required'),
    lastname: Yup.string().required('Last Name is required'),
    email: Yup.string().email('Invalid email address').required('Email is required'),
    dateOfBirth: Yup.date().required('Date of Birth is required').test('is-adult', 'You must be 18 or older', function (value) {
        const dob = new Date(value);
        const today = new Date();
        const age = today.getFullYear() - dob.getFullYear();
        return age >= 18;
    }),
});

const Profile: React.FC = () => {
    const { getUser, jwt, setUser } = useAuth();
    const theme = useTheme();
    const matches = useMediaQuery(theme.breakpoints.up('md'));
    const { data, loading, error, update } = useUpdateUser();
    const { showNotification } = useNotification();
    const user = getUser();
    const navigate = useNavigate();

    // On submit update information form
    const onSubmit = (values: any) => {
        update({
            username: user?.username,
            ...values
        }, jwt)
    }

    useEffect(() => {
        if(data) {
            setUser(JSON.stringify(data))
            showNotification("Successfully updated!", "success")
        }
        if(error) {
            showNotification(error, 'error')
        }
    }, [data, error]);

    return (
        <Grid container justifyContent="center" alignItems="center" style={{ height: '100%' }}>
            <Grid item xs={10} sm={6} md={4} style={{width: '100%', marginTop: "10em"}}>
                <Paper sx={{padding: 4}}>
                    <Box>
                        <Box sx={{display: 'flex', flexDirection: 'row', alignItems: 'center'}}>
                            <Avatar sx={matches ? {height: 120, width: 120} : {height: 80, width: 80}}/>
                            <Box sx={{display: 'flex', justifyContent: 'center', alignItems: 'flex-start', flexDirection: 'column', px: 4, overflow: 'hidden',  }}>
                                <Typography variant={ matches ? 'h2' : 'h3'}>{user?.firstname} {user?.lastname}</Typography>
                                <Tooltip title={user?.username}>
                                    <Typography sx={{fontStyle: 'italic', color: "grey", fontSize: "16px", cursor: "pointer" ,textOverflow: 'ellipsis', whiteSpace: 'nowrap',}} >{user?.username}</Typography>
                                </Tooltip>
                            </Box>
                        </Box>
                        <Typography variant={'h3'} sx={{mt: 6}}>Account Details</Typography>
                        <Divider sx={{mb: 8, mt: 2}}/>
                        <Grid>
                            <Formik initialValues={user} validationSchema={validationSchema} onSubmit={onSubmit}>
                                <Form>
                                    <Grid container spacing={5}>
                                        <Grid item xs={6}>
                                            <Field as={TextField} fullWidth label="First Name" name="firstname"
                                                   InputLabelProps={{
                                                       shrink: true,
                                                   }}/>
                                            <ErrorMessage name="firstname" component="div" render={(errorMessage) => <ErrorText errorMessage={errorMessage}/>}/>
                                        </Grid>
                                        <Grid item xs={6}>
                                            <Field as={TextField} fullWidth label="Last Name" name="lastname"
                                                   InputLabelProps={{
                                                       shrink: true,
                                                   }}/>
                                            <ErrorMessage name="lastname" component="div" render={(errorMessage) => <ErrorText errorMessage={errorMessage}/>}/>
                                        </Grid>
                                        <Grid item xs={12}>
                                            <Field as={TextField} fullWidth label="Email" name="email" type="text"
                                                   InputLabelProps={{
                                                       shrink: true,
                                                   }}/>
                                            <ErrorMessage name="email" component="div" render={(errorMessage) => <ErrorText errorMessage={errorMessage}/>}/>
                                        </Grid>
                                        <Grid item xs={12}>
                                            <Field as={TextField} fullWidth label="Date Of Birth" name="dateOfBirth" type="date"
                                                   InputLabelProps={{
                                                       shrink: true,
                                                   }}/>
                                            <ErrorMessage name="dateOfBirth" component="div" render={(errorMessage) => <ErrorText errorMessage={errorMessage}/>}/>
                                        </Grid>
                                        <Grid item xs={12} sx={{display: 'flex', flexDirection: 'row'}}>
                                            <Button type="button" variant="contained" color="secondary" onClick={() => navigate('/update-password')}>
                                                New Password
                                            </Button>
                                            <Button disabled={loading} sx={{ml: 4 }} type="submit" variant="contained" color="primary" >
                                                Update
                                            </Button>
                                        </Grid>
                                    </Grid>
                                </Form>
                            </Formik>
                        </Grid>
                    </Box>
                </Paper>
            </Grid>
        </Grid>
    );
};

export default Profile;
