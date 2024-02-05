import {useEffect, useState} from "react";
import {useAuth} from "../context/AuthContext";

const API_URL = process.env.REACT_APP_API_URL;

interface PageProps {
    page: number,
    size: number
}

const useGetActivity = (props: PageProps) => {
    const [data, setData] = useState<any>();
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
    const { jwt, getUser } = useAuth();
    const [page, setPage] = useState<number>(props.page);
    const [size, setSize ] = useState<number>(props.size);

    // Get Activity
    useEffect(() => {
        const getActivity = async () => {
            setData(null)
            setLoading(true)
            setError(null);
            await fetch(`${API_URL}/user/activity/${getUser()?.id}?page=${page}&size=${size}`, {
                method: "GET",
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${jwt}`
                },
            }).then(res => {

                // Handle API request error
                if(res.status === 401 || res.status === 400) {
                    setError("You are not having permission to perform this action!")
                }
                return res.json();
            })
                .then(res => {
                    setData(res.data)
                })
                .catch(er => setError("Unexpected internal error!"))
                .finally(() => setLoading(false));
        }
        getActivity();
    }, [page, size]);

    return { data, loading, error, page, setPage, size, setSize  }
}

export default useGetActivity;