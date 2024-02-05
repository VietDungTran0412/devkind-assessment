import React, {useEffect} from 'react';
import {TextField, Button, Grid, Box, Typography, Divider, Link} from '@mui/material';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import ErrorText from "../component/ErrorText";
import useRegister from "../hooks/useRegister";
import {useNotification} from "../context/NotificationProvider";
import {useAuth} from "../context/AuthContext";
import {useNavigate} from "react-router-dom";

// Validate Form Schema using Yup
const validationSchema = Yup.object({
    firstname: Yup.string().required('First Name is required').matches(/^[a-zA-Z]+(?:[-\s][a-zA-Z]+)*$/, 'Firstname requires alphabetical characters and hyphens!'),
    lastname: Yup.string().required('Last Name is required').matches(/^[a-zA-Z]+(?:[-\s][a-zA-Z]+)*$/, 'Lastname requires alphabetical characters and hyphens!'),
    email: Yup.string().email('Invalid email address').required('Email is required').matches(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/, 'Invalid email address'),
    dateOfBirth: Yup.date().required('Date of Birth is required').test('is-adult', 'You must be 18 or older', function (value) {
        const dob = new Date(value);
        const today = new Date();
        const age = today.getFullYear() - dob.getFullYear();
        return age >= 18;
    }),
    password: Yup.string().required('Password is required').min(8, 'Password must be at least 8 characters').matches(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/, 'Password must contain at least one uppercase letter, one lowercase letter, and one digit'),
    username: Yup.string().required('Username is required').min(8, 'Username must be at least 8 characters').matches(/^[a-zA-Z0-9]{8,}$/, 'Username must contain only alphanumeric characters and be at least 8 characters long'),
});

// Form Initial Value
const initialValues = {
    firstname: '',
    lastname: '',
    email: '',
    dateOfBirth: '',
    username: '',
    password: ''
};

const Register: React.FunctionComponent = () => {
    const { data, loading, register, error } = useRegister();
    const { showNotification } = useNotification();
    const { authenticate } = useAuth();
    const navigate = useNavigate();
    const onSubmit = (values: any) => {
        register(values);
    };

    useEffect(() => {
        // Show notification whenever successfully register or error
        if(data) {
            authenticate(data?.jwt, JSON.stringify(data?.user));
            showNotification("Successfully register new account!", "success");
            navigate("/profile");
        }
        if(error) {
            showNotification(error, "error");
        }
    }, [data, error])

    return (
        <Grid container justifyContent="center" alignItems="center" style={{ height: '100%' }}>
            <Grid item xs={10} sm={6} md={4} style={{width: '100%', marginTop: "8em"}}>
                {/*<Paper elevation={3} style={{ padding: '50px' }}>*/}
                    <Box sx={{width: "100%", display: "flex", justifyContent: "center", alignItems: "center", my: "2em", fontSize: "2em"}}>
                        <Typography variant={'h2'}>Register Form</Typography>
                    </Box>
                <Divider sx={{mb: 6}} variant={"fullWidth"}/>
                <Formik initialValues={initialValues} validationSchema={validationSchema} onSubmit={onSubmit}>
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
                                <Field as={TextField} fullWidth label="Username" name="username" type="text"
                                       InputLabelProps={{
                                           shrink: true,
                                       }}/>
                                <ErrorMessage name="username" component="div" render={(errorMessage) => <ErrorText errorMessage={errorMessage}/>}/>
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
                            <Grid item xs={12}>
                                <Field as={TextField} fullWidth label="Password" name="password" type="password"
                                       InputLabelProps={{
                                           shrink: true,
                                       }}/>
                                <ErrorMessage name="password" component="div" render={(errorMessage) => <ErrorText errorMessage={errorMessage}/>}/>
                            </Grid>
                            <Grid item xs={12}>
                                <Link sx={{cursor: 'pointer'}} onClick={() => navigate('/log-in')}>Already have an account?</Link>
                            </Grid>
                            <Grid item xs={12}>
                                <Button disabled={loading} type="submit" variant="contained" color="primary" fullWidth>
                                    Register
                                </Button>
                            </Grid>
                        </Grid>
                    </Form>
                </Formik>
            </Grid>
        </Grid>
    );
};

export default Register;
