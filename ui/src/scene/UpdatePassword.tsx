import React, {useEffect} from "react";
import {Box, Button, Divider, Grid, Link, TextField, Typography} from "@mui/material";
import {ErrorMessage, Field, Form, Formik} from "formik";
import ErrorText from "../component/ErrorText";
import {useNavigate} from "react-router-dom";
import * as Yup from 'yup'
import useUpdatePassword from "../hooks/useUpdatePassword";
import {useNotification} from "../context/NotificationProvider";
import {useAuth} from "../context/AuthContext";

const validationSchema = Yup.object({
    username: Yup.string().required("Username is required"),
    password: Yup.string().required("Password is required"),
    newPassword: Yup.string().required('Password is required')
        .min(8, 'Password must be at least 8 characters').matches(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/, 'Password must contain at least one uppercase letter, one lowercase letter, and one digit'),
    confirmPassword: Yup.string()
        .oneOf([Yup.ref('newPassword')], 'Passwords must match')
        .required('Confirm password is required'),
});

// Form Initial Value
const initialValues = {
    username: '',
    password: '',
    newPassword: '',
    confirmPassword: ''
};

// Update password scene
const UpdatePassword: React.FC = () => {
    const navigate = useNavigate();
    const {data, loading, error, update} = useUpdatePassword();
    const {showNotification} = useNotification();
    const { logout } = useAuth();
    const onSubmit = (values: any) => {
        update({ username: values?.username, password: values?.password, newPassword: values?.newPassword });
    }

    useEffect(() => {
        if(data) {
            showNotification("Successfully updated new password! Please log in again!", "success");
            // Remove credentials
            logout();
        }
        if(error) {
            showNotification(error, "error");
        }
    }, [data, error]);

    return (
        <Grid container justifyContent="center" alignItems="center" style={{ height: '100%' }}>
            <Grid item xs={10} sm={6} md={4} style={{width: '100%', marginTop: "8em"}}>

                <Box sx={{width: "100%", display: "flex", justifyContent: "center", alignItems: "center", my: "2em", fontSize: "2em"}}>
                    <Typography variant={'h2'}>Update Your Password</Typography>
                </Box>
                <Divider sx={{mb: 6}} variant={"fullWidth"}/>
                <Formik initialValues={initialValues} validationSchema={validationSchema} onSubmit={onSubmit}>
                    <Form>
                        <Grid container spacing={5}>
                            <Grid item xs={12}>
                                <Field as={TextField} fullWidth label="Username" name="username" type="text"
                                       InputLabelProps={{
                                           shrink: true,
                                       }}/>
                                <ErrorMessage name="username" component="div" render={(errorMessage) => <ErrorText errorMessage={errorMessage}/>}/>
                            </Grid>
                            <Grid item xs={12}>
                                <Field as={TextField} fullWidth label="Password" name="password" type="password"
                                       InputLabelProps={{
                                           shrink: true,
                                       }}/>
                                <ErrorMessage name="password" component="div" render={(errorMessage) => <ErrorText errorMessage={errorMessage}/>}/>
                            </Grid>
                            <Grid item xs={12}>
                                <Field as={TextField} fullWidth label="New Password" name="newPassword" type="password"
                                       InputLabelProps={{
                                           shrink: true,
                                       }}/>
                                <ErrorMessage name="newPassword" component="div" render={(errorMessage) => <ErrorText errorMessage={errorMessage}/>}/>
                            </Grid>
                            <Grid item xs={12}>
                                <Field as={TextField} fullWidth label="Confirm New Password" name="confirmPassword" type="password"
                                       InputLabelProps={{
                                           shrink: true,
                                       }}/>
                                <ErrorMessage name="confirmPassword" component="div" render={(errorMessage) => <ErrorText errorMessage={errorMessage}/>}/>
                            </Grid>
                            <Grid item xs={12}>
                                <Button disabled={loading} type="submit" variant="contained" color="primary" fullWidth>
                                    Update
                                </Button>
                            </Grid>
                        </Grid>
                    </Form>
                </Formik>
            </Grid>
        </Grid>
    )
}

export default UpdatePassword;