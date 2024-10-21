"use client";
import axios from 'axios';
import { useEffect, useState } from 'react';

function Test() {
    const [data, setData] = useState(null);
    const [error, setError] = useState(null);

    useEffect(() => {
        // Check if the code is running on the client
        if (typeof window !== 'undefined') {
            const token = localStorage.getItem('token');
            axios.defaults.headers.common['Authorization'] = token ? `Bearer ${token}` : '';

            axios.get('/admin/protected-data')
                .then(response => {
                    setData(response.data);
                })
                .catch(err => {
                    setError(err.message);
                });
        }
    }, []); // Empty dependency array to run this effect only once after the component mounts.

    if (error) return <>Error: {error}</>;
    if (!data) return <>Loading...</>;
    return <>{data}</>;
}

export default Test;
