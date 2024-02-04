import React, {useEffect} from "react";
import {Box, Button, Divider, Grid, Link, TextField, Typography} from "@mui/material";
import {ErrorMessage, Field, Form, Formik} from "formik";
import ErrorText from "../component/ErrorText";
import {useNavigate} from "react-router-dom";
import useLogIn from "../hooks/useLogIn";
import {useAuth} from "../context/AuthContext";
import {useNotification} from "../context/NotificationProvider";


// Form Initial Value
const initialValues = {
    username: '',
    password: ''
};

const LogIn: React.FunctionComponent = () => {
    const navigate = useNavigate();
    const { data, loading, error, logIn } = useLogIn();
    const { authenticate } = useAuth();
    const {showNotification} = useNotification();
    const onSubmit = (values: any) => {
        logIn(values);
    }

    useEffect(() => {
        if(data) {
            authenticate(data?.jwt, JSON.stringify(data?.user));
            showNotification(`Welcome back ${data?.user?.firstname} ${data?.user?.lastname}`, "success");
            navigate("/profile")
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
                    <Typography variant={'h2'}>Welcome To Our System</Typography>
                </Box>
                <Divider sx={{mb: 6}} variant={"fullWidth"}/>
                <Formik initialValues={initialValues}  onSubmit={onSubmit}>
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
                                <Link sx={{cursor: 'pointer'}} onClick={() => navigate('/register')}>Created your new account?</Link>
                            </Grid>
                            <Grid item xs={12}>
                                <Button disabled={loading} type="submit" variant="contained" color="primary" fullWidth>
                                    Log In
                                </Button>
                            </Grid>
                        </Grid>
                    </Form>
                </Formik>
            </Grid>
        </Grid>
    )
}


export default LogIn;