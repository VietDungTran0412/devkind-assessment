import {useState} from "react";

const API_URL = process.env.REACT_APP_API_URL;

interface UpdateUserProps {
    username: string,
    firstname: string,
    lastname: string,
    dateOfBirth: string,
    email: string
}

const useUpdateUser = () => {
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
    const [data, setData] = useState<any>(null);

    const update = async (values: UpdateUserProps, jwt: string | null) => {
        setData(null)
        setError(null)
        setLoading(true)
        await fetch(`${API_URL}/user`, {
            method: "PUT",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${jwt}`
            },
            body: JSON.stringify(values)
        }).then(res => {
            if(res.status === 401) {
                setError("Your session has been expired!")
            }
            if(res.status === 400) {
                setError("Your format does not match!")
            }
            return res.json();
        })
            .then(res => {
                setData(res.data)
            })
            .catch(er => setError("Unexpected internal error!"))
            .finally(() => setLoading(false));
    }
    return { data, error, loading, update }
}


export  default  useUpdateUser;