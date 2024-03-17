import React, { useRef } from "react";
import { MfaInterface } from "../../interfaces/MfaInterface";

function Mfa(props: MfaInterface): React.JSX.Element {
    const inptOtpCode: any = useRef(null);

    function sendOtpToApi() {
        let otp: string = inptOtpCode.current.value;
        fetch("http://localhost:8080/mfa", {
            headers: {
                "Accept": "application/json",
                "Content-type": "application/json"
            },
            method: "POST",
            body: JSON.stringify({ code: otp, email: props.email })
        }).then(response => response.json())
        .then(data => {
            props.handlerResponse(data);
        })
    }


    return(
        <div>
            <img src={props.response.imageURI} alt="Qr code OTP" />
            <input type="email" id="otp" name="otp" ref={inptOtpCode} required/>
            <p>Please, scan the QR code and insert the code</p>
            <button id="btnSendEmail" onClick={sendOtpToApi}>Confirm</button>
        </div>
    )
}

export default Mfa;