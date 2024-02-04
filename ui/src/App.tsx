import React, {useEffect} from 'react';
import {BrowserRouter, Route, Routes, useLocation, useNavigate} from "react-router-dom";
import Home from "./scene/Home";
import ProtectedRoute from "./component/ProtectedRoute";
import NavBar from "./global/nav-bar/NavBar";
import Footer from "./global/footer/Footer";
import Register from "./scene/Register";
import LogIn from "./scene/LogIn";
import Profile from "./scene/Profile";
import UpdatePassword from "./scene/UpdatePassword";
import {useNotification} from "./context/NotificationProvider";
import {jwtDecode} from "jwt-decode";
import {useAuth} from "./context/AuthContext";

// Scroll to top when new page to be appeared
const ScrollToTop = () => {
    const { pathname } = useLocation();
    useEffect(() => {
        window.scroll(0,0)
    }, [pathname]);
    return null;
}

/* Check the valid expired time of jwt */
const CheckJwt = () => {
    const { showNotification } = useNotification();
    const { pathname } = useLocation();
    const { jwt, isAuthenticated, logout } = useAuth() ;
    useEffect(() => {
        if(jwt !== null && isAuthenticated) {
            const decodedToken = jwtDecode(jwt);
            const currentTime = Date.now() / 1000;
            if (decodedToken.exp && currentTime > decodedToken.exp) {
                showNotification("Your session has been expired!", "info")
                logout();
            }
        }
    }, [pathname]);
    return null;
}

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <ScrollToTop/>
          <CheckJwt/>
          <NavBar/>
              <Routes>
                  <Route path={'/'} element={<Home/>}/>
                  <Route path={'profile'} element={<ProtectedRoute element={<Profile/>}/>}/>
                  <Route path={'update-password'} element={<ProtectedRoute element={<UpdatePassword/>}/>}/>
                  <Route path={'register'} element={<Register/>}/>
                  <Route path={'log-in'} element={<LogIn/>}/>
              </Routes>
          <Footer/>
      </BrowserRouter>
    </div>
  );
}

export default App;
