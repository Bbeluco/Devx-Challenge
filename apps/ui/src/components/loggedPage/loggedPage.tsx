import React, { useEffect, useState } from "react";
import { LoggedPageInterface } from "../../interfaces/loggedPageInterface";
import { Link } from "react-router-dom";


function LoggedPage({ jwtToken }: LoggedPageInterface): React.JSX.Element {
    const [response, setResponse] = useState();


    useEffect(() => {
        fetch("http://localhost:8080/private", {
            headers: {
                "Accept": "application/json",
                "Content-type": "application/json",
                "Authorization": `Bearer ${jwtToken}`
            }
        })
        .then(response => response.json())
        .then(data => {
            console.log(jwtToken);
            setResponse(data.message);
        })
        .catch(error => {
        })
    }, [jwtToken]);

    function Component(): React.JSX.Element {
        if(response) {
            return (
                <div>
                    <h1>Nice!</h1>
                    <p>{response}</p>
                    <p>If you want to check JWT token check out browser console</p>
                    <Link to="/">Click here if you want to login again</Link>
                </div>
            )
        }

        return (
            <div>
                <h1>Your unauthorized to see this window, please log in first</h1>
                <Link to="/">Click here to go to Login page</Link>
            </div>
        )
    }

    return Component();
}

export default LoggedPage;