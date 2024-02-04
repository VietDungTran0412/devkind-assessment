// ProtectedRoute.tsx
import React, {FunctionComponent} from 'react';
import {Navigate} from 'react-router-dom';
import {useAuth} from "../context/AuthContext";


interface ProtectedRouteProps {
    element: React.ReactNode
}

/*
* Protected Route for route that requires Authorization
* */
const ProtectedRoute: FunctionComponent<ProtectedRouteProps> = ({element}) => {
    const { isAuthenticated } = useAuth();

    return isAuthenticated ? <>{element}</> : <Navigate to={'/log-in'}/>
};

export default ProtectedRoute;
