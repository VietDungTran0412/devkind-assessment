import {useState} from "react";

const API_URL = process.env.REACT_APP_API_URL;

interface LogInProps {
    username: string,
    password: string
}

const useLogIn = () => {
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
    const [data, setData] = useState<any>(null);

    const logIn = async (values: LogInProps) => {
        setData(null)
        setError(null)
        setLoading(true)
        await fetch(`${API_URL}/auth/login`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(values)
        }).then(res => {
            if(res.status === 409 || res.status === 401) {
                setError("Username or password has been incorrect!")
            }
            return res.json();
        })
            .then(res => {
                setData(res.data)
            })
            .catch(er => setError("Unexpected internal error!"))
            .finally(() => setLoading(false));
    }
    return { data, error, loading, logIn }
}

export default useLogIn;