import React, { useRef, useState } from "react";
import { MfaInterface } from "../../interfaces/MfaInterface";
import styles from "./mfa.module.scss"

function Mfa(props: MfaInterface): React.JSX.Element {
    const inptOtpCode: any = useRef(null);
    const [requiredFiled, setRequiredField] = useState(false);

    function resetRequiredField() {
        setRequiredField(false);
    }

    function sendOtpToApi() {
        let otp: string = inptOtpCode.current.value;

        if(!otp) {
            setRequiredField(true);
            return;
        }

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
        }).catch(_error => {
            setRequiredField(true);
        })
    }

    function registerOtpOnDevice(): React.JSX.Element {
        return(
            <div>
                <img src={props.response.imageURI} alt="Qr code OTP" width={250} height={250}/>
                <p>Please, scan the QR code and insert the code</p>
                <input type="text" id="otp" name="otp" ref={inptOtpCode} className={requiredFiled ? styles.required : ""} onChange={resetRequiredField} maxLength={6}/>
                {requiredFiled ? <p className={styles.informationMessage}>Invalid OTP Number</p> : ""}
                <button id="btnSendEmail" onClick={sendOtpToApi}>Confirm</button>
            </div>
        )
    }

    function inputOtpSavedInMobile(): React.JSX.Element {
        return(
            <div>
                <p>Please, insert OTP code</p>
                <input type="text" id="otp" name="otp" ref={inptOtpCode} className={requiredFiled ? styles.required : ""} onChange={resetRequiredField} maxLength={6}/>
                {requiredFiled ? <p className={styles.informationMessage}>Invalid OTP Number</p> : ""}
                <button id="btnSendEmail" onClick={sendOtpToApi}>Confirm</button>
            </div>
        )
    }


    return props.response.challenge == "VALIDATE_QR_CODE" 
        ? registerOtpOnDevice() 
        : inputOtpSavedInMobile();
}

export default Mfa;