// AuthContext.tsx
import React, {createContext, FunctionComponent, useContext, useState} from 'react';


/* Auth Context for controlling authentication state
* @state
* --- jwt: string
* --- userId: id of authenticated user
*  */
interface AuthContextType {
    isAuthenticated: boolean;
    authenticate: (jwt: string, user: string) => void;
    getUser: () => any;
    jwt: string | null;
    setUser: (user: string) => void;
    logout: () => void;
}

interface AuthProviderProps {
    children: React.ReactNode;
}

const AuthContext = createContext<AuthContextType>({
    isAuthenticated: false,
    jwt: null,
    authenticate: (jwt: string, userId: string) => {},
    setUser: (user: string) => {},
    getUser: () => {},
    logout: () => {},
});

export const useAuth = () => useContext(AuthContext);

export const AuthProvider: FunctionComponent<AuthProviderProps> = ({ children } : AuthProviderProps) => {
    //  The initial value is whether jwt has been stored inside local storage
    const [isAuthenticated, setIsAuthenticated] = useState(!!localStorage.getItem('jwt'));
    const [jwt, setJwt] = useState<string | null>(localStorage.getItem('jwt'));

    const authenticate = (jwt: string, user: string) => {
        localStorage.setItem('jwt', jwt)
        localStorage.setItem('user', user)
        setJwt(jwt)
        setIsAuthenticated(true)
    };
    const logout = () => {
        localStorage.removeItem('jwt')
        localStorage.removeItem('user')
        setJwt(jwt)
        setIsAuthenticated(false);
    }

    const getUser = () => {
        // @ts-ignore
        return JSON.parse(localStorage.getItem('user'));
    }
    const setUser = (user: string) => {
        localStorage.setItem('user', user);
    }

    return (
        <AuthContext.Provider value={{ isAuthenticated, authenticate, logout, getUser, jwt, setUser }}>
            {children} {/* Render the child components */}
        </AuthContext.Provider>
    );
};
