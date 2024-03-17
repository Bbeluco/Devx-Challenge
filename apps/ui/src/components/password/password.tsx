import React, { useRef } from "react";
import { PasswordInterface } from "../../interfaces/passwordInterface";

function Password(props: PasswordInterface): React.JSX.Element {
    const inptPass1: any = useRef(null);
    const inptPass2: any = useRef(null);

    function sendPasswordToApi() {
        let password: string = inptPass1.current.value;
        if(props.response.challenge !== "SEND_PASSWORD" && password !== inptPass2.current.value) {
            alert("Passwords different, please check information");
        }

        fetch("http://localhost:8080/password", {
            headers: {
                "Accept": "application/json",
                "Content-type": "application/json"
            },
            method: "POST",
            body: JSON.stringify({ password, email: props.email })
        }).then(response => response.json())
        .then(data => {
            console.log(data);
        })
    }


    function setupPassword(): React.JSX.Element {
        return (
            <div>
                <h1>Setup your fist password</h1>
                <input type="password" id="password" name="password" ref={inptPass1} required/>
                <input type="password" id="password-confirm" name="password-confirm" ref={inptPass2} required/>
                <button id="btnSendEmail" onClick={sendPasswordToApi}>Confirm</button>
            </div>
        )
    }

    function enterPassword(): React.JSX.Element {
        return (
            <div>
                <h1>Please, insert your password</h1>
                <input type="password" id="password" name="password" ref={inptPass1} required/>
                <button id="btnSendEmail" onClick={sendPasswordToApi}>Confirm</button>
            </div>
        )
    }

    return props.response.challenge == "SET_PASSWORD" ? setupPassword() : enterPassword();
}

export default Password;