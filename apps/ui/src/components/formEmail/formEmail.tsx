import React, { useRef, useState } from "react";
import { FormEmailInterface } from "../../interfaces/FormEmailInterface";
import styles from './formEmail.module.scss';

function FormEmail(props: FormEmailInterface): React.JSX.Element {
    const inptEmailRef: any = useRef(null);
    const [requiredFiled, setRequiredField] = useState(false);

    function sendEmailToApi() {
        let email: string = inptEmailRef.current.value;

        if(!email) {
            setRequiredField(true);
            return;
        }

        fetch("http://localhost:8080/login", {
            headers: {
                "Accept": "application/json",
                "Content-type": "application/json"
            },
            method: "POST",
            body: JSON.stringify({ email })
        }).then(response => response.json())
        .then(data => {
            props.handlerResponse(data);
            props.handlerEmail(email);
            console.log(data["otpCode"]);
        })
    }

    return (
        <div>
            <h1>Login</h1>
            <div className={styles.formEmail}>
                <p>Please, insert your email</p>
                <input type="email" id="email" name="email" className={requiredFiled ? styles.required : ""} ref={inptEmailRef} required/>
                {requiredFiled ? <p className={styles.informationMessage}>Please, insert a valid email</p> : ""}
                <button id="btnSendEmail" onClick={sendEmailToApi}>Confirm</button>
            </div>
        </div>
    )
}


export default FormEmail;