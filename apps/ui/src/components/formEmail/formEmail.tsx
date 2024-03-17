import React, { useRef } from "react";
import { FormEmailInterface } from "../../interfaces/FormEmailInterface";

function FormEmail(props: FormEmailInterface): React.JSX.Element {
    const inptEmailRef: any = useRef(null);

    function sendEmailToApi() {


        let email: string = inptEmailRef.current.value;
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
        })
    }

    return (
        <div>
            <h1>Please, put your login</h1>
            <input type="email" id="email" name="email" ref={inptEmailRef} required/>
            <button id="btnSendEmail" onClick={sendEmailToApi}>Confirm</button>
        </div>
    )
}


export default FormEmail;