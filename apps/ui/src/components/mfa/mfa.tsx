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

    function registerOtpOnDevice(): React.JSX.Element {
        return(
            <div>
                <img src={props.response.imageURI} alt="Qr code OTP" />
                <input type="email" id="otp" name="otp" ref={inptOtpCode} required/>
                <p>Please, scan the QR code and insert the code</p>
                <button id="btnSendEmail" onClick={sendOtpToApi}>Confirm</button>
            </div>
        )
    }

    function inputOtpSavedInMobile(): React.JSX.Element {
        return(
            <div>
                <p>Please, insert OTP code</p>
                <input type="email" id="otp" name="otp" ref={inptOtpCode} required/>
                <button id="btnSendEmail" onClick={sendOtpToApi}>Confirm</button>
            </div>
        )
    }


    return props.response.challenge == "VALIDATE_QR_CODE" 
        ? registerOtpOnDevice() 
        : inputOtpSavedInMobile();
}

export default Mfa;