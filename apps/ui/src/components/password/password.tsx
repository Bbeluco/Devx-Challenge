import React, { useRef, useState } from "react";
import { PasswordInterface } from "../../interfaces/passwordInterface";
import { useNavigate } from "react-router-dom";
import styles from "./password.module.scss";

function Password(props: PasswordInterface): React.JSX.Element {
    const inptPass1: any = useRef(null);
    const inptPass2: any = useRef(null);
    const [requiredFiled, setRequiredField] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");

    let navigate = useNavigate();

    function resetRequiredField() {
        setRequiredField(false);
    }

    function sendPasswordToApi() {
        let password: string = inptPass1.current.value;
        if(props.response.challenge !== "SEND_PASSWORD" && password !== inptPass2.current.value) {
            setErrorMessage("Passwords different, please check information");
            setRequiredField(true);
            return;
        }

        if(!password) {
            setErrorMessage("Required field");
            setRequiredField(true);
            return;
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
            props.handlerJwttoken(data["access_token"]);
            navigate("/loggedPage")
        })
        .catch(_error => {
            setErrorMessage("Invalid password");
            setRequiredField(true);
        })
    }


    function setupPassword(): React.JSX.Element {
        return (
            <div>
                <h1>Setup your fist password</h1>
                <input type="password" id="password" name="password" ref={inptPass1} className={requiredFiled ? styles.required : ""} onChange={resetRequiredField}/>
                <input type="password" id="password-confirm" name="password-confirm" ref={inptPass2} className={requiredFiled ? styles.required : ""} onChange={resetRequiredField}/>
                {requiredFiled ? <p className={styles.informationMessage}>{errorMessage}</p> : ""}
                <button id="btnSendEmail" onClick={sendPasswordToApi}>Confirm</button>
            </div>
        )
    }

    function enterPassword(): React.JSX.Element {
        return (
            <div>
                <h1>Password</h1>
                <p>Please, insert your password</p>
                <input type="password" id="password" name="password" ref={inptPass1} className={requiredFiled ? styles.required : ""} onChange={resetRequiredField}/>
                {requiredFiled ? <p className={styles.informationMessage}>{errorMessage}</p> : ""}
                <button id="btnSendEmail" onClick={sendPasswordToApi}>Confirm</button>
            </div>
        )
    }

    return props.response.challenge == "SET_PASSWORD" ? setupPassword() : enterPassword();
}

export default Password;